package dev.fluttercommunity.plus.packageinfo

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.content.pm.PackageManager.NameNotFoundException
import android.os.Build.VERSION
import dev.fluttercommunity.plus.share.Share$$ExternalSyntheticApiModelOutline0
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.Result
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.HashMap

public class PackageInfoPlugin : MethodChannel.MethodCallHandler, FlutterPlugin {
   private final var applicationContext: Context?
   private final var methodChannel: MethodChannel?

   private fun bytesToHex(bytes: ByteArray): String {
      val var7: CharArray = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
      val var6: CharArray = new char[var1.length * 2];
      val var3: Int = var1.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Byte = var1[var2];
         val var5: Int = var2 * 2;
         var6[var2 * 2] = var7[(var4 and 255) ushr 4];
         var6[var5 + 1] = var7[var4 and 15];
      }

      return new java.lang.String(var6);
   }

   private fun getBuildSignature(pm: PackageManager): String? {
      val var3: Any = null;

      var var2: Int;
      try {
         var2 = VERSION.SDK_INT;
      } catch (NoSuchAlgorithmException | var12: NameNotFoundException) {
         return null;
      }

      var var13: java.lang.String;
      if (var2 >= 28) {
         try {
            val var4: Context = this.applicationContext;
            var14 = Share$$ExternalSyntheticApiModelOutline0.m(var1.getPackageInfo(var4.getPackageName(), 134217728));
         } catch (NoSuchAlgorithmException | var11: NameNotFoundException) {
            return null;
         }

         if (var14 == null) {
            return null;
         }

         try {
            if (Share$$ExternalSyntheticApiModelOutline0.m(var14)) {
               val var17: Array<Signature> = Share$$ExternalSyntheticApiModelOutline0.m(var14);
               val var18: ByteArray = ArraysKt.first(var17).toByteArray();
               return this.signatureToSha256(var18);
            }
         } catch (NoSuchAlgorithmException | var10: NameNotFoundException) {
            return null;
         }

         try {
            val var15: Array<Signature> = Share$$ExternalSyntheticApiModelOutline0.m$1(var14);
            val var16: ByteArray = ArraysKt.first(var15).toByteArray();
            var13 = this.signatureToSha256(var16);
         } catch (NoSuchAlgorithmException | var6: NameNotFoundException) {
            var13 = null;
         }
      } else {
         var var21: Array<Signature>;
         try {
            val var20: Context = this.applicationContext;
            var21 = var1.getPackageInfo(var20.getPackageName(), 64).signatures;
         } catch (NoSuchAlgorithmException | var9: NameNotFoundException) {
            return null;
         }

         var13 = null;
         if (var21 != null) {
            label100: {
               try {
                  if (var21.length == 0) {
                     break label100;
                  }
               } catch (NoSuchAlgorithmException | var8: NameNotFoundException) {
                  return null;
               }

               try {
                  if (ArraysKt.first(var21) == null) {
                     return (java.lang.String)var3;
                  }
               } catch (NoSuchAlgorithmException | var7: NameNotFoundException) {
                  return null;
               }

               try {
                  val var19: ByteArray = ArraysKt.first(var21).toByteArray();
                  var23 = this.signatureToSha256(var19);
               } catch (NoSuchAlgorithmException | var5: NameNotFoundException) {
                  var23 = null;
               }

               return var23;
            }

            var13 = null;
         }
      }

      return var13;
   }

   private fun getInstallerPackageName(): String? {
      val var1: Context = this.applicationContext;
      val var3: PackageManager = var1.getPackageManager();
      val var2: Context = this.applicationContext;
      val var5: java.lang.String = var2.getPackageName();
      val var4: java.lang.String;
      if (VERSION.SDK_INT >= 30) {
         var4 = Share$$ExternalSyntheticApiModelOutline0.m(Share$$ExternalSyntheticApiModelOutline0.m(var3, var5));
      } else {
         var4 = var3.getInstallerPackageName(var5);
      }

      return var4;
   }

   private fun getLongVersionCode(info: PackageInfo): Long {
      val var2: Long;
      if (VERSION.SDK_INT >= 28) {
         var2 = Share$$ExternalSyntheticApiModelOutline0.m(var1);
      } else {
         var2 = var1.versionCode;
      }

      return var2;
   }

   @Throws(java/security/NoSuchAlgorithmException::class)
   private fun signatureToSha256(sig: ByteArray): String {
      val var2: MessageDigest = MessageDigest.getInstance("SHA-256");
      var2.update(var1);
      var1 = var2.digest();
      return this.bytesToHex(var1);
   }

   public override fun onAttachedToEngine(binding: FlutterPluginBinding) {
      this.applicationContext = var1.getApplicationContext();
      val var2: MethodChannel = new MethodChannel(var1.getBinaryMessenger(), "dev.fluttercommunity.plus/package_info");
      this.methodChannel = var2;
      var2.setMethodCallHandler(this);
   }

   public override fun onDetachedFromEngine(binding: FlutterPluginBinding) {
      this.applicationContext = null;
      val var2: MethodChannel = this.methodChannel;
      var2.setMethodCallHandler(null);
      this.methodChannel = null;
   }

   public override fun onMethodCall(call: MethodCall, result: Result) {
      label102: {
         var var4: ApplicationInfo;
         var var5: PackageInfo;
         var var6: java.lang.String;
         var var7: HashMap;
         var var8: java.lang.String;
         try {
            if (!(var1.method == "getAll")) {
               break label102;
            }

            val var18: Context = this.applicationContext;
            var19 = var18.getPackageManager();
            val var3: Context = this.applicationContext;
            var5 = var19.getPackageInfo(var3.getPackageName(), 0);
            var8 = this.getBuildSignature(var19);
            var6 = this.getInstallerPackageName();
            var7 = new HashMap();
            var4 = var5.applicationInfo;
         } catch (var17: NameNotFoundException) {
            var2.error("Name not found", var17.getMessage(), null);
            return;
         }

         label72: {
            if (var4 != null) {
               try {
                  var20 = var4.loadLabel(var19);
               } catch (var16: NameNotFoundException) {
                  var2.error("Name not found", var16.getMessage(), null);
                  return;
               }

               if (var20 != null) {
                  try {
                     var25 = var20.toString();
                  } catch (var15: NameNotFoundException) {
                     var2.error("Name not found", var15.getMessage(), null);
                     return;
                  }

                  var21 = var25;
                  if (var25 != null) {
                     break label72;
                  }
               }
            }

            var21 = "";
         }

         try {
            var7.put("appName", var21);
            val var22: Context = this.applicationContext;
            var7.put("packageName", var22.getPackageName());
            var23 = var5.versionName;
         } catch (var14: NameNotFoundException) {
            var2.error("Name not found", var14.getMessage(), null);
            return;
         }

         if (var23 == null) {
            var23 = "";
         }

         try {
            var7.put("version", var23);
            var7.put("buildNumber", java.lang.String.valueOf(this.getLongVersionCode(var5)));
         } catch (var13: NameNotFoundException) {
            var2.error("Name not found", var13.getMessage(), null);
            return;
         }

         if (var8 != null) {
            try {
               var7.put("buildSignature", var8);
            } catch (var12: NameNotFoundException) {
               var2.error("Name not found", var12.getMessage(), null);
               return;
            }
         }

         if (var6 != null) {
            try {
               var7.put("installerStore", var6);
            } catch (var11: NameNotFoundException) {
               var2.error("Name not found", var11.getMessage(), null);
               return;
            }
         }

         try {
            var2.success(var7);
         } catch (var10: NameNotFoundException) {
            var2.error("Name not found", var10.getMessage(), null);
         }

         return;
      }

      try {
         var2.notImplemented();
      } catch (var9: NameNotFoundException) {
         var2.error("Name not found", var9.getMessage(), null);
      }
   }

   public companion object {
      private const val CHANNEL_NAME: String
   }
}
