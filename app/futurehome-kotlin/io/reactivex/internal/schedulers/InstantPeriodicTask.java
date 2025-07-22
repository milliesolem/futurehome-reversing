package io.reactivex.internal.schedulers;

import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.Functions;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;

final class InstantPeriodicTask implements Callable<Void>, Disposable {
   static final FutureTask<Void> CANCELLED = new FutureTask<>(Functions.EMPTY_RUNNABLE, null);
   final ExecutorService executor;
   final AtomicReference<Future<?>> first;
   final AtomicReference<Future<?>> rest;
   Thread runner;
   final Runnable task;

   InstantPeriodicTask(Runnable var1, ExecutorService var2) {
      this.task = var1;
      this.first = new AtomicReference<>();
      this.rest = new AtomicReference<>();
      this.executor = var2;
   }

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public Void call() throws Exception {
      this.runner = Thread.currentThread();

      try {
         this.task.run();
         this.setRest(this.executor.submit(this));
         this.runner = null;
      } catch (Throwable var3) {
         this.runner = null;
         RxJavaPlugins.onError(var3);
         return null;
      }

      return null;
   }

   @Override
   public void dispose() {
      AtomicReference var4 = this.first;
      FutureTask var3 = CANCELLED;
      Future var6 = var4.getAndSet(var3);
      boolean var2 = true;
      if (var6 != null && var6 != var3) {
         boolean var1;
         if (this.runner != Thread.currentThread()) {
            var1 = true;
         } else {
            var1 = false;
         }

         var6.cancel(var1);
      }

      Future var7 = this.rest.getAndSet(var3);
      if (var7 != null && var7 != var3) {
         boolean var5;
         if (this.runner != Thread.currentThread()) {
            var5 = var2;
         } else {
            var5 = false;
         }

         var7.cancel(var5);
      }
   }

   @Override
   public boolean isDisposed() {
      boolean var1;
      if (this.first.get() == CANCELLED) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   void setFirst(Future<?> var1) {
      Future var3;
      do {
         var3 = this.first.get();
         if (var3 == CANCELLED) {
            boolean var2;
            if (this.runner != Thread.currentThread()) {
               var2 = true;
            } else {
               var2 = false;
            }

            var1.cancel(var2);
            return;
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.first, var3, var1));
   }

   void setRest(Future<?> var1) {
      Future var3;
      do {
         var3 = this.rest.get();
         if (var3 == CANCELLED) {
            boolean var2;
            if (this.runner != Thread.currentThread()) {
               var2 = true;
            } else {
               var2 = false;
            }

            var1.cancel(var2);
            return;
         }
      } while (!ExternalSyntheticBackportWithForwarding0.m(this.rest, var3, var1));
   }
}
