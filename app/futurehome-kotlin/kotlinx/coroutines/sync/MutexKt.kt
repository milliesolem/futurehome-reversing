package kotlinx.coroutines.sync

import kotlin.contracts.InvocationKind
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.IntrinsicsKt
import kotlin.coroutines.jvm.internal.ContinuationImpl
import kotlin.jvm.functions.Function0
import kotlinx.coroutines.internal.Symbol

private const val HOLDS_LOCK_ANOTHER_OWNER: Int = 2
private const val HOLDS_LOCK_UNLOCKED: Int = 0
private const val HOLDS_LOCK_YES: Int = 1
private final val NO_OWNER: Symbol = new Symbol("NO_OWNER")
private final val ON_LOCK_ALREADY_LOCKED_BY_OWNER: Symbol = new Symbol("ALREADY_LOCKED_BY_OWNER")
private const val TRY_LOCK_ALREADY_LOCKED_BY_OWNER: Int = 2
private const val TRY_LOCK_FAILED: Int = 1
private const val TRY_LOCK_SUCCESS: Int = 0

public fun Mutex(locked: Boolean = false): Mutex {
   return new MutexImpl(var0);
}

@JvmSynthetic
fun `Mutex$default`(var0: Boolean, var1: Int, var2: Any): Mutex {
   if ((var1 and 1) != 0) {
      var0 = false;
   }

   return Mutex(var0);
}

@JvmSynthetic
fun `access$getNO_OWNER$p`(): Symbol {
   return NO_OWNER;
}

@JvmSynthetic
fun `access$getON_LOCK_ALREADY_LOCKED_BY_OWNER$p`(): Symbol {
   return ON_LOCK_ALREADY_LOCKED_BY_OWNER;
}

public suspend inline fun <T> Mutex.withLock(owner: Any? = ..., action: () -> T): T {
   contract {
      callsInPlace(action, InvocationKind.EXACTLY_ONCE)
   }

   label31: {
      var var6: Any;
      label29: {
         if (var3 is <unrepresentable>) {
            var6 = var3 as <unrepresentable>;
            if (((var3 as <unrepresentable>).label and Integer.MIN_VALUE) != 0) {
               ((<unrepresentable>)var6).label += Integer.MIN_VALUE;
               break label29;
            }
         }

         var6 = new ContinuationImpl(var3) {
            Object L$0;
            Object L$1;
            Object L$2;
            int label;
            Object result;

            {
               super(var1);
            }

            @Override
            public final Object invokeSuspend(Object var1) {
               this.result = var1;
               this.label |= Integer.MIN_VALUE;
               return MutexKt.withLock(null, null, null, this);
            }
         };
      }

      val var7: Any = ((<unrepresentable>)var6).result;
      val var8: Any = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      val var5: Any;
      val var12: Mutex;
      if (((<unrepresentable>)var6).label != 0) {
         if (((<unrepresentable>)var6).label != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
         }

         var2 = ((<unrepresentable>)var6).L$2 as Function0;
         var5 = ((<unrepresentable>)var6).L$1;
         var12 = ((<unrepresentable>)var6).L$0 as Mutex;
         ResultKt.throwOnFailure(var7);
      } else {
         ResultKt.throwOnFailure(var7);
         ((<unrepresentable>)var6).L$0 = var0;
         ((<unrepresentable>)var6).L$1 = var1;
         ((<unrepresentable>)var6).L$2 = var2;
         ((<unrepresentable>)var6).label = 1;
         var12 = var0;
         var5 = var1;
         if (var0.lock(var1, (Continuation<? super Unit>)var6) === var8) {
            return var8;
         }
      }

      try {
         val var11: Any = var2.invoke();
      } catch (var9: java.lang.Throwable) {
         var12.unlock(var5);
      }

      var12.unlock(var5);
   }
}

fun <T> `withLock$$forInline`(var0: Mutex, var1: Any, var2: () -> T, var3: Continuation<? super T>): Any {
   label13: {
      var0.lock(var1, var3);

      try {
         val var6: Any = var2.invoke();
      } catch (var4: java.lang.Throwable) {
         var0.unlock(var1);
      }

      var0.unlock(var1);
   }
}

@JvmSynthetic
fun `withLock$default`(var0: Mutex, var1: Any, var2: Function0, var3: Continuation, var4: Int, var5: Any): Any {
   label17: {
      if ((var4 and 1) != 0) {
         var1 = null;
      }

      var0.lock(var1, var3);

      try {
         val var8: Any = var2.invoke();
      } catch (var6: java.lang.Throwable) {
         var0.unlock(var1);
      }

      var0.unlock(var1);
   }
}
