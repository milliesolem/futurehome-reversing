package kotlin.reflect

import java.lang.reflect.GenericArrayType
import java.lang.reflect.Type

private class GenericArrayTypeImpl(elementType: Type) : GenericArrayType, TypeImpl {
   private final val elementType: Type

   init {
      this.elementType = var1;
   }

   public override operator fun equals(other: Any?): Boolean {
      val var2: Boolean;
      if (var1 is GenericArrayType && this.getGenericComponentType() == (var1 as GenericArrayType).getGenericComponentType()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public override fun getGenericComponentType(): Type {
      return this.elementType;
   }

   public override fun getTypeName(): String {
      val var1: StringBuilder = new StringBuilder();
      var1.append(TypesJVMKt.access$typeToString(this.elementType));
      var1.append("[]");
      return var1.toString();
   }

   public override fun hashCode(): Int {
      return this.getGenericComponentType().hashCode();
   }

   public override fun toString(): String {
      return this.getTypeName();
   }
}
