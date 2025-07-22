package io.sentry.android.core;

import android.os.Looper;
import io.sentry.EventProcessor;
import io.sentry.Hint;
import io.sentry.SentryEvent;
import io.sentry.SpanContext;
import io.sentry.SpanId;
import io.sentry.SpanStatus;
import io.sentry.android.core.performance.ActivityLifecycleTimeSpan;
import io.sentry.android.core.performance.AppStartMetrics;
import io.sentry.android.core.performance.TimeSpan;
import io.sentry.protocol.SentryId;
import io.sentry.protocol.SentrySpan;
import io.sentry.protocol.SentryTransaction;
import io.sentry.util.Objects;
import j..util.concurrent.ConcurrentHashMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

final class PerformanceAndroidEventProcessor implements EventProcessor {
   private static final String APP_METRICS_ACTIVITIES_OP = "activity.load";
   private static final String APP_METRICS_APPLICATION_OP = "application.load";
   private static final String APP_METRICS_CONTENT_PROVIDER_OP = "contentprovider.load";
   private static final String APP_METRICS_ORIGIN = "auto.ui";
   private static final String APP_METRICS_PROCESS_INIT_OP = "process.load";
   private static final long MAX_PROCESS_INIT_APP_START_DIFF_MS = 10000L;
   private final ActivityFramesTracker activityFramesTracker;
   private final SentryAndroidOptions options;
   private boolean sentStartMeasurement = false;

   PerformanceAndroidEventProcessor(SentryAndroidOptions var1, ActivityFramesTracker var2) {
      this.options = Objects.requireNonNull(var1, "SentryAndroidOptions is required");
      this.activityFramesTracker = Objects.requireNonNull(var2, "ActivityFramesTracker is required");
   }

   private void attachAppStartSpans(AppStartMetrics var1, SentryTransaction var2) {
      if (var1.getAppStartType() != AppStartMetrics.AppStartType.UNKNOWN) {
         SpanContext var5 = var2.getContexts().getTrace();
         if (var5 != null) {
            SentryId var6 = var5.getTraceId();
            Iterator var7 = var2.getSpans().iterator();

            while (true) {
               if (var7.hasNext()) {
                  SentrySpan var11 = (SentrySpan)var7.next();
                  if (!var11.getOp().contentEquals("app.start.cold") && !var11.getOp().contentEquals("app.start.warm")) {
                     continue;
                  }

                  var10 = var11.getSpanId();
                  break;
               }

               var10 = null;
               break;
            }

            if (var1.getAppStartType() == AppStartMetrics.AppStartType.COLD) {
               long var3 = var1.getClassLoadedUptimeMs();
               TimeSpan var8 = var1.getAppStartTimeSpan();
               if (var8.hasStarted() && Math.abs(var3 - var8.getStartUptimeMs()) <= 10000L) {
                  TimeSpan var12 = new TimeSpan();
                  var12.setStartedAt(var8.getStartUptimeMs());
                  var12.setStartUnixTimeMs(var8.getStartTimestampMs());
                  var12.setStoppedAt(var3);
                  var12.setDescription("Process Initialization");
                  var2.getSpans().add(timeSpanToSentrySpan(var12, var10, var6, "process.load"));
               }

               List var13 = var1.getContentProviderOnCreateTimeSpans();
               if (!var13.isEmpty()) {
                  for (TimeSpan var17 : var13) {
                     var2.getSpans().add(timeSpanToSentrySpan(var17, var10, var6, "contentprovider.load"));
                  }
               }

               TimeSpan var15 = var1.getApplicationOnCreateTimeSpan();
               if (var15.hasStopped()) {
                  var2.getSpans().add(timeSpanToSentrySpan(var15, var10, var6, "application.load"));
               }
            }

            for (ActivityLifecycleTimeSpan var16 : var1.getActivityLifecycleTimeSpans()) {
               if (var16.getOnCreate().hasStarted() && var16.getOnCreate().hasStopped()) {
                  var2.getSpans().add(timeSpanToSentrySpan(var16.getOnCreate(), var10, var6, "activity.load"));
               }

               if (var16.getOnStart().hasStarted() && var16.getOnStart().hasStopped()) {
                  var2.getSpans().add(timeSpanToSentrySpan(var16.getOnStart(), var10, var6, "activity.load"));
               }
            }
         }
      }
   }

