package io.sentry.android.core;

import io.sentry.ILogger;
import io.sentry.ISentryExecutorService;
import io.sentry.PerformanceCollectionData;
import io.sentry.android.core.internal.util.SentryFrameMetricsCollector;
import io.sentry.profilemeasurements.ProfileMeasurement;
import io.sentry.profilemeasurements.ProfileMeasurementValue;
import io.sentry.util.Objects;
import java.io.File;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

public class AndroidProfiler {
   private static final int BUFFER_SIZE_BYTES = 3000000;
   private static final int PROFILING_TIMEOUT_MILLIS = 30000;
   private final BuildInfoProvider buildInfoProvider;
   private final ISentryExecutorService executorService;
   private final SentryFrameMetricsCollector frameMetricsCollector;
   private String frameMetricsCollectorId;
   private final ArrayDeque<ProfileMeasurementValue> frozenFrameRenderMeasurements;
   private final int intervalUs;
   private boolean isRunning;
   private final ILogger logger;
   private final Map<String, ProfileMeasurement> measurementsMap;
   private long profileStartNanos = 0L;
   private Future<?> scheduledFinish = null;
   private final ArrayDeque<ProfileMeasurementValue> screenFrameRateMeasurements;
   private final ArrayDeque<ProfileMeasurementValue> slowFrameRenderMeasurements;
   private File traceFile = null;
   private final File traceFilesDir;

   public AndroidProfiler(String var1, int var2, SentryFrameMetricsCollector var3, ISentryExecutorService var4, ILogger var5, BuildInfoProvider var6) {
      this.screenFrameRateMeasurements = new ArrayDeque<>();
      this.slowFrameRenderMeasurements = new ArrayDeque<>();
      this.frozenFrameRenderMeasurements = new ArrayDeque<>();
      this.measurementsMap = new HashMap<>();
      this.isRunning = false;
      this.traceFilesDir = new File(Objects.requireNonNull(var1, "TracesFilesDirPath is required"));
      this.intervalUs = var2;
      this.logger = Objects.requireNonNull(var5, "Logger is required");
      this.executorService = Objects.requireNonNull(var4, "ExecutorService is required.");
      this.frameMetricsCollector = Objects.requireNonNull(var3, "SentryFrameMetricsCollector is required");
      this.buildInfoProvider = Objects.requireNonNull(var6, "The BuildInfoProvider is required.");
   }

