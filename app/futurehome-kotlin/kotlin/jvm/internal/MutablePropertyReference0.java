package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KMutableProperty0;
import kotlin.reflect.KProperty0;

public abstract class MutablePropertyReference0 extends MutablePropertyReference implements KMutableProperty0 {
   public MutablePropertyReference0() {
   }

   public MutablePropertyReference0(Object var1) {
      super(var1);
   }

   public MutablePropertyReference0(Object var1, Class var2, String var3, String var4, int var5) {
      super(var1, var2, var3, var4, var5);
   }

   @Override
   protected KCallable computeReflected() {
      return Reflection.mutableProperty0(this);
   }

   @Override
   public Object getDelegate() {
      return ((KMutableProperty0)this.getReflected()).getDelegate();
   }

   @Override
   public KProperty0.Getter getGetter() {
      return ((KMutableProperty0)this.getReflected()).getGetter();
   }

   @Override
   public KMutableProperty0.Setter getSetter() {
      return ((KMutableProperty0)this.getReflected()).getSetter();
   }

   @Override
   public Object invoke() {
      return this.get();
   }
}
