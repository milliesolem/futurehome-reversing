package io.reactivex.internal.subscribers;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
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
import org.reactivestreams.Subscription;

public final class FutureSubscriber<T> extends CountDownLatch implements FlowableSubscriber<T>, Future<T>, Subscription {
   Throwable error;
   final AtomicReference<Subscription> upstream = new AtomicReference<>();
   T value;

   public FutureSubscriber() {
      super(1);
   }

   public void cancel() {
   }

   @Override
   public boolean cancel(boolean var1) {
      while (true) {
         Subscription var2 = this.upstream.get();
         if (var2 != this && var2 != SubscriptionHelper.CANCELLED) {
            if (!ExternalSyntheticBackportWithForwarding0.m(this.upstream, var2, SubscriptionHelper.CANCELLED)) {
               continue;
            }

            if (var2 != null) {
               var2.cancel();
            }

            this.countDown();
            return true;
         }

         return false;
      }
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
      boolean var1;
      if (this.upstream.get() == SubscriptionHelper.CANCELLED) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
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

   public void onComplete() {
      if (this.value == null) {
         this.onError(new NoSuchElementException("The source is empty"));
      } else {
         while (true) {
            Subscription var1 = this.upstream.get();
            if (var1 == this || var1 == SubscriptionHelper.CANCELLED) {
               break;
            }

            if (ExternalSyntheticBackportWithForwarding0.m(this.upstream, var1, this)) {
               this.countDown();
               break;
            }
         }
      }
   }

   public void onError(Throwable var1) {
      while (true) {
         Subscription var2 = this.upstream.get();
         if (var2 != this && var2 != SubscriptionHelper.CANCELLED) {
            this.error = var1;
            if (!ExternalSyntheticBackportWithForwarding0.m(this.upstream, var2, this)) {
               continue;
            }

            this.countDown();
            return;
         }

         RxJavaPlugins.onError(var1);
         return;
      }
   }

   public void onNext(T var1) {
      if (this.value != null) {
         this.upstream.get().cancel();
         this.onError(new IndexOutOfBoundsException("More than one element received"));
      } else {
         this.value = (T)var1;
      }
   }

   @Override
   public void onSubscribe(Subscription var1) {
      SubscriptionHelper.setOnce(this.upstream, var1, Long.MAX_VALUE);
   }

   public void request(long var1) {
   }
}
