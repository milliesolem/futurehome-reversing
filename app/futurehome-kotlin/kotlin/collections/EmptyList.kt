package kotlin.collections

import java.io.Serializable
import java.util.RandomAccess
import kotlin.jvm.internal.CollectionToArray
import kotlin.jvm.internal.markers.KMappedMarker

internal object EmptyList : java.util.List, Serializable, RandomAccess, KMappedMarker {
   private const val serialVersionUID: Long = -7390468764508069838L

   public open val size: Int
      public open get() {
         return 0;
      }


   private fun readResolve(): Any {
      return INSTANCE;
   }

   fun add(var1: Int, var2: Void) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   fun add(var1: Void): Boolean {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   override fun addAll(var1: Int, var2: java.util.Collection): Boolean {
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
      if (var1 is java.util.List && (var1 as java.util.List).isEmpty()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public open operator fun get(index: Int): Nothing {
      val var2: StringBuilder = new StringBuilder("Empty list doesn't contain element at index ");
      var2.append(var1);
      var2.append('.');
      throw new IndexOutOfBoundsException(var2.toString());
   }

   public override fun hashCode(): Int {
      return 1;
   }

   public open fun indexOf(element: Nothing): Int {
      return -1;
   }

   public override fun isEmpty(): Boolean {
      return true;
   }

   public override operator fun iterator(): Iterator<Nothing> {
      return EmptyIterator.INSTANCE;
   }

   public open fun lastIndexOf(element: Nothing): Int {
      return -1;
   }

   public override fun listIterator(): ListIterator<Nothing> {
      return EmptyIterator.INSTANCE;
   }

   public override fun listIterator(index: Int): ListIterator<Nothing> {
      if (var1 == 0) {
         return EmptyIterator.INSTANCE;
      } else {
         val var2: StringBuilder = new StringBuilder("Index: ");
         var2.append(var1);
         throw new IndexOutOfBoundsException(var2.toString());
      }
   }

   fun remove(var1: Int): Void {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
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

   fun set(var1: Int, var2: Void): Void {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public override fun subList(fromIndex: Int, toIndex: Int): List<Nothing> {
      if (var1 == 0 && var2 == 0) {
         return this;
      } else {
         val var3: StringBuilder = new StringBuilder("fromIndex: ");
         var3.append(var1);
         var3.append(", toIndex: ");
         var3.append(var2);
         throw new IndexOutOfBoundsException(var3.toString());
      }
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
