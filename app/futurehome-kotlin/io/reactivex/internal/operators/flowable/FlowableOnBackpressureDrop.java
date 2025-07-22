package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableOnBackpressureDrop<T> extends AbstractFlowableWithUpstream<T, T> implements Consumer<T> {
   final Consumer<? super T> onDrop;

   public FlowableOnBackpressureDrop(Flowable<T> var1) {
      super(var1);
      this.onDrop = this;
   }

   public FlowableOnBackpressureDrop(Flowable<T> var1, Consumer<? super T> var2) {
      super(var1);
      this.onDrop = var2;
   }

   @Override
   public void accept(T var1) {
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(new FlowableOnBackpressureDrop.BackpressureDropSubscriber<>(var1, this.onDrop));
   }

   static final class BackpressureDropSubscriber<T> extends AtomicLong implements FlowableSubscriber<T>, Subscription {
      private static final long serialVersionUID = -6246093802440953054L;
      boolean done;
      final Subscriber<? super T> downstream;
      final Consumer<? super T> onDrop;
      Subscription upstream;

      BackpressureDropSubscriber(Subscriber<? super T> var1, Consumer<? super T> var2) {
         this.downstream = var1;
         this.onDrop = var2;
      }

      public void cancel() {
         this.upstream.cancel();
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
            if (this.get() != 0L) {
               this.downstream.onNext(var1);
               BackpressureHelper.produced(this, 1L);
            } else {
               try {
                  this.onDrop.accept((T)var1);
               } catch (Throwable var3) {
                  Exceptions.throwIfFatal(var3);
                  this.cancel();
                  this.onError(var3);
                  return;
               }
            }
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            var1.request(Long.MAX_VALUE);
         }
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            BackpressureHelper.add(this, var1);
         }
      }
   }
}
