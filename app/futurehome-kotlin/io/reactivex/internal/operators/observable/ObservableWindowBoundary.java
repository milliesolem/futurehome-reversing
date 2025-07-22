package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subjects.UnicastSubject;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableWindowBoundary<T, B> extends AbstractObservableWithUpstream<T, Observable<T>> {
   final int capacityHint;
   final ObservableSource<B> other;

   public ObservableWindowBoundary(ObservableSource<T> var1, ObservableSource<B> var2, int var3) {
      super(var1);
      this.other = var2;
      this.capacityHint = var3;
   }

   @Override
   public void subscribeActual(Observer<? super Observable<T>> var1) {
      ObservableWindowBoundary.WindowBoundaryMainObserver var2 = new ObservableWindowBoundary.WindowBoundaryMainObserver(var1, this.capacityHint);
      var1.onSubscribe(var2);
      this.other.subscribe(var2.boundaryObserver);
      this.source.subscribe(var2);
   }

   static final class WindowBoundaryInnerObserver<T, B> extends DisposableObserver<B> {
      boolean done;
      final ObservableWindowBoundary.WindowBoundaryMainObserver<T, B> parent;

      WindowBoundaryInnerObserver(ObservableWindowBoundary.WindowBoundaryMainObserver<T, B> var1) {
         this.parent = var1;
      }

      @Override
      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.parent.innerComplete();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.parent.innerError(var1);
         }
      }

      @Override
      public void onNext(B var1) {
         if (!this.done) {
            this.parent.innerNext();
         }
      }
   }

   static final class WindowBoundaryMainObserver<T, B> extends AtomicInteger implements Observer<T>, Disposable, Runnable {
      static final Object NEXT_WINDOW = new Object();
      private static final long serialVersionUID = 2233020065421370272L;
      final ObservableWindowBoundary.WindowBoundaryInnerObserver<T, B> boundaryObserver;
      final int capacityHint;
      volatile boolean done;
      final Observer<? super Observable<T>> downstream;
      final AtomicThrowable errors;
      final MpscLinkedQueue<Object> queue;
      final AtomicBoolean stopWindows;
      final AtomicReference<Disposable> upstream;
      UnicastSubject<T> window;
      final AtomicInteger windows;

      WindowBoundaryMainObserver(Observer<? super Observable<T>> var1, int var2) {
         this.downstream = var1;
         this.capacityHint = var2;
         this.boundaryObserver = new ObservableWindowBoundary.WindowBoundaryInnerObserver<>(this);
         this.upstream = new AtomicReference<>();
         this.windows = new AtomicInteger(1);
         this.queue = new MpscLinkedQueue<>();
         this.errors = new AtomicThrowable();
         this.stopWindows = new AtomicBoolean();
      }

      @Override
      public void dispose() {
         if (this.stopWindows.compareAndSet(false, true)) {
            this.boundaryObserver.dispose();
            if (this.windows.decrementAndGet() == 0) {
               DisposableHelper.dispose(this.upstream);
            }
         }
      }

      void drain() {
         if (this.getAndIncrement() == 0) {
            Observer var4 = this.downstream;
            MpscLinkedQueue var6 = this.queue;
            AtomicThrowable var7 = this.errors;
            int var1 = 1;

            while (this.windows.get() != 0) {
               UnicastSubject var5 = this.window;
               boolean var3 = this.done;
               if (var3 && var7.get() != null) {
                  var6.clear();
                  Throwable var12 = var7.terminate();
                  if (var5 != null) {
                     this.window = null;
                     var5.onError(var12);
                  }

                  var4.onError(var12);
                  return;
               }

               Object var8 = var6.poll();
               boolean var2;
               if (var8 == null) {
                  var2 = 1;
               } else {
                  var2 = 0;
               }

               if (var3 && var2) {
                  Throwable var11 = var7.terminate();
                  if (var11 == null) {
                     if (var5 != null) {
                        this.window = null;
                        var5.onComplete();
                     }

                     var4.onComplete();
                  } else {
                     if (var5 != null) {
                        this.window = null;
                        var5.onError(var11);
                     }

                     var4.onError(var11);
                  }

                  return;
               }

               if (var2) {
                  var2 = this.addAndGet(-var1);
                  var1 = var2;
                  if (var2 == 0) {
                     return;
                  }
               } else if (var8 != NEXT_WINDOW) {
                  var5.onNext(var8);
               } else {
                  if (var5 != null) {
                     this.window = null;
                     var5.onComplete();
                  }

                  if (!this.stopWindows.get()) {
                     var5 = UnicastSubject.create(this.capacityHint, this);
                     this.window = var5;
                     this.windows.getAndIncrement();
                     var4.onNext(var5);
                  }
               }
            }

            var6.clear();
            this.window = null;
         }
      }

      void innerComplete() {
         DisposableHelper.dispose(this.upstream);
         this.done = true;
         this.drain();
      }

      void innerError(Throwable var1) {
         DisposableHelper.dispose(this.upstream);
         if (this.errors.addThrowable(var1)) {
            this.done = true;
            this.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      void innerNext() {
         this.queue.offer(NEXT_WINDOW);
         this.drain();
      }

      @Override
      public boolean isDisposed() {
         return this.stopWindows.get();
      }

      @Override
      public void onComplete() {
         this.boundaryObserver.dispose();
         this.done = true;
         this.drain();
      }

      @Override
      public void onError(Throwable var1) {
         this.boundaryObserver.dispose();
         if (this.errors.addThrowable(var1)) {
            this.done = true;
            this.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      @Override
      public void onNext(T var1) {
         this.queue.offer(var1);
         this.drain();
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.setOnce(this.upstream, var1)) {
            this.innerNext();
         }
      }

      @Override
      public void run() {
         if (this.windows.decrementAndGet() == 0) {
            DisposableHelper.dispose(this.upstream);
         }
      }
   }
}
