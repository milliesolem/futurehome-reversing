package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.NoSuchElementException;

public final class ObservableLastSingle<T> extends Single<T> {
   final T defaultItem;
   final ObservableSource<T> source;

   public ObservableLastSingle(ObservableSource<T> var1, T var2) {
      this.source = var1;
      this.defaultItem = (T)var2;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      this.source.subscribe(new ObservableLastSingle.LastObserver<>(var1, this.defaultItem));
   }

   static final class LastObserver<T> implements Observer<T>, Disposable {
      final T defaultItem;
      final SingleObserver<? super T> downstream;
      T item;
      Disposable upstream;

      LastObserver(SingleObserver<? super T> var1, T var2) {
         this.downstream = var1;
         this.defaultItem = (T)var2;
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
            var1 = this.defaultItem;
            if (var1 != null) {
               this.downstream.onSuccess((T)var1);
            } else {
               this.downstream.onError(new NoSuchElementException());
            }
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
