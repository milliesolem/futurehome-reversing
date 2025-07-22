package io.reactivex.internal.operators.single;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import java.util.concurrent.TimeUnit;

public final class SingleDelay<T> extends Single<T> {
   final boolean delayError;
   final Scheduler scheduler;
   final SingleSource<? extends T> source;
   final long time;
   final TimeUnit unit;

   public SingleDelay(SingleSource<? extends T> var1, long var2, TimeUnit var4, Scheduler var5, boolean var6) {
      this.source = var1;
      this.time = var2;
      this.unit = var4;
      this.scheduler = var5;
      this.delayError = var6;
   }

   @Override
   protected void subscribeActual(SingleObserver<? super T> var1) {
      SequentialDisposable var2 = new SequentialDisposable();
      var1.onSubscribe(var2);
      this.source.subscribe(new SingleDelay.Delay(this, var2, var1));
   }

   final class Delay implements SingleObserver<T> {
      final SingleObserver<? super T> downstream;
      private final SequentialDisposable sd;
      final SingleDelay this$0;

      Delay(SequentialDisposable var1, SingleObserver<? super T> var2, SingleObserver var3) {
         this.this$0 = var1;
         this.sd = var2;
         this.downstream = var3;
      }

      @Override
      public void onError(Throwable var1) {
         SequentialDisposable var4 = this.sd;
         Scheduler var5 = this.this$0.scheduler;
         SingleDelay.Delay.OnError var6 = new SingleDelay.Delay.OnError(this, var1);
         long var2;
         if (this.this$0.delayError) {
            var2 = this.this$0.time;
         } else {
            var2 = 0L;
         }

         var4.replace(var5.scheduleDirect(var6, var2, this.this$0.unit));
      }

      @Override
      public void onSubscribe(Disposable var1) {
         this.sd.replace(var1);
      }

      @Override
      public void onSuccess(T var1) {
         this.sd.replace(this.this$0.scheduler.scheduleDirect(new SingleDelay.Delay.OnSuccess(this, var1), this.this$0.time, this.this$0.unit));
      }

      final class OnError implements Runnable {
         private final Throwable e;
         final SingleDelay.Delay this$1;

         OnError(SingleDelay.Delay var1, Throwable var2) {
            this.this$1 = var1;
            this.e = var2;
         }

         @Override
         public void run() {
            this.this$1.downstream.onError(this.e);
         }
      }

      final class OnSuccess implements Runnable {
         final SingleDelay.Delay this$1;
         private final T value;

         OnSuccess(T var1, Object var2) {
            this.this$1 = var1;
            this.value = (T)var2;
         }

         @Override
         public void run() {
            this.this$1.downstream.onSuccess(this.value);
         }
      }
   }
}
