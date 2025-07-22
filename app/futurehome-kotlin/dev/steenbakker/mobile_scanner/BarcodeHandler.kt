package dev.steenbakker.mobile_scanner

import android.os.Handler
import android.os.Looper
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.EventChannel.EventSink

public class BarcodeHandler(binaryMessenger: BinaryMessenger) : EventChannel.StreamHandler {
   private final var eventSink: EventSink?
   private final val eventChannel: EventChannel

   init {
      val var2: EventChannel = new EventChannel(var1, "dev.steenbakker.mobile_scanner/scanner/event");
      this.eventChannel = var2;
      var2.setStreamHandler(this);
   }

   @JvmStatic
   fun `publishError$lambda$0`(var0: BarcodeHandler, var1: java.lang.String, var2: java.lang.String, var3: Any) {
      if (var0.eventSink != null) {
         var0.eventSink.error(var1, var2, var3);
      }
   }

   @JvmStatic
   fun `publishEvent$lambda$1`(var0: BarcodeHandler, var1: java.util.Map) {
      if (var0.eventSink != null) {
         var0.eventSink.success(var1);
      }
   }

   public override fun onCancel(event: Any?) {
      this.eventSink = null;
   }

   public override fun onListen(event: Any?, eventSink: EventSink?) {
      this.eventSink = var2;
   }

   public fun publishError(errorCode: String, errorMessage: String, errorDetails: Any?) {
      new Handler(Looper.getMainLooper()).post(new BarcodeHandler$$ExternalSyntheticLambda1(this, var1, var2, var3));
   }

   public fun publishEvent(event: Map<String, Any>) {
      new Handler(Looper.getMainLooper()).post(new BarcodeHandler$$ExternalSyntheticLambda0(this, var1));
   }
}
