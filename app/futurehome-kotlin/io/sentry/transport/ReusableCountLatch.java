package io.sentry.transport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public final class ReusableCountLatch {
   private final ReusableCountLatch.Sync sync;

   public ReusableCountLatch() {
      this(0);
   }

   public ReusableCountLatch(int var1) {
      if (var1 >= 0) {
         this.sync = new ReusableCountLatch.Sync(var1);
      } else {
         StringBuilder var2 = new StringBuilder("negative initial count '");
         var2.append(var1);
         var2.append("' is not allowed");
         throw new IllegalArgumentException(var2.toString());
      }
   }

   public void decrement() {
      this.sync.decrement();
   }

   public int getCount() {
      return this.sync.getCount();
   }

   public void increment() {
      this.sync.increment();
   }

   public void waitTillZero() throws InterruptedException {
      this.sync.acquireSharedInterruptibly(1);
   }

   public boolean waitTillZero(long var1, TimeUnit var3) throws InterruptedException {
      return this.sync.tryAcquireSharedNanos(1, var3.toNanos(var1));
   }

   private static final class Sync extends AbstractQueuedSynchronizer {
      private static final long serialVersionUID = 5970133580157457018L;

      Sync(int var1) {
         this.setState(var1);
      }

      private void decrement() {
         this.releaseShared(1);
      }

      private int getCount() {
         return this.getState();
      }

      private void increment() {
         int var1;
         do {
            var1 = this.getState();
         } while (!this.compareAndSetState(var1, var1 + 1));
      }

      @Override
      public int tryAcquireShared(int var1) {
         byte var2;
         if (this.getState() == 0) {
            var2 = 1;
         } else {
            var2 = -1;
         }

         return var2;
      }

      @Override
      public boolean tryReleaseShared(int var1) {
         int var2;
         boolean var3;
         do {
            var1 = this.getState();
            var3 = false;
            if (var1 == 0) {
               return false;
            }

            var2 = var1 - 1;
         } while (!this.compareAndSetState(var1, var2));

         if (var2 == 0) {
            var3 = true;
         }

         return var3;
      }
   }
}
