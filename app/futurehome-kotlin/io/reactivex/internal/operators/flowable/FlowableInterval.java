package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.schedulers.TrampolineScheduler;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableInterval extends Flowable<Long> {
   final long initialDelay;
   final long period;
   final Scheduler scheduler;
   final TimeUnit unit;

   public FlowableInterval(long var1, long var3, TimeUnit var5, Scheduler var6) {
      this.initialDelay = var1;
      this.period = var3;
      this.unit = var5;
      this.scheduler = var6;
   }

   @Override
   public void subscribeActual(Subscriber<? super Long> var1) {
      FlowableInterval.IntervalSubscriber var2 = new FlowableInterval.IntervalSubscriber(var1);
      var1.onSubscribe(var2);
      Scheduler var3 = this.scheduler;
      if (var3 instanceof TrampolineScheduler) {
         Scheduler.Worker var4 = var3.createWorker();
         var2.setResource(var4);
         var4.schedulePeriodically(var2, this.initialDelay, this.period, this.unit);
      } else {
         var2.setResource(var3.schedulePeriodicallyDirect(var2, this.initialDelay, this.period, this.unit));
      }
   }

   static final class IntervalSubscriber extends AtomicLong implements Subscription, Runnable {
      private static final long serialVersionUID = -2809475196591179431L;
      long count;
      final Subscriber<? super Long> downstream;
      final AtomicReference<Disposable> resource = new AtomicReference<>();

      IntervalSubscriber(Subscriber<? super Long> var1) {
         this.downstream = var1;
      }

      public void cancel() {
         DisposableHelper.dispose(this.resource);
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            BackpressureHelper.add(this, var1);
         }
      }

      @Override
      public void run() {
         if (this.resource.get() != DisposableHelper.DISPOSED) {
            if (this.get() != 0L) {
               Subscriber var3 = this.downstream;
               long var1 = (long)(this.count++);
               var3.onNext(var1);
               BackpressureHelper.produced(this, 1L);
            } else {
               Subscriber var5 = this.downstream;
               StringBuilder var4 = new StringBuilder("Can't deliver value ");
               var4.append(this.count);
               var4.append(" due to lack of requests");
               var5.onError(new MissingBackpressureException(var4.toString()));
               DisposableHelper.dispose(this.resource);
            }
         }
      }

      public void setResource(Disposable var1) {
         DisposableHelper.setOnce(this.resource, var1);
      }
   }
}
