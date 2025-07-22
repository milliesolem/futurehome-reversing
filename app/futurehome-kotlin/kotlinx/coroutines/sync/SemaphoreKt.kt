package kotlinx.coroutines.sync

import kotlin.contracts.InvocationKind
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlin.jvm.functions.Function0
import kotlinx.coroutines.internal.Symbol
import kotlinx.coroutines.internal.SystemPropsKt

private final val BROKEN: Symbol = new Symbol("BROKEN")
private final val CANCELLED: Symbol = new Symbol("CANCELLED")
private final val MAX_SPIN_CYCLES: Int = SystemPropsKt.systemProp$default("kotlinx.coroutines.semaphore.maxSpinCycles", 100, 0, 0, 12, null)
private final val PERMIT: Symbol = new Symbol("PERMIT")
private final val SEGMENT_SIZE: Int = SystemPropsKt.systemProp$default("kotlinx.coroutines.semaphore.segmentSize", 16, 0, 0, 12, null)
private final val TAKEN: Symbol = new Symbol("TAKEN")

public fun Semaphore(permits: Int, acquiredPermits: Int = 0): Semaphore {
   return new SemaphoreImpl(var0, var1);
}

@JvmSynthetic
fun `Semaphore$default`(var0: Int, var1: Int, var2: Int, var3: Any): Semaphore {
   if ((var2 and 2) != 0) {
      var1 = 0;
   }

   return Semaphore(var0, var1);
}

@JvmSynthetic
fun `access$createSegment`(var0: Long, var2: SemaphoreSegment): SemaphoreSegment {
   return createSegment(var0, var2);
}

@JvmSynthetic
fun `access$getBROKEN$p`(): Symbol {
   return BROKEN;
}

@JvmSynthetic
fun `access$getCANCELLED$p`(): Symbol {
   return CANCELLED;
}

@JvmSynthetic
fun `access$getMAX_SPIN_CYCLES$p`(): Int {
   return MAX_SPIN_CYCLES;
}

@JvmSynthetic
fun `access$getPERMIT$p`(): Symbol {
   return PERMIT;
}

@JvmSynthetic
fun `access$getSEGMENT_SIZE$p`(): Int {
   return SEGMENT_SIZE;
}

@JvmSynthetic
fun `access$getTAKEN$p`(): Symbol {
   return TAKEN;
}

private fun createSegment(id: Long, prev: SemaphoreSegment?): SemaphoreSegment {
   return new SemaphoreSegment(var0, var2, 0);
}

public suspend inline fun <T> Semaphore.withPermit(action: () -> T): T {
   contract {
      callsInPlace(action, InvocationKind.EXACTLY_ONCE)
   }

   label31: {
      var var4: Any;
      label29: {
         if (var2 is <unrepresentable>) {
            var4 = var2 as <unrepresentable>;
            if (((var2 as <unrepresentable>).label and Integer.MIN_VALUE) != 0) {
               ((<unrepresentable>)var4).label += Integer.MIN_VALUE;
               break label29;
            }
         }

         var4 = new ContinuationImpl(var2) {
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
               return SemaphoreKt.withPermit(null, null, this);
            }
         };
      }

      val var5: Any = ((<unrepresentable>)var4).result;
      val var6: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      val var10: Semaphore;
      if (((<unrepresentable>)var4).label != 0) {
         if (((<unrepresentable>)var4).label != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
         }

         var1 = ((<unrepresentable>)var4).L$1 as Function0;
         var10 = ((<unrepresentable>)var4).L$0 as Semaphore;
         ResultKt.throwOnFailure(var5);
      } else {
         ResultKt.throwOnFailure(var5);
         ((<unrepresentable>)var4).L$0 = var0;
         ((<unrepresentable>)var4).L$1 = var1;
         ((<unrepresentable>)var4).label = 1;
         var10 = var0;
         if (var0.acquire((Continuation<? super Unit>)var4) === var6) {
            return var6;
         }
      }

      try {
         val var9: Any = var1.invoke();
      } catch (var7: java.lang.Throwable) {
         var10.release();
      }

      var10.release();
   }
}

fun <T> `withPermit$$forInline`(var0: Semaphore, var1: () -> T, var2: Continuation<? super T>): Any {
   label13: {
      var0.acquire(var2);

      try {
         val var5: Any = var1.invoke();
      } catch (var3: java.lang.Throwable) {
         var0.release();
      }

      var0.release();
   }
}
