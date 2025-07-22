package kotlin.collections

import java.util.ArrayList
import java.util.Comparator
import java.util.NoSuchElementException
import kotlin.collections.Map.Entry

internal class MapsKt___MapsKt : MapsKt___MapsJvmKt {
   open fun MapsKt___MapsKt() {
   }

   @JvmStatic
   public inline fun <K, V> Map<out K, V>.all(predicate: (Entry<K, V>) -> Boolean): Boolean {
      if (var0.isEmpty()) {
         return true;
      } else {
         val var2: java.util.Iterator = var0.entrySet().iterator();

         while (var2.hasNext()) {
            if (!var1.invoke(var2.next() as java.util.Map.Entry) as java.lang.Boolean) {
               return false;
            }
         }

         return true;
      }
   }

   @JvmStatic
   public fun <K, V> Map<out K, V>.any(): Boolean {
      return var0.isEmpty() xor true;
   }

   @JvmStatic
   public inline fun <K, V> Map<out K, V>.any(predicate: (Entry<K, V>) -> Boolean): Boolean {
      if (var0.isEmpty()) {
         return false;
      } else {
         val var2: java.util.Iterator = var0.entrySet().iterator();

         while (var2.hasNext()) {
            if (var1.invoke(var2.next() as java.util.Map.Entry) as java.lang.Boolean) {
               return true;
            }
         }

         return false;
      }
   }

   @JvmStatic
   public inline fun <K, V> Map<out K, V>.asIterable(): Iterable<Entry<K, V>> {
      return var0.entrySet();
   }

   @JvmStatic
   public fun <K, V> Map<out K, V>.asSequence(): Sequence<Entry<K, V>> {
      return CollectionsKt.asSequence(var0.entrySet());
   }

   @JvmStatic
   public inline fun <K, V> Map<out K, V>.count(): Int {
      return var0.size();
   }

