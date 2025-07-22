package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.subjects.UnicastSubject;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public final class ObservableWindow<T> extends AbstractObservableWithUpstream<T, Observable<T>> {
   final int capacityHint;
   final long count;
   final long skip;

   public ObservableWindow(ObservableSource<T> var1, long var2, long var4, int var6) {
      super(var1);
      this.count = var2;
      this.skip = var4;
      this.capacityHint = var6;
   }

   @Override
   public void subscribeActual(Observer<? super Observable<T>> var1) {
      if (this.count == this.skip) {
         this.source.subscribe(new ObservableWindow.WindowExactObserver<>(var1, this.count, this.capacityHint));
      } else {
         this.source.subscribe(new ObservableWindow.WindowSkipObserver<>(var1, this.count, this.skip, this.capacityHint));
      }
   }

   static final class WindowExactObserver<T> extends AtomicInteger implements Observer<T>, Disposable, Runnable {
      private static final long serialVersionUID = -7481782523886138128L;
      volatile boolean cancelled;
      final int capacityHint;
      final long count;
      final Observer<? super Observable<T>> downstream;
      long size;
      Disposable upstream;
      UnicastSubject<T> window;

      WindowExactObserver(Observer<? super Observable<T>> var1, long var2, int var4) {
         this.downstream = var1;
         this.count = var2;
         this.capacityHint = var4;
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
         UnicastSubject var1 = this.window;
         if (var1 != null) {
            this.window = null;
            var1.onComplete();
         }

         this.downstream.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         UnicastSubject var2 = this.window;
         if (var2 != null) {
            this.window = null;
            var2.onError(var1);
         }

         this.downstream.onError(var1);
      }

      @Override
      public void onNext(T var1) {
         UnicastSubject var5 = this.window;
         UnicastSubject var4 = var5;
         if (var5 == null) {
            var4 = var5;
            if (!this.cancelled) {
               var4 = UnicastSubject.create(this.capacityHint, this);
               this.window = var4;
               this.downstream.onNext(var4);
            }
         }

         if (var4 != null) {
            var4.onNext(var1);
            long var2 = this.size + 1L;
            this.size = var2;
            if (var2 >= this.count) {
               this.size = 0L;
               this.window = null;
               var4.onComplete();
               if (this.cancelled) {
                  this.upstream.dispose();
               }
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

      @Override
      public void run() {
         if (this.cancelled) {
            this.upstream.dispose();
         }
      }
   }

   static final class WindowSkipObserver<T> extends AtomicBoolean implements Observer<T>, Disposable, Runnable {
      private static final long serialVersionUID = 3366976432059579510L;
      volatile boolean cancelled;
      final int capacityHint;
      final long count;
      final Observer<? super Observable<T>> downstream;
      long firstEmission;
      long index;
      final long skip;
      Disposable upstream;
      final ArrayDeque<UnicastSubject<T>> windows;
      final AtomicInteger wip = new AtomicInteger();

      WindowSkipObserver(Observer<? super Observable<T>> var1, long var2, long var4, int var6) {
         this.downstream = var1;
         this.count = var2;
         this.skip = var4;
         this.capacityHint = var6;
         this.windows = new ArrayDeque<>();
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
         ArrayDeque var1 = this.windows;

         while (!var1.isEmpty()) {
            ((UnicastSubject)var1.poll()).onComplete();
         }

         this.downstream.onComplete();
      }

      @Override
      public void onError(Throwable var1) {
         ArrayDeque var2 = this.windows;

         while (!var2.isEmpty()) {
            ((UnicastSubject)var2.poll()).onError(var1);
         }

         this.downstream.onError(var1);
      }

      @Override
      public void onNext(T var1) {
         ArrayDeque var8 = this.windows;
         long var2 = this.index;
         long var4 = this.skip;
         if (var2 % var4 == 0L && !this.cancelled) {
            this.wip.getAndIncrement();
            UnicastSubject var9 = UnicastSubject.create(this.capacityHint, this);
            var8.offer(var9);
            this.downstream.onNext(var9);
         }

         long var6 = this.firstEmission + 1L;
         Iterator var10 = var8.iterator();

         while (var10.hasNext()) {
            ((UnicastSubject)var10.next()).onNext(var1);
         }

         if (var6 >= this.count) {
            ((UnicastSubject)var8.poll()).onComplete();
            if (var8.isEmpty() && this.cancelled) {
               this.upstream.dispose();
               return;
            }

            this.firstEmission = var6 - var4;
         } else {
            this.firstEmission = var6;
         }

         this.index = var2 + 1L;
      }

      @Override
      public void onSubscribe(Disposable var1) {
         if (DisposableHelper.validate(this.upstream, var1)) {
            this.upstream = var1;
            this.downstream.onSubscribe(this);
         }
      }

      @Override
      public void run() {
         if (this.wip.decrementAndGet() == 0 && this.cancelled) {
            this.upstream.dispose();
         }
      }
   }
}
