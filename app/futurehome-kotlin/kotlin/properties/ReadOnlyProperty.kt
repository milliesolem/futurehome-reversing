package kotlin.properties

import kotlin.reflect.KProperty

public fun interface ReadOnlyProperty<T, V> {
   public abstract operator fun getValue(thisRef: Any, property: KProperty<*>): Any {
   }
}
