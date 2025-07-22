package kotlin.collections

import kotlin.jvm.internal.markers.KMutableList

public abstract class AbstractMutableList<E> : java.util.AbstractList<E>, java.util.List<E>, KMutableList {
   open fun AbstractMutableList() {
   }

   public abstract override fun add(index: Int, element: Any) {
   }

   abstract fun getSize(): Int

   public abstract fun removeAt(index: Int): Any {
   }

   public abstract override operator fun set(index: Int, element: Any): Any {
   }
}
