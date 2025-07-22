package io.sentry.android.core;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.DisplayMetrics;
import androidx.webkit.internal.ApiHelperForN..ExternalSyntheticApiModelOutline4;
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0;
import io.sentry.ILogger;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.android.core.util.AndroidLazyEvaluator;
import io.sentry.protocol.App;
import io.sentry.util.LazyEvaluator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class ContextUtils {
   private static final AndroidLazyEvaluator<String> applicationName = new AndroidLazyEvaluator<>(new ContextUtils$$ExternalSyntheticLambda4());
   private static final AndroidLazyEvaluator<String> deviceName = new AndroidLazyEvaluator<>(new ContextUtils$$ExternalSyntheticLambda7());
   private static final LazyEvaluator<Boolean> isForegroundImportance = new LazyEvaluator<>(new ContextUtils$$ExternalSyntheticLambda8());
   private static final AndroidLazyEvaluator<ApplicationInfo> staticAppInfo = new AndroidLazyEvaluator<>(new ContextUtils$$ExternalSyntheticLambda6());
   private static final AndroidLazyEvaluator<ApplicationInfo> staticAppInfo33 = new AndroidLazyEvaluator<>(new ContextUtils$$ExternalSyntheticLambda5());
   private static final AndroidLazyEvaluator<PackageInfo> staticPackageInfo = new AndroidLazyEvaluator<>(new ContextUtils$$ExternalSyntheticLambda10());
   private static final AndroidLazyEvaluator<PackageInfo> staticPackageInfo33 = new AndroidLazyEvaluator<>(new ContextUtils$$ExternalSyntheticLambda9());

   private ContextUtils() {
   }

   public static Context getApplicationContext(Context var0) {
      Context var1 = var0.getApplicationContext();
      return var1 != null ? var1 : var0;
   }

   static ApplicationInfo getApplicationInfo(Context var0, BuildInfoProvider var1) {
      return var1.getSdkInfoVersion() >= 33 ? staticAppInfo33.getValue(var0) : staticAppInfo.getValue(var0);
   }

   static String getApplicationName(Context var0) {
      return applicationName.getValue(var0);
   }

   static String[] getArchitectures(BuildInfoProvider var0) {
      String[] var1;
      if (var0.getSdkInfoVersion() >= 21) {
         var1 = Build.SUPPORTED_ABIS;
      } else {
         var1 = new String[]{Build.CPU_ABI, Build.CPU_ABI2};
      }

      return var1;
   }

   static String getDeviceName(Context var0) {
      return deviceName.getValue(var0);
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static DisplayMetrics getDisplayMetrics(Context var0, ILogger var1) {
      try {
         return var0.getResources().getDisplayMetrics();
      } catch (Throwable var3) {
         var1.log(SentryLevel.ERROR, "Error getting DisplayMetrics.", var3);
         return null;
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static String getFamily(ILogger var0) {
      try {
         return Build.MODEL.split(" ", -1)[0];
      } catch (Throwable var3) {
         var0.log(SentryLevel.ERROR, "Error getting device family.", var3);
         return null;
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static String getKernelVersion(ILogger var0) {
      String var1 = System.getProperty("os.version");
      File var4 = new File("/proc/version");
      if (!var4.canRead()) {
         return var1;
      } else {
         BufferedReader var2;
         try {
            FileReader var3 = new FileReader(var4);
            var2 = new BufferedReader(var3);
         } catch (IOException var20) {
            var0.log(SentryLevel.ERROR, "Exception while attempting to read kernel information", var20);
            return var1;
         }

         String var25;
         try {
            var25 = var2.readLine();
         } catch (Throwable var22) {
            Throwable var24 = var22;

            try {
               var2.close();
            } catch (Throwable var21) {
               Throwable var23 = var21;

               label66:
               try {
                  var24.addSuppressed(var23);
                  break label66;
               } catch (IOException var18) {
                  var0.log(SentryLevel.ERROR, "Exception while attempting to read kernel information", var18);
                  return var1;
               }
            }

            try {
               throw var24;
            } catch (IOException var17) {
               var0.log(SentryLevel.ERROR, "Exception while attempting to read kernel information", var17);
               return var1;
            }
         }

         try {
            var2.close();
            return var25;
         } catch (IOException var19) {
            var0.log(SentryLevel.ERROR, "Exception while attempting to read kernel information", var19);
            return var1;
         }
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static MemoryInfo getMemInfo(Context var0, ILogger var1) {
      ActivityManager var2;
      try {
         var2 = (ActivityManager)var0.getSystemService("activity");
         var15 = new MemoryInfo();
      } catch (Throwable var14) {
         var1.log(SentryLevel.ERROR, "Error getting MemoryInfo.", var14);
         return null;
      }

      if (var2 != null) {
         try {
            var2.getMemoryInfo(var15);
            return var15;
         } catch (Throwable var12) {
            var1.log(SentryLevel.ERROR, "Error getting MemoryInfo.", var12);
            return null;
         }
      } else {
         try {
            var1.log(SentryLevel.INFO, "Error getting MemoryInfo.");
            return null;
         } catch (Throwable var13) {
            var1.log(SentryLevel.ERROR, "Error getting MemoryInfo.", var13);
            return null;
         }
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static PackageInfo getPackageInfo(Context var0, int var1, ILogger var2, BuildInfoProvider var3) {
      try {
         return var3.getSdkInfoVersion() >= 33
            ? AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(
               var0.getPackageManager(), var0.getPackageName(), AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m((long)var1)
            )
            : var0.getPackageManager().getPackageInfo(var0.getPackageName(), var1);
      } catch (Throwable var5) {
         var2.log(SentryLevel.ERROR, "Error getting package info.", var5);
         return null;
      }
   }

   static PackageInfo getPackageInfo(Context var0, BuildInfoProvider var1) {
      return var1.getSdkInfoVersion() >= 33 ? staticPackageInfo33.getValue(var0) : staticPackageInfo.getValue(var0);
   }

   static String getVersionCode(PackageInfo var0, BuildInfoProvider var1) {
      return var1.getSdkInfoVersion() >= 28 ? Long.toString(AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var0)) : getVersionCodeDep(var0);
   }

   private static String getVersionCodeDep(PackageInfo var0) {
      return Integer.toString(var0.versionCode);
   }

   static String getVersionName(PackageInfo var0) {
      return var0.versionName;
   }

   public static boolean isForegroundImportance() {
      return isForegroundImportance.getValue();
   }

   static Intent registerReceiver(Context var0, SentryOptions var1, BroadcastReceiver var2, IntentFilter var3) {
      return registerReceiver(var0, new BuildInfoProvider(var1.getLogger()), var2, var3);
   }

   static Intent registerReceiver(Context var0, BuildInfoProvider var1, BroadcastReceiver var2, IntentFilter var3) {
      return var1.getSdkInfoVersion() >= 33 ? ExternalSyntheticApiModelOutline4.m(var0, var2, var3, 4) : var0.registerReceiver(var2, var3);
   }

   static void resetInstance() {
      deviceName.resetValue();
      isForegroundImportance.resetValue();
      staticPackageInfo33.resetValue();
      staticPackageInfo.resetValue();
      applicationName.resetValue();
      staticAppInfo33.resetValue();
      staticAppInfo.resetValue();
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   static ContextUtils.SideLoadedInfo retrieveSideLoadedInfo(Context var0, ILogger var1, BuildInfoProvider var2) {
      label45: {
         PackageManager var4;
         try {
            var10 = getPackageInfo(var0, var2);
            var4 = var0.getPackageManager();
         } catch (IllegalArgumentException var8) {
            var9 = null;
            break label45;
         }

         if (var10 == null || var4 == null) {
            return null;
         }

         try {
            var9 = var10.packageName;
         } catch (IllegalArgumentException var7) {
            var9 = null;
            break label45;
         }

         try {
            var11 = var4.getInstallerPackageName(var9);
         } catch (IllegalArgumentException var6) {
            break label45;
         }

         boolean var3;
         if (var11 == null) {
            var3 = true;
         } else {
            var3 = false;
         }

         try {
            return new ContextUtils.SideLoadedInfo(var3, var11);
         } catch (IllegalArgumentException var5) {
         }
      }

      var1.log(SentryLevel.DEBUG, "%s package isn't installed.", var9);
      return null;
   }

   static void setAppPackageInfo(PackageInfo var0, BuildInfoProvider var1, App var2) {
      var2.setAppIdentifier(var0.packageName);
      var2.setAppVersion(var0.versionName);
      var2.setAppBuild(getVersionCode(var0, var1));
      HashMap var4 = new HashMap();
      String[] var9 = var0.requestedPermissions;
      int[] var5 = var0.requestedPermissionsFlags;
      if (var9 != null && var9.length > 0 && var5 != null && var5.length > 0) {
         for (int var3 = 0; var3 < var9.length; var3++) {
            String var7 = var9[var3];
            String var6 = var7.substring(var7.lastIndexOf(46) + 1);
            String var8;
            if ((var5[var3] & 2) == 2) {
               var8 = "granted";
            } else {
               var8 = "not_granted";
            }

            var4.put(var6, var8);
         }
      }

      var2.setPermissions(var4);
   }

   static class SideLoadedInfo {
      private final String installerStore;
      private final boolean isSideLoaded;

      public SideLoadedInfo(boolean var1, String var2) {
         this.isSideLoaded = var1;
         this.installerStore = var2;
      }

      public Map<String, String> asTags() {
         HashMap var1 = new HashMap();
         var1.put("isSideLoaded", String.valueOf(this.isSideLoaded));
         String var2 = this.installerStore;
         if (var2 != null) {
            var1.put("installerStore", var2);
         }

         return var1;
      }

      public String getInstallerStore() {
         return this.installerStore;
      }

      public boolean isSideLoaded() {
         return this.isSideLoaded;
      }
   }
}
