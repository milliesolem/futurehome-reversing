package kotlin.collections

import java.util.LinkedHashSet

internal class SetsKt___SetsKt : SetsKt__SetsKt {
   open fun SetsKt___SetsKt() {
   }

   @JvmStatic
   public operator fun <T> Set<T>.minus(elements: Iterable<T>): Set<T> {
      val var6: java.util.Collection = CollectionsKt.convertToListIfNotCollection(var1);
      if (var6.isEmpty()) {
         return CollectionsKt.toSet(var0);
      } else if (var6 is java.util.Set) {
         val var2: java.lang.Iterable = var0;
         val var5: java.util.Collection = new LinkedHashSet();

         for (Object var3 : var2) {
            if (!(var6 as java.util.Set).contains(var3)) {
               var5.add(var3);
            }
         }

         return var5 as MutableSet<T>;
      } else {
         val var4: LinkedHashSet = new LinkedHashSet(var0);
         var4.removeAll(var6);
         return var4;
      }
   }

   @JvmStatic
   public operator fun <T> Set<T>.minus(element: T): Set<T> {
      val var6: LinkedHashSet = new LinkedHashSet(MapsKt.mapCapacity(var0.size()));
      val var8: java.util.Iterator = var0.iterator();
      var var4: Boolean = false;

      while (var8.hasNext()) {
         val var7: Any = var8.next();
         var var3: Boolean = var4;
         var var2: Boolean = true;
         if (!var4) {
            var3 = var4;
            var2 = true;
            if (var7 == var1) {
               var3 = true;
               var2 = false;
            }
         }

         var4 = var3;
         if (var2) {
            var6.add(var7);
            var4 = var3;
         }
      }

      return var6;
   }

   @JvmStatic
   public operator fun <T> Set<T>.minus(elements: Sequence<T>): Set<T> {
      val var2: LinkedHashSet = new LinkedHashSet(var0);
      CollectionsKt.removeAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public operator fun <T> Set<T>.minus(elements: Array<out T>): Set<T> {
      val var2: LinkedHashSet = new LinkedHashSet(var0);
      CollectionsKt.removeAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public inline fun <T> Set<T>.minusElement(element: T): Set<T> {
      return (java.util.Set<T>)SetsKt.minus(var0, var1);
   }

   @JvmStatic
   public operator fun <T> Set<T>.plus(elements: Iterable<T>): Set<T> {
      val var3: Int = CollectionsKt.collectionSizeOrNull(var1);
      val var4: Int;
      if (var3 != null) {
         var4 = var0.size() + var3.intValue();
      } else {
         var4 = var0.size() * 2;
      }

      val var5: LinkedHashSet = new LinkedHashSet(MapsKt.mapCapacity(var4));
      var5.addAll(var0);
      CollectionsKt.addAll(var5, var1);
      return var5;
   }

   @JvmStatic
   public operator fun <T> Set<T>.plus(element: T): Set<T> {
      val var2: LinkedHashSet = new LinkedHashSet(MapsKt.mapCapacity(var0.size() + 1));
      var2.addAll(var0);
      var2.add(var1);
      return var2;
   }

   @JvmStatic
   public operator fun <T> Set<T>.plus(elements: Sequence<T>): Set<T> {
      val var2: LinkedHashSet = new LinkedHashSet(MapsKt.mapCapacity(var0.size() * 2));
      var2.addAll(var0);
      CollectionsKt.addAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public operator fun <T> Set<T>.plus(elements: Array<out T>): Set<T> {
      val var2: LinkedHashSet = new LinkedHashSet(MapsKt.mapCapacity(var0.size() + var1.length));
      var2.addAll(var0);
      CollectionsKt.addAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public inline fun <T> Set<T>.plusElement(element: T): Set<T> {
      return (java.util.Set<T>)SetsKt.plus(var0, var1);
   }
}
