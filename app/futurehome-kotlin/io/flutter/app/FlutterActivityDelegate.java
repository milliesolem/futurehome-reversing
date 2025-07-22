package io.flutter.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import io.flutter.Log;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.util.Preconditions;
import io.flutter.view.FlutterMain;
import io.flutter.view.FlutterNativeView;
import io.flutter.view.FlutterRunArguments;
import io.flutter.view.FlutterView;
import java.util.ArrayList;

@Deprecated
public final class FlutterActivityDelegate implements FlutterActivityEvents, FlutterView.Provider, PluginRegistry {
   private static final String SPLASH_SCREEN_META_DATA_KEY = "io.flutter.app.android.SplashScreenUntilFirstFrame";
   private static final String TAG = "FlutterActivityDelegate";
   private static final LayoutParams matchParent = new LayoutParams(-1, -1);
   private final Activity activity;
   private FlutterView flutterView;
   private View launchView;
   private final FlutterActivityDelegate.ViewFactory viewFactory;

   public FlutterActivityDelegate(Activity var1, FlutterActivityDelegate.ViewFactory var2) {
      this.activity = Preconditions.checkNotNull(var1);
      this.viewFactory = Preconditions.checkNotNull(var2);
   }

   private void addLaunchView() {
      View var1 = this.launchView;
      if (var1 != null) {
         this.activity.addContentView(var1, matchParent);
         this.flutterView.addFirstFrameListener(new FlutterView.FirstFrameListener(this) {
            final FlutterActivityDelegate this$0;

            {
               this.this$0 = var1;
            }

            @Override
            public void onFirstFrame() {
               this.this$0.launchView.animate().alpha(0.0F).setListener(new AnimatorListenerAdapter(this) {
                  final <unrepresentable> this$1;

                  {
                     this.this$1 = var1;
                  }

                  public void onAnimationEnd(Animator var1) {
                     ((ViewGroup)this.this$1.this$0.launchView.getParent()).removeView(this.this$1.this$0.launchView);
                     this.this$1.this$0.launchView = null;
                  }
               });
               this.this$0.flutterView.removeFirstFrameListener(this);
            }
         });
         this.activity.setTheme(16973833);
      }
   }

   private View createLaunchView() {
      if (!this.showSplashScreenUntilFirstFrame()) {
         return null;
      } else {
         Drawable var2 = this.getLaunchScreenDrawableFromActivityTheme();
         if (var2 == null) {
            return null;
         } else {
            View var1 = new View(this.activity);
            var1.setLayoutParams(matchParent);
            var1.setBackground(var2);
            return var1;
         }
      }
   }

   private static String[] getArgsFromIntent(Intent var0) {
      ArrayList var2 = new ArrayList();
      if (var0.getBooleanExtra("trace-startup", false)) {
         var2.add("--trace-startup");
      }

      if (var0.getBooleanExtra("start-paused", false)) {
         var2.add("--start-paused");
      }

      if (var0.getBooleanExtra("disable-service-auth-codes", false)) {
         var2.add("--disable-service-auth-codes");
      }

      if (var0.getBooleanExtra("use-test-fonts", false)) {
         var2.add("--use-test-fonts");
      }

      if (var0.getBooleanExtra("enable-dart-profiling", false)) {
         var2.add("--enable-dart-profiling");
      }

      if (var0.getBooleanExtra("enable-software-rendering", false)) {
         var2.add("--enable-software-rendering");
      }

      if (var0.getBooleanExtra("skia-deterministic-rendering", false)) {
         var2.add("--skia-deterministic-rendering");
      }

      if (var0.getBooleanExtra("trace-skia", false)) {
         var2.add("--trace-skia");
      }

      if (var0.getBooleanExtra("trace-systrace", false)) {
         var2.add("--trace-systrace");
      }

      if (var0.hasExtra("trace-to-file")) {
         StringBuilder var3 = new StringBuilder("--trace-to-file=");
         var3.append(var0.getStringExtra("trace-to-file"));
         var2.add(var3.toString());
      }

      if (var0.getBooleanExtra("dump-skp-on-shader-compilation", false)) {
         var2.add("--dump-skp-on-shader-compilation");
      }

      if (var0.getBooleanExtra("cache-sksl", false)) {
         var2.add("--cache-sksl");
      }

      if (var0.getBooleanExtra("purge-persistent-cache", false)) {
         var2.add("--purge-persistent-cache");
      }

      if (var0.getBooleanExtra("verbose-logging", false)) {
         var2.add("--verbose-logging");
      }

      int var1 = var0.getIntExtra("vm-service-port", 0);
      if (var1 > 0) {
         StringBuilder var5 = new StringBuilder("--vm-service-port=");
         var5.append(Integer.toString(var1));
         var2.add(var5.toString());
      } else {
         var1 = var0.getIntExtra("observatory-port", 0);
         if (var1 > 0) {
            StringBuilder var6 = new StringBuilder("--vm-service-port=");
            var6.append(Integer.toString(var1));
            var2.add(var6.toString());
         }
      }

      if (var0.getBooleanExtra("endless-trace-buffer", false)) {
         var2.add("--endless-trace-buffer");
      }

      if (var0.hasExtra("dart-flags")) {
         StringBuilder var7 = new StringBuilder("--dart-flags=");
         var7.append(var0.getStringExtra("dart-flags"));
         var2.add(var7.toString());
      }

      return !var2.isEmpty() ? var2.toArray(new String[var2.size()]) : null;
   }

