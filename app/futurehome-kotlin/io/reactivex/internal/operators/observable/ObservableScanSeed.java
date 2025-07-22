package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;

public final class ObservableScanSeed<T, R> extends AbstractObservableWithUpstream<T, R> {
   final BiFunction<R, ? super T, R> accumulator;
   final Callable<R> seedSupplier;

   public ObservableScanSeed(ObservableSource<T> var1, Callable<R> var2, BiFunction<R, ? super T, R> var3) {
      super(var1);
      this.accumulator = var3;
      this.seedSupplier = var2;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribeActual(Observer<? super R> var1) {
      Object var2;
      try {
         var2 = ObjectHelper.requireNonNull(this.seedSupplier.call(), "The seed supplied is null");
      } catch (Throwable var4) {
         Exceptions.throwIfFatal(var4);
         EmptyDisposable.error(var4, var1);
         return;
      }

      this.source.subscribe(new ObservableScanSeed.ScanSeedObserver<>(var1, this.accumulator, (R)var2));
   }

   static final class ScanSeedObserver<T, R> implements Observer<T>, Disposable {
      final BiFunction<R, ? super T, R> accumulator;
      boolean done;
      final Observer<? super R> downstream;
      Disposable upstream;
      R value;

      ScanSeedObserver(Observer<? super R> var1, BiFunction<R, ? super T, R> var2, R var3) {
         this.downstream = var1;
         this.accumulator = var2;
         this.value = (R)var3;
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
            this.downstream.onComplete();
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

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onNext(T var1) {
         if (!this.done) {
            Object var2 = this.value;

            try {
               var1 = ObjectHelper.requireNonNull(this.accumulator.apply((R)var2, (T)var1), "The accumulator returned a null value");
            } catch (Throwable var4) {
               Exceptions.throwIfFatal(var4);
               this.upstream.dispose();
               this.onError(var4);
               return;
            }

            this.value = (R)var1;
            this.downstream.onNext((R)var1);
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            this.downstream.onNext(this.value);
         }
      }
   }
}
