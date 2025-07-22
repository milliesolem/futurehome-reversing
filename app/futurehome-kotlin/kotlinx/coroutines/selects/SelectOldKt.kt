package kotlinx.coroutines.selects

import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.DebugProbesKt
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CoroutineDispatcher

@JvmSynthetic
fun `access$resumeUndispatched`(var0: CancellableContinuation, var1: Any) {
   resumeUndispatched(var0, var1);
}

@JvmSynthetic
fun `access$resumeUndispatchedWithException`(var0: CancellableContinuation, var1: java.lang.Throwable) {
   resumeUndispatchedWithException(var0, var1);
}

private fun <T> CancellableContinuation<T>.resumeUndispatched(result: T) {
   val var2: CoroutineDispatcher = var0.getContext().get(CoroutineDispatcher.Key);
   if (var2 != null) {
      var0.resumeUndispatched(var2, var1);
   } else {
      val var3: Continuation = var0;
      val var4: Result.Companion = Result.Companion;
      var3.resumeWith(Result.constructor-impl(var1));
   }
}

private fun CancellableContinuation<*>.resumeUndispatchedWithException(exception: Throwable) {
   val var2: CoroutineDispatcher = var0.getContext().get(CoroutineDispatcher.Key);
   if (var2 != null) {
      var0.resumeUndispatchedWithException(var2, var1);
   } else {
      val var3: Continuation = var0;
      val var4: Result.Companion = Result.Companion;
      var3.resumeWith(Result.constructor-impl(ResultKt.createFailure(var1)));
   }
}

internal suspend inline fun <R> selectOld(crossinline builder: (SelectBuilder<R>) -> Unit): R {
   val var2: SelectBuilderImpl = new SelectBuilderImpl(var1);

   label16:
   try {
      var0.invoke(var2);
   } catch (var3: java.lang.Throwable) {
      var2.handleBuilderException(var3);
      break label16;
   }

   val var5: Any = var2.getResult();
   if (var5 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(var1);
   }

   return var5;
}

// $VF: Could not inline inconsistent finally blocks
// Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
fun <R> `selectOld$$forInline`(var0: (SelectBuilder<? super R>?) -> Unit, var1: Continuation<? super R>): Any {
   val var2: Continuation = var1;
   val var6: SelectBuilderImpl = new SelectBuilderImpl(var1);

   label16:
   try {
      var0.invoke(var6);
   } catch (var3: java.lang.Throwable) {
      var6.handleBuilderException(var3);
      break label16;
   }

   val var5: Any = var6.getResult();
   if (var5 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(var1);
   }

   return var5;
}

internal suspend inline fun <R> selectUnbiasedOld(crossinline builder: (SelectBuilder<R>) -> Unit): R {
   val var2: UnbiasedSelectBuilderImpl = new UnbiasedSelectBuilderImpl(var1);

   label16:
   try {
      var0.invoke(var2);
   } catch (var3: java.lang.Throwable) {
      var2.handleBuilderException(var3);
      break label16;
   }

   val var5: Any = var2.initSelectResult();
   if (var5 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(var1);
   }

   return var5;
}

// $VF: Could not inline inconsistent finally blocks
// Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
fun <R> `selectUnbiasedOld$$forInline`(var0: (SelectBuilder<? super R>?) -> Unit, var1: Continuation<? super R>): Any {
   val var2: Continuation = var1;
   val var6: UnbiasedSelectBuilderImpl = new UnbiasedSelectBuilderImpl(var1);

   label16:
   try {
      var0.invoke(var6);
   } catch (var3: java.lang.Throwable) {
      var6.handleBuilderException(var3);
      break label16;
   }

   val var5: Any = var6.initSelectResult();
   if (var5 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      DebugProbesKt.probeCoroutineSuspended(var1);
   }

   return var5;
}
