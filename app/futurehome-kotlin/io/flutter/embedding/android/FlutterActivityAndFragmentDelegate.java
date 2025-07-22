package io.flutter.embedding.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.window.BackEvent;
import androidx.lifecycle.Lifecycle;
import io.flutter.FlutterInjector;
import io.flutter.Log;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.FlutterEngineGroup;
import io.flutter.embedding.engine.FlutterEngineGroupCache;
import io.flutter.embedding.engine.FlutterShellArgs;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.embedding.engine.renderer.FlutterUiDisplayListener;
import io.flutter.plugin.platform.PlatformPlugin;
import java.util.Arrays;
import java.util.List;

class FlutterActivityAndFragmentDelegate implements ExclusiveAppComponent<Activity> {
   private static final int FLUTTER_SPLASH_VIEW_FALLBACK_ID = 486947586;
   private static final String FRAMEWORK_RESTORATION_BUNDLE_KEY = "framework";
   private static final String PLUGINS_RESTORATION_BUNDLE_KEY = "plugins";
   private static final String TAG = "FlutterActivityAndFragmentDelegate";
   OnPreDrawListener activePreDrawListener;
   private FlutterEngineGroup engineGroup;
   private FlutterEngine flutterEngine;
   private final FlutterUiDisplayListener flutterUiDisplayListener = new FlutterUiDisplayListener(this) {
      final FlutterActivityAndFragmentDelegate this$0;

      {
         this.this$0 = var1;
      }

      @Override
      public void onFlutterUiDisplayed() {
         this.this$0.host.onFlutterUiDisplayed();
         this.this$0.isFlutterUiDisplayed = true;
         this.this$0.isFirstFrameRendered = true;
      }

      @Override
      public void onFlutterUiNoLongerDisplayed() {
         this.this$0.host.onFlutterUiNoLongerDisplayed();
         this.this$0.isFlutterUiDisplayed = false;
      }
   };
   FlutterView flutterView;
   private FlutterActivityAndFragmentDelegate.Host host;
   private boolean isAttached;
   private boolean isFirstFrameRendered;
   private boolean isFlutterEngineFromHost;
   private boolean isFlutterUiDisplayed;
   private PlatformPlugin platformPlugin;
   private Integer previousVisibility;

   FlutterActivityAndFragmentDelegate(FlutterActivityAndFragmentDelegate.Host var1) {
      this(var1, null);
   }

   FlutterActivityAndFragmentDelegate(FlutterActivityAndFragmentDelegate.Host var1, FlutterEngineGroup var2) {
      this.host = var1;
      this.isFirstFrameRendered = false;
      this.engineGroup = var2;
   }

   private FlutterEngineGroup.Options addEntrypointOptions(FlutterEngineGroup.Options var1) {
      String var2;
      label17: {
         String var3 = this.host.getAppBundlePath();
         if (var3 != null) {
            var2 = var3;
            if (!var3.isEmpty()) {
               break label17;
            }
         }

         var2 = FlutterInjector.instance().flutterLoader().findAppBundlePath();
      }

      DartExecutor.DartEntrypoint var4 = new DartExecutor.DartEntrypoint(var2, this.host.getDartEntrypointFunctionName());
      String var6 = this.host.getInitialRoute();
      var2 = var6;
      if (var6 == null) {
         var6 = this.maybeGetInitialRouteFromIntent(this.host.getActivity().getIntent());
         var2 = var6;
         if (var6 == null) {
            var2 = "/";
         }
      }

      return var1.setDartEntrypoint(var4).setInitialRoute(var2).setDartEntrypointArgs(this.host.getDartEntrypointArgs());
   }

   private void delayFirstAndroidViewDraw(FlutterView var1) {
      if (this.host.getRenderMode() == RenderMode.surface) {
         if (this.activePreDrawListener != null) {
            var1.getViewTreeObserver().removeOnPreDrawListener(this.activePreDrawListener);
         }

         this.activePreDrawListener = new OnPreDrawListener(this, var1) {
            final FlutterActivityAndFragmentDelegate this$0;
            final FlutterView val$flutterView;

            {
               this.this$0 = var1;
               this.val$flutterView = var2;
            }

            public boolean onPreDraw() {
               if (this.this$0.isFlutterUiDisplayed && this.this$0.activePreDrawListener != null) {
                  this.val$flutterView.getViewTreeObserver().removeOnPreDrawListener(this);
                  this.this$0.activePreDrawListener = null;
               }

               return this.this$0.isFlutterUiDisplayed;
            }
         };
         var1.getViewTreeObserver().addOnPreDrawListener(this.activePreDrawListener);
      } else {
         throw new IllegalArgumentException("Cannot delay the first Android view draw when the render mode is not set to `RenderMode.surface`.");
      }
   }

