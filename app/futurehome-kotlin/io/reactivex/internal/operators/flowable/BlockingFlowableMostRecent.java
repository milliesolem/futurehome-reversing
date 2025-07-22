package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.subscribers.DefaultSubscriber;
import java.util.NoSuchElementException;

public final class BlockingFlowableMostRecent<T> implements Iterable<T> {
   final T initialValue;
   final Flowable<T> source;

   public BlockingFlowableMostRecent(Flowable<T> var1, T var2) {
      this.source = var1;
      this.initialValue = (T)var2;
   }

   @Override
   public java.util.Iterator<T> iterator() {
      BlockingFlowableMostRecent.MostRecentSubscriber var1 = new BlockingFlowableMostRecent.MostRecentSubscriber(this.initialValue);
      this.source.subscribe(var1);
      return var1.getIterable();
   }

   static final class MostRecentSubscriber<T> extends DefaultSubscriber<T> {
      volatile Object value;

      MostRecentSubscriber(T var1) {
         this.value = NotificationLite.next(var1);
      }

      public BlockingFlowableMostRecent.MostRecentSubscriber<T>.Iterator getIterable() {
         return new BlockingFlowableMostRecent.MostRecentSubscriber.Iterator(this);
      }

      public void onComplete() {
         this.value = NotificationLite.complete();
      }

      public void onError(Throwable var1) {
         this.value = NotificationLite.error(var1);
      }

      public void onNext(T var1) {
         this.value = NotificationLite.next(var1);
      }

      final class Iterator implements java.util.Iterator<T> {
         private Object buf;
         final BlockingFlowableMostRecent.MostRecentSubscriber this$0;

         Iterator(BlockingFlowableMostRecent.MostRecentSubscriber var1) {
            this.this$0 = var1;
         }

         @Override
         public boolean hasNext() {
            Object var1 = this.this$0.value;
            this.buf = var1;
            return NotificationLite.isComplete(var1) ^ true;
         }

         @Override
         public T next() {
            Object var1;
            try {
               if (this.buf == null) {
                  this.buf = this.this$0.value;
               }

               if (NotificationLite.isComplete(this.buf)) {
                  var1 = new NoSuchElementException();
                  throw var1;
               }

               if (NotificationLite.isError(this.buf)) {
                  throw ExceptionHelper.wrapOrThrow(NotificationLite.getError(this.buf));
               }

               var1 = NotificationLite.getValue(this.buf);
            } finally {
               this.buf = null;
            }

            return (T)var1;
         }

         @Override
         public void remove() {
            throw new UnsupportedOperationException("Read only iterator");
         }
      }
   }
}
