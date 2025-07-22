package io.sentry;

import java.util.Queue;

final class SynchronizedQueue<E> extends SynchronizedCollection<E> implements Queue<E> {
   private static final long serialVersionUID = 1L;

   private SynchronizedQueue(Queue<E> var1) {
      super(var1);
   }

   protected SynchronizedQueue(Queue<E> var1, Object var2) {
      super(var1, var2);
   }

   static <E> SynchronizedQueue<E> synchronizedQueue(Queue<E> var0) {
      return new SynchronizedQueue<>(var0);
   }

   protected Queue<E> decorated() {
      return (Queue<E>)super.decorated();
   }

   @Override
   public E element() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/SynchronizedQueue.lock Ljava/lang/Object;
      // 04: astore 1
      // 05: aload 1
      // 06: monitorenter
      // 07: aload 0
      // 08: invokevirtual io/sentry/SynchronizedQueue.decorated ()Ljava/util/Queue;
      // 0b: invokeinterface java/util/Queue.element ()Ljava/lang/Object; 1
      // 10: astore 2
      // 11: aload 1
      // 12: monitorexit
      // 13: aload 2
      // 14: areturn
      // 15: astore 2
      // 16: aload 1
      // 17: monitorexit
      // 18: aload 2
      // 19: athrow
   }

   @Override
   public boolean equals(Object param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 1
      // 01: aload 0
      // 02: if_acmpne 07
      // 05: bipush 1
      // 06: ireturn
      // 07: aload 0
      // 08: getfield io/sentry/SynchronizedQueue.lock Ljava/lang/Object;
      // 0b: astore 3
      // 0c: aload 3
      // 0d: monitorenter
      // 0e: aload 0
      // 0f: invokevirtual io/sentry/SynchronizedQueue.decorated ()Ljava/util/Queue;
      // 12: aload 1
      // 13: invokevirtual java/lang/Object.equals (Ljava/lang/Object;)Z
      // 16: istore 2
      // 17: aload 3
      // 18: monitorexit
      // 19: iload 2
      // 1a: ireturn
      // 1b: astore 1
      // 1c: aload 3
      // 1d: monitorexit
      // 1e: aload 1
      // 1f: athrow
   }

   @Override
   public int hashCode() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/SynchronizedQueue.lock Ljava/lang/Object;
      // 04: astore 3
      // 05: aload 3
      // 06: monitorenter
      // 07: aload 0
      // 08: invokevirtual io/sentry/SynchronizedQueue.decorated ()Ljava/util/Queue;
      // 0b: invokevirtual java/lang/Object.hashCode ()I
      // 0e: istore 1
      // 0f: aload 3
      // 10: monitorexit
      // 11: iload 1
      // 12: ireturn
      // 13: astore 2
      // 14: aload 3
      // 15: monitorexit
      // 16: aload 2
      // 17: athrow
   }

   @Override
   public boolean offer(E param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/SynchronizedQueue.lock Ljava/lang/Object;
      // 04: astore 3
      // 05: aload 3
      // 06: monitorenter
      // 07: aload 0
      // 08: invokevirtual io/sentry/SynchronizedQueue.decorated ()Ljava/util/Queue;
      // 0b: aload 1
      // 0c: invokeinterface java/util/Queue.offer (Ljava/lang/Object;)Z 2
      // 11: istore 2
      // 12: aload 3
      // 13: monitorexit
      // 14: iload 2
      // 15: ireturn
      // 16: astore 1
      // 17: aload 3
      // 18: monitorexit
      // 19: aload 1
      // 1a: athrow
   }

   @Override
   public E peek() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/SynchronizedQueue.lock Ljava/lang/Object;
      // 04: astore 1
      // 05: aload 1
      // 06: monitorenter
      // 07: aload 0
      // 08: invokevirtual io/sentry/SynchronizedQueue.decorated ()Ljava/util/Queue;
      // 0b: invokeinterface java/util/Queue.peek ()Ljava/lang/Object; 1
      // 10: astore 2
      // 11: aload 1
      // 12: monitorexit
      // 13: aload 2
      // 14: areturn
      // 15: astore 2
      // 16: aload 1
      // 17: monitorexit
      // 18: aload 2
      // 19: athrow
   }

   @Override
   public E poll() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/SynchronizedQueue.lock Ljava/lang/Object;
      // 04: astore 1
      // 05: aload 1
      // 06: monitorenter
      // 07: aload 0
      // 08: invokevirtual io/sentry/SynchronizedQueue.decorated ()Ljava/util/Queue;
      // 0b: invokeinterface java/util/Queue.poll ()Ljava/lang/Object; 1
      // 10: astore 2
      // 11: aload 1
      // 12: monitorexit
      // 13: aload 2
      // 14: areturn
      // 15: astore 2
      // 16: aload 1
      // 17: monitorexit
      // 18: aload 2
      // 19: athrow
   }

   @Override
   public E remove() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/SynchronizedQueue.lock Ljava/lang/Object;
      // 04: astore 1
      // 05: aload 1
      // 06: monitorenter
      // 07: aload 0
      // 08: invokevirtual io/sentry/SynchronizedQueue.decorated ()Ljava/util/Queue;
      // 0b: invokeinterface java/util/Queue.remove ()Ljava/lang/Object; 1
      // 10: astore 2
      // 11: aload 1
      // 12: monitorexit
      // 13: aload 2
      // 14: areturn
      // 15: astore 2
      // 16: aload 1
      // 17: monitorexit
      // 18: aload 2
      // 19: athrow
   }

   @Override
   public Object[] toArray() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/SynchronizedQueue.lock Ljava/lang/Object;
      // 04: astore 1
      // 05: aload 1
      // 06: monitorenter
      // 07: aload 0
      // 08: invokevirtual io/sentry/SynchronizedQueue.decorated ()Ljava/util/Queue;
      // 0b: invokeinterface java/util/Queue.toArray ()[Ljava/lang/Object; 1
      // 10: astore 2
      // 11: aload 1
      // 12: monitorexit
      // 13: aload 2
      // 14: areturn
      // 15: astore 2
      // 16: aload 1
      // 17: monitorexit
      // 18: aload 2
      // 19: athrow
   }

   @Override
   public <T> T[] toArray(T[] param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/SynchronizedQueue.lock Ljava/lang/Object;
      // 04: astore 2
      // 05: aload 2
      // 06: monitorenter
      // 07: aload 0
      // 08: invokevirtual io/sentry/SynchronizedQueue.decorated ()Ljava/util/Queue;
      // 0b: aload 1
      // 0c: invokeinterface java/util/Queue.toArray ([Ljava/lang/Object;)[Ljava/lang/Object; 2
      // 11: astore 1
      // 12: aload 2
      // 13: monitorexit
      // 14: aload 1
      // 15: areturn
      // 16: astore 1
      // 17: aload 2
      // 18: monitorexit
      // 19: aload 1
      // 1a: athrow
   }
}
