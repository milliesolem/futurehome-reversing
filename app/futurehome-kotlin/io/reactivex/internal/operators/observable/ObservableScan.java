package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableScan<T> extends AbstractObservableWithUpstream<T, T> {
   final BiFunction<T, T, T> accumulator;

   public ObservableScan(ObservableSource<T> var1, BiFunction<T, T, T> var2) {
      super(var1);
      this.accumulator = var2;
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(new ObservableScan.ScanObserver<>(var1, this.accumulator));
   }

   static final class ScanObserver<T> implements Observer<T>, Disposable {
      final BiFunction<T, T, T> accumulator;
      boolean done;
      final Observer<? super T> downstream;
      Disposable upstream;
      T value;

      ScanObserver(Observer<? super T> var1, BiFunction<T, T, T> var2) {
         this.downstream = var1;
         this.accumulator = var2;
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
            Observer var2 = this.downstream;
            Object var3 = this.value;
            if (var3 == null) {
               this.value = (T)var1;
               var2.onNext(var1);
            } else {
               try {
                  var1 = ObjectHelper.requireNonNull(this.accumulator.apply((T)var3, (T)var1), "The value returned by the accumulator is null");
               } catch (Throwable var5) {
                  Exceptions.throwIfFatal(var5);
                  this.upstream.dispose();
                  this.onError(var5);
                  return;
               }

               this.value = (T)var1;
               var2.onNext(var1);
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
