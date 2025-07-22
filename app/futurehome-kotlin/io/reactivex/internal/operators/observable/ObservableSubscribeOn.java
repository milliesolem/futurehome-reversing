package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableSubscribeOn<T> extends AbstractObservableWithUpstream<T, T> {
   final Scheduler scheduler;

   public ObservableSubscribeOn(ObservableSource<T> var1, Scheduler var2) {
      super(var1);
      this.scheduler = var2;
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      ObservableSubscribeOn.SubscribeOnObserver var2 = new ObservableSubscribeOn.SubscribeOnObserver(var1);
      var1.onSubscribe(var2);
      var2.setDisposable(this.scheduler.scheduleDirect(new ObservableSubscribeOn.SubscribeTask(this, var2)));
   }

   static final class SubscribeOnObserver<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable {
      private static final long serialVersionUID = 8094547886072529208L;
      final Observer<? super T> downstream;
      final AtomicReference<Disposable> upstream;

      SubscribeOnObserver(Observer<? super T> var1) {
         this.downstream = var1;
         this.upstream = new AtomicReference<>();
      }

      @Override
      public void dispose() {
         DisposableHelper.dispose(this.upstream);
         DisposableHelper.dispose(this);
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.get());
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
         this.downstream.onNext((T)var1);
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this.upstream, var1);
      }

      void setDisposable(Disposable var1) {
         DisposableHelper.setOnce(this, var1);
      }
   }

   final class SubscribeTask implements Runnable {
      private final ObservableSubscribeOn.SubscribeOnObserver<T> parent;
      final ObservableSubscribeOn this$0;

      SubscribeTask(ObservableSubscribeOn.SubscribeOnObserver<T> var1, ObservableSubscribeOn.SubscribeOnObserver var2) {
         this.this$0 = var1;
         this.parent = var2;
      }

      @Override
      public void run() {
         this.this$0.source.subscribe(this.parent);
      }
   }
}
