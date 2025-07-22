package kotlin.comparisons

import java.util.Comparator

internal class ComparisonsKt___ComparisonsKt : ComparisonsKt___ComparisonsJvmKt {
   open fun ComparisonsKt___ComparisonsKt() {
   }

   @JvmStatic
   public fun <T> maxOf(a: T, b: T, c: T, comparator: Comparator<in T>): T {
      return (T)ComparisonsKt.maxOf(var0, ComparisonsKt.maxOf(var1, var2, var3), var3);
   }

   @JvmStatic
   public fun <T> maxOf(a: T, b: T, comparator: Comparator<in T>): T {
      if (var2.compare(var0, var1) < 0) {
         var0 = var1;
      }

      return (T)var0;
   }

   @JvmStatic
   public fun <T> maxOf(a: T, vararg other: T, comparator: Comparator<in T>): T {
      val var4: Int = var1.length;
      var var3: Int = 0;

      while (var3 < var4) {
         val var6: Any = var1[var3];
         var var5: Any = var0;
         if (var2.compare(var0, var6) < 0) {
            var5 = var6;
         }

         var3++;
         var0 = var5;
      }

      return (T)var0;
   }

   @JvmStatic
   public fun <T> minOf(a: T, b: T, c: T, comparator: Comparator<in T>): T {
      return (T)ComparisonsKt.minOf(var0, ComparisonsKt.minOf(var1, var2, var3), var3);
   }

   @JvmStatic
   public fun <T> minOf(a: T, b: T, comparator: Comparator<in T>): T {
      if (var2.compare(var0, var1) > 0) {
         var0 = var1;
      }

      return (T)var0;
   }

   @JvmStatic
   public fun <T> minOf(a: T, vararg other: T, comparator: Comparator<in T>): T {
      val var4: Int = var1.length;
      var var3: Int = 0;

      while (var3 < var4) {
         val var6: Any = var1[var3];
         var var5: Any = var0;
         if (var2.compare(var0, var6) > 0) {
            var5 = var6;
         }

         var3++;
         var0 = var5;
      }

      return (T)var0;
   }
}
