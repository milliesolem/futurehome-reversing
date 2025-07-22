package io.sentry.android.core;

import android.content.Context;
import io.sentry.DateUtils;
import io.sentry.HubAdapter;
import io.sentry.IHub;
import io.sentry.ILogger;
import io.sentry.ISentryExecutorService;
import io.sentry.ITransaction;
import io.sentry.ITransactionProfiler;
import io.sentry.PerformanceCollectionData;
import io.sentry.ProfilingTraceData;
import io.sentry.ProfilingTransactionData;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.android.core.internal.util.SentryFrameMetricsCollector;
import io.sentry.util.Objects;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

final class AndroidTransactionProfiler implements ITransactionProfiler {
   private final BuildInfoProvider buildInfoProvider;
   private final Context context;
   private ProfilingTransactionData currentProfilingTransactionData;
   private final ISentryExecutorService executorService;
   private final SentryFrameMetricsCollector frameMetricsCollector;
   private boolean isInitialized = false;
   private final boolean isProfilingEnabled;
   private final ILogger logger;
   private long profileStartCpuMillis;
   private long profileStartNanos;
   private Date profileStartTimestamp;
   private AndroidProfiler profiler;
   private final String profilingTracesDirPath;
   private final int profilingTracesHz;
   private int transactionsCounter = 0;

   public AndroidTransactionProfiler(
      Context var1, BuildInfoProvider var2, SentryFrameMetricsCollector var3, ILogger var4, String var5, boolean var6, int var7, ISentryExecutorService var8
   ) {
      this.profiler = null;
      this.context = Objects.requireNonNull(ContextUtils.getApplicationContext(var1), "The application context is required");
      this.logger = Objects.requireNonNull(var4, "ILogger is required");
      this.frameMetricsCollector = Objects.requireNonNull(var3, "SentryFrameMetricsCollector is required");
      this.buildInfoProvider = Objects.requireNonNull(var2, "The BuildInfoProvider is required.");
      this.profilingTracesDirPath = var5;
      this.isProfilingEnabled = var6;
      this.profilingTracesHz = var7;
      this.executorService = Objects.requireNonNull(var8, "The ISentryExecutorService is required.");
      this.profileStartTimestamp = DateUtils.getCurrentDateTime();
   }

   public AndroidTransactionProfiler(Context var1, SentryAndroidOptions var2, BuildInfoProvider var3, SentryFrameMetricsCollector var4) {
      this(
         var1,
         var3,
         var4,
         var2.getLogger(),
         var2.getProfilingTracesDirPath(),
         var2.isProfilingEnabled(),
         var2.getProfilingTracesHz(),
         var2.getExecutorService()
      );
   }

   @Deprecated
   public AndroidTransactionProfiler(Context var1, SentryAndroidOptions var2, BuildInfoProvider var3, SentryFrameMetricsCollector var4, IHub var5) {
      this(var1, var2, var3, var4);
   }

   private void init() {
      if (!this.isInitialized) {
         this.isInitialized = true;
         if (!this.isProfilingEnabled) {
            this.logger.log(SentryLevel.INFO, "Profiling is disabled in options.");
         } else if (this.profilingTracesDirPath == null) {
            this.logger.log(SentryLevel.WARNING, "Disabling profiling because no profiling traces dir path is defined in options.");
         } else if (this.profilingTracesHz <= 0) {
            this.logger.log(SentryLevel.WARNING, "Disabling profiling because trace rate is set to %d", this.profilingTracesHz);
         } else {
            this.profiler = new AndroidProfiler(
               this.profilingTracesDirPath,
               (int)TimeUnit.SECONDS.toMicros(1L) / this.profilingTracesHz,
               this.frameMetricsCollector,
               this.executorService,
               this.logger,
               this.buildInfoProvider
            );
         }
      }
   }

   private boolean onFirstStart() {
      AndroidProfiler var1 = this.profiler;
      if (var1 == null) {
         return false;
      } else {
         AndroidProfiler.ProfileStartData var2 = var1.start();
         if (var2 == null) {
            return false;
         } else {
            this.profileStartNanos = var2.startNanos;
            this.profileStartCpuMillis = var2.startCpuMillis;
            this.profileStartTimestamp = var2.startTimestamp;
            return true;
         }
      }
   }

