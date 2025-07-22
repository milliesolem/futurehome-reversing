package io.sentry.android.core;

import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import io.sentry.BackfillingEventProcessor;
import io.sentry.Breadcrumb;
import io.sentry.Hint;
import io.sentry.SentryBaseEvent;
import io.sentry.SentryEvent;
import io.sentry.SentryExceptionFactory;
import io.sentry.SentryLevel;
import io.sentry.SentryStackTraceFactory;
import io.sentry.SpanContext;
import io.sentry.android.core.internal.util.CpuInfoUtils;
import io.sentry.cache.PersistingOptionsObserver;
import io.sentry.cache.PersistingScopeObserver;
import io.sentry.hints.AbnormalExit;
import io.sentry.hints.Backfillable;
import io.sentry.protocol.App;
import io.sentry.protocol.Contexts;
import io.sentry.protocol.DebugImage;
import io.sentry.protocol.DebugMeta;
import io.sentry.protocol.Device;
import io.sentry.protocol.Mechanism;
import io.sentry.protocol.OperatingSystem;
import io.sentry.protocol.Request;
import io.sentry.protocol.SdkVersion;
import io.sentry.protocol.SentryStackTrace;
import io.sentry.protocol.SentryThread;
import io.sentry.protocol.SentryTransaction;
import io.sentry.protocol.User;
import io.sentry.util.HintUtils;
import io.sentry.util.SentryRandom;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

public final class AnrV2EventProcessor implements BackfillingEventProcessor {
   private final BuildInfoProvider buildInfoProvider;
   private final Context context;
   private final SentryAndroidOptions options;
   private final SentryExceptionFactory sentryExceptionFactory;

   public AnrV2EventProcessor(Context var1, SentryAndroidOptions var2, BuildInfoProvider var3) {
      this.context = ContextUtils.getApplicationContext(var1);
      this.options = var2;
      this.buildInfoProvider = var3;
      this.sentryExceptionFactory = new SentryExceptionFactory(new SentryStackTraceFactory(var2));
   }

   private void backfillOptions(SentryEvent var1, Object var2) {
      this.setRelease(var1);
      this.setEnvironment(var1);
      this.setDist(var1);
      this.setDebugMeta(var1);
      this.setSdk(var1);
      this.setApp(var1, var2);
      this.setOptionsTags(var1);
   }

   private void backfillScope(SentryEvent var1, Object var2) {
      this.setRequest(var1);
      this.setUser(var1);
      this.setScopeTags(var1);
      this.setBreadcrumbs(var1);
      this.setExtras(var1);
      this.setContexts(var1);
      this.setTransaction(var1);
      this.setFingerprints(var1, var2);
      this.setLevel(var1);
      this.setTrace(var1);
      this.setReplayId(var1);
   }

   private SentryThread findMainThread(List<SentryThread> var1) {
      if (var1 != null) {
         for (SentryThread var3 : var1) {
            String var4 = var3.getName();
            if (var4 != null && var4.equals("main")) {
               return var3;
            }
         }
      }

      return null;
   }

