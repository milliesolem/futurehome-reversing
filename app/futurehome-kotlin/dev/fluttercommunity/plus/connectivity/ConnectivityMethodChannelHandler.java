package dev.fluttercommunity.plus.connectivity;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

class ConnectivityMethodChannelHandler implements MethodChannel.MethodCallHandler {
   static final boolean $assertionsDisabled = false;
   private final Connectivity connectivity;

   ConnectivityMethodChannelHandler(Connectivity var1) {
      this.connectivity = var1;
   }

   @Override
   public void onMethodCall(MethodCall var1, MethodChannel.Result var2) {
      if ("check".equals(var1.method)) {
         var2.success(this.connectivity.getNetworkType());
      } else {
         var2.notImplemented();
      }
   }
}
