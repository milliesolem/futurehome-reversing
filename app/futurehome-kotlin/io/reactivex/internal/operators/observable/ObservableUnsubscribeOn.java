package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ObservableUnsubscribeOn<T> extends AbstractObservableWithUpstream<T, T> {
   final Scheduler scheduler;

   public ObservableUnsubscribeOn(ObservableSource<T> var1, Scheduler var2) {
      super(var1);
      this.scheduler = var2;
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(new ObservableUnsubscribeOn.UnsubscribeObserver<>(var1, this.scheduler));
   }

   static final class UnsubscribeObserver<T> extends AtomicBoolean implements Observer<T>, Disposable {
      private static final long serialVersionUID = 1015244841293359600L;
      final Observer<? super T> downstream;
      final Scheduler scheduler;
      Disposable upstream;

      UnsubscribeObserver(Observer<? super T> var1, Scheduler var2) {
         this.downstream = var1;
         this.scheduler = var2;
      }

      @Override
      public void dispose() {
         if (this.compareAndSet(false, true)) {
            this.scheduler.scheduleDirect(new ObservableUnsubscribeOn.UnsubscribeObserver.DisposeTask(this));
         }
      }

      @Override
      public boolean isDisposed() {
         return this.get();
      }

      @Override
      public void onComplete() {
         if (!this.get()) {
            this.downstream.onComplete();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.get()) {
            RxJavaPlugins.onError(var1);
         } else {
            this.downstream.onError(var1);
         }
      }

      @Override
      public void onNext(T var1) {
         if (!this.get()) {
            this.downstream.onNext((T)var1);
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      final class DisposeTask implements Runnable {
         final ObservableUnsubscribeOn.UnsubscribeObserver this$0;

         DisposeTask(ObservableUnsubscribeOn.UnsubscribeObserver var1) {
            this.this$0 = var1;
         }

         @Override
         public void run() {
            this.this$0.upstream.dispose();
         }
      }
   }
}
