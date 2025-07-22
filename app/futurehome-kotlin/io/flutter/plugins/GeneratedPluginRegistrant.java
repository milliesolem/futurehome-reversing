package io.flutter.plugins;

import com.baseflow.geocoding.GeocodingPlugin;
import com.baseflow.geolocator.GeolocatorPlugin;
import com.baseflow.permissionhandler.PermissionHandlerPlugin;
import com.dexterous.flutterlocalnotifications.FlutterLocalNotificationsPlugin;
import com.it_nomads.fluttersecurestorage.FlutterSecureStoragePlugin;
import com.lib.flutter_blue_plus.FlutterBluePlusPlugin;
import com.mixpanel.mixpanel_flutter.MixpanelFlutterPlugin;
import com.signify.hue.flutterreactiveble.ReactiveBlePlugin;
import com.spencerccf.app_settings.AppSettingsPlugin;
import dev.flutter.plugins.integration_test.IntegrationTestPlugin;
import dev.fluttercommunity.android_id.AndroidIdPlugin;
import dev.fluttercommunity.plus.connectivity.ConnectivityPlugin;
import dev.fluttercommunity.plus.device_info.DeviceInfoPlusPlugin;
import dev.fluttercommunity.plus.packageinfo.PackageInfoPlugin;
import dev.fluttercommunity.plus.share.SharePlusPlugin;
import dev.fluttercommunity.plus.wakelock.WakelockPlusPlugin;
import dev.steenbakker.mobile_scanner.MobileScannerPlugin;
import io.flutter.Log;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.plugins.PluginRegistry;
import io.flutter.plugins.firebase.analytics.FlutterFirebaseAnalyticsPlugin;
import io.flutter.plugins.firebase.core.FlutterFirebaseCorePlugin;
import io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigPlugin;
import io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingPlugin;
import io.flutter.plugins.flutter_plugin_android_lifecycle.FlutterAndroidLifecyclePlugin;
import io.flutter.plugins.localauth.LocalAuthPlugin;
import io.flutter.plugins.pathprovider.PathProviderPlugin;
import io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin;
import io.flutter.plugins.urllauncher.UrlLauncherPlugin;
import io.flutter.plugins.webviewflutter.WebViewFlutterPlugin;
import io.sentry.flutter.SentryFlutterPlugin;

public final class GeneratedPluginRegistrant {
   private static final String TAG = "GeneratedPluginRegistrant";

