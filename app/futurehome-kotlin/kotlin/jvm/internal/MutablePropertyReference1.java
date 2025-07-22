package kotlin.jvm.internal;

import kotlin.reflect.KCallable;
import kotlin.reflect.KMutableProperty1;
import kotlin.reflect.KProperty1;

public abstract class MutablePropertyReference1 extends MutablePropertyReference implements KMutableProperty1 {
   public MutablePropertyReference1() {
   }

   public MutablePropertyReference1(Object var1) {
      super(var1);
   }

   public MutablePropertyReference1(Object var1, Class var2, String var3, String var4, int var5) {
      super(var1, var2, var3, var4, var5);
   }

   @Override
   protected KCallable computeReflected() {
      return Reflection.mutableProperty1(this);
   }

   @Override
   public Object getDelegate(Object var1) {
      return ((KMutableProperty1)this.getReflected()).getDelegate(var1);
   }

   @Override
   public KProperty1.Getter getGetter() {
      return ((KMutableProperty1)this.getReflected()).getGetter();
   }

   @Override
   public KMutableProperty1.Setter getSetter() {
      return ((KMutableProperty1)this.getReflected()).getSetter();
   }

   @Override
   public Object invoke(Object var1) {
      return this.get(var1);
   }
}
