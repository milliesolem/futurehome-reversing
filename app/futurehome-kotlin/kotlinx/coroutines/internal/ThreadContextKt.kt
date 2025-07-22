package kotlinx.coroutines.internal

import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.CoroutineContext.Element
import kotlin.jvm.functions.Function2
import kotlinx.coroutines.ThreadContextElement

internal final val NO_THREAD_ELEMENTS: Symbol = new Symbol("NO_THREAD_ELEMENTS")
private final val countAll: (Any?, Element) -> Any? = <unrepresentable>.INSTANCE as Function2
private final val findOne: (ThreadContextElement<*>?, Element) -> ThreadContextElement<*>? = <unrepresentable>.INSTANCE as Function2
private final val updateState: (ThreadState, Element) -> ThreadState = <unrepresentable>.INSTANCE as Function2

internal fun restoreThreadContext(context: CoroutineContext, oldState: Any?) {
   if (var1 != NO_THREAD_ELEMENTS) {
      if (var1 is ThreadState) {
         (var1 as ThreadState).restore(var0);
      } else {
         val var2: Any = var0.fold(null, findOne);
         (var2 as ThreadContextElement).restoreThreadContext(var0, var1);
      }
   }
}

internal fun threadContextElements(context: CoroutineContext): Any {
   val var1: Any = var0.fold(0, countAll);
   return var1;
}

internal fun updateThreadContext(context: CoroutineContext, countOrElement: Any?): Any? {
   var var2: Any = var1;
   if (var1 == null) {
      var2 = threadContextElements(var0);
   }

   val var3: Any;
   if (var2 === 0) {
      var3 = NO_THREAD_ELEMENTS;
   } else if (var2 is Int) {
      var3 = var0.fold(new ThreadState(var0, (var2 as java.lang.Number).intValue()), updateState);
   } else {
      var3 = (var2 as ThreadContextElement).updateThreadContext(var0);
   }

   return var3;
}
