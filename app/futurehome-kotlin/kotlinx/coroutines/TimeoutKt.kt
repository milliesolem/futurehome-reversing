package kotlinx.coroutines

import kotlin.contracts.InvocationKind
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlin.coroutines.jvm.internal.DebugProbesKt
import kotlin.jvm.functions.Function2
import kotlin.jvm.internal.Ref
import kotlin.time.Duration
import kotlin.time.DurationKt
import kotlin.time.DurationUnit
import kotlinx.coroutines.intrinsics.UndispatchedKt

internal fun TimeoutCancellationException(time: Long, delay: Delay, coroutine: Job): TimeoutCancellationException {
   val var5: DelayWithTimeoutDiagnostics;
   if (var2 is DelayWithTimeoutDiagnostics) {
      var5 = var2 as DelayWithTimeoutDiagnostics;
   } else {
      var5 = null;
   }

   if (var5 != null) {
      val var4: Duration.Companion = Duration.Companion;
      val var8: java.lang.String = var5.timeoutMessage-LRDsOJo(DurationKt.toDuration(var0, DurationUnit.MILLISECONDS));
      if (var8 != null) {
         return new TimeoutCancellationException(var8, var3);
      }
   }

   val var7: StringBuilder = new StringBuilder("Timed out waiting for ");
   var7.append(var0);
   var7.append(" ms");
   return new TimeoutCancellationException(var7.toString(), var3);
}

private fun <U, T : U> setupTimeout(coroutine: TimeoutCoroutine<U, T>, block: (CoroutineScope, Continuation<T>) -> Any?): Any? {
   JobKt.disposeOnCompletion(var0, DelayKt.getDelay(var0.uCont.getContext()).invokeOnTimeout(var0.time, var0, var0.getContext()));
   return UndispatchedKt.startUndispatchedOrReturnIgnoreTimeout(var0, var0, var1);
}

public suspend fun <T> withTimeout(timeMillis: Long, block: (CoroutineScope, Continuation<T>) -> Any?): T {
   contract {
      callsInPlace(block, InvocationKind.EXACTLY_ONCE)
   }

   if (var0 > 0L) {
      val var4: Any = setupTimeout(new TimeoutCoroutine(var0, var3), var2);
      if (var4 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
         DebugProbesKt.probeCoroutineSuspended(var3);
      }

      return var4;
   } else {
      throw new TimeoutCancellationException("Timed out immediately");
   }
}

public suspend fun <T> withTimeout(timeout: Duration, block: (CoroutineScope, Continuation<T>) -> Any?): T {
   contract {
      callsInPlace(block, InvocationKind.EXACTLY_ONCE)
   }

   return withTimeout(DelayKt.toDelayMillis-LRDsOJo(var0), var2, var3);
}

public suspend fun <T> withTimeoutOrNull(timeMillis: Long, block: (CoroutineScope, Continuation<T>) -> Any?): T? {
   label60: {
      if (var3 is <unrepresentable>) {
         val var5: <unrepresentable> = var3 as <unrepresentable>;
         if ((var3.label and Integer.MIN_VALUE) != 0) {
            var5.label += Integer.MIN_VALUE;
            var3 = var5;
            break label60;
         }
      }

      var3 = new ContinuationImpl(var3) {
         long J$0;
         Object L$0;
         Object L$1;
         int label;
         Object result;

         {
            super(var1);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            this.result = var1;
            this.label |= Integer.MIN_VALUE;
            return TimeoutKt.withTimeoutOrNull(0L, null, this);
         }
      };
   }

   var var17: Ref.ObjectRef = (Ref.ObjectRef)var3.result;
   var var6: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
   label52:
   if (var3.label != 0) {
      if (var3.label != 1) {
         throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
      }

      var0 = var3.J$0;
      var6 = var3.L$1 as Ref.ObjectRef;
      var2 = var3.L$0 as Function2;

      try {
         ResultKt.throwOnFailure(var17);
      } catch (var9: TimeoutCancellationException) {
         var13 = var9;
         var16 = (Ref.ObjectRef)var6;
         break label52;
      }

      return var17;
   } else {
      label75: {
         ResultKt.throwOnFailure(var17);
         if (var0 <= 0L) {
            return null;
         }

         var17 = new Ref.ObjectRef();

         try {
            var3.L$0 = var2;
            var3.L$1 = var17;
            var3.J$0 = var0;
            var3.label = 1;
            val var7: TimeoutCoroutine = new TimeoutCoroutine<>(var0, var3);
            var17.element = (T)var7;
            var14 = setupTimeout(var7, var2);
            if (var14 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
               DebugProbesKt.probeCoroutineSuspended(var3);
            }
         } catch (var10: TimeoutCancellationException) {
            var13 = var10;
            var16 = var17;
            break label75;
         }

         if (var14 === var6) {
            return var6;
         }

         return var14;
      }
   }

   if (var13.coroutine === var16.element) {
      return null;
   } else {
      throw var13;
   }
}

public suspend fun <T> withTimeoutOrNull(timeout: Duration, block: (CoroutineScope, Continuation<T>) -> Any?): T? {
   return withTimeoutOrNull(DelayKt.toDelayMillis-LRDsOJo(var0), var2, var3);
}
