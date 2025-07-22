package kotlin

import kotlin.reflect.KProperty

internal class LazyKt__LazyKt : LazyKt__LazyJVMKt {
   open fun LazyKt__LazyKt() {
   }

   @JvmStatic
   public inline operator fun <T> Lazy<T>.getValue(thisRef: Any?, property: KProperty<*>): T {
      return (T)var0.getValue();
   }

   @JvmStatic
   public fun <T> lazyOf(value: T): Lazy<T> {
      return (Lazy<T>)(new InitializedLazyImpl<>(var0));
   }
}