   private boolean hasAppStartSpan(SentryTransaction var1) {
      Iterator var4 = var1.getSpans().iterator();

      SentrySpan var5;
      do {
         boolean var2 = var4.hasNext();
         boolean var3 = true;
         if (!var2) {
            SpanContext var6 = var1.getContexts().getTrace();
            if (var6 != null) {
               if (var6.getOperation().equals("app.start.cold")) {
                  return var3;
               }

               if (var6.getOperation().equals("app.start.warm")) {
                  return var3;
               }
            }

            return false;
         }

         var5 = (SentrySpan)var4.next();
      } while (!var5.getOp().contentEquals("app.start.cold") && !var5.getOp().contentEquals("app.start.warm"));

      return true;
   }

   private static boolean isTimestampWithinSpan(double var0, SentrySpan var2) {
      boolean var3;
      if (!(var0 >= var2.getStartTimestamp()) || var2.getTimestamp() != null && !(var0 <= var2.getTimestamp())) {
         var3 = false;
      } else {
         var3 = true;
      }

      return var3;
   }

   private void setContributingFlags(SentryTransaction var1) {
      Iterator var10 = var1.getSpans().iterator();
      SentrySpan var6 = null;
      SentrySpan var5 = null;

      SentrySpan var7;
      SentrySpan var8;
      while (true) {
         var7 = var6;
         var8 = var5;
         if (!var10.hasNext()) {
            break;
         }

         SentrySpan var9 = (SentrySpan)var10.next();
         if ("ui.load.initial_display".equals(var9.getOp())) {
            var7 = var9;
            var8 = var5;
         } else {
            var7 = var6;
            var8 = var5;
            if ("ui.load.full_display".equals(var9.getOp())) {
               var8 = var9;
               var7 = var6;
            }
         }

         var6 = var7;
         var5 = var8;
         if (var7 != null) {
            var6 = var7;
            var5 = var8;
            if (var8 != null) {
               break;
            }
         }
      }

      if (var7 != null || var8 != null) {
         for (SentrySpan var16 : var1.getSpans()) {
            if (var16 != var7 && var16 != var8) {
               boolean var2;
               boolean var4;
               label71: {
                  Map var11 = var16.getData();
                  var4 = false;
                  if (var11 != null) {
                     Object var12 = var11.get("thread.name");
                     if (var12 != null && !"main".equals(var12)) {
                        var2 = false;
                        break label71;
                     }
                  }

                  var2 = true;
               }

               if (var7 != null && isTimestampWithinSpan(var16.getStartTimestamp(), var7) && var2) {
                  var2 = true;
               } else {
                  var2 = false;
               }

               boolean var3 = var4;
               if (var8 != null) {
                  var3 = var4;
                  if (isTimestampWithinSpan(var16.getStartTimestamp(), var8)) {
                     var3 = true;
                  }
               }

               if (var2 || var3) {
                  Map var15 = var16.getData();
                  Object var13 = var15;
                  if (var15 == null) {
                     var13 = new ConcurrentHashMap();
                     var16.setData((Map<String, Object>)var13);
                  }

                  if (var2) {
                     var13.put("ui.contributes_to_ttid", true);
                  }

                  if (var3) {
                     var13.put("ui.contributes_to_ttfd", true);
                  }
               }
            }
         }
      }
   }

   private static SentrySpan timeSpanToSentrySpan(TimeSpan var0, SpanId var1, SentryId var2, String var3) {
      HashMap var5 = new HashMap(2);
      var5.put("thread.id", Looper.getMainLooper().getThread().getId());
      var5.put("thread.name", "main");
      Boolean var4 = true;
      var5.put("ui.contributes_to_ttid", var4);
      var5.put("ui.contributes_to_ttfd", var4);
      return new SentrySpan(
         var0.getStartTimestampSecs(),
         var0.getProjectedStopTimestampSecs(),
         var2,
         new SpanId(),
         var1,
         var3,
         var0.getDescription(),
         SpanStatus.OK,
         "auto.ui",
         new ConcurrentHashMap(),
         new ConcurrentHashMap(),
         null,
         var5
      );
   }

