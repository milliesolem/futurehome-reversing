package kotlin.collections.builders

import java.io.NotSerializableException
import java.io.Serializable
import java.util.Arrays
import java.util.ConcurrentModificationException
import java.util.NoSuchElementException
import java.util.RandomAccess
import kotlin.jvm.internal.markers.KMutableList
import kotlin.jvm.internal.markers.KMutableListIterator

internal class ListBuilder<E>(initialCapacity: Int = 10) : AbstractMutableList<E>, java.util.List<E>, RandomAccess, Serializable, KMutableList {
   private final var backing: Array<Any>
   private final var length: Int
   private final var isReadOnly: Boolean

   public open val size: Int
      public open get() {
         return this.length;
      }


   @JvmStatic
   fun {
      val var0: ListBuilder = new ListBuilder(0);
      var0.isReadOnly = true;
      Empty = var0;
   }

   fun ListBuilder() {
      this(0, 1, null);
   }

   init {
      this.backing = (E[])ListBuilderKt.arrayOfUninitializedElements(var1);
   }

   private fun addAllInternal(i: Int, elements: Collection<Any>, n: Int) {
      this.registerModification();
      this.insertAtInternal(var1, var3);
      val var5: java.util.Iterator = var2.iterator();

      for (int var4 = 0; var4 < var3; var4++) {
         this.backing[var1 + var4] = (E)var5.next();
      }
   }

   private fun addAtInternal(i: Int, element: Any) {
      this.registerModification();
      this.insertAtInternal(var1, 1);
      this.backing[var1] = (E)var2;
   }

   private fun checkIsMutable() {
      if (this.isReadOnly) {
         throw new UnsupportedOperationException();
      }
   }

   private fun contentEquals(other: List<*>): Boolean {
      return ListBuilderKt.access$subarrayContentEquals(this.backing, 0, this.length, var1);
   }

   private fun ensureCapacityInternal(minCapacity: Int) {
      if (var1 >= 0) {
         if (var1 > this.backing.length) {
            this.backing = ListBuilderKt.copyOfUninitializedElements(this.backing, AbstractList.Companion.newCapacity$kotlin_stdlib(this.backing.length, var1));
         }
      } else {
         throw new OutOfMemoryError();
      }
   }

   private fun ensureExtraCapacity(n: Int) {
      this.ensureCapacityInternal(this.length + var1);
   }

   private fun insertAtInternal(i: Int, n: Int) {
      this.ensureExtraCapacity(var2);
      ArraysKt.copyInto(this.backing, this.backing, var1 + var2, var1, this.length);
      this.length += var2;
   }

   private fun registerModification() {
      this.modCount++;
   }

   private fun removeAtInternal(i: Int): Any {
      this.registerModification();
      val var3: Any = this.backing[var1];
      ArraysKt.copyInto(this.backing, this.backing, var1, var1 + 1, this.length);
      ListBuilderKt.resetAt(this.backing, this.length - 1);
      this.length--;
      return (E)var3;
   }

   private fun removeRangeInternal(rangeOffset: Int, rangeLength: Int) {
      if (var2 > 0) {
         this.registerModification();
      }

      ArraysKt.copyInto(this.backing, this.backing, var1, var1 + var2, this.length);
      ListBuilderKt.resetRange(this.backing, this.length - var2, this.length);
      this.length -= var2;
   }

   private fun retainOrRemoveAllInternal(rangeOffset: Int, rangeLength: Int, elements: Collection<Any>, retain: Boolean): Int {
      var var5: Int = 0;
      var var6: Int = 0;

      while (var5 < var2) {
         val var7: Int = var1 + var5;
         if (var3.contains(this.backing[var1 + var5]) == var4) {
            var5++;
            this.backing[var6 + var1] = this.backing[var7];
            var6++;
         } else {
            var5++;
         }
      }

      var5 = var2 - var6;
      ArraysKt.copyInto(this.backing, this.backing, var1 + var6, var2 + var1, this.length);
      ListBuilderKt.resetRange(this.backing, this.length - var5, this.length);
      if (var5 > 0) {
         this.registerModification();
      }

      this.length -= var5;
      return var5;
   }

