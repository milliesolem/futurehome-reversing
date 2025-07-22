package kotlin.jvm.internal;

import java.io.Serializable;
import kotlin.reflect.KFunction;

public class FunInterfaceConstructorReference extends FunctionReference implements Serializable {
   private final Class funInterface;

   public FunInterfaceConstructorReference(Class var1) {
      super(1);
      this.funInterface = var1;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof FunInterfaceConstructorReference)) {
         return false;
      } else {
         var1 = var1;
         return this.funInterface.equals(var1.funInterface);
      }
   }

   @Override
   protected KFunction getReflected() {
      throw new UnsupportedOperationException("Functional interface constructor does not support reflection");
   }

   @Override
   public int hashCode() {
      return this.funInterface.hashCode();
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("fun interface ");
      var1.append(this.funInterface.getName());
      return var1.toString();
   }
}
