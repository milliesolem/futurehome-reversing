package io.flutter.embedding.engine.plugins;

import android.content.Context;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineGroup;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.platform.PlatformViewRegistry;
import io.flutter.view.TextureRegistry;

public interface FlutterPlugin {
   void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding var1);

   void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding var1);

   public interface FlutterAssets {
      String getAssetFilePathByName(String var1);

      String getAssetFilePathByName(String var1, String var2);

      String getAssetFilePathBySubpath(String var1);

      String getAssetFilePathBySubpath(String var1, String var2);
   }

   public static class FlutterPluginBinding {
      private final Context applicationContext;
      private final BinaryMessenger binaryMessenger;
      private final FlutterPlugin.FlutterAssets flutterAssets;
      private final FlutterEngine flutterEngine;
      private final FlutterEngineGroup group;
      private final PlatformViewRegistry platformViewRegistry;
      private final TextureRegistry textureRegistry;

      public FlutterPluginBinding(
         Context var1,
         FlutterEngine var2,
         BinaryMessenger var3,
         TextureRegistry var4,
         PlatformViewRegistry var5,
         FlutterPlugin.FlutterAssets var6,
         FlutterEngineGroup var7
      ) {
         this.applicationContext = var1;
         this.flutterEngine = var2;
         this.binaryMessenger = var3;
         this.textureRegistry = var4;
         this.platformViewRegistry = var5;
         this.flutterAssets = var6;
         this.group = var7;
      }

      public Context getApplicationContext() {
         return this.applicationContext;
      }

      public BinaryMessenger getBinaryMessenger() {
         return this.binaryMessenger;
      }

      public FlutterEngineGroup getEngineGroup() {
         return this.group;
      }

      public FlutterPlugin.FlutterAssets getFlutterAssets() {
         return this.flutterAssets;
      }

      @Deprecated
      public FlutterEngine getFlutterEngine() {
         return this.flutterEngine;
      }

      public PlatformViewRegistry getPlatformViewRegistry() {
         return this.platformViewRegistry;
      }

      public TextureRegistry getTextureRegistry() {
         return this.textureRegistry;
      }
   }
}
