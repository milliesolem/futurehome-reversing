package kotlin.collections

import java.util.ArrayList
import java.util.Comparator
import kotlin.contracts.InvocationKind
import kotlin.jvm.functions.Function1
import kotlin.random.Random

internal class CollectionsKt__CollectionsKt : CollectionsKt__CollectionsJVMKt {
   public final val indices: IntRange
      public final get() {
         return new IntRange(0, var0.size() - 1);
      }


   public final val lastIndex: Int
      public final get() {
         return var0.size() - 1;
      }


   open fun CollectionsKt__CollectionsKt() {
   }

   @JvmStatic
   public inline fun <T> List(size: Int, init: (Int) -> T): List<T> {
      val var3: ArrayList = new ArrayList(var0);

      for (int var2 = 0; var2 < var0; var2++) {
         var3.add(var1.invoke(var2));
      }

      return var3;
   }

   @JvmStatic
   public inline fun <T> MutableList(size: Int, init: (Int) -> T): MutableList<T> {
      val var3: ArrayList = new ArrayList(var0);

      for (int var2 = 0; var2 < var0; var2++) {
         var3.add(var1.invoke(var2));
      }

      return var3;
   }

   @JvmStatic
   public inline fun <T> arrayListOf(): ArrayList<T> {
      return new ArrayList();
   }

   @JvmStatic
   public fun <T> arrayListOf(vararg elements: T): ArrayList<T> {
      val var1: ArrayList;
      if (var0.length == 0) {
         var1 = new ArrayList();
      } else {
         var1 = new ArrayList<>(new ArrayAsCollection<>(var0, true));
      }

      return var1;
   }

   @JvmStatic
   internal fun <T> Array<out T>.asCollection(): Collection<T> {
      return (java.util.Collection<T>)(new ArrayAsCollection<>(var0, false));
   }

   @JvmStatic
   public fun <T> List<T>.binarySearch(fromIndex: Int = 0, toIndex: Int = var0.size(), comparison: (T) -> Int): Int {
      rangeCheck$CollectionsKt__CollectionsKt(var0.size(), var1, var2);
      var2--;

      while (var1 <= var2) {
         val var4: Int = var1 + var2 ushr 1;
         val var5: Int = (var3.invoke(var0.get(var1 + var2 ushr 1)) as java.lang.Number).intValue();
         if (var5 < 0) {
            var1 = var4 + 1;
         } else {
            if (var5 <= 0) {
               return var4;
            }

            var2 = var4 - 1;
         }
      }

      return -(var1 + 1);
   }

   @JvmStatic
   public fun <T : Comparable<T>> List<T?>.binarySearch(element: T?, fromIndex: Int = 0, toIndex: Int = var0.size()): Int {
      rangeCheck$CollectionsKt__CollectionsKt(var0.size(), var2, var3);
      var3--;

      while (var2 <= var3) {
         val var4: Int = var2 + var3 ushr 1;
         val var5: Int = ComparisonsKt.compareValues(var0.get(var2 + var3 ushr 1) as java.lang.Comparable, var1);
         if (var5 < 0) {
            var2 = var4 + 1;
         } else {
            if (var5 <= 0) {
               return var4;
            }

            var3 = var4 - 1;
         }
      }

      return -(var2 + 1);
   }

   @JvmStatic
   public fun <T> List<T>.binarySearch(element: T, comparator: Comparator<in T>, fromIndex: Int = 0, toIndex: Int = var0.size()): Int {
      rangeCheck$CollectionsKt__CollectionsKt(var0.size(), var3, var4);
      var4--;

      while (var3 <= var4) {
         val var5: Int = var3 + var4 ushr 1;
         val var6: Int = var2.compare(var0.get(var3 + var4 ushr 1), var1);
         if (var6 < 0) {
            var3 = var5 + 1;
         } else {
            if (var6 <= 0) {
               return var5;
            }

            var4 = var5 - 1;
         }
      }

      return -(var3 + 1);
   }

   @JvmStatic
   public inline fun <T, K : Comparable<K>> List<T>.binarySearchBy(key: K?, fromIndex: Int = 0, toIndex: Int = var0.size(), crossinline selector: (T) -> K?): Int {
      return CollectionsKt.binarySearch(var0, var2, var3, new Function1<T, Integer>(var4, var1) {
         final K $key;
         final Function1<T, K> $selector;

         {
            this.$selector = var1;
            this.$key = (K)var2;
         }

         public final Integer invoke(T var1) {
            return ComparisonsKt.compareValues(this.$selector.invoke(var1) as java.lang.Comparable, this.$key);
         }
      });
   }

   @JvmStatic
   public inline fun <E> buildList(capacity: Int, builderAction: (MutableList<E>) -> Unit): List<E> {
      contract {
         callsInPlace(builderAction, InvocationKind.EXACTLY_ONCE)
      }

      val var2: java.util.List = CollectionsKt.createListBuilder(var0);
      var1.invoke(var2);
      return CollectionsKt.build(var2);
   }

   @JvmStatic
   public inline fun <E> buildList(builderAction: (MutableList<E>) -> Unit): List<E> {
      contract {
         callsInPlace(builderAction, InvocationKind.EXACTLY_ONCE)
      }

      val var1: java.util.List = CollectionsKt.createListBuilder();
      var0.invoke(var1);
      return CollectionsKt.build(var1);
   }

