package kotlin.collections

import java.util.NoSuchElementException

internal class MapsKt__MapWithDefaultKt {
   open fun MapsKt__MapWithDefaultKt() {
   }

   @JvmStatic
   internal fun <K, V> Map<K, V>.getOrImplicitDefault(key: K): V {
      if (var0 is MapWithDefault) {
         return (V)(var0 as MapWithDefault).getOrImplicitDefault(var1);
      } else {
         val var2: Any = var0.get(var1);
         if (var2 == null && !var0.containsKey(var1)) {
            val var3: StringBuilder = new StringBuilder("Key ");
            var3.append(var1);
            var3.append(" is missing in the map.");
            throw new NoSuchElementException(var3.toString());
         } else {
            return (V)var2;
         }
      }
   }

   @JvmStatic
   public fun <K, V> Map<K, V>.withDefault(defaultValue: (K) -> V): Map<K, V> {
      if (var0 is MapWithDefault) {
         var0 = MapsKt.withDefault((var0 as MapWithDefault).getMap(), var1);
      } else {
         var0 = new MapWithDefaultImpl(var0, var1);
      }

      return var0;
   }

   @JvmStatic
   public fun <K, V> MutableMap<K, V>.withDefault(defaultValue: (K) -> V): MutableMap<K, V> {
      if (var0 is MutableMapWithDefault) {
         var0 = MapsKt.withDefaultMutable((var0 as MutableMapWithDefault).getMap(), var1);
      } else {
         var0 = new MutableMapWithDefaultImpl(var0, var1);
      }

      return var0;
   }
}
