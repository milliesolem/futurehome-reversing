package io.sentry;

import io.sentry.metrics.LocalMetricsAggregator;
import io.sentry.protocol.Contexts;
import io.sentry.protocol.SentryId;
import io.sentry.protocol.TransactionNameSource;
import io.sentry.util.Objects;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public final class SentryTracer implements ITransaction {
   private final Baggage baggage;
   private final List<Span> children;
   private final Contexts contexts;
   private volatile TimerTask deadlineTimeoutTask;
   private final SentryId eventId = new SentryId();
   private SentryTracer.FinishStatus finishStatus;
   private final IHub hub;
   private volatile TimerTask idleTimeoutTask;
   private final Instrumenter instrumenter;
   private final AtomicBoolean isDeadlineTimerRunning;
   private final AtomicBoolean isIdleFinishTimerRunning;
   private String name;
   private final Span root;
   private volatile Timer timer;
   private final Object timerLock;
   private TransactionNameSource transactionNameSource;
   private final TransactionOptions transactionOptions;
   private final TransactionPerformanceCollector transactionPerformanceCollector;

   public SentryTracer(TransactionContext var1, IHub var2) {
      this(var1, var2, new TransactionOptions(), null);
   }

   public SentryTracer(TransactionContext var1, IHub var2, TransactionOptions var3) {
      this(var1, var2, var3, null);
   }

   SentryTracer(TransactionContext var1, IHub var2, TransactionOptions var3, TransactionPerformanceCollector var4) {
      this.children = new CopyOnWriteArrayList<>();
      this.finishStatus = SentryTracer.FinishStatus.NOT_FINISHED;
      this.timer = null;
      this.timerLock = new Object();
      this.isIdleFinishTimerRunning = new AtomicBoolean(false);
      this.isDeadlineTimerRunning = new AtomicBoolean(false);
      this.contexts = new Contexts();
      Objects.requireNonNull(var1, "context is required");
      Objects.requireNonNull(var2, "hub is required");
      this.root = new Span(var1, this, var2, var3.getStartTimestamp(), var3);
      this.name = var1.getName();
      this.instrumenter = var1.getInstrumenter();
      this.hub = var2;
      this.transactionPerformanceCollector = var4;
      this.transactionNameSource = var1.getTransactionNameSource();
      this.transactionOptions = var3;
      if (var1.getBaggage() != null) {
         this.baggage = var1.getBaggage();
      } else {
         this.baggage = new Baggage(var2.getOptions().getLogger());
      }

      if (var4 != null) {
         var4.start(this);
      }

      if (var3.getIdleTimeout() != null || var3.getDeadlineTimeout() != null) {
         this.timer = new Timer(true);
         this.scheduleDeadlineTimeout();
         this.scheduleFinish();
      }
   }

   private void cancelDeadlineTimer() {
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
      // 01: getfield io/sentry/SentryTracer.timerLock Ljava/lang/Object;
      // 04: astore 1
      // 05: aload 1
      // 06: monitorenter
      // 07: aload 0
      // 08: getfield io/sentry/SentryTracer.deadlineTimeoutTask Ljava/util/TimerTask;
      // 0b: ifnull 23
      // 0e: aload 0
      // 0f: getfield io/sentry/SentryTracer.deadlineTimeoutTask Ljava/util/TimerTask;
      // 12: invokevirtual java/util/TimerTask.cancel ()Z
      // 15: pop
      // 16: aload 0
      // 17: getfield io/sentry/SentryTracer.isDeadlineTimerRunning Ljava/util/concurrent/atomic/AtomicBoolean;
      // 1a: bipush 0
      // 1b: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // 1e: aload 0
      // 1f: aconst_null
      // 20: putfield io/sentry/SentryTracer.deadlineTimeoutTask Ljava/util/TimerTask;
      // 23: aload 1
      // 24: monitorexit
      // 25: return
      // 26: astore 2
      // 27: aload 1
      // 28: monitorexit
      // 29: aload 2
      // 2a: athrow
   }

   private void cancelIdleTimer() {
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
      // 01: getfield io/sentry/SentryTracer.timerLock Ljava/lang/Object;
      // 04: astore 1
      // 05: aload 1
      // 06: monitorenter
      // 07: aload 0
      // 08: getfield io/sentry/SentryTracer.idleTimeoutTask Ljava/util/TimerTask;
      // 0b: ifnull 23
      // 0e: aload 0
      // 0f: getfield io/sentry/SentryTracer.idleTimeoutTask Ljava/util/TimerTask;
      // 12: invokevirtual java/util/TimerTask.cancel ()Z
      // 15: pop
      // 16: aload 0
      // 17: getfield io/sentry/SentryTracer.isIdleFinishTimerRunning Ljava/util/concurrent/atomic/AtomicBoolean;
      // 1a: bipush 0
      // 1b: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // 1e: aload 0
      // 1f: aconst_null
      // 20: putfield io/sentry/SentryTracer.idleTimeoutTask Ljava/util/TimerTask;
      // 23: aload 1
      // 24: monitorexit
      // 25: return
      // 26: astore 2
      // 27: aload 1
      // 28: monitorexit
      // 29: aload 2
      // 2a: athrow
   }

   private ISpan createChild(SpanId var1, String var2, String var3, SentryDate var4, Instrumenter var5, SpanOptions var6) {
      if (this.root.isFinished()) {
         return NoOpSpan.getInstance();
      } else if (!this.instrumenter.equals(var5)) {
         return NoOpSpan.getInstance();
      } else if (this.children.size() < this.hub.getOptions().getMaxSpans()) {
         Objects.requireNonNull(var1, "parentSpanId is required");
         Objects.requireNonNull(var2, "operation is required");
         this.cancelIdleTimer();
         Span var9 = new Span(this.root.getTraceId(), var1, this, var2, this.hub, var4, var6, new SentryTracer$$ExternalSyntheticLambda3(this));
         var9.setDescription(var3);
         var9.setData("thread.id", String.valueOf(Thread.currentThread().getId()));
         String var7;
         if (this.hub.getOptions().getMainThreadChecker().isMainThread()) {
            var7 = "main";
         } else {
            var7 = Thread.currentThread().getName();
         }

         var9.setData("thread.name", var7);
         this.children.add(var9);
         TransactionPerformanceCollector var8 = this.transactionPerformanceCollector;
         if (var8 != null) {
            var8.onSpanStarted(var9);
         }

         return var9;
      } else {
         this.hub
            .getOptions()
            .getLogger()
            .log(SentryLevel.WARNING, "Span operation: %s, description: %s dropped due to limit reached. Returning NoOpSpan.", var2, var3);
         return NoOpSpan.getInstance();
      }
   }

   private ISpan createChild(SpanId var1, String var2, String var3, SpanOptions var4) {
      return this.createChild(var1, var2, var3, null, Instrumenter.SENTRY, var4);
   }

   private ISpan createChild(String var1, String var2, SentryDate var3, Instrumenter var4, SpanOptions var5) {
      if (this.root.isFinished()) {
         return NoOpSpan.getInstance();
      } else if (!this.instrumenter.equals(var4)) {
         return NoOpSpan.getInstance();
      } else if (this.children.size() < this.hub.getOptions().getMaxSpans()) {
         return this.root.startChild(var1, var2, var3, var4, var5);
      } else {
         this.hub
            .getOptions()
            .getLogger()
            .log(SentryLevel.WARNING, "Span operation: %s, description: %s dropped due to limit reached. Returning NoOpSpan.", var1, var2);
         return NoOpSpan.getInstance();
      }
   }

   private boolean hasAllChildrenFinished() {
      ArrayList var1 = new ArrayList<>(this.children);
      if (!var1.isEmpty()) {
         for (Span var2 : var1) {
            if (!var2.isFinished() && var2.getFinishDate() == null) {
               return false;
            }
         }
      }

      return true;
   }

   private void onDeadlineTimeoutReached() {
      SpanStatus var2 = this.getStatus();
      if (var2 == null) {
         var2 = SpanStatus.DEADLINE_EXCEEDED;
      }

      boolean var1;
      if (this.transactionOptions.getIdleTimeout() != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      this.forceFinish(var2, var1, null);
      this.isDeadlineTimerRunning.set(false);
   }

   private void onIdleTimeoutReached() {
      SpanStatus var1 = this.getStatus();
      if (var1 == null) {
         var1 = SpanStatus.OK;
      }

      this.finish(var1);
      this.isIdleFinishTimerRunning.set(false);
   }

   private void scheduleDeadlineTimeout() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/SentryTracer.transactionOptions Lio/sentry/TransactionOptions;
      // 04: invokevirtual io/sentry/TransactionOptions.getDeadlineTimeout ()Ljava/lang/Long;
      // 07: astore 2
      // 08: aload 2
      // 09: ifnull 6d
      // 0c: aload 0
      // 0d: getfield io/sentry/SentryTracer.timerLock Ljava/lang/Object;
      // 10: astore 1
      // 11: aload 1
      // 12: monitorenter
      // 13: aload 0
      // 14: getfield io/sentry/SentryTracer.timer Ljava/util/Timer;
      // 17: ifnull 63
      // 1a: aload 0
      // 1b: invokespecial io/sentry/SentryTracer.cancelDeadlineTimer ()V
      // 1e: aload 0
      // 1f: getfield io/sentry/SentryTracer.isDeadlineTimerRunning Ljava/util/concurrent/atomic/AtomicBoolean;
      // 22: bipush 1
      // 23: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // 26: new io/sentry/SentryTracer$2
      // 29: astore 3
      // 2a: aload 3
      // 2b: aload 0
      // 2c: invokespecial io/sentry/SentryTracer$2.<init> (Lio/sentry/SentryTracer;)V
      // 2f: aload 0
      // 30: aload 3
      // 31: putfield io/sentry/SentryTracer.deadlineTimeoutTask Ljava/util/TimerTask;
      // 34: aload 0
      // 35: getfield io/sentry/SentryTracer.timer Ljava/util/Timer;
      // 38: aload 0
      // 39: getfield io/sentry/SentryTracer.deadlineTimeoutTask Ljava/util/TimerTask;
      // 3c: aload 2
      // 3d: invokevirtual java/lang/Long.longValue ()J
      // 40: invokevirtual java/util/Timer.schedule (Ljava/util/TimerTask;J)V
      // 43: goto 63
      // 46: astore 2
      // 47: aload 0
      // 48: getfield io/sentry/SentryTracer.hub Lio/sentry/IHub;
      // 4b: invokeinterface io/sentry/IHub.getOptions ()Lio/sentry/SentryOptions; 1
      // 50: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 53: getstatic io/sentry/SentryLevel.WARNING Lio/sentry/SentryLevel;
      // 56: ldc_w "Failed to schedule finish timer"
      // 59: aload 2
      // 5a: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 5f: aload 0
      // 60: invokespecial io/sentry/SentryTracer.onDeadlineTimeoutReached ()V
      // 63: aload 1
      // 64: monitorexit
      // 65: goto 6d
      // 68: astore 2
      // 69: aload 1
      // 6a: monitorexit
      // 6b: aload 2
      // 6c: athrow
      // 6d: return
   }

   private void updateBaggageValues() {
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
      // 03: getfield io/sentry/SentryTracer.baggage Lio/sentry/Baggage;
      // 06: invokevirtual io/sentry/Baggage.isMutable ()Z
      // 09: ifeq 5e
      // 0c: new java/util/concurrent/atomic/AtomicReference
      // 0f: astore 2
      // 10: aload 2
      // 11: invokespecial java/util/concurrent/atomic/AtomicReference.<init> ()V
      // 14: new java/util/concurrent/atomic/AtomicReference
      // 17: astore 1
      // 18: aload 1
      // 19: invokespecial java/util/concurrent/atomic/AtomicReference.<init> ()V
      // 1c: aload 0
      // 1d: getfield io/sentry/SentryTracer.hub Lio/sentry/IHub;
      // 20: astore 4
      // 22: new io/sentry/SentryTracer$$ExternalSyntheticLambda4
      // 25: astore 3
      // 26: aload 3
      // 27: aload 2
      // 28: aload 1
      // 29: invokespecial io/sentry/SentryTracer$$ExternalSyntheticLambda4.<init> (Ljava/util/concurrent/atomic/AtomicReference;Ljava/util/concurrent/atomic/AtomicReference;)V
      // 2c: aload 4
      // 2e: aload 3
      // 2f: invokeinterface io/sentry/IHub.configureScope (Lio/sentry/ScopeCallback;)V 2
      // 34: aload 0
      // 35: getfield io/sentry/SentryTracer.baggage Lio/sentry/Baggage;
      // 38: aload 0
      // 39: aload 2
      // 3a: invokevirtual java/util/concurrent/atomic/AtomicReference.get ()Ljava/lang/Object;
      // 3d: checkcast io/sentry/protocol/User
      // 40: aload 1
      // 41: invokevirtual java/util/concurrent/atomic/AtomicReference.get ()Ljava/lang/Object;
      // 44: checkcast io/sentry/protocol/SentryId
      // 47: aload 0
      // 48: getfield io/sentry/SentryTracer.hub Lio/sentry/IHub;
      // 4b: invokeinterface io/sentry/IHub.getOptions ()Lio/sentry/SentryOptions; 1
      // 50: aload 0
      // 51: invokevirtual io/sentry/SentryTracer.getSamplingDecision ()Lio/sentry/TracesSamplingDecision;
      // 54: invokevirtual io/sentry/Baggage.setValuesFromTransaction (Lio/sentry/ITransaction;Lio/sentry/protocol/User;Lio/sentry/protocol/SentryId;Lio/sentry/SentryOptions;Lio/sentry/TracesSamplingDecision;)V
      // 57: aload 0
      // 58: getfield io/sentry/SentryTracer.baggage Lio/sentry/Baggage;
      // 5b: invokevirtual io/sentry/Baggage.freeze ()V
      // 5e: aload 0
      // 5f: monitorexit
      // 60: return
      // 61: astore 1
      // 62: aload 0
      // 63: monitorexit
      // 64: aload 1
      // 65: athrow
   }

   @Override
   public void finish() {
      this.finish(this.getStatus());
   }

   @Override
   public void finish(SpanStatus var1) {
      this.finish(var1, null);
   }

   @Override
   public void finish(SpanStatus var1, SentryDate var2) {
      this.finish(var1, var2, true, null);
   }

   @Override
   public void finish(SpanStatus param1, SentryDate param2, boolean param3, Hint param4) {
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
      // 001: getfield io/sentry/SentryTracer.root Lio/sentry/Span;
      // 004: invokevirtual io/sentry/Span.getFinishDate ()Lio/sentry/SentryDate;
      // 007: astore 5
      // 009: aload 2
      // 00a: ifnull 010
      // 00d: goto 013
      // 010: aload 5
      // 012: astore 2
      // 013: aload 2
      // 014: astore 5
      // 016: aload 2
      // 017: ifnonnull 02d
      // 01a: aload 0
      // 01b: getfield io/sentry/SentryTracer.hub Lio/sentry/IHub;
      // 01e: invokeinterface io/sentry/IHub.getOptions ()Lio/sentry/SentryOptions; 1
      // 023: invokevirtual io/sentry/SentryOptions.getDateProvider ()Lio/sentry/SentryDateProvider;
      // 026: invokeinterface io/sentry/SentryDateProvider.now ()Lio/sentry/SentryDate; 1
      // 02b: astore 5
      // 02d: aload 0
      // 02e: getfield io/sentry/SentryTracer.children Ljava/util/List;
      // 031: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 036: astore 7
      // 038: aload 7
      // 03a: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 03f: ifeq 075
      // 042: aload 7
      // 044: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 049: checkcast io/sentry/Span
      // 04c: astore 6
      // 04e: aload 6
      // 050: invokevirtual io/sentry/Span.getOptions ()Lio/sentry/SpanOptions;
      // 053: invokevirtual io/sentry/SpanOptions.isIdle ()Z
      // 056: ifeq 038
      // 059: aload 1
      // 05a: ifnull 062
      // 05d: aload 1
      // 05e: astore 2
      // 05f: goto 06a
      // 062: aload 0
      // 063: invokevirtual io/sentry/SentryTracer.getSpanContext ()Lio/sentry/SpanContext;
      // 066: getfield io/sentry/SpanContext.status Lio/sentry/SpanStatus;
      // 069: astore 2
      // 06a: aload 6
      // 06c: aload 2
      // 06d: aload 5
      // 06f: invokevirtual io/sentry/Span.finish (Lio/sentry/SpanStatus;Lio/sentry/SentryDate;)V
      // 072: goto 038
      // 075: aload 0
      // 076: aload 1
      // 077: invokestatic io/sentry/SentryTracer$FinishStatus.finishing (Lio/sentry/SpanStatus;)Lio/sentry/SentryTracer$FinishStatus;
      // 07a: putfield io/sentry/SentryTracer.finishStatus Lio/sentry/SentryTracer$FinishStatus;
      // 07d: aload 0
      // 07e: getfield io/sentry/SentryTracer.root Lio/sentry/Span;
      // 081: invokevirtual io/sentry/Span.isFinished ()Z
      // 084: ifne 1cd
      // 087: aload 0
      // 088: getfield io/sentry/SentryTracer.transactionOptions Lio/sentry/TransactionOptions;
      // 08b: invokevirtual io/sentry/TransactionOptions.isWaitForChildren ()Z
      // 08e: ifeq 098
      // 091: aload 0
      // 092: invokespecial io/sentry/SentryTracer.hasAllChildrenFinished ()Z
      // 095: ifeq 1cd
      // 098: new java/util/concurrent/atomic/AtomicReference
      // 09b: dup
      // 09c: invokespecial java/util/concurrent/atomic/AtomicReference.<init> ()V
      // 09f: astore 2
      // 0a0: aload 0
      // 0a1: getfield io/sentry/SentryTracer.root Lio/sentry/Span;
      // 0a4: invokevirtual io/sentry/Span.getSpanFinishedCallback ()Lio/sentry/SpanFinishedCallback;
      // 0a7: astore 1
      // 0a8: aload 0
      // 0a9: getfield io/sentry/SentryTracer.root Lio/sentry/Span;
      // 0ac: new io/sentry/SentryTracer$$ExternalSyntheticLambda1
      // 0af: dup
      // 0b0: aload 0
      // 0b1: aload 1
      // 0b2: aload 2
      // 0b3: invokespecial io/sentry/SentryTracer$$ExternalSyntheticLambda1.<init> (Lio/sentry/SentryTracer;Lio/sentry/SpanFinishedCallback;Ljava/util/concurrent/atomic/AtomicReference;)V
      // 0b6: invokevirtual io/sentry/Span.setSpanFinishedCallback (Lio/sentry/SpanFinishedCallback;)V
      // 0b9: aload 0
      // 0ba: getfield io/sentry/SentryTracer.root Lio/sentry/Span;
      // 0bd: aload 0
      // 0be: getfield io/sentry/SentryTracer.finishStatus Lio/sentry/SentryTracer$FinishStatus;
      // 0c1: invokestatic io/sentry/SentryTracer$FinishStatus.access$100 (Lio/sentry/SentryTracer$FinishStatus;)Lio/sentry/SpanStatus;
      // 0c4: aload 5
      // 0c6: invokevirtual io/sentry/Span.finish (Lio/sentry/SpanStatus;Lio/sentry/SentryDate;)V
      // 0c9: getstatic java/lang/Boolean.TRUE Ljava/lang/Boolean;
      // 0cc: aload 0
      // 0cd: invokevirtual io/sentry/SentryTracer.isSampled ()Ljava/lang/Boolean;
      // 0d0: invokevirtual java/lang/Boolean.equals (Ljava/lang/Object;)Z
      // 0d3: ifeq 109
      // 0d6: getstatic java/lang/Boolean.TRUE Ljava/lang/Boolean;
      // 0d9: aload 0
      // 0da: invokevirtual io/sentry/SentryTracer.isProfileSampled ()Ljava/lang/Boolean;
      // 0dd: invokevirtual java/lang/Boolean.equals (Ljava/lang/Object;)Z
      // 0e0: ifeq 109
      // 0e3: aload 0
      // 0e4: getfield io/sentry/SentryTracer.hub Lio/sentry/IHub;
      // 0e7: invokeinterface io/sentry/IHub.getOptions ()Lio/sentry/SentryOptions; 1
      // 0ec: invokevirtual io/sentry/SentryOptions.getTransactionProfiler ()Lio/sentry/ITransactionProfiler;
      // 0ef: aload 0
      // 0f0: aload 2
      // 0f1: invokevirtual java/util/concurrent/atomic/AtomicReference.get ()Ljava/lang/Object;
      // 0f4: checkcast java/util/List
      // 0f7: aload 0
      // 0f8: getfield io/sentry/SentryTracer.hub Lio/sentry/IHub;
      // 0fb: invokeinterface io/sentry/IHub.getOptions ()Lio/sentry/SentryOptions; 1
      // 100: invokeinterface io/sentry/ITransactionProfiler.onTransactionFinish (Lio/sentry/ITransaction;Ljava/util/List;Lio/sentry/SentryOptions;)Lio/sentry/ProfilingTraceData; 4
      // 105: astore 1
      // 106: goto 10b
      // 109: aconst_null
      // 10a: astore 1
      // 10b: aload 2
      // 10c: invokevirtual java/util/concurrent/atomic/AtomicReference.get ()Ljava/lang/Object;
      // 10f: ifnull 11e
      // 112: aload 2
      // 113: invokevirtual java/util/concurrent/atomic/AtomicReference.get ()Ljava/lang/Object;
      // 116: checkcast java/util/List
      // 119: invokeinterface java/util/List.clear ()V 1
      // 11e: aload 0
      // 11f: getfield io/sentry/SentryTracer.hub Lio/sentry/IHub;
      // 122: new io/sentry/SentryTracer$$ExternalSyntheticLambda2
      // 125: dup
      // 126: aload 0
      // 127: invokespecial io/sentry/SentryTracer$$ExternalSyntheticLambda2.<init> (Lio/sentry/SentryTracer;)V
      // 12a: invokeinterface io/sentry/IHub.configureScope (Lio/sentry/ScopeCallback;)V 2
      // 12f: new io/sentry/protocol/SentryTransaction
      // 132: dup
      // 133: aload 0
      // 134: invokespecial io/sentry/protocol/SentryTransaction.<init> (Lio/sentry/SentryTracer;)V
      // 137: astore 5
      // 139: aload 0
      // 13a: getfield io/sentry/SentryTracer.timer Ljava/util/Timer;
      // 13d: ifnull 16c
      // 140: aload 0
      // 141: getfield io/sentry/SentryTracer.timerLock Ljava/lang/Object;
      // 144: astore 2
      // 145: aload 2
      // 146: monitorenter
      // 147: aload 0
      // 148: getfield io/sentry/SentryTracer.timer Ljava/util/Timer;
      // 14b: ifnull 162
      // 14e: aload 0
      // 14f: invokespecial io/sentry/SentryTracer.cancelIdleTimer ()V
      // 152: aload 0
      // 153: invokespecial io/sentry/SentryTracer.cancelDeadlineTimer ()V
      // 156: aload 0
      // 157: getfield io/sentry/SentryTracer.timer Ljava/util/Timer;
      // 15a: invokevirtual java/util/Timer.cancel ()V
      // 15d: aload 0
      // 15e: aconst_null
      // 15f: putfield io/sentry/SentryTracer.timer Ljava/util/Timer;
      // 162: aload 2
      // 163: monitorexit
      // 164: goto 16c
      // 167: astore 1
      // 168: aload 2
      // 169: monitorexit
      // 16a: aload 1
      // 16b: athrow
      // 16c: iload 3
      // 16d: ifeq 1a9
      // 170: aload 0
      // 171: getfield io/sentry/SentryTracer.children Ljava/util/List;
      // 174: invokeinterface java/util/List.isEmpty ()Z 1
      // 179: ifeq 1a9
      // 17c: aload 0
      // 17d: getfield io/sentry/SentryTracer.transactionOptions Lio/sentry/TransactionOptions;
      // 180: invokevirtual io/sentry/TransactionOptions.getIdleTimeout ()Ljava/lang/Long;
      // 183: ifnull 1a9
      // 186: aload 0
      // 187: getfield io/sentry/SentryTracer.hub Lio/sentry/IHub;
      // 18a: invokeinterface io/sentry/IHub.getOptions ()Lio/sentry/SentryOptions; 1
      // 18f: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 192: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
      // 195: ldc_w "Dropping idle transaction %s because it has no child spans"
      // 198: bipush 1
      // 199: anewarray 4
      // 19c: dup
      // 19d: bipush 0
      // 19e: aload 0
      // 19f: getfield io/sentry/SentryTracer.name Ljava/lang/String;
      // 1a2: aastore
      // 1a3: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 1a8: return
      // 1a9: aload 5
      // 1ab: invokevirtual io/sentry/protocol/SentryTransaction.getMeasurements ()Ljava/util/Map;
      // 1ae: aload 0
      // 1af: getfield io/sentry/SentryTracer.root Lio/sentry/Span;
      // 1b2: invokevirtual io/sentry/Span.getMeasurements ()Ljava/util/Map;
      // 1b5: invokeinterface java/util/Map.putAll (Ljava/util/Map;)V 2
      // 1ba: aload 0
      // 1bb: getfield io/sentry/SentryTracer.hub Lio/sentry/IHub;
      // 1be: aload 5
      // 1c0: aload 0
      // 1c1: invokevirtual io/sentry/SentryTracer.traceContext ()Lio/sentry/TraceContext;
      // 1c4: aload 4
      // 1c6: aload 1
      // 1c7: invokeinterface io/sentry/IHub.captureTransaction (Lio/sentry/protocol/SentryTransaction;Lio/sentry/TraceContext;Lio/sentry/Hint;Lio/sentry/ProfilingTraceData;)Lio/sentry/protocol/SentryId; 5
      // 1cc: pop
      // 1cd: return
   }

   @Override
   public void forceFinish(SpanStatus var1, boolean var2, Hint var3) {
      if (!this.isFinished()) {
         SentryDate var4 = this.hub.getOptions().getDateProvider().now();
         List var5 = this.children;
         ListIterator var7 = var5.listIterator(var5.size());

         while (var7.hasPrevious()) {
            Span var6 = (Span)var7.previous();
            var6.setSpanFinishedCallback(null);
            var6.finish(var1, var4);
         }

         this.finish(var1, var4, var2, var3);
      }
   }

   public List<Span> getChildren() {
      return this.children;
   }

   @Override
   public Contexts getContexts() {
      return this.contexts;
   }

   @Override
   public Object getData(String var1) {
      return this.root.getData(var1);
   }

   public Map<String, Object> getData() {
      return this.root.getData();
   }

   TimerTask getDeadlineTimeoutTask() {
      return this.deadlineTimeoutTask;
   }

   @Override
   public String getDescription() {
      return this.root.getDescription();
   }

   @Override
   public SentryId getEventId() {
      return this.eventId;
   }

   @Override
   public SentryDate getFinishDate() {
      return this.root.getFinishDate();
   }

   TimerTask getIdleTimeoutTask() {
      return this.idleTimeoutTask;
   }

   @Override
   public Span getLatestActiveSpan() {
      ArrayList var2 = new ArrayList<>(this.children);
      if (!var2.isEmpty()) {
         for (int var1 = var2.size() - 1; var1 >= 0; var1--) {
            if (!((Span)var2.get(var1)).isFinished()) {
               return (Span)var2.get(var1);
            }
         }
      }

      return null;
   }

   @Override
   public LocalMetricsAggregator getLocalMetricsAggregator() {
      return this.root.getLocalMetricsAggregator();
   }

   @Override
   public String getName() {
      return this.name;
   }

   @Override
   public String getOperation() {
      return this.root.getOperation();
   }

   Span getRoot() {
      return this.root;
   }

   @Override
   public TracesSamplingDecision getSamplingDecision() {
      return this.root.getSamplingDecision();
   }

   @Override
   public SpanContext getSpanContext() {
      return this.root.getSpanContext();
   }

   @Override
   public List<Span> getSpans() {
      return this.children;
   }

   @Override
   public SentryDate getStartDate() {
      return this.root.getStartDate();
   }

   @Override
   public SpanStatus getStatus() {
      return this.root.getStatus();
   }

   @Override
   public String getTag(String var1) {
      return this.root.getTag(var1);
   }

   @Override
   public Throwable getThrowable() {
      return this.root.getThrowable();
   }

   Timer getTimer() {
      return this.timer;
   }

   @Override
   public TransactionNameSource getTransactionNameSource() {
      return this.transactionNameSource;
   }

   AtomicBoolean isDeadlineTimerRunning() {
      return this.isDeadlineTimerRunning;
   }

   AtomicBoolean isFinishTimerRunning() {
      return this.isIdleFinishTimerRunning;
   }

   @Override
   public boolean isFinished() {
      return this.root.isFinished();
   }

   @Override
   public boolean isNoOp() {
      return false;
   }

   @Override
   public Boolean isProfileSampled() {
      return this.root.isProfileSampled();
   }

   @Override
   public Boolean isSampled() {
      return this.root.isSampled();
   }

   @Override
   public void scheduleFinish() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/SentryTracer.timerLock Ljava/lang/Object;
      // 04: astore 1
      // 05: aload 1
      // 06: monitorenter
      // 07: aload 0
      // 08: getfield io/sentry/SentryTracer.timer Ljava/util/Timer;
      // 0b: ifnull 63
      // 0e: aload 0
      // 0f: getfield io/sentry/SentryTracer.transactionOptions Lio/sentry/TransactionOptions;
      // 12: invokevirtual io/sentry/TransactionOptions.getIdleTimeout ()Ljava/lang/Long;
      // 15: astore 3
      // 16: aload 3
      // 17: ifnull 63
      // 1a: aload 0
      // 1b: invokespecial io/sentry/SentryTracer.cancelIdleTimer ()V
      // 1e: aload 0
      // 1f: getfield io/sentry/SentryTracer.isIdleFinishTimerRunning Ljava/util/concurrent/atomic/AtomicBoolean;
      // 22: bipush 1
      // 23: invokevirtual java/util/concurrent/atomic/AtomicBoolean.set (Z)V
      // 26: new io/sentry/SentryTracer$1
      // 29: astore 2
      // 2a: aload 2
      // 2b: aload 0
      // 2c: invokespecial io/sentry/SentryTracer$1.<init> (Lio/sentry/SentryTracer;)V
      // 2f: aload 0
      // 30: aload 2
      // 31: putfield io/sentry/SentryTracer.idleTimeoutTask Ljava/util/TimerTask;
      // 34: aload 0
      // 35: getfield io/sentry/SentryTracer.timer Ljava/util/Timer;
      // 38: aload 0
      // 39: getfield io/sentry/SentryTracer.idleTimeoutTask Ljava/util/TimerTask;
      // 3c: aload 3
      // 3d: invokevirtual java/lang/Long.longValue ()J
      // 40: invokevirtual java/util/Timer.schedule (Ljava/util/TimerTask;J)V
      // 43: goto 63
      // 46: astore 2
      // 47: aload 0
      // 48: getfield io/sentry/SentryTracer.hub Lio/sentry/IHub;
      // 4b: invokeinterface io/sentry/IHub.getOptions ()Lio/sentry/SentryOptions; 1
      // 50: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 53: getstatic io/sentry/SentryLevel.WARNING Lio/sentry/SentryLevel;
      // 56: ldc_w "Failed to schedule finish timer"
      // 59: aload 2
      // 5a: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 5f: aload 0
      // 60: invokespecial io/sentry/SentryTracer.onIdleTimeoutReached ()V
      // 63: aload 1
      // 64: monitorexit
      // 65: return
      // 66: astore 2
      // 67: aload 1
      // 68: monitorexit
      // 69: aload 2
      // 6a: athrow
   }

   @Override
   public void setContext(String var1, Object var2) {
      this.contexts.put(var1, var2);
   }

   @Override
   public void setData(String var1, Object var2) {
      if (this.root.isFinished()) {
         this.hub.getOptions().getLogger().log(SentryLevel.DEBUG, "The transaction is already finished. Data %s cannot be set", var1);
      } else {
         this.root.setData(var1, var2);
      }
   }

   @Override
   public void setDescription(String var1) {
      if (this.root.isFinished()) {
         this.hub.getOptions().getLogger().log(SentryLevel.DEBUG, "The transaction is already finished. Description %s cannot be set", var1);
      } else {
         this.root.setDescription(var1);
      }
   }

   @Override
   public void setMeasurement(String var1, Number var2) {
      this.root.setMeasurement(var1, var2);
   }

   @Override
   public void setMeasurement(String var1, Number var2, MeasurementUnit var3) {
      this.root.setMeasurement(var1, var2, var3);
   }

   public void setMeasurementFromChild(String var1, Number var2) {
      if (!this.root.getMeasurements().containsKey(var1)) {
         this.setMeasurement(var1, var2);
      }
   }

   public void setMeasurementFromChild(String var1, Number var2, MeasurementUnit var3) {
      if (!this.root.getMeasurements().containsKey(var1)) {
         this.setMeasurement(var1, var2, var3);
      }
   }

   @Override
   public void setName(String var1) {
      this.setName(var1, TransactionNameSource.CUSTOM);
   }

   @Override
   public void setName(String var1, TransactionNameSource var2) {
      if (this.root.isFinished()) {
         this.hub.getOptions().getLogger().log(SentryLevel.DEBUG, "The transaction is already finished. Name %s cannot be set", var1);
      } else {
         this.name = var1;
         this.transactionNameSource = var2;
      }
   }

   @Override
   public void setOperation(String var1) {
      if (this.root.isFinished()) {
         this.hub.getOptions().getLogger().log(SentryLevel.DEBUG, "The transaction is already finished. Operation %s cannot be set", var1);
      } else {
         this.root.setOperation(var1);
      }
   }

   @Override
   public void setStatus(SpanStatus var1) {
      if (this.root.isFinished()) {
         ILogger var2 = this.hub.getOptions().getLogger();
         SentryLevel var3 = SentryLevel.DEBUG;
         String var4;
         if (var1 == null) {
            var4 = "null";
         } else {
            var4 = var1.name();
         }

         var2.log(var3, "The transaction is already finished. Status %s cannot be set", var4);
      } else {
         this.root.setStatus(var1);
      }
   }

   @Override
   public void setTag(String var1, String var2) {
      if (this.root.isFinished()) {
         this.hub.getOptions().getLogger().log(SentryLevel.DEBUG, "The transaction is already finished. Tag %s cannot be set", var1);
      } else {
         this.root.setTag(var1, var2);
      }
   }

   @Override
   public void setThrowable(Throwable var1) {
      if (this.root.isFinished()) {
         this.hub.getOptions().getLogger().log(SentryLevel.DEBUG, "The transaction is already finished. Throwable cannot be set");
      } else {
         this.root.setThrowable(var1);
      }
   }

   ISpan startChild(SpanId var1, String var2, String var3) {
      return this.startChild(var1, var2, var3, new SpanOptions());
   }

   ISpan startChild(SpanId var1, String var2, String var3, SentryDate var4, Instrumenter var5) {
      return this.createChild(var1, var2, var3, var4, var5, new SpanOptions());
   }

   ISpan startChild(SpanId var1, String var2, String var3, SentryDate var4, Instrumenter var5, SpanOptions var6) {
      return this.createChild(var1, var2, var3, var4, var5, var6);
   }

   ISpan startChild(SpanId var1, String var2, String var3, SpanOptions var4) {
      return this.createChild(var1, var2, var3, var4);
   }

   @Override
   public ISpan startChild(String var1) {
      String var2 = (String)null;
      return this.startChild(var1, null);
   }

   @Override
   public ISpan startChild(String var1, String var2) {
      return this.startChild(var1, var2, null, Instrumenter.SENTRY, new SpanOptions());
   }

   @Override
   public ISpan startChild(String var1, String var2, SentryDate var3) {
      return this.createChild(var1, var2, var3, Instrumenter.SENTRY, new SpanOptions());
   }

   @Override
   public ISpan startChild(String var1, String var2, SentryDate var3, Instrumenter var4) {
      return this.startChild(var1, var2, var3, var4, new SpanOptions());
   }

   @Override
   public ISpan startChild(String var1, String var2, SentryDate var3, Instrumenter var4, SpanOptions var5) {
      return this.createChild(var1, var2, var3, var4, var5);
   }

   @Override
   public ISpan startChild(String var1, String var2, SpanOptions var3) {
      return this.createChild(var1, var2, null, Instrumenter.SENTRY, var3);
   }

   @Override
   public BaggageHeader toBaggageHeader(List<String> var1) {
      if (this.hub.getOptions().isTraceSampling()) {
         this.updateBaggageValues();
         return BaggageHeader.fromBaggageAndOutgoingHeader(this.baggage, var1);
      } else {
         return null;
      }
   }

   @Override
   public SentryTraceHeader toSentryTrace() {
      return this.root.toSentryTrace();
   }

   @Override
   public TraceContext traceContext() {
      if (this.hub.getOptions().isTraceSampling()) {
         this.updateBaggageValues();
         return this.baggage.toTraceContext();
      } else {
         return null;
      }
   }

   @Override
   public boolean updateEndDate(SentryDate var1) {
      return this.root.updateEndDate(var1);
   }

   private static final class FinishStatus {
      static final SentryTracer.FinishStatus NOT_FINISHED = notFinished();
      private final boolean isFinishing;
      private final SpanStatus spanStatus;

      private FinishStatus(boolean var1, SpanStatus var2) {
         this.isFinishing = var1;
         this.spanStatus = var2;
      }

      static SentryTracer.FinishStatus finishing(SpanStatus var0) {
         return new SentryTracer.FinishStatus(true, var0);
      }

      private static SentryTracer.FinishStatus notFinished() {
         return new SentryTracer.FinishStatus(false, null);
      }
   }
}
