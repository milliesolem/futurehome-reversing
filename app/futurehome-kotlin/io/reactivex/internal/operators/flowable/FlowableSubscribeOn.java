package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableSubscribeOn<T> extends AbstractFlowableWithUpstream<T, T> {
   final boolean nonScheduledRequests;
   final Scheduler scheduler;

   public FlowableSubscribeOn(Flowable<T> var1, Scheduler var2, boolean var3) {
      super(var1);
      this.scheduler = var2;
      this.nonScheduledRequests = var3;
   }

   @Override
   public void subscribeActual(Subscriber<? super T> var1) {
      Scheduler.Worker var3 = this.scheduler.createWorker();
      FlowableSubscribeOn.SubscribeOnSubscriber var2 = new FlowableSubscribeOn.SubscribeOnSubscriber(var1, var3, this.source, this.nonScheduledRequests);
      var1.onSubscribe(var2);
      var3.schedule(var2);
   }

   static final class SubscribeOnSubscriber<T> extends AtomicReference<Thread> implements FlowableSubscriber<T>, Subscription, Runnable {
      private static final long serialVersionUID = 8094547886072529208L;
      final Subscriber<? super T> downstream;
      final boolean nonScheduledRequests;
      final AtomicLong requested;
      Publisher<T> source;
      final AtomicReference<Subscription> upstream;
      final Scheduler.Worker worker;

      SubscribeOnSubscriber(Subscriber<? super T> var1, Scheduler.Worker var2, Publisher<T> var3, boolean var4) {
         this.downstream = var1;
         this.worker = var2;
         this.source = var3;
         this.upstream = new AtomicReference<>();
         this.requested = new AtomicLong();
         this.nonScheduledRequests = var4 ^ true;
      }

      public void cancel() {
         SubscriptionHelper.cancel(this.upstream);
         this.worker.dispose();
      }

      public void onComplete() {
         this.downstream.onComplete();
         this.worker.dispose();
      }

      public void onError(Throwable var1) {
         this.downstream.onError(var1);
         this.worker.dispose();
      }

      public void onNext(T var1) {
         this.downstream.onNext(var1);
      }

      @Override
      public void onSubscribe(Subscription var1) {
         if (SubscriptionHelper.setOnce(this.upstream, var1)) {
            long var2 = this.requested.getAndSet(0L);
            if (var2 != 0L) {
               this.requestUpstream(var2, var1);
            }
         }
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            Subscription var3 = this.upstream.get();
            if (var3 != null) {
               this.requestUpstream(var1, var3);
            } else {
               BackpressureHelper.add(this.requested, var1);
               var3 = this.upstream.get();
               if (var3 != null) {
                  var1 = this.requested.getAndSet(0L);
                  if (var1 != 0L) {
                     this.requestUpstream(var1, var3);
                  }
               }
            }
         }
      }

      void requestUpstream(long var1, Subscription var3) {
         if (!this.nonScheduledRequests && Thread.currentThread() != this.get()) {
            this.worker.schedule(new FlowableSubscribeOn.SubscribeOnSubscriber.Request(var3, var1));
         } else {
            var3.request(var1);
         }
      }

      @Override
      public void run() {
         this.lazySet(Thread.currentThread());
         Publisher var1 = this.source;
         this.source = null;
         var1.subscribe(this);
      }

      static final class Request implements Runnable {
         final long n;
         final Subscription upstream;

         Request(Subscription var1, long var2) {
            this.upstream = var1;
            this.n = var2;
         }

         @Override
         public void run() {
            this.upstream.request(this.n);
         }
      }
   }
}
