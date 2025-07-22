package io.reactivex.internal.operators.observable;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;

public final class ObservableLastMaybe<T> extends Maybe<T> {
   final ObservableSource<T> source;

   public ObservableLastMaybe(ObservableSource<T> var1) {
      this.source = var1;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      this.source.subscribe(new ObservableLastMaybe.LastObserver<>(var1));
   }

   static final class LastObserver<T> implements Observer<T>, Disposable {
      final MaybeObserver<? super T> downstream;
      T item;
      Disposable upstream;

      LastObserver(MaybeObserver<? super T> var1) {
         this.downstream = var1;
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
         this.upstream = DisposableHelper.DISPOSED;
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.upstream == DisposableHelper.DISPOSED) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      @Override
      public void onComplete() {
         this.upstream = DisposableHelper.DISPOSED;
         Object var1 = this.item;
         if (var1 != null) {
            this.item = null;
            this.downstream.onSuccess((T)var1);
         } else {
            this.downstream.onComplete();
         }
      }

      @Override
      public void onError(Throwable var1) {
         this.upstream = DisposableHelper.DISPOSED;
         this.item = null;
         this.downstream.onError(var1);
      }

      @Override
      public void onNext(T var1) {
         this.item = (T)var1;
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
