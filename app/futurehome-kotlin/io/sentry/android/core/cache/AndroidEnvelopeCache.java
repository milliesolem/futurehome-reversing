package io.sentry.android.core.cache;

import io.sentry.Hint;
import io.sentry.SentryEnvelope;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.UncaughtExceptionHandlerIntegration;
import io.sentry.android.core.AnrV2Integration;
import io.sentry.android.core.SentryAndroidOptions;
import io.sentry.android.core.internal.util.AndroidCurrentDateProvider;
import io.sentry.android.core.performance.AppStartMetrics;
import io.sentry.android.core.performance.TimeSpan;
import io.sentry.cache.EnvelopeCache;
import io.sentry.transport.ICurrentDateProvider;
import io.sentry.util.FileUtils;
import io.sentry.util.HintUtils;
import io.sentry.util.Objects;
import java.io.File;
import java.io.FileOutputStream;

public final class AndroidEnvelopeCache extends EnvelopeCache {
   public static final String LAST_ANR_REPORT = "last_anr_report";
   private final ICurrentDateProvider currentDateProvider;

   public AndroidEnvelopeCache(SentryAndroidOptions var1) {
      this(var1, AndroidCurrentDateProvider.getInstance());
   }

   AndroidEnvelopeCache(SentryAndroidOptions var1, ICurrentDateProvider var2) {
      super(var1, Objects.requireNonNull(var1.getCacheDirPath(), "cacheDirPath must not be null"), var1.getMaxCacheItems());
      this.currentDateProvider = var2;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static boolean hasStartupCrashMarker(SentryOptions var0) {
      String var2 = var0.getOutboxPath();
      if (var2 == null) {
         var0.getLogger().log(SentryLevel.DEBUG, "Outbox path is null, the startup crash marker file does not exist");
         return false;
      } else {
         File var9 = new File(var2, "startup_crash");

         boolean var1;
         try {
            var1 = var9.exists();
         } catch (Throwable var8) {
            var0.getLogger().log(SentryLevel.ERROR, "Error reading/deleting the startup crash marker file on the disk", var8);
            return false;
         }

         if (var1) {
            try {
               if (!var9.delete()) {
                  var0.getLogger().log(SentryLevel.ERROR, "Failed to delete the startup crash marker file. %s.", var9.getAbsolutePath());
               }
            } catch (Throwable var7) {
               var0.getLogger().log(SentryLevel.ERROR, "Error reading/deleting the startup crash marker file on the disk", var7);
               return false;
            }
         }

         return var1;
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static Long lastReportedAnr(SentryOptions var0) {
      File var2 = new File(Objects.requireNonNull(var0.getCacheDirPath(), "Cache dir path should be set for getting ANRs reported"), "last_anr_report");
      Long var1 = null;

      try {
         if (!var2.exists() || !var2.canRead()) {
            var0.getLogger().log(SentryLevel.DEBUG, "Last ANR marker does not exist. %s.", var2.getAbsolutePath());
            return null;
         }

         String var7 = FileUtils.readText(var2);
         if (var7.equals("null")) {
            return var1;
         }

         var1 = Long.parseLong(var7.trim());
      } catch (Throwable var4) {
         var0.getLogger().log(SentryLevel.ERROR, "Error reading last ANR marker", var4);
         return null;
      }

      return var1;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private void writeLastReportedAnrMarker(Long var1) {
      String var2 = this.options.getCacheDirPath();
      if (var2 == null) {
         this.options.getLogger().log(SentryLevel.DEBUG, "Cache dir path is null, the ANR marker will not be written");
      } else {
         File var3 = new File(var2, "last_anr_report");

         try {
            var47 = new FileOutputStream(var3);
         } catch (Throwable var43) {
            this.options.getLogger().log(SentryLevel.ERROR, "Error writing the ANR marker to the disk", var43);
            return;
         }

         try {
            var47.write(String.valueOf(var1).getBytes(UTF_8));
            var47.flush();
         } catch (Throwable var45) {
            Throwable var46 = var45;

            try {
               var47.close();
            } catch (Throwable var44) {
               Throwable var48 = var44;

               label177:
               try {
                  var46.addSuppressed(var48);
                  break label177;
               } catch (Throwable var42) {
                  this.options.getLogger().log(SentryLevel.ERROR, "Error writing the ANR marker to the disk", var42);
                  return;
               }
            }

            try {
               throw var46;
            } catch (Throwable var41) {
               this.options.getLogger().log(SentryLevel.ERROR, "Error writing the ANR marker to the disk", var41);
               return;
            }
         }

         try {
            var47.close();
         } catch (Throwable var40) {
            this.options.getLogger().log(SentryLevel.ERROR, "Error writing the ANR marker to the disk", var40);
            return;
         }
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private void writeStartupCrashMarkerFile() {
      String var1 = this.options.getOutboxPath();
      if (var1 == null) {
         this.options.getLogger().log(SentryLevel.DEBUG, "Outbox path is null, the startup crash marker file will not be written");
      } else {
         File var4 = new File(var1, "startup_crash");

         try {
            var4.createNewFile();
         } catch (Throwable var3) {
            this.options.getLogger().log(SentryLevel.ERROR, "Error writing the startup crash marker file to the disk", var3);
            return;
         }
      }
   }

   public File getDirectory() {
      return this.directory;
   }

   @Override
   public void store(SentryEnvelope var1, Hint var2) {
      super.store(var1, var2);
      SentryAndroidOptions var6 = (SentryAndroidOptions)this.options;
      TimeSpan var5 = AppStartMetrics.getInstance().getSdkInitTimeSpan();
      if (HintUtils.hasType(var2, UncaughtExceptionHandlerIntegration.UncaughtExceptionHint.class) && var5.hasStarted()) {
         long var3 = this.currentDateProvider.getCurrentTimeMillis() - var5.getStartUptimeMs();
         if (var3 <= var6.getStartupCrashDurationThresholdMillis()) {
            var6.getLogger()
               .log(SentryLevel.DEBUG, "Startup Crash detected %d milliseconds after SDK init. Writing a startup crash marker file to disk.", var3);
            this.writeStartupCrashMarkerFile();
         }
      }

      HintUtils.runIfHasType(var2, AnrV2Integration.AnrV2Hint.class, new AndroidEnvelopeCache$$ExternalSyntheticLambda0(this, var6));
   }
}
