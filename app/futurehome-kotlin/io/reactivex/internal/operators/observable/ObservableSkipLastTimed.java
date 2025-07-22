package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class ObservableSkipLastTimed<T> extends AbstractObservableWithUpstream<T, T> {
   final int bufferSize;
   final boolean delayError;
   final Scheduler scheduler;
   final long time;
   final TimeUnit unit;

   public ObservableSkipLastTimed(ObservableSource<T> var1, long var2, TimeUnit var4, Scheduler var5, int var6, boolean var7) {
      super(var1);
      this.time = var2;
      this.unit = var4;
      this.scheduler = var5;
      this.bufferSize = var6;
      this.delayError = var7;
   }

   @Override
   public void subscribeActual(Observer<? super T> var1) {
      this.source.subscribe(new ObservableSkipLastTimed.SkipLastTimedObserver<>(var1, this.time, this.unit, this.scheduler, this.bufferSize, this.delayError));
   }

   static final class SkipLastTimedObserver<T> extends AtomicInteger implements Observer<T>, Disposable {
      private static final long serialVersionUID = -5677354903406201275L;
      volatile boolean cancelled;
      final boolean delayError;
      volatile boolean done;
      final Observer<? super T> downstream;
      Throwable error;
      final SpscLinkedArrayQueue<Object> queue;
      final Scheduler scheduler;
      final long time;
      final TimeUnit unit;
      Disposable upstream;

      SkipLastTimedObserver(Observer<? super T> var1, long var2, TimeUnit var4, Scheduler var5, int var6, boolean var7) {
         this.downstream = var1;
         this.time = var2;
         this.unit = var4;
         this.scheduler = var5;
         this.queue = new SpscLinkedArrayQueue<>(var6);
         this.delayError = var7;
      }

      @Override
      public void dispose() {
         if (!this.cancelled) {
            this.cancelled = true;
            this.upstream.dispose();
            if (this.getAndIncrement() == 0) {
               this.queue.clear();
            }
         }
      }

      void drain() {
         if (this.getAndIncrement() == 0) {
            Observer var10 = this.downstream;
            SpscLinkedArrayQueue var12 = this.queue;
            boolean var8 = this.delayError;
            TimeUnit var13 = this.unit;
            Scheduler var11 = this.scheduler;
            long var6 = this.time;
            int var2 = 1;

            while (!this.cancelled) {
               boolean var9 = this.done;
               Long var14 = (Long)var12.peek();
               boolean var1;
               if (var14 == null) {
                  var1 = 1;
               } else {
                  var1 = 0;
               }

               long var4 = var11.now(var13);
               boolean var3 = (boolean)var1;
               if (!var1) {
                  var3 = (boolean)var1;
                  if (var14 > var4 - var6) {
                     var3 = true;
                  }
               }

               if (var9) {
                  if (var8) {
                     if (var3) {
                        Throwable var16 = this.error;
                        if (var16 != null) {
                           var10.onError(var16);
                        } else {
                           var10.onComplete();
                        }

                        return;
                     }
                  } else {
                     Throwable var17 = this.error;
                     if (var17 != null) {
                        this.queue.clear();
                        var10.onError(var17);
                        return;
                     }

                     if (var3) {
                        var10.onComplete();
                        return;
                     }
                  }
               }

               if (var3) {
                  var1 = this.addAndGet(-var2);
                  var2 = var1;
                  if (var1 == 0) {
                     return;
                  }
               } else {
                  var12.poll();
                  var10.onNext(var12.poll());
               }
            }

            this.queue.clear();
         }
      }

      @Override
      public boolean isDisposed() {
         return this.cancelled;
      }

      @Override
      public void onComplete() {
         this.done = true;
         this.drain();
      }

      @Override
      public void onError(Throwable var1) {
         this.error = var1;
         this.done = true;
         this.drain();
      }

      @Override
      public void onNext(T var1) {
         this.queue.offer(this.scheduler.now(this.unit), var1);
         this.drain();
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