   private void putPerformanceCollectionDataInMeasurements(List<PerformanceCollectionData> param1) {
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
      // 001: getfield io/sentry/android/core/AndroidProfiler.buildInfoProvider Lio/sentry/android/core/BuildInfoProvider;
      // 004: invokevirtual io/sentry/android/core/BuildInfoProvider.getSdkInfoVersion ()I
      // 007: bipush 21
      // 009: if_icmpge 00d
      // 00c: return
      // 00d: invokestatic android/os/SystemClock.elapsedRealtimeNanos ()J
      // 010: aload 0
      // 011: getfield io/sentry/android/core/AndroidProfiler.profileStartNanos J
      // 014: lsub
      // 015: getstatic java/util/concurrent/TimeUnit.MILLISECONDS Ljava/util/concurrent/TimeUnit;
      // 018: invokestatic java/lang/System.currentTimeMillis ()J
      // 01b: invokevirtual java/util/concurrent/TimeUnit.toNanos (J)J
      // 01e: lsub
      // 01f: lstore 2
      // 020: aload 1
      // 021: ifnull 190
      // 024: new java/util/ArrayDeque
      // 027: dup
      // 028: aload 1
      // 029: invokeinterface java/util/List.size ()I 1
      // 02e: invokespecial java/util/ArrayDeque.<init> (I)V
      // 031: astore 7
      // 033: new java/util/ArrayDeque
      // 036: dup
      // 037: aload 1
      // 038: invokeinterface java/util/List.size ()I 1
      // 03d: invokespecial java/util/ArrayDeque.<init> (I)V
      // 040: astore 4
      // 042: new java/util/ArrayDeque
      // 045: dup
      // 046: aload 1
      // 047: invokeinterface java/util/List.size ()I 1
      // 04c: invokespecial java/util/ArrayDeque.<init> (I)V
      // 04f: astore 5
      // 051: aload 1
      // 052: monitorenter
      // 053: aload 1
      // 054: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 059: astore 6
      // 05b: aload 6
      // 05d: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 062: ifeq 127
      // 065: aload 6
      // 067: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 06c: checkcast io/sentry/PerformanceCollectionData
      // 06f: astore 8
      // 071: aload 8
      // 073: invokevirtual io/sentry/PerformanceCollectionData.getCpuData ()Lio/sentry/CpuCollectionData;
      // 076: astore 9
      // 078: aload 8
      // 07a: invokevirtual io/sentry/PerformanceCollectionData.getMemoryData ()Lio/sentry/MemoryCollectionData;
      // 07d: astore 8
      // 07f: aload 9
      // 081: ifnull 0ae
      // 084: new io/sentry/profilemeasurements/ProfileMeasurementValue
      // 087: astore 10
      // 089: aload 10
      // 08b: getstatic java/util/concurrent/TimeUnit.MILLISECONDS Ljava/util/concurrent/TimeUnit;
      // 08e: aload 9
      // 090: invokevirtual io/sentry/CpuCollectionData.getTimestampMillis ()J
      // 093: invokevirtual java/util/concurrent/TimeUnit.toNanos (J)J
      // 096: lload 2
      // 097: ladd
      // 098: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 09b: aload 9
      // 09d: invokevirtual io/sentry/CpuCollectionData.getCpuUsagePercentage ()D
      // 0a0: invokestatic java/lang/Double.valueOf (D)Ljava/lang/Double;
      // 0a3: invokespecial io/sentry/profilemeasurements/ProfileMeasurementValue.<init> (Ljava/lang/Long;Ljava/lang/Number;)V
      // 0a6: aload 5
      // 0a8: aload 10
      // 0aa: invokevirtual java/util/ArrayDeque.add (Ljava/lang/Object;)Z
      // 0ad: pop
      // 0ae: aload 8
      // 0b0: ifnull 0e9
      // 0b3: aload 8
      // 0b5: invokevirtual io/sentry/MemoryCollectionData.getUsedHeapMemory ()J
      // 0b8: ldc2_w -1
      // 0bb: lcmp
      // 0bc: ifle 0e9
      // 0bf: new io/sentry/profilemeasurements/ProfileMeasurementValue
      // 0c2: astore 9
      // 0c4: aload 9
      // 0c6: getstatic java/util/concurrent/TimeUnit.MILLISECONDS Ljava/util/concurrent/TimeUnit;
      // 0c9: aload 8
      // 0cb: invokevirtual io/sentry/MemoryCollectionData.getTimestampMillis ()J
      // 0ce: invokevirtual java/util/concurrent/TimeUnit.toNanos (J)J
      // 0d1: lload 2
      // 0d2: ladd
      // 0d3: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 0d6: aload 8
      // 0d8: invokevirtual io/sentry/MemoryCollectionData.getUsedHeapMemory ()J
      // 0db: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 0de: invokespecial io/sentry/profilemeasurements/ProfileMeasurementValue.<init> (Ljava/lang/Long;Ljava/lang/Number;)V
      // 0e1: aload 7
      // 0e3: aload 9
      // 0e5: invokevirtual java/util/ArrayDeque.add (Ljava/lang/Object;)Z
      // 0e8: pop
      // 0e9: aload 8
      // 0eb: ifnull 05b
      // 0ee: aload 8
      // 0f0: invokevirtual io/sentry/MemoryCollectionData.getUsedNativeMemory ()J
      // 0f3: ldc2_w -1
      // 0f6: lcmp
      // 0f7: ifle 05b
      // 0fa: new io/sentry/profilemeasurements/ProfileMeasurementValue
      // 0fd: astore 9
      // 0ff: aload 9
      // 101: getstatic java/util/concurrent/TimeUnit.MILLISECONDS Ljava/util/concurrent/TimeUnit;
      // 104: aload 8
      // 106: invokevirtual io/sentry/MemoryCollectionData.getTimestampMillis ()J
      // 109: invokevirtual java/util/concurrent/TimeUnit.toNanos (J)J
      // 10c: lload 2
      // 10d: ladd
      // 10e: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 111: aload 8
      // 113: invokevirtual io/sentry/MemoryCollectionData.getUsedNativeMemory ()J
      // 116: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 119: invokespecial io/sentry/profilemeasurements/ProfileMeasurementValue.<init> (Ljava/lang/Long;Ljava/lang/Number;)V
      // 11c: aload 4
      // 11e: aload 9
      // 120: invokevirtual java/util/ArrayDeque.add (Ljava/lang/Object;)Z
      // 123: pop
      // 124: goto 05b
      // 127: aload 1
      // 128: monitorexit
      // 129: aload 5
      // 12b: invokevirtual java/util/ArrayDeque.isEmpty ()Z
      // 12e: ifne 148
      // 131: aload 0
      // 132: getfield io/sentry/android/core/AndroidProfiler.measurementsMap Ljava/util/Map;
      // 135: ldc "cpu_usage"
      // 137: new io/sentry/profilemeasurements/ProfileMeasurement
      // 13a: dup
      // 13b: ldc "percent"
      // 13d: aload 5
      // 13f: invokespecial io/sentry/profilemeasurements/ProfileMeasurement.<init> (Ljava/lang/String;Ljava/util/Collection;)V
      // 142: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 147: pop
      // 148: aload 7
      // 14a: invokevirtual java/util/ArrayDeque.isEmpty ()Z
      // 14d: ifne 167
      // 150: aload 0
      // 151: getfield io/sentry/android/core/AndroidProfiler.measurementsMap Ljava/util/Map;
      // 154: ldc "memory_footprint"
      // 156: new io/sentry/profilemeasurements/ProfileMeasurement
      // 159: dup
      // 15a: ldc "byte"
      // 15c: aload 7
      // 15e: invokespecial io/sentry/profilemeasurements/ProfileMeasurement.<init> (Ljava/lang/String;Ljava/util/Collection;)V
      // 161: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 166: pop
      // 167: aload 4
      // 169: invokevirtual java/util/ArrayDeque.isEmpty ()Z
      // 16c: ifne 190
      // 16f: aload 0
      // 170: getfield io/sentry/android/core/AndroidProfiler.measurementsMap Ljava/util/Map;
      // 173: ldc "memory_native_footprint"
      // 175: new io/sentry/profilemeasurements/ProfileMeasurement
      // 178: dup
      // 179: ldc "byte"
      // 17b: aload 4
      // 17d: invokespecial io/sentry/profilemeasurements/ProfileMeasurement.<init> (Ljava/lang/String;Ljava/util/Collection;)V
      // 180: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 185: pop
      // 186: goto 190
      // 189: astore 4
      // 18b: aload 1
      // 18c: monitorexit
      // 18d: aload 4
      // 18f: athrow
      // 190: return
   }

