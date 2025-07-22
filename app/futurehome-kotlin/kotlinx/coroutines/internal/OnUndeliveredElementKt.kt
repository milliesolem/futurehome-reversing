package kotlinx.coroutines.internal

import kotlin.coroutines.CoroutineContext
import kotlin.jvm.functions.Function1
import kotlinx.coroutines.CoroutineExceptionHandlerKt

internal fun <E> ((E) -> Unit).bindCancellationFun(element: E, context: CoroutineContext): (Throwable) -> Unit {
   return (new Function1<java.lang.Throwable, Unit>(var0, var1, var2) {
      final CoroutineContext $context;
      final E $element;
      final Function1<E, Unit> $this_bindCancellationFun;

      {
         super(1);
         this.$this_bindCancellationFun = var1;
         this.$element = (E)var2;
         this.$context = var3;
      }

      public final void invoke(java.lang.Throwable var1) {
         OnUndeliveredElementKt.callUndeliveredElement(this.$this_bindCancellationFun, this.$element, this.$context);
      }
   }) as (java.lang.Throwable?) -> Unit;
}

internal fun <E> ((E) -> Unit).callUndeliveredElement(element: E, context: CoroutineContext) {
   val var3: UndeliveredElementException = callUndeliveredElementCatchingException(var0, var1, null);
   if (var3 != null) {
      CoroutineExceptionHandlerKt.handleCoroutineException(var2, var3);
   }
}

internal fun <E> ((E) -> Unit).callUndeliveredElementCatchingException(element: E, undeliveredElementException: UndeliveredElementException? = null): UndeliveredElementException? {
   try {
      var0.invoke(var1);
   } catch (var3: java.lang.Throwable) {
      if (var2 == null || var2.getCause() === var3) {
         val var5: StringBuilder = new StringBuilder("Exception in undelivered element handler for ");
         var5.append(var1);
         return new UndeliveredElementException(var5.toString(), var3);
      }

      ExceptionsKt.addSuppressed(var2, var3);
      return var2;
   }

   return var2;
}

@JvmSynthetic
fun `callUndeliveredElementCatchingException$default`(var0: Function1, var1: Any, var2: UndeliveredElementException, var3: Int, var4: Any): UndeliveredElementException {
   if ((var3 and 2) != 0) {
      var2 = null;
   }

   return callUndeliveredElementCatchingException(var0, var1, var2);
}
