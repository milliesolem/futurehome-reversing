package io.flutter.embedding.engine.plugins.activity;

public interface ActivityAware {
   void onAttachedToActivity(ActivityPluginBinding var1);

   void onDetachedFromActivity();

   void onDetachedFromActivityForConfigChanges();

   void onReattachedToActivityForConfigChanges(ActivityPluginBinding var1);
}
