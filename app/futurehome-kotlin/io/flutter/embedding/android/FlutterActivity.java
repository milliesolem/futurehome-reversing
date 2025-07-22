package io.flutter.embedding.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.view.View;
import android.view.Window;
import android.window.BackEvent;
import android.window.OnBackAnimationCallback;
import android.window.OnBackInvokedCallback;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Lifecycle.Event;
import io.flutter.Log;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterShellArgs;
import io.flutter.embedding.engine.plugins.util.GeneratedPluginRegister;
import io.flutter.plugin.platform.PlatformPlugin;
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0;
import java.util.ArrayList;
import java.util.List;

public class FlutterActivity extends Activity implements FlutterActivityAndFragmentDelegate.Host, LifecycleOwner {
   public static final int FLUTTER_VIEW_ID = View.generateViewId();
   private static final String TAG = "FlutterActivity";
   protected FlutterActivityAndFragmentDelegate delegate;
   private boolean hasRegisteredBackCallback = false;
   private LifecycleRegistry lifecycle;
   private final OnBackInvokedCallback onBackInvokedCallback;

   public FlutterActivity() {
      OnBackInvokedCallback var1;
      if (VERSION.SDK_INT < 33) {
         var1 = null;
      } else {
         var1 = this.createOnBackInvokedCallback();
      }

      this.onBackInvokedCallback = var1;
      this.lifecycle = new LifecycleRegistry(this);
   }

   private void configureStatusBarForFullscreenFlutterExperience() {
      Window var1 = this.getWindow();
      var1.addFlags(Integer.MIN_VALUE);
      var1.setStatusBarColor(1073741824);
      var1.getDecorView().setSystemUiVisibility(1280);
   }

   private void configureWindowForTransparency() {
      if (this.getBackgroundMode() == FlutterActivityLaunchConfigs.BackgroundMode.transparent) {
         this.getWindow().setBackgroundDrawable(new ColorDrawable(0));
      }
   }

   public static Intent createDefaultIntent(Context var0) {
      return withNewEngine().build(var0);
   }

   private View createFlutterView() {
      FlutterActivityAndFragmentDelegate var3 = this.delegate;
      int var1 = FLUTTER_VIEW_ID;
      boolean var2;
      if (this.getRenderMode() == RenderMode.surface) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var3.onCreateView(null, null, null, var1, var2);
   }

   private OnBackInvokedCallback createOnBackInvokedCallback() {
      return (OnBackInvokedCallback)(VERSION.SDK_INT >= 34 ? new OnBackAnimationCallback(this) {
         final FlutterActivity this$0;

         {
            this.this$0 = var1;
         }

         public void onBackCancelled() {
            this.this$0.cancelBackGesture();
         }

         public void onBackInvoked() {
            this.this$0.commitBackGesture();
         }

         public void onBackProgressed(BackEvent var1) {
            this.this$0.updateBackGestureProgress(var1);
         }

         public void onBackStarted(BackEvent var1) {
            this.this$0.startBackGesture(var1);
         }
      } : new FlutterActivity$$ExternalSyntheticLambda3(this));
   }

