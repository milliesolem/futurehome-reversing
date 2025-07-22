package kotlin.collections

import java.io.Serializable
import kotlin.jvm.internal.CollectionToArray
import kotlin.jvm.internal.markers.KMappedMarker

internal object EmptySet : java.util.Set, Serializable, KMappedMarker {
   private const val serialVersionUID: Long = 3406603774387020532L

   public open val size: Int
      public open get() {
         return 0;
      }


   private fun readResolve(): Any {
      return INSTANCE;
   }

   fun add(var1: Void): Boolean {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   override fun addAll(var1: java.util.Collection): Boolean {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   override fun clear() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public open operator fun contains(element: Nothing): Boolean {
      return false;
   }

   public override fun containsAll(elements: Collection<Nothing>): Boolean {
      return var1.isEmpty();
   }

   public override operator fun equals(other: Any?): Boolean {
      val var2: Boolean;
      if (var1 is java.util.Set && (var1 as java.util.Set).isEmpty()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public override fun hashCode(): Int {
      return 0;
   }

   public override fun isEmpty(): Boolean {
      return true;
   }

   public override operator fun iterator(): Iterator<Nothing> {
      return EmptyIterator.INSTANCE;
   }

   override fun remove(var1: Any): Boolean {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   override fun removeAll(var1: java.util.Collection): Boolean {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   override fun retainAll(var1: java.util.Collection): Boolean {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   override fun toArray(): Array<Any> {
      return CollectionToArray.toArray(this);
   }

   override fun <T> toArray(var1: Array<T>): Array<T> {
      return (T[])CollectionToArray.toArray(this, var1);
   }

   public override fun toString(): String {
      return "[]";
   }
}
