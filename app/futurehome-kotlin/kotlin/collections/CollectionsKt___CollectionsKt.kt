package kotlin.collections

import java.util.ArrayList
import java.util.Comparator
import java.util.HashSet
import java.util.LinkedHashMap
import java.util.LinkedHashSet
import java.util.NoSuchElementException
import java.util.RandomAccess
import kotlin.contracts.InvocationKind
import kotlin.internal.PlatformImplementationsKt
import kotlin.jvm.functions.Function1
import kotlin.random.Random

internal class CollectionsKt___CollectionsKt : CollectionsKt___CollectionsJvmKt {
   open fun CollectionsKt___CollectionsKt() {
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.all(predicate: (T) -> Boolean): Boolean {
      if (var0 is java.util.Collection && (var0 as java.util.Collection).isEmpty()) {
         return true;
      } else {
         val var2: java.util.Iterator = var0.iterator();

         while (var2.hasNext()) {
            if (!var1.invoke(var2.next()) as java.lang.Boolean) {
               return false;
            }
         }

         return true;
      }
   }

   @JvmStatic
   public fun <T> Iterable<T>.any(): Boolean {
      return if (var0 is java.util.Collection) (var0 as java.util.Collection).isEmpty() xor true else var0.iterator().hasNext();
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.any(predicate: (T) -> Boolean): Boolean {
      if (var0 is java.util.Collection && (var0 as java.util.Collection).isEmpty()) {
         return false;
      } else {
         val var2: java.util.Iterator = var0.iterator();

         while (var2.hasNext()) {
            if (var1.invoke(var2.next()) as java.lang.Boolean) {
               return true;
            }
         }

         return false;
      }
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.asIterable(): Iterable<T> {
      return var0;
   }

   @JvmStatic
   public fun <T> Iterable<T>.asSequence(): Sequence<T> {
      return new Sequence<T>(var0) {
         final java.lang.Iterable $this_asSequence$inlined;

         {
            this.$this_asSequence$inlined = var1;
         }

         @Override
         public java.util.Iterator<T> iterator() {
            return this.$this_asSequence$inlined.iterator();
         }
      };
   }

   @JvmStatic
   public inline fun <T, K, V> Iterable<T>.associate(transform: (T) -> Pair<K, V>): Map<K, V> {
      val var2: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(var0, 10)), 16));
      val var4: java.util.Iterator = var0.iterator();

      while (var4.hasNext()) {
         val var3: Pair = var1.invoke(var4.next()) as Pair;
         var2.put(var3.getFirst(), var3.getSecond());
      }

      return var2;
   }

