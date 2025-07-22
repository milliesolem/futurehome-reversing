package kotlin.reflect

import kotlin.jvm.functions.Function1

public interface KProperty1<T, V> : KProperty<V>, Function1<T, V> {
   public val getter: KProperty1.Getter<Any, Any>

   public abstract fun get(receiver: Any): Any {
   }

   public abstract fun getDelegate(receiver: Any): Any? {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls

   public interface Getter<T, V> : KProperty.Getter<V>, Function1<T, V>
}
