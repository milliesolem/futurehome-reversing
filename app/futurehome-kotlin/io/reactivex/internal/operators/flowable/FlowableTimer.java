package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTimer extends Flowable<Long> {
   final long delay;
   final Scheduler scheduler;
   final TimeUnit unit;

   public FlowableTimer(long var1, TimeUnit var3, Scheduler var4) {
      this.delay = var1;
      this.unit = var3;
      this.scheduler = var4;
   }

   @Override
   public void subscribeActual(Subscriber<? super Long> var1) {
      FlowableTimer.TimerSubscriber var2 = new FlowableTimer.TimerSubscriber(var1);
      var1.onSubscribe(var2);
      var2.setResource(this.scheduler.scheduleDirect(var2, this.delay, this.unit));
   }

   static final class TimerSubscriber extends AtomicReference<Disposable> implements Subscription, Runnable {
      private static final long serialVersionUID = -2809475196591179431L;
      final Subscriber<? super Long> downstream;
      volatile boolean requested;

      TimerSubscriber(Subscriber<? super Long> var1) {
         this.downstream = var1;
      }

      public void cancel() {
         DisposableHelper.dispose(this);
      }

      public void request(long var1) {
         if (SubscriptionHelper.validate(var1)) {
            this.requested = true;
         }
      }

      @Override
      public void run() {
         if (this.get() != DisposableHelper.DISPOSED) {
            if (this.requested) {
               this.downstream.onNext(0L);
               this.lazySet(EmptyDisposable.INSTANCE);
               this.downstream.onComplete();
            } else {
               this.lazySet(EmptyDisposable.INSTANCE);
               this.downstream.onError(new MissingBackpressureException("Can't deliver value due to lack of requests"));
            }
         }
      }

      public void setResource(Disposable var1) {
         DisposableHelper.trySet(this, var1);
      }
   }
}
