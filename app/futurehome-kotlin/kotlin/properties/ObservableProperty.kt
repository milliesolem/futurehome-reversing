package kotlin.properties

import kotlin.reflect.KProperty

public abstract class ObservableProperty<V> : ReadWriteProperty<Object, V> {
   private final var value: Any

   open fun ObservableProperty(var1: V) {
      this.value = (V)var1;
   }

   protected open fun afterChange(property: KProperty<*>, oldValue: Any, newValue: Any) {
   }

   protected open fun beforeChange(property: KProperty<*>, oldValue: Any, newValue: Any): Boolean {
      return true;
   }

   public override operator fun getValue(thisRef: Any?, property: KProperty<*>): Any {
      return this.value;
   }

   public override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Any) {
      var1 = this.value;
      if (this.beforeChange(var2, this.value, var3)) {
         this.value = (V)var3;
         this.afterChange(var2, (V)var1, (V)var3);
      }
   }

   public override fun toString(): String {
      val var1: StringBuilder = new StringBuilder("ObservableProperty(value=");
      var1.append(this.value);
      var1.append(')');
      return var1.toString();
   }
}
