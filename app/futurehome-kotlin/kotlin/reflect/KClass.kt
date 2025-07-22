package kotlin.reflect

public interface KClass<T> : KDeclarationContainer, KAnnotatedElement, KClassifier {
   public val simpleName: String?
   public val qualifiedName: String?
   public val members: Collection<KCallable<*>>
   public val constructors: Collection<KFunction<Any>>
   public val nestedClasses: Collection<KClass<*>>
   public val objectInstance: Any?
   public val typeParameters: List<KTypeParameter>
   public val supertypes: List<KType>
   public val sealedSubclasses: List<KClass<out Any>>
   public val visibility: KVisibility?
   public val isFinal: Boolean
   public val isOpen: Boolean
   public val isAbstract: Boolean
   public val isSealed: Boolean
   public val isData: Boolean
   public val isInner: Boolean
   public val isCompanion: Boolean
   public val isFun: Boolean
   public val isValue: Boolean

   public abstract override operator fun equals(other: Any?): Boolean {
   }

   public abstract override fun hashCode(): Int {
   }

   public abstract fun isInstance(value: Any?): Boolean {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls
}
