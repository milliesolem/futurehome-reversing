package kotlin.reflect

public interface KMutableProperty<V> : KProperty<V> {
   public val setter: kotlin.reflect.KMutableProperty.Setter<Any>

   public interface Setter<V> : KProperty.Accessor<V>, KFunction<Unit>
}
