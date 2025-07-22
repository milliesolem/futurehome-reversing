package kotlin.jvm.internal;

import kotlin.reflect.KClass;
import kotlin.reflect.KDeclarationContainer;

public class PropertyReference0Impl extends PropertyReference0 {
   public PropertyReference0Impl(Class var1, String var2, String var3, int var4) {
      super(NO_RECEIVER, var1, var2, var3, var4);
   }

   public PropertyReference0Impl(Object var1, Class var2, String var3, String var4, int var5) {
      super(var1, var2, var3, var4, var5);
   }

   public PropertyReference0Impl(KDeclarationContainer var1, String var2, String var3) {
      super(NO_RECEIVER, ((ClassBasedDeclarationContainer)var1).getJClass(), var2, var3, var1 instanceof KClass ^ 1);
   }

   @Override
   public Object get() {
      return this.getGetter().call(new Object[0]);
   }
}