   public void close() {
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
      // 03: getfield io/sentry/android/core/AndroidProfiler.scheduledFinish Ljava/util/concurrent/Future;
      // 06: astore 1
      // 07: aload 1
      // 08: ifnull 18
      // 0b: aload 1
      // 0c: bipush 1
      // 0d: invokeinterface java/util/concurrent/Future.cancel (Z)Z 2
      // 12: pop
      // 13: aload 0
      // 14: aconst_null
      // 15: putfield io/sentry/android/core/AndroidProfiler.scheduledFinish Ljava/util/concurrent/Future;
      // 18: aload 0
      // 19: getfield io/sentry/android/core/AndroidProfiler.isRunning Z
      // 1c: ifeq 26
      // 1f: aload 0
      // 20: bipush 1
      // 21: aconst_null
      // 22: invokevirtual io/sentry/android/core/AndroidProfiler.endAndCollect (ZLjava/util/List;)Lio/sentry/android/core/AndroidProfiler$ProfileEndData;
      // 25: pop
      // 26: aload 0
      // 27: monitorexit
      // 28: return
      // 29: astore 1
      // 2a: aload 0
      // 2b: monitorexit
      // 2c: aload 1
      // 2d: athrow
   }

   public AndroidProfiler.ProfileEndData endAndCollect(boolean param1, List<PerformanceCollectionData> param2) {
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
      // 003: getfield io/sentry/android/core/AndroidProfiler.isRunning Z
      // 006: ifne 020
      // 009: aload 0
      // 00a: getfield io/sentry/android/core/AndroidProfiler.logger Lio/sentry/ILogger;
      // 00d: getstatic io/sentry/SentryLevel.WARNING Lio/sentry/SentryLevel;
      // 010: ldc_w "Profiler not running"
      // 013: bipush 0
      // 014: anewarray 4
      // 017: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 01c: aload 0
      // 01d: monitorexit
      // 01e: aconst_null
      // 01f: areturn
      // 020: aload 0
      // 021: getfield io/sentry/android/core/AndroidProfiler.buildInfoProvider Lio/sentry/android/core/BuildInfoProvider;
      // 024: invokevirtual io/sentry/android/core/BuildInfoProvider.getSdkInfoVersion ()I
      // 027: istore 3
      // 028: iload 3
      // 029: bipush 21
      // 02b: if_icmpge 032
      // 02e: aload 0
      // 02f: monitorexit
      // 030: aconst_null
      // 031: areturn
      // 032: invokestatic android/os/Debug.stopMethodTracing ()V
      // 035: aload 0
      // 036: bipush 0
      // 037: putfield io/sentry/android/core/AndroidProfiler.isRunning Z
      // 03a: goto 053
      // 03d: astore 8
      // 03f: aload 0
      // 040: getfield io/sentry/android/core/AndroidProfiler.logger Lio/sentry/ILogger;
      // 043: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 046: ldc_w "Error while stopping profiling: "
      // 049: aload 8
      // 04b: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 050: goto 035
      // 053: aload 0
      // 054: getfield io/sentry/android/core/AndroidProfiler.frameMetricsCollector Lio/sentry/android/core/internal/util/SentryFrameMetricsCollector;
      // 057: aload 0
      // 058: getfield io/sentry/android/core/AndroidProfiler.frameMetricsCollectorId Ljava/lang/String;
      // 05b: invokevirtual io/sentry/android/core/internal/util/SentryFrameMetricsCollector.stopCollection (Ljava/lang/String;)V
      // 05e: invokestatic android/os/SystemClock.elapsedRealtimeNanos ()J
      // 061: lstore 6
      // 063: invokestatic android/os/Process.getElapsedCpuTime ()J
      // 066: lstore 4
      // 068: aload 0
      // 069: getfield io/sentry/android/core/AndroidProfiler.traceFile Ljava/io/File;
      // 06c: ifnonnull 086
      // 06f: aload 0
      // 070: getfield io/sentry/android/core/AndroidProfiler.logger Lio/sentry/ILogger;
      // 073: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 076: ldc_w "Trace file does not exists"
      // 079: bipush 0
      // 07a: anewarray 4
      // 07d: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 082: aload 0
      // 083: monitorexit
      // 084: aconst_null
      // 085: areturn
      // 086: aload 0
      // 087: getfield io/sentry/android/core/AndroidProfiler.slowFrameRenderMeasurements Ljava/util/ArrayDeque;
      // 08a: invokevirtual java/util/ArrayDeque.isEmpty ()Z
      // 08d: ifne 0b4
      // 090: aload 0
      // 091: getfield io/sentry/android/core/AndroidProfiler.measurementsMap Ljava/util/Map;
      // 094: astore 8
      // 096: new io/sentry/profilemeasurements/ProfileMeasurement
      // 099: astore 9
      // 09b: aload 9
      // 09d: ldc_w "nanosecond"
      // 0a0: aload 0
      // 0a1: getfield io/sentry/android/core/AndroidProfiler.slowFrameRenderMeasurements Ljava/util/ArrayDeque;
      // 0a4: invokespecial io/sentry/profilemeasurements/ProfileMeasurement.<init> (Ljava/lang/String;Ljava/util/Collection;)V
      // 0a7: aload 8
      // 0a9: ldc_w "slow_frame_renders"
      // 0ac: aload 9
      // 0ae: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 0b3: pop
      // 0b4: aload 0
      // 0b5: getfield io/sentry/android/core/AndroidProfiler.frozenFrameRenderMeasurements Ljava/util/ArrayDeque;
      // 0b8: invokevirtual java/util/ArrayDeque.isEmpty ()Z
      // 0bb: ifne 0e2
      // 0be: aload 0
      // 0bf: getfield io/sentry/android/core/AndroidProfiler.measurementsMap Ljava/util/Map;
      // 0c2: astore 8
      // 0c4: new io/sentry/profilemeasurements/ProfileMeasurement
      // 0c7: astore 9
      // 0c9: aload 9
      // 0cb: ldc_w "nanosecond"
      // 0ce: aload 0
      // 0cf: getfield io/sentry/android/core/AndroidProfiler.frozenFrameRenderMeasurements Ljava/util/ArrayDeque;
      // 0d2: invokespecial io/sentry/profilemeasurements/ProfileMeasurement.<init> (Ljava/lang/String;Ljava/util/Collection;)V
      // 0d5: aload 8
      // 0d7: ldc_w "frozen_frame_renders"
      // 0da: aload 9
      // 0dc: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 0e1: pop
      // 0e2: aload 0
      // 0e3: getfield io/sentry/android/core/AndroidProfiler.screenFrameRateMeasurements Ljava/util/ArrayDeque;
      // 0e6: invokevirtual java/util/ArrayDeque.isEmpty ()Z
      // 0e9: ifne 110
      // 0ec: aload 0
      // 0ed: getfield io/sentry/android/core/AndroidProfiler.measurementsMap Ljava/util/Map;
      // 0f0: astore 9
      // 0f2: new io/sentry/profilemeasurements/ProfileMeasurement
      // 0f5: astore 8
      // 0f7: aload 8
      // 0f9: ldc_w "hz"
      // 0fc: aload 0
      // 0fd: getfield io/sentry/android/core/AndroidProfiler.screenFrameRateMeasurements Ljava/util/ArrayDeque;
      // 100: invokespecial io/sentry/profilemeasurements/ProfileMeasurement.<init> (Ljava/lang/String;Ljava/util/Collection;)V
      // 103: aload 9
      // 105: ldc_w "screen_frame_rates"
      // 108: aload 8
      // 10a: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 10f: pop
      // 110: aload 0
      // 111: aload 2
      // 112: invokespecial io/sentry/android/core/AndroidProfiler.putPerformanceCollectionDataInMeasurements (Ljava/util/List;)V
      // 115: aload 0
      // 116: getfield io/sentry/android/core/AndroidProfiler.scheduledFinish Ljava/util/concurrent/Future;
      // 119: astore 2
      // 11a: aload 2
      // 11b: ifnull 12b
      // 11e: aload 2
      // 11f: bipush 1
      // 120: invokeinterface java/util/concurrent/Future.cancel (Z)Z 2
      // 125: pop
      // 126: aload 0
      // 127: aconst_null
      // 128: putfield io/sentry/android/core/AndroidProfiler.scheduledFinish Ljava/util/concurrent/Future;
      // 12b: new io/sentry/android/core/AndroidProfiler$ProfileEndData
      // 12e: dup
      // 12f: lload 6
      // 131: lload 4
      // 133: iload 1
      // 134: aload 0
      // 135: getfield io/sentry/android/core/AndroidProfiler.traceFile Ljava/io/File;
      // 138: aload 0
      // 139: getfield io/sentry/android/core/AndroidProfiler.measurementsMap Ljava/util/Map;
      // 13c: invokespecial io/sentry/android/core/AndroidProfiler$ProfileEndData.<init> (JJZLjava/io/File;Ljava/util/Map;)V
      // 13f: astore 2
      // 140: aload 0
      // 141: monitorexit
      // 142: aload 2
      // 143: areturn
      // 144: astore 2
      // 145: aload 0
      // 146: bipush 0
      // 147: putfield io/sentry/android/core/AndroidProfiler.isRunning Z
      // 14a: aload 2
      // 14b: athrow
      // 14c: astore 2
      // 14d: aload 0
      // 14e: monitorexit
      // 14f: aload 2
      // 150: athrow
   }

