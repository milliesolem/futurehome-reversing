package kotlinx.coroutines.selects

import kotlin.contracts.InvocationKind
import kotlin.coroutines.Continuation
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function3
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.internal.Symbol

private final val DUMMY_PROCESS_RESULT_FUNCTION: (Any, Any?, Any?) -> Any? = <unrepresentable>.INSTANCE as Function3
private final val NO_RESULT: Symbol = new Symbol("NO_RESULT")
internal final val PARAM_CLAUSE_0: Symbol = new Symbol("PARAM_CLAUSE_0")
private final val STATE_CANCELLED: Symbol = new Symbol("STATE_CANCELLED")
private final val STATE_COMPLETED: Symbol = new Symbol("STATE_COMPLETED")
private final val STATE_REG: Symbol = new Symbol("STATE_REG")
private const val TRY_SELECT_ALREADY_SELECTED: Int = 3
private const val TRY_SELECT_CANCELLED: Int = 2
private const val TRY_SELECT_REREGISTER: Int = 1
private const val TRY_SELECT_SUCCESSFUL: Int = 0

@JvmSynthetic
fun `OnCancellationConstructor$annotations`() {
}

@JvmSynthetic
fun `ProcessResultFunction$annotations`() {
}

@JvmSynthetic
fun `RegistrationFunction$annotations`() {
}

private fun TrySelectDetailedResult(trySelectInternalResult: Int): TrySelectDetailedResult {
   val var1: TrySelectDetailedResult;
   if (var0 != 0) {
      if (var0 != 1) {
         if (var0 != 2) {
            if (var0 != 3) {
               val var2: StringBuilder = new StringBuilder("Unexpected internal result: ");
               var2.append(var0);
               throw new IllegalStateException(var2.toString().toString());
            }

            var1 = TrySelectDetailedResult.ALREADY_SELECTED;
         } else {
            var1 = TrySelectDetailedResult.CANCELLED;
         }
      } else {
         var1 = TrySelectDetailedResult.REREGISTER;
      }
   } else {
      var1 = TrySelectDetailedResult.SUCCESSFUL;
   }

   return var1;
}

@JvmSynthetic
fun `access$TrySelectDetailedResult`(var0: Int): TrySelectDetailedResult {
   return TrySelectDetailedResult(var0);
}

@JvmSynthetic
fun `access$getDUMMY_PROCESS_RESULT_FUNCTION$p`(): Function3 {
   return DUMMY_PROCESS_RESULT_FUNCTION;
}

@JvmSynthetic
fun `access$getNO_RESULT$p`(): Symbol {
   return NO_RESULT;
}

@JvmSynthetic
fun `access$getSTATE_CANCELLED$p`(): Symbol {
   return STATE_CANCELLED;
}

@JvmSynthetic
fun `access$getSTATE_COMPLETED$p`(): Symbol {
   return STATE_COMPLETED;
}

@JvmSynthetic
fun `access$getSTATE_REG$p`(): Symbol {
   return STATE_REG;
}

@JvmSynthetic
fun `access$tryResume`(var0: CancellableContinuation, var1: Function1): Boolean {
   return tryResume(var0, var1);
}

public suspend inline fun <R> select(crossinline builder: (SelectBuilder<R>) -> Unit): R {
   contract {
      callsInPlace(builder, InvocationKind.EXACTLY_ONCE)
   }

   val var2: SelectImplementation = new SelectImplementation(var1.getContext());
   var0.invoke(var2);
   return var2.doSelect(var1);
}

fun <R> `select$$forInline`(var0: (SelectBuilder<? super R>?) -> Unit, var1: Continuation<? super R>): Any {
   val var2: SelectImplementation = new SelectImplementation;
   throw new NullPointerException();
}

private fun CancellableContinuation<Unit>.tryResume(onCancellation: ((Throwable) -> Unit)?): Boolean {
   val var2: Any = var0.tryResume(Unit.INSTANCE, null, var1);
   if (var2 == null) {
      return false;
   } else {
      var0.completeResume(var2);
      return true;
   }
}
