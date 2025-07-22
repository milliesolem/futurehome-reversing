package kotlin

import kotlin.contracts.InvocationKind

internal fun createFailure(exception: Throwable): Any {
   return new Result.Failure(var0);
}

public inline fun <R, T> Result<T>.fold(onSuccess: (T) -> R, onFailure: (Throwable) -> R): R {
   contract {
      callsInPlace(onSuccess, InvocationKind.AT_MOST_ONCE)
      callsInPlace(onFailure, InvocationKind.AT_MOST_ONCE)
   }

   val var3: java.lang.Throwable = Result.exceptionOrNull-impl(var0);
   if (var3 == null) {
      var0 = var1.invoke(var0);
   } else {
      var0 = var2.invoke(var3);
   }

   return (R)var0;
}

public inline fun <R, T : R> Result<T>.getOrDefault(defaultValue: R): R {
   return (R)(if (Result.isFailure-impl(var0)) var1 else var0);
}

public inline fun <R, T : R> Result<T>.getOrElse(onFailure: (Throwable) -> R): R {
   contract {
      callsInPlace(onFailure, InvocationKind.AT_MOST_ONCE)
   }

   val var2: java.lang.Throwable = Result.exceptionOrNull-impl(var0);
   if (var2 != null) {
      var0 = var1.invoke(var2);
   }

   return (R)var0;
}

public inline fun <T> Result<T>.getOrThrow(): T {
   throwOnFailure(var0);
   return (T)var0;
}

public inline fun <R, T> Result<T>.map(transform: (T) -> R): Result<R> {
   contract {
      callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
   }

   if (Result.isSuccess-impl(var0)) {
      val var2: Result.Companion = Result.Companion;
      var0 = Result.constructor-impl(var1.invoke(var0));
   } else {
      var0 = Result.constructor-impl(var0);
   }

   return var0;
}

public inline fun <R, T> Result<T>.mapCatching(transform: (T) -> R): Result<R> {
   if (Result.isSuccess-impl(var0)) {
      try {
         val var2: Result.Companion = Result.Companion;
         var0 = Result.constructor-impl(var1.invoke(var0));
      } catch (var3: java.lang.Throwable) {
         var0 = Result.Companion;
         return Result.constructor-impl(createFailure(var3));
      }
   } else {
      var0 = Result.constructor-impl(var0);
   }

   return var0;
}

public inline fun <T> Result<T>.onFailure(action: (Throwable) -> Unit): Result<T> {
   contract {
      callsInPlace(action, InvocationKind.AT_MOST_ONCE)
   }

   val var2: java.lang.Throwable = Result.exceptionOrNull-impl(var0);
   if (var2 != null) {
      var1.invoke(var2);
   }

   return var0;
}

public inline fun <T> Result<T>.onSuccess(action: (T) -> Unit): Result<T> {
   contract {
      callsInPlace(action, InvocationKind.AT_MOST_ONCE)
   }

   if (Result.isSuccess-impl(var0)) {
      var1.invoke(var0);
   }

   return var0;
}

public inline fun <R, T : R> Result<T>.recover(transform: (Throwable) -> R): Result<R> {
   contract {
      callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
   }

   val var2: java.lang.Throwable = Result.exceptionOrNull-impl(var0);
   if (var2 != null) {
      var0 = Result.Companion;
      var0 = (Result.Companion)Result.constructor-impl(var1.invoke(var2));
   }

   return var0;
}

public inline fun <R, T : R> Result<T>.recoverCatching(transform: (Throwable) -> R): Result<R> {
   val var2: java.lang.Throwable = Result.exceptionOrNull-impl(var0);
   if (var2 != null) {
      try {
         var0 = Result.Companion;
         var0 = (Result.Companion)Result.constructor-impl(var1.invoke(var2));
      } catch (var3: java.lang.Throwable) {
         val var6: Result.Companion = Result.Companion;
         return Result.constructor-impl(createFailure(var3));
      }
   }

   return var0;
}

public inline fun <T, R> T.runCatching(block: (T) -> R): Result<R> {
   try {
      val var2: Result.Companion = Result.Companion;
      var0 = Result.constructor-impl(var1.invoke(var0));
   } catch (var3: java.lang.Throwable) {
      val var6: Result.Companion = Result.Companion;
      return Result.constructor-impl(createFailure(var3));
   }

   return var0;
}

public inline fun <R> runCatching(block: () -> R): Result<R> {
   try {
      val var5: Result.Companion = Result.Companion;
      var4 = Result.constructor-impl(var0.invoke());
   } catch (var2: java.lang.Throwable) {
      val var1: Result.Companion = Result.Companion;
      return Result.constructor-impl(createFailure(var2));
   }

   return var4;
}

internal fun Result<*>.throwOnFailure() {
   if (var0 is Result.Failure) {
      throw (var0 as Result.Failure).exception;
   }
}
