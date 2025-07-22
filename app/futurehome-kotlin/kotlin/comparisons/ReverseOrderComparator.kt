package kotlin.comparisons

import java.util.Comparator

private object ReverseOrderComparator : Comparator<java.lang.Comparable<? super Object>>, j..util.Comparator {
   public open fun compare(a: Comparable<Any>, b: Comparable<Any>): Int {
      return var2.compareTo(var1);
   }

   public override fun reversed(): Comparator<Comparable<Any>> {
      return NaturalOrderComparator.INSTANCE;
   }
}
