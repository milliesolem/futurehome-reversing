package io.reactivex.internal.operators.observable;

import io.reactivex.Notification;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public final class ObservableMaterialize<T> extends AbstractObservableWithUpstream<T, Notification<T>> {
   public ObservableMaterialize(ObservableSource<T> var1) {
      super(var1);
   }

   @Override
   public void subscribeActual(Observer<? super Notification<T>> var1) {
      this.source.subscribe(new ObservableMaterialize.MaterializeObserver<>(var1));
   }

   static final class MaterializeObserver<T> implements Observer<T>, Disposable {
      final Observer<? super Notification<T>> downstream;
      Disposable upstream;

      MaterializeObserver(Observer<? super Notification<T>> var1) {
         this.downstream = var1;
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
         Notification var1 = Notification.createOnComplete();
         this.downstream.onNext(var1);
         this.downstream.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         Notification var2 = Notification.createOnError(var1);
         this.downstream.onNext(var2);
         this.downstream.onComplete();
      }

      @Override
      public void onNext(T var1) {
         this.downstream.onNext(Notification.createOnNext((T)var1));
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }
   }
}
