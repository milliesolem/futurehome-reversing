package io.flutter.embedding.android;

import android.app.Activity;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnWindowFocusChangeListener;
import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import io.flutter.Log;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterShellArgs;
import io.flutter.embedding.engine.renderer.FlutterUiDisplayListener;
import io.flutter.plugin.platform.PlatformPlugin;
import java.util.ArrayList;
import java.util.List;

public class FlutterFragment
   extends Fragment
   implements FlutterActivityAndFragmentDelegate.Host,
   ComponentCallbacks2,
   FlutterActivityAndFragmentDelegate.DelegateFactory {
   protected static final String ARG_APP_BUNDLE_PATH = "app_bundle_path";
   protected static final String ARG_CACHED_ENGINE_GROUP_ID = "cached_engine_group_id";
   protected static final String ARG_CACHED_ENGINE_ID = "cached_engine_id";
   protected static final String ARG_DART_ENTRYPOINT = "dart_entrypoint";
   protected static final String ARG_DART_ENTRYPOINT_ARGS = "dart_entrypoint_args";
   protected static final String ARG_DART_ENTRYPOINT_URI = "dart_entrypoint_uri";
   protected static final String ARG_DESTROY_ENGINE_WITH_FRAGMENT = "destroy_engine_with_fragment";
   protected static final String ARG_ENABLE_STATE_RESTORATION = "enable_state_restoration";
   protected static final String ARG_FLUTTERVIEW_RENDER_MODE = "flutterview_render_mode";
   protected static final String ARG_FLUTTERVIEW_TRANSPARENCY_MODE = "flutterview_transparency_mode";
   protected static final String ARG_FLUTTER_INITIALIZATION_ARGS = "initialization_args";
   protected static final String ARG_HANDLE_DEEPLINKING = "handle_deeplinking";
   protected static final String ARG_INITIAL_ROUTE = "initial_route";
   protected static final String ARG_SHOULD_ATTACH_ENGINE_TO_ACTIVITY = "should_attach_engine_to_activity";
   protected static final String ARG_SHOULD_AUTOMATICALLY_HANDLE_ON_BACK_PRESSED = "should_automatically_handle_on_back_pressed";
   protected static final String ARG_SHOULD_DELAY_FIRST_ANDROID_VIEW_DRAW = "should_delay_first_android_view_draw";
   public static final int FLUTTER_VIEW_ID = View.generateViewId();
   private static final String TAG = "FlutterFragment";
   FlutterActivityAndFragmentDelegate delegate;
   private FlutterActivityAndFragmentDelegate.DelegateFactory delegateFactory;
   private final OnBackPressedCallback onBackPressedCallback;
   private final OnWindowFocusChangeListener onWindowFocusChangeListener = new OnWindowFocusChangeListener(this) {
      final FlutterFragment this$0;

      {
         this.this$0 = var1;
      }

      public void onWindowFocusChanged(boolean var1) {
         if (this.this$0.stillAttachedForEvent("onWindowFocusChanged")) {
            this.this$0.delegate.onWindowFocusChanged(var1);
         }
      }
   };

   public FlutterFragment() {
      this.delegateFactory = this;
      this.onBackPressedCallback = new OnBackPressedCallback(this, true) {
         final FlutterFragment this$0;

         {
            this.this$0 = var1;
         }

         public void handleOnBackPressed() {
            this.this$0.onBackPressed();
         }
      };
      this.setArguments(new Bundle());
   }

   public static FlutterFragment createDefault() {
      return new FlutterFragment.NewEngineFragmentBuilder().build();
   }

   private boolean stillAttachedForEvent(String var1) {
      FlutterActivityAndFragmentDelegate var2 = this.delegate;
      if (var2 == null) {
         StringBuilder var4 = new StringBuilder("FlutterFragment ");
         var4.append(this.hashCode());
         var4.append(" ");
         var4.append(var1);
         var4.append(" called after release.");
         Log.w("FlutterFragment", var4.toString());
         return false;
      } else if (!var2.isAttached()) {
         StringBuilder var3 = new StringBuilder("FlutterFragment ");
         var3.append(this.hashCode());
         var3.append(" ");
         var3.append(var1);
         var3.append(" called after detach.");
         Log.w("FlutterFragment", var3.toString());
         return false;
      } else {
         return true;
      }
   }

   public static FlutterFragment.CachedEngineFragmentBuilder withCachedEngine(String var0) {
      return new FlutterFragment.CachedEngineFragmentBuilder(var0);
   }

   public static FlutterFragment.NewEngineFragmentBuilder withNewEngine() {
      return new FlutterFragment.NewEngineFragmentBuilder();
   }

   public static FlutterFragment.NewEngineInGroupFragmentBuilder withNewEngineInGroup(String var0) {
      return new FlutterFragment.NewEngineInGroupFragmentBuilder(var0);
   }

   @Override
   public boolean attachToEngineAutomatically() {
      return true;
   }

   @Override
   public void cleanUpFlutterEngine(FlutterEngine var1) {
      FragmentActivity var2 = this.getActivity();
      if (var2 instanceof FlutterEngineConfigurator) {
         ((FlutterEngineConfigurator)var2).cleanUpFlutterEngine(var1);
      }
   }

   @Override
   public void configureFlutterEngine(FlutterEngine var1) {
      FragmentActivity var2 = this.getActivity();
      if (var2 instanceof FlutterEngineConfigurator) {
         ((FlutterEngineConfigurator)var2).configureFlutterEngine(var1);
      }
   }

   @Override
   public FlutterActivityAndFragmentDelegate createDelegate(FlutterActivityAndFragmentDelegate.Host var1) {
      return new FlutterActivityAndFragmentDelegate(var1);
   }

   @Override
   public void detachFromFlutterEngine() {
      StringBuilder var1 = new StringBuilder("FlutterFragment ");
      var1.append(this);
      var1.append(" connection to the engine ");
      var1.append(this.getFlutterEngine());
      var1.append(" evicted by another attaching activity");
      Log.w("FlutterFragment", var1.toString());
      FlutterActivityAndFragmentDelegate var2 = this.delegate;
      if (var2 != null) {
         var2.onDestroyView();
         this.delegate.onDetach();
      }
   }

   @Override
   public String getAppBundlePath() {
      return this.getArguments().getString("app_bundle_path");
   }

   @Override
   public String getCachedEngineGroupId() {
      return this.getArguments().getString("cached_engine_group_id", null);
   }

   @Override
   public String getCachedEngineId() {
      return this.getArguments().getString("cached_engine_id", null);
   }

   @Override
   public List<String> getDartEntrypointArgs() {
      return this.getArguments().getStringArrayList("dart_entrypoint_args");
   }

   @Override
   public String getDartEntrypointFunctionName() {
      return this.getArguments().getString("dart_entrypoint", "main");
   }

   @Override
   public String getDartEntrypointLibraryUri() {
      return this.getArguments().getString("dart_entrypoint_uri");
   }

   @Override
   public ExclusiveAppComponent<Activity> getExclusiveAppComponent() {
      return this.delegate;
   }

   public FlutterEngine getFlutterEngine() {
      return this.delegate.getFlutterEngine();
   }

   @Override
   public FlutterShellArgs getFlutterShellArgs() {
      String[] var1 = this.getArguments().getStringArray("initialization_args");
      if (var1 == null) {
         var1 = new String[0];
      }

      return new FlutterShellArgs(var1);
   }

   @Override
   public String getInitialRoute() {
      return this.getArguments().getString("initial_route");
   }

   @Override
   public RenderMode getRenderMode() {
      return RenderMode.valueOf(this.getArguments().getString("flutterview_render_mode", RenderMode.surface.name()));
   }

   @Override
   public TransparencyMode getTransparencyMode() {
      return TransparencyMode.valueOf(this.getArguments().getString("flutterview_transparency_mode", TransparencyMode.transparent.name()));
   }

   boolean isFlutterEngineInjected() {
      return this.delegate.isFlutterEngineFromHost();
   }

   public void onActivityResult(int var1, int var2, Intent var3) {
      if (this.stillAttachedForEvent("onActivityResult")) {
         this.delegate.onActivityResult(var1, var2, var3);
      }
   }

   public void onAttach(Context var1) {
      super.onAttach(var1);
      FlutterActivityAndFragmentDelegate var2 = this.delegateFactory.createDelegate(this);
      this.delegate = var2;
      var2.onAttach(var1);
      if (this.getArguments().getBoolean("should_automatically_handle_on_back_pressed", false)) {
         this.requireActivity().getOnBackPressedDispatcher().addCallback(this, this.onBackPressedCallback);
         this.onBackPressedCallback.setEnabled(false);
      }

      var1.registerComponentCallbacks(this);
   }

   public void onBackPressed() {
      if (this.stillAttachedForEvent("onBackPressed")) {
         this.delegate.onBackPressed();
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.delegate.onRestoreInstanceState(var1);
   }

   public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
      return this.delegate.onCreateView(var1, var2, var3, FLUTTER_VIEW_ID, this.shouldDelayFirstAndroidViewDraw());
   }

   public void onDestroyView() {
      super.onDestroyView();
      this.requireView().getViewTreeObserver().removeOnWindowFocusChangeListener(this.onWindowFocusChangeListener);
      if (this.stillAttachedForEvent("onDestroyView")) {
         this.delegate.onDestroyView();
      }
   }

   public void onDetach() {
      this.getContext().unregisterComponentCallbacks(this);
      super.onDetach();
      FlutterActivityAndFragmentDelegate var1 = this.delegate;
      if (var1 != null) {
         var1.onDetach();
         this.delegate.release();
         this.delegate = null;
      } else {
         StringBuilder var2 = new StringBuilder("FlutterFragment ");
         var2.append(this);
         var2.append(" onDetach called after release.");
         Log.v("FlutterFragment", var2.toString());
      }
   }

   @Override
   public void onFlutterSurfaceViewCreated(FlutterSurfaceView var1) {
   }

   @Override
   public void onFlutterTextureViewCreated(FlutterTextureView var1) {
   }

   @Override
   public void onFlutterUiDisplayed() {
      FragmentActivity var1 = this.getActivity();
      if (var1 instanceof FlutterUiDisplayListener) {
         ((FlutterUiDisplayListener)var1).onFlutterUiDisplayed();
      }
   }

   @Override
   public void onFlutterUiNoLongerDisplayed() {
      FragmentActivity var1 = this.getActivity();
      if (var1 instanceof FlutterUiDisplayListener) {
         ((FlutterUiDisplayListener)var1).onFlutterUiNoLongerDisplayed();
      }
   }

   public void onNewIntent(Intent var1) {
      if (this.stillAttachedForEvent("onNewIntent")) {
         this.delegate.onNewIntent(var1);
      }
   }

   public void onPause() {
      super.onPause();
      if (this.stillAttachedForEvent("onPause")) {
         this.delegate.onPause();
      }
   }

   public void onPostResume() {
      if (this.stillAttachedForEvent("onPostResume")) {
         this.delegate.onPostResume();
      }
   }

   public void onRequestPermissionsResult(int var1, String[] var2, int[] var3) {
      if (this.stillAttachedForEvent("onRequestPermissionsResult")) {
         this.delegate.onRequestPermissionsResult(var1, var2, var3);
      }
   }

   public void onResume() {
      super.onResume();
      if (this.stillAttachedForEvent("onResume")) {
         this.delegate.onResume();
      }
   }

   public void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
      if (this.stillAttachedForEvent("onSaveInstanceState")) {
         this.delegate.onSaveInstanceState(var1);
      }
   }

   public void onStart() {
      super.onStart();
      if (this.stillAttachedForEvent("onStart")) {
         this.delegate.onStart();
      }
   }

   public void onStop() {
      super.onStop();
      if (this.stillAttachedForEvent("onStop")) {
         this.delegate.onStop();
      }
   }

   public void onTrimMemory(int var1) {
      if (this.stillAttachedForEvent("onTrimMemory")) {
         this.delegate.onTrimMemory(var1);
      }
   }

   public void onUserLeaveHint() {
      if (this.stillAttachedForEvent("onUserLeaveHint")) {
         this.delegate.onUserLeaveHint();
      }
   }

   public void onViewCreated(View var1, Bundle var2) {
      super.onViewCreated(var1, var2);
      var1.getViewTreeObserver().addOnWindowFocusChangeListener(this.onWindowFocusChangeListener);
   }

   @Override
   public boolean popSystemNavigator() {
      if (this.getArguments().getBoolean("should_automatically_handle_on_back_pressed", false)) {
         FragmentActivity var2 = this.getActivity();
         if (var2 != null) {
            boolean var1 = this.onBackPressedCallback.isEnabled();
            if (var1) {
               this.onBackPressedCallback.setEnabled(false);
            }

            var2.getOnBackPressedDispatcher().onBackPressed();
            if (var1) {
               this.onBackPressedCallback.setEnabled(true);
            }

            return true;
         }
      }

      return false;
   }

   @Override
   public FlutterEngine provideFlutterEngine(Context var1) {
      FragmentActivity var2 = this.getActivity();
      FlutterEngine var3;
      if (var2 instanceof FlutterEngineProvider) {
         Log.v("FlutterFragment", "Deferring to attached Activity to provide a FlutterEngine.");
         var3 = ((FlutterEngineProvider)var2).provideFlutterEngine(this.getContext());
      } else {
         var3 = null;
      }

      return var3;
   }

   @Override
   public PlatformPlugin providePlatformPlugin(Activity var1, FlutterEngine var2) {
      return var1 != null ? new PlatformPlugin(this.getActivity(), var2.getPlatformChannel(), this) : null;
   }

   void setDelegateFactory(FlutterActivityAndFragmentDelegate.DelegateFactory var1) {
      this.delegateFactory = var1;
      this.delegate = var1.createDelegate(this);
   }

   @Override
   public void setFrameworkHandlesBack(boolean var1) {
      if (this.getArguments().getBoolean("should_automatically_handle_on_back_pressed", false)) {
         this.onBackPressedCallback.setEnabled(var1);
      }
   }

   @Override
   public boolean shouldAttachEngineToActivity() {
      return this.getArguments().getBoolean("should_attach_engine_to_activity");
   }

   boolean shouldDelayFirstAndroidViewDraw() {
      return this.getArguments().getBoolean("should_delay_first_android_view_draw");
   }

   @Override
   public boolean shouldDestroyEngineWithHost() {
      boolean var2 = this.getArguments().getBoolean("destroy_engine_with_fragment", false);
      boolean var1 = var2;
      if (this.getCachedEngineId() == null) {
         if (this.delegate.isFlutterEngineFromHost()) {
            var1 = var2;
         } else {
            var1 = this.getArguments().getBoolean("destroy_engine_with_fragment", true);
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
      return this.getArguments().getBoolean("handle_deeplinking");
   }

   @Override
   public boolean shouldRestoreAndSaveState() {
      return this.getArguments().containsKey("enable_state_restoration")
         ? this.getArguments().getBoolean("enable_state_restoration")
         : this.getCachedEngineId() == null;
   }

   @Override
   public void updateSystemUiOverlays() {
      FlutterActivityAndFragmentDelegate var1 = this.delegate;
      if (var1 != null) {
         var1.updateSystemUiOverlays();
      }
   }

   @interface ActivityCallThrough {
   }

   public static class CachedEngineFragmentBuilder {
      private boolean destroyEngineWithFragment = false;
      private final String engineId;
      private final Class<? extends FlutterFragment> fragmentClass;
      private boolean handleDeeplinking = false;
      private RenderMode renderMode = RenderMode.surface;
      private boolean shouldAttachEngineToActivity;
      private boolean shouldAutomaticallyHandleOnBackPressed;
      private boolean shouldDelayFirstAndroidViewDraw;
      private TransparencyMode transparencyMode = TransparencyMode.transparent;

      public CachedEngineFragmentBuilder(Class<? extends FlutterFragment> var1, String var2) {
         this.shouldAttachEngineToActivity = true;
         this.shouldAutomaticallyHandleOnBackPressed = false;
         this.shouldDelayFirstAndroidViewDraw = false;
         this.fragmentClass = var1;
         this.engineId = var2;
      }

      private CachedEngineFragmentBuilder(String var1) {
         this(FlutterFragment.class, var1);
      }

      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      public <T extends FlutterFragment> T build() {
         FlutterFragment var1;
         try {
            var1 = this.fragmentClass.getDeclaredConstructor(null).newInstance(null);
         } catch (Exception var5) {
            StringBuilder var2 = new StringBuilder("Could not instantiate FlutterFragment subclass (");
            var2.append(this.fragmentClass.getName());
            var2.append(")");
            throw new RuntimeException(var2.toString(), var5);
         }

         if (var1 != null) {
            try {
               var1.setArguments(this.createArgs());
               return (T)var1;
            } catch (Exception var3) {
               StringBuilder var9 = new StringBuilder("Could not instantiate FlutterFragment subclass (");
               var9.append(this.fragmentClass.getName());
               var9.append(")");
               throw new RuntimeException(var9.toString(), var3);
            }
         } else {
            try {
               StringBuilder var6 = new StringBuilder("The FlutterFragment subclass sent in the constructor (");
               var6.append(this.fragmentClass.getCanonicalName());
               var6.append(") does not match the expected return type.");
               RuntimeException var8 = new RuntimeException(var6.toString());
               throw var8;
            } catch (Exception var4) {
               StringBuilder var7 = new StringBuilder("Could not instantiate FlutterFragment subclass (");
               var7.append(this.fragmentClass.getName());
               var7.append(")");
               throw new RuntimeException(var7.toString(), var4);
            }
         }
      }

      protected Bundle createArgs() {
         Bundle var2 = new Bundle();
         var2.putString("cached_engine_id", this.engineId);
         var2.putBoolean("destroy_engine_with_fragment", this.destroyEngineWithFragment);
         var2.putBoolean("handle_deeplinking", this.handleDeeplinking);
         RenderMode var1 = this.renderMode;
         if (var1 == null) {
            var1 = RenderMode.surface;
         }

         var2.putString("flutterview_render_mode", var1.name());
         TransparencyMode var3 = this.transparencyMode;
         if (var3 == null) {
            var3 = TransparencyMode.transparent;
         }

         var2.putString("flutterview_transparency_mode", var3.name());
         var2.putBoolean("should_attach_engine_to_activity", this.shouldAttachEngineToActivity);
         var2.putBoolean("should_automatically_handle_on_back_pressed", this.shouldAutomaticallyHandleOnBackPressed);
         var2.putBoolean("should_delay_first_android_view_draw", this.shouldDelayFirstAndroidViewDraw);
         return var2;
      }

      public FlutterFragment.CachedEngineFragmentBuilder destroyEngineWithFragment(boolean var1) {
         this.destroyEngineWithFragment = var1;
         return this;
      }

      public FlutterFragment.CachedEngineFragmentBuilder handleDeeplinking(Boolean var1) {
         this.handleDeeplinking = var1;
         return this;
      }

      public FlutterFragment.CachedEngineFragmentBuilder renderMode(RenderMode var1) {
         this.renderMode = var1;
         return this;
      }

      public FlutterFragment.CachedEngineFragmentBuilder shouldAttachEngineToActivity(boolean var1) {
         this.shouldAttachEngineToActivity = var1;
         return this;
      }

      public FlutterFragment.CachedEngineFragmentBuilder shouldAutomaticallyHandleOnBackPressed(boolean var1) {
         this.shouldAutomaticallyHandleOnBackPressed = var1;
         return this;
      }

      public FlutterFragment.CachedEngineFragmentBuilder shouldDelayFirstAndroidViewDraw(boolean var1) {
         this.shouldDelayFirstAndroidViewDraw = var1;
         return this;
      }

      public FlutterFragment.CachedEngineFragmentBuilder transparencyMode(TransparencyMode var1) {
         this.transparencyMode = var1;
         return this;
      }
   }

   public static class NewEngineFragmentBuilder {
      private String appBundlePath;
      private String dartEntrypoint = "main";
      private List<String> dartEntrypointArgs;
      private String dartLibraryUri = null;
      private final Class<? extends FlutterFragment> fragmentClass;
      private boolean handleDeeplinking;
      private String initialRoute = "/";
      private RenderMode renderMode;
      private FlutterShellArgs shellArgs;
      private boolean shouldAttachEngineToActivity;
      private boolean shouldAutomaticallyHandleOnBackPressed;
      private boolean shouldDelayFirstAndroidViewDraw;
      private TransparencyMode transparencyMode;

      public NewEngineFragmentBuilder() {
         this.handleDeeplinking = false;
         this.appBundlePath = null;
         this.shellArgs = null;
         this.renderMode = RenderMode.surface;
         this.transparencyMode = TransparencyMode.transparent;
         this.shouldAttachEngineToActivity = true;
         this.shouldAutomaticallyHandleOnBackPressed = false;
         this.shouldDelayFirstAndroidViewDraw = false;
         this.fragmentClass = FlutterFragment.class;
      }

      public NewEngineFragmentBuilder(Class<? extends FlutterFragment> var1) {
         this.handleDeeplinking = false;
         this.appBundlePath = null;
         this.shellArgs = null;
         this.renderMode = RenderMode.surface;
         this.transparencyMode = TransparencyMode.transparent;
         this.shouldAttachEngineToActivity = true;
         this.shouldAutomaticallyHandleOnBackPressed = false;
         this.shouldDelayFirstAndroidViewDraw = false;
         this.fragmentClass = var1;
      }

      public FlutterFragment.NewEngineFragmentBuilder appBundlePath(String var1) {
         this.appBundlePath = var1;
         return this;
      }

      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      public <T extends FlutterFragment> T build() {
         FlutterFragment var6;
         try {
            var6 = this.fragmentClass.getDeclaredConstructor(null).newInstance(null);
         } catch (Exception var5) {
            StringBuilder var1 = new StringBuilder("Could not instantiate FlutterFragment subclass (");
            var1.append(this.fragmentClass.getName());
            var1.append(")");
            throw new RuntimeException(var1.toString(), var5);
         }

         if (var6 != null) {
            try {
               var6.setArguments(this.createArgs());
               return (T)var6;
            } catch (Exception var3) {
               StringBuilder var9 = new StringBuilder("Could not instantiate FlutterFragment subclass (");
               var9.append(this.fragmentClass.getName());
               var9.append(")");
               throw new RuntimeException(var9.toString(), var3);
            }
         } else {
            try {
               StringBuilder var2 = new StringBuilder("The FlutterFragment subclass sent in the constructor (");
               var2.append(this.fragmentClass.getCanonicalName());
               var2.append(") does not match the expected return type.");
               RuntimeException var8 = new RuntimeException(var2.toString());
               throw var8;
            } catch (Exception var4) {
               StringBuilder var7 = new StringBuilder("Could not instantiate FlutterFragment subclass (");
               var7.append(this.fragmentClass.getName());
               var7.append(")");
               throw new RuntimeException(var7.toString(), var4);
            }
         }
      }

      protected Bundle createArgs() {
         Bundle var2 = new Bundle();
         var2.putString("initial_route", this.initialRoute);
         var2.putBoolean("handle_deeplinking", this.handleDeeplinking);
         var2.putString("app_bundle_path", this.appBundlePath);
         var2.putString("dart_entrypoint", this.dartEntrypoint);
         var2.putString("dart_entrypoint_uri", this.dartLibraryUri);
         ArrayList var1;
         if (this.dartEntrypointArgs != null) {
            var1 = new ArrayList<>(this.dartEntrypointArgs);
         } else {
            var1 = null;
         }

         var2.putStringArrayList("dart_entrypoint_args", var1);
         FlutterShellArgs var3 = this.shellArgs;
         if (var3 != null) {
            var2.putStringArray("initialization_args", var3.toArray());
         }

         RenderMode var4 = this.renderMode;
         if (var4 == null) {
            var4 = RenderMode.surface;
         }

         var2.putString("flutterview_render_mode", var4.name());
         TransparencyMode var5 = this.transparencyMode;
         if (var5 == null) {
            var5 = TransparencyMode.transparent;
         }

         var2.putString("flutterview_transparency_mode", var5.name());
         var2.putBoolean("should_attach_engine_to_activity", this.shouldAttachEngineToActivity);
         var2.putBoolean("destroy_engine_with_fragment", true);
         var2.putBoolean("should_automatically_handle_on_back_pressed", this.shouldAutomaticallyHandleOnBackPressed);
         var2.putBoolean("should_delay_first_android_view_draw", this.shouldDelayFirstAndroidViewDraw);
         return var2;
      }

      public FlutterFragment.NewEngineFragmentBuilder dartEntrypoint(String var1) {
         this.dartEntrypoint = var1;
         return this;
      }

      public FlutterFragment.NewEngineFragmentBuilder dartEntrypointArgs(List<String> var1) {
         this.dartEntrypointArgs = var1;
         return this;
      }

      public FlutterFragment.NewEngineFragmentBuilder dartLibraryUri(String var1) {
         this.dartLibraryUri = var1;
         return this;
      }

      public FlutterFragment.NewEngineFragmentBuilder flutterShellArgs(FlutterShellArgs var1) {
         this.shellArgs = var1;
         return this;
      }

      public FlutterFragment.NewEngineFragmentBuilder handleDeeplinking(Boolean var1) {
         this.handleDeeplinking = var1;
         return this;
      }

      public FlutterFragment.NewEngineFragmentBuilder initialRoute(String var1) {
         this.initialRoute = var1;
         return this;
      }

      public FlutterFragment.NewEngineFragmentBuilder renderMode(RenderMode var1) {
         this.renderMode = var1;
         return this;
      }

      public FlutterFragment.NewEngineFragmentBuilder shouldAttachEngineToActivity(boolean var1) {
         this.shouldAttachEngineToActivity = var1;
         return this;
      }

      public FlutterFragment.NewEngineFragmentBuilder shouldAutomaticallyHandleOnBackPressed(boolean var1) {
         this.shouldAutomaticallyHandleOnBackPressed = var1;
         return this;
      }

      public FlutterFragment.NewEngineFragmentBuilder shouldDelayFirstAndroidViewDraw(boolean var1) {
         this.shouldDelayFirstAndroidViewDraw = var1;
         return this;
      }

      public FlutterFragment.NewEngineFragmentBuilder transparencyMode(TransparencyMode var1) {
         this.transparencyMode = var1;
         return this;
      }
   }

   public static class NewEngineInGroupFragmentBuilder {
      private final String cachedEngineGroupId;
      private String dartEntrypoint = "main";
      private final Class<? extends FlutterFragment> fragmentClass;
      private boolean handleDeeplinking;
      private String initialRoute = "/";
      private RenderMode renderMode;
      private boolean shouldAttachEngineToActivity;
      private boolean shouldAutomaticallyHandleOnBackPressed;
      private boolean shouldDelayFirstAndroidViewDraw;
      private TransparencyMode transparencyMode;

      public NewEngineInGroupFragmentBuilder(Class<? extends FlutterFragment> var1, String var2) {
         this.handleDeeplinking = false;
         this.renderMode = RenderMode.surface;
         this.transparencyMode = TransparencyMode.transparent;
         this.shouldAttachEngineToActivity = true;
         this.shouldAutomaticallyHandleOnBackPressed = false;
         this.shouldDelayFirstAndroidViewDraw = false;
         this.fragmentClass = var1;
         this.cachedEngineGroupId = var2;
      }

      public NewEngineInGroupFragmentBuilder(String var1) {
         this(FlutterFragment.class, var1);
      }

      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      public <T extends FlutterFragment> T build() {
         FlutterFragment var6;
         try {
            var6 = this.fragmentClass.getDeclaredConstructor(null).newInstance(null);
         } catch (Exception var5) {
            StringBuilder var1 = new StringBuilder("Could not instantiate FlutterFragment subclass (");
            var1.append(this.fragmentClass.getName());
            var1.append(")");
            throw new RuntimeException(var1.toString(), var5);
         }

         if (var6 != null) {
            try {
               var6.setArguments(this.createArgs());
               return (T)var6;
            } catch (Exception var3) {
               StringBuilder var9 = new StringBuilder("Could not instantiate FlutterFragment subclass (");
               var9.append(this.fragmentClass.getName());
               var9.append(")");
               throw new RuntimeException(var9.toString(), var3);
            }
         } else {
            try {
               StringBuilder var8 = new StringBuilder("The FlutterFragment subclass sent in the constructor (");
               var8.append(this.fragmentClass.getCanonicalName());
               var8.append(") does not match the expected return type.");
               RuntimeException var2 = new RuntimeException(var8.toString());
               throw var2;
            } catch (Exception var4) {
               StringBuilder var7 = new StringBuilder("Could not instantiate FlutterFragment subclass (");
               var7.append(this.fragmentClass.getName());
               var7.append(")");
               throw new RuntimeException(var7.toString(), var4);
            }
         }
      }

      protected Bundle createArgs() {
         Bundle var2 = new Bundle();
         var2.putString("cached_engine_group_id", this.cachedEngineGroupId);
         var2.putString("dart_entrypoint", this.dartEntrypoint);
         var2.putString("initial_route", this.initialRoute);
         var2.putBoolean("handle_deeplinking", this.handleDeeplinking);
         RenderMode var1 = this.renderMode;
         if (var1 == null) {
            var1 = RenderMode.surface;
         }

         var2.putString("flutterview_render_mode", var1.name());
         TransparencyMode var3 = this.transparencyMode;
         if (var3 == null) {
            var3 = TransparencyMode.transparent;
         }

         var2.putString("flutterview_transparency_mode", var3.name());
         var2.putBoolean("should_attach_engine_to_activity", this.shouldAttachEngineToActivity);
         var2.putBoolean("destroy_engine_with_fragment", true);
         var2.putBoolean("should_automatically_handle_on_back_pressed", this.shouldAutomaticallyHandleOnBackPressed);
         var2.putBoolean("should_delay_first_android_view_draw", this.shouldDelayFirstAndroidViewDraw);
         return var2;
      }

      public FlutterFragment.NewEngineInGroupFragmentBuilder dartEntrypoint(String var1) {
         this.dartEntrypoint = var1;
         return this;
      }

      public FlutterFragment.NewEngineInGroupFragmentBuilder handleDeeplinking(boolean var1) {
         this.handleDeeplinking = var1;
         return this;
      }

      public FlutterFragment.NewEngineInGroupFragmentBuilder initialRoute(String var1) {
         this.initialRoute = var1;
         return this;
      }

      public FlutterFragment.NewEngineInGroupFragmentBuilder renderMode(RenderMode var1) {
         this.renderMode = var1;
         return this;
      }

      public FlutterFragment.NewEngineInGroupFragmentBuilder shouldAttachEngineToActivity(boolean var1) {
         this.shouldAttachEngineToActivity = var1;
         return this;
      }

      public FlutterFragment.NewEngineInGroupFragmentBuilder shouldAutomaticallyHandleOnBackPressed(boolean var1) {
         this.shouldAutomaticallyHandleOnBackPressed = var1;
         return this;
      }

      public FlutterFragment.NewEngineInGroupFragmentBuilder shouldDelayFirstAndroidViewDraw(boolean var1) {
         this.shouldDelayFirstAndroidViewDraw = var1;
         return this;
      }

      public FlutterFragment.NewEngineInGroupFragmentBuilder transparencyMode(TransparencyMode var1) {
         this.transparencyMode = var1;
         return this;
      }
   }
}
