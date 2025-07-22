package kotlinx.coroutines

import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlin.coroutines.jvm.internal.DebugProbesKt
import kotlin.time.Duration

internal final val delay: Delay
   internal final get() {
      val var2: CoroutineContext.Element = var0.get(ContinuationInterceptor.Key);
      val var3: Delay;
      if (var2 is Delay) {
         var3 = var2 as Delay;
      } else {
         var3 = null;
      }

      var var1: Delay = var3;
      if (var3 == null) {
         var1 = DefaultExecutorKt.getDefaultDelay();
      }

      return var1;
   }


public suspend fun awaitCancellation(): Nothing {
   label27: {
      if (var0 is <unrepresentable>) {
         val var2: <unrepresentable> = var0 as <unrepresentable>;
         if ((var0.label and Integer.MIN_VALUE) != 0) {
            var2.label += Integer.MIN_VALUE;
            var0 = var2;
            break label27;
         }
      }

      var0 = new ContinuationImpl(var0) {
         int label;
         Object result;

         {
            super(var1);
         }

         @Override
         public final Object invokeSuspend(Object var1) {
            this.result = var1;
            this.label |= Integer.MIN_VALUE;
            return DelayKt.awaitCancellation(this);
         }
      };
   }

   var var3: CancellableContinuationImpl = (CancellableContinuationImpl)var0.result;
   val var7: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
   if (var0.label != 0) {
      if (var0.label != 1) {
         throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
      }

      ResultKt.throwOnFailure(var3);
   } else {
      ResultKt.throwOnFailure(var3);
      var0.label = 1;
      val var6: Continuation = var0;
      var3 = new CancellableContinuationImpl<>(IntrinsicsKt.intercepted(var0), 1);
      var3.initCancellability();
      val var4: CancellableContinuation = var3;
      var3 = (CancellableContinuationImpl)var3.getResult();
      if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
         DebugProbesKt.probeCoroutineSuspended(var6);
      }

      if (var3 === var7) {
         return var7;
      }
   }

   throw new KotlinNothingValueException();
}

public suspend fun delay(timeMillis: Long) {
   if (var0 <= 0L) {
      return Unit.INSTANCE;
   } else {
      val var3: CancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(var2), 1);
      var3.initCancellability();
      val var4: CancellableContinuation = var3;
      if (var0 < java.lang.Long.MAX_VALUE) {
         getDelay(var4.getContext()).scheduleResumeAfterDelay(var0, var4);
      }

      val var5: Any = var3.getResult();
      if (var5 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
         DebugProbesKt.probeCoroutineSuspended(var2);
      }

      return if (var5 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var5 else Unit.INSTANCE;
   }
}

public suspend fun delay(duration: Duration) {
   val var3: Any = delay(toDelayMillis-LRDsOJo(var0), var2);
   return if (var3 === IntrinsicsKt.getCOROUTINE_SUSPENDED()) var3 else Unit.INSTANCE;
}

internal fun Duration.toDelayMillis(): Long {
   if (Duration.compareTo-LRDsOJo(var0, Duration.Companion.getZERO-UwyO8pc()) > 0) {
      var0 = RangesKt.coerceAtLeast(Duration.getInWholeMilliseconds-impl(var0), 1L);
   } else {
      var0 = 0L;
   }

   return var0;
}
