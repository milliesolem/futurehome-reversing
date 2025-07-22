package io.sentry.android.core;

import android.os.SystemClock;
import android.system.Os;
import android.system.OsConstants;
import io.sentry.CpuCollectionData;
import io.sentry.ILogger;
import io.sentry.IPerformanceSnapshotCollector;
import io.sentry.PerformanceCollectionData;
import io.sentry.SentryLevel;
import io.sentry.util.FileUtils;
import io.sentry.util.Objects;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public final class AndroidCpuCollector implements IPerformanceSnapshotCollector {
   private final long NANOSECOND_PER_SECOND;
   private final BuildInfoProvider buildInfoProvider;
   private long clockSpeedHz;
   private boolean isEnabled;
   private long lastCpuNanos;
   private long lastRealtimeNanos = 0L;
   private final ILogger logger;
   private double nanosecondsPerClockTick;
   private final Pattern newLinePattern;
   private long numCores;
   private final File selfStat;

   public AndroidCpuCollector(ILogger var1, BuildInfoProvider var2) {
      this.lastCpuNanos = 0L;
      this.clockSpeedHz = 1L;
      this.numCores = 1L;
      this.NANOSECOND_PER_SECOND = 1000000000L;
      this.nanosecondsPerClockTick = 1.0E9 / 1L;
      this.selfStat = new File("/proc/self/stat");
      this.isEnabled = false;
      this.newLinePattern = Pattern.compile("[\n\t\r ]");
      this.logger = Objects.requireNonNull(var1, "Logger is required.");
      this.buildInfoProvider = Objects.requireNonNull(var2, "BuildInfoProvider is required.");
   }

   private long readTotalCpuNanos() {
      String var5;
      try {
         var5 = FileUtils.readText(this.selfStat);
      } catch (IOException var6) {
         this.isEnabled = false;
         this.logger.log(SentryLevel.WARNING, "Unable to read /proc/self/stat file. Disabling cpu collection.", var6);
         var5 = null;
      }

      if (var5 != null) {
         var5 = var5.trim();
         String[] var10 = this.newLinePattern.split(var5);

         label23: {
            double var1;
            double var3;
            try {
               var3 = Long.parseLong(var10[13]) + Long.parseLong(var10[14]) + Long.parseLong(var10[15]) + Long.parseLong(var10[16]);
               var1 = this.nanosecondsPerClockTick;
            } catch (NumberFormatException var7) {
               var11 = var7;
               break label23;
            } catch (ArrayIndexOutOfBoundsException var8) {
               var11 = var8;
               break label23;
            }

            return (long)(var3 * var1);
         }

         this.logger.log(SentryLevel.ERROR, "Error parsing /proc/self/stat file.", (Throwable)var11);
      }

      return 0L;
   }

   @Override
   public void collect(PerformanceCollectionData var1) {
      if (this.buildInfoProvider.getSdkInfoVersion() >= 21 && this.isEnabled) {
         long var4 = SystemClock.elapsedRealtimeNanos();
         long var8 = this.lastRealtimeNanos;
         this.lastRealtimeNanos = var4;
         long var10 = this.readTotalCpuNanos();
         long var6 = this.lastCpuNanos;
         this.lastCpuNanos = var10;
         double var2 = (double)(var10 - var6) / (var4 - var8);
         var1.addCpuData(new CpuCollectionData(System.currentTimeMillis(), var2 / this.numCores * 100.0));
      }
   }

   @Override
   public void setup() {
      if (this.buildInfoProvider.getSdkInfoVersion() < 21) {
         this.isEnabled = false;
      } else {
         this.isEnabled = true;
         this.clockSpeedHz = Os.sysconf(OsConstants._SC_CLK_TCK);
         this.numCores = Os.sysconf(OsConstants._SC_NPROCESSORS_CONF);
         this.nanosecondsPerClockTick = 1.0E9 / this.clockSpeedHz;
         this.lastCpuNanos = this.readTotalCpuNanos();
      }
   }
}
