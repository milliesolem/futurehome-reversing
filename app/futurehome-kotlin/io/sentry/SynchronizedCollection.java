package io.sentry;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

class SynchronizedCollection<E> implements Collection<E>, Serializable {
   private static final long serialVersionUID = 2412805092710877986L;
   private final Collection<E> collection;
   final Object lock;

   SynchronizedCollection(Collection<E> var1) {
      if (var1 != null) {
         this.collection = var1;
         this.lock = this;
      } else {
         throw new NullPointerException("Collection must not be null.");
      }
   }

   SynchronizedCollection(Collection<E> var1, Object var2) {
      if (var1 != null) {
         if (var2 != null) {
            this.collection = var1;
            this.lock = var2;
         } else {
            throw new NullPointerException("Lock must not be null.");
         }
      } else {
         throw new NullPointerException("Collection must not be null.");
      }
   }

   public static <T> SynchronizedCollection<T> synchronizedCollection(Collection<T> var0) {
      return new SynchronizedCollection(var0);
   }

   @Override
   public boolean add(E param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/SynchronizedCollection.lock Ljava/lang/Object;
      // 04: astore 3
      // 05: aload 3
      // 06: monitorenter
      // 07: aload 0
      // 08: invokevirtual io/sentry/SynchronizedCollection.decorated ()Ljava/util/Collection;
      // 0b: aload 1
      // 0c: invokeinterface java/util/Collection.add (Ljava/lang/Object;)Z 2
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
   public boolean addAll(Collection<? extends E> param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/SynchronizedCollection.lock Ljava/lang/Object;
      // 04: astore 3
      // 05: aload 3
      // 06: monitorenter
      // 07: aload 0
      // 08: invokevirtual io/sentry/SynchronizedCollection.decorated ()Ljava/util/Collection;
      // 0b: aload 1
      // 0c: invokeinterface java/util/Collection.addAll (Ljava/util/Collection;)Z 2
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
   public void clear() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/SynchronizedCollection.lock Ljava/lang/Object;
      // 04: astore 1
      // 05: aload 1
      // 06: monitorenter
      // 07: aload 0
      // 08: invokevirtual io/sentry/SynchronizedCollection.decorated ()Ljava/util/Collection;
      // 0b: invokeinterface java/util/Collection.clear ()V 1
      // 10: aload 1
      // 11: monitorexit
      // 12: return
      // 13: astore 2
      // 14: aload 1
      // 15: monitorexit
      // 16: aload 2
      // 17: athrow
   }

   @Override
   public boolean contains(Object param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/SynchronizedCollection.lock Ljava/lang/Object;
      // 04: astore 3
      // 05: aload 3
      // 06: monitorenter
      // 07: aload 0
      // 08: invokevirtual io/sentry/SynchronizedCollection.decorated ()Ljava/util/Collection;
      // 0b: aload 1
      // 0c: invokeinterface java/util/Collection.contains (Ljava/lang/Object;)Z 2
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
   public boolean containsAll(Collection<?> param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/SynchronizedCollection.lock Ljava/lang/Object;
      // 04: astore 3
      // 05: aload 3
      // 06: monitorenter
      // 07: aload 0
      // 08: invokevirtual io/sentry/SynchronizedCollection.decorated ()Ljava/util/Collection;
      // 0b: aload 1
      // 0c: invokeinterface java/util/Collection.containsAll (Ljava/util/Collection;)Z 2
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

   protected Collection<E> decorated() {
      return this.collection;
   }

   @Override
   public boolean equals(Object param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/SynchronizedCollection.lock Ljava/lang/Object;
      // 04: astore 4
      // 06: aload 4
      // 08: monitorenter
      // 09: bipush 1
      // 0a: istore 3
      // 0b: aload 1
      // 0c: aload 0
      // 0d: if_acmpne 19
      // 10: aload 4
      // 12: monitorexit
      // 13: bipush 1
      // 14: ireturn
      // 15: astore 1
      // 16: goto 39
      // 19: iload 3
      // 1a: istore 2
      // 1b: aload 1
      // 1c: aload 0
      // 1d: if_acmpeq 34
      // 20: aload 0
      // 21: invokevirtual io/sentry/SynchronizedCollection.decorated ()Ljava/util/Collection;
      // 24: aload 1
      // 25: invokeinterface java/util/Collection.equals (Ljava/lang/Object;)Z 2
      // 2a: ifeq 32
      // 2d: iload 3
      // 2e: istore 2
      // 2f: goto 34
      // 32: bipush 0
      // 33: istore 2
      // 34: aload 4
      // 36: monitorexit
      // 37: iload 2
      // 38: ireturn
      // 39: aload 4
      // 3b: monitorexit
      // 3c: aload 1
      // 3d: athrow
   }

   @Override
   public int hashCode() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/SynchronizedCollection.lock Ljava/lang/Object;
      // 04: astore 2
      // 05: aload 2
      // 06: monitorenter
      // 07: aload 0
      // 08: invokevirtual io/sentry/SynchronizedCollection.decorated ()Ljava/util/Collection;
      // 0b: invokeinterface java/util/Collection.hashCode ()I 1
      // 10: istore 1
      // 11: aload 2
      // 12: monitorexit
      // 13: iload 1
      // 14: ireturn
      // 15: astore 3
      // 16: aload 2
      // 17: monitorexit
      // 18: aload 3
      // 19: athrow
   }

   @Override
   public boolean isEmpty() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/SynchronizedCollection.lock Ljava/lang/Object;
      // 04: astore 2
      // 05: aload 2
      // 06: monitorenter
      // 07: aload 0
      // 08: invokevirtual io/sentry/SynchronizedCollection.decorated ()Ljava/util/Collection;
      // 0b: invokeinterface java/util/Collection.isEmpty ()Z 1
      // 10: istore 1
      // 11: aload 2
      // 12: monitorexit
      // 13: iload 1
      // 14: ireturn
      // 15: astore 3
      // 16: aload 2
      // 17: monitorexit
      // 18: aload 3
      // 19: athrow
   }

   @Override
   public Iterator<E> iterator() {
      return this.decorated().iterator();
   }

   @Override
   public boolean remove(Object param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/SynchronizedCollection.lock Ljava/lang/Object;
      // 04: astore 3
      // 05: aload 3
      // 06: monitorenter
      // 07: aload 0
      // 08: invokevirtual io/sentry/SynchronizedCollection.decorated ()Ljava/util/Collection;
      // 0b: aload 1
      // 0c: invokeinterface java/util/Collection.remove (Ljava/lang/Object;)Z 2
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
   public boolean removeAll(Collection<?> param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/SynchronizedCollection.lock Ljava/lang/Object;
      // 04: astore 3
      // 05: aload 3
      // 06: monitorenter
      // 07: aload 0
      // 08: invokevirtual io/sentry/SynchronizedCollection.decorated ()Ljava/util/Collection;
      // 0b: aload 1
      // 0c: invokeinterface java/util/Collection.removeAll (Ljava/util/Collection;)Z 2
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
   public boolean retainAll(Collection<?> param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/SynchronizedCollection.lock Ljava/lang/Object;
      // 04: astore 3
      // 05: aload 3
      // 06: monitorenter
      // 07: aload 0
      // 08: invokevirtual io/sentry/SynchronizedCollection.decorated ()Ljava/util/Collection;
      // 0b: aload 1
      // 0c: invokeinterface java/util/Collection.retainAll (Ljava/util/Collection;)Z 2
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
   public int size() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/SynchronizedCollection.lock Ljava/lang/Object;
      // 04: astore 2
      // 05: aload 2
      // 06: monitorenter
      // 07: aload 0
      // 08: invokevirtual io/sentry/SynchronizedCollection.decorated ()Ljava/util/Collection;
      // 0b: invokeinterface java/util/Collection.size ()I 1
      // 10: istore 1
      // 11: aload 2
      // 12: monitorexit
      // 13: iload 1
      // 14: ireturn
      // 15: astore 3
      // 16: aload 2
      // 17: monitorexit
      // 18: aload 3
      // 19: athrow
   }

   @Override
   public Object[] toArray() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/SynchronizedCollection.lock Ljava/lang/Object;
      // 04: astore 1
      // 05: aload 1
      // 06: monitorenter
      // 07: aload 0
      // 08: invokevirtual io/sentry/SynchronizedCollection.decorated ()Ljava/util/Collection;
      // 0b: invokeinterface java/util/Collection.toArray ()[Ljava/lang/Object; 1
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
      // 01: getfield io/sentry/SynchronizedCollection.lock Ljava/lang/Object;
      // 04: astore 2
      // 05: aload 2
      // 06: monitorenter
      // 07: aload 0
      // 08: invokevirtual io/sentry/SynchronizedCollection.decorated ()Ljava/util/Collection;
      // 0b: aload 1
      // 0c: invokeinterface java/util/Collection.toArray ([Ljava/lang/Object;)[Ljava/lang/Object; 2
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

   @Override
   public String toString() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield io/sentry/SynchronizedCollection.lock Ljava/lang/Object;
      // 04: astore 1
      // 05: aload 1
      // 06: monitorenter
      // 07: aload 0
      // 08: invokevirtual io/sentry/SynchronizedCollection.decorated ()Ljava/util/Collection;
      // 0b: invokevirtual java/lang/Object.toString ()Ljava/lang/String;
      // 0e: astore 2
      // 0f: aload 1
      // 10: monitorexit
      // 11: aload 2
      // 12: areturn
      // 13: astore 2
      // 14: aload 1
      // 15: monitorexit
      // 16: aload 2
      // 17: athrow
   }
}
