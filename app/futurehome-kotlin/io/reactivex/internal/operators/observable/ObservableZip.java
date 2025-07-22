package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableZip<T, R> extends Observable<R> {
   final int bufferSize;
   final boolean delayError;
   final ObservableSource<? extends T>[] sources;
   final Iterable<? extends ObservableSource<? extends T>> sourcesIterable;
   final Function<? super Object[], ? extends R> zipper;

   public ObservableZip(
      ObservableSource<? extends T>[] var1,
      Iterable<? extends ObservableSource<? extends T>> var2,
      Function<? super Object[], ? extends R> var3,
      int var4,
      boolean var5
   ) {
      this.sources = var1;
      this.sourcesIterable = var2;
      this.zipper = var3;
      this.bufferSize = var4;
      this.delayError = var5;
   }

   @Override
   public void subscribeActual(Observer<? super R> var1) {
      ObservableSource[] var5 = this.sources;
      int var3;
      if (var5 == null) {
         ObservableSource[] var4 = new ObservableSource[8];
         Iterator var6 = this.sourcesIterable.iterator();
         int var2 = 0;

         while (true) {
            var5 = var4;
            var3 = var2;
            if (!var6.hasNext()) {
               break;
            }

            ObservableSource var7 = (ObservableSource)var6.next();
            var5 = var4;
            if (var2 == var4.length) {
               var5 = new ObservableSource[(var2 >> 2) + var2];
               System.arraycopy(var4, 0, var5, 0, var2);
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
      } else {
         new ObservableZip.ZipCoordinator<T, R>(var1, this.zipper, var3, this.delayError).subscribe(var5, this.bufferSize);
      }
   }

   static final class ZipCoordinator<T, R> extends AtomicInteger implements Disposable {
      private static final long serialVersionUID = 2983708048395377667L;
      volatile boolean cancelled;
      final boolean delayError;
      final Observer<? super R> downstream;
      final ObservableZip.ZipObserver<T, R>[] observers;
      final T[] row;
      final Function<? super Object[], ? extends R> zipper;

      ZipCoordinator(Observer<? super R> var1, Function<? super Object[], ? extends R> var2, int var3, boolean var4) {
         this.downstream = var1;
         this.zipper = var2;
         this.observers = new ObservableZip.ZipObserver[var3];
         this.row = (T[])(new Object[var3]);
         this.delayError = var4;
      }

      void cancel() {
         this.clear();
         this.cancelSources();
      }

      void cancelSources() {
         ObservableZip.ZipObserver[] var3 = this.observers;
         int var2 = var3.length;

         for (int var1 = 0; var1 < var2; var1++) {
            var3[var1].dispose();
         }
      }

      boolean checkTerminated(boolean var1, boolean var2, Observer<? super R> var3, boolean var4, ObservableZip.ZipObserver<?, ?> var5) {
         if (this.cancelled) {
            this.cancel();
            return true;
         } else {
            if (var1) {
               if (var4) {
                  if (var2) {
                     Throwable var6 = var5.error;
                     this.cancelled = true;
                     this.cancel();
                     if (var6 != null) {
                        var3.onError(var6);
                     } else {
                        var3.onComplete();
                     }

                     return true;
                  }
               } else {
                  Throwable var7 = var5.error;
                  if (var7 != null) {
                     this.cancelled = true;
                     this.cancel();
                     var3.onError(var7);
                     return true;
                  }

                  if (var2) {
                     this.cancelled = true;
                     this.cancel();
                     var3.onComplete();
                     return true;
                  }
               }
            }

            return false;
         }
      }

      void clear() {
         ObservableZip.ZipObserver[] var3 = this.observers;
         int var2 = var3.length;

         for (int var1 = 0; var1 < var2; var1++) {
            var3[var1].queue.clear();
         }
      }

      @Override
      public void dispose() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.cancelSources();
            if (this.getAndIncrement() == 0) {
               this.clear();
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public void drain() {
         if (this.getAndIncrement() == 0) {
            ObservableZip.ZipObserver[] var12 = this.observers;
            Observer var10 = this.downstream;
            Object[] var11 = this.row;
            boolean var9 = this.delayError;
            int var1 = 1;

            while (true) {
               int var6 = var12.length;
               int var3 = 0;
               int var5 = 0;
               int var2 = 0;

               while (var3 < var6) {
                  ObservableZip.ZipObserver var13 = var12[var3];
                  int var4;
                  if (var11[var2] == null) {
                     boolean var8 = var13.done;
                     Object var14 = var13.queue.poll();
                     boolean var7;
                     if (var14 == null) {
                        var7 = true;
                     } else {
                        var7 = false;
                     }

                     if (this.checkTerminated(var8, var7, var10, var9, var13)) {
                        return;
                     }

                     if (!var7) {
                        var11[var2] = var14;
                        var4 = var5;
                     } else {
                        var4 = var5 + 1;
                     }
                  } else {
                     var4 = var5;
                     if (var13.done) {
                        var4 = var5;
                        if (!var9) {
                           Throwable var18 = var13.error;
                           var4 = var5;
                           if (var18 != null) {
                              this.cancelled = true;
                              this.cancel();
                              var10.onError(var18);
                              return;
                           }
                        }
                     }
                  }

                  var2++;
                  var3++;
                  var5 = var4;
               }

               if (var5 != 0) {
                  var2 = this.addAndGet(-var1);
                  var1 = var2;
                  if (var2 == 0) {
                     return;
                  }
               } else {
                  Object var19;
                  try {
                     var19 = ObjectHelper.requireNonNull(this.zipper.apply((Object[])var11.clone()), "The zipper returned a null value");
                  } catch (Throwable var16) {
                     Exceptions.throwIfFatal(var16);
                     this.cancel();
                     var10.onError(var16);
                     return;
                  }

                  var10.onNext(var19);
                  Arrays.fill(var11, null);
               }
            }
         }
      }

      @Override
      public boolean isDisposed() {
         return this.cancelled;
      }

      public void subscribe(ObservableSource<? extends T>[] var1, int var2) {
         ObservableZip.ZipObserver[] var6 = this.observers;
         int var5 = var6.length;
         byte var4 = 0;

         for (int var3 = 0; var3 < var5; var3++) {
            var6[var3] = new ObservableZip.ZipObserver<>(this, var2);
         }

         this.lazySet(0);
         this.downstream.onSubscribe(this);

         for (int var7 = var4; var7 < var5; var7++) {
            if (this.cancelled) {
               return;
            }

            var1[var7].subscribe(var6[var7]);
         }
      }
   }

   static final class ZipObserver<T, R> implements Observer<T> {
      volatile boolean done;
      Throwable error;
      final ObservableZip.ZipCoordinator<T, R> parent;
      final SpscLinkedArrayQueue<T> queue;
      final AtomicReference<Disposable> upstream = new AtomicReference<>();

      ZipObserver(ObservableZip.ZipCoordinator<T, R> var1, int var2) {
         this.parent = var1;
         this.queue = new SpscLinkedArrayQueue<>(var2);
      }

      public void dispose() {
         DisposableHelper.dispose(this.upstream);
      }

      @Override
      public void onComplete() {
         this.done = true;
         this.parent.drain();
      }

      @Override
      public void onError(Throwable var1) {
         this.error = var1;
         this.done = true;
         this.parent.drain();
      }

      @Override
      public void onNext(T var1) {
         this.queue.offer((T)var1);
         this.parent.drain();
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this.upstream, var1);
      }
   }
}
