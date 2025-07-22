package kotlin.collections

import java.util.Collections
import java.util.Comparator
import java.util.Properties
import java.util.SortedMap
import java.util.TreeMap
import java.util.Map.Entry
import java.util.concurrent.ConcurrentMap
import kotlin.collections.builders.MapBuilder

internal class MapsKt__MapsJVMKt : MapsKt__MapWithDefaultKt {
   private const val INT_MAX_POWER_OF_TWO: Int = 1073741824

   open fun MapsKt__MapsJVMKt() {
   }

   @JvmStatic
   internal fun <K, V> build(builder: MutableMap<K, V>): Map<K, V> {
      return (var0 as MapBuilder).build();
   }

   @JvmStatic
   internal inline fun <K, V> buildMapInternal(capacity: Int, builderAction: (MutableMap<K, V>) -> Unit): Map<K, V> {
      val var2: java.util.Map = MapsKt.createMapBuilder(var0);
      var1.invoke(var2);
      return MapsKt.build(var2);
   }

   @JvmStatic
   internal inline fun <K, V> buildMapInternal(builderAction: (MutableMap<K, V>) -> Unit): Map<K, V> {
      val var1: java.util.Map = MapsKt.createMapBuilder();
      var0.invoke(var1);
      return MapsKt.build(var1);
   }

   @JvmStatic
   internal fun <K, V> createMapBuilder(): MutableMap<K, V> {
      return new MapBuilder();
   }

   @JvmStatic
   internal fun <K, V> createMapBuilder(capacity: Int): MutableMap<K, V> {
      return new MapBuilder(var0);
   }

   @JvmStatic
   public inline fun <K, V> ConcurrentMap<K, V>.getOrPut(key: K, defaultValue: () -> V): V {
      val var4: Any = var0.get(var1);
      var var3: Any = var4;
      if (var4 == null) {
         var3 = var2.invoke();
         val var5: Any = var0.putIfAbsent(var1, var3);
         if (var5 != null) {
            var3 = var5;
         }
      }

      return (V)var3;
   }

   @JvmStatic
   internal fun mapCapacity(expectedSize: Int): Int {
      if (var0 >= 0) {
         if (var0 < 3) {
            var0++;
         } else if (var0 < 1073741824) {
            var0 = (int)(var0 / 0.75F + 1.0F);
         } else {
            var0 = Integer.MAX_VALUE;
         }
      }

      return var0;
   }

   @JvmStatic
   public fun <K, V> mapOf(pair: Pair<K, V>): Map<K, V> {
      val var1: java.util.Map = Collections.singletonMap(var0.getFirst(), var0.getSecond());
      return var1;
   }

   @JvmStatic
   public fun <K, V> sortedMapOf(comparator: Comparator<in K>, vararg pairs: Pair<K, V>): SortedMap<K, V> {
      val var2: TreeMap = new TreeMap(var0);
      MapsKt.putAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public fun <K : Comparable<K>, V> sortedMapOf(vararg pairs: Pair<K, V>): SortedMap<K, V> {
      val var1: TreeMap = new TreeMap();
      MapsKt.putAll(var1, var0);
      return var1;
   }

   @JvmStatic
   public inline fun Map<String, String>.toProperties(): Properties {
      val var1: Properties = new Properties();
      var1.putAll(var0);
      return var1;
   }

   @JvmStatic
   internal fun <K, V> Map<out K, V>.toSingletonMap(): Map<K, V> {
      val var1: Entry = var0.entrySet().iterator().next() as Entry;
      var0 = Collections.singletonMap(var1.getKey(), var1.getValue());
      return var0;
   }

   @JvmStatic
   internal inline fun <K, V> Map<K, V>.toSingletonMapOrSelf(): Map<K, V> {
      return MapsKt.toSingletonMap(var0);
   }

   @JvmStatic
   public fun <K : Comparable<K>, V> Map<out K, V>.toSortedMap(): SortedMap<K, V> {
      return new TreeMap(var0);
   }

   @JvmStatic
   public fun <K, V> Map<out K, V>.toSortedMap(comparator: Comparator<in K>): SortedMap<K, V> {
      val var2: TreeMap = new TreeMap(var1);
      var2.putAll(var0);
      return var2;
   }
}
