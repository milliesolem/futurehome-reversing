package io.sentry;

import io.sentry.util.Objects;
import j..util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicBoolean;

public final class DefaultTransactionPerformanceCollector implements TransactionPerformanceCollector {
   private static final long TRANSACTION_COLLECTION_INTERVAL_MILLIS = 100L;
   private static final long TRANSACTION_COLLECTION_TIMEOUT_MILLIS = 30000L;
   private final List<IPerformanceContinuousCollector> continuousCollectors;
   private final boolean hasNoCollectors;
   private final AtomicBoolean isStarted;
   private long lastCollectionTimestamp;
   private final SentryOptions options;
   private final Map<String, List<PerformanceCollectionData>> performanceDataMap;
   private final List<IPerformanceSnapshotCollector> snapshotCollectors;
   private volatile Timer timer;
   private final Object timerLock = new Object();

   public DefaultTransactionPerformanceCollector(SentryOptions var1) {
      this.timer = null;
      this.performanceDataMap = new ConcurrentHashMap();
      boolean var3 = false;
      this.isStarted = new AtomicBoolean(false);
      this.lastCollectionTimestamp = 0L;
      this.options = Objects.requireNonNull(var1, "The options object is required.");
      this.snapshotCollectors = new ArrayList<>();
      this.continuousCollectors = new ArrayList<>();

      for (IPerformanceCollector var4 : var1.getPerformanceCollectors()) {
         if (var4 instanceof IPerformanceSnapshotCollector) {
            this.snapshotCollectors.add((IPerformanceSnapshotCollector)var4);
         }

         if (var4 instanceof IPerformanceContinuousCollector) {
            this.continuousCollectors.add((IPerformanceContinuousCollector)var4);
         }
      }

      boolean var2 = var3;
      if (this.snapshotCollectors.isEmpty()) {
         var2 = var3;
         if (this.continuousCollectors.isEmpty()) {
            var2 = true;
         }
      }

      this.hasNoCollectors = var2;
   }

