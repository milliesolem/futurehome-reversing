package kotlin

import java.util.Arrays
import java.util.NoSuchElementException
import kotlin.jvm.internal.CollectionToArray
import kotlin.jvm.internal.markers.KMappedMarker

@JvmInline
public inline class UShortArray : java.util.Collection<UShort>, KMappedMarker {
   internal final val storage: ShortArray

   public open val size: Int
      public open get() {
         return var0.length;
      }


   @JvmStatic
   fun `constructor-impl`(var0: Int): ShortArray {
      return constructor-impl(new short[var0]);
   }

   @JvmStatic
   fun `constructor-impl`(var0: ShortArray): ShortArray {
      return var0;
   }

   @JvmStatic
   public open operator fun contains(element: UShort): Boolean {
      return ArraysKt.contains(var0, var1);
   }

   @JvmStatic
   public open fun containsAll(elements: Collection<UShort>): Boolean {
      val var5: java.lang.Iterable = var1;
      var var2: Boolean = (var1 as java.util.Collection).isEmpty();
      val var3: Boolean = true;
      if (var2) {
         var2 = true;
      } else {
         val var6: java.util.Iterator = var5.iterator();

         while (true) {
            var2 = var3;
            if (!var6.hasNext()) {
               break;
            }

            val var4: Any = var6.next();
            if (var4 !is UShort || !ArraysKt.contains(var0, (var4 as UShort).unbox-impl())) {
               var2 = false;
               break;
            }
         }
      }

      return var2;
   }

   @JvmStatic
   fun `equals-impl`(var0: ShortArray, var1: Any): Boolean {
      if (var1 !is UShortArray) {
         return false;
      } else {
         return var0 == (var1 as UShortArray).unbox-impl();
      }
   }

   @JvmStatic
   fun `equals-impl0`(var0: ShortArray, var1: ShortArray): Boolean {
      return var0 == var1;
   }

   @JvmStatic
   public operator fun get(index: Int): UShort {
      return UShort.constructor-impl(var0[var1]);
   }

   @JvmStatic
   fun `hashCode-impl`(var0: ShortArray): Int {
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
   public open operator fun iterator(): kotlin.collections.Iterator<UShort> {
      return new UShortArray.Iterator(var0);
   }

   @JvmStatic
   public operator fun set(index: Int, value: UShort) {
      var0[var1] = var2;
   }

   @JvmStatic
   fun `toString-impl`(var0: ShortArray): java.lang.String {
      val var1: StringBuilder = new StringBuilder("UShortArray(storage=");
      var1.append(Arrays.toString(var0));
      var1.append(')');
      return var1.toString();
   }

   fun `add-xj2QHRw`(var1: Short): Boolean {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   override fun addAll(var1: MutableCollection<UShort>): Boolean {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   override fun clear() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   fun `contains-xj2QHRw`(var1: Short): Boolean {
      return contains-xj2QHRw(this.storage, var1);
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

   override fun iterator(): MutableIterator<UShort> {
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

   private class Iterator(array: ShortArray) : java.util.Iterator<UShort>, KMappedMarker {
      private final val array: ShortArray
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

      public open operator fun next(): UShort {
         val var1: Int = this.index;
         val var2: ShortArray = this.array;
         if (this.index < this.array.length) {
            this.index++;
            return UShort.constructor-impl(var2[var1]);
         } else {
            throw new NoSuchElementException(java.lang.String.valueOf(this.index));
         }
      }

      override fun remove() {
         throw new UnsupportedOperationException("Operation is not supported for read-only collection");
      }
   }
}
