package io.reactivex.internal.queue;

import io.reactivex.internal.fuseable.SimplePlainQueue;
import java.util.concurrent.atomic.AtomicReference;

public final class MpscLinkedQueue<T> implements SimplePlainQueue<T> {
   private final AtomicReference<MpscLinkedQueue.LinkedQueueNode<T>> consumerNode;
   private final AtomicReference<MpscLinkedQueue.LinkedQueueNode<T>> producerNode = new AtomicReference<>();

   public MpscLinkedQueue() {
      this.consumerNode = new AtomicReference<>();
      MpscLinkedQueue.LinkedQueueNode var1 = new MpscLinkedQueue.LinkedQueueNode();
      this.spConsumerNode(var1);
      this.xchgProducerNode(var1);
   }

   @Override
   public void clear() {
      while (this.poll() != null && !this.isEmpty()) {
      }
   }

   @Override
   public boolean isEmpty() {
      boolean var1;
      if (this.lvConsumerNode() == this.lvProducerNode()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   MpscLinkedQueue.LinkedQueueNode<T> lpConsumerNode() {
      return this.consumerNode.get();
   }

   MpscLinkedQueue.LinkedQueueNode<T> lvConsumerNode() {
      return this.consumerNode.get();
   }

   MpscLinkedQueue.LinkedQueueNode<T> lvProducerNode() {
      return this.producerNode.get();
   }

   @Override
   public boolean offer(T var1) {
      if (var1 != null) {
         var1 = new MpscLinkedQueue.LinkedQueueNode<>(var1);
         this.xchgProducerNode(var1).soNext(var1);
         return true;
      } else {
         throw new NullPointerException("Null is not a valid element");
      }
   }

   @Override
   public boolean offer(T var1, T var2) {
      this.offer((T)var1);
      this.offer((T)var2);
      return true;
   }

   @Override
   public T poll() {
      MpscLinkedQueue.LinkedQueueNode var1 = this.lpConsumerNode();
      MpscLinkedQueue.LinkedQueueNode var2 = var1.lvNext();
      if (var2 != null) {
         Object var4 = var2.getAndNullValue();
         this.spConsumerNode(var2);
         return (T)var4;
      } else if (var1 == this.lvProducerNode()) {
         return null;
      } else {
         do {
            var2 = var1.lvNext();
         } while (var2 == null);

         Object var3 = var2.getAndNullValue();
         this.spConsumerNode(var2);
         return (T)var3;
      }
   }

   void spConsumerNode(MpscLinkedQueue.LinkedQueueNode<T> var1) {
      this.consumerNode.lazySet(var1);
   }

   MpscLinkedQueue.LinkedQueueNode<T> xchgProducerNode(MpscLinkedQueue.LinkedQueueNode<T> var1) {
      return this.producerNode.getAndSet(var1);
   }

   static final class LinkedQueueNode<E> extends AtomicReference<MpscLinkedQueue.LinkedQueueNode<E>> {
      private static final long serialVersionUID = 2404266111789071508L;
      private E value;

      LinkedQueueNode() {
      }

      LinkedQueueNode(E var1) {
         this.spValue((E)var1);
      }

      public E getAndNullValue() {
         Object var1 = this.lpValue();
         this.spValue(null);
         return (E)var1;
      }

      public E lpValue() {
         return this.value;
      }

      public MpscLinkedQueue.LinkedQueueNode<E> lvNext() {
         return this.get();
      }

      public void soNext(MpscLinkedQueue.LinkedQueueNode<E> var1) {
         this.lazySet(var1);
      }

      public void spValue(E var1) {
         this.value = (E)var1;
      }
   }
}
