package io.flutter.plugin.text;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.embedding.engine.systemchannels.ProcessTextChannel;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessTextPlugin implements FlutterPlugin, ActivityAware, PluginRegistry.ActivityResultListener, ProcessTextChannel.ProcessTextMethodHandler {
   private static final String TAG = "ProcessTextPlugin";
   private ActivityPluginBinding activityBinding;
   private final PackageManager packageManager;
   private final ProcessTextChannel processTextChannel;
   private Map<Integer, MethodChannel.Result> requestsByCode = new HashMap<>();
   private Map<String, ResolveInfo> resolveInfosById;

   public ProcessTextPlugin(ProcessTextChannel var1) {
      this.processTextChannel = var1;
      this.packageManager = var1.packageManager;
      var1.setMethodHandler(this);
   }

   private void cacheResolveInfos() {
      this.resolveInfosById = new HashMap<>();
      if (VERSION.SDK_INT >= 23) {
         Intent var1 = new Intent().setAction("android.intent.action.PROCESS_TEXT").setType("text/plain");
         List var4;
         if (VERSION.SDK_INT >= 33) {
            var4 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(
               this.packageManager, var1, AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(0L)
            );
         } else {
            var4 = this.packageManager.queryIntentActivities(var1, 0);
         }

         for (ResolveInfo var3 : var4) {
            String var2 = var3.activityInfo.name;
            var3.loadLabel(this.packageManager).toString();
            this.resolveInfosById.put(var2, var3);
         }
      }
   }

   public void destroy() {
      this.processTextChannel.setMethodHandler(null);
   }

   @Override
   public boolean onActivityResult(int var1, int var2, Intent var3) {
      if (!this.requestsByCode.containsKey(var1)) {
         return false;
      } else {
         String var4;
         if (var2 == -1) {
            var4 = var3.getStringExtra("android.intent.extra.PROCESS_TEXT");
         } else {
            var4 = null;
         }

         this.requestsByCode.remove(var1).success(var4);
         return true;
      }
   }

   @Override
   public void onAttachedToActivity(ActivityPluginBinding var1) {
      this.activityBinding = var1;
      var1.addActivityResultListener(this);
   }

   @Override
   public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding var1) {
   }

   @Override
   public void onDetachedFromActivity() {
      this.activityBinding.removeActivityResultListener(this);
      this.activityBinding = null;
   }

   @Override
   public void onDetachedFromActivityForConfigChanges() {
      this.activityBinding.removeActivityResultListener(this);
      this.activityBinding = null;
   }

   @Override
   public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding var1) {
   }

   @Override
   public void onReattachedToActivityForConfigChanges(ActivityPluginBinding var1) {
      this.activityBinding = var1;
      var1.addActivityResultListener(this);
   }

   @Override
   public void processTextAction(String var1, String var2, boolean var3, MethodChannel.Result var4) {
      if (this.activityBinding == null) {
         var4.error("error", "Plugin not bound to an Activity", null);
      } else if (VERSION.SDK_INT < 23) {
         var4.error("error", "Android version not supported", null);
      } else {
         Map var6 = this.resolveInfosById;
         if (var6 == null) {
            var4.error("error", "Can not process text actions before calling queryTextActions", null);
         } else {
            ResolveInfo var10 = (ResolveInfo)var6.get(var1);
            if (var10 == null) {
               var4.error("error", "Text processing activity not found", null);
            } else {
               int var5 = var4.hashCode();
               Integer var7 = var5;
               this.requestsByCode.put(var7, var4);
               Intent var9 = new Intent();
               var9.setClassName(var10.activityInfo.packageName, var10.activityInfo.name);
               var9.setAction("android.intent.action.PROCESS_TEXT");
               var9.setType("text/plain");
               var9.putExtra("android.intent.extra.PROCESS_TEXT", var2);
               var9.putExtra("android.intent.extra.PROCESS_TEXT_READONLY", var3);
               Activity var8 = this.activityBinding.getActivity();
               var7.getClass();
               var8.startActivityForResult(var9, var5);
            }
         }
      }
   }

   @Override
   public Map<String, String> queryTextActions() {
      if (this.resolveInfosById == null) {
         this.cacheResolveInfos();
      }

      HashMap var2 = new HashMap();

      for (String var1 : this.resolveInfosById.keySet()) {
         var2.put(var1, this.resolveInfosById.get(var1).loadLabel(this.packageManager).toString());
      }

      return var2;
   }
}
