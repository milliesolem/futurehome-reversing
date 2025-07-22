package kotlin

import java.util.Arrays
import java.util.NoSuchElementException
import kotlin.jvm.internal.CollectionToArray
import kotlin.jvm.internal.markers.KMappedMarker

@JvmInline
public inline class UIntArray : java.util.Collection<UInt>, KMappedMarker {
   internal final val storage: IntArray

   public open val size: Int
      public open get() {
         return var0.length;
      }


   @JvmStatic
   fun `constructor-impl`(var0: Int): IntArray {
      return constructor-impl(new int[var0]);
   }

   @JvmStatic
   fun `constructor-impl`(var0: IntArray): IntArray {
      return var0;
   }

   @JvmStatic
   public open operator fun contains(element: UInt): Boolean {
      return ArraysKt.contains(var0, var1);
   }

   @JvmStatic
   public open fun containsAll(elements: Collection<UInt>): Boolean {
      val var5: java.lang.Iterable = var1;
      var var2: Boolean = (var1 as java.util.Collection).isEmpty();
      val var3: Boolean = true;
      if (var2) {
         var2 = true;
      } else {
         val var4: java.util.Iterator = var5.iterator();

         while (true) {
            var2 = var3;
            if (!var4.hasNext()) {
               break;
            }

            val var6: Any = var4.next();
            if (var6 !is UInt || !ArraysKt.contains(var0, (var6 as UInt).unbox-impl())) {
               var2 = false;
               break;
            }
         }
      }

      return var2;
   }

   @JvmStatic
   fun `equals-impl`(var0: IntArray, var1: Any): Boolean {
      if (var1 !is UIntArray) {
         return false;
      } else {
         return var0 == (var1 as UIntArray).unbox-impl();
      }
   }

   @JvmStatic
   fun `equals-impl0`(var0: IntArray, var1: IntArray): Boolean {
      return var0 == var1;
   }

   @JvmStatic
   public operator fun get(index: Int): UInt {
      return UInt.constructor-impl(var0[var1]);
   }

   @JvmStatic
   fun `hashCode-impl`(var0: IntArray): Int {
      return Arrays.hashCode(var0);
   }

   @JvmStatic
   public open fun isEmpty(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   public open operator fun iterator(): kotlin.collections.Iterator<UInt> {
      return new UIntArray.Iterator(var0);
   }

   @JvmStatic
   public operator fun set(index: Int, value: UInt) {
      var0[var1] = var2;
   }

   @JvmStatic
   fun `toString-impl`(var0: IntArray): java.lang.String {
      val var1: StringBuilder = new StringBuilder("UIntArray(storage=");
      var1.append(Arrays.toString(var0));
      var1.append(')');
      return var1.toString();
   }

   fun `add-WZ4Q5Ns`(var1: Int): Boolean {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   override fun addAll(var1: MutableCollection<UInt>): Boolean {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   override fun clear() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   fun `contains-WZ4Q5Ns`(var1: Int): Boolean {
      return contains-WZ4Q5Ns(this.storage, var1);
   }

   override fun containsAll(var1: MutableCollection<Any>): Boolean {
      return containsAll-impl(this.storage, var1);
   }

   public override operator fun equals(other: Any?): Boolean {
      return equals-impl(this.storage, var1);
   }

   fun getSize(): Int {
      return getSize-impl(this.storage);
   }

   public override fun hashCode(): Int {
      return hashCode-impl(this.storage);
   }

   override fun isEmpty(): Boolean {
      return isEmpty-impl(this.storage);
   }

   override fun iterator(): MutableIterator<UInt> {
      return iterator-impl(this.storage);
   }

   override fun remove(var1: Any): Boolean {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   override fun removeAll(var1: MutableCollection<Any>): Boolean {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   override fun retainAll(var1: MutableCollection<Any>): Boolean {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   override fun toArray(): Array<Any> {
      return CollectionToArray.toArray(this as MutableCollection<*>);
   }

   override fun <T> toArray(var1: Array<T>): Array<T> {
      return (T[])CollectionToArray.toArray(this as MutableCollection<*>, var1);
   }

   public override fun toString(): String {
      return toString-impl(this.storage);
   }

   private class Iterator(array: IntArray) : java.util.Iterator<UInt>, KMappedMarker {
      private final val array: IntArray
      private final var index: Int

      init {
         this.array = var1;
      }

      public override operator fun hasNext(): Boolean {
         val var1: Boolean;
         if (this.index < this.array.length) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public open operator fun next(): UInt {
         val var1: Int = this.index;
         val var2: IntArray = this.array;
         if (this.index < this.array.length) {
            this.index++;
            return UInt.constructor-impl(var2[var1]);
         } else {
            throw new NoSuchElementException(java.lang.String.valueOf(this.index));
         }
      }

      override fun remove() {
         throw new UnsupportedOperationException("Operation is not supported for read-only collection");
      }
   }
}
