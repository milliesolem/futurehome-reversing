package kotlin.reflect

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.Arrays

private class ParameterizedTypeImpl(rawType: Class<*>, ownerType: Type?, typeArguments: List<Type>) : ParameterizedType, TypeImpl {
   private final val rawType: Class<*>
   private final val ownerType: Type?
   private final val typeArguments: Array<Type>

   init {
      this.rawType = var1;
      this.ownerType = var2;
      this.typeArguments = var3.toArray(new Type[0]);
   }

   public override operator fun equals(other: Any?): Boolean {
      return var1 is ParameterizedType
         && this.rawType == (var1 as ParameterizedType).getRawType()
         && this.ownerType == (var1 as ParameterizedType).getOwnerType()
         && Arrays.equals((Object[])this.getActualTypeArguments(), (Object[])(var1 as ParameterizedType).getActualTypeArguments());
   }

   public override fun getActualTypeArguments(): Array<Type> {
      return this.typeArguments;
   }

   public override fun getOwnerType(): Type? {
      return this.ownerType;
   }

   public override fun getRawType(): Type {
      return this.rawType;
   }

   public override fun getTypeName(): String {
      val var2: StringBuilder = new StringBuilder();
      if (this.ownerType != null) {
         var2.append(TypesJVMKt.access$typeToString(this.ownerType));
         var2.append("$");
         var2.append(this.rawType.getSimpleName());
      } else {
         var2.append(TypesJVMKt.access$typeToString(this.rawType));
      }

      val var1: Boolean;
      if (this.typeArguments.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (!var1) {
         ArraysKt.joinTo$default(this.typeArguments, var2, null, "<", ">", 0, null, <unrepresentable>.INSTANCE, 50, null);
      }

      return var2.toString();
   }

   public override fun hashCode(): Int {
      val var2: Int = this.rawType.hashCode();
      val var1: Int;
      if (this.ownerType != null) {
         var1 = this.ownerType.hashCode();
      } else {
         var1 = 0;
      }

      return var2 xor var1 xor Arrays.hashCode((Object[])this.getActualTypeArguments());
   }

   public override fun toString(): String {
      return this.getTypeName();
   }
}
