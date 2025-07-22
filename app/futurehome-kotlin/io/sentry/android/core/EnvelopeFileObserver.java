package io.sentry.android.core;

import android.os.FileObserver;
import io.sentry.Hint;
import io.sentry.IEnvelopeSender;
import io.sentry.ILogger;
import io.sentry.SentryLevel;
import io.sentry.hints.ApplyScopeData;
import io.sentry.hints.Cached;
import io.sentry.hints.Flushable;
import io.sentry.hints.Resettable;
import io.sentry.hints.Retryable;
import io.sentry.hints.SubmissionResult;
import io.sentry.util.HintUtils;
import io.sentry.util.Objects;
import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

final class EnvelopeFileObserver extends FileObserver {
   private final IEnvelopeSender envelopeSender;
   private final long flushTimeoutMillis;
   private final ILogger logger;
   private final String rootPath;

   EnvelopeFileObserver(String var1, IEnvelopeSender var2, ILogger var3, long var4) {
      super(var1);
      this.rootPath = var1;
      this.envelopeSender = Objects.requireNonNull(var2, "Envelope sender is required.");
      this.logger = Objects.requireNonNull(var3, "Logger is required.");
      this.flushTimeoutMillis = var4;
   }

   public void onEvent(int var1, String var2) {
      if (var2 != null && var1 == 8) {
         this.logger.log(SentryLevel.DEBUG, "onEvent fired for EnvelopeFileObserver with event type %d on path: %s for file %s.", var1, this.rootPath, var2);
         Hint var3 = HintUtils.createWithTypeCheckHint(new EnvelopeFileObserver.CachedEnvelopeHint(this.flushTimeoutMillis, this.logger));
         IEnvelopeSender var5 = this.envelopeSender;
         StringBuilder var4 = new StringBuilder();
         var4.append(this.rootPath);
         var4.append(File.separator);
         var4.append(var2);
         var5.processEnvelopeFile(var4.toString(), var3);
      }
   }

   private static final class CachedEnvelopeHint implements Cached, Retryable, SubmissionResult, Flushable, ApplyScopeData, Resettable {
      private final long flushTimeoutMillis;
      private CountDownLatch latch;
      private final ILogger logger;
      boolean retry;
      boolean succeeded;

      public CachedEnvelopeHint(long var1, ILogger var3) {
         this.reset();
         this.flushTimeoutMillis = var1;
         this.logger = Objects.requireNonNull(var3, "ILogger is required.");
      }

      @Override
      public boolean isRetry() {
         return this.retry;
      }

      @Override
      public boolean isSuccess() {
         return this.succeeded;
      }

      @Override
      public void reset() {
         this.latch = new CountDownLatch(1);
         this.retry = false;
         this.succeeded = false;
      }

      @Override
      public void setResult(boolean var1) {
         this.succeeded = var1;
         this.latch.countDown();
      }

      @Override
      public void setRetry(boolean var1) {
         this.retry = var1;
      }

      @Override
      public boolean waitFlush() {
         try {
            return this.latch.await(this.flushTimeoutMillis, TimeUnit.MILLISECONDS);
         } catch (InterruptedException var3) {
            Thread.currentThread().interrupt();
            this.logger.log(SentryLevel.ERROR, "Exception while awaiting on lock.", var3);
            return false;
         }
      }
   }
}
