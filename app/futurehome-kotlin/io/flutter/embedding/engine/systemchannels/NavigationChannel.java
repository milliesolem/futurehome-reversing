package io.flutter.embedding.engine.systemchannels;

import io.flutter.Log;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.JSONMethodCodec;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.util.HashMap;

public class NavigationChannel {
   private static final String TAG = "NavigationChannel";
   public final MethodChannel channel;
   private final MethodChannel.MethodCallHandler defaultHandler;

   public NavigationChannel(DartExecutor var1) {
      MethodChannel.MethodCallHandler var2 = new MethodChannel.MethodCallHandler(this) {
         final NavigationChannel this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void onMethodCall(MethodCall var1, MethodChannel.Result var2x) {
            var2x.success(null);
         }
      };
      this.defaultHandler = var2;
      MethodChannel var3 = new MethodChannel(var1, "flutter/navigation", JSONMethodCodec.INSTANCE);
      this.channel = var3;
      var3.setMethodCallHandler(var2);
   }

   public void popRoute() {
      Log.v("NavigationChannel", "Sending message to pop route.");
      this.channel.invokeMethod("popRoute", null);
   }

   public void pushRoute(String var1) {
      StringBuilder var2 = new StringBuilder("Sending message to push route '");
      var2.append(var1);
      var2.append("'");
      Log.v("NavigationChannel", var2.toString());
      this.channel.invokeMethod("pushRoute", var1);
   }

   public void pushRouteInformation(String var1) {
      StringBuilder var2 = new StringBuilder("Sending message to push route information '");
      var2.append(var1);
      var2.append("'");
      Log.v("NavigationChannel", var2.toString());
      HashMap var3 = new HashMap();
      var3.put("location", var1);
      this.channel.invokeMethod("pushRouteInformation", var3);
   }

   public void setInitialRoute(String var1) {
      StringBuilder var2 = new StringBuilder("Sending message to set initial route to '");
      var2.append(var1);
      var2.append("'");
      Log.v("NavigationChannel", var2.toString());
      this.channel.invokeMethod("setInitialRoute", var1);
   }

   public void setMethodCallHandler(MethodChannel.MethodCallHandler var1) {
      this.channel.setMethodCallHandler(var1);
   }
}
