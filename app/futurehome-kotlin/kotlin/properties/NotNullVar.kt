package kotlin.properties

import kotlin.reflect.KProperty

private class NotNullVar<T> : ReadWriteProperty<Object, T> {
   private final var value: Any?

   public override operator fun getValue(thisRef: Any?, property: KProperty<*>): Any {
      if (this.value != null) {
         return this.value;
      } else {
         var1 = new StringBuilder("Property ");
         var1.append(var2.getName());
         var1.append(" should be initialized before get.");
         throw new IllegalStateException(var1.toString());
      }
   }

   public override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Any) {
      this.value = (T)var3;
   }

   public override fun toString(): String {
      val var2: StringBuilder = new StringBuilder("NotNullProperty(");
      val var3: java.lang.String;
      if (this.value != null) {
         val var1: StringBuilder = new StringBuilder("value=");
         var1.append(this.value);
         var3 = var1.toString();
      } else {
         var3 = "value not initialized yet";
      }

      var2.append(var3);
      var2.append(')');
      return var2.toString();
   }
}
