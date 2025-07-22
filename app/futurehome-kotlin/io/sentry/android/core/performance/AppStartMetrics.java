package io.sentry.android.core.performance;

import android.app.Activity;
import android.app.Application;
import android.content.ContentProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import io.sentry.ITransactionProfiler;
import io.sentry.NoOpLogger;
import io.sentry.TracesSamplingDecision;
import io.sentry.android.core.BuildInfoProvider;
import io.sentry.android.core.ContextUtils;
import io.sentry.android.core.SentryAndroidOptions;
import io.sentry.android.core.internal.util.FirstDrawDoneListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class AppStartMetrics extends ActivityLifecycleCallbacksAdapter {
   private static long CLASS_LOADED_UPTIME_MS = SystemClock.uptimeMillis();
   private static volatile AppStartMetrics instance;
   private final AtomicInteger activeActivitiesCounter;
   private final List<ActivityLifecycleTimeSpan> activityLifecycles;
   private boolean appLaunchedInForeground;
   private ITransactionProfiler appStartProfiler;
   private TracesSamplingDecision appStartSamplingDecision;
   private final TimeSpan appStartSpan;
   private AppStartMetrics.AppStartType appStartType = AppStartMetrics.AppStartType.UNKNOWN;
   private final TimeSpan applicationOnCreate;
   private final Map<ContentProvider, TimeSpan> contentProviderOnCreates;
   private final AtomicBoolean firstDrawDone;
   private boolean isCallbackRegistered;
   private final TimeSpan sdkInitTimeSpan;
   private boolean shouldSendStartMeasurements;

   public AppStartMetrics() {
      this.appStartProfiler = null;
      this.appStartSamplingDecision = null;
      this.isCallbackRegistered = false;
      this.shouldSendStartMeasurements = true;
      this.activeActivitiesCounter = new AtomicInteger();
      this.firstDrawDone = new AtomicBoolean(false);
      this.appStartSpan = new TimeSpan();
      this.sdkInitTimeSpan = new TimeSpan();
      this.applicationOnCreate = new TimeSpan();
      this.contentProviderOnCreates = new HashMap<>();
      this.activityLifecycles = new ArrayList<>();
      this.appLaunchedInForeground = ContextUtils.isForegroundImportance();
   }

   private void checkCreateTimeOnMain() {
      new Handler(Looper.getMainLooper()).post(new AppStartMetrics$$ExternalSyntheticLambda0(this));
   }

   public static AppStartMetrics getInstance() {
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
      // 00: getstatic io/sentry/android/core/performance/AppStartMetrics.instance Lio/sentry/android/core/performance/AppStartMetrics;
      // 03: ifnonnull 27
      // 06: ldc io/sentry/android/core/performance/AppStartMetrics
      // 08: monitorenter
      // 09: getstatic io/sentry/android/core/performance/AppStartMetrics.instance Lio/sentry/android/core/performance/AppStartMetrics;
      // 0c: ifnonnull 1b
      // 0f: new io/sentry/android/core/performance/AppStartMetrics
      // 12: astore 0
      // 13: aload 0
      // 14: invokespecial io/sentry/android/core/performance/AppStartMetrics.<init> ()V
      // 17: aload 0
      // 18: putstatic io/sentry/android/core/performance/AppStartMetrics.instance Lio/sentry/android/core/performance/AppStartMetrics;
      // 1b: ldc io/sentry/android/core/performance/AppStartMetrics
      // 1d: monitorexit
      // 1e: goto 27
      // 21: astore 0
      // 22: ldc io/sentry/android/core/performance/AppStartMetrics
      // 24: monitorexit
      // 25: aload 0
      // 26: athrow
      // 27: getstatic io/sentry/android/core/performance/AppStartMetrics.instance Lio/sentry/android/core/performance/AppStartMetrics;
      // 2a: areturn
   }

   public static void onApplicationCreate(Application var0) {
      long var1 = SystemClock.uptimeMillis();
      AppStartMetrics var3 = getInstance();
      if (var3.applicationOnCreate.hasNotStarted()) {
         var3.applicationOnCreate.setStartedAt(var1);
         var3.registerLifecycleCallbacks(var0);
      }
   }

   public static void onApplicationPostCreate(Application var0) {
      long var1 = SystemClock.uptimeMillis();
      AppStartMetrics var4 = getInstance();
      if (var4.applicationOnCreate.hasNotStopped()) {
         TimeSpan var5 = var4.applicationOnCreate;
         StringBuilder var3 = new StringBuilder();
         var3.append(var0.getClass().getName());
         var3.append(".onCreate");
         var5.setDescription(var3.toString());
         var4.applicationOnCreate.setStoppedAt(var1);
      }
   }

   public static void onContentProviderCreate(ContentProvider var0) {
      long var1 = SystemClock.uptimeMillis();
      TimeSpan var3 = new TimeSpan();
      var3.setStartedAt(var1);
      getInstance().contentProviderOnCreates.put(var0, var3);
   }

   public static void onContentProviderPostCreate(ContentProvider var0) {
      long var1 = SystemClock.uptimeMillis();
      TimeSpan var3 = getInstance().contentProviderOnCreates.get(var0);
      if (var3 != null && var3.hasNotStopped()) {
         StringBuilder var4 = new StringBuilder();
         var4.append(var0.getClass().getName());
         var4.append(".onCreate");
         var3.setDescription(var4.toString());
         var3.setStoppedAt(var1);
      }
   }

   public void addActivityLifecycleTimeSpans(ActivityLifecycleTimeSpan var1) {
      this.activityLifecycles.add(var1);
   }

   public void clear() {
      this.appStartType = AppStartMetrics.AppStartType.UNKNOWN;
      this.appStartSpan.reset();
      this.sdkInitTimeSpan.reset();
      this.applicationOnCreate.reset();
      this.contentProviderOnCreates.clear();
      this.activityLifecycles.clear();
      ITransactionProfiler var1 = this.appStartProfiler;
      if (var1 != null) {
         var1.close();
      }

      this.appStartProfiler = null;
      this.appStartSamplingDecision = null;
      this.appLaunchedInForeground = false;
      this.isCallbackRegistered = false;
      this.shouldSendStartMeasurements = true;
      this.firstDrawDone.set(false);
      this.activeActivitiesCounter.set(0);
   }

   public List<ActivityLifecycleTimeSpan> getActivityLifecycleTimeSpans() {
      ArrayList var1 = new ArrayList<>(this.activityLifecycles);
      Collections.sort(var1);
      return var1;
   }

   public ITransactionProfiler getAppStartProfiler() {
      return this.appStartProfiler;
   }

   public TracesSamplingDecision getAppStartSamplingDecision() {
      return this.appStartSamplingDecision;
   }

   public TimeSpan getAppStartTimeSpan() {
      return this.appStartSpan;
   }

   public TimeSpan getAppStartTimeSpanWithFallback(SentryAndroidOptions var1) {
      if (this.appStartType != AppStartMetrics.AppStartType.UNKNOWN && this.appLaunchedInForeground) {
         if (var1.isEnablePerformanceV2()) {
            TimeSpan var2 = this.getAppStartTimeSpan();
            if (var2.hasStarted() && var2.getDurationMs() <= TimeUnit.MINUTES.toMillis(1L)) {
               return var2;
            }
         }

         TimeSpan var3 = this.getSdkInitTimeSpan();
         if (var3.hasStarted() && var3.getDurationMs() <= TimeUnit.MINUTES.toMillis(1L)) {
            return var3;
         }
      }

      return new TimeSpan();
   }

   public AppStartMetrics.AppStartType getAppStartType() {
      return this.appStartType;
   }

   public TimeSpan getApplicationOnCreateTimeSpan() {
      return this.applicationOnCreate;
   }

   public long getClassLoadedUptimeMs() {
      return CLASS_LOADED_UPTIME_MS;
   }

   public List<TimeSpan> getContentProviderOnCreateTimeSpans() {
      ArrayList var1 = new ArrayList<>(this.contentProviderOnCreates.values());
      Collections.sort(var1);
      return var1;
   }

   public TimeSpan getSdkInitTimeSpan() {
      return this.sdkInitTimeSpan;
   }

   public boolean isAppLaunchedInForeground() {
      return this.appLaunchedInForeground;
   }

   @Override
   public void onActivityCreated(Activity var1, Bundle var2) {
      long var5 = SystemClock.uptimeMillis();
      if (this.activeActivitiesCounter.incrementAndGet() == 1 && !this.firstDrawDone.get()) {
         long var3 = this.appStartSpan.getStartUptimeMs();
         if (this.appLaunchedInForeground && var5 - var3 <= TimeUnit.MINUTES.toMillis(1L)) {
            AppStartMetrics.AppStartType var7;
            if (var2 == null) {
               var7 = AppStartMetrics.AppStartType.COLD;
            } else {
               var7 = AppStartMetrics.AppStartType.WARM;
            }

            this.appStartType = var7;
         } else {
            this.appStartType = AppStartMetrics.AppStartType.WARM;
            this.shouldSendStartMeasurements = true;
            this.appStartSpan.reset();
            this.appStartSpan.start();
            this.appStartSpan.setStartedAt(var5);
            CLASS_LOADED_UPTIME_MS = var5;
            this.contentProviderOnCreates.clear();
            this.applicationOnCreate.reset();
         }
      }

      this.appLaunchedInForeground = true;
   }

   @Override
   public void onActivityDestroyed(Activity var1) {
      if (this.activeActivitiesCounter.decrementAndGet() == 0 && !var1.isChangingConfigurations()) {
         this.appLaunchedInForeground = false;
         this.shouldSendStartMeasurements = true;
         this.firstDrawDone.set(false);
      }
   }

   @Override
   public void onActivityStarted(Activity var1) {
      if (!this.firstDrawDone.get()) {
         if (var1.getWindow() != null) {
            FirstDrawDoneListener.registerForNextDraw(
               var1, new AppStartMetrics$$ExternalSyntheticLambda2(this), new BuildInfoProvider(NoOpLogger.getInstance())
            );
         } else {
            new Handler(Looper.getMainLooper()).post(new AppStartMetrics$$ExternalSyntheticLambda3(this));
         }
      }
   }

   public void onAppStartSpansSent() {
      this.shouldSendStartMeasurements = false;
      this.contentProviderOnCreates.clear();
      this.activityLifecycles.clear();
   }

   void onFirstFrameDrawn() {
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
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield io/sentry/android/core/performance/AppStartMetrics.firstDrawDone Ljava/util/concurrent/atomic/AtomicBoolean;
      // 06: bipush 1
      // 07: invokevirtual java/util/concurrent/atomic/AtomicBoolean.getAndSet (Z)Z
      // 0a: ifne 1f
      // 0d: invokestatic io/sentry/android/core/performance/AppStartMetrics.getInstance ()Lio/sentry/android/core/performance/AppStartMetrics;
      // 10: astore 1
      // 11: aload 1
      // 12: invokevirtual io/sentry/android/core/performance/AppStartMetrics.getSdkInitTimeSpan ()Lio/sentry/android/core/performance/TimeSpan;
      // 15: invokevirtual io/sentry/android/core/performance/TimeSpan.stop ()V
      // 18: aload 1
      // 19: invokevirtual io/sentry/android/core/performance/AppStartMetrics.getAppStartTimeSpan ()Lio/sentry/android/core/performance/TimeSpan;
      // 1c: invokevirtual io/sentry/android/core/performance/TimeSpan.stop ()V
      // 1f: aload 0
      // 20: monitorexit
      // 21: return
      // 22: astore 1
      // 23: aload 0
      // 24: monitorexit
      // 25: aload 1
      // 26: athrow
   }

   public void registerLifecycleCallbacks(Application var1) {
      if (!this.isCallbackRegistered) {
         boolean var3 = true;
         this.isCallbackRegistered = true;
         boolean var2 = var3;
         if (!this.appLaunchedInForeground) {
            if (ContextUtils.isForegroundImportance()) {
               var2 = var3;
            } else {
               var2 = false;
            }
         }

         this.appLaunchedInForeground = var2;
         var1.registerActivityLifecycleCallbacks(instance);
         new Handler(Looper.getMainLooper()).post(new AppStartMetrics$$ExternalSyntheticLambda1(this));
      }
   }

   public void setAppLaunchedInForeground(boolean var1) {
      this.appLaunchedInForeground = var1;
   }

   public void setAppStartProfiler(ITransactionProfiler var1) {
      this.appStartProfiler = var1;
   }

   public void setAppStartSamplingDecision(TracesSamplingDecision var1) {
      this.appStartSamplingDecision = var1;
   }

   public void setAppStartType(AppStartMetrics.AppStartType var1) {
      this.appStartType = var1;
   }

   public void setClassLoadedUptimeMs(long var1) {
      CLASS_LOADED_UPTIME_MS = var1;
   }

   public boolean shouldSendStartMeasurements() {
      boolean var1;
      if (this.shouldSendStartMeasurements && this.appLaunchedInForeground) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static enum AppStartType {
      COLD,
      UNKNOWN,
      WARM;
      private static final AppStartMetrics.AppStartType[] $VALUES = $values();
   }
}
