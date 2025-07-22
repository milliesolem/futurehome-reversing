package kotlin.reflect

public interface KProperty<V> : KCallable<V> {
   public val isLateinit: Boolean
   public val isConst: Boolean
   public val getter: kotlin.reflect.KProperty.Getter<Any>

   public interface Accessor<V> {
      public val property: KProperty<Any>
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls

   public interface Getter<V> : KProperty.Accessor<V>, KFunction<V>
}
