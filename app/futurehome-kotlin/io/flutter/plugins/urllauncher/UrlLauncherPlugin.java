package io.flutter.plugins.urllauncher;

import android.util.Log;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;

public final class UrlLauncherPlugin implements FlutterPlugin, ActivityAware {
   private static final String TAG = "UrlLauncherPlugin";
   private UrlLauncher urlLauncher;

   @Override
   public void onAttachedToActivity(ActivityPluginBinding var1) {
      UrlLauncher var2 = this.urlLauncher;
      if (var2 == null) {
         Log.wtf("UrlLauncherPlugin", "urlLauncher was never set.");
      } else {
         var2.setActivity(var1.getActivity());
      }
   }

   @Override
   public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding var1) {
      this.urlLauncher = new UrlLauncher(var1.getApplicationContext());
      Messages$UrlLauncherApi$_CC.setUp(var1.getBinaryMessenger(), this.urlLauncher);
   }

   @Override
   public void onDetachedFromActivity() {
      UrlLauncher var1 = this.urlLauncher;
      if (var1 == null) {
         Log.wtf("UrlLauncherPlugin", "urlLauncher was never set.");
      } else {
         var1.setActivity(null);
      }
   }

   @Override
   public void onDetachedFromActivityForConfigChanges() {
      this.onDetachedFromActivity();
   }

   @Override
   public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding var1) {
      if (this.urlLauncher == null) {
         Log.wtf("UrlLauncherPlugin", "Already detached from the engine.");
      } else {
         Messages$UrlLauncherApi$_CC.setUp(var1.getBinaryMessenger(), null);
         this.urlLauncher = null;
      }
   }

   @Override
   public void onReattachedToActivityForConfigChanges(ActivityPluginBinding var1) {
      this.onAttachedToActivity(var1);
   }
}
