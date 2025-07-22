package io.reactivex.internal.operators.observable;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subjects.UnicastSubject;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableWindowBoundarySupplier<T, B> extends AbstractObservableWithUpstream<T, Observable<T>> {
   final int capacityHint;
   final Callable<? extends ObservableSource<B>> other;

   public ObservableWindowBoundarySupplier(ObservableSource<T> var1, Callable<? extends ObservableSource<B>> var2, int var3) {
      super(var1);
      this.other = var2;
      this.capacityHint = var3;
   }

   @Override
   public void subscribeActual(Observer<? super Observable<T>> var1) {
      ObservableWindowBoundarySupplier.WindowBoundaryMainObserver var2 = new ObservableWindowBoundarySupplier.WindowBoundaryMainObserver<>(
         var1, this.capacityHint, this.other
      );
      this.source.subscribe(var2);
   }

   static final class WindowBoundaryInnerObserver<T, B> extends DisposableObserver<B> {
      boolean done;
      final ObservableWindowBoundarySupplier.WindowBoundaryMainObserver<T, B> parent;

      WindowBoundaryInnerObserver(ObservableWindowBoundarySupplier.WindowBoundaryMainObserver<T, B> var1) {
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
            this.done = true;
            this.dispose();
            this.parent.innerNext(this);
         }
      }
   }

   static final class WindowBoundaryMainObserver<T, B> extends AtomicInteger implements Observer<T>, Disposable, Runnable {
      static final ObservableWindowBoundarySupplier.WindowBoundaryInnerObserver<Object, Object> BOUNDARY_DISPOSED = new ObservableWindowBoundarySupplier.WindowBoundaryInnerObserver<>(
         null
      );
      static final Object NEXT_WINDOW = new Object();
      private static final long serialVersionUID = 2233020065421370272L;
      final AtomicReference<ObservableWindowBoundarySupplier.WindowBoundaryInnerObserver<T, B>> boundaryObserver;
      final int capacityHint;
      volatile boolean done;
      final Observer<? super Observable<T>> downstream;
      final AtomicThrowable errors;
      final Callable<? extends ObservableSource<B>> other;
      final MpscLinkedQueue<Object> queue;
      final AtomicBoolean stopWindows;
      Disposable upstream;
      UnicastSubject<T> window;
      final AtomicInteger windows;

      WindowBoundaryMainObserver(Observer<? super Observable<T>> var1, int var2, Callable<? extends ObservableSource<B>> var3) {
         this.downstream = var1;
         this.capacityHint = var2;
         this.boundaryObserver = new AtomicReference<>();
         this.windows = new AtomicInteger(1);
         this.queue = new MpscLinkedQueue<>();
         this.errors = new AtomicThrowable();
         this.stopWindows = new AtomicBoolean();
         this.other = var3;
      }

      @Override
      public void dispose() {
         if (this.stopWindows.compareAndSet(false, true)) {
            this.disposeBoundary();
            if (this.windows.decrementAndGet() == 0) {
               this.upstream.dispose();
            }
         }
      }

      void disposeBoundary() {
         AtomicReference var2 = this.boundaryObserver;
         ObservableWindowBoundarySupplier.WindowBoundaryInnerObserver var1 = BOUNDARY_DISPOSED;
         Disposable var3 = var2.getAndSet(var1);
         if (var3 != null && var3 != var1) {
            var3.dispose();
         }
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void drain() {
         if (this.getAndIncrement() == 0) {
            Observer var4 = this.downstream;
            MpscLinkedQueue var5 = this.queue;
            AtomicThrowable var6 = this.errors;
            int var1 = 1;

            while (this.windows.get() != 0) {
               UnicastSubject var7 = this.window;
               boolean var3 = this.done;
               if (var3 && var6.get() != null) {
                  var5.clear();
                  Throwable var14 = var6.terminate();
                  if (var7 != null) {
                     this.window = null;
                     var7.onError(var14);
                  }

                  var4.onError(var14);
                  return;
               }

               Object var8 = var5.poll();
               boolean var2;
               if (var8 == null) {
                  var2 = 1;
               } else {
                  var2 = 0;
               }

               if (var3 && var2) {
                  Throwable var13 = var6.terminate();
                  if (var13 == null) {
                     if (var7 != null) {
                        this.window = null;
                        var7.onComplete();
                     }

                     var4.onComplete();
                  } else {
                     if (var7 != null) {
                        this.window = null;
                        var7.onError(var13);
                     }

                     var4.onError(var13);
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
                  var7.onNext(var8);
               } else {
                  if (var7 != null) {
                     this.window = null;
                     var7.onComplete();
                  }

                  if (!this.stopWindows.get()) {
                     var7 = UnicastSubject.create(this.capacityHint, this);
                     this.window = var7;
                     this.windows.getAndIncrement();

                     ObservableSource var9;
                     try {
                        var9 = ObjectHelper.requireNonNull(this.other.call(), "The other Callable returned a null ObservableSource");
                     } catch (Throwable var11) {
                        Exceptions.throwIfFatal(var11);
                        var6.addThrowable(var11);
                        this.done = true;
                        continue;
                     }

                     var8 = new ObservableWindowBoundarySupplier.WindowBoundaryInnerObserver<>(this);
                     if (ExternalSyntheticBackportWithForwarding0.m(this.boundaryObserver, null, var8)) {
                        var9.subscribe((Observer<? super T>)var8);
                        var4.onNext(var7);
                     }
                  }
               }
            }

            var5.clear();
            this.window = null;
         }
      }

      void innerComplete() {
         this.upstream.dispose();
         this.done = true;
         this.drain();
      }

      void innerError(Throwable var1) {
         this.upstream.dispose();
         if (this.errors.addThrowable(var1)) {
            this.done = true;
            this.drain();
         } else {
            RxJavaPlugins.onError(var1);
         }
      }

      void innerNext(ObservableWindowBoundarySupplier.WindowBoundaryInnerObserver<T, B> var1) {
         ExternalSyntheticBackportWithForwarding0.m(this.boundaryObserver, var1, null);
         this.queue.offer(NEXT_WINDOW);
         this.drain();
      }

      @Override
      public boolean isDisposed() {
         return this.stopWindows.get();
      }

      @Override
      public void onComplete() {
         this.disposeBoundary();
         this.done = true;
         this.drain();
      }

      @Override
      public void onError(Throwable var1) {
         this.disposeBoundary();
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
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            this.queue.offer(NEXT_WINDOW);
            this.drain();
         }
      }

      @Override
      public void run() {
         if (this.windows.decrementAndGet() == 0) {
            this.upstream.dispose();
         }
      }
   }
}