   private void doInitialFlutterViewRun() {
      if (this.host.getCachedEngineId() == null) {
         if (!this.flutterEngine.getDartExecutor().isExecutingDart()) {
            String var2 = this.host.getInitialRoute();
            String var1 = var2;
            if (var2 == null) {
               var2 = this.maybeGetInitialRouteFromIntent(this.host.getActivity().getIntent());
               var1 = var2;
               if (var2 == null) {
                  var1 = "/";
               }
            }

            String var3 = this.host.getDartEntrypointLibraryUri();
            StringBuilder var7 = new StringBuilder("Executing Dart entrypoint: ");
            var7.append(this.host.getDartEntrypointFunctionName());
            var7.append(", library uri: ");
            var7.append(var3);
            if (var7.toString() == null) {
               var2 = "\"\"";
            } else {
               StringBuilder var9 = new StringBuilder();
               var9.append(var3);
               var9.append(", and sending initial route: ");
               var9.append(var1);
               var2 = var9.toString();
            }

            label29: {
               Log.v("FlutterActivityAndFragmentDelegate", var2);
               this.flutterEngine.getNavigationChannel().setInitialRoute(var1);
               var2 = this.host.getAppBundlePath();
               if (var2 != null) {
                  var1 = var2;
                  if (!var2.isEmpty()) {
                     break label29;
                  }
               }

               var1 = FlutterInjector.instance().flutterLoader().findAppBundlePath();
            }

            DartExecutor.DartEntrypoint var5;
            if (var3 == null) {
               var5 = new DartExecutor.DartEntrypoint(var1, this.host.getDartEntrypointFunctionName());
            } else {
               var5 = new DartExecutor.DartEntrypoint(var1, var3, this.host.getDartEntrypointFunctionName());
            }

            this.flutterEngine.getDartExecutor().executeDartEntrypoint(var5, this.host.getDartEntrypointArgs());
         }
      }
   }

   private void ensureAlive() {
      if (this.host == null) {
         throw new IllegalStateException("Cannot execute method on a destroyed FlutterActivityAndFragmentDelegate.");
      }
   }

   private String maybeGetInitialRouteFromIntent(Intent var1) {
      if (this.host.shouldHandleDeeplinking()) {
         Uri var2 = var1.getData();
         if (var2 != null) {
            return var2.toString();
         }
      }

      return null;
   }

   void cancelBackGesture() {
      this.ensureAlive();
      if (this.flutterEngine != null) {
         Log.v("FlutterActivityAndFragmentDelegate", "Forwarding cancelBackGesture() to FlutterEngine.");
         this.flutterEngine.getBackGestureChannel().cancelBackGesture();
      } else {
         Log.w("FlutterActivityAndFragmentDelegate", "Invoked cancelBackGesture() before FlutterFragment was attached to an Activity.");
      }
   }

   void commitBackGesture() {
      this.ensureAlive();
      if (this.flutterEngine != null) {
         Log.v("FlutterActivityAndFragmentDelegate", "Forwarding commitBackGesture() to FlutterEngine.");
         this.flutterEngine.getBackGestureChannel().commitBackGesture();
      } else {
         Log.w("FlutterActivityAndFragmentDelegate", "Invoked commitBackGesture() before FlutterFragment was attached to an Activity.");
      }
   }

   @Override
   public void detachFromFlutterEngine() {
      if (!this.host.shouldDestroyEngineWithHost()) {
         this.host.detachFromFlutterEngine();
      } else {
         StringBuilder var1 = new StringBuilder("The internal FlutterEngine created by ");
         var1.append(this.host);
         var1.append(
            " has been attached to by another activity. To persist a FlutterEngine beyond the ownership of this activity, explicitly create a FlutterEngine"
         );
         throw new AssertionError(var1.toString());
      }
   }

   public Activity getAppComponent() {
      Activity var1 = this.host.getActivity();
      if (var1 != null) {
         return var1;
      } else {
         throw new AssertionError(
            "FlutterActivityAndFragmentDelegate's getAppComponent should only be queried after onAttach, when the host's activity should always be non-null"
         );
      }
   }

   FlutterEngine getFlutterEngine() {
      return this.flutterEngine;
   }

