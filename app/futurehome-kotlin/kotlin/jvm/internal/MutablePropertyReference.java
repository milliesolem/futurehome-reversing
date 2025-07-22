package kotlin.jvm.internal;

import kotlin.reflect.KMutableProperty;

public abstract class MutablePropertyReference extends PropertyReference implements KMutableProperty {
   public MutablePropertyReference() {
   }

   public MutablePropertyReference(Object var1) {
      super(var1);
   }

   public MutablePropertyReference(Object var1, Class var2, String var3, String var4, int var5) {
      super(var1, var2, var3, var4, var5);
   }
}
