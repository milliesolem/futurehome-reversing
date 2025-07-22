package kotlin.jvm.internal;

import kotlin.reflect.KClass;
import kotlin.reflect.KDeclarationContainer;

public class PropertyReference2Impl extends PropertyReference2 {
   public PropertyReference2Impl(Class var1, String var2, String var3, int var4) {
      super(var1, var2, var3, var4);
   }

   public PropertyReference2Impl(KDeclarationContainer var1, String var2, String var3) {
      super(((ClassBasedDeclarationContainer)var1).getJClass(), var2, var3, var1 instanceof KClass ^ 1);
   }

   @Override
   public Object get(Object var1, Object var2) {
      return this.getGetter().call(new Object[]{var1, var2});
   }
}
