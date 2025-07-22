package io.sentry.android.core;

import android.app.Activity;
import android.util.SparseIntArray;
import androidx.core.app.FrameMetricsAggregator;
import io.sentry.ILogger;
import io.sentry.SentryLevel;
import io.sentry.android.core.internal.util.AndroidMainThreadChecker;
import io.sentry.protocol.MeasurementValue;
import io.sentry.protocol.SentryId;
import j..util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.WeakHashMap;

public final class ActivityFramesTracker {
   private final Map<SentryId, Map<String, MeasurementValue>> activityMeasurements;
   private final Map<Activity, ActivityFramesTracker.FrameCounts> frameCountAtStartSnapshots;
   private FrameMetricsAggregator frameMetricsAggregator = null;
   private final MainLooperHandler handler;
   private final SentryAndroidOptions options;

   public ActivityFramesTracker(LoadClass var1, SentryAndroidOptions var2) {
      this(var1, var2, new MainLooperHandler());
   }

   public ActivityFramesTracker(LoadClass var1, SentryAndroidOptions var2, MainLooperHandler var3) {
      this.activityMeasurements = new ConcurrentHashMap();
      this.frameCountAtStartSnapshots = new WeakHashMap<>();
      if (var1.isClassAvailable("androidx.core.app.FrameMetricsAggregator", var2.getLogger())) {
         this.frameMetricsAggregator = new FrameMetricsAggregator();
      }

      this.options = var2;
      this.handler = var3;
   }

   ActivityFramesTracker(LoadClass var1, SentryAndroidOptions var2, MainLooperHandler var3, FrameMetricsAggregator var4) {
      this(var1, var2, var3);
      this.frameMetricsAggregator = var4;
   }

   private ActivityFramesTracker.FrameCounts calculateCurrentFrameCounts() {
      if (!this.isFrameMetricsAggregatorAvailable()) {
         return null;
      } else {
         FrameMetricsAggregator var9 = this.frameMetricsAggregator;
         if (var9 == null) {
            return null;
         } else {
            SparseIntArray[] var10 = var9.getMetrics();
            int var3 = 0;
            int var5 = 0;
            if (var10 != null && var10.length > 0) {
               SparseIntArray var11 = var10[0];
               if (var11 != null) {
                  var3 = 0;
                  int var13 = 0;
                  int var12 = 0;

                  while (var5 < var11.size()) {
                     int var8 = var11.keyAt(var5);
                     int var7 = var11.valueAt(var5);
                     var3 += var7;
                     int var4;
                     int var6;
                     if (var8 > 700) {
                        var6 = var12 + var7;
                        var4 = var13;
                     } else {
                        var4 = var13;
                        var6 = var12;
                        if (var8 > 16) {
                           var4 = var13 + var7;
                           var6 = var12;
                        }
                     }

                     var5++;
                     var13 = var4;
                     var12 = var6;
                  }

                  return new ActivityFramesTracker.FrameCounts(var3, var13, var12);
               }
            }

            byte var2 = 0;
            byte var1 = 0;
            return new ActivityFramesTracker.FrameCounts(var3, var2, var1);
         }
      }
   }

