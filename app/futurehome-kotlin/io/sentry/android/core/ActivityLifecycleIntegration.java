package io.sentry.android.core;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import android.os.SystemClock;
import io.sentry.FullyDisplayedReporter;
import io.sentry.IHub;
import io.sentry.IScope;
import io.sentry.ISentryExecutorService;
import io.sentry.ISpan;
import io.sentry.ITransaction;
import io.sentry.Instrumenter;
import io.sentry.Integration;
import io.sentry.MeasurementUnit;
import io.sentry.NoOpTransaction;
import io.sentry.SentryDate;
import io.sentry.SentryLevel;
import io.sentry.SentryNanotimeDate;
import io.sentry.SentryOptions;
import io.sentry.SpanStatus;
import io.sentry.TracesSamplingDecision;
import io.sentry.TransactionContext;
import io.sentry.TransactionOptions;
import io.sentry.android.core.performance.ActivityLifecycleTimeSpan;
import io.sentry.android.core.performance.AppStartMetrics;
import io.sentry.android.core.performance.TimeSpan;
import io.sentry.protocol.TransactionNameSource;
import io.sentry.util.IntegrationUtils;
import io.sentry.util.Objects;
import io.sentry.util.TracingUtils;
import java.io.Closeable;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.WeakHashMap;
import java.util.Map.Entry;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

public final class ActivityLifecycleIntegration implements Integration, Closeable, ActivityLifecycleCallbacks {
   static final String APP_START_COLD = "app.start.cold";
   static final String APP_START_WARM = "app.start.warm";
   private static final String TRACE_ORIGIN = "auto.ui.activity";
   static final String TTFD_OP = "ui.load.full_display";
   static final long TTFD_TIMEOUT_MILLIS = 25000L;
   static final String TTID_OP = "ui.load.initial_display";
   static final String UI_LOAD_OP = "ui.load";
   private final WeakHashMap<Activity, ITransaction> activitiesWithOngoingTransactions;
   private final ActivityFramesTracker activityFramesTracker;
   private final WeakHashMap<Activity, ActivityLifecycleTimeSpan> activityLifecycleMap;
   private ISpan appStartSpan;
   private final Application application;
   private final BuildInfoProvider buildInfoProvider;
   private boolean firstActivityCreated;
   private FullyDisplayedReporter fullyDisplayedReporter;
   private IHub hub;
   private boolean isAllActivityCallbacksAvailable;
   private SentryDate lastPausedTime;
   private long lastPausedUptimeMillis;
   private SentryAndroidOptions options;
   private boolean performanceEnabled = false;
   private boolean timeToFullDisplaySpanEnabled = false;
   private Future<?> ttfdAutoCloseFuture;
   private final WeakHashMap<Activity, ISpan> ttfdSpanMap;
   private final WeakHashMap<Activity, ISpan> ttidSpanMap;

   public ActivityLifecycleIntegration(Application var1, BuildInfoProvider var2, ActivityFramesTracker var3) {
      this.firstActivityCreated = false;
      this.fullyDisplayedReporter = null;
      this.ttidSpanMap = new WeakHashMap<>();
      this.ttfdSpanMap = new WeakHashMap<>();
      this.activityLifecycleMap = new WeakHashMap<>();
      this.lastPausedTime = new SentryNanotimeDate(new Date(0L), 0L);
      this.lastPausedUptimeMillis = 0L;
      this.ttfdAutoCloseFuture = null;
      this.activitiesWithOngoingTransactions = new WeakHashMap<>();
      this.application = Objects.requireNonNull(var1, "Application is required");
      this.buildInfoProvider = Objects.requireNonNull(var2, "BuildInfoProvider is required");
      this.activityFramesTracker = Objects.requireNonNull(var3, "ActivityFramesTracker is required");
      if (var2.getSdkInfoVersion() >= 29) {
         this.isAllActivityCallbacksAvailable = true;
      }
   }

   private void cancelTtfdAutoClose() {
      Future var1 = this.ttfdAutoCloseFuture;
      if (var1 != null) {
         var1.cancel(false);
         this.ttfdAutoCloseFuture = null;
      }
   }

   private void clear() {
      this.firstActivityCreated = false;
      this.activityLifecycleMap.clear();
   }

