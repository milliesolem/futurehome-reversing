package io.reactivex.internal.queue;

import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.util.Pow2;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;

public final class SpscLinkedArrayQueue<T> implements SimplePlainQueue<T> {
   private static final Object HAS_NEXT = new Object();
   static final int MAX_LOOK_AHEAD_STEP = Integer.getInteger("jctools.spsc.max.lookahead.step", 4096);
   AtomicReferenceArray<Object> consumerBuffer;
   final AtomicLong consumerIndex;
   final int consumerMask;
   AtomicReferenceArray<Object> producerBuffer;
   final AtomicLong producerIndex = new AtomicLong();
   long producerLookAhead;
   int producerLookAheadStep;
   final int producerMask;

   public SpscLinkedArrayQueue(int var1) {
      this.consumerIndex = new AtomicLong();
      var1 = Pow2.roundToPowerOfTwo(Math.max(8, var1));
      int var2 = var1 - 1;
      AtomicReferenceArray var3 = new AtomicReferenceArray(var1 + 1);
      this.producerBuffer = var3;
      this.producerMask = var2;
      this.adjustLookAheadStep(var1);
      this.consumerBuffer = var3;
      this.consumerMask = var2;
      this.producerLookAhead = var1 - 2;
      this.soProducerIndex(0L);
   }

   private void adjustLookAheadStep(int var1) {
      this.producerLookAheadStep = Math.min(var1 / 4, MAX_LOOK_AHEAD_STEP);
   }

   private static int calcDirectOffset(int var0) {
      return var0;
   }

   private static int calcWrappedOffset(long var0, int var2) {
      return calcDirectOffset((int)var0 & var2);
   }

   private long lpConsumerIndex() {
      return this.consumerIndex.get();
   }

   private long lpProducerIndex() {
      return this.producerIndex.get();
   }

   private long lvConsumerIndex() {
      return this.consumerIndex.get();
   }

   private static <E> Object lvElement(AtomicReferenceArray<Object> var0, int var1) {
      return var0.get(var1);
   }

   private AtomicReferenceArray<Object> lvNextBufferAndUnlink(AtomicReferenceArray<Object> var1, int var2) {
      var2 = calcDirectOffset(var2);
      AtomicReferenceArray var3 = (AtomicReferenceArray)lvElement(var1, var2);
      soElement(var1, var2, null);
      return var3;
   }

   private long lvProducerIndex() {
      return this.producerIndex.get();
   }

   private T newBufferPeek(AtomicReferenceArray<Object> var1, long var2, int var4) {
      this.consumerBuffer = var1;
      return (T)lvElement(var1, calcWrappedOffset(var2, var4));
   }

   private T newBufferPoll(AtomicReferenceArray<Object> var1, long var2, int var4) {
      this.consumerBuffer = var1;
      var4 = calcWrappedOffset(var2, var4);
      Object var5 = lvElement(var1, var4);
      if (var5 != null) {
         soElement(var1, var4, null);
         this.soConsumerIndex(var2 + 1L);
      }

      return (T)var5;
   }

   private void resize(AtomicReferenceArray<Object> var1, long var2, int var4, T var5, long var6) {
      AtomicReferenceArray var8 = new AtomicReferenceArray(var1.length());
      this.producerBuffer = var8;
      this.producerLookAhead = var6 + var2 - 1L;
      soElement(var8, var4, var5);
      this.soNext(var1, var8);
      soElement(var1, var4, HAS_NEXT);
      this.soProducerIndex(var2 + 1L);
   }

   private void soConsumerIndex(long var1) {
      this.consumerIndex.lazySet(var1);
   }

   private static void soElement(AtomicReferenceArray<Object> var0, int var1, Object var2) {
      var0.lazySet(var1, var2);
   }

   private void soNext(AtomicReferenceArray<Object> var1, AtomicReferenceArray<Object> var2) {
      soElement(var1, calcDirectOffset(var1.length() - 1), var2);
   }

