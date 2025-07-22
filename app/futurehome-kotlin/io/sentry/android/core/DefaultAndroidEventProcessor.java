package io.sentry.android.core;

import android.content.Context;
import android.content.pm.PackageInfo;
import io.sentry.DateUtils;
import io.sentry.EventProcessor;
import io.sentry.Hint;
import io.sentry.SentryBaseEvent;
import io.sentry.SentryEvent;
import io.sentry.SentryLevel;
import io.sentry.SentryReplayEvent;
import io.sentry.android.core.internal.util.AndroidMainThreadChecker;
import io.sentry.android.core.performance.AppStartMetrics;
import io.sentry.android.core.performance.TimeSpan;
import io.sentry.protocol.App;
import io.sentry.protocol.OperatingSystem;
import io.sentry.protocol.SentryException;
import io.sentry.protocol.SentryStackFrame;
import io.sentry.protocol.SentryStackTrace;
import io.sentry.protocol.SentryThread;
import io.sentry.protocol.SentryTransaction;
import io.sentry.protocol.User;
import io.sentry.util.HintUtils;
import io.sentry.util.Objects;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

final class DefaultAndroidEventProcessor implements EventProcessor {
   private final BuildInfoProvider buildInfoProvider;
   final Context context;
   private final Future<DeviceInfoUtil> deviceInfoUtil;
   private final SentryAndroidOptions options;

   public DefaultAndroidEventProcessor(Context var1, BuildInfoProvider var2, SentryAndroidOptions var3) {
      this.context = Objects.requireNonNull(ContextUtils.getApplicationContext(var1), "The application context is required.");
      this.buildInfoProvider = Objects.requireNonNull(var2, "The BuildInfoProvider is required.");
      this.options = Objects.requireNonNull(var3, "The options object is required.");
      ExecutorService var4 = Executors.newSingleThreadExecutor();
      this.deviceInfoUtil = var4.submit(new DefaultAndroidEventProcessor$$ExternalSyntheticLambda0(this, var3));
      var4.shutdown();
   }

