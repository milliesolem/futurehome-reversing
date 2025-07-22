package kotlin.jvm.functions

import kotlin.jvm.internal.FunctionBase

public interface FunctionN<R> : Function<R>, FunctionBase<R> {
   public val arity: Int

   public abstract operator fun invoke(vararg args: Any?): Any {
   }
}
