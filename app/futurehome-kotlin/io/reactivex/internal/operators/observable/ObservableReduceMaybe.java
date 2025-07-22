package io.reactivex.internal.operators.observable;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableReduceMaybe<T> extends Maybe<T> {
   final BiFunction<T, T, T> reducer;
   final ObservableSource<T> source;

   public ObservableReduceMaybe(ObservableSource<T> var1, BiFunction<T, T, T> var2) {
      this.source = var1;
      this.reducer = var2;
   }

   @Override
   protected void subscribeActual(MaybeObserver<? super T> var1) {
      this.source.subscribe(new ObservableReduceMaybe.ReduceObserver<>(var1, this.reducer));
   }

   static final class ReduceObserver<T> implements Observer<T>, Disposable {
      boolean done;
      final MaybeObserver<? super T> downstream;
      final BiFunction<T, T, T> reducer;
      Disposable upstream;
      T value;

      ReduceObserver(MaybeObserver<? super T> var1, BiFunction<T, T, T> var2) {
         this.downstream = var1;
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
         if (!this.done) {
            this.done = true;
            Object var1 = this.value;
            this.value = null;
            if (var1 != null) {
               this.downstream.onSuccess((T)var1);
            } else {
               this.downstream.onComplete();
            }
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.value = null;
            this.downstream.onError(var1);
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onNext(T var1) {
         if (!this.done) {
            Object var2 = this.value;
            if (var2 == null) {
               this.value = (T)var1;
            } else {
               try {
                  this.value = ObjectHelper.requireNonNull(this.reducer.apply((T)var2, (T)var1), "The reducer returned a null value");
               } catch (Throwable var4) {
                  Exceptions.throwIfFatal(var4);
                  this.upstream.dispose();
                  this.onError(var4);
                  return;
               }
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
