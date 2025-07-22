package io.reactivex.internal.schedulers;

import io.reactivex.disposables.Disposable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

final class DisposeOnCancel implements Future<Object> {
   final Disposable upstream;

   DisposeOnCancel(Disposable var1) {
      this.upstream = var1;
   }

   @Override
   public boolean cancel(boolean var1) {
      this.upstream.dispose();
      return false;
   }

   @Override
   public Object get() throws InterruptedException, ExecutionException {
      return null;
   }

   @Override
   public Object get(long var1, TimeUnit var3) throws InterruptedException, ExecutionException, TimeoutException {
      return null;
   }

   @Override
   public boolean isCancelled() {
      return false;
   }

   @Override
   public boolean isDone() {
      return false;
   }
}
