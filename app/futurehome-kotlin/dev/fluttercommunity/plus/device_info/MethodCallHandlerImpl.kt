package dev.fluttercommunity.plus.device_info

import android.app.ActivityManager
import android.content.ContentResolver
import android.content.pm.FeatureInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION
import android.provider.Settings.Global
import com.baseflow.geocoding.Geocoding..ExternalSyntheticApiModelOutline0
import dev.fluttercommunity.plus.share.Share$$ExternalSyntheticApiModelOutline0
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.Result
import java.util.ArrayList
import java.util.Arrays
import java.util.HashMap

internal class MethodCallHandlerImpl(packageManager: PackageManager, activityManager: ActivityManager, contentResolver: ContentResolver) :
   MethodChannel.MethodCallHandler {
   private final val packageManager: PackageManager
   private final val activityManager: ActivityManager
   private final val contentResolver: ContentResolver

   private final val isEmulator: Boolean
      private final get() {
         var var2: java.lang.String = Build.BRAND;
         if (StringsKt.startsWith$default(var2, "generic", false, 2, null)) {
            var2 = Build.DEVICE;
            if (StringsKt.startsWith$default(var2, "generic", false, 2, null)) {
               return true;
            }
         }

         var2 = Build.FINGERPRINT;
         if (!StringsKt.startsWith$default(var2, "generic", false, 2, null)) {
            var2 = Build.FINGERPRINT;
            if (!StringsKt.startsWith$default(var2, "unknown", false, 2, null)) {
               var2 = Build.HARDWARE;
               if (!StringsKt.contains$default(var2, "goldfish", false, 2, null)) {
                  var2 = Build.HARDWARE;
                  if (!StringsKt.contains$default(var2, "ranchu", false, 2, null)) {
                     var2 = Build.MODEL;
                     if (!StringsKt.contains$default(var2, "google_sdk", false, 2, null)) {
                        var2 = Build.MODEL;
                        if (!StringsKt.contains$default(var2, "Emulator", false, 2, null)) {
                           var2 = Build.MODEL;
                           if (!StringsKt.contains$default(var2, "Android SDK built for x86", false, 2, null)) {
                              var2 = Build.MANUFACTURER;
                              if (!StringsKt.contains$default(var2, "Genymotion", false, 2, null)) {
                                 var2 = Build.PRODUCT;
                                 if (!StringsKt.contains$default(var2, "sdk", false, 2, null)) {
                                    var2 = Build.PRODUCT;
                                    if (!StringsKt.contains$default(var2, "vbox86p", false, 2, null)) {
                                       var2 = Build.PRODUCT;
                                       if (!StringsKt.contains$default(var2, "emulator", false, 2, null)) {
                                          var2 = Build.PRODUCT;
                                          if (!StringsKt.contains$default(var2, "simulator", false, 2, null)) {
                                             return false;
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         return true;
      }


   init {
      this.packageManager = var1;
      this.activityManager = var2;
      this.contentResolver = var3;
   }

   private fun getSystemFeatures(): List<String> {
      val var4: Array<FeatureInfo> = this.packageManager.getSystemAvailableFeatures();
      val var5: java.util.Collection = new ArrayList();
      val var2: Int = var4.length;

      for (int var1 = 0; var1 < var2; var1++) {
         val var3: FeatureInfo = var4[var1];
         if (var4[var1].name != null) {
            var5.add(var3);
         }
      }

      val var6: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var5 as java.util.List, 10));
      val var8: java.util.Iterator = (var5 as java.util.List).iterator();

      while (var8.hasNext()) {
         var6.add((var8.next() as FeatureInfo).name);
      }

      return var6 as MutableList<java.lang.String>;
   }

   public override fun onMethodCall(call: MethodCall, result: Result) {
      if (var1.method.equals("getDeviceInfo")) {
         val var3: java.util.Map = new HashMap();
         var3.put("board", Build.BOARD);
         var3.put("bootloader", Build.BOOTLOADER);
         var3.put("brand", Build.BRAND);
         var3.put("device", Build.DEVICE);
         var3.put("display", Build.DISPLAY);
         var3.put("fingerprint", Build.FINGERPRINT);
         var3.put("hardware", Build.HARDWARE);
         var3.put("host", Build.HOST);
         var3.put("id", Build.ID);
         var3.put("manufacturer", Build.MANUFACTURER);
         var3.put("model", Build.MODEL);
         var3.put("product", Build.PRODUCT);
         if (VERSION.SDK_INT >= 25) {
            var3.put("name", Global.getString(this.contentResolver, "device_name"));
         }

         var3.put("supported32BitAbis", CollectionsKt.listOf(Arrays.copyOf(Build.SUPPORTED_32_BIT_ABIS, Build.SUPPORTED_32_BIT_ABIS.length)));
         var3.put("supported64BitAbis", CollectionsKt.listOf(Arrays.copyOf(Build.SUPPORTED_64_BIT_ABIS, Build.SUPPORTED_64_BIT_ABIS.length)));
         var3.put("supportedAbis", CollectionsKt.listOf(Arrays.copyOf(Build.SUPPORTED_ABIS, Build.SUPPORTED_ABIS.length)));
         var3.put("tags", Build.TAGS);
         var3.put("type", Build.TYPE);
         var3.put("isPhysicalDevice", this.isEmulator() xor true);
         var3.put("systemFeatures", this.getSystemFeatures());
         val var8: java.util.Map = new HashMap();
         if (VERSION.SDK_INT >= 23) {
            var8.put("baseOS", ExternalSyntheticApiModelOutline0.m());
            var8.put("previewSdkInt", ExternalSyntheticApiModelOutline0.m());
            var8.put("securityPatch", Share$$ExternalSyntheticApiModelOutline0.m());
         }

         var8.put("codename", VERSION.CODENAME);
         var8.put("incremental", VERSION.INCREMENTAL);
         var8.put("release", VERSION.RELEASE);
         var8.put("sdkInt", VERSION.SDK_INT);
         var3.put("version", var8);
         var3.put("isLowRamDevice", this.activityManager.isLowRamDevice());
         if (VERSION.SDK_INT >= 26) {
            try {
               var9 = Share$$ExternalSyntheticApiModelOutline0.m$1();
            } catch (var4: SecurityException) {
               var9 = "unknown";
            }

            var3.put("serialNumber", var9);
         } else {
            var3.put("serialNumber", Build.SERIAL);
         }

         var2.success(var3);
      } else {
         var2.notImplemented();
      }
   }
}
