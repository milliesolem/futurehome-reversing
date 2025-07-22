package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty1;

public abstract class PropertyReference1 extends PropertyReference implements KProperty1 {
   public PropertyReference1() {
   }

   public PropertyReference1(Object var1) {
      super(var1);
   }

   public PropertyReference1(Object var1, Class var2, String var3, String var4, int var5) {
      super(var1, var2, var3, var4, var5);
   }

   @Override
   protected KCallable computeReflected() {
      return Reflection.property1(this);
   }

   @Override
   public Object getDelegate(Object var1) {
      return ((KProperty1)this.getReflected()).getDelegate(var1);
   }

   @Override
   public KProperty1.Getter getGetter() {
      return ((KProperty1)this.getReflected()).getGetter();
   }

   @Override
   public Object invoke(Object var1) {
      return this.get(var1);
   }
}
