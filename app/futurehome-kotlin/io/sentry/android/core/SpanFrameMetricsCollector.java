package io.sentry.android.core;

import io.sentry.DateUtils;
import io.sentry.IPerformanceContinuousCollector;
import io.sentry.ISpan;
import io.sentry.SentryDate;
import io.sentry.SentryNanotimeDate;
import io.sentry.android.core.internal.util.SentryFrameMetricsCollector;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;

public class SpanFrameMetricsCollector implements IPerformanceContinuousCollector, SentryFrameMetricsCollector.FrameMetricsCollectorListener {
   private static final SentryNanotimeDate EMPTY_NANO_TIME = new SentryNanotimeDate(new Date(0L), 0L);
   private static final int MAX_FRAMES_COUNT = 3600;
   private static final long ONE_SECOND_NANOS = TimeUnit.SECONDS.toNanos(1L);
   private final boolean enabled;
   private final SentryFrameMetricsCollector frameMetricsCollector;
   private final ConcurrentSkipListSet<SpanFrameMetricsCollector.Frame> frames;
   private long lastKnownFrameDurationNanos;
   private volatile String listenerId;
   private final Object lock = new Object();
   private final SortedSet<ISpan> runningSpans = new TreeSet<>(new SpanFrameMetricsCollector$$ExternalSyntheticLambda0());

   public SpanFrameMetricsCollector(SentryAndroidOptions var1, SentryFrameMetricsCollector var2) {
      this.frames = new ConcurrentSkipListSet<>();
      this.lastKnownFrameDurationNanos = 16666666L;
      this.frameMetricsCollector = var2;
      boolean var3;
      if (var1.isEnablePerformanceV2() && var1.isEnableFramesTracking()) {
         var3 = true;
      } else {
         var3 = false;
      }

      this.enabled = var3;
   }

   private static int addPendingFrameDelay(SentryFrameMetrics var0, long var1, long var3, long var5) {
      var3 = Math.max(0L, var3 - var5);
      if (SentryFrameMetricsCollector.isSlow(var3, var1)) {
         boolean var7 = SentryFrameMetricsCollector.isFrozen(var3);
         var0.addFrame(var3, Math.max(0L, var3 - var1), true, var7);
         return 1;
      } else {
         return 0;
      }
   }

