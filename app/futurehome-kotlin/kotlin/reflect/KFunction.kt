package kotlin.reflect

public interface KFunction<R> : KCallable<R>, Function<R> {
   public val isInline: Boolean
   public val isExternal: Boolean
   public val isOperator: Boolean
   public val isInfix: Boolean
   public val isSuspend: Boolean

   // $VF: Class flags could not be determined
   internal class DefaultImpls
}
