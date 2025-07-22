package com.spencerccf.app_settings

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.Result
import kotlin.jvm.internal.Intrinsics

public class AppSettingsPlugin : FlutterPlugin, MethodChannel.MethodCallHandler, ActivityAware {
   private final var activity: Activity?
   private final lateinit var channel: MethodChannel

   private fun handleOpenSettings(call: MethodCall, result: Result) {
      val var4: java.lang.Boolean = var1.argument("asAnotherTask");
      val var3: Boolean;
      if (var4 != null) {
         var3 = var4;
      } else {
         var3 = false;
      }

      val var5: java.lang.String = var1.argument("type");
      if (var5 != null) {
         switch (var5.hashCode()) {
            case -2045253606:
               if (var5.equals("batteryOptimization")) {
                  this.openBatteryOptimizationSettings(var2, var3);
                  return;
               }
               break;
            case -1928150741:
               if (var5.equals("generalSettings")) {
                  this.openSettings("android.settings.SETTINGS", var2, var3);
                  return;
               }
               break;
            case -1335157162:
               if (var5.equals("device")) {
                  this.openSettings("android.settings.DEVICE_INFO_SETTINGS", var2, var3);
                  return;
               }
               break;
            case -1000044642:
               if (var5.equals("wireless")) {
                  this.openSettings("android.settings.WIRELESS_SETTINGS", var2, var3);
                  return;
               }
               break;
            case -213139122:
               if (var5.equals("accessibility")) {
                  this.openSettings("android.settings.ACCESSIBILITY_SETTINGS", var2, var3);
                  return;
               }
               break;
            case -114233073:
               if (var5.equals("dataRoaming")) {
                  this.openSettings("android.settings.DATA_ROAMING_SETTINGS", var2, var3);
                  return;
               }
               break;
            case -80681014:
               if (var5.equals("developer")) {
                  this.openSettings("android.settings.APPLICATION_DEVELOPMENT_SETTINGS", var2, var3);
                  return;
               }
               break;
            case 96799:
               if (var5.equals("apn")) {
                  this.openSettings("android.settings.APN_SETTINGS", var2, var3);
                  return;
               }
               break;
            case 108971:
               if (var5.equals("nfc")) {
                  this.openSettings("android.settings.NFC_SETTINGS", var2, var3);
                  return;
               }
               break;
            case 116980:
               if (var5.equals("vpn")) {
                  this.openVpnSettings(var2, var3);
                  return;
               }
               break;
            case 3076014:
               if (var5.equals("date")) {
                  this.openSettings("android.settings.DATE_SETTINGS", var2, var3);
                  return;
               }
               break;
            case 3649301:
               if (var5.equals("wifi")) {
                  this.openSettings("android.settings.WIFI_SETTINGS", var2, var3);
                  return;
               }
               break;
            case 92895825:
               if (var5.equals("alarm")) {
                  this.openAlarmSettings(var2, var3);
                  return;
               }
               break;
            case 109627663:
               if (var5.equals("sound")) {
                  this.openSettings("android.settings.SOUND_SETTINGS", var2, var3);
                  return;
               }
               break;
            case 595233003:
               if (var5.equals("notification")) {
                  this.openNotificationSettings(var2, var3);
                  return;
               }
               break;
            case 949122880:
               if (var5.equals("security")) {
                  this.openSettings("android.settings.SECURITY_SETTINGS", var2, var3);
                  return;
               }
               break;
            case 1039955198:
               if (var5.equals("internalStorage")) {
                  this.openSettings("android.settings.INTERNAL_STORAGE_SETTINGS", var2, var3);
                  return;
               }
               break;
            case 1099603663:
               if (var5.equals("hotspot")) {
                  this.openHotspotSettings(var2, var3);
                  return;
               }
               break;
            case 1214667623:
               if (var5.equals("lockAndPassword")) {
                  this.openSettings("android.app.action.SET_NEW_PASSWORD", var2, var3);
                  return;
               }
               break;
            case 1294374875:
               if (var5.equals("appLocale")) {
                  this.openAppLocaleSettings(var2, var3);
                  return;
               }
               break;
            case 1434631203:
               if (var5.equals("settings")) {
                  this.openAppSettings(var2, var3);
                  return;
               }
               break;
            case 1671764162:
               if (var5.equals("display")) {
                  this.openSettings("android.settings.DISPLAY_SETTINGS", var2, var3);
                  return;
               }
               break;
            case 1901043637:
               if (var5.equals("location")) {
                  this.openSettings("android.settings.LOCATION_SOURCE_SETTINGS", var2, var3);
                  return;
               }
               break;
            case 1968882350:
               if (var5.equals("bluetooth")) {
                  this.openSettings("android.settings.BLUETOOTH_SETTINGS", var2, var3);
                  return;
               }
            default:
         }
      }

      var2.notImplemented();
   }

