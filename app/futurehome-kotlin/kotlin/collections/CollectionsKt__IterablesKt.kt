package kotlin.collections

import java.util.ArrayList
import kotlin.jvm.functions.Function0

internal class CollectionsKt__IterablesKt : CollectionsKt__CollectionsKt {
   open fun CollectionsKt__IterablesKt() {
   }

   @JvmStatic
   public inline fun <T> Iterable(crossinline iterator: () -> Iterator<T>): Iterable<T> {
      return new java.lang.Iterable<T>(var0) {
         final Function0<java.util.Iterator<T>> $iterator;

         {
            this.$iterator = var1;
         }

         @Override
         public java.util.Iterator<T> iterator() {
            return this.$iterator.invoke() as MutableIterator<T>;
         }
      };
   }

   @JvmStatic
   internal fun <T> Iterable<T>.collectionSizeOrDefault(default: Int): Int {
      if (var0 is java.util.Collection) {
         var1 = (var0 as java.util.Collection).size();
      }

      return var1;
   }

   @JvmStatic
   internal fun <T> Iterable<T>.collectionSizeOrNull(): Int? {
      val var1: Int;
      if (var0 is java.util.Collection) {
         var1 = (var0 as java.util.Collection).size();
      } else {
         var1 = null;
      }

      return var1;
   }

   @JvmStatic
   public fun <T> Iterable<Iterable<T>>.flatten(): List<T> {
      val var1: ArrayList = new ArrayList();

      for (var0 : var0) {
         CollectionsKt.addAll(var1, var0);
      }

      return var1;
   }

   @JvmStatic
   public fun <T, R> Iterable<Pair<T, R>>.unzip(): Pair<List<T>, List<R>> {
      val var1: Int = CollectionsKt.collectionSizeOrDefault(var0, 10);
      val var2: ArrayList = new ArrayList(var1);
      val var3: ArrayList = new ArrayList(var1);

      for (Pair var5 : var0) {
         var2.add(var5.getFirst());
         var3.add(var5.getSecond());
      }

      return TuplesKt.to(var2, var3);
   }
}
