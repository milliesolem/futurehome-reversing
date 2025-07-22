package kotlin.jvm.internal;

import kotlin.reflect.KClass;
import kotlin.reflect.KDeclarationContainer;

public class MutablePropertyReference1Impl extends MutablePropertyReference1 {
   public MutablePropertyReference1Impl(Class var1, String var2, String var3, int var4) {
      super(NO_RECEIVER, var1, var2, var3, var4);
   }

   public MutablePropertyReference1Impl(Object var1, Class var2, String var3, String var4, int var5) {
      super(var1, var2, var3, var4, var5);
   }

   public MutablePropertyReference1Impl(KDeclarationContainer var1, String var2, String var3) {
      super(NO_RECEIVER, ((ClassBasedDeclarationContainer)var1).getJClass(), var2, var3, var1 instanceof KClass ^ 1);
   }

   @Override
   public Object get(Object var1) {
      return this.getGetter().call(new Object[]{var1});
   }

   @Override
   public void set(Object var1, Object var2) {
      this.getSetter().call(new Object[]{var1, var2});
   }
}
