package kotlin

import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0
import kotlin.reflect.KProperty1

public inline operator fun <V> KProperty0<V>.getValue(thisRef: Any?, property: KProperty<*>): V {
   return (V)var0.get();
}

public inline operator fun <T, V> KProperty1<T, V>.getValue(thisRef: T, property: KProperty<*>): V {
   return (V)var0.get(var1);
}

public inline operator fun <V> KMutableProperty0<V>.setValue(thisRef: Any?, property: KProperty<*>, value: V) {
   var0.set(var3);
}

public inline operator fun <T, V> KMutableProperty1<T, V>.setValue(thisRef: T, property: KProperty<*>, value: V) {
   var0.set(var1, var3);
}
