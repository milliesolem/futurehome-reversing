package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty2;

public abstract class PropertyReference2 extends PropertyReference implements KProperty2 {
   public PropertyReference2() {
   }

   public PropertyReference2(Class var1, String var2, String var3, int var4) {
      super(NO_RECEIVER, var1, var2, var3, var4);
   }

   @Override
   protected KCallable computeReflected() {
      return Reflection.property2(this);
   }

   @Override
   public Object getDelegate(Object var1, Object var2) {
      return ((KProperty2)this.getReflected()).getDelegate(var1, var2);
   }

   @Override
   public KProperty2.Getter getGetter() {
      return ((KProperty2)this.getReflected()).getGetter();
   }

   @Override
   public Object invoke(Object var1, Object var2) {
      return this.get(var1, var2);
   }
}
