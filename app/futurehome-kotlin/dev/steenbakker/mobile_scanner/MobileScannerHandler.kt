package dev.steenbakker.mobile_scanner

import android.app.Activity
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Size
import androidx.camera.core.CameraSelector
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScannerOptions.Builder
import dev.steenbakker.mobile_scanner.objects.BarcodeFormats
import dev.steenbakker.mobile_scanner.objects.DetectionSpeed
import dev.steenbakker.mobile_scanner.objects.MobileScannerStartParameters
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.PluginRegistry
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.RequestPermissionsResultListener
import io.flutter.view.TextureRegistry
import java.io.File
import java.util.ArrayList
import java.util.Arrays

public class MobileScannerHandler(activity: Activity,
      barcodeHandler: BarcodeHandler,
      binaryMessenger: BinaryMessenger,
      permissions: MobileScannerPermissions,
      addPermissionListener: (RequestPermissionsResultListener) -> Unit,
      textureRegistry: TextureRegistry
   ) :
   MethodChannel.MethodCallHandler {
   private final val activity: Activity
   private final val barcodeHandler: BarcodeHandler
   private final val permissions: MobileScannerPermissions
   private final val addPermissionListener: (RequestPermissionsResultListener) -> Unit
   private final val analyzeImageErrorCallback: (String) -> Unit
   private final val analyzeImageSuccessCallback: (List<Map<String, Any?>>?) -> Unit
   private final var analyzerResult: Result?
   private final val callback: (List<Map<String, Any?>>, ByteArray?, Int?, Int?) -> Unit
   private final val errorCallback: (String) -> Unit
   private final var methodChannel: MethodChannel?
   private final var mobileScanner: MobileScanner?
   private final val torchStateCallback: (Int) -> Unit
   private final val zoomScaleStateCallback: (Double) -> Unit

   init {
      this.activity = var1;
      this.barcodeHandler = var2;
      this.permissions = var4;
      this.addPermissionListener = var5;
      this.analyzeImageErrorCallback = new MobileScannerHandler$$ExternalSyntheticLambda3(this);
      this.analyzeImageSuccessCallback = new MobileScannerHandler$$ExternalSyntheticLambda4(this);
      val var7: MobileScannerHandler$$ExternalSyntheticLambda5 = new MobileScannerHandler$$ExternalSyntheticLambda5(this);
      this.callback = var7;
      val var9: MobileScannerHandler$$ExternalSyntheticLambda6 = new MobileScannerHandler$$ExternalSyntheticLambda6(this);
      this.errorCallback = var9;
      this.torchStateCallback = new MobileScannerHandler$$ExternalSyntheticLambda7(this);
      this.zoomScaleStateCallback = new MobileScannerHandler$$ExternalSyntheticLambda8(this);
      val var8: MethodChannel = new MethodChannel(var3, "dev.steenbakker.mobile_scanner/scanner/method");
      this.methodChannel = var8;
      var8.setMethodCallHandler(this);
      this.mobileScanner = new MobileScanner(var1, var6, var7, var9, null, 16, null);
   }

   private fun analyzeImage(call: MethodCall, result: Result) {
      this.analyzerResult = var2;
      val var6: java.util.List = var1.argument("formats");
      var var4: MobileScanner = var1.argument("filePath");
      val var3: java.lang.String = var4 as java.lang.String;
      var4 = this.mobileScanner;
      val var7: Uri = Uri.fromFile(new File(var3));
      var4.analyzeImage(var7, this.buildBarcodeScannerOptions(var6), this.analyzeImageSuccessCallback, this.analyzeImageErrorCallback);
   }

   @JvmStatic
   fun `analyzeImageErrorCallback$lambda$1`(var0: MobileScannerHandler, var1: java.lang.String): Unit {
      new Handler(Looper.getMainLooper()).post(new MobileScannerHandler$$ExternalSyntheticLambda1(var0, var1));
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `analyzeImageErrorCallback$lambda$1$lambda$0`(var0: MobileScannerHandler, var1: java.lang.String) {
      if (var0.analyzerResult != null) {
         var0.analyzerResult.error("MOBILE_SCANNER_BARCODE_ERROR", var1, null);
      }

      var0.analyzerResult = null;
   }

   @JvmStatic
   fun `analyzeImageSuccessCallback$lambda$3`(var0: MobileScannerHandler, var1: java.util.List): Unit {
      new Handler(Looper.getMainLooper()).post(new MobileScannerHandler$$ExternalSyntheticLambda0(var0, var1));
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `analyzeImageSuccessCallback$lambda$3$lambda$2`(var0: MobileScannerHandler, var1: java.util.List) {
      if (var0.analyzerResult != null) {
         var0.analyzerResult.success(MapsKt.mapOf(new Pair[]{TuplesKt.to("name", "barcode"), TuplesKt.to("data", var1)}));
      }

      var0.analyzerResult = null;
   }

   private fun buildBarcodeScannerOptions(formats: List<Int>?): BarcodeScannerOptions? {
      if (var1 == null) {
         return null;
      } else {
         val var3: java.util.List = new ArrayList();
         val var4: java.util.Iterator = var1.iterator();

         while (var4.hasNext()) {
            var3.add(BarcodeFormats.Companion.fromRawValue((var4.next() as java.lang.Number).intValue()).getIntValue());
         }

         if (var3.size() == 1) {
            return new Builder().setBarcodeFormats(CollectionsKt.<java.lang.Number>first(var3).intValue(), new int[0]).build();
         } else {
            val var5: Builder = new Builder();
            val var6: Int = CollectionsKt.<java.lang.Number>first(var3).intValue();
            val var7: IntArray = CollectionsKt.toIntArray(var3.subList(1, var3.size()));
            return var5.setBarcodeFormats(var6, Arrays.copyOf(var7, var7.length)).build();
         }
      }
   }

   @JvmStatic
   fun `callback$lambda$4`(var0: MobileScannerHandler, var1: java.util.List, var2: ByteArray, var3: Int, var4: Int): Unit {
      val var5: BarcodeHandler = var0.barcodeHandler;
      val var6: Pair = TuplesKt.to("name", "barcode");
      val var7: Pair = TuplesKt.to("data", var1);
      val var11: Pair = TuplesKt.to("bytes", var2);
      val var8: java.lang.Double;
      if (var3 != null) {
         var8 = (double)var3.intValue();
      } else {
         var8 = null;
      }

      val var12: Pair = TuplesKt.to("width", var8);
      var var9: java.lang.Double = null;
      if (var4 != null) {
         var9 = (double)var4.intValue();
      }

      var5.publishEvent(MapsKt.mapOf(new Pair[]{var6, var7, TuplesKt.to("image", MapsKt.mapOf(new Pair[]{var11, var12, TuplesKt.to("height", var9)}))}));
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `errorCallback$lambda$5`(var0: MobileScannerHandler, var1: java.lang.String): Unit {
      var0.barcodeHandler.publishError("MOBILE_SCANNER_BARCODE_ERROR", var1, null);
      return Unit.INSTANCE;
   }

   private fun resetScale(result: Result) {
      try {
         val var2: MobileScanner = this.mobileScanner;
         var2.resetScale();
         var1.success(null);
      } catch (var3: ZoomWhenStopped) {
         var1.error("MOBILE_SCANNER_SET_SCALE_WHEN_STOPPED_ERROR", "The zoom scale cannot be changed when the camera is stopped.", null);
      }
   }

   private fun setScale(call: MethodCall, result: Result) {
      try {
         val var3: MobileScanner = this.mobileScanner;
         val var6: Any = var1.arguments;
         var3.setScale(var6 as java.lang.Double);
         var2.success(null);
      } catch (var4: ZoomWhenStopped) {
         var2.error("MOBILE_SCANNER_SET_SCALE_WHEN_STOPPED_ERROR", "The zoom scale cannot be changed when the camera is stopped.", null);
      } catch (var5: ZoomNotInRange) {
         var2.error("MOBILE_SCANNER_GENERIC_ERROR", "The zoom scale should be between 0 and 1 (both inclusive)", null);
      }
   }

   private fun start(call: MethodCall, result: Result) {
      var var9: java.lang.Boolean = var1.argument("torch");
      val var6: Boolean;
      if (var9 != null) {
         var6 = var9;
      } else {
         var6 = false;
      }

      val var15: Int = var1.argument("facing");
      val var3: Int;
      if (var15 != null) {
         var3 = var15;
      } else {
         var3 = 0;
      }

      val var10: java.util.List = var1.argument("formats");
      var9 = var1.argument("returnImage");
      val var7: Boolean;
      if (var9 != null) {
         var7 = var9;
      } else {
         var7 = false;
      }

      val var17: Int = var1.argument("speed");
      val var4: Int;
      if (var17 != null) {
         var4 = var17;
      } else {
         var4 = 1;
      }

      val var18: Int = var1.argument("timeout");
      val var5: Int;
      if (var18 != null) {
         var5 = var18;
      } else {
         var5 = 250;
      }

      val var19: java.util.List = var1.argument("cameraResolution");
      val var13: java.lang.Boolean = var1.argument("useNewCameraSelector");
      val var8: Boolean;
      if (var13 != null) {
         var8 = var13;
      } else {
         var8 = false;
      }

      val var20: Size;
      if (var19 != null) {
         var20 = new Size((var19.get(0) as java.lang.Number).intValue(), (var19.get(1) as java.lang.Number).intValue());
      } else {
         var20 = null;
      }

      val var11: BarcodeScannerOptions = this.buildBarcodeScannerOptions(var10);
      val var21: CameraSelector;
      if (var3 == 0) {
         var21 = CameraSelector.DEFAULT_FRONT_CAMERA;
      } else {
         var21 = CameraSelector.DEFAULT_BACK_CAMERA;
      }

      val var14: DetectionSpeed;
      if (var4 != 0) {
         if (var4 != 1) {
            var14 = DetectionSpeed.UNRESTRICTED;
         } else {
            var14 = DetectionSpeed.NORMAL;
         }
      } else {
         var14 = DetectionSpeed.NO_DUPLICATES;
      }

      val var12: MobileScanner = this.mobileScanner;
      var12.start(
         var11,
         var7,
         var21,
         var6,
         var14,
         this.torchStateCallback,
         this.zoomScaleStateCallback,
         new MobileScannerHandler$$ExternalSyntheticLambda9(var2),
         new MobileScannerHandler$$ExternalSyntheticLambda10(var2),
         (long)var5,
         var20,
         var8
      );
   }

   @JvmStatic
   fun `start$lambda$11`(var0: MethodChannel.Result, var1: Exception): Unit {
      new Handler(Looper.getMainLooper()).post(new MobileScannerHandler$$ExternalSyntheticLambda2(var1, var0));
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `start$lambda$11$lambda$10`(var0: Exception, var1: MethodChannel.Result) {
      if (var0 is AlreadyStarted) {
         var1.error("MOBILE_SCANNER_ALREADY_STARTED_ERROR", "The scanner was already started.", null);
      } else if (var0 is CameraError) {
         var1.error("MOBILE_SCANNER_CAMERA_ERROR", "An error occurred when opening the camera.", null);
      } else if (var0 is NoCamera) {
         var1.error("MOBILE_SCANNER_NO_CAMERA_ERROR", "No cameras available.", null);
      } else {
         var1.error("MOBILE_SCANNER_GENERIC_ERROR", "An unknown error occurred.", null);
      }
   }

   @JvmStatic
   fun `start$lambda$9`(var0: MethodChannel.Result, var1: MobileScannerStartParameters): Unit {
      new Handler(Looper.getMainLooper()).post(new MobileScannerHandler$$ExternalSyntheticLambda11(var0, var1));
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `start$lambda$9$lambda$8`(var0: MethodChannel.Result, var1: MobileScannerStartParameters) {
      var0.success(
         MapsKt.mapOf(
            new Pair[]{
               TuplesKt.to("textureId", var1.getId()),
               TuplesKt.to("size", MapsKt.mapOf(new Pair[]{TuplesKt.to("width", var1.getWidth()), TuplesKt.to("height", var1.getHeight())})),
               TuplesKt.to("currentTorchState", var1.getCurrentTorchState()),
               TuplesKt.to("numberOfCameras", var1.getNumberOfCameras())
            }
         )
      );
   }

   private fun stop(result: Result) {
      try {
         val var2: MobileScanner = this.mobileScanner;
         var2.stop();
         var1.success(null);
      } catch (var3: AlreadyStopped) {
         var1.success(null);
      }
   }

   private fun toggleTorch(result: Result) {
      if (this.mobileScanner != null) {
         this.mobileScanner.toggleTorch();
      }

      var1.success(null);
   }

   @JvmStatic
   fun `torchStateCallback$lambda$6`(var0: MobileScannerHandler, var1: Int): Unit {
      var0.barcodeHandler.publishEvent(MapsKt.mapOf(new Pair[]{TuplesKt.to("name", "torchState"), TuplesKt.to("data", var1)}));
      return Unit.INSTANCE;
   }

   private fun updateScanWindow(call: MethodCall, result: Result) {
      if (this.mobileScanner != null) {
         this.mobileScanner.setScanWindow(var1.argument("rect"));
      }

      var2.success(null);
   }

   @JvmStatic
   fun `zoomScaleStateCallback$lambda$7`(var0: MobileScannerHandler, var1: Double): Unit {
      var0.barcodeHandler.publishEvent(MapsKt.mapOf(new Pair[]{TuplesKt.to("name", "zoomScaleState"), TuplesKt.to("data", var1)}));
      return Unit.INSTANCE;
   }

   public fun dispose(activityPluginBinding: ActivityPluginBinding) {
      if (this.methodChannel != null) {
         this.methodChannel.setMethodCallHandler(null);
      }

      this.methodChannel = null;
      if (this.mobileScanner != null) {
         this.mobileScanner.dispose();
      }

      this.mobileScanner = null;
      val var4: PluginRegistry.RequestPermissionsResultListener = this.permissions.getPermissionListener();
      if (var4 != null) {
         var1.removeRequestPermissionsResultListener(var4);
      }
   }

   public override fun onMethodCall(call: MethodCall, result: Result) {
      val var3: java.lang.String = var1.method;
      if (var1.method != null) {
         switch (var1.method.hashCode()) {
            case -1688013509:
               if (var3.equals("resetScale")) {
                  this.resetScale(var2);
                  return;
               }
               break;
            case -655811320:
               if (var3.equals("toggleTorch")) {
                  this.toggleTorch(var2);
                  return;
               }
               break;
            case 3540994:
               if (var3.equals("stop")) {
                  this.stop(var2);
                  return;
               }
               break;
            case 16698223:
               if (var3.equals("analyzeImage")) {
                  this.analyzeImage(var1, var2);
                  return;
               }
               break;
            case 109757538:
               if (var3.equals("start")) {
                  this.start(var1, var2);
                  return;
               }
               break;
            case 109757585:
               if (var3.equals("state")) {
                  var2.success(this.permissions.hasCameraPermission(this.activity));
                  return;
               }
               break;
            case 1095692943:
               if (var3.equals("request")) {
                  this.permissions
                     .requestPermission(
                        this.activity,
                        this.addPermissionListener,
                        new MobileScannerPermissions.ResultCallback(var2) {
                           final MethodChannel.Result $result;

                           {
                              this.$result = var1;
                           }

                           @Override
                           public void onResult(java.lang.String var1) {
                              if (var1 == null) {
                                 this.$result.success(true);
                              } else if (var1 == "MOBILE_SCANNER_CAMERA_PERMISSION_DENIED") {
                                 this.$result.success(false);
                              } else if (var1 == "MOBILE_SCANNER_CAMERA_PERMISSION_REQUEST_PENDING") {
                                 this.$result
                                    .error(
                                       "MOBILE_SCANNER_CAMERA_PERMISSION_REQUEST_PENDING",
                                       "Another request is ongoing and multiple requests cannot be handled at once.",
                                       null
                                    );
                              } else {
                                 this.$result.error("MOBILE_SCANNER_GENERIC_ERROR", "An unknown error occurred.", null);
                              }
                           }
                        }
                     );
                  return;
               }
               break;
            case 1403963912:
               if (var3.equals("setScale")) {
                  this.setScale(var1, var2);
                  return;
               }
               break;
            case 2023844470:
               if (var3.equals("updateScanWindow")) {
                  this.updateScanWindow(var1, var2);
                  return;
               }
            default:
         }
      }

      var2.notImplemented();
   }
}
