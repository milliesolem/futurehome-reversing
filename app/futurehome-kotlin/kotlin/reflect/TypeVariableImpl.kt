package kotlin.reflect

import java.lang.reflect.GenericDeclaration
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable
import java.util.ArrayList

private class TypeVariableImpl(typeParameter: KTypeParameter) : TypeVariable<GenericDeclaration>, TypeImpl {
   private final val typeParameter: KTypeParameter

   init {
      this.typeParameter = var1;
   }

   public override operator fun equals(other: Any?): Boolean {
      return var1 is TypeVariable
         && this.getName() == (var1 as TypeVariable).getName()
         && this.getGenericDeclaration() == (var1 as TypeVariable).getGenericDeclaration();
   }

   public override fun <T : Annotation> getAnnotation(annotationClass: Class<T>): T? {
      return null;
   }

   public override fun getAnnotations(): Array<Annotation> {
      return new java.lang.annotation.Annotation[0];
   }

   public override fun getBounds(): Array<Type> {
      val var2: java.lang.Iterable = this.typeParameter.getUpperBounds();
      val var1: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var2, 10));
      val var3: java.util.Iterator = var2.iterator();

      while (var3.hasNext()) {
         var1.add(TypesJVMKt.access$computeJavaType(var3.next() as KType, true));
      }

      return (var1 as java.util.List).toArray(new Type[0]);
   }

   public override fun getDeclaredAnnotations(): Array<Annotation> {
      return new java.lang.annotation.Annotation[0];
   }

   public override fun getGenericDeclaration(): GenericDeclaration {
      var var1: StringBuilder = new StringBuilder("getGenericDeclaration() is not yet supported for type variables created from KType: ");
      var1.append(this.typeParameter);
      val var2: java.lang.String = var1.toString();
      var1 = new StringBuilder("An operation is not implemented: ");
      var1.append(var2);
      throw new NotImplementedError(var1.toString());
   }

   public override fun getName(): String {
      return this.typeParameter.getName();
   }

   public override fun getTypeName(): String {
      return this.getName();
   }

   public override fun hashCode(): Int {
      return this.getName().hashCode() xor this.getGenericDeclaration().hashCode();
   }

   public override fun toString(): String {
      return this.getTypeName();
   }
}
