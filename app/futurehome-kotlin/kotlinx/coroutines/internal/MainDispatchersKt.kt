package kotlinx.coroutines.internal

import kotlinx.coroutines.MainCoroutineDispatcher

private const val FAST_SERVICE_LOADER_PROPERTY_NAME: String = "kotlinx.coroutines.fast.service.loader"
private final val SUPPORT_MISSING: Boolean = true

private fun createMissingDispatcher(cause: Throwable? = null, errorHint: String? = null): MissingMainCoroutineDispatcher {
   if (SUPPORT_MISSING) {
      return new MissingMainCoroutineDispatcher(var0, var1);
   } else if (var0 != null) {
      throw var0;
   } else {
      throwMissingMainDispatcherException();
      throw new KotlinNothingValueException();
   }
}

@JvmSynthetic
fun `createMissingDispatcher$default`(var0: java.lang.Throwable, var1: java.lang.String, var2: Int, var3: Any): MissingMainCoroutineDispatcher {
   if ((var2 and 1) != 0) {
      var0 = null;
   }

   if ((var2 and 2) != 0) {
      var1 = null;
   }

   return createMissingDispatcher(var0, var1);
}

public fun MainCoroutineDispatcher.isMissing(): Boolean {
   return var0.getImmediate() is MissingMainCoroutineDispatcher;
}

internal fun throwMissingMainDispatcherException(): Nothing {
   throw new IllegalStateException(
      "Module with the Main dispatcher is missing. Add dependency providing the Main dispatcher, e.g. 'kotlinx-coroutines-android' and ensure it has the same version as 'kotlinx-coroutines-core'"
   );
}

public fun MainDispatcherFactory.tryCreateDispatcher(factories: List<MainDispatcherFactory>): MainCoroutineDispatcher {
   try {
      var5 = var0.createDispatcher(var1);
   } catch (var2: java.lang.Throwable) {
      return createMissingDispatcher(var2, var0.hintOnError());
   }

   return var5;
}