   private void captureFrameMetrics(ISpan param1) {
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
      // 001: getfield io/sentry/android/core/SpanFrameMetricsCollector.lock Ljava/lang/Object;
      // 004: astore 16
      // 006: aload 16
      // 008: monitorenter
      // 009: aload 0
      // 00a: getfield io/sentry/android/core/SpanFrameMetricsCollector.runningSpans Ljava/util/SortedSet;
      // 00d: aload 1
      // 00e: invokeinterface java/util/SortedSet.remove (Ljava/lang/Object;)Z 2
      // 013: ifne 01a
      // 016: aload 16
      // 018: monitorexit
      // 019: return
      // 01a: aload 1
      // 01b: invokeinterface io/sentry/ISpan.getFinishDate ()Lio/sentry/SentryDate; 1
      // 020: astore 17
      // 022: aload 17
      // 024: ifnonnull 02b
      // 027: aload 16
      // 029: monitorexit
      // 02a: return
      // 02b: aload 1
      // 02c: invokeinterface io/sentry/ISpan.getStartDate ()Lio/sentry/SentryDate; 1
      // 031: invokestatic io/sentry/android/core/SpanFrameMetricsCollector.toNanoTime (Lio/sentry/SentryDate;)J
      // 034: lstore 14
      // 036: aload 17
      // 038: invokestatic io/sentry/android/core/SpanFrameMetricsCollector.toNanoTime (Lio/sentry/SentryDate;)J
      // 03b: lstore 10
      // 03d: lload 10
      // 03f: lload 14
      // 041: lsub
      // 042: lstore 12
      // 044: lload 12
      // 046: lconst_0
      // 047: lcmp
      // 048: ifgt 04f
      // 04b: aload 16
      // 04d: monitorexit
      // 04e: return
      // 04f: new io/sentry/android/core/SentryFrameMetrics
      // 052: astore 17
      // 054: aload 17
      // 056: invokespecial io/sentry/android/core/SentryFrameMetrics.<init> ()V
      // 059: aload 0
      // 05a: getfield io/sentry/android/core/SpanFrameMetricsCollector.lastKnownFrameDurationNanos J
      // 05d: lstore 6
      // 05f: lload 6
      // 061: lstore 8
      // 063: aload 0
      // 064: getfield io/sentry/android/core/SpanFrameMetricsCollector.frames Ljava/util/concurrent/ConcurrentSkipListSet;
      // 067: invokevirtual java/util/concurrent/ConcurrentSkipListSet.isEmpty ()Z
      // 06a: ifne 17b
      // 06d: aload 0
      // 06e: getfield io/sentry/android/core/SpanFrameMetricsCollector.frames Ljava/util/concurrent/ConcurrentSkipListSet;
      // 071: astore 18
      // 073: new io/sentry/android/core/SpanFrameMetricsCollector$Frame
      // 076: astore 19
      // 078: aload 19
      // 07a: lload 14
      // 07c: invokespecial io/sentry/android/core/SpanFrameMetricsCollector$Frame.<init> (J)V
      // 07f: aload 18
      // 081: aload 19
      // 083: invokevirtual java/util/concurrent/ConcurrentSkipListSet.tailSet (Ljava/lang/Object;)Ljava/util/NavigableSet;
      // 086: invokeinterface java/util/NavigableSet.iterator ()Ljava/util/Iterator; 1
      // 08b: astore 19
      // 08d: lload 6
      // 08f: lstore 8
      // 091: aload 19
      // 093: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 098: ifeq 17b
      // 09b: aload 19
      // 09d: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 0a2: checkcast io/sentry/android/core/SpanFrameMetricsCollector$Frame
      // 0a5: astore 18
      // 0a7: aload 18
      // 0a9: invokestatic io/sentry/android/core/SpanFrameMetricsCollector$Frame.access$000 (Lio/sentry/android/core/SpanFrameMetricsCollector$Frame;)J
      // 0ac: lload 10
      // 0ae: lcmp
      // 0af: ifle 0b9
      // 0b2: lload 6
      // 0b4: lstore 8
      // 0b6: goto 17b
      // 0b9: aload 18
      // 0bb: invokestatic io/sentry/android/core/SpanFrameMetricsCollector$Frame.access$000 (Lio/sentry/android/core/SpanFrameMetricsCollector$Frame;)J
      // 0be: lload 14
      // 0c0: lcmp
      // 0c1: iflt 0eb
      // 0c4: aload 18
      // 0c6: invokestatic io/sentry/android/core/SpanFrameMetricsCollector$Frame.access$100 (Lio/sentry/android/core/SpanFrameMetricsCollector$Frame;)J
      // 0c9: lload 10
      // 0cb: lcmp
      // 0cc: ifgt 0eb
      // 0cf: aload 17
      // 0d1: aload 18
      // 0d3: invokestatic io/sentry/android/core/SpanFrameMetricsCollector$Frame.access$200 (Lio/sentry/android/core/SpanFrameMetricsCollector$Frame;)J
      // 0d6: aload 18
      // 0d8: invokestatic io/sentry/android/core/SpanFrameMetricsCollector$Frame.access$300 (Lio/sentry/android/core/SpanFrameMetricsCollector$Frame;)J
      // 0db: aload 18
      // 0dd: invokestatic io/sentry/android/core/SpanFrameMetricsCollector$Frame.access$400 (Lio/sentry/android/core/SpanFrameMetricsCollector$Frame;)Z
      // 0e0: aload 18
      // 0e2: invokestatic io/sentry/android/core/SpanFrameMetricsCollector$Frame.access$500 (Lio/sentry/android/core/SpanFrameMetricsCollector$Frame;)Z
      // 0e5: invokevirtual io/sentry/android/core/SentryFrameMetrics.addFrame (JJZZ)V
      // 0e8: goto 171
      // 0eb: lload 14
      // 0ed: aload 18
      // 0ef: invokestatic io/sentry/android/core/SpanFrameMetricsCollector$Frame.access$000 (Lio/sentry/android/core/SpanFrameMetricsCollector$Frame;)J
      // 0f2: lcmp
      // 0f3: ifle 101
      // 0f6: lload 14
      // 0f8: aload 18
      // 0fa: invokestatic io/sentry/android/core/SpanFrameMetricsCollector$Frame.access$100 (Lio/sentry/android/core/SpanFrameMetricsCollector$Frame;)J
      // 0fd: lcmp
      // 0fe: iflt 117
      // 101: lload 10
      // 103: aload 18
      // 105: invokestatic io/sentry/android/core/SpanFrameMetricsCollector$Frame.access$000 (Lio/sentry/android/core/SpanFrameMetricsCollector$Frame;)J
      // 108: lcmp
      // 109: ifle 171
      // 10c: lload 10
      // 10e: aload 18
      // 110: invokestatic io/sentry/android/core/SpanFrameMetricsCollector$Frame.access$100 (Lio/sentry/android/core/SpanFrameMetricsCollector$Frame;)J
      // 113: lcmp
      // 114: ifge 171
      // 117: lconst_0
      // 118: lconst_0
      // 119: lload 14
      // 11b: aload 18
      // 11d: invokestatic io/sentry/android/core/SpanFrameMetricsCollector$Frame.access$000 (Lio/sentry/android/core/SpanFrameMetricsCollector$Frame;)J
      // 120: lsub
      // 121: invokestatic java/lang/Math.max (JJ)J
      // 124: aload 18
      // 126: invokestatic io/sentry/android/core/SpanFrameMetricsCollector$Frame.access$600 (Lio/sentry/android/core/SpanFrameMetricsCollector$Frame;)J
      // 129: lsub
      // 12a: invokestatic java/lang/Math.max (JJ)J
      // 12d: lstore 6
      // 12f: aload 18
      // 131: invokestatic io/sentry/android/core/SpanFrameMetricsCollector$Frame.access$300 (Lio/sentry/android/core/SpanFrameMetricsCollector$Frame;)J
      // 134: lload 6
      // 136: lsub
      // 137: lload 12
      // 139: invokestatic java/lang/Math.min (JJ)J
      // 13c: lstore 6
      // 13e: lload 14
      // 140: aload 18
      // 142: invokestatic io/sentry/android/core/SpanFrameMetricsCollector$Frame.access$000 (Lio/sentry/android/core/SpanFrameMetricsCollector$Frame;)J
      // 145: invokestatic java/lang/Math.max (JJ)J
      // 148: lstore 8
      // 14a: lload 10
      // 14c: aload 18
      // 14e: invokestatic io/sentry/android/core/SpanFrameMetricsCollector$Frame.access$100 (Lio/sentry/android/core/SpanFrameMetricsCollector$Frame;)J
      // 151: invokestatic java/lang/Math.min (JJ)J
      // 154: lload 8
      // 156: lsub
      // 157: lstore 8
      // 159: aload 17
      // 15b: lload 8
      // 15d: lload 6
      // 15f: lload 8
      // 161: aload 18
      // 163: invokestatic io/sentry/android/core/SpanFrameMetricsCollector$Frame.access$600 (Lio/sentry/android/core/SpanFrameMetricsCollector$Frame;)J
      // 166: invokestatic io/sentry/android/core/internal/util/SentryFrameMetricsCollector.isSlow (JJ)Z
      // 169: lload 8
      // 16b: invokestatic io/sentry/android/core/internal/util/SentryFrameMetricsCollector.isFrozen (J)Z
      // 16e: invokevirtual io/sentry/android/core/SentryFrameMetrics.addFrame (JJZZ)V
      // 171: aload 18
      // 173: invokestatic io/sentry/android/core/SpanFrameMetricsCollector$Frame.access$600 (Lio/sentry/android/core/SpanFrameMetricsCollector$Frame;)J
      // 176: lstore 6
      // 178: goto 08d
      // 17b: aload 17
      // 17d: invokevirtual io/sentry/android/core/SentryFrameMetrics.getSlowFrozenFrameCount ()I
      // 180: istore 5
      // 182: aload 0
      // 183: getfield io/sentry/android/core/SpanFrameMetricsCollector.frameMetricsCollector Lio/sentry/android/core/internal/util/SentryFrameMetricsCollector;
      // 186: invokevirtual io/sentry/android/core/internal/util/SentryFrameMetricsCollector.getLastKnownFrameStartTimeNanos ()J
      // 189: lstore 6
      // 18b: iload 5
      // 18d: istore 4
      // 18f: lload 6
      // 191: ldc2_w -1
      // 194: lcmp
      // 195: ifeq 1b2
      // 198: iload 5
      // 19a: aload 17
      // 19c: lload 8
      // 19e: lload 10
      // 1a0: lload 6
      // 1a2: invokestatic io/sentry/android/core/SpanFrameMetricsCollector.addPendingFrameDelay (Lio/sentry/android/core/SentryFrameMetrics;JJJ)I
      // 1a5: iadd
      // 1a6: aload 17
      // 1a8: lload 8
      // 1aa: lload 12
      // 1ac: invokestatic io/sentry/android/core/SpanFrameMetricsCollector.interpolateFrameCount (Lio/sentry/android/core/SentryFrameMetrics;JJ)I
      // 1af: iadd
      // 1b0: istore 4
      // 1b2: aload 17
      // 1b4: invokevirtual io/sentry/android/core/SentryFrameMetrics.getSlowFrameDelayNanos ()J
      // 1b7: aload 17
      // 1b9: invokevirtual io/sentry/android/core/SentryFrameMetrics.getFrozenFrameDelayNanos ()J
      // 1bc: ladd
      // 1bd: l2d
      // 1be: ldc2_w 1.0E9
      // 1c1: ddiv
      // 1c2: dstore 2
      // 1c3: aload 1
      // 1c4: ldc "frames.total"
      // 1c6: iload 4
      // 1c8: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 1cb: invokeinterface io/sentry/ISpan.setData (Ljava/lang/String;Ljava/lang/Object;)V 3
      // 1d0: aload 1
      // 1d1: ldc "frames.slow"
      // 1d3: aload 17
      // 1d5: invokevirtual io/sentry/android/core/SentryFrameMetrics.getSlowFrameCount ()I
      // 1d8: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 1db: invokeinterface io/sentry/ISpan.setData (Ljava/lang/String;Ljava/lang/Object;)V 3
      // 1e0: aload 1
      // 1e1: ldc "frames.frozen"
      // 1e3: aload 17
      // 1e5: invokevirtual io/sentry/android/core/SentryFrameMetrics.getFrozenFrameCount ()I
      // 1e8: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 1eb: invokeinterface io/sentry/ISpan.setData (Ljava/lang/String;Ljava/lang/Object;)V 3
      // 1f0: aload 1
      // 1f1: ldc "frames.delay"
      // 1f3: dload 2
      // 1f4: invokestatic java/lang/Double.valueOf (D)Ljava/lang/Double;
      // 1f7: invokeinterface io/sentry/ISpan.setData (Ljava/lang/String;Ljava/lang/Object;)V 3
      // 1fc: aload 1
      // 1fd: instanceof io/sentry/ITransaction
      // 200: ifeq 23f
      // 203: aload 1
      // 204: ldc "frames_total"
      // 206: iload 4
      // 208: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 20b: invokeinterface io/sentry/ISpan.setMeasurement (Ljava/lang/String;Ljava/lang/Number;)V 3
      // 210: aload 1
      // 211: ldc_w "frames_slow"
      // 214: aload 17
      // 216: invokevirtual io/sentry/android/core/SentryFrameMetrics.getSlowFrameCount ()I
      // 219: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 21c: invokeinterface io/sentry/ISpan.setMeasurement (Ljava/lang/String;Ljava/lang/Number;)V 3
      // 221: aload 1
      // 222: ldc_w "frames_frozen"
      // 225: aload 17
      // 227: invokevirtual io/sentry/android/core/SentryFrameMetrics.getFrozenFrameCount ()I
      // 22a: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 22d: invokeinterface io/sentry/ISpan.setMeasurement (Ljava/lang/String;Ljava/lang/Number;)V 3
      // 232: aload 1
      // 233: ldc_w "frames_delay"
      // 236: dload 2
      // 237: invokestatic java/lang/Double.valueOf (D)Ljava/lang/Double;
      // 23a: invokeinterface io/sentry/ISpan.setMeasurement (Ljava/lang/String;Ljava/lang/Number;)V 3
      // 23f: aload 16
      // 241: monitorexit
      // 242: return
      // 243: astore 1
      // 244: aload 16
      // 246: monitorexit
      // 247: aload 1
      // 248: athrow
   }

