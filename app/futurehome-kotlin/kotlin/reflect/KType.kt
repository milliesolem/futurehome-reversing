package kotlin.reflect

public interface KType : KAnnotatedElement {
   public val classifier: KClassifier?
   public val arguments: List<KTypeProjection>
   public val isMarkedNullable: Boolean

   // $VF: Class flags could not be determined
   internal class DefaultImpls
}
