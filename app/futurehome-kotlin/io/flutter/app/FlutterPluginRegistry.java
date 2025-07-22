package io.flutter.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.platform.PlatformViewRegistry;
import io.flutter.plugin.platform.PlatformViewsController;
import io.flutter.view.FlutterMain;
import io.flutter.view.FlutterNativeView;
import io.flutter.view.FlutterView;
import io.flutter.view.TextureRegistry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Deprecated
public class FlutterPluginRegistry
   implements PluginRegistry,
   PluginRegistry.RequestPermissionsResultListener,
   PluginRegistry.ActivityResultListener,
   PluginRegistry.NewIntentListener,
   PluginRegistry.WindowFocusChangedListener,
   PluginRegistry.UserLeaveHintListener,
   PluginRegistry.ViewDestroyListener {
   private static final String TAG = "FlutterPluginRegistry";
   private Activity mActivity;
   private final List<PluginRegistry.ActivityResultListener> mActivityResultListeners;
   private Context mAppContext;
   private FlutterView mFlutterView;
   private FlutterNativeView mNativeView;
   private final List<PluginRegistry.NewIntentListener> mNewIntentListeners;
   private final PlatformViewsController mPlatformViewsController;
   private final Map<String, Object> mPluginMap = new LinkedHashMap<>(0);
   private final List<PluginRegistry.RequestPermissionsResultListener> mRequestPermissionsResultListeners = new ArrayList<>(0);
   private final List<PluginRegistry.UserLeaveHintListener> mUserLeaveHintListeners;
   private final List<PluginRegistry.ViewDestroyListener> mViewDestroyListeners;
   private final List<PluginRegistry.WindowFocusChangedListener> mWindowFocusChangedListeners;

   public FlutterPluginRegistry(FlutterEngine var1, Context var2) {
      this.mActivityResultListeners = new ArrayList<>(0);
      this.mNewIntentListeners = new ArrayList<>(0);
      this.mUserLeaveHintListeners = new ArrayList<>(0);
      this.mWindowFocusChangedListeners = new ArrayList<>(0);
      this.mViewDestroyListeners = new ArrayList<>(0);
      this.mAppContext = var2;
      this.mPlatformViewsController = new PlatformViewsController();
   }

   public FlutterPluginRegistry(FlutterNativeView var1, Context var2) {
      this.mActivityResultListeners = new ArrayList<>(0);
      this.mNewIntentListeners = new ArrayList<>(0);
      this.mUserLeaveHintListeners = new ArrayList<>(0);
      this.mWindowFocusChangedListeners = new ArrayList<>(0);
      this.mViewDestroyListeners = new ArrayList<>(0);
      this.mNativeView = var1;
      this.mAppContext = var2;
      this.mPlatformViewsController = new PlatformViewsController();
   }

   public void attach(FlutterView var1, Activity var2) {
      this.mFlutterView = var1;
      this.mActivity = var2;
      this.mPlatformViewsController.attach(var2, var1, var1.getDartExecutor());
   }

   public void destroy() {
      this.mPlatformViewsController.onDetachedFromJNI();
   }

   public void detach() {
      this.mPlatformViewsController.detach();
      this.mPlatformViewsController.onDetachedFromJNI();
      this.mFlutterView = null;
      this.mActivity = null;
   }

   public PlatformViewsController getPlatformViewsController() {
      return this.mPlatformViewsController;
   }

   @Override
   public boolean hasPlugin(String var1) {
      return this.mPluginMap.containsKey(var1);
   }

   @Override
   public boolean onActivityResult(int var1, int var2, Intent var3) {
      Iterator var4 = this.mActivityResultListeners.iterator();

      while (var4.hasNext()) {
         if (((PluginRegistry.ActivityResultListener)var4.next()).onActivityResult(var1, var2, var3)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean onNewIntent(Intent var1) {
      Iterator var2 = this.mNewIntentListeners.iterator();

      while (var2.hasNext()) {
         if (((PluginRegistry.NewIntentListener)var2.next()).onNewIntent(var1)) {
            return true;
         }
      }

      return false;
   }

   public void onPreEngineRestart() {
      this.mPlatformViewsController.onPreEngineRestart();
   }

   @Override
   public boolean onRequestPermissionsResult(int var1, String[] var2, int[] var3) {
      Iterator var4 = this.mRequestPermissionsResultListeners.iterator();

      while (var4.hasNext()) {
         if (((PluginRegistry.RequestPermissionsResultListener)var4.next()).onRequestPermissionsResult(var1, var2, var3)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public void onUserLeaveHint() {
      Iterator var1 = this.mUserLeaveHintListeners.iterator();

      while (var1.hasNext()) {
         ((PluginRegistry.UserLeaveHintListener)var1.next()).onUserLeaveHint();
      }
   }

   @Override
   public boolean onViewDestroy(FlutterNativeView var1) {
      Iterator var3 = this.mViewDestroyListeners.iterator();
      boolean var2 = false;

      while (var3.hasNext()) {
         if (((PluginRegistry.ViewDestroyListener)var3.next()).onViewDestroy(var1)) {
            var2 = true;
         }
      }

      return var2;
   }

   @Override
   public void onWindowFocusChanged(boolean var1) {
      Iterator var2 = this.mWindowFocusChangedListeners.iterator();

      while (var2.hasNext()) {
         ((PluginRegistry.WindowFocusChangedListener)var2.next()).onWindowFocusChanged(var1);
      }
   }

   @Override
   public PluginRegistry.Registrar registrarFor(String var1) {
      if (!this.mPluginMap.containsKey(var1)) {
         this.mPluginMap.put(var1, null);
         return new FlutterPluginRegistry.FlutterRegistrar(this, var1);
      } else {
         StringBuilder var2 = new StringBuilder("Plugin key ");
         var2.append(var1);
         var2.append(" is already in use");
         throw new IllegalStateException(var2.toString());
      }
   }

   @Override
   public <T> T valuePublishedByPlugin(String var1) {
      return (T)this.mPluginMap.get(var1);
   }

   private class FlutterRegistrar implements PluginRegistry.Registrar {
      private final String pluginKey;
      final FlutterPluginRegistry this$0;

      FlutterRegistrar(FlutterPluginRegistry var1, String var2) {
         this.this$0 = var1;
         this.pluginKey = var2;
      }

      @Override
      public Context activeContext() {
         Object var1;
         if (this.this$0.mActivity != null) {
            var1 = this.this$0.mActivity;
         } else {
            var1 = this.this$0.mAppContext;
         }

         return (Context)var1;
      }

      @Override
      public Activity activity() {
         return this.this$0.mActivity;
      }

      @Override
      public PluginRegistry.Registrar addActivityResultListener(PluginRegistry.ActivityResultListener var1) {
         this.this$0.mActivityResultListeners.add(var1);
         return this;
      }

      @Override
      public PluginRegistry.Registrar addNewIntentListener(PluginRegistry.NewIntentListener var1) {
         this.this$0.mNewIntentListeners.add(var1);
         return this;
      }

      @Override
      public PluginRegistry.Registrar addRequestPermissionsResultListener(PluginRegistry.RequestPermissionsResultListener var1) {
         this.this$0.mRequestPermissionsResultListeners.add(var1);
         return this;
      }

      @Override
      public PluginRegistry.Registrar addUserLeaveHintListener(PluginRegistry.UserLeaveHintListener var1) {
         this.this$0.mUserLeaveHintListeners.add(var1);
         return this;
      }

      @Override
      public PluginRegistry.Registrar addViewDestroyListener(PluginRegistry.ViewDestroyListener var1) {
         this.this$0.mViewDestroyListeners.add(var1);
         return this;
      }

      @Override
      public PluginRegistry.Registrar addWindowFocusChangedListener(PluginRegistry.WindowFocusChangedListener var1) {
         this.this$0.mWindowFocusChangedListeners.add(var1);
         return this;
      }

      @Override
      public Context context() {
         return this.this$0.mAppContext;
      }

      @Override
      public String lookupKeyForAsset(String var1) {
         return FlutterMain.getLookupKeyForAsset(var1);
      }

      @Override
      public String lookupKeyForAsset(String var1, String var2) {
         return FlutterMain.getLookupKeyForAsset(var1, var2);
      }

      @Override
      public BinaryMessenger messenger() {
         return this.this$0.mNativeView;
      }

      @Override
      public PlatformViewRegistry platformViewRegistry() {
         return this.this$0.mPlatformViewsController.getRegistry();
      }

      @Override
      public PluginRegistry.Registrar publish(Object var1) {
         this.this$0.mPluginMap.put(this.pluginKey, var1);
         return this;
      }

      @Override
      public TextureRegistry textures() {
         return this.this$0.mFlutterView;
      }

      @Override
      public FlutterView view() {
         return this.this$0.mFlutterView;
      }
   }
}
