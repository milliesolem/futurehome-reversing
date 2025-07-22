package kotlin.reflect

import java.lang.reflect.Type
import java.lang.reflect.WildcardType
import java.util.Arrays

private class WildcardTypeImpl(upperBound: Type?, lowerBound: Type?) : WildcardType, TypeImpl {
   private final val upperBound: Type?
   private final val lowerBound: Type?

   init {
      this.upperBound = var1;
      this.lowerBound = var2;
   }

   public override operator fun equals(other: Any?): Boolean {
      return var1 is WildcardType
         && Arrays.equals((Object[])this.getUpperBounds(), (Object[])(var1 as WildcardType).getUpperBounds())
         && Arrays.equals((Object[])this.getLowerBounds(), (Object[])(var1 as WildcardType).getLowerBounds());
   }

   public override fun getLowerBounds(): Array<Type> {
      val var2: Array<Type>;
      if (this.lowerBound == null) {
         var2 = new Type[0];
      } else {
         var2 = new Type[]{this.lowerBound};
      }

      return var2;
   }

   public override fun getTypeName(): String {
      val var2: java.lang.String;
      if (this.lowerBound != null) {
         val var1: StringBuilder = new StringBuilder("? super ");
         var1.append(TypesJVMKt.access$typeToString(this.lowerBound));
         var2 = var1.toString();
      } else if (this.upperBound != null && !(this.upperBound == Object::class.java)) {
         val var4: StringBuilder = new StringBuilder("? extends ");
         var4.append(TypesJVMKt.access$typeToString(this.upperBound));
         var2 = var4.toString();
      } else {
         var2 = "?";
      }

      return var2;
   }

   public override fun getUpperBounds(): Array<Type> {
      var var1: Type = this.upperBound;
      if (this.upperBound == null) {
         var1 = Object::class.java;
      }

      return new Type[]{var1};
   }

   public override fun hashCode(): Int {
      return Arrays.hashCode((Object[])this.getUpperBounds()) xor Arrays.hashCode((Object[])this.getLowerBounds());
   }

   public override fun toString(): String {
      return this.getTypeName();
   }

   public companion object {
      public final val STAR: WildcardTypeImpl
   }
}
