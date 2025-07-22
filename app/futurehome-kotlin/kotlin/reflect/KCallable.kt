package kotlin.reflect

public interface KCallable<R> : KAnnotatedElement {
   public val name: String
   public val parameters: List<KParameter>
   public val returnType: KType
   public val typeParameters: List<KTypeParameter>
   public val visibility: KVisibility?
   public val isFinal: Boolean
   public val isOpen: Boolean
   public val isAbstract: Boolean
   public val isSuspend: Boolean

   public abstract fun call(vararg args: Any?): Any {
   }

   public abstract fun callBy(args: Map<KParameter, Any?>): Any {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls
}
