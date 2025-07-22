package kotlin.reflect

public interface KTypeParameter : KClassifier {
   public val name: String
   public val upperBounds: List<KType>
   public val variance: KVariance
   public val isReified: Boolean
}
