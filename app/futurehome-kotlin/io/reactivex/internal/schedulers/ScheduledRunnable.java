package io.reactivex.internal.schedulers;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableContainer;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReferenceArray;

public final class ScheduledRunnable extends AtomicReferenceArray<Object> implements Runnable, Callable<Object>, Disposable {
   static final Object ASYNC_DISPOSED = new Object();
   static final Object DONE = new Object();
   static final int FUTURE_INDEX = 1;
   static final Object PARENT_DISPOSED = new Object();
   static final int PARENT_INDEX = 0;
   static final Object SYNC_DISPOSED = new Object();
   static final int THREAD_INDEX = 2;
   private static final long serialVersionUID = -6120223772001106981L;
   final Runnable actual;

   public ScheduledRunnable(Runnable var1, DisposableContainer var2) {
      super(3);
      this.actual = var1;
      this.lazySet(0, var2);
   }

   @Override
   public Object call() {
      this.run();
      return null;
   }

   @Override
   public void dispose() {
      while (true) {
         Object var4 = this.get(1);
         if (var4 != DONE) {
            Object var2 = SYNC_DISPOSED;
            if (var4 != var2) {
               Object var3 = ASYNC_DISPOSED;
               if (var4 != var3) {
                  boolean var1;
                  if (this.get(2) != Thread.currentThread()) {
                     var1 = true;
                  } else {
                     var1 = false;
                  }

                  if (var1) {
                     var2 = var3;
                  }

                  if (!this.compareAndSet(1, var4, var2)) {
                     continue;
                  }

                  if (var4 != null) {
                     ((Future)var4).cancel(var1);
                  }
               }
            }
         }

         while (true) {
            Object var6 = this.get(0);
            if (var6 == DONE) {
               break;
            }

            Object var5 = PARENT_DISPOSED;
            if (var6 == var5 || var6 == null) {
               break;
            }

            if (this.compareAndSet(0, var6, var5)) {
               ((DisposableContainer)var6).delete(this);
               break;
            }
         }

         return;
      }
   }

   @Override
   public boolean isDisposed() {
      boolean var1 = false;
      Object var2 = this.get(0);
      if (var2 == PARENT_DISPOSED || var2 == DONE) {
         var1 = true;
      }

      return var1;
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public void run() {
      this.lazySet(2, Thread.currentThread());

      try {
         this.actual.run();
      } catch (Throwable var9) {
         Throwable var1 = var9;
         boolean var5 = false /* VF: Semaphore variable */;

         label124:
         try {
            var5 = true;
            RxJavaPlugins.onError(var1);
            var5 = false;
            break label124;
         } finally {
            if (var5) {
               this.lazySet(2, null);
               Object var2 = this.get(0);
               if (var2 != PARENT_DISPOSED && this.compareAndSet(0, var2, DONE) && var2 != null) {
                  ((DisposableContainer)var2).delete(this);
               }

               do {
                  var2 = this.get(1);
               } while (var2 != SYNC_DISPOSED && var2 != ASYNC_DISPOSED && !this.compareAndSet(1, var2, DONE));
            }
         }
      }

      this.lazySet(2, null);
      Object var10 = this.get(0);
      if (var10 != PARENT_DISPOSED && this.compareAndSet(0, var10, DONE) && var10 != null) {
         ((DisposableContainer)var10).delete(this);
      }

      do {
         var10 = this.get(1);
      } while (var10 != SYNC_DISPOSED && var10 != ASYNC_DISPOSED && !this.compareAndSet(1, var10, DONE));
   }

   public void setFuture(Future<?> var1) {
      Object var2;
      do {
         var2 = this.get(1);
         if (var2 == DONE) {
            return;
         }

         if (var2 == SYNC_DISPOSED) {
            var1.cancel(false);
            return;
         }

         if (var2 == ASYNC_DISPOSED) {
            var1.cancel(true);
            return;
         }
      } while (!this.compareAndSet(1, var2, var1));
   }
}
