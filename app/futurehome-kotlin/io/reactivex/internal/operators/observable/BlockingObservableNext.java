package io.reactivex.internal.operators.observable;

import io.reactivex.Notification;
import io.reactivex.ObservableSource;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public final class BlockingObservableNext<T> implements Iterable<T> {
   final ObservableSource<T> source;

   public BlockingObservableNext(ObservableSource<T> var1) {
      this.source = var1;
   }

   @Override
   public Iterator<T> iterator() {
      BlockingObservableNext.NextObserver var1 = new BlockingObservableNext.NextObserver();
      return new BlockingObservableNext.NextIterator<>(this.source, var1);
   }

   static final class NextIterator<T> implements Iterator<T> {
      private Throwable error;
      private boolean hasNext = true;
      private boolean isNextConsumed = true;
      private final ObservableSource<T> items;
      private T next;
      private final BlockingObservableNext.NextObserver<T> observer;
      private boolean started;

      NextIterator(ObservableSource<T> var1, BlockingObservableNext.NextObserver<T> var2) {
         this.items = var1;
         this.observer = var2;
      }

      private boolean moveToNext() {
         if (!this.started) {
            this.started = true;
            this.observer.setWaiting();
            new ObservableMaterialize<>(this.items).subscribe(this.observer);
         }

         Notification var1;
         try {
            var1 = this.observer.takeNext();
         } catch (InterruptedException var2) {
            this.observer.dispose();
            this.error = var2;
            throw ExceptionHelper.wrapOrThrow(var2);
         }

         if (var1.isOnNext()) {
            this.isNextConsumed = false;
            this.next = (T)var1.getValue();
            return true;
         } else {
            this.hasNext = false;
            if (var1.isOnComplete()) {
               return false;
            } else {
               Throwable var3 = var1.getError();
               this.error = var3;
               throw ExceptionHelper.wrapOrThrow(var3);
            }
         }
      }

      @Override
      public boolean hasNext() {
         Throwable var3 = this.error;
         if (var3 == null) {
            boolean var2 = this.hasNext;
            boolean var1 = false;
            if (!var2) {
               return false;
            } else {
               if (!this.isNextConsumed || this.moveToNext()) {
                  var1 = true;
               }

               return var1;
            }
         } else {
            throw ExceptionHelper.wrapOrThrow(var3);
         }
      }

      @Override
      public T next() {
         Throwable var1 = this.error;
         if (var1 == null) {
            if (this.hasNext()) {
               this.isNextConsumed = true;
               return this.next;
            } else {
               throw new NoSuchElementException("No more elements");
            }
         } else {
            throw ExceptionHelper.wrapOrThrow(var1);
         }
      }

      @Override
      public void remove() {
         throw new UnsupportedOperationException("Read only iterator");
      }
   }

   static final class NextObserver<T> extends DisposableObserver<Notification<T>> {
      private final BlockingQueue<Notification<T>> buf = new ArrayBlockingQueue<>(1);
      final AtomicInteger waiting = new AtomicInteger();

      @Override
      public void onComplete() {
      }

      @Override
      public void onError(Throwable var1) {
         RxJavaPlugins.onError(var1);
      }

      public void onNext(Notification<T> var1) {
         Notification var2 = var1;
         if (this.waiting.getAndSet(0) != 1) {
            if (var1.isOnNext()) {
               return;
            }

            var2 = var1;
         }

         while (!this.buf.offer(var2)) {
            var1 = this.buf.poll();
            if (var1 != null && !var1.isOnNext()) {
               var2 = var1;
            }
         }
      }

      void setWaiting() {
         this.waiting.set(1);
      }

      public Notification<T> takeNext() throws InterruptedException {
         this.setWaiting();
         BlockingHelper.verifyNonBlocking();
         return this.buf.take();
      }
   }
}
