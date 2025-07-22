package kotlin.collections

import kotlin.reflect.KProperty

public inline operator fun <V, V1 : V> Map<in String, V>.getValue(thisRef: Any?, property: KProperty<*>): V1 {
   return (V1)MapsKt.getOrImplicitDefaultNullable(var0, var2.getName());
}

public inline operator fun <V, V1 : V> MutableMap<in String, out V>.getValue(thisRef: Any?, property: KProperty<*>): V1 {
   return (V1)MapsKt.getOrImplicitDefaultNullable(var0, var2.getName());
}

public inline operator fun <V> MutableMap<in String, in V>.setValue(thisRef: Any?, property: KProperty<*>, value: V) {
   var0.put(var2.getName(), var3);
}
