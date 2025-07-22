package io.sentry;

import io.sentry.metrics.IMetricsClient;
import io.sentry.metrics.LocalMetricsAggregator;
import io.sentry.metrics.Metric;
import io.sentry.metrics.MetricType;
import io.sentry.metrics.MetricsHelper;
import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.CRC32;

public final class MetricsAggregator implements IMetricsAggregator, Runnable, Closeable {
   private static final Charset UTF8 = Charset.forName("UTF-8");
   private final SentryOptions.BeforeEmitMetricCallback beforeEmitCallback;
   private final NavigableMap<Long, Map<String, Metric>> buckets;
   private final IMetricsClient client;
   private final SentryDateProvider dateProvider;
   private volatile ISentryExecutorService executorService;
   private volatile boolean flushScheduled;
   private volatile boolean isClosed = false;
   private final ILogger logger;
   private final int maxWeight;
   private final AtomicInteger totalBucketsWeight;

   public MetricsAggregator(SentryOptions var1, IMetricsClient var2) {
      this(var2, var1.getLogger(), var1.getDateProvider(), 100000, var1.getBeforeEmitMetricCallback(), NoOpSentryExecutorService.getInstance());
   }

   public MetricsAggregator(
      IMetricsClient var1, ILogger var2, SentryDateProvider var3, int var4, SentryOptions.BeforeEmitMetricCallback var5, ISentryExecutorService var6
   ) {
      this.flushScheduled = false;
      this.buckets = new ConcurrentSkipListMap<>();
      this.totalBucketsWeight = new AtomicInteger();
      this.client = var1;
      this.logger = var2;
      this.dateProvider = var3;
      this.maxWeight = var4;
      this.beforeEmitCallback = var5;
      this.executorService = var6;
   }

