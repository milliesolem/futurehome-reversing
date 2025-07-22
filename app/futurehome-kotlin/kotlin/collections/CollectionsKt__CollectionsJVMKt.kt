package kotlin.collections

import java.util.ArrayList
import java.util.Arrays
import java.util.Collections
import java.util.Enumeration
import java.util.Random
import kotlin.collections.builders.ListBuilder
import kotlin.internal.PlatformImplementationsKt
import kotlin.jvm.internal.CollectionToArray

internal class CollectionsKt__CollectionsJVMKt {
   open fun CollectionsKt__CollectionsJVMKt() {
   }

   @JvmStatic
   internal fun <E> build(builder: MutableList<E>): List<E> {
      return (var0 as ListBuilder).build();
   }

   @JvmStatic
   internal inline fun <E> buildListInternal(capacity: Int, builderAction: (MutableList<E>) -> Unit): List<E> {
      val var2: java.util.List = CollectionsKt.createListBuilder(var0);
      var1.invoke(var2);
      return CollectionsKt.build(var2);
   }

   @JvmStatic
   internal inline fun <E> buildListInternal(builderAction: (MutableList<E>) -> Unit): List<E> {
      val var1: java.util.List = CollectionsKt.createListBuilder();
      var0.invoke(var1);
      return CollectionsKt.build(var1);
   }

   @JvmStatic
   internal inline fun checkCountOverflow(count: Int): Int {
      if (var0 < 0) {
         if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            throw new ArithmeticException("Count overflow has happened.");
         }

         CollectionsKt.throwCountOverflow();
      }

      return var0;
   }

   @JvmStatic
   internal inline fun checkIndexOverflow(index: Int): Int {
      if (var0 < 0) {
         if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            throw new ArithmeticException("Index overflow has happened.");
         }

         CollectionsKt.throwIndexOverflow();
      }

      return var0;
   }

   @JvmStatic
   internal inline fun collectionToArray(collection: Collection<*>): Array<Any?> {
      return CollectionToArray.toArray(var0);
   }

   @JvmStatic
   internal inline fun <T> collectionToArray(collection: Collection<*>, array: Array<T>): Array<T> {
      return (T[])CollectionToArray.toArray(var0, var1);
   }

   @JvmStatic
   internal fun <T> Array<out T>.copyToArrayOfAny(isVarargs: Boolean): Array<out Any?> {
      if (!var1 || !(var0.getClass() == Object[]::class.java)) {
         var0 = Arrays.copyOf(var0, var0.length, Object[].class);
      }

      return var0;
   }

   @JvmStatic
   internal fun <E> createListBuilder(): MutableList<E> {
      return new ListBuilder(0, 1, null);
   }

   @JvmStatic
   internal fun <E> createListBuilder(capacity: Int): MutableList<E> {
      return new ListBuilder(var0);
   }

   @JvmStatic
   public fun <T> listOf(element: T): List<T> {
      var0 = Collections.singletonList(var0);
      return var0;
   }

   @JvmStatic
   public fun <T> Iterable<T>.shuffled(): List<T> {
      val var1: java.util.List = CollectionsKt.toMutableList(var0);
      Collections.shuffle(var1);
      return var1;
   }

   @JvmStatic
   public fun <T> Iterable<T>.shuffled(random: Random): List<T> {
      val var2: java.util.List = CollectionsKt.toMutableList(var0);
      Collections.shuffle(var2, var1);
      return var2;
   }

   @JvmStatic
   internal fun <T> terminateCollectionToArray(collectionSize: Int, array: Array<T>): Array<T> {
      if (var0 < var1.length) {
         var1[var0] = null;
      }

      return (T[])var1;
   }

   @JvmStatic
   public inline fun <T> Enumeration<T>.toList(): List<T> {
      val var1: ArrayList = Collections.list(var0);
      return var1;
   }
}
