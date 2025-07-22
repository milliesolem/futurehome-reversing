package com.signify.hue.flutterreactiveble

import android.content.Context
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.Result

public class ReactiveBlePlugin : FlutterPlugin, MethodChannel.MethodCallHandler {
   public override fun onAttachedToEngine(binding: FlutterPluginBinding) {
      val var2: ReactiveBlePlugin.Companion = Companion;
      val var3: BinaryMessenger = var1.getBinaryMessenger();
      val var4: Context = var1.getApplicationContext();
      ReactiveBlePlugin.Companion.access$initializePlugin(var2, var3, var4, this);
   }

   public override fun onDetachedFromEngine(binding: FlutterPluginBinding) {
      ReactiveBlePlugin.Companion.access$deinitializePlugin(Companion);
   }

   public override fun onMethodCall(call: MethodCall, result: Result) {
      Companion.getPluginController().execute$reactive_ble_mobile_release(var1, var2);
   }

   public companion object {
      public final lateinit var pluginController: PluginController
         internal set

      private fun deinitializePlugin() {
         this.getPluginController().deinitialize$reactive_ble_mobile_release();
      }

      private fun initializePlugin(messenger: BinaryMessenger, context: Context, plugin: ReactiveBlePlugin) {
         new MethodChannel(var1, "flutter_reactive_ble_method").setMethodCallHandler(var3);
         this.setPluginController(new PluginController());
         this.getPluginController().initialize$reactive_ble_mobile_release(var1, var2);
      }
   }
}
