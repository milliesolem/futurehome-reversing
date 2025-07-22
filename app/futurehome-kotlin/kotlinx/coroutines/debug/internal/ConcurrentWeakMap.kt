package kotlinx.coroutines.debug.internal

import com.google.common.util.concurrent.Striped.SmallLazyStriped..ExternalSyntheticBackportWithForwarding0
import java.lang.ref.Reference
import java.lang.ref.ReferenceQueue
import java.util.NoSuchElementException
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater
import java.util.concurrent.atomic.AtomicReferenceArray
import kotlin.collections.MutableMap.MutableEntry
import kotlin.jvm.internal.Intrinsics
import kotlin.jvm.internal.markers.KMutableIterator
import kotlin.jvm.internal.markers.KMutableMap
import kotlinx.atomicfu.AtomicArray
import kotlinx.atomicfu.AtomicInt
import kotlinx.atomicfu.AtomicRef

internal class ConcurrentWeakMap<K, V>(weakRefQueue: Boolean = false) : AbstractMutableMap<K, V> {
   private final val _size: AtomicInt
   private final val core: AtomicRef<kotlinx.coroutines.debug.internal.ConcurrentWeakMap.Core>

   public open val entries: MutableSet<MutableEntry<Any, Any>>
      public open get() {
         return new ConcurrentWeakMap.KeyValueSet<>(this, <unrepresentable>.INSTANCE);
      }


   public open val keys: MutableSet<Any>
      public open get() {
         return new ConcurrentWeakMap.KeyValueSet<>(this, <unrepresentable>.INSTANCE);
      }


   public open val size: Int
      public open get() {
         return _size$FU.get(this);
      }


   private final val weakRefQueue: ReferenceQueue<Any>?

   fun ConcurrentWeakMap() {
      this(false, 1, null);
   }

   init {
      val var2: ReferenceQueue;
      if (var1) {
         var2 = new ReferenceQueue();
      } else {
         var2 = null;
      }

      this.weakRefQueue = var2;
   }

   private fun cleanWeakRef(w: HashedWeakRef<*>) {
      (core$FU.get(this) as ConcurrentWeakMap.Core).cleanWeakRef(var1);
   }

   private fun decrementSize() {
      _size$FU.decrementAndGet(this);
   }

   private fun putSynchronized(key: Any, value: Any?): Any? {
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
      //
      // Bytecode:
      // 00: aload 0
      // 01: monitorenter
      // 02: getstatic kotlinx/coroutines/debug/internal/ConcurrentWeakMap.core$FU Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;
      // 05: aload 0
      // 06: invokevirtual java/util/concurrent/atomic/AtomicReferenceFieldUpdater.get (Ljava/lang/Object;)Ljava/lang/Object;
      // 09: checkcast kotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core
      // 0c: astore 3
      // 0d: aload 3
      // 0e: aload 1
      // 0f: aload 2
      // 10: aconst_null
      // 11: bipush 4
      // 12: aconst_null
      // 13: invokestatic kotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core.putImpl$default (Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core;Ljava/lang/Object;Ljava/lang/Object;Lkotlinx/coroutines/debug/internal/HashedWeakRef;ILjava/lang/Object;)Ljava/lang/Object;
      // 16: astore 5
      // 18: invokestatic kotlinx/coroutines/debug/internal/ConcurrentWeakMapKt.access$getREHASH$p ()Lkotlinx/coroutines/internal/Symbol;
      // 1b: astore 4
      // 1d: aload 5
      // 1f: aload 4
      // 21: if_acmpeq 29
      // 24: aload 0
      // 25: monitorexit
      // 26: aload 5
      // 28: areturn
      // 29: aload 3
      // 2a: invokevirtual kotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core.rehash ()Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap$Core;
      // 2d: astore 3
      // 2e: getstatic kotlinx/coroutines/debug/internal/ConcurrentWeakMap.core$FU Ljava/util/concurrent/atomic/AtomicReferenceFieldUpdater;
      // 31: aload 0
      // 32: aload 3
      // 33: invokevirtual java/util/concurrent/atomic/AtomicReferenceFieldUpdater.set (Ljava/lang/Object;Ljava/lang/Object;)V
      // 36: goto 0d
      // 39: astore 1
      // 3a: aload 0
      // 3b: monitorexit
      // 3c: aload 1
      // 3d: athrow
   }

