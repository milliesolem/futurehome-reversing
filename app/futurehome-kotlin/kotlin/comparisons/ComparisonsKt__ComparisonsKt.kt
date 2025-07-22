package kotlin.comparisons

import java.util.Comparator
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2

internal class ComparisonsKt__ComparisonsKt {
   open fun ComparisonsKt__ComparisonsKt() {
   }

   @JvmStatic
   public inline fun <T, K> compareBy(comparator: Comparator<in K>, crossinline selector: (T) -> K): Comparator<T> {
      return new Comparator(var0, var1) {
         final Comparator<? super K> $comparator;
         final Function1<T, K> $selector;

         {
            this.$comparator = var1;
            this.$selector = var2;
         }

         @Override
         public final int compare(T var1, T var2) {
            val var3: Function1 = this.$selector;
            return this.$comparator.compare(this.$selector.invoke((T)var1), var3.invoke(var2));
         }
      };
   }

   @JvmStatic
   public inline fun <T> compareBy(crossinline selector: (T) -> Comparable<*>?): Comparator<T> {
      return new Comparator(var0) {
         final Function1<T, java.lang.Comparable<?>> $selector;

         {
            this.$selector = var1;
         }

         @Override
         public final int compare(T var1, T var2) {
            val var3: Function1 = this.$selector;
            return ComparisonsKt.compareValues(this.$selector.invoke((T)var1), var3.invoke(var2) as java.langComparable<?>);
         }
      };
   }

   @JvmStatic
   public fun <T> compareBy(vararg selectors: (T) -> Comparable<*>?): Comparator<T> {
      if (var0.length > 0) {
         return new ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda3(var0);
      } else {
         throw new IllegalArgumentException("Failed requirement.".toString());
      }
   }

   @JvmStatic
   fun `compareBy$lambda$0$ComparisonsKt__ComparisonsKt`(var0: Array<Array<Function1>>, var1: Any, var2: Any): Int {
      return compareValuesByImpl$ComparisonsKt__ComparisonsKt(var1, var2, var0);
   }

   @JvmStatic
   public inline fun <T, K> compareByDescending(comparator: Comparator<in K>, crossinline selector: (T) -> K): Comparator<T> {
      return new Comparator(var0, var1) {
         final Comparator<? super K> $comparator;
         final Function1<T, K> $selector;

         {
            this.$comparator = var1;
            this.$selector = var2;
         }

         @Override
         public final int compare(T var1, T var2) {
            val var4: Function1 = this.$selector;
            return this.$comparator.compare(this.$selector.invoke((T)var2), var4.invoke(var1));
         }
      };
   }

   @JvmStatic
   public inline fun <T> compareByDescending(crossinline selector: (T) -> Comparable<*>?): Comparator<T> {
      return new Comparator(var0) {
         final Function1<T, java.lang.Comparable<?>> $selector;

         {
            this.$selector = var1;
         }

         @Override
         public final int compare(T var1, T var2) {
            val var3: Function1 = this.$selector;
            return ComparisonsKt.compareValues(this.$selector.invoke((T)var2), var3.invoke(var1) as java.langComparable<?>);
         }
      };
   }

   @JvmStatic
   public fun <T : Comparable<*>> compareValues(a: T?, b: T?): Int {
      if (var0 === var1) {
         return 0;
      } else if (var0 == null) {
         return -1;
      } else {
         return if (var1 == null) 1 else var0.compareTo(var1);
      }
   }

   @JvmStatic
   public inline fun <T, K> compareValuesBy(a: T, b: T, comparator: Comparator<in K>, selector: (T) -> K): Int {
      return var2.compare(var3.invoke(var0), var3.invoke(var1));
   }

   @JvmStatic
   public inline fun <T> compareValuesBy(a: T, b: T, selector: (T) -> Comparable<*>?): Int {
      return ComparisonsKt.compareValues(var2.invoke(var0) as java.lang.Comparable, var2.invoke(var1) as java.lang.Comparable);
   }

