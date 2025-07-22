package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiPredicate;
import io.reactivex.internal.disposables.ArrayCompositeDisposable;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;

public final class ObservableSequenceEqualSingle<T> extends Single<Boolean> implements FuseToObservable<Boolean> {
   final int bufferSize;
   final BiPredicate<? super T, ? super T> comparer;
   final ObservableSource<? extends T> first;
   final ObservableSource<? extends T> second;

   public ObservableSequenceEqualSingle(
      ObservableSource<? extends T> var1, ObservableSource<? extends T> var2, BiPredicate<? super T, ? super T> var3, int var4
   ) {
      this.first = var1;
      this.second = var2;
      this.comparer = var3;
      this.bufferSize = var4;
   }

   @Override
   public Observable<Boolean> fuseToObservable() {
      return RxJavaPlugins.onAssembly(new ObservableSequenceEqual<>(this.first, this.second, this.comparer, this.bufferSize));
   }

   @Override
   public void subscribeActual(SingleObserver<? super Boolean> var1) {
      ObservableSequenceEqualSingle.EqualCoordinator var2 = new ObservableSequenceEqualSingle.EqualCoordinator<>(
         var1, this.bufferSize, this.first, this.second, this.comparer
      );
      var1.onSubscribe(var2);
      var2.subscribe();
   }

   static final class EqualCoordinator<T> extends AtomicInteger implements Disposable {
      private static final long serialVersionUID = -6178010334400373240L;
      volatile boolean cancelled;
      final BiPredicate<? super T, ? super T> comparer;
      final SingleObserver<? super Boolean> downstream;
      final ObservableSource<? extends T> first;
      final ObservableSequenceEqualSingle.EqualObserver<T>[] observers;
      final ArrayCompositeDisposable resources;
      final ObservableSource<? extends T> second;
      T v1;
      T v2;

      EqualCoordinator(
         SingleObserver<? super Boolean> var1,
         int var2,
         ObservableSource<? extends T> var3,
         ObservableSource<? extends T> var4,
         BiPredicate<? super T, ? super T> var5
      ) {
         this.downstream = var1;
         this.first = var3;
         this.second = var4;
         this.comparer = var5;
         ObservableSequenceEqualSingle.EqualObserver[] var6 = new ObservableSequenceEqualSingle.EqualObserver[2];
         this.observers = var6;
         var6[0] = new ObservableSequenceEqualSingle.EqualObserver<>(this, 0, var2);
         var6[1] = new ObservableSequenceEqualSingle.EqualObserver<>(this, 1, var2);
         this.resources = new ArrayCompositeDisposable(2);
      }

      void cancel(SpscLinkedArrayQueue<T> var1, SpscLinkedArrayQueue<T> var2) {
         this.cancelled = true;
         var1.clear();
         var2.clear();
      }

      @Override
      public void dispose() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.resources.dispose();
            if (this.getAndIncrement() == 0) {
               ObservableSequenceEqualSingle.EqualObserver[] var1 = this.observers;
               var1[0].queue.clear();
               var1[1].queue.clear();
            }
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void drain() {
         if (this.getAndIncrement() == 0) {
            ObservableSequenceEqualSingle.EqualObserver[] var8 = this.observers;
            ObservableSequenceEqualSingle.EqualObserver var7 = var8[0];
            SpscLinkedArrayQueue var6 = var7.queue;
            ObservableSequenceEqualSingle.EqualObserver var9 = var8[1];
            SpscLinkedArrayQueue var15 = var9.queue;
            int var1 = 1;

            while (!this.cancelled) {
               boolean var4 = var7.done;
               if (var4) {
                  Throwable var10 = var7.error;
                  if (var10 != null) {
                     this.cancel(var6, var15);
                     this.downstream.onError(var10);
                     return;
                  }
               }

               boolean var5 = var9.done;
               if (var5) {
                  Throwable var16 = var9.error;
                  if (var16 != null) {
                     this.cancel(var6, var15);
                     this.downstream.onError(var16);
                     return;
                  }
               }

               if (this.v1 == null) {
                  this.v1 = (T)var6.poll();
               }

               boolean var2;
               if (this.v1 == null) {
                  var2 = 1;
               } else {
                  var2 = 0;
               }

               if (this.v2 == null) {
                  this.v2 = (T)var15.poll();
               }

               Object var17 = this.v2;
               boolean var3;
               if (var17 == null) {
                  var3 = true;
               } else {
                  var3 = false;
               }

               if (var4 && var5 && var2 && var3) {
                  this.downstream.onSuccess(true);
                  return;
               }

               if (var4 && var5 && var2 != var3) {
                  this.cancel(var6, var15);
                  this.downstream.onSuccess(false);
                  return;
               }

               if (!var2 && !var3) {
                  try {
                     var4 = this.comparer.test(this.v1, (T)var17);
                  } catch (Throwable var12) {
                     Exceptions.throwIfFatal(var12);
                     this.cancel(var6, var15);
                     this.downstream.onError(var12);
                     return;
                  }

                  if (!var4) {
                     this.cancel(var6, var15);
                     this.downstream.onSuccess(false);
                     return;
                  }

                  this.v1 = null;
                  this.v2 = null;
               }

               if (var2 || var3) {
                  var2 = this.addAndGet(-var1);
                  var1 = var2;
                  if (var2 == 0) {
                     return;
                  }
               }
            }

            var6.clear();
            var15.clear();
         }
      }

      @Override
      public boolean isDisposed() {
         return this.cancelled;
      }

      boolean setDisposable(Disposable var1, int var2) {
         return this.resources.setResource(var2, var1);
      }

      void subscribe() {
         ObservableSequenceEqualSingle.EqualObserver[] var1 = this.observers;
         this.first.subscribe(var1[0]);
         this.second.subscribe(var1[1]);
      }
   }

   static final class EqualObserver<T> implements Observer<T> {
      volatile boolean done;
      Throwable error;
      final int index;
      final ObservableSequenceEqualSingle.EqualCoordinator<T> parent;
      final SpscLinkedArrayQueue<T> queue;

      EqualObserver(ObservableSequenceEqualSingle.EqualCoordinator<T> var1, int var2, int var3) {
         this.parent = var1;
         this.index = var2;
         this.queue = new SpscLinkedArrayQueue<>(var3);
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
         this.parent.setDisposable(var1, this.index);
      }
   }
}
