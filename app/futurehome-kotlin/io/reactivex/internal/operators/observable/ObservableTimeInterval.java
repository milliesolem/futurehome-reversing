package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.schedulers.Timed;
import java.util.concurrent.TimeUnit;

public final class ObservableTimeInterval<T> extends AbstractObservableWithUpstream<T, Timed<T>> {
   final Scheduler scheduler;
   final TimeUnit unit;

   public ObservableTimeInterval(ObservableSource<T> var1, TimeUnit var2, Scheduler var3) {
      super(var1);
      this.scheduler = var3;
      this.unit = var2;
   }

   @Override
   public void subscribeActual(Observer<? super Timed<T>> var1) {
      this.source.subscribe(new ObservableTimeInterval.TimeIntervalObserver<>(var1, this.unit, this.scheduler));
   }

   static final class TimeIntervalObserver<T> implements Observer<T>, Disposable {
      final Observer<? super Timed<T>> downstream;
      long lastTime;
      final Scheduler scheduler;
      final TimeUnit unit;
      Disposable upstream;

      TimeIntervalObserver(Observer<? super Timed<T>> var1, TimeUnit var2, Scheduler var3) {
         this.downstream = var1;
         this.scheduler = var3;
         this.unit = var2;
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      @Override
      public void onComplete() {
         this.downstream.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         this.downstream.onError(var1);
      }

      @Override
      public void onNext(T var1) {
         long var2 = this.scheduler.now(this.unit);
         long var4 = this.lastTime;
         this.lastTime = var2;
         this.downstream.onNext(new Timed<>((T)var1, var2 - var4, this.unit));
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.lastTime = this.scheduler.now(this.unit);
            this.downstream.onSubscribe(this);
         }
      }
   }
}