   public AndroidProfiler.ProfileStartData start() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: aload 0
      // 001: monitorenter
      // 002: aload 0
      // 003: getfield io/sentry/android/core/AndroidProfiler.intervalUs I
      // 006: ifne 02a
      // 009: aload 0
      // 00a: getfield io/sentry/android/core/AndroidProfiler.logger Lio/sentry/ILogger;
      // 00d: getstatic io/sentry/SentryLevel.WARNING Lio/sentry/SentryLevel;
      // 010: ldc_w "Disabling profiling because intervaUs is set to %d"
      // 013: bipush 1
      // 014: anewarray 4
      // 017: dup
      // 018: bipush 0
      // 019: aload 0
      // 01a: getfield io/sentry/android/core/AndroidProfiler.intervalUs I
      // 01d: invokestatic java/lang/Integer.valueOf (I)Ljava/lang/Integer;
      // 020: aastore
      // 021: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 026: aload 0
      // 027: monitorexit
      // 028: aconst_null
      // 029: areturn
      // 02a: aload 0
      // 02b: getfield io/sentry/android/core/AndroidProfiler.isRunning Z
      // 02e: ifeq 048
      // 031: aload 0
      // 032: getfield io/sentry/android/core/AndroidProfiler.logger Lio/sentry/ILogger;
      // 035: getstatic io/sentry/SentryLevel.WARNING Lio/sentry/SentryLevel;
      // 038: ldc_w "Profiling has already started..."
      // 03b: bipush 0
      // 03c: anewarray 4
      // 03f: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 044: aload 0
      // 045: monitorexit
      // 046: aconst_null
      // 047: areturn
      // 048: aload 0
      // 049: getfield io/sentry/android/core/AndroidProfiler.buildInfoProvider Lio/sentry/android/core/BuildInfoProvider;
      // 04c: invokevirtual io/sentry/android/core/BuildInfoProvider.getSdkInfoVersion ()I
      // 04f: istore 1
      // 050: iload 1
      // 051: bipush 21
      // 053: if_icmpge 05a
      // 056: aload 0
      // 057: monitorexit
      // 058: aconst_null
      // 059: areturn
      // 05a: new java/io/File
      // 05d: astore 4
      // 05f: aload 0
      // 060: getfield io/sentry/android/core/AndroidProfiler.traceFilesDir Ljava/io/File;
      // 063: astore 6
      // 065: new java/lang/StringBuilder
      // 068: astore 5
      // 06a: aload 5
      // 06c: invokespecial java/lang/StringBuilder.<init> ()V
      // 06f: aload 5
      // 071: invokestatic java/util/UUID.randomUUID ()Ljava/util/UUID;
      // 074: invokevirtual java/lang/StringBuilder.append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      // 077: pop
      // 078: aload 5
      // 07a: ldc_w ".trace"
      // 07d: invokevirtual java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
      // 080: pop
      // 081: aload 4
      // 083: aload 6
      // 085: aload 5
      // 087: invokevirtual java/lang/StringBuilder.toString ()Ljava/lang/String;
      // 08a: invokespecial java/io/File.<init> (Ljava/io/File;Ljava/lang/String;)V
      // 08d: aload 0
      // 08e: aload 4
      // 090: putfield io/sentry/android/core/AndroidProfiler.traceFile Ljava/io/File;
      // 093: aload 0
      // 094: getfield io/sentry/android/core/AndroidProfiler.measurementsMap Ljava/util/Map;
      // 097: invokeinterface java/util/Map.clear ()V 1
      // 09c: aload 0
      // 09d: getfield io/sentry/android/core/AndroidProfiler.screenFrameRateMeasurements Ljava/util/ArrayDeque;
      // 0a0: invokevirtual java/util/ArrayDeque.clear ()V
      // 0a3: aload 0
      // 0a4: getfield io/sentry/android/core/AndroidProfiler.slowFrameRenderMeasurements Ljava/util/ArrayDeque;
      // 0a7: invokevirtual java/util/ArrayDeque.clear ()V
      // 0aa: aload 0
      // 0ab: getfield io/sentry/android/core/AndroidProfiler.frozenFrameRenderMeasurements Ljava/util/ArrayDeque;
      // 0ae: invokevirtual java/util/ArrayDeque.clear ()V
      // 0b1: aload 0
      // 0b2: getfield io/sentry/android/core/AndroidProfiler.frameMetricsCollector Lio/sentry/android/core/internal/util/SentryFrameMetricsCollector;
      // 0b5: astore 4
      // 0b7: new io/sentry/android/core/AndroidProfiler$1
      // 0ba: astore 5
      // 0bc: aload 5
      // 0be: aload 0
      // 0bf: invokespecial io/sentry/android/core/AndroidProfiler$1.<init> (Lio/sentry/android/core/AndroidProfiler;)V
      // 0c2: aload 0
      // 0c3: aload 4
      // 0c5: aload 5
      // 0c7: invokevirtual io/sentry/android/core/internal/util/SentryFrameMetricsCollector.startCollection (Lio/sentry/android/core/internal/util/SentryFrameMetricsCollector$FrameMetricsCollectorListener;)Ljava/lang/String;
      // 0ca: putfield io/sentry/android/core/AndroidProfiler.frameMetricsCollectorId Ljava/lang/String;
      // 0cd: aload 0
      // 0ce: getfield io/sentry/android/core/AndroidProfiler.executorService Lio/sentry/ISentryExecutorService;
      // 0d1: astore 5
      // 0d3: new io/sentry/android/core/AndroidProfiler$$ExternalSyntheticLambda0
      // 0d6: astore 4
      // 0d8: aload 4
      // 0da: aload 0
      // 0db: invokespecial io/sentry/android/core/AndroidProfiler$$ExternalSyntheticLambda0.<init> (Lio/sentry/android/core/AndroidProfiler;)V
      // 0de: aload 0
      // 0df: aload 5
      // 0e1: aload 4
      // 0e3: ldc2_w 30000
      // 0e6: invokeinterface io/sentry/ISentryExecutorService.schedule (Ljava/lang/Runnable;J)Ljava/util/concurrent/Future; 4
      // 0eb: putfield io/sentry/android/core/AndroidProfiler.scheduledFinish Ljava/util/concurrent/Future;
      // 0ee: goto 104
      // 0f1: astore 4
      // 0f3: aload 0
      // 0f4: getfield io/sentry/android/core/AndroidProfiler.logger Lio/sentry/ILogger;
      // 0f7: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 0fa: ldc_w "Failed to call the executor. Profiling will not be automatically finished. Did you call Sentry.close()?"
      // 0fd: aload 4
      // 0ff: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 104: aload 0
      // 105: invokestatic android/os/SystemClock.elapsedRealtimeNanos ()J
      // 108: putfield io/sentry/android/core/AndroidProfiler.profileStartNanos J
      // 10b: invokestatic io/sentry/DateUtils.getCurrentDateTime ()Ljava/util/Date;
      // 10e: astore 4
      // 110: invokestatic android/os/Process.getElapsedCpuTime ()J
      // 113: lstore 2
      // 114: aload 0
      // 115: getfield io/sentry/android/core/AndroidProfiler.traceFile Ljava/io/File;
      // 118: invokevirtual java/io/File.getPath ()Ljava/lang/String;
      // 11b: ldc 3000000
      // 11d: aload 0
      // 11e: getfield io/sentry/android/core/AndroidProfiler.intervalUs I
      // 121: invokestatic android/os/Debug.startMethodTracingSampling (Ljava/lang/String;II)V
      // 124: aload 0
      // 125: bipush 1
      // 126: putfield io/sentry/android/core/AndroidProfiler.isRunning Z
      // 129: new io/sentry/android/core/AndroidProfiler$ProfileStartData
      // 12c: dup
      // 12d: aload 0
      // 12e: getfield io/sentry/android/core/AndroidProfiler.profileStartNanos J
      // 131: lload 2
      // 132: aload 4
      // 134: invokespecial io/sentry/android/core/AndroidProfiler$ProfileStartData.<init> (JJLjava/util/Date;)V
      // 137: astore 4
      // 139: aload 0
      // 13a: monitorexit
      // 13b: aload 4
      // 13d: areturn
      // 13e: astore 4
      // 140: aload 0
      // 141: bipush 0
      // 142: aconst_null
      // 143: invokevirtual io/sentry/android/core/AndroidProfiler.endAndCollect (ZLjava/util/List;)Lio/sentry/android/core/AndroidProfiler$ProfileEndData;
      // 146: pop
      // 147: aload 0
      // 148: getfield io/sentry/android/core/AndroidProfiler.logger Lio/sentry/ILogger;
      // 14b: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 14e: ldc_w "Unable to start a profile: "
      // 151: aload 4
      // 153: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 158: aload 0
      // 159: bipush 0
      // 15a: putfield io/sentry/android/core/AndroidProfiler.isRunning Z
      // 15d: aload 0
      // 15e: monitorexit
      // 15f: aconst_null
      // 160: areturn
      // 161: astore 4
      // 163: aload 0
      // 164: monitorexit
      // 165: aload 4
      // 167: athrow
   }

   public static class ProfileEndData {
      public final boolean didTimeout;
      public final long endCpuMillis;
      public final long endNanos;
      public final Map<String, ProfileMeasurement> measurementsMap;
      public final File traceFile;

      public ProfileEndData(long var1, long var3, boolean var5, File var6, Map<String, ProfileMeasurement> var7) {
         this.endNanos = var1;
         this.traceFile = var6;
         this.endCpuMillis = var3;
         this.measurementsMap = var7;
         this.didTimeout = var5;
      }
   }

   public static class ProfileStartData {
      public final long startCpuMillis;
      public final long startNanos;
      public final Date startTimestamp;

      public ProfileStartData(long var1, long var3, Date var5) {
         this.startNanos = var1;
         this.startCpuMillis = var3;
         this.startTimestamp = var5;
      }
   }
}
