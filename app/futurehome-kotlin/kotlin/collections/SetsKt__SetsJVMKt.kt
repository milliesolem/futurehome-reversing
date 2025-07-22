package kotlin.collections

import java.util.Collections
import java.util.Comparator
import java.util.TreeSet
import kotlin.collections.builders.SetBuilder

internal class SetsKt__SetsJVMKt {
   open fun SetsKt__SetsJVMKt() {
   }

   @JvmStatic
   internal fun <E> build(builder: MutableSet<E>): Set<E> {
      return (var0 as SetBuilder).build();
   }

   @JvmStatic
   internal inline fun <E> buildSetInternal(capacity: Int, builderAction: (MutableSet<E>) -> Unit): Set<E> {
      val var2: java.util.Set = SetsKt.createSetBuilder(var0);
      var1.invoke(var2);
      return SetsKt.build(var2);
   }

   @JvmStatic
   internal inline fun <E> buildSetInternal(builderAction: (MutableSet<E>) -> Unit): Set<E> {
      val var1: java.util.Set = SetsKt.createSetBuilder();
      var0.invoke(var1);
      return SetsKt.build(var1);
   }

   @JvmStatic
   internal fun <E> createSetBuilder(): MutableSet<E> {
      return new SetBuilder();
   }

   @JvmStatic
   internal fun <E> createSetBuilder(capacity: Int): MutableSet<E> {
      return new SetBuilder(var0);
   }

   @JvmStatic
   public fun <T> setOf(element: T): Set<T> {
      var0 = Collections.singleton(var0);
      return var0;
   }

   @JvmStatic
   public fun <T> sortedSetOf(comparator: Comparator<in T>, vararg elements: T): TreeSet<T> {
      return ArraysKt.toCollection(var1, new TreeSet(var0)) as TreeSet<T>;
   }

   @JvmStatic
   public fun <T> sortedSetOf(vararg elements: T): TreeSet<T> {
      return ArraysKt.toCollection(var0, new TreeSet()) as TreeSet<T>;
   }
}