   private void finishAppStartSpan() {
      SentryDate var1 = AppStartMetrics.getInstance().getAppStartTimeSpanWithFallback(this.options).getProjectedStopTimestamp();
      if (this.performanceEnabled && var1 != null) {
         this.finishSpan(this.appStartSpan, var1);
      }
   }

   private void finishExceededTtfdSpan(ISpan var1, ISpan var2) {
      if (var1 != null && !var1.isFinished()) {
         var1.setDescription(this.getExceededTtfdDesc(var1));
         SentryDate var3;
         if (var2 != null) {
            var3 = var2.getFinishDate();
         } else {
            var3 = null;
         }

         if (var3 == null) {
            var3 = var1.getStartDate();
         }

         this.finishSpan(var1, var3, SpanStatus.DEADLINE_EXCEEDED);
      }
   }

   private void finishSpan(ISpan var1) {
      if (var1 != null && !var1.isFinished()) {
         var1.finish();
      }
   }

   private void finishSpan(ISpan var1, SentryDate var2) {
      this.finishSpan(var1, var2, null);
   }

   private void finishSpan(ISpan var1, SentryDate var2, SpanStatus var3) {
      if (var1 != null && !var1.isFinished()) {
         if (var3 == null) {
            if (var1.getStatus() != null) {
               var3 = var1.getStatus();
            } else {
               var3 = SpanStatus.OK;
            }
         }

         var1.finish(var3, var2);
      }
   }

   private void finishSpan(ISpan var1, SpanStatus var2) {
      if (var1 != null && !var1.isFinished()) {
         var1.finish(var2);
      }
   }

   private void finishTransaction(ITransaction var1, ISpan var2, ISpan var3) {
      if (var1 != null) {
         if (var1.isFinished()) {
            return;
         }

         this.finishSpan(var2, SpanStatus.DEADLINE_EXCEEDED);
         this.finishExceededTtfdSpan(var3, var2);
         this.cancelTtfdAutoClose();
         SpanStatus var6 = var1.getStatus();
         SpanStatus var4 = var6;
         if (var6 == null) {
            var4 = SpanStatus.OK;
         }

         var1.finish(var4);
         IHub var5 = this.hub;
         if (var5 != null) {
            var5.configureScope(new ActivityLifecycleIntegration$$ExternalSyntheticLambda8(this, var1));
         }
      }
   }

   private String getActivityName(Activity var1) {
      return var1.getClass().getSimpleName();
   }

   private String getAppStartDesc(boolean var1) {
      return var1 ? "Cold Start" : "Warm Start";
   }

   private String getAppStartOp(boolean var1) {
      return var1 ? "app.start.cold" : "app.start.warm";
   }

   private String getExceededTtfdDesc(ISpan var1) {
      String var2 = var1.getDescription();
      if (var2 != null && var2.endsWith(" - Deadline Exceeded")) {
         return var2;
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append(var1.getDescription());
         var3.append(" - Deadline Exceeded");
         return var3.toString();
      }
   }

   private String getTtfdDesc(String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(var1);
      var2.append(" full display");
      return var2.toString();
   }

   private String getTtidDesc(String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(var1);
      var2.append(" initial display");
      return var2.toString();
   }

