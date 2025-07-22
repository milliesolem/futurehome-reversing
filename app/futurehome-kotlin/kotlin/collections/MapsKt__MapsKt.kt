package kotlin.collections

import java.util.HashMap
import java.util.LinkedHashMap
import kotlin.collections.Map.Entry
import kotlin.collections.MutableMap.MutableEntry
import kotlin.contracts.InvocationKind
import kotlin.jvm.internal.TypeIntrinsics

internal class MapsKt__MapsKt : MapsKt__MapsJVMKt {
   open fun MapsKt__MapsKt() {
   }

   @JvmStatic
   public inline fun <K, V> buildMap(capacity: Int, builderAction: (MutableMap<K, V>) -> Unit): Map<K, V> {
      contract {
         callsInPlace(builderAction, InvocationKind.EXACTLY_ONCE)
      }

      val var2: java.util.Map = MapsKt.createMapBuilder(var0);
      var1.invoke(var2);
      return MapsKt.build(var2);
   }

   @JvmStatic
   public inline fun <K, V> buildMap(builderAction: (MutableMap<K, V>) -> Unit): Map<K, V> {
      contract {
         callsInPlace(builderAction, InvocationKind.EXACTLY_ONCE)
      }

      val var1: java.util.Map = MapsKt.createMapBuilder();
      var0.invoke(var1);
      return MapsKt.build(var1);
   }

   @JvmStatic
   public inline operator fun <K, V> Entry<K, V>.component1(): K {
      return (K)var0.getKey();
   }

   @JvmStatic
   public inline operator fun <K, V> Entry<K, V>.component2(): V {
      return (V)var0.getValue();
   }

   @JvmStatic
   public inline operator fun <K, V> Map<out K, V>.contains(key: K): Boolean {
      return var0.containsKey(var1);
   }

   @JvmStatic
   public inline fun <K> Map<out K, *>.containsKey(key: K): Boolean {
      return var0.containsKey(var1);
   }

   @JvmStatic
   public inline fun <K, V> Map<K, V>.containsValue(value: V): Boolean {
      return var0.containsValue(var1);
   }

   @JvmStatic
   public fun <K, V> emptyMap(): Map<K, V> {
      val var0: EmptyMap = EmptyMap.INSTANCE;
      return var0;
   }

   @JvmStatic
   public inline fun <K, V> Map<out K, V>.filter(predicate: (Entry<K, V>) -> Boolean): Map<K, V> {
      val var2: java.util.Map = new LinkedHashMap();

      for (java.util.Map.Entry var3 : var0.entrySet()) {
         if (var1.invoke(var3) as java.lang.Boolean) {
            var2.put(var3.getKey(), var3.getValue());
         }
      }

      return var2;
   }

   @JvmStatic
   public inline fun <K, V> Map<out K, V>.filterKeys(predicate: (K) -> Boolean): Map<K, V> {
      val var2: LinkedHashMap = new LinkedHashMap();

      for (java.util.Map.Entry var3 : var0.entrySet()) {
         if (var1.invoke(var3.getKey()) as java.lang.Boolean) {
            var2.put(var3.getKey(), var3.getValue());
         }
      }

      return var2;
   }

