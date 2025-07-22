package io.sentry.metrics;

import io.sentry.MeasurementUnit;
import io.sentry.protocol.MetricSummary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class LocalMetricsAggregator {
   private final Map<String, Map<String, GaugeMetric>> buckets = new HashMap<>();

   public void add(String param1, MetricType param2, String param3, double param4, MeasurementUnit param6, Map<String, String> param7) {
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
      // 00: aload 2
      // 01: aload 3
      // 02: aload 6
      // 04: invokestatic io/sentry/metrics/MetricsHelper.getExportKey (Lio/sentry/metrics/MetricType;Ljava/lang/String;Lio/sentry/MeasurementUnit;)Ljava/lang/String;
      // 07: astore 10
      // 09: aload 0
      // 0a: getfield io/sentry/metrics/LocalMetricsAggregator.buckets Ljava/util/Map;
      // 0d: astore 9
      // 0f: aload 9
      // 11: monitorenter
      // 12: aload 0
      // 13: getfield io/sentry/metrics/LocalMetricsAggregator.buckets Ljava/util/Map;
      // 16: aload 10
      // 18: invokeinterface java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 1d: checkcast java/util/Map
      // 20: astore 8
      // 22: aload 8
      // 24: astore 2
      // 25: aload 8
      // 27: ifnonnull 3f
      // 2a: new java/util/HashMap
      // 2d: astore 2
      // 2e: aload 2
      // 2f: invokespecial java/util/HashMap.<init> ()V
      // 32: aload 0
      // 33: getfield io/sentry/metrics/LocalMetricsAggregator.buckets Ljava/util/Map;
      // 36: aload 10
      // 38: aload 2
      // 39: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 3e: pop
      // 3f: aload 2
      // 40: aload 1
      // 41: invokeinterface java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 46: checkcast io/sentry/metrics/GaugeMetric
      // 49: astore 8
      // 4b: aload 8
      // 4d: ifnonnull 6e
      // 50: new io/sentry/metrics/GaugeMetric
      // 53: astore 8
      // 55: aload 8
      // 57: aload 3
      // 58: dload 4
      // 5a: aload 6
      // 5c: aload 7
      // 5e: invokespecial io/sentry/metrics/GaugeMetric.<init> (Ljava/lang/String;DLio/sentry/MeasurementUnit;Ljava/util/Map;)V
      // 61: aload 2
      // 62: aload 1
      // 63: aload 8
      // 65: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 6a: pop
      // 6b: goto 75
      // 6e: aload 8
      // 70: dload 4
      // 72: invokevirtual io/sentry/metrics/GaugeMetric.add (D)V
      // 75: aload 9
      // 77: monitorexit
      // 78: return
      // 79: astore 1
      // 7a: aload 9
      // 7c: monitorexit
      // 7d: aload 1
      // 7e: athrow
   }

   public Map<String, List<MetricSummary>> getSummaries() {
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
      // 00: new java/util/HashMap
      // 03: dup
      // 04: invokespecial java/util/HashMap.<init> ()V
      // 07: astore 2
      // 08: aload 0
      // 09: getfield io/sentry/metrics/LocalMetricsAggregator.buckets Ljava/util/Map;
      // 0c: astore 1
      // 0d: aload 1
      // 0e: monitorenter
      // 0f: aload 0
      // 10: getfield io/sentry/metrics/LocalMetricsAggregator.buckets Ljava/util/Map;
      // 13: invokeinterface java/util/Map.entrySet ()Ljava/util/Set; 1
      // 18: invokeinterface java/util/Set.iterator ()Ljava/util/Iterator; 1
      // 1d: astore 3
      // 1e: aload 3
      // 1f: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 24: ifeq b8
      // 27: aload 3
      // 28: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 2d: checkcast java/util/Map$Entry
      // 30: astore 6
      // 32: aload 6
      // 34: invokeinterface java/util/Map$Entry.getKey ()Ljava/lang/Object; 1
      // 39: checkcast java/lang/String
      // 3c: invokestatic java/util/Objects.requireNonNull (Ljava/lang/Object;)Ljava/lang/Object;
      // 3f: checkcast java/lang/String
      // 42: astore 5
      // 44: new java/util/ArrayList
      // 47: astore 4
      // 49: aload 4
      // 4b: invokespecial java/util/ArrayList.<init> ()V
      // 4e: aload 6
      // 50: invokeinterface java/util/Map$Entry.getValue ()Ljava/lang/Object; 1
      // 55: checkcast java/util/Map
      // 58: invokeinterface java/util/Map.values ()Ljava/util/Collection; 1
      // 5d: invokeinterface java/util/Collection.iterator ()Ljava/util/Iterator; 1
      // 62: astore 6
      // 64: aload 6
      // 66: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 6b: ifeq aa
      // 6e: aload 6
      // 70: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 75: checkcast io/sentry/metrics/GaugeMetric
      // 78: astore 7
      // 7a: new io/sentry/protocol/MetricSummary
      // 7d: astore 8
      // 7f: aload 8
      // 81: aload 7
      // 83: invokevirtual io/sentry/metrics/GaugeMetric.getMin ()D
      // 86: aload 7
      // 88: invokevirtual io/sentry/metrics/GaugeMetric.getMax ()D
      // 8b: aload 7
      // 8d: invokevirtual io/sentry/metrics/GaugeMetric.getSum ()D
      // 90: aload 7
      // 92: invokevirtual io/sentry/metrics/GaugeMetric.getCount ()I
      // 95: aload 7
      // 97: invokevirtual io/sentry/metrics/GaugeMetric.getTags ()Ljava/util/Map;
      // 9a: invokespecial io/sentry/protocol/MetricSummary.<init> (DDDILjava/util/Map;)V
      // 9d: aload 4
      // 9f: aload 8
      // a1: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // a6: pop
      // a7: goto 64
      // aa: aload 2
      // ab: aload 5
      // ad: aload 4
      // af: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // b4: pop
      // b5: goto 1e
      // b8: aload 1
      // b9: monitorexit
      // ba: aload 2
      // bb: areturn
      // bc: astore 2
      // bd: aload 1
      // be: monitorexit
      // bf: aload 2
      // c0: athrow
   }
}