   private boolean isPerformanceEnabled(SentryAndroidOptions var1) {
      boolean var2;
      if (var1.isTracingEnabled() && var1.isEnableAutoActivityLifecycleTracing()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private boolean isRunningTransactionOrTrace(Activity var1) {
      return this.activitiesWithOngoingTransactions.containsKey(var1);
   }

   private void onFirstFrameDrawn(ISpan var1, ISpan var2) {
      AppStartMetrics var6 = AppStartMetrics.getInstance();
      TimeSpan var5 = var6.getAppStartTimeSpan();
      TimeSpan var10 = var6.getSdkInitTimeSpan();
      if (var5.hasStarted() && var5.hasNotStopped()) {
         var5.stop();
      }

      if (var10.hasStarted() && var10.hasNotStopped()) {
         var10.stop();
      }

      this.finishAppStartSpan();
      SentryAndroidOptions var8 = this.options;
      if (var8 != null && var2 != null) {
         SentryDate var9 = var8.getDateProvider().now();
         long var3 = var9.diff(var2.getStartDate());
         var3 = TimeUnit.NANOSECONDS.toMillis(var3);
         var2.setMeasurement("time_to_initial_display", var3, MeasurementUnit.Duration.MILLISECOND);
         if (var1 != null && var1.isFinished()) {
            var1.updateEndDate(var9);
            var2.setMeasurement("time_to_full_display", var3, MeasurementUnit.Duration.MILLISECOND);
         }

         this.finishSpan(var2, var9);
      } else {
         this.finishSpan(var2);
      }
   }

   private void onFullFrameDrawn(ISpan var1) {
      SentryAndroidOptions var4 = this.options;
      if (var4 != null && var1 != null) {
         SentryDate var5 = var4.getDateProvider().now();
         long var2 = var5.diff(var1.getStartDate());
         var1.setMeasurement("time_to_full_display", TimeUnit.NANOSECONDS.toMillis(var2), MeasurementUnit.Duration.MILLISECOND);
         this.finishSpan(var1, var5);
      } else {
         this.finishSpan(var1);
      }

      this.cancelTtfdAutoClose();
   }

   private void setSpanOrigin(ISpan var1) {
      if (var1 != null) {
         var1.getSpanContext().setOrigin("auto.ui.activity");
      }
   }

   private void startTracing(Activity var1) {
      WeakReference var6 = new WeakReference<>(var1);
      if (this.hub != null && !this.isRunningTransactionOrTrace(var1)) {
         if (!this.performanceEnabled) {
            this.activitiesWithOngoingTransactions.put(var1, NoOpTransaction.getInstance());
            TracingUtils.startNewTrace(this.hub);
         } else {
            this.stopPreviousTransactions();
            String var8 = this.getActivityName(var1);
            TimeSpan var4 = AppStartMetrics.getInstance().getAppStartTimeSpanWithFallback(this.options);
            boolean var2 = ContextUtils.isForegroundImportance();
            boolean var3 = false;
            TracesSamplingDecision var7 = null;
            Boolean var5;
            SentryDate var13;
            if (var2 && var4.hasStarted()) {
               var13 = var4.getStartTimestamp();
               if (AppStartMetrics.getInstance().getAppStartType() == AppStartMetrics.AppStartType.COLD) {
                  var2 = true;
               } else {
                  var2 = false;
               }

               var5 = var2;
            } else {
               var13 = null;
               var5 = null;
            }

            TransactionOptions var9 = new TransactionOptions();
            var9.setDeadlineTimeout(30000L);
            if (this.options.isEnableActivityLifecycleTracingAutoFinish()) {
               var9.setIdleTimeout(this.options.getIdleTimeout());
               var9.setTrimEnd(true);
            }

            var9.setWaitForChildren(true);
            var9.setTransactionFinishedCallback(new ActivityLifecycleIntegration$$ExternalSyntheticLambda1(this, var6, var8));
            SentryDate var17;
            if (!this.firstActivityCreated && var13 != null && var5 != null) {
               var7 = AppStartMetrics.getInstance().getAppStartSamplingDecision();
               AppStartMetrics.getInstance().setAppStartSamplingDecision(null);
               var17 = var13;
            } else {
               var17 = this.lastPausedTime;
            }

            var9.setStartTimestamp(var17);
            var2 = var3;
            if (var7 != null) {
               var2 = true;
            }

            var9.setAppStartTransaction(var2);
            ITransaction var19 = this.hub.startTransaction(new TransactionContext(var8, TransactionNameSource.COMPONENT, "ui.load", var7), var9);
            this.setSpanOrigin(var19);
            if (!this.firstActivityCreated && var13 != null && var5 != null) {
               ISpan var14 = var19.startChild(this.getAppStartOp(var5), this.getAppStartDesc(var5), var13, Instrumenter.SENTRY);
               this.appStartSpan = var14;
               this.setSpanOrigin(var14);
               this.finishAppStartSpan();
            }

            ISpan var15 = var19.startChild("ui.load.initial_display", this.getTtidDesc(var8), var17, Instrumenter.SENTRY);
            this.ttidSpanMap.put(var1, var15);
            this.setSpanOrigin(var15);
            if (this.timeToFullDisplaySpanEnabled && this.fullyDisplayedReporter != null && this.options != null) {
               ISpan var20 = var19.startChild("ui.load.full_display", this.getTtfdDesc(var8), var17, Instrumenter.SENTRY);
               this.setSpanOrigin(var20);

               try {
                  this.ttfdSpanMap.put(var1, var20);
                  ISentryExecutorService var16 = this.options.getExecutorService();
                  ActivityLifecycleIntegration$$ExternalSyntheticLambda2 var18 = new ActivityLifecycleIntegration$$ExternalSyntheticLambda2(this, var20, var15);
                  this.ttfdAutoCloseFuture = var16.schedule(var18, 25000L);
               } catch (RejectedExecutionException var10) {
                  this.options
                     .getLogger()
                     .log(
                        SentryLevel.ERROR,
                        "Failed to call the executor. Time to full display span will not be finished automatically. Did you call Sentry.close()?",
                        var10
                     );
               }
            }

            this.hub.configureScope(new ActivityLifecycleIntegration$$ExternalSyntheticLambda3(this, var19));
            this.activitiesWithOngoingTransactions.put(var1, var19);
         }
      }
   }

   private void stopPreviousTransactions() {
      for (Entry var1 : this.activitiesWithOngoingTransactions.entrySet()) {
         this.finishTransaction((ITransaction)var1.getValue(), this.ttidSpanMap.get(var1.getKey()), this.ttfdSpanMap.get(var1.getKey()));
      }
   }

   private void stopTracing(Activity var1, boolean var2) {
      if (this.performanceEnabled && var2) {
         this.finishTransaction(this.activitiesWithOngoingTransactions.get(var1), null, null);
      }
   }

   void applyScope(IScope var1, ITransaction var2) {
      var1.withTransaction(new ActivityLifecycleIntegration$$ExternalSyntheticLambda9(this, var1, var2));
   }

   void clearScope(IScope var1, ITransaction var2) {
      var1.withTransaction(new ActivityLifecycleIntegration$$ExternalSyntheticLambda0(var2, var1));
   }

   @Override
   public void close() throws IOException {
      this.application.unregisterActivityLifecycleCallbacks(this);
      SentryAndroidOptions var1 = this.options;
      if (var1 != null) {
         var1.getLogger().log(SentryLevel.DEBUG, "ActivityLifecycleIntegration removed.");
      }

      this.activityFramesTracker.stop();
   }

   WeakHashMap<Activity, ITransaction> getActivitiesWithOngoingTransactions() {
      return this.activitiesWithOngoingTransactions;
   }

   ActivityFramesTracker getActivityFramesTracker() {
      return this.activityFramesTracker;
   }

   WeakHashMap<Activity, ActivityLifecycleTimeSpan> getActivityLifecycleMap() {
      return this.activityLifecycleMap;
   }

   ISpan getAppStartSpan() {
      return this.appStartSpan;
   }

   WeakHashMap<Activity, ISpan> getTtfdSpanMap() {
      return this.ttfdSpanMap;
   }

   WeakHashMap<Activity, ISpan> getTtidSpanMap() {
      return this.ttidSpanMap;
   }

   public void onActivityCreated(Activity param1, Bundle param2) {
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
      // 03: getfield io/sentry/android/core/ActivityLifecycleIntegration.isAllActivityCallbacksAvailable Z
      // 06: ifne 0f
      // 09: aload 0
      // 0a: aload 1
      // 0b: aload 2
      // 0c: invokevirtual io/sentry/android/core/ActivityLifecycleIntegration.onActivityPreCreated (Landroid/app/Activity;Landroid/os/Bundle;)V
      // 0f: aload 0
      // 10: getfield io/sentry/android/core/ActivityLifecycleIntegration.hub Lio/sentry/IHub;
      // 13: ifnull 42
      // 16: aload 0
      // 17: getfield io/sentry/android/core/ActivityLifecycleIntegration.options Lio/sentry/android/core/SentryAndroidOptions;
      // 1a: astore 2
      // 1b: aload 2
      // 1c: ifnull 42
      // 1f: aload 2
      // 20: invokevirtual io/sentry/android/core/SentryAndroidOptions.isEnableScreenTracking ()Z
      // 23: ifeq 42
      // 26: aload 1
      // 27: invokestatic io/sentry/android/core/internal/util/ClassUtil.getClassName (Ljava/lang/Object;)Ljava/lang/String;
      // 2a: astore 4
      // 2c: aload 0
      // 2d: getfield io/sentry/android/core/ActivityLifecycleIntegration.hub Lio/sentry/IHub;
      // 30: astore 3
      // 31: new io/sentry/android/core/ActivityLifecycleIntegration$$ExternalSyntheticLambda4
      // 34: astore 2
      // 35: aload 2
      // 36: aload 4
      // 38: invokespecial io/sentry/android/core/ActivityLifecycleIntegration$$ExternalSyntheticLambda4.<init> (Ljava/lang/String;)V
      // 3b: aload 3
      // 3c: aload 2
      // 3d: invokeinterface io/sentry/IHub.configureScope (Lio/sentry/ScopeCallback;)V 2
      // 42: aload 0
      // 43: aload 1
      // 44: invokespecial io/sentry/android/core/ActivityLifecycleIntegration.startTracing (Landroid/app/Activity;)V
      // 47: aload 0
      // 48: getfield io/sentry/android/core/ActivityLifecycleIntegration.ttfdSpanMap Ljava/util/WeakHashMap;
      // 4b: aload 1
      // 4c: invokevirtual java/util/WeakHashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
      // 4f: checkcast io/sentry/ISpan
      // 52: astore 1
      // 53: aload 0
      // 54: bipush 1
      // 55: putfield io/sentry/android/core/ActivityLifecycleIntegration.firstActivityCreated Z
      // 58: aload 0
      // 59: getfield io/sentry/android/core/ActivityLifecycleIntegration.performanceEnabled Z
      // 5c: ifeq 7b
      // 5f: aload 1
      // 60: ifnull 7b
      // 63: aload 0
      // 64: getfield io/sentry/android/core/ActivityLifecycleIntegration.fullyDisplayedReporter Lio/sentry/FullyDisplayedReporter;
      // 67: astore 2
      // 68: aload 2
      // 69: ifnull 7b
      // 6c: new io/sentry/android/core/ActivityLifecycleIntegration$$ExternalSyntheticLambda5
      // 6f: astore 3
      // 70: aload 3
      // 71: aload 0
      // 72: aload 1
      // 73: invokespecial io/sentry/android/core/ActivityLifecycleIntegration$$ExternalSyntheticLambda5.<init> (Lio/sentry/android/core/ActivityLifecycleIntegration;Lio/sentry/ISpan;)V
      // 76: aload 2
      // 77: aload 3
      // 78: invokevirtual io/sentry/FullyDisplayedReporter.registerFullyDrawnListener (Lio/sentry/FullyDisplayedReporter$FullyDisplayedReporterListener;)V
      // 7b: aload 0
      // 7c: monitorexit
      // 7d: return
      // 7e: astore 1
      // 7f: aload 0
      // 80: monitorexit
      // 81: aload 1
      // 82: athrow
   }

   public void onActivityDestroyed(Activity param1) {
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
      // 03: getfield io/sentry/android/core/ActivityLifecycleIntegration.activityLifecycleMap Ljava/util/WeakHashMap;
      // 06: aload 1
      // 07: invokevirtual java/util/WeakHashMap.remove (Ljava/lang/Object;)Ljava/lang/Object;
      // 0a: pop
      // 0b: aload 0
      // 0c: getfield io/sentry/android/core/ActivityLifecycleIntegration.performanceEnabled Z
      // 0f: ifeq 64
      // 12: aload 0
      // 13: aload 0
      // 14: getfield io/sentry/android/core/ActivityLifecycleIntegration.appStartSpan Lio/sentry/ISpan;
      // 17: getstatic io/sentry/SpanStatus.CANCELLED Lio/sentry/SpanStatus;
      // 1a: invokespecial io/sentry/android/core/ActivityLifecycleIntegration.finishSpan (Lio/sentry/ISpan;Lio/sentry/SpanStatus;)V
      // 1d: aload 0
      // 1e: getfield io/sentry/android/core/ActivityLifecycleIntegration.ttidSpanMap Ljava/util/WeakHashMap;
      // 21: aload 1
      // 22: invokevirtual java/util/WeakHashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
      // 25: checkcast io/sentry/ISpan
      // 28: astore 3
      // 29: aload 0
      // 2a: getfield io/sentry/android/core/ActivityLifecycleIntegration.ttfdSpanMap Ljava/util/WeakHashMap;
      // 2d: aload 1
      // 2e: invokevirtual java/util/WeakHashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
      // 31: checkcast io/sentry/ISpan
      // 34: astore 2
      // 35: aload 0
      // 36: aload 3
      // 37: getstatic io/sentry/SpanStatus.DEADLINE_EXCEEDED Lio/sentry/SpanStatus;
      // 3a: invokespecial io/sentry/android/core/ActivityLifecycleIntegration.finishSpan (Lio/sentry/ISpan;Lio/sentry/SpanStatus;)V
      // 3d: aload 0
      // 3e: aload 2
      // 3f: aload 3
      // 40: invokespecial io/sentry/android/core/ActivityLifecycleIntegration.finishExceededTtfdSpan (Lio/sentry/ISpan;Lio/sentry/ISpan;)V
      // 43: aload 0
      // 44: invokespecial io/sentry/android/core/ActivityLifecycleIntegration.cancelTtfdAutoClose ()V
      // 47: aload 0
      // 48: aload 1
      // 49: bipush 1
      // 4a: invokespecial io/sentry/android/core/ActivityLifecycleIntegration.stopTracing (Landroid/app/Activity;Z)V
      // 4d: aload 0
      // 4e: aconst_null
      // 4f: putfield io/sentry/android/core/ActivityLifecycleIntegration.appStartSpan Lio/sentry/ISpan;
      // 52: aload 0
      // 53: getfield io/sentry/android/core/ActivityLifecycleIntegration.ttidSpanMap Ljava/util/WeakHashMap;
      // 56: aload 1
      // 57: invokevirtual java/util/WeakHashMap.remove (Ljava/lang/Object;)Ljava/lang/Object;
      // 5a: pop
      // 5b: aload 0
      // 5c: getfield io/sentry/android/core/ActivityLifecycleIntegration.ttfdSpanMap Ljava/util/WeakHashMap;
      // 5f: aload 1
      // 60: invokevirtual java/util/WeakHashMap.remove (Ljava/lang/Object;)Ljava/lang/Object;
      // 63: pop
      // 64: aload 0
      // 65: getfield io/sentry/android/core/ActivityLifecycleIntegration.activitiesWithOngoingTransactions Ljava/util/WeakHashMap;
      // 68: aload 1
      // 69: invokevirtual java/util/WeakHashMap.remove (Ljava/lang/Object;)Ljava/lang/Object;
      // 6c: pop
      // 6d: aload 0
      // 6e: getfield io/sentry/android/core/ActivityLifecycleIntegration.activitiesWithOngoingTransactions Ljava/util/WeakHashMap;
      // 71: invokevirtual java/util/WeakHashMap.isEmpty ()Z
      // 74: ifeq 82
      // 77: aload 1
      // 78: invokevirtual android/app/Activity.isChangingConfigurations ()Z
      // 7b: ifne 82
      // 7e: aload 0
      // 7f: invokespecial io/sentry/android/core/ActivityLifecycleIntegration.clear ()V
      // 82: aload 0
      // 83: monitorexit
      // 84: return
      // 85: astore 1
      // 86: aload 0
      // 87: monitorexit
      // 88: aload 1
      // 89: athrow
   }

   public void onActivityPaused(Activity param1) {
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
      // 03: getfield io/sentry/android/core/ActivityLifecycleIntegration.isAllActivityCallbacksAvailable Z
      // 06: ifne 0e
      // 09: aload 0
      // 0a: aload 1
      // 0b: invokevirtual io/sentry/android/core/ActivityLifecycleIntegration.onActivityPrePaused (Landroid/app/Activity;)V
      // 0e: aload 0
      // 0f: monitorexit
      // 10: return
      // 11: astore 1
      // 12: aload 0
      // 13: monitorexit
      // 14: aload 1
      // 15: athrow
   }

   public void onActivityPostCreated(Activity var1, Bundle var2) {
      if (this.appStartSpan == null) {
         this.activityLifecycleMap.remove(var1);
      } else {
         ActivityLifecycleTimeSpan var4 = this.activityLifecycleMap.get(var1);
         if (var4 != null) {
            var4.getOnCreate().stop();
            TimeSpan var3 = var4.getOnCreate();
            StringBuilder var5 = new StringBuilder();
            var5.append(var1.getClass().getName());
            var5.append(".onCreate");
            var3.setDescription(var5.toString());
         }
      }
   }

   public void onActivityPostResumed(Activity var1) {
   }

   public void onActivityPostStarted(Activity var1) {
      ActivityLifecycleTimeSpan var4 = this.activityLifecycleMap.remove(var1);
      if (this.appStartSpan != null) {
         if (var4 != null) {
            var4.getOnStart().stop();
            TimeSpan var2 = var4.getOnStart();
            StringBuilder var3 = new StringBuilder();
            var3.append(var1.getClass().getName());
            var3.append(".onStart");
            var2.setDescription(var3.toString());
            AppStartMetrics.getInstance().addActivityLifecycleTimeSpans(var4);
         }
      }
   }

   public void onActivityPreCreated(Activity var1, Bundle var2) {
      if (!this.firstActivityCreated) {
         IHub var3 = this.hub;
         SentryDate var4;
         if (var3 != null) {
            var4 = var3.getOptions().getDateProvider().now();
         } else {
            var4 = AndroidDateUtils.getCurrentSentryDateTime();
         }

         this.lastPausedTime = var4;
         this.lastPausedUptimeMillis = SystemClock.uptimeMillis();
         ActivityLifecycleTimeSpan var5 = new ActivityLifecycleTimeSpan();
         var5.getOnCreate().setStartedAt(this.lastPausedUptimeMillis);
         this.activityLifecycleMap.put(var1, var5);
      }
   }

   public void onActivityPrePaused(Activity var1) {
      this.firstActivityCreated = true;
      IHub var2 = this.hub;
      SentryDate var3;
      if (var2 != null) {
         var3 = var2.getOptions().getDateProvider().now();
      } else {
         var3 = AndroidDateUtils.getCurrentSentryDateTime();
      }

      this.lastPausedTime = var3;
      this.lastPausedUptimeMillis = SystemClock.uptimeMillis();
   }

   public void onActivityPreStarted(Activity var1) {
      if (this.appStartSpan != null) {
         ActivityLifecycleTimeSpan var2 = this.activityLifecycleMap.get(var1);
         if (var2 != null) {
            var2.getOnStart().setStartedAt(SystemClock.uptimeMillis());
         }
      }
   }

   public void onActivityResumed(Activity param1) {
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
      // 03: getfield io/sentry/android/core/ActivityLifecycleIntegration.isAllActivityCallbacksAvailable Z
      // 06: ifne 0e
      // 09: aload 0
      // 0a: aload 1
      // 0b: invokevirtual io/sentry/android/core/ActivityLifecycleIntegration.onActivityPostStarted (Landroid/app/Activity;)V
      // 0e: aload 0
      // 0f: getfield io/sentry/android/core/ActivityLifecycleIntegration.performanceEnabled Z
      // 12: ifeq 6d
      // 15: aload 0
      // 16: getfield io/sentry/android/core/ActivityLifecycleIntegration.ttidSpanMap Ljava/util/WeakHashMap;
      // 19: aload 1
      // 1a: invokevirtual java/util/WeakHashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
      // 1d: checkcast io/sentry/ISpan
      // 20: astore 3
      // 21: aload 0
      // 22: getfield io/sentry/android/core/ActivityLifecycleIntegration.ttfdSpanMap Ljava/util/WeakHashMap;
      // 25: aload 1
      // 26: invokevirtual java/util/WeakHashMap.get (Ljava/lang/Object;)Ljava/lang/Object;
      // 29: checkcast io/sentry/ISpan
      // 2c: astore 2
      // 2d: aload 1
      // 2e: invokevirtual android/app/Activity.getWindow ()Landroid/view/Window;
      // 31: ifnull 4e
      // 34: new io/sentry/android/core/ActivityLifecycleIntegration$$ExternalSyntheticLambda6
      // 37: astore 4
      // 39: aload 4
      // 3b: aload 0
      // 3c: aload 2
      // 3d: aload 3
      // 3e: invokespecial io/sentry/android/core/ActivityLifecycleIntegration$$ExternalSyntheticLambda6.<init> (Lio/sentry/android/core/ActivityLifecycleIntegration;Lio/sentry/ISpan;Lio/sentry/ISpan;)V
      // 41: aload 1
      // 42: aload 4
      // 44: aload 0
      // 45: getfield io/sentry/android/core/ActivityLifecycleIntegration.buildInfoProvider Lio/sentry/android/core/BuildInfoProvider;
      // 48: invokestatic io/sentry/android/core/internal/util/FirstDrawDoneListener.registerForNextDraw (Landroid/app/Activity;Ljava/lang/Runnable;Lio/sentry/android/core/BuildInfoProvider;)V
      // 4b: goto 6d
      // 4e: new android/os/Handler
      // 51: astore 1
      // 52: aload 1
      // 53: invokestatic android/os/Looper.getMainLooper ()Landroid/os/Looper;
      // 56: invokespecial android/os/Handler.<init> (Landroid/os/Looper;)V
      // 59: new io/sentry/android/core/ActivityLifecycleIntegration$$ExternalSyntheticLambda7
      // 5c: astore 4
      // 5e: aload 4
      // 60: aload 0
      // 61: aload 2
      // 62: aload 3
      // 63: invokespecial io/sentry/android/core/ActivityLifecycleIntegration$$ExternalSyntheticLambda7.<init> (Lio/sentry/android/core/ActivityLifecycleIntegration;Lio/sentry/ISpan;Lio/sentry/ISpan;)V
      // 66: aload 1
      // 67: aload 4
      // 69: invokevirtual android/os/Handler.post (Ljava/lang/Runnable;)Z
      // 6c: pop
      // 6d: aload 0
      // 6e: monitorexit
      // 6f: return
      // 70: astore 1
      // 71: aload 0
      // 72: monitorexit
      // 73: aload 1
      // 74: athrow
   }

   public void onActivitySaveInstanceState(Activity var1, Bundle var2) {
   }

   public void onActivityStarted(Activity param1) {
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
      // 03: getfield io/sentry/android/core/ActivityLifecycleIntegration.isAllActivityCallbacksAvailable Z
      // 06: ifne 14
      // 09: aload 0
      // 0a: aload 1
      // 0b: aconst_null
      // 0c: invokevirtual io/sentry/android/core/ActivityLifecycleIntegration.onActivityPostCreated (Landroid/app/Activity;Landroid/os/Bundle;)V
      // 0f: aload 0
      // 10: aload 1
      // 11: invokevirtual io/sentry/android/core/ActivityLifecycleIntegration.onActivityPreStarted (Landroid/app/Activity;)V
      // 14: aload 0
      // 15: getfield io/sentry/android/core/ActivityLifecycleIntegration.performanceEnabled Z
      // 18: ifeq 23
      // 1b: aload 0
      // 1c: getfield io/sentry/android/core/ActivityLifecycleIntegration.activityFramesTracker Lio/sentry/android/core/ActivityFramesTracker;
      // 1f: aload 1
      // 20: invokevirtual io/sentry/android/core/ActivityFramesTracker.addActivity (Landroid/app/Activity;)V
      // 23: aload 0
      // 24: monitorexit
      // 25: return
      // 26: astore 1
      // 27: aload 0
      // 28: monitorexit
      // 29: aload 1
      // 2a: athrow
   }

   public void onActivityStopped(Activity var1) {
   }

   @Override
   public void register(IHub var1, SentryOptions var2) {
      SentryAndroidOptions var3;
      if (var2 instanceof SentryAndroidOptions) {
         var3 = (SentryAndroidOptions)var2;
      } else {
         var3 = null;
      }

      this.options = Objects.requireNonNull(var3, "SentryAndroidOptions is required");
      this.hub = Objects.requireNonNull(var1, "Hub is required");
      this.performanceEnabled = this.isPerformanceEnabled(this.options);
      this.fullyDisplayedReporter = this.options.getFullyDisplayedReporter();
      this.timeToFullDisplaySpanEnabled = this.options.isEnableTimeToFullDisplayTracing();
      this.application.registerActivityLifecycleCallbacks(this);
      this.options.getLogger().log(SentryLevel.DEBUG, "ActivityLifecycleIntegration installed.");
      IntegrationUtils.addIntegrationToSdkVersion("ActivityLifecycle");
   }

   void setFirstActivityCreated(boolean var1) {
      this.firstActivityCreated = var1;
   }
}
