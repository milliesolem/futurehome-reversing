package kotlin.text

import java.math.BigDecimal
import java.math.BigInteger
import java.util.SortedSet
import java.util.TreeSet

internal class StringsKt___StringsJvmKt : StringsKt__StringsKt {
   open fun StringsKt___StringsJvmKt() {
   }

   @JvmStatic
   public inline fun CharSequence.elementAt(index: Int): Char {
      return var0.charAt(var1);
   }

   @JvmStatic
   public inline fun CharSequence.sumOf(selector: (Char) -> BigDecimal): BigDecimal {
      var var3: BigDecimal = BigDecimal.valueOf(0L);

      for (int var2 = 0; var2 < var0.length(); var2++) {
         var3 = var3.add(var1.invoke(var0.charAt(var2)) as BigDecimal);
      }

      return var3;
   }

   @JvmStatic
   public inline fun CharSequence.sumOf(selector: (Char) -> BigInteger): BigInteger {
      var var3: BigInteger = BigInteger.valueOf(0L);

      for (int var2 = 0; var2 < var0.length(); var2++) {
         var3 = var3.add(var1.invoke(var0.charAt(var2)) as BigInteger);
      }

      return var3;
   }

   @JvmStatic
   public fun CharSequence.toSortedSet(): SortedSet<Char> {
      return StringsKt.toCollection(var0, new TreeSet<>());
   }
}
