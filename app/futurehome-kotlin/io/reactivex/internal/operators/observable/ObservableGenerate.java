package io.reactivex.internal.operators.observable;

import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;

public final class ObservableGenerate<T, S> extends Observable<T> {
   final Consumer<? super S> disposeState;
   final BiFunction<S, Emitter<T>, S> generator;
   final Callable<S> stateSupplier;

   public ObservableGenerate(Callable<S> var1, BiFunction<S, Emitter<T>, S> var2, Consumer<? super S> var3) {
      this.stateSupplier = var1;
      this.generator = var2;
      this.disposeState = var3;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribeActual(Observer<? super T> var1) {
      Object var2;
      try {
         var2 = this.stateSupplier.call();
      } catch (Throwable var4) {
         Exceptions.throwIfFatal(var4);
         EmptyDisposable.error(var4, var1);
         return;
      }

      var2 = new ObservableGenerate.GeneratorDisposable<>(var1, this.generator, this.disposeState, (S)var2);
      var1.onSubscribe(var2);
      var2.run();
   }

   static final class GeneratorDisposable<T, S> implements Emitter<T>, Disposable {
      volatile boolean cancelled;
      final Consumer<? super S> disposeState;
      final Observer<? super T> downstream;
      final BiFunction<S, ? super Emitter<T>, S> generator;
      boolean hasNext;
      S state;
      boolean terminate;

      GeneratorDisposable(Observer<? super T> var1, BiFunction<S, ? super Emitter<T>, S> var2, Consumer<? super S> var3, S var4) {
         this.downstream = var1;
         this.generator = var2;
         this.disposeState = var3;
         this.state = (S)var4;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      private void dispose(S var1) {
         try {
            this.disposeState.accept((S)var1);
         } catch (Throwable var3) {
            Exceptions.throwIfFatal(var3);
            RxJavaPlugins.onError(var3);
            return;
         }
      }

      @Override
      public void dispose() {
         this.cancelled = true;
      }

      @Override
      public boolean isDisposed() {
         return this.cancelled;
      }

      @Override
      public void onComplete() {
         if (!this.terminate) {
            this.terminate = true;
            this.downstream.onComplete();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.terminate) {
            RxJavaPlugins.onError(var1);
         } else {
            Object var2 = var1;
            if (var1 == null) {
               var2 = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
            }

            this.terminate = true;
            this.downstream.onError((Throwable)var2);
         }
      }

      @Override
      public void onNext(T var1) {
         if (!this.terminate) {
            if (this.hasNext) {
               this.onError(new IllegalStateException("onNext already called in this generate turn"));
            } else if (var1 == null) {
               this.onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
            } else {
               this.hasNext = true;
               this.downstream.onNext((T)var1);
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void run() {
         Object var1 = this.state;
         if (this.cancelled) {
            this.state = null;
            this.dispose((S)var1);
         } else {
            BiFunction var3 = this.generator;

            while (!this.cancelled) {
               this.hasNext = false;

               Object var2;
               try {
                  var2 = var3.apply(var1, this);
               } catch (Throwable var5) {
                  Exceptions.throwIfFatal(var5);
                  this.state = null;
                  this.cancelled = true;
                  this.onError(var5);
                  this.dispose((S)var1);
                  return;
               }

               var1 = var2;
               if (this.terminate) {
                  this.cancelled = true;
                  this.state = null;
                  this.dispose((S)var2);
                  return;
               }
            }

            this.state = null;
            this.dispose((S)var1);
         }
      }
   }
}