   @Override
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
      // 01: getfield io/sentry/DefaultTransactionPerformanceCollector.options Lio/sentry/SentryOptions;
      // 04: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 07: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 0a: ldc "stop collecting all performance info for transactions"
      // 0c: bipush 0
      // 0d: anewarray 4
      // 10: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 15: aload 0
      // 16: getfield io/sentry/DefaultTransactionPerformanceCollector.performanceDataMap Ljava/util/Map;
      // 19: invokeinterface java/util/Map.clear ()V 1
      // 1e: aload 0
      // 1f: getfield io/sentry/DefaultTransactionPerformanceCollector.continuousCollectors Ljava/util/List;
      // 22: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 27: astore 1
      // 28: aload 1
      // 29: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 2e: ifeq 42
      // 31: aload 1
      // 32: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 37: checkcast io/sentry/IPerformanceContinuousCollector
      // 3a: invokeinterface io/sentry/IPerformanceContinuousCollector.clear ()V 1
      // 3f: goto 28
      // 42: aload 0
      // 43: getfield io/sentry/DefaultTransactionPerformanceCollector.isStarted Ljava/util/concurrent/atomic/AtomicBoolean;
      // 46: bipush 0
      // 47: invokevirtual java/util/concurrent/atomic/AtomicBoolean.getAndSet (Z)Z
      // 4a: ifeq 71
      // 4d: aload 0
      // 4e: getfield io/sentry/DefaultTransactionPerformanceCollector.timerLock Ljava/lang/Object;
      // 51: astore 2
      // 52: aload 2
      // 53: monitorenter
      // 54: aload 0
      // 55: getfield io/sentry/DefaultTransactionPerformanceCollector.timer Ljava/util/Timer;
      // 58: ifnull 67
      // 5b: aload 0
      // 5c: getfield io/sentry/DefaultTransactionPerformanceCollector.timer Ljava/util/Timer;
      // 5f: invokevirtual java/util/Timer.cancel ()V
      // 62: aload 0
      // 63: aconst_null
      // 64: putfield io/sentry/DefaultTransactionPerformanceCollector.timer Ljava/util/Timer;
      // 67: aload 2
      // 68: monitorexit
      // 69: goto 71
      // 6c: astore 1
      // 6d: aload 2
      // 6e: monitorexit
      // 6f: aload 1
      // 70: athrow
      // 71: return
   }

   @Override
   public void onSpanFinished(ISpan var1) {
      Iterator var2 = this.continuousCollectors.iterator();

      while (var2.hasNext()) {
         ((IPerformanceContinuousCollector)var2.next()).onSpanFinished(var1);
      }
   }

   @Override
   public void onSpanStarted(ISpan var1) {
      Iterator var2 = this.continuousCollectors.iterator();

      while (var2.hasNext()) {
         ((IPerformanceContinuousCollector)var2.next()).onSpanStarted(var1);
      }
   }

   @Override
   public void start(ITransaction param1) {
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
      // 001: getfield io/sentry/DefaultTransactionPerformanceCollector.hasNoCollectors Z
      // 004: ifeq 01d
      // 007: aload 0
      // 008: getfield io/sentry/DefaultTransactionPerformanceCollector.options Lio/sentry/SentryOptions;
      // 00b: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 00e: getstatic io/sentry/SentryLevel.INFO Lio/sentry/SentryLevel;
      // 011: ldc "No collector found. Performance stats will not be captured during transactions."
      // 013: bipush 0
      // 014: anewarray 4
      // 017: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 01c: return
      // 01d: aload 0
      // 01e: getfield io/sentry/DefaultTransactionPerformanceCollector.continuousCollectors Ljava/util/List;
      // 021: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 026: astore 2
      // 027: aload 2
      // 028: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 02d: ifeq 042
      // 030: aload 2
      // 031: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 036: checkcast io/sentry/IPerformanceContinuousCollector
      // 039: aload 1
      // 03a: invokeinterface io/sentry/IPerformanceContinuousCollector.onSpanStarted (Lio/sentry/ISpan;)V 2
      // 03f: goto 027
      // 042: aload 0
      // 043: getfield io/sentry/DefaultTransactionPerformanceCollector.performanceDataMap Ljava/util/Map;
      // 046: aload 1
      // 047: invokeinterface io/sentry/ITransaction.getEventId ()Lio/sentry/protocol/SentryId; 1
      // 04c: invokevirtual io/sentry/protocol/SentryId.toString ()Ljava/lang/String;
      // 04f: invokeinterface java/util/Map.containsKey (Ljava/lang/Object;)Z 2
      // 054: ifne 0a4
      // 057: aload 0
      // 058: getfield io/sentry/DefaultTransactionPerformanceCollector.performanceDataMap Ljava/util/Map;
      // 05b: aload 1
      // 05c: invokeinterface io/sentry/ITransaction.getEventId ()Lio/sentry/protocol/SentryId; 1
      // 061: invokevirtual io/sentry/protocol/SentryId.toString ()Ljava/lang/String;
      // 064: new java/util/ArrayList
      // 067: dup
      // 068: invokespecial java/util/ArrayList.<init> ()V
      // 06b: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 070: pop
      // 071: aload 0
      // 072: getfield io/sentry/DefaultTransactionPerformanceCollector.options Lio/sentry/SentryOptions;
      // 075: invokevirtual io/sentry/SentryOptions.getExecutorService ()Lio/sentry/ISentryExecutorService;
      // 078: astore 3
      // 079: new io/sentry/DefaultTransactionPerformanceCollector$$ExternalSyntheticLambda0
      // 07c: astore 2
      // 07d: aload 2
      // 07e: aload 0
      // 07f: aload 1
      // 080: invokespecial io/sentry/DefaultTransactionPerformanceCollector$$ExternalSyntheticLambda0.<init> (Lio/sentry/DefaultTransactionPerformanceCollector;Lio/sentry/ITransaction;)V
      // 083: aload 3
      // 084: aload 2
      // 085: ldc2_w 30000
      // 088: invokeinterface io/sentry/ISentryExecutorService.schedule (Ljava/lang/Runnable;J)Ljava/util/concurrent/Future; 4
      // 08d: pop
      // 08e: goto 0a4
      // 091: astore 1
      // 092: aload 0
      // 093: getfield io/sentry/DefaultTransactionPerformanceCollector.options Lio/sentry/SentryOptions;
      // 096: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 099: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 09c: ldc "Failed to call the executor. Performance collector will not be automatically finished. Did you call Sentry.close()?"
      // 09e: aload 1
      // 09f: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 0a4: aload 0
      // 0a5: getfield io/sentry/DefaultTransactionPerformanceCollector.isStarted Ljava/util/concurrent/atomic/AtomicBoolean;
      // 0a8: bipush 1
      // 0a9: invokevirtual java/util/concurrent/atomic/AtomicBoolean.getAndSet (Z)Z
      // 0ac: ifne 100
      // 0af: aload 0
      // 0b0: getfield io/sentry/DefaultTransactionPerformanceCollector.timerLock Ljava/lang/Object;
      // 0b3: astore 1
      // 0b4: aload 1
      // 0b5: monitorenter
      // 0b6: aload 0
      // 0b7: getfield io/sentry/DefaultTransactionPerformanceCollector.timer Ljava/util/Timer;
      // 0ba: ifnonnull 0cb
      // 0bd: new java/util/Timer
      // 0c0: astore 2
      // 0c1: aload 2
      // 0c2: bipush 1
      // 0c3: invokespecial java/util/Timer.<init> (Z)V
      // 0c6: aload 0
      // 0c7: aload 2
      // 0c8: putfield io/sentry/DefaultTransactionPerformanceCollector.timer Ljava/util/Timer;
      // 0cb: aload 0
      // 0cc: getfield io/sentry/DefaultTransactionPerformanceCollector.timer Ljava/util/Timer;
      // 0cf: astore 3
      // 0d0: new io/sentry/DefaultTransactionPerformanceCollector$1
      // 0d3: astore 2
      // 0d4: aload 2
      // 0d5: aload 0
      // 0d6: invokespecial io/sentry/DefaultTransactionPerformanceCollector$1.<init> (Lio/sentry/DefaultTransactionPerformanceCollector;)V
      // 0d9: aload 3
      // 0da: aload 2
      // 0db: lconst_0
      // 0dc: invokevirtual java/util/Timer.schedule (Ljava/util/TimerTask;J)V
      // 0df: new io/sentry/DefaultTransactionPerformanceCollector$2
      // 0e2: astore 2
      // 0e3: aload 2
      // 0e4: aload 0
      // 0e5: invokespecial io/sentry/DefaultTransactionPerformanceCollector$2.<init> (Lio/sentry/DefaultTransactionPerformanceCollector;)V
      // 0e8: aload 0
      // 0e9: getfield io/sentry/DefaultTransactionPerformanceCollector.timer Ljava/util/Timer;
      // 0ec: aload 2
      // 0ed: ldc2_w 100
      // 0f0: ldc2_w 100
      // 0f3: invokevirtual java/util/Timer.scheduleAtFixedRate (Ljava/util/TimerTask;JJ)V
      // 0f6: aload 1
      // 0f7: monitorexit
      // 0f8: goto 100
      // 0fb: astore 2
      // 0fc: aload 1
      // 0fd: monitorexit
      // 0fe: aload 2
      // 0ff: athrow
      // 100: return
   }

   @Override
   public List<PerformanceCollectionData> stop(ITransaction var1) {
      this.options
         .getLogger()
         .log(SentryLevel.DEBUG, "stop collecting performance info for transactions %s (%s)", var1.getName(), var1.getSpanContext().getTraceId().toString());
      List var3 = this.performanceDataMap.remove(var1.getEventId().toString());
      Iterator var2 = this.continuousCollectors.iterator();

      while (var2.hasNext()) {
         ((IPerformanceContinuousCollector)var2.next()).onSpanFinished(var1);
      }

      if (this.performanceDataMap.isEmpty()) {
         this.close();
      }

      return var3;
   }
}