   private ProfilingTraceData onTransactionFinish(
      String param1, String param2, String param3, boolean param4, List<PerformanceCollectionData> param5, SentryOptions param6
   ) {
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
      // 003: getfield io/sentry/android/core/AndroidTransactionProfiler.profiler Lio/sentry/android/core/AndroidProfiler;
      // 006: astore 13
      // 008: aconst_null
      // 009: astore 12
      // 00b: aload 13
      // 00d: ifnonnull 014
      // 010: aload 0
      // 011: monitorexit
      // 012: aconst_null
      // 013: areturn
      // 014: aload 0
      // 015: getfield io/sentry/android/core/AndroidTransactionProfiler.buildInfoProvider Lio/sentry/android/core/BuildInfoProvider;
      // 018: invokevirtual io/sentry/android/core/BuildInfoProvider.getSdkInfoVersion ()I
      // 01b: istore 7
      // 01d: iload 7
      // 01f: bipush 22
      // 021: if_icmpge 028
      // 024: aload 0
      // 025: monitorexit
      // 026: aconst_null
      // 027: areturn
      // 028: aload 0
      // 029: getfield io/sentry/android/core/AndroidTransactionProfiler.currentProfilingTransactionData Lio/sentry/ProfilingTransactionData;
      // 02c: astore 13
      // 02e: aload 13
      // 030: ifnull 234
      // 033: aload 13
      // 035: invokevirtual io/sentry/ProfilingTransactionData.getId ()Ljava/lang/String;
      // 038: aload 2
      // 039: invokevirtual java/lang/String.equals (Ljava/lang/Object;)Z
      // 03c: ifne 042
      // 03f: goto 234
      // 042: aload 0
      // 043: getfield io/sentry/android/core/AndroidTransactionProfiler.transactionsCounter I
      // 046: istore 7
      // 048: iload 7
      // 04a: ifle 055
      // 04d: aload 0
      // 04e: iload 7
      // 050: bipush 1
      // 051: isub
      // 052: putfield io/sentry/android/core/AndroidTransactionProfiler.transactionsCounter I
      // 055: aload 0
      // 056: getfield io/sentry/android/core/AndroidTransactionProfiler.logger Lio/sentry/ILogger;
      // 059: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 05c: ldc "Transaction %s (%s) finished."
      // 05e: bipush 2
      // 05f: anewarray 4
      // 062: dup
      // 063: bipush 0
      // 064: aload 1
      // 065: aastore
      // 066: dup
      // 067: bipush 1
      // 068: aload 3
      // 069: aastore
      // 06a: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 06f: aload 0
      // 070: getfield io/sentry/android/core/AndroidTransactionProfiler.transactionsCounter I
      // 073: ifeq 0a1
      // 076: aload 0
      // 077: getfield io/sentry/android/core/AndroidTransactionProfiler.currentProfilingTransactionData Lio/sentry/ProfilingTransactionData;
      // 07a: astore 1
      // 07b: aload 1
      // 07c: ifnull 09d
      // 07f: aload 1
      // 080: invokestatic android/os/SystemClock.elapsedRealtimeNanos ()J
      // 083: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 086: aload 0
      // 087: getfield io/sentry/android/core/AndroidTransactionProfiler.profileStartNanos J
      // 08a: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 08d: invokestatic android/os/Process.getElapsedCpuTime ()J
      // 090: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 093: aload 0
      // 094: getfield io/sentry/android/core/AndroidTransactionProfiler.profileStartCpuMillis J
      // 097: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 09a: invokevirtual io/sentry/ProfilingTransactionData.notifyFinish (Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/Long;)V
      // 09d: aload 0
      // 09e: monitorexit
      // 09f: aconst_null
      // 0a0: areturn
      // 0a1: aload 0
      // 0a2: getfield io/sentry/android/core/AndroidTransactionProfiler.profiler Lio/sentry/android/core/AndroidProfiler;
      // 0a5: bipush 0
      // 0a6: aload 5
      // 0a8: invokevirtual io/sentry/android/core/AndroidProfiler.endAndCollect (ZLjava/util/List;)Lio/sentry/android/core/AndroidProfiler$ProfileEndData;
      // 0ab: astore 13
      // 0ad: aload 13
      // 0af: ifnonnull 0b6
      // 0b2: aload 0
      // 0b3: monitorexit
      // 0b4: aconst_null
      // 0b5: areturn
      // 0b6: aload 13
      // 0b8: getfield io/sentry/android/core/AndroidProfiler$ProfileEndData.endNanos J
      // 0bb: lstore 10
      // 0bd: aload 0
      // 0be: getfield io/sentry/android/core/AndroidTransactionProfiler.profileStartNanos J
      // 0c1: lstore 8
      // 0c3: new java/util/ArrayList
      // 0c6: astore 14
      // 0c8: aload 14
      // 0ca: bipush 1
      // 0cb: invokespecial java/util/ArrayList.<init> (I)V
      // 0ce: aload 0
      // 0cf: getfield io/sentry/android/core/AndroidTransactionProfiler.currentProfilingTransactionData Lio/sentry/ProfilingTransactionData;
      // 0d2: astore 5
      // 0d4: aload 5
      // 0d6: ifnull 0e3
      // 0d9: aload 14
      // 0db: aload 5
      // 0dd: invokeinterface java/util/List.add (Ljava/lang/Object;)Z 2
      // 0e2: pop
      // 0e3: aload 0
      // 0e4: aconst_null
      // 0e5: putfield io/sentry/android/core/AndroidTransactionProfiler.currentProfilingTransactionData Lio/sentry/ProfilingTransactionData;
      // 0e8: aload 0
      // 0e9: bipush 0
      // 0ea: putfield io/sentry/android/core/AndroidTransactionProfiler.transactionsCounter I
      // 0ed: aload 12
      // 0ef: astore 5
      // 0f1: aload 6
      // 0f3: instanceof io/sentry/android/core/SentryAndroidOptions
      // 0f6: ifeq 10a
      // 0f9: aload 0
      // 0fa: getfield io/sentry/android/core/AndroidTransactionProfiler.context Landroid/content/Context;
      // 0fd: aload 6
      // 0ff: checkcast io/sentry/android/core/SentryAndroidOptions
      // 102: invokestatic io/sentry/android/core/DeviceInfoUtil.getInstance (Landroid/content/Context;Lio/sentry/android/core/SentryAndroidOptions;)Lio/sentry/android/core/DeviceInfoUtil;
      // 105: invokevirtual io/sentry/android/core/DeviceInfoUtil.getTotalMemory ()Ljava/lang/Long;
      // 108: astore 5
      // 10a: aload 5
      // 10c: ifnull 11c
      // 10f: aload 5
      // 111: invokevirtual java/lang/Long.longValue ()J
      // 114: invokestatic java/lang/Long.toString (J)Ljava/lang/String;
      // 117: astore 5
      // 119: goto 121
      // 11c: ldc_w "0"
      // 11f: astore 5
      // 121: getstatic android/os/Build.SUPPORTED_ABIS [Ljava/lang/String;
      // 124: astore 12
      // 126: aload 14
      // 128: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 12d: astore 15
      // 12f: aload 15
      // 131: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 136: ifeq 167
      // 139: aload 15
      // 13b: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 140: checkcast io/sentry/ProfilingTransactionData
      // 143: aload 13
      // 145: getfield io/sentry/android/core/AndroidProfiler$ProfileEndData.endNanos J
      // 148: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 14b: aload 0
      // 14c: getfield io/sentry/android/core/AndroidTransactionProfiler.profileStartNanos J
      // 14f: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 152: aload 13
      // 154: getfield io/sentry/android/core/AndroidProfiler$ProfileEndData.endCpuMillis J
      // 157: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 15a: aload 0
      // 15b: getfield io/sentry/android/core/AndroidTransactionProfiler.profileStartCpuMillis J
      // 15e: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 161: invokevirtual io/sentry/ProfilingTransactionData.notifyFinish (Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/Long;)V
      // 164: goto 12f
      // 167: aload 13
      // 169: getfield io/sentry/android/core/AndroidProfiler$ProfileEndData.traceFile Ljava/io/File;
      // 16c: astore 15
      // 16e: aload 0
      // 16f: getfield io/sentry/android/core/AndroidTransactionProfiler.profileStartTimestamp Ljava/util/Date;
      // 172: astore 16
      // 174: lload 10
      // 176: lload 8
      // 178: lsub
      // 179: invokestatic java/lang/Long.toString (J)Ljava/lang/String;
      // 17c: astore 17
      // 17e: aload 0
      // 17f: getfield io/sentry/android/core/AndroidTransactionProfiler.buildInfoProvider Lio/sentry/android/core/BuildInfoProvider;
      // 182: invokevirtual io/sentry/android/core/BuildInfoProvider.getSdkInfoVersion ()I
      // 185: istore 7
      // 187: aload 12
      // 189: ifnull 19b
      // 18c: aload 12
      // 18e: arraylength
      // 18f: ifle 19b
      // 192: aload 12
      // 194: bipush 0
      // 195: aaload
      // 196: astore 12
      // 198: goto 1a0
      // 19b: ldc_w ""
      // 19e: astore 12
      // 1a0: new io/sentry/android/core/AndroidTransactionProfiler$$ExternalSyntheticLambda0
      // 1a3: astore 24
      // 1a5: aload 24
      // 1a7: invokespecial io/sentry/android/core/AndroidTransactionProfiler$$ExternalSyntheticLambda0.<init> ()V
      // 1aa: aload 0
      // 1ab: getfield io/sentry/android/core/AndroidTransactionProfiler.buildInfoProvider Lio/sentry/android/core/BuildInfoProvider;
      // 1ae: invokevirtual io/sentry/android/core/BuildInfoProvider.getManufacturer ()Ljava/lang/String;
      // 1b1: astore 19
      // 1b3: aload 0
      // 1b4: getfield io/sentry/android/core/AndroidTransactionProfiler.buildInfoProvider Lio/sentry/android/core/BuildInfoProvider;
      // 1b7: invokevirtual io/sentry/android/core/BuildInfoProvider.getModel ()Ljava/lang/String;
      // 1ba: astore 22
      // 1bc: aload 0
      // 1bd: getfield io/sentry/android/core/AndroidTransactionProfiler.buildInfoProvider Lio/sentry/android/core/BuildInfoProvider;
      // 1c0: invokevirtual io/sentry/android/core/BuildInfoProvider.getVersionRelease ()Ljava/lang/String;
      // 1c3: astore 20
      // 1c5: aload 0
      // 1c6: getfield io/sentry/android/core/AndroidTransactionProfiler.buildInfoProvider Lio/sentry/android/core/BuildInfoProvider;
      // 1c9: invokevirtual io/sentry/android/core/BuildInfoProvider.isEmulator ()Ljava/lang/Boolean;
      // 1cc: astore 23
      // 1ce: aload 6
      // 1d0: invokevirtual io/sentry/SentryOptions.getProguardUuid ()Ljava/lang/String;
      // 1d3: astore 21
      // 1d5: aload 6
      // 1d7: invokevirtual io/sentry/SentryOptions.getRelease ()Ljava/lang/String;
      // 1da: astore 18
      // 1dc: aload 6
      // 1de: invokevirtual io/sentry/SentryOptions.getEnvironment ()Ljava/lang/String;
      // 1e1: astore 25
      // 1e3: aload 13
      // 1e5: getfield io/sentry/android/core/AndroidProfiler$ProfileEndData.didTimeout Z
      // 1e8: ifne 1fb
      // 1eb: iload 4
      // 1ed: ifeq 1f3
      // 1f0: goto 1fb
      // 1f3: ldc_w "normal"
      // 1f6: astore 6
      // 1f8: goto 200
      // 1fb: ldc_w "timeout"
      // 1fe: astore 6
      // 200: new io/sentry/ProfilingTraceData
      // 203: dup
      // 204: aload 15
      // 206: aload 16
      // 208: aload 14
      // 20a: aload 1
      // 20b: aload 2
      // 20c: aload 3
      // 20d: aload 17
      // 20f: iload 7
      // 211: aload 12
      // 213: aload 24
      // 215: aload 19
      // 217: aload 22
      // 219: aload 20
      // 21b: aload 23
      // 21d: aload 5
      // 21f: aload 21
      // 221: aload 18
      // 223: aload 25
      // 225: aload 6
      // 227: aload 13
      // 229: getfield io/sentry/android/core/AndroidProfiler$ProfileEndData.measurementsMap Ljava/util/Map;
      // 22c: invokespecial io/sentry/ProfilingTraceData.<init> (Ljava/io/File;Ljava/util/Date;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/util/concurrent/Callable;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;)V
      // 22f: astore 1
      // 230: aload 0
      // 231: monitorexit
      // 232: aload 1
      // 233: areturn
      // 234: aload 0
      // 235: getfield io/sentry/android/core/AndroidTransactionProfiler.logger Lio/sentry/ILogger;
      // 238: getstatic io/sentry/SentryLevel.INFO Lio/sentry/SentryLevel;
      // 23b: ldc_w "Transaction %s (%s) finished, but was not currently being profiled. Skipping"
      // 23e: bipush 2
      // 23f: anewarray 4
      // 242: dup
      // 243: bipush 0
      // 244: aload 1
      // 245: aastore
      // 246: dup
      // 247: bipush 1
      // 248: aload 3
      // 249: aastore
      // 24a: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 24f: aload 0
      // 250: monitorexit
      // 251: aconst_null
      // 252: areturn
      // 253: astore 1
      // 254: aload 0
      // 255: monitorexit
      // 256: aload 1
      // 257: athrow
   }