   private void add(
      MetricType param1, String param2, double param3, MeasurementUnit param5, Map<String, String> param6, long param7, LocalMetricsAggregator param9
   ) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 000: dload 3
      // 001: dstore 10
      // 003: aload 0
      // 004: getfield io/sentry/MetricsAggregator.isClosed Z
      // 007: ifeq 00b
      // 00a: return
      // 00b: aload 0
      // 00c: getfield io/sentry/MetricsAggregator.beforeEmitCallback Lio/sentry/SentryOptions$BeforeEmitMetricCallback;
      // 00f: astore 14
      // 011: aload 14
      // 013: ifnull 03a
      // 016: aload 14
      // 018: aload 2
      // 019: aload 6
      // 01b: invokeinterface io/sentry/SentryOptions$BeforeEmitMetricCallback.execute (Ljava/lang/String;Ljava/util/Map;)Z 3
      // 020: istore 13
      // 022: iload 13
      // 024: ifne 03a
      // 027: return
      // 028: astore 14
      // 02a: aload 0
      // 02b: getfield io/sentry/MetricsAggregator.logger Lio/sentry/ILogger;
      // 02e: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 031: ldc "The beforeEmit callback threw an exception."
      // 033: aload 14
      // 035: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 03a: aload 0
      // 03b: lload 7
      // 03d: invokestatic io/sentry/metrics/MetricsHelper.getTimeBucketKey (J)J
      // 040: invokespecial io/sentry/MetricsAggregator.getOrAddTimeBucket (J)Ljava/util/Map;
      // 043: astore 15
      // 045: aload 1
      // 046: aload 2
      // 047: aload 5
      // 049: aload 6
      // 04b: invokestatic io/sentry/metrics/MetricsHelper.getMetricBucketKey (Lio/sentry/metrics/MetricType;Ljava/lang/String;Lio/sentry/MeasurementUnit;Ljava/util/Map;)Ljava/lang/String;
      // 04e: astore 16
      // 050: aload 15
      // 052: monitorenter
      // 053: aload 15
      // 055: aload 16
      // 057: invokeinterface java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 05c: checkcast io/sentry/metrics/Metric
      // 05f: astore 14
      // 061: aload 14
      // 063: ifnull 081
      // 066: aload 14
      // 068: invokevirtual io/sentry/metrics/Metric.getWeight ()I
      // 06b: istore 12
      // 06d: aload 14
      // 06f: dload 10
      // 071: invokevirtual io/sentry/metrics/Metric.add (D)V
      // 074: aload 14
      // 076: invokevirtual io/sentry/metrics/Metric.getWeight ()I
      // 079: iload 12
      // 07b: isub
      // 07c: istore 12
      // 07e: goto 129
      // 081: getstatic io/sentry/MetricsAggregator$1.$SwitchMap$io$sentry$metrics$MetricType [I
      // 084: aload 1
      // 085: invokevirtual io/sentry/metrics/MetricType.ordinal ()I
      // 088: iaload
      // 089: istore 12
      // 08b: iload 12
      // 08d: bipush 1
      // 08e: if_icmpeq 107
      // 091: iload 12
      // 093: bipush 2
      // 094: if_icmpeq 0f5
      // 097: iload 12
      // 099: bipush 3
      // 09a: if_icmpeq 0e3
      // 09d: iload 12
      // 09f: bipush 4
      // 0a0: if_icmpne 0be
      // 0a3: new io/sentry/metrics/SetMetric
      // 0a6: astore 14
      // 0a8: aload 14
      // 0aa: aload 2
      // 0ab: aload 5
      // 0ad: aload 6
      // 0af: invokespecial io/sentry/metrics/SetMetric.<init> (Ljava/lang/String;Lio/sentry/MeasurementUnit;Ljava/util/Map;)V
      // 0b2: aload 14
      // 0b4: dload 10
      // 0b6: d2i
      // 0b7: i2d
      // 0b8: invokevirtual io/sentry/metrics/Metric.add (D)V
      // 0bb: goto 116
      // 0be: new java/lang/IllegalArgumentException
      // 0c1: astore 2
      // 0c2: new java/lang/StringBuilder
      // 0c5: astore 5
      // 0c7: aload 5
      // 0c9: ldc "Unknown MetricType: "
      // 0cb: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 0ce: aload 5
      // 0d0: aload 1
      // 0d1: invokevirtual io/sentry/metrics/MetricType.name ()Ljava/lang/String;
      // 0d4: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 0d7: pop
      // 0d8: aload 2
      // 0d9: aload 5
      // 0db: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 0de: invokespecial java/lang/IllegalArgumentException.<init> (Ljava/lang/String;)V
      // 0e1: aload 2
      // 0e2: athrow
      // 0e3: new io/sentry/metrics/DistributionMetric
      // 0e6: dup
      // 0e7: aload 2
      // 0e8: dload 3
      // 0e9: aload 5
      // 0eb: aload 6
      // 0ed: invokespecial io/sentry/metrics/DistributionMetric.<init> (Ljava/lang/String;DLio/sentry/MeasurementUnit;Ljava/util/Map;)V
      // 0f0: astore 14
      // 0f2: goto 116
      // 0f5: new io/sentry/metrics/GaugeMetric
      // 0f8: dup
      // 0f9: aload 2
      // 0fa: dload 3
      // 0fb: aload 5
      // 0fd: aload 6
      // 0ff: invokespecial io/sentry/metrics/GaugeMetric.<init> (Ljava/lang/String;DLio/sentry/MeasurementUnit;Ljava/util/Map;)V
      // 102: astore 14
      // 104: goto 116
      // 107: new io/sentry/metrics/CounterMetric
      // 10a: dup
      // 10b: aload 2
      // 10c: dload 3
      // 10d: aload 5
      // 10f: aload 6
      // 111: invokespecial io/sentry/metrics/CounterMetric.<init> (Ljava/lang/String;DLio/sentry/MeasurementUnit;Ljava/util/Map;)V
      // 114: astore 14
      // 116: aload 14
      // 118: invokevirtual io/sentry/metrics/Metric.getWeight ()I
      // 11b: istore 12
      // 11d: aload 15
      // 11f: aload 16
      // 121: aload 14
      // 123: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 128: pop
      // 129: aload 0
      // 12a: getfield io/sentry/MetricsAggregator.totalBucketsWeight Ljava/util/concurrent/atomic/AtomicInteger;
      // 12d: iload 12
      // 12f: invokevirtual java/util/concurrent/atomic/AtomicInteger.addAndGet (I)I
      // 132: pop
      // 133: aload 15
      // 135: monitorexit
      // 136: aload 9
      // 138: ifnull 156
      // 13b: aload 1
      // 13c: getstatic io/sentry/metrics/MetricType.Set Lio/sentry/metrics/MetricType;
      // 13f: if_acmpne 147
      // 142: iload 12
      // 144: i2d
      // 145: dstore 10
      // 147: aload 9
      // 149: aload 16
      // 14b: aload 1
      // 14c: aload 2
      // 14d: dload 10
      // 14f: aload 5
      // 151: aload 6
      // 153: invokevirtual io/sentry/metrics/LocalMetricsAggregator.add (Ljava/lang/String;Lio/sentry/metrics/MetricType;Ljava/lang/String;DLio/sentry/MeasurementUnit;Ljava/util/Map;)V
      // 156: aload 0
      // 157: invokespecial io/sentry/MetricsAggregator.isOverWeight ()Z
      // 15a: istore 13
      // 15c: aload 0
      // 15d: getfield io/sentry/MetricsAggregator.isClosed Z
      // 160: ifne 1bb
      // 163: iload 13
      // 165: ifne 16f
      // 168: aload 0
      // 169: getfield io/sentry/MetricsAggregator.flushScheduled Z
      // 16c: ifne 1bb
      // 16f: aload 0
      // 170: monitorenter
      // 171: aload 0
      // 172: getfield io/sentry/MetricsAggregator.isClosed Z
      // 175: ifne 1b1
      // 178: aload 0
      // 179: getfield io/sentry/MetricsAggregator.executorService Lio/sentry/ISentryExecutorService;
      // 17c: instanceof io/sentry/NoOpSentryExecutorService
      // 17f: ifeq 18f
      // 182: new io/sentry/SentryExecutorService
      // 185: astore 1
      // 186: aload 1
      // 187: invokespecial io/sentry/SentryExecutorService.<init> ()V
      // 18a: aload 0
      // 18b: aload 1
      // 18c: putfield io/sentry/MetricsAggregator.executorService Lio/sentry/ISentryExecutorService;
      // 18f: aload 0
      // 190: bipush 1
      // 191: putfield io/sentry/MetricsAggregator.flushScheduled Z
      // 194: iload 13
      // 196: ifeq 19f
      // 199: lconst_0
      // 19a: lstore 7
      // 19c: goto 1a4
      // 19f: ldc2_w 5000
      // 1a2: lstore 7
      // 1a4: aload 0
      // 1a5: getfield io/sentry/MetricsAggregator.executorService Lio/sentry/ISentryExecutorService;
      // 1a8: aload 0
      // 1a9: lload 7
      // 1ab: invokeinterface io/sentry/ISentryExecutorService.schedule (Ljava/lang/Runnable;J)Ljava/util/concurrent/Future; 4
      // 1b0: pop
      // 1b1: aload 0
      // 1b2: monitorexit
      // 1b3: goto 1bb
      // 1b6: astore 1
      // 1b7: aload 0
      // 1b8: monitorexit
      // 1b9: aload 1
      // 1ba: athrow
      // 1bb: return
      // 1bc: astore 1
      // 1bd: aload 15
      // 1bf: monitorexit
      // 1c0: aload 1
      // 1c1: athrow
   }

   private static int getBucketWeight(Map<String, Metric> var0) {
      Iterator var2 = var0.values().iterator();
      int var1 = 0;

      while (var2.hasNext()) {
         var1 += ((Metric)var2.next()).getWeight();
      }

      return var1;
   }

   private Set<Long> getFlushableBuckets(boolean var1) {
      if (var1) {
         return this.buckets.keySet();
      } else {
         long var2 = MetricsHelper.getTimeBucketKey(MetricsHelper.getCutoffTimestampMs(this.nowMillis()));
         return this.buckets.headMap(var2, true).keySet();
      }
   }

   private Map<String, Metric> getOrAddTimeBucket(long param1) {
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
      // 01: getfield io/sentry/MetricsAggregator.buckets Ljava/util/NavigableMap;
      // 04: lload 1
      // 05: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 08: invokeinterface java/util/NavigableMap.get (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 0d: checkcast java/util/Map
      // 10: astore 4
      // 12: aload 4
      // 14: astore 3
      // 15: aload 4
      // 17: ifnonnull 60
      // 1a: aload 0
      // 1b: getfield io/sentry/MetricsAggregator.buckets Ljava/util/NavigableMap;
      // 1e: astore 5
      // 20: aload 5
      // 22: monitorenter
      // 23: aload 0
      // 24: getfield io/sentry/MetricsAggregator.buckets Ljava/util/NavigableMap;
      // 27: lload 1
      // 28: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 2b: invokeinterface java/util/NavigableMap.get (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 30: checkcast java/util/Map
      // 33: astore 4
      // 35: aload 4
      // 37: astore 3
      // 38: aload 4
      // 3a: ifnonnull 54
      // 3d: new java/util/HashMap
      // 40: astore 3
      // 41: aload 3
      // 42: invokespecial java/util/HashMap.<init> ()V
      // 45: aload 0
      // 46: getfield io/sentry/MetricsAggregator.buckets Ljava/util/NavigableMap;
      // 49: lload 1
      // 4a: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 4d: aload 3
      // 4e: invokeinterface java/util/NavigableMap.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 53: pop
      // 54: aload 5
      // 56: monitorexit
      // 57: goto 60
      // 5a: astore 3
      // 5b: aload 5
      // 5d: monitorexit
      // 5e: aload 3
      // 5f: athrow
      // 60: aload 3
      // 61: areturn
   }

   private boolean isOverWeight() {
      boolean var1;
      if (this.buckets.size() + this.totalBucketsWeight.get() >= this.maxWeight) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private long nowMillis() {
      return TimeUnit.NANOSECONDS.toMillis(this.dateProvider.now().nanoTimestamp());
   }

   @Override
   public void close() throws IOException {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: bipush 1
      // 04: putfield io/sentry/MetricsAggregator.isClosed Z
      // 07: aload 0
      // 08: getfield io/sentry/MetricsAggregator.executorService Lio/sentry/ISentryExecutorService;
      // 0b: lconst_0
      // 0c: invokeinterface io/sentry/ISentryExecutorService.close (J)V 3
      // 11: aload 0
      // 12: monitorexit
      // 13: aload 0
      // 14: bipush 1
      // 15: invokevirtual io/sentry/MetricsAggregator.flush (Z)V
      // 18: return
      // 19: astore 1
      // 1a: aload 0
      // 1b: monitorexit
      // 1c: aload 1
      // 1d: athrow
   }

   @Override
   public void distribution(String var1, double var2, MeasurementUnit var4, Map<String, String> var5, long var6, LocalMetricsAggregator var8) {
      this.add(MetricType.Distribution, var1, var2, var4, var5, var6, var8);
   }

   @Override
   public void flush(boolean param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 000: iload 1
      // 001: istore 4
      // 003: iload 1
      // 004: ifne 027
      // 007: iload 1
      // 008: istore 4
      // 00a: aload 0
      // 00b: invokespecial io/sentry/MetricsAggregator.isOverWeight ()Z
      // 00e: ifeq 027
      // 011: aload 0
      // 012: getfield io/sentry/MetricsAggregator.logger Lio/sentry/ILogger;
      // 015: getstatic io/sentry/SentryLevel.INFO Lio/sentry/SentryLevel;
      // 018: ldc_w "Metrics: total weight exceeded, flushing all buckets"
      // 01b: bipush 0
      // 01c: anewarray 4
      // 01f: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 024: bipush 1
      // 025: istore 4
      // 027: aload 0
      // 028: bipush 0
      // 029: putfield io/sentry/MetricsAggregator.flushScheduled Z
      // 02c: aload 0
      // 02d: iload 4
      // 02f: invokespecial io/sentry/MetricsAggregator.getFlushableBuckets (Z)Ljava/util/Set;
      // 032: astore 5
      // 034: aload 5
      // 036: invokeinterface java/util/Set.isEmpty ()Z 1
      // 03b: ifeq 052
      // 03e: aload 0
      // 03f: getfield io/sentry/MetricsAggregator.logger Lio/sentry/ILogger;
      // 042: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 045: ldc_w "Metrics: nothing to flush"
      // 048: bipush 0
      // 049: anewarray 4
      // 04c: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 051: return
      // 052: aload 0
      // 053: getfield io/sentry/MetricsAggregator.logger Lio/sentry/ILogger;
      // 056: astore 8
      // 058: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 05b: astore 6
      // 05d: new java/lang/StringBuilder
      // 060: dup
      // 061: ldc_w "Metrics: flushing "
      // 064: invokespecial java/lang/StringBuilder.<init> (Ljava/lang/String;)V
      // 067: astore 7
      // 069: aload 7
      // 06b: aload 5
      // 06d: invokeinterface java/util/Set.size ()I 1
      // 072: invokevirtual java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
      // 075: pop
      // 076: aload 7
      // 078: ldc_w " buckets"
      // 07b: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 07e: pop
      // 07f: aload 8
      // 081: aload 6
      // 083: aload 7
      // 085: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 088: bipush 0
      // 089: anewarray 4
      // 08c: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 091: new java/util/HashMap
      // 094: dup
      // 095: invokespecial java/util/HashMap.<init> ()V
      // 098: astore 6
      // 09a: aload 5
      // 09c: invokeinterface java/util/Set.iterator ()Ljava/util/Iterator; 1
      // 0a1: astore 8
      // 0a3: bipush 0
      // 0a4: istore 2
      // 0a5: aload 8
      // 0a7: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 0ac: ifeq 10d
      // 0af: aload 8
      // 0b1: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 0b6: checkcast java/lang/Long
      // 0b9: astore 7
      // 0bb: aload 7
      // 0bd: invokevirtual java/lang/Long.longValue ()J
      // 0c0: pop2
      // 0c1: aload 0
      // 0c2: getfield io/sentry/MetricsAggregator.buckets Ljava/util/NavigableMap;
      // 0c5: aload 7
      // 0c7: invokeinterface java/util/NavigableMap.remove (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 0cc: checkcast java/util/Map
      // 0cf: astore 5
      // 0d1: aload 5
      // 0d3: ifnull 0a5
      // 0d6: aload 5
      // 0d8: monitorenter
      // 0d9: aload 5
      // 0db: invokestatic io/sentry/MetricsAggregator.getBucketWeight (Ljava/util/Map;)I
      // 0de: istore 3
      // 0df: aload 0
      // 0e0: getfield io/sentry/MetricsAggregator.totalBucketsWeight Ljava/util/concurrent/atomic/AtomicInteger;
      // 0e3: iload 3
      // 0e4: ineg
      // 0e5: invokevirtual java/util/concurrent/atomic/AtomicInteger.addAndGet (I)I
      // 0e8: pop
      // 0e9: iload 2
      // 0ea: aload 5
      // 0ec: invokeinterface java/util/Map.size ()I 1
      // 0f1: iadd
      // 0f2: istore 2
      // 0f3: aload 6
      // 0f5: aload 7
      // 0f7: aload 5
      // 0f9: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 0fe: pop
      // 0ff: aload 5
      // 101: monitorexit
      // 102: goto 0a5
      // 105: astore 6
      // 107: aload 5
      // 109: monitorexit
      // 10a: aload 6
      // 10c: athrow
      // 10d: iload 2
      // 10e: ifne 125
      // 111: aload 0
      // 112: getfield io/sentry/MetricsAggregator.logger Lio/sentry/ILogger;
      // 115: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 118: ldc_w "Metrics: only empty buckets found"
      // 11b: bipush 0
      // 11c: anewarray 4
      // 11f: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 124: return
      // 125: aload 0
      // 126: getfield io/sentry/MetricsAggregator.logger Lio/sentry/ILogger;
      // 129: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 12c: ldc_w "Metrics: capturing metrics"
      // 12f: bipush 0
      // 130: anewarray 4
      // 133: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 138: aload 0
      // 139: getfield io/sentry/MetricsAggregator.client Lio/sentry/metrics/IMetricsClient;
      // 13c: new io/sentry/metrics/EncodedMetrics
      // 13f: dup
      // 140: aload 6
      // 142: invokespecial io/sentry/metrics/EncodedMetrics.<init> (Ljava/util/Map;)V
      // 145: invokeinterface io/sentry/metrics/IMetricsClient.captureMetrics (Lio/sentry/metrics/EncodedMetrics;)Lio/sentry/protocol/SentryId; 2
      // 14a: pop
      // 14b: return
   }

   @Override
   public void gauge(String var1, double var2, MeasurementUnit var4, Map<String, String> var5, long var6, LocalMetricsAggregator var8) {
      this.add(MetricType.Gauge, var1, var2, var4, var5, var6, var8);
   }

   @Override
   public void increment(String var1, double var2, MeasurementUnit var4, Map<String, String> var5, long var6, LocalMetricsAggregator var8) {
      this.add(MetricType.Counter, var1, var2, var4, var5, var6, var8);
   }

   @Override
   public void run() {
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
      // 01: bipush 0
      // 02: invokevirtual io/sentry/MetricsAggregator.flush (Z)V
      // 05: aload 0
      // 06: monitorenter
      // 07: aload 0
      // 08: getfield io/sentry/MetricsAggregator.isClosed Z
      // 0b: ifne 28
      // 0e: aload 0
      // 0f: getfield io/sentry/MetricsAggregator.buckets Ljava/util/NavigableMap;
      // 12: invokeinterface java/util/NavigableMap.isEmpty ()Z 1
      // 17: ifne 28
      // 1a: aload 0
      // 1b: getfield io/sentry/MetricsAggregator.executorService Lio/sentry/ISentryExecutorService;
      // 1e: aload 0
      // 1f: ldc2_w 5000
      // 22: invokeinterface io/sentry/ISentryExecutorService.schedule (Ljava/lang/Runnable;J)Ljava/util/concurrent/Future; 4
      // 27: pop
      // 28: aload 0
      // 29: monitorexit
      // 2a: return
      // 2b: astore 1
      // 2c: aload 0
      // 2d: monitorexit
      // 2e: aload 1
      // 2f: athrow
   }

   @Override
   public void set(String var1, int var2, MeasurementUnit var3, Map<String, String> var4, long var5, LocalMetricsAggregator var7) {
      this.add(MetricType.Set, var1, var2, var3, var4, var5, var7);
   }

   @Override
   public void set(String var1, String var2, MeasurementUnit var3, Map<String, String> var4, long var5, LocalMetricsAggregator var7) {
      byte[] var10 = var2.getBytes(UTF8);
      CRC32 var9 = new CRC32();
      var9.update(var10, 0, var10.length);
      int var8 = (int)var9.getValue();
      this.add(MetricType.Set, var1, var8, var3, var4, var5, var7);
   }
}
