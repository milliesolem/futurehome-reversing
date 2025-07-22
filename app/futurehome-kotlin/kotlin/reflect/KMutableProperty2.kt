package kotlin.reflect

import kotlin.jvm.functions.Function3

public interface KMutableProperty2<D, E, V> : KProperty2<D, E, V>, KMutableProperty<V> {
   public val setter: KMutableProperty2.Setter<Any, Any, Any>

   public abstract fun set(receiver1: Any, receiver2: Any, value: Any) {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls

   public interface Setter<D, E, V> : KMutableProperty.Setter<V>, Function3<D, E, V, Unit>
}
