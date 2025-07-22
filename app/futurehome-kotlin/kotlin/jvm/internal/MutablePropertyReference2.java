package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KMutableProperty2;
import kotlin.reflect.KProperty2;

public abstract class MutablePropertyReference2 extends MutablePropertyReference implements KMutableProperty2 {
   public MutablePropertyReference2() {
   }

   public MutablePropertyReference2(Class var1, String var2, String var3, int var4) {
      super(NO_RECEIVER, var1, var2, var3, var4);
   }

   @Override
   protected KCallable computeReflected() {
      return Reflection.mutableProperty2(this);
   }

   @Override
   public Object getDelegate(Object var1, Object var2) {
      return ((KMutableProperty2)this.getReflected()).getDelegate(var1, var2);
   }

   @Override
   public KProperty2.Getter getGetter() {
      return ((KMutableProperty2)this.getReflected()).getGetter();
   }

   @Override
   public KMutableProperty2.Setter getSetter() {
      return ((KMutableProperty2)this.getReflected()).getSetter();
   }

   @Override
   public Object invoke(Object var1, Object var2) {
      return this.get(var1, var2);
   }
}