   @JvmStatic
   public inline fun <K, V> Map<out K, V>.filterNot(predicate: (Entry<K, V>) -> Boolean): Map<K, V> {
      val var2: java.util.Map = new LinkedHashMap();

      for (java.util.Map.Entry var3 : var0.entrySet()) {
         if (!var1.invoke(var3) as java.lang.Boolean) {
            var2.put(var3.getKey(), var3.getValue());
         }
      }

      return var2;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, in V>> Map<out K, V>.filterNotTo(destination: M, predicate: (Entry<K, V>) -> Boolean): M {
      for (java.util.Map.Entry var4 : var0.entrySet()) {
         if (!var2.invoke(var4) as java.lang.Boolean) {
            var1.put(var4.getKey(), var4.getValue());
         }
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, in V>> Map<out K, V>.filterTo(destination: M, predicate: (Entry<K, V>) -> Boolean): M {
      for (java.util.Map.Entry var4 : var0.entrySet()) {
         if (var2.invoke(var4) as java.lang.Boolean) {
            var1.put(var4.getKey(), var4.getValue());
         }
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V> Map<out K, V>.filterValues(predicate: (V) -> Boolean): Map<K, V> {
      val var2: LinkedHashMap = new LinkedHashMap();

      for (java.util.Map.Entry var4 : var0.entrySet()) {
         if (var1.invoke(var4.getValue()) as java.lang.Boolean) {
            var2.put(var4.getKey(), var4.getValue());
         }
      }

      return var2;
   }

   @JvmStatic
   public inline operator fun <K, V> Map<out K, V>.get(key: K): V? {
      return (V)var0.get(var1);
   }

   @JvmStatic
   public inline fun <K, V> Map<K, V>.getOrElse(key: K, defaultValue: () -> V): V {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      var1 = var0.get(var1);
      var var3: Any = var1;
      if (var1 == null) {
         var3 = var2.invoke();
      }

      return (V)var3;
   }

   @JvmStatic
   internal inline fun <K, V> Map<K, V>.getOrElseNullable(key: K, defaultValue: () -> V): V {
      val var3: Any = var0.get(var1);
      return (V)(if (var3 == null && !var0.containsKey(var1)) var2.invoke() else var3);
   }

   @JvmStatic
   public inline fun <K, V> MutableMap<K, V>.getOrPut(key: K, defaultValue: () -> V): V {
      val var4: Any = var0.get(var1);
      var var3: Any = var4;
      if (var4 == null) {
         var3 = var2.invoke();
         var0.put(var1, var3);
      }

      return (V)var3;
   }

   @JvmStatic
   public fun <K, V> Map<K, V>.getValue(key: K): V {
      return (V)MapsKt.getOrImplicitDefaultNullable(var0, var1);
   }

   @JvmStatic
   public inline fun <K, V> hashMapOf(): HashMap<K, V> {
      return new HashMap();
   }

   @JvmStatic
   public fun <K, V> hashMapOf(vararg pairs: Pair<K, V>): HashMap<K, V> {
      val var1: HashMap = new HashMap(MapsKt.mapCapacity(var0.length));
      MapsKt.putAll(var1, var0);
      return var1;
   }

   @JvmStatic
   public inline fun <M, R> M.ifEmpty(defaultValue: () -> R): R where M : Map<*, *>, M : R {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      var var2: Any = var0;
      if (var0.isEmpty()) {
         var2 = var1.invoke();
      }

      return (R)var2;
   }

   @JvmStatic
   public inline fun <K, V> Map<out K, V>.isNotEmpty(): Boolean {
      return var0.isEmpty() xor true;
   }

   @JvmStatic
   public inline fun <K, V> Map<out K, V>?.isNullOrEmpty(): Boolean {
      contract {
         returns(false) implies (this != null)
      }

      val var1: Boolean;
      if (var0 != null && !var0.isEmpty()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   @JvmStatic
   public inline operator fun <K, V> Map<out K, V>.iterator(): Iterator<Entry<K, V>> {
      return var0.entrySet().iterator();
   }

   @JvmStatic
   public inline fun <K, V> linkedMapOf(): LinkedHashMap<K, V> {
      return new LinkedHashMap();
   }

   @JvmStatic
   public fun <K, V> linkedMapOf(vararg pairs: Pair<K, V>): LinkedHashMap<K, V> {
      return MapsKt.toMap(var0, new LinkedHashMap(MapsKt.mapCapacity(var0.length))) as LinkedHashMap<K, V>;
   }

   @JvmStatic
   public inline fun <K, V, R> Map<out K, V>.mapKeys(transform: (Entry<K, V>) -> R): Map<R, V> {
      val var2: java.util.Map = new LinkedHashMap(MapsKt.mapCapacity(var0.size()));

      for (Object var4 : var0.entrySet()) {
         var2.put(var1.invoke(var4), (var4 as java.util.Map.Entry).getValue());
      }

      return var2;
   }

   @JvmStatic
   public inline fun <K, V, R, M : MutableMap<in R, in V>> Map<out K, V>.mapKeysTo(destination: M, transform: (Entry<K, V>) -> R): M {
      for (Object var3 : var0.entrySet()) {
         var1.put(var2.invoke(var3), (var3 as java.util.Map.Entry).getValue());
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V> mapOf(): Map<K, V> {
      return MapsKt.emptyMap();
   }

   @JvmStatic
   public fun <K, V> mapOf(vararg pairs: Pair<K, V>): Map<K, V> {
      val var1: java.util.Map;
      if (var0.length > 0) {
         var1 = MapsKt.toMap(var0, new LinkedHashMap(MapsKt.mapCapacity(var0.length)));
      } else {
         var1 = MapsKt.emptyMap();
      }

      return var1;
   }

   @JvmStatic
   public inline fun <K, V, R> Map<out K, V>.mapValues(transform: (Entry<K, V>) -> R): Map<K, R> {
      val var2: java.util.Map = new LinkedHashMap(MapsKt.mapCapacity(var0.size()));

      for (Object var3 : var0.entrySet()) {
         var2.put((var3 as java.util.Map.Entry).getKey(), var1.invoke(var3));
      }

      return var2;
   }

   @JvmStatic
   public inline fun <K, V, R, M : MutableMap<in K, in R>> Map<out K, V>.mapValuesTo(destination: M, transform: (Entry<K, V>) -> R): M {
      for (Object var4 : var0.entrySet()) {
         var1.put((var4 as java.util.Map.Entry).getKey(), var2.invoke(var4));
      }

      return (M)var1;
   }

   @JvmStatic
   public operator fun <K, V> Map<out K, V>.minus(keys: Iterable<K>): Map<K, V> {
      var0 = MapsKt.toMutableMap(var0);
      CollectionsKt.removeAll(var0.keySet(), var1);
      return MapsKt.optimizeReadOnlyMap(var0);
   }

   @JvmStatic
   public operator fun <K, V> Map<out K, V>.minus(key: K): Map<K, V> {
      var0 = MapsKt.toMutableMap(var0);
      var0.remove(var1);
      return MapsKt.optimizeReadOnlyMap(var0);
   }

   @JvmStatic
   public operator fun <K, V> Map<out K, V>.minus(keys: Sequence<K>): Map<K, V> {
      var0 = MapsKt.toMutableMap(var0);
      CollectionsKt.removeAll(var0.keySet(), var1);
      return MapsKt.optimizeReadOnlyMap(var0);
   }

   @JvmStatic
   public operator fun <K, V> Map<out K, V>.minus(keys: Array<out K>): Map<K, V> {
      var0 = MapsKt.toMutableMap(var0);
      CollectionsKt.removeAll(var0.keySet(), var1);
      return MapsKt.optimizeReadOnlyMap(var0);
   }

   @JvmStatic
   public inline operator fun <K, V> MutableMap<K, V>.minusAssign(keys: Iterable<K>) {
      CollectionsKt.removeAll(var0.keySet(), var1);
   }

   @JvmStatic
   public inline operator fun <K, V> MutableMap<K, V>.minusAssign(key: K) {
      var0.remove(var1);
   }

   @JvmStatic
   public inline operator fun <K, V> MutableMap<K, V>.minusAssign(keys: Sequence<K>) {
      CollectionsKt.removeAll(var0.keySet(), var1);
   }

   @JvmStatic
   public inline operator fun <K, V> MutableMap<K, V>.minusAssign(keys: Array<out K>) {
      CollectionsKt.removeAll(var0.keySet(), var1);
   }

   @JvmStatic
   public inline operator fun <K, V> MutableMap<K, V>.iterator(): MutableIterator<MutableEntry<K, V>> {
      return var0.entrySet().iterator();
   }

   @JvmStatic
   public inline fun <K, V> mutableMapOf(): MutableMap<K, V> {
      return new LinkedHashMap();
   }

   @JvmStatic
   public fun <K, V> mutableMapOf(vararg pairs: Pair<K, V>): MutableMap<K, V> {
      val var1: java.util.Map = new LinkedHashMap(MapsKt.mapCapacity(var0.length));
      MapsKt.putAll(var1, var0);
      return var1;
   }

   @JvmStatic
   internal fun <K, V> Map<K, V>.optimizeReadOnlyMap(): Map<K, V> {
      val var1: Int = var0.size();
      if (var1 != 0) {
         if (var1 == 1) {
            var0 = MapsKt.toSingletonMap(var0);
         }
      } else {
         var0 = MapsKt.emptyMap();
      }

      return var0;
   }

   @JvmStatic
   public inline fun <K, V> Map<K, V>?.orEmpty(): Map<K, V> {
      var var1: java.util.Map = var0;
      if (var0 == null) {
         var1 = MapsKt.emptyMap();
      }

      return var1;
   }

   @JvmStatic
   public operator fun <K, V> Map<out K, V>.plus(pairs: Iterable<Pair<K, V>>): Map<K, V> {
      if (var0.isEmpty()) {
         var0 = MapsKt.toMap(var1);
      } else {
         var0 = new LinkedHashMap(var0);
         MapsKt.putAll(var0, var1);
      }

      return var0;
   }

   @JvmStatic
   public operator fun <K, V> Map<out K, V>.plus(map: Map<out K, V>): Map<K, V> {
      val var2: LinkedHashMap = new LinkedHashMap(var0);
      var2.putAll(var1);
      return var2;
   }

   @JvmStatic
   public operator fun <K, V> Map<out K, V>.plus(pair: Pair<K, V>): Map<K, V> {
      if (var0.isEmpty()) {
         var0 = MapsKt.mapOf(var1);
      } else {
         val var3: LinkedHashMap = new LinkedHashMap(var0);
         var3.put(var1.getFirst(), var1.getSecond());
         var0 = var3;
      }

      return var0;
   }

   @JvmStatic
   public operator fun <K, V> Map<out K, V>.plus(pairs: Sequence<Pair<K, V>>): Map<K, V> {
      var0 = new LinkedHashMap(var0);
      MapsKt.putAll(var0, var1);
      return MapsKt.optimizeReadOnlyMap(var0);
   }

   @JvmStatic
   public operator fun <K, V> Map<out K, V>.plus(pairs: Array<out Pair<K, V>>): Map<K, V> {
      if (var0.isEmpty()) {
         var0 = MapsKt.toMap(var1);
      } else {
         var0 = new LinkedHashMap(var0);
         MapsKt.putAll(var0, var1);
      }

      return var0;
   }

   @JvmStatic
   public inline operator fun <K, V> MutableMap<in K, in V>.plusAssign(pairs: Iterable<Pair<K, V>>) {
      MapsKt.putAll(var0, var1);
   }

   @JvmStatic
   public inline operator fun <K, V> MutableMap<in K, in V>.plusAssign(map: Map<K, V>) {
      var0.putAll(var1);
   }

   @JvmStatic
   public inline operator fun <K, V> MutableMap<in K, in V>.plusAssign(pair: Pair<K, V>) {
      var0.put(var1.getFirst(), var1.getSecond());
   }

   @JvmStatic
   public inline operator fun <K, V> MutableMap<in K, in V>.plusAssign(pairs: Sequence<Pair<K, V>>) {
      MapsKt.putAll(var0, var1);
   }

   @JvmStatic
   public inline operator fun <K, V> MutableMap<in K, in V>.plusAssign(pairs: Array<out Pair<K, V>>) {
      MapsKt.putAll(var0, var1);
   }

   @JvmStatic
   public fun <K, V> MutableMap<in K, in V>.putAll(pairs: Iterable<Pair<K, V>>) {
      for (Pair var2 : var1) {
         var0.put(var2.component1(), var2.component2());
      }
   }

   @JvmStatic
   public fun <K, V> MutableMap<in K, in V>.putAll(pairs: Sequence<Pair<K, V>>) {
      for (Pair var2 : var1) {
         var0.put(var2.component1(), var2.component2());
      }
   }

   @JvmStatic
   public fun <K, V> MutableMap<in K, in V>.putAll(pairs: Array<out Pair<K, V>>) {
      val var3: Int = var1.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var0.put(var1[var2].component1(), var1[var2].component2());
      }
   }

   @JvmStatic
   public inline fun <K, V> MutableMap<out K, V>.remove(key: K): V? {
      return (V)TypeIntrinsics.asMutableMap(var0).remove(var1);
   }

   @JvmStatic
   public inline operator fun <K, V> MutableMap<K, V>.set(key: K, value: V) {
      var0.put(var1, var2);
   }

   @JvmStatic
   public fun <K, V> Iterable<Pair<K, V>>.toMap(): Map<K, V> {
      if (var0 is java.util.Collection) {
         val var2: java.util.Collection = var0 as java.util.Collection;
         val var1: Int = (var0 as java.util.Collection).size();
         val var3: java.util.Map;
         if (var1 != 0) {
            if (var1 != 1) {
               var3 = MapsKt.toMap(var0, new LinkedHashMap(MapsKt.mapCapacity(var2.size())));
            } else {
               val var4: Any;
               if (var0 is java.util.List) {
                  var4 = (var0 as java.util.List).get(0);
               } else {
                  var4 = var2.iterator().next();
               }

               var3 = MapsKt.mapOf(var4 as Pair);
            }
         } else {
            var3 = MapsKt.emptyMap();
         }

         return var3;
      } else {
         return MapsKt.optimizeReadOnlyMap(MapsKt.toMap(var0, new LinkedHashMap()));
      }
   }

   @JvmStatic
   public fun <K, V, M : MutableMap<in K, in V>> Iterable<Pair<K, V>>.toMap(destination: M): M {
      MapsKt.putAll(var1, var0);
      return (M)var1;
   }

   @JvmStatic
   public fun <K, V> Map<out K, V>.toMap(): Map<K, V> {
      val var1: Int = var0.size();
      if (var1 != 0) {
         if (var1 != 1) {
            var0 = MapsKt.toMutableMap(var0);
         } else {
            var0 = MapsKt.toSingletonMap(var0);
         }
      } else {
         var0 = MapsKt.emptyMap();
      }

      return var0;
   }

   @JvmStatic
   public fun <K, V, M : MutableMap<in K, in V>> Map<out K, V>.toMap(destination: M): M {
      var1.putAll(var0);
      return (M)var1;
   }

   @JvmStatic
   public fun <K, V> Sequence<Pair<K, V>>.toMap(): Map<K, V> {
      return MapsKt.optimizeReadOnlyMap(MapsKt.toMap(var0, new LinkedHashMap()));
   }

   @JvmStatic
   public fun <K, V, M : MutableMap<in K, in V>> Sequence<Pair<K, V>>.toMap(destination: M): M {
      MapsKt.putAll(var1, var0);
      return (M)var1;
   }

   @JvmStatic
   public fun <K, V> Array<out Pair<K, V>>.toMap(): Map<K, V> {
      val var1: Int = var0.length;
      val var2: java.util.Map;
      if (var0.length != 0) {
         if (var1 != 1) {
            var2 = MapsKt.toMap(var0, new LinkedHashMap(MapsKt.mapCapacity(var0.length)));
         } else {
            var2 = MapsKt.mapOf(var0[0]);
         }
      } else {
         var2 = MapsKt.emptyMap();
      }

      return var2;
   }

   @JvmStatic
   public fun <K, V, M : MutableMap<in K, in V>> Array<out Pair<K, V>>.toMap(destination: M): M {
      MapsKt.putAll(var1, var0);
      return (M)var1;
   }

   @JvmStatic
   public fun <K, V> Map<out K, V>.toMutableMap(): MutableMap<K, V> {
      return new LinkedHashMap(var0);
   }

   @JvmStatic
   public inline fun <K, V> Entry<K, V>.toPair(): Pair<K, V> {
      return (Pair<K, V>)(new Pair<>(var0.getKey(), var0.getValue()));
   }
}