   private Device getDevice() {
      Device var1 = new Device();
      if (this.options.isSendDefaultPii()) {
         var1.setName(ContextUtils.getDeviceName(this.context));
      }

      var1.setManufacturer(Build.MANUFACTURER);
      var1.setBrand(Build.BRAND);
      var1.setFamily(ContextUtils.getFamily(this.options.getLogger()));
      var1.setModel(Build.MODEL);
      var1.setModelId(Build.ID);
      var1.setArchs(ContextUtils.getArchitectures(this.buildInfoProvider));
      MemoryInfo var2 = ContextUtils.getMemInfo(this.context, this.options.getLogger());
      if (var2 != null) {
         var1.setMemorySize(this.getMemorySize(var2));
      }

      var1.setSimulator(this.buildInfoProvider.isEmulator());
      DisplayMetrics var3 = ContextUtils.getDisplayMetrics(this.context, this.options.getLogger());
      if (var3 != null) {
         var1.setScreenWidthPixels(var3.widthPixels);
         var1.setScreenHeightPixels(var3.heightPixels);
         var1.setScreenDensity(var3.density);
         var1.setScreenDpi(var3.densityDpi);
      }

      if (var1.getId() == null) {
         var1.setId(this.getDeviceId());
      }

      List var4 = CpuInfoUtils.getInstance().readMaxFrequencies();
      if (!var4.isEmpty()) {
         var1.setProcessorFrequency(Collections.max(var4).doubleValue());
         var1.setProcessorCount(var4.size());
      }

      return var1;
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

   private Long getMemorySize(MemoryInfo var1) {
      return var1.totalMem;
   }

   private boolean isBackgroundAnr(Object var1) {
      return var1 instanceof AbnormalExit ? "anr_background".equals(((AbnormalExit)var1).mechanism()) : false;
   }

   private void mergeOS(SentryBaseEvent var1) {
      OperatingSystem var3 = var1.getContexts().getOperatingSystem();
      OperatingSystem var2 = DeviceInfoUtil.getInstance(this.context, this.options).getOperatingSystem();
      var1.getContexts().setOperatingSystem(var2);
      if (var3 != null) {
         String var4 = var3.getName();
         String var5;
         if (var4 != null && !var4.isEmpty()) {
            StringBuilder var6 = new StringBuilder("os_");
            var6.append(var4.trim().toLowerCase(Locale.ROOT));
            var5 = var6.toString();
         } else {
            var5 = "os_1";
         }

         var1.getContexts().put(var5, var3);
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
         var2.setId(this.getDeviceId());
      }

      if (var2.getIpAddress() == null && this.options.isSendDefaultPii()) {
         var2.setIpAddress("{{auto}}");
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private boolean sampleReplay(SentryEvent var1) {
      String var2 = PersistingOptionsObserver.read(this.options, "replay-error-sample-rate.json", String.class);
      if (var2 == null) {
         return false;
      } else {
         try {
            if (Double.parseDouble(var2) < SentryRandom.current().nextDouble()) {
               this.options.getLogger().log(SentryLevel.DEBUG, "Not capturing replay for ANR %s due to not being sampled.", var1.getEventId());
               return false;
            } else {
               return true;
            }
         } catch (Throwable var4) {
            this.options.getLogger().log(SentryLevel.ERROR, "Error parsing replay sample rate.", var4);
            return false;
         }
      }
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private void setApp(SentryBaseEvent var1, Object var2) {
      App var4 = var1.getContexts().getApp();
      App var3 = var4;
      if (var4 == null) {
         var3 = new App();
      }

      var3.setAppName(ContextUtils.getApplicationName(this.context));
      var3.setInForeground(this.isBackgroundAnr(var2) ^ true);
      var2 = ContextUtils.getPackageInfo(this.context, this.buildInfoProvider);
      if (var2 != null) {
         var3.setAppIdentifier(var2.packageName);
      }

      String var10;
      if (var1.getRelease() != null) {
         var10 = var1.getRelease();
      } else {
         var10 = PersistingOptionsObserver.read(this.options, "release.json", String.class);
      }

      if (var10 != null) {
         boolean var7 = false /* VF: Semaphore variable */;

         label35:
         try {
            var7 = true;
            String var11 = var10.substring(var10.indexOf(64) + 1, var10.indexOf(43));
            String var5 = var10.substring(var10.indexOf(43) + 1);
            var3.setAppVersion(var11);
            var3.setAppBuild(var5);
            var7 = false;
         } finally {
            if (var7) {
               this.options.getLogger().log(SentryLevel.WARNING, "Failed to parse release from scope cache: %s", var10);
               break label35;
            }
         }
      }

      var1.getContexts().setApp(var3);
   }

   private void setBreadcrumbs(SentryBaseEvent var1) {
      List var2 = PersistingScopeObserver.read(this.options, "breadcrumbs.json", List.class, new Breadcrumb.Deserializer());
      if (var2 != null) {
         if (var1.getBreadcrumbs() == null) {
            var1.setBreadcrumbs(new ArrayList<>(var2));
         } else {
            var1.getBreadcrumbs().addAll(var2);
         }
      }
   }

   private void setContexts(SentryBaseEvent var1) {
      Contexts var2 = PersistingScopeObserver.read(this.options, "contexts.json", Contexts.class);
      if (var2 != null) {
         Contexts var5 = var1.getContexts();

         for (Entry var3 : new Contexts(var2).entrySet()) {
            Object var4 = var3.getValue();
            if ((!"trace".equals(var3.getKey()) || !(var4 instanceof SpanContext)) && !var5.containsKey(var3.getKey())) {
               var5.put((String)var3.getKey(), var4);
            }
         }
      }
   }

   private void setDebugMeta(SentryBaseEvent var1) {
      DebugMeta var3 = var1.getDebugMeta();
      DebugMeta var2 = var3;
      if (var3 == null) {
         var2 = new DebugMeta();
      }

      if (var2.getImages() == null) {
         var2.setImages(new ArrayList<>());
      }

      List var6 = var2.getImages();
      if (var6 != null) {
         String var5 = PersistingOptionsObserver.read(this.options, "proguard-uuid.json", String.class);
         if (var5 != null) {
            DebugImage var4 = new DebugImage();
            var4.setType("proguard");
            var4.setUuid(var5);
            var6.add(var4);
         }

         var1.setDebugMeta(var2);
      }
   }

   private void setDevice(SentryBaseEvent var1) {
      if (var1.getContexts().getDevice() == null) {
         var1.getContexts().setDevice(this.getDevice());
      }
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private void setDist(SentryBaseEvent var1) {
      if (var1.getDist() == null) {
         var1.setDist(PersistingOptionsObserver.read(this.options, "dist.json", String.class));
      }

      if (var1.getDist() == null) {
         String var2 = PersistingOptionsObserver.read(this.options, "release.json", String.class);
         if (var2 != null) {
            boolean var4 = false /* VF: Semaphore variable */;

            try {
               var4 = true;
               var1.setDist(var2.substring(var2.indexOf(43) + 1));
               var4 = false;
            } finally {
               if (var4) {
                  this.options.getLogger().log(SentryLevel.WARNING, "Failed to parse release from scope cache: %s", var2);
                  return;
               }
            }
         }
      }
   }

   private void setEnvironment(SentryBaseEvent var1) {
      if (var1.getEnvironment() == null) {
         String var2 = PersistingOptionsObserver.read(this.options, "environment.json", String.class);
         if (var2 == null) {
            var2 = this.options.getEnvironment();
         }

         var1.setEnvironment(var2);
      }
   }

   private void setExceptions(SentryEvent var1, Object var2) {
      Mechanism var4 = new Mechanism();
      if (!((Backfillable)var2).shouldEnrich()) {
         var4.setType("HistoricalAppExitInfo");
      } else {
         var4.setType("AppExitInfo");
      }

      if (this.isBackgroundAnr(var2)) {
         var2 = "Background ANR";
      } else {
         var2 = "ANR";
      }

      ApplicationNotResponding var5 = new ApplicationNotResponding(var2, Thread.currentThread());
      SentryThread var3 = this.findMainThread(var1.getThreads());
      SentryThread var7 = var3;
      if (var3 == null) {
         var7 = new SentryThread();
         var7.setStacktrace(new SentryStackTrace());
      }

      var1.setExceptions(this.sentryExceptionFactory.getSentryExceptionsFromThread(var7, var4, var5));
   }

   private void setExtras(SentryBaseEvent var1) {
      Map var2 = PersistingScopeObserver.read(this.options, "extras.json", Map.class);
      if (var2 != null) {
         if (var1.getExtras() == null) {
            var1.setExtras(new HashMap<>(var2));
         } else {
            for (Entry var3 : var2.entrySet()) {
               if (!var1.getExtras().containsKey(var3.getKey())) {
                  var1.getExtras().put((String)var3.getKey(), var3.getValue());
               }
            }
         }
      }
   }

   private void setFingerprints(SentryEvent var1, Object var2) {
      List var4 = PersistingScopeObserver.read(this.options, "fingerprint.json", List.class);
      if (var1.getFingerprints() == null) {
         var1.setFingerprints(var4);
      }

      boolean var3 = this.isBackgroundAnr(var2);
      if (var1.getFingerprints() == null) {
         if (var3) {
            var2 = "background-anr";
         } else {
            var2 = "foreground-anr";
         }

         var1.setFingerprints(Arrays.asList("{{ default }}", var2));
      }
   }

   private void setLevel(SentryEvent var1) {
      SentryLevel var2 = PersistingScopeObserver.read(this.options, "level.json", SentryLevel.class);
      if (var1.getLevel() == null) {
         var1.setLevel(var2);
      }
   }

   private void setOptionsTags(SentryBaseEvent var1) {
      Map var2 = PersistingOptionsObserver.read(this.options, "tags.json", Map.class);
      if (var2 != null) {
         if (var1.getTags() == null) {
            var1.setTags(new HashMap<>(var2));
         } else {
            for (Entry var4 : var2.entrySet()) {
               if (!var1.getTags().containsKey(var4.getKey())) {
                  var1.setTag((String)var4.getKey(), (String)var4.getValue());
               }
            }
         }
      }
   }

   private void setPlatform(SentryBaseEvent var1) {
      if (var1.getPlatform() == null) {
         var1.setPlatform("java");
      }
   }

   private void setRelease(SentryBaseEvent var1) {
      if (var1.getRelease() == null) {
         var1.setRelease(PersistingOptionsObserver.read(this.options, "release.json", String.class));
      }
   }

   private void setReplayId(SentryEvent var1) {
      String var8 = PersistingScopeObserver.read(this.options, "replay.json", String.class);
      String var9 = this.options.getCacheDirPath();
      StringBuilder var10 = new StringBuilder("replay_");
      var10.append(var8);
      if (!new File(var9, var10.toString()).exists()) {
         if (!this.sampleReplay(var1)) {
            return;
         }

         File[] var15 = new File(this.options.getCacheDirPath()).listFiles();
         var9 = null;
         var8 = null;
         if (var15 != null) {
            int var3 = var15.length;
            long var6 = Long.MIN_VALUE;
            int var2 = 0;

            while (true) {
               var9 = var8;
               if (var2 >= var3) {
                  break;
               }

               File var11 = var15[var2];
               var9 = var8;
               long var4 = var6;
               if (var11.isDirectory()) {
                  var9 = var8;
                  var4 = var6;
                  if (var11.getName().startsWith("replay_")) {
                     var9 = var8;
                     var4 = var6;
                     if (var11.lastModified() > var6) {
                        var9 = var8;
                        var4 = var6;
                        if (var11.lastModified() <= var1.getTimestamp().getTime()) {
                           var4 = var11.lastModified();
                           var9 = var11.getName().substring(7);
                        }
                     }
                  }
               }

               var2++;
               var8 = var9;
               var6 = var4;
            }
         }

         var8 = var9;
      }

      if (var8 != null) {
         PersistingScopeObserver.store(this.options, var8, "replay.json");
         var1.getContexts().put("replay_id", var8);
      }
   }

   private void setRequest(SentryBaseEvent var1) {
      if (var1.getRequest() == null) {
         var1.setRequest(PersistingScopeObserver.read(this.options, "request.json", Request.class));
      }
   }

   private void setScopeTags(SentryBaseEvent var1) {
      Map var2 = PersistingScopeObserver.read(this.options, "tags.json", Map.class);
      if (var2 != null) {
         if (var1.getTags() == null) {
            var1.setTags(new HashMap<>(var2));
         } else {
            for (Entry var4 : var2.entrySet()) {
               if (!var1.getTags().containsKey(var4.getKey())) {
                  var1.setTag((String)var4.getKey(), (String)var4.getValue());
               }
            }
         }
      }
   }

   private void setSdk(SentryBaseEvent var1) {
      if (var1.getSdk() == null) {
         var1.setSdk(PersistingOptionsObserver.read(this.options, "sdk-version.json", SdkVersion.class));
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private void setSideLoadedInfo(SentryBaseEvent var1) {
      ContextUtils.SideLoadedInfo var2;
      try {
         var2 = DeviceInfoUtil.getInstance(this.context, this.options).getSideLoadedInfo();
      } catch (Throwable var15) {
         this.options.getLogger().log(SentryLevel.ERROR, "Error getting side loaded info.", var15);
         return;
      }

      if (var2 != null) {
         Iterator var3;
         try {
            var3 = var2.asTags().entrySet().iterator();
         } catch (Throwable var14) {
            this.options.getLogger().log(SentryLevel.ERROR, "Error getting side loaded info.", var14);
            return;
         }

         while (true) {
            try {
               if (!var3.hasNext()) {
                  break;
               }

               Entry var16 = (Entry)var3.next();
               var1.setTag((String)var16.getKey(), (String)var16.getValue());
            } catch (Throwable var13) {
               this.options.getLogger().log(SentryLevel.ERROR, "Error getting side loaded info.", var13);
               break;
            }
         }
      }
   }

   private void setStaticValues(SentryEvent var1) {
      this.mergeUser(var1);
      this.setSideLoadedInfo(var1);
   }

   private void setTrace(SentryEvent var1) {
      SpanContext var2 = PersistingScopeObserver.read(this.options, "trace.json", SpanContext.class);
      if (var1.getContexts().getTrace() == null && var2 != null && var2.getSpanId() != null && var2.getTraceId() != null) {
         var1.getContexts().setTrace(var2);
      }
   }

   private void setTransaction(SentryEvent var1) {
      String var2 = PersistingScopeObserver.read(this.options, "transaction.json", String.class);
      if (var1.getTransaction() == null) {
         var1.setTransaction(var2);
      }
   }

   private void setUser(SentryBaseEvent var1) {
      if (var1.getUser() == null) {
         var1.setUser(PersistingScopeObserver.read(this.options, "user.json", User.class));
      }
   }

   @Override
   public SentryEvent process(SentryEvent var1, Hint var2) {
      Object var3 = HintUtils.getSentrySdkHint(var2);
      if (!(var3 instanceof Backfillable)) {
         this.options.getLogger().log(SentryLevel.WARNING, "The event is not Backfillable, but has been passed to BackfillingEventProcessor, skipping.");
         return var1;
      } else {
         this.setExceptions(var1, var3);
         this.setPlatform(var1);
         this.mergeOS(var1);
         this.setDevice(var1);
         if (!((Backfillable)var3).shouldEnrich()) {
            this.options.getLogger().log(SentryLevel.DEBUG, "The event is Backfillable, but should not be enriched, skipping.");
            return var1;
         } else {
            this.backfillScope(var1, var3);
            this.backfillOptions(var1, var3);
            this.setStaticValues(var1);
            return var1;
         }
      }
   }

   @Override
   public SentryTransaction process(SentryTransaction var1, Hint var2) {
      return var1;
   }
}
