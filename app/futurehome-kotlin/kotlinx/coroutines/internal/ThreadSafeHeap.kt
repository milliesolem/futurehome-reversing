package kotlinx.coroutines.internal

import java.util.Arrays
import kotlinx.atomicfu.AtomicInt
import kotlinx.coroutines.DebugKt

public open class ThreadSafeHeap<T extends ThreadSafeHeapNode & java.lang.Comparable<? super T>> {
   private final val _size: AtomicInt
   private final var a: Array<Any?>?

   public final val isEmpty: Boolean
      public final get() {
         val var1: Boolean;
         if (this.getSize() == 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }


   public final var size: Int
      public final get() {
         return _size$FU.get(this);
      }

      public final set(value) {
         _size$FU.set(this, var1);
      }


   private fun realloc(): Array<Any?> {
      val var2: Array<ThreadSafeHeapNode> = this.a;
      var var1: Array<ThreadSafeHeapNode>;
      if (this.a == null) {
         var1 = new ThreadSafeHeapNode[4];
         this.a = (T[])var1;
      } else {
         var1 = this.a;
         if (this.getSize() >= var2.length) {
            val var3: Array<Any> = Arrays.copyOf(var2, this.getSize() * 2);
            var1 = var3 as Array<ThreadSafeHeapNode>;
            this.a = (T[])var3;
         }
      }

      return (T[])var1;
   }

   private tailrec fun siftDownFrom(i: Int) {
      var var2: Int = var1;

      while (true) {
         var var3: Int = var2 * 2;
         var1 = var2 * 2 + 1;
         if (var2 * 2 + 1 >= this.getSize()) {
            return;
         }

         val var4: Array<ThreadSafeHeapNode> = this.a;
         var3 = var3 + 2;
         if (var3 + 2 < this.getSize()) {
            val var5: ThreadSafeHeapNode = var4[var3];
            val var10: java.lang.Comparable = var5 as java.lang.Comparable;
            val var6: ThreadSafeHeapNode = var4[var1];
            if (var10.compareTo(var6) < 0) {
               var1 = var3;
            }
         }

         val var11: ThreadSafeHeapNode = var4[var2];
         val var12: java.lang.Comparable = var11 as java.lang.Comparable;
         val var9: ThreadSafeHeapNode = var4[var1];
         if (var12.compareTo(var9) <= 0) {
            return;
         }

         this.swap(var2, var1);
         var2 = var1;
      }
   }

   private tailrec fun siftUpFrom(i: Int) {
      while (var1 > 0) {
         val var4: Array<ThreadSafeHeapNode> = this.a;
         val var2: Int = (var1 - 1) / 2;
         val var3: ThreadSafeHeapNode = var4[(var1 - 1) / 2];
         val var5: java.lang.Comparable = var3 as java.lang.Comparable;
         val var6: ThreadSafeHeapNode = var4[var1];
         if (var5.compareTo(var6) <= 0) {
            return;
         }

         this.swap(var1, var2);
         var1 = var2;
      }
   }

   private fun swap(i: Int, j: Int) {
      val var3: Array<ThreadSafeHeapNode> = this.a;
      val var4: ThreadSafeHeapNode = var3[var2];
      val var5: ThreadSafeHeapNode = var3[var1];
      var3[var1] = var4;
      var3[var2] = var5;
      var4.setIndex(var1);
      var5.setIndex(var2);
   }

   internal fun addImpl(node: Any) {
      if (DebugKt.getASSERTIONS_ENABLED() && var1.getHeap() != null) {
         throw new AssertionError();
      } else {
         var1.setHeap(this);
         val var3: Array<ThreadSafeHeapNode> = this.realloc();
         val var2: Int = this.getSize();
         this.setSize(var2 + 1);
         var3[var2] = var1;
         var1.setIndex(var2);
         this.siftUpFrom(var2);
      }
   }

   public fun addLast(node: Any) {
      label13: {
         synchronized (this){} // $VF: monitorenter 

         try {
            this.addImpl((T)var1);
         } catch (var2: java.lang.Throwable) {
            // $VF: monitorexit
         }

         // $VF: monitorexit
      }
   }

   public inline fun addLastIf(node: Any, cond: (Any?) -> Boolean): Boolean {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.IfStatement.initExprents(IfStatement.java:276)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:189)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:148)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 2
      // 03: aload 0
      // 04: invokevirtual kotlinx/coroutines/internal/ThreadSafeHeap.firstImpl ()Lkotlinx/coroutines/internal/ThreadSafeHeapNode;
      // 07: invokeinterface kotlin/jvm/functions/Function1.invoke (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 0c: checkcast java/lang/Boolean
      // 0f: invokevirtual java/lang/Boolean.booleanValue ()Z
      // 12: ifeq 1f
      // 15: aload 0
      // 16: aload 1
      // 17: invokevirtual kotlinx/coroutines/internal/ThreadSafeHeap.addImpl (Lkotlinx/coroutines/internal/ThreadSafeHeapNode;)V
      // 1a: bipush 1
      // 1b: istore 3
      // 1c: goto 21
      // 1f: bipush 0
      // 20: istore 3
      // 21: aload 0
      // 22: monitorexit
      // 23: iload 3
      // 24: ireturn
      // 25: astore 1
      // 26: aload 0
      // 27: monitorexit
      // 28: aload 1
      // 29: athrow
   }

   public fun clear() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: getfield kotlinx/coroutines/internal/ThreadSafeHeap.a [Lkotlinx/coroutines/internal/ThreadSafeHeapNode;
      // 06: astore 1
      // 07: aload 1
      // 08: ifnull 15
      // 0b: aload 1
      // 0c: aconst_null
      // 0d: bipush 0
      // 0e: bipush 0
      // 0f: bipush 6
      // 11: aconst_null
      // 12: invokestatic kotlin/collections/ArraysKt.fill$default ([Ljava/lang/Object;Ljava/lang/Object;IIILjava/lang/Object;)V
      // 15: getstatic kotlinx/coroutines/internal/ThreadSafeHeap._size$FU Ljava/util/concurrent/atomic/AtomicIntegerFieldUpdater;
      // 18: aload 0
      // 19: bipush 0
      // 1a: invokevirtual java/util/concurrent/atomic/AtomicIntegerFieldUpdater.set (Ljava/lang/Object;I)V
      // 1d: getstatic kotlin/Unit.INSTANCE Lkotlin/Unit;
      // 20: astore 1
      // 21: aload 0
      // 22: monitorexit
      // 23: return
      // 24: astore 1
      // 25: aload 0
      // 26: monitorexit
      // 27: aload 1
      // 28: athrow
   }

   public fun find(predicate: (Any) -> Boolean): Any? {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.insertSemaphore(FinallyProcessor.java:350)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:99)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: invokevirtual kotlinx/coroutines/internal/ThreadSafeHeap.getSize ()I
      // 06: istore 3
      // 07: bipush 0
      // 08: istore 2
      // 09: aconst_null
      // 0a: astore 5
      // 0c: aconst_null
      // 0d: astore 6
      // 0f: iload 2
      // 10: iload 3
      // 11: if_icmpge 4c
      // 14: aload 0
      // 15: getfield kotlinx/coroutines/internal/ThreadSafeHeap.a [Lkotlinx/coroutines/internal/ThreadSafeHeapNode;
      // 18: astore 7
      // 1a: aload 6
      // 1c: astore 5
      // 1e: aload 7
      // 20: ifnull 29
      // 23: aload 7
      // 25: iload 2
      // 26: aaload
      // 27: astore 5
      // 29: aload 5
      // 2b: invokestatic kotlin/jvm/internal/Intrinsics.checkNotNull (Ljava/lang/Object;)V
      // 2e: aload 1
      // 2f: aload 5
      // 31: invokeinterface kotlin/jvm/functions/Function1.invoke (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 36: checkcast java/lang/Boolean
      // 39: invokevirtual java/lang/Boolean.booleanValue ()Z
      // 3c: istore 4
      // 3e: iload 4
      // 40: ifeq 46
      // 43: goto 4c
      // 46: iinc 2 1
      // 49: goto 09
      // 4c: aload 0
      // 4d: monitorexit
      // 4e: aload 5
      // 50: areturn
      // 51: astore 1
      // 52: aload 0
      // 53: monitorexit
      // 54: aload 1
      // 55: athrow
   }

   internal fun firstImpl(): Any? {
      val var2: ThreadSafeHeapNode;
      if (this.a != null) {
         var2 = this.a[0];
      } else {
         var2 = null;
      }

      return (T)var2;
   }

   public fun peek(): Any? {
      label13: {
         synchronized (this){} // $VF: monitorenter 

         try {
            val var1: ThreadSafeHeapNode = this.firstImpl();
         } catch (var2: java.lang.Throwable) {
            // $VF: monitorexit
         }

         // $VF: monitorexit
      }
   }

   public fun remove(node: Any): Boolean {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.insertSemaphore(FinallyProcessor.java:350)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:99)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 1
      // 03: invokeinterface kotlinx/coroutines/internal/ThreadSafeHeapNode.getHeap ()Lkotlinx/coroutines/internal/ThreadSafeHeap; 1
      // 08: ifnonnull 10
      // 0b: bipush 0
      // 0c: istore 3
      // 0d: goto 36
      // 10: aload 1
      // 11: invokeinterface kotlinx/coroutines/internal/ThreadSafeHeapNode.getIndex ()I 1
      // 16: istore 2
      // 17: invokestatic kotlinx/coroutines/DebugKt.getASSERTIONS_ENABLED ()Z
      // 1a: ifeq 2e
      // 1d: iload 2
      // 1e: iflt 24
      // 21: goto 2e
      // 24: new java/lang/AssertionError
      // 27: astore 1
      // 28: aload 1
      // 29: invokespecial java/lang/AssertionError.<init> ()V
      // 2c: aload 1
      // 2d: athrow
      // 2e: aload 0
      // 2f: iload 2
      // 30: invokevirtual kotlinx/coroutines/internal/ThreadSafeHeap.removeAtImpl (I)Lkotlinx/coroutines/internal/ThreadSafeHeapNode;
      // 33: pop
      // 34: bipush 1
      // 35: istore 3
      // 36: aload 0
      // 37: monitorexit
      // 38: iload 3
      // 39: ireturn
      // 3a: astore 1
      // 3b: aload 0
      // 3c: monitorexit
      // 3d: aload 1
      // 3e: athrow
   }

   internal fun removeAtImpl(index: Int): Any {
      if (DebugKt.getASSERTIONS_ENABLED() && this.getSize() <= 0) {
         throw new AssertionError();
      } else {
         var var3: Array<ThreadSafeHeapNode>;
         var3 = this.a;
         this.setSize(this.getSize() - 1);
         label27:
         if (var1 < this.getSize()) {
            this.swap(var1, this.getSize());
            val var2: Int = (var1 - 1) / 2;
            if (var1 > 0) {
               val var4: ThreadSafeHeapNode = var3[var1];
               val var6: java.lang.Comparable = var4 as java.lang.Comparable;
               val var5: ThreadSafeHeapNode = var3[var2];
               if (var6.compareTo(var5) < 0) {
                  this.swap(var1, var2);
                  this.siftUpFrom(var2);
                  break label27;
               }
            }

            this.siftDownFrom(var1);
         }

         val var7: ThreadSafeHeapNode = var3[this.getSize()];
         if (DebugKt.getASSERTIONS_ENABLED() && var7.getHeap() != this) {
            throw new AssertionError();
         } else {
            var7.setHeap(null);
            var7.setIndex(-1);
            var3[this.getSize()] = null;
            return (T)var7;
         }
      }
   }

   public inline fun removeFirstIf(predicate: (Any) -> Boolean): Any? {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: invokevirtual kotlinx/coroutines/internal/ThreadSafeHeap.firstImpl ()Lkotlinx/coroutines/internal/ThreadSafeHeapNode;
      // 06: astore 3
      // 07: aconst_null
      // 08: astore 2
      // 09: aload 3
      // 0a: ifnonnull 11
      // 0d: aload 0
      // 0e: monitorexit
      // 0f: aconst_null
      // 10: areturn
      // 11: aload 1
      // 12: aload 3
      // 13: invokeinterface kotlin/jvm/functions/Function1.invoke (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 18: checkcast java/lang/Boolean
      // 1b: invokevirtual java/lang/Boolean.booleanValue ()Z
      // 1e: ifeq 27
      // 21: aload 0
      // 22: bipush 0
      // 23: invokevirtual kotlinx/coroutines/internal/ThreadSafeHeap.removeAtImpl (I)Lkotlinx/coroutines/internal/ThreadSafeHeapNode;
      // 26: astore 2
      // 27: aload 0
      // 28: monitorexit
      // 29: aload 2
      // 2a: areturn
      // 2b: astore 1
      // 2c: aload 0
      // 2d: monitorexit
      // 2e: aload 1
      // 2f: athrow
   }

   public fun removeFirstOrNull(): Any? {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.IfStatement.initExprents(IfStatement.java:276)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:189)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.initStatementExprents(ExprProcessor.java:192)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.processStatement(ExprProcessor.java:148)
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: aload 0
      // 03: invokevirtual kotlinx/coroutines/internal/ThreadSafeHeap.getSize ()I
      // 06: ifle 12
      // 09: aload 0
      // 0a: bipush 0
      // 0b: invokevirtual kotlinx/coroutines/internal/ThreadSafeHeap.removeAtImpl (I)Lkotlinx/coroutines/internal/ThreadSafeHeapNode;
      // 0e: astore 1
      // 0f: goto 14
      // 12: aconst_null
      // 13: astore 1
      // 14: aload 0
      // 15: monitorexit
      // 16: aload 1
      // 17: areturn
      // 18: astore 1
      // 19: aload 0
      // 1a: monitorexit
      // 1b: aload 1
      // 1c: athrow
   }
}
