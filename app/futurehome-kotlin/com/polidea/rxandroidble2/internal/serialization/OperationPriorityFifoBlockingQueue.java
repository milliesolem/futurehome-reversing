package com.polidea.rxandroidble2.internal.serialization;

import java.util.concurrent.PriorityBlockingQueue;

class OperationPriorityFifoBlockingQueue {
   private final PriorityBlockingQueue<FIFORunnableEntry> q = new PriorityBlockingQueue<>();

   public void add(FIFORunnableEntry var1) {
      this.q.add(var1);
   }

   public boolean isEmpty() {
      return this.q.isEmpty();
   }

   public boolean remove(FIFORunnableEntry var1) {
      for (FIFORunnableEntry var3 : this.q) {
         if (var3 == var1) {
            return this.q.remove(var3);
         }
      }

      return false;
   }

   public FIFORunnableEntry<?> take() throws InterruptedException {
      return this.q.take();
   }

   public FIFORunnableEntry<?> takeNow() {
      return this.q.poll();
   }
}
