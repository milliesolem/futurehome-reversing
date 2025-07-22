package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;

public final class ObservableSingleSingle<T> extends Single<T> {
   final T defaultValue;
   final ObservableSource<? extends T> source;

   public ObservableSingleSingle(ObservableSource<? extends T> var1, T var2) {
      this.source = var1;
      this.defaultValue = (T)var2;
   }

   @Override
   public void subscribeActual(SingleObserver<? super T> var1) {
      this.source.subscribe(new ObservableSingleSingle.SingleElementObserver<>(var1, this.defaultValue));
   }

   static final class SingleElementObserver<T> implements Observer<T>, Disposable {
      final T defaultValue;
      boolean done;
      final SingleObserver<? super T> downstream;
      Disposable upstream;
      T value;

      SingleElementObserver(SingleObserver<? super T> var1, T var2) {
         this.downstream = var1;
         this.defaultValue = (T)var2;
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
         if (!this.done) {
            this.done = true;
            Object var2 = this.value;
            this.value = null;
            Object var1 = var2;
            if (var2 == null) {
               var1 = this.defaultValue;
            }

            if (var1 != null) {
               this.downstream.onSuccess((T)var1);
            } else {
               this.downstream.onError(new NoSuchElementException());
            }
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.downstream.onError(var1);
         }
      }

      @Override
      public void onNext(T var1) {
         if (!this.done) {
            if (this.value != null) {
               this.done = true;
               this.upstream.dispose();
               this.downstream.onError(new IllegalArgumentException("Sequence contains more than one element!"));
            } else {
               this.value = (T)var1;
            }
         }
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
