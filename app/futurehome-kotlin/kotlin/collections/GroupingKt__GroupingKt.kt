package kotlin.collections

import java.util.LinkedHashMap

internal class GroupingKt__GroupingKt : GroupingKt__GroupingJVMKt {
   open fun GroupingKt__GroupingKt() {
   }

   @JvmStatic
   public inline fun <T, K, R> Grouping<T, K>.aggregate(operation: (K, R?, T, Boolean) -> R): Map<K, R> {
      val var6: java.util.Map = new LinkedHashMap();
      val var7: java.util.Iterator = var0.sourceIterator();

      while (var7.hasNext()) {
         val var3: Any = var7.next();
         val var4: Any = var0.keyOf(var3);
         val var5: Any = var6.get(var4);
         val var2: Boolean;
         if (var5 == null && !var6.containsKey(var4)) {
            var2 = true;
         } else {
            var2 = false;
         }

         var6.put(var4, var1.invoke(var4, var5, var3, var2));
      }

      return var6;
   }

   @JvmStatic
   public inline fun <T, K, R, M : MutableMap<in K, R>> Grouping<T, K>.aggregateTo(destination: M, operation: (K, R?, T, Boolean) -> R): M {
      val var6: java.util.Iterator = var0.sourceIterator();

      while (var6.hasNext()) {
         val var7: Any = var6.next();
         val var4: Any = var0.keyOf(var7);
         val var5: Any = var1.get(var4);
         val var3: Boolean;
         if (var5 == null && !var1.containsKey(var4)) {
            var3 = true;
         } else {
            var3 = false;
         }

         var1.put(var4, var2.invoke(var4, var5, var7, var3));
      }

      return (M)var1;
   }

   @JvmStatic
   public fun <T, K, M : MutableMap<in K, Int>> Grouping<T, K>.eachCountTo(destination: M): M {
      val var4: java.util.Iterator = var0.sourceIterator();

      while (var4.hasNext()) {
         val var5: Any = var0.keyOf(var4.next());
         var var3: Any = var1.get(var5);
         val var2: Boolean;
         if (var3 == null && !var1.containsKey(var5)) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (var2) {
            var3 = 0;
         }

         var1.put(var5, (var3 as java.lang.Number).intValue() + 1);
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <T, K, R> Grouping<T, K>.fold(initialValue: R, operation: (R, T) -> R): Map<K, R> {
      val var8: java.util.Map = new LinkedHashMap();
      val var7: java.util.Iterator = var0.sourceIterator();

      while (var7.hasNext()) {
         val var5: Any = var7.next();
         val var6: Any = var0.keyOf(var5);
         var var4: Any = var8.get(var6);
         val var3: Boolean;
         if (var4 == null && !var8.containsKey(var6)) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3) {
            var4 = var1;
         }

         var8.put(var6, var2.invoke(var4, var5));
      }

      return var8;
   }

   @JvmStatic
   public inline fun <T, K, R> Grouping<T, K>.fold(initialValueSelector: (K, T) -> R, operation: (K, R, T) -> R): Map<K, R> {
      val var6: java.util.Map = new LinkedHashMap();
      val var5: java.util.Iterator = var0.sourceIterator();

      while (var5.hasNext()) {
         val var7: Any = var5.next();
         val var8: Any = var0.keyOf(var7);
         var var4: Any = var6.get(var8);
         val var3: Boolean;
         if (var4 == null && !var6.containsKey(var8)) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3) {
            var4 = var1.invoke(var8, var7);
         }

         var6.put(var8, var2.invoke(var8, var4, var7));
      }

      return var6;
   }

   @JvmStatic
   public inline fun <T, K, R, M : MutableMap<in K, R>> Grouping<T, K>.foldTo(destination: M, initialValue: R, operation: (R, T) -> R): M {
      val var6: java.util.Iterator = var0.sourceIterator();

      while (var6.hasNext()) {
         val var8: Any = var6.next();
         val var7: Any = var0.keyOf(var8);
         var var5: Any = var1.get(var7);
         val var4: Boolean;
         if (var5 == null && !var1.containsKey(var7)) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            var5 = var2;
         }

         var1.put(var7, var3.invoke(var5, var8));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <T, K, R, M : MutableMap<in K, R>> Grouping<T, K>.foldTo(destination: M, initialValueSelector: (K, T) -> R, operation: (K, R, T) -> R): M {
      val var6: java.util.Iterator = var0.sourceIterator();

      while (var6.hasNext()) {
         val var8: Any = var6.next();
         val var7: Any = var0.keyOf(var8);
         var var5: Any = var1.get(var7);
         val var4: Boolean;
         if (var5 == null && !var1.containsKey(var7)) {
            var4 = true;
         } else {
            var4 = false;
         }

         if (var4) {
            var5 = var2.invoke(var7, var8);
         }

         var1.put(var7, var3.invoke(var7, var5, var8));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <S, T : S, K> Grouping<T, K>.reduce(operation: (K, S, T) -> S): Map<K, S> {
      val var4: java.util.Map = new LinkedHashMap();
      val var5: java.util.Iterator = var0.sourceIterator();

      while (var5.hasNext()) {
         var var3: Any = var5.next();
         val var6: Any = var0.keyOf(var3);
         val var7: Any = var4.get(var6);
         val var2: Boolean;
         if (var7 == null && !var4.containsKey(var6)) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (!var2) {
            var3 = var1.invoke(var6, var7, var3);
         }

         var4.put(var6, var3);
      }

      return var4;
   }

   @JvmStatic
   public inline fun <S, T : S, K, M : MutableMap<in K, S>> Grouping<T, K>.reduceTo(destination: M, operation: (K, S, T) -> S): M {
      val var6: java.util.Iterator = var0.sourceIterator();

      while (var6.hasNext()) {
         var var4: Any = var6.next();
         val var5: Any = var0.keyOf(var4);
         val var7: Any = var1.get(var5);
         val var3: Boolean;
         if (var7 == null && !var1.containsKey(var5)) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (!var3) {
            var4 = var2.invoke(var5, var7, var4);
         }

         var1.put(var5, var4);
      }

      return (M)var1;
   }
}
