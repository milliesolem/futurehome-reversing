package io.reactivex.internal.queue;

import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.util.Pow2;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;

public final class SpscArrayQueue<E> extends AtomicReferenceArray<E> implements SimplePlainQueue<E> {
   private static final Integer MAX_LOOK_AHEAD_STEP = Integer.getInteger("jctools.spsc.max.lookahead.step", 4096);
   private static final long serialVersionUID = -1296597691183856449L;
   final AtomicLong consumerIndex;
   final int lookAheadStep;
   final int mask = this.length() - 1;
   final AtomicLong producerIndex = new AtomicLong();
   long producerLookAhead;

   public SpscArrayQueue(int var1) {
      super(Pow2.roundToPowerOfTwo(var1));
      this.consumerIndex = new AtomicLong();
      this.lookAheadStep = Math.min(var1 / 4, MAX_LOOK_AHEAD_STEP);
   }

   int calcElementOffset(long var1) {
      int var3 = (int)var1;
      return this.mask & var3;
   }

   int calcElementOffset(long var1, int var3) {
      return (int)var1 & var3;
   }

   @Override
   public void clear() {
      while (this.poll() != null || !this.isEmpty()) {
      }
   }

   @Override
   public boolean isEmpty() {
      boolean var1;
      if (this.producerIndex.get() == this.consumerIndex.get()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   E lvElement(int var1) {
      return this.get(var1);
   }

   @Override
   public boolean offer(E var1) {
      if (var1 != null) {
         int var2 = this.mask;
         long var4 = this.producerIndex.get();
         int var3 = this.calcElementOffset(var4, var2);
         if (var4 >= this.producerLookAhead) {
            long var6 = this.lookAheadStep + var4;
            if (this.lvElement(this.calcElementOffset(var6, var2)) == null) {
               this.producerLookAhead = var6;
            } else if (this.lvElement(var3) != null) {
               return false;
            }
         }

         this.soElement(var3, (E)var1);
         this.soProducerIndex(var4 + 1L);
         return true;
      } else {
         throw new NullPointerException("Null is not a valid element");
      }
   }

   @Override
   public boolean offer(E var1, E var2) {
      boolean var3;
      if (this.offer((E)var1) && this.offer((E)var2)) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   @Override
   public E poll() {
      long var2 = this.consumerIndex.get();
      int var1 = this.calcElementOffset(var2);
      Object var4 = this.lvElement(var1);
      if (var4 == null) {
         return null;
      } else {
         this.soConsumerIndex(var2 + 1L);
         this.soElement(var1, null);
         return (E)var4;
      }
   }

   void soConsumerIndex(long var1) {
      this.consumerIndex.lazySet(var1);
   }

   void soElement(int var1, E var2) {
      this.lazySet(var1, (E)var2);
   }

   void soProducerIndex(long var1) {
      this.producerIndex.lazySet(var1);
   }
}
