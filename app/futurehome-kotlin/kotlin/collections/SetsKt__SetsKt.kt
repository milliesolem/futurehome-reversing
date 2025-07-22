package kotlin.collections

import java.util.HashSet
import java.util.LinkedHashSet
import kotlin.contracts.InvocationKind

internal class SetsKt__SetsKt : SetsKt__SetsJVMKt {
   open fun SetsKt__SetsKt() {
   }

   @JvmStatic
   public inline fun <E> buildSet(capacity: Int, builderAction: (MutableSet<E>) -> Unit): Set<E> {
      contract {
         callsInPlace(builderAction, InvocationKind.EXACTLY_ONCE)
      }

      val var2: java.util.Set = SetsKt.createSetBuilder(var0);
      var1.invoke(var2);
      return SetsKt.build(var2);
   }

   @JvmStatic
   public inline fun <E> buildSet(builderAction: (MutableSet<E>) -> Unit): Set<E> {
      contract {
         callsInPlace(builderAction, InvocationKind.EXACTLY_ONCE)
      }

      val var1: java.util.Set = SetsKt.createSetBuilder();
      var0.invoke(var1);
      return SetsKt.build(var1);
   }

   @JvmStatic
   public fun <T> emptySet(): Set<T> {
      return EmptySet.INSTANCE;
   }

   @JvmStatic
   public inline fun <T> hashSetOf(): HashSet<T> {
      return new HashSet();
   }

   @JvmStatic
   public fun <T> hashSetOf(vararg elements: T): HashSet<T> {
      return ArraysKt.toCollection(var0, new HashSet(MapsKt.mapCapacity(var0.length))) as HashSet<T>;
   }

   @JvmStatic
   public inline fun <T> linkedSetOf(): LinkedHashSet<T> {
      return new LinkedHashSet();
   }

   @JvmStatic
   public fun <T> linkedSetOf(vararg elements: T): LinkedHashSet<T> {
      return ArraysKt.toCollection(var0, new LinkedHashSet(MapsKt.mapCapacity(var0.length))) as LinkedHashSet<T>;
   }

   @JvmStatic
   public inline fun <T> mutableSetOf(): MutableSet<T> {
      return new LinkedHashSet();
   }

   @JvmStatic
   public fun <T> mutableSetOf(vararg elements: T): MutableSet<T> {
      return ArraysKt.toCollection(var0, new LinkedHashSet(MapsKt.mapCapacity(var0.length))) as MutableSet<T>;
   }

   @JvmStatic
   internal fun <T> Set<T>.optimizeReadOnlySet(): Set<T> {
      val var1: Int = var0.size();
      if (var1 != 0) {
         if (var1 == 1) {
            var0 = SetsKt.setOf(var0.iterator().next());
         }
      } else {
         var0 = SetsKt.emptySet();
      }

      return var0;
   }

   @JvmStatic
   public inline fun <T> Set<T>?.orEmpty(): Set<T> {
      var var1: java.util.Set = var0;
      if (var0 == null) {
         var1 = SetsKt.emptySet();
      }

      return var1;
   }

   @JvmStatic
   public inline fun <T> setOf(): Set<T> {
      return SetsKt.emptySet();
   }

   @JvmStatic
   public fun <T> setOf(vararg elements: T): Set<T> {
      return (java.util.Set<T>)ArraysKt.toSet(var0);
   }

   @JvmStatic
   public fun <T : Any> setOfNotNull(element: T?): Set<T> {
      if (var0 != null) {
         var0 = SetsKt.setOf(var0);
      } else {
         var0 = SetsKt.emptySet();
      }

      return var0;
   }

   @JvmStatic
   public fun <T : Any> setOfNotNull(vararg elements: T?): Set<T> {
      return ArraysKt.filterNotNullTo(var0, new LinkedHashSet()) as MutableSet<T>;
   }
}