   private static int interpolateFrameCount(SentryFrameMetrics var0, long var1, long var3) {
      var3 -= var0.getTotalDurationNanos();
      return var3 > 0L ? (int)Math.ceil((double)var3 / var1) : 0;
   }

   private static long toNanoTime(SentryDate var0) {
      if (var0 instanceof SentryNanotimeDate) {
         return var0.diff(EMPTY_NANO_TIME);
      } else {
         long var3 = DateUtils.millisToNanos(System.currentTimeMillis());
         long var1 = var0.nanoTimestamp();
         return System.nanoTime() - (var3 - var1);
      }
   }

   @Override
   public void clear() {
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
      // 01: getfield io/sentry/android/core/SpanFrameMetricsCollector.lock Ljava/lang/Object;
      // 04: astore 1
      // 05: aload 1
      // 06: monitorenter
      // 07: aload 0
      // 08: getfield io/sentry/android/core/SpanFrameMetricsCollector.listenerId Ljava/lang/String;
      // 0b: ifnull 1e
      // 0e: aload 0
      // 0f: getfield io/sentry/android/core/SpanFrameMetricsCollector.frameMetricsCollector Lio/sentry/android/core/internal/util/SentryFrameMetricsCollector;
      // 12: aload 0
      // 13: getfield io/sentry/android/core/SpanFrameMetricsCollector.listenerId Ljava/lang/String;
      // 16: invokevirtual io/sentry/android/core/internal/util/SentryFrameMetricsCollector.stopCollection (Ljava/lang/String;)V
      // 19: aload 0
      // 1a: aconst_null
      // 1b: putfield io/sentry/android/core/SpanFrameMetricsCollector.listenerId Ljava/lang/String;
      // 1e: aload 0
      // 1f: getfield io/sentry/android/core/SpanFrameMetricsCollector.frames Ljava/util/concurrent/ConcurrentSkipListSet;
      // 22: invokevirtual java/util/concurrent/ConcurrentSkipListSet.clear ()V
      // 25: aload 0
      // 26: getfield io/sentry/android/core/SpanFrameMetricsCollector.runningSpans Ljava/util/SortedSet;
      // 29: invokeinterface java/util/SortedSet.clear ()V 1
      // 2e: aload 1
      // 2f: monitorexit
      // 30: return
      // 31: astore 2
      // 32: aload 1
      // 33: monitorexit
      // 34: aload 2
      // 35: athrow
   }

