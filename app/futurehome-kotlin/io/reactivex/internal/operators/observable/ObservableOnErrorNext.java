package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableOnErrorNext<T> extends AbstractObservableWithUpstream<T, T> {
   final boolean allowFatal;
   final Function<? super Throwable, ? extends ObservableSource<? extends T>> nextSupplier;

   public ObservableOnErrorNext(ObservableSource<T> var1, Function<? super Throwable, ? extends ObservableSource<? extends T>> var2, boolean var3) {
      super(var1);
      this.nextSupplier = var2;
      this.allowFatal = var3;
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      ObservableOnErrorNext.OnErrorNextObserver var2 = new ObservableOnErrorNext.OnErrorNextObserver<>(var1, this.nextSupplier, this.allowFatal);
      var1.onSubscribe(var2.arbiter);
      this.source.subscribe(var2);
   }

   static final class OnErrorNextObserver<T> implements Observer<T> {
      final boolean allowFatal;
      final SequentialDisposable arbiter;
      boolean done;
      final Observer<? super T> downstream;
      final Function<? super Throwable, ? extends ObservableSource<? extends T>> nextSupplier;
      boolean once;

      OnErrorNextObserver(Observer<? super T> var1, Function<? super Throwable, ? extends ObservableSource<? extends T>> var2, boolean var3) {
         this.downstream = var1;
         this.nextSupplier = var2;
         this.allowFatal = var3;
         this.arbiter = new SequentialDisposable();
      }

      @Override
      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.once = true;
            this.downstream.onComplete();
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void onError(Throwable var1) {
         if (this.once) {
            if (this.done) {
               RxJavaPlugins.onError(var1);
            } else {
               this.downstream.onError(var1);
            }
         } else {
            this.once = true;
            if (this.allowFatal && !(var1 instanceof Exception)) {
               this.downstream.onError(var1);
            } else {
               ObservableSource var2;
               try {
                  var2 = this.nextSupplier.apply(var1);
               } catch (Throwable var4) {
                  Exceptions.throwIfFatal(var4);
                  this.downstream.onError(new CompositeException(var1, var4));
                  return;
               }

               if (var2 == null) {
                  NullPointerException var5 = new NullPointerException("Observable is null");
                  var5.initCause(var1);
                  this.downstream.onError(var5);
               } else {
                  var2.subscribe(this);
               }
            }
         }
      }

      @Override
      public void onNext(T var1) {
         if (!this.done) {
            this.downstream.onNext((T)var1);
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         this.arbiter.replace(var1);
      }
   }
}
