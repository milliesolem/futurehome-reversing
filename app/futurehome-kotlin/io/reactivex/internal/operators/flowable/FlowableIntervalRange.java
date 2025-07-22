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

public final class FlowableIntervalRange extends Flowable<Long> {
   final long end;
   final long initialDelay;
   final long period;
   final Scheduler scheduler;
   final long start;
   final TimeUnit unit;

   public FlowableIntervalRange(long var1, long var3, long var5, long var7, TimeUnit var9, Scheduler var10) {
      this.initialDelay = var5;
      this.period = var7;
      this.unit = var9;
      this.scheduler = var10;
      this.start = var1;
      this.end = var3;
   }

   @Override
   public void subscribeActual(Subscriber<? super Long> var1) {
      FlowableIntervalRange.IntervalRangeSubscriber var2 = new FlowableIntervalRange.IntervalRangeSubscriber(var1, this.start, this.end);
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

   static final class IntervalRangeSubscriber extends AtomicLong implements Subscription, Runnable {
      private static final long serialVersionUID = -2809475196591179431L;
      long count;
      final Subscriber<? super Long> downstream;
      final long end;
      final AtomicReference<Disposable> resource = new AtomicReference<>();

      IntervalRangeSubscriber(Subscriber<? super Long> var1, long var2, long var4) {
         this.downstream = var1;
         this.count = var2;
         this.end = var4;
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
            long var3 = this.get();
            if (var3 != 0L) {
               long var1 = this.count;
               this.downstream.onNext(var1);
               if (var1 == this.end) {
                  if (this.resource.get() != DisposableHelper.DISPOSED) {
                     this.downstream.onComplete();
                  }

                  DisposableHelper.dispose(this.resource);
                  return;
               }

               this.count = var1 + 1L;
               if (var3 != Long.MAX_VALUE) {
                  this.decrementAndGet();
               }
            } else {
               Subscriber var6 = this.downstream;
               StringBuilder var5 = new StringBuilder("Can't deliver value ");
               var5.append(this.count);
               var5.append(" due to lack of requests");
               var6.onError(new MissingBackpressureException(var5.toString()));
               DisposableHelper.dispose(this.resource);
            }
         }
      }

      public void setResource(Disposable var1) {
         DisposableHelper.setOnce(this.resource, var1);
      }
   }
}
