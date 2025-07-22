package kotlinx.coroutines.flow

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.DebugKt
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.internal.NullSurrogateKt
import kotlinx.coroutines.internal.Symbol

private final val NONE: Symbol = new Symbol("NONE")
private final val PENDING: Symbol = new Symbol("PENDING")

public fun <T> MutableStateFlow(value: T): MutableStateFlow<T> {
   var var1: Any = var0;
   if (var0 == null) {
      var1 = NullSurrogateKt.NULL;
   }

   return new StateFlowImpl(var1);
}

@JvmSynthetic
fun `access$getNONE$p`(): Symbol {
   return NONE;
}

@JvmSynthetic
fun `access$getPENDING$p`(): Symbol {
   return PENDING;
}

internal fun <T> StateFlow<T>.fuseStateFlow(context: CoroutineContext, capacity: Int, onBufferOverflow: BufferOverflow): Flow<T> {
   if (DebugKt.getASSERTIONS_ENABLED() && var2 == -1) {
      throw new AssertionError();
   } else {
      return (Flow<T>)(if ((var2 >= 0 && var2 < 2 || var2 == -2) && var3 === BufferOverflow.DROP_OLDEST)
         var0
         else
         SharedFlowKt.fuseSharedFlow(var0, var1, var2, var3));
   }
}

public inline fun <T> MutableStateFlow<T>.getAndUpdate(function: (T) -> T): T {
   val var2: Any;
   do {
      var2 = var0.getValue();
   } while (!var0.compareAndSet(var2, var1.invoke(var2)));

   return (T)var2;
}

public inline fun <T> MutableStateFlow<T>.update(function: (T) -> T) {
   val var2: Any;
   do {
      var2 = var0.getValue();
   } while (!var0.compareAndSet(var2, var1.invoke(var2)));
}

public inline fun <T> MutableStateFlow<T>.updateAndGet(function: (T) -> T): T {
   val var2: Any;
   val var3: Any;
   do {
      var3 = var0.getValue();
      var2 = var1.invoke(var3);
   } while (!var0.compareAndSet(var3, var2));

   return (T)var2;
}
