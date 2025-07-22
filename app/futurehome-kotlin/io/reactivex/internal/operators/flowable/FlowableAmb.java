package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableAmb<T> extends Flowable<T> {
   final Publisher<? extends T>[] sources;
   final Iterable<? extends Publisher<? extends T>> sourcesIterable;

   public FlowableAmb(Publisher<? extends T>[] var1, Iterable<? extends Publisher<? extends T>> var2) {
      this.sources = var1;
      this.sourcesIterable = var2;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribeActual(Subscriber<? super T> var1) {
      Publisher[] var5 = this.sources;
      int var3;
      if (var5 == null) {
         Publisher[] var4 = new Publisher[8];

         Iterator var6;
         try {
            var6 = this.sourcesIterable.iterator();
         } catch (Throwable var25) {
            Exceptions.throwIfFatal(var25);
            EmptySubscription.error(var25, var1);
            return;
         }

         int var2 = 0;

         while (true) {
            var5 = var4;
            var3 = var2;

            Publisher var7;
            try {
               if (!var6.hasNext()) {
                  break;
               }

               var7 = (Publisher)var6.next();
            } catch (Throwable var27) {
               Exceptions.throwIfFatal(var27);
               EmptySubscription.error(var27, var1);
               return;
            }

            if (var7 == null) {
               try {
                  NullPointerException var28 = new NullPointerException("One of the sources is null");
                  EmptySubscription.error(var28, var1);
                  return;
               } catch (Throwable var24) {
                  Exceptions.throwIfFatal(var24);
                  EmptySubscription.error(var24, var1);
                  return;
               }
            }

            var5 = var4;

            try {
               if (var2 == var4.length) {
                  var5 = new Publisher[(var2 >> 2) + var2];
                  System.arraycopy(var4, 0, var5, 0, var2);
               }
            } catch (Throwable var26) {
               Exceptions.throwIfFatal(var26);
               EmptySubscription.error(var26, var1);
               return;
            }

            var5[var2] = var7;
            var2++;
            var4 = var5;
         }
      } else {
         var3 = var5.length;
      }

      if (var3 == 0) {
         EmptySubscription.complete(var1);
      } else if (var3 == 1) {
         var5[0].subscribe(var1);
      } else {
         new FlowableAmb.AmbCoordinator(var1, var3).subscribe(var5);
      }
   }

   static final class AmbCoordinator<T> implements Subscription {
      final Subscriber<? super T> downstream;
      final FlowableAmb.AmbInnerSubscriber<T>[] subscribers;
      final AtomicInteger winner = new AtomicInteger();

      AmbCoordinator(Subscriber<? super T> var1, int var2) {
         this.downstream = var1;
         this.subscribers = new FlowableAmb.AmbInnerSubscriber[var2];
      }

      public void cancel() {
         if (this.winner.get() != -1) {
            this.winner.lazySet(-1);
            FlowableAmb.AmbInnerSubscriber[] var3 = this.subscribers;
            int var2 = var3.length;

            for (int var1 = 0; var1 < var2; var1++) {
               var3[var1].cancel();
            }
         }
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            int var3 = this.winner.get();
            if (var3 > 0) {
               this.subscribers[var3 - 1].request(var1);
            } else if (var3 == 0) {
               FlowableAmb.AmbInnerSubscriber[] var5 = this.subscribers;
               int var4 = var5.length;

               for (int var6 = 0; var6 < var4; var6++) {
                  var5[var6].request(var1);
               }
            }
         }
      }

      public void subscribe(Publisher<? extends T>[] var1) {
         FlowableAmb.AmbInnerSubscriber[] var6 = this.subscribers;
         int var5 = var6.length;
         byte var3 = 0;
         int var2 = 0;

         while (var2 < var5) {
            int var4 = var2 + 1;
            var6[var2] = new FlowableAmb.AmbInnerSubscriber<>(this, var4, this.downstream);
            var2 = var4;
         }

         this.winner.lazySet(0);
         this.downstream.onSubscribe(this);

         for (int var7 = var3; var7 < var5; var7++) {
            if (this.winner.get() != 0) {
               return;
            }

            var1[var7].subscribe(var6[var7]);
         }
      }

      public boolean win(int var1) {
         int var3 = this.winner.get();
         int var2 = 0;
         if (var3 == 0 && this.winner.compareAndSet(0, var1)) {
            FlowableAmb.AmbInnerSubscriber[] var5 = this.subscribers;
            int var4 = var5.length;

            while (var2 < var4) {
               var3 = var2 + 1;
               if (var3 != var1) {
                  var5[var2].cancel();
               }

               var2 = var3;
            }

            return true;
         } else {
            return false;
         }
      }
   }

   static final class AmbInnerSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Subscription {
      private static final long serialVersionUID = -1185974347409665484L;
      final Subscriber<? super T> downstream;
      final int index;
      final AtomicLong missedRequested = new AtomicLong();
      final FlowableAmb.AmbCoordinator<T> parent;
      boolean won;

      AmbInnerSubscriber(FlowableAmb.AmbCoordinator<T> var1, int var2, Subscriber<? super T> var3) {
         this.parent = var1;
         this.index = var2;
         this.downstream = var3;
      }

      public void cancel() {
         SubscriptionHelper.cancel(this);
      }

      public void onComplete() {
         if (this.won) {
            this.downstream.onComplete();
         } else if (this.parent.win(this.index)) {
            this.won = true;
            this.downstream.onComplete();
         } else {
            this.get().cancel();
         }
      }

      public void onError(Throwable var1) {
         if (this.won) {
            this.downstream.onError(var1);
         } else if (this.parent.win(this.index)) {
            this.won = true;
            this.downstream.onError(var1);
         } else {
            this.get().cancel();
            RxJavaPlugins.onError(var1);
         }
      }

      public void onNext(T var1) {
         if (this.won) {
            this.downstream.onNext(var1);
         } else if (this.parent.win(this.index)) {
            this.won = true;
            this.downstream.onNext(var1);
         } else {
            this.get().cancel();
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         SubscriptionHelper.deferredSetOnce(this, this.missedRequested, var1);
      }

      public void request(long var1) {
         SubscriptionHelper.deferredRequest(this, this.missedRequested, var1);
      }
   }
}
