package io.flutter.plugins.webviewflutter;

import android.content.res.AssetManager;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import java.io.IOException;

public abstract class FlutterAssetManager {
   final AssetManager assetManager;

   public FlutterAssetManager(AssetManager var1) {
      this.assetManager = var1;
   }

   abstract String getAssetFilePathByName(String var1);

   public String[] list(String var1) throws IOException {
      return this.assetManager.list(var1);
   }

   static class PluginBindingFlutterAssetManager extends FlutterAssetManager {
      final FlutterPlugin.FlutterAssets flutterAssets;

      PluginBindingFlutterAssetManager(AssetManager var1, FlutterPlugin.FlutterAssets var2) {
         super(var1);
         this.flutterAssets = var2;
      }

      @Override
      public String getAssetFilePathByName(String var1) {
         return this.flutterAssets.getAssetFilePathByName(var1);
      }
   }
}
