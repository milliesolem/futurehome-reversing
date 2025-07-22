package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.Notification;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subscribers.DisposableSubscriber;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;

public final class BlockingFlowableNext<T> implements Iterable<T> {
   final Publisher<? extends T> source;

   public BlockingFlowableNext(Publisher<? extends T> var1) {
      this.source = var1;
   }

   @Override
   public Iterator<T> iterator() {
      BlockingFlowableNext.NextSubscriber var1 = new BlockingFlowableNext.NextSubscriber();
      return new BlockingFlowableNext.NextIterator<>(this.source, var1);
   }

   static final class NextIterator<T> implements Iterator<T> {
      private Throwable error;
      private boolean hasNext = true;
      private boolean isNextConsumed = true;
      private final Publisher<? extends T> items;
      private T next;
      private boolean started;
      private final BlockingFlowableNext.NextSubscriber<T> subscriber;

      NextIterator(Publisher<? extends T> var1, BlockingFlowableNext.NextSubscriber<T> var2) {
         this.items = var1;
         this.subscriber = var2;
      }

      private boolean moveToNext() {
         try {
            if (!this.started) {
               this.started = true;
               this.subscriber.setWaiting();
               Flowable.<T>fromPublisher(this.items).materialize().subscribe(this.subscriber);
            }

            Notification var1 = this.subscriber.takeNext();
            if (var1.isOnNext()) {
               this.isNextConsumed = false;
               this.next = (T)var1.getValue();
               return true;
            } else {
               this.hasNext = false;
               if (var1.isOnComplete()) {
                  return false;
               } else if (var1.isOnError()) {
                  Throwable var4 = var1.getError();
                  this.error = var4;
                  throw ExceptionHelper.wrapOrThrow(var4);
               } else {
                  IllegalStateException var3 = new IllegalStateException("Should not reach here");
                  throw var3;
               }
            }
         } catch (InterruptedException var2) {
            this.subscriber.dispose();
            this.error = var2;
            throw ExceptionHelper.wrapOrThrow(var2);
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

   static final class NextSubscriber<T> extends DisposableSubscriber<Notification<T>> {
      private final BlockingQueue<Notification<T>> buf = new ArrayBlockingQueue<>(1);
      final AtomicInteger waiting = new AtomicInteger();

      public void onComplete() {
      }

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
