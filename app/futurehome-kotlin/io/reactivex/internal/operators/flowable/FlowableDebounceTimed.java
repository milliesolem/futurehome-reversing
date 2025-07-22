package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableDebounceTimed<T> extends AbstractFlowableWithUpstream<T, T> {
   final Scheduler scheduler;
   final long timeout;
   final TimeUnit unit;

   public FlowableDebounceTimed(Flowable<T> var1, long var2, TimeUnit var4, Scheduler var5) {
      super(var1);
      this.timeout = var2;
      this.unit = var4;
      this.scheduler = var5;
   }

   @Override
   protected void subscribeActual(Subscriber<? super T> var1) {
      this.source
         .subscribe(
            new FlowableDebounceTimed.DebounceTimedSubscriber<>(new SerializedSubscriber<>(var1), this.timeout, this.unit, this.scheduler.createWorker())
         );
   }

   static final class DebounceEmitter<T> extends AtomicReference<Disposable> implements Runnable, Disposable {
      private static final long serialVersionUID = 6812032969491025141L;
      final long idx;
      final AtomicBoolean once = new AtomicBoolean();
      final FlowableDebounceTimed.DebounceTimedSubscriber<T> parent;
      final T value;

      DebounceEmitter(T var1, long var2, FlowableDebounceTimed.DebounceTimedSubscriber<T> var4) {
         this.value = (T)var1;
         this.idx = var2;
         this.parent = var4;
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this);
      }

      void emit() {
         if (this.once.compareAndSet(false, true)) {
            this.parent.emit(this.idx, this.value, this);
         }
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.get() == DisposableHelper.DISPOSED) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      @Override
      public void run() {
         this.emit();
      }

      public void setResource(Disposable var1) {
         DisposableHelper.replace(this, var1);
      }
   }

   static final class DebounceTimedSubscriber<T> extends AtomicLong implements FlowableSubscriber<T>, Subscription {
      private static final long serialVersionUID = -9102637559663639004L;
      boolean done;
      final Subscriber<? super T> downstream;
      volatile long index;
      final long timeout;
      Disposable timer;
      final TimeUnit unit;
      Subscription upstream;
      final Scheduler.Worker worker;

      DebounceTimedSubscriber(Subscriber<? super T> var1, long var2, TimeUnit var4, Scheduler.Worker var5) {
         this.downstream = var1;
         this.timeout = var2;
         this.unit = var4;
         this.worker = var5;
      }

      public void cancel() {
         this.upstream.cancel();
         this.worker.dispose();
      }

      void emit(long var1, T var3, FlowableDebounceTimed.DebounceEmitter<T> var4) {
         if (var1 == this.index) {
            if (this.get() != 0L) {
               this.downstream.onNext(var3);
               BackpressureHelper.produced(this, 1L);
               var4.dispose();
            } else {
               this.cancel();
               this.downstream.onError(new MissingBackpressureException("Could not deliver value due to lack of requests"));
            }
         }
      }

      public void onComplete() {
         if (!this.done) {
            this.done = true;
            Disposable var1 = this.timer;
            if (var1 != null) {
               var1.dispose();
            }

            FlowableDebounceTimed.DebounceEmitter var2 = (FlowableDebounceTimed.DebounceEmitter)var1;
            if (var2 != null) {
               var2.emit();
            }

            this.downstream.onComplete();
            this.worker.dispose();
         }
      }

      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            Disposable var2 = this.timer;
            if (var2 != null) {
               var2.dispose();
            }

            this.downstream.onError(var1);
            this.worker.dispose();
         }
      }

      public void onNext(T var1) {
         if (!this.done) {
            long var2 = this.index + 1L;
            this.index = var2;
            Disposable var4 = this.timer;
            if (var4 != null) {
               var4.dispose();
            }

            var1 = new FlowableDebounceTimed.DebounceEmitter<>(var1, var2, this);
            this.timer = var1;
            var1.setResource(this.worker.schedule(var1, this.timeout, this.unit));
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
