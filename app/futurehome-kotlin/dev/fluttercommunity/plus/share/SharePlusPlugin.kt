package dev.fluttercommunity.plus.share

import android.content.Context
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodChannel
import kotlin.jvm.internal.Intrinsics

public class SharePlusPlugin : FlutterPlugin, ActivityAware {
   private final lateinit var share: Share
   private final lateinit var manager: ShareSuccessManager
   private final lateinit var methodChannel: MethodChannel

   public override fun onAttachedToActivity(binding: ActivityPluginBinding) {
      var var2: ShareSuccessManager = this.manager;
      if (this.manager == null) {
         Intrinsics.throwUninitializedPropertyAccessException("manager");
         var2 = null;
      }

      var1.addActivityResultListener(var2);
      var var5: Share = this.share;
      if (this.share == null) {
         Intrinsics.throwUninitializedPropertyAccessException("share");
         var5 = null;
      }

      var5.setActivity(var1.getActivity());
   }

   public override fun onAttachedToEngine(binding: FlutterPluginBinding) {
      this.methodChannel = new MethodChannel(var1.getBinaryMessenger(), "dev.fluttercommunity.plus/share");
      val var2: Context = var1.getApplicationContext();
      this.manager = new ShareSuccessManager(var2);
      val var4: Context = var1.getApplicationContext();
      var var5: ShareSuccessManager = this.manager;
      if (this.manager == null) {
         Intrinsics.throwUninitializedPropertyAccessException("manager");
         var5 = null;
      }

      this.share = new Share(var4, null, var5);
      var var6: Share = this.share;
      if (this.share == null) {
         Intrinsics.throwUninitializedPropertyAccessException("share");
         var6 = null;
      }

      var var10: ShareSuccessManager = this.manager;
      if (this.manager == null) {
         Intrinsics.throwUninitializedPropertyAccessException("manager");
         var10 = null;
      }

      val var11: MethodCallHandler = new MethodCallHandler(var6, var10);
      var var7: MethodChannel = this.methodChannel;
      if (this.methodChannel == null) {
         Intrinsics.throwUninitializedPropertyAccessException("methodChannel");
         var7 = null;
      }

      var7.setMethodCallHandler(var11);
   }

   public override fun onDetachedFromActivity() {
      var var1: Share = this.share;
      if (this.share == null) {
         Intrinsics.throwUninitializedPropertyAccessException("share");
         var1 = null;
      }

      var1.setActivity(null);
   }

   public override fun onDetachedFromActivityForConfigChanges() {
      this.onDetachedFromActivity();
   }

   public override fun onDetachedFromEngine(binding: FlutterPluginBinding) {
      var var3: MethodChannel = this.methodChannel;
      if (this.methodChannel == null) {
         Intrinsics.throwUninitializedPropertyAccessException("methodChannel");
         var3 = null;
      }

      var3.setMethodCallHandler(null);
   }

   public override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
      this.onAttachedToActivity(var1);
   }

   public companion object {
      private const val CHANNEL: String
   }
}
