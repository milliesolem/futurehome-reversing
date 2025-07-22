package io.sentry.transport;

import io.sentry.DateUtils;
import io.sentry.Hint;
import io.sentry.ILogger;
import io.sentry.RequestDetails;
import io.sentry.SentryDate;
import io.sentry.SentryDateProvider;
import io.sentry.SentryEnvelope;
import io.sentry.SentryLevel;
import io.sentry.SentryOptions;
import io.sentry.UncaughtExceptionHandlerIntegration;
import io.sentry.cache.IEnvelopeCache;
import io.sentry.clientreport.DiscardReason;
import io.sentry.hints.Cached;
import io.sentry.hints.DiskFlushNotification;
import io.sentry.hints.Enqueable;
import io.sentry.hints.Retryable;
import io.sentry.hints.SubmissionResult;
import io.sentry.util.HintUtils;
import io.sentry.util.Objects;
import java.io.IOException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public final class AsyncHttpTransport implements ITransport {
   private final HttpConnection connection;
   private volatile Runnable currentRunnable = null;
   private final IEnvelopeCache envelopeCache;
   private final QueuedThreadPoolExecutor executor;
   private final SentryOptions options;
   private final RateLimiter rateLimiter;
   private final ITransportGate transportGate;

   public AsyncHttpTransport(SentryOptions var1, RateLimiter var2, ITransportGate var3, RequestDetails var4) {
      this(
         initExecutor(var1.getMaxQueueSize(), var1.getEnvelopeDiskCache(), var1.getLogger(), var1.getDateProvider()),
         var1,
         var2,
         var3,
         new HttpConnection(var1, var4, var2)
      );
   }

   public AsyncHttpTransport(QueuedThreadPoolExecutor var1, SentryOptions var2, RateLimiter var3, ITransportGate var4, HttpConnection var5) {
      this.executor = Objects.requireNonNull(var1, "executor is required");
      this.envelopeCache = Objects.requireNonNull(var2.getEnvelopeDiskCache(), "envelopeCache is required");
      this.options = Objects.requireNonNull(var2, "options is required");
      this.rateLimiter = Objects.requireNonNull(var3, "rateLimiter is required");
      this.transportGate = Objects.requireNonNull(var4, "transportGate is required");
      this.connection = Objects.requireNonNull(var5, "httpConnection is required");
   }

   private static QueuedThreadPoolExecutor initExecutor(int var0, IEnvelopeCache var1, ILogger var2, SentryDateProvider var3) {
      AsyncHttpTransport$$ExternalSyntheticLambda2 var4 = new AsyncHttpTransport$$ExternalSyntheticLambda2(var1, var2);
      return new QueuedThreadPoolExecutor(1, var0, new AsyncHttpTransport.AsyncConnectionThreadFactory(), var4, var2, var3);
   }

   private static void markHintWhenSendingFailed(Hint var0, boolean var1) {
      HintUtils.runIfHasType(var0, SubmissionResult.class, new AsyncHttpTransport$$ExternalSyntheticLambda0());
      HintUtils.runIfHasType(var0, Retryable.class, new AsyncHttpTransport$$ExternalSyntheticLambda1(var1));
   }

   @Override
   public void close() throws IOException {
      this.close(false);
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   @Override
   public void close(boolean var1) throws IOException {
      this.rateLimiter.close();
      this.executor.shutdown();
      this.options.getLogger().log(SentryLevel.DEBUG, "Shutting down");
      long var2;
      if (var1) {
         var2 = 0L;
      } else {
         try {
            var2 = this.options.getFlushTimeoutMillis();
         } catch (InterruptedException var8) {
            this.options.getLogger().log(SentryLevel.DEBUG, "Thread interrupted while closing the connection.");
            Thread.currentThread().interrupt();
            return;
         }
      }

      try {
         if (!this.executor.awaitTermination(var2, TimeUnit.MILLISECONDS)) {
            ILogger var6 = this.options.getLogger();
            SentryLevel var5 = SentryLevel.WARNING;
            StringBuilder var4 = new StringBuilder("Failed to shutdown the async connection async sender  within ");
            var4.append(var2);
            var4.append(" ms. Trying to force it now.");
            var6.log(var5, var4.toString());
            this.executor.shutdownNow();
            if (this.currentRunnable != null) {
               this.executor.getRejectedExecutionHandler().rejectedExecution(this.currentRunnable, this.executor);
            }
         }
      } catch (InterruptedException var7) {
         this.options.getLogger().log(SentryLevel.DEBUG, "Thread interrupted while closing the connection.");
         Thread.currentThread().interrupt();
      }
   }

   @Override
   public void flush(long var1) {
      this.executor.waitTillIdle(var1);
   }

   @Override
   public RateLimiter getRateLimiter() {
      return this.rateLimiter;
   }

   @Override
   public boolean isHealthy() {
      boolean var1 = this.rateLimiter.isAnyRateLimitActive();
      boolean var2 = this.executor.didRejectRecently();
      if (!var1 && !var2) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public void send(SentryEnvelope var1, Hint var2) throws IOException {
      Object var5 = this.envelopeCache;
      boolean var4 = HintUtils.hasType(var2, Cached.class);
      boolean var3 = false;
      if (var4) {
         var5 = NoOpEnvelopeCache.getInstance();
         this.options.getLogger().log(SentryLevel.DEBUG, "Captured Envelope is already cached");
         var3 = true;
      }

      SentryEnvelope var6 = this.rateLimiter.filter(var1, var2);
      if (var6 == null) {
         if (var3) {
            this.envelopeCache.discard(var1);
         }
      } else {
         var1 = var6;
         if (HintUtils.hasType(var2, UncaughtExceptionHandlerIntegration.UncaughtExceptionHint.class)) {
            var1 = this.options.getClientReportRecorder().attachReportToEnvelope(var6);
         }

         var5 = this.executor.submit(new AsyncHttpTransport.EnvelopeSender(this, var1, var2, (IEnvelopeCache)var5));
         if (var5 != null && var5.isCancelled()) {
            this.options.getClientReportRecorder().recordLostEnvelope(DiscardReason.QUEUE_OVERFLOW, var1);
         } else {
            HintUtils.runIfHasType(var2, Enqueable.class, new AsyncHttpTransport$$ExternalSyntheticLambda3(this));
         }
      }
   }

   private static final class AsyncConnectionThreadFactory implements ThreadFactory {
      private int cnt;

      private AsyncConnectionThreadFactory() {
      }

      @Override
      public Thread newThread(Runnable var1) {
         StringBuilder var3 = new StringBuilder("SentryAsyncConnection-");
         int var2 = this.cnt++;
         var3.append(var2);
         Thread var4 = new Thread(var1, var3.toString());
         var4.setDaemon(true);
         return var4;
      }
   }

   private final class EnvelopeSender implements Runnable {
      private final SentryEnvelope envelope;
      private final IEnvelopeCache envelopeCache;
      private final TransportResult failedResult;
      private final Hint hint;
      final AsyncHttpTransport this$0;

      EnvelopeSender(AsyncHttpTransport var1, SentryEnvelope var2, Hint var3, IEnvelopeCache var4) {
         this.this$0 = var1;
         this.failedResult = TransportResult.error();
         this.envelope = Objects.requireNonNull(var2, "Envelope is required.");
         this.hint = var3;
         this.envelopeCache = Objects.requireNonNull(var4, "EnvelopeCache is required.");
      }

      private TransportResult flush() {
         TransportResult var1 = this.failedResult;
         this.envelope.getHeader().setSentAt(null);
         this.envelopeCache.store(this.envelope, this.hint);
         HintUtils.runIfHasType(this.hint, DiskFlushNotification.class, new AsyncHttpTransport$EnvelopeSender$$ExternalSyntheticLambda0(this));
         if (this.this$0.transportGate.isConnected()) {
            SentryEnvelope var2 = this.this$0.options.getClientReportRecorder().attachReportToEnvelope(this.envelope);

            try {
               SentryDate var6 = this.this$0.options.getDateProvider().now();
               var2.getHeader().setSentAt(DateUtils.nanosToDate(var6.nanoTimestamp()));
               var1 = this.this$0.connection.send(var2);
               if (!var1.isSuccess()) {
                  StringBuilder var3 = new StringBuilder("The transport failed to send the envelope with response code ");
                  var3.append(var1.getResponseCode());
                  String var9 = var3.toString();
                  this.this$0.options.getLogger().log(SentryLevel.ERROR, var9);
                  if (var1.getResponseCode() >= 400 && var1.getResponseCode() != 429) {
                     Hint var4 = this.hint;
                     AsyncHttpTransport$EnvelopeSender$$ExternalSyntheticLambda1 var7 = new AsyncHttpTransport$EnvelopeSender$$ExternalSyntheticLambda1(
                        this, var2
                     );
                     HintUtils.runIfDoesNotHaveType(var4, Retryable.class, var7);
                  }

                  IllegalStateException var8 = new IllegalStateException(var9);
                  throw var8;
               }

               this.envelopeCache.discard(this.envelope);
            } catch (IOException var5) {
               HintUtils.runIfHasType(
                  this.hint,
                  Retryable.class,
                  new AsyncHttpTransport$EnvelopeSender$$ExternalSyntheticLambda2(),
                  new AsyncHttpTransport$EnvelopeSender$$ExternalSyntheticLambda3(this, var2)
               );
               throw new IllegalStateException("Sending the event failed.", var5);
            }
         } else {
            HintUtils.runIfHasType(
               this.hint,
               Retryable.class,
               new AsyncHttpTransport$EnvelopeSender$$ExternalSyntheticLambda4(),
               new AsyncHttpTransport$EnvelopeSender$$ExternalSyntheticLambda5(this)
            );
         }

         return var1;
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
         // 01: getfield io/sentry/transport/AsyncHttpTransport$EnvelopeSender.this$0 Lio/sentry/transport/AsyncHttpTransport;
         // 04: aload 0
         // 05: invokestatic io/sentry/transport/AsyncHttpTransport.access$102 (Lio/sentry/transport/AsyncHttpTransport;Ljava/lang/Runnable;)Ljava/lang/Runnable;
         // 08: pop
         // 09: aload 0
         // 0a: getfield io/sentry/transport/AsyncHttpTransport$EnvelopeSender.failedResult Lio/sentry/transport/TransportResult;
         // 0d: astore 1
         // 0e: aload 0
         // 0f: invokespecial io/sentry/transport/AsyncHttpTransport$EnvelopeSender.flush ()Lio/sentry/transport/TransportResult;
         // 12: astore 2
         // 13: aload 2
         // 14: astore 1
         // 15: aload 0
         // 16: getfield io/sentry/transport/AsyncHttpTransport$EnvelopeSender.this$0 Lio/sentry/transport/AsyncHttpTransport;
         // 19: invokestatic io/sentry/transport/AsyncHttpTransport.access$200 (Lio/sentry/transport/AsyncHttpTransport;)Lio/sentry/SentryOptions;
         // 1c: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
         // 1f: getstatic io/sentry/SentryLevel.DEBUG Lio/sentry/SentryLevel;
         // 22: ldc_w "Envelope flushed"
         // 25: bipush 0
         // 26: anewarray 4
         // 29: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/String;[Ljava/lang/Object;)V 4
         // 2e: aload 0
         // 2f: getfield io/sentry/transport/AsyncHttpTransport$EnvelopeSender.hint Lio/sentry/Hint;
         // 32: ldc_w io/sentry/hints/SubmissionResult
         // 35: new io/sentry/transport/AsyncHttpTransport$EnvelopeSender$$ExternalSyntheticLambda6
         // 38: dup
         // 39: aload 0
         // 3a: aload 2
         // 3b: invokespecial io/sentry/transport/AsyncHttpTransport$EnvelopeSender$$ExternalSyntheticLambda6.<init> (Lio/sentry/transport/AsyncHttpTransport$EnvelopeSender;Lio/sentry/transport/TransportResult;)V
         // 3e: invokestatic io/sentry/util/HintUtils.runIfHasType (Lio/sentry/Hint;Ljava/lang/Class;Lio/sentry/util/HintUtils$SentryConsumer;)V
         // 41: aload 0
         // 42: getfield io/sentry/transport/AsyncHttpTransport$EnvelopeSender.this$0 Lio/sentry/transport/AsyncHttpTransport;
         // 45: aconst_null
         // 46: invokestatic io/sentry/transport/AsyncHttpTransport.access$102 (Lio/sentry/transport/AsyncHttpTransport;Ljava/lang/Runnable;)Ljava/lang/Runnable;
         // 49: pop
         // 4a: return
         // 4b: astore 2
         // 4c: aload 0
         // 4d: getfield io/sentry/transport/AsyncHttpTransport$EnvelopeSender.this$0 Lio/sentry/transport/AsyncHttpTransport;
         // 50: invokestatic io/sentry/transport/AsyncHttpTransport.access$200 (Lio/sentry/transport/AsyncHttpTransport;)Lio/sentry/SentryOptions;
         // 53: invokevirtual io/sentry/SentryOptions.getLogger ()Lio/sentry/ILogger;
         // 56: getstatic io/sentry/SentryLevel.ERROR Lio/sentry/SentryLevel;
         // 59: aload 2
         // 5a: ldc_w "Envelope submission failed"
         // 5d: bipush 0
         // 5e: anewarray 4
         // 61: invokeinterface io/sentry/ILogger.log (Lio/sentry/SentryLevel;Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V 5
         // 66: aload 2
         // 67: athrow
         // 68: astore 2
         // 69: aload 0
         // 6a: getfield io/sentry/transport/AsyncHttpTransport$EnvelopeSender.hint Lio/sentry/Hint;
         // 6d: ldc_w io/sentry/hints/SubmissionResult
         // 70: new io/sentry/transport/AsyncHttpTransport$EnvelopeSender$$ExternalSyntheticLambda6
         // 73: dup
         // 74: aload 0
         // 75: aload 1
         // 76: invokespecial io/sentry/transport/AsyncHttpTransport$EnvelopeSender$$ExternalSyntheticLambda6.<init> (Lio/sentry/transport/AsyncHttpTransport$EnvelopeSender;Lio/sentry/transport/TransportResult;)V
         // 79: invokestatic io/sentry/util/HintUtils.runIfHasType (Lio/sentry/Hint;Ljava/lang/Class;Lio/sentry/util/HintUtils$SentryConsumer;)V
         // 7c: aload 0
         // 7d: getfield io/sentry/transport/AsyncHttpTransport$EnvelopeSender.this$0 Lio/sentry/transport/AsyncHttpTransport;
         // 80: aconst_null
         // 81: invokestatic io/sentry/transport/AsyncHttpTransport.access$102 (Lio/sentry/transport/AsyncHttpTransport;Ljava/lang/Runnable;)Ljava/lang/Runnable;
         // 84: pop
         // 85: aload 2
         // 86: athrow
      }
   }
}
