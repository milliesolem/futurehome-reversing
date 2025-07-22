package io.reactivex.internal.observers;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

public final class FutureObserver<T> extends CountDownLatch implements Observer<T>, Future<T>, Disposable {
   Throwable error;
   final AtomicReference<Disposable> upstream = new AtomicReference<>();
   T value;

   public FutureObserver() {
      super(1);
   }

   @Override
   public boolean cancel(boolean var1) {
      while (true) {
         Disposable var2 = this.upstream.get();
         if (var2 != this && var2 != DisposableHelper.DISPOSED) {
            if (!ExternalSyntheticBackportWithForwarding0.m(this.upstream, var2, DisposableHelper.DISPOSED)) {
               continue;
            }

            if (var2 != null) {
               var2.dispose();
            }

            this.countDown();
            return true;
         }

         return false;
      }
   }

   @Override
   public void dispose() {
   }

   @Override
   public T get() throws InterruptedException, ExecutionException {
      if (this.getCount() != 0L) {
         BlockingHelper.verifyNonBlocking();
         this.await();
      }

      if (!this.isCancelled()) {
         Throwable var1 = this.error;
         if (var1 == null) {
            return this.value;
         } else {
            throw new ExecutionException(var1);
         }
      } else {
         throw new CancellationException();
      }
   }

   @Override
   public T get(long var1, TimeUnit var3) throws InterruptedException, ExecutionException, TimeoutException {
      if (this.getCount() != 0L) {
         BlockingHelper.verifyNonBlocking();
         if (!this.await(var1, var3)) {
            throw new TimeoutException(ExceptionHelper.timeoutMessage(var1, var3));
         }
      }

      if (!this.isCancelled()) {
         Throwable var4 = this.error;
         if (var4 == null) {
            return this.value;
         } else {
            throw new ExecutionException(var4);
         }
      } else {
         throw new CancellationException();
      }
   }

   @Override
   public boolean isCancelled() {
      return DisposableHelper.isDisposed(this.upstream.get());
   }

   @Override
   public boolean isDisposed() {
      return this.isDone();
   }

   @Override
   public boolean isDone() {
      boolean var1;
      if (this.getCount() == 0L) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public void onComplete() {
      if (this.value == null) {
         this.onError(new NoSuchElementException("The source is empty"));
      } else {
         while (true) {
            Disposable var1 = this.upstream.get();
            if (var1 == this || var1 == DisposableHelper.DISPOSED) {
               break;
            }

            if (ExternalSyntheticBackportWithForwarding0.m(this.upstream, var1, this)) {
               this.countDown();
               break;
            }
         }
      }
   }

   @Override
   public void onError(Throwable var1) {
      if (this.error != null) {
         RxJavaPlugins.onError(var1);
      } else {
         this.error = var1;

         Disposable var2;
         do {
            var2 = this.upstream.get();
            if (var2 == this || var2 == DisposableHelper.DISPOSED) {
               RxJavaPlugins.onError(var1);
               return;
            }
         } while (!ExternalSyntheticBackportWithForwarding0.m(this.upstream, var2, this));

         this.countDown();
      }
   }

   @Override
   public void onNext(T var1) {
      if (this.value != null) {
         this.upstream.get().dispose();
         this.onError(new IndexOutOfBoundsException("More than one element received"));
      } else {
         this.value = (T)var1;
      }
   }

   @Override
   public void onSubscribe(Disposable var1) {
      DisposableHelper.setOnce(this.upstream, var1);
   }
}
