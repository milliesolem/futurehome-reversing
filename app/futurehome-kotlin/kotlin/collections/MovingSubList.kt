package kotlin.collections

import java.util.RandomAccess

internal class MovingSubList<E>(list: List<Any>) : AbstractList<E>, RandomAccess {
   private final val list: List<Any>
   private final var fromIndex: Int
   private final var _size: Int

   public open val size: Int
      public open get() {
         return this._size;
      }


   init {
      this.list = var1;
   }

   public override operator fun get(index: Int): Any {
      AbstractList.Companion.checkElementIndex$kotlin_stdlib(var1, this._size);
      return this.list.get(this.fromIndex + var1);
   }

   public fun move(fromIndex: Int, toIndex: Int) {
      AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(var1, var2, this.list.size());
      this.fromIndex = var1;
      this._size = var2 - var1;
   }
}
