package io.reactivex.internal.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import java.util.concurrent.CountDownLatch;

public abstract class BlockingBaseObserver<T> extends CountDownLatch implements Observer<T>, Disposable {
   volatile boolean cancelled;
   Throwable error;
   Disposable upstream;
   T value;

   public BlockingBaseObserver() {
      super(1);
   }

   public final T blockingGet() {
      if (this.getCount() != 0L) {
         try {
            BlockingHelper.verifyNonBlocking();
            this.await();
         } catch (InterruptedException var2) {
            this.dispose();
            throw ExceptionHelper.wrapOrThrow(var2);
         }
      }

      Throwable var1 = this.error;
      if (var1 == null) {
         return this.value;
      } else {
         throw ExceptionHelper.wrapOrThrow(var1);
      }
   }

   @Override
   public final void dispose() {
      this.cancelled = true;
      Disposable var1 = this.upstream;
      if (var1 != null) {
         var1.dispose();
      }
   }

   @Override
   public final boolean isDisposed() {
      return this.cancelled;
   }

   @Override
   public final void onComplete() {
      this.countDown();
   }

   @Override
   public final void onSubscribe(Disposable var1) {
      this.upstream = var1;
      if (this.cancelled) {
         var1.dispose();
      }
   }
}
