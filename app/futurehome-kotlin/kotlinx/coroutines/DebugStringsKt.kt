package kotlinx.coroutines

import kotlin.coroutines.Continuation
import kotlinx.coroutines.internal.DispatchedContinuation

internal final val classSimpleName: String
   internal final get() {
      return var0.getClass().getSimpleName();
   }


internal final val hexAddress: String
   internal final get() {
      return Integer.toHexString(System.identityHashCode(var0));
   }


internal fun Continuation<*>.toDebugString(): String {
   val var5: java.lang.String;
   if (var0 is DispatchedContinuation) {
      var5 = var0.toString();
   } else {
      var var1: StringBuilder;
      label19:
      try {
         var1 = Result.Companion;
         var1 = new StringBuilder();
         var1.append(var0);
         var1.append('@');
         var1.append(getHexAddress(var0));
         var1 = (StringBuilder)Result.constructor-impl(var1.toString());
      } catch (var3: java.lang.Throwable) {
         val var2: Result.Companion = Result.Companion;
         var1 = (StringBuilder)Result.constructor-impl(ResultKt.createFailure(var3));
         break label19;
      }

      if (Result.exceptionOrNull-impl(var1) != null) {
         var1 = new StringBuilder();
         var1.append(var0.getClass().getName());
         var1.append('@');
         var1.append(getHexAddress(var0));
         var1 = var1.toString();
      }

      var5 = var1 as java.lang.String;
   }

   return var5;
}
