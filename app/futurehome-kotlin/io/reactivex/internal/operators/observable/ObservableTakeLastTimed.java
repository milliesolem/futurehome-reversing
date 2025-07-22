package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ObservableTakeLastTimed<T> extends AbstractObservableWithUpstream<T, T> {
   final int bufferSize;
   final long count;
   final boolean delayError;
   final Scheduler scheduler;
   final long time;
   final TimeUnit unit;

   public ObservableTakeLastTimed(ObservableSource<T> var1, long var2, long var4, TimeUnit var6, Scheduler var7, int var8, boolean var9) {
      super(var1);
      this.count = var2;
      this.time = var4;
      this.unit = var6;
      this.scheduler = var7;
      this.bufferSize = var8;
      this.delayError = var9;
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      this.source
         .subscribe(
            new ObservableTakeLastTimed.TakeLastTimedObserver<>(var1, this.count, this.time, this.unit, this.scheduler, this.bufferSize, this.delayError)
         );
   }

   static final class TakeLastTimedObserver<T> extends AtomicBoolean implements Observer<T>, Disposable {
      private static final long serialVersionUID = -5677354903406201275L;
      volatile boolean cancelled;
      final long count;
      final boolean delayError;
      final Observer<? super T> downstream;
      Throwable error;
      final SpscLinkedArrayQueue<Object> queue;
      final Scheduler scheduler;
      final long time;
      final TimeUnit unit;
      Disposable upstream;

      TakeLastTimedObserver(Observer<? super T> var1, long var2, long var4, TimeUnit var6, Scheduler var7, int var8, boolean var9) {
         this.downstream = var1;
         this.count = var2;
         this.time = var4;
         this.unit = var6;
         this.scheduler = var7;
         this.queue = new SpscLinkedArrayQueue<>(var8);
         this.delayError = var9;
      }

      @Override
      public void dispose() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.upstream.dispose();
            if (this.compareAndSet(false, true)) {
               this.queue.clear();
            }
         }
      }

      void drain() {
         if (this.compareAndSet(false, true)) {
            Observer var6 = this.downstream;
            SpscLinkedArrayQueue var7 = this.queue;
            boolean var1 = this.delayError;
            long var2 = this.scheduler.now(this.unit);
            long var4 = this.time;

            while (!this.cancelled) {
               if (!var1) {
                  Throwable var8 = this.error;
                  if (var8 != null) {
                     var7.clear();
                     var6.onError(var8);
                     return;
                  }
               }

               Object var11 = var7.poll();
               if (var11 == null) {
                  Throwable var10 = this.error;
                  if (var10 != null) {
                     var6.onError(var10);
                  } else {
                     var6.onComplete();
                  }

                  return;
               }

               Object var9 = var7.poll();
               if ((Long)var11 >= var2 - var4) {
                  var6.onNext(var9);
               }
            }

            var7.clear();
         }
      }

      @Override
      public boolean isDisposed() {
         return this.cancelled;
      }

      @Override
      public void onComplete() {
         this.drain();
      }

      @Override
      public void onError(Throwable var1) {
         this.error = var1;
         this.drain();
      }

      @Override
      public void onNext(T var1) {
         SpscLinkedArrayQueue var9 = this.queue;
         long var5 = this.scheduler.now(this.unit);
         long var3 = this.time;
         long var7 = this.count;
         boolean var2;
         if (var7 == Long.MAX_VALUE) {
            var2 = true;
         } else {
            var2 = false;
         }

         var9.offer(var5, (Long)var1);

         while (!var9.isEmpty() && (var9.peek() <= var5 - var3 || !var2 && var9.size() >> 1 > var7)) {
            var9.poll();
            var9.poll();
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
