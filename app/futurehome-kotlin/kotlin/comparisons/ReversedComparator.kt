package kotlin.comparisons

import java.util.Comparator

private class ReversedComparator<T>(comparator: Comparator<Any>) : Comparator<T>, j..util.Comparator {
   public final val comparator: Comparator<Any>

   init {
      this.comparator = var1;
   }

   public override fun compare(a: Any, b: Any): Int {
      return this.comparator.compare((T)var2, (T)var1);
   }

   public override fun reversed(): Comparator<Any> {
      return this.comparator;
   }
}
