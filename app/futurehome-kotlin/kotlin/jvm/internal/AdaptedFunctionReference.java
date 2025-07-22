package kotlin.jvm.internal;

import java.io.Serializable;
import kotlin.reflect.KDeclarationContainer;

public class AdaptedFunctionReference implements FunctionBase, Serializable {
   private final int arity;
   private final int flags;
   private final boolean isTopLevel;
   private final String name;
   private final Class owner;
   protected final Object receiver;
   private final String signature;

   public AdaptedFunctionReference(int var1, Class var2, String var3, String var4, int var5) {
      this(var1, CallableReference.NO_RECEIVER, var2, var3, var4, var5);
   }

   public AdaptedFunctionReference(int var1, Object var2, Class var3, String var4, String var5, int var6) {
      this.receiver = var2;
      this.owner = var3;
      this.name = var4;
      this.signature = var5;
      boolean var7;
      if ((var6 & 1) == 1) {
         var7 = true;
      } else {
         var7 = false;
      }

      this.isTopLevel = var7;
      this.arity = var1;
      this.flags = var6 >> 1;
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof AdaptedFunctionReference)) {
         return false;
      } else {
         var1 = var1;
         if (this.isTopLevel != var1.isTopLevel
            || this.arity != var1.arity
            || this.flags != var1.flags
            || !Intrinsics.areEqual(this.receiver, var1.receiver)
            || !Intrinsics.areEqual(this.owner, var1.owner)
            || !this.name.equals(var1.name)
            || !this.signature.equals(var1.signature)) {
            var2 = false;
         }

         return var2;
      }
   }

   @Override
   public int getArity() {
      return this.arity;
   }

   public KDeclarationContainer getOwner() {
      Class var1 = this.owner;
      Object var2;
      if (var1 == null) {
         var2 = null;
      } else if (this.isTopLevel) {
         var2 = Reflection.getOrCreateKotlinPackage(var1);
      } else {
         var2 = Reflection.getOrCreateKotlinClass(var1);
      }

      return (KDeclarationContainer)var2;
   }

   @Override
   public int hashCode() {
      Object var6 = this.receiver;
      int var2 = 0;
      int var1;
      if (var6 != null) {
         var1 = var6.hashCode();
      } else {
         var1 = 0;
      }

      var6 = this.owner;
      if (var6 != null) {
         var2 = var6.hashCode();
      }

      int var5 = this.name.hashCode();
      int var4 = this.signature.hashCode();
      short var3;
      if (this.isTopLevel) {
         var3 = 1231;
      } else {
         var3 = 1237;
      }

      return (((((var1 * 31 + var2) * 31 + var5) * 31 + var4) * 31 + var3) * 31 + this.arity) * 31 + this.flags;
   }

   @Override
   public String toString() {
      return Reflection.renderLambdaToString(this);
   }
}
