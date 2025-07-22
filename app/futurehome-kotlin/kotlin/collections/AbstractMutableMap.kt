package kotlin.collections

import kotlin.jvm.internal.markers.KMutableMap

public abstract class AbstractMutableMap<K, V> : java.util.AbstractMap<K, V>, java.util.Map<K, V>, KMutableMap {
   open fun AbstractMutableMap() {
   }

   abstract fun getEntries(): java.util.Set

   public abstract override fun put(key: Any, value: Any): Any? {
   }
}
