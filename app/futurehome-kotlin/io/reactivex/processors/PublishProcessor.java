package io.reactivex.processors;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class PublishProcessor<T> extends FlowableProcessor<T> {
   static final PublishProcessor.PublishSubscription[] EMPTY = new PublishProcessor.PublishSubscription[0];
   static final PublishProcessor.PublishSubscription[] TERMINATED = new PublishProcessor.PublishSubscription[0];
   Throwable error;
   final AtomicReference<PublishProcessor.PublishSubscription<T>[]> subscribers = new AtomicReference<>(EMPTY);

   PublishProcessor() {
   }

   @CheckReturnValue
   public static <T> PublishProcessor<T> create() {
      return new PublishProcessor<>();
   }

   boolean add(PublishProcessor.PublishSubscription<T> var1) {
      PublishProcessor.PublishSubscription[] var3;
      PublishProcessor.PublishSubscription[] var4;
      do {
         var4 = this.subscribers.get();
         if (var4 == TERMINATED) {
            return false;
         }

         int var2 = var4.length;
         var3 = new PublishProcessor.PublishSubscription[var2 + 1];
         System.arraycopy(var4, 0, var3, 0, var2);
         var3[var2] = var1;
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.subscribers, var4, var3));

      return true;
   }

   @Override
   public Throwable getThrowable() {
      return this.subscribers.get() == TERMINATED ? this.error : null;
   }

   @Override
   public boolean hasComplete() {
      boolean var1;
      if (this.subscribers.get() == TERMINATED && this.error == null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public boolean hasSubscribers() {
      boolean var1;
      if (((PublishProcessor.PublishSubscription[])this.subscribers.get()).length != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public boolean hasThrowable() {
      boolean var1;
      if (this.subscribers.get() == TERMINATED && this.error != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean offer(T var1) {
      if (var1 == null) {
         this.onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
         return true;
      } else {
         PublishProcessor.PublishSubscription[] var5 = this.subscribers.get();
         int var4 = var5.length;
         byte var3 = 0;

         for (int var2 = 0; var2 < var4; var2++) {
            if (var5[var2].isFull()) {
               return false;
            }
         }

         var4 = var5.length;

         for (int var6 = var3; var6 < var4; var6++) {
            var5[var6].onNext(var1);
         }

         return true;
      }
   }

   public void onComplete() {
      Object var4 = this.subscribers.get();
      PublishProcessor.PublishSubscription[] var3 = TERMINATED;
      if (var4 != var3) {
         var3 = this.subscribers.getAndSet(var3);
         int var2 = var3.length;

         for (int var1 = 0; var1 < var2; var1++) {
            var3[var1].onComplete();
         }
      }
   }

   public void onError(Throwable var1) {
      ObjectHelper.requireNonNull(var1, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
      Object var4 = this.subscribers.get();
      PublishProcessor.PublishSubscription[] var5 = TERMINATED;
      if (var4 == var5) {
         RxJavaPlugins.onError(var1);
      } else {
         this.error = var1;
         var4 = this.subscribers.getAndSet(var5);
         int var3 = ((Object[])var4).length;

         for (int var2 = 0; var2 < var3; var2++) {
            ((PublishProcessor.PublishSubscription)((Object[])var4)[var2]).onError(var1);
         }
      }
   }

   public void onNext(T var1) {
      ObjectHelper.requireNonNull(var1, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
      PublishProcessor.PublishSubscription[] var4 = this.subscribers.get();
      int var3 = var4.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4[var2].onNext(var1);
      }
   }

   @Override
   public void onSubscribe(Subscription var1) {
      if (this.subscribers.get() == TERMINATED) {
         var1.cancel();
      } else {
         var1.request(Long.MAX_VALUE);
      }
   }

   void remove(PublishProcessor.PublishSubscription<T> var1) {
      while (true) {
         PublishProcessor.PublishSubscription[] var5 = this.subscribers.get();
         if (var5 != TERMINATED && var5 != EMPTY) {
            int var3 = var5.length;
            int var2 = 0;

            while (true) {
               if (var2 >= var3) {
                  var2 = -1;
                  break;
               }

               if (var5[var2] == var1) {
                  break;
               }

               var2++;
            }

            if (var2 < 0) {
               return;
            }

            PublishProcessor.PublishSubscription[] var4;
            if (var3 == 1) {
               var4 = EMPTY;
            } else {
               var4 = new PublishProcessor.PublishSubscription[var3 - 1];
               System.arraycopy(var5, 0, var4, 0, var2);
               System.arraycopy(var5, var2 + 1, var4, var2, var3 - var2 - 1);
            }

            if (!ExternalSyntheticBackportWithForwarding0.m(this.subscribers, var5, var4)) {
               continue;
            }
         }

         return;
      }
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      PublishProcessor.PublishSubscription var2 = new PublishProcessor.PublishSubscription<>(var1, this);
      var1.onSubscribe(var2);
      if (this.add(var2)) {
         if (var2.isCancelled()) {
            this.remove(var2);
         }
      } else {
         Throwable var3 = this.error;
         if (var3 != null) {
            var1.onError(var3);
         } else {
            var1.onComplete();
         }
      }
   }

   static final class PublishSubscription<T> extends AtomicLong implements Subscription {
      private static final long serialVersionUID = 3562861878281475070L;
      final Subscriber<? super T> downstream;
      final PublishProcessor<T> parent;

      PublishSubscription(Subscriber<? super T> var1, PublishProcessor<T> var2) {
         this.downstream = var1;
         this.parent = var2;
      }

      public void cancel() {
         if (this.getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
            this.parent.remove(this);
         }
      }

      public boolean isCancelled() {
         boolean var1;
         if (this.get() == Long.MIN_VALUE) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      boolean isFull() {
         boolean var1;
         if (this.get() == 0L) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public void onComplete() {
         if (this.get() != Long.MIN_VALUE) {
            this.downstream.onComplete();
         }
      }

      public void onError(Throwable var1) {
         if (this.get() != Long.MIN_VALUE) {
            this.downstream.onError(var1);
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      public void onNext(T var1) {
         long var2 = this.get();
         if (var2 != Long.MIN_VALUE) {
            if (var2 != 0L) {
               this.downstream.onNext(var1);
               BackpressureHelper.producedCancel(this, 1L);
            } else {
               this.cancel();
               this.downstream.onError(new MissingBackpressureException("Could not emit value due to lack of requests"));
            }
         }
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            BackpressureHelper.addCancel(this, var1);
         }
      }
   }
}