   @Override
   public SentryEvent process(SentryEvent var1, Hint var2) {
      return var1;
   }

   @Override
   public SentryTransaction process(SentryTransaction param1, Hint param2) {
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
      // 000: aload 0
      // 001: monitorenter
      // 002: aload 0
      // 003: getfield io/sentry/android/core/PerformanceAndroidEventProcessor.options Lio/sentry/android/core/SentryAndroidOptions;
      // 006: invokevirtual io/sentry/android/core/SentryAndroidOptions.isTracingEnabled ()Z
      // 009: istore 5
      // 00b: iload 5
      // 00d: ifne 014
      // 010: aload 0
      // 011: monitorexit
      // 012: aload 1
      // 013: areturn
      // 014: invokestatic io/sentry/android/core/performance/AppStartMetrics.getInstance ()Lio/sentry/android/core/performance/AppStartMetrics;
      // 017: astore 7
      // 019: aload 0
      // 01a: aload 1
      // 01b: invokespecial io/sentry/android/core/PerformanceAndroidEventProcessor.hasAppStartSpan (Lio/sentry/protocol/SentryTransaction;)Z
      // 01e: ifeq 0bf
      // 021: aload 7
      // 023: invokevirtual io/sentry/android/core/performance/AppStartMetrics.shouldSendStartMeasurements ()Z
      // 026: ifeq 080
      // 029: aload 7
      // 02b: aload 0
      // 02c: getfield io/sentry/android/core/PerformanceAndroidEventProcessor.options Lio/sentry/android/core/SentryAndroidOptions;
      // 02f: invokevirtual io/sentry/android/core/performance/AppStartMetrics.getAppStartTimeSpanWithFallback (Lio/sentry/android/core/SentryAndroidOptions;)Lio/sentry/android/core/performance/TimeSpan;
      // 032: invokevirtual io/sentry/android/core/performance/TimeSpan.getDurationMs ()J
      // 035: lstore 3
      // 036: lload 3
      // 037: lconst_0
      // 038: lcmp
      // 039: ifeq 080
      // 03c: new io/sentry/protocol/MeasurementValue
      // 03f: astore 6
      // 041: aload 6
      // 043: lload 3
      // 044: l2f
      // 045: invokestatic java/lang/Float.valueOf (F)Ljava/lang/Float;
      // 048: getstatic io/sentry/MeasurementUnit$Duration.MILLISECOND Lio/sentry/MeasurementUnit$Duration;
      // 04b: invokevirtual io/sentry/MeasurementUnit$Duration.apiName ()Ljava/lang/String;
      // 04e: invokespecial io/sentry/protocol/MeasurementValue.<init> (Ljava/lang/Number;Ljava/lang/String;)V
      // 051: aload 7
      // 053: invokevirtual io/sentry/android/core/performance/AppStartMetrics.getAppStartType ()Lio/sentry/android/core/performance/AppStartMetrics$AppStartType;
      // 056: getstatic io/sentry/android/core/performance/AppStartMetrics$AppStartType.COLD Lio/sentry/android/core/performance/AppStartMetrics$AppStartType;
      // 059: if_acmpne 063
      // 05c: ldc_w "app_start_cold"
      // 05f: astore 2
      // 060: goto 067
      // 063: ldc_w "app_start_warm"
      // 066: astore 2
      // 067: aload 1
      // 068: invokevirtual io/sentry/protocol/SentryTransaction.getMeasurements ()Ljava/util/Map;
      // 06b: aload 2
      // 06c: aload 6
      // 06e: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 073: pop
      // 074: aload 0
      // 075: aload 7
      // 077: aload 1
      // 078: invokespecial io/sentry/android/core/PerformanceAndroidEventProcessor.attachAppStartSpans (Lio/sentry/android/core/performance/AppStartMetrics;Lio/sentry/protocol/SentryTransaction;)V
      // 07b: aload 7
      // 07d: invokevirtual io/sentry/android/core/performance/AppStartMetrics.onAppStartSpansSent ()V
      // 080: aload 1
      // 081: invokevirtual io/sentry/protocol/SentryTransaction.getContexts ()Lio/sentry/protocol/Contexts;
      // 084: invokevirtual io/sentry/protocol/Contexts.getApp ()Lio/sentry/protocol/App;
      // 087: astore 6
      // 089: aload 6
      // 08b: astore 2
      // 08c: aload 6
      // 08e: ifnonnull 0a1
      // 091: new io/sentry/protocol/App
      // 094: astore 2
      // 095: aload 2
      // 096: invokespecial io/sentry/protocol/App.<init> ()V
      // 099: aload 1
      // 09a: invokevirtual io/sentry/protocol/SentryTransaction.getContexts ()Lio/sentry/protocol/Contexts;
      // 09d: aload 2
      // 09e: invokevirtual io/sentry/protocol/Contexts.setApp (Lio/sentry/protocol/App;)V
      // 0a1: aload 7
      // 0a3: invokevirtual io/sentry/android/core/performance/AppStartMetrics.getAppStartType ()Lio/sentry/android/core/performance/AppStartMetrics$AppStartType;
      // 0a6: getstatic io/sentry/android/core/performance/AppStartMetrics$AppStartType.COLD Lio/sentry/android/core/performance/AppStartMetrics$AppStartType;
      // 0a9: if_acmpne 0b4
      // 0ac: ldc_w "cold"
      // 0af: astore 6
      // 0b1: goto 0b9
      // 0b4: ldc_w "warm"
      // 0b7: astore 6
      // 0b9: aload 2
      // 0ba: aload 6
      // 0bc: invokevirtual io/sentry/protocol/App.setStartType (Ljava/lang/String;)V
      // 0bf: aload 0
      // 0c0: aload 1
      // 0c1: invokespecial io/sentry/android/core/PerformanceAndroidEventProcessor.setContributingFlags (Lio/sentry/protocol/SentryTransaction;)V
      // 0c4: aload 1
      // 0c5: invokevirtual io/sentry/protocol/SentryTransaction.getEventId ()Lio/sentry/protocol/SentryId;
      // 0c8: astore 6
      // 0ca: aload 1
      // 0cb: invokevirtual io/sentry/protocol/SentryTransaction.getContexts ()Lio/sentry/protocol/Contexts;
      // 0ce: invokevirtual io/sentry/protocol/Contexts.getTrace ()Lio/sentry/SpanContext;
      // 0d1: astore 2
      // 0d2: aload 6
      // 0d4: ifnull 100
      // 0d7: aload 2
      // 0d8: ifnull 100
      // 0db: aload 2
      // 0dc: invokevirtual io/sentry/SpanContext.getOperation ()Ljava/lang/String;
      // 0df: ldc_w "ui.load"
      // 0e2: invokevirtual java/lang/String.contentEquals (Ljava/lang/CharSequence;)Z
      // 0e5: ifeq 100
      // 0e8: aload 0
      // 0e9: getfield io/sentry/android/core/PerformanceAndroidEventProcessor.activityFramesTracker Lio/sentry/android/core/ActivityFramesTracker;
      // 0ec: aload 6
      // 0ee: invokevirtual io/sentry/android/core/ActivityFramesTracker.takeMetrics (Lio/sentry/protocol/SentryId;)Ljava/util/Map;
      // 0f1: astore 2
      // 0f2: aload 2
      // 0f3: ifnull 100
      // 0f6: aload 1
      // 0f7: invokevirtual io/sentry/protocol/SentryTransaction.getMeasurements ()Ljava/util/Map;
      // 0fa: aload 2
      // 0fb: invokeinterface java/util/Map.putAll (Ljava/util/Map;)V 2
      // 100: aload 0
      // 101: monitorexit
      // 102: aload 1
      // 103: areturn
      // 104: astore 1
      // 105: aload 0
      // 106: monitorexit
      // 107: aload 1
      // 108: athrow
   }
}
