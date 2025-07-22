package io.reactivex.internal.operators.observable;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.QueueDrainObserver;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subjects.UnicastSubject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableWindowBoundarySelector<T, B, V> extends AbstractObservableWithUpstream<T, Observable<T>> {
   final int bufferSize;
   final Function<? super B, ? extends ObservableSource<V>> close;
   final ObservableSource<B> open;

   public ObservableWindowBoundarySelector(
      ObservableSource<T> var1, ObservableSource<B> var2, Function<? super B, ? extends ObservableSource<V>> var3, int var4
   ) {
      super(var1);
      this.open = var2;
      this.close = var3;
      this.bufferSize = var4;
   }

   @Override
   public void subscribeActual(Observer<? super Observable<T>> var1) {
      this.source
         .subscribe(new ObservableWindowBoundarySelector.WindowBoundaryMainObserver<>(new SerializedObserver<>(var1), this.open, this.close, this.bufferSize));
   }

   static final class OperatorWindowBoundaryCloseObserver<T, V> extends DisposableObserver<V> {
      boolean done;
      final ObservableWindowBoundarySelector.WindowBoundaryMainObserver<T, ?, V> parent;
      final UnicastSubject<T> w;

      OperatorWindowBoundaryCloseObserver(ObservableWindowBoundarySelector.WindowBoundaryMainObserver<T, ?, V> var1, UnicastSubject<T> var2) {
         this.parent = var1;
         this.w = var2;
      }

      @Override
      public void onComplete() {
         if (!this.done) {
            this.done = true;
            this.parent.close(this);
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.done = true;
            this.parent.error(var1);
         }
      }

      @Override
      public void onNext(V var1) {
         this.dispose();
         this.onComplete();
      }
   }

   static final class OperatorWindowBoundaryOpenObserver<T, B> extends DisposableObserver<B> {
      final ObservableWindowBoundarySelector.WindowBoundaryMainObserver<T, B, ?> parent;

      OperatorWindowBoundaryOpenObserver(ObservableWindowBoundarySelector.WindowBoundaryMainObserver<T, B, ?> var1) {
         this.parent = var1;
      }

      @Override
      public void onComplete() {
         this.parent.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         this.parent.error(var1);
      }

      @Override
      public void onNext(B var1) {
         this.parent.open((B)var1);
      }
   }

   static final class WindowBoundaryMainObserver<T, B, V> extends QueueDrainObserver<T, Object, Observable<T>> implements Disposable {
      final AtomicReference<Disposable> boundary = new AtomicReference<>();
      final int bufferSize;
      final Function<? super B, ? extends ObservableSource<V>> close;
      final ObservableSource<B> open;
      final CompositeDisposable resources;
      final AtomicBoolean stopWindows;
      Disposable upstream;
      final AtomicLong windows;
      final List<UnicastSubject<T>> ws;

      WindowBoundaryMainObserver(
         Observer<? super Observable<T>> var1, ObservableSource<B> var2, Function<? super B, ? extends ObservableSource<V>> var3, int var4
      ) {
         super(var1, new MpscLinkedQueue<>());
         AtomicLong var5 = new AtomicLong();
         this.windows = var5;
         this.stopWindows = new AtomicBoolean();
         this.open = var2;
         this.close = var3;
         this.bufferSize = var4;
         this.resources = new CompositeDisposable();
         this.ws = new ArrayList<>();
         var5.lazySet(1L);
      }

      @Override
      public void accept(Observer<? super Observable<T>> var1, Object var2) {
      }

      void close(ObservableWindowBoundarySelector.OperatorWindowBoundaryCloseObserver<T, V> var1) {
         this.resources.delete(var1);
         this.queue.offer(new ObservableWindowBoundarySelector.WindowOperation<>(var1.w, null));
         if (this.enter()) {
            this.drainLoop();
         }
      }

      @Override
      public void dispose() {
         if (this.stopWindows.compareAndSet(false, true)) {
            DisposableHelper.dispose(this.boundary);
            if (this.windows.decrementAndGet() == 0L) {
               this.upstream.dispose();
            }
         }
      }

      void disposeBoundary() {
         this.resources.dispose();
         DisposableHelper.dispose(this.boundary);
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      void drainLoop() {
         MpscLinkedQueue var6 = (MpscLinkedQueue)this.queue;
         Observer var5 = this.downstream;
         List var4 = this.ws;
         int var1 = 1;

         while (true) {
            boolean var3 = this.done;
            Object var8 = var6.poll();
            boolean var2;
            if (var8 == null) {
               var2 = 1;
            } else {
               var2 = 0;
            }

            if (var3 && var2) {
               this.disposeBoundary();
               Throwable var12 = this.error;
               if (var12 != null) {
                  Iterator var14 = var4.iterator();

                  while (var14.hasNext()) {
                     ((UnicastSubject)var14.next()).onError(var12);
                  }
               } else {
                  Iterator var13 = var4.iterator();

                  while (var13.hasNext()) {
                     ((UnicastSubject)var13.next()).onComplete();
                  }
               }

               var4.clear();
               return;
            }

            if (var2) {
               var2 = this.leave(-var1);
               var1 = var2;
               if (var2 == 0) {
                  return;
               }
            } else if (var8 instanceof ObservableWindowBoundarySelector.WindowOperation) {
               ObservableWindowBoundarySelector.WindowOperation var15 = (ObservableWindowBoundarySelector.WindowOperation)var8;
               if (var15.w != null) {
                  if (var4.remove(var15.w)) {
                     var15.w.onComplete();
                     if (this.windows.decrementAndGet() == 0L) {
                        this.disposeBoundary();
                        return;
                     }
                  }
               } else if (!this.stopWindows.get()) {
                  var8 = UnicastSubject.create(this.bufferSize);
                  var4.add(var8);
                  var5.onNext(var8);

                  try {
                     var16 = ObjectHelper.requireNonNull(this.close.apply(var15.open), "The ObservableSource supplied is null");
                  } catch (Throwable var10) {
                     Exceptions.throwIfFatal(var10);
                     this.stopWindows.set(true);
                     var5.onError(var10);
                     continue;
                  }

                  var8 = new ObservableWindowBoundarySelector.OperatorWindowBoundaryCloseObserver<>(this, (UnicastSubject<T>)var8);
                  if (this.resources.add((Disposable)var8)) {
                     this.windows.getAndIncrement();
                     var16.subscribe((Observer<? super T>)var8);
                  }
               }
            } else {
               Iterator var7 = var4.iterator();

               while (var7.hasNext()) {
                  ((UnicastSubject)var7.next()).onNext(NotificationLite.getValue(var8));
               }
            }
         }
      }

      void error(Throwable var1) {
         this.upstream.dispose();
         this.resources.dispose();
         this.onError(var1);
      }

      @Override
      public boolean isDisposed() {
         return this.stopWindows.get();
      }

      @Override
      public void onComplete() {
         if (!this.done) {
            this.done = true;
            if (this.enter()) {
               this.drainLoop();
            }

            if (this.windows.decrementAndGet() == 0L) {
               this.resources.dispose();
            }

            this.downstream.onComplete();
         }
      }

      @Override
      public void onError(Throwable var1) {
         if (this.done) {
            RxJavaPlugins.onError(var1);
         } else {
            this.error = var1;
            this.done = true;
            if (this.enter()) {
               this.drainLoop();
            }

            if (this.windows.decrementAndGet() == 0L) {
               this.resources.dispose();
            }

            this.downstream.onError(var1);
         }
      }

      @Override
      public void onNext(T var1) {
         if (this.fastEnter()) {
            Iterator var2 = this.ws.iterator();

            while (var2.hasNext()) {
               ((UnicastSubject)var2.next()).onNext(var1);
            }

            if (this.leave(-1) == 0) {
               return;
            }
         } else {
            this.queue.offer(NotificationLite.next(var1));
            if (!this.enter()) {
               return;
            }
         }

         this.drainLoop();
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
            if (this.stopWindows.get()) {
               return;
            }

            ObservableWindowBoundarySelector.OperatorWindowBoundaryOpenObserver var2 = new ObservableWindowBoundarySelector.OperatorWindowBoundaryOpenObserver<>(
               this
            );
            if (ExternalSyntheticBackportWithForwarding0.m(this.boundary, null, var2)) {
               this.open.subscribe(var2);
            }
         }
      }

      void open(B var1) {
         this.queue.offer(new ObservableWindowBoundarySelector.WindowOperation<>(null, var1));
         if (this.enter()) {
            this.drainLoop();
         }
      }
   }

   static final class WindowOperation<T, B> {
      final B open;
      final UnicastSubject<T> w;

      WindowOperation(UnicastSubject<T> var1, B var2) {
         this.w = var1;
         this.open = (B)var2;
      }
   }
}
