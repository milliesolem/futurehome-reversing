package kotlin.sequences

import java.math.BigDecimal
import java.math.BigInteger
import java.util.Comparator
import java.util.SortedSet
import java.util.TreeSet

internal class SequencesKt___SequencesJvmKt : SequencesKt__SequencesKt {
   open fun SequencesKt___SequencesJvmKt() {
   }

   @JvmStatic
   public fun <R> Sequence<*>.filterIsInstance(klass: Class<R>): Sequence<R> {
      var0 = SequencesKt.filter(var0, new SequencesKt___SequencesJvmKt$$ExternalSyntheticLambda0(var1));
      return var0;
   }

   @JvmStatic
   fun `filterIsInstance$lambda$0$SequencesKt___SequencesJvmKt`(var0: Class, var1: Any): Boolean {
      return var0.isInstance(var1);
   }

   @JvmStatic
   public fun <C : MutableCollection<in R>, R> Sequence<*>.filterIsInstanceTo(destination: C, klass: Class<R>): C {
      for (Object var3 : var0) {
         if (var2.isInstance(var3)) {
            var1.add(var3);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.sumOf(selector: (T) -> BigDecimal): BigDecimal {
      val var2: BigDecimal = BigDecimal.valueOf(0L);
      val var3: java.util.Iterator = var0.iterator();
      var var4: BigDecimal = var2;

      while (var3.hasNext()) {
         var4 = var4.add(var1.invoke(var3.next()) as BigDecimal);
      }

      return var4;
   }

   @JvmStatic
   public inline fun <T> Sequence<T>.sumOf(selector: (T) -> BigInteger): BigInteger {
      val var2: BigInteger = BigInteger.valueOf(0L);
      val var3: java.util.Iterator = var0.iterator();
      var var4: BigInteger = var2;

      while (var3.hasNext()) {
         var4 = var4.add(var1.invoke(var3.next()) as BigInteger);
      }

      return var4;
   }

   @JvmStatic
   public fun <T : Comparable<T>> Sequence<T>.toSortedSet(): SortedSet<T> {
      return SequencesKt.toCollection(var0, new TreeSet()) as SortedSet<T>;
   }

   @JvmStatic
   public fun <T> Sequence<T>.toSortedSet(comparator: Comparator<in T>): SortedSet<T> {
      return SequencesKt.toCollection(var0, new TreeSet(var1)) as SortedSet<T>;
   }
}
