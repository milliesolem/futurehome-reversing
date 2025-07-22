package dev.fluttercommunity.android_id

import android.content.ContentResolver
import android.provider.Settings.Secure
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.Result
import kotlin.jvm.internal.Intrinsics

public class AndroidIdPlugin : FlutterPlugin, MethodChannel.MethodCallHandler {
   private final lateinit var channel: MethodChannel
   private final lateinit var contentResolver: ContentResolver

   private fun getAndroidId(): String? {
      var var1: ContentResolver = this.contentResolver;
      if (this.contentResolver == null) {
         Intrinsics.throwUninitializedPropertyAccessException("contentResolver");
         var1 = null;
      }

      return Secure.getString(var1, "android_id");
   }

   public override fun onAttachedToEngine(flutterPluginBinding: FlutterPluginBinding) {
      this.contentResolver = var1.getApplicationContext().getContentResolver();
      val var2: MethodChannel = new MethodChannel(var1.getBinaryMessenger(), "android_id");
      this.channel = var2;
      var2.setMethodCallHandler(this);
   }

   public override fun onDetachedFromEngine(binding: FlutterPluginBinding) {
      var var3: MethodChannel = this.channel;
      if (this.channel == null) {
         Intrinsics.throwUninitializedPropertyAccessException("channel");
         var3 = null;
      }

      var3.setMethodCallHandler(null);
   }

   public override fun onMethodCall(call: MethodCall, result: Result) {
      if (var1.method == "getId") {
         try {
            var2.success(this.getAndroidId());
         } catch (var3: Exception) {
            var2.error("ERROR_GETTING_ID", "Failed to get Android ID", var3.getLocalizedMessage());
         }
      } else {
         var2.notImplemented();
      }
   }
}