   boolean isAttached() {
      return this.isAttached;
   }

   boolean isFlutterEngineFromHost() {
      return this.isFlutterEngineFromHost;
   }

   void onActivityResult(int var1, int var2, Intent var3) {
      this.ensureAlive();
      if (this.flutterEngine != null) {
         StringBuilder var4 = new StringBuilder("Forwarding onActivityResult() to FlutterEngine:\nrequestCode: ");
         var4.append(var1);
         var4.append("\nresultCode: ");
         var4.append(var2);
         var4.append("\ndata: ");
         var4.append(var3);
         Log.v("FlutterActivityAndFragmentDelegate", var4.toString());
         this.flutterEngine.getActivityControlSurface().onActivityResult(var1, var2, var3);
      } else {
         Log.w("FlutterActivityAndFragmentDelegate", "onActivityResult() invoked before FlutterFragment was attached to an Activity.");
      }
   }

   void onAttach(Context var1) {
      this.ensureAlive();
      if (this.flutterEngine == null) {
         this.setUpFlutterEngine();
      }

      if (this.host.shouldAttachEngineToActivity()) {
         Log.v("FlutterActivityAndFragmentDelegate", "Attaching FlutterEngine to the Activity that owns this delegate.");
         this.flutterEngine.getActivityControlSurface().attachToActivity(this, this.host.getLifecycle());
      }

      FlutterActivityAndFragmentDelegate.Host var2 = this.host;
      this.platformPlugin = var2.providePlatformPlugin(var2.getActivity(), this.flutterEngine);
      this.host.configureFlutterEngine(this.flutterEngine);
      this.isAttached = true;
   }

   void onBackPressed() {
      this.ensureAlive();
      if (this.flutterEngine != null) {
         Log.v("FlutterActivityAndFragmentDelegate", "Forwarding onBackPressed() to FlutterEngine.");
         this.flutterEngine.getNavigationChannel().popRoute();
      } else {
         Log.w("FlutterActivityAndFragmentDelegate", "Invoked onBackPressed() before FlutterFragment was attached to an Activity.");
      }
   }

