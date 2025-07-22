package kotlin.jvm.internal;

import kotlin.reflect.KClass;
import kotlin.reflect.KDeclarationContainer;

public class FunctionReferenceImpl extends FunctionReference {
   public FunctionReferenceImpl(int var1, Class var2, String var3, String var4, int var5) {
      super(var1, NO_RECEIVER, var2, var3, var4, var5);
   }

   public FunctionReferenceImpl(int var1, Object var2, Class var3, String var4, String var5, int var6) {
      super(var1, var2, var3, var4, var5, var6);
   }

   public FunctionReferenceImpl(int var1, KDeclarationContainer var2, String var3, String var4) {
      super(var1, NO_RECEIVER, ((ClassBasedDeclarationContainer)var2).getJClass(), var3, var4, var2 instanceof KClass ^ 1);
   }
}
