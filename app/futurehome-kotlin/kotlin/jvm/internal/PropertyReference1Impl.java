package kotlin.jvm.internal;

import kotlin.reflect.KClass;
import kotlin.reflect.KDeclarationContainer;

public class PropertyReference1Impl extends PropertyReference1 {
   public PropertyReference1Impl(Class var1, String var2, String var3, int var4) {
      super(NO_RECEIVER, var1, var2, var3, var4);
   }

   public PropertyReference1Impl(Object var1, Class var2, String var3, String var4, int var5) {
      super(var1, var2, var3, var4, var5);
   }

   public PropertyReference1Impl(KDeclarationContainer var1, String var2, String var3) {
      super(NO_RECEIVER, ((ClassBasedDeclarationContainer)var1).getJClass(), var2, var3, var1 instanceof KClass ^ 1);
   }

   @Override
   public Object get(Object var1) {
      return this.getGetter().call(new Object[]{var1});
   }
}
