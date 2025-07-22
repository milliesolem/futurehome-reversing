package kotlin.collections.jdk8

import j..util.Map._EL
import kotlin.jvm.internal.TypeIntrinsics

public inline fun <K, V> Map<out K, V>.getOrDefault(key: K, defaultValue: V): V {
   return (V)_EL.getOrDefault(var0, var1, var2);
}

public inline fun <K, V> MutableMap<out K, out V>.remove(key: K, value: V): Boolean {
   return _EL.remove(TypeIntrinsics.asMutableMap(var0), var1, var2);
}
