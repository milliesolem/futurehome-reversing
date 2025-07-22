package dev.fluttercommunity.plus.device_info

import android.app.ActivityManager
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodChannel
import kotlin.jvm.internal.Intrinsics

public class DeviceInfoPlusPlugin : FlutterPlugin {
   private final lateinit var methodChannel: MethodChannel

   private fun setupMethodChannel(messenger: BinaryMessenger, context: Context) {
      this.methodChannel = new MethodChannel(var1, "dev.fluttercommunity.plus/device_info");
      val var4: PackageManager = var2.getPackageManager();
      var var3: Any = var2.getSystemService("activity");
      var3 = var3 as ActivityManager;
      val var6: ContentResolver = var2.getContentResolver();
      var3 = new MethodCallHandlerImpl(var4, (ActivityManager)var3, var6);
      var var5: MethodChannel = this.methodChannel;
      if (this.methodChannel == null) {
         Intrinsics.throwUninitializedPropertyAccessException("methodChannel");
         var5 = null;
      }

      var5.setMethodCallHandler(var3 as MethodChannel.MethodCallHandler);
   }

   public override fun onAttachedToEngine(binding: FlutterPluginBinding) {
      val var2: BinaryMessenger = var1.getBinaryMessenger();
      val var3: Context = var1.getApplicationContext();
      this.setupMethodChannel(var2, var3);
   }

   public override fun onDetachedFromEngine(binding: FlutterPluginBinding) {
      var var3: MethodChannel = this.methodChannel;
      if (this.methodChannel == null) {
         Intrinsics.throwUninitializedPropertyAccessException("methodChannel");
         var3 = null;
      }

      var3.setMethodCallHandler(null);
   }
}
