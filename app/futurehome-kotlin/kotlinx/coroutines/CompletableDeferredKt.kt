package kotlinx.coroutines

public fun <T> CompletableDeferred(value: T): CompletableDeferred<T> {
   val var1: CompletableDeferredImpl = new CompletableDeferredImpl(null);
   var1.complete(var0);
   return var1;
}

public fun <T> CompletableDeferred(parent: Job? = null): CompletableDeferred<T> {
   return new CompletableDeferredImpl(var0);
}

@JvmSynthetic
fun `CompletableDeferred$default`(var0: Job, var1: Int, var2: Any): CompletableDeferred {
   if ((var1 and 1) != 0) {
      var0 = null;
   }

   return CompletableDeferred(var0);
}

public fun <T> CompletableDeferred<T>.completeWith(result: Result<T>): Boolean {
   val var3: java.lang.Throwable = Result.exceptionOrNull-impl(var1);
   val var2: Boolean;
   if (var3 == null) {
      var2 = var0.complete(var1);
   } else {
      var2 = var0.completeExceptionally(var3);
   }

   return var2;
}