   private void soProducerIndex(long var1) {
      this.producerIndex.lazySet(var1);
   }

   private boolean writeToQueue(AtomicReferenceArray<Object> var1, T var2, long var3, int var5) {
      soElement(var1, var5, var2);
      this.soProducerIndex(var3 + 1L);
      return true;
   }

   @Override
   public void clear() {
      while (this.poll() != null || !this.isEmpty()) {
      }
   }

   @Override
   public boolean isEmpty() {
      boolean var1;
      if (this.lvProducerIndex() == this.lvConsumerIndex()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public boolean offer(T var1) {
      if (var1 != null) {
         AtomicReferenceArray var8 = this.producerBuffer;
         long var4 = this.lpProducerIndex();
         int var3 = this.producerMask;
         int var2 = calcWrappedOffset(var4, var3);
         if (var4 < this.producerLookAhead) {
            return this.writeToQueue(var8, (T)var1, var4, var2);
         } else {
            long var6 = this.producerLookAheadStep + var4;
            if (lvElement(var8, calcWrappedOffset(var6, var3)) == null) {
               this.producerLookAhead = var6 - 1L;
               return this.writeToQueue(var8, (T)var1, var4, var2);
            } else if (lvElement(var8, calcWrappedOffset(1L + var4, var3)) == null) {
               return this.writeToQueue(var8, (T)var1, var4, var2);
            } else {
               this.resize(var8, var4, var2, (T)var1, var3);
               return true;
            }
         }
      } else {
         throw new NullPointerException("Null is not a valid element");
      }
   }

   @Override
   public boolean offer(T var1, T var2) {
      AtomicReferenceArray var9 = this.producerBuffer;
      long var4 = this.lvProducerIndex();
      int var3 = this.producerMask;
      long var6 = 2L + var4;
      if (lvElement(var9, calcWrappedOffset(var6, var3)) == null) {
         var3 = calcWrappedOffset(var4, var3);
         soElement(var9, var3 + 1, var2);
         soElement(var9, var3, var1);
         this.soProducerIndex(var6);
      } else {
         AtomicReferenceArray var8 = new AtomicReferenceArray(var9.length());
         this.producerBuffer = var8;
         var3 = calcWrappedOffset(var4, var3);
         soElement(var8, var3 + 1, var2);
         soElement(var8, var3, var1);
         this.soNext(var9, var8);
         soElement(var9, var3, HAS_NEXT);
         this.soProducerIndex(var6);
      }

      return true;
   }

   public T peek() {
      AtomicReferenceArray var5 = this.consumerBuffer;
      long var2 = this.lpConsumerIndex();
      int var1 = this.consumerMask;
      Object var4 = lvElement(var5, calcWrappedOffset(var2, var1));
      return (T)(var4 == HAS_NEXT ? this.newBufferPeek(this.lvNextBufferAndUnlink(var5, var1 + 1), var2, var1) : var4);
   }

   @Override
   public T poll() {
      AtomicReferenceArray var6 = this.consumerBuffer;
      long var4 = this.lpConsumerIndex();
      int var2 = this.consumerMask;
      int var3 = calcWrappedOffset(var4, var2);
      Object var7 = lvElement(var6, var3);
      boolean var1;
      if (var7 == HAS_NEXT) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (var7 != null && !var1) {
         soElement(var6, var3, null);
         this.soConsumerIndex(var4 + 1L);
         return (T)var7;
      } else {
         return var1 ? this.newBufferPoll(this.lvNextBufferAndUnlink(var6, var2 + 1), var4, var2) : null;
      }
   }

   public int size() {
      long var1 = this.lvConsumerIndex();

      while (true) {
         long var5 = this.lvProducerIndex();
         long var3 = this.lvConsumerIndex();
         if (var1 == var3) {
            return (int)(var5 - var3);
         }

         var1 = var3;
      }
   }
}