   private static void fixExceptionOrder(SentryEvent var0) {
      List var2 = var0.getExceptions();
      if (var2 != null && var2.size() > 1) {
         SentryException var1 = (SentryException)var2.get(var2.size() - 1);
         if ("java.lang".equals(var1.getModule())) {
            SentryStackTrace var3 = var1.getStacktrace();
            if (var3 != null) {
               List var4 = var3.getFrames();
               if (var4 != null) {
                  Iterator var5 = var4.iterator();

                  while (var5.hasNext()) {
                     if ("com.android.internal.os.RuntimeInit$MethodAndArgsCaller".equals(((SentryStackFrame)var5.next()).getModule())) {
                        Collections.reverse(var2);
                        break;
                     }
                  }
               }
            }
         }
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private void mergeOS(SentryBaseEvent var1) {
      OperatingSystem var3 = var1.getContexts().getOperatingSystem();

      label40:
      try {
         OperatingSystem var2 = this.deviceInfoUtil.get().getOperatingSystem();
         var1.getContexts().setOperatingSystem(var2);
      } catch (Throwable var6) {
         this.options.getLogger().log(SentryLevel.ERROR, "Failed to retrieve os system", var6);
         break label40;
      }

      if (var3 != null) {
         String var7 = var3.getName();
         if (var7 != null && !var7.isEmpty()) {
            StringBuilder var4 = new StringBuilder("os_");
            var4.append(var7.trim().toLowerCase(Locale.ROOT));
            var7 = var4.toString();
         } else {
            var7 = "os_1";
         }

         var1.getContexts().put(var7, var3);
      }
   }

   private void mergeUser(SentryBaseEvent var1) {
      User var3 = var1.getUser();
      User var2 = var3;
      if (var3 == null) {
         var2 = new User();
         var1.setUser(var2);
      }

      if (var2.getId() == null) {
         var2.setId(Installation.id(this.context));
      }

      if (var2.getIpAddress() == null && this.options.isSendDefaultPii()) {
         var2.setIpAddress("{{auto}}");
      }
   }

   private void processNonCachedEvent(SentryBaseEvent var1, Hint var2) {
      App var4 = var1.getContexts().getApp();
      App var3 = var4;
      if (var4 == null) {
         var3 = new App();
      }

      this.setAppExtras(var3, var2);
      this.setPackageInfo(var1, var3);
      var1.getContexts().setApp(var3);
   }

   private void setAppExtras(App var1, Hint var2) {
      var1.setAppName(ContextUtils.getApplicationName(this.context));
      TimeSpan var3 = AppStartMetrics.getInstance().getAppStartTimeSpanWithFallback(this.options);
      if (var3.hasStarted()) {
         var1.setAppStartTime(DateUtils.toUtilDate(var3.getStartTimestamp()));
      }

      if (!HintUtils.isFromHybridSdk(var2) && var1.getInForeground() == null) {
         Boolean var4 = AppState.getInstance().isInBackground();
         if (var4 != null) {
            var1.setInForeground(var4 ^ true);
         }
      }
   }

   private void setCommons(SentryBaseEvent var1, boolean var2, boolean var3) {
      this.mergeUser(var1);
      this.setDevice(var1, var2, var3);
      this.setSideLoadedInfo(var1);
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private void setDevice(SentryBaseEvent var1, boolean var2, boolean var3) {
      if (var1.getContexts().getDevice() == null) {
         label21:
         try {
            var1.getContexts().setDevice(this.deviceInfoUtil.get().collectDeviceInformation(var2, var3));
         } catch (Throwable var6) {
            this.options.getLogger().log(SentryLevel.ERROR, "Failed to retrieve device info", var6);
            break label21;
         }

         this.mergeOS(var1);
      }
   }

   private void setDist(SentryBaseEvent var1, String var2) {
      if (var1.getDist() == null) {
         var1.setDist(var2);
      }
   }

   private void setPackageInfo(SentryBaseEvent var1, App var2) {
      PackageInfo var3 = ContextUtils.getPackageInfo(this.context, 4096, this.options.getLogger(), this.buildInfoProvider);
      if (var3 != null) {
         this.setDist(var1, ContextUtils.getVersionCode(var3, this.buildInfoProvider));
         ContextUtils.setAppPackageInfo(var3, this.buildInfoProvider, var2);
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private void setSideLoadedInfo(SentryBaseEvent var1) {
      ContextUtils.SideLoadedInfo var2;
      try {
         var2 = this.deviceInfoUtil.get().getSideLoadedInfo();
      } catch (Throwable var15) {
         this.options.getLogger().log(SentryLevel.ERROR, "Error getting side loaded info.", var15);
         return;
      }

      if (var2 != null) {
         try {
            var16 = var2.asTags().entrySet().iterator();
         } catch (Throwable var14) {
            this.options.getLogger().log(SentryLevel.ERROR, "Error getting side loaded info.", var14);
            return;
         }

         while (true) {
            try {
               if (!var16.hasNext()) {
                  break;
               }

               Entry var3 = (Entry)var16.next();
               var1.setTag((String)var3.getKey(), (String)var3.getValue());
            } catch (Throwable var13) {
               this.options.getLogger().log(SentryLevel.ERROR, "Error getting side loaded info.", var13);
               break;
            }
         }
      }
   }

   private void setThreads(SentryEvent var1, Hint var2) {
      if (var1.getThreads() != null) {
         boolean var3 = HintUtils.isFromHybridSdk(var2);

         for (SentryThread var6 : var1.getThreads()) {
            boolean var4 = AndroidMainThreadChecker.getInstance().isMainThread(var6);
            if (var6.isCurrent() == null) {
               var6.setCurrent(var4);
            }

            if (!var3 && var6.isMain() == null) {
               var6.setMain(var4);
            }
         }
      }
   }

   private boolean shouldApplyScopeData(SentryBaseEvent var1, Hint var2) {
      if (HintUtils.shouldApplyScopeData(var2)) {
         return true;
      } else {
         this.options
            .getLogger()
            .log(SentryLevel.DEBUG, "Event was cached so not applying data relevant to the current app execution/version: %s", var1.getEventId());
         return false;
      }
   }

   public User getDefaultUser(Context var1) {
      User var2 = new User();
      var2.setId(Installation.id(var1));
      return var2;
   }

   @Override
   public SentryEvent process(SentryEvent var1, Hint var2) {
      boolean var3 = this.shouldApplyScopeData(var1, var2);
      if (var3) {
         this.processNonCachedEvent(var1, var2);
         this.setThreads(var1, var2);
      }

      this.setCommons(var1, true, var3);
      fixExceptionOrder(var1);
      return var1;
   }

   @Override
   public SentryReplayEvent process(SentryReplayEvent var1, Hint var2) {
      boolean var3 = this.shouldApplyScopeData(var1, var2);
      if (var3) {
         this.processNonCachedEvent(var1, var2);
      }

      this.setCommons(var1, false, var3);
      return var1;
   }

   @Override
   public SentryTransaction process(SentryTransaction var1, Hint var2) {
      boolean var3 = this.shouldApplyScopeData(var1, var2);
      if (var3) {
         this.processNonCachedEvent(var1, var2);
      }

      this.setCommons(var1, false, var3);
      return var1;
   }
}
