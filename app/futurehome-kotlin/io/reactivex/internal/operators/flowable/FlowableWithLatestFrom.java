package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableWithLatestFrom<T, U, R> extends AbstractFlowableWithUpstream<T, R> {
   final BiFunction<? super T, ? super U, ? extends R> combiner;
   final Publisher<? extends U> other;

   public FlowableWithLatestFrom(Flowable<T> var1, BiFunction<? super T, ? super U, ? extends R> var2, Publisher<? extends U> var3) {
      super(var1);
      this.combiner = var2;
      this.other = var3;
   }

   @Override
   protected void subscribeActual(Subscriber<? super R> var1) {
      SerializedSubscriber var2 = new SerializedSubscriber(var1);
      FlowableWithLatestFrom.WithLatestFromSubscriber var3 = new FlowableWithLatestFrom.WithLatestFromSubscriber<>(var2, this.combiner);
      var2.onSubscribe(var3);
      this.other.subscribe(new FlowableWithLatestFrom.FlowableWithLatestSubscriber(this, var3));
      this.source.subscribe(var3);
   }

   final class FlowableWithLatestSubscriber implements FlowableSubscriber<U> {
      final FlowableWithLatestFrom this$0;
      private final FlowableWithLatestFrom.WithLatestFromSubscriber<T, U, R> wlf;

      FlowableWithLatestSubscriber(FlowableWithLatestFrom.WithLatestFromSubscriber<T, U, R> var1, FlowableWithLatestFrom.WithLatestFromSubscriber var2) {
         this.this$0 = var1;
         this.wlf = var2;
      }

      public void onComplete() {
      }

      public void onError(Throwable var1) {
         this.wlf.otherError(var1);
      }

      public void onNext(U var1) {
         this.wlf.lazySet((U)var1);
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (this.wlf.setOther(var1)) {
            var1.request(Long.MAX_VALUE);
         }
      }
   }

   static final class WithLatestFromSubscriber<T, U, R> extends AtomicReference<U> implements ConditionalSubscriber<T>, Subscription {
      private static final long serialVersionUID = -312246233408980075L;
      final BiFunction<? super T, ? super U, ? extends R> combiner;
      final Subscriber<? super R> downstream;
      final AtomicReference<Subscription> other;
      final AtomicLong requested;
      final AtomicReference<Subscription> upstream = new AtomicReference<>();

      WithLatestFromSubscriber(Subscriber<? super R> var1, BiFunction<? super T, ? super U, ? extends R> var2) {
         this.requested = new AtomicLong();
         this.other = new AtomicReference<>();
         this.downstream = var1;
         this.combiner = var2;
      }

      public void cancel() {
         SubscriptionHelper.cancel(this.upstream);
         SubscriptionHelper.cancel(this.other);
      }

      public void onComplete() {
         SubscriptionHelper.cancel(this.other);
         this.downstream.onComplete();
      }

      public void onError(Throwable var1) {
         SubscriptionHelper.cancel(this.other);
         this.downstream.onError(var1);
      }

      public void onNext(T var1) {
         if (!this.tryOnNext((T)var1)) {
            this.upstream.get().request(1L);
         }
      }

      @Override
      public void onSubscribe(Subscription var1) {
         SubscriptionHelper.deferredSetOnce(this.upstream, this.requested, var1);
      }

      public void otherError(Throwable var1) {
         SubscriptionHelper.cancel(this.upstream);
         this.downstream.onError(var1);
      }

      public void request(long var1) {
         SubscriptionHelper.deferredRequest(this.upstream, this.requested, var1);
      }

      public boolean setOther(Subscription var1) {
         return SubscriptionHelper.setOnce(this.other, var1);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public boolean tryOnNext(T var1) {
         Object var2 = this.get();
         if (var2 != null) {
            try {
               var1 = ObjectHelper.requireNonNull(this.combiner.apply((T)var1, (U)var2), "The combiner returned a null value");
            } catch (Throwable var4) {
               Exceptions.throwIfFatal(var4);
               this.cancel();
               this.downstream.onError(var4);
               return false;
            }

            this.downstream.onNext(var1);
            return true;
         } else {
            return false;
         }
      }
   }
}
