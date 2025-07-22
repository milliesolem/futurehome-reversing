package kotlinx.coroutines.selects

import kotlin.coroutines.Continuation

public sealed interface SelectBuilder<R> {
   public abstract operator fun SelectClause0.invoke(block: (Continuation<Any>) -> Any?) {
   }

   public abstract operator fun <Q> SelectClause1<Q>.invoke(block: (Q, Continuation<Any>) -> Any?) {
   }

   public abstract operator fun <P, Q> SelectClause2<P, Q>.invoke(param: P, block: (Q, Continuation<Any>) -> Any?) {
   }

   public open operator fun <P, Q> SelectClause2<P?, Q>.invoke(block: (Q, Continuation<Any>) -> Any?) {
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Replaced with the same extension function", replaceWith = @ReplaceWith(expression = "onTimeout", imports = ["kotlinx.coroutines.selects.onTimeout"]))
   public open fun onTimeout(timeMillis: Long, block: (Continuation<Any>) -> Any?) {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun <R, P, Q> invoke(var0: SelectBuilder<? super R>, var1: SelectClause2<? super P, ? extends Q>, var2: (Q?, Continuation<? super R>?) -> Any) {
         var0.invoke(var1, null, var2);
      }

      @Deprecated(level = DeprecationLevel.ERROR, message = "Replaced with the same extension function", replaceWith = @ReplaceWith(expression = "onTimeout", imports = ["kotlinx.coroutines.selects.onTimeout"]))
      @JvmStatic
      fun <R> onTimeout(var0: SelectBuilder<? super R>, var1: Long, var3: (Continuation<? super R>?) -> Any) {
         OnTimeoutKt.onTimeout(var0, var1, var3);
      }
   }
}
