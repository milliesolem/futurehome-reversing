package io.flutter.embedding.android;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import androidx.fragment.app.FragmentActivity;
import io.flutter.Log;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterShellArgs;
import io.flutter.embedding.engine.plugins.util.GeneratedPluginRegister;
import java.util.ArrayList;
import java.util.List;

public class FlutterFragmentActivity extends FragmentActivity implements FlutterEngineProvider, FlutterEngineConfigurator {
   public static final int FRAGMENT_CONTAINER_ID = View.generateViewId();
   private static final String TAG = "FlutterFragmentActivity";
   private static final String TAG_FLUTTER_FRAGMENT = "flutter_fragment";
   private FlutterFragment flutterFragment;

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

   private View createFragmentContainer() {
      FrameLayout var1 = this.provideRootLayout(this);
      var1.setId(FRAGMENT_CONTAINER_ID);
      var1.setLayoutParams(new LayoutParams(-1, -1));
      return var1;
   }

   private void ensureFlutterFragmentCreated() {
      if (this.flutterFragment == null) {
         this.flutterFragment = this.retrieveExistingFlutterFragmentIfPossible();
      }

      if (this.flutterFragment == null) {
         this.flutterFragment = this.createFlutterFragment();
         this.getSupportFragmentManager().beginTransaction().add(FRAGMENT_CONTAINER_ID, this.flutterFragment, "flutter_fragment").commit();
      }
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

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private void switchLaunchThemeForNormalTheme() {
      Bundle var2;
      try {
         var2 = this.getMetaData();
      } catch (NameNotFoundException var6) {
         Log.e("FlutterFragmentActivity", "Could not read meta-data for FlutterFragmentActivity. Using the launch theme as normal theme.");
         return;
      }

      if (var2 != null) {
         int var1;
         try {
            var1 = var2.getInt("io.flutter.embedding.android.NormalTheme", -1);
         } catch (NameNotFoundException var5) {
            Log.e("FlutterFragmentActivity", "Could not read meta-data for FlutterFragmentActivity. Using the launch theme as normal theme.");
            return;
         }

         if (var1 != -1) {
            try {
               this.setTheme(var1);
            } catch (NameNotFoundException var4) {
               Log.e("FlutterFragmentActivity", "Could not read meta-data for FlutterFragmentActivity. Using the launch theme as normal theme.");
            }
         }
      } else {
         try {
            Log.v("FlutterFragmentActivity", "Using the launch theme as normal theme.");
         } catch (NameNotFoundException var3) {
            Log.e("FlutterFragmentActivity", "Could not read meta-data for FlutterFragmentActivity. Using the launch theme as normal theme.");
         }
      }
   }

   public static FlutterFragmentActivity.CachedEngineIntentBuilder withCachedEngine(String var0) {
      return new FlutterFragmentActivity.CachedEngineIntentBuilder(FlutterFragmentActivity.class, var0);
   }

   public static FlutterFragmentActivity.NewEngineIntentBuilder withNewEngine() {
      return new FlutterFragmentActivity.NewEngineIntentBuilder(FlutterFragmentActivity.class);
   }

   public static FlutterFragmentActivity.NewEngineInGroupIntentBuilder withNewEngineInGroup(String var0) {
      return new FlutterFragmentActivity.NewEngineInGroupIntentBuilder(FlutterFragmentActivity.class, var0);
   }

   @Override
   public void cleanUpFlutterEngine(FlutterEngine var1) {
   }

   @Override
   public void configureFlutterEngine(FlutterEngine var1) {
      FlutterFragment var2 = this.flutterFragment;
      if (var2 == null || !var2.isFlutterEngineInjected()) {
         GeneratedPluginRegister.registerGeneratedPlugins(var1);
      }
   }

   protected FlutterFragment createFlutterFragment() {
      FlutterActivityLaunchConfigs.BackgroundMode var3 = this.getBackgroundMode();
      RenderMode var4 = this.getRenderMode();
      TransparencyMode var2;
      if (var3 == FlutterActivityLaunchConfigs.BackgroundMode.opaque) {
         var2 = TransparencyMode.opaque;
      } else {
         var2 = TransparencyMode.transparent;
      }

      boolean var1;
      if (var4 == RenderMode.surface) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (this.getCachedEngineId() != null) {
         StringBuilder var7 = new StringBuilder("Creating FlutterFragment with cached engine:\nCached engine ID: ");
         var7.append(this.getCachedEngineId());
         var7.append("\nWill destroy engine when Activity is destroyed: ");
         var7.append(this.shouldDestroyEngineWithHost());
         var7.append("\nBackground transparency mode: ");
         var7.append(var3);
         var7.append("\nWill attach FlutterEngine to Activity: ");
         var7.append(this.shouldAttachEngineToActivity());
         Log.v("FlutterFragmentActivity", var7.toString());
         return FlutterFragment.withCachedEngine(this.getCachedEngineId())
            .renderMode(var4)
            .transparencyMode(var2)
            .handleDeeplinking(this.shouldHandleDeeplinking())
            .shouldAttachEngineToActivity(this.shouldAttachEngineToActivity())
            .destroyEngineWithFragment(this.shouldDestroyEngineWithHost())
            .shouldDelayFirstAndroidViewDraw(var1)
            .shouldAutomaticallyHandleOnBackPressed(true)
            .build();
      } else {
         StringBuilder var5 = new StringBuilder("Creating FlutterFragment with new engine:\nCached engine group ID: ");
         var5.append(this.getCachedEngineGroupId());
         var5.append("\nBackground transparency mode: ");
         var5.append(var3);
         var5.append("\nDart entrypoint: ");
         var5.append(this.getDartEntrypointFunctionName());
         var5.append("\nDart entrypoint library uri: ");
         String var6;
         if (this.getDartEntrypointLibraryUri() != null) {
            var6 = this.getDartEntrypointLibraryUri();
         } else {
            var6 = "\"\"";
         }

         var5.append(var6);
         var5.append("\nInitial route: ");
         var5.append(this.getInitialRoute());
         var5.append("\nApp bundle path: ");
         var5.append(this.getAppBundlePath());
         var5.append("\nWill attach FlutterEngine to Activity: ");
         var5.append(this.shouldAttachEngineToActivity());
         Log.v("FlutterFragmentActivity", var5.toString());
         return this.getCachedEngineGroupId() != null
            ? FlutterFragment.withNewEngineInGroup(this.getCachedEngineGroupId())
               .dartEntrypoint(this.getDartEntrypointFunctionName())
               .initialRoute(this.getInitialRoute())
               .handleDeeplinking(this.shouldHandleDeeplinking())
               .renderMode(var4)
               .transparencyMode(var2)
               .shouldAttachEngineToActivity(this.shouldAttachEngineToActivity())
               .shouldDelayFirstAndroidViewDraw(var1)
               .shouldAutomaticallyHandleOnBackPressed(true)
               .build()
            : FlutterFragment.withNewEngine()
               .dartEntrypoint(this.getDartEntrypointFunctionName())
               .dartLibraryUri(this.getDartEntrypointLibraryUri())
               .dartEntrypointArgs(this.getDartEntrypointArgs())
               .initialRoute(this.getInitialRoute())
               .appBundlePath(this.getAppBundlePath())
               .flutterShellArgs(FlutterShellArgs.fromIntent(this.getIntent()))
               .handleDeeplinking(this.shouldHandleDeeplinking())
               .renderMode(var4)
               .transparencyMode(var2)
               .shouldAttachEngineToActivity(this.shouldAttachEngineToActivity())
               .shouldDelayFirstAndroidViewDraw(var1)
               .shouldAutomaticallyHandleOnBackPressed(true)
               .build();
      }
   }

   protected String getAppBundlePath() {
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

   protected String getCachedEngineGroupId() {
      return this.getIntent().getStringExtra("cached_engine_group_id");
   }

   protected String getCachedEngineId() {
      return this.getIntent().getStringExtra("cached_engine_id");
   }

   public List<String> getDartEntrypointArgs() {
      return (List<String>)this.getIntent().getSerializableExtra("dart_entrypoint_args");
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public String getDartEntrypointFunctionName() {
      String var2 = "main";

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

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
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

   protected FlutterEngine getFlutterEngine() {
      return this.flutterFragment.getFlutterEngine();
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   protected String getInitialRoute() {
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

   protected Bundle getMetaData() throws NameNotFoundException {
      return this.getPackageManager().getActivityInfo(this.getComponentName(), 128).metaData;
   }

   protected RenderMode getRenderMode() {
      RenderMode var1;
      if (this.getBackgroundMode() == FlutterActivityLaunchConfigs.BackgroundMode.opaque) {
         var1 = RenderMode.surface;
      } else {
         var1 = RenderMode.texture;
      }

      return var1;
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      super.onActivityResult(var1, var2, var3);
      this.flutterFragment.onActivityResult(var1, var2, var3);
   }

   protected void onCreate(Bundle var1) {
      this.switchLaunchThemeForNormalTheme();
      this.flutterFragment = this.retrieveExistingFlutterFragmentIfPossible();
      super.onCreate(var1);
      this.configureWindowForTransparency();
      this.setContentView(this.createFragmentContainer());
      this.configureStatusBarForFullscreenFlutterExperience();
      this.ensureFlutterFragmentCreated();
   }

   protected void onNewIntent(Intent var1) {
      this.flutterFragment.onNewIntent(var1);
      super.onNewIntent(var1);
   }

   public void onPostResume() {
      super.onPostResume();
      this.flutterFragment.onPostResume();
   }

   public void onRequestPermissionsResult(int var1, String[] var2, int[] var3) {
      super.onRequestPermissionsResult(var1, var2, var3);
      this.flutterFragment.onRequestPermissionsResult(var1, var2, var3);
   }

   public void onTrimMemory(int var1) {
      super.onTrimMemory(var1);
      this.flutterFragment.onTrimMemory(var1);
   }

   public void onUserLeaveHint() {
      this.flutterFragment.onUserLeaveHint();
   }

   @Override
   public FlutterEngine provideFlutterEngine(Context var1) {
      return null;
   }

   protected FrameLayout provideRootLayout(Context var1) {
      return new FrameLayout(var1);
   }

   FlutterFragment retrieveExistingFlutterFragmentIfPossible() {
      return (FlutterFragment)this.getSupportFragmentManager().findFragmentByTag("flutter_fragment");
   }

   protected boolean shouldAttachEngineToActivity() {
      return true;
   }

   public boolean shouldDestroyEngineWithHost() {
      return this.getIntent().getBooleanExtra("destroy_engine_with_activity", false);
   }

   protected boolean shouldHandleDeeplinking() {
      try {
         return FlutterActivityLaunchConfigs.deepLinkEnabled(this.getMetaData());
      } catch (NameNotFoundException var3) {
         return false;
      }
   }

   public static class CachedEngineIntentBuilder {
      private final Class<? extends FlutterFragmentActivity> activityClass;
      private String backgroundMode;
      private final String cachedEngineId;
      private boolean destroyEngineWithActivity = false;

      public CachedEngineIntentBuilder(Class<? extends FlutterFragmentActivity> var1, String var2) {
         this.backgroundMode = FlutterActivityLaunchConfigs.DEFAULT_BACKGROUND_MODE;
         this.activityClass = var1;
         this.cachedEngineId = var2;
      }

      public FlutterFragmentActivity.CachedEngineIntentBuilder backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode var1) {
         this.backgroundMode = var1.name();
         return this;
      }

      public Intent build(Context var1) {
         return new Intent(var1, this.activityClass)
            .putExtra("cached_engine_id", this.cachedEngineId)
            .putExtra("destroy_engine_with_activity", this.destroyEngineWithActivity)
            .putExtra("background_mode", this.backgroundMode);
      }

      public FlutterFragmentActivity.CachedEngineIntentBuilder destroyEngineWithActivity(boolean var1) {
         this.destroyEngineWithActivity = var1;
         return this;
      }
   }

   public static class NewEngineInGroupIntentBuilder {
      private final Class<? extends FlutterFragmentActivity> activityClass;
      private String backgroundMode;
      private final String cachedEngineGroupId;
      private String dartEntrypoint = "main";
      private String initialRoute = "/";

      public NewEngineInGroupIntentBuilder(Class<? extends FlutterFragmentActivity> var1, String var2) {
         this.backgroundMode = FlutterActivityLaunchConfigs.DEFAULT_BACKGROUND_MODE;
         this.activityClass = var1;
         this.cachedEngineGroupId = var2;
      }

      public FlutterFragmentActivity.NewEngineInGroupIntentBuilder backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode var1) {
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

      public FlutterFragmentActivity.NewEngineInGroupIntentBuilder dartEntrypoint(String var1) {
         this.dartEntrypoint = var1;
         return this;
      }

      public FlutterFragmentActivity.NewEngineInGroupIntentBuilder initialRoute(String var1) {
         this.initialRoute = var1;
         return this;
      }
   }

   public static class NewEngineIntentBuilder {
      private final Class<? extends FlutterFragmentActivity> activityClass;
      private String backgroundMode;
      private List<String> dartEntrypointArgs;
      private String initialRoute = "/";

      public NewEngineIntentBuilder(Class<? extends FlutterFragmentActivity> var1) {
         this.backgroundMode = FlutterActivityLaunchConfigs.DEFAULT_BACKGROUND_MODE;
         this.activityClass = var1;
      }

      public FlutterFragmentActivity.NewEngineIntentBuilder backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode var1) {
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

      public FlutterFragmentActivity.NewEngineIntentBuilder dartEntrypointArgs(List<String> var1) {
         this.dartEntrypointArgs = var1;
         return this;
      }

      public FlutterFragmentActivity.NewEngineIntentBuilder initialRoute(String var1) {
         this.initialRoute = var1;
         return this;
      }
   }
}
