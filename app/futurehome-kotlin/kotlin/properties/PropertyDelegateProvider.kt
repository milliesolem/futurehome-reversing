package kotlin.properties

import kotlin.reflect.KProperty

public fun interface PropertyDelegateProvider<T, D> {
   public abstract operator fun provideDelegate(thisRef: Any, property: KProperty<*>): Any {
   }
}
