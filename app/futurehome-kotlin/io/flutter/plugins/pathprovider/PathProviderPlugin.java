package io.flutter.plugins.pathprovider;

import android.content.Context;
import android.util.Log;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.util.PathUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PathProviderPlugin implements FlutterPlugin, Messages.PathProviderApi {
   static final String TAG = "PathProviderPlugin";
   private Context context;

   private void setUp(BinaryMessenger var1, Context var2) {
      try {
         Messages$PathProviderApi$_CC.setUp(var1, this);
      } catch (Exception var3) {
         Log.e("PathProviderPlugin", "Received exception while setting up PathProviderPlugin", var3);
      }

      this.context = var2;
   }

   @Override
   public String getApplicationCachePath() {
      return this.context.getCacheDir().getPath();
   }

   @Override
   public String getApplicationDocumentsPath() {
      return PathUtils.getDataDirectory(this.context);
   }

   @Override
   public String getApplicationSupportPath() {
      return PathUtils.getFilesDir(this.context);
   }

   @Override
   public List<String> getExternalCachePaths() {
      ArrayList var5 = new ArrayList();

      for (File var3 : this.context.getExternalCacheDirs()) {
         if (var3 != null) {
            var5.add(var3.getAbsolutePath());
         }
      }

      return var5;
   }

   @Override
   public String getExternalStoragePath() {
      File var1 = this.context.getExternalFilesDir(null);
      return var1 == null ? null : var1.getAbsolutePath();
   }

   @Override
   public List<String> getExternalStoragePaths(Messages.StorageDirectory var1) {
      ArrayList var4 = new ArrayList();

      for (File var6 : this.context.getExternalFilesDirs(this.getStorageDirectoryString(var1))) {
         if (var6 != null) {
            var4.add(var6.getAbsolutePath());
         }
      }

      return var4;
   }

   String getStorageDirectoryString(Messages.StorageDirectory var1) {
      switch (<unrepresentable>.$SwitchMap$io$flutter$plugins$pathprovider$Messages$StorageDirectory[var1.ordinal()]) {
         case 1:
            return null;
         case 2:
            return "music";
         case 3:
            return "podcasts";
         case 4:
            return "ringtones";
         case 5:
            return "alarms";
         case 6:
            return "notifications";
         case 7:
            return "pictures";
         case 8:
            return "movies";
         case 9:
            return "downloads";
         case 10:
            return "dcim";
         case 11:
            return "documents";
         default:
            StringBuilder var2 = new StringBuilder("Unrecognized directory: ");
            var2.append(var1);
            throw new RuntimeException(var2.toString());
      }
   }

   @Override
   public String getTemporaryPath() {
      return this.context.getCacheDir().getPath();
   }

   @Override
   public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding var1) {
      this.setUp(var1.getBinaryMessenger(), var1.getApplicationContext());
   }

   @Override
   public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding var1) {
      Messages$PathProviderApi$_CC.setUp(var1.getBinaryMessenger(), null);
   }
}
