package io.reactivex.android.schedulers;

import android.os.Handler;
import android.os.Message;
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.TimeUnit;

final class HandlerScheduler extends Scheduler {
   private final boolean async;
   private final Handler handler;

   HandlerScheduler(Handler var1, boolean var2) {
      this.handler = var1;
      this.async = var2;
   }

   @Override
   public Scheduler.Worker createWorker() {
      return new HandlerScheduler.HandlerWorker(this.handler, this.async);
   }

   @Override
   public Disposable scheduleDirect(Runnable var1, long var2, TimeUnit var4) {
      if (var1 != null) {
         if (var4 != null) {
            var1 = RxJavaPlugins.onSchedule(var1);
            HandlerScheduler.ScheduledRunnable var5 = new HandlerScheduler.ScheduledRunnable(this.handler, var1);
            Message var7 = Message.obtain(this.handler, var5);
            if (this.async) {
               AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var7, true);
            }

            this.handler.sendMessageDelayed(var7, var4.toMillis(var2));
            return var5;
         } else {
            throw new NullPointerException("unit == null");
         }
      } else {
         throw new NullPointerException("run == null");
      }
   }

   private static final class HandlerWorker extends Scheduler.Worker {
      private final boolean async;
      private volatile boolean disposed;
      private final Handler handler;

      HandlerWorker(Handler var1, boolean var2) {
         this.handler = var1;
         this.async = var2;
      }

      @Override
      public void dispose() {
         this.disposed = true;
         this.handler.removeCallbacksAndMessages(this);
      }

      @Override
      public boolean isDisposed() {
         return this.disposed;
      }

      @Override
      public Disposable schedule(Runnable var1, long var2, TimeUnit var4) {
         if (var1 != null) {
            if (var4 != null) {
               if (this.disposed) {
                  return Disposables.disposed();
               } else {
                  var1 = RxJavaPlugins.onSchedule(var1);
                  HandlerScheduler.ScheduledRunnable var7 = new HandlerScheduler.ScheduledRunnable(this.handler, var1);
                  Message var5 = Message.obtain(this.handler, var7);
                  var5.obj = this;
                  if (this.async) {
                     AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var5, true);
                  }

                  this.handler.sendMessageDelayed(var5, var4.toMillis(var2));
                  if (this.disposed) {
                     this.handler.removeCallbacks(var7);
                     return Disposables.disposed();
                  } else {
                     return var7;
                  }
               }
            } else {
               throw new NullPointerException("unit == null");
            }
         } else {
            throw new NullPointerException("run == null");
         }
      }
   }

   private static final class ScheduledRunnable implements Runnable, Disposable {
      private final Runnable delegate;
      private volatile boolean disposed;
      private final Handler handler;

      ScheduledRunnable(Handler var1, Runnable var2) {
         this.handler = var1;
         this.delegate = var2;
      }

      @Override
      public void dispose() {
         this.handler.removeCallbacks(this);
         this.disposed = true;
      }

      @Override
      public boolean isDisposed() {
         return this.disposed;
      }

      // $VF: Could not inline inconsistent finally blocks
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void run() {
         try {
            this.delegate.run();
         } catch (Throwable var3) {
            RxJavaPlugins.onError(var3);
            return;
         }
      }
   }
}
