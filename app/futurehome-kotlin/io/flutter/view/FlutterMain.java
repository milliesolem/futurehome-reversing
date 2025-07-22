package io.flutter.view;

import android.content.Context;
import android.os.Handler;
import io.flutter.FlutterInjector;
import io.flutter.embedding.engine.loader.FlutterLoader;

@Deprecated
public class FlutterMain {
   public static void ensureInitializationComplete(Context var0, String[] var1) {
      FlutterInjector.instance().flutterLoader().ensureInitializationComplete(var0, var1);
   }

   public static void ensureInitializationCompleteAsync(Context var0, String[] var1, Handler var2, Runnable var3) {
      FlutterInjector.instance().flutterLoader().ensureInitializationCompleteAsync(var0, var1, var2, var3);
   }

   public static String findAppBundlePath() {
      return FlutterInjector.instance().flutterLoader().findAppBundlePath();
   }

   @Deprecated
   public static String findAppBundlePath(Context var0) {
      return FlutterInjector.instance().flutterLoader().findAppBundlePath();
   }

   public static String getLookupKeyForAsset(String var0) {
      return FlutterInjector.instance().flutterLoader().getLookupKeyForAsset(var0);
   }

   public static String getLookupKeyForAsset(String var0, String var1) {
      return FlutterInjector.instance().flutterLoader().getLookupKeyForAsset(var0, var1);
   }

   public static void startInitialization(Context var0) {
      FlutterInjector.instance().flutterLoader().startInitialization(var0);
   }

   public static void startInitialization(Context var0, FlutterMain.Settings var1) {
      FlutterLoader.Settings var2 = new FlutterLoader.Settings();
      var2.setLogTag(var1.getLogTag());
      FlutterInjector.instance().flutterLoader().startInitialization(var0, var2);
   }

   public static class Settings {
      private String logTag;

      public String getLogTag() {
         return this.logTag;
      }

      public void setLogTag(String var1) {
         this.logTag = var1;
      }
   }
}
