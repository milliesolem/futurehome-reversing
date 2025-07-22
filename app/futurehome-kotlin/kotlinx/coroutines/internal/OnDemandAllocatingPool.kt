package kotlinx.coroutines.internal

import java.util.ArrayList
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater
import java.util.concurrent.atomic.AtomicReferenceArray
import kotlinx.atomicfu.AtomicArray
import kotlinx.atomicfu.AtomicInt

internal class OnDemandAllocatingPool<T>(maxCapacity: Int, create: (Int) -> Any) {
   private final val controlState: AtomicInt
   private final val create: (Int) -> Any
   private final val elements: AtomicArray<Any?>
   private final val maxCapacity: Int

   init {
      this.maxCapacity = var1;
      this.create = var2;
      this.elements = new AtomicReferenceArray(var1);
   }

   private inline fun Int.isClosed(): Boolean {
      val var2: Boolean;
      if ((var1 and Integer.MIN_VALUE) != 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   fun `loop$atomicfu`(var1: AtomicIntegerFieldUpdater, var2: (Int?) -> Unit, var3: Any) {
      while (true) {
         var2.invoke(var1.get(var3));
      }
   }

   private inline fun tryForbidNewElements(): Int {
      val var2: AtomicIntegerFieldUpdater = controlState$FU;

      val var1: Int;
      do {
         var1 = var2.get(this);
         if ((var1 and Integer.MIN_VALUE) != 0) {
            return 0;
         }
      } while (!controlState$FU.compareAndSet(this, var1, -2147483648 | var1));

      return var1;
   }

   public fun allocate(): Boolean {
      val var2: AtomicIntegerFieldUpdater = controlState$FU;

      val var1: Int;
      do {
         var1 = var2.get(this);
         if ((Integer.MIN_VALUE and var1) != 0) {
            return false;
         }

         if (var1 >= this.maxCapacity) {
            return true;
         }
      } while (!controlState$FU.compareAndSet(this, var1, var1 + 1));

      this.elements.set(var1, this.create.invoke(var1));
      return true;
   }

   public fun close(): List<Any> {
      val var2: AtomicIntegerFieldUpdater = controlState$FU;

      var var1: Int;
      do {
         var1 = var2.get(this);
         if ((var1 and Integer.MIN_VALUE) != 0) {
            var1 = 0;
            break;
         }
      } while (!controlState$FU.compareAndSet(this, var1, -2147483648 | var1));

      val var3: java.lang.Iterable = RangesKt.until(0, var1);
      val var6: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var3, 10));
      val var4: java.util.Iterator = var3.iterator();

      while (var4.hasNext()) {
         var1 = (var4 as IntIterator).nextInt();

         do {
            var7 = this.elements.getAndSet(var1, null);
         } while (var7 == null);

         var6.add(var7);
      }

      return var6 as MutableList<T>;
   }

   internal fun stateRepresentation(): String {
      val var2: Int = controlState$FU.get(this);
      val var4: java.lang.Iterable = RangesKt.until(0, Integer.MAX_VALUE and var2);
      val var3: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var4, 10));
      val var7: java.util.Iterator = var4.iterator();

      while (var7.hasNext()) {
         var3.add(this.elements.get((var7 as IntIterator).nextInt()));
      }

      val var8: java.lang.String = (var3 as java.util.List).toString();
      val var6: java.lang.String;
      if ((var2 and Integer.MIN_VALUE) != 0) {
         var6 = "[closed]";
      } else {
         var6 = "";
      }

      val var5: StringBuilder = new StringBuilder();
      var5.append(var8);
      var5.append(var6);
      return var5.toString();
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("OnDemandAllocatingPool(");
      var1.append(this.stateRepresentation$kotlinx_coroutines_core());
      var1.append(')');
      return var1.toString();
   }
}
