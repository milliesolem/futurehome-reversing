package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableOnBackpressureLatest<T> extends AbstractFlowableWithUpstream<T, T> {
   public FlowableOnBackpressureLatest(Flowable<T> var1) {
      super(var1);
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source.subscribe(new FlowableOnBackpressureLatest.BackpressureLatestSubscriber<>(var1));
   }

   static final class BackpressureLatestSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
      private static final long serialVersionUID = 163080509307634843L;
      volatile boolean cancelled;
      final AtomicReference<T> current;
      volatile boolean done;
      final Subscriber<? super T> downstream;
      Throwable error;
      final AtomicLong requested = new AtomicLong();
      Subscription upstream;

      BackpressureLatestSubscriber(Subscriber<? super T> var1) {
         this.current = new AtomicReference<>();
         this.downstream = var1;
      }

      public void cancel() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.upstream.cancel();
            if (this.getAndIncrement() == 0) {
               this.current.lazySet(null);
            }
         }
      }

      boolean checkTerminated(boolean var1, boolean var2, Subscriber<?> var3, AtomicReference<T> var4) {
         if (this.cancelled) {
            var4.lazySet(null);
            return true;
         } else {
            if (var1) {
               Throwable var5 = this.error;
               if (var5 != null) {
                  var4.lazySet(null);
                  var3.onError(var5);
                  return true;
               }

               if (var2) {
                  var3.onComplete();
                  return true;
               }
            }

            return false;
         }
      }

      void drain() {
         if (this.getAndIncrement() == 0) {
            Subscriber var11 = this.downstream;
            AtomicLong var13 = this.requested;
            AtomicReference var10 = this.current;
            int var1 = 1;

            int var2;
            do {
               long var3 = 0L;

               boolean var8;
               while (true) {
                  long var5 = var13.get();
                  var8 = false;
                  if (var3 == var5) {
                     break;
                  }

                  boolean var9 = this.done;
                  Object var12 = var10.getAndSet(null);
                  boolean var7;
                  if (var12 == null) {
                     var7 = true;
                  } else {
                     var7 = false;
                  }

                  if (this.checkTerminated(var9, var7, var11, var10)) {
                     return;
                  }

                  if (var7) {
                     break;
                  }

                  var11.onNext(var12);
                  var3++;
               }

               if (var3 == var13.get()) {
                  boolean var15 = this.done;
                  boolean var14 = var8;
                  if (var10.get() == null) {
                     var14 = true;
                  }

                  if (this.checkTerminated(var15, var14, var11, var10)) {
                     return;
                  }
               }

               if (var3 != 0L) {
                  BackpressureHelper.produced(var13, var3);
               }

               var2 = this.addAndGet(-var1);
               var1 = var2;
            } while (var2 != 0);
         }
      }

      public void onComplete() {
         this.done = true;
         this.drain();
      }

      public void onError(Throwable var1) {
         this.error = var1;
         this.done = true;
         this.drain();
      }

      public void onNext(T var1) {
         this.current.lazySet((T)var1);
         this.drain();
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
            BackpressureHelper.add(this.requested, var1);
            this.drain();
         }
      }
   }
}
