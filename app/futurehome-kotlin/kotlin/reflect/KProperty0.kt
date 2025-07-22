package kotlin.reflect

import kotlin.jvm.functions.Function0

public interface KProperty0<V> : KProperty<V>, Function0<V> {
   public val getter: KProperty0.Getter<Any>

   public abstract fun get(): Any {
   }

   public abstract fun getDelegate(): Any? {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls

   public interface Getter<V> : KProperty.Getter<V>, Function0<V>
}
