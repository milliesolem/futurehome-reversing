package io.reactivex.internal.operators.observable;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableSingleMaybe<T> extends Maybe<T> {
   final ObservableSource<T> source;

   public ObservableSingleMaybe(ObservableSource<T> var1) {
      this.source = var1;
   }

   @Override
   public void subscribeActual(MaybeObserver<? super T> var1) {
      this.source.subscribe(new ObservableSingleMaybe.SingleElementObserver<>(var1));
   }

   static final class SingleElementObserver<T> implements Observer<T>, Disposable {
      boolean done;
      final MaybeObserver<? super T> downstream;
      Disposable upstream;
      T value;

      SingleElementObserver(MaybeObserver<? super T> var1) {
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
         if (!this.done) {
            this.done = true;
            Object var1 = this.value;
            this.value = null;
            if (var1 == null) {
               this.downstream.onComplete();
            } else {
               this.downstream.onSuccess((T)var1);
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
