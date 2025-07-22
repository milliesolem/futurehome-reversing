package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.EmptyComponent;

public final class ObservableDetach<T> extends AbstractObservableWithUpstream<T, T> {
   public ObservableDetach(ObservableSource<T> var1) {
      super(var1);
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(new ObservableDetach.DetachObserver<>(var1));
   }

   static final class DetachObserver<T> implements Observer<T>, Disposable {
      Observer<? super T> downstream;
      Disposable upstream;

      DetachObserver(Observer<? super T> var1) {
         this.downstream = var1;
      }

      @Override
      public void dispose() {
         Disposable var1 = this.upstream;
         this.upstream = EmptyComponent.INSTANCE;
         this.downstream = EmptyComponent.asObserver();
         var1.dispose();
      }

      @Override
      public boolean isDisposed() {
         return this.upstream.isDisposed();
      }

      @Override
      public void onComplete() {
         Observer var1 = this.downstream;
         this.upstream = EmptyComponent.INSTANCE;
         this.downstream = EmptyComponent.asObserver();
         var1.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         Observer var2 = this.downstream;
         this.upstream = EmptyComponent.INSTANCE;
         this.downstream = EmptyComponent.asObserver();
         var2.onError(var1);
      }

      @Override
      public void onNext(T var1) {
         this.downstream.onNext((T)var1);
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
