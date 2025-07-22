package kotlin.ranges

public interface OpenEndRange<T extends java.lang.Comparable<? super T>> {
   public val start: Any
   public val endExclusive: Any

   public open operator fun contains(value: Any): Boolean {
   }

   public open fun isEmpty(): Boolean {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun <T extends java.lang.Comparable<? super T>> contains(var0: OpenEndRange<T>, var1: T): Boolean {
         val var2: Boolean;
         if (var1.compareTo(var0.getStart()) >= 0 && var1.compareTo(var0.getEndExclusive()) < 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      @JvmStatic
      fun <T extends java.lang.Comparable<? super T>> isEmpty(var0: OpenEndRange<T>): Boolean {
         val var1: Boolean;
         if (var0.getStart().compareTo(var0.getEndExclusive()) >= 0) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }
   }
}
