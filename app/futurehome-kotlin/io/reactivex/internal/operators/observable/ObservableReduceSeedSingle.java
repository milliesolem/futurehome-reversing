package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableReduceSeedSingle<T, R> extends Single<R> {
   final BiFunction<R, ? super T, R> reducer;
   final R seed;
   final ObservableSource<T> source;

   public ObservableReduceSeedSingle(ObservableSource<T> var1, R var2, BiFunction<R, ? super T, R> var3) {
      this.source = var1;
      this.seed = (R)var2;
      this.reducer = var3;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super R> var1) {
      this.source.subscribe(new ObservableReduceSeedSingle.ReduceSeedObserver<>(var1, this.reducer, this.seed));
   }

   static final class ReduceSeedObserver<T, R> implements Observer<T>, Disposable {
      final SingleObserver<? super R> downstream;
      final BiFunction<R, ? super T, R> reducer;
      Disposable upstream;
      R value;

      ReduceSeedObserver(SingleObserver<? super R> var1, BiFunction<R, ? super T, R> var2, R var3) {
         this.downstream = var1;
         this.value = (R)var3;
         this.reducer = var2;
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
         Object var1 = this.value;
         if (var1 != null) {
            this.value = null;
            this.downstream.onSuccess((R)var1);
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.value != null) {
            this.value = null;
            this.downstream.onError(var1);
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onNext(T var1) {
         Object var2 = this.value;
         if (var2 != null) {
            try {
               this.value = ObjectHelper.requireNonNull(this.reducer.apply((R)var2, (T)var1), "The reducer returned a null value");
            } catch (Throwable var4) {
               Exceptions.throwIfFatal(var4);
               this.upstream.dispose();
               this.onError(var4);
               return;
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
