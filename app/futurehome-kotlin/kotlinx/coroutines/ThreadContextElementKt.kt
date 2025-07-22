package kotlinx.coroutines

import kotlin.coroutines.Continuation
import kotlin.coroutines.jvm.internal.Boxing
import kotlinx.coroutines.internal.ThreadLocalElement
import kotlinx.coroutines.internal.ThreadLocalKey

public fun <T> ThreadLocal<T>.asContextElement(value: T = var0.get()): ThreadContextElement<T> {
   return (ThreadContextElement<T>)(new ThreadLocalElement<>(var1, var0));
}

@JvmSynthetic
fun `asContextElement$default`(var0: ThreadLocal, var1: Any, var2: Int, var3: Any): ThreadContextElement {
   if ((var2 and 1) != 0) {
      var1 = var0.get();
   }

   return asContextElement(var0, var1);
}

public suspend inline fun ThreadLocal<*>.ensurePresent() {
   if (var1.getContext().<ThreadLocalElement<?>>get(new ThreadLocalKey(var0)) != null) {
      return Unit.INSTANCE;
   } else {
      val var2: StringBuilder = new StringBuilder("ThreadLocal ");
      var2.append(var0);
      var2.append(" is missing from context ");
      var2.append(var1.getContext());
      throw new IllegalStateException(var2.toString().toString());
   }
}

fun `ensurePresent$$forInline`(var0: ThreadLocal<?>, var1: Continuation<? super Unit>): Any {
   throw new NullPointerException();
}

public suspend inline fun ThreadLocal<*>.isPresent(): Boolean {
   val var2: Boolean;
   if (var1.getContext().<ThreadLocalElement<?>>get(new ThreadLocalKey(var0)) != null) {
      var2 = true;
   } else {
      var2 = false;
   }

   return Boxing.boxBoolean(var2);
}

fun `isPresent$$forInline`(var0: ThreadLocal<?>, var1: Continuation<? super java.lang.Boolean>): Any {
   throw new NullPointerException();
}
