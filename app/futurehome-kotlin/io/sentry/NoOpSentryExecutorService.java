package io.sentry;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

final class NoOpSentryExecutorService implements ISentryExecutorService {
   private static final NoOpSentryExecutorService instance = new NoOpSentryExecutorService();

   private NoOpSentryExecutorService() {
   }

   public static ISentryExecutorService getInstance() {
      return instance;
   }

   @Override
   public void close(long var1) {
   }

   @Override
   public boolean isClosed() {
      return false;
   }

   @Override
   public Future<?> schedule(Runnable var1, long var2) {
      return new FutureTask(new NoOpSentryExecutorService$$ExternalSyntheticLambda0());
   }

   @Override
   public Future<?> submit(Runnable var1) {
      return new FutureTask(new NoOpSentryExecutorService$$ExternalSyntheticLambda2());
   }

   @Override
   public <T> Future<T> submit(Callable<T> var1) {
      return new FutureTask<>(new NoOpSentryExecutorService$$ExternalSyntheticLambda1());
   }
}