   @Override
   public void onFrameMetricCollected(long var1, long var3, long var5, long var7, boolean var9, boolean var10, float var11) {
      if (this.frames.size() <= 3600) {
         long var12 = (long)((double)ONE_SECOND_NANOS / var11);
         this.lastKnownFrameDurationNanos = var12;
         if (var9 || var10) {
            this.frames.add(new SpanFrameMetricsCollector.Frame(var1, var3, var5, var7, var9, var10, var12));
         }
      }
   }

   @Override
   public void onSpanFinished(ISpan param1) {
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
      // 01: getfield io/sentry/android/core/SpanFrameMetricsCollector.enabled Z
      // 04: ifne 08
      // 07: return
      // 08: aload 1
      // 09: instanceof io/sentry/NoOpSpan
      // 0c: ifeq 10
      // 0f: return
      // 10: aload 1
      // 11: instanceof io/sentry/NoOpTransaction
      // 14: ifeq 18
      // 17: return
      // 18: aload 0
      // 19: getfield io/sentry/android/core/SpanFrameMetricsCollector.lock Ljava/lang/Object;
      // 1c: astore 2
      // 1d: aload 2
      // 1e: monitorenter
      // 1f: aload 0
      // 20: getfield io/sentry/android/core/SpanFrameMetricsCollector.runningSpans Ljava/util/SortedSet;
      // 23: aload 1
      // 24: invokeinterface java/util/SortedSet.contains (Ljava/lang/Object;)Z 2
      // 29: ifne 2f
      // 2c: aload 2
      // 2d: monitorexit
      // 2e: return
      // 2f: aload 2
      // 30: monitorexit
      // 31: aload 0
      // 32: aload 1
      // 33: invokespecial io/sentry/android/core/SpanFrameMetricsCollector.captureFrameMetrics (Lio/sentry/ISpan;)V
      // 36: aload 0
      // 37: getfield io/sentry/android/core/SpanFrameMetricsCollector.lock Ljava/lang/Object;
      // 3a: astore 1
      // 3b: aload 1
      // 3c: monitorenter
      // 3d: aload 0
      // 3e: getfield io/sentry/android/core/SpanFrameMetricsCollector.runningSpans Ljava/util/SortedSet;
      // 41: invokeinterface java/util/SortedSet.isEmpty ()Z 1
      // 46: ifeq 50
      // 49: aload 0
      // 4a: invokevirtual io/sentry/android/core/SpanFrameMetricsCollector.clear ()V
      // 4d: goto 80
      // 50: aload 0
      // 51: getfield io/sentry/android/core/SpanFrameMetricsCollector.runningSpans Ljava/util/SortedSet;
      // 54: invokeinterface java/util/SortedSet.first ()Ljava/lang/Object; 1
      // 59: checkcast io/sentry/ISpan
      // 5c: astore 2
      // 5d: aload 0
      // 5e: getfield io/sentry/android/core/SpanFrameMetricsCollector.frames Ljava/util/concurrent/ConcurrentSkipListSet;
      // 61: astore 3
      // 62: new io/sentry/android/core/SpanFrameMetricsCollector$Frame
      // 65: astore 4
      // 67: aload 4
      // 69: aload 2
      // 6a: invokeinterface io/sentry/ISpan.getStartDate ()Lio/sentry/SentryDate; 1
      // 6f: invokestatic io/sentry/android/core/SpanFrameMetricsCollector.toNanoTime (Lio/sentry/SentryDate;)J
      // 72: invokespecial io/sentry/android/core/SpanFrameMetricsCollector$Frame.<init> (J)V
      // 75: aload 3
      // 76: aload 4
      // 78: invokevirtual java/util/concurrent/ConcurrentSkipListSet.headSet (Ljava/lang/Object;)Ljava/util/NavigableSet;
      // 7b: invokeinterface java/util/NavigableSet.clear ()V 1
      // 80: aload 1
      // 81: monitorexit
      // 82: return
      // 83: astore 2
      // 84: aload 1
      // 85: monitorexit
      // 86: aload 2
      // 87: athrow
      // 88: astore 1
      // 89: aload 2
      // 8a: monitorexit
      // 8b: aload 1
      // 8c: athrow
   }

