package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;

public final class ObservableZipIterable<T, U, V> extends Observable<V> {
   final Iterable<U> other;
   final Observable<? extends T> source;
   final BiFunction<? super T, ? super U, ? extends V> zipper;

   public ObservableZipIterable(Observable<? extends T> var1, Iterable<U> var2, BiFunction<? super T, ? super U, ? extends V> var3) {
      this.source = var1;
      this.other = var2;
      this.zipper = var3;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribeActual(Observer<? super V> var1) {
      Iterator var3;
      try {
         var3 = ObjectHelper.requireNonNull(this.other.iterator(), "The iterator returned by other is null");
      } catch (Throwable var9) {
         Exceptions.throwIfFatal(var9);
         EmptyDisposable.error(var9, var1);
         return;
      }

      boolean var2;
      try {
         var2 = var3.hasNext();
      } catch (Throwable var8) {
         Exceptions.throwIfFatal(var8);
         EmptyDisposable.error(var8, var1);
         return;
      }

      if (!var2) {
         EmptyDisposable.complete(var1);
      } else {
         this.source.subscribe(new ObservableZipIterable.ZipIterableObserver<>(var1, var3, this.zipper));
      }
   }

   static final class ZipIterableObserver<T, U, V> implements Observer<T>, Disposable {
      boolean done;
      final Observer<? super V> downstream;
      final Iterator<U> iterator;
      Disposable upstream;
      final BiFunction<? super T, ? super U, ? extends V> zipper;

      ZipIterableObserver(Observer<? super V> var1, Iterator<U> var2, BiFunction<? super T, ? super U, ? extends V> var3) {
         this.downstream = var1;
         this.iterator = var2;
         this.zipper = var3;
      }

      @Override
      public void dispose() {
         this.upstream.dispose();
      }

      void error(Throwable var1) {
         this.done = true;
         this.upstream.dispose();
         this.downstream.onError(var1);
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
            Object var3;
            try {
               var3 = ObjectHelper.requireNonNull(this.iterator.next(), "The iterator returned a null value");
            } catch (Throwable var15) {
               Exceptions.throwIfFatal(var15);
               this.error(var15);
               return;
            }

            try {
               var1 = ObjectHelper.requireNonNull(this.zipper.apply((T)var1, (U)var3), "The zipper function returned a null value");
            } catch (Throwable var14) {
               Exceptions.throwIfFatal(var14);
               this.error(var14);
               return;
            }

            this.downstream.onNext((V)var1);

            boolean var2;
            try {
               var2 = this.iterator.hasNext();
            } catch (Throwable var13) {
               Exceptions.throwIfFatal(var13);
               this.error(var13);
               return;
            }

            if (!var2) {
               this.done = true;
               this.upstream.dispose();
               this.downstream.onComplete();
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
