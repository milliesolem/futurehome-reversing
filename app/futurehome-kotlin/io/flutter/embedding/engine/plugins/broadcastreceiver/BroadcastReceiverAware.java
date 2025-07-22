package io.flutter.embedding.engine.plugins.broadcastreceiver;

public interface BroadcastReceiverAware {
   void onAttachedToBroadcastReceiver(BroadcastReceiverPluginBinding var1);

   void onDetachedFromBroadcastReceiver();
}
