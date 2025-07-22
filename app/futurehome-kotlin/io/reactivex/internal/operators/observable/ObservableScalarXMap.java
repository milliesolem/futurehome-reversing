package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public final class ObservableScalarXMap {
   private ObservableScalarXMap() {
      throw new IllegalStateException("No instances!");
   }

   public static <T, U> Observable<U> scalarXMap(T var0, Function<? super T, ? extends ObservableSource<? extends U>> var1) {
      return RxJavaPlugins.onAssembly(new ObservableScalarXMap.ScalarXMapObservable<>(var0, var1));
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static <T, R> boolean tryScalarXMapSubscribe(
      ObservableSource<T> var0, Observer<? super R> var1, Function<? super T, ? extends ObservableSource<? extends R>> var2
   ) {
      if (var0 instanceof Callable) {
         try {
            var15 = ((Callable)var0).call();
         } catch (Throwable var14) {
            Exceptions.throwIfFatal(var14);
            EmptyDisposable.error(var14, var1);
            return true;
         }

         if (var15 == null) {
            EmptyDisposable.complete(var1);
            return true;
         } else {
            try {
               var0 = ObjectHelper.requireNonNull((ObservableSource)var2.apply(var15), "The mapper returned a null ObservableSource");
            } catch (Throwable var13) {
               Exceptions.throwIfFatal(var13);
               EmptyDisposable.error(var13, var1);
               return true;
            }

            if (var0 instanceof Callable) {
               try {
                  var17 = ((Callable)var0).call();
               } catch (Throwable var12) {
                  Exceptions.throwIfFatal(var12);
                  EmptyDisposable.error(var12, var1);
                  return true;
               }

               if (var17 == null) {
                  EmptyDisposable.complete(var1);
                  return true;
               }

               ObservableScalarXMap.ScalarDisposable var18 = new ObservableScalarXMap.ScalarDisposable<>(var1, var17);
               var1.onSubscribe(var18);
               var18.run();
            } else {
               var0.subscribe(var1);
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public static final class ScalarDisposable<T> extends AtomicInteger implements QueueDisposable<T>, Runnable {
      static final int FUSED = 1;
      static final int ON_COMPLETE = 3;
      static final int ON_NEXT = 2;
      static final int START = 0;
      private static final long serialVersionUID = 3880992722410194083L;
      final Observer<? super T> observer;
      final T value;

      public ScalarDisposable(Observer<? super T> var1, T var2) {
         this.observer = var1;
         this.value = (T)var2;
      }

      @Override
      public void clear() {
         this.lazySet(3);
      }

      @Override
      public void dispose() {
         this.set(3);
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.get() == 3) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      @Override
      public boolean isEmpty() {
         int var1 = this.get();
         boolean var2 = true;
         if (var1 == 1) {
            var2 = false;
         }

         return var2;
      }

      @Override
      public boolean offer(T var1) {
         throw new UnsupportedOperationException("Should not be called!");
      }

      @Override
      public boolean offer(T var1, T var2) {
         throw new UnsupportedOperationException("Should not be called!");
      }

      @Override
      public T poll() throws Exception {
         if (this.get() == 1) {
            this.lazySet(3);
            return this.value;
         } else {
            return null;
         }
      }

      @Override
      public int requestFusion(int var1) {
         if ((var1 & 1) != 0) {
            this.lazySet(1);
            return 1;
         } else {
            return 0;
         }
      }

      @Override
      public void run() {
         if (this.get() == 0 && this.compareAndSet(0, 2)) {
            this.observer.onNext(this.value);
            if (this.get() == 2) {
               this.lazySet(3);
               this.observer.onComplete();
            }
         }
      }
   }

   static final class ScalarXMapObservable<T, R> extends Observable<R> {
      final Function<? super T, ? extends ObservableSource<? extends R>> mapper;
      final T value;

      ScalarXMapObservable(T var1, Function<? super T, ? extends ObservableSource<? extends R>> var2) {
         this.value = (T)var1;
         this.mapper = var2;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void subscribeActual(Observer<? super R> var1) {
         ObservableSource var2;
         try {
            var2 = ObjectHelper.requireNonNull(this.mapper.apply(this.value), "The mapper returned a null ObservableSource");
         } catch (Throwable var8) {
            EmptyDisposable.error(var8, var1);
            return;
         }

         if (var2 instanceof Callable) {
            try {
               var9 = ((Callable)var2).call();
            } catch (Throwable var7) {
               Exceptions.throwIfFatal(var7);
               EmptyDisposable.error(var7, var1);
               return;
            }

            if (var9 == null) {
               EmptyDisposable.complete(var1);
               return;
            }

            ObservableScalarXMap.ScalarDisposable var10 = new ObservableScalarXMap.ScalarDisposable<>(var1, var9);
            var1.onSubscribe(var10);
            var10.run();
         } else {
            var2.subscribe(var1);
         }
      }
   }
}