   public override fun clear() {
      val var1: java.util.Iterator = this.keySet().iterator();

      while (var1.hasNext()) {
         this.remove(var1.next());
      }
   }

   public override operator fun get(key: Any): Any? {
      return (V)(if (var1 == null) null else (core$FU.get(this) as ConcurrentWeakMap.Core).getImpl((K)var1));
   }

   public override fun put(key: Any, value: Any): Any? {
      val var4: Any = ConcurrentWeakMap.Core.putImpl$default(core$FU.get(this) as ConcurrentWeakMap.Core, var1, var2, null, 4, null);
      var var3: Any = var4;
      if (var4 === ConcurrentWeakMapKt.access$getREHASH$p()) {
         var3 = this.putSynchronized((K)var1, (V)var2);
      }

      if (var3 == null) {
         _size$FU.incrementAndGet(this);
      }

      return (V)var3;
   }

   public override fun remove(key: Any): Any? {
      if (var1 == null) {
         return null;
      } else {
         val var3: Any = ConcurrentWeakMap.Core.putImpl$default(core$FU.get(this) as ConcurrentWeakMap.Core, var1, null, null, 4, null);
         var var2: Any = var3;
         if (var3 === ConcurrentWeakMapKt.access$getREHASH$p()) {
            var2 = this.putSynchronized((K)var1, null);
         }

         if (var2 != null) {
            _size$FU.decrementAndGet(this);
         }

         return (V)var2;
      }
   }

   public fun runWeakRefQueueCleaningLoopUntilInterrupted() {
      if (this.weakRefQueue != null) {
         while (true) {
            try {
               val var1: Reference = this.weakRefQueue.remove();
               this.cleanWeakRef(var1 as HashedWeakRef<?>);
            } catch (var2: InterruptedException) {
               Thread.currentThread().interrupt();
               return;
            }
         }
      } else {
         throw new IllegalStateException("Must be created with weakRefQueue = true".toString());
      }
   }

