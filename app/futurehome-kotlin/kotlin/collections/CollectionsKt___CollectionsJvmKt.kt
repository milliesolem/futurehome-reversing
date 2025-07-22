package kotlin.collections

import java.math.BigDecimal
import java.math.BigInteger
import java.util.ArrayList
import java.util.Collections
import java.util.Comparator
import java.util.SortedSet
import java.util.TreeSet

internal class CollectionsKt___CollectionsJvmKt : CollectionsKt__ReversedViewsKt {
   open fun CollectionsKt___CollectionsJvmKt() {
   }

   @JvmStatic
   public fun <R> Iterable<*>.filterIsInstance(klass: Class<R>): List<R> {
      return CollectionsKt.filterIsInstanceTo(var0, new ArrayList(), var1) as MutableList<R>;
   }

   @JvmStatic
   public fun <C : MutableCollection<in R>, R> Iterable<*>.filterIsInstanceTo(destination: C, klass: Class<R>): C {
      for (Object var4 : var0) {
         if (var2.isInstance(var4)) {
            var1.add(var4);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public fun <T> MutableList<T>.reverse() {
      Collections.reverse(var0);
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.sumOf(selector: (T) -> BigDecimal): BigDecimal {
      val var2: BigDecimal = BigDecimal.valueOf(0L);
      val var3: java.util.Iterator = var0.iterator();
      var var4: BigDecimal = var2;

      while (var3.hasNext()) {
         var4 = var4.add(var1.invoke(var3.next()) as BigDecimal);
      }

      return var4;
   }

   @JvmStatic
   public inline fun <T> Iterable<T>.sumOf(selector: (T) -> BigInteger): BigInteger {
      val var2: BigInteger = BigInteger.valueOf(0L);
      val var3: java.util.Iterator = var0.iterator();
      var var4: BigInteger = var2;

      while (var3.hasNext()) {
         var4 = var4.add(var1.invoke(var3.next()) as BigInteger);
      }

      return var4;
   }

   @JvmStatic
   public fun <T : Comparable<T>> Iterable<T>.toSortedSet(): SortedSet<T> {
      return CollectionsKt.toCollection(var0, new TreeSet()) as SortedSet<T>;
   }

   @JvmStatic
   public fun <T> Iterable<T>.toSortedSet(comparator: Comparator<in T>): SortedSet<T> {
      return CollectionsKt.toCollection(var0, new TreeSet(var1)) as SortedSet<T>;
   }
}