   private fun handleOpenSettingsPanel(call: MethodCall, result: Result) {
      if (VERSION.SDK_INT < 29) {
         var2.success(null);
      } else {
         val var3: Activity = this.activity;
         if (this.activity != null) {
            val var4: java.lang.String = var1.argument("type");
            if (var4 != null) {
               switch (var4.hashCode()) {
                  case -810883302:
                     if (var4.equals("volume")) {
                        var3.startActivity(new Intent("android.settings.panel.action.VOLUME"));
                        var2.success(null);
                        return;
                     }
                     break;
                  case 108971:
                     if (var4.equals("nfc")) {
                        var3.startActivity(new Intent("android.settings.panel.action.NFC"));
                        var2.success(null);
                        return;
                     }
                     break;
                  case 3649301:
                     if (var4.equals("wifi")) {
                        var3.startActivity(new Intent("android.settings.panel.action.WIFI"));
                        var2.success(null);
                        return;
                     }
                     break;
                  case 21015448:
                     if (var4.equals("internetConnectivity")) {
                        var3.startActivity(new Intent("android.settings.panel.action.INTERNET_CONNECTIVITY"));
                        var2.success(null);
                        return;
                     }
                  default:
               }
            }

            var2.notImplemented();
         } else {
            val var5: AppSettingsPlugin = this;
            var2.success(null);
         }
      }
   }

   private fun openAlarmSettings(result: Result, asAnotherTask: Boolean = false) {
      if (VERSION.SDK_INT >= 31) {
         val var4: Uri;
         if (this.activity != null) {
            var4 = Uri.fromParts("package", this.activity.getPackageName(), null);
         } else {
            var4 = null;
         }

         if (var4 == null) {
            var1.success(null);
            return;
         }

         this.openSettingsWithIntent(new Intent("android.settings.REQUEST_SCHEDULE_EXACT_ALARM", var4), var1, var2);
      } else {
         this.openAppSettings(var1, var2);
      }
   }

   private fun openAppLocaleSettings(result: Result, asAnotherTask: Boolean = false) {
      if (VERSION.SDK_INT < 33) {
         var1.success(null);
      } else {
         val var3: Intent = new Intent("android.settings.APP_LOCALE_SETTINGS");
         if (var2) {
            var3.addFlags(268435456);
         }

         val var4: Activity = this.activity;
         if (this.activity != null) {
            var3.setData(Uri.fromParts("package", this.activity.getPackageName(), null));
            var4.startActivity(var3);
         }

         var1.success(null);
      }
   }

   private fun openAppSettings(result: Result, asAnotherTask: Boolean = false) {
      val var3: Intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
      if (var2) {
         var3.addFlags(268435456);
      }

      val var4: Activity = this.activity;
      if (this.activity != null) {
         var3.setData(Uri.fromParts("package", this.activity.getPackageName(), null));
         var4.startActivity(var3);
      }

      var1.success(null);
   }

