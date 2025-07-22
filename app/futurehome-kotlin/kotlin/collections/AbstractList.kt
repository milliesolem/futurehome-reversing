package kotlin.collections

import java.util.NoSuchElementException
import java.util.RandomAccess
import kotlin.jvm.internal.markers.KMappedMarker

public abstract class AbstractList<E> : AbstractCollection<E>, java.util.List<E>, KMappedMarker {
   public abstract val size: Int

   open fun AbstractList() {
   }

   override fun add(var1: Int, var2: E) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   override fun addAll(var1: Int, var2: MutableCollection<E>): Boolean {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public override operator fun equals(other: Any?): Boolean {
      if (var1 === this) {
         return true;
      } else {
         return var1 is java.util.List && Companion.orderedEquals$kotlin_stdlib(this, var1 as MutableCollection<*>);
      }
   }

   public abstract override operator fun get(index: Int): Any {
   }

   public override fun hashCode(): Int {
      return Companion.orderedHashCode$kotlin_stdlib(this);
   }

   public override fun indexOf(element: Any): Int {
      val var3: java.util.Iterator = this.iterator();
      var var2: Int = 0;

      while (true) {
         if (!var3.hasNext()) {
            var2 = -1;
            break;
         }

         if (var3.next() == var1) {
            break;
         }

         var2++;
      }

      return var2;
   }

   public override operator fun iterator(): Iterator<Any> {
      return new AbstractList.IteratorImpl(this);
   }

   public override fun lastIndexOf(element: Any): Int {
      val var4: java.util.ListIterator = this.listIterator(this.size());

      var var2: Int;
      while (true) {
         if (var4.hasPrevious()) {
            if (!(var4.previous() == var1)) {
               continue;
            }

            var2 = var4.nextIndex();
            break;
         }

         var2 = -1;
         break;
      }

      return var2;
   }

   public override fun listIterator(): ListIterator<Any> {
      return new AbstractList.ListIteratorImpl((int)this, 0);
   }

   public override fun listIterator(index: Int): ListIterator<Any> {
      return new AbstractList.ListIteratorImpl((int)this, var1);
   }

   override fun remove(var1: Int): E {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   override fun set(var1: Int, var2: E): E {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public override fun subList(fromIndex: Int, toIndex: Int): List<Any> {
      return new AbstractList.SubList<>(this, var1, var2);
   }

   internal companion object {
      private const val maxArraySize: Int

      internal fun checkBoundsIndexes(startIndex: Int, endIndex: Int, size: Int) {
         if (var1 < 0 || var2 > var3) {
            val var5: StringBuilder = new StringBuilder("startIndex: ");
            var5.append(var1);
            var5.append(", endIndex: ");
            var5.append(var2);
            var5.append(", size: ");
            var5.append(var3);
            throw new IndexOutOfBoundsException(var5.toString());
         } else if (var1 > var2) {
            val var4: StringBuilder = new StringBuilder("startIndex: ");
            var4.append(var1);
            var4.append(" > endIndex: ");
            var4.append(var2);
            throw new IllegalArgumentException(var4.toString());
         }
      }

      internal fun checkElementIndex(index: Int, size: Int) {
         if (var1 < 0 || var1 >= var2) {
            val var3: StringBuilder = new StringBuilder("index: ");
            var3.append(var1);
            var3.append(", size: ");
            var3.append(var2);
            throw new IndexOutOfBoundsException(var3.toString());
         }
      }

      internal fun checkPositionIndex(index: Int, size: Int) {
         if (var1 < 0 || var1 > var2) {
            val var3: StringBuilder = new StringBuilder("index: ");
            var3.append(var1);
            var3.append(", size: ");
            var3.append(var2);
            throw new IndexOutOfBoundsException(var3.toString());
         }
      }

      internal fun checkRangeIndexes(fromIndex: Int, toIndex: Int, size: Int) {
         if (var1 < 0 || var2 > var3) {
            val var5: StringBuilder = new StringBuilder("fromIndex: ");
            var5.append(var1);
            var5.append(", toIndex: ");
            var5.append(var2);
            var5.append(", size: ");
            var5.append(var3);
            throw new IndexOutOfBoundsException(var5.toString());
         } else if (var1 > var2) {
            val var4: StringBuilder = new StringBuilder("fromIndex: ");
            var4.append(var1);
            var4.append(" > toIndex: ");
            var4.append(var2);
            throw new IllegalArgumentException(var4.toString());
         }
      }

      internal fun newCapacity(oldCapacity: Int, minCapacity: Int): Int {
         var var3: Int = var1 + (var1 shr 1);
         var1 = var1 + (var1 shr 1);
         if (var3 - var2 < 0) {
            var1 = var2;
         }

         var3 = var1;
         if (var1 - 2147483639 > 0) {
            if (var2 > 2147483639) {
               var3 = Integer.MAX_VALUE;
            } else {
               var3 = 2147483639;
            }
         }

         return var3;
      }

      internal fun orderedEquals(c: Collection<*>, other: Collection<*>): Boolean {
         if (var1.size() != var2.size()) {
            return false;
         } else {
            val var4: java.util.Iterator = var2.iterator();
            val var3: java.util.Iterator = var1.iterator();

            while (var3.hasNext()) {
               if (!(var3.next() == var4.next())) {
                  return false;
               }
            }

            return true;
         }
      }

      internal fun orderedHashCode(c: Collection<*>): Int {
         val var4: java.util.Iterator = var1.iterator();
         var var2: Int = 1;

         while (var4.hasNext()) {
            val var5: Any = var4.next();
            val var3: Int;
            if (var5 != null) {
               var3 = var5.hashCode();
            } else {
               var3 = 0;
            }

            var2 = var2 * 31 + var3;
         }

         return var2;
      }
   }

   private open inner class IteratorImpl : java.util.Iterator<E>, KMappedMarker {
      protected final var index: Int
         internal set

      init {
         this.this$0 = var1;
      }

      public override operator fun hasNext(): Boolean {
         val var1: Boolean;
         if (this.index < this.this$0.size()) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public override operator fun next(): Any {
         if (this.hasNext()) {
            return this.this$0.get(this.index++);
         } else {
            throw new NoSuchElementException();
         }
      }

      override fun remove() {
         throw new UnsupportedOperationException("Operation is not supported for read-only collection");
      }
   }

   private open inner class ListIteratorImpl(index: Int) : AbstractList.IteratorImpl(var1), java.util.ListIterator<E>, KMappedMarker {
      init {
         this.this$0 = var1;
         AbstractList.Companion.checkPositionIndex$kotlin_stdlib(var2, var1.size());
         this.setIndex(var2);
      }

      override fun add(var1: E) {
         throw new UnsupportedOperationException("Operation is not supported for read-only collection");
      }

      public override fun hasPrevious(): Boolean {
         val var1: Boolean;
         if (this.getIndex() > 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public override fun nextIndex(): Int {
         return this.getIndex();
      }

      public override fun previous(): Any {
         if (this.hasPrevious()) {
            val var1: AbstractList = this.this$0;
            this.setIndex(this.getIndex() - 1);
            return (E)var1.get(this.getIndex());
         } else {
            throw new NoSuchElementException();
         }
      }

      public override fun previousIndex(): Int {
         return this.getIndex() - 1;
      }

      override fun set(var1: E) {
         throw new UnsupportedOperationException("Operation is not supported for read-only collection");
      }
   }

   private class SubList<E>(list: AbstractList<Any>, fromIndex: Int, toIndex: Int) : AbstractList<E>, RandomAccess {
      private final val list: AbstractList<Any>
      private final val fromIndex: Int
      private final var _size: Int

      public open val size: Int
         public open get() {
            return this._size;
         }


      init {
         this.list = var1;
         this.fromIndex = var2;
         AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(var2, var3, var1.size());
         this._size = var3 - var2;
      }

      public override operator fun get(index: Int): Any {
         AbstractList.Companion.checkElementIndex$kotlin_stdlib(var1, this._size);
         return this.list.get(this.fromIndex + var1);
      }
   }
}
