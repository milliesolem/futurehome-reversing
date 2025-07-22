package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.Notification;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subscribers.DisposableSubscriber;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;

public final class BlockingFlowableLatest<T> implements Iterable<T> {
   final Publisher<? extends T> source;

   public BlockingFlowableLatest(Publisher<? extends T> var1) {
      this.source = var1;
   }

   @Override
   public Iterator<T> iterator() {
      BlockingFlowableLatest.LatestSubscriberIterator var1 = new BlockingFlowableLatest.LatestSubscriberIterator();
      Flowable.<T>fromPublisher(this.source).materialize().subscribe(var1);
      return var1;
   }

   static final class LatestSubscriberIterator<T> extends DisposableSubscriber<Notification<T>> implements Iterator<T> {
      Notification<T> iteratorNotification;
      final Semaphore notify = new Semaphore(0);
      final AtomicReference<Notification<T>> value = new AtomicReference<>();

      @Override
      public boolean hasNext() {
         Notification var1 = this.iteratorNotification;
         if (var1 != null && var1.isOnError()) {
            throw ExceptionHelper.wrapOrThrow(this.iteratorNotification.getError());
         } else {
            var1 = this.iteratorNotification;
            if ((var1 == null || var1.isOnNext()) && this.iteratorNotification == null) {
               try {
                  BlockingHelper.verifyNonBlocking();
                  this.notify.acquire();
               } catch (InterruptedException var2) {
                  this.dispose();
                  this.iteratorNotification = Notification.createOnError(var2);
                  throw ExceptionHelper.wrapOrThrow(var2);
               }

               var1 = this.value.getAndSet(null);
               this.iteratorNotification = var1;
               if (var1.isOnError()) {
                  throw ExceptionHelper.wrapOrThrow(var1.getError());
               }
            }

            return this.iteratorNotification.isOnNext();
         }
      }

      @Override
      public T next() {
         if (this.hasNext() && this.iteratorNotification.isOnNext()) {
            Object var1 = this.iteratorNotification.getValue();
            this.iteratorNotification = null;
            return (T)var1;
         } else {
            throw new NoSuchElementException();
         }
      }

      public void onComplete() {
      }

      public void onError(Throwable var1) {
         RxJavaPlugins.onError(var1);
      }

      public void onNext(Notification<T> var1) {
         if (this.value.getAndSet(var1) == null) {
            this.notify.release();
         }
      }

      @Override
      public void remove() {
         throw new UnsupportedOperationException("Read-only iterator.");
      }
   }
}
