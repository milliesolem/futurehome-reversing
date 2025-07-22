package kotlin.reflect

public fun <T : Any> KClass<T>.cast(value: Any?): T {
   if (var0.isInstance(var1)) {
      return (T)var1;
   } else {
      var1 = new StringBuilder("Value cannot be cast to ");
      var1.append(var0.getQualifiedName());
      throw new ClassCastException(var1.toString());
   }
}

public fun <T : Any> KClass<T>.safeCast(value: Any?): T? {
   if (var0.isInstance(var1)) {
      ;
   } else {
      var1 = null;
   }

   return (T)var1;
}
