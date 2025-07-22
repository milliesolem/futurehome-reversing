package kotlin.ranges

public interface ClosedFloatingPointRange<T extends java.lang.Comparable<? super T>> : ClosedRange<T> {
   public override operator fun contains(value: Any): Boolean {
   }

   public override fun isEmpty(): Boolean {
   }

   public abstract fun lessThanOrEquals(a: Any, b: Any): Boolean {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun <T extends java.lang.Comparable<? super T>> contains(var0: ClosedFloatingPointRange<T>, var1: T): Boolean {
         val var2: Boolean;
         if (var0.lessThanOrEquals((T)var0.getStart(), (T)var1) && var0.lessThanOrEquals((T)var1, (T)var0.getEndInclusive())) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      @JvmStatic
      fun <T extends java.lang.Comparable<? super T>> isEmpty(var0: ClosedFloatingPointRange<T>): Boolean {
         return var0.lessThanOrEquals((T)var0.getStart(), (T)var0.getEndInclusive()) xor true;
      }
   }
}
