package io.flutter.embedding.engine.loader;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import java.io.IOException;
import org.json.JSONArray;
import org.xmlpull.v1.XmlPullParserException;

public final class ApplicationInfoLoader {
   public static final String NETWORK_POLICY_METADATA_KEY = "io.flutter.network-policy";
   public static final String PUBLIC_AOT_SHARED_LIBRARY_NAME;
   public static final String PUBLIC_AUTOMATICALLY_REGISTER_PLUGINS_METADATA_KEY = "io.flutter.automatically-register-plugins";
   public static final String PUBLIC_FLUTTER_ASSETS_DIR_KEY;
   public static final String PUBLIC_ISOLATE_SNAPSHOT_DATA_KEY;
   public static final String PUBLIC_VM_SNAPSHOT_DATA_KEY;

   static {
      StringBuilder var0 = new StringBuilder();
      var0.append(FlutterLoader.class.getName());
      var0.append(".aot-shared-library-name");
      PUBLIC_AOT_SHARED_LIBRARY_NAME = var0.toString();
      var0 = new StringBuilder();
      var0.append(FlutterLoader.class.getName());
      var0.append(".vm-snapshot-data");
      PUBLIC_VM_SNAPSHOT_DATA_KEY = var0.toString();
      var0 = new StringBuilder();
      var0.append(FlutterLoader.class.getName());
      var0.append(".isolate-snapshot-data");
      PUBLIC_ISOLATE_SNAPSHOT_DATA_KEY = var0.toString();
      var0 = new StringBuilder();
      var0.append(FlutterLoader.class.getName());
      var0.append(".flutter-assets-dir");
      PUBLIC_FLUTTER_ASSETS_DIR_KEY = var0.toString();
   }

   private static ApplicationInfo getApplicationInfo(Context var0) {
      try {
         return var0.getPackageManager().getApplicationInfo(var0.getPackageName(), 128);
      } catch (NameNotFoundException var1) {
         throw new RuntimeException(var1);
      }
   }

   private static boolean getBoolean(Bundle var0, String var1, boolean var2) {
      return var0 == null ? var2 : var0.getBoolean(var1, var2);
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   private static String getNetworkPolicy(ApplicationInfo var0, Context var1) {
      Bundle var6 = var0.metaData;
      if (var6 == null) {
         return null;
      } else {
         int var2 = var6.getInt("io.flutter.network-policy", 0);
         if (var2 <= 0) {
            return null;
         } else {
            JSONArray var7 = new JSONArray();

            try {
               var8 = var1.getResources().getXml(var2);
               var8.next();
               var2 = var8.getEventType();
            } catch (XmlPullParserException | IOException var5) {
               return null;
            }

            while (var2 != 1) {
               if (var2 == 2) {
                  try {
                     if (var8.getName().equals("domain-config")) {
                        parseDomainConfig(var8, var7, false);
                     }
                  } catch (XmlPullParserException | IOException var3) {
                     return null;
                  }
               }

               try {
                  var2 = var8.next();
               } catch (XmlPullParserException | IOException var4) {
                  return null;
               }
            }

            return var7.toString();
         }
      }
   }

   private static String getString(Bundle var0, String var1) {
      return var0 == null ? null : var0.getString(var1, null);
   }

   public static FlutterApplicationInfo load(Context var0) {
      ApplicationInfo var1 = getApplicationInfo(var0);
      return new FlutterApplicationInfo(
         getString(var1.metaData, PUBLIC_AOT_SHARED_LIBRARY_NAME),
         getString(var1.metaData, PUBLIC_VM_SNAPSHOT_DATA_KEY),
         getString(var1.metaData, PUBLIC_ISOLATE_SNAPSHOT_DATA_KEY),
         getString(var1.metaData, PUBLIC_FLUTTER_ASSETS_DIR_KEY),
         getNetworkPolicy(var1, var0),
         var1.nativeLibraryDir,
         getBoolean(var1.metaData, "io.flutter.automatically-register-plugins", true)
      );
   }

   private static void parseDomain(XmlResourceParser var0, JSONArray var1, boolean var2) throws IOException, XmlPullParserException {
      boolean var3 = var0.getAttributeBooleanValue(null, "includeSubdomains", false);
      var0.next();
      if (var0.getEventType() == 4) {
         String var4 = var0.getText().trim();
         JSONArray var5 = new JSONArray();
         var5.put(var4);
         var5.put(var3);
         var5.put(var2);
         var1.put(var5);
         var0.next();
         if (var0.getEventType() != 3) {
            throw new IllegalStateException("Expected end of domain tag");
         }
      } else {
         throw new IllegalStateException("Expected text");
      }
   }

   private static void parseDomainConfig(XmlResourceParser var0, JSONArray var1, boolean var2) throws IOException, XmlPullParserException {
      var2 = var0.getAttributeBooleanValue(null, "cleartextTrafficPermitted", var2);

      while (true) {
         int var3 = var0.next();
         if (var3 == 2) {
            if (var0.getName().equals("domain")) {
               parseDomain(var0, var1, var2);
            } else if (var0.getName().equals("domain-config")) {
               parseDomainConfig(var0, var1, var2);
            } else {
               skipTag(var0);
            }
         } else if (var3 == 3) {
            return;
         }
      }
   }

   private static void skipTag(XmlResourceParser var0) throws IOException, XmlPullParserException {
      String var2 = var0.getName();
      int var1 = var0.getEventType();

      while (var1 != 3 || var0.getName() != var2) {
         var1 = var0.next();
      }
   }
}
