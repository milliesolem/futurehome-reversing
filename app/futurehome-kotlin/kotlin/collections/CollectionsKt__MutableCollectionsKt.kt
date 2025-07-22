package kotlin.collections

import java.util.NoSuchElementException
import java.util.RandomAccess
import kotlin.jvm.internal.TypeIntrinsics

internal class CollectionsKt__MutableCollectionsKt : CollectionsKt__MutableCollectionsJVMKt {
   open fun CollectionsKt__MutableCollectionsKt() {
   }

   @JvmStatic
   public fun <T> MutableCollection<in T>.addAll(elements: Iterable<T>): Boolean {
      if (var1 is java.util.Collection) {
         return var0.addAll(var1 as java.util.Collection);
      } else {
         val var3: java.util.Iterator = var1.iterator();
         var var2: Boolean = false;

         while (var3.hasNext()) {
            if (var0.add(var3.next())) {
               var2 = true;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public fun <T> MutableCollection<in T>.addAll(elements: Sequence<T>): Boolean {
      val var3: java.util.Iterator = var1.iterator();
      var var2: Boolean = false;

      while (var3.hasNext()) {
         if (var0.add(var3.next())) {
            var2 = true;
         }
      }

      return var2;
   }

   @JvmStatic
   public fun <T> MutableCollection<in T>.addAll(elements: Array<out T>): Boolean {
      return var0.addAll(ArraysKt.asList(var1));
   }

   @JvmStatic
   internal fun <T> Iterable<T>.convertToListIfNotCollection(): Collection<T> {
      if (var0 !is java.util.Collection) {
         var0 = CollectionsKt.toList((java.lang.Iterable)var0);
      }

      return var0 as MutableCollection<T>;
   }

   @JvmStatic
   private fun <T> MutableIterable<T>.filterInPlace(predicate: (T) -> Boolean, predicateResultToRemove: Boolean): Boolean {
      val var4: java.util.Iterator = var0.iterator();
      var var3: Boolean = false;

      while (var4.hasNext()) {
         if (var1.invoke(var4.next()) as java.lang.Boolean == var2) {
            var4.remove();
            var3 = true;
         }
      }

      return var3;
   }

   @JvmStatic
   private fun <T> MutableList<T>.filterInPlace(predicate: (T) -> Boolean, predicateResultToRemove: Boolean): Boolean {
      if (var0 !is RandomAccess) {
         return filterInPlace$CollectionsKt__MutableCollectionsKt(TypeIntrinsics.asMutableIterable(var0), var1, var2);
      } else {
         val var6: Int = CollectionsKt.getLastIndex(var0);
         val var4: Int;
         if (var6 >= 0) {
            var var5: Int = 0;
            var var3: Int = 0;

            while (true) {
               val var7: Any = var0.get(var5);
               if (var1.invoke(var7) as java.lang.Boolean != var2) {
                  if (var3 != var5) {
                     var0.set(var3, var7);
                  }

                  var3++;
               }

               var4 = var3;
               if (var5 == var6) {
                  break;
               }

               var5++;
            }
         } else {
            var4 = 0;
         }

         if (var4 >= var0.size()) {
            return false;
         } else {
            var var8: Int = CollectionsKt.getLastIndex(var0);
            if (var4 <= var8) {
               while (true) {
                  var0.remove(var8);
                  if (var8 == var4) {
                     break;
                  }

                  var8--;
               }
            }

            return true;
         }
      }
   }

   @JvmStatic
   public inline operator fun <T> MutableCollection<in T>.minusAssign(elements: Iterable<T>) {
      CollectionsKt.removeAll(var0, var1);
   }

   @JvmStatic
   public inline operator fun <T> MutableCollection<in T>.minusAssign(element: T) {
      var0.remove(var1);
   }

   @JvmStatic
   public inline operator fun <T> MutableCollection<in T>.minusAssign(elements: Sequence<T>) {
      CollectionsKt.removeAll(var0, var1);
   }

   @JvmStatic
   public inline operator fun <T> MutableCollection<in T>.minusAssign(elements: Array<T>) {
      CollectionsKt.removeAll(var0, var1);
   }

   @JvmStatic
   public inline operator fun <T> MutableCollection<in T>.plusAssign(elements: Iterable<T>) {
      CollectionsKt.addAll(var0, var1);
   }

   @JvmStatic
   public inline operator fun <T> MutableCollection<in T>.plusAssign(element: T) {
      var0.add(var1);
   }

   @JvmStatic
   public inline operator fun <T> MutableCollection<in T>.plusAssign(elements: Sequence<T>) {
      CollectionsKt.addAll(var0, var1);
   }

   @JvmStatic
   public inline operator fun <T> MutableCollection<in T>.plusAssign(elements: Array<T>) {
      CollectionsKt.addAll(var0, var1);
   }

   @Deprecated(level = DeprecationLevel.ERROR, message = "Use removeAt(index) instead.", replaceWith = @ReplaceWith(expression = "removeAt(index)", imports = []))
   @JvmStatic
   public inline fun <T> MutableList<T>.remove(index: Int): T {
      return (T)var0.remove(var1);
   }

   @JvmStatic
   public inline fun <T> MutableCollection<out T>.remove(element: T): Boolean {
      return TypeIntrinsics.asMutableCollection(var0).remove(var1);
   }

   @JvmStatic
   public fun <T> MutableIterable<T>.removeAll(predicate: (T) -> Boolean): Boolean {
      return filterInPlace$CollectionsKt__MutableCollectionsKt(var0, var1, true);
   }

   @JvmStatic
   public fun <T> MutableCollection<in T>.removeAll(elements: Iterable<T>): Boolean {
      return var0.removeAll(CollectionsKt.convertToListIfNotCollection(var1));
   }

   @JvmStatic
   public inline fun <T> MutableCollection<out T>.removeAll(elements: Collection<T>): Boolean {
      return TypeIntrinsics.asMutableCollection(var0).removeAll(var1);
   }

   @JvmStatic
   public fun <T> MutableCollection<in T>.removeAll(elements: Sequence<T>): Boolean {
      val var3: java.util.Collection = SequencesKt.toList(var1);
      val var2: Boolean;
      if (!var3.isEmpty() && var0.removeAll(var3)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @JvmStatic
   public fun <T> MutableCollection<in T>.removeAll(elements: Array<out T>): Boolean {
      val var2: Int = var1.length;
      var var3: Boolean = true;
      val var4: Boolean;
      if (var2 == 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4 || !var0.removeAll(ArraysKt.asList(var1))) {
         var3 = false;
      }

      return var3;
   }

   @JvmStatic
   public fun <T> MutableList<T>.removeAll(predicate: (T) -> Boolean): Boolean {
      return filterInPlace$CollectionsKt__MutableCollectionsKt(var0, var1, true);
   }

   @JvmStatic
   public fun <T> MutableList<T>.removeFirst(): T {
      if (!var0.isEmpty()) {
         return (T)var0.remove(0);
      } else {
         throw new NoSuchElementException("List is empty.");
      }
   }

   @JvmStatic
   public fun <T> MutableList<T>.removeFirstOrNull(): T? {
      val var1: Any;
      if (var0.isEmpty()) {
         var1 = null;
      } else {
         var1 = var0.remove(0);
      }

      return (T)var1;
   }

   @JvmStatic
   public fun <T> MutableList<T>.removeLast(): T {
      if (!var0.isEmpty()) {
         return (T)var0.remove(CollectionsKt.getLastIndex(var0));
      } else {
         throw new NoSuchElementException("List is empty.");
      }
   }

   @JvmStatic
   public fun <T> MutableList<T>.removeLastOrNull(): T? {
      val var1: Any;
      if (var0.isEmpty()) {
         var1 = null;
      } else {
         var1 = var0.remove(CollectionsKt.getLastIndex(var0));
      }

      return (T)var1;
   }

   @JvmStatic
   public fun <T> MutableIterable<T>.retainAll(predicate: (T) -> Boolean): Boolean {
      return filterInPlace$CollectionsKt__MutableCollectionsKt(var0, var1, false);
   }

   @JvmStatic
   public fun <T> MutableCollection<in T>.retainAll(elements: Iterable<T>): Boolean {
      return var0.retainAll(CollectionsKt.convertToListIfNotCollection(var1));
   }

   @JvmStatic
   public inline fun <T> MutableCollection<out T>.retainAll(elements: Collection<T>): Boolean {
      return TypeIntrinsics.asMutableCollection(var0).retainAll(var1);
   }

   @JvmStatic
   public fun <T> MutableCollection<in T>.retainAll(elements: Sequence<T>): Boolean {
      val var2: java.util.Collection = SequencesKt.toList(var1);
      return if (!var2.isEmpty()) var0.retainAll(var2) else retainNothing$CollectionsKt__MutableCollectionsKt(var0);
   }

   @JvmStatic
   public fun <T> MutableCollection<in T>.retainAll(elements: Array<out T>): Boolean {
      val var2: Boolean;
      if (var1.length == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return if (!var2) var0.retainAll(ArraysKt.asList(var1)) else retainNothing$CollectionsKt__MutableCollectionsKt(var0);
   }

   @JvmStatic
   public fun <T> MutableList<T>.retainAll(predicate: (T) -> Boolean): Boolean {
      return filterInPlace$CollectionsKt__MutableCollectionsKt(var0, var1, false);
   }

   @JvmStatic
   private fun MutableCollection<*>.retainNothing(): Boolean {
      val var1: Boolean = var0.isEmpty();
      var0.clear();
      return var1 xor true;
   }
}
