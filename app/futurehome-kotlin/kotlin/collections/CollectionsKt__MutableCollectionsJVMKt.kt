package kotlin.collections

import java.util.Collections
import java.util.Comparator
import java.util.Random

internal class CollectionsKt__MutableCollectionsJVMKt : CollectionsKt__IteratorsKt {
   open fun CollectionsKt__MutableCollectionsJVMKt() {
   }

   @JvmStatic
   public inline fun <T> MutableList<T>.fill(value: T) {
      Collections.fill(var0, var1);
   }

   @JvmStatic
   public inline fun <T> MutableList<T>.shuffle() {
      Collections.shuffle(var0);
   }

   @JvmStatic
   public inline fun <T> MutableList<T>.shuffle(random: Random) {
      Collections.shuffle(var0, var1);
   }

   @JvmStatic
   public fun <T : Comparable<T>> MutableList<T>.sort() {
      if (var0.size() > 1) {
         Collections.sort(var0);
      }
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Use sortWith(comparator) instead.", replaceWith = @ReplaceWith(expression = "this.sortWith(comparator)", imports = []))
   @JvmStatic
   public inline fun <T> MutableList<T>.sort(comparator: Comparator<in T>) {
      throw new NotImplementedError(null, 1, null);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Use sortWith(Comparator(comparison)) instead.", replaceWith = @ReplaceWith(expression = "this.sortWith(Comparator(comparison))", imports = []))
   @JvmStatic
   public inline fun <T> MutableList<T>.sort(comparison: (T, T) -> Int) {
      throw new NotImplementedError(null, 1, null);
   }

   @JvmStatic
   public fun <T> MutableList<T>.sortWith(comparator: Comparator<in T>) {
      if (var0.size() > 1) {
         Collections.sort(var0, var1);
      }
   }
}