   public static void registerWith(FlutterEngine var0) {
      try {
         PluginRegistry var2 = var0.getPlugins();
         AndroidIdPlugin var1 = new AndroidIdPlugin();
         var2.add(var1);
      } catch (Exception var30) {
         Log.e("GeneratedPluginRegistrant", "Error registering plugin android_id, dev.fluttercommunity.android_id.AndroidIdPlugin", var30);
      }

      try {
         PluginRegistry var59 = var0.getPlugins();
         AppSettingsPlugin var32 = new AppSettingsPlugin();
         var59.add(var32);
      } catch (Exception var29) {
         Log.e("GeneratedPluginRegistrant", "Error registering plugin app_settings, com.spencerccf.app_settings.AppSettingsPlugin", var29);
      }

      try {
         PluginRegistry var33 = var0.getPlugins();
         ConnectivityPlugin var60 = new ConnectivityPlugin();
         var33.add(var60);
      } catch (Exception var28) {
         Log.e("GeneratedPluginRegistrant", "Error registering plugin connectivity_plus, dev.fluttercommunity.plus.connectivity.ConnectivityPlugin", var28);
      }

      try {
         PluginRegistry var34 = var0.getPlugins();
         DeviceInfoPlusPlugin var61 = new DeviceInfoPlusPlugin();
         var34.add(var61);
      } catch (Exception var27) {
         Log.e("GeneratedPluginRegistrant", "Error registering plugin device_info_plus, dev.fluttercommunity.plus.device_info.DeviceInfoPlusPlugin", var27);
      }

      try {
         PluginRegistry var35 = var0.getPlugins();
         FlutterFirebaseAnalyticsPlugin var62 = new FlutterFirebaseAnalyticsPlugin();
         var35.add(var62);
      } catch (Exception var26) {
         Log.e(
            "GeneratedPluginRegistrant",
            "Error registering plugin firebase_analytics, io.flutter.plugins.firebase.analytics.FlutterFirebaseAnalyticsPlugin",
            var26
         );
      }

      try {
         PluginRegistry var63 = var0.getPlugins();
         FlutterFirebaseCorePlugin var36 = new FlutterFirebaseCorePlugin();
         var63.add(var36);
      } catch (Exception var25) {
         Log.e("GeneratedPluginRegistrant", "Error registering plugin firebase_core, io.flutter.plugins.firebase.core.FlutterFirebaseCorePlugin", var25);
      }

      try {
         PluginRegistry var64 = var0.getPlugins();
         FlutterFirebaseMessagingPlugin var37 = new FlutterFirebaseMessagingPlugin();
         var64.add(var37);
      } catch (Exception var24) {
         Log.e(
            "GeneratedPluginRegistrant",
            "Error registering plugin firebase_messaging, io.flutter.plugins.firebase.messaging.FlutterFirebaseMessagingPlugin",
            var24
         );
      }

      try {
         PluginRegistry var38 = var0.getPlugins();
         FirebaseRemoteConfigPlugin var65 = new FirebaseRemoteConfigPlugin();
         var38.add(var65);
      } catch (Exception var23) {
         Log.e(
            "GeneratedPluginRegistrant",
            "Error registering plugin firebase_remote_config, io.flutter.plugins.firebase.firebaseremoteconfig.FirebaseRemoteConfigPlugin",
            var23
         );
      }

      try {
         PluginRegistry var66 = var0.getPlugins();
         FlutterBluePlusPlugin var39 = new FlutterBluePlusPlugin();
         var66.add(var39);
      } catch (Exception var22) {
         Log.e("GeneratedPluginRegistrant", "Error registering plugin flutter_blue_plus_android, com.lib.flutter_blue_plus.FlutterBluePlusPlugin", var22);
      }

      try {
         PluginRegistry var67 = var0.getPlugins();
         FlutterLocalNotificationsPlugin var40 = new FlutterLocalNotificationsPlugin();
         var67.add(var40);
      } catch (Exception var21) {
         Log.e(
            "GeneratedPluginRegistrant",
            "Error registering plugin flutter_local_notifications, com.dexterous.flutterlocalnotifications.FlutterLocalNotificationsPlugin",
            var21
         );
      }

      try {
         PluginRegistry var41 = var0.getPlugins();
         FlutterAndroidLifecyclePlugin var68 = new FlutterAndroidLifecyclePlugin();
         var41.add(var68);
      } catch (Exception var20) {
         Log.e(
            "GeneratedPluginRegistrant",
            "Error registering plugin flutter_plugin_android_lifecycle, io.flutter.plugins.flutter_plugin_android_lifecycle.FlutterAndroidLifecyclePlugin",
            var20
         );
      }

      try {
         PluginRegistry var69 = var0.getPlugins();
         FlutterSecureStoragePlugin var42 = new FlutterSecureStoragePlugin();
         var69.add(var42);
      } catch (Exception var19) {
         Log.e(
            "GeneratedPluginRegistrant",
            "Error registering plugin flutter_secure_storage, com.it_nomads.fluttersecurestorage.FlutterSecureStoragePlugin",
            var19
         );
      }

      try {
         PluginRegistry var70 = var0.getPlugins();
         GeocodingPlugin var43 = new GeocodingPlugin();
         var70.add(var43);
      } catch (Exception var18) {
         Log.e("GeneratedPluginRegistrant", "Error registering plugin geocoding_android, com.baseflow.geocoding.GeocodingPlugin", var18);
      }

      try {
         PluginRegistry var44 = var0.getPlugins();
         GeolocatorPlugin var71 = new GeolocatorPlugin();
         var44.add(var71);
      } catch (Exception var17) {
         Log.e("GeneratedPluginRegistrant", "Error registering plugin geolocator_android, com.baseflow.geolocator.GeolocatorPlugin", var17);
      }

      try {
         PluginRegistry var72 = var0.getPlugins();
         IntegrationTestPlugin var45 = new IntegrationTestPlugin();
         var72.add(var45);
      } catch (Exception var16) {
         Log.e("GeneratedPluginRegistrant", "Error registering plugin integration_test, dev.flutter.plugins.integration_test.IntegrationTestPlugin", var16);
      }

      try {
         PluginRegistry var73 = var0.getPlugins();
         LocalAuthPlugin var46 = new LocalAuthPlugin();
         var73.add(var46);
      } catch (Exception var15) {
         Log.e("GeneratedPluginRegistrant", "Error registering plugin local_auth_android, io.flutter.plugins.localauth.LocalAuthPlugin", var15);
      }

      try {
         PluginRegistry var47 = var0.getPlugins();
         MixpanelFlutterPlugin var74 = new MixpanelFlutterPlugin();
         var47.add(var74);
      } catch (Exception var14) {
         Log.e("GeneratedPluginRegistrant", "Error registering plugin mixpanel_flutter, com.mixpanel.mixpanel_flutter.MixpanelFlutterPlugin", var14);
      }

      try {
         PluginRegistry var75 = var0.getPlugins();
         MobileScannerPlugin var48 = new MobileScannerPlugin();
         var75.add(var48);
      } catch (Exception var13) {
         Log.e("GeneratedPluginRegistrant", "Error registering plugin mobile_scanner, dev.steenbakker.mobile_scanner.MobileScannerPlugin", var13);
      }

      try {
         PluginRegistry var49 = var0.getPlugins();
         PackageInfoPlugin var76 = new PackageInfoPlugin();
         var49.add(var76);
      } catch (Exception var12) {
         Log.e("GeneratedPluginRegistrant", "Error registering plugin package_info_plus, dev.fluttercommunity.plus.packageinfo.PackageInfoPlugin", var12);
      }

      try {
         PluginRegistry var77 = var0.getPlugins();
         PathProviderPlugin var50 = new PathProviderPlugin();
         var77.add(var50);
      } catch (Exception var11) {
         Log.e("GeneratedPluginRegistrant", "Error registering plugin path_provider_android, io.flutter.plugins.pathprovider.PathProviderPlugin", var11);
      }

      try {
         PluginRegistry var78 = var0.getPlugins();
         PermissionHandlerPlugin var51 = new PermissionHandlerPlugin();
         var78.add(var51);
      } catch (Exception var10) {
         Log.e(
            "GeneratedPluginRegistrant", "Error registering plugin permission_handler_android, com.baseflow.permissionhandler.PermissionHandlerPlugin", var10
         );
      }

      try {
         PluginRegistry var79 = var0.getPlugins();
         ReactiveBlePlugin var52 = new ReactiveBlePlugin();
         var79.add(var52);
      } catch (Exception var9) {
         Log.e("GeneratedPluginRegistrant", "Error registering plugin reactive_ble_mobile, com.signify.hue.flutterreactiveble.ReactiveBlePlugin", var9);
      }

      try {
         PluginRegistry var80 = var0.getPlugins();
         SentryFlutterPlugin var53 = new SentryFlutterPlugin();
         var80.add(var53);
      } catch (Exception var8) {
         Log.e("GeneratedPluginRegistrant", "Error registering plugin sentry_flutter, io.sentry.flutter.SentryFlutterPlugin", var8);
      }

      try {
         PluginRegistry var54 = var0.getPlugins();
         SharePlusPlugin var81 = new SharePlusPlugin();
         var54.add(var81);
      } catch (Exception var7) {
         Log.e("GeneratedPluginRegistrant", "Error registering plugin share_plus, dev.fluttercommunity.plus.share.SharePlusPlugin", var7);
      }

      try {
         PluginRegistry var82 = var0.getPlugins();
         SharedPreferencesPlugin var55 = new SharedPreferencesPlugin();
         var82.add(var55);
      } catch (Exception var6) {
         Log.e(
            "GeneratedPluginRegistrant",
            "Error registering plugin shared_preferences_android, io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin",
            var6
         );
      }

      try {
         PluginRegistry var83 = var0.getPlugins();
         UrlLauncherPlugin var56 = new UrlLauncherPlugin();
         var83.add(var56);
      } catch (Exception var5) {
         Log.e("GeneratedPluginRegistrant", "Error registering plugin url_launcher_android, io.flutter.plugins.urllauncher.UrlLauncherPlugin", var5);
      }

      try {
         PluginRegistry var84 = var0.getPlugins();
         WakelockPlusPlugin var57 = new WakelockPlusPlugin();
         var84.add(var57);
      } catch (Exception var4) {
         Log.e("GeneratedPluginRegistrant", "Error registering plugin wakelock_plus, dev.fluttercommunity.plus.wakelock.WakelockPlusPlugin", var4);
      }

      try {
         PluginRegistry var31 = var0.getPlugins();
         WebViewFlutterPlugin var58 = new WebViewFlutterPlugin();
         var31.add(var58);
      } catch (Exception var3) {
         Log.e("GeneratedPluginRegistrant", "Error registering plugin webview_flutter_android, io.flutter.plugins.webviewflutter.WebViewFlutterPlugin", var3);
      }
   }
}
