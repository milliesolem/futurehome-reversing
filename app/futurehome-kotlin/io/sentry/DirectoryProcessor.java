package io.sentry;

import io.sentry.hints.Cached;
import io.sentry.hints.Enqueable;
import io.sentry.hints.Flushable;
import io.sentry.hints.Retryable;
import io.sentry.hints.SubmissionResult;
import io.sentry.util.HintUtils;
import java.io.File;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

abstract class DirectoryProcessor {
   private static final long ENVELOPE_PROCESSING_DELAY = 100L;
   private final long flushTimeoutMillis;
   private final IHub hub;
   private final ILogger logger;
   private final Queue<String> processedEnvelopes;

   DirectoryProcessor(IHub var1, ILogger var2, long var3, int var5) {
      this.hub = var1;
      this.logger = var2;
      this.flushTimeoutMillis = var3;
      this.processedEnvelopes = SynchronizedQueue.synchronizedQueue(new CircularFifoQueue<>(var5));
   }

   protected abstract boolean isRelevantFileName(String var1);

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public void processDirectory(File var1) {
      try {
         this.logger.log(SentryLevel.DEBUG, "Processing dir. %s", var1.getAbsolutePath());
         if (!var1.exists()) {
            this.logger.log(SentryLevel.WARNING, "Directory '%s' doesn't exist. No cached events to send.", var1.getAbsolutePath());
            return;
         }
      } catch (Throwable var162) {
         this.logger.log(SentryLevel.ERROR, var162, "Failed processing '%s'", var1.getAbsolutePath());
         return;
      }

      try {
         if (!var1.isDirectory()) {
            this.logger.log(SentryLevel.ERROR, "Cache dir %s is not a directory.", var1.getAbsolutePath());
            return;
         }
      } catch (Throwable var163) {
         this.logger.log(SentryLevel.ERROR, var163, "Failed processing '%s'", var1.getAbsolutePath());
         return;
      }

      File[] var4;
      try {
         var4 = var1.listFiles();
      } catch (Throwable var161) {
         this.logger.log(SentryLevel.ERROR, var161, "Failed processing '%s'", var1.getAbsolutePath());
         return;
      }

      if (var4 == null) {
         try {
            this.logger.log(SentryLevel.ERROR, "Cache dir %s is null.", var1.getAbsolutePath());
            return;
         } catch (Throwable var160) {
            this.logger.log(SentryLevel.ERROR, var160, "Failed processing '%s'", var1.getAbsolutePath());
            return;
         }
      } else {
         File[] var6;
         SentryLevel var7;
         ILogger var165;
         try {
            DirectoryProcessor$$ExternalSyntheticLambda0 var5 = new DirectoryProcessor$$ExternalSyntheticLambda0(this);
            var6 = var1.listFiles(var5);
            var165 = this.logger;
            var7 = SentryLevel.DEBUG;
         } catch (Throwable var159) {
            this.logger.log(SentryLevel.ERROR, var159, "Failed processing '%s'", var1.getAbsolutePath());
            return;
         }

         int var2;
         if (var6 != null) {
            try {
               var2 = var6.length;
            } catch (Throwable var158) {
               this.logger.log(SentryLevel.ERROR, var158, "Failed processing '%s'", var1.getAbsolutePath());
               return;
            }
         } else {
            var2 = 0;
         }

         int var3;
         try {
            var165.log(var7, "Processing %d items from cache dir %s", var2, var1.getAbsolutePath());
            var3 = var4.length;
         } catch (Throwable var157) {
            this.logger.log(SentryLevel.ERROR, var157, "Failed processing '%s'", var1.getAbsolutePath());
            return;
         }

         for (int var164 = 0; var164 < var3; var164++) {
            File var166 = var4[var164];

            try {
               if (!var166.isFile()) {
                  this.logger.log(SentryLevel.DEBUG, "File %s is not a File.", var166.getAbsolutePath());
                  continue;
               }
            } catch (Throwable var156) {
               this.logger.log(SentryLevel.ERROR, var156, "Failed processing '%s'", var1.getAbsolutePath());
               break;
            }

            try {
               var167 = var166.getAbsolutePath();
               if (this.processedEnvelopes.contains(var167)) {
                  this.logger.log(SentryLevel.DEBUG, "File '%s' has already been processed so it will not be processed again.", var167);
                  continue;
               }
            } catch (Throwable var155) {
               this.logger.log(SentryLevel.ERROR, var155, "Failed processing '%s'", var1.getAbsolutePath());
               break;
            }

            try {
               var168 = this.hub.getRateLimiter();
            } catch (Throwable var154) {
               this.logger.log(SentryLevel.ERROR, var154, "Failed processing '%s'", var1.getAbsolutePath());
               break;
            }

            if (var168 != null) {
               try {
                  if (var168.isActiveForCategory(DataCategory.All)) {
                     this.logger.log(SentryLevel.INFO, "DirectoryProcessor, rate limiting active.");
                     return;
                  }
               } catch (Throwable var153) {
                  this.logger.log(SentryLevel.ERROR, var153, "Failed processing '%s'", var1.getAbsolutePath());
                  break;
               }
            }

            try {
               this.logger.log(SentryLevel.DEBUG, "Processing file: %s", var167);
               DirectoryProcessor.SendCachedEnvelopeHint var169 = new DirectoryProcessor.SendCachedEnvelopeHint(
                  this.flushTimeoutMillis, this.logger, var167, this.processedEnvelopes
               );
               this.processFile(var166, HintUtils.createWithTypeCheckHint(var169));
               Thread.sleep(100L);
            } catch (Throwable var152) {
               this.logger.log(SentryLevel.ERROR, var152, "Failed processing '%s'", var1.getAbsolutePath());
               break;
            }
         }
      }
   }

   protected abstract void processFile(File var1, Hint var2);

   private static final class SendCachedEnvelopeHint implements Cached, Retryable, SubmissionResult, Flushable, Enqueable {
      private final String filePath;
      private final long flushTimeoutMillis;
      private final CountDownLatch latch;
      private final ILogger logger;
      private final Queue<String> processedEnvelopes;
      boolean retry = false;
      boolean succeeded = false;

      public SendCachedEnvelopeHint(long var1, ILogger var3, String var4, Queue<String> var5) {
         this.flushTimeoutMillis = var1;
         this.filePath = var4;
         this.processedEnvelopes = var5;
         this.latch = new CountDownLatch(1);
         this.logger = var3;
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
      public void markEnqueued() {
         this.processedEnvelopes.add(this.filePath);
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