   private inner class Core(allocated: Int) {
      private final val allocated: Int
      private final val keys: AtomicArray<HashedWeakRef<Any>?>
      private final val load: AtomicInt
      private final val shift: Int
      private final val threshold: Int
      private final val values: AtomicArray<Any?>

      init {
         this.this$0 = var1;
         this.allocated = var2;
         this.shift = Integer.numberOfLeadingZeros(var2) + 1;
         this.threshold = var2 * 2 / 3;
         this.keys = new AtomicReferenceArray(var2);
         this.values = new AtomicReferenceArray(var2);
      }

      private fun index(hash: Int): Int {
         return var1 * -1640531527 ushr this.shift;
      }

      private fun removeCleanedAt(index: Int) {
         val var2: Any;
         do {
            var2 = this.values.get(var1);
            if (var2 == null) {
               return;
            }

            if (var2 is Marked) {
               return;
            }
         } while (!ExternalSyntheticBackportWithForwarding0.m(this.values, var1, var2, null));

         ConcurrentWeakMap.access$decrementSize(this.this$0);
      }

      fun `update$atomicfu`(var1: AtomicIntegerFieldUpdater, var2: (Int?) -> Int, var3: Any) {
         val var4: Int;
         do {
            var4 = var1.get(var3);
         } while (!var1.compareAndSet(var3, var4, ((java.lang.Number)var2.invoke(var4)).intValue()));
      }

      public fun cleanWeakRef(weakRef: HashedWeakRef<*>) {
         var var2: Int = this.index(var1.hash);

         while (true) {
            val var4: HashedWeakRef = this.keys.get(var2) as HashedWeakRef;
            if (var4 == null) {
               return;
            }

            if (var4 === var1) {
               this.removeCleanedAt(var2);
               return;
            }

            var var3: Int = var2;
            if (var2 == 0) {
               var3 = this.allocated;
            }

            var2 = var3 - 1;
         }
      }

      public fun getImpl(key: Any): Any? {
         var var2: Int = this.index(var1.hashCode());

         while (true) {
            val var4: HashedWeakRef = this.keys.get(var2) as HashedWeakRef;
            if (var4 == null) {
               return null;
            }

            var var6: Any = var4.get();
            if (var1 == var6) {
               var6 = this.values.get(var2);
               var1 = var6;
               if (var6 is Marked) {
                  var1 = (var6 as Marked).ref;
               }

               return (V)var1;
            }

            if (var6 == null) {
               this.removeCleanedAt(var2);
            }

            var var3: Int = var2;
            if (var2 == 0) {
               var3 = this.allocated;
            }

            var2 = var3 - 1;
         }
      }

      public fun <E> keyValueIterator(factory: (Any, Any) -> E): MutableIterator<E> {
         return new ConcurrentWeakMap.Core.KeyValueIterator(this, var1);
      }

      public fun putImpl(key: Any, value: Any?, weakKey0: HashedWeakRef<Any>? = null): Any? {
         var var4: Int = this.index(var1.hashCode());
         var var5: Int = 0;

         while (true) {
            var var7: HashedWeakRef = this.keys.get(var4) as HashedWeakRef;
            if (var7 == null) {
               if (var2 == null) {
                  return null;
               }

               var var10: Boolean = (boolean)var5;
               if (!var5) {
                  val var12: AtomicIntegerFieldUpdater = load$FU;

                  do {
                     var5 = var12.get(this);
                     if (var5 >= this.threshold) {
                        return ConcurrentWeakMapKt.access$getREHASH$p();
                     }
                  } while (!var12.compareAndSet(this, var5, var5 + 1));

                  var10 = true;
               }

               var7 = var3;
               if (var3 == null) {
                  var7 = new HashedWeakRef<>(var1, ConcurrentWeakMap.access$getWeakRefQueue$p(this.this$0));
               }

               if (ExternalSyntheticBackportWithForwarding0.m(this.keys, var4, null, var7)) {
                  break;
               }

               var5 = var10;
               var3 = var7;
            } else {
               val var11: Any = var7.get();
               if (var1 == var11) {
                  if (var5) {
                     load$FU.decrementAndGet(this);
                  }
                  break;
               }

               if (var11 == null) {
                  this.removeCleanedAt(var4);
               }

               var var6: Int = var4;
               if (var4 == 0) {
                  var6 = this.allocated;
               }

               var4 = var6 - 1;
            }
         }

         do {
            var1 = this.values.get(var4);
            if (var1 is Marked) {
               return ConcurrentWeakMapKt.access$getREHASH$p();
            }
         } while (!ExternalSyntheticBackportWithForwarding0.m(this.values, var4, var1, var2));

         return var1;
      }

      public fun rehash(): kotlinx.coroutines.debug.internal.ConcurrentWeakMap.Core {
         label39:
         while (true) {
            val var5: ConcurrentWeakMap.Core = this.this$0.new Core((int)this.this$0, Integer.highestOneBit(RangesKt.coerceAtLeast(this.this$0.size(), 4)) * 4);
            val var2: Int = this.allocated;

            for (int var7 = 0; var7 < var2; var7++) {
               val var6: HashedWeakRef = this.keys.get(var7) as HashedWeakRef;
               val var3: Any;
               if (var6 != null) {
                  var3 = var6.get();
               } else {
                  var3 = null;
               }

               if (var6 != null && var3 == null) {
                  this.removeCleanedAt(var7);
               }

               var var4: Any;
               do {
                  var4 = this.values.get(var7);
                  if (var4 is Marked) {
                     var4 = (var4 as Marked).ref;
                     break;
                  }
               } while (!ExternalSyntheticBackportWithForwarding0.m(this.values, var7, var4, ConcurrentWeakMapKt.access$mark(var4)));

               if (var3 != null && var4 != null && var5.putImpl(var3, (HashedWeakRef)var4, var6) === ConcurrentWeakMapKt.access$getREHASH$p()) {
                  continue label39;
               }
            }

            return var5;
         }
      }

      private inner class KeyValueIterator<E>(factory: (Any, Any) -> Any) : java.util.Iterator<E>, KMutableIterator {
         private final val factory: (Any, Any) -> Any
         private final var index: Int
         private final lateinit var key: Any
         private final lateinit var value: Any

         init {
            this.this$0 = var1;
            this.factory = var2;
            this.index = -1;
            this.findNext();
         }

         private fun findNext() {
            while (true) {
               val var1: Int = this.index + 1;
               this.index += 1;
               if (var1 < ConcurrentWeakMap.Core.access$getAllocated$p(this.this$0)) {
                  val var2: HashedWeakRef = ConcurrentWeakMap.Core.access$getKeys$p(this.this$0).get(this.index) as HashedWeakRef;
                  if (var2 == null) {
                     continue;
                  }

                  var var4: Any = var2.get();
                  if (var4 == null) {
                     continue;
                  }

                  this.key = (K)var4;
                  val var3: Any = ConcurrentWeakMap.Core.access$getValues$p(this.this$0).get(this.index);
                  var4 = var3;
                  if (var3 is Marked) {
                     var4 = (var3 as Marked).ref;
                  }

                  if (var4 == null) {
                     continue;
                  }

                  this.value = (V)var4;
               }

               return;
            }
         }

         public override operator fun hasNext(): Boolean {
            val var1: Boolean;
            if (this.index < ConcurrentWeakMap.Core.access$getAllocated$p(this.this$0)) {
               var1 = true;
            } else {
               var1 = false;
            }

            return var1;
         }

         public override operator fun next(): Any {
            if (this.index < ConcurrentWeakMap.Core.access$getAllocated$p(this.this$0)) {
               var var1: Any = this.key;
               if (this.key == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("key");
                  var1 = Unit.INSTANCE;
               }

               var var6: Any = this.value;
               if (this.value == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("value");
                  var6 = Unit.INSTANCE;
               }

               var1 = this.factory.invoke((K)var1, (V)var6);
               this.findNext();
               return (E)var1;
            } else {
               throw new NoSuchElementException();
            }
         }

         public open fun remove(): Nothing {
            ConcurrentWeakMapKt.access$noImpl();
            throw new KotlinNothingValueException();
         }
      }
   }

   private class Entry<K, V>(key: Any, value: Any) : java.util.Map.Entry<K, V>, KMutableMap.Entry {
      public open val key: Any
      public open val value: Any

      init {
         this.key = (K)var1;
         this.value = (V)var2;
      }

      public override fun setValue(newValue: Any): Any {
         ConcurrentWeakMapKt.access$noImpl();
         throw new KotlinNothingValueException();
      }
   }

   private inner class KeyValueSet<E>(factory: (Any, Any) -> Any) : AbstractMutableSet<E> {
      private final val factory: (Any, Any) -> Any

      public open val size: Int
         public open get() {
            return this.this$0.size();
         }


      init {
         this.this$0 = var1;
         this.factory = var2;
      }

      public override fun add(element: Any): Boolean {
         ConcurrentWeakMapKt.access$noImpl();
         throw new KotlinNothingValueException();
      }

      public override operator fun iterator(): MutableIterator<Any> {
         return (ConcurrentWeakMap.access$getCore$FU$p().get(this.this$0) as ConcurrentWeakMap.Core).keyValueIterator(this.factory);
      }
   }
}
