package kotlin.collections

import kotlin.jvm.internal.ArrayIteratorKt
import kotlin.jvm.internal.CollectionToArray
import kotlin.jvm.internal.markers.KMappedMarker

private class ArrayAsCollection<T>(vararg values: Any, isVarargs: Boolean) : java.util.Collection<T>, KMappedMarker {
   public final val values: Array<out Any>
   public final val isVarargs: Boolean

   public open val size: Int
      public open get() {
         return this.values.length;
      }


   init {
      this.values = (T[])var1;
      this.isVarargs = var2;
   }

   override fun add(var1: T): Boolean {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   override fun addAll(var1: MutableCollection<T>): Boolean {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   override fun clear() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public override operator fun contains(element: Any): Boolean {
      return ArraysKt.contains(this.values, var1);
   }

   public override fun containsAll(elements: Collection<Any>): Boolean {
      val var4: java.lang.Iterable = var1;
      var var2: Boolean = (var1 as java.util.Collection).isEmpty();
      val var3: Boolean = true;
      if (var2) {
         var2 = true;
      } else {
         val var5: java.util.Iterator = var4.iterator();

         while (true) {
            var2 = var3;
            if (!var5.hasNext()) {
               break;
            }

            if (!this.contains(var5.next())) {
               var2 = false;
               break;
            }
         }
      }

      return var2;
   }

   public override fun isEmpty(): Boolean {
      val var1: Boolean;
      if (this.values.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public override operator fun iterator(): Iterator<Any> {
      return ArrayIteratorKt.iterator(this.values);
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

   public override fun toArray(): Array<out Any?> {
      return CollectionsKt.copyToArrayOfAny(this.values, this.isVarargs);
   }

   override fun <T> toArray(var1: Array<T>): Array<T> {
      return (T[])CollectionToArray.toArray(this, var1);
   }
}