   @JvmStatic
   public inline fun <T, K> Iterable<T>.associateBy(keySelector: (T) -> K): Map<K, T> {
      val var2: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(var0, 10)), 16));

      for (Object var3 : var0) {
         var2.put(var1.invoke(var3), var3);
      }

      return var2;
   }

   @JvmStatic
   public inline fun <T, K, V> Iterable<T>.associateBy(keySelector: (T) -> K, valueTransform: (T) -> V): Map<K, V> {
      val var3: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(var0, 10)), 16));

      for (Object var4 : var0) {
         var3.put(var1.invoke(var4), var2.invoke(var4));
      }

      return var3;
   }

   @JvmStatic
   public inline fun <T, K, M : MutableMap<in K, in T>> Iterable<T>.associateByTo(destination: M, keySelector: (T) -> K): M {
      for (Object var4 : var0) {
         var1.put(var2.invoke(var4), var4);
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <T, K, V, M : MutableMap<in K, in V>> Iterable<T>.associateByTo(destination: M, keySelector: (T) -> K, valueTransform: (T) -> V): M {
      for (Object var5 : var0) {
         var1.put(var2.invoke(var5), var3.invoke(var5));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <T, K, V, M : MutableMap<in K, in V>> Iterable<T>.associateTo(destination: M, transform: (T) -> Pair<K, V>): M {
      val var4: java.util.Iterator = var0.iterator();

      while (var4.hasNext()) {
         val var3: Pair = var2.invoke(var4.next()) as Pair;
         var1.put(var3.getFirst(), var3.getSecond());
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V> Iterable<K>.associateWith(valueSelector: (K) -> V): Map<K, V> {
      val var2: LinkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(var0, 10)), 16));

      for (Object var4 : var0) {
         var2.put(var4, var1.invoke(var4));
      }

      return var2;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, in V>> Iterable<K>.associateWithTo(destination: M, valueSelector: (K) -> V): M {
      for (Object var4 : var0) {
         var1.put(var4, var2.invoke(var4));
      }

      return (M)var1;
   }

   @JvmStatic
   public fun Iterable<Byte>.average(): Double {
      val var7: java.util.Iterator = var0.iterator();
      var var1: Double = 0.0;
      var var5: Int = 0;

      while (var7.hasNext()) {
         val var3: Double = var1 + (var7.next() as java.lang.Number).byteValue();
         val var6: Int = var5 + 1;
         var1 = var3;
         var5 = var6;
         if (var6 < 0) {
            CollectionsKt.throwCountOverflow();
            var1 = var3;
            var5 = var6;
         }
      }

      if (var5 == 0) {
         var1 = java.lang.Double.NaN;
      } else {
         var1 = var1 / var5;
      }

      return var1;
   }

   @JvmStatic
   public fun Iterable<Double>.average(): Double {
      val var7: java.util.Iterator = var0.iterator();
      var var1: Double = 0.0;
      var var5: Int = 0;

      while (var7.hasNext()) {
         val var3: Double = var1 + (var7.next() as java.lang.Number).doubleValue();
         val var6: Int = var5 + 1;
         var1 = var3;
         var5 = var6;
         if (var6 < 0) {
            CollectionsKt.throwCountOverflow();
            var1 = var3;
            var5 = var6;
         }
      }

      if (var5 == 0) {
         var1 = java.lang.Double.NaN;
      } else {
         var1 = var1 / var5;
      }

      return var1;
   }

   @JvmStatic
   public fun Iterable<Float>.average(): Double {
      val var7: java.util.Iterator = var0.iterator();
      var var1: Double = 0.0;
      var var5: Int = 0;

      while (var7.hasNext()) {
         val var3: Double = var1 + (var7.next() as java.lang.Number).floatValue();
         val var6: Int = var5 + 1;
         var1 = var3;
         var5 = var6;
         if (var6 < 0) {
            CollectionsKt.throwCountOverflow();
            var1 = var3;
            var5 = var6;
         }
      }

      if (var5 == 0) {
         var1 = java.lang.Double.NaN;
      } else {
         var1 = var1 / var5;
      }

      return var1;
   }

   @JvmStatic
   public fun Iterable<Int>.average(): Double {
      val var7: java.util.Iterator = var0.iterator();
      var var1: Double = 0.0;
      var var5: Int = 0;

      while (var7.hasNext()) {
         val var3: Double = var1 + (var7.next() as java.lang.Number).intValue();
         val var6: Int = var5 + 1;
         var1 = var3;
         var5 = var6;
         if (var6 < 0) {
            CollectionsKt.throwCountOverflow();
            var1 = var3;
            var5 = var6;
         }
      }

      if (var5 == 0) {
         var1 = java.lang.Double.NaN;
      } else {
         var1 = var1 / var5;
      }

      return var1;
   }

   @JvmStatic
   public fun Iterable<Long>.average(): Double {
      val var7: java.util.Iterator = var0.iterator();
      var var1: Double = 0.0;
      var var5: Int = 0;

      while (var7.hasNext()) {
         val var3: Double = var1 + (var7.next() as java.lang.Number).longValue();
         val var6: Int = var5 + 1;
         var1 = var3;
         var5 = var6;
         if (var6 < 0) {
            CollectionsKt.throwCountOverflow();
            var1 = var3;
            var5 = var6;
         }
      }

      if (var5 == 0) {
         var1 = java.lang.Double.NaN;
      } else {
         var1 = var1 / var5;
      }

      return var1;
   }

   @JvmStatic
   public fun Iterable<Short>.average(): Double {
      val var7: java.util.Iterator = var0.iterator();
      var var1: Double = 0.0;
      var var5: Int = 0;

      while (var7.hasNext()) {
         val var3: Double = var1 + (var7.next() as java.lang.Number).shortValue();
         val var6: Int = var5 + 1;
         var1 = var3;
         var5 = var6;
         if (var6 < 0) {
            CollectionsKt.throwCountOverflow();
            var1 = var3;
            var5 = var6;
         }
      }

      if (var5 == 0) {
         var1 = java.lang.Double.NaN;
      } else {
         var1 = var1 / var5;
      }

      return var1;
   }

   @JvmStatic
   public fun <T> Iterable<T>.chunked(size: Int): List<List<T>> {
      return CollectionsKt.windowed(var0, var1, var1, true);
   }

   @JvmStatic
   public fun <T, R> Iterable<T>.chunked(size: Int, transform: (List<T>) -> R): List<R> {
      return CollectionsKt.windowed(var0, var1, var1, true, var2);
   }

   @JvmStatic
   public inline operator fun <T> List<T>.component1(): T {
      return (T)var0.get(0);
   }

   @JvmStatic
   public inline operator fun <T> List<T>.component2(): T {
      return (T)var0.get(1);
   }

   @JvmStatic
   public inline operator fun <T> List<T>.component3(): T {
      return (T)var0.get(2);
   }

   @JvmStatic
   public inline operator fun <T> List<T>.component4(): T {
      return (T)var0.get(3);
   }

   @JvmStatic
   public inline operator fun <T> List<T>.component5(): T {
      return (T)var0.get(4);
   }

   @JvmStatic
   public operator fun <T> Iterable<T>.contains(element: T): Boolean {
      if (var0 is java.util.Collection) {
         return (var0 as java.util.Collection).contains(var1);
      } else {
         val var2: Boolean;
         if (CollectionsKt.indexOf(var0, var1) >= 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }
   }

   @JvmStatic
   public fun <T> Iterable<T>.count(): Int {
      if (var0 is java.util.Collection) {
         return (var0 as java.util.Collection).size();
      } else {
         val var3: java.util.Iterator = var0.iterator();
         var var1: Int = 0;

         while (var3.hasNext()) {
            var3.next();
            val var2: Int = var1 + 1;
            var1 += 1;
            if (var2 < 0) {
               CollectionsKt.throwCountOverflow();
               var1 = var2;
            }
         }

         return var1;
      }
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.count(predicate: (T) -> Boolean): Int {
      if (var0 is java.util.Collection && (var0 as java.util.Collection).isEmpty()) {
         return 0;
      } else {
         val var4: java.util.Iterator = var0.iterator();
         var var2: Int = 0;

         while (var4.hasNext()) {
            if (var1.invoke(var4.next()) as java.lang.Boolean) {
               val var3: Int = var2 + 1;
               var2 += 1;
               if (var3 < 0) {
                  if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
                     throw new ArithmeticException("Count overflow has happened.");
                  }

                  CollectionsKt.throwCountOverflow();
                  var2 = var3;
               }
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <T> Collection<T>.count(): Int {
      return var0.size();
   }

   @JvmStatic
   public fun <T> Iterable<T>.distinct(): List<T> {
      return CollectionsKt.toList(CollectionsKt.toMutableSet(var0));
   }

   @JvmStatic
   public inline fun <T, K> Iterable<T>.distinctBy(selector: (T) -> K): List<T> {
      val var3: HashSet = new HashSet();
      val var2: ArrayList = new ArrayList();

      for (Object var5 : var0) {
         if (var3.add(var1.invoke(var5))) {
            var2.add(var5);
         }
      }

      return var2;
   }

   @JvmStatic
   public fun <T> Iterable<T>.drop(n: Int): List<T> {
      if (var1 >= 0) {
         if (var1 == 0) {
            return CollectionsKt.toList(var0);
         } else {
            val var3: ArrayList;
            if (var0 is java.util.Collection) {
               val var2: Int = (var0 as java.util.Collection).size() - var1;
               if (var2 <= 0) {
                  return CollectionsKt.emptyList();
               }

               if (var2 == 1) {
                  return (java.util.List<T>)CollectionsKt.listOf(CollectionsKt.last(var0));
               }

               val var4: ArrayList = new ArrayList(var2);
               var3 = var4;
               if (var0 is java.util.List) {
                  if (var0 is RandomAccess) {
                     val var6: java.util.List = var0 as java.util.List;

                     for (int var11 = ((java.util.List)var0).size(); var1 < var11; var1++) {
                        var4.add(var6.get(var1));
                     }
                  } else {
                     val var7: java.util.Iterator = (var0 as java.util.List).listIterator(var1);

                     while (var7.hasNext()) {
                        var4.add(var7.next());
                     }
                  }

                  return var4;
               }
            } else {
               var3 = new ArrayList();
            }

            val var13: java.util.Iterator = var0.iterator();
            var var12: Int = 0;

            while (var13.hasNext()) {
               var var8: Any = var13.next();
               if (var12 >= var1) {
                  var8 = var3.add(var8);
               } else {
                  var8 = ++var12;
               }

               var8 = var8 as java.lang.Comparable;
            }

            return CollectionsKt.optimizeReadOnlyList(var3);
         }
      } else {
         val var5: StringBuilder = new StringBuilder("Requested element count ");
         var5.append(var1);
         var5.append(" is less than zero.");
         throw new IllegalArgumentException(var5.toString().toString());
      }
   }

   @JvmStatic
   public fun <T> List<T>.dropLast(n: Int): List<T> {
      if (var1 >= 0) {
         return CollectionsKt.take(var0, RangesKt.coerceAtLeast(var0.size() - var1, 0));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public inline fun <T> List<T>.dropLastWhile(predicate: (T) -> Boolean): List<T> {
      if (!var0.isEmpty()) {
         val var2: java.util.ListIterator = var0.listIterator(var0.size());

         while (var2.hasPrevious()) {
            if (!var1.invoke(var2.previous()) as java.lang.Boolean) {
               return CollectionsKt.take(var0, var2.nextIndex() + 1);
            }
         }
      }

      return CollectionsKt.emptyList();
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.dropWhile(predicate: (T) -> Boolean): List<T> {
      val var3: ArrayList = new ArrayList();
      val var4: java.util.Iterator = var0.iterator();
      var var2: Boolean = false;

      while (var4.hasNext()) {
         val var5: Any = var4.next();
         if (var2) {
            var3.add(var5);
         } else if (!var1.invoke(var5) as java.lang.Boolean) {
            var3.add(var5);
            var2 = true;
         }
      }

      return var3;
   }

   @JvmStatic
   public fun <T> Iterable<T>.elementAt(index: Int): T {
      return (T)(if (var0 is java.util.List)
         (var0 as java.util.List).get(var1)
         else
         CollectionsKt.elementAtOrElse(var0, var1, new CollectionsKt___CollectionsKt$$ExternalSyntheticLambda1(var1)));
   }

   @JvmStatic
   public inline fun <T> List<T>.elementAt(index: Int): T {
      return (T)var0.get(var1);
   }

   @JvmStatic
   fun `elementAt$lambda$0$CollectionsKt___CollectionsKt`(var0: Int, var1: Int): Any {
      val var2: StringBuilder = new StringBuilder("Collection doesn't contain element at index ");
      var2.append(var0);
      var2.append('.');
      throw new IndexOutOfBoundsException(var2.toString());
   }

   @JvmStatic
   public fun <T> Iterable<T>.elementAtOrElse(index: Int, defaultValue: (Int) -> T): T {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      if (var0 !is java.util.List) {
         if (var1 < 0) {
            return (T)var2.invoke(var1);
         } else {
            val var4: java.util.Iterator = var0.iterator();

            for (int var3 = 0; var4.hasNext(); var3++) {
               val var7: Any = var4.next();
               if (var1 == var3) {
                  return (T)var7;
               }
            }

            return (T)var2.invoke(var1);
         }
      } else {
         val var5: java.util.List = var0 as java.util.List;
         val var6: Any;
         if (var1 >= 0 && var1 < (var0 as java.util.List).size()) {
            var6 = var5.get(var1);
         } else {
            var6 = var2.invoke(var1);
         }

         return (T)var6;
      }
   }

   @JvmStatic
   public inline fun <T> List<T>.elementAtOrElse(index: Int, defaultValue: (Int) -> T): T {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      val var3: Any;
      if (var1 >= 0 && var1 < var0.size()) {
         var3 = var0.get(var1);
      } else {
         var3 = var2.invoke(var1);
      }

      return (T)var3;
   }

   @JvmStatic
   public fun <T> Iterable<T>.elementAtOrNull(index: Int): T? {
      if (var0 is java.util.List) {
         return (T)CollectionsKt.getOrNull(var0 as java.util.List, var1);
      } else if (var1 < 0) {
         return null;
      } else {
         val var3: java.util.Iterator = var0.iterator();

         for (int var2 = 0; var3.hasNext(); var2++) {
            val var4: Any = var3.next();
            if (var1 == var2) {
               return (T)var4;
            }
         }

         return null;
      }
   }

   @JvmStatic
   public inline fun <T> List<T>.elementAtOrNull(index: Int): T? {
      return (T)CollectionsKt.getOrNull(var0, var1);
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.filter(predicate: (T) -> Boolean): List<T> {
      val var2: java.util.Collection = new ArrayList();

      for (Object var3 : var0) {
         if (var1.invoke(var3) as java.lang.Boolean) {
            var2.add(var3);
         }
      }

      return var2 as MutableList<T>;
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.filterIndexed(predicate: (Int, T) -> Boolean): List<T> {
      val var3: java.util.Collection = new ArrayList();
      val var5: java.util.Iterator = var0.iterator();

      for (int var2 = 0; var5.hasNext(); var2++) {
         val var4: Any = var5.next();
         if (var2 < 0) {
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
               throw new ArithmeticException("Index overflow has happened.");
            }

            CollectionsKt.throwIndexOverflow();
         }

         if (var1.invoke(var2, var4) as java.lang.Boolean) {
            var3.add(var4);
         }
      }

      return var3 as MutableList<T>;
   }

   @JvmStatic
   public inline fun <T, C : MutableCollection<in T>> Iterable<T>.filterIndexedTo(destination: C, predicate: (Int, T) -> Boolean): C {
      val var5: java.util.Iterator = var0.iterator();

      for (int var3 = 0; var5.hasNext(); var3++) {
         val var4: Any = var5.next();
         if (var3 < 0) {
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
               throw new ArithmeticException("Index overflow has happened.");
            }

            CollectionsKt.throwIndexOverflow();
         }

         if (var2.invoke(var3, var4) as java.lang.Boolean) {
            var1.add(var4);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.filterNot(predicate: (T) -> Boolean): List<T> {
      val var2: java.util.Collection = new ArrayList();

      for (Object var4 : var0) {
         if (!var1.invoke(var4) as java.lang.Boolean) {
            var2.add(var4);
         }
      }

      return var2 as MutableList<T>;
   }

   @JvmStatic
   public fun <T : Any> Iterable<T?>.filterNotNull(): List<T> {
      return CollectionsKt.filterNotNullTo(var0, new ArrayList()) as MutableList<T>;
   }

   @JvmStatic
   public fun <C : MutableCollection<in T>, T : Any> Iterable<T?>.filterNotNullTo(destination: C): C {
      for (Object var2 : var0) {
         if (var2 != null) {
            var1.add(var2);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T, C : MutableCollection<in T>> Iterable<T>.filterNotTo(destination: C, predicate: (T) -> Boolean): C {
      for (Object var3 : var0) {
         if (!var2.invoke(var3) as java.lang.Boolean) {
            var1.add(var3);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T, C : MutableCollection<in T>> Iterable<T>.filterTo(destination: C, predicate: (T) -> Boolean): C {
      for (Object var3 : var0) {
         if (var2.invoke(var3) as java.lang.Boolean) {
            var1.add(var3);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.find(predicate: (T) -> Boolean): T? {
      val var2: java.util.Iterator = var0.iterator();

      do {
         if (!var2.hasNext()) {
            var3 = null;
            break;
         }

         var3 = var2.next();
      } while (!var1.invoke(var3));

      return (T)var3;
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.findLast(predicate: (T) -> Boolean): T? {
      val var3: java.util.Iterator = var0.iterator();
      var var4: Any = null;

      while (var3.hasNext()) {
         val var2: Any = var3.next();
         if (var1.invoke(var2) as java.lang.Boolean) {
            var4 = var2;
         }
      }

      return (T)var4;
   }

   @JvmStatic
   public inline fun <T> List<T>.findLast(predicate: (T) -> Boolean): T? {
      val var2: java.util.ListIterator = var0.listIterator(var0.size());

      do {
         if (!var2.hasPrevious()) {
            var3 = null;
            break;
         }

         var3 = var2.previous();
      } while (!var1.invoke(var3));

      return (T)var3;
   }

   @JvmStatic
   public fun <T> Iterable<T>.first(): T {
      if (var0 is java.util.List) {
         return (T)CollectionsKt.first(var0 as java.util.List);
      } else {
         val var1: java.util.Iterator = var0.iterator();
         if (var1.hasNext()) {
            return (T)var1.next();
         } else {
            throw new NoSuchElementException("Collection is empty.");
         }
      }
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.first(predicate: (T) -> Boolean): T {
      for (Object var3 : var0) {
         if (var1.invoke(var3) as java.lang.Boolean) {
            return (T)var3;
         }
      }

      throw new NoSuchElementException("Collection contains no element matching the predicate.");
   }

   @JvmStatic
   public fun <T> List<T>.first(): T {
      if (!var0.isEmpty()) {
         return (T)var0.get(0);
      } else {
         throw new NoSuchElementException("List is empty.");
      }
   }

   @JvmStatic
   public inline fun <T, R : Any> Iterable<T>.firstNotNullOf(transform: (T) -> R?): R {
      val var3: java.util.Iterator = var0.iterator();

      while (true) {
         if (var3.hasNext()) {
            val var2: Any = var1.invoke(var3.next());
            var4 = var2;
            if (var2 == null) {
               continue;
            }
            break;
         }

         var4 = null;
         break;
      }

      if (var4 != null) {
         return (R)var4;
      } else {
         throw new NoSuchElementException("No element of the collection was transformed to a non-null value.");
      }
   }

   @JvmStatic
   public inline fun <T, R : Any> Iterable<T>.firstNotNullOfOrNull(transform: (T) -> R?): R? {
      val var3: java.util.Iterator = var0.iterator();

      while (var3.hasNext()) {
         val var2: Any = var1.invoke(var3.next());
         if (var2 != null) {
            return (R)var2;
         }
      }

      return null;
   }

   @JvmStatic
   public fun <T> Iterable<T>.firstOrNull(): T? {
      if (var0 is java.util.List) {
         return (T)(if ((var0 as java.util.List).isEmpty()) null else (var0 as java.util.List).get(0));
      } else {
         val var1: java.util.Iterator = var0.iterator();
         return (T)(if (!var1.hasNext()) null else var1.next());
      }
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.firstOrNull(predicate: (T) -> Boolean): T? {
      for (Object var2 : var0) {
         if (var1.invoke(var2) as java.lang.Boolean) {
            return (T)var2;
         }
      }

      return null;
   }

   @JvmStatic
   public fun <T> List<T>.firstOrNull(): T? {
      val var1: Any;
      if (var0.isEmpty()) {
         var1 = null;
      } else {
         var1 = var0.get(0);
      }

      return (T)var1;
   }

   @JvmStatic
   public inline fun <T, R> Iterable<T>.flatMap(transform: (T) -> Iterable<R>): List<R> {
      val var2: java.util.Collection = new ArrayList();
      val var3: java.util.Iterator = var0.iterator();

      while (var3.hasNext()) {
         CollectionsKt.addAll(var2, var1.invoke(var3.next()) as java.lang.Iterable);
      }

      return var2 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <T, R> Iterable<T>.flatMapIndexed(transform: (Int, T) -> Iterable<R>): List<R> {
      val var3: java.util.Collection = new ArrayList();
      val var5: java.util.Iterator = var0.iterator();

      for (int var2 = 0; var5.hasNext(); var2++) {
         val var4: Any = var5.next();
         if (var2 < 0) {
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
               throw new ArithmeticException("Index overflow has happened.");
            }

            CollectionsKt.throwIndexOverflow();
         }

         CollectionsKt.addAll(var3, var1.invoke(var2, var4) as java.lang.Iterable);
      }

      return var3 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <T, R, C : MutableCollection<in R>> Iterable<T>.flatMapIndexedTo(destination: C, transform: (Int, T) -> Iterable<R>): C {
      val var4: java.util.Iterator = var0.iterator();

      for (int var3 = 0; var4.hasNext(); var3++) {
         val var5: Any = var4.next();
         if (var3 < 0) {
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
               throw new ArithmeticException("Index overflow has happened.");
            }

            CollectionsKt.throwIndexOverflow();
         }

         CollectionsKt.addAll(var1, var2.invoke(var3, var5) as java.lang.Iterable);
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T, R> Iterable<T>.flatMapIndexed(transform: (Int, T) -> Sequence<R>): List<R> {
      val var3: java.util.Collection = new ArrayList();
      val var5: java.util.Iterator = var0.iterator();

      for (int var2 = 0; var5.hasNext(); var2++) {
         val var4: Any = var5.next();
         if (var2 < 0) {
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
               throw new ArithmeticException("Index overflow has happened.");
            }

            CollectionsKt.throwIndexOverflow();
         }

         CollectionsKt.addAll(var3, var1.invoke(var2, var4) as Sequence);
      }

      return var3 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <T, R, C : MutableCollection<in R>> Iterable<T>.flatMapIndexedTo(destination: C, transform: (Int, T) -> Sequence<R>): C {
      val var5: java.util.Iterator = var0.iterator();

      for (int var3 = 0; var5.hasNext(); var3++) {
         val var4: Any = var5.next();
         if (var3 < 0) {
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
               throw new ArithmeticException("Index overflow has happened.");
            }

            CollectionsKt.throwIndexOverflow();
         }

         CollectionsKt.addAll(var1, var2.invoke(var3, var4) as Sequence);
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T, R> Iterable<T>.flatMap(transform: (T) -> Sequence<R>): List<R> {
      val var2: java.util.Collection = new ArrayList();
      val var3: java.util.Iterator = var0.iterator();

      while (var3.hasNext()) {
         CollectionsKt.addAll(var2, var1.invoke(var3.next()) as Sequence);
      }

      return var2 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <T, R, C : MutableCollection<in R>> Iterable<T>.flatMapTo(destination: C, transform: (T) -> Sequence<R>): C {
      val var3: java.util.Iterator = var0.iterator();

      while (var3.hasNext()) {
         CollectionsKt.addAll(var1, var2.invoke(var3.next()) as Sequence);
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T, R, C : MutableCollection<in R>> Iterable<T>.flatMapTo(destination: C, transform: (T) -> Iterable<R>): C {
      val var3: java.util.Iterator = var0.iterator();

      while (var3.hasNext()) {
         CollectionsKt.addAll(var1, var2.invoke(var3.next()) as java.lang.Iterable);
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T, R> Iterable<T>.fold(initial: R, operation: (R, T) -> R): R {
      val var3: java.util.Iterator = var0.iterator();

      while (var3.hasNext()) {
         var1 = var2.invoke(var1, var3.next());
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <T, R> Iterable<T>.foldIndexed(initial: R, operation: (Int, R, T) -> R): R {
      val var5: java.util.Iterator = var0.iterator();

      for (int var3 = 0; var5.hasNext(); var3++) {
         val var4: Any = var5.next();
         if (var3 < 0) {
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
               throw new ArithmeticException("Index overflow has happened.");
            }

            CollectionsKt.throwIndexOverflow();
         }

         var1 = var2.invoke(var3, var1, var4);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <T, R> List<T>.foldRight(initial: R, operation: (T, R) -> R): R {
      var var3: Any = var1;
      if (!var0.isEmpty()) {
         val var4: java.util.ListIterator = var0.listIterator(var0.size());

         while (true) {
            var3 = var1;
            if (!var4.hasPrevious()) {
               break;
            }

            var1 = var2.invoke(var4.previous(), var1);
         }
      }

      return (R)var3;
   }

   @JvmStatic
   public inline fun <T, R> List<T>.foldRightIndexed(initial: R, operation: (Int, T, R) -> R): R {
      var var3: Any = var1;
      if (!var0.isEmpty()) {
         val var4: java.util.ListIterator = var0.listIterator(var0.size());

         while (true) {
            var3 = var1;
            if (!var4.hasPrevious()) {
               break;
            }

            var1 = var2.invoke(var4.previousIndex(), var4.previous(), var1);
         }
      }

      return (R)var3;
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.forEach(action: (T) -> Unit) {
      val var2: java.util.Iterator = var0.iterator();

      while (var2.hasNext()) {
         var1.invoke(var2.next());
      }
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.forEachIndexed(action: (Int, T) -> Unit) {
      val var4: java.util.Iterator = var0.iterator();

      for (int var2 = 0; var4.hasNext(); var2++) {
         val var3: Any = var4.next();
         if (var2 < 0) {
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
               throw new ArithmeticException("Index overflow has happened.");
            }

            CollectionsKt.throwIndexOverflow();
         }

         var1.invoke(var2, var3);
      }
   }

   @JvmStatic
   public inline fun <T> List<T>.getOrElse(index: Int, defaultValue: (Int) -> T): T {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      val var3: Any;
      if (var1 >= 0 && var1 < var0.size()) {
         var3 = var0.get(var1);
      } else {
         var3 = var2.invoke(var1);
      }

      return (T)var3;
   }

   @JvmStatic
   public fun <T> List<T>.getOrNull(index: Int): T? {
      val var2: Any;
      if (var1 >= 0 && var1 < var0.size()) {
         var2 = var0.get(var1);
      } else {
         var2 = null;
      }

      return (T)var2;
   }

   @JvmStatic
   public inline fun <T, K> Iterable<T>.groupBy(keySelector: (T) -> K): Map<K, List<T>> {
      val var3: java.util.Map = new LinkedHashMap();

      for (Object var6 : var0) {
         val var5: Any = var1.invoke(var6);
         val var2: Any = var3.get(var5);
         var var7: Any = var2;
         if (var2 == null) {
            var7 = new ArrayList();
            var3.put(var5, var7);
         }

         (var7 as java.util.List).add(var6);
      }

      return var3;
   }

   @JvmStatic
   public inline fun <T, K, V> Iterable<T>.groupBy(keySelector: (T) -> K, valueTransform: (T) -> V): Map<K, List<V>> {
      val var4: java.util.Map = new LinkedHashMap();

      for (Object var7 : var0) {
         val var6: Any = var1.invoke(var7);
         val var3: Any = var4.get(var6);
         var var8: Any = var3;
         if (var3 == null) {
            var8 = new ArrayList();
            var4.put(var6, var8);
         }

         (var8 as java.util.List).add(var2.invoke(var7));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <T, K, M : MutableMap<in K, MutableList<T>>> Iterable<T>.groupByTo(destination: M, keySelector: (T) -> K): M {
      for (Object var5 : var0) {
         val var6: Any = var2.invoke(var5);
         val var3: Any = var1.get(var6);
         var var7: Any = var3;
         if (var3 == null) {
            var7 = new ArrayList();
            var1.put(var6, var7);
         }

         (var7 as java.util.List).add(var5);
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <T, K, V, M : MutableMap<in K, MutableList<V>>> Iterable<T>.groupByTo(destination: M, keySelector: (T) -> K, valueTransform: (T) -> V): M {
      for (Object var7 : var0) {
         val var6: Any = var2.invoke(var7);
         val var4: Any = var1.get(var6);
         var var8: Any = var4;
         if (var4 == null) {
            var8 = new ArrayList();
            var1.put(var6, var8);
         }

         (var8 as java.util.List).add(var3.invoke(var7));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <T, K> Iterable<T>.groupingBy(crossinline keySelector: (T) -> K): Grouping<T, K> {
      return new Grouping<T, K>(var0, var1) {
         final Function1<T, K> $keySelector;
         final java.lang.Iterable<T> $this_groupingBy;

         {
            this.$this_groupingBy = var1;
            this.$keySelector = var2;
         }

         @Override
         public K keyOf(T var1) {
            return (K)this.$keySelector.invoke(var1);
         }

         @Override
         public java.util.Iterator<T> sourceIterator() {
            return this.$this_groupingBy.iterator();
         }
      };
   }

   @JvmStatic
   public fun <T> Iterable<T>.indexOf(element: T): Int {
      if (var0 is java.util.List) {
         return (var0 as java.util.List).indexOf(var1);
      } else {
         val var3: java.util.Iterator = var0.iterator();

         for (int var2 = 0; var3.hasNext(); var2++) {
            val var4: Any = var3.next();
            if (var2 < 0) {
               CollectionsKt.throwIndexOverflow();
            }

            if (var1 == var4) {
               return var2;
            }
         }

         return -1;
      }
   }

   @JvmStatic
   public fun <T> List<T>.indexOf(element: T): Int {
      return var0.indexOf(var1);
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.indexOfFirst(predicate: (T) -> Boolean): Int {
      val var4: java.util.Iterator = var0.iterator();

      for (int var2 = 0; var4.hasNext(); var2++) {
         val var3: Any = var4.next();
         if (var2 < 0) {
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
               throw new ArithmeticException("Index overflow has happened.");
            }

            CollectionsKt.throwIndexOverflow();
         }

         if (var1.invoke(var3) as java.lang.Boolean) {
            return var2;
         }
      }

      return -1;
   }

   @JvmStatic
   public inline fun <T> List<T>.indexOfFirst(predicate: (T) -> Boolean): Int {
      val var3: java.util.Iterator = var0.iterator();

      for (int var2 = 0; var3.hasNext(); var2++) {
         if (var1.invoke(var3.next()) as java.lang.Boolean) {
            return var2;
         }
      }

      return -1;
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.indexOfLast(predicate: (T) -> Boolean): Int {
      val var5: java.util.Iterator = var0.iterator();
      var var3: Int = -1;

      for (int var2 = 0; var5.hasNext(); var2++) {
         val var4: Any = var5.next();
         if (var2 < 0) {
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
               throw new ArithmeticException("Index overflow has happened.");
            }

            CollectionsKt.throwIndexOverflow();
         }

         if (var1.invoke(var4) as java.lang.Boolean) {
            var3 = var2;
         }
      }

      return var3;
   }

   @JvmStatic
   public inline fun <T> List<T>.indexOfLast(predicate: (T) -> Boolean): Int {
      val var2: java.util.ListIterator = var0.listIterator(var0.size());

      while (var2.hasPrevious()) {
         if (var1.invoke(var2.previous()) as java.lang.Boolean) {
            return var2.nextIndex();
         }
      }

      return -1;
   }

   @JvmStatic
   public infix fun <T> Iterable<T>.intersect(other: Iterable<T>): Set<T> {
      val var2: java.util.Set = CollectionsKt.toMutableSet(var0);
      CollectionsKt.retainAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public fun <T, A : Appendable> Iterable<T>.joinTo(
      buffer: A,
      separator: CharSequence = ...,
      prefix: CharSequence = ...,
      postfix: CharSequence = ...,
      limit: Int = ...,
      truncated: CharSequence = ...,
      transform: ((T) -> CharSequence)? = ...
   ): A {
      var1.append(var3);
      val var10: java.util.Iterator = var0.iterator();
      val var8: Int = 0;

      var var9: Int;
      while (true) {
         var9 = var8;
         if (!var10.hasNext()) {
            break;
         }

         val var11: Any = var10.next();
         if (++var8 > 1) {
            var1.append(var2);
         }

         if (var5 >= 0) {
            var9 = var8;
            if (var8 > var5) {
               break;
            }
         }

         StringsKt.appendElement(var1, var11, var7);
      }

      if (var5 >= 0 && var9 > var5) {
         var1.append(var6);
      }

      var1.append(var4);
      return (A)var1;
   }

   @JvmStatic
   public fun <T> Iterable<T>.joinToString(
      separator: CharSequence = ", " as java.lang.CharSequence,
      prefix: CharSequence = "" as java.lang.CharSequence,
      postfix: CharSequence = "" as java.lang.CharSequence,
      limit: Int = -1,
      truncated: CharSequence = "..." as java.lang.CharSequence,
      transform: ((T) -> CharSequence)? = null
   ): String {
      return CollectionsKt.joinTo(var0, new StringBuilder(), var1, var2, var3, var4, var5, var6).toString();
   }

   @JvmStatic
   public fun <T> Iterable<T>.last(): T {
      if (var0 is java.util.List) {
         return (T)CollectionsKt.last(var0 as java.util.List);
      } else {
         val var1: java.util.Iterator = var0.iterator();
         if (!var1.hasNext()) {
            throw new NoSuchElementException("Collection is empty.");
         } else {
            var var2: Any = var1.next();

            while (var1.hasNext()) {
               var2 = var1.next();
            }

            return (T)var2;
         }
      }
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.last(predicate: (T) -> Boolean): T {
      val var4: java.util.Iterator = var0.iterator();
      var var5: Any = null;
      var var2: Boolean = false;

      while (var4.hasNext()) {
         val var3: Any = var4.next();
         if (var1.invoke(var3) as java.lang.Boolean) {
            var2 = true;
            var5 = var3;
         }
      }

      if (var2) {
         return (T)var5;
      } else {
         throw new NoSuchElementException("Collection contains no element matching the predicate.");
      }
   }

   @JvmStatic
   public fun <T> List<T>.last(): T {
      if (!var0.isEmpty()) {
         return (T)var0.get(CollectionsKt.getLastIndex(var0));
      } else {
         throw new NoSuchElementException("List is empty.");
      }
   }

   @JvmStatic
   public inline fun <T> List<T>.last(predicate: (T) -> Boolean): T {
      val var2: java.util.ListIterator = var0.listIterator(var0.size());

      while (var2.hasPrevious()) {
         val var3: Any = var2.previous();
         if (var1.invoke(var3) as java.lang.Boolean) {
            return (T)var3;
         }
      }

      throw new NoSuchElementException("List contains no element matching the predicate.");
   }

   @JvmStatic
   public fun <T> Iterable<T>.lastIndexOf(element: T): Int {
      if (var0 is java.util.List) {
         return (var0 as java.util.List).lastIndexOf(var1);
      } else {
         val var5: java.util.Iterator = var0.iterator();
         var var3: Int = -1;

         for (int var2 = 0; var5.hasNext(); var2++) {
            val var4: Any = var5.next();
            if (var2 < 0) {
               CollectionsKt.throwIndexOverflow();
            }

            if (var1 == var4) {
               var3 = var2;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun <T> List<T>.lastIndexOf(element: T): Int {
      return var0.lastIndexOf(var1);
   }

   @JvmStatic
   public fun <T> Iterable<T>.lastOrNull(): T? {
      if (var0 is java.util.List) {
         val var4: java.util.List = var0 as java.util.List;
         val var5: Any;
         if ((var0 as java.util.List).isEmpty()) {
            var5 = null;
         } else {
            var5 = var4.get(var4.size() - 1);
         }

         return (T)var5;
      } else {
         val var6: java.util.Iterator = var0.iterator();
         if (!var6.hasNext()) {
            return null;
         } else {
            var var3: Any = var6.next();

            while (var6.hasNext()) {
               var3 = var6.next();
            }

            return (T)var3;
         }
      }
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.lastOrNull(predicate: (T) -> Boolean): T? {
      val var3: java.util.Iterator = var0.iterator();
      var var4: Any = null;

      while (var3.hasNext()) {
         val var2: Any = var3.next();
         if (var1.invoke(var2) as java.lang.Boolean) {
            var4 = var2;
         }
      }

      return (T)var4;
   }

   @JvmStatic
   public fun <T> List<T>.lastOrNull(): T? {
      val var1: Any;
      if (var0.isEmpty()) {
         var1 = null;
      } else {
         var1 = var0.get(var0.size() - 1);
      }

      return (T)var1;
   }

   @JvmStatic
   public inline fun <T> List<T>.lastOrNull(predicate: (T) -> Boolean): T? {
      val var2: java.util.ListIterator = var0.listIterator(var0.size());

      while (var2.hasPrevious()) {
         val var3: Any = var2.previous();
         if (var1.invoke(var3) as java.lang.Boolean) {
            return (T)var3;
         }
      }

      return null;
   }

   @JvmStatic
   public inline fun <T, R> Iterable<T>.map(transform: (T) -> R): List<R> {
      val var2: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var0, 10));
      val var3: java.util.Iterator = var0.iterator();

      while (var3.hasNext()) {
         var2.add(var1.invoke(var3.next()));
      }

      return var2 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <T, R> Iterable<T>.mapIndexed(transform: (Int, T) -> R): List<R> {
      val var3: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var0, 10));
      val var4: java.util.Iterator = var0.iterator();

      for (int var2 = 0; var4.hasNext(); var2++) {
         val var5: Any = var4.next();
         if (var2 < 0) {
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
               throw new ArithmeticException("Index overflow has happened.");
            }

            CollectionsKt.throwIndexOverflow();
         }

         var3.add(var1.invoke(var2, var5));
      }

      return var3 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <T, R : Any> Iterable<T>.mapIndexedNotNull(transform: (Int, T) -> R?): List<R> {
      val var3: java.util.Collection = new ArrayList();
      val var5: java.util.Iterator = var0.iterator();

      for (int var2 = 0; var5.hasNext(); var2++) {
         var var4: Any = var5.next();
         if (var2 < 0) {
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
               throw new ArithmeticException("Index overflow has happened.");
            }

            CollectionsKt.throwIndexOverflow();
         }

         var4 = var1.invoke(var2, var4);
         if (var4 != null) {
            var3.add(var4);
         }
      }

      return var3 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <T, R : Any, C : MutableCollection<in R>> Iterable<T>.mapIndexedNotNullTo(destination: C, transform: (Int, T) -> R?): C {
      val var5: java.util.Iterator = var0.iterator();

      for (int var3 = 0; var5.hasNext(); var3++) {
         var var4: Any = var5.next();
         if (var3 < 0) {
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
               throw new ArithmeticException("Index overflow has happened.");
            }

            CollectionsKt.throwIndexOverflow();
         }

         var4 = var2.invoke(var3, var4);
         if (var4 != null) {
            var1.add(var4);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T, R, C : MutableCollection<in R>> Iterable<T>.mapIndexedTo(destination: C, transform: (Int, T) -> R): C {
      val var5: java.util.Iterator = var0.iterator();

      for (int var3 = 0; var5.hasNext(); var3++) {
         val var4: Any = var5.next();
         if (var3 < 0) {
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
               throw new ArithmeticException("Index overflow has happened.");
            }

            CollectionsKt.throwIndexOverflow();
         }

         var1.add(var2.invoke(var3, var4));
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T, R : Any> Iterable<T>.mapNotNull(transform: (T) -> R?): List<R> {
      val var2: java.util.Collection = new ArrayList();
      val var4: java.util.Iterator = var0.iterator();

      while (var4.hasNext()) {
         val var3: Any = var1.invoke(var4.next());
         if (var3 != null) {
            var2.add(var3);
         }
      }

      return var2 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <T, R : Any, C : MutableCollection<in R>> Iterable<T>.mapNotNullTo(destination: C, transform: (T) -> R?): C {
      val var3: java.util.Iterator = var0.iterator();

      while (var3.hasNext()) {
         val var4: Any = var2.invoke(var3.next());
         if (var4 != null) {
            var1.add(var4);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T, R, C : MutableCollection<in R>> Iterable<T>.mapTo(destination: C, transform: (T) -> R): C {
      val var3: java.util.Iterator = var0.iterator();

      while (var3.hasNext()) {
         var1.add(var2.invoke(var3.next()));
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Iterable<T>.maxByOrNull(selector: (T) -> R): T? {
      val var7: java.util.Iterator = var0.iterator();
      if (!var7.hasNext()) {
         return null;
      } else {
         var var4: Any = var7.next();
         if (!var7.hasNext()) {
            return (T)var4;
         } else {
            var var8: java.lang.Comparable = var1.invoke(var4) as java.lang.Comparable;

            var var3: Any;
            do {
               val var6: Any = var7.next();
               val var5: java.lang.Comparable = var1.invoke(var6) as java.lang.Comparable;
               var3 = var4;
               var var2: java.lang.Comparable = var8;
               if (var8.compareTo(var5) < 0) {
                  var3 = var6;
                  var2 = var5;
               }

               var4 = var3;
               var8 = var2;
            } while (var7.hasNext());

            return (T)var3;
         }
      }
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Iterable<T>.maxBy(selector: (T) -> R): T {
      val var7: java.util.Iterator = var0.iterator();
      if (!var7.hasNext()) {
         throw new NoSuchElementException();
      } else {
         var var3: Any = var7.next();
         if (!var7.hasNext()) {
            return (T)var3;
         } else {
            var var8: java.lang.Comparable = var1.invoke(var3) as java.lang.Comparable;

            var var4: Any;
            do {
               val var6: Any = var7.next();
               val var5: java.lang.Comparable = var1.invoke(var6) as java.lang.Comparable;
               var4 = var3;
               var var2: java.lang.Comparable = var8;
               if (var8.compareTo(var5) < 0) {
                  var4 = var6;
                  var2 = var5;
               }

               var3 = var4;
               var8 = var2;
            } while (var7.hasNext());

            return (T)var4;
         }
      }
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.maxOf(selector: (T) -> Double): Double {
      val var4: java.util.Iterator = var0.iterator();
      if (!var4.hasNext()) {
         throw new NoSuchElementException();
      } else {
         var var2: Double = (var1.invoke(var4.next()) as java.lang.Number).doubleValue();

         while (var4.hasNext()) {
            var2 = Math.max(var2, (var1.invoke(var4.next()) as java.lang.Number).doubleValue());
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.maxOf(selector: (T) -> Float): Float {
      val var3: java.util.Iterator = var0.iterator();
      if (!var3.hasNext()) {
         throw new NoSuchElementException();
      } else {
         var var2: Float = (var1.invoke(var3.next()) as java.lang.Number).floatValue();

         while (var3.hasNext()) {
            var2 = Math.max(var2, (var1.invoke(var3.next()) as java.lang.Number).floatValue());
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Iterable<T>.maxOf(selector: (T) -> R): R {
      val var3: java.util.Iterator = var0.iterator();
      if (var3.hasNext()) {
         var var4: java.lang.Comparable = var1.invoke(var3.next()) as java.lang.Comparable;

         while (var3.hasNext()) {
            val var2: java.lang.Comparable = var1.invoke(var3.next()) as java.lang.Comparable;
            if (var4.compareTo(var2) < 0) {
               var4 = var2;
            }
         }

         return (R)var4;
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Iterable<T>.maxOfOrNull(selector: (T) -> R): R? {
      val var3: java.util.Iterator = var0.iterator();
      if (!var3.hasNext()) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(var3.next()) as java.lang.Comparable;

         while (var3.hasNext()) {
            val var2: java.lang.Comparable = var1.invoke(var3.next()) as java.lang.Comparable;
            if (var4.compareTo(var2) < 0) {
               var4 = var2;
            }
         }

         return (R)var4;
      }
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.maxOfOrNull(selector: (T) -> Double): Double? {
      val var4: java.util.Iterator = var0.iterator();
      if (!var4.hasNext()) {
         return null;
      } else {
         var var2: Double = (var1.invoke(var4.next()) as java.lang.Number).doubleValue();

         while (var4.hasNext()) {
            var2 = Math.max(var2, (var1.invoke(var4.next()) as java.lang.Number).doubleValue());
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.maxOfOrNull(selector: (T) -> Float): Float? {
      val var3: java.util.Iterator = var0.iterator();
      if (!var3.hasNext()) {
         return null;
      } else {
         var var2: Float = (var1.invoke(var3.next()) as java.lang.Number).floatValue();

         while (var3.hasNext()) {
            var2 = Math.max(var2, (var1.invoke(var3.next()) as java.lang.Number).floatValue());
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <T, R> Iterable<T>.maxOfWith(comparator: Comparator<in R>, selector: (T) -> R): R {
      val var4: java.util.Iterator = var0.iterator();
      if (var4.hasNext()) {
         var var5: Any = var2.invoke(var4.next());

         while (var4.hasNext()) {
            val var3: Any = var2.invoke(var4.next());
            if (var1.compare(var5, var3) < 0) {
               var5 = var3;
            }
         }

         return (R)var5;
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <T, R> Iterable<T>.maxOfWithOrNull(comparator: Comparator<in R>, selector: (T) -> R): R? {
      val var4: java.util.Iterator = var0.iterator();
      if (!var4.hasNext()) {
         return null;
      } else {
         var var5: Any = var2.invoke(var4.next());

         while (var4.hasNext()) {
            val var3: Any = var2.invoke(var4.next());
            if (var1.compare(var5, var3) < 0) {
               var5 = var3;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public fun <T : Comparable<T>> Iterable<T>.maxOrNull(): T? {
      val var2: java.util.Iterator = var0.iterator();
      if (!var2.hasNext()) {
         return null;
      } else {
         var var3: java.lang.Comparable = var2.next() as java.lang.Comparable;

         while (var2.hasNext()) {
            val var1: java.lang.Comparable = var2.next() as java.lang.Comparable;
            if (var3.compareTo(var1) < 0) {
               var3 = var1;
            }
         }

         return (T)var3;
      }
   }

   @JvmStatic
   public fun Iterable<Double>.maxOrNull(): Double? {
      val var3: java.util.Iterator = var0.iterator();
      if (!var3.hasNext()) {
         return null;
      } else {
         var var1: Double = (var3.next() as java.lang.Number).doubleValue();

         while (var3.hasNext()) {
            var1 = Math.max(var1, (var3.next() as java.lang.Number).doubleValue());
         }

         return var1;
      }
   }

   @JvmStatic
   public fun Iterable<Float>.maxOrNull(): Float? {
      val var2: java.util.Iterator = var0.iterator();
      if (!var2.hasNext()) {
         return null;
      } else {
         var var1: Float = (var2.next() as java.lang.Number).floatValue();

         while (var2.hasNext()) {
            var1 = Math.max(var1, (var2.next() as java.lang.Number).floatValue());
         }

         return var1;
      }
   }

   @JvmStatic
   public fun Iterable<Double>.max(): Double {
      val var3: java.util.Iterator = var0.iterator();
      if (!var3.hasNext()) {
         throw new NoSuchElementException();
      } else {
         var var1: Double = (var3.next() as java.lang.Number).doubleValue();

         while (var3.hasNext()) {
            var1 = Math.max(var1, (var3.next() as java.lang.Number).doubleValue());
         }

         return var1;
      }
   }

   @JvmStatic
   public fun Iterable<Float>.max(): Float {
      val var2: java.util.Iterator = var0.iterator();
      if (!var2.hasNext()) {
         throw new NoSuchElementException();
      } else {
         var var1: Float = (var2.next() as java.lang.Number).floatValue();

         while (var2.hasNext()) {
            var1 = Math.max(var1, (var2.next() as java.lang.Number).floatValue());
         }

         return var1;
      }
   }

   @JvmStatic
   public fun <T : Comparable<T>> Iterable<T>.max(): T {
      val var2: java.util.Iterator = var0.iterator();
      if (var2.hasNext()) {
         var var3: java.lang.Comparable = var2.next() as java.lang.Comparable;

         while (var2.hasNext()) {
            val var1: java.lang.Comparable = var2.next() as java.lang.Comparable;
            if (var3.compareTo(var1) < 0) {
               var3 = var1;
            }
         }

         return (T)var3;
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public fun <T> Iterable<T>.maxWithOrNull(comparator: Comparator<in T>): T? {
      val var3: java.util.Iterator = var0.iterator();
      if (!var3.hasNext()) {
         return null;
      } else {
         var var4: Any = var3.next();

         while (var3.hasNext()) {
            val var2: Any = var3.next();
            if (var1.compare(var4, var2) < 0) {
               var4 = var2;
            }
         }

         return (T)var4;
      }
   }

   @JvmStatic
   public fun <T> Iterable<T>.maxWith(comparator: Comparator<in T>): T {
      val var3: java.util.Iterator = var0.iterator();
      if (var3.hasNext()) {
         var var4: Any = var3.next();

         while (var3.hasNext()) {
            val var2: Any = var3.next();
            if (var1.compare(var4, var2) < 0) {
               var4 = var2;
            }
         }

         return (T)var4;
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Iterable<T>.minByOrNull(selector: (T) -> R): T? {
      val var7: java.util.Iterator = var0.iterator();
      if (!var7.hasNext()) {
         return null;
      } else {
         var var3: Any = var7.next();
         if (!var7.hasNext()) {
            return (T)var3;
         } else {
            var var8: java.lang.Comparable = var1.invoke(var3) as java.lang.Comparable;

            var var4: Any;
            do {
               val var6: Any = var7.next();
               val var5: java.lang.Comparable = var1.invoke(var6) as java.lang.Comparable;
               var4 = var3;
               var var2: java.lang.Comparable = var8;
               if (var8.compareTo(var5) > 0) {
                  var4 = var6;
                  var2 = var5;
               }

               var3 = var4;
               var8 = var2;
            } while (var7.hasNext());

            return (T)var4;
         }
      }
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Iterable<T>.minBy(selector: (T) -> R): T {
      val var7: java.util.Iterator = var0.iterator();
      if (!var7.hasNext()) {
         throw new NoSuchElementException();
      } else {
         var var3: Any = var7.next();
         if (!var7.hasNext()) {
            return (T)var3;
         } else {
            var var8: java.lang.Comparable = var1.invoke(var3) as java.lang.Comparable;

            var var4: Any;
            do {
               val var6: Any = var7.next();
               val var5: java.lang.Comparable = var1.invoke(var6) as java.lang.Comparable;
               var4 = var3;
               var var2: java.lang.Comparable = var8;
               if (var8.compareTo(var5) > 0) {
                  var4 = var6;
                  var2 = var5;
               }

               var3 = var4;
               var8 = var2;
            } while (var7.hasNext());

            return (T)var4;
         }
      }
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.minOf(selector: (T) -> Double): Double {
      val var4: java.util.Iterator = var0.iterator();
      if (!var4.hasNext()) {
         throw new NoSuchElementException();
      } else {
         var var2: Double = (var1.invoke(var4.next()) as java.lang.Number).doubleValue();

         while (var4.hasNext()) {
            var2 = Math.min(var2, (var1.invoke(var4.next()) as java.lang.Number).doubleValue());
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.minOf(selector: (T) -> Float): Float {
      val var3: java.util.Iterator = var0.iterator();
      if (!var3.hasNext()) {
         throw new NoSuchElementException();
      } else {
         var var2: Float = (var1.invoke(var3.next()) as java.lang.Number).floatValue();

         while (var3.hasNext()) {
            var2 = Math.min(var2, (var1.invoke(var3.next()) as java.lang.Number).floatValue());
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Iterable<T>.minOf(selector: (T) -> R): R {
      val var3: java.util.Iterator = var0.iterator();
      if (var3.hasNext()) {
         var var4: java.lang.Comparable = var1.invoke(var3.next()) as java.lang.Comparable;

         while (var3.hasNext()) {
            val var2: java.lang.Comparable = var1.invoke(var3.next()) as java.lang.Comparable;
            if (var4.compareTo(var2) > 0) {
               var4 = var2;
            }
         }

         return (R)var4;
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Iterable<T>.minOfOrNull(selector: (T) -> R): R? {
      val var3: java.util.Iterator = var0.iterator();
      if (!var3.hasNext()) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(var3.next()) as java.lang.Comparable;

         while (var3.hasNext()) {
            val var2: java.lang.Comparable = var1.invoke(var3.next()) as java.lang.Comparable;
            if (var4.compareTo(var2) > 0) {
               var4 = var2;
            }
         }

         return (R)var4;
      }
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.minOfOrNull(selector: (T) -> Double): Double? {
      val var4: java.util.Iterator = var0.iterator();
      if (!var4.hasNext()) {
         return null;
      } else {
         var var2: Double = (var1.invoke(var4.next()) as java.lang.Number).doubleValue();

         while (var4.hasNext()) {
            var2 = Math.min(var2, (var1.invoke(var4.next()) as java.lang.Number).doubleValue());
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.minOfOrNull(selector: (T) -> Float): Float? {
      val var3: java.util.Iterator = var0.iterator();
      if (!var3.hasNext()) {
         return null;
      } else {
         var var2: Float = (var1.invoke(var3.next()) as java.lang.Number).floatValue();

         while (var3.hasNext()) {
            var2 = Math.min(var2, (var1.invoke(var3.next()) as java.lang.Number).floatValue());
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <T, R> Iterable<T>.minOfWith(comparator: Comparator<in R>, selector: (T) -> R): R {
      val var4: java.util.Iterator = var0.iterator();
      if (var4.hasNext()) {
         var var5: Any = var2.invoke(var4.next());

         while (var4.hasNext()) {
            val var3: Any = var2.invoke(var4.next());
            if (var1.compare(var5, var3) > 0) {
               var5 = var3;
            }
         }

         return (R)var5;
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <T, R> Iterable<T>.minOfWithOrNull(comparator: Comparator<in R>, selector: (T) -> R): R? {
      val var4: java.util.Iterator = var0.iterator();
      if (!var4.hasNext()) {
         return null;
      } else {
         var var5: Any = var2.invoke(var4.next());

         while (var4.hasNext()) {
            val var3: Any = var2.invoke(var4.next());
            if (var1.compare(var5, var3) > 0) {
               var5 = var3;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public fun <T : Comparable<T>> Iterable<T>.minOrNull(): T? {
      val var2: java.util.Iterator = var0.iterator();
      if (!var2.hasNext()) {
         return null;
      } else {
         var var3: java.lang.Comparable = var2.next() as java.lang.Comparable;

         while (var2.hasNext()) {
            val var1: java.lang.Comparable = var2.next() as java.lang.Comparable;
            if (var3.compareTo(var1) > 0) {
               var3 = var1;
            }
         }

         return (T)var3;
      }
   }

   @JvmStatic
   public fun Iterable<Double>.minOrNull(): Double? {
      val var3: java.util.Iterator = var0.iterator();
      if (!var3.hasNext()) {
         return null;
      } else {
         var var1: Double = (var3.next() as java.lang.Number).doubleValue();

         while (var3.hasNext()) {
            var1 = Math.min(var1, (var3.next() as java.lang.Number).doubleValue());
         }

         return var1;
      }
   }

   @JvmStatic
   public fun Iterable<Float>.minOrNull(): Float? {
      val var2: java.util.Iterator = var0.iterator();
      if (!var2.hasNext()) {
         return null;
      } else {
         var var1: Float = (var2.next() as java.lang.Number).floatValue();

         while (var2.hasNext()) {
            var1 = Math.min(var1, (var2.next() as java.lang.Number).floatValue());
         }

         return var1;
      }
   }

   @JvmStatic
   public fun Iterable<Double>.min(): Double {
      val var3: java.util.Iterator = var0.iterator();
      if (!var3.hasNext()) {
         throw new NoSuchElementException();
      } else {
         var var1: Double = (var3.next() as java.lang.Number).doubleValue();

         while (var3.hasNext()) {
            var1 = Math.min(var1, (var3.next() as java.lang.Number).doubleValue());
         }

         return var1;
      }
   }

   @JvmStatic
   public fun Iterable<Float>.min(): Float {
      val var2: java.util.Iterator = var0.iterator();
      if (!var2.hasNext()) {
         throw new NoSuchElementException();
      } else {
         var var1: Float = (var2.next() as java.lang.Number).floatValue();

         while (var2.hasNext()) {
            var1 = Math.min(var1, (var2.next() as java.lang.Number).floatValue());
         }

         return var1;
      }
   }

   @JvmStatic
   public fun <T : Comparable<T>> Iterable<T>.min(): T {
      val var2: java.util.Iterator = var0.iterator();
      if (var2.hasNext()) {
         var var3: java.lang.Comparable = var2.next() as java.lang.Comparable;

         while (var2.hasNext()) {
            val var1: java.lang.Comparable = var2.next() as java.lang.Comparable;
            if (var3.compareTo(var1) > 0) {
               var3 = var1;
            }
         }

         return (T)var3;
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public fun <T> Iterable<T>.minWithOrNull(comparator: Comparator<in T>): T? {
      val var3: java.util.Iterator = var0.iterator();
      if (!var3.hasNext()) {
         return null;
      } else {
         var var4: Any = var3.next();

         while (var3.hasNext()) {
            val var2: Any = var3.next();
            if (var1.compare(var4, var2) > 0) {
               var4 = var2;
            }
         }

         return (T)var4;
      }
   }

   @JvmStatic
   public fun <T> Iterable<T>.minWith(comparator: Comparator<in T>): T {
      val var3: java.util.Iterator = var0.iterator();
      if (var3.hasNext()) {
         var var4: Any = var3.next();

         while (var3.hasNext()) {
            val var2: Any = var3.next();
            if (var1.compare(var4, var2) > 0) {
               var4 = var2;
            }
         }

         return (T)var4;
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public operator fun <T> Iterable<T>.minus(elements: Iterable<T>): List<T> {
      val var5: java.util.Collection = CollectionsKt.convertToListIfNotCollection(var1);
      if (var5.isEmpty()) {
         return CollectionsKt.toList(var0);
      } else {
         val var2: java.util.Collection = new ArrayList();

         for (Object var3 : var0) {
            if (!var5.contains(var3)) {
               var2.add(var3);
            }
         }

         return var2 as MutableList<T>;
      }
   }

   @JvmStatic
   public operator fun <T> Iterable<T>.minus(element: T): List<T> {
      val var6: ArrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(var0, 10));
      val var8: java.util.Iterator = var0.iterator();
      var var3: Boolean = false;

      while (var8.hasNext()) {
         val var7: Any = var8.next();
         var var4: Boolean = var3;
         var var2: Boolean = true;
         if (!var3) {
            var4 = var3;
            var2 = true;
            if (var7 == var1) {
               var4 = true;
               var2 = false;
            }
         }

         var3 = var4;
         if (var2) {
            var6.add(var7);
            var3 = var4;
         }
      }

      return var6;
   }

   @JvmStatic
   public operator fun <T> Iterable<T>.minus(elements: Sequence<T>): List<T> {
      val var5: java.util.List = SequencesKt.toList(var1);
      if (var5.isEmpty()) {
         return CollectionsKt.toList(var0);
      } else {
         val var2: java.util.Collection = new ArrayList();

         for (Object var4 : var0) {
            if (!var5.contains(var4)) {
               var2.add(var4);
            }
         }

         return var2 as MutableList<T>;
      }
   }

   @JvmStatic
   public operator fun <T> Iterable<T>.minus(elements: Array<out T>): List<T> {
      if (var1.length == 0) {
         return CollectionsKt.toList(var0);
      } else {
         val var2: java.util.Collection = new ArrayList();

         for (Object var4 : var0) {
            if (!ArraysKt.contains(var1, var4)) {
               var2.add(var4);
            }
         }

         return var2 as MutableList<T>;
      }
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.minusElement(element: T): List<T> {
      return (java.util.List<T>)CollectionsKt.minus(var0, var1);
   }

   @JvmStatic
   public fun <T> Iterable<T>.none(): Boolean {
      return if (var0 is java.util.Collection) (var0 as java.util.Collection).isEmpty() else var0.iterator().hasNext() xor true;
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.none(predicate: (T) -> Boolean): Boolean {
      if (var0 is java.util.Collection && (var0 as java.util.Collection).isEmpty()) {
         return true;
      } else {
         val var2: java.util.Iterator = var0.iterator();

         while (var2.hasNext()) {
            if (var1.invoke(var2.next()) as java.lang.Boolean) {
               return false;
            }
         }

         return true;
      }
   }

   @JvmStatic
   public inline fun <T, C : Iterable<T>> C.onEach(action: (T) -> Unit): C {
      val var2: java.util.Iterator = var0.iterator();

      while (var2.hasNext()) {
         var1.invoke(var2.next());
      }

      return (C)var0;
   }

   @JvmStatic
   public inline fun <T, C : Iterable<T>> C.onEachIndexed(action: (Int, T) -> Unit): C {
      val var3: java.util.Iterator = var0.iterator();

      for (int var2 = 0; var3.hasNext(); var2++) {
         val var4: Any = var3.next();
         if (var2 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         var1.invoke(var2, var4);
      }

      return (C)var0;
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.partition(predicate: (T) -> Boolean): Pair<List<T>, List<T>> {
      val var2: ArrayList = new ArrayList();
      val var3: ArrayList = new ArrayList();

      for (Object var4 : var0) {
         if (var1.invoke(var4) as java.lang.Boolean) {
            var2.add(var4);
         } else {
            var3.add(var4);
         }
      }

      return new Pair<>(var2, var3);
   }

   @JvmStatic
   public operator fun <T> Iterable<T>.plus(elements: Iterable<T>): List<T> {
      if (var0 is java.util.Collection) {
         return CollectionsKt.plus(var0 as java.util.Collection, var1);
      } else {
         val var2: ArrayList = new ArrayList();
         val var3: java.util.Collection = var2;
         CollectionsKt.addAll(var2, var0);
         CollectionsKt.addAll(var3, var1);
         return var2;
      }
   }

   @JvmStatic
   public operator fun <T> Iterable<T>.plus(element: T): List<T> {
      if (var0 is java.util.Collection) {
         return (java.util.List<T>)CollectionsKt.plus(var0 as MutableCollection<Any>, var1);
      } else {
         val var2: ArrayList = new ArrayList();
         CollectionsKt.addAll(var2, var0);
         var2.add(var1);
         return var2;
      }
   }

   @JvmStatic
   public operator fun <T> Iterable<T>.plus(elements: Sequence<T>): List<T> {
      val var2: ArrayList = new ArrayList();
      val var3: java.util.Collection = var2;
      CollectionsKt.addAll(var2, var0);
      CollectionsKt.addAll(var3, var1);
      return var2;
   }

   @JvmStatic
   public operator fun <T> Iterable<T>.plus(elements: Array<out T>): List<T> {
      if (var0 is java.util.Collection) {
         return (java.util.List<T>)CollectionsKt.plus(var0 as MutableCollection<Any>, var1);
      } else {
         val var3: ArrayList = new ArrayList();
         val var2: java.util.Collection = var3;
         CollectionsKt.addAll(var3, var0);
         CollectionsKt.addAll(var2, var1);
         return var3;
      }
   }

   @JvmStatic
   public operator fun <T> Collection<T>.plus(elements: Iterable<T>): List<T> {
      if (var1 is java.util.Collection) {
         val var2: Int = var0.size();
         val var5: java.util.Collection = var1 as java.util.Collection;
         val var3: ArrayList = new ArrayList(var2 + (var1 as java.util.Collection).size());
         var3.addAll(var0);
         var3.addAll(var5);
         return var3;
      } else {
         val var4: ArrayList = new ArrayList(var0);
         CollectionsKt.addAll(var4, var1);
         return var4;
      }
   }

   @JvmStatic
   public operator fun <T> Collection<T>.plus(element: T): List<T> {
      val var2: ArrayList = new ArrayList(var0.size() + 1);
      var2.addAll(var0);
      var2.add(var1);
      return var2;
   }

   @JvmStatic
   public operator fun <T> Collection<T>.plus(elements: Sequence<T>): List<T> {
      val var2: ArrayList = new ArrayList(var0.size() + 10);
      var2.addAll(var0);
      CollectionsKt.addAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public operator fun <T> Collection<T>.plus(elements: Array<out T>): List<T> {
      val var2: ArrayList = new ArrayList(var0.size() + var1.length);
      var2.addAll(var0);
      CollectionsKt.addAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.plusElement(element: T): List<T> {
      return (java.util.List<T>)CollectionsKt.plus(var0, var1);
   }

   @JvmStatic
   public inline fun <T> Collection<T>.plusElement(element: T): List<T> {
      return (java.util.List<T>)CollectionsKt.plus(var0, var1);
   }

   @JvmStatic
   public inline fun <T> Collection<T>.random(): T {
      return (T)CollectionsKt.random(var0, Random.Default);
   }

   @JvmStatic
   public fun <T> Collection<T>.random(random: Random): T {
      if (!var0.isEmpty()) {
         return (T)CollectionsKt.elementAt(var0, var1.nextInt(var0.size()));
      } else {
         throw new NoSuchElementException("Collection is empty.");
      }
   }

   @JvmStatic
   public inline fun <T> Collection<T>.randomOrNull(): T? {
      return (T)CollectionsKt.randomOrNull(var0, Random.Default);
   }

   @JvmStatic
   public fun <T> Collection<T>.randomOrNull(random: Random): T? {
      return (T)(if (var0.isEmpty()) null else CollectionsKt.elementAt(var0, var1.nextInt(var0.size())));
   }

   @JvmStatic
   public inline fun <S, T : S> Iterable<T>.reduce(operation: (S, T) -> S): S {
      val var2: java.util.Iterator = var0.iterator();
      if (!var2.hasNext()) {
         throw new UnsupportedOperationException("Empty collection can't be reduced.");
      } else {
         var var3: Any = var2.next();

         while (var2.hasNext()) {
            var3 = var1.invoke(var3, var2.next());
         }

         return (S)var3;
      }
   }

   @JvmStatic
   public inline fun <S, T : S> Iterable<T>.reduceIndexed(operation: (Int, S, T) -> S): S {
      val var3: java.util.Iterator = var0.iterator();
      if (var3.hasNext()) {
         var var4: Any = var3.next();

         for (int var2 = 1; var3.hasNext(); var2++) {
            if (var2 < 0) {
               if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
                  throw new ArithmeticException("Index overflow has happened.");
               }

               CollectionsKt.throwIndexOverflow();
            }

            var4 = var1.invoke(var2, var4, var3.next());
         }

         return (S)var4;
      } else {
         throw new UnsupportedOperationException("Empty collection can't be reduced.");
      }
   }

   @JvmStatic
   public inline fun <S, T : S> Iterable<T>.reduceIndexedOrNull(operation: (Int, S, T) -> S): S? {
      val var3: java.util.Iterator = var0.iterator();
      if (!var3.hasNext()) {
         return null;
      } else {
         var var4: Any = var3.next();

         for (int var2 = 1; var3.hasNext(); var2++) {
            if (var2 < 0) {
               if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
                  throw new ArithmeticException("Index overflow has happened.");
               }

               CollectionsKt.throwIndexOverflow();
            }

            var4 = var1.invoke(var2, var4, var3.next());
         }

         return (S)var4;
      }
   }

   @JvmStatic
   public inline fun <S, T : S> Iterable<T>.reduceOrNull(operation: (S, T) -> S): S? {
      val var2: java.util.Iterator = var0.iterator();
      if (!var2.hasNext()) {
         return null;
      } else {
         var var3: Any = var2.next();

         while (var2.hasNext()) {
            var3 = var1.invoke(var3, var2.next());
         }

         return (S)var3;
      }
   }

   @JvmStatic
   public inline fun <S, T : S> List<T>.reduceRight(operation: (T, S) -> S): S {
      val var2: java.util.ListIterator = var0.listIterator(var0.size());
      if (!var2.hasPrevious()) {
         throw new UnsupportedOperationException("Empty list can't be reduced.");
      } else {
         var var3: Any = var2.previous();

         while (var2.hasPrevious()) {
            var3 = var1.invoke(var2.previous(), var3);
         }

         return (S)var3;
      }
   }

   @JvmStatic
   public inline fun <S, T : S> List<T>.reduceRightIndexed(operation: (Int, T, S) -> S): S {
      val var2: java.util.ListIterator = var0.listIterator(var0.size());
      if (!var2.hasPrevious()) {
         throw new UnsupportedOperationException("Empty list can't be reduced.");
      } else {
         var var3: Any = var2.previous();

         while (var2.hasPrevious()) {
            var3 = var1.invoke(var2.previousIndex(), var2.previous(), var3);
         }

         return (S)var3;
      }
   }

   @JvmStatic
   public inline fun <S, T : S> List<T>.reduceRightIndexedOrNull(operation: (Int, T, S) -> S): S? {
      val var2: java.util.ListIterator = var0.listIterator(var0.size());
      if (!var2.hasPrevious()) {
         return null;
      } else {
         var var3: Any = var2.previous();

         while (var2.hasPrevious()) {
            var3 = var1.invoke(var2.previousIndex(), var2.previous(), var3);
         }

         return (S)var3;
      }
   }

   @JvmStatic
   public inline fun <S, T : S> List<T>.reduceRightOrNull(operation: (T, S) -> S): S? {
      val var2: java.util.ListIterator = var0.listIterator(var0.size());
      if (!var2.hasPrevious()) {
         return null;
      } else {
         var var3: Any = var2.previous();

         while (var2.hasPrevious()) {
            var3 = var1.invoke(var2.previous(), var3);
         }

         return (S)var3;
      }
   }

   @JvmStatic
   public fun <T : Any> Iterable<T?>.requireNoNulls(): Iterable<T> {
      val var1: java.util.Iterator = var0.iterator();

      while (var1.hasNext()) {
         if (var1.next() == null) {
            val var2: StringBuilder = new StringBuilder("null element found in ");
            var2.append(var0);
            var2.append('.');
            throw new IllegalArgumentException(var2.toString());
         }
      }

      return var0;
   }

   @JvmStatic
   public fun <T : Any> List<T?>.requireNoNulls(): List<T> {
      val var1: java.util.Iterator = var0.iterator();

      while (var1.hasNext()) {
         if (var1.next() == null) {
            val var2: StringBuilder = new StringBuilder("null element found in ");
            var2.append(var0);
            var2.append('.');
            throw new IllegalArgumentException(var2.toString());
         }
      }

      return var0;
   }

   @JvmStatic
   public fun <T> Iterable<T>.reversed(): List<T> {
      if (var0 is java.util.Collection && (var0 as java.util.Collection).size() <= 1) {
         return CollectionsKt.toList(var0);
      } else {
         val var1: java.util.List = CollectionsKt.toMutableList(var0);
         CollectionsKt.reverse(var1);
         return var1;
      }
   }

   @JvmStatic
   public inline fun <T, R> Iterable<T>.runningFold(initial: R, operation: (R, T) -> R): List<R> {
      val var3: Int = CollectionsKt.collectionSizeOrDefault(var0, 9);
      if (var3 == 0) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var4: ArrayList = new ArrayList(var3 + 1);
         var4.add(var1);
         val var5: java.util.Iterator = var0.iterator();

         while (var5.hasNext()) {
            var1 = var2.invoke(var1, var5.next());
            var4.add(var1);
         }

         return var4;
      }
   }

   @JvmStatic
   public inline fun <T, R> Iterable<T>.runningFoldIndexed(initial: R, operation: (Int, R, T) -> R): List<R> {
      val var3: Int = CollectionsKt.collectionSizeOrDefault(var0, 9);
      if (var3 == 0) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var4: ArrayList = new ArrayList(var3 + 1);
         var4.add(var1);
         val var5: java.util.Iterator = var0.iterator();

         for (int var6 = 0; var5.hasNext(); var6++) {
            var1 = var2.invoke(var6, var1, var5.next());
            var4.add(var1);
         }

         return var4;
      }
   }

   @JvmStatic
   public inline fun <S, T : S> Iterable<T>.runningReduce(operation: (S, T) -> S): List<S> {
      val var3: java.util.Iterator = var0.iterator();
      if (!var3.hasNext()) {
         return CollectionsKt.emptyList();
      } else {
         val var2: Any = var3.next();
         val var4: ArrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(var0, 10));
         var4.add(var2);
         var var5: Any = var2;

         while (var3.hasNext()) {
            var5 = var1.invoke(var5, var3.next());
            var4.add(var5);
         }

         return var4;
      }
   }

   @JvmStatic
   public inline fun <S, T : S> Iterable<T>.runningReduceIndexed(operation: (Int, S, T) -> S): List<S> {
      val var4: java.util.Iterator = var0.iterator();
      if (!var4.hasNext()) {
         return CollectionsKt.emptyList();
      } else {
         val var3: Any = var4.next();
         val var5: ArrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(var0, 10));
         var5.add(var3);
         var var2: Int = 1;

         for (Object var6 = var3; var4.hasNext(); var2++) {
            var6 = var1.invoke(var2, var6, var4.next());
            var5.add(var6);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun <T, R> Iterable<T>.scan(initial: R, operation: (R, T) -> R): List<R> {
      val var3: Int = CollectionsKt.collectionSizeOrDefault(var0, 9);
      val var5: java.util.List;
      if (var3 == 0) {
         var5 = CollectionsKt.listOf(var1);
      } else {
         val var4: ArrayList = new ArrayList(var3 + 1);
         var4.add(var1);
         val var6: java.util.Iterator = var0.iterator();

         while (var6.hasNext()) {
            var1 = var2.invoke(var1, var6.next());
            var4.add(var1);
         }

         var5 = var4;
      }

      return var5;
   }

   @JvmStatic
   public inline fun <T, R> Iterable<T>.scanIndexed(initial: R, operation: (Int, R, T) -> R): List<R> {
      val var3: Int = CollectionsKt.collectionSizeOrDefault(var0, 9);
      val var5: java.util.List;
      if (var3 == 0) {
         var5 = CollectionsKt.listOf(var1);
      } else {
         val var4: ArrayList = new ArrayList(var3 + 1);
         var4.add(var1);
         val var6: java.util.Iterator = var0.iterator();

         for (int var7 = 0; var6.hasNext(); var7++) {
            var1 = var2.invoke(var7, var1, var6.next());
            var4.add(var1);
         }

         var5 = var4;
      }

      return var5;
   }

   @JvmStatic
   public fun <T> MutableList<T>.shuffle(random: Random) {
      for (int var2 = CollectionsKt.getLastIndex(var0); var2 > 0; var2--) {
         val var3: Int = var1.nextInt(var2 + 1);
         var0.set(var3, var0.set(var2, var0.get(var3)));
      }
   }

   @JvmStatic
   public fun <T> Iterable<T>.single(): T {
      if (var0 is java.util.List) {
         return (T)CollectionsKt.single(var0 as java.util.List);
      } else {
         val var2: java.util.Iterator = var0.iterator();
         if (var2.hasNext()) {
            val var1: Any = var2.next();
            if (!var2.hasNext()) {
               return (T)var1;
            } else {
               throw new IllegalArgumentException("Collection has more than one element.");
            }
         } else {
            throw new NoSuchElementException("Collection is empty.");
         }
      }
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.single(predicate: (T) -> Boolean): T {
      val var4: java.util.Iterator = var0.iterator();
      var var5: Any = null;
      var var2: Boolean = false;

      while (var4.hasNext()) {
         val var3: Any = var4.next();
         if (var1.invoke(var3) as java.lang.Boolean) {
            if (var2) {
               throw new IllegalArgumentException("Collection contains more than one matching element.");
            }

            var2 = true;
            var5 = var3;
         }
      }

      if (var2) {
         return (T)var5;
      } else {
         throw new NoSuchElementException("Collection contains no element matching the predicate.");
      }
   }

   @JvmStatic
   public fun <T> List<T>.single(): T {
      val var1: Int = var0.size();
      if (var1 != 0) {
         if (var1 == 1) {
            return (T)var0.get(0);
         } else {
            throw new IllegalArgumentException("List has more than one element.");
         }
      } else {
         throw new NoSuchElementException("List is empty.");
      }
   }

   @JvmStatic
   public fun <T> Iterable<T>.singleOrNull(): T? {
      if (var0 is java.util.List) {
         val var3: java.util.List = var0 as java.util.List;
         var var5: Any = null;
         if (var3.size() == 1) {
            var5 = var3.get(0);
         }

         return (T)var5;
      } else {
         val var4: java.util.Iterator = var0.iterator();
         if (!var4.hasNext()) {
            return null;
         } else {
            return (T)(if (var4.hasNext()) null else var4.next());
         }
      }
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.singleOrNull(predicate: (T) -> Boolean): T? {
      val var4: java.util.Iterator = var0.iterator();
      var var2: Boolean = false;
      var var5: Any = null;

      while (var4.hasNext()) {
         val var3: Any = var4.next();
         if (var1.invoke(var3) as java.lang.Boolean) {
            if (var2) {
               return null;
            }

            var2 = true;
            var5 = var3;
         }
      }

      return (T)(if (!var2) null else var5);
   }

   @JvmStatic
   public fun <T> List<T>.singleOrNull(): T? {
      val var1: Any;
      if (var0.size() == 1) {
         var1 = var0.get(0);
      } else {
         var1 = null;
      }

      return (T)var1;
   }

   @JvmStatic
   public fun <T> List<T>.slice(indices: Iterable<Int>): List<T> {
      val var2: Int = CollectionsKt.collectionSizeOrDefault(var1, 10);
      if (var2 == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var3: ArrayList = new ArrayList(var2);
         val var4: java.util.Iterator = var1.iterator();

         while (var4.hasNext()) {
            var3.add(var0.get((var4.next() as java.lang.Number).intValue()));
         }

         return var3;
      }
   }

   @JvmStatic
   public fun <T> List<T>.slice(indices: IntRange): List<T> {
      return if (var1.isEmpty()) CollectionsKt.emptyList() else CollectionsKt.toList(var0.subList(var1.getStart(), var1.getEndInclusive() + 1));
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> MutableList<T>.sortBy(crossinline selector: (T) -> R?) {
      if (var0.size() > 1) {
         CollectionsKt.sortWith(var0, new Comparator(var1) {
            final Function1<T, java.lang.Comparable<?>> $selector;

            {
               this.$selector = var1;
            }

            @Override
            public final int compare(T var1, T var2) {
               val var3: Function1 = this.$selector;
               return ComparisonsKt.compareValues(this.$selector.invoke((T)var1) as java.langComparable<?>, var3.invoke(var2) as java.langComparable<?>);
            }
         });
      }
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> MutableList<T>.sortByDescending(crossinline selector: (T) -> R?) {
      if (var0.size() > 1) {
         CollectionsKt.sortWith(var0, new Comparator(var1) {
            final Function1<T, java.lang.Comparable<?>> $selector;

            {
               this.$selector = var1;
            }

            @Override
            public final int compare(T var1, T var2) {
               val var3: Function1 = this.$selector;
               return ComparisonsKt.compareValues(this.$selector.invoke((T)var2) as java.langComparable<?>, var3.invoke(var1) as java.langComparable<?>);
            }
         });
      }
   }

   @JvmStatic
   public fun <T : Comparable<T>> MutableList<T>.sortDescending() {
      CollectionsKt.sortWith(var0, ComparisonsKt.reverseOrder());
   }

   @JvmStatic
   public fun <T : Comparable<T>> Iterable<T>.sorted(): List<T> {
      if (var0 is java.util.Collection) {
         val var1: java.util.Collection = var0 as java.util.Collection;
         if ((var0 as java.util.Collection).size() <= 1) {
            return CollectionsKt.toList(var0);
         } else {
            val var3: Array<Any> = var1.toArray(new java.lang.Comparable[0]);
            ArraysKt.sort(var3 as Array<java.lang.Comparable>);
            return (java.util.List<T>)ArraysKt.asList(var3);
         }
      } else {
         val var2: java.util.List = CollectionsKt.toMutableList(var0);
         CollectionsKt.sort(var2);
         return var2;
      }
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Iterable<T>.sortedBy(crossinline selector: (T) -> R?): List<T> {
      return CollectionsKt.sortedWith(var0, new Comparator(var1) {
         final Function1<T, java.lang.Comparable<?>> $selector;

         {
            this.$selector = var1;
         }

         @Override
         public final int compare(T var1, T var2) {
            val var3: Function1 = this.$selector;
            return ComparisonsKt.compareValues(this.$selector.invoke((T)var1) as java.langComparable<?>, var3.invoke(var2) as java.langComparable<?>);
         }
      });
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Iterable<T>.sortedByDescending(crossinline selector: (T) -> R?): List<T> {
      return CollectionsKt.sortedWith(var0, new Comparator(var1) {
         final Function1<T, java.lang.Comparable<?>> $selector;

         {
            this.$selector = var1;
         }

         @Override
         public final int compare(T var1, T var2) {
            val var3: Function1 = this.$selector;
            return ComparisonsKt.compareValues(this.$selector.invoke((T)var2) as java.langComparable<?>, var3.invoke(var1) as java.langComparable<?>);
         }
      });
   }

   @JvmStatic
   public fun <T : Comparable<T>> Iterable<T>.sortedDescending(): List<T> {
      return CollectionsKt.sortedWith(var0, ComparisonsKt.reverseOrder());
   }

   @JvmStatic
   public fun <T> Iterable<T>.sortedWith(comparator: Comparator<in T>): List<T> {
      if (var0 is java.util.Collection) {
         val var2: java.util.Collection = var0 as java.util.Collection;
         if ((var0 as java.util.Collection).size() <= 1) {
            return CollectionsKt.toList(var0);
         } else {
            val var4: Array<Any> = var2.toArray(new Object[0]);
            ArraysKt.sortWith(var4, var1);
            return (java.util.List<T>)ArraysKt.asList(var4);
         }
      } else {
         val var3: java.util.List = CollectionsKt.toMutableList(var0);
         CollectionsKt.sortWith(var3, var1);
         return var3;
      }
   }

   @JvmStatic
   public infix fun <T> Iterable<T>.subtract(other: Iterable<T>): Set<T> {
      val var2: java.util.Set = CollectionsKt.toMutableSet(var0);
      CollectionsKt.removeAll(var2, var1);
      return var2;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun <T> Iterable<T>.sumBy(selector: (T) -> Int): Int {
      val var3: java.util.Iterator = var0.iterator();
      var var2: Int = 0;

      while (var3.hasNext()) {
         var2 += (var1.invoke(var3.next()) as java.lang.Number).intValue();
      }

      return var2;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun <T> Iterable<T>.sumByDouble(selector: (T) -> Double): Double {
      val var4: java.util.Iterator = var0.iterator();
      var var2: Double = 0.0;

      while (var4.hasNext()) {
         var2 += (var1.invoke(var4.next()) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @JvmStatic
   public fun Iterable<Byte>.sum(): Int {
      val var2: java.util.Iterator = var0.iterator();
      var var1: Int = 0;

      while (var2.hasNext()) {
         var1 += (var2.next() as java.lang.Number).byteValue();
      }

      return var1;
   }

   @JvmStatic
   public fun Iterable<Double>.sum(): Double {
      val var3: java.util.Iterator = var0.iterator();
      var var1: Double = 0.0;

      while (var3.hasNext()) {
         var1 += (var3.next() as java.lang.Number).doubleValue();
      }

      return var1;
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.sumOf(selector: (T) -> Double): Double {
      val var4: java.util.Iterator = var0.iterator();
      var var2: Double = 0.0;

      while (var4.hasNext()) {
         var2 += (var1.invoke(var4.next()) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @JvmStatic
   public fun Iterable<Float>.sum(): Float {
      val var2: java.util.Iterator = var0.iterator();
      var var1: Float = 0.0F;

      while (var2.hasNext()) {
         var1 += (var2.next() as java.lang.Number).floatValue();
      }

      return var1;
   }

   @JvmStatic
   public fun Iterable<Int>.sum(): Int {
      val var2: java.util.Iterator = var0.iterator();
      var var1: Int = 0;

      while (var2.hasNext()) {
         var1 += (var2.next() as java.lang.Number).intValue();
      }

      return var1;
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.sumOf(selector: (T) -> Int): Int {
      val var3: java.util.Iterator = var0.iterator();
      var var2: Int = 0;

      while (var3.hasNext()) {
         var2 += (var1.invoke(var3.next()) as java.lang.Number).intValue();
      }

      return var2;
   }

   @JvmStatic
   public fun Iterable<Long>.sum(): Long {
      val var3: java.util.Iterator = var0.iterator();
      var var1: Long = 0L;

      while (var3.hasNext()) {
         var1 += (var3.next() as java.lang.Number).longValue();
      }

      return var1;
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.sumOf(selector: (T) -> Long): Long {
      val var4: java.util.Iterator = var0.iterator();
      var var2: Long = 0L;

      while (var4.hasNext()) {
         var2 += (var1.invoke(var4.next()) as java.lang.Number).longValue();
      }

      return var2;
   }

   @JvmStatic
   public fun Iterable<Short>.sum(): Int {
      val var2: java.util.Iterator = var0.iterator();
      var var1: Int = 0;

      while (var2.hasNext()) {
         var1 += (var2.next() as java.lang.Number).shortValue();
      }

      return var1;
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.sumOf(selector: (T) -> UInt): UInt {
      var var2: Int = UInt.constructor-impl(0);
      val var3: java.util.Iterator = var0.iterator();

      while (var3.hasNext()) {
         var2 = UInt.constructor-impl(var2 + (var1.invoke(var3.next()) as UInt).unbox-impl());
      }

      return var2;
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.sumOf(selector: (T) -> ULong): ULong {
      var var2: Long = ULong.constructor-impl(0L);
      val var4: java.util.Iterator = var0.iterator();

      while (var4.hasNext()) {
         var2 = ULong.constructor-impl(var2 + (var1.invoke(var4.next()) as ULong).unbox-impl());
      }

      return var2;
   }

   @JvmStatic
   public fun <T> Iterable<T>.take(n: Int): List<T> {
      if (var1 < 0) {
         val var6: StringBuilder = new StringBuilder("Requested element count ");
         var6.append(var1);
         var6.append(" is less than zero.");
         throw new IllegalArgumentException(var6.toString().toString());
      } else if (var1 == 0) {
         return CollectionsKt.emptyList();
      } else {
         if (var0 is java.util.Collection) {
            if (var1 >= (var0 as java.util.Collection).size()) {
               return CollectionsKt.toList(var0);
            }

            if (var1 == 1) {
               return (java.util.List<T>)CollectionsKt.listOf(CollectionsKt.first(var0));
            }
         }

         val var4: ArrayList = new ArrayList(var1);
         val var5: java.util.Iterator = var0.iterator();
         var var2: Int = 0;

         while (var5.hasNext()) {
            var4.add(var5.next());
            val var3: Int = var2 + 1;
            var2 += 1;
            if (var3 == var1) {
               break;
            }
         }

         return CollectionsKt.optimizeReadOnlyList(var4);
      }
   }

   @JvmStatic
   public fun <T> List<T>.takeLast(n: Int): List<T> {
      if (var1 >= 0) {
         if (var1 == 0) {
            return CollectionsKt.emptyList();
         } else {
            val var2: Int = var0.size();
            if (var1 >= var2) {
               return CollectionsKt.toList(var0);
            } else if (var1 == 1) {
               return (java.util.List<T>)CollectionsKt.listOf(CollectionsKt.last(var0));
            } else {
               val var3: ArrayList = new ArrayList(var1);
               if (var0 is RandomAccess) {
                  for (int var6 = var2 - var1; var6 < var2; var6++) {
                     var3.add(var0.get(var6));
                  }
               } else {
                  val var5: java.util.Iterator = var0.listIterator(var2 - var1);

                  while (var5.hasNext()) {
                     var3.add(var5.next());
                  }
               }

               return var3;
            }
         }
      } else {
         val var4: StringBuilder = new StringBuilder("Requested element count ");
         var4.append(var1);
         var4.append(" is less than zero.");
         throw new IllegalArgumentException(var4.toString().toString());
      }
   }

   @JvmStatic
   public inline fun <T> List<T>.takeLastWhile(predicate: (T) -> Boolean): List<T> {
      if (var0.isEmpty()) {
         return CollectionsKt.emptyList();
      } else {
         val var3: java.util.ListIterator = var0.listIterator(var0.size());

         while (var3.hasPrevious()) {
            if (!var1.invoke(var3.previous()) as java.lang.Boolean) {
               var3.next();
               val var2: Int = var0.size() - var3.nextIndex();
               if (var2 == 0) {
                  return CollectionsKt.emptyList();
               }

               val var4: ArrayList = new ArrayList(var2);

               while (var3.hasNext()) {
                  var4.add(var3.next());
               }

               return var4;
            }
         }

         return CollectionsKt.toList(var0);
      }
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.takeWhile(predicate: (T) -> Boolean): List<T> {
      val var2: ArrayList = new ArrayList();

      for (Object var4 : var0) {
         if (!var1.invoke(var4) as java.lang.Boolean) {
            break;
         }

         var2.add(var4);
      }

      return var2;
   }

   @JvmStatic
   public fun Collection<Boolean>.toBooleanArray(): BooleanArray {
      val var2: BooleanArray = new boolean[var0.size()];
      val var3: java.util.Iterator = var0.iterator();

      for (int var1 = 0; var3.hasNext(); var1++) {
         var2[var1] = var3.next() as java.lang.Boolean;
      }

      return var2;
   }

   @JvmStatic
   public fun Collection<Byte>.toByteArray(): ByteArray {
      val var2: ByteArray = new byte[var0.size()];
      val var3: java.util.Iterator = var0.iterator();

      for (int var1 = 0; var3.hasNext(); var1++) {
         var2[var1] = (var3.next() as java.lang.Number).byteValue();
      }

      return var2;
   }

   @JvmStatic
   public fun Collection<Char>.toCharArray(): CharArray {
      val var2: CharArray = new char[var0.size()];
      val var3: java.util.Iterator = var0.iterator();

      for (int var1 = 0; var3.hasNext(); var1++) {
         var2[var1] = var3.next() as Character;
      }

      return var2;
   }

   @JvmStatic
   public fun <T, C : MutableCollection<in T>> Iterable<T>.toCollection(destination: C): C {
      val var2: java.util.Iterator = var0.iterator();

      while (var2.hasNext()) {
         var1.add(var2.next());
      }

      return (C)var1;
   }

   @JvmStatic
   public fun Collection<Double>.toDoubleArray(): DoubleArray {
      val var2: DoubleArray = new double[var0.size()];
      val var3: java.util.Iterator = var0.iterator();

      for (int var1 = 0; var3.hasNext(); var1++) {
         var2[var1] = (var3.next() as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @JvmStatic
   public fun Collection<Float>.toFloatArray(): FloatArray {
      val var2: FloatArray = new float[var0.size()];
      val var3: java.util.Iterator = var0.iterator();

      for (int var1 = 0; var3.hasNext(); var1++) {
         var2[var1] = (var3.next() as java.lang.Number).floatValue();
      }

      return var2;
   }

   @JvmStatic
   public fun <T> Iterable<T>.toHashSet(): HashSet<T> {
      return CollectionsKt.toCollection(var0, new HashSet(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(var0, 12)))) as HashSet<T>;
   }

   @JvmStatic
   public fun Collection<Int>.toIntArray(): IntArray {
      val var2: IntArray = new int[var0.size()];
      val var3: java.util.Iterator = var0.iterator();

      for (int var1 = 0; var3.hasNext(); var1++) {
         var2[var1] = (var3.next() as java.lang.Number).intValue();
      }

      return var2;
   }

   @JvmStatic
   public fun <T> Iterable<T>.toList(): List<T> {
      if (var0 is java.util.Collection) {
         val var2: java.util.Collection = var0 as java.util.Collection;
         val var1: Int = (var0 as java.util.Collection).size();
         val var3: java.util.List;
         if (var1 != 0) {
            if (var1 != 1) {
               var3 = CollectionsKt.toMutableList(var2);
            } else {
               val var4: Any;
               if (var0 is java.util.List) {
                  var4 = (var0 as java.util.List).get(0);
               } else {
                  var4 = var2.iterator().next();
               }

               var3 = CollectionsKt.listOf(var4);
            }
         } else {
            var3 = CollectionsKt.emptyList();
         }

         return var3;
      } else {
         return CollectionsKt.optimizeReadOnlyList(CollectionsKt.toMutableList(var0));
      }
   }

   @JvmStatic
   public fun Collection<Long>.toLongArray(): LongArray {
      val var2: LongArray = new long[var0.size()];
      val var3: java.util.Iterator = var0.iterator();

      for (int var1 = 0; var3.hasNext(); var1++) {
         var2[var1] = (var3.next() as java.lang.Number).longValue();
      }

      return var2;
   }

   @JvmStatic
   public fun <T> Iterable<T>.toMutableList(): MutableList<T> {
      return if (var0 is java.util.Collection)
         CollectionsKt.toMutableList(var0 as java.util.Collection)
         else
         CollectionsKt.toCollection(var0, new ArrayList()) as java.util.List;
   }

   @JvmStatic
   public fun <T> Collection<T>.toMutableList(): MutableList<T> {
      return new ArrayList(var0);
   }

   @JvmStatic
   public fun <T> Iterable<T>.toMutableSet(): MutableSet<T> {
      val var1: java.util.Set;
      if (var0 is java.util.Collection) {
         var1 = new LinkedHashSet(var0 as java.util.Collection);
      } else {
         var1 = CollectionsKt.toCollection(var0, new LinkedHashSet());
      }

      return var1;
   }

   @JvmStatic
   public fun <T> Iterable<T>.toSet(): Set<T> {
      if (var0 is java.util.Collection) {
         val var2: java.util.Collection = var0 as java.util.Collection;
         val var1: Int = (var0 as java.util.Collection).size();
         val var3: java.util.Set;
         if (var1 != 0) {
            if (var1 != 1) {
               var3 = CollectionsKt.toCollection(var0, new LinkedHashSet(MapsKt.mapCapacity(var2.size())));
            } else {
               val var4: Any;
               if (var0 is java.util.List) {
                  var4 = (var0 as java.util.List).get(0);
               } else {
                  var4 = var2.iterator().next();
               }

               var3 = SetsKt.setOf(var4);
            }
         } else {
            var3 = SetsKt.emptySet();
         }

         return var3;
      } else {
         return SetsKt.optimizeReadOnlySet(CollectionsKt.toCollection(var0, new LinkedHashSet()));
      }
   }

   @JvmStatic
   public fun Collection<Short>.toShortArray(): ShortArray {
      val var2: ShortArray = new short[var0.size()];
      val var3: java.util.Iterator = var0.iterator();

      for (int var1 = 0; var3.hasNext(); var1++) {
         var2[var1] = (var3.next() as java.lang.Number).shortValue();
      }

      return var2;
   }

   @JvmStatic
   public infix fun <T> Iterable<T>.union(other: Iterable<T>): Set<T> {
      val var2: java.util.Set = CollectionsKt.toMutableSet(var0);
      CollectionsKt.addAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public fun <T> Iterable<T>.windowed(size: Int, step: Int = 1, partialWindows: Boolean = false): List<List<T>> {
      SlidingWindowKt.checkWindowSizeStep(var1, var2);
      if (var0 is RandomAccess && var0 is java.util.List) {
         val var11: java.util.List = var0 as java.util.List;
         val var6: Int = (var0 as java.util.List).size();
         val var5: Int = var6 / var2;
         val var4: Byte;
         if (var6 % var2 == 0) {
            var4 = 0;
         } else {
            var4 = 1;
         }

         val var14: ArrayList = new ArrayList(var5 + var4);

         for (int var12 = 0; var12 >= 0 && var12 < var6; var12 += var2) {
            val var7: Int = RangesKt.coerceAtMost(var1, var6 - var12);
            if (var7 < var1 && !var3) {
               break;
            }

            val var9: ArrayList = new ArrayList(var7);

            for (int var13 = 0; var13 < var7; var13++) {
               var9.add(var11.get(var13 + var12));
            }

            var14.add(var9);
         }

         return var14;
      } else {
         val var8: ArrayList = new ArrayList();
         val var10: java.util.Iterator = SlidingWindowKt.windowedIterator(var0.iterator(), var1, var2, var3, false);

         while (var10.hasNext()) {
            var8.add(var10.next() as java.util.List);
         }

         return var8;
      }
   }

   @JvmStatic
   public fun <T, R> Iterable<T>.windowed(size: Int, step: Int = 1, partialWindows: Boolean = false, transform: (List<T>) -> R): List<R> {
      SlidingWindowKt.checkWindowSizeStep(var1, var2);
      val var9: Boolean = var0 is RandomAccess;
      var var5: Byte = 1;
      if (var9 && var0 is java.util.List) {
         val var15: java.util.List = var0 as java.util.List;
         val var7: Int = (var0 as java.util.List).size();
         val var8: Int = var7 / var2;
         if (var7 % var2 == 0) {
            var5 = 0;
         }

         val var12: ArrayList = new ArrayList(var8 + var5);
         val var16: MovingSubList = new MovingSubList(var15);

         for (int var13 = 0; var13 >= 0 && var13 < var7; var13 += var2) {
            val var14: Int = RangesKt.coerceAtMost(var1, var7 - var13);
            if (!var3 && var14 < var1) {
               break;
            }

            var16.move(var13, var14 + var13);
            var12.add(var4.invoke(var16));
         }

         return var12;
      } else {
         val var10: ArrayList = new ArrayList();
         val var11: java.util.Iterator = SlidingWindowKt.windowedIterator(var0.iterator(), var1, var2, var3, true);

         while (var11.hasNext()) {
            var10.add(var4.invoke(var11.next() as java.util.List));
         }

         return var10;
      }
   }

   @JvmStatic
   public fun <T> Iterable<T>.withIndex(): Iterable<IndexedValue<T>> {
      return new IndexingIterable(new CollectionsKt___CollectionsKt$$ExternalSyntheticLambda0(var0));
   }

   @JvmStatic
   fun `withIndex$lambda$17$CollectionsKt___CollectionsKt`(var0: java.lang.Iterable): java.util.Iterator {
      return var0.iterator();
   }

   @JvmStatic
   public infix fun <T, R> Iterable<T>.zip(other: Iterable<R>): List<Pair<T, R>> {
      val var2: java.util.Iterator = var0.iterator();
      val var3: java.util.Iterator = var1.iterator();
      val var4: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var0, 10), CollectionsKt.collectionSizeOrDefault(var1, 10)));

      while (var2.hasNext() && var3.hasNext()) {
         var4.add(TuplesKt.to(var2.next(), var3.next()));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <T, R, V> Iterable<T>.zip(other: Iterable<R>, transform: (T, R) -> V): List<V> {
      val var4: java.util.Iterator = var0.iterator();
      val var3: java.util.Iterator = var1.iterator();
      val var5: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var0, 10), CollectionsKt.collectionSizeOrDefault(var1, 10)));

      while (var4.hasNext() && var3.hasNext()) {
         var5.add(var2.invoke(var4.next(), var3.next()));
      }

      return var5;
   }

   @JvmStatic
   public infix fun <T, R> Iterable<T>.zip(other: Array<out R>): List<Pair<T, R>> {
      val var3: Int = var1.length;
      val var4: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var0, 10), var1.length));
      val var6: java.util.Iterator = var0.iterator();

      for (int var2 = 0; var6.hasNext(); var2++) {
         val var5: Any = var6.next();
         if (var2 >= var3) {
            break;
         }

         var4.add(TuplesKt.to(var5, var1[var2]));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <T, R, V> Iterable<T>.zip(other: Array<out R>, transform: (T, R) -> V): List<V> {
      val var4: Int = var1.length;
      val var5: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var0, 10), var1.length));
      val var7: java.util.Iterator = var0.iterator();

      for (int var3 = 0; var7.hasNext(); var3++) {
         val var6: Any = var7.next();
         if (var3 >= var4) {
            break;
         }

         var5.add(var2.invoke(var6, var1[var3]));
      }

      return var5;
   }

   @JvmStatic
   public fun <T> Iterable<T>.zipWithNext(): List<Pair<T, T>> {
      val var3: java.util.Iterator = var0.iterator();
      val var4: java.util.List;
      if (!var3.hasNext()) {
         var4 = CollectionsKt.emptyList();
      } else {
         val var1: java.util.List = new ArrayList();
         var var5: Any = var3.next();

         while (var3.hasNext()) {
            val var2: Any = var3.next();
            var1.add(TuplesKt.to(var5, var2));
            var5 = var2;
         }

         var4 = var1;
      }

      return var4;
   }

   @JvmStatic
   public inline fun <T, R> Iterable<T>.zipWithNext(transform: (T, T) -> R): List<R> {
      val var3: java.util.Iterator = var0.iterator();
      if (!var3.hasNext()) {
         return CollectionsKt.emptyList();
      } else {
         val var4: java.util.List = new ArrayList();
         var var5: Any = var3.next();

         while (var3.hasNext()) {
            val var2: Any = var3.next();
            var4.add(var1.invoke(var5, var2));
            var5 = var2;
         }

         return var4;
      }
   }
}