   private fun writeReplace(): Any {
      if (this.isReadOnly) {
         return new SerializedCollection(this, 0);
      } else {
         throw new NotSerializableException("The list cannot be serialized while it is being built.");
      }
   }

   public override fun add(index: Int, element: Any) {
      this.checkIsMutable();
      AbstractList.Companion.checkPositionIndex$kotlin_stdlib(var1, this.length);
      this.addAtInternal(var1, (E)var2);
   }

   public override fun add(element: Any): Boolean {
      this.checkIsMutable();
      this.addAtInternal(this.length, (E)var1);
      return true;
   }

   public override fun addAll(index: Int, elements: Collection<Any>): Boolean {
      this.checkIsMutable();
      AbstractList.Companion.checkPositionIndex$kotlin_stdlib(var1, this.length);
      val var3: Int = var2.size();
      this.addAllInternal(var1, var2, var3);
      val var4: Boolean;
      if (var3 > 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public override fun addAll(elements: Collection<Any>): Boolean {
      this.checkIsMutable();
      val var2: Int = var1.size();
      this.addAllInternal(this.length, var1, var2);
      val var3: Boolean;
      if (var2 > 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public fun build(): List<Any> {
      this.checkIsMutable();
      this.isReadOnly = true;
      val var1: java.util.List;
      if (this.length > 0) {
         var1 = this;
      } else {
         var1 = Empty;
      }

      return var1;
   }

   public override fun clear() {
      this.checkIsMutable();
      this.removeRangeInternal(0, this.length);
   }

   public override operator fun equals(other: Any?): Boolean {
      val var2: Boolean;
      if (var1 === this || var1 is java.util.List && this.contentEquals(var1 as MutableList<*>)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public override operator fun get(index: Int): Any {
      AbstractList.Companion.checkElementIndex$kotlin_stdlib(var1, this.length);
      return this.backing[var1];
   }

   public override fun hashCode(): Int {
      return ListBuilderKt.access$subarrayContentHashCode(this.backing, 0, this.length);
   }

   public override fun indexOf(element: Any): Int {
      for (int var2 = 0; var2 < this.length; var2++) {
         if (this.backing[var2] == var1) {
            return var2;
         }
      }

      return -1;
   }

   public override fun isEmpty(): Boolean {
      val var1: Boolean;
      if (this.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public override operator fun iterator(): MutableIterator<Any> {
      return this.listIterator(0);
   }

   public override fun lastIndexOf(element: Any): Int {
      for (int var2 = this.length - 1; var2 >= 0; var2--) {
         if (this.backing[var2] == var1) {
            return var2;
         }
      }

      return -1;
   }

   public override fun listIterator(): MutableListIterator<Any> {
      return this.listIterator(0);
   }

   public override fun listIterator(index: Int): MutableListIterator<Any> {
      AbstractList.Companion.checkPositionIndex$kotlin_stdlib(var1, this.length);
      return new ListBuilder.Itr<>(this, var1);
   }

   public override fun remove(element: Any): Boolean {
      this.checkIsMutable();
      val var2: Int = this.indexOf(var1);
      if (var2 >= 0) {
         this.remove(var2);
      }

      val var3: Boolean;
      if (var2 >= 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public override fun removeAll(elements: Collection<Any>): Boolean {
      this.checkIsMutable();
      var var3: Boolean = false;
      if (this.retainOrRemoveAllInternal(0, this.length, var1, false) > 0) {
         var3 = true;
      }

      return var3;
   }

   public override fun removeAt(index: Int): Any {
      this.checkIsMutable();
      AbstractList.Companion.checkElementIndex$kotlin_stdlib(var1, this.length);
      return this.removeAtInternal(var1);
   }

   public override fun retainAll(elements: Collection<Any>): Boolean {
      this.checkIsMutable();
      var var3: Boolean = false;
      if (this.retainOrRemoveAllInternal(0, this.length, var1, true) > 0) {
         var3 = true;
      }

      return var3;
   }

   public override operator fun set(index: Int, element: Any): Any {
      this.checkIsMutable();
      AbstractList.Companion.checkElementIndex$kotlin_stdlib(var1, this.length);
      val var3: Any = this.backing[var1];
      this.backing[var1] = (E)var2;
      return (E)var3;
   }

   public override fun subList(fromIndex: Int, toIndex: Int): MutableList<Any> {
      AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(var1, var2, this.length);
      return new ListBuilder.BuilderSubList<>(this.backing, var1, var2 - var1, null, this);
   }

   public override fun toArray(): Array<Any?> {
      return ArraysKt.copyOfRange(this.backing, 0, this.length);
   }

   public override fun <T> toArray(array: Array<T>): Array<T> {
      if (var1.length < this.length) {
         var1 = Arrays.copyOfRange(this.backing, 0, this.length, (Class<? extends Object[]>)var1.getClass());
         return (T[])var1;
      } else {
         ArraysKt.copyInto(this.backing, (E[])var1, 0, 0, this.length);
         return (T[])CollectionsKt.terminateCollectionToArray(this.length, var1);
      }
   }

   public override fun toString(): String {
      return ListBuilderKt.access$subarrayContentToString(this.backing, 0, this.length, this);
   }

   public class BuilderSubList<E>(vararg backing: Any,
         offset: Int,
         length: Int,
         parent: kotlin.collections.builders.ListBuilder.BuilderSubList<Any>?,
         root: ListBuilder<Any>
      )
      : AbstractMutableList<E>,
      java.util.List<E>,
      RandomAccess,
      Serializable,
      KMutableList {
      private final var backing: Array<Any>
      private final val offset: Int
      private final var length: Int
      private final val parent: kotlin.collections.builders.ListBuilder.BuilderSubList<Any>?
      private final val root: ListBuilder<Any>

      public open val size: Int
         public open get() {
            this.checkForComodification();
            return this.length;
         }


      private final val isReadOnly: Boolean
         private final get() {
            return ListBuilder.access$isReadOnly$p(this.root);
         }


      init {
         this.backing = (E[])var1;
         this.offset = var2;
         this.length = var3;
         this.parent = var4;
         this.root = var5;
         this.modCount = ListBuilder.access$getModCount$p$s-2084097795(var5);
      }

      private fun addAllInternal(i: Int, elements: Collection<Any>, n: Int) {
         this.registerModification();
         if (this.parent != null) {
            this.parent.addAllInternal(var1, var2, var3);
         } else {
            ListBuilder.access$addAllInternal(this.root, var1, var2, var3);
         }

         this.backing = (E[])ListBuilder.access$getBacking$p(this.root);
         this.length += var3;
      }

      private fun addAtInternal(i: Int, element: Any) {
         this.registerModification();
         if (this.parent != null) {
            this.parent.addAtInternal(var1, (E)var2);
         } else {
            ListBuilder.access$addAtInternal(this.root, var1, var2);
         }

         this.backing = (E[])ListBuilder.access$getBacking$p(this.root);
         this.length++;
      }

      private fun checkForComodification() {
         if (ListBuilder.access$getModCount$p$s-2084097795(this.root) != this.modCount) {
            throw new ConcurrentModificationException();
         }
      }

      private fun checkIsMutable() {
         if (this.isReadOnly()) {
            throw new UnsupportedOperationException();
         }
      }

      private fun contentEquals(other: List<*>): Boolean {
         return ListBuilderKt.access$subarrayContentEquals(this.backing, this.offset, this.length, var1);
      }

      private fun registerModification() {
         this.modCount++;
      }

      private fun removeAtInternal(i: Int): Any {
         this.registerModification();
         val var3: Any;
         if (this.parent != null) {
            var3 = this.parent.removeAtInternal(var1);
         } else {
            var3 = ListBuilder.access$removeAtInternal(this.root, var1);
         }

         this.length--;
         return (E)var3;
      }

      private fun removeRangeInternal(rangeOffset: Int, rangeLength: Int) {
         if (var2 > 0) {
            this.registerModification();
         }

         if (this.parent != null) {
            this.parent.removeRangeInternal(var1, var2);
         } else {
            ListBuilder.access$removeRangeInternal(this.root, var1, var2);
         }

         this.length -= var2;
      }

      private fun retainOrRemoveAllInternal(rangeOffset: Int, rangeLength: Int, elements: Collection<Any>, retain: Boolean): Int {
         if (this.parent != null) {
            var1 = this.parent.retainOrRemoveAllInternal(var1, var2, var3, var4);
         } else {
            var1 = ListBuilder.access$retainOrRemoveAllInternal(this.root, var1, var2, var3, var4);
         }

         if (var1 > 0) {
            this.registerModification();
         }

         this.length -= var1;
         return var1;
      }

      private fun writeReplace(): Any {
         if (this.isReadOnly()) {
            return new SerializedCollection(this, 0);
         } else {
            throw new NotSerializableException("The list cannot be serialized while it is being built.");
         }
      }

      public override fun add(index: Int, element: Any) {
         this.checkIsMutable();
         this.checkForComodification();
         AbstractList.Companion.checkPositionIndex$kotlin_stdlib(var1, this.length);
         this.addAtInternal(this.offset + var1, (E)var2);
      }

      public override fun add(element: Any): Boolean {
         this.checkIsMutable();
         this.checkForComodification();
         this.addAtInternal(this.offset + this.length, (E)var1);
         return true;
      }

      public override fun addAll(index: Int, elements: Collection<Any>): Boolean {
         this.checkIsMutable();
         this.checkForComodification();
         AbstractList.Companion.checkPositionIndex$kotlin_stdlib(var1, this.length);
         val var3: Int = var2.size();
         this.addAllInternal(this.offset + var1, var2, var3);
         val var4: Boolean;
         if (var3 > 0) {
            var4 = true;
         } else {
            var4 = false;
         }

         return var4;
      }

      public override fun addAll(elements: Collection<Any>): Boolean {
         this.checkIsMutable();
         this.checkForComodification();
         val var2: Int = var1.size();
         this.addAllInternal(this.offset + this.length, var1, var2);
         val var3: Boolean;
         if (var2 > 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      public override fun clear() {
         this.checkIsMutable();
         this.checkForComodification();
         this.removeRangeInternal(this.offset, this.length);
      }

      public override operator fun equals(other: Any?): Boolean {
         this.checkForComodification();
         val var2: Boolean;
         if (var1 === this || var1 is java.util.List && this.contentEquals(var1 as MutableList<*>)) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public override operator fun get(index: Int): Any {
         this.checkForComodification();
         AbstractList.Companion.checkElementIndex$kotlin_stdlib(var1, this.length);
         return this.backing[this.offset + var1];
      }

      public override fun hashCode(): Int {
         this.checkForComodification();
         return ListBuilderKt.access$subarrayContentHashCode(this.backing, this.offset, this.length);
      }

      public override fun indexOf(element: Any): Int {
         this.checkForComodification();

         for (int var2 = 0; var2 < this.length; var2++) {
            if (this.backing[this.offset + var2] == var1) {
               return var2;
            }
         }

         return -1;
      }

      public override fun isEmpty(): Boolean {
         this.checkForComodification();
         val var1: Boolean;
         if (this.length == 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public override operator fun iterator(): MutableIterator<Any> {
         return this.listIterator(0);
      }

      public override fun lastIndexOf(element: Any): Int {
         this.checkForComodification();

         for (int var2 = this.length - 1; var2 >= 0; var2--) {
            if (this.backing[this.offset + var2] == var1) {
               return var2;
            }
         }

         return -1;
      }

      public override fun listIterator(): MutableListIterator<Any> {
         return this.listIterator(0);
      }

      public override fun listIterator(index: Int): MutableListIterator<Any> {
         this.checkForComodification();
         AbstractList.Companion.checkPositionIndex$kotlin_stdlib(var1, this.length);
         return new ListBuilder.BuilderSubList.Itr<>(this, var1);
      }

      public override fun remove(element: Any): Boolean {
         this.checkIsMutable();
         this.checkForComodification();
         val var2: Int = this.indexOf(var1);
         if (var2 >= 0) {
            this.remove(var2);
         }

         val var3: Boolean;
         if (var2 >= 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      public override fun removeAll(elements: Collection<Any>): Boolean {
         this.checkIsMutable();
         this.checkForComodification();
         var var4: Boolean = false;
         if (this.retainOrRemoveAllInternal(this.offset, this.length, var1, false) > 0) {
            var4 = true;
         }

         return var4;
      }

      public override fun removeAt(index: Int): Any {
         this.checkIsMutable();
         this.checkForComodification();
         AbstractList.Companion.checkElementIndex$kotlin_stdlib(var1, this.length);
         return this.removeAtInternal(this.offset + var1);
      }

      public override fun retainAll(elements: Collection<Any>): Boolean {
         this.checkIsMutable();
         this.checkForComodification();
         var var4: Boolean = true;
         if (this.retainOrRemoveAllInternal(this.offset, this.length, var1, true) <= 0) {
            var4 = false;
         }

         return var4;
      }

      public override operator fun set(index: Int, element: Any): Any {
         this.checkIsMutable();
         this.checkForComodification();
         AbstractList.Companion.checkElementIndex$kotlin_stdlib(var1, this.length);
         val var4: Any = this.backing[this.offset + var1];
         this.backing[this.offset + var1] = (E)var2;
         return (E)var4;
      }

      public override fun subList(fromIndex: Int, toIndex: Int): MutableList<Any> {
         AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(var1, var2, this.length);
         return new ListBuilder.BuilderSubList<>(this.backing, this.offset + var1, var2 - var1, this, this.root);
      }

      public override fun toArray(): Array<Any?> {
         this.checkForComodification();
         return ArraysKt.copyOfRange(this.backing, this.offset, this.length + this.offset);
      }

      public override fun <T> toArray(array: Array<T>): Array<T> {
         this.checkForComodification();
         if (var1.length < this.length) {
            var1 = Arrays.copyOfRange(this.backing, this.offset, this.length + this.offset, (Class<? extends Object[]>)var1.getClass());
            return (T[])var1;
         } else {
            ArraysKt.copyInto(this.backing, (E[])var1, 0, this.offset, this.length + this.offset);
            return (T[])CollectionsKt.terminateCollectionToArray(this.length, var1);
         }
      }

      public override fun toString(): String {
         this.checkForComodification();
         return ListBuilderKt.access$subarrayContentToString(this.backing, this.offset, this.length, this);
      }

      private class Itr<E>(list: kotlin.collections.builders.ListBuilder.BuilderSubList<Any>, index: Int) : java.util.ListIterator<E>, KMutableListIterator {
         private final val list: kotlin.collections.builders.ListBuilder.BuilderSubList<Any>
         private final var index: Int
         private final var lastIndex: Int
         private final var expectedModCount: Int

         init {
            this.list = var1;
            this.index = var2;
            this.lastIndex = -1;
            this.expectedModCount = ListBuilder.BuilderSubList.access$getModCount$p$s1462993667(var1);
         }

         private fun checkForComodification() {
            if (ListBuilder.access$getModCount$p$s-2084097795(ListBuilder.BuilderSubList.access$getRoot$p(this.list)) != this.expectedModCount) {
               throw new ConcurrentModificationException();
            }
         }

         public override fun add(element: Any) {
            this.checkForComodification();
            this.list.add(this.index++, (E)var1);
            this.lastIndex = -1;
            this.expectedModCount = ListBuilder.BuilderSubList.access$getModCount$p$s1462993667(this.list);
         }

         public override operator fun hasNext(): Boolean {
            val var1: Boolean;
            if (this.index < ListBuilder.BuilderSubList.access$getLength$p(this.list)) {
               var1 = true;
            } else {
               var1 = false;
            }

            return var1;
         }

         public override fun hasPrevious(): Boolean {
            val var1: Boolean;
            if (this.index > 0) {
               var1 = true;
            } else {
               var1 = false;
            }

            return var1;
         }

         public override operator fun next(): Any {
            this.checkForComodification();
            if (this.index < ListBuilder.BuilderSubList.access$getLength$p(this.list)) {
               this.lastIndex = this.index++;
               return (E)ListBuilder.BuilderSubList.access$getBacking$p(this.list)[ListBuilder.BuilderSubList.access$getOffset$p(this.list) + this.lastIndex];
            } else {
               throw new NoSuchElementException();
            }
         }

         public override fun nextIndex(): Int {
            return this.index;
         }

         public override fun previous(): Any {
            this.checkForComodification();
            if (this.index > 0) {
               this.index = --this.index;
               val var1: Int;
               this.lastIndex = var1;
               return (E)ListBuilder.BuilderSubList.access$getBacking$p(this.list)[ListBuilder.BuilderSubList.access$getOffset$p(this.list) + this.lastIndex];
            } else {
               throw new NoSuchElementException();
            }
         }

         public override fun previousIndex(): Int {
            return this.index - 1;
         }

         public override fun remove() {
            this.checkForComodification();
            if (this.lastIndex != -1) {
               this.list.remove(this.lastIndex);
               this.index = this.lastIndex;
               this.lastIndex = -1;
               this.expectedModCount = ListBuilder.BuilderSubList.access$getModCount$p$s1462993667(this.list);
            } else {
               throw new IllegalStateException("Call next() or previous() before removing element from the iterator.".toString());
            }
         }

         public override fun set(element: Any) {
            this.checkForComodification();
            if (this.lastIndex != -1) {
               this.list.set(this.lastIndex, (E)var1);
            } else {
               throw new IllegalStateException("Call next() or previous() before replacing element from the iterator.".toString());
            }
         }
      }
   }

   private companion object {
      private final val Empty: ListBuilder<Nothing>
   }

   private class Itr<E>(list: ListBuilder<Any>, index: Int) : java.util.ListIterator<E>, KMutableListIterator {
      private final val list: ListBuilder<Any>
      private final var index: Int
      private final var lastIndex: Int
      private final var expectedModCount: Int

      init {
         this.list = var1;
         this.index = var2;
         this.lastIndex = -1;
         this.expectedModCount = ListBuilder.access$getModCount$p$s-2084097795(var1);
      }

      private fun checkForComodification() {
         if (ListBuilder.access$getModCount$p$s-2084097795(this.list) != this.expectedModCount) {
            throw new ConcurrentModificationException();
         }
      }

      public override fun add(element: Any) {
         this.checkForComodification();
         this.list.add(this.index++, (E)var1);
         this.lastIndex = -1;
         this.expectedModCount = ListBuilder.access$getModCount$p$s-2084097795(this.list);
      }

      public override operator fun hasNext(): Boolean {
         val var1: Boolean;
         if (this.index < ListBuilder.access$getLength$p(this.list)) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public override fun hasPrevious(): Boolean {
         val var1: Boolean;
         if (this.index > 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public override operator fun next(): Any {
         this.checkForComodification();
         if (this.index < ListBuilder.access$getLength$p(this.list)) {
            this.lastIndex = this.index++;
            return (E)ListBuilder.access$getBacking$p(this.list)[this.lastIndex];
         } else {
            throw new NoSuchElementException();
         }
      }

      public override fun nextIndex(): Int {
         return this.index;
      }

      public override fun previous(): Any {
         this.checkForComodification();
         if (this.index > 0) {
            this.index = --this.index;
            val var1: Int;
            this.lastIndex = var1;
            return (E)ListBuilder.access$getBacking$p(this.list)[this.lastIndex];
         } else {
            throw new NoSuchElementException();
         }
      }

      public override fun previousIndex(): Int {
         return this.index - 1;
      }

      public override fun remove() {
         this.checkForComodification();
         if (this.lastIndex != -1) {
            this.list.remove(this.lastIndex);
            this.index = this.lastIndex;
            this.lastIndex = -1;
            this.expectedModCount = ListBuilder.access$getModCount$p$s-2084097795(this.list);
         } else {
            throw new IllegalStateException("Call next() or previous() before removing element from the iterator.".toString());
         }
      }

      public override fun set(element: Any) {
         this.checkForComodification();
         if (this.lastIndex != -1) {
            this.list.set(this.lastIndex, (E)var1);
         } else {
            throw new IllegalStateException("Call next() or previous() before replacing element from the iterator.".toString());
         }
      }
   }
}
