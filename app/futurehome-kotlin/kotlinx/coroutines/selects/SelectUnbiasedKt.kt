package kotlinx.coroutines.selects

import kotlin.contracts.InvocationKind
import kotlin.coroutines.Continuation

public suspend inline fun <R> selectUnbiased(crossinline builder: (SelectBuilder<R>) -> Unit): R {
   contract {
      callsInPlace(builder, InvocationKind.EXACTLY_ONCE)
   }

   val var2: UnbiasedSelectImplementation = new UnbiasedSelectImplementation(var1.getContext());
   var0.invoke(var2);
   return var2.doSelect(var1);
}

fun <R> `selectUnbiased$$forInline`(var0: (SelectBuilder<? super R>?) -> Unit, var1: Continuation<? super R>): Any {
   val var2: UnbiasedSelectImplementation = new UnbiasedSelectImplementation;
   throw new NullPointerException();
}
