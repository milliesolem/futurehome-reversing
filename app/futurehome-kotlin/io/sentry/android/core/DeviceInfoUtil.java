package io.sentry.android.core;

import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.LocaleList;
import android.os.StatFs;
import android.os.SystemClock;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import androidx.activity.ComponentDialog..ExternalSyntheticApiModelOutline0;
import io.sentry.DateUtils;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.android.core.internal.util.CpuInfoUtils;
import io.sentry.android.core.internal.util.DeviceOrientations;
import io.sentry.android.core.internal.util.RootChecker;
import io.sentry.protocol.Device;
import io.sentry.protocol.OperatingSystem;
import java.io.File;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public final class DeviceInfoUtil {
   private static volatile DeviceInfoUtil instance;
   private final BuildInfoProvider buildInfoProvider;
   private final Context context;
   private final Boolean isEmulator;
   private final SentryAndroidOptions options;
   private final OperatingSystem os;
   private final ContextUtils.SideLoadedInfo sideLoadedInfo;
   private final Long totalMem;

   public DeviceInfoUtil(Context var1, SentryAndroidOptions var2) {
      this.context = var1;
      this.options = var2;
      BuildInfoProvider var3 = new BuildInfoProvider(var2.getLogger());
      this.buildInfoProvider = var3;
      CpuInfoUtils.getInstance().readMaxFrequencies();
      this.os = this.retrieveOperatingSystemInformation();
      this.isEmulator = var3.isEmulator();
      this.sideLoadedInfo = ContextUtils.retrieveSideLoadedInfo(var1, var2.getLogger(), var3);
      MemoryInfo var4 = ContextUtils.getMemInfo(var1, var2.getLogger());
      if (var4 != null) {
         this.totalMem = var4.totalMem;
      } else {
         this.totalMem = null;
      }
   }

   private Intent getBatteryIntent() {
      return ContextUtils.registerReceiver(this.context, this.buildInfoProvider, null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static Float getBatteryLevel(Intent var0, SentryOptions var1) {
      int var3;
      int var4;
      try {
         var3 = var0.getIntExtra("level", -1);
         var4 = var0.getIntExtra("scale", -1);
      } catch (Throwable var6) {
         var1.getLogger().log(SentryLevel.ERROR, "Error getting device battery level.", var6);
         return null;
      }

      if (var3 != -1 && var4 != -1) {
         float var2 = (float)var3 / var4;
         return var2 * 100.0F;
      } else {
         return null;
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private Float getBatteryTemperature(Intent var1) {
      int var3;
      try {
         var3 = var1.getIntExtra("temperature", -1);
      } catch (Throwable var5) {
         this.options.getLogger().log(SentryLevel.ERROR, "Error getting battery temperature.", var5);
         return null;
      }

      if (var3 != -1) {
         float var2 = var3 / 10.0F;
         return var2;
      } else {
         return null;
      }
   }

   private Date getBootTime() {
      try {
         return DateUtils.getDateTime(System.currentTimeMillis() - SystemClock.elapsedRealtime());
      } catch (IllegalArgumentException var2) {
         this.options.getLogger().log(SentryLevel.ERROR, var2, "Error getting the device's boot time.");
         return null;
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private String getDeviceId() {
      try {
         return Installation.id(this.context);
      } catch (Throwable var3) {
         this.options.getLogger().log(SentryLevel.ERROR, "Error getting installationId.", var3);
         return null;
      }
   }

   private File getExternalStorageDep(File var1) {
      File[] var4 = this.context.getExternalFilesDirs(null);
      int var2 = 0;
      if (var4 != null) {
         String var6;
         if (var1 != null) {
            var6 = var1.getAbsolutePath();
         } else {
            var6 = null;
         }

         for (int var3 = var4.length; var2 < var3; var2++) {
            File var5 = var4[var2];
            if (var5 != null && (var6 == null || var6.isEmpty() || !var5.getAbsolutePath().contains(var6))) {
               return var5;
            }
         }
      } else {
         this.options.getLogger().log(SentryLevel.INFO, "Not possible to read getExternalFilesDirs");
      }

      return null;
   }

   private StatFs getExternalStorageStat(File param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: aload 1
      // 02: invokespecial io/sentry/android/core/DeviceInfoUtil.getExternalStorageDep (Ljava/io/File;)Ljava/io/File;
      // 05: astore 1
      // 06: aload 1
      // 07: ifnull 2e
      // 0a: new android/os/StatFs
      // 0d: dup
      // 0e: aload 1
      // 0f: invokevirtual java/io/File.getPath ()Ljava/lang/String;
      // 12: invokespecial android/os/StatFs.<init> (Ljava/lang/String;)V
      // 15: astore 1
      // 16: aload 1
      // 17: areturn
      // 18: astore 1
      // 19: aload 0
      // 1a: getfield io/sentry/android/core/DeviceInfoUtil.options Lio/sentry/android/core/SentryAndroidOptions;
      // 1d: invokevirtual io/sentry/android/core/SentryAndroidOptions.getLogger ()Lio/sentry/ILogger;
      // 20: getstatic io/sentry/SentryLevel.INFO Lio/sentry/SentryLevel;
      // 23: ldc "Not possible to read external files directory"
      // 25: bipush 0
      // 26: anewarray 4
      // 29: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 2e: aconst_null
      // 2f: areturn
   }

   public static DeviceInfoUtil getInstance(Context param0, SentryAndroidOptions param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: getstatic io/sentry/android/core/DeviceInfoUtil.instance Lio/sentry/android/core/DeviceInfoUtil;
      // 03: ifnonnull 2c
      // 06: ldc io/sentry/android/core/DeviceInfoUtil
      // 08: monitorenter
      // 09: getstatic io/sentry/android/core/DeviceInfoUtil.instance Lio/sentry/android/core/DeviceInfoUtil;
      // 0c: ifnonnull 20
      // 0f: new io/sentry/android/core/DeviceInfoUtil
      // 12: astore 2
      // 13: aload 2
      // 14: aload 0
      // 15: invokestatic io/sentry/android/core/ContextUtils.getApplicationContext (Landroid/content/Context;)Landroid/content/Context;
      // 18: aload 1
      // 19: invokespecial io/sentry/android/core/DeviceInfoUtil.<init> (Landroid/content/Context;Lio/sentry/android/core/SentryAndroidOptions;)V
      // 1c: aload 2
      // 1d: putstatic io/sentry/android/core/DeviceInfoUtil.instance Lio/sentry/android/core/DeviceInfoUtil;
      // 20: ldc io/sentry/android/core/DeviceInfoUtil
      // 22: monitorexit
      // 23: goto 2c
      // 26: astore 0
      // 27: ldc io/sentry/android/core/DeviceInfoUtil
      // 29: monitorexit
      // 2a: aload 0
      // 2b: athrow
      // 2c: getstatic io/sentry/android/core/DeviceInfoUtil.instance Lio/sentry/android/core/DeviceInfoUtil;
      // 2f: areturn
   }

   private Device.DeviceOrientation getOrientation() {
      Device.DeviceOrientation var1;
      try {
         var1 = DeviceOrientations.getOrientation(this.context.getResources().getConfiguration().orientation);
      } finally {
         ;
      }

      if (var1 == null) {
         try {
            this.options.getLogger().log(SentryLevel.INFO, "No device orientation available (ORIENTATION_SQUARE|ORIENTATION_UNDEFINED)");
            return null;
         } finally {
            this.options.getLogger().log(SentryLevel.ERROR, "Error getting device orientation.", var1);
            return var1;
         }
      } else {
         return var1;
      }
   }

   private TimeZone getTimeZone() {
      if (this.buildInfoProvider.getSdkInfoVersion() >= 24) {
         LocaleList var1 = ExternalSyntheticApiModelOutline0.m(this.context.getResources().getConfiguration());
         if (!androidx.core.util.HalfKt..ExternalSyntheticApiModelOutline0.m(var1)) {
            return Calendar.getInstance(androidx.core.util.HalfKt..ExternalSyntheticApiModelOutline0.m(var1, 0)).getTimeZone();
         }
      }

      return Calendar.getInstance().getTimeZone();
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private Long getTotalExternalStorage(StatFs var1) {
      long var2;
      long var4;
      try {
         var2 = var1.getBlockSizeLong();
         var4 = var1.getBlockCountLong();
      } catch (Throwable var7) {
         this.options.getLogger().log(SentryLevel.ERROR, "Error getting total external storage amount.", var7);
         return null;
      }

      return var4 * var2;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private Long getTotalInternalStorage(StatFs var1) {
      long var2;
      long var4;
      try {
         var2 = var1.getBlockSizeLong();
         var4 = var1.getBlockCountLong();
      } catch (Throwable var7) {
         this.options.getLogger().log(SentryLevel.ERROR, "Error getting total internal storage amount.", var7);
         return null;
      }

      return var4 * var2;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private Long getUnusedExternalStorage(StatFs var1) {
      long var2;
      long var4;
      try {
         var4 = var1.getBlockSizeLong();
         var2 = var1.getAvailableBlocksLong();
      } catch (Throwable var7) {
         this.options.getLogger().log(SentryLevel.ERROR, "Error getting unused external storage amount.", var7);
         return null;
      }

      return var2 * var4;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private Long getUnusedInternalStorage(StatFs var1) {
      long var2;
      long var4;
      try {
         var2 = var1.getBlockSizeLong();
         var4 = var1.getAvailableBlocksLong();
      } catch (Throwable var7) {
         this.options.getLogger().log(SentryLevel.ERROR, "Error getting unused internal storage amount.", var7);
         return null;
      }

      return var4 * var2;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static Boolean isCharging(Intent var0, SentryOptions var1) {
      int var2;
      try {
         var2 = var0.getIntExtra("plugged", -1);
      } catch (Throwable var6) {
         var1.getLogger().log(SentryLevel.ERROR, "Error getting device charging state.", var6);
         return null;
      }

      boolean var4 = true;
      boolean var3 = var4;
      if (var2 != 1) {
         if (var2 == 2) {
            var3 = var4;
         } else {
            var3 = false;
         }
      }

      return var3;
   }

   public static void resetInstance() {
      instance = null;
   }

   private OperatingSystem retrieveOperatingSystemInformation() {
      OperatingSystem var2 = new OperatingSystem();
      var2.setName("Android");
      var2.setVersion(VERSION.RELEASE);
      var2.setBuild(Build.DISPLAY);
      String var1 = ContextUtils.getKernelVersion(this.options.getLogger());
      if (var1 != null) {
         var2.setKernelVersion(var1);
      }

      if (this.options.isEnableRootCheck()) {
         var2.setRooted(new RootChecker(this.context, this.buildInfoProvider, this.options.getLogger()).isDeviceRooted());
      }

      return var2;
   }

   private void setDeviceIO(Device var1, boolean var2) {
      Intent var4 = this.getBatteryIntent();
      if (var4 != null) {
         var1.setBatteryLevel(getBatteryLevel(var4, this.options));
         var1.setCharging(isCharging(var4, this.options));
         var1.setBatteryTemperature(this.getBatteryTemperature(var4));
      }

      int var3 = <unrepresentable>.$SwitchMap$io$sentry$IConnectionStatusProvider$ConnectionStatus[this.options
         .getConnectionStatusProvider()
         .getConnectionStatus()
         .ordinal()];
      Boolean var6;
      if (var3 != 1) {
         if (var3 != 2) {
            var6 = null;
         } else {
            var6 = true;
         }
      } else {
         var6 = false;
      }

      var1.setOnline(var6);
      MemoryInfo var7 = ContextUtils.getMemInfo(this.context, this.options.getLogger());
      if (var7 != null && var2) {
         var1.setFreeMemory(var7.availMem);
         var1.setLowMemory(var7.lowMemory);
      }

      File var8 = this.context.getExternalFilesDir(null);
      if (var8 != null) {
         StatFs var5 = new StatFs(var8.getPath());
         var1.setStorageSize(this.getTotalInternalStorage(var5));
         var1.setFreeStorage(this.getUnusedInternalStorage(var5));
      }

      StatFs var9 = this.getExternalStorageStat(var8);
      if (var9 != null) {
         var1.setExternalStorageSize(this.getTotalExternalStorage(var9));
         var1.setExternalFreeStorage(this.getUnusedExternalStorage(var9));
      }

      if (var1.getConnectionType() == null) {
         var1.setConnectionType(this.options.getConnectionStatusProvider().getConnectionType());
      }
   }

   public Device collectDeviceInformation(boolean var1, boolean var2) {
      Device var3 = new Device();
      if (this.options.isSendDefaultPii()) {
         var3.setName(ContextUtils.getDeviceName(this.context));
      }

      var3.setManufacturer(Build.MANUFACTURER);
      var3.setBrand(Build.BRAND);
      var3.setFamily(ContextUtils.getFamily(this.options.getLogger()));
      var3.setModel(Build.MODEL);
      var3.setModelId(Build.ID);
      var3.setArchs(ContextUtils.getArchitectures(this.buildInfoProvider));
      var3.setOrientation(this.getOrientation());
      Boolean var4 = this.isEmulator;
      if (var4 != null) {
         var3.setSimulator(var4);
      }

      DisplayMetrics var5 = ContextUtils.getDisplayMetrics(this.context, this.options.getLogger());
      if (var5 != null) {
         var3.setScreenWidthPixels(var5.widthPixels);
         var3.setScreenHeightPixels(var5.heightPixels);
         var3.setScreenDensity(var5.density);
         var3.setScreenDpi(var5.densityDpi);
      }

      var3.setBootTime(this.getBootTime());
      var3.setTimezone(this.getTimeZone());
      if (var3.getId() == null) {
         var3.setId(this.getDeviceId());
      }

      Locale var6 = Locale.getDefault();
      if (var3.getLanguage() == null) {
         var3.setLanguage(var6.getLanguage());
      }

      if (var3.getLocale() == null) {
         var3.setLocale(var6.toString());
      }

      List var7 = CpuInfoUtils.getInstance().readMaxFrequencies();
      if (!var7.isEmpty()) {
         var3.setProcessorFrequency(Collections.max(var7).doubleValue());
         var3.setProcessorCount(var7.size());
      }

      var3.setMemorySize(this.totalMem);
      if (var1 && this.options.isCollectAdditionalContext()) {
         this.setDeviceIO(var3, var2);
      }

      return var3;
   }

   public OperatingSystem getOperatingSystem() {
      return this.os;
   }

   public ContextUtils.SideLoadedInfo getSideLoadedInfo() {
      return this.sideLoadedInfo;
   }

   public Long getTotalMemory() {
      return this.totalMem;
   }
}
