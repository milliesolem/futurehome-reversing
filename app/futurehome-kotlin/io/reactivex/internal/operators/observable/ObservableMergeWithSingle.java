package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableMergeWithSingle<T> extends AbstractObservableWithUpstream<T, T> {
   final SingleSource<? extends T> other;

   public ObservableMergeWithSingle(Observable<T> var1, SingleSource<? extends T> var2) {
      super(var1);
      this.other = var2;
   }

   @Override
   protected void subscribeActual(Observer<? super T> var1) {
      ObservableMergeWithSingle.MergeWithObserver var2 = new ObservableMergeWithSingle.MergeWithObserver(var1);
      var1.onSubscribe(var2);
      this.source.subscribe(var2);
      this.other.subscribe(var2.otherObserver);
   }

   static final class MergeWithObserver<T> extends AtomicInteger implements Observer<T>, Disposable {
      static final int OTHER_STATE_CONSUMED_OR_EMPTY = 2;
      static final int OTHER_STATE_HAS_VALUE = 1;
      private static final long serialVersionUID = -4592979584110982903L;
      volatile boolean disposed;
      final Observer<? super T> downstream;
      final AtomicThrowable error;
      final AtomicReference<Disposable> mainDisposable;
      volatile boolean mainDone;
      final ObservableMergeWithSingle.MergeWithObserver.OtherObserver<T> otherObserver;
      volatile int otherState;
      volatile SimplePlainQueue<T> queue;
      T singleItem;

      MergeWithObserver(Observer<? super T> var1) {
         this.downstream = var1;
         this.mainDisposable = new AtomicReference<>();
         this.otherObserver = new ObservableMergeWithSingle.MergeWithObserver.OtherObserver<>(this);
         this.error = new AtomicThrowable();
      }

      @Override
      public void dispose() {
         this.disposed = true;
         DisposableHelper.dispose(this.mainDisposable);
         DisposableHelper.dispose(this.otherObserver);
         if (this.getAndIncrement() == 0) {
            this.queue = null;
            this.singleItem = null;
         }
      }

      void drain() {
         if (this.getAndIncrement() == 0) {
            this.drainLoop();
         }
      }

      void drainLoop() {
         Observer var6 = this.downstream;
         int var1 = 1;

         while (!this.disposed) {
            if (this.error.get() != null) {
               this.singleItem = null;
               this.queue = null;
               var6.onError(this.error.terminate());
               return;
            }

            int var3 = this.otherState;
            int var2 = var3;
            if (var3 == 1) {
               Object var5 = this.singleItem;
               this.singleItem = null;
               this.otherState = 2;
               var6.onNext(var5);
               var2 = 2;
            }

            boolean var4 = this.mainDone;
            SimplePlainQueue var9 = this.queue;
            Object var10;
            if (var9 != null) {
               var10 = var9.poll();
            } else {
               var10 = null;
            }

            boolean var8;
            if (var10 == null) {
               var8 = true;
            } else {
               var8 = false;
            }

            if (var4 && var8 && var2 == 2) {
               this.queue = null;
               var6.onComplete();
               return;
            }

            if (var8) {
               var2 = this.addAndGet(-var1);
               var1 = var2;
               if (var2 == 0) {
                  return;
               }
            } else {
               var6.onNext(var10);
            }
         }

         this.singleItem = null;
         this.queue = null;
      }

      SimplePlainQueue<T> getOrCreateQueue() {
         SimplePlainQueue var2 = this.queue;
         Object var1 = var2;
         if (var2 == null) {
            var1 = new SpscLinkedArrayQueue(Observable.bufferSize());
            this.queue = (SimplePlainQueue<T>)var1;
         }

         return (SimplePlainQueue<T>)var1;
      }

      @Override
      public boolean isDisposed() {
         return DisposableHelper.isDisposed(this.mainDisposable.get());
      }

      @Override
      public void onComplete() {
         this.mainDone = true;
         this.drain();
      }

      @Override
      public void onError(Throwable var1) {
         if (this.error.addThrowable(var1)) {
            DisposableHelper.dispose(this.otherObserver);
            this.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void onNext(T var1) {
         if (this.compareAndSet(0, 1)) {
            this.downstream.onNext((T)var1);
            if (this.decrementAndGet() == 0) {
               return;
            }
         } else {
            this.getOrCreateQueue().offer((T)var1);
            if (this.getAndIncrement() != 0) {
               return;
            }
         }

         this.drainLoop();
      }

      @Override
      public void onSubscribe(Disposable var1) {
         DisposableHelper.setOnce(this.mainDisposable, var1);
      }

      void otherError(Throwable var1) {
         if (this.error.addThrowable(var1)) {
            DisposableHelper.dispose(this.mainDisposable);
            this.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      void otherSuccess(T var1) {
         if (this.compareAndSet(0, 1)) {
            this.downstream.onNext((T)var1);
            this.otherState = 2;
         } else {
            this.singleItem = (T)var1;
            this.otherState = 1;
            if (this.getAndIncrement() != 0) {
               return;
            }
         }

         this.drainLoop();
      }

      static final class OtherObserver<T> extends AtomicReference<Disposable> implements SingleObserver<T> {
         private static final long serialVersionUID = -2935427570954647017L;
         final ObservableMergeWithSingle.MergeWithObserver<T> parent;

         OtherObserver(ObservableMergeWithSingle.MergeWithObserver<T> var1) {
            this.parent = var1;
         }

         @Override
         public void onError(Throwable var1) {
            this.parent.otherError(var1);
         }

         @Override
         public void onSubscribe(Disposable var1) {
            DisposableHelper.setOnce(this, var1);
         }

         @Override
         public void onSuccess(T var1) {
            this.parent.otherSuccess((T)var1);
         }
      }
   }
}
