package dev.fluttercommunity.plus.wakelock

import WakelockPlusApi.Companion
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.BinaryMessenger

public class WakelockPlusPlugin : FlutterPlugin, WakelockPlusApi, ActivityAware {
   private final var wakelock: Wakelock?

   public open fun isEnabled(): IsEnabledMessage {
      val var1: Wakelock = this.wakelock;
      return var1.isEnabled();
   }

   public override fun onAttachedToActivity(binding: ActivityPluginBinding) {
      if (this.wakelock != null) {
         this.wakelock.setActivity(var1.getActivity());
      }
   }

   public override fun onAttachedToEngine(flutterPluginBinding: FlutterPluginBinding) {
      val var2: Companion = WakelockPlusApi.Companion;
      val var3: BinaryMessenger = var1.getBinaryMessenger();
      Companion.setUp$default(var2, var3, this, null, 4, null);
      this.wakelock = new Wakelock();
   }

   public override fun onDetachedFromActivity() {
      if (this.wakelock != null) {
         this.wakelock.setActivity(null);
      }
   }

   public override fun onDetachedFromActivityForConfigChanges() {
      this.onDetachedFromActivity();
   }

   public override fun onDetachedFromEngine(binding: FlutterPluginBinding) {
      val var2: Companion = WakelockPlusApi.Companion;
      val var3: BinaryMessenger = var1.getBinaryMessenger();
      Companion.setUp$default(var2, var3, null, null, 4, null);
      this.wakelock = null;
   }

   public override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
      this.onAttachedToActivity(var1);
   }

   public open fun toggle(msg: ToggleMessage) {
      val var2: Wakelock = this.wakelock;
      var2.toggle(var1);
   }
}