   @Override
   public void bindTransaction(ITransaction param1) {
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
      // 03: getfield io/sentry/android/core/AndroidTransactionProfiler.transactionsCounter I
      // 06: ifle 2c
      // 09: aload 0
      // 0a: getfield io/sentry/android/core/AndroidTransactionProfiler.currentProfilingTransactionData Lio/sentry/ProfilingTransactionData;
      // 0d: ifnonnull 2c
      // 10: new io/sentry/ProfilingTransactionData
      // 13: astore 2
      // 14: aload 2
      // 15: aload 1
      // 16: aload 0
      // 17: getfield io/sentry/android/core/AndroidTransactionProfiler.profileStartNanos J
      // 1a: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 1d: aload 0
      // 1e: getfield io/sentry/android/core/AndroidTransactionProfiler.profileStartCpuMillis J
      // 21: invokestatic java/lang/Long.valueOf (J)Ljava/lang/Long;
      // 24: invokespecial io/sentry/ProfilingTransactionData.<init> (Lio/sentry/ITransaction;Ljava/lang/Long;Ljava/lang/Long;)V
      // 27: aload 0
      // 28: aload 2
      // 29: putfield io/sentry/android/core/AndroidTransactionProfiler.currentProfilingTransactionData Lio/sentry/ProfilingTransactionData;
      // 2c: aload 0
      // 2d: monitorexit
      // 2e: return
      // 2f: astore 1
      // 30: aload 0
      // 31: monitorexit
      // 32: aload 1
      // 33: athrow
   }