   @JvmStatic
   internal fun collectionToArrayCommonImpl(collection: Collection<*>): Array<Any?> {
      val var2: Boolean = var0.isEmpty();
      var var1: Int = 0;
      if (var2) {
         return new Object[0];
      } else {
         val var3: Array<Any> = new Object[var0.size()];

         for (java.util.Iterator var4 = var0.iterator(); var4.hasNext(); var1++) {
            var3[var1] = var4.next();
         }

         return var3;
      }
   }

   @JvmStatic
   internal fun <T> collectionToArrayCommonImpl(collection: Collection<*>, array: Array<T>): Array<T> {
      val var3: Boolean = var0.isEmpty();
      var var2: Int = 0;
      if (var3) {
         return (T[])CollectionsKt.terminateCollectionToArray(0, var1);
      } else {
         var var4: Array<Any> = var1;
         if (var1.length < var0.size()) {
            var4 = ArraysKt.arrayOfNulls(var1, var0.size());
         }

         for (java.util.Iterator var5 = var0.iterator(); var5.hasNext(); var2++) {
            var4[var2] = var5.next();
         }

         return (T[])CollectionsKt.terminateCollectionToArray(var0.size(), var4);
      }
   }

   @JvmStatic
   public inline fun <T> Collection<T>.containsAll(elements: Collection<T>): Boolean {
      return var0.containsAll(var1);
   }

   @JvmStatic
   public fun <T> emptyList(): List<T> {
      return EmptyList.INSTANCE;
   }

   @JvmStatic
   public inline fun <C, R> C.ifEmpty(defaultValue: () -> R): R where C : Collection<*>, C : R {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      var var2: Any = var0;
      if (var0.isEmpty()) {
         var2 = var1.invoke();
      }

      return (R)var2;
   }

   @JvmStatic
   public inline fun <T> Collection<T>.isNotEmpty(): Boolean {
      return var0.isEmpty() xor true;
   }

   @JvmStatic
   public inline fun <T> Collection<T>?.isNullOrEmpty(): Boolean {
      contract {
         returns(false) implies (this != null)
      }

      val var1: Boolean;
      if (var0 != null && !var0.isEmpty()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   @JvmStatic
   public inline fun <T> listOf(): List<T> {
      return CollectionsKt.emptyList();
   }

   @JvmStatic
   public fun <T> listOf(vararg elements: T): List<T> {
      val var1: java.util.List;
      if (var0.length > 0) {
         var1 = ArraysKt.asList(var0);
      } else {
         var1 = CollectionsKt.emptyList();
      }

      return var1;
   }

   @JvmStatic
   public fun <T : Any> listOfNotNull(element: T?): List<T> {
      if (var0 != null) {
         var0 = CollectionsKt.listOf(var0);
      } else {
         var0 = CollectionsKt.emptyList();
      }

      return var0;
   }

   @JvmStatic
   public fun <T : Any> listOfNotNull(vararg elements: T?): List<T> {
      return (java.util.List<T>)ArraysKt.filterNotNull(var0);
   }

   @JvmStatic
   public inline fun <T> mutableListOf(): MutableList<T> {
      return new ArrayList();
   }

   @JvmStatic
   public fun <T> mutableListOf(vararg elements: T): MutableList<T> {
      val var1: java.util.List;
      if (var0.length == 0) {
         var1 = new ArrayList();
      } else {
         var1 = new ArrayList<>(new ArrayAsCollection<>(var0, true));
      }

      return var1;
   }

   @JvmStatic
   internal fun <T> List<T>.optimizeReadOnlyList(): List<T> {
      val var1: Int = var0.size();
      if (var1 != 0) {
         if (var1 == 1) {
            var0 = CollectionsKt.listOf(var0.get(0));
         }
      } else {
         var0 = CollectionsKt.emptyList();
      }

      return var0;
   }

   @JvmStatic
   public inline fun <T> Collection<T>?.orEmpty(): Collection<T> {
      var var1: java.util.Collection = var0;
      if (var0 == null) {
         var1 = CollectionsKt.emptyList();
      }

      return var1;
   }

   @JvmStatic
   public inline fun <T> List<T>?.orEmpty(): List<T> {
      var var1: java.util.List = var0;
      if (var0 == null) {
         var1 = CollectionsKt.emptyList();
      }

      return var1;
   }

   @JvmStatic
   private fun rangeCheck(size: Int, fromIndex: Int, toIndex: Int) {
      if (var1 <= var2) {
         if (var1 >= 0) {
            if (var2 > var0) {
               val var5: StringBuilder = new StringBuilder("toIndex (");
               var5.append(var2);
               var5.append(") is greater than size (");
               var5.append(var0);
               var5.append(").");
               throw new IndexOutOfBoundsException(var5.toString());
            }
         } else {
            val var4: StringBuilder = new StringBuilder("fromIndex (");
            var4.append(var1);
            var4.append(") is less than zero.");
            throw new IndexOutOfBoundsException(var4.toString());
         }
      } else {
         val var3: StringBuilder = new StringBuilder("fromIndex (");
         var3.append(var1);
         var3.append(") is greater than toIndex (");
         var3.append(var2);
         var3.append(").");
         throw new IllegalArgumentException(var3.toString());
      }
   }

   @JvmStatic
   public fun <T> Iterable<T>.shuffled(random: Random): List<T> {
      val var2: java.util.List = CollectionsKt.toMutableList(var0);
      CollectionsKt.shuffle(var2, var1);
      return var2;
   }

   @JvmStatic
   internal fun throwCountOverflow() {
      throw new ArithmeticException("Count overflow has happened.");
   }

   @JvmStatic
   internal fun throwIndexOverflow() {
      throw new ArithmeticException("Index overflow has happened.");
   }
}
