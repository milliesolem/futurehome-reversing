package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableAmb<T> extends Observable<T> {
   final ObservableSource<? extends T>[] sources;
   final Iterable<? extends ObservableSource<? extends T>> sourcesIterable;

   public ObservableAmb(ObservableSource<? extends T>[] var1, Iterable<? extends ObservableSource<? extends T>> var2) {
      this.sources = var1;
      this.sourcesIterable = var2;
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void subscribeActual(Observer<? super T> var1) {
      ObservableSource[] var5 = this.sources;
      int var3;
      if (var5 == null) {
         ObservableSource[] var4 = new ObservableSource[8];

         Iterator var6;
         try {
            var6 = this.sourcesIterable.iterator();
         } catch (Throwable var25) {
            Exceptions.throwIfFatal(var25);
            EmptyDisposable.error(var25, var1);
            return;
         }

         int var2 = 0;

         while (true) {
            var5 = var4;
            var3 = var2;

            ObservableSource var7;
            try {
               if (!var6.hasNext()) {
                  break;
               }

               var7 = (ObservableSource)var6.next();
            } catch (Throwable var27) {
               Exceptions.throwIfFatal(var27);
               EmptyDisposable.error(var27, var1);
               return;
            }

            if (var7 == null) {
               try {
                  NullPointerException var28 = new NullPointerException("One of the sources is null");
                  EmptyDisposable.error(var28, var1);
                  return;
               } catch (Throwable var24) {
                  Exceptions.throwIfFatal(var24);
                  EmptyDisposable.error(var24, var1);
                  return;
               }
            }

            var5 = var4;

            try {
               if (var2 == var4.length) {
                  var5 = new ObservableSource[(var2 >> 2) + var2];
                  System.arraycopy(var4, 0, var5, 0, var2);
               }
            } catch (Throwable var26) {
               Exceptions.throwIfFatal(var26);
               EmptyDisposable.error(var26, var1);
               return;
            }

            var5[var2] = var7;
            var2++;
            var4 = var5;
         }
      } else {
         var3 = var5.length;
      }

      if (var3 == 0) {
         EmptyDisposable.complete(var1);
      } else if (var3 == 1) {
         var5[0].subscribe(var1);
      } else {
         new ObservableAmb.AmbCoordinator(var1, var3).subscribe(var5);
      }
   }

   static final class AmbCoordinator<T> implements Disposable {
      final Observer<? super T> downstream;
      final ObservableAmb.AmbInnerObserver<T>[] observers;
      final AtomicInteger winner = new AtomicInteger();

      AmbCoordinator(Observer<? super T> var1, int var2) {
         this.downstream = var1;
         this.observers = new ObservableAmb.AmbInnerObserver[var2];
      }

      @Override
      public void dispose() {
         if (this.winner.get() != -1) {
            this.winner.lazySet(-1);
            ObservableAmb.AmbInnerObserver[] var3 = this.observers;
            int var2 = var3.length;

            for (int var1 = 0; var1 < var2; var1++) {
               var3[var1].dispose();
            }
         }
      }

      @Override
      public boolean isDisposed() {
         boolean var1;
         if (this.winner.get() == -1) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public void subscribe(ObservableSource<? extends T>[] var1) {
         ObservableAmb.AmbInnerObserver[] var6 = this.observers;
         int var5 = var6.length;
         byte var3 = 0;
         int var2 = 0;

         while (var2 < var5) {
            int var4 = var2 + 1;
            var6[var2] = new ObservableAmb.AmbInnerObserver<>(this, var4, this.downstream);
            var2 = var4;
         }

         this.winner.lazySet(0);
         this.downstream.onSubscribe(this);

         for (int var7 = var3; var7 < var5; var7++) {
            if (this.winner.get() != 0) {
               return;
            }

            var1[var7].subscribe(var6[var7]);
         }
      }

      public boolean win(int var1) {
         int var3 = this.winner.get();
         boolean var5 = true;
         int var2 = 0;
         if (var3 == 0) {
            if (this.winner.compareAndSet(0, var1)) {
               ObservableAmb.AmbInnerObserver[] var6 = this.observers;
               int var4 = var6.length;

               while (var2 < var4) {
                  var3 = var2 + 1;
                  if (var3 != var1) {
                     var6[var2].dispose();
                  }

                  var2 = var3;
               }

               return true;
            } else {
               return false;
            }
         } else {
            if (var3 != var1) {
               var5 = false;
            }

            return var5;
         }
      }
   }

   static final class AmbInnerObserver<T> extends AtomicReference<Disposable> implements Observer<T> {
      private static final long serialVersionUID = -1185974347409665484L;
      final Observer<? super T> downstream;
      final int index;
      final ObservableAmb.AmbCoordinator<T> parent;
      boolean won;

      AmbInnerObserver(ObservableAmb.AmbCoordinator<T> var1, int var2, Observer<? super T> var3) {
         this.parent = var1;
         this.index = var2;
         this.downstream = var3;
      }

      public void dispose() {
         DisposableHelper.dispose(this);
      }

      @Override
      public void onComplete() {
         if (this.won) {
            this.downstream.onComplete();
         } else if (this.parent.win(this.index)) {
            this.won = true;
            this.downstream.onComplete();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.won) {
            this.downstream.onError(var1);
         } else if (this.parent.win(this.index)) {
            this.won = true;
            this.downstream.onError(var1);
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void onNext(T var1) {
         if (this.won) {
            this.downstream.onNext((T)var1);
         } else if (this.parent.win(this.index)) {
            this.won = true;
            this.downstream.onNext((T)var1);
         } else {
            this.get().dispose();
         }
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this, var1);
      }
   }
}
