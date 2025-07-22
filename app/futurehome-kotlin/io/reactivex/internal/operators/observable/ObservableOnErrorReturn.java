package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;

public final class ObservableOnErrorReturn<T> extends AbstractObservableWithUpstream<T, T> {
   final Function<? super Throwable, ? extends T> valueSupplier;

   public ObservableOnErrorReturn(ObservableSource<T> var1, Function<? super Throwable, ? extends T> var2) {
      super(var1);
      this.valueSupplier = var2;
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(new ObservableOnErrorReturn.OnErrorReturnObserver<>(var1, this.valueSupplier));
   }

   static final class OnErrorReturnObserver<T> implements Observer<T>, Disposable {
      final Observer<? super T> downstream;
      Disposable upstream;
      final Function<? super Throwable, ? extends T> valueSupplier;

      OnErrorReturnObserver(Observer<? super T> var1, Function<? super Throwable, ? extends T> var2) {
         this.downstream = var1;
         this.valueSupplier = var2;
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

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onError(Throwable var1) {
         Object var2;
         try {
            var2 = this.valueSupplier.apply(var1);
         } catch (Throwable var4) {
            Exceptions.throwIfFatal(var4);
            this.downstream.onError(new CompositeException(var1, var4));
            return;
         }

         if (var2 == null) {
            var2 = new NullPointerException("The supplied value is null");
            var2.initCause(var1);
            this.downstream.onError(var2);
         } else {
            this.downstream.onNext((T)var2);
            this.downstream.onComplete();
         }
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