   @JvmStatic
   public fun <T> compareValuesBy(a: T, b: T, vararg selectors: (T) -> Comparable<*>?): Int {
      if (var2.length > 0) {
         return compareValuesByImpl$ComparisonsKt__ComparisonsKt(var0, var1, var2);
      } else {
         throw new IllegalArgumentException("Failed requirement.".toString());
      }
   }

   @JvmStatic
   private fun <T> compareValuesByImpl(a: T, b: T, selectors: Array<out (T) -> Comparable<*>?>): Int {
      val var4: Int = var2.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Int = ComparisonsKt.compareValues(var2[var3].invoke(var0) as java.lang.Comparable, var2[var3].invoke(var1) as java.lang.Comparable);
         if (var5 != 0) {
            return var5;
         }
      }

      return 0;
   }

   @JvmStatic
   public fun <T : Comparable<T>> naturalOrder(): Comparator<T> {
      val var0: NaturalOrderComparator = NaturalOrderComparator.INSTANCE;
      return var0;
   }

   @JvmStatic
   public inline fun <T : Comparable<T>> nullsFirst(): Comparator<T?> {
      return ComparisonsKt.nullsFirst(ComparisonsKt.naturalOrder());
   }

   @JvmStatic
   public fun <T : Any> nullsFirst(comparator: Comparator<in T>): Comparator<T?> {
      return new ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda4(var0);
   }

   @JvmStatic
   fun `nullsFirst$lambda$3$ComparisonsKt__ComparisonsKt`(var0: Comparator, var1: Any, var2: Any): Int {
      val var3: Int;
      if (var1 === var2) {
         var3 = 0;
      } else if (var1 == null) {
         var3 = -1;
      } else if (var2 == null) {
         var3 = 1;
      } else {
         var3 = var0.compare(var1, var2);
      }

      return var3;
   }

   @JvmStatic
   public inline fun <T : Comparable<T>> nullsLast(): Comparator<T?> {
      return ComparisonsKt.nullsLast(ComparisonsKt.naturalOrder());
   }

   @JvmStatic
   public fun <T : Any> nullsLast(comparator: Comparator<in T>): Comparator<T?> {
      return new ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda0(var0);
   }

   @JvmStatic
   fun `nullsLast$lambda$4$ComparisonsKt__ComparisonsKt`(var0: Comparator, var1: Any, var2: Any): Int {
      val var3: Int;
      if (var1 === var2) {
         var3 = 0;
      } else if (var1 == null) {
         var3 = 1;
      } else if (var2 == null) {
         var3 = -1;
      } else {
         var3 = var0.compare(var1, var2);
      }

      return var3;
   }

   @JvmStatic
   public fun <T : Comparable<T>> reverseOrder(): Comparator<T> {
      val var0: ReverseOrderComparator = ReverseOrderComparator.INSTANCE;
      return var0;
   }

   @JvmStatic
   public fun <T> Comparator<T>.reversed(): Comparator<T> {
      if (var0 is ReversedComparator) {
         var0 = (var0 as ReversedComparator).getComparator();
      } else if (var0 == NaturalOrderComparator.INSTANCE) {
         val var2: ReverseOrderComparator = ReverseOrderComparator.INSTANCE;
         var0 = var2;
      } else if (var0 == ReverseOrderComparator.INSTANCE) {
         val var3: NaturalOrderComparator = NaturalOrderComparator.INSTANCE;
         var0 = var3;
      } else {
         var0 = new ReversedComparator(var0);
      }

      return var0;
   }

   @JvmStatic
   public infix fun <T> Comparator<T>.then(comparator: Comparator<in T>): Comparator<T> {
      return new ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda2(var0, var1);
   }

   @JvmStatic
   fun `then$lambda$1$ComparisonsKt__ComparisonsKt`(var0: Comparator, var1: Comparator, var2: Any, var3: Any): Int {
      var var4: Int = var0.compare(var2, var3);
      if (var4 == 0) {
         var4 = var1.compare(var2, var3);
      }

      return var4;
   }

   @JvmStatic
   public inline fun <T, K> Comparator<T>.thenBy(comparator: Comparator<in K>, crossinline selector: (T) -> K): Comparator<T> {
      return new Comparator(var0, var1, var2) {
         final Comparator<? super K> $comparator;
         final Function1<T, K> $selector;
         final Comparator<T> $this_thenBy;

         {
            this.$this_thenBy = var1;
            this.$comparator = var2;
            this.$selector = var3;
         }

         @Override
         public final int compare(T var1, T var2) {
            var var3: Int = this.$this_thenBy.compare((T)var1, (T)var2);
            if (var3 == 0) {
               val var4: Function1 = this.$selector;
               var3 = this.$comparator.compare(this.$selector.invoke((T)var1), var4.invoke(var2));
            }

            return var3;
         }
      };
   }

   @JvmStatic
   public inline fun <T> Comparator<T>.thenBy(crossinline selector: (T) -> Comparable<*>?): Comparator<T> {
      return new Comparator(var0, var1) {
         final Function1<T, java.lang.Comparable<?>> $selector;
         final Comparator<T> $this_thenBy;

         {
            this.$this_thenBy = var1;
            this.$selector = var2;
         }

         @Override
         public final int compare(T var1, T var2) {
            var var3: Int = this.$this_thenBy.compare((T)var1, (T)var2);
            if (var3 == 0) {
               val var4: Function1 = this.$selector;
               var3 = ComparisonsKt.compareValues(this.$selector.invoke((T)var1), var4.invoke(var2) as java.langComparable<?>);
            }

            return var3;
         }
      };
   }

   @JvmStatic
   public inline fun <T, K> Comparator<T>.thenByDescending(comparator: Comparator<in K>, crossinline selector: (T) -> K): Comparator<T> {
      return new Comparator(var0, var1, var2) {
         final Comparator<? super K> $comparator;
         final Function1<T, K> $selector;
         final Comparator<T> $this_thenByDescending;

         {
            this.$this_thenByDescending = var1;
            this.$comparator = var2;
            this.$selector = var3;
         }

         @Override
         public final int compare(T var1, T var2) {
            var var3: Int = this.$this_thenByDescending.compare((T)var1, (T)var2);
            if (var3 == 0) {
               val var5: Function1 = this.$selector;
               var3 = this.$comparator.compare(this.$selector.invoke((T)var2), var5.invoke(var1));
            }

            return var3;
         }
      };
   }

   @JvmStatic
   public inline fun <T> Comparator<T>.thenByDescending(crossinline selector: (T) -> Comparable<*>?): Comparator<T> {
      return new Comparator(var0, var1) {
         final Function1<T, java.lang.Comparable<?>> $selector;
         final Comparator<T> $this_thenByDescending;

         {
            this.$this_thenByDescending = var1;
            this.$selector = var2;
         }

         @Override
         public final int compare(T var1, T var2) {
            var var3: Int = this.$this_thenByDescending.compare((T)var1, (T)var2);
            if (var3 == 0) {
               val var4: Function1 = this.$selector;
               var3 = ComparisonsKt.compareValues(this.$selector.invoke((T)var2), var4.invoke(var1) as java.langComparable<?>);
            }

            return var3;
         }
      };
   }

   @JvmStatic
   public inline fun <T> Comparator<T>.thenComparator(crossinline comparison: (T, T) -> Int): Comparator<T> {
      return new Comparator(var0, var1) {
         final Function2<T, T, Integer> $comparison;
         final Comparator<T> $this_thenComparator;

         {
            this.$this_thenComparator = var1;
            this.$comparison = var2;
         }

         @Override
         public final int compare(T var1, T var2) {
            var var3: Int = this.$this_thenComparator.compare((T)var1, (T)var2);
            if (var3 == 0) {
               var3 = this.$comparison.invoke((T)var1, (T)var2).intValue();
            }

            return var3;
         }
      };
   }

   @JvmStatic
   public infix fun <T> Comparator<T>.thenDescending(comparator: Comparator<in T>): Comparator<T> {
      return new ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda1(var0, var1);
   }

   @JvmStatic
   fun `thenDescending$lambda$2$ComparisonsKt__ComparisonsKt`(var0: Comparator, var1: Comparator, var2: Any, var3: Any): Int {
      var var4: Int = var0.compare(var2, var3);
      if (var4 == 0) {
         var4 = var1.compare(var3, var2);
      }

      return var4;
   }
}
