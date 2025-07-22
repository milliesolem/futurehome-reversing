package io.sentry;

import io.sentry.clientreport.DiscardReason;
import io.sentry.hints.SessionEndHint;
import io.sentry.hints.SessionStartHint;
import io.sentry.metrics.LocalMetricsAggregator;
import io.sentry.metrics.MetricsApi;
import io.sentry.protocol.SentryId;
import io.sentry.protocol.SentryTransaction;
import io.sentry.protocol.User;
import io.sentry.transport.RateLimiter;
import io.sentry.util.ExceptionUtils;
import io.sentry.util.HintUtils;
import io.sentry.util.Objects;
import io.sentry.util.Pair;
import io.sentry.util.TracingUtils;
import j..util.DesugarCollections;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public final class Hub implements IHub, MetricsApi.IMetricsInterface {
   private volatile boolean isEnabled;
   private volatile SentryId lastEventId;
   private final MetricsApi metricsApi;
   private final SentryOptions options;
   private final Stack stack;
   private final Map<Throwable, Pair<WeakReference<ISpan>, String>> throwableToSpan = DesugarCollections.synchronizedMap(new WeakHashMap());
   private final TracesSampler tracesSampler;
   private final TransactionPerformanceCollector transactionPerformanceCollector;

   public Hub(SentryOptions var1) {
      this(var1, createRootStackItem(var1));
   }

   private Hub(SentryOptions var1, Stack.StackItem var2) {
      this(var1, new Stack(var1.getLogger(), var2));
   }

   private Hub(SentryOptions var1, Stack var2) {
      validateOptions(var1);
      this.options = var1;
      this.tracesSampler = new TracesSampler(var1);
      this.stack = var2;
      this.lastEventId = SentryId.EMPTY_ID;
      this.transactionPerformanceCollector = var1.getTransactionPerformanceCollector();
      this.isEnabled = true;
      this.metricsApi = new MetricsApi(this);
   }

   private void assignTraceContext(SentryEvent var1) {
      if (this.options.isTracingEnabled() && var1.getThrowable() != null) {
         Pair var2 = this.throwableToSpan.get(ExceptionUtils.findRootCause(var1.getThrowable()));
         if (var2 != null) {
            WeakReference var3 = (WeakReference)var2.getFirst();
            if (var1.getContexts().getTrace() == null && var3 != null) {
               ISpan var5 = (ISpan)var3.get();
               if (var5 != null) {
                  var1.getContexts().setTrace(var5.getSpanContext());
               }
            }

            String var4 = (String)var2.getSecond();
            if (var1.getTransaction() == null && var4 != null) {
               var1.setTransaction(var4);
            }
         }
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private IScope buildLocalScope(IScope var1, ScopeCallback var2) {
      if (var2 != null) {
         try {
            IScope var3 = var1.clone();
            var2.run(var3);
            return var3;
         } catch (Throwable var5) {
            this.options.getLogger().log(SentryLevel.ERROR, "Error in the 'ScopeCallback' callback.", var5);
            return var1;
         }
      } else {
         return var1;
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private SentryId captureEventInternal(SentryEvent var1, Hint var2, ScopeCallback var3) {
      SentryId var5 = SentryId.EMPTY_ID;
      SentryId var4;
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'captureEvent' call is a no-op.");
         var4 = var5;
      } else if (var1 == null) {
         this.options.getLogger().log(SentryLevel.WARNING, "captureEvent called with null parameter.");
         var4 = var5;
      } else {
         try {
            this.assignTraceContext(var1);
         } catch (Throwable var36) {
            ILogger var37 = this.options.getLogger();
            SentryLevel var6 = SentryLevel.ERROR;
            StringBuilder var43 = new StringBuilder("Error while capturing event with id: ");
            var43.append(var1.getEventId());
            var37.log(var6, var43.toString(), var36);
            return var5;
         }

         Stack.StackItem var50;
         try {
            var50 = this.stack.peek();
         } catch (Throwable var35) {
            ILogger var38 = this.options.getLogger();
            SentryLevel var49 = SentryLevel.ERROR;
            StringBuilder var44 = new StringBuilder("Error while capturing event with id: ");
            var44.append(var1.getEventId());
            var38.log(var49, var44.toString(), var35);
            return var5;
         }

         try {
            var46 = this.buildLocalScope(var50.getScope(), var3);
         } catch (Throwable var34) {
            ILogger var39 = this.options.getLogger();
            SentryLevel var51 = SentryLevel.ERROR;
            StringBuilder var45 = new StringBuilder("Error while capturing event with id: ");
            var45.append(var1.getEventId());
            var39.log(var51, var45.toString(), var34);
            return var5;
         }

         try {
            var41 = var50.getClient().captureEvent(var1, var46, var2);
         } catch (Throwable var33) {
            ILogger var40 = this.options.getLogger();
            SentryLevel var52 = SentryLevel.ERROR;
            StringBuilder var47 = new StringBuilder("Error while capturing event with id: ");
            var47.append(var1.getEventId());
            var40.log(var52, var47.toString(), var33);
            return var5;
         }

         try {
            this.lastEventId = var41;
         } catch (Throwable var32) {
            ILogger var42 = this.options.getLogger();
            SentryLevel var53 = SentryLevel.ERROR;
            StringBuilder var48 = new StringBuilder("Error while capturing event with id: ");
            var48.append(var1.getEventId());
            var42.log(var53, var48.toString(), var32);
            return var41;
         }

         var4 = var41;
      }

      return var4;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private SentryId captureExceptionInternal(Throwable var1, Hint var2, ScopeCallback var3) {
      SentryId var4 = SentryId.EMPTY_ID;
      SentryId var9;
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'captureException' call is a no-op.");
         var9 = var4;
      } else if (var1 == null) {
         this.options.getLogger().log(SentryLevel.WARNING, "captureException called with null parameter.");
         var9 = var4;
      } else {
         label25: {
            try {
               Stack.StackItem var14 = this.stack.peek();
               SentryEvent var5 = new SentryEvent(var1);
               this.assignTraceContext(var5);
               IScope var13 = this.buildLocalScope(var14.getScope(), var3);
               var11 = var14.getClient().captureEvent(var5, var13, var2);
            } catch (Throwable var8) {
               ILogger var12 = this.options.getLogger();
               SentryLevel var10 = SentryLevel.ERROR;
               StringBuilder var6 = new StringBuilder("Error while capturing exception: ");
               var6.append(var1.getMessage());
               var12.log(var10, var6.toString(), var8);
               var9 = var4;
               break label25;
            }

            var9 = var11;
         }
      }

      this.lastEventId = var9;
      return var9;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private SentryId captureMessageInternal(String var1, SentryLevel var2, ScopeCallback var3) {
      SentryId var4 = SentryId.EMPTY_ID;
      SentryId var9;
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'captureMessage' call is a no-op.");
         var9 = var4;
      } else if (var1 == null) {
         this.options.getLogger().log(SentryLevel.WARNING, "captureMessage called with null parameter.");
         var9 = var4;
      } else {
         label25: {
            try {
               Stack.StackItem var5 = this.stack.peek();
               IScope var13 = this.buildLocalScope(var5.getScope(), var3);
               var11 = var5.getClient().captureMessage(var1, var2, var13);
            } catch (Throwable var8) {
               ILogger var10 = this.options.getLogger();
               SentryLevel var12 = SentryLevel.ERROR;
               StringBuilder var6 = new StringBuilder("Error while capturing message: ");
               var6.append(var1);
               var10.log(var12, var6.toString(), var8);
               var9 = var4;
               break label25;
            }

            var9 = var11;
         }
      }

      this.lastEventId = var9;
      return var9;
   }

   private static Stack.StackItem createRootStackItem(SentryOptions var0) {
      validateOptions(var0);
      Scope var1 = new Scope(var0);
      return new Stack.StackItem(var0, new SentryClient(var0), var1);
   }

   private ITransaction createTransaction(TransactionContext var1, TransactionOptions var2) {
      Objects.requireNonNull(var1, "transactionContext is required");
      Object var4;
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'startTransaction' returns a no-op.");
         var4 = NoOpTransaction.getInstance();
      } else if (!this.options.getInstrumenter().equals(var1.getInstrumenter())) {
         this.options
            .getLogger()
            .log(
               SentryLevel.DEBUG,
               "Returning no-op for instrumenter %s as the SDK has been configured to use instrumenter %s",
               var1.getInstrumenter(),
               this.options.getInstrumenter()
            );
         var4 = NoOpTransaction.getInstance();
      } else if (!this.options.isTracingEnabled()) {
         this.options.getLogger().log(SentryLevel.INFO, "Tracing is disabled and this 'startTransaction' returns a no-op.");
         var4 = NoOpTransaction.getInstance();
      } else {
         SamplingContext var3 = new SamplingContext(var1, var2.getCustomSamplingContext());
         TracesSamplingDecision var5 = this.tracesSampler.sample(var3);
         var1.setSamplingDecision(var5);
         var4 = new SentryTracer(var1, this, var2, this.transactionPerformanceCollector);
         if (var5.getSampled() && var5.getProfileSampled()) {
            ITransactionProfiler var6 = this.options.getTransactionProfiler();
            if (!var6.isRunning()) {
               var6.start();
               var6.bindTransaction((ITransaction)var4);
            } else if (var2.isAppStartTransaction()) {
               var6.bindTransaction((ITransaction)var4);
            }
         }
      }

      if (var2.isBindToScope()) {
         this.configureScope(new Hub$$ExternalSyntheticLambda3((ITransaction)var4));
      }

      return (ITransaction)var4;
   }

   private static void validateOptions(SentryOptions var0) {
      Objects.requireNonNull(var0, "SentryOptions is required.");
      if (var0.getDsn() == null || var0.getDsn().isEmpty()) {
         throw new IllegalArgumentException("Hub requires a DSN to be instantiated. Considering using the NoOpHub if no DSN is available.");
      }
   }

   @Override
   public void addBreadcrumb(Breadcrumb var1) {
      this.addBreadcrumb(var1, new Hint());
   }

   @Override
   public void addBreadcrumb(Breadcrumb var1, Hint var2) {
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'addBreadcrumb' call is a no-op.");
      } else if (var1 == null) {
         this.options.getLogger().log(SentryLevel.WARNING, "addBreadcrumb called with null parameter.");
      } else {
         this.stack.peek().getScope().addBreadcrumb(var1, var2);
      }
   }

   @Override
   public void bindClient(ISentryClient var1) {
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'bindClient' call is a no-op.");
      } else {
         Stack.StackItem var2 = this.stack.peek();
         if (var1 != null) {
            this.options.getLogger().log(SentryLevel.DEBUG, "New client bound to scope.");
            var2.setClient(var1);
         } else {
            this.options.getLogger().log(SentryLevel.DEBUG, "NoOp client bound to scope.");
            var2.setClient(NoOpSentryClient.getInstance());
         }
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public SentryId captureCheckIn(CheckIn var1) {
      SentryId var2 = SentryId.EMPTY_ID;
      SentryId var6;
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'captureCheckIn' call is a no-op.");
         var6 = var2;
      } else {
         label20:
         try {
            Stack.StackItem var3 = this.stack.peek();
            var6 = var3.getClient().captureCheckIn(var1, var3.getScope(), null);
         } catch (Throwable var5) {
            this.options.getLogger().log(SentryLevel.ERROR, "Error while capturing check-in for slug", var5);
            var6 = var2;
            break label20;
         }
      }

      this.lastEventId = var6;
      return var6;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public SentryId captureEnvelope(SentryEnvelope var1, Hint var2) {
      Objects.requireNonNull(var1, "SentryEnvelope is required.");
      SentryId var3 = SentryId.EMPTY_ID;
      SentryId var6;
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'captureEnvelope' call is a no-op.");
         var6 = var3;
      } else {
         try {
            var7 = this.stack.peek().getClient().captureEnvelope(var1, var2);
         } catch (Throwable var5) {
            this.options.getLogger().log(SentryLevel.ERROR, "Error while capturing envelope.", var5);
            return var3;
         }

         var6 = var3;
         if (var7 != null) {
            var6 = var7;
         }
      }

      return var6;
   }

   @Override
   public SentryId captureEvent(SentryEvent var1, Hint var2) {
      return this.captureEventInternal(var1, var2, null);
   }

   @Override
   public SentryId captureEvent(SentryEvent var1, Hint var2, ScopeCallback var3) {
      return this.captureEventInternal(var1, var2, var3);
   }

   @Override
   public SentryId captureException(Throwable var1, Hint var2) {
      return this.captureExceptionInternal(var1, var2, null);
   }

   @Override
   public SentryId captureException(Throwable var1, Hint var2, ScopeCallback var3) {
      return this.captureExceptionInternal(var1, var2, var3);
   }

   @Override
   public SentryId captureMessage(String var1, SentryLevel var2) {
      return this.captureMessageInternal(var1, var2, null);
   }

   @Override
   public SentryId captureMessage(String var1, SentryLevel var2, ScopeCallback var3) {
      return this.captureMessageInternal(var1, var2, var3);
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public SentryId captureReplay(SentryReplayEvent var1, Hint var2) {
      SentryId var3 = SentryId.EMPTY_ID;
      SentryId var7;
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'captureReplay' call is a no-op.");
         var7 = var3;
      } else {
         try {
            Stack.StackItem var4 = this.stack.peek();
            var7 = var4.getClient().captureReplayEvent(var1, var4.getScope(), var2);
         } catch (Throwable var6) {
            this.options.getLogger().log(SentryLevel.ERROR, "Error while capturing replay", var6);
            return var3;
         }
      }

      return var7;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public SentryId captureTransaction(SentryTransaction var1, TraceContext var2, Hint var3, ProfilingTraceData var4) {
      Objects.requireNonNull(var1, "transaction is required");
      SentryId var5 = SentryId.EMPTY_ID;
      SentryId var9;
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'captureTransaction' call is a no-op.");
         var9 = var5;
      } else if (!var1.isFinished()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Transaction: %s is not finished and this 'captureTransaction' call is a no-op.", var1.getEventId());
         var9 = var5;
      } else if (!Boolean.TRUE.equals(var1.isSampled())) {
         this.options.getLogger().log(SentryLevel.DEBUG, "Transaction %s was dropped due to sampling decision.", var1.getEventId());
         if (this.options.getBackpressureMonitor().getDownsampleFactor() > 0) {
            this.options.getClientReportRecorder().recordLostEvent(DiscardReason.BACKPRESSURE, DataCategory.Transaction);
            this.options.getClientReportRecorder().recordLostEvent(DiscardReason.BACKPRESSURE, DataCategory.Span, var1.getSpans().size() + 1);
            var9 = var5;
         } else {
            this.options.getClientReportRecorder().recordLostEvent(DiscardReason.SAMPLE_RATE, DataCategory.Transaction);
            this.options.getClientReportRecorder().recordLostEvent(DiscardReason.SAMPLE_RATE, DataCategory.Span, var1.getSpans().size() + 1);
            var9 = var5;
         }
      } else {
         try {
            Stack.StackItem var13 = this.stack.peek();
            var10 = var13.getClient().captureTransaction(var1, var2, var13.getScope(), var3, var4);
         } catch (Throwable var8) {
            ILogger var12 = this.options.getLogger();
            SentryLevel var6 = SentryLevel.ERROR;
            StringBuilder var11 = new StringBuilder("Error while capturing transaction with id: ");
            var11.append(var1.getEventId());
            var12.log(var6, var11.toString(), var8);
            return var5;
         }

         var9 = var10;
      }

      return var9;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void captureUserFeedback(UserFeedback var1) {
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'captureUserFeedback' call is a no-op.");
      } else {
         try {
            this.stack.peek().getClient().captureUserFeedback(var1);
         } catch (Throwable var7) {
            ILogger var3 = this.options.getLogger();
            SentryLevel var5 = SentryLevel.ERROR;
            StringBuilder var4 = new StringBuilder("Error while capturing captureUserFeedback: ");
            var4.append(var1.toString());
            var3.log(var5, var4.toString(), var7);
            return;
         }
      }
   }

   @Override
   public void clearBreadcrumbs() {
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'clearBreadcrumbs' call is a no-op.");
      } else {
         this.stack.peek().getScope().clearBreadcrumbs();
      }
   }

   @Override
   public IHub clone() {
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Disabled Hub cloned.");
      }

      return new Hub(this.options, new Stack(this.stack));
   }

   @Override
   public void close() {
      this.close(false);
   }

   @Override
   public void close(boolean param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: parsing failure!
      //   at org.jetbrains.java.decompiler.modules.decompiler.decompose.DomHelper.parseGraph(DomHelper.java:211)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:166)
      //
      // Bytecode:
      // 000: aload 0
      // 001: invokevirtual io/sentry/Hub.isEnabled ()Z
      // 004: ifne 020
      // 007: aload 0
      // 008: getfield io/sentry/Hub.options Lio/sentry/SentryOptions;
      // 00b: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 00e: getstatic io/sentry/SentryLevel.WARNING Lio/sentry/SentryLevel;
      // 011: ldc_w "Instance is disabled and this 'close' call is a no-op."
      // 014: bipush 0
      // 015: anewarray 4
      // 018: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 01d: goto 100
      // 020: aload 0
      // 021: getfield io/sentry/Hub.options Lio/sentry/SentryOptions;
      // 024: invokevirtual io/sentry/SentryOptions.getIntegrations ()Ljava/util/List;
      // 027: invokeinterface java/util/List.iterator ()Ljava/util/Iterator; 1
      // 02c: astore 3
      // 02d: aload 3
      // 02e: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 033: ifeq 07d
      // 036: aload 3
      // 037: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 03c: checkcast io/sentry/Integration
      // 03f: astore 4
      // 041: aload 4
      // 043: instanceof java/io/Closeable
      // 046: istore 2
      // 047: iload 2
      // 048: ifeq 02d
      // 04b: aload 4
      // 04d: checkcast java/io/Closeable
      // 050: invokeinterface java/io/Closeable.close ()V 1
      // 055: goto 02d
      // 058: astore 5
      // 05a: aload 0
      // 05b: getfield io/sentry/Hub.options Lio/sentry/SentryOptions;
      // 05e: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 061: getstatic io/sentry/SentryLevel.WARNING Lio/sentry/SentryLevel;
      // 064: ldc_w "Failed to close the integration {}."
      // 067: bipush 2
      // 068: anewarray 4
      // 06b: dup
      // 06c: bipush 0
      // 06d: aload 4
      // 06f: aastore
      // 070: dup
      // 071: bipush 1
      // 072: aload 5
      // 074: aastore
      // 075: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
      // 07a: goto 02d
      // 07d: new io/sentry/Hub$$ExternalSyntheticLambda0
      // 080: astore 3
      // 081: aload 3
      // 082: invokespecial io/sentry/Hub$$ExternalSyntheticLambda0.<init> ()V
      // 085: aload 0
      // 086: aload 3
      // 087: invokevirtual io/sentry/Hub.configureScope (Lio/sentry/ScopeCallback;)V
      // 08a: aload 0
      // 08b: getfield io/sentry/Hub.options Lio/sentry/SentryOptions;
      // 08e: invokevirtual io/sentry/SentryOptions.getTransactionProfiler ()Lio/sentry/ITransactionProfiler;
      // 091: invokeinterface io/sentry/ITransactionProfiler.close ()V 1
      // 096: aload 0
      // 097: getfield io/sentry/Hub.options Lio/sentry/SentryOptions;
      // 09a: invokevirtual io/sentry/SentryOptions.getTransactionPerformanceCollector ()Lio/sentry/TransactionPerformanceCollector;
      // 09d: invokeinterface io/sentry/TransactionPerformanceCollector.close ()V 1
      // 0a2: aload 0
      // 0a3: getfield io/sentry/Hub.options Lio/sentry/SentryOptions;
      // 0a6: invokevirtual io/sentry/SentryOptions.getExecutorService ()Lio/sentry/ISentryExecutorService;
      // 0a9: astore 4
      // 0ab: iload 1
      // 0ac: ifeq 0c6
      // 0af: new io/sentry/Hub$$ExternalSyntheticLambda1
      // 0b2: astore 3
      // 0b3: aload 3
      // 0b4: aload 0
      // 0b5: aload 4
      // 0b7: invokespecial io/sentry/Hub$$ExternalSyntheticLambda1.<init> (Lio/sentry/Hub;Lio/sentry/ISentryExecutorService;)V
      // 0ba: aload 4
      // 0bc: aload 3
      // 0bd: invokeinterface io/sentry/ISentryExecutorService.submit (Ljava/lang/Runnable;)Ljava/util/concurrent/Future; 2
      // 0c2: pop
      // 0c3: goto 0d4
      // 0c6: aload 4
      // 0c8: aload 0
      // 0c9: getfield io/sentry/Hub.options Lio/sentry/SentryOptions;
      // 0cc: invokevirtual io/sentry/SentryOptions.getShutdownTimeoutMillis ()J
      // 0cf: invokeinterface io/sentry/ISentryExecutorService.close (J)V 3
      // 0d4: aload 0
      // 0d5: getfield io/sentry/Hub.stack Lio/sentry/Stack;
      // 0d8: invokevirtual io/sentry/Stack.peek ()Lio/sentry/Stack$StackItem;
      // 0db: invokevirtual io/sentry/Stack$StackItem.getClient ()Lio/sentry/ISentryClient;
      // 0de: iload 1
      // 0df: invokeinterface io/sentry/ISentryClient.close (Z)V 2
      // 0e4: goto 0fb
      // 0e7: astore 3
      // 0e8: aload 0
      // 0e9: getfield io/sentry/Hub.options Lio/sentry/SentryOptions;
      // 0ec: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
      // 0ef: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
      // 0f2: ldc_w "Error while closing the Hub."
      // 0f5: aload 3
      // 0f6: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;Ljava/lang/Throwable;)V 4
      // 0fb: aload 0
      // 0fc: bipush 0
      // 0fd: putfield io/sentry/Hub.isEnabled Z
      // 100: return
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void configureScope(ScopeCallback var1) {
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'configureScope' call is a no-op.");
      } else {
         try {
            var1.run(this.stack.peek().getScope());
         } catch (Throwable var3) {
            this.options.getLogger().log(SentryLevel.ERROR, "Error in the 'configureScope' callback.", var3);
            return;
         }
      }
   }

   @Override
   public TransactionContext continueTrace(String var1, List<String> var2) {
      PropagationContext var3 = PropagationContext.fromHeaders(this.getOptions().getLogger(), var1, var2);
      this.configureScope(new Hub$$ExternalSyntheticLambda2(var3));
      return this.options.isTracingEnabled() ? TransactionContext.fromPropagationContext(var3) : null;
   }

   @Override
   public void endSession() {
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'endSession' call is a no-op.");
      } else {
         Stack.StackItem var1 = this.stack.peek();
         Session var3 = var1.getScope().endSession();
         if (var3 != null) {
            Hint var2 = HintUtils.createWithTypeCheckHint(new SessionEndHint());
            var1.getClient().captureSession(var3, var2);
         }
      }
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void flush(long var1) {
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'flush' call is a no-op.");
      } else {
         try {
            this.stack.peek().getClient().flush(var1);
         } catch (Throwable var5) {
            this.options.getLogger().log(SentryLevel.ERROR, "Error in the 'client.flush'.", var5);
            return;
         }
      }
   }

   @Override
   public BaggageHeader getBaggage() {
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'getBaggage' call is a no-op.");
      } else {
         TracingUtils.TracingHeaders var1 = TracingUtils.trace(this, null, this.getSpan());
         if (var1 != null) {
            return var1.getBaggageHeader();
         }
      }

      return null;
   }

   @Override
   public Map<String, String> getDefaultTagsForMetrics() {
      if (!this.options.isEnableDefaultTagsForMetrics()) {
         return Collections.emptyMap();
      } else {
         HashMap var1 = new HashMap();
         String var2 = this.options.getRelease();
         if (var2 != null) {
            var1.put("release", var2);
         }

         var2 = this.options.getEnvironment();
         if (var2 != null) {
            var1.put("environment", var2);
         }

         var2 = this.stack.peek().getScope().getTransactionName();
         if (var2 != null) {
            var1.put("transaction", var2);
         }

         return Collections.unmodifiableMap(var1);
      }
   }

   @Override
   public SentryId getLastEventId() {
      return this.lastEventId;
   }

   @Override
   public LocalMetricsAggregator getLocalMetricsAggregator() {
      if (!this.options.isEnableSpanLocalMetricAggregation()) {
         return null;
      } else {
         ISpan var1 = this.getSpan();
         return var1 != null ? var1.getLocalMetricsAggregator() : null;
      }
   }

   @Override
   public IMetricsAggregator getMetricsAggregator() {
      return this.stack.peek().getClient().getMetricsAggregator();
   }

   @Override
   public SentryOptions getOptions() {
      return this.stack.peek().getOptions();
   }

   @Override
   public RateLimiter getRateLimiter() {
      return this.stack.peek().getClient().getRateLimiter();
   }

   @Override
   public ISpan getSpan() {
      ISpan var1;
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'getSpan' call is a no-op.");
         var1 = null;
      } else {
         var1 = this.stack.peek().getScope().getSpan();
      }

      return var1;
   }

   SpanContext getSpanContext(Throwable var1) {
      Objects.requireNonNull(var1, "throwable is required");
      var1 = ExceptionUtils.findRootCause(var1);
      Pair var3 = this.throwableToSpan.get(var1);
      if (var3 != null) {
         WeakReference var4 = (WeakReference)var3.getFirst();
         if (var4 != null) {
            ISpan var5 = (ISpan)var4.get();
            if (var5 != null) {
               return var5.getSpanContext();
            }
         }
      }

      return null;
   }

   @Override
   public SentryTraceHeader getTraceparent() {
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'getTraceparent' call is a no-op.");
      } else {
         TracingUtils.TracingHeaders var1 = TracingUtils.trace(this, null, this.getSpan());
         if (var1 != null) {
            return var1.getSentryTraceHeader();
         }
      }

      return null;
   }

   @Override
   public ITransaction getTransaction() {
      ITransaction var1;
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'getTransaction' call is a no-op.");
         var1 = null;
      } else {
         var1 = this.stack.peek().getScope().getTransaction();
      }

      return var1;
   }

   @Override
   public Boolean isCrashedLastRun() {
      return SentryCrashLastRunState.getInstance().isCrashedLastRun(this.options.getCacheDirPath(), this.options.isEnableAutoSessionTracking() ^ true);
   }

   @Override
   public boolean isEnabled() {
      return this.isEnabled;
   }

   @Override
   public boolean isHealthy() {
      return this.stack.peek().getClient().isHealthy();
   }

   @Override
   public MetricsApi metrics() {
      return this.metricsApi;
   }

   @Override
   public void popScope() {
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'popScope' call is a no-op.");
      } else {
         this.stack.pop();
      }
   }

   @Override
   public void pushScope() {
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'pushScope' call is a no-op.");
      } else {
         Stack.StackItem var1 = this.stack.peek();
         var1 = new Stack.StackItem(this.options, var1.getClient(), var1.getScope().clone());
         this.stack.push(var1);
      }
   }

   @Override
   public void removeExtra(String var1) {
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'removeExtra' call is a no-op.");
      } else if (var1 == null) {
         this.options.getLogger().log(SentryLevel.WARNING, "removeExtra called with null parameter.");
      } else {
         this.stack.peek().getScope().removeExtra(var1);
      }
   }

   @Override
   public void removeTag(String var1) {
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'removeTag' call is a no-op.");
      } else if (var1 == null) {
         this.options.getLogger().log(SentryLevel.WARNING, "removeTag called with null parameter.");
      } else {
         this.stack.peek().getScope().removeTag(var1);
      }
   }

   @Override
   public void reportFullyDisplayed() {
      if (this.options.isEnableTimeToFullDisplayTracing()) {
         this.options.getFullyDisplayedReporter().reportFullyDrawn();
      }
   }

   @Override
   public void setExtra(String var1, String var2) {
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'setExtra' call is a no-op.");
      } else if (var1 != null && var2 != null) {
         this.stack.peek().getScope().setExtra(var1, var2);
      } else {
         this.options.getLogger().log(SentryLevel.WARNING, "setExtra called with null parameter.");
      }
   }

   @Override
   public void setFingerprint(List<String> var1) {
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'setFingerprint' call is a no-op.");
      } else if (var1 == null) {
         this.options.getLogger().log(SentryLevel.WARNING, "setFingerprint called with null parameter.");
      } else {
         this.stack.peek().getScope().setFingerprint(var1);
      }
   }

   @Override
   public void setLevel(SentryLevel var1) {
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'setLevel' call is a no-op.");
      } else {
         this.stack.peek().getScope().setLevel(var1);
      }
   }

   @Override
   public void setSpanContext(Throwable var1, ISpan var2, String var3) {
      Objects.requireNonNull(var1, "throwable is required");
      Objects.requireNonNull(var2, "span is required");
      Objects.requireNonNull(var3, "transactionName is required");
      var1 = ExceptionUtils.findRootCause(var1);
      if (!this.throwableToSpan.containsKey(var1)) {
         this.throwableToSpan.put(var1, new Pair<>(new WeakReference<>(var2), var3));
      }
   }

   @Override
   public void setTag(String var1, String var2) {
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'setTag' call is a no-op.");
      } else if (var1 != null && var2 != null) {
         this.stack.peek().getScope().setTag(var1, var2);
      } else {
         this.options.getLogger().log(SentryLevel.WARNING, "setTag called with null parameter.");
      }
   }

   @Override
   public void setTransaction(String var1) {
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'setTransaction' call is a no-op.");
      } else if (var1 != null) {
         this.stack.peek().getScope().setTransaction(var1);
      } else {
         this.options.getLogger().log(SentryLevel.WARNING, "Transaction cannot be null");
      }
   }

   @Override
   public void setUser(User var1) {
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'setUser' call is a no-op.");
      } else {
         this.stack.peek().getScope().setUser(var1);
      }
   }

   @Override
   public void startSession() {
      if (!this.isEnabled()) {
         this.options.getLogger().log(SentryLevel.WARNING, "Instance is disabled and this 'startSession' call is a no-op.");
      } else {
         Stack.StackItem var1 = this.stack.peek();
         Scope.SessionPair var2 = var1.getScope().startSession();
         if (var2 != null) {
            if (var2.getPrevious() != null) {
               Hint var3 = HintUtils.createWithTypeCheckHint(new SessionEndHint());
               var1.getClient().captureSession(var2.getPrevious(), var3);
            }

            Hint var4 = HintUtils.createWithTypeCheckHint(new SessionStartHint());
            var1.getClient().captureSession(var2.getCurrent(), var4);
         } else {
            this.options.getLogger().log(SentryLevel.WARNING, "Session could not be started.");
         }
      }
   }

   @Override
   public ISpan startSpanForMetric(String var1, String var2) {
      ISpan var3 = this.getSpan();
      return var3 != null ? var3.startChild(var1, var2) : null;
   }

   @Override
   public ITransaction startTransaction(TransactionContext var1, TransactionOptions var2) {
      return this.createTransaction(var1, var2);
   }

   @Deprecated
   @Override
   public SentryTraceHeader traceHeaders() {
      return this.getTraceparent();
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void withScope(ScopeCallback var1) {
      if (!this.isEnabled()) {
         try {
            var1.run(NoOpScope.getInstance());
         } catch (Throwable var7) {
            this.options.getLogger().log(SentryLevel.ERROR, "Error in the 'withScope' callback.", var7);
            return;
         }
      } else {
         this.pushScope();

         label42:
         try {
            var1.run(this.stack.peek().getScope());
         } catch (Throwable var6) {
            this.options.getLogger().log(SentryLevel.ERROR, "Error in the 'withScope' callback.", var6);
            break label42;
         }

         this.popScope();
      }
   }
}
