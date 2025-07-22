package io.reactivex.internal.observers;

import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class BlockingMultiObserver<T> extends CountDownLatch implements SingleObserver<T>, CompletableObserver, MaybeObserver<T> {
   volatile boolean cancelled;
   Throwable error;
   Disposable upstream;
   T value;

   public BlockingMultiObserver() {
      super(1);
   }

   public boolean blockingAwait(long var1, TimeUnit var3) {
      if (this.getCount() != 0L) {
         try {
            BlockingHelper.verifyNonBlocking();
            if (!this.await(var1, var3)) {
               this.dispose();
               return false;
            }
         } catch (InterruptedException var4) {
            this.dispose();
            throw ExceptionHelper.wrapOrThrow(var4);
         }
      }

      Throwable var5 = this.error;
      if (var5 == null) {
         return true;
      } else {
         throw ExceptionHelper.wrapOrThrow(var5);
      }
   }

   public T blockingGet() {
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

   public T blockingGet(T var1) {
      if (this.getCount() != 0L) {
         try {
            BlockingHelper.verifyNonBlocking();
            this.await();
         } catch (InterruptedException var3) {
            this.dispose();
            throw ExceptionHelper.wrapOrThrow(var3);
         }
      }

      Throwable var2 = this.error;
      if (var2 == null) {
         var2 = this.value;
         if (var2 != null) {
            var1 = var2;
         }

         return (T)var1;
      } else {
         throw ExceptionHelper.wrapOrThrow(var2);
      }
   }

   public Throwable blockingGetError() {
      if (this.getCount() != 0L) {
         try {
            BlockingHelper.verifyNonBlocking();
            this.await();
         } catch (InterruptedException var2) {
            this.dispose();
            return var2;
         }
      }

      return this.error;
   }

   public Throwable blockingGetError(long var1, TimeUnit var3) {
      if (this.getCount() != 0L) {
         try {
            BlockingHelper.verifyNonBlocking();
            if (!this.await(var1, var3)) {
               this.dispose();
               TimeoutException var4 = new TimeoutException(ExceptionHelper.timeoutMessage(var1, var3));
               throw ExceptionHelper.wrapOrThrow(var4);
            }
         } catch (InterruptedException var5) {
            this.dispose();
            throw ExceptionHelper.wrapOrThrow(var5);
         }
      }

      return this.error;
   }

   void dispose() {
      this.cancelled = true;
      Disposable var1 = this.upstream;
      if (var1 != null) {
         var1.dispose();
      }
   }

   @Override
   public void onComplete() {
      this.countDown();
   }

   @Override
   public void onError(Throwable var1) {
      this.error = var1;
      this.countDown();
   }

   @Override
   public void onSubscribe(Disposable var1) {
      this.upstream = var1;
      if (this.cancelled) {
         var1.dispose();
      }
   }

   @Override
   public void onSuccess(T var1) {
      this.value = (T)var1;
      this.countDown();
   }
}
