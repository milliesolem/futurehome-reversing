package kotlin.collections

import kotlin.jvm.internal.markers.KMutableCollection

public abstract class AbstractMutableCollection<E> : java.util.AbstractCollection<E>, java.util.Collection<E>, KMutableCollection {
   open fun AbstractMutableCollection() {
   }

   public abstract override fun add(element: Any): Boolean {
   }

   abstract fun getSize(): Int
}
