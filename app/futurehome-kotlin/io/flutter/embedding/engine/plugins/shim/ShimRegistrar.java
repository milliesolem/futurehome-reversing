package io.flutter.embedding.engine.plugins.shim;

import android.app.Activity;
import android.content.Context;
import io.flutter.FlutterInjector;
import io.flutter.Log;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.platform.PlatformViewRegistry;
import io.flutter.view.FlutterView;
import io.flutter.view.TextureRegistry;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class ShimRegistrar implements PluginRegistry.Registrar, FlutterPlugin, ActivityAware {
   private static final String TAG = "ShimRegistrar";
   private final Set<PluginRegistry.WindowFocusChangedListener> WindowFocusChangedListeners;
   private ActivityPluginBinding activityPluginBinding;
   private final Set<PluginRegistry.ActivityResultListener> activityResultListeners;
   private final Map<String, Object> globalRegistrarMap;
   private final Set<PluginRegistry.NewIntentListener> newIntentListeners;
   private FlutterPlugin.FlutterPluginBinding pluginBinding;
   private final String pluginId;
   private final Set<PluginRegistry.RequestPermissionsResultListener> requestPermissionsResultListeners;
   private final Set<PluginRegistry.UserLeaveHintListener> userLeaveHintListeners;
   private final Set<PluginRegistry.ViewDestroyListener> viewDestroyListeners = new HashSet<>();

   public ShimRegistrar(String var1, Map<String, Object> var2) {
      this.requestPermissionsResultListeners = new HashSet<>();
      this.activityResultListeners = new HashSet<>();
      this.newIntentListeners = new HashSet<>();
      this.userLeaveHintListeners = new HashSet<>();
      this.WindowFocusChangedListeners = new HashSet<>();
      this.pluginId = var1;
      this.globalRegistrarMap = var2;
   }

   private void addExistingListenersToActivityPluginBinding() {
      for (PluginRegistry.RequestPermissionsResultListener var1 : this.requestPermissionsResultListeners) {
         this.activityPluginBinding.addRequestPermissionsResultListener(var1);
      }

      for (PluginRegistry.ActivityResultListener var7 : this.activityResultListeners) {
         this.activityPluginBinding.addActivityResultListener(var7);
      }

      for (PluginRegistry.NewIntentListener var4 : this.newIntentListeners) {
         this.activityPluginBinding.addOnNewIntentListener(var4);
      }

      for (PluginRegistry.UserLeaveHintListener var5 : this.userLeaveHintListeners) {
         this.activityPluginBinding.addOnUserLeaveHintListener(var5);
      }

      for (PluginRegistry.WindowFocusChangedListener var6 : this.WindowFocusChangedListeners) {
         this.activityPluginBinding.addOnWindowFocusChangedListener(var6);
      }
   }

   @Override
   public Context activeContext() {
      Object var1;
      if (this.activityPluginBinding == null) {
         var1 = this.context();
      } else {
         var1 = this.activity();
      }

      return (Context)var1;
   }

   @Override
   public Activity activity() {
      ActivityPluginBinding var1 = this.activityPluginBinding;
      Activity var2;
      if (var1 != null) {
         var2 = var1.getActivity();
      } else {
         var2 = null;
      }

      return var2;
   }

   @Override
   public PluginRegistry.Registrar addActivityResultListener(PluginRegistry.ActivityResultListener var1) {
      this.activityResultListeners.add(var1);
      ActivityPluginBinding var2 = this.activityPluginBinding;
      if (var2 != null) {
         var2.addActivityResultListener(var1);
      }

      return this;
   }

   @Override
   public PluginRegistry.Registrar addNewIntentListener(PluginRegistry.NewIntentListener var1) {
      this.newIntentListeners.add(var1);
      ActivityPluginBinding var2 = this.activityPluginBinding;
      if (var2 != null) {
         var2.addOnNewIntentListener(var1);
      }

      return this;
   }

   @Override
   public PluginRegistry.Registrar addRequestPermissionsResultListener(PluginRegistry.RequestPermissionsResultListener var1) {
      this.requestPermissionsResultListeners.add(var1);
      ActivityPluginBinding var2 = this.activityPluginBinding;
      if (var2 != null) {
         var2.addRequestPermissionsResultListener(var1);
      }

      return this;
   }

   @Override
   public PluginRegistry.Registrar addUserLeaveHintListener(PluginRegistry.UserLeaveHintListener var1) {
      this.userLeaveHintListeners.add(var1);
      ActivityPluginBinding var2 = this.activityPluginBinding;
      if (var2 != null) {
         var2.addOnUserLeaveHintListener(var1);
      }

      return this;
   }

   @Override
   public PluginRegistry.Registrar addViewDestroyListener(PluginRegistry.ViewDestroyListener var1) {
      this.viewDestroyListeners.add(var1);
      return this;
   }

   @Override
   public PluginRegistry.Registrar addWindowFocusChangedListener(PluginRegistry.WindowFocusChangedListener var1) {
      this.WindowFocusChangedListeners.add(var1);
      ActivityPluginBinding var2 = this.activityPluginBinding;
      if (var2 != null) {
         var2.addOnWindowFocusChangedListener(var1);
      }

      return this;
   }

   @Override
   public Context context() {
      FlutterPlugin.FlutterPluginBinding var1 = this.pluginBinding;
      Context var2;
      if (var1 != null) {
         var2 = var1.getApplicationContext();
      } else {
         var2 = null;
      }

      return var2;
   }

   @Override
   public String lookupKeyForAsset(String var1) {
      return FlutterInjector.instance().flutterLoader().getLookupKeyForAsset(var1);
   }

   @Override
   public String lookupKeyForAsset(String var1, String var2) {
      return FlutterInjector.instance().flutterLoader().getLookupKeyForAsset(var1, var2);
   }

   @Override
   public BinaryMessenger messenger() {
      FlutterPlugin.FlutterPluginBinding var1 = this.pluginBinding;
      BinaryMessenger var2;
      if (var1 != null) {
         var2 = var1.getBinaryMessenger();
      } else {
         var2 = null;
      }

      return var2;
   }

   @Override
   public void onAttachedToActivity(ActivityPluginBinding var1) {
      Log.v("ShimRegistrar", "Attached to an Activity.");
      this.activityPluginBinding = var1;
      this.addExistingListenersToActivityPluginBinding();
   }

   @Override
   public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding var1) {
      Log.v("ShimRegistrar", "Attached to FlutterEngine.");
      this.pluginBinding = var1;
   }

   @Override
   public void onDetachedFromActivity() {
      Log.v("ShimRegistrar", "Detached from an Activity.");
      this.activityPluginBinding = null;
   }

   @Override
   public void onDetachedFromActivityForConfigChanges() {
      Log.v("ShimRegistrar", "Detached from an Activity for config changes.");
      this.activityPluginBinding = null;
   }

   @Override
   public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding var1) {
      Log.v("ShimRegistrar", "Detached from FlutterEngine.");
      Iterator var2 = this.viewDestroyListeners.iterator();

      while (var2.hasNext()) {
         ((PluginRegistry.ViewDestroyListener)var2.next()).onViewDestroy(null);
      }

      this.pluginBinding = null;
      this.activityPluginBinding = null;
   }

   @Override
   public void onReattachedToActivityForConfigChanges(ActivityPluginBinding var1) {
      Log.v("ShimRegistrar", "Reconnected to an Activity after config changes.");
      this.activityPluginBinding = var1;
      this.addExistingListenersToActivityPluginBinding();
   }

   @Override
   public PlatformViewRegistry platformViewRegistry() {
      FlutterPlugin.FlutterPluginBinding var1 = this.pluginBinding;
      PlatformViewRegistry var2;
      if (var1 != null) {
         var2 = var1.getPlatformViewRegistry();
      } else {
         var2 = null;
      }

      return var2;
   }

   @Override
   public PluginRegistry.Registrar publish(Object var1) {
      this.globalRegistrarMap.put(this.pluginId, var1);
      return this;
   }

   @Override
   public TextureRegistry textures() {
      FlutterPlugin.FlutterPluginBinding var1 = this.pluginBinding;
      TextureRegistry var2;
      if (var1 != null) {
         var2 = var1.getTextureRegistry();
      } else {
         var2 = null;
      }

      return var2;
   }

   @Override
   public FlutterView view() {
      throw new UnsupportedOperationException("The new embedding does not support the old FlutterView.");
   }
}
