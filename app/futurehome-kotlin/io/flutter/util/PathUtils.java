package io.flutter.util;

import android.content.Context;
import android.os.Build.VERSION;
import androidx.transition.CanvasUtils..ExternalSyntheticApiModelOutline0;
import java.io.File;

public final class PathUtils {
   public static String getCacheDirectory(Context var0) {
      File var2 = var0.getCodeCacheDir();
      File var1 = var2;
      if (var2 == null) {
         var1 = var0.getCacheDir();
      }

      var2 = var1;
      if (var1 == null) {
         var2 = new File(getDataDirPath(var0), "cache");
      }

      return var2.getPath();
   }

   private static String getDataDirPath(Context var0) {
      return VERSION.SDK_INT >= 24 ? ExternalSyntheticApiModelOutline0.m(var0).getPath() : var0.getApplicationInfo().dataDir;
   }

   public static String getDataDirectory(Context var0) {
      File var2 = var0.getDir("flutter", 0);
      File var1 = var2;
      if (var2 == null) {
         var1 = new File(getDataDirPath(var0), "app_flutter");
      }

      return var1.getPath();
   }

   public static String getFilesDir(Context var0) {
      File var2 = var0.getFilesDir();
      File var1 = var2;
      if (var2 == null) {
         var1 = new File(getDataDirPath(var0), "files");
      }

      return var1.getPath();
   }
}