   private Drawable getLaunchScreenDrawableFromActivityTheme() {
      TypedValue var1 = new TypedValue();
      if (!this.activity.getTheme().resolveAttribute(16842836, var1, true)) {
         return null;
      } else if (var1.resourceId == 0) {
         return null;
      } else {
         try {
            return this.activity.getResources().getDrawable(var1.resourceId);
         } catch (NotFoundException var2) {
            Log.e("FlutterActivityDelegate", "Referenced launch screen windowBackground resource does not exist");
            return null;
         }
      }
   }

   private boolean isDebuggable() {
      boolean var1;
      if ((this.activity.getApplicationInfo().flags & 2) != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private boolean loadIntent(Intent var1) {
      if ("android.intent.action.RUN".equals(var1.getAction())) {
         String var3 = var1.getStringExtra("route");
         String var2 = var1.getDataString();
         String var4 = var2;
         if (var2 == null) {
            var4 = FlutterMain.findAppBundlePath();
         }

         if (var3 != null) {
            this.flutterView.setInitialRoute(var3);
         }

         this.runBundle(var4);
         return true;
      } else {
         return false;
      }
   }

   private void runBundle(String var1) {
      if (!this.flutterView.getFlutterNativeView().isApplicationRunning()) {
         FlutterRunArguments var2 = new FlutterRunArguments();
         var2.bundlePath = var1;
         var2.entrypoint = "main";
         this.flutterView.runFromBundle(var2);
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private Boolean showSplashScreenUntilFirstFrame() {
      Bundle var2;
      try {
         var2 = this.activity.getPackageManager().getActivityInfo(this.activity.getComponentName(), 128).metaData;
      } catch (NameNotFoundException var3) {
         return false;
      }

      label29: {
         if (var2 != null) {
            try {
               if (var2.getBoolean("io.flutter.app.android.SplashScreenUntilFirstFrame")) {
                  break label29;
               }
            } catch (NameNotFoundException var4) {
               return false;
            }
         }

         boolean var1 = false;
         return var1;
      }

      boolean var5 = true;
      return var5;
   }

   @Override
   public FlutterView getFlutterView() {
      return this.flutterView;
   }

   @Override
   public boolean hasPlugin(String var1) {
      return this.flutterView.getPluginRegistry().hasPlugin(var1);
   }

   @Override
   public boolean onActivityResult(int var1, int var2, Intent var3) {
      return this.flutterView.getPluginRegistry().onActivityResult(var1, var2, var3);
   }

   @Override
   public boolean onBackPressed() {
      FlutterView var1 = this.flutterView;
      if (var1 != null) {
         var1.popRoute();
         return true;
      } else {
         return false;
      }
   }

   public void onConfigurationChanged(Configuration var1) {
   }

   @Override
   public void onCreate(Bundle var1) {
      Window var2 = this.activity.getWindow();
      var2.addFlags(Integer.MIN_VALUE);
      var2.setStatusBarColor(1073741824);
      var2.getDecorView().setSystemUiVisibility(1280);
      String[] var3 = getArgsFromIntent(this.activity.getIntent());
      FlutterMain.ensureInitializationComplete(this.activity.getApplicationContext(), var3);
      FlutterView var4 = this.viewFactory.createFlutterView(this.activity);
      this.flutterView = var4;
      if (var4 == null) {
         FlutterNativeView var5 = this.viewFactory.createFlutterNativeView();
         FlutterView var6 = new FlutterView(this.activity, null, var5);
         this.flutterView = var6;
         var6.setLayoutParams(matchParent);
         this.activity.setContentView(this.flutterView);
         View var7 = this.createLaunchView();
         this.launchView = var7;
         if (var7 != null) {
            this.addLaunchView();
         }
      }

      if (!this.loadIntent(this.activity.getIntent())) {
         String var8 = FlutterMain.findAppBundlePath();
         if (var8 != null) {
            this.runBundle(var8);
         }
      }
   }

   @Override
   public void onDestroy() {
      Application var1 = (Application)this.activity.getApplicationContext();
      if (var1 instanceof FlutterApplication) {
         FlutterApplication var2 = (FlutterApplication)var1;
         if (this.activity.equals(var2.getCurrentActivity())) {
            var2.setCurrentActivity(null);
         }
      }

      FlutterView var3 = this.flutterView;
      if (var3 != null) {
         if (!var3.getPluginRegistry().onViewDestroy(this.flutterView.getFlutterNativeView()) && !this.viewFactory.retainFlutterNativeView()) {
            this.flutterView.destroy();
         } else {
            this.flutterView.detach();
         }
      }
   }

   public void onLowMemory() {
      this.flutterView.onMemoryPressure();
   }

   @Override
   public void onNewIntent(Intent var1) {
      if (!this.isDebuggable() || !this.loadIntent(var1)) {
         this.flutterView.getPluginRegistry().onNewIntent(var1);
      }
   }

   @Override
   public void onPause() {
      Application var1 = (Application)this.activity.getApplicationContext();
      if (var1 instanceof FlutterApplication) {
         FlutterApplication var2 = (FlutterApplication)var1;
         if (this.activity.equals(var2.getCurrentActivity())) {
            var2.setCurrentActivity(null);
         }
      }

      FlutterView var3 = this.flutterView;
      if (var3 != null) {
         var3.onPause();
      }
   }

   @Override
   public void onPostResume() {
      FlutterView var1 = this.flutterView;
      if (var1 != null) {
         var1.onPostResume();
      }
   }

   @Override
   public boolean onRequestPermissionsResult(int var1, String[] var2, int[] var3) {
      return this.flutterView.getPluginRegistry().onRequestPermissionsResult(var1, var2, var3);
   }

   @Override
   public void onResume() {
      Application var1 = (Application)this.activity.getApplicationContext();
      if (var1 instanceof FlutterApplication) {
         ((FlutterApplication)var1).setCurrentActivity(this.activity);
      }
   }

   @Override
   public void onStart() {
      FlutterView var1 = this.flutterView;
      if (var1 != null) {
         var1.onStart();
      }
   }

   @Override
   public void onStop() {
      this.flutterView.onStop();
   }

   public void onTrimMemory(int var1) {
      if (var1 == 10) {
         this.flutterView.onMemoryPressure();
      }
   }

   @Override
   public void onUserLeaveHint() {
      this.flutterView.getPluginRegistry().onUserLeaveHint();
   }

   @Override
   public void onWindowFocusChanged(boolean var1) {
      this.flutterView.getPluginRegistry().onWindowFocusChanged(var1);
   }

   @Override
   public PluginRegistry.Registrar registrarFor(String var1) {
      return this.flutterView.getPluginRegistry().registrarFor(var1);
   }

   @Override
   public <T> T valuePublishedByPlugin(String var1) {
      return this.flutterView.getPluginRegistry().valuePublishedByPlugin(var1);
   }

   public interface ViewFactory {
      FlutterNativeView createFlutterNativeView();

      FlutterView createFlutterView(Context var1);

      boolean retainFlutterNativeView();
   }
}