   private ActivityFramesTracker.FrameCounts diffFrameCountsAtEnd(Activity var1) {
      ActivityFramesTracker.FrameCounts var3 = this.frameCountAtStartSnapshots.remove(var1);
      if (var3 == null) {
         return null;
      } else {
         ActivityFramesTracker.FrameCounts var2 = this.calculateCurrentFrameCounts();
         return var2 == null
            ? null
            : new ActivityFramesTracker.FrameCounts(
               var2.totalFrames - var3.totalFrames, var2.slowFrames - var3.slowFrames, var2.frozenFrames - var3.frozenFrames
            );
      }
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private void runSafelyOnUiThread(Runnable var1, String var2) {
      boolean var6 = false /* VF: Semaphore variable */;

      label30:
      try {
         var6 = true;
         if (AndroidMainThreadChecker.getInstance().isMainThread()) {
            var1.run();
            var6 = false;
         } else {
            MainLooperHandler var9 = this.handler;
            ActivityFramesTracker$$ExternalSyntheticLambda1 var10 = new ActivityFramesTracker$$ExternalSyntheticLambda1(this, var1, var2);
            var9.post(var10);
            var6 = false;
         }
      } finally {
         if (var6) {
            if (var2 != null) {
               ILogger var8 = this.options.getLogger();
               SentryLevel var3 = SentryLevel.WARNING;
               StringBuilder var4 = new StringBuilder("Failed to execute ");
               var4.append(var2);
               var8.log(var3, var4.toString());
            }
            break label30;
         }
      }
   }

   private void snapshotFrameCountsAtStart(Activity var1) {
      ActivityFramesTracker.FrameCounts var2 = this.calculateCurrentFrameCounts();
      if (var2 != null) {
         this.frameCountAtStartSnapshots.put(var1, var2);
      }
   }

   public void addActivity(Activity param1) {
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
      // 03: invokevirtual io/sentry/android/core/ActivityFramesTracker.isFrameMetricsAggregatorAvailable ()Z
      // 06: istore 2
      // 07: iload 2
      // 08: ifne 0e
      // 0b: aload 0
      // 0c: monitorexit
      // 0d: return
      // 0e: new io/sentry/android/core/ActivityFramesTracker$$ExternalSyntheticLambda0
      // 11: astore 3
      // 12: aload 3
      // 13: aload 0
      // 14: aload 1
      // 15: invokespecial io/sentry/android/core/ActivityFramesTracker$$ExternalSyntheticLambda0.<init> (Lio/sentry/android/core/ActivityFramesTracker;Landroid/app/Activity;)V
      // 18: aload 0
      // 19: aload 3
      // 1a: ldc "FrameMetricsAggregator.add"
      // 1c: invokespecial io/sentry/android/core/ActivityFramesTracker.runSafelyOnUiThread (Ljava/lang/Runnable;Ljava/lang/String;)V
      // 1f: aload 0
      // 20: aload 1
      // 21: invokespecial io/sentry/android/core/ActivityFramesTracker.snapshotFrameCountsAtStart (Landroid/app/Activity;)V
      // 24: aload 0
      // 25: monitorexit
      // 26: return
      // 27: astore 1
      // 28: aload 0
      // 29: monitorexit
      // 2a: aload 1
      // 2b: athrow
   }

   public boolean isFrameMetricsAggregatorAvailable() {
      boolean var1;
      if (this.frameMetricsAggregator != null && this.options.isEnableFramesTracking() && !this.options.isEnablePerformanceV2()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void setMetrics(Activity param1, SentryId param2) {
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
      // 03: invokevirtual io/sentry/android/core/ActivityFramesTracker.isFrameMetricsAggregatorAvailable ()Z
      // 06: istore 3
      // 07: iload 3
      // 08: ifne 0e
      // 0b: aload 0
      // 0c: monitorexit
      // 0d: return
      // 0e: new io/sentry/android/core/ActivityFramesTracker$$ExternalSyntheticLambda2
      // 11: astore 4
      // 13: aload 4
      // 15: aload 0
      // 16: aload 1
      // 17: invokespecial io/sentry/android/core/ActivityFramesTracker$$ExternalSyntheticLambda2.<init> (Lio/sentry/android/core/ActivityFramesTracker;Landroid/app/Activity;)V
      // 1a: aload 0
      // 1b: aload 4
      // 1d: aconst_null
      // 1e: invokespecial io/sentry/android/core/ActivityFramesTracker.runSafelyOnUiThread (Ljava/lang/Runnable;Ljava/lang/String;)V
      // 21: aload 0
      // 22: aload 1
      // 23: invokespecial io/sentry/android/core/ActivityFramesTracker.diffFrameCountsAtEnd (Landroid/app/Activity;)Lio/sentry/android/core/ActivityFramesTracker$FrameCounts;
      // 26: astore 6
      // 28: aload 6
      // 2a: ifnull bf
      // 2d: aload 6
      // 2f: invokestatic io/sentry/android/core/ActivityFramesTracker$FrameCounts.access$100 (Lio/sentry/android/core/ActivityFramesTracker$FrameCounts;)I
      // 32: ifne 48
      // 35: aload 6
      // 37: invokestatic io/sentry/android/core/ActivityFramesTracker$FrameCounts.access$200 (Lio/sentry/android/core/ActivityFramesTracker$FrameCounts;)I
      // 3a: ifne 48
      // 3d: aload 6
      // 3f: invokestatic io/sentry/android/core/ActivityFramesTracker$FrameCounts.access$300 (Lio/sentry/android/core/ActivityFramesTracker$FrameCounts;)I
      // 42: ifne 48
      // 45: goto bf
      // 48: new io/sentry/protocol/MeasurementValue
      // 4b: astore 1
      // 4c: aload 1
      // 4d: aload 6
      // 4f: invokestatic io/sentry/android/core/ActivityFramesTracker$FrameCounts.access$100 (Lio/sentry/android/core/ActivityFramesTracker$FrameCounts;)I
      // 52: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 55: ldc "none"
      // 57: invokespecial io/sentry/protocol/MeasurementValue.<init> (Ljava/lang/Number;Ljava/lang/String;)V
      // 5a: new io/sentry/protocol/MeasurementValue
      // 5d: astore 5
      // 5f: aload 5
      // 61: aload 6
      // 63: invokestatic io/sentry/android/core/ActivityFramesTracker$FrameCounts.access$200 (Lio/sentry/android/core/ActivityFramesTracker$FrameCounts;)I
      // 66: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 69: ldc "none"
      // 6b: invokespecial io/sentry/protocol/MeasurementValue.<init> (Ljava/lang/Number;Ljava/lang/String;)V
      // 6e: new io/sentry/protocol/MeasurementValue
      // 71: astore 4
      // 73: aload 4
      // 75: aload 6
      // 77: invokestatic io/sentry/android/core/ActivityFramesTracker$FrameCounts.access$300 (Lio/sentry/android/core/ActivityFramesTracker$FrameCounts;)I
      // 7a: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 7d: ldc "none"
      // 7f: invokespecial io/sentry/protocol/MeasurementValue.<init> (Ljava/lang/Number;Ljava/lang/String;)V
      // 82: new java/util/HashMap
      // 85: astore 6
      // 87: aload 6
      // 89: invokespecial java/util/HashMap.<init> ()V
      // 8c: aload 6
      // 8e: ldc "frames_total"
      // 90: aload 1
      // 91: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 96: pop
      // 97: aload 6
      // 99: ldc "frames_slow"
      // 9b: aload 5
      // 9d: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // a2: pop
      // a3: aload 6
      // a5: ldc "frames_frozen"
      // a7: aload 4
      // a9: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // ae: pop
      // af: aload 0
      // b0: getfield io/sentry/android/core/ActivityFramesTracker.activityMeasurements Ljava/util/Map;
      // b3: aload 2
      // b4: aload 6
      // b6: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // bb: pop
      // bc: aload 0
      // bd: monitorexit
      // be: return
      // bf: aload 0
      // c0: monitorexit
      // c1: return
      // c2: astore 1
      // c3: aload 0
      // c4: monitorexit
      // c5: aload 1
      // c6: athrow
   }

   public void stop() {
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
      // 03: invokevirtual io/sentry/android/core/ActivityFramesTracker.isFrameMetricsAggregatorAvailable ()Z
      // 06: ifeq 21
      // 09: new io/sentry/android/core/ActivityFramesTracker$$ExternalSyntheticLambda3
      // 0c: astore 1
      // 0d: aload 1
      // 0e: aload 0
      // 0f: invokespecial io/sentry/android/core/ActivityFramesTracker$$ExternalSyntheticLambda3.<init> (Lio/sentry/android/core/ActivityFramesTracker;)V
      // 12: aload 0
      // 13: aload 1
      // 14: ldc "FrameMetricsAggregator.stop"
      // 16: invokespecial io/sentry/android/core/ActivityFramesTracker.runSafelyOnUiThread (Ljava/lang/Runnable;Ljava/lang/String;)V
      // 19: aload 0
      // 1a: getfield io/sentry/android/core/ActivityFramesTracker.frameMetricsAggregator Landroidx/core/app/FrameMetricsAggregator;
      // 1d: invokevirtual androidx/core/app/FrameMetricsAggregator.reset ()[Landroid/util/SparseIntArray;
      // 20: pop
      // 21: aload 0
      // 22: getfield io/sentry/android/core/ActivityFramesTracker.activityMeasurements Ljava/util/Map;
      // 25: invokeinterface java/util/Map.clear ()V 1
      // 2a: aload 0
      // 2b: monitorexit
      // 2c: return
      // 2d: astore 1
      // 2e: aload 0
      // 2f: monitorexit
      // 30: aload 1
      // 31: athrow
   }

   public Map<String, MeasurementValue> takeMetrics(SentryId param1) {
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
      // 03: invokevirtual io/sentry/android/core/ActivityFramesTracker.isFrameMetricsAggregatorAvailable ()Z
      // 06: istore 2
      // 07: iload 2
      // 08: ifne 0f
      // 0b: aload 0
      // 0c: monitorexit
      // 0d: aconst_null
      // 0e: areturn
      // 0f: aload 0
      // 10: getfield io/sentry/android/core/ActivityFramesTracker.activityMeasurements Ljava/util/Map;
      // 13: aload 1
      // 14: invokeinterface java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 19: checkcast java/util/Map
      // 1c: astore 3
      // 1d: aload 0
      // 1e: getfield io/sentry/android/core/ActivityFramesTracker.activityMeasurements Ljava/util/Map;
      // 21: aload 1
      // 22: invokeinterface java/util/Map.remove (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 27: pop
      // 28: aload 0
      // 29: monitorexit
      // 2a: aload 3
      // 2b: areturn
      // 2c: astore 1
      // 2d: aload 0
      // 2e: monitorexit
      // 2f: aload 1
      // 30: athrow
   }

   private static final class FrameCounts {
      private final int frozenFrames;
      private final int slowFrames;
      private final int totalFrames;

      private FrameCounts(int var1, int var2, int var3) {
         this.totalFrames = var1;
         this.slowFrames = var2;
         this.frozenFrames = var3;
      }
   }
}
