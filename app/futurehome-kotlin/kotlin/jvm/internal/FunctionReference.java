package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KFunction;

public class FunctionReference extends CallableReference implements FunctionBase, KFunction {
   private final int arity;
   private final int flags;

   public FunctionReference(int var1) {
      this(var1, NO_RECEIVER, null, null, null, 0);
   }

   public FunctionReference(int var1, Object var2) {
      this(var1, var2, null, null, null, 0);
   }

   public FunctionReference(int var1, Object var2, Class var3, String var4, String var5, int var6) {
      boolean var7;
      if ((var6 & 1) == 1) {
         var7 = true;
      } else {
         var7 = false;
      }

      super(var2, var3, var4, var5, var7);
      this.arity = var1;
      this.flags = var6 >> 1;
   }

   @Override
   protected KCallable computeReflected() {
      return Reflection.function(this);
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof FunctionReference)) {
         return var1 instanceof KFunction ? var1.equals(this.compute()) : false;
      } else {
         var1 = var1;
         if (!this.getName().equals(var1.getName())
            || !this.getSignature().equals(var1.getSignature())
            || this.flags != var1.flags
            || this.arity != var1.arity
            || !Intrinsics.areEqual(this.getBoundReceiver(), var1.getBoundReceiver())
            || !Intrinsics.areEqual(this.getOwner(), var1.getOwner())) {
            var2 = false;
         }

         return var2;
      }
   }

   @Override
   public int getArity() {
      return this.arity;
   }

   protected KFunction getReflected() {
      return (KFunction)super.getReflected();
   }

   @Override
   public int hashCode() {
      int var1;
      if (this.getOwner() == null) {
         var1 = 0;
      } else {
         var1 = this.getOwner().hashCode() * 31;
      }

      return (var1 + this.getName().hashCode()) * 31 + this.getSignature().hashCode();
   }

   @Override
   public boolean isExternal() {
      return this.getReflected().isExternal();
   }

   @Override
   public boolean isInfix() {
      return this.getReflected().isInfix();
   }

   @Override
   public boolean isInline() {
      return this.getReflected().isInline();
   }

   @Override
   public boolean isOperator() {
      return this.getReflected().isOperator();
   }

   @Override
   public boolean isSuspend() {
      return this.getReflected().isSuspend();
   }

   @Override
   public String toString() {
      KCallable var1 = this.compute();
      if (var1 != this) {
         return var1.toString();
      } else {
         String var2;
         if ("<init>".equals(this.getName())) {
            var2 = "constructor (Kotlin reflection is not available)";
         } else {
            StringBuilder var3 = new StringBuilder("function ");
            var3.append(this.getName());
            var3.append(" (Kotlin reflection is not available)");
            var2 = var3.toString();
         }

         return var2;
      }
   }
}
