package io.sentry;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

public interface ISentryExecutorService {
   void close(long var1);

   boolean isClosed();

   Future<?> schedule(Runnable var1, long var2) throws RejectedExecutionException;

   Future<?> submit(Runnable var1) throws RejectedExecutionException;

   <T> Future<T> submit(Callable<T> var1) throws RejectedExecutionException;
}
