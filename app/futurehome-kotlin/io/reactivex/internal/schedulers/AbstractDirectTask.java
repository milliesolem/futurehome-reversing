package io.reactivex.internal.schedulers;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.SchedulerRunnableIntrospection;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;

abstract class AbstractDirectTask extends AtomicReference<Future<?>> implements Disposable, SchedulerRunnableIntrospection {
   protected static final FutureTask<Void> DISPOSED = new FutureTask<>(Functions.EMPTY_RUNNABLE, null);
   protected static final FutureTask<Void> FINISHED = new FutureTask<>(Functions.EMPTY_RUNNABLE, null);
   private static final long serialVersionUID = 1811839108042568751L;
   protected final Runnable runnable;
   protected Thread runner;

   AbstractDirectTask(Runnable var1) {
      this.runnable = var1;
   }

   @Override
   public final void dispose() {
      Future var2 = this.get();
      if (var2 != FINISHED) {
         FutureTask var3 = DISPOSED;
         if (var2 != var3 && this.compareAndSet(var2, var3) && var2 != null) {
            boolean var1;
            if (this.runner != Thread.currentThread()) {
               var1 = true;
            } else {
               var1 = false;
            }

            var2.cancel(var1);
         }
      }
   }

   @Override
   public Runnable getWrappedRunnable() {
      return this.runnable;
   }

   @Override
   public final boolean isDisposed() {
      Future var2 = this.get();
      boolean var1;
      if (var2 != FINISHED && var2 != DISPOSED) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public final void setFuture(Future<?> var1) {
      while (true) {
         Future var3 = this.get();
         if (var3 != FINISHED) {
            if (var3 == DISPOSED) {
               boolean var2;
               if (this.runner != Thread.currentThread()) {
                  var2 = true;
               } else {
                  var2 = false;
               }

               var1.cancel(var2);
            } else if (!this.compareAndSet(var3, var1)) {
               continue;
            }
         }

         return;
      }
   }
}