   private fun openBatteryOptimizationSettings(result: Result, asAnotherTask: Boolean = false) {
      if (VERSION.SDK_INT >= 23) {
         this.openSettings("android.settings.IGNORE_BATTERY_OPTIMIZATION_SETTINGS", var1, var2);
      } else {
         this.openAppSettings(var1, var2);
      }
   }

   private fun openHotspotSettings(result: Result, asAnotherTask: Boolean) {
      val var3: Intent = new Intent().setClassName("com.android.settings", "com.android.settings.TetherSettings");
      this.openSettingsWithIntent(var3, var1, var2);
   }

   private fun openNotificationSettings(result: Result, asAnotherTask: Boolean) {
      if (VERSION.SDK_INT >= 26) {
         val var3: Activity = this.activity;
         if (this.activity != null) {
            val var4: Intent = new Intent("android.settings.APP_NOTIFICATION_SETTINGS").putExtra("android.provider.extra.APP_PACKAGE", var3.getPackageName());
            if (var2) {
               var4.addFlags(268435456);
            }

            var3.startActivity(var4);
         }

         var1.success(null);
      } else {
         this.openAppSettings(var1, var2);
      }
   }

   private fun openSettings(url: String, result: Result, asAnotherTask: Boolean = false) {
      var var4: Intent;
      try {
         var4 = new Intent(var1);
      } catch (var9: Exception) {
         this.openAppSettings(var2, var3);
         return;
      }

      if (var3) {
         try {
            var4.addFlags(268435456);
         } catch (var8: Exception) {
            this.openAppSettings(var2, var3);
            return;
         }
      }

      try {
         var10 = this.activity;
      } catch (var7: Exception) {
         this.openAppSettings(var2, var3);
         return;
      }

      if (var10 != null) {
         try {
            var10.startActivity(var4);
         } catch (var6: Exception) {
            this.openAppSettings(var2, var3);
            return;
         }
      }

      try {
         var2.success(null);
      } catch (var5: Exception) {
         this.openAppSettings(var2, var3);
      }
   }

   private fun openSettingsWithIntent(intent: Intent, result: Result, asAnotherTask: Boolean = false) {
      if (var3) {
         try {
            var1.addFlags(268435456);
         } catch (var8: Exception) {
            this.openAppSettings(var2, var3);
            return;
         }
      }

      var var4: Activity;
      try {
         var4 = this.activity;
      } catch (var7: Exception) {
         this.openAppSettings(var2, var3);
         return;
      }

      if (var4 != null) {
         try {
            var4.startActivity(var1);
         } catch (var6: Exception) {
            this.openAppSettings(var2, var3);
            return;
         }
      }

      try {
         var2.success(null);
      } catch (var5: Exception) {
         this.openAppSettings(var2, var3);
      }
   }

   private fun openVpnSettings(result: Result, asAnotherTask: Boolean) {
      if (VERSION.SDK_INT >= 24) {
         this.openSettings("android.settings.VPN_SETTINGS", var1, var2);
      } else {
         this.openSettings("android.net.vpn.SETTINGS", var1, var2);
      }
   }

   public override fun onAttachedToActivity(binding: ActivityPluginBinding) {
      this.activity = var1.getActivity();
   }

   public override fun onAttachedToEngine(flutterPluginBinding: FlutterPluginBinding) {
      val var2: MethodChannel = new MethodChannel(var1.getBinaryMessenger(), "com.spencerccf.app_settings/methods");
      this.channel = var2;
      var2.setMethodCallHandler(this);
   }

   public override fun onDetachedFromActivity() {
      this.activity = null;
   }

   public override fun onDetachedFromActivityForConfigChanges() {
      this.activity = null;
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
      val var3: java.lang.String = var1.method;
      if (var1.method == "openSettings") {
         this.handleOpenSettings(var1, var2);
      } else if (var3 == "openSettingsPanel") {
         this.handleOpenSettingsPanel(var1, var2);
      } else {
         var2.notImplemented();
      }
   }

   public override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
      this.activity = var1.getActivity();
   }
}
