package dev.steenbakker.mobile_scanner

import android.app.Activity
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.PluginRegistry
import io.flutter.view.TextureRegistry
import kotlin.jvm.functions.Function1

public class MobileScannerPlugin : FlutterPlugin, ActivityAware {
   private final var activityPluginBinding: ActivityPluginBinding?
   private final var flutterPluginBinding: FlutterPluginBinding?
   private final var methodCallHandler: MobileScannerHandler?

   public override fun onAttachedToActivity(activityPluginBinding: ActivityPluginBinding) {
      val var2: FlutterPlugin.FlutterPluginBinding = this.flutterPluginBinding;
      val var8: BinaryMessenger = var2.getBinaryMessenger();
      val var6: Activity = var1.getActivity();
      val var4: BarcodeHandler = new BarcodeHandler(var8);
      val var3: MobileScannerPermissions = new MobileScannerPermissions();
      val var5: Function1 = (
         new Function1<PluginRegistry.RequestPermissionsResultListener, Unit>(var1) {
            {
               super(
                  1,
                  var1,
                  ActivityPluginBinding::class.java,
                  "addRequestPermissionsResultListener",
                  "addRequestPermissionsResultListener(Lio/flutter/plugin/common/PluginRegistry$RequestPermissionsResultListener;)V",
                  0
               );
            }

            public final void invoke(PluginRegistry.RequestPermissionsResultListener var1) {
               (this.receiver as ActivityPluginBinding).addRequestPermissionsResultListener(var1);
            }
         }
      ) as Function1;
      val var7: FlutterPlugin.FlutterPluginBinding = this.flutterPluginBinding;
      val var9: TextureRegistry = var7.getTextureRegistry();
      this.methodCallHandler = new MobileScannerHandler(var6, var4, var8, var3, var5, var9);
      this.activityPluginBinding = var1;
   }

   public override fun onAttachedToEngine(binding: FlutterPluginBinding) {
      this.flutterPluginBinding = var1;
   }

   public override fun onDetachedFromActivity() {
      val var2: MobileScannerHandler = this.methodCallHandler;
      if (this.methodCallHandler != null) {
         val var1: ActivityPluginBinding = this.activityPluginBinding;
         var2.dispose(var1);
      }

      this.methodCallHandler = null;
      this.activityPluginBinding = null;
   }

   public override fun onDetachedFromActivityForConfigChanges() {
      this.onDetachedFromActivity();
   }

   public override fun onDetachedFromEngine(binding: FlutterPluginBinding) {
      this.flutterPluginBinding = null;
   }

   public override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
      this.onAttachedToActivity(var1);
   }
}
