package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableZipIterable<T, U, V> extends AbstractFlowableWithUpstream<T, V> {
   final Iterable<U> other;
   final BiFunction<? super T, ? super U, ? extends V> zipper;

   public FlowableZipIterable(Flowable<T> var1, Iterable<U> var2, BiFunction<? super T, ? super U, ? extends V> var3) {
      super(var1);
      this.other = var2;
      this.zipper = var3;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribeActual(Subscriber<? super V> var1) {
      Iterator var3;
      try {
         var3 = ObjectHelper.requireNonNull(this.other.iterator(), "The iterator returned by other is null");
      } catch (Throwable var9) {
         Exceptions.throwIfFatal(var9);
         EmptySubscription.error(var9, var1);
         return;
      }

      boolean var2;
      try {
         var2 = var3.hasNext();
      } catch (Throwable var8) {
         Exceptions.throwIfFatal(var8);
         EmptySubscription.error(var8, var1);
         return;
      }

      if (!var2) {
         EmptySubscription.complete(var1);
      } else {
         this.source.subscribe(new FlowableZipIterable.ZipIterableSubscriber<>(var1, var3, this.zipper));
      }
   }

   static final class ZipIterableSubscriber<T, U, V> implements FlowableSubscriber<T>, Subscription {
      boolean done;
      final Subscriber<? super V> downstream;
      final Iterator<U> iterator;
      Subscription upstream;
      final BiFunction<? super T, ? super U, ? extends V> zipper;

      ZipIterableSubscriber(Subscriber<? super V> var1, Iterator<U> var2, BiFunction<? super T, ? super U, ? extends V> var3) {
         this.downstream = var1;
         this.iterator = var2;
         this.zipper = var3;
      }

      public void cancel() {
         this.upstream.cancel();
      }

      void error(Throwable var1) {
         Exceptions.throwIfFatal(var1);
         this.done = true;
         this.upstream.cancel();
         this.downstream.onError(var1);
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.downstream.onComplete();
         }
      }

      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.downstream.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void onNext(T var1) {
         if (!this.done) {
            Object var3;
            try {
               var3 = ObjectHelper.requireNonNull(this.iterator.next(), "The iterator returned a null value");
            } catch (Throwable var15) {
               this.error(var15);
               return;
            }

            try {
               var1 = ObjectHelper.requireNonNull(this.zipper.apply((T)var1, (U)var3), "The zipper function returned a null value");
            } catch (Throwable var14) {
               this.error(var14);
               return;
            }

            this.downstream.onNext(var1);

            boolean var2;
            try {
               var2 = this.iterator.hasNext();
            } catch (Throwable var13) {
               this.error(var13);
               return;
            }

            if (!var2) {
               this.done = true;
               this.upstream.cancel();
               this.downstream.onComplete();
            }
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      public void request(long var1) {
         this.upstream.request(var1);
      }
   }
}
