package com.mixpanel.android.mpmetrics;

import android.content.Context;
import android.content.pm.PackageManager;
import com.mixpanel.android.util.MPLog;

class ConfigurationChecker {
   public static String LOGTAG;

   public static boolean checkBasicConfiguration(Context var0) {
      PackageManager var1 = var0.getPackageManager();
      String var2 = var0.getPackageName();
      if (var1 == null || var2 == null) {
         MPLog.w(LOGTAG, "Can't check configuration when using a Context with null packageManager or packageName");
         return false;
      } else if (var1.checkPermission("android.permission.INTERNET", var2) != 0) {
         MPLog.w(LOGTAG, "Package does not have permission android.permission.INTERNET - Mixpanel will not work at all!");
         MPLog.i(
            LOGTAG,
            "You can fix this by adding the following to your AndroidManifest.xml file:\n<uses-permission android:name=\"android.permission.INTERNET\" />"
         );
         return false;
      } else {
         return true;
      }
   }
}
