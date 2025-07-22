package kotlinx.coroutines.intrinsics

import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2
import kotlinx.coroutines.internal.DispatchedContinuationKt

private fun dispatcherFailure(completion: Continuation<*>, e: Throwable) {
   val var2: Result.Companion = Result.Companion;
   var0.resumeWith(Result.constructor-impl(ResultKt.createFailure(var1)));
   throw var1;
}

private inline fun runSafely(completion: Continuation<*>, block: () -> Unit) {
   try {
      var1.invoke();
   } catch (var2: java.lang.Throwable) {
      dispatcherFailure(var0, var2);
      return;
   }
}

internal fun Continuation<Unit>.startCoroutineCancellable(fatalCompletion: Continuation<*>) {
   try {
      var0 = IntrinsicsKt.intercepted(var0);
      val var2: Result.Companion = Result.Companion;
      DispatchedContinuationKt.resumeCancellableWith$default(var0, Result.constructor-impl(Unit.INSTANCE), null, 2, null);
   } catch (var3: java.lang.Throwable) {
      dispatcherFailure(var1, var3);
      return;
   }
}

public fun <T> ((Continuation<T>) -> Any?).startCoroutineCancellable(completion: Continuation<T>) {
   try {
      val var5: Continuation = IntrinsicsKt.intercepted(IntrinsicsKt.createCoroutineUnintercepted(var0, var1));
      val var2: Result.Companion = Result.Companion;
      DispatchedContinuationKt.resumeCancellableWith$default(var5, Result.constructor-impl(Unit.INSTANCE), null, 2, null);
   } catch (var3: java.lang.Throwable) {
      dispatcherFailure(var1, var3);
      return;
   }
}

internal fun <R, T> ((R, Continuation<T>) -> Any?).startCoroutineCancellable(
   receiver: R,
   completion: Continuation<T>,
   onCancellation: ((Throwable) -> Unit)? = null
) {
   try {
      var1 = IntrinsicsKt.intercepted(IntrinsicsKt.createCoroutineUnintercepted(var0, var1, var2));
      val var6: Result.Companion = Result.Companion;
      DispatchedContinuationKt.resumeCancellableWith(var1, Result.constructor-impl(Unit.INSTANCE), var3);
   } catch (var4: java.lang.Throwable) {
      dispatcherFailure(var2, var4);
      return;
   }
}

@JvmSynthetic
fun `startCoroutineCancellable$default`(var0: Function2, var1: Any, var2: Continuation, var3: Function1, var4: Int, var5: Any) {
   if ((var4 and 4) != 0) {
      var3 = null;
   }

   startCoroutineCancellable(var0, var1, var2, var3);
}
