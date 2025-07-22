package kotlin.reflect

import kotlin.jvm.functions.Function1

public interface KMutableProperty0<V> : KProperty0<V>, KMutableProperty<V> {
   public val setter: KMutableProperty0.Setter<Any>

   public abstract fun set(value: Any) {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls

   public interface Setter<V> : KMutableProperty.Setter<V>, Function1<V, Unit>
}