   @JvmStatic
   public inline fun <K, V> Map<out K, V>.count(predicate: (Entry<K, V>) -> Boolean): Int {
      val var3: Boolean = var0.isEmpty();
      var var2: Int = 0;
      if (var3) {
         return 0;
      } else {
         val var4: java.util.Iterator = var0.entrySet().iterator();

         while (var4.hasNext()) {
            if (var1.invoke(var4.next() as java.util.Map.Entry) as java.lang.Boolean) {
               var2++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <K, V, R : Any> Map<out K, V>.firstNotNullOf(transform: (Entry<K, V>) -> R?): R {
      val var3: java.util.Iterator = var0.entrySet().iterator();

      while (true) {
         if (var3.hasNext()) {
            val var2: Any = var1.invoke(var3.next() as java.util.Map.Entry);
            var4 = var2;
            if (var2 == null) {
               continue;
            }
            break;
         }

         var4 = null;
         break;
      }

      if (var4 != null) {
         return (R)var4;
      } else {
         throw new NoSuchElementException("No element of the map was transformed to a non-null value.");
      }
   }

   @JvmStatic
   public inline fun <K, V, R : Any> Map<out K, V>.firstNotNullOfOrNull(transform: (Entry<K, V>) -> R?): R? {
      val var3: java.util.Iterator = var0.entrySet().iterator();

      while (var3.hasNext()) {
         val var2: Any = var1.invoke(var3.next() as java.util.Map.Entry);
         if (var2 != null) {
            return (R)var2;
         }
      }

      return null;
   }

   @JvmStatic
   public inline fun <K, V, R> Map<out K, V>.flatMap(transform: (Entry<K, V>) -> Iterable<R>): List<R> {
      val var2: java.util.Collection = new ArrayList();
      val var3: java.util.Iterator = var0.entrySet().iterator();

      while (var3.hasNext()) {
         CollectionsKt.addAll(var2, var1.invoke(var3.next() as java.util.Map.Entry) as java.lang.Iterable);
      }

      return var2 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <K, V, R> Map<out K, V>.flatMap(transform: (Entry<K, V>) -> Sequence<R>): List<R> {
      val var2: java.util.Collection = new ArrayList();
      val var3: java.util.Iterator = var0.entrySet().iterator();

      while (var3.hasNext()) {
         CollectionsKt.addAll(var2, var1.invoke(var3.next() as java.util.Map.Entry) as Sequence);
      }

      return var2 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <K, V, R, C : MutableCollection<in R>> Map<out K, V>.flatMapTo(destination: C, transform: (Entry<K, V>) -> Sequence<R>): C {
      val var3: java.util.Iterator = var0.entrySet().iterator();

      while (var3.hasNext()) {
         CollectionsKt.addAll(var1, var2.invoke(var3.next() as java.util.Map.Entry) as Sequence);
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <K, V, R, C : MutableCollection<in R>> Map<out K, V>.flatMapTo(destination: C, transform: (Entry<K, V>) -> Iterable<R>): C {
      val var3: java.util.Iterator = var0.entrySet().iterator();

      while (var3.hasNext()) {
         CollectionsKt.addAll(var1, var2.invoke(var3.next() as java.util.Map.Entry) as java.lang.Iterable);
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <K, V> Map<out K, V>.forEach(action: (Entry<K, V>) -> Unit) {
      val var2: java.util.Iterator = var0.entrySet().iterator();

      while (var2.hasNext()) {
         var1.invoke(var2.next() as java.util.Map.Entry);
      }
   }

   @JvmStatic
   public inline fun <K, V, R> Map<out K, V>.map(transform: (Entry<K, V>) -> R): List<R> {
      val var2: java.util.Collection = new ArrayList(var0.size());
      val var3: java.util.Iterator = var0.entrySet().iterator();

      while (var3.hasNext()) {
         var2.add(var1.invoke(var3.next() as java.util.Map.Entry));
      }

      return var2 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <K, V, R : Any> Map<out K, V>.mapNotNull(transform: (Entry<K, V>) -> R?): List<R> {
      val var2: java.util.Collection = new ArrayList();
      val var3: java.util.Iterator = var0.entrySet().iterator();

      while (var3.hasNext()) {
         val var4: Any = var1.invoke(var3.next() as java.util.Map.Entry);
         if (var4 != null) {
            var2.add(var4);
         }
      }

      return var2 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <K, V, R : Any, C : MutableCollection<in R>> Map<out K, V>.mapNotNullTo(destination: C, transform: (Entry<K, V>) -> R?): C {
      val var4: java.util.Iterator = var0.entrySet().iterator();

      while (var4.hasNext()) {
         val var3: Any = var2.invoke(var4.next() as java.util.Map.Entry);
         if (var3 != null) {
            var1.add(var3);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <K, V, R, C : MutableCollection<in R>> Map<out K, V>.mapTo(destination: C, transform: (Entry<K, V>) -> R): C {
      val var3: java.util.Iterator = var0.entrySet().iterator();

      while (var3.hasNext()) {
         var1.add(var2.invoke(var3.next() as java.util.Map.Entry));
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <K, V, R : Comparable<R>> Map<out K, V>.maxByOrNull(selector: (Entry<K, V>) -> R): Entry<K, V>? {
      val var7: java.util.Iterator = var0.entrySet().iterator();
      var var8: Any;
      if (!var7.hasNext()) {
         var8 = null;
      } else {
         var8 = var7.next();
         if (var7.hasNext()) {
            var var3: java.lang.Comparable = var1.invoke(var8) as java.lang.Comparable;
            var var4: Any = var8;

            do {
               val var6: Any = var7.next();
               val var5: java.lang.Comparable = var1.invoke(var6) as java.lang.Comparable;
               var8 = var4;
               var var2: java.lang.Comparable = var3;
               if (var3.compareTo(var5) < 0) {
                  var8 = var6;
                  var2 = var5;
               }

               var4 = var8;
               var3 = var2;
            } while (var7.hasNext());
         }
      }

      return var8 as MutableMap.MutableEntry<K, V>;
   }

   @JvmStatic
   public inline fun <K, V, R : Comparable<R>> Map<out K, V>.maxBy(selector: (Entry<K, V>) -> R): Entry<K, V> {
      val var7: java.util.Iterator = var0.entrySet().iterator();
      if (!var7.hasNext()) {
         throw new NoSuchElementException();
      } else {
         var var4: Any = var7.next();
         var var8: Any;
         if (!var7.hasNext()) {
            var8 = var4;
         } else {
            var var3: java.lang.Comparable = var1.invoke(var4) as java.lang.Comparable;

            do {
               val var6: Any = var7.next();
               val var5: java.lang.Comparable = var1.invoke(var6) as java.lang.Comparable;
               var8 = var4;
               var var2: java.lang.Comparable = var3;
               if (var3.compareTo(var5) < 0) {
                  var8 = var6;
                  var2 = var5;
               }

               var4 = var8;
               var3 = var2;
            } while (var7.hasNext());
         }

         return var8 as MutableMap.MutableEntry<K, V>;
      }
   }

   @JvmStatic
   public inline fun <K, V> Map<out K, V>.maxOf(selector: (Entry<K, V>) -> Double): Double {
      val var4: java.util.Iterator = var0.entrySet().iterator();
      if (!var4.hasNext()) {
         throw new NoSuchElementException();
      } else {
         var var2: Double = (var1.invoke(var4.next()) as java.lang.Number).doubleValue();

         while (var4.hasNext()) {
            var2 = Math.max(var2, (var1.invoke(var4.next()) as java.lang.Number).doubleValue());
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <K, V> Map<out K, V>.maxOf(selector: (Entry<K, V>) -> Float): Float {
      val var3: java.util.Iterator = var0.entrySet().iterator();
      if (!var3.hasNext()) {
         throw new NoSuchElementException();
      } else {
         var var2: Float = (var1.invoke(var3.next()) as java.lang.Number).floatValue();

         while (var3.hasNext()) {
            var2 = Math.max(var2, (var1.invoke(var3.next()) as java.lang.Number).floatValue());
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <K, V, R : Comparable<R>> Map<out K, V>.maxOf(selector: (Entry<K, V>) -> R): R {
      val var3: java.util.Iterator = var0.entrySet().iterator();
      if (var3.hasNext()) {
         var var4: java.lang.Comparable = var1.invoke(var3.next()) as java.lang.Comparable;

         while (var3.hasNext()) {
            val var2: java.lang.Comparable = var1.invoke(var3.next()) as java.lang.Comparable;
            if (var4.compareTo(var2) < 0) {
               var4 = var2;
            }
         }

         return (R)var4;
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <K, V, R : Comparable<R>> Map<out K, V>.maxOfOrNull(selector: (Entry<K, V>) -> R): R? {
      val var3: java.util.Iterator = var0.entrySet().iterator();
      var var4: java.lang.Comparable;
      if (!var3.hasNext()) {
         var4 = null;
      } else {
         var4 = var1.invoke(var3.next()) as java.lang.Comparable;

         while (var3.hasNext()) {
            val var2: java.lang.Comparable = var1.invoke(var3.next()) as java.lang.Comparable;
            if (var4.compareTo(var2) < 0) {
               var4 = var2;
            }
         }
      }

      return (R)var4;
   }

   @JvmStatic
   public inline fun <K, V> Map<out K, V>.maxOfOrNull(selector: (Entry<K, V>) -> Double): Double? {
      val var4: java.util.Iterator = var0.entrySet().iterator();
      val var5: java.lang.Double;
      if (!var4.hasNext()) {
         var5 = null;
      } else {
         var var2: Double = (var1.invoke(var4.next()) as java.lang.Number).doubleValue();

         while (var4.hasNext()) {
            var2 = Math.max(var2, (var1.invoke(var4.next()) as java.lang.Number).doubleValue());
         }

         var5 = var2;
      }

      return var5;
   }

   @JvmStatic
   public inline fun <K, V> Map<out K, V>.maxOfOrNull(selector: (Entry<K, V>) -> Float): Float? {
      val var3: java.util.Iterator = var0.entrySet().iterator();
      val var4: java.lang.Float;
      if (!var3.hasNext()) {
         var4 = null;
      } else {
         var var2: Float = (var1.invoke(var3.next()) as java.lang.Number).floatValue();

         while (var3.hasNext()) {
            var2 = Math.max(var2, (var1.invoke(var3.next()) as java.lang.Number).floatValue());
         }

         var4 = var2;
      }

      return var4;
   }

   @JvmStatic
   public inline fun <K, V, R> Map<out K, V>.maxOfWith(comparator: Comparator<in R>, selector: (Entry<K, V>) -> R): R {
      val var4: java.util.Iterator = var0.entrySet().iterator();
      if (var4.hasNext()) {
         var var5: Any = var2.invoke(var4.next());

         while (var4.hasNext()) {
            val var3: Any = var2.invoke(var4.next());
            if (var1.compare(var5, var3) < 0) {
               var5 = var3;
            }
         }

         return (R)var5;
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <K, V, R> Map<out K, V>.maxOfWithOrNull(comparator: Comparator<in R>, selector: (Entry<K, V>) -> R): R? {
      val var4: java.util.Iterator = var0.entrySet().iterator();
      var var5: Any;
      if (!var4.hasNext()) {
         var5 = null;
      } else {
         var5 = var2.invoke(var4.next());

         while (var4.hasNext()) {
            val var3: Any = var2.invoke(var4.next());
            if (var1.compare(var5, var3) < 0) {
               var5 = var3;
            }
         }
      }

      return (R)var5;
   }

   @JvmStatic
   public inline fun <K, V> Map<out K, V>.maxWithOrNull(comparator: Comparator<in Entry<K, V>>): Entry<K, V>? {
      return CollectionsKt.maxWithOrNull(var0.entrySet(), var1) as MutableMap.MutableEntry<K, V>;
   }

   @JvmStatic
   public inline fun <K, V> Map<out K, V>.maxWith(comparator: Comparator<in Entry<K, V>>): Entry<K, V> {
      return CollectionsKt.maxWithOrThrow(var0.entrySet(), var1) as MutableMap.MutableEntry<K, V>;
   }

   @JvmStatic
   public inline fun <K, V, R : Comparable<R>> Map<out K, V>.minByOrNull(selector: (Entry<K, V>) -> R): Entry<K, V>? {
      val var7: java.util.Iterator = var0.entrySet().iterator();
      var var8: Any;
      if (!var7.hasNext()) {
         var8 = null;
      } else {
         var8 = var7.next();
         if (var7.hasNext()) {
            var var3: java.lang.Comparable = var1.invoke(var8) as java.lang.Comparable;
            var var4: Any = var8;

            do {
               val var6: Any = var7.next();
               val var5: java.lang.Comparable = var1.invoke(var6) as java.lang.Comparable;
               var8 = var4;
               var var2: java.lang.Comparable = var3;
               if (var3.compareTo(var5) > 0) {
                  var8 = var6;
                  var2 = var5;
               }

               var4 = var8;
               var3 = var2;
            } while (var7.hasNext());
         }
      }

      return var8 as MutableMap.MutableEntry<K, V>;
   }

   @JvmStatic
   public inline fun <K, V, R : Comparable<R>> Map<out K, V>.minBy(selector: (Entry<K, V>) -> R): Entry<K, V> {
      val var7: java.util.Iterator = var0.entrySet().iterator();
      if (!var7.hasNext()) {
         throw new NoSuchElementException();
      } else {
         var var4: Any = var7.next();
         var var8: Any;
         if (!var7.hasNext()) {
            var8 = var4;
         } else {
            var var3: java.lang.Comparable = var1.invoke(var4) as java.lang.Comparable;

            do {
               val var6: Any = var7.next();
               val var5: java.lang.Comparable = var1.invoke(var6) as java.lang.Comparable;
               var8 = var4;
               var var2: java.lang.Comparable = var3;
               if (var3.compareTo(var5) > 0) {
                  var8 = var6;
                  var2 = var5;
               }

               var4 = var8;
               var3 = var2;
            } while (var7.hasNext());
         }

         return var8 as MutableMap.MutableEntry<K, V>;
      }
   }

   @JvmStatic
   public inline fun <K, V> Map<out K, V>.minOf(selector: (Entry<K, V>) -> Double): Double {
      val var4: java.util.Iterator = var0.entrySet().iterator();
      if (!var4.hasNext()) {
         throw new NoSuchElementException();
      } else {
         var var2: Double = (var1.invoke(var4.next()) as java.lang.Number).doubleValue();

         while (var4.hasNext()) {
            var2 = Math.min(var2, (var1.invoke(var4.next()) as java.lang.Number).doubleValue());
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <K, V> Map<out K, V>.minOf(selector: (Entry<K, V>) -> Float): Float {
      val var3: java.util.Iterator = var0.entrySet().iterator();
      if (!var3.hasNext()) {
         throw new NoSuchElementException();
      } else {
         var var2: Float = (var1.invoke(var3.next()) as java.lang.Number).floatValue();

         while (var3.hasNext()) {
            var2 = Math.min(var2, (var1.invoke(var3.next()) as java.lang.Number).floatValue());
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <K, V, R : Comparable<R>> Map<out K, V>.minOf(selector: (Entry<K, V>) -> R): R {
      val var3: java.util.Iterator = var0.entrySet().iterator();
      if (var3.hasNext()) {
         var var4: java.lang.Comparable = var1.invoke(var3.next()) as java.lang.Comparable;

         while (var3.hasNext()) {
            val var2: java.lang.Comparable = var1.invoke(var3.next()) as java.lang.Comparable;
            if (var4.compareTo(var2) > 0) {
               var4 = var2;
            }
         }

         return (R)var4;
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <K, V, R : Comparable<R>> Map<out K, V>.minOfOrNull(selector: (Entry<K, V>) -> R): R? {
      val var3: java.util.Iterator = var0.entrySet().iterator();
      var var4: java.lang.Comparable;
      if (!var3.hasNext()) {
         var4 = null;
      } else {
         var4 = var1.invoke(var3.next()) as java.lang.Comparable;

         while (var3.hasNext()) {
            val var2: java.lang.Comparable = var1.invoke(var3.next()) as java.lang.Comparable;
            if (var4.compareTo(var2) > 0) {
               var4 = var2;
            }
         }
      }

      return (R)var4;
   }

   @JvmStatic
   public inline fun <K, V> Map<out K, V>.minOfOrNull(selector: (Entry<K, V>) -> Double): Double? {
      val var4: java.util.Iterator = var0.entrySet().iterator();
      val var5: java.lang.Double;
      if (!var4.hasNext()) {
         var5 = null;
      } else {
         var var2: Double = (var1.invoke(var4.next()) as java.lang.Number).doubleValue();

         while (var4.hasNext()) {
            var2 = Math.min(var2, (var1.invoke(var4.next()) as java.lang.Number).doubleValue());
         }

         var5 = var2;
      }

      return var5;
   }

   @JvmStatic
   public inline fun <K, V> Map<out K, V>.minOfOrNull(selector: (Entry<K, V>) -> Float): Float? {
      val var3: java.util.Iterator = var0.entrySet().iterator();
      val var4: java.lang.Float;
      if (!var3.hasNext()) {
         var4 = null;
      } else {
         var var2: Float = (var1.invoke(var3.next()) as java.lang.Number).floatValue();

         while (var3.hasNext()) {
            var2 = Math.min(var2, (var1.invoke(var3.next()) as java.lang.Number).floatValue());
         }

         var4 = var2;
      }

      return var4;
   }

   @JvmStatic
   public inline fun <K, V, R> Map<out K, V>.minOfWith(comparator: Comparator<in R>, selector: (Entry<K, V>) -> R): R {
      val var4: java.util.Iterator = var0.entrySet().iterator();
      if (var4.hasNext()) {
         var var5: Any = var2.invoke(var4.next());

         while (var4.hasNext()) {
            val var3: Any = var2.invoke(var4.next());
            if (var1.compare(var5, var3) > 0) {
               var5 = var3;
            }
         }

         return (R)var5;
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <K, V, R> Map<out K, V>.minOfWithOrNull(comparator: Comparator<in R>, selector: (Entry<K, V>) -> R): R? {
      val var4: java.util.Iterator = var0.entrySet().iterator();
      var var5: Any;
      if (!var4.hasNext()) {
         var5 = null;
      } else {
         var5 = var2.invoke(var4.next());

         while (var4.hasNext()) {
            val var3: Any = var2.invoke(var4.next());
            if (var1.compare(var5, var3) > 0) {
               var5 = var3;
            }
         }
      }

      return (R)var5;
   }

   @JvmStatic
   public inline fun <K, V> Map<out K, V>.minWithOrNull(comparator: Comparator<in Entry<K, V>>): Entry<K, V>? {
      return CollectionsKt.minWithOrNull(var0.entrySet(), var1) as MutableMap.MutableEntry<K, V>;
   }

   @JvmStatic
   public inline fun <K, V> Map<out K, V>.minWith(comparator: Comparator<in Entry<K, V>>): Entry<K, V> {
      return CollectionsKt.minWithOrThrow(var0.entrySet(), var1) as MutableMap.MutableEntry<K, V>;
   }

   @JvmStatic
   public fun <K, V> Map<out K, V>.none(): Boolean {
      return var0.isEmpty();
   }

   @JvmStatic
   public inline fun <K, V> Map<out K, V>.none(predicate: (Entry<K, V>) -> Boolean): Boolean {
      if (var0.isEmpty()) {
         return true;
      } else {
         val var2: java.util.Iterator = var0.entrySet().iterator();

         while (var2.hasNext()) {
            if (var1.invoke(var2.next() as java.util.Map.Entry) as java.lang.Boolean) {
               return false;
            }
         }

         return true;
      }
   }

   @JvmStatic
   public inline fun <K, V, M : Map<out K, V>> M.onEach(action: (Entry<K, V>) -> Unit): M {
      val var2: java.util.Iterator = var0.entrySet().iterator();

      while (var2.hasNext()) {
         var1.invoke(var2.next() as java.util.Map.Entry);
      }

      return (M)var0;
   }

   @JvmStatic
   public inline fun <K, V, M : Map<out K, V>> M.onEachIndexed(action: (Int, Entry<K, V>) -> Unit): M {
      val var3: java.util.Iterator = var0.entrySet().iterator();

      for (int var2 = 0; var3.hasNext(); var2++) {
         val var4: Any = var3.next();
         if (var2 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         var1.invoke(var2, var4);
      }

      return (M)var0;
   }

   @JvmStatic
   public fun <K, V> Map<out K, V>.toList(): List<Pair<K, V>> {
      if (var0.size() == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var1: java.util.Iterator = var0.entrySet().iterator();
         if (!var1.hasNext()) {
            return CollectionsKt.emptyList();
         } else {
            var var2: java.util.Map.Entry = var1.next() as java.util.Map.Entry;
            if (!var1.hasNext()) {
               return CollectionsKt.listOf(new Pair<>(var2.getKey(), var2.getValue()));
            } else {
               val var3: ArrayList = new ArrayList(var0.size());
               var3.add(new Pair<>(var2.getKey(), var2.getValue()));

               do {
                  var2 = var1.next() as java.util.Map.Entry;
                  var3.add(new Pair<>(var2.getKey(), var2.getValue()));
               } while (var1.hasNext());

               return var3;
            }
         }
      }
   }
}
