package dev.fluttercommunity.plus.connectivity;

import android.content.Context;
import android.net.ConnectivityManager;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;

public class ConnectivityPlugin implements FlutterPlugin {
   private EventChannel eventChannel;
   private MethodChannel methodChannel;
   private ConnectivityBroadcastReceiver receiver;

   private void setupChannels(BinaryMessenger var1, Context var2) {
      this.methodChannel = new MethodChannel(var1, "dev.fluttercommunity.plus/connectivity");
      this.eventChannel = new EventChannel(var1, "dev.fluttercommunity.plus/connectivity_status");
      Connectivity var3 = new Connectivity((ConnectivityManager)var2.getSystemService("connectivity"));
      ConnectivityMethodChannelHandler var4 = new ConnectivityMethodChannelHandler(var3);
      this.receiver = new ConnectivityBroadcastReceiver(var2, var3);
      this.methodChannel.setMethodCallHandler(var4);
      this.eventChannel.setStreamHandler(this.receiver);
   }

   private void teardownChannels() {
      this.methodChannel.setMethodCallHandler(null);
      this.eventChannel.setStreamHandler(null);
      this.receiver.onCancel(null);
      this.methodChannel = null;
      this.eventChannel = null;
      this.receiver = null;
   }

   @Override
   public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding var1) {
      this.setupChannels(var1.getBinaryMessenger(), var1.getApplicationContext());
   }

   @Override
   public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding var1) {
      this.teardownChannels();
   }
}
