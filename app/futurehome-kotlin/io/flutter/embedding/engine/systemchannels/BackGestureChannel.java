package io.flutter.embedding.engine.systemchannels;

import android.window.BackEvent;
import io.flutter.Log;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMethodCodec;
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BackGestureChannel {
   private static final String TAG = "BackGestureChannel";
   public final MethodChannel channel;
   private final MethodChannel.MethodCallHandler defaultHandler;

   public BackGestureChannel(DartExecutor var1) {
      MethodChannel.MethodCallHandler var2 = new MethodChannel.MethodCallHandler(this) {
         final BackGestureChannel this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void onMethodCall(MethodCall var1, MethodChannel.Result var2x) {
            var2x.success(null);
         }
      };
      this.defaultHandler = var2;
      MethodChannel var3 = new MethodChannel(var1, "flutter/backgesture", StandardMethodCodec.INSTANCE);
      this.channel = var3;
      var3.setMethodCallHandler(var2);
   }

   private Map<String, Object> backEventToJsonMap(BackEvent var1) {
      HashMap var5 = new HashMap(3);
      float var3 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1);
      float var2 = AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m$1(var1);
      List var4;
      if (!Float.isNaN(var3) && !Float.isNaN(var2)) {
         var4 = Arrays.asList(var3, var2);
      } else {
         var4 = null;
      }

      var5.put("touchOffset", var4);
      var5.put("progress", AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m$2(var1));
      var5.put("swipeEdge", AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var1));
      return var5;
   }

   public void cancelBackGesture() {
      Log.v("BackGestureChannel", "Sending message to cancel back gesture");
      this.channel.invokeMethod("cancelBackGesture", null);
   }

   public void commitBackGesture() {
      Log.v("BackGestureChannel", "Sending message to commit back gesture");
      this.channel.invokeMethod("commitBackGesture", null);
   }

   public void setMethodCallHandler(MethodChannel.MethodCallHandler var1) {
      this.channel.setMethodCallHandler(var1);
   }

   public void startBackGesture(BackEvent var1) {
      Log.v("BackGestureChannel", "Sending message to start back gesture");
      this.channel.invokeMethod("startBackGesture", this.backEventToJsonMap(var1));
   }

   public void updateBackGestureProgress(BackEvent var1) {
      Log.v("BackGestureChannel", "Sending message to update back gesture progress");
      this.channel.invokeMethod("updateBackGestureProgress", this.backEventToJsonMap(var1));
   }
}