   @Override
   public void close() {
      ProfilingTransactionData var2 = this.currentProfilingTransactionData;
      if (var2 != null) {
         this.onTransactionFinish(
            var2.getName(),
            this.currentProfilingTransactionData.getId(),
            this.currentProfilingTransactionData.getTraceId(),
            true,
            null,
            HubAdapter.getInstance().getOptions()
         );
      } else {
         int var1 = this.transactionsCounter;
         if (var1 != 0) {
            this.transactionsCounter = var1 - 1;
         }
      }

      AndroidProfiler var3 = this.profiler;
      if (var3 != null) {
         var3.close();
      }
   }

   int getTransactionsCounter() {
      return this.transactionsCounter;
   }

   @Override
   public boolean isRunning() {
      boolean var1;
      if (this.transactionsCounter != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public ProfilingTraceData onTransactionFinish(ITransaction param1, List<PerformanceCollectionData> param2, SentryOptions param3) {
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
      // 03: aload 1
      // 04: invokeinterface io/sentry/ITransaction.getName ()Ljava/lang/String; 1
      // 09: aload 1
      // 0a: invokeinterface io/sentry/ITransaction.getEventId ()Lio/sentry/protocol/SentryId; 1
      // 0f: invokevirtual io/sentry/protocol/SentryId.toString ()Ljava/lang/String;
      // 12: aload 1
      // 13: invokeinterface io/sentry/ITransaction.getSpanContext ()Lio/sentry/SpanContext; 1
      // 18: invokevirtual io/sentry/SpanContext.getTraceId ()Lio/sentry/protocol/SentryId;
      // 1b: invokevirtual io/sentry/protocol/SentryId.toString ()Ljava/lang/String;
      // 1e: bipush 0
      // 1f: aload 2
      // 20: aload 3
      // 21: invokespecial io/sentry/android/core/AndroidTransactionProfiler.onTransactionFinish (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLjava/util/List;Lio/sentry/SentryOptions;)Lio/sentry/ProfilingTraceData;
      // 24: astore 1
      // 25: aload 0
      // 26: monitorexit
      // 27: aload 1
      // 28: areturn
      // 29: astore 1
      // 2a: aload 0
      // 2b: monitorexit
      // 2c: aload 1
      // 2d: athrow
   }

   @Override
   public void start() {
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
      // 03: getfield io/sentry/android/core/AndroidTransactionProfiler.buildInfoProvider Lio/sentry/android/core/BuildInfoProvider;
      // 06: invokevirtual io/sentry/android/core/BuildInfoProvider.getSdkInfoVersion ()I
      // 09: istore 1
      // 0a: iload 1
      // 0b: bipush 22
      // 0d: if_icmpge 13
      // 10: aload 0
      // 11: monitorexit
      // 12: return
      // 13: aload 0
      // 14: invokespecial io/sentry/android/core/AndroidTransactionProfiler.init ()V
      // 17: aload 0
      // 18: getfield io/sentry/android/core/AndroidTransactionProfiler.transactionsCounter I
      // 1b: bipush 1
      // 1c: iadd
      // 1d: istore 1
      // 1e: aload 0
      // 1f: iload 1
      // 20: putfield io/sentry/android/core/AndroidTransactionProfiler.transactionsCounter I
      // 23: iload 1
      // 24: bipush 1
      // 25: if_icmpne 45
      // 28: aload 0
      // 29: invokespecial io/sentry/android/core/AndroidTransactionProfiler.onFirstStart ()Z
      // 2c: ifeq 45
      // 2f: aload 0
      // 30: getfield io/sentry/android/core/AndroidTransactionProfiler.logger Lio/sentry/ILogger;
      // 33: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 36: ldc_w "Profiler started."
      // 39: bipush 0
      // 3a: anewarray 4
      // 3d: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 42: goto 62
      // 45: aload 0
      // 46: aload 0
      // 47: getfield io/sentry/android/core/AndroidTransactionProfiler.transactionsCounter I
      // 4a: bipush 1
      // 4b: isub
      // 4c: putfield io/sentry/android/core/AndroidTransactionProfiler.transactionsCounter I
      // 4f: aload 0
      // 50: getfield io/sentry/android/core/AndroidTransactionProfiler.logger Lio/sentry/ILogger;
      // 53: getstatic io/sentry/SentryLevel.WARNING Lio/sentry/SentryLevel;
      // 56: ldc_w "A profile is already running. This profile will be ignored."
      // 59: bipush 0
      // 5a: anewarray 4
      // 5d: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 62: aload 0
      // 63: monitorexit
      // 64: return
      // 65: astore 2
      // 66: aload 0
      // 67: monitorexit
      // 68: aload 2
      // 69: athrow
   }
}