   View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3, int var4, boolean var5) {
      Log.v("FlutterActivityAndFragmentDelegate", "Creating FlutterView.");
      this.ensureAlive();
      RenderMode var12 = this.host.getRenderMode();
      RenderMode var8 = RenderMode.surface;
      boolean var7 = true;
      boolean var6 = true;
      if (var12 == var8) {
         Context var9 = this.host.getContext();
         if (this.host.getTransparencyMode() != TransparencyMode.transparent) {
            var6 = false;
         }

         FlutterSurfaceView var10 = new FlutterSurfaceView(var9, var6);
         this.host.onFlutterSurfaceViewCreated(var10);
         this.flutterView = new FlutterView(this.host.getContext(), var10);
      } else {
         FlutterTextureView var11 = new FlutterTextureView(this.host.getContext());
         if (this.host.getTransparencyMode() == TransparencyMode.opaque) {
            var6 = var7;
         } else {
            var6 = false;
         }

         var11.setOpaque(var6);
         this.host.onFlutterTextureViewCreated(var11);
         this.flutterView = new FlutterView(this.host.getContext(), var11);
      }

      this.flutterView.addOnFirstFrameRenderedListener(this.flutterUiDisplayListener);
      if (this.host.attachToEngineAutomatically()) {
         Log.v("FlutterActivityAndFragmentDelegate", "Attaching FlutterEngine to FlutterView.");
         this.flutterView.attachToFlutterEngine(this.flutterEngine);
      }

      this.flutterView.setId(var4);
      if (var5) {
         this.delayFirstAndroidViewDraw(this.flutterView);
      }

      return this.flutterView;
   }

   void onDestroyView() {
      Log.v("FlutterActivityAndFragmentDelegate", "onDestroyView()");
      this.ensureAlive();
      if (this.activePreDrawListener != null) {
         this.flutterView.getViewTreeObserver().removeOnPreDrawListener(this.activePreDrawListener);
         this.activePreDrawListener = null;
      }

      FlutterView var1 = this.flutterView;
      if (var1 != null) {
         var1.detachFromFlutterEngine();
         this.flutterView.removeOnFirstFrameRenderedListener(this.flutterUiDisplayListener);
      }
   }

   void onDetach() {
      if (this.isAttached) {
         Log.v("FlutterActivityAndFragmentDelegate", "onDetach()");
         this.ensureAlive();
         this.host.cleanUpFlutterEngine(this.flutterEngine);
         if (this.host.shouldAttachEngineToActivity()) {
            Log.v("FlutterActivityAndFragmentDelegate", "Detaching FlutterEngine from the Activity that owns this Fragment.");
            if (this.host.getActivity().isChangingConfigurations()) {
               this.flutterEngine.getActivityControlSurface().detachFromActivityForConfigChanges();
            } else {
               this.flutterEngine.getActivityControlSurface().detachFromActivity();
            }
         }

         PlatformPlugin var1 = this.platformPlugin;
         if (var1 != null) {
            var1.destroy();
            this.platformPlugin = null;
         }

         if (this.host.shouldDispatchAppLifecycleState()) {
            FlutterEngine var2 = this.flutterEngine;
            if (var2 != null) {
               var2.getLifecycleChannel().appIsDetached();
            }
         }

         if (this.host.shouldDestroyEngineWithHost()) {
            this.flutterEngine.destroy();
            if (this.host.getCachedEngineId() != null) {
               FlutterEngineCache.getInstance().remove(this.host.getCachedEngineId());
            }

            this.flutterEngine = null;
         }

         this.isAttached = false;
      }
   }

   void onNewIntent(Intent var1) {
      this.ensureAlive();
      if (this.flutterEngine != null) {
         Log.v("FlutterActivityAndFragmentDelegate", "Forwarding onNewIntent() to FlutterEngine and sending pushRouteInformation message.");
         this.flutterEngine.getActivityControlSurface().onNewIntent(var1);
         String var2 = this.maybeGetInitialRouteFromIntent(var1);
         if (var2 != null && !var2.isEmpty()) {
            this.flutterEngine.getNavigationChannel().pushRouteInformation(var2);
         }
      } else {
         Log.w("FlutterActivityAndFragmentDelegate", "onNewIntent() invoked before FlutterFragment was attached to an Activity.");
      }
   }

   void onPause() {
      Log.v("FlutterActivityAndFragmentDelegate", "onPause()");
      this.ensureAlive();
      if (this.host.shouldDispatchAppLifecycleState()) {
         FlutterEngine var1 = this.flutterEngine;
         if (var1 != null) {
            var1.getLifecycleChannel().appIsInactive();
         }
      }
   }

   void onPostResume() {
      Log.v("FlutterActivityAndFragmentDelegate", "onPostResume()");
      this.ensureAlive();
      if (this.flutterEngine != null) {
         this.updateSystemUiOverlays();
         this.flutterEngine.getPlatformViewsController().onResume();
      } else {
         Log.w("FlutterActivityAndFragmentDelegate", "onPostResume() invoked before FlutterFragment was attached to an Activity.");
      }
   }

   void onRequestPermissionsResult(int var1, String[] var2, int[] var3) {
      this.ensureAlive();
      if (this.flutterEngine != null) {
         StringBuilder var4 = new StringBuilder("Forwarding onRequestPermissionsResult() to FlutterEngine:\nrequestCode: ");
         var4.append(var1);
         var4.append("\npermissions: ");
         var4.append(Arrays.toString((Object[])var2));
         var4.append("\ngrantResults: ");
         var4.append(Arrays.toString(var3));
         Log.v("FlutterActivityAndFragmentDelegate", var4.toString());
         this.flutterEngine.getActivityControlSurface().onRequestPermissionsResult(var1, var2, var3);
      } else {
         Log.w("FlutterActivityAndFragmentDelegate", "onRequestPermissionResult() invoked before FlutterFragment was attached to an Activity.");
      }
   }

   void onRestoreInstanceState(Bundle var1) {
      Log.v("FlutterActivityAndFragmentDelegate", "onRestoreInstanceState. Giving framework and plugins an opportunity to restore state.");
      this.ensureAlive();
      byte[] var2;
      if (var1 != null) {
         Bundle var3 = var1.getBundle("plugins");
         var2 = var1.getByteArray("framework");
         var1 = var3;
      } else {
         var1 = null;
         var2 = null;
      }

      if (this.host.shouldRestoreAndSaveState()) {
         this.flutterEngine.getRestorationChannel().setRestorationData(var2);
      }

      if (this.host.shouldAttachEngineToActivity()) {
         this.flutterEngine.getActivityControlSurface().onRestoreInstanceState(var1);
      }
   }

   void onResume() {
      Log.v("FlutterActivityAndFragmentDelegate", "onResume()");
      this.ensureAlive();
      if (this.host.shouldDispatchAppLifecycleState()) {
         FlutterEngine var1 = this.flutterEngine;
         if (var1 != null) {
            var1.getLifecycleChannel().appIsResumed();
         }
      }
   }

   void onSaveInstanceState(Bundle var1) {
      Log.v("FlutterActivityAndFragmentDelegate", "onSaveInstanceState. Giving framework and plugins an opportunity to save state.");
      this.ensureAlive();
      if (this.host.shouldRestoreAndSaveState()) {
         var1.putByteArray("framework", this.flutterEngine.getRestorationChannel().getRestorationData());
      }

      if (this.host.shouldAttachEngineToActivity()) {
         Bundle var2 = new Bundle();
         this.flutterEngine.getActivityControlSurface().onSaveInstanceState(var2);
         var1.putBundle("plugins", var2);
      }
   }

   void onStart() {
      Log.v("FlutterActivityAndFragmentDelegate", "onStart()");
      this.ensureAlive();
      this.doInitialFlutterViewRun();
      Integer var1 = this.previousVisibility;
      if (var1 != null) {
         this.flutterView.setVisibility(var1);
      }
   }

   void onStop() {
      Log.v("FlutterActivityAndFragmentDelegate", "onStop()");
      this.ensureAlive();
      if (this.host.shouldDispatchAppLifecycleState()) {
         FlutterEngine var1 = this.flutterEngine;
         if (var1 != null) {
            var1.getLifecycleChannel().appIsPaused();
         }
      }

      this.previousVisibility = this.flutterView.getVisibility();
      this.flutterView.setVisibility(8);
      FlutterEngine var2 = this.flutterEngine;
      if (var2 != null) {
         var2.getRenderer().onTrimMemory(40);
      }
   }

   void onTrimMemory(int var1) {
      this.ensureAlive();
      FlutterEngine var2 = this.flutterEngine;
      if (var2 != null) {
         if (this.isFirstFrameRendered && var1 >= 10) {
            var2.getDartExecutor().notifyLowMemoryWarning();
            this.flutterEngine.getSystemChannel().sendMemoryPressureWarning();
         }

         this.flutterEngine.getRenderer().onTrimMemory(var1);
         this.flutterEngine.getPlatformViewsController().onTrimMemory(var1);
      }
   }

   void onUserLeaveHint() {
      this.ensureAlive();
      if (this.flutterEngine != null) {
         Log.v("FlutterActivityAndFragmentDelegate", "Forwarding onUserLeaveHint() to FlutterEngine.");
         this.flutterEngine.getActivityControlSurface().onUserLeaveHint();
      } else {
         Log.w("FlutterActivityAndFragmentDelegate", "onUserLeaveHint() invoked before FlutterFragment was attached to an Activity.");
      }
   }

   void onWindowFocusChanged(boolean var1) {
      this.ensureAlive();
      String var2;
      if (var1) {
         var2 = "true";
      } else {
         var2 = "false";
      }

      Log.v("FlutterActivityAndFragmentDelegate", "Received onWindowFocusChanged: ".concat(var2));
      if (this.host.shouldDispatchAppLifecycleState()) {
         FlutterEngine var3 = this.flutterEngine;
         if (var3 != null) {
            if (var1) {
               var3.getLifecycleChannel().aWindowIsFocused();
            } else {
               var3.getLifecycleChannel().noWindowsAreFocused();
            }
         }
      }
   }

   void release() {
      this.host = null;
      this.flutterEngine = null;
      this.flutterView = null;
      this.platformPlugin = null;
   }

   void setUpFlutterEngine() {
      Log.v("FlutterActivityAndFragmentDelegate", "Setting up FlutterEngine.");
      String var1 = this.host.getCachedEngineId();
      if (var1 != null) {
         FlutterEngine var9 = FlutterEngineCache.getInstance().get(var1);
         this.flutterEngine = var9;
         this.isFlutterEngineFromHost = true;
         if (var9 == null) {
            StringBuilder var10 = new StringBuilder("The requested cached FlutterEngine did not exist in the FlutterEngineCache: '");
            var10.append(var1);
            var10.append("'");
            throw new IllegalStateException(var10.toString());
         }
      } else {
         FlutterActivityAndFragmentDelegate.Host var3 = this.host;
         FlutterEngine var4 = var3.provideFlutterEngine(var3.getContext());
         this.flutterEngine = var4;
         if (var4 != null) {
            this.isFlutterEngineFromHost = true;
         } else {
            var1 = this.host.getCachedEngineGroupId();
            if (var1 != null) {
               FlutterEngineGroup var7 = FlutterEngineGroupCache.getInstance().get(var1);
               if (var7 != null) {
                  this.flutterEngine = var7.createAndRunEngine(this.addEntrypointOptions(new FlutterEngineGroup.Options(this.host.getContext())));
                  this.isFlutterEngineFromHost = false;
               } else {
                  StringBuilder var8 = new StringBuilder("The requested cached FlutterEngineGroup did not exist in the FlutterEngineGroupCache: '");
                  var8.append(var1);
                  var8.append("'");
                  throw new IllegalStateException(var8.toString());
               }
            } else {
               Log.v("FlutterActivityAndFragmentDelegate", "No preferred FlutterEngine was provided. Creating a new FlutterEngine for this FlutterFragment.");
               FlutterEngineGroup var2 = this.engineGroup;
               FlutterEngineGroup var6 = var2;
               if (var2 == null) {
                  var6 = new FlutterEngineGroup(this.host.getContext(), this.host.getFlutterShellArgs().toArray());
               }

               this.flutterEngine = var6.createAndRunEngine(
                  this.addEntrypointOptions(
                     new FlutterEngineGroup.Options(this.host.getContext())
                        .setAutomaticallyRegisterPlugins(false)
                        .setWaitForRestorationData(this.host.shouldRestoreAndSaveState())
                  )
               );
               this.isFlutterEngineFromHost = false;
            }
         }
      }
   }

   void startBackGesture(BackEvent var1) {
      this.ensureAlive();
      if (this.flutterEngine != null) {
         Log.v("FlutterActivityAndFragmentDelegate", "Forwarding startBackGesture() to FlutterEngine.");
         this.flutterEngine.getBackGestureChannel().startBackGesture(var1);
      } else {
         Log.w("FlutterActivityAndFragmentDelegate", "Invoked startBackGesture() before FlutterFragment was attached to an Activity.");
      }
   }

   void updateBackGestureProgress(BackEvent var1) {
      this.ensureAlive();
      if (this.flutterEngine != null) {
         Log.v("FlutterActivityAndFragmentDelegate", "Forwarding updateBackGestureProgress() to FlutterEngine.");
         this.flutterEngine.getBackGestureChannel().updateBackGestureProgress(var1);
      } else {
         Log.w("FlutterActivityAndFragmentDelegate", "Invoked updateBackGestureProgress() before FlutterFragment was attached to an Activity.");
      }
   }

   void updateSystemUiOverlays() {
      PlatformPlugin var1 = this.platformPlugin;
      if (var1 != null) {
         var1.updateSystemUiOverlays();
      }
   }

   public interface DelegateFactory {
      FlutterActivityAndFragmentDelegate createDelegate(FlutterActivityAndFragmentDelegate.Host var1);
   }

   interface Host extends FlutterEngineProvider, FlutterEngineConfigurator, PlatformPlugin.PlatformPluginDelegate {
      boolean attachToEngineAutomatically();

      @Override
      void cleanUpFlutterEngine(FlutterEngine var1);

      @Override
      void configureFlutterEngine(FlutterEngine var1);

      void detachFromFlutterEngine();

      Activity getActivity();

      String getAppBundlePath();

      String getCachedEngineGroupId();

      String getCachedEngineId();

      Context getContext();

      List<String> getDartEntrypointArgs();

      String getDartEntrypointFunctionName();

      String getDartEntrypointLibraryUri();

      ExclusiveAppComponent<Activity> getExclusiveAppComponent();

      FlutterShellArgs getFlutterShellArgs();

      String getInitialRoute();

      Lifecycle getLifecycle();

      RenderMode getRenderMode();

      TransparencyMode getTransparencyMode();

      void onFlutterSurfaceViewCreated(FlutterSurfaceView var1);

      void onFlutterTextureViewCreated(FlutterTextureView var1);

      void onFlutterUiDisplayed();

      void onFlutterUiNoLongerDisplayed();

      @Override
      FlutterEngine provideFlutterEngine(Context var1);

      PlatformPlugin providePlatformPlugin(Activity var1, FlutterEngine var2);

      boolean shouldAttachEngineToActivity();

      boolean shouldDestroyEngineWithHost();

      boolean shouldDispatchAppLifecycleState();

      boolean shouldHandleDeeplinking();

      boolean shouldRestoreAndSaveState();

      void updateSystemUiOverlays();
   }
}
