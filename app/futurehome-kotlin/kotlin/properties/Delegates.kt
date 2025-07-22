package kotlin.properties

import kotlin.jvm.functions.Function3
import kotlin.reflect.KProperty

public object Delegates {
   public fun <T : Any> notNull(): ReadWriteProperty<Any?, T> {
      return new NotNullVar();
   }

   public inline fun <T> observable(initialValue: T, crossinline onChange: (KProperty<*>, T, T) -> Unit): ReadWriteProperty<Any?, T> {
      return (new ObservableProperty<T>(var1, var2) {
         final Function3<KProperty<?>, T, T, Unit> $onChange;

         {
            super(var1);
            this.$onChange = var2;
         }

         @Override
         protected void afterChange(KProperty<?> var1, T var2, T var3) {
            this.$onChange.invoke(var1, var2, var3);
         }
      }) as ReadWriteProperty<Object, T>;
   }

   public inline fun <T> vetoable(initialValue: T, crossinline onChange: (KProperty<*>, T, T) -> Boolean): ReadWriteProperty<Any?, T> {
      return (new ObservableProperty<T>(var1, var2) {
         final Function3<KProperty<?>, T, T, java.lang.Boolean> $onChange;

         {
            super(var1);
            this.$onChange = var2;
         }

         @Override
         protected boolean beforeChange(KProperty<?> var1, T var2, T var3) {
            return this.$onChange.invoke(var1, var2, var3);
         }
      }) as ReadWriteProperty<Object, T>;
   }
}