   @Override
   public void onSpanStarted(ISpan param1) {
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
      // 01: getfield io/sentry/android/core/SpanFrameMetricsCollector.enabled Z
      // 04: ifne 08
      // 07: return
      // 08: aload 1
      // 09: instanceof io/sentry/NoOpSpan
      // 0c: ifeq 10
      // 0f: return
      // 10: aload 1
      // 11: instanceof io/sentry/NoOpTransaction
      // 14: ifeq 18
      // 17: return
      // 18: aload 0
      // 19: getfield io/sentry/android/core/SpanFrameMetricsCollector.lock Ljava/lang/Object;
      // 1c: astore 2
      // 1d: aload 2
      // 1e: monitorenter
      // 1f: aload 0
      // 20: getfield io/sentry/android/core/SpanFrameMetricsCollector.runningSpans Ljava/util/SortedSet;
      // 23: aload 1
      // 24: invokeinterface java/util/SortedSet.add (Ljava/lang/Object;)Z 2
      // 29: pop
      // 2a: aload 0
      // 2b: getfield io/sentry/android/core/SpanFrameMetricsCollector.listenerId Ljava/lang/String;
      // 2e: ifnonnull 3d
      // 31: aload 0
      // 32: aload 0
      // 33: getfield io/sentry/android/core/SpanFrameMetricsCollector.frameMetricsCollector Lio/sentry/android/core/internal/util/SentryFrameMetricsCollector;
      // 36: aload 0
      // 37: invokevirtual io/sentry/android/core/internal/util/SentryFrameMetricsCollector.startCollection (Lio/sentry/android/core/internal/util/SentryFrameMetricsCollector$FrameMetricsCollectorListener;)Ljava/lang/String;
      // 3a: putfield io/sentry/android/core/SpanFrameMetricsCollector.listenerId Ljava/lang/String;
      // 3d: aload 2
      // 3e: monitorexit
      // 3f: return
      // 40: astore 1
      // 41: aload 2
      // 42: monitorexit
      // 43: aload 1
      // 44: athrow
   }

   private static class Frame implements Comparable<SpanFrameMetricsCollector.Frame> {
      private final long delayNanos;
      private final long durationNanos;
      private final long endNanos;
      private final long expectedDurationNanos;
      private final boolean isFrozen;
      private final boolean isSlow;
      private final long startNanos;

      Frame(long var1) {
         this(var1, var1, 0L, 0L, false, false, 0L);
      }

      Frame(long var1, long var3, long var5, long var7, boolean var9, boolean var10, long var11) {
         this.startNanos = var1;
         this.endNanos = var3;
         this.durationNanos = var5;
         this.delayNanos = var7;
         this.isSlow = var9;
         this.isFrozen = var10;
         this.expectedDurationNanos = var11;
      }

      public int compareTo(SpanFrameMetricsCollector.Frame var1) {
         return Long.compare(this.endNanos, var1.endNanos);
      }
   }
}
