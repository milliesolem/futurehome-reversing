package dev.flutter.plugins.integration_test;

import android.app.Activity;
import android.content.Context;
import com.google.common.util.concurrent.SettableFuture;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Future;

public class IntegrationTestPlugin implements MethodChannel.MethodCallHandler, FlutterPlugin, ActivityAware {
   private static final String CHANNEL = "plugins.flutter.io/integration_test";
   public static final Future<Map<String, String>> testResults;
   private static final SettableFuture<Map<String, String>> testResultsSettable;
   private Activity flutterActivity;
   private MethodChannel methodChannel;

   static {
      SettableFuture var0 = SettableFuture.create();
      testResultsSettable = var0;
      testResults = var0;
   }

   private void onAttachedToEngine(Context var1, BinaryMessenger var2) {
      MethodChannel var3 = new MethodChannel(var2, "plugins.flutter.io/integration_test");
      this.methodChannel = var3;
      var3.setMethodCallHandler(this);
   }

   @Override
   public void onAttachedToActivity(ActivityPluginBinding var1) {
      this.flutterActivity = var1.getActivity();
   }

   @Override
   public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding var1) {
      this.onAttachedToEngine(var1.getApplicationContext(), var1.getBinaryMessenger());
   }

   @Override
   public void onDetachedFromActivity() {
      this.flutterActivity = null;
   }

   @Override
   public void onDetachedFromActivityForConfigChanges() {
      this.flutterActivity = null;
   }

   @Override
   public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding var1) {
      this.methodChannel.setMethodCallHandler(null);
      this.methodChannel = null;
   }

   @Override
   public void onMethodCall(MethodCall var1, MethodChannel.Result var2) {
      String var5 = var1.method;
      var5.hashCode();
      int var4 = var5.hashCode();
      byte var3 = -1;
      switch (var4) {
         case -575977140:
            if (var5.equals("captureScreenshot")) {
               var3 = 0;
            }
            break;
         case -426872452:
            if (var5.equals("convertFlutterSurfaceToImage")) {
               var3 = 1;
            }
            break;
         case -399852893:
            if (var5.equals("revertFlutterImage")) {
               var3 = 2;
            }
            break;
         case 15721874:
            if (var5.equals("allTestsFinished")) {
               var3 = 3;
            }
      }

      switch (var3) {
         case 0:
            if (FlutterDeviceScreenshot.hasInstrumentation()) {
               try {
                  var11 = FlutterDeviceScreenshot.captureWithUiAutomation();
               } catch (IOException var6) {
                  var2.error("Could not capture screenshot", "UiAutomation failed", var6);
                  return;
               }

               var2.success(var11);
               return;
            } else {
               Activity var10 = this.flutterActivity;
               if (var10 == null) {
                  var2.error("Could not capture screenshot", "Activity not initialized", null);
                  return;
               }

               FlutterDeviceScreenshot.captureView(var10, this.methodChannel, var2);
               return;
            }
         case 1:
            Activity var9 = this.flutterActivity;
            if (var9 == null) {
               var2.error("Could not convert to image", "Activity not initialized", null);
               return;
            }

            FlutterDeviceScreenshot.convertFlutterSurfaceToImage(var9);
            var2.success(null);
            return;
         case 2:
            Activity var8 = this.flutterActivity;
            if (var8 == null) {
               var2.error("Could not revert Flutter image", "Activity not initialized", null);
               return;
            }

            FlutterDeviceScreenshot.revertFlutterImage(var8);
            var2.success(null);
            return;
         case 3:
            Map var7 = var1.argument("results");
            testResultsSettable.set(var7);
            var2.success(null);
            return;
         default:
            var2.notImplemented();
      }
   }

   @Override
   public void onReattachedToActivityForConfigChanges(ActivityPluginBinding var1) {
      this.flutterActivity = var1.getActivity();
   }
}
