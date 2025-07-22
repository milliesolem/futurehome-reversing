package kotlinx.coroutines.internal

import kotlin.jvm.functions.Function1
import okio.NioSystemFileSystem$$ExternalSyntheticApiModelOutline0

private object ClassValueCtorCache : CtorCache {
   private final val cache: <unrepresentable> = new ClassValue<Function1<? super java.lang.Throwable, ? extends java.lang.Throwable>>() {
      protected Function1<java.lang.Throwable, java.lang.Throwable> computeValue(Class<?> var1) {
         return ExceptionsConstructorKt.access$createConstructor(var1);
      }
   }

   public override fun get(key: Class<out Throwable>): (Throwable) -> Throwable? {
      return NioSystemFileSystem$$ExternalSyntheticApiModelOutline0.m(cache, var1) as (java.lang.Throwable?) -> java.lang.Throwable;
   }
}
