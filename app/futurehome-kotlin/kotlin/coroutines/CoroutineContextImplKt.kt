package kotlin.coroutines

import kotlin.coroutines.CoroutineContext.Element
import kotlin.coroutines.CoroutineContext.Key

public fun <E : Element> Element.getPolymorphicElement(key: Key<E>): E? {
   if (var1 is AbstractCoroutineContextKey) {
      val var4: AbstractCoroutineContextKey = var1 as AbstractCoroutineContextKey;
      var var6: CoroutineContext.Element = null;
      if (var4.isSubKey$kotlin_stdlib(var0.getKey())) {
         var0 = var4.tryCast$kotlin_stdlib(var0);
         var6 = null;
         if (var0 is CoroutineContext.Element) {
            var6 = var0;
         }
      }

      return (E)var6;
   } else {
      if (var0.getKey() != var1) {
         var0 = null;
      }

      return (E)var0;
   }
}

public fun Element.minusPolymorphicKey(key: Key<*>): CoroutineContext {
   if (var1 is AbstractCoroutineContextKey) {
      val var4: AbstractCoroutineContextKey = var1 as AbstractCoroutineContextKey;
      var var3: Any = var0;
      if (var4.isSubKey$kotlin_stdlib(var0.getKey())) {
         var3 = var0;
         if (var4.tryCast$kotlin_stdlib(var0) != null) {
            var3 = EmptyCoroutineContext.INSTANCE;
         }
      }

      return var3 as CoroutineContext;
   } else {
      var var2: Any = var0;
      if (var0.getKey() === var1) {
         var2 = EmptyCoroutineContext.INSTANCE;
      }

      return var2 as CoroutineContext;
   }
}
