package io.flutter.embedding.engine.plugins.util;

import io.flutter.Log;
import io.flutter.embedding.engine.FlutterEngine;

public class GeneratedPluginRegister {
   private static final String TAG = "GeneratedPluginsRegister";

   public static void registerGeneratedPlugins(FlutterEngine var0) {
      try {
         Class.forName("io.flutter.plugins.GeneratedPluginRegistrant").getDeclaredMethod("registerWith", FlutterEngine.class).invoke(null, var0);
      } catch (Exception var3) {
         StringBuilder var1 = new StringBuilder("Tried to automatically register plugins with FlutterEngine (");
         var1.append(var0);
         var1.append(") but could not find or invoke the GeneratedPluginRegistrant.");
         Log.e("GeneratedPluginsRegister", var1.toString());
         Log.e("GeneratedPluginsRegister", "Received exception while registering", var3);
      }
   }
}