   private boolean isDebuggable() {
      boolean var1;
      if ((this.getApplicationInfo().flags & 2) != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private boolean stillAttachedForEvent(String var1) {
      FlutterActivityAndFragmentDelegate var2 = this.delegate;
      if (var2 == null) {
         StringBuilder var4 = new StringBuilder("FlutterActivity ");
         var4.append(this.hashCode());
         var4.append(" ");
         var4.append(var1);
         var4.append(" called after release.");
         Log.w("FlutterActivity", var4.toString());
         return false;
      } else if (!var2.isAttached()) {
         StringBuilder var3 = new StringBuilder("FlutterActivity ");
         var3.append(this.hashCode());
         var3.append(" ");
         var3.append(var1);
         var3.append(" called after detach.");
         Log.w("FlutterActivity", var3.toString());
         return false;
      } else {
         return true;
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private void switchLaunchThemeForNormalTheme() {
      Bundle var2;
      try {
         var2 = this.getMetaData();
      } catch (NameNotFoundException var6) {
         Log.e("FlutterActivity", "Could not read meta-data for FlutterActivity. Using the launch theme as normal theme.");
         return;
      }

      if (var2 != null) {
         int var1;
         try {
            var1 = var2.getInt("io.flutter.embedding.android.NormalTheme", -1);
         } catch (NameNotFoundException var5) {
            Log.e("FlutterActivity", "Could not read meta-data for FlutterActivity. Using the launch theme as normal theme.");
            return;
         }

         if (var1 != -1) {
            try {
               this.setTheme(var1);
            } catch (NameNotFoundException var4) {
               Log.e("FlutterActivity", "Could not read meta-data for FlutterActivity. Using the launch theme as normal theme.");
            }
         }
      } else {
         try {
            Log.v("FlutterActivity", "Using the launch theme as normal theme.");
         } catch (NameNotFoundException var3) {
            Log.e("FlutterActivity", "Could not read meta-data for FlutterActivity. Using the launch theme as normal theme.");
         }
      }
   }

   public static FlutterActivity.CachedEngineIntentBuilder withCachedEngine(String var0) {
      return new FlutterActivity.CachedEngineIntentBuilder(FlutterActivity.class, var0);
   }

   public static FlutterActivity.NewEngineIntentBuilder withNewEngine() {
      return new FlutterActivity.NewEngineIntentBuilder(FlutterActivity.class);
   }

   public static FlutterActivity.NewEngineInGroupIntentBuilder withNewEngineInGroup(String var0) {
      return new FlutterActivity.NewEngineInGroupIntentBuilder(FlutterActivity.class, var0);
   }

   @Override
   public boolean attachToEngineAutomatically() {
      return true;
   }

   public void cancelBackGesture() {
      if (this.stillAttachedForEvent("cancelBackGesture")) {
         this.delegate.cancelBackGesture();
      }
   }

   @Override
   public void cleanUpFlutterEngine(FlutterEngine var1) {
   }

   public void commitBackGesture() {
      if (this.stillAttachedForEvent("commitBackGesture")) {
         this.delegate.commitBackGesture();
      }
   }

   @Override
   public void configureFlutterEngine(FlutterEngine var1) {
      if (!this.delegate.isFlutterEngineFromHost()) {
         GeneratedPluginRegister.registerGeneratedPlugins(var1);
      }
   }

   @Override
   public void detachFromFlutterEngine() {
      StringBuilder var1 = new StringBuilder("FlutterActivity ");
      var1.append(this);
      var1.append(" connection to the engine ");
      var1.append(this.getFlutterEngine());
      var1.append(" evicted by another attaching activity");
      Log.w("FlutterActivity", var1.toString());
      FlutterActivityAndFragmentDelegate var2 = this.delegate;
      if (var2 != null) {
         var2.onDestroyView();
         this.delegate.onDetach();
      }
   }

   @Override
   public Activity getActivity() {
      return this;
   }

   @Override
   public String getAppBundlePath() {
      if (this.isDebuggable() && "android.intent.action.RUN".equals(this.getIntent().getAction())) {
         String var1 = this.getIntent().getDataString();
         if (var1 != null) {
            return var1;
         }
      }

      return null;
   }

   protected FlutterActivityLaunchConfigs.BackgroundMode getBackgroundMode() {
      return this.getIntent().hasExtra("background_mode")
         ? FlutterActivityLaunchConfigs.BackgroundMode.valueOf(this.getIntent().getStringExtra("background_mode"))
         : FlutterActivityLaunchConfigs.BackgroundMode.opaque;
   }

   @Override
   public String getCachedEngineGroupId() {
      return this.getIntent().getStringExtra("cached_engine_group_id");
   }

   @Override
   public String getCachedEngineId() {
      return this.getIntent().getStringExtra("cached_engine_id");
   }

   @Override
   public Context getContext() {
      return this;
   }

   @Override
   public List<String> getDartEntrypointArgs() {
      return (List<String>)this.getIntent().getSerializableExtra("dart_entrypoint_args");
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   @Override
   public String getDartEntrypointFunctionName() {
      String var2 = "main";
      if (this.getIntent().hasExtra("dart_entrypoint")) {
         return this.getIntent().getStringExtra("dart_entrypoint");
      } else {
         Bundle var1;
         try {
            var1 = this.getMetaData();
         } catch (NameNotFoundException var4) {
            return var2;
         }

         String var5;
         if (var1 != null) {
            try {
               var5 = var1.getString("io.flutter.Entrypoint");
            } catch (NameNotFoundException var3) {
               return var2;
            }
         } else {
            var5 = null;
         }

         if (var5 != null) {
            var2 = var5;
         }

         return var2;
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   @Override
   public String getDartEntrypointLibraryUri() {
      Object var2 = null;

      Bundle var3;
      try {
         var3 = this.getMetaData();
      } catch (NameNotFoundException var5) {
         return (String)var2;
      }

      String var1 = (String)var2;
      if (var3 != null) {
         try {
            var1 = var3.getString("io.flutter.EntrypointUri");
         } catch (NameNotFoundException var4) {
            var1 = (String)var2;
         }
      }

      return var1;
   }

   @Override
   public ExclusiveAppComponent<Activity> getExclusiveAppComponent() {
      return this.delegate;
   }

   protected FlutterEngine getFlutterEngine() {
      return this.delegate.getFlutterEngine();
   }

   @Override
   public FlutterShellArgs getFlutterShellArgs() {
      return FlutterShellArgs.fromIntent(this.getIntent());
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   @Override
   public String getInitialRoute() {
      if (this.getIntent().hasExtra("route")) {
         return this.getIntent().getStringExtra("route");
      } else {
         Object var2 = null;

         Bundle var3;
         try {
            var3 = this.getMetaData();
         } catch (NameNotFoundException var5) {
            return (String)var2;
         }

         String var1 = (String)var2;
         if (var3 != null) {
            try {
               var1 = var3.getString("io.flutter.InitialRoute");
            } catch (NameNotFoundException var4) {
               var1 = (String)var2;
            }
         }

         return var1;
      }
   }

   @Override
   public Lifecycle getLifecycle() {
      return this.lifecycle;
   }

   protected Bundle getMetaData() throws NameNotFoundException {
      return this.getPackageManager().getActivityInfo(this.getComponentName(), 128).metaData;
   }

   protected OnBackInvokedCallback getOnBackInvokedCallback() {
      return this.onBackInvokedCallback;
   }

   @Override
   public RenderMode getRenderMode() {
      RenderMode var1;
      if (this.getBackgroundMode() == FlutterActivityLaunchConfigs.BackgroundMode.opaque) {
         var1 = RenderMode.surface;
      } else {
         var1 = RenderMode.texture;
      }

      return var1;
   }

   @Override
   public TransparencyMode getTransparencyMode() {
      TransparencyMode var1;
      if (this.getBackgroundMode() == FlutterActivityLaunchConfigs.BackgroundMode.opaque) {
         var1 = TransparencyMode.opaque;
      } else {
         var1 = TransparencyMode.transparent;
      }

      return var1;
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      if (this.stillAttachedForEvent("onActivityResult")) {
         this.delegate.onActivityResult(var1, var2, var3);
      }
   }

   public void onBackPressed() {
      if (this.stillAttachedForEvent("onBackPressed")) {
         this.delegate.onBackPressed();
      }
   }

   protected void onCreate(Bundle var1) {
      this.switchLaunchThemeForNormalTheme();
      super.onCreate(var1);
      FlutterActivityAndFragmentDelegate var2 = new FlutterActivityAndFragmentDelegate(this);
      this.delegate = var2;
      var2.onAttach(this);
      this.delegate.onRestoreInstanceState(var1);
      this.lifecycle.handleLifecycleEvent(Event.ON_CREATE);
      this.configureWindowForTransparency();
      this.setContentView(this.createFlutterView());
      this.configureStatusBarForFullscreenFlutterExperience();
   }

   protected void onDestroy() {
      super.onDestroy();
      if (this.stillAttachedForEvent("onDestroy")) {
         this.delegate.onDestroyView();
         this.delegate.onDetach();
      }

      this.release();
      this.lifecycle.handleLifecycleEvent(Event.ON_DESTROY);
   }

   @Override
   public void onFlutterSurfaceViewCreated(FlutterSurfaceView var1) {
   }

   @Override
   public void onFlutterTextureViewCreated(FlutterTextureView var1) {
   }

   @Override
   public void onFlutterUiDisplayed() {
      if (VERSION.SDK_INT >= 29) {
         this.reportFullyDrawn();
      }
   }

   @Override
   public void onFlutterUiNoLongerDisplayed() {
   }

   protected void onNewIntent(Intent var1) {
      super.onNewIntent(var1);
      if (this.stillAttachedForEvent("onNewIntent")) {
         this.delegate.onNewIntent(var1);
      }
   }

   protected void onPause() {
      super.onPause();
      if (this.stillAttachedForEvent("onPause")) {
         this.delegate.onPause();
      }

      this.lifecycle.handleLifecycleEvent(Event.ON_PAUSE);
   }

   public void onPostResume() {
      super.onPostResume();
      if (this.stillAttachedForEvent("onPostResume")) {
         this.delegate.onPostResume();
      }
   }

   public void onRequestPermissionsResult(int var1, String[] var2, int[] var3) {
      if (this.stillAttachedForEvent("onRequestPermissionsResult")) {
         this.delegate.onRequestPermissionsResult(var1, var2, var3);
      }
   }

   protected void onResume() {
      super.onResume();
      this.lifecycle.handleLifecycleEvent(Event.ON_RESUME);
      if (this.stillAttachedForEvent("onResume")) {
         this.delegate.onResume();
      }
   }

   protected void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
      if (this.stillAttachedForEvent("onSaveInstanceState")) {
         this.delegate.onSaveInstanceState(var1);
      }
   }

   protected void onStart() {
      super.onStart();
      this.lifecycle.handleLifecycleEvent(Event.ON_START);
      if (this.stillAttachedForEvent("onStart")) {
         this.delegate.onStart();
      }
   }

   protected void onStop() {
      super.onStop();
      if (this.stillAttachedForEvent("onStop")) {
         this.delegate.onStop();
      }

      this.lifecycle.handleLifecycleEvent(Event.ON_STOP);
   }

   public void onTrimMemory(int var1) {
      super.onTrimMemory(var1);
      if (this.stillAttachedForEvent("onTrimMemory")) {
         this.delegate.onTrimMemory(var1);
      }
   }

   public void onUserLeaveHint() {
      if (this.stillAttachedForEvent("onUserLeaveHint")) {
         this.delegate.onUserLeaveHint();
      }
   }

   public void onWindowFocusChanged(boolean var1) {
      super.onWindowFocusChanged(var1);
      if (this.stillAttachedForEvent("onWindowFocusChanged")) {
         this.delegate.onWindowFocusChanged(var1);
      }
   }

   @Override
   public boolean popSystemNavigator() {
      return false;
   }

   @Override
   public FlutterEngine provideFlutterEngine(Context var1) {
      return null;
   }

   @Override
   public PlatformPlugin providePlatformPlugin(Activity var1, FlutterEngine var2) {
      return new PlatformPlugin(this.getActivity(), var2.getPlatformChannel(), this);
   }

   public void registerOnBackInvokedCallback() {
      if (VERSION.SDK_INT >= 33) {
         AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(this), 0, this.onBackInvokedCallback);
         this.hasRegisteredBackCallback = true;
      }
   }

   public void release() {
      this.unregisterOnBackInvokedCallback();
      FlutterActivityAndFragmentDelegate var1 = this.delegate;
      if (var1 != null) {
         var1.release();
         this.delegate = null;
      }
   }

   void setDelegate(FlutterActivityAndFragmentDelegate var1) {
      this.delegate = var1;
   }

   @Override
   public void setFrameworkHandlesBack(boolean var1) {
      if (var1 && !this.hasRegisteredBackCallback) {
         this.registerOnBackInvokedCallback();
      } else if (!var1 && this.hasRegisteredBackCallback) {
         this.unregisterOnBackInvokedCallback();
      }
   }

   @Override
   public boolean shouldAttachEngineToActivity() {
      return true;
   }

   @Override
   public boolean shouldDestroyEngineWithHost() {
      boolean var2 = this.getIntent().getBooleanExtra("destroy_engine_with_activity", false);
      boolean var1 = var2;
      if (this.getCachedEngineId() == null) {
         if (this.delegate.isFlutterEngineFromHost()) {
            var1 = var2;
         } else {
            var1 = this.getIntent().getBooleanExtra("destroy_engine_with_activity", true);
         }
      }

      return var1;
   }

   @Override
   public boolean shouldDispatchAppLifecycleState() {
      return true;
   }

   @Override
   public boolean shouldHandleDeeplinking() {
      try {
         return FlutterActivityLaunchConfigs.deepLinkEnabled(this.getMetaData());
      } catch (NameNotFoundException var3) {
         return false;
      }
   }

   @Override
   public boolean shouldRestoreAndSaveState() {
      return this.getIntent().hasExtra("enable_state_restoration")
         ? this.getIntent().getBooleanExtra("enable_state_restoration", false)
         : this.getCachedEngineId() == null;
   }

   public void startBackGesture(BackEvent var1) {
      if (this.stillAttachedForEvent("startBackGesture")) {
         this.delegate.startBackGesture(var1);
      }
   }

   public void unregisterOnBackInvokedCallback() {
      if (VERSION.SDK_INT >= 33) {
         AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(this), this.onBackInvokedCallback);
         this.hasRegisteredBackCallback = false;
      }
   }

   public void updateBackGestureProgress(BackEvent var1) {
      if (this.stillAttachedForEvent("updateBackGestureProgress")) {
         this.delegate.updateBackGestureProgress(var1);
      }
   }

   @Override
   public void updateSystemUiOverlays() {
      FlutterActivityAndFragmentDelegate var1 = this.delegate;
      if (var1 != null) {
         var1.updateSystemUiOverlays();
      }
   }

   public static class CachedEngineIntentBuilder {
      private final Class<? extends FlutterActivity> activityClass;
      private String backgroundMode;
      private final String cachedEngineId;
      private boolean destroyEngineWithActivity = false;

      public CachedEngineIntentBuilder(Class<? extends FlutterActivity> var1, String var2) {
         this.backgroundMode = FlutterActivityLaunchConfigs.DEFAULT_BACKGROUND_MODE;
         this.activityClass = var1;
         this.cachedEngineId = var2;
      }

      public FlutterActivity.CachedEngineIntentBuilder backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode var1) {
         this.backgroundMode = var1.name();
         return this;
      }

      public Intent build(Context var1) {
         return new Intent(var1, this.activityClass)
            .putExtra("cached_engine_id", this.cachedEngineId)
            .putExtra("destroy_engine_with_activity", this.destroyEngineWithActivity)
            .putExtra("background_mode", this.backgroundMode);
      }

      public FlutterActivity.CachedEngineIntentBuilder destroyEngineWithActivity(boolean var1) {
         this.destroyEngineWithActivity = var1;
         return this;
      }
   }

   public static class NewEngineInGroupIntentBuilder {
      private final Class<? extends FlutterActivity> activityClass;
      private String backgroundMode;
      private final String cachedEngineGroupId;
      private String dartEntrypoint = "main";
      private String initialRoute = "/";

      public NewEngineInGroupIntentBuilder(Class<? extends FlutterActivity> var1, String var2) {
         this.backgroundMode = FlutterActivityLaunchConfigs.DEFAULT_BACKGROUND_MODE;
         this.activityClass = var1;
         this.cachedEngineGroupId = var2;
      }

      public FlutterActivity.NewEngineInGroupIntentBuilder backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode var1) {
         this.backgroundMode = var1.name();
         return this;
      }

      public Intent build(Context var1) {
         return new Intent(var1, this.activityClass)
            .putExtra("dart_entrypoint", this.dartEntrypoint)
            .putExtra("route", this.initialRoute)
            .putExtra("cached_engine_group_id", this.cachedEngineGroupId)
            .putExtra("background_mode", this.backgroundMode)
            .putExtra("destroy_engine_with_activity", true);
      }

      public FlutterActivity.NewEngineInGroupIntentBuilder dartEntrypoint(String var1) {
         this.dartEntrypoint = var1;
         return this;
      }

      public FlutterActivity.NewEngineInGroupIntentBuilder initialRoute(String var1) {
         this.initialRoute = var1;
         return this;
      }
   }

   public static class NewEngineIntentBuilder {
      private final Class<? extends FlutterActivity> activityClass;
      private String backgroundMode;
      private List<String> dartEntrypointArgs;
      private String initialRoute = "/";

      public NewEngineIntentBuilder(Class<? extends FlutterActivity> var1) {
         this.backgroundMode = FlutterActivityLaunchConfigs.DEFAULT_BACKGROUND_MODE;
         this.activityClass = var1;
      }

      public FlutterActivity.NewEngineIntentBuilder backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode var1) {
         this.backgroundMode = var1.name();
         return this;
      }

      public Intent build(Context var1) {
         Intent var2 = new Intent(var1, this.activityClass)
            .putExtra("route", this.initialRoute)
            .putExtra("background_mode", this.backgroundMode)
            .putExtra("destroy_engine_with_activity", true);
         if (this.dartEntrypointArgs != null) {
            var2.putExtra("dart_entrypoint_args", new ArrayList<>(this.dartEntrypointArgs));
         }

         return var2;
      }

      public FlutterActivity.NewEngineIntentBuilder dartEntrypointArgs(List<String> var1) {
         this.dartEntrypointArgs = var1;
         return this;
      }

      public FlutterActivity.NewEngineIntentBuilder initialRoute(String var1) {
         this.initialRoute = var1;
         return this;
      }
   }
}
