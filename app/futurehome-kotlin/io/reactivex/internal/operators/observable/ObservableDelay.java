package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.SerializedObserver;
import java.util.concurrent.TimeUnit;

public final class ObservableDelay<T> extends AbstractObservableWithUpstream<T, T> {
   final long delay;
   final boolean delayError;
   final Scheduler scheduler;
   final TimeUnit unit;

   public ObservableDelay(ObservableSource<T> var1, long var2, TimeUnit var4, Scheduler var5, boolean var6) {
      super(var1);
      this.delay = var2;
      this.unit = var4;
      this.scheduler = var5;
      this.delayError = var6;
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      if (!this.delayError) {
         var1 = new SerializedObserver((Observer<? super T>)var1);
      }

      Scheduler.Worker var2 = this.scheduler.createWorker();
      this.source.subscribe(new ObservableDelay.DelayObserver<>((Observer<? super T>)var1, this.delay, this.unit, var2, this.delayError));
   }

   static final class DelayObserver<T> implements Observer<T>, Disposable {
      final long delay;
      final boolean delayError;
      final Observer<? super T> downstream;
      final TimeUnit unit;
      Disposable upstream;
      final Scheduler.Worker w;

      DelayObserver(Observer<? super T> var1, long var2, TimeUnit var4, Scheduler.Worker var5, boolean var6) {
         this.downstream = var1;
         this.delay = var2;
         this.unit = var4;
         this.w = var5;
         this.delayError = var6;
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
         this.w.dispose();
      }

      @Override
      public boolean isDisposed() {
         return this.w.isDisposed();
      }

      @Override
      public void onComplete() {
         this.w.schedule(new ObservableDelay.DelayObserver.OnComplete(this), this.delay, this.unit);
      }

      @Override
      public void onError(Throwable var1) {
         Scheduler.Worker var4 = this.w;
         ObservableDelay.DelayObserver.OnError var5 = new ObservableDelay.DelayObserver.OnError(this, var1);
         long var2;
         if (this.delayError) {
            var2 = this.delay;
         } else {
            var2 = 0L;
         }

         var4.schedule(var5, var2, this.unit);
      }

      @Override
      public void onNext(T var1) {
         this.w.schedule(new ObservableDelay.DelayObserver.OnNext(this, var1), this.delay, this.unit);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      final class OnComplete implements Runnable {
         final ObservableDelay.DelayObserver this$0;

         OnComplete(ObservableDelay.DelayObserver var1) {
            this.this$0 = var1;
         }

         @Override
         public void run() {
            try {
               this.this$0.downstream.onComplete();
            } finally {
               this.this$0.w.dispose();
            }
         }
      }

      final class OnError implements Runnable {
         final ObservableDelay.DelayObserver this$0;
         private final Throwable throwable;

         OnError(ObservableDelay.DelayObserver var1, Throwable var2) {
            this.this$0 = var1;
            this.throwable = var2;
         }

         @Override
         public void run() {
            try {
               this.this$0.downstream.onError(this.throwable);
            } finally {
               this.this$0.w.dispose();
            }
         }
      }

      final class OnNext implements Runnable {
         private final T t;
         final ObservableDelay.DelayObserver this$0;

         OnNext(T var1, Object var2) {
            this.this$0 = var1;
            this.t = (T)var2;
         }

         @Override
         public void run() {
            this.this$0.downstream.onNext(this.t);
         }
      }
   }
}
