package kotlin.comparisons

import java.util.Comparator

private object NaturalOrderComparator : Comparator<java.lang.Comparable<? super Object>>, j..util.Comparator {
   public open fun compare(a: Comparable<Any>, b: Comparable<Any>): Int {
      return var1.compareTo(var2);
   }

   public override fun reversed(): Comparator<Comparable<Any>> {
      return ReverseOrderComparator.INSTANCE;
   }
}
