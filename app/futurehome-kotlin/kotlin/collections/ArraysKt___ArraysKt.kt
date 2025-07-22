package kotlin.collections

import java.util.ArrayList
import java.util.Arrays
import java.util.Comparator
import java.util.HashSet
import java.util.LinkedHashMap
import java.util.LinkedHashSet
import java.util.NoSuchElementException
import kotlin.contracts.InvocationKind
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.ArrayIteratorKt
import kotlin.jvm.internal.ArrayIteratorsKt
import kotlin.jvm.internal.Intrinsics
import kotlin.random.Random

internal class ArraysKt___ArraysKt : ArraysKt___ArraysJvmKt {
   public final val indices: IntRange
      public final get() {
         return new IntRange(0, ArraysKt.getLastIndex(var0));
      }


   public final val indices: IntRange
      public final get() {
         return new IntRange(0, ArraysKt.getLastIndex(var0));
      }


   public final val indices: IntRange
      public final get() {
         return new IntRange(0, ArraysKt.getLastIndex(var0));
      }


   public final val indices: IntRange
      public final get() {
         return new IntRange(0, ArraysKt.getLastIndex(var0));
      }


   public final val indices: IntRange
      public final get() {
         return new IntRange(0, ArraysKt.getLastIndex(var0));
      }


   public final val indices: IntRange
      public final get() {
         return new IntRange(0, ArraysKt.getLastIndex(var0));
      }


   public final val indices: IntRange
      public final get() {
         return new IntRange(0, ArraysKt.getLastIndex(var0));
      }


   public final val indices: IntRange
      public final get() {
         return new IntRange(0, ArraysKt.getLastIndex(var0));
      }


   public final val indices: IntRange
      public final get() {
         return new IntRange(0, ArraysKt.getLastIndex(var0));
      }


   public final val lastIndex: Int
      public final get() {
         return var0.length - 1;
      }


   public final val lastIndex: Int
      public final get() {
         return var0.length - 1;
      }


   public final val lastIndex: Int
      public final get() {
         return var0.length - 1;
      }


   public final val lastIndex: Int
      public final get() {
         return var0.length - 1;
      }


   public final val lastIndex: Int
      public final get() {
         return var0.length - 1;
      }


   public final val lastIndex: Int
      public final get() {
         return var0.length - 1;
      }


   public final val lastIndex: Int
      public final get() {
         return var0.length - 1;
      }


   public final val lastIndex: Int
      public final get() {
         return var0.length - 1;
      }


   public final val lastIndex: Int
      public final get() {
         return var0.length - 1;
      }


   open fun ArraysKt___ArraysKt() {
   }

   @JvmStatic
   public inline fun ByteArray.all(predicate: (Byte) -> Boolean): Boolean {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public inline fun CharArray.all(predicate: (Char) -> Boolean): Boolean {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public inline fun DoubleArray.all(predicate: (Double) -> Boolean): Boolean {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public inline fun FloatArray.all(predicate: (Float) -> Boolean): Boolean {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public inline fun IntArray.all(predicate: (Int) -> Boolean): Boolean {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public inline fun LongArray.all(predicate: (Long) -> Boolean): Boolean {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.all(predicate: (T) -> Boolean): Boolean {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public inline fun ShortArray.all(predicate: (Short) -> Boolean): Boolean {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public inline fun BooleanArray.all(predicate: (Boolean) -> Boolean): Boolean {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public fun ByteArray.any(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1 xor true;
   }

   @JvmStatic
   public inline fun ByteArray.any(predicate: (Byte) -> Boolean): Boolean {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return true;
         }
      }

      return false;
   }

   @JvmStatic
   public fun CharArray.any(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1 xor true;
   }

   @JvmStatic
   public inline fun CharArray.any(predicate: (Char) -> Boolean): Boolean {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return true;
         }
      }

      return false;
   }

   @JvmStatic
   public fun DoubleArray.any(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1 xor true;
   }

   @JvmStatic
   public inline fun DoubleArray.any(predicate: (Double) -> Boolean): Boolean {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return true;
         }
      }

      return false;
   }

   @JvmStatic
   public fun FloatArray.any(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1 xor true;
   }

   @JvmStatic
   public inline fun FloatArray.any(predicate: (Float) -> Boolean): Boolean {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return true;
         }
      }

      return false;
   }

   @JvmStatic
   public fun IntArray.any(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1 xor true;
   }

   @JvmStatic
   public inline fun IntArray.any(predicate: (Int) -> Boolean): Boolean {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return true;
         }
      }

      return false;
   }

   @JvmStatic
   public fun LongArray.any(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1 xor true;
   }

   @JvmStatic
   public inline fun LongArray.any(predicate: (Long) -> Boolean): Boolean {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return true;
         }
      }

      return false;
   }

   @JvmStatic
   public fun <T> Array<out T>.any(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1 xor true;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.any(predicate: (T) -> Boolean): Boolean {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return true;
         }
      }

      return false;
   }

   @JvmStatic
   public fun ShortArray.any(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1 xor true;
   }

   @JvmStatic
   public inline fun ShortArray.any(predicate: (Short) -> Boolean): Boolean {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return true;
         }
      }

      return false;
   }

   @JvmStatic
   public fun BooleanArray.any(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1 xor true;
   }

   @JvmStatic
   public inline fun BooleanArray.any(predicate: (Boolean) -> Boolean): Boolean {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return true;
         }
      }

      return false;
   }

   @JvmStatic
   public fun ByteArray.asIterable(): Iterable<Byte> {
      return (java.lang.Iterable<java.lang.Byte>)(if (var0.length == 0) CollectionsKt.emptyList() else new java.lang.Iterable<java.lang.Byte>(var0) {
         final byte[] $this_asIterable$inlined;

         {
            this.$this_asIterable$inlined = var1;
         }

         @Override
         public java.util.Iterator<java.lang.Byte> iterator() {
            return ArrayIteratorsKt.iterator(this.$this_asIterable$inlined);
         }
      });
   }

   @JvmStatic
   public fun CharArray.asIterable(): Iterable<Char> {
      return (java.lang.Iterable<Character>)(if (var0.length == 0) CollectionsKt.emptyList() else new java.lang.Iterable<Character>(var0) {
         final char[] $this_asIterable$inlined;

         {
            this.$this_asIterable$inlined = var1;
         }

         @Override
         public java.util.Iterator<Character> iterator() {
            return ArrayIteratorsKt.iterator(this.$this_asIterable$inlined);
         }
      });
   }

   @JvmStatic
   public fun DoubleArray.asIterable(): Iterable<Double> {
      return (java.lang.Iterable<java.lang.Double>)(if (var0.length == 0) CollectionsKt.emptyList() else new java.lang.Iterable<java.lang.Double>(var0) {
         final double[] $this_asIterable$inlined;

         {
            this.$this_asIterable$inlined = var1;
         }

         @Override
         public java.util.Iterator<java.lang.Double> iterator() {
            return ArrayIteratorsKt.iterator(this.$this_asIterable$inlined);
         }
      });
   }

   @JvmStatic
   public fun FloatArray.asIterable(): Iterable<Float> {
      return (java.lang.Iterable<java.lang.Float>)(if (var0.length == 0) CollectionsKt.emptyList() else new java.lang.Iterable<java.lang.Float>(var0) {
         final float[] $this_asIterable$inlined;

         {
            this.$this_asIterable$inlined = var1;
         }

         @Override
         public java.util.Iterator<java.lang.Float> iterator() {
            return ArrayIteratorsKt.iterator(this.$this_asIterable$inlined);
         }
      });
   }

   @JvmStatic
   public fun IntArray.asIterable(): Iterable<Int> {
      return (java.lang.Iterable<Integer>)(if (var0.length == 0) CollectionsKt.emptyList() else new java.lang.Iterable<Integer>(var0) {
         final int[] $this_asIterable$inlined;

         {
            this.$this_asIterable$inlined = var1;
         }

         @Override
         public java.util.Iterator<Integer> iterator() {
            return ArrayIteratorsKt.iterator(this.$this_asIterable$inlined);
         }
      });
   }

   @JvmStatic
   public fun LongArray.asIterable(): Iterable<Long> {
      return (java.lang.Iterable<java.lang.Long>)(if (var0.length == 0) CollectionsKt.emptyList() else new java.lang.Iterable<java.lang.Long>(var0) {
         final long[] $this_asIterable$inlined;

         {
            this.$this_asIterable$inlined = var1;
         }

         @Override
         public java.util.Iterator<java.lang.Long> iterator() {
            return ArrayIteratorsKt.iterator(this.$this_asIterable$inlined);
         }
      });
   }

   @JvmStatic
   public fun <T> Array<out T>.asIterable(): Iterable<T> {
      return (java.lang.Iterable<T>)(if (var0.length == 0) CollectionsKt.emptyList() else new java.lang.Iterable<T>(var0) {
         final Object[] $this_asIterable$inlined;

         {
            this.$this_asIterable$inlined = var1;
         }

         @Override
         public java.util.Iterator<T> iterator() {
            return (java.util.Iterator<T>)ArrayIteratorKt.iterator(this.$this_asIterable$inlined);
         }
      });
   }

   @JvmStatic
   public fun ShortArray.asIterable(): Iterable<Short> {
      return (java.lang.Iterable<java.lang.Short>)(if (var0.length == 0) CollectionsKt.emptyList() else new java.lang.Iterable<java.lang.Short>(var0) {
         final short[] $this_asIterable$inlined;

         {
            this.$this_asIterable$inlined = var1;
         }

         @Override
         public java.util.Iterator<java.lang.Short> iterator() {
            return ArrayIteratorsKt.iterator(this.$this_asIterable$inlined);
         }
      });
   }

   @JvmStatic
   public fun BooleanArray.asIterable(): Iterable<Boolean> {
      return (java.lang.Iterable<java.lang.Boolean>)(if (var0.length == 0) CollectionsKt.emptyList() else new java.lang.Iterable<java.lang.Boolean>(var0) {
         final boolean[] $this_asIterable$inlined;

         {
            this.$this_asIterable$inlined = var1;
         }

         @Override
         public java.util.Iterator<java.lang.Boolean> iterator() {
            return ArrayIteratorsKt.iterator(this.$this_asIterable$inlined);
         }
      });
   }

   @JvmStatic
   public fun ByteArray.asSequence(): Sequence<Byte> {
      return if (var0.length == 0) SequencesKt.emptySequence() else new Sequence<java.lang.Byte>(var0) {
         final byte[] $this_asSequence$inlined;

         {
            this.$this_asSequence$inlined = var1;
         }

         @Override
         public java.util.Iterator<java.lang.Byte> iterator() {
            return ArrayIteratorsKt.iterator(this.$this_asSequence$inlined);
         }
      };
   }

   @JvmStatic
   public fun CharArray.asSequence(): Sequence<Char> {
      return if (var0.length == 0) SequencesKt.emptySequence() else new Sequence<Character>(var0) {
         final char[] $this_asSequence$inlined;

         {
            this.$this_asSequence$inlined = var1;
         }

         @Override
         public java.util.Iterator<Character> iterator() {
            return ArrayIteratorsKt.iterator(this.$this_asSequence$inlined);
         }
      };
   }

   @JvmStatic
   public fun DoubleArray.asSequence(): Sequence<Double> {
      return if (var0.length == 0) SequencesKt.emptySequence() else new Sequence<java.lang.Double>(var0) {
         final double[] $this_asSequence$inlined;

         {
            this.$this_asSequence$inlined = var1;
         }

         @Override
         public java.util.Iterator<java.lang.Double> iterator() {
            return ArrayIteratorsKt.iterator(this.$this_asSequence$inlined);
         }
      };
   }

   @JvmStatic
   public fun FloatArray.asSequence(): Sequence<Float> {
      return if (var0.length == 0) SequencesKt.emptySequence() else new Sequence<java.lang.Float>(var0) {
         final float[] $this_asSequence$inlined;

         {
            this.$this_asSequence$inlined = var1;
         }

         @Override
         public java.util.Iterator<java.lang.Float> iterator() {
            return ArrayIteratorsKt.iterator(this.$this_asSequence$inlined);
         }
      };
   }

   @JvmStatic
   public fun IntArray.asSequence(): Sequence<Int> {
      return if (var0.length == 0) SequencesKt.emptySequence() else new Sequence<Integer>(var0) {
         final int[] $this_asSequence$inlined;

         {
            this.$this_asSequence$inlined = var1;
         }

         @Override
         public java.util.Iterator<Integer> iterator() {
            return ArrayIteratorsKt.iterator(this.$this_asSequence$inlined);
         }
      };
   }

   @JvmStatic
   public fun LongArray.asSequence(): Sequence<Long> {
      return if (var0.length == 0) SequencesKt.emptySequence() else new Sequence<java.lang.Long>(var0) {
         final long[] $this_asSequence$inlined;

         {
            this.$this_asSequence$inlined = var1;
         }

         @Override
         public java.util.Iterator<java.lang.Long> iterator() {
            return ArrayIteratorsKt.iterator(this.$this_asSequence$inlined);
         }
      };
   }

   @JvmStatic
   public fun <T> Array<out T>.asSequence(): Sequence<T> {
      return if (var0.length == 0) SequencesKt.emptySequence() else new Sequence<T>(var0) {
         final Object[] $this_asSequence$inlined;

         {
            this.$this_asSequence$inlined = var1;
         }

         @Override
         public java.util.Iterator<T> iterator() {
            return (java.util.Iterator<T>)ArrayIteratorKt.iterator(this.$this_asSequence$inlined);
         }
      };
   }

   @JvmStatic
   public fun ShortArray.asSequence(): Sequence<Short> {
      return if (var0.length == 0) SequencesKt.emptySequence() else new Sequence<java.lang.Short>(var0) {
         final short[] $this_asSequence$inlined;

         {
            this.$this_asSequence$inlined = var1;
         }

         @Override
         public java.util.Iterator<java.lang.Short> iterator() {
            return ArrayIteratorsKt.iterator(this.$this_asSequence$inlined);
         }
      };
   }

   @JvmStatic
   public fun BooleanArray.asSequence(): Sequence<Boolean> {
      return if (var0.length == 0) SequencesKt.emptySequence() else new Sequence<java.lang.Boolean>(var0) {
         final boolean[] $this_asSequence$inlined;

         {
            this.$this_asSequence$inlined = var1;
         }

         @Override
         public java.util.Iterator<java.lang.Boolean> iterator() {
            return ArrayIteratorsKt.iterator(this.$this_asSequence$inlined);
         }
      };
   }

   @JvmStatic
   public inline fun <K, V> ByteArray.associate(transform: (Byte) -> Pair<K, V>): Map<K, V> {
      val var4: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var5: Pair = var1.invoke(var0[var2]) as Pair;
         var4.put(var5.getFirst(), var5.getSecond());
      }

      return var4;
   }

   @JvmStatic
   public inline fun <K, V> CharArray.associate(transform: (Char) -> Pair<K, V>): Map<K, V> {
      val var4: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var5: Pair = var1.invoke(var0[var2]) as Pair;
         var4.put(var5.getFirst(), var5.getSecond());
      }

      return var4;
   }

   @JvmStatic
   public inline fun <K, V> DoubleArray.associate(transform: (Double) -> Pair<K, V>): Map<K, V> {
      val var4: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var5: Pair = var1.invoke(var0[var2]) as Pair;
         var4.put(var5.getFirst(), var5.getSecond());
      }

      return var4;
   }

   @JvmStatic
   public inline fun <K, V> FloatArray.associate(transform: (Float) -> Pair<K, V>): Map<K, V> {
      val var4: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var5: Pair = var1.invoke(var0[var2]) as Pair;
         var4.put(var5.getFirst(), var5.getSecond());
      }

      return var4;
   }

   @JvmStatic
   public inline fun <K, V> IntArray.associate(transform: (Int) -> Pair<K, V>): Map<K, V> {
      val var5: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Pair = var1.invoke(var0[var2]) as Pair;
         var5.put(var4.getFirst(), var4.getSecond());
      }

      return var5;
   }

   @JvmStatic
   public inline fun <K, V> LongArray.associate(transform: (Long) -> Pair<K, V>): Map<K, V> {
      val var5: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Pair = var1.invoke(var0[var2]) as Pair;
         var5.put(var4.getFirst(), var4.getSecond());
      }

      return var5;
   }

   @JvmStatic
   public inline fun <T, K, V> Array<out T>.associate(transform: (T) -> Pair<K, V>): Map<K, V> {
      val var4: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var5: Pair = var1.invoke(var0[var2]) as Pair;
         var4.put(var5.getFirst(), var5.getSecond());
      }

      return var4;
   }

   @JvmStatic
   public inline fun <K, V> ShortArray.associate(transform: (Short) -> Pair<K, V>): Map<K, V> {
      val var4: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var5: Pair = var1.invoke(var0[var2]) as Pair;
         var4.put(var5.getFirst(), var5.getSecond());
      }

      return var4;
   }

   @JvmStatic
   public inline fun <K, V> BooleanArray.associate(transform: (Boolean) -> Pair<K, V>): Map<K, V> {
      val var4: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var5: Pair = var1.invoke(var0[var2]) as Pair;
         var4.put(var5.getFirst(), var5.getSecond());
      }

      return var4;
   }

   @JvmStatic
   public inline fun <K> ByteArray.associateBy(keySelector: (Byte) -> K): Map<K, Byte> {
      val var5: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var5.put(var1.invoke(var0[var3]), var0[var3]);
      }

      return var5;
   }

   @JvmStatic
   public inline fun <K, V> ByteArray.associateBy(keySelector: (Byte) -> K, valueTransform: (Byte) -> V): Map<K, V> {
      val var6: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         var6.put(var1.invoke(var0[var4]), var2.invoke(var0[var4]));
      }

      return var6;
   }

   @JvmStatic
   public inline fun <K> CharArray.associateBy(keySelector: (Char) -> K): Map<K, Char> {
      val var5: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var5.put(var1.invoke(var0[var3]), var0[var3]);
      }

      return var5;
   }

   @JvmStatic
   public inline fun <K, V> CharArray.associateBy(keySelector: (Char) -> K, valueTransform: (Char) -> V): Map<K, V> {
      val var6: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         var6.put(var1.invoke(var0[var4]), var2.invoke(var0[var4]));
      }

      return var6;
   }

   @JvmStatic
   public inline fun <K> DoubleArray.associateBy(keySelector: (Double) -> K): Map<K, Double> {
      val var6: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         var6.put(var1.invoke(var0[var4]), var0[var4]);
      }

      return var6;
   }

   @JvmStatic
   public inline fun <K, V> DoubleArray.associateBy(keySelector: (Double) -> K, valueTransform: (Double) -> V): Map<K, V> {
      val var7: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var6: Int = var0.length;

      for (int var5 = 0; var5 < var6; var5++) {
         var7.put(var1.invoke(var0[var5]), var2.invoke(var0[var5]));
      }

      return var7;
   }

   @JvmStatic
   public inline fun <K> FloatArray.associateBy(keySelector: (Float) -> K): Map<K, Float> {
      val var5: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var5.put(var1.invoke(var0[var3]), var0[var3]);
      }

      return var5;
   }

   @JvmStatic
   public inline fun <K, V> FloatArray.associateBy(keySelector: (Float) -> K, valueTransform: (Float) -> V): Map<K, V> {
      val var6: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         var6.put(var1.invoke(var0[var4]), var2.invoke(var0[var4]));
      }

      return var6;
   }

   @JvmStatic
   public inline fun <K> IntArray.associateBy(keySelector: (Int) -> K): Map<K, Int> {
      val var5: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var5.put(var1.invoke(var0[var2]), var0[var2]);
      }

      return var5;
   }

   @JvmStatic
   public inline fun <K, V> IntArray.associateBy(keySelector: (Int) -> K, valueTransform: (Int) -> V): Map<K, V> {
      val var6: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var6.put(var1.invoke(var0[var3]), var2.invoke(var0[var3]));
      }

      return var6;
   }

   @JvmStatic
   public inline fun <K> LongArray.associateBy(keySelector: (Long) -> K): Map<K, Long> {
      val var6: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var6.put(var1.invoke(var0[var2]), var0[var2]);
      }

      return var6;
   }

   @JvmStatic
   public inline fun <K, V> LongArray.associateBy(keySelector: (Long) -> K, valueTransform: (Long) -> V): Map<K, V> {
      val var7: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var7.put(var1.invoke(var0[var3]), var2.invoke(var0[var3]));
      }

      return var7;
   }

   @JvmStatic
   public inline fun <T, K> Array<out T>.associateBy(keySelector: (T) -> K): Map<K, T> {
      val var4: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4.put(var1.invoke(var0[var2]), var0[var2]);
      }

      return var4;
   }

   @JvmStatic
   public inline fun <T, K, V> Array<out T>.associateBy(keySelector: (T) -> K, valueTransform: (T) -> V): Map<K, V> {
      val var6: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var6.put(var1.invoke(var0[var3]), var2.invoke(var0[var3]));
      }

      return var6;
   }

   @JvmStatic
   public inline fun <K> ShortArray.associateBy(keySelector: (Short) -> K): Map<K, Short> {
      val var5: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var5.put(var1.invoke(var0[var3]), var0[var3]);
      }

      return var5;
   }

   @JvmStatic
   public inline fun <K, V> ShortArray.associateBy(keySelector: (Short) -> K, valueTransform: (Short) -> V): Map<K, V> {
      val var6: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         var6.put(var1.invoke(var0[var4]), var2.invoke(var0[var4]));
      }

      return var6;
   }

   @JvmStatic
   public inline fun <K> BooleanArray.associateBy(keySelector: (Boolean) -> K): Map<K, Boolean> {
      val var5: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var5.put(var1.invoke(var0[var2]), var0[var2]);
      }

      return var5;
   }

   @JvmStatic
   public inline fun <K, V> BooleanArray.associateBy(keySelector: (Boolean) -> K, valueTransform: (Boolean) -> V): Map<K, V> {
      val var6: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var6.put(var1.invoke(var0[var3]), var2.invoke(var0[var3]));
      }

      return var6;
   }

   @JvmStatic
   public inline fun <K, M : MutableMap<in K, in Byte>> ByteArray.associateByTo(destination: M, keySelector: (Byte) -> K): M {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         var1.put(var2.invoke(var0[var4]), var0[var4]);
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, in V>> ByteArray.associateByTo(destination: M, keySelector: (Byte) -> K, valueTransform: (Byte) -> V): M {
      val var6: Int = var0.length;

      for (int var5 = 0; var5 < var6; var5++) {
         var1.put(var2.invoke(var0[var5]), var3.invoke(var0[var5]));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, M : MutableMap<in K, in Char>> CharArray.associateByTo(destination: M, keySelector: (Char) -> K): M {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         var1.put(var2.invoke(var0[var4]), var0[var4]);
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, in V>> CharArray.associateByTo(destination: M, keySelector: (Char) -> K, valueTransform: (Char) -> V): M {
      val var6: Int = var0.length;

      for (int var5 = 0; var5 < var6; var5++) {
         var1.put(var2.invoke(var0[var5]), var3.invoke(var0[var5]));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, M : MutableMap<in K, in Double>> DoubleArray.associateByTo(destination: M, keySelector: (Double) -> K): M {
      val var6: Int = var0.length;

      for (int var5 = 0; var5 < var6; var5++) {
         var1.put(var2.invoke(var0[var5]), var0[var5]);
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, in V>> DoubleArray.associateByTo(destination: M, keySelector: (Double) -> K, valueTransform: (Double) -> V): M {
      val var7: Int = var0.length;

      for (int var6 = 0; var6 < var7; var6++) {
         var1.put(var2.invoke(var0[var6]), var3.invoke(var0[var6]));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, M : MutableMap<in K, in Float>> FloatArray.associateByTo(destination: M, keySelector: (Float) -> K): M {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         var1.put(var2.invoke(var0[var4]), var0[var4]);
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, in V>> FloatArray.associateByTo(destination: M, keySelector: (Float) -> K, valueTransform: (Float) -> V): M {
      val var6: Int = var0.length;

      for (int var5 = 0; var5 < var6; var5++) {
         var1.put(var2.invoke(var0[var5]), var3.invoke(var0[var5]));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, M : MutableMap<in K, in Int>> IntArray.associateByTo(destination: M, keySelector: (Int) -> K): M {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var1.put(var2.invoke(var0[var3]), var0[var3]);
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, in V>> IntArray.associateByTo(destination: M, keySelector: (Int) -> K, valueTransform: (Int) -> V): M {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         var1.put(var2.invoke(var0[var4]), var3.invoke(var0[var4]));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, M : MutableMap<in K, in Long>> LongArray.associateByTo(destination: M, keySelector: (Long) -> K): M {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var1.put(var2.invoke(var0[var3]), var0[var3]);
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, in V>> LongArray.associateByTo(destination: M, keySelector: (Long) -> K, valueTransform: (Long) -> V): M {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         var1.put(var2.invoke(var0[var4]), var3.invoke(var0[var4]));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <T, K, M : MutableMap<in K, in T>> Array<out T>.associateByTo(destination: M, keySelector: (T) -> K): M {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var1.put(var2.invoke(var0[var3]), var0[var3]);
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <T, K, V, M : MutableMap<in K, in V>> Array<out T>.associateByTo(destination: M, keySelector: (T) -> K, valueTransform: (T) -> V): M {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         var1.put(var2.invoke(var0[var4]), var3.invoke(var0[var4]));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, M : MutableMap<in K, in Short>> ShortArray.associateByTo(destination: M, keySelector: (Short) -> K): M {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         var1.put(var2.invoke(var0[var4]), var0[var4]);
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, in V>> ShortArray.associateByTo(destination: M, keySelector: (Short) -> K, valueTransform: (Short) -> V): M {
      val var6: Int = var0.length;

      for (int var5 = 0; var5 < var6; var5++) {
         var1.put(var2.invoke(var0[var5]), var3.invoke(var0[var5]));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, M : MutableMap<in K, in Boolean>> BooleanArray.associateByTo(destination: M, keySelector: (Boolean) -> K): M {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var1.put(var2.invoke(var0[var3]), var0[var3]);
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, in V>> BooleanArray.associateByTo(destination: M, keySelector: (Boolean) -> K, valueTransform: (Boolean) -> V): M {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         var1.put(var2.invoke(var0[var4]), var3.invoke(var0[var4]));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, in V>> ByteArray.associateTo(destination: M, transform: (Byte) -> Pair<K, V>): M {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Pair = var2.invoke(var0[var3]) as Pair;
         var1.put(var5.getFirst(), var5.getSecond());
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, in V>> CharArray.associateTo(destination: M, transform: (Char) -> Pair<K, V>): M {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Pair = var2.invoke(var0[var3]) as Pair;
         var1.put(var5.getFirst(), var5.getSecond());
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, in V>> DoubleArray.associateTo(destination: M, transform: (Double) -> Pair<K, V>): M {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Pair = var2.invoke(var0[var3]) as Pair;
         var1.put(var5.getFirst(), var5.getSecond());
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, in V>> FloatArray.associateTo(destination: M, transform: (Float) -> Pair<K, V>): M {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Pair = var2.invoke(var0[var3]) as Pair;
         var1.put(var5.getFirst(), var5.getSecond());
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, in V>> IntArray.associateTo(destination: M, transform: (Int) -> Pair<K, V>): M {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Pair = var2.invoke(var0[var3]) as Pair;
         var1.put(var5.getFirst(), var5.getSecond());
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, in V>> LongArray.associateTo(destination: M, transform: (Long) -> Pair<K, V>): M {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Pair = var2.invoke(var0[var3]) as Pair;
         var1.put(var5.getFirst(), var5.getSecond());
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <T, K, V, M : MutableMap<in K, in V>> Array<out T>.associateTo(destination: M, transform: (T) -> Pair<K, V>): M {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Pair = var2.invoke(var0[var3]) as Pair;
         var1.put(var5.getFirst(), var5.getSecond());
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, in V>> ShortArray.associateTo(destination: M, transform: (Short) -> Pair<K, V>): M {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Pair = var2.invoke(var0[var3]) as Pair;
         var1.put(var5.getFirst(), var5.getSecond());
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, in V>> BooleanArray.associateTo(destination: M, transform: (Boolean) -> Pair<K, V>): M {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Pair = var2.invoke(var0[var3]) as Pair;
         var1.put(var5.getFirst(), var5.getSecond());
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <V> ByteArray.associateWith(valueSelector: (Byte) -> V): Map<Byte, V> {
      val var5: LinkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var5.put(var0[var3], var1.invoke(var0[var3]));
      }

      return var5;
   }

   @JvmStatic
   public inline fun <V> CharArray.associateWith(valueSelector: (Char) -> V): Map<Char, V> {
      val var5: LinkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(RangesKt.coerceAtMost(var0.length, 128)), 16));
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var5.put(var0[var3], var1.invoke(var0[var3]));
      }

      return var5;
   }

   @JvmStatic
   public inline fun <V> DoubleArray.associateWith(valueSelector: (Double) -> V): Map<Double, V> {
      val var6: LinkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         var6.put(var0[var4], var1.invoke(var0[var4]));
      }

      return var6;
   }

   @JvmStatic
   public inline fun <V> FloatArray.associateWith(valueSelector: (Float) -> V): Map<Float, V> {
      val var5: LinkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var5.put(var0[var3], var1.invoke(var0[var3]));
      }

      return var5;
   }

   @JvmStatic
   public inline fun <V> IntArray.associateWith(valueSelector: (Int) -> V): Map<Int, V> {
      val var5: LinkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var5.put(var0[var2], var1.invoke(var0[var2]));
      }

      return var5;
   }

   @JvmStatic
   public inline fun <V> LongArray.associateWith(valueSelector: (Long) -> V): Map<Long, V> {
      val var6: LinkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var6.put(var0[var2], var1.invoke(var0[var2]));
      }

      return var6;
   }

   @JvmStatic
   public inline fun <K, V> Array<out K>.associateWith(valueSelector: (K) -> V): Map<K, V> {
      val var4: LinkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4.put(var0[var2], var1.invoke(var0[var2]));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <V> ShortArray.associateWith(valueSelector: (Short) -> V): Map<Short, V> {
      val var5: LinkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var5.put(var0[var3], var1.invoke(var0[var3]));
      }

      return var5;
   }

   @JvmStatic
   public inline fun <V> BooleanArray.associateWith(valueSelector: (Boolean) -> V): Map<Boolean, V> {
      val var5: LinkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(var0.length), 16));
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var5.put(var0[var2], var1.invoke(var0[var2]));
      }

      return var5;
   }

   @JvmStatic
   public inline fun <V, M : MutableMap<in Byte, in V>> ByteArray.associateWithTo(destination: M, valueSelector: (Byte) -> V): M {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         var1.put(var0[var4], var2.invoke(var0[var4]));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <V, M : MutableMap<in Char, in V>> CharArray.associateWithTo(destination: M, valueSelector: (Char) -> V): M {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         var1.put(var0[var4], var2.invoke(var0[var4]));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <V, M : MutableMap<in Double, in V>> DoubleArray.associateWithTo(destination: M, valueSelector: (Double) -> V): M {
      val var6: Int = var0.length;

      for (int var5 = 0; var5 < var6; var5++) {
         var1.put(var0[var5], var2.invoke(var0[var5]));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <V, M : MutableMap<in Float, in V>> FloatArray.associateWithTo(destination: M, valueSelector: (Float) -> V): M {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         var1.put(var0[var4], var2.invoke(var0[var4]));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <V, M : MutableMap<in Int, in V>> IntArray.associateWithTo(destination: M, valueSelector: (Int) -> V): M {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var1.put(var0[var3], var2.invoke(var0[var3]));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <V, M : MutableMap<in Long, in V>> LongArray.associateWithTo(destination: M, valueSelector: (Long) -> V): M {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var1.put(var0[var3], var2.invoke(var0[var3]));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, in V>> Array<out K>.associateWithTo(destination: M, valueSelector: (K) -> V): M {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var1.put(var0[var3], var2.invoke(var0[var3]));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <V, M : MutableMap<in Short, in V>> ShortArray.associateWithTo(destination: M, valueSelector: (Short) -> V): M {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         var1.put(var0[var4], var2.invoke(var0[var4]));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <V, M : MutableMap<in Boolean, in V>> BooleanArray.associateWithTo(destination: M, valueSelector: (Boolean) -> V): M {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var1.put(var0[var3], var2.invoke(var0[var3]));
      }

      return (M)var1;
   }

   @JvmStatic
   public fun ByteArray.average(): Double {
      val var5: Int = var0.length;
      var var1: Double = 0.0;
      var var3: Int = 0;

      var var4: Int;
      for (var4 = 0; var3 < var5; var3++) {
         var1 += var0[var3];
         var4++;
      }

      if (var4 == 0) {
         var1 = java.lang.Double.NaN;
      } else {
         var1 = var1 / var4;
      }

      return var1;
   }

   @JvmStatic
   public fun DoubleArray.average(): Double {
      val var5: Int = var0.length;
      var var1: Double = 0.0;
      var var3: Int = 0;

      var var4: Int;
      for (var4 = 0; var3 < var5; var3++) {
         var1 += var0[var3];
         var4++;
      }

      if (var4 == 0) {
         var1 = java.lang.Double.NaN;
      } else {
         var1 = var1 / var4;
      }

      return var1;
   }

   @JvmStatic
   public fun FloatArray.average(): Double {
      val var5: Int = var0.length;
      var var1: Double = 0.0;
      var var4: Int = 0;

      var var3: Int;
      for (var3 = 0; var4 < var5; var4++) {
         var1 += var0[var4];
         var3++;
      }

      if (var3 == 0) {
         var1 = java.lang.Double.NaN;
      } else {
         var1 = var1 / var3;
      }

      return var1;
   }

   @JvmStatic
   public fun IntArray.average(): Double {
      val var5: Int = var0.length;
      var var1: Double = 0.0;
      var var3: Int = 0;

      var var4: Int;
      for (var4 = 0; var3 < var5; var3++) {
         var1 += var0[var3];
         var4++;
      }

      if (var4 == 0) {
         var1 = java.lang.Double.NaN;
      } else {
         var1 = var1 / var4;
      }

      return var1;
   }

   @JvmStatic
   public fun LongArray.average(): Double {
      val var5: Int = var0.length;
      var var1: Double = 0.0;
      var var3: Int = 0;

      var var4: Int;
      for (var4 = 0; var3 < var5; var3++) {
         var1 += var0[var3];
         var4++;
      }

      if (var4 == 0) {
         var1 = java.lang.Double.NaN;
      } else {
         var1 = var1 / var4;
      }

      return var1;
   }

   @JvmStatic
   public fun ShortArray.average(): Double {
      val var5: Int = var0.length;
      var var1: Double = 0.0;
      var var3: Int = 0;

      var var4: Int;
      for (var4 = 0; var3 < var5; var3++) {
         var1 += var0[var3];
         var4++;
      }

      if (var4 == 0) {
         var1 = java.lang.Double.NaN;
      } else {
         var1 = var1 / var4;
      }

      return var1;
   }

   @JvmStatic
   public fun Array<out Byte>.average(): Double {
      val var5: Int = var0.length;
      var var1: Double = 0.0;
      var var3: Int = 0;

      var var4: Int;
      for (var4 = 0; var3 < var5; var3++) {
         var1 += var0[var3].byteValue();
         var4++;
      }

      if (var4 == 0) {
         var1 = java.lang.Double.NaN;
      } else {
         var1 = var1 / var4;
      }

      return var1;
   }

   @JvmStatic
   public fun Array<out Double>.average(): Double {
      val var5: Int = var0.length;
      var var1: Double = 0.0;
      var var3: Int = 0;

      var var4: Int;
      for (var4 = 0; var3 < var5; var3++) {
         var1 += var0[var3];
         var4++;
      }

      if (var4 == 0) {
         var1 = java.lang.Double.NaN;
      } else {
         var1 = var1 / var4;
      }

      return var1;
   }

   @JvmStatic
   public fun Array<out Float>.average(): Double {
      val var5: Int = var0.length;
      var var1: Double = 0.0;
      var var3: Int = 0;

      var var4: Int;
      for (var4 = 0; var3 < var5; var3++) {
         var1 += var0[var3].floatValue();
         var4++;
      }

      if (var4 == 0) {
         var1 = java.lang.Double.NaN;
      } else {
         var1 = var1 / var4;
      }

      return var1;
   }

   @JvmStatic
   public fun Array<out Int>.average(): Double {
      val var5: Int = var0.length;
      var var1: Double = 0.0;
      var var4: Int = 0;

      var var3: Int;
      for (var3 = 0; var4 < var5; var4++) {
         var1 += var0[var4].intValue();
         var3++;
      }

      if (var3 == 0) {
         var1 = java.lang.Double.NaN;
      } else {
         var1 = var1 / var3;
      }

      return var1;
   }

   @JvmStatic
   public fun Array<out Long>.average(): Double {
      val var5: Int = var0.length;
      var var1: Double = 0.0;
      var var4: Int = 0;

      var var3: Int;
      for (var3 = 0; var4 < var5; var4++) {
         var1 += var0[var4].longValue();
         var3++;
      }

      if (var3 == 0) {
         var1 = java.lang.Double.NaN;
      } else {
         var1 = var1 / var3;
      }

      return var1;
   }

   @JvmStatic
   public fun Array<out Short>.average(): Double {
      val var5: Int = var0.length;
      var var1: Double = 0.0;
      var var4: Int = 0;

      var var3: Int;
      for (var3 = 0; var4 < var5; var4++) {
         var1 += var0[var4].shortValue();
         var3++;
      }

      if (var3 == 0) {
         var1 = java.lang.Double.NaN;
      } else {
         var1 = var1 / var3;
      }

      return var1;
   }

   @JvmStatic
   public inline operator fun ByteArray.component1(): Byte {
      return var0[0];
   }

   @JvmStatic
   public inline operator fun CharArray.component1(): Char {
      return var0[0];
   }

   @JvmStatic
   public inline operator fun DoubleArray.component1(): Double {
      return var0[0];
   }

   @JvmStatic
   public inline operator fun FloatArray.component1(): Float {
      return var0[0];
   }

   @JvmStatic
   public inline operator fun IntArray.component1(): Int {
      return var0[0];
   }

   @JvmStatic
   public inline operator fun LongArray.component1(): Long {
      return var0[0];
   }

   @JvmStatic
   public inline operator fun <T> Array<out T>.component1(): T {
      return (T)var0[0];
   }

   @JvmStatic
   public inline operator fun ShortArray.component1(): Short {
      return var0[0];
   }

   @JvmStatic
   public inline operator fun BooleanArray.component1(): Boolean {
      return var0[0];
   }

   @JvmStatic
   public inline operator fun ByteArray.component2(): Byte {
      return var0[1];
   }

   @JvmStatic
   public inline operator fun CharArray.component2(): Char {
      return var0[1];
   }

   @JvmStatic
   public inline operator fun DoubleArray.component2(): Double {
      return var0[1];
   }

   @JvmStatic
   public inline operator fun FloatArray.component2(): Float {
      return var0[1];
   }

   @JvmStatic
   public inline operator fun IntArray.component2(): Int {
      return var0[1];
   }

   @JvmStatic
   public inline operator fun LongArray.component2(): Long {
      return var0[1];
   }

   @JvmStatic
   public inline operator fun <T> Array<out T>.component2(): T {
      return (T)var0[1];
   }

   @JvmStatic
   public inline operator fun ShortArray.component2(): Short {
      return var0[1];
   }

   @JvmStatic
   public inline operator fun BooleanArray.component2(): Boolean {
      return var0[1];
   }

   @JvmStatic
   public inline operator fun ByteArray.component3(): Byte {
      return var0[2];
   }

   @JvmStatic
   public inline operator fun CharArray.component3(): Char {
      return var0[2];
   }

   @JvmStatic
   public inline operator fun DoubleArray.component3(): Double {
      return var0[2];
   }

   @JvmStatic
   public inline operator fun FloatArray.component3(): Float {
      return var0[2];
   }

   @JvmStatic
   public inline operator fun IntArray.component3(): Int {
      return var0[2];
   }

   @JvmStatic
   public inline operator fun LongArray.component3(): Long {
      return var0[2];
   }

   @JvmStatic
   public inline operator fun <T> Array<out T>.component3(): T {
      return (T)var0[2];
   }

   @JvmStatic
   public inline operator fun ShortArray.component3(): Short {
      return var0[2];
   }

   @JvmStatic
   public inline operator fun BooleanArray.component3(): Boolean {
      return var0[2];
   }

   @JvmStatic
   public inline operator fun ByteArray.component4(): Byte {
      return var0[3];
   }

   @JvmStatic
   public inline operator fun CharArray.component4(): Char {
      return var0[3];
   }

   @JvmStatic
   public inline operator fun DoubleArray.component4(): Double {
      return var0[3];
   }

   @JvmStatic
   public inline operator fun FloatArray.component4(): Float {
      return var0[3];
   }

   @JvmStatic
   public inline operator fun IntArray.component4(): Int {
      return var0[3];
   }

   @JvmStatic
   public inline operator fun LongArray.component4(): Long {
      return var0[3];
   }

   @JvmStatic
   public inline operator fun <T> Array<out T>.component4(): T {
      return (T)var0[3];
   }

   @JvmStatic
   public inline operator fun ShortArray.component4(): Short {
      return var0[3];
   }

   @JvmStatic
   public inline operator fun BooleanArray.component4(): Boolean {
      return var0[3];
   }

   @JvmStatic
   public inline operator fun ByteArray.component5(): Byte {
      return var0[4];
   }

   @JvmStatic
   public inline operator fun CharArray.component5(): Char {
      return var0[4];
   }

   @JvmStatic
   public inline operator fun DoubleArray.component5(): Double {
      return var0[4];
   }

   @JvmStatic
   public inline operator fun FloatArray.component5(): Float {
      return var0[4];
   }

   @JvmStatic
   public inline operator fun IntArray.component5(): Int {
      return var0[4];
   }

   @JvmStatic
   public inline operator fun LongArray.component5(): Long {
      return var0[4];
   }

   @JvmStatic
   public inline operator fun <T> Array<out T>.component5(): T {
      return (T)var0[4];
   }

   @JvmStatic
   public inline operator fun ShortArray.component5(): Short {
      return var0[4];
   }

   @JvmStatic
   public inline operator fun BooleanArray.component5(): Boolean {
      return var0[4];
   }

   @JvmStatic
   public operator fun ByteArray.contains(element: Byte): Boolean {
      val var2: Boolean;
      if (ArraysKt.indexOf(var0, var1) >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @JvmStatic
   public operator fun CharArray.contains(element: Char): Boolean {
      val var2: Boolean;
      if (ArraysKt.indexOf(var0, var1) >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @JvmStatic
   public operator fun IntArray.contains(element: Int): Boolean {
      val var2: Boolean;
      if (ArraysKt.indexOf(var0, var1) >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @JvmStatic
   public operator fun LongArray.contains(element: Long): Boolean {
      val var3: Boolean;
      if (ArraysKt.indexOf(var0, var1) >= 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   @JvmStatic
   public operator fun <T> Array<out T>.contains(element: T): Boolean {
      val var2: Boolean;
      if (ArraysKt.indexOf(var0, var1) >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @JvmStatic
   public operator fun ShortArray.contains(element: Short): Boolean {
      val var2: Boolean;
      if (ArraysKt.indexOf(var0, var1) >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @JvmStatic
   public operator fun BooleanArray.contains(element: Boolean): Boolean {
      if (ArraysKt.indexOf(var0, var1) >= 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   public inline fun ByteArray.count(): Int {
      return var0.length;
   }

   @JvmStatic
   public inline fun ByteArray.count(predicate: (Byte) -> Boolean): Int {
      val var5: Int = var0.length;
      var var3: Int = 0;
      var var4: Int = 0;

      while (var3 < var5) {
         var var2: Int = var4;
         if (var1.invoke(var0[var3]) as java.lang.Boolean) {
            var2 = var4 + 1;
         }

         var3++;
         var4 = var2;
      }

      return var4;
   }

   @JvmStatic
   public inline fun CharArray.count(): Int {
      return var0.length;
   }

   @JvmStatic
   public inline fun CharArray.count(predicate: (Char) -> Boolean): Int {
      val var5: Int = var0.length;
      var var2: Int = 0;
      var var3: Int = 0;

      while (var2 < var5) {
         var var4: Int = var3;
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            var4 = var3 + 1;
         }

         var2++;
         var3 = var4;
      }

      return var3;
   }

   @JvmStatic
   public inline fun DoubleArray.count(): Int {
      return var0.length;
   }

   @JvmStatic
   public inline fun DoubleArray.count(predicate: (Double) -> Boolean): Int {
      val var5: Int = var0.length;
      var var4: Int = 0;
      var var2: Int = 0;

      while (var4 < var5) {
         var var3: Int = var2;
         if (var1.invoke(var0[var4]) as java.lang.Boolean) {
            var3 = var2 + 1;
         }

         var4++;
         var2 = var3;
      }

      return var2;
   }

   @JvmStatic
   public inline fun FloatArray.count(): Int {
      return var0.length;
   }

   @JvmStatic
   public inline fun FloatArray.count(predicate: (Float) -> Boolean): Int {
      val var5: Int = var0.length;
      var var4: Int = 0;
      var var2: Int = 0;

      while (var4 < var5) {
         var var3: Int = var2;
         if (var1.invoke(var0[var4]) as java.lang.Boolean) {
            var3 = var2 + 1;
         }

         var4++;
         var2 = var3;
      }

      return var2;
   }

   @JvmStatic
   public inline fun IntArray.count(): Int {
      return var0.length;
   }

   @JvmStatic
   public inline fun IntArray.count(predicate: (Int) -> Boolean): Int {
      val var5: Int = var0.length;
      var var3: Int = 0;
      var var4: Int = 0;

      while (var3 < var5) {
         var var2: Int = var4;
         if (var1.invoke(var0[var3]) as java.lang.Boolean) {
            var2 = var4 + 1;
         }

         var3++;
         var4 = var2;
      }

      return var4;
   }

   @JvmStatic
   public inline fun LongArray.count(): Int {
      return var0.length;
   }

   @JvmStatic
   public inline fun LongArray.count(predicate: (Long) -> Boolean): Int {
      val var5: Int = var0.length;
      var var3: Int = 0;
      var var4: Int = 0;

      while (var3 < var5) {
         var var2: Int = var4;
         if (var1.invoke(var0[var3]) as java.lang.Boolean) {
            var2 = var4 + 1;
         }

         var3++;
         var4 = var2;
      }

      return var4;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.count(): Int {
      return var0.length;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.count(predicate: (T) -> Boolean): Int {
      val var5: Int = var0.length;
      var var2: Int = 0;
      var var3: Int = 0;

      while (var2 < var5) {
         var var4: Int = var3;
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            var4 = var3 + 1;
         }

         var2++;
         var3 = var4;
      }

      return var3;
   }

   @JvmStatic
   public inline fun ShortArray.count(): Int {
      return var0.length;
   }

   @JvmStatic
   public inline fun ShortArray.count(predicate: (Short) -> Boolean): Int {
      val var5: Int = var0.length;
      var var4: Int = 0;
      var var2: Int = 0;

      while (var4 < var5) {
         var var3: Int = var2;
         if (var1.invoke(var0[var4]) as java.lang.Boolean) {
            var3 = var2 + 1;
         }

         var4++;
         var2 = var3;
      }

      return var2;
   }

   @JvmStatic
   public inline fun BooleanArray.count(): Int {
      return var0.length;
   }

   @JvmStatic
   public inline fun BooleanArray.count(predicate: (Boolean) -> Boolean): Int {
      val var5: Int = var0.length;
      var var2: Int = 0;
      var var3: Int = 0;

      while (var2 < var5) {
         var var4: Int = var3;
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            var4 = var3 + 1;
         }

         var2++;
         var3 = var4;
      }

      return var3;
   }

   @JvmStatic
   public fun ByteArray.distinct(): List<Byte> {
      return CollectionsKt.toList(ArraysKt.toMutableSet(var0));
   }

   @JvmStatic
   public fun CharArray.distinct(): List<Char> {
      return CollectionsKt.toList(ArraysKt.toMutableSet(var0));
   }

   @JvmStatic
   public fun DoubleArray.distinct(): List<Double> {
      return CollectionsKt.toList(ArraysKt.toMutableSet(var0));
   }

   @JvmStatic
   public fun FloatArray.distinct(): List<Float> {
      return CollectionsKt.toList(ArraysKt.toMutableSet(var0));
   }

   @JvmStatic
   public fun IntArray.distinct(): List<Int> {
      return CollectionsKt.toList(ArraysKt.toMutableSet(var0));
   }

   @JvmStatic
   public fun LongArray.distinct(): List<Long> {
      return CollectionsKt.toList(ArraysKt.toMutableSet(var0));
   }

   @JvmStatic
   public fun <T> Array<out T>.distinct(): List<T> {
      return (java.util.List<T>)CollectionsKt.toList(ArraysKt.toMutableSet(var0));
   }

   @JvmStatic
   public fun ShortArray.distinct(): List<Short> {
      return CollectionsKt.toList(ArraysKt.toMutableSet(var0));
   }

   @JvmStatic
   public fun BooleanArray.distinct(): List<Boolean> {
      return CollectionsKt.toList(ArraysKt.toMutableSet(var0));
   }

   @JvmStatic
   public inline fun <K> ByteArray.distinctBy(selector: (Byte) -> K): List<Byte> {
      val var5: HashSet = new HashSet();
      val var6: ArrayList = new ArrayList();
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Byte = var0[var3];
         if (var5.add(var1.invoke(var0[var3]))) {
            var6.add(var2);
         }
      }

      return var6;
   }

   @JvmStatic
   public inline fun <K> CharArray.distinctBy(selector: (Char) -> K): List<Char> {
      val var5: HashSet = new HashSet();
      val var6: ArrayList = new ArrayList();
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Char = var0[var3];
         if (var5.add(var1.invoke(var0[var3]))) {
            var6.add(var2);
         }
      }

      return var6;
   }

   @JvmStatic
   public inline fun <K> DoubleArray.distinctBy(selector: (Double) -> K): List<Double> {
      val var7: HashSet = new HashSet();
      val var6: ArrayList = new ArrayList();
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         val var2: Double = var0[var4];
         if (var7.add(var1.invoke(var0[var4]))) {
            var6.add(var2);
         }
      }

      return var6;
   }

   @JvmStatic
   public inline fun <K> FloatArray.distinctBy(selector: (Float) -> K): List<Float> {
      val var5: HashSet = new HashSet();
      val var6: ArrayList = new ArrayList();
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Float = var0[var3];
         if (var5.add(var1.invoke(var0[var3]))) {
            var6.add(var2);
         }
      }

      return var6;
   }

   @JvmStatic
   public inline fun <K> IntArray.distinctBy(selector: (Int) -> K): List<Int> {
      val var6: HashSet = new HashSet();
      val var5: ArrayList = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Int = var0[var2];
         if (var6.add(var1.invoke(var0[var2]))) {
            var5.add(var4);
         }
      }

      return var5;
   }

   @JvmStatic
   public inline fun <K> LongArray.distinctBy(selector: (Long) -> K): List<Long> {
      val var7: HashSet = new HashSet();
      val var6: ArrayList = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Long = var0[var2];
         if (var7.add(var1.invoke(var0[var2]))) {
            var6.add(var4);
         }
      }

      return var6;
   }

   @JvmStatic
   public inline fun <T, K> Array<out T>.distinctBy(selector: (T) -> K): List<T> {
      val var4: HashSet = new HashSet();
      val var5: ArrayList = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var6: Any = var0[var2];
         if (var4.add(var1.invoke(var0[var2]))) {
            var5.add(var6);
         }
      }

      return var5;
   }

   @JvmStatic
   public inline fun <K> ShortArray.distinctBy(selector: (Short) -> K): List<Short> {
      val var6: HashSet = new HashSet();
      val var5: ArrayList = new ArrayList();
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Short = var0[var3];
         if (var6.add(var1.invoke(var0[var3]))) {
            var5.add(var2);
         }
      }

      return var5;
   }

   @JvmStatic
   public inline fun <K> BooleanArray.distinctBy(selector: (Boolean) -> K): List<Boolean> {
      val var6: HashSet = new HashSet();
      val var5: ArrayList = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Boolean = var0[var2];
         if (var6.add(var1.invoke(var0[var2]))) {
            var5.add(var4);
         }
      }

      return var5;
   }

   @JvmStatic
   public fun ByteArray.drop(n: Int): List<Byte> {
      if (var1 >= 0) {
         return ArraysKt.takeLast(var0, RangesKt.coerceAtLeast(var0.length - var1, 0));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun CharArray.drop(n: Int): List<Char> {
      if (var1 >= 0) {
         return ArraysKt.takeLast(var0, RangesKt.coerceAtLeast(var0.length - var1, 0));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun DoubleArray.drop(n: Int): List<Double> {
      if (var1 >= 0) {
         return ArraysKt.takeLast(var0, RangesKt.coerceAtLeast(var0.length - var1, 0));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun FloatArray.drop(n: Int): List<Float> {
      if (var1 >= 0) {
         return ArraysKt.takeLast(var0, RangesKt.coerceAtLeast(var0.length - var1, 0));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun IntArray.drop(n: Int): List<Int> {
      if (var1 >= 0) {
         return ArraysKt.takeLast(var0, RangesKt.coerceAtLeast(var0.length - var1, 0));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun LongArray.drop(n: Int): List<Long> {
      if (var1 >= 0) {
         return ArraysKt.takeLast(var0, RangesKt.coerceAtLeast(var0.length - var1, 0));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun <T> Array<out T>.drop(n: Int): List<T> {
      if (var1 >= 0) {
         return (java.util.List<T>)ArraysKt.takeLast(var0, RangesKt.coerceAtLeast(var0.length - var1, 0));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun ShortArray.drop(n: Int): List<Short> {
      if (var1 >= 0) {
         return ArraysKt.takeLast(var0, RangesKt.coerceAtLeast(var0.length - var1, 0));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun BooleanArray.drop(n: Int): List<Boolean> {
      if (var1 >= 0) {
         return ArraysKt.takeLast(var0, RangesKt.coerceAtLeast(var0.length - var1, 0));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun ByteArray.dropLast(n: Int): List<Byte> {
      if (var1 >= 0) {
         return ArraysKt.take(var0, RangesKt.coerceAtLeast(var0.length - var1, 0));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun CharArray.dropLast(n: Int): List<Char> {
      if (var1 >= 0) {
         return ArraysKt.take(var0, RangesKt.coerceAtLeast(var0.length - var1, 0));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun DoubleArray.dropLast(n: Int): List<Double> {
      if (var1 >= 0) {
         return ArraysKt.take(var0, RangesKt.coerceAtLeast(var0.length - var1, 0));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun FloatArray.dropLast(n: Int): List<Float> {
      if (var1 >= 0) {
         return ArraysKt.take(var0, RangesKt.coerceAtLeast(var0.length - var1, 0));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun IntArray.dropLast(n: Int): List<Int> {
      if (var1 >= 0) {
         return ArraysKt.take(var0, RangesKt.coerceAtLeast(var0.length - var1, 0));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun LongArray.dropLast(n: Int): List<Long> {
      if (var1 >= 0) {
         return ArraysKt.take(var0, RangesKt.coerceAtLeast(var0.length - var1, 0));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun <T> Array<out T>.dropLast(n: Int): List<T> {
      if (var1 >= 0) {
         return (java.util.List<T>)ArraysKt.take(var0, RangesKt.coerceAtLeast(var0.length - var1, 0));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun ShortArray.dropLast(n: Int): List<Short> {
      if (var1 >= 0) {
         return ArraysKt.take(var0, RangesKt.coerceAtLeast(var0.length - var1, 0));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun BooleanArray.dropLast(n: Int): List<Boolean> {
      if (var1 >= 0) {
         return ArraysKt.take(var0, RangesKt.coerceAtLeast(var0.length - var1, 0));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public inline fun ByteArray.dropLastWhile(predicate: (Byte) -> Boolean): List<Byte> {
      for (int var2 = ArraysKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            return ArraysKt.take(var0, var2 + 1);
         }
      }

      return CollectionsKt.emptyList();
   }

   @JvmStatic
   public inline fun CharArray.dropLastWhile(predicate: (Char) -> Boolean): List<Char> {
      for (int var2 = ArraysKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            return ArraysKt.take(var0, var2 + 1);
         }
      }

      return CollectionsKt.emptyList();
   }

   @JvmStatic
   public inline fun DoubleArray.dropLastWhile(predicate: (Double) -> Boolean): List<Double> {
      for (int var2 = ArraysKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            return ArraysKt.take(var0, var2 + 1);
         }
      }

      return CollectionsKt.emptyList();
   }

   @JvmStatic
   public inline fun FloatArray.dropLastWhile(predicate: (Float) -> Boolean): List<Float> {
      for (int var2 = ArraysKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            return ArraysKt.take(var0, var2 + 1);
         }
      }

      return CollectionsKt.emptyList();
   }

   @JvmStatic
   public inline fun IntArray.dropLastWhile(predicate: (Int) -> Boolean): List<Int> {
      for (int var2 = ArraysKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            return ArraysKt.take(var0, var2 + 1);
         }
      }

      return CollectionsKt.emptyList();
   }

   @JvmStatic
   public inline fun LongArray.dropLastWhile(predicate: (Long) -> Boolean): List<Long> {
      for (int var2 = ArraysKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            return ArraysKt.take(var0, var2 + 1);
         }
      }

      return CollectionsKt.emptyList();
   }

   @JvmStatic
   public inline fun <T> Array<out T>.dropLastWhile(predicate: (T) -> Boolean): List<T> {
      for (int var2 = ArraysKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            return (java.util.List<T>)ArraysKt.take(var0, var2 + 1);
         }
      }

      return CollectionsKt.emptyList();
   }

   @JvmStatic
   public inline fun ShortArray.dropLastWhile(predicate: (Short) -> Boolean): List<Short> {
      for (int var2 = ArraysKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            return ArraysKt.take(var0, var2 + 1);
         }
      }

      return CollectionsKt.emptyList();
   }

   @JvmStatic
   public inline fun BooleanArray.dropLastWhile(predicate: (Boolean) -> Boolean): List<Boolean> {
      for (int var2 = ArraysKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            return ArraysKt.take(var0, var2 + 1);
         }
      }

      return CollectionsKt.emptyList();
   }

   @JvmStatic
   public inline fun ByteArray.dropWhile(predicate: (Byte) -> Boolean): List<Byte> {
      val var6: ArrayList = new ArrayList();
      val var5: Int = var0.length;
      var var3: Int = 0;

      for (boolean var4 = false; var3 < var5; var3++) {
         val var2: Byte = var0[var3];
         if (var4) {
            var6.add(var2);
         } else if (!var1.invoke(var2) as java.lang.Boolean) {
            var6.add(var2);
            var4 = true;
         }
      }

      return var6;
   }

   @JvmStatic
   public inline fun CharArray.dropWhile(predicate: (Char) -> Boolean): List<Char> {
      val var6: ArrayList = new ArrayList();
      val var5: Int = var0.length;
      var var3: Int = 0;

      for (boolean var4 = false; var3 < var5; var3++) {
         val var2: Char = var0[var3];
         if (var4) {
            var6.add(var2);
         } else if (!var1.invoke(var2) as java.lang.Boolean) {
            var6.add(var2);
            var4 = true;
         }
      }

      return var6;
   }

   @JvmStatic
   public inline fun DoubleArray.dropWhile(predicate: (Double) -> Boolean): List<Double> {
      val var7: ArrayList = new ArrayList();
      val var6: Int = var0.length;
      var var4: Int = 0;

      for (boolean var5 = false; var4 < var6; var4++) {
         val var2: Double = var0[var4];
         if (var5) {
            var7.add(var2);
         } else if (!var1.invoke(var2) as java.lang.Boolean) {
            var7.add(var2);
            var5 = true;
         }
      }

      return var7;
   }

   @JvmStatic
   public inline fun FloatArray.dropWhile(predicate: (Float) -> Boolean): List<Float> {
      val var6: ArrayList = new ArrayList();
      val var5: Int = var0.length;
      var var3: Int = 0;

      for (boolean var4 = false; var3 < var5; var3++) {
         val var2: Float = var0[var3];
         if (var4) {
            var6.add(var2);
         } else if (!var1.invoke(var2) as java.lang.Boolean) {
            var6.add(var2);
            var4 = true;
         }
      }

      return var6;
   }

   @JvmStatic
   public inline fun IntArray.dropWhile(predicate: (Int) -> Boolean): List<Int> {
      val var6: ArrayList = new ArrayList();
      val var4: Int = var0.length;
      var var2: Int = 0;

      for (boolean var3 = false; var2 < var4; var2++) {
         val var5: Int = var0[var2];
         if (var3) {
            var6.add(var5);
         } else if (!var1.invoke(var5) as java.lang.Boolean) {
            var6.add(var5);
            var3 = true;
         }
      }

      return var6;
   }

   @JvmStatic
   public inline fun LongArray.dropWhile(predicate: (Long) -> Boolean): List<Long> {
      val var7: ArrayList = new ArrayList();
      val var4: Int = var0.length;
      var var2: Int = 0;

      for (boolean var3 = false; var2 < var4; var2++) {
         val var5: Long = var0[var2];
         if (var3) {
            var7.add(var5);
         } else if (!var1.invoke(var5) as java.lang.Boolean) {
            var7.add(var5);
            var3 = true;
         }
      }

      return var7;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.dropWhile(predicate: (T) -> Boolean): List<T> {
      val var6: ArrayList = new ArrayList();
      val var4: Int = var0.length;
      var var2: Int = 0;

      for (boolean var3 = false; var2 < var4; var2++) {
         val var5: Any = var0[var2];
         if (var3) {
            var6.add(var5);
         } else if (!var1.invoke(var5) as java.lang.Boolean) {
            var6.add(var5);
            var3 = true;
         }
      }

      return var6;
   }

   @JvmStatic
   public inline fun ShortArray.dropWhile(predicate: (Short) -> Boolean): List<Short> {
      val var6: ArrayList = new ArrayList();
      val var5: Int = var0.length;
      var var3: Int = 0;

      for (boolean var4 = false; var3 < var5; var3++) {
         val var2: Short = var0[var3];
         if (var4) {
            var6.add(var2);
         } else if (!var1.invoke(var2) as java.lang.Boolean) {
            var6.add(var2);
            var4 = true;
         }
      }

      return var6;
   }

   @JvmStatic
   public inline fun BooleanArray.dropWhile(predicate: (Boolean) -> Boolean): List<Boolean> {
      val var6: ArrayList = new ArrayList();
      val var4: Int = var0.length;
      var var2: Int = 0;

      for (boolean var3 = false; var2 < var4; var2++) {
         val var5: Boolean = var0[var2];
         if (var3) {
            var6.add(var5);
         } else if (!var1.invoke(var5) as java.lang.Boolean) {
            var6.add(var5);
            var3 = true;
         }
      }

      return var6;
   }

   @JvmStatic
   public inline fun ByteArray.elementAtOrElse(index: Int, defaultValue: (Int) -> Byte): Byte {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      val var3: Byte;
      if (var1 >= 0 && var1 < var0.length) {
         var3 = var0[var1];
      } else {
         var3 = (var2.invoke(var1) as java.lang.Number).byteValue();
      }

      return var3;
   }

   @JvmStatic
   public inline fun CharArray.elementAtOrElse(index: Int, defaultValue: (Int) -> Char): Char {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      val var3: Char;
      if (var1 >= 0 && var1 < var0.length) {
         var3 = var0[var1];
      } else {
         var3 = var2.invoke(var1) as Character;
      }

      return var3;
   }

   @JvmStatic
   public inline fun DoubleArray.elementAtOrElse(index: Int, defaultValue: (Int) -> Double): Double {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      val var3: Double;
      if (var1 >= 0 && var1 < var0.length) {
         var3 = var0[var1];
      } else {
         var3 = (var2.invoke(var1) as java.lang.Number).doubleValue();
      }

      return var3;
   }

   @JvmStatic
   public inline fun FloatArray.elementAtOrElse(index: Int, defaultValue: (Int) -> Float): Float {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      val var3: Float;
      if (var1 >= 0 && var1 < var0.length) {
         var3 = var0[var1];
      } else {
         var3 = (var2.invoke(var1) as java.lang.Number).floatValue();
      }

      return var3;
   }

   @JvmStatic
   public inline fun IntArray.elementAtOrElse(index: Int, defaultValue: (Int) -> Int): Int {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      if (var1 >= 0 && var1 < var0.length) {
         var1 = var0[var1];
      } else {
         var1 = (var2.invoke(var1) as java.lang.Number).intValue();
      }

      return var1;
   }

   @JvmStatic
   public inline fun LongArray.elementAtOrElse(index: Int, defaultValue: (Int) -> Long): Long {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      val var3: Long;
      if (var1 >= 0 && var1 < var0.length) {
         var3 = var0[var1];
      } else {
         var3 = (var2.invoke(var1) as java.lang.Number).longValue();
      }

      return var3;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.elementAtOrElse(index: Int, defaultValue: (Int) -> T): T {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      if (var1 >= 0 && var1 < ((Object[])var0).length) {
         var0 = ((Object[])var0)[var1];
      } else {
         var0 = var2.invoke(var1);
      }

      return (T)var0;
   }

   @JvmStatic
   public inline fun ShortArray.elementAtOrElse(index: Int, defaultValue: (Int) -> Short): Short {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      val var3: Short;
      if (var1 >= 0 && var1 < var0.length) {
         var3 = var0[var1];
      } else {
         var3 = (var2.invoke(var1) as java.lang.Number).shortValue();
      }

      return var3;
   }

   @JvmStatic
   public inline fun BooleanArray.elementAtOrElse(index: Int, defaultValue: (Int) -> Boolean): Boolean {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      val var3: Boolean;
      if (var1 >= 0 && var1 < var0.length) {
         var3 = var0[var1];
      } else {
         var3 = var2.invoke(var1) as java.lang.Boolean;
      }

      return var3;
   }

   @JvmStatic
   public inline fun BooleanArray.elementAtOrNull(index: Int): Boolean? {
      return ArraysKt.getOrNull(var0, var1);
   }

   @JvmStatic
   public inline fun ByteArray.elementAtOrNull(index: Int): Byte? {
      return ArraysKt.getOrNull(var0, var1);
   }

   @JvmStatic
   public inline fun CharArray.elementAtOrNull(index: Int): Char? {
      return ArraysKt.getOrNull(var0, var1);
   }

   @JvmStatic
   public inline fun DoubleArray.elementAtOrNull(index: Int): Double? {
      return ArraysKt.getOrNull(var0, var1);
   }

   @JvmStatic
   public inline fun FloatArray.elementAtOrNull(index: Int): Float? {
      return ArraysKt.getOrNull(var0, var1);
   }

   @JvmStatic
   public inline fun IntArray.elementAtOrNull(index: Int): Int? {
      return ArraysKt.getOrNull(var0, var1);
   }

   @JvmStatic
   public inline fun LongArray.elementAtOrNull(index: Int): Long? {
      return ArraysKt.getOrNull(var0, var1);
   }

   @JvmStatic
   public inline fun <T> Array<out T>.elementAtOrNull(index: Int): T? {
      return (T)ArraysKt.getOrNull(var0, var1);
   }

   @JvmStatic
   public inline fun ShortArray.elementAtOrNull(index: Int): Short? {
      return ArraysKt.getOrNull(var0, var1);
   }

   @JvmStatic
   public inline fun ByteArray.filter(predicate: (Byte) -> Boolean): List<Byte> {
      val var5: java.util.Collection = new ArrayList();
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Byte = var0[var3];
         if (var1.invoke(var0[var3]) as java.lang.Boolean) {
            var5.add(var2);
         }
      }

      return var5 as MutableList<java.lang.Byte>;
   }

   @JvmStatic
   public inline fun CharArray.filter(predicate: (Char) -> Boolean): List<Char> {
      val var5: java.util.Collection = new ArrayList();
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Char = var0[var3];
         if (var1.invoke(var0[var3]) as java.lang.Boolean) {
            var5.add(var2);
         }
      }

      return var5 as MutableList<Character>;
   }

   @JvmStatic
   public inline fun DoubleArray.filter(predicate: (Double) -> Boolean): List<Double> {
      val var6: java.util.Collection = new ArrayList();
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         val var2: Double = var0[var4];
         if (var1.invoke(var0[var4]) as java.lang.Boolean) {
            var6.add(var2);
         }
      }

      return var6 as MutableList<java.lang.Double>;
   }

   @JvmStatic
   public inline fun FloatArray.filter(predicate: (Float) -> Boolean): List<Float> {
      val var5: java.util.Collection = new ArrayList();
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Float = var0[var3];
         if (var1.invoke(var0[var3]) as java.lang.Boolean) {
            var5.add(var2);
         }
      }

      return var5 as MutableList<java.lang.Float>;
   }

   @JvmStatic
   public inline fun IntArray.filter(predicate: (Int) -> Boolean): List<Int> {
      val var5: java.util.Collection = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Int = var0[var2];
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            var5.add(var4);
         }
      }

      return var5 as MutableList<Int>;
   }

   @JvmStatic
   public inline fun LongArray.filter(predicate: (Long) -> Boolean): List<Long> {
      val var6: java.util.Collection = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Long = var0[var2];
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            var6.add(var4);
         }
      }

      return var6 as MutableList<java.lang.Long>;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.filter(predicate: (T) -> Boolean): List<T> {
      val var4: java.util.Collection = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var5: Any = var0[var2];
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            var4.add(var5);
         }
      }

      return var4 as MutableList<T>;
   }

   @JvmStatic
   public inline fun ShortArray.filter(predicate: (Short) -> Boolean): List<Short> {
      val var5: java.util.Collection = new ArrayList();
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Short = var0[var3];
         if (var1.invoke(var0[var3]) as java.lang.Boolean) {
            var5.add(var2);
         }
      }

      return var5 as MutableList<java.lang.Short>;
   }

   @JvmStatic
   public inline fun BooleanArray.filter(predicate: (Boolean) -> Boolean): List<Boolean> {
      val var5: java.util.Collection = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Boolean = var0[var2];
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            var5.add(var4);
         }
      }

      return var5 as MutableList<java.lang.Boolean>;
   }

   @JvmStatic
   public inline fun ByteArray.filterIndexed(predicate: (Int, Byte) -> Boolean): List<Byte> {
      val var6: java.util.Collection = new ArrayList();
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         val var2: Byte = var0[var4];
         if (var1.invoke(var3, var0[var4]) as java.lang.Boolean) {
            var6.add(var2);
         }

         var4++;
      }

      return var6 as MutableList<java.lang.Byte>;
   }

   @JvmStatic
   public inline fun CharArray.filterIndexed(predicate: (Int, Char) -> Boolean): List<Char> {
      val var6: java.util.Collection = new ArrayList();
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         val var2: Char = var0[var4];
         if (var1.invoke(var3, var0[var4]) as java.lang.Boolean) {
            var6.add(var2);
         }

         var4++;
      }

      return var6 as MutableList<Character>;
   }

   @JvmStatic
   public inline fun DoubleArray.filterIndexed(predicate: (Int, Double) -> Boolean): List<Double> {
      val var7: java.util.Collection = new ArrayList();
      val var6: Int = var0.length;
      var var5: Int = 0;

      for (int var4 = 0; var5 < var6; var4++) {
         val var2: Double = var0[var5];
         if (var1.invoke(var4, var0[var5]) as java.lang.Boolean) {
            var7.add(var2);
         }

         var5++;
      }

      return var7 as MutableList<java.lang.Double>;
   }

   @JvmStatic
   public inline fun FloatArray.filterIndexed(predicate: (Int, Float) -> Boolean): List<Float> {
      val var6: java.util.Collection = new ArrayList();
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         val var2: Float = var0[var4];
         if (var1.invoke(var3, var0[var4]) as java.lang.Boolean) {
            var6.add(var2);
         }

         var4++;
      }

      return var6 as MutableList<java.lang.Float>;
   }

   @JvmStatic
   public inline fun IntArray.filterIndexed(predicate: (Int, Int) -> Boolean): List<Int> {
      val var6: java.util.Collection = new ArrayList();
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         val var5: Int = var0[var3];
         if (var1.invoke(var2, var0[var3]) as java.lang.Boolean) {
            var6.add(var5);
         }

         var3++;
      }

      return var6 as MutableList<Int>;
   }

   @JvmStatic
   public inline fun LongArray.filterIndexed(predicate: (Int, Long) -> Boolean): List<Long> {
      val var7: java.util.Collection = new ArrayList();
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         val var5: Long = var0[var3];
         if (var1.invoke(var2, var0[var3]) as java.lang.Boolean) {
            var7.add(var5);
         }

         var3++;
      }

      return var7 as MutableList<java.lang.Long>;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.filterIndexed(predicate: (Int, T) -> Boolean): List<T> {
      val var5: java.util.Collection = new ArrayList();
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         val var6: Any = var0[var3];
         if (var1.invoke(var2, var0[var3]) as java.lang.Boolean) {
            var5.add(var6);
         }

         var3++;
      }

      return var5 as MutableList<T>;
   }

   @JvmStatic
   public inline fun ShortArray.filterIndexed(predicate: (Int, Short) -> Boolean): List<Short> {
      val var6: java.util.Collection = new ArrayList();
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         val var2: Short = var0[var4];
         if (var1.invoke(var3, var0[var4]) as java.lang.Boolean) {
            var6.add(var2);
         }

         var4++;
      }

      return var6 as MutableList<java.lang.Short>;
   }

   @JvmStatic
   public inline fun BooleanArray.filterIndexed(predicate: (Int, Boolean) -> Boolean): List<Boolean> {
      val var6: java.util.Collection = new ArrayList();
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         val var5: Boolean = var0[var3];
         if (var1.invoke(var2, var0[var3]) as java.lang.Boolean) {
            var6.add(var5);
         }

         var3++;
      }

      return var6 as MutableList<java.lang.Boolean>;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in Byte>> ByteArray.filterIndexedTo(destination: C, predicate: (Int, Byte) -> Boolean): C {
      val var6: Int = var0.length;
      var var5: Int = 0;

      for (int var4 = 0; var5 < var6; var4++) {
         val var3: Byte = var0[var5];
         if (var2.invoke(var4, var0[var5]) as java.lang.Boolean) {
            var1.add(var3);
         }

         var5++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in Char>> CharArray.filterIndexedTo(destination: C, predicate: (Int, Char) -> Boolean): C {
      val var6: Int = var0.length;
      var var5: Int = 0;

      for (int var4 = 0; var5 < var6; var4++) {
         val var3: Char = var0[var5];
         if (var2.invoke(var4, var0[var5]) as java.lang.Boolean) {
            var1.add(var3);
         }

         var5++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in Double>> DoubleArray.filterIndexedTo(destination: C, predicate: (Int, Double) -> Boolean): C {
      val var7: Int = var0.length;
      var var6: Int = 0;

      for (int var5 = 0; var6 < var7; var5++) {
         val var3: Double = var0[var6];
         if (var2.invoke(var5, var0[var6]) as java.lang.Boolean) {
            var1.add(var3);
         }

         var6++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in Float>> FloatArray.filterIndexedTo(destination: C, predicate: (Int, Float) -> Boolean): C {
      val var6: Int = var0.length;
      var var5: Int = 0;

      for (int var4 = 0; var5 < var6; var4++) {
         val var3: Float = var0[var5];
         if (var2.invoke(var4, var0[var5]) as java.lang.Boolean) {
            var1.add(var3);
         }

         var5++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in Int>> IntArray.filterIndexedTo(destination: C, predicate: (Int, Int) -> Boolean): C {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         val var6: Int = var0[var4];
         if (var2.invoke(var3, var0[var4]) as java.lang.Boolean) {
            var1.add(var6);
         }

         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in Long>> LongArray.filterIndexedTo(destination: C, predicate: (Int, Long) -> Boolean): C {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         val var6: Long = var0[var4];
         if (var2.invoke(var3, var0[var4]) as java.lang.Boolean) {
            var1.add(var6);
         }

         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T, C : MutableCollection<in T>> Array<out T>.filterIndexedTo(destination: C, predicate: (Int, T) -> Boolean): C {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         val var6: Any = var0[var4];
         if (var2.invoke(var3, var0[var4]) as java.lang.Boolean) {
            var1.add(var6);
         }

         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in Short>> ShortArray.filterIndexedTo(destination: C, predicate: (Int, Short) -> Boolean): C {
      val var6: Int = var0.length;
      var var5: Int = 0;

      for (int var4 = 0; var5 < var6; var4++) {
         val var3: Short = var0[var5];
         if (var2.invoke(var4, var0[var5]) as java.lang.Boolean) {
            var1.add(var3);
         }

         var5++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in Boolean>> BooleanArray.filterIndexedTo(destination: C, predicate: (Int, Boolean) -> Boolean): C {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         val var6: Boolean = var0[var4];
         if (var2.invoke(var3, var0[var4]) as java.lang.Boolean) {
            var1.add(var6);
         }

         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun ByteArray.filterNot(predicate: (Byte) -> Boolean): List<Byte> {
      val var5: java.util.Collection = new ArrayList();
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Byte = var0[var3];
         if (!var1.invoke(var0[var3]) as java.lang.Boolean) {
            var5.add(var2);
         }
      }

      return var5 as MutableList<java.lang.Byte>;
   }

   @JvmStatic
   public inline fun CharArray.filterNot(predicate: (Char) -> Boolean): List<Char> {
      val var5: java.util.Collection = new ArrayList();
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Char = var0[var3];
         if (!var1.invoke(var0[var3]) as java.lang.Boolean) {
            var5.add(var2);
         }
      }

      return var5 as MutableList<Character>;
   }

   @JvmStatic
   public inline fun DoubleArray.filterNot(predicate: (Double) -> Boolean): List<Double> {
      val var6: java.util.Collection = new ArrayList();
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         val var2: Double = var0[var4];
         if (!var1.invoke(var0[var4]) as java.lang.Boolean) {
            var6.add(var2);
         }
      }

      return var6 as MutableList<java.lang.Double>;
   }

   @JvmStatic
   public inline fun FloatArray.filterNot(predicate: (Float) -> Boolean): List<Float> {
      val var5: java.util.Collection = new ArrayList();
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Float = var0[var3];
         if (!var1.invoke(var0[var3]) as java.lang.Boolean) {
            var5.add(var2);
         }
      }

      return var5 as MutableList<java.lang.Float>;
   }

   @JvmStatic
   public inline fun IntArray.filterNot(predicate: (Int) -> Boolean): List<Int> {
      val var5: java.util.Collection = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Int = var0[var2];
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            var5.add(var4);
         }
      }

      return var5 as MutableList<Int>;
   }

   @JvmStatic
   public inline fun LongArray.filterNot(predicate: (Long) -> Boolean): List<Long> {
      val var6: java.util.Collection = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Long = var0[var2];
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            var6.add(var4);
         }
      }

      return var6 as MutableList<java.lang.Long>;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.filterNot(predicate: (T) -> Boolean): List<T> {
      val var4: java.util.Collection = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var5: Any = var0[var2];
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            var4.add(var5);
         }
      }

      return var4 as MutableList<T>;
   }

   @JvmStatic
   public inline fun ShortArray.filterNot(predicate: (Short) -> Boolean): List<Short> {
      val var5: java.util.Collection = new ArrayList();
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Short = var0[var3];
         if (!var1.invoke(var0[var3]) as java.lang.Boolean) {
            var5.add(var2);
         }
      }

      return var5 as MutableList<java.lang.Short>;
   }

   @JvmStatic
   public inline fun BooleanArray.filterNot(predicate: (Boolean) -> Boolean): List<Boolean> {
      val var5: java.util.Collection = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Boolean = var0[var2];
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            var5.add(var4);
         }
      }

      return var5 as MutableList<java.lang.Boolean>;
   }

   @JvmStatic
   public fun <T : Any> Array<out T?>.filterNotNull(): List<T> {
      return ArraysKt.filterNotNullTo(var0, new ArrayList()) as MutableList<T>;
   }

   @JvmStatic
   public fun <C : MutableCollection<in T>, T : Any> Array<out T?>.filterNotNullTo(destination: C): C {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Any = var0[var2];
         if (var0[var2] != null) {
            var1.add(var4);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in Byte>> ByteArray.filterNotTo(destination: C, predicate: (Byte) -> Boolean): C {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         val var3: Byte = var0[var4];
         if (!var2.invoke(var0[var4]) as java.lang.Boolean) {
            var1.add(var3);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in Char>> CharArray.filterNotTo(destination: C, predicate: (Char) -> Boolean): C {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         val var3: Char = var0[var4];
         if (!var2.invoke(var0[var4]) as java.lang.Boolean) {
            var1.add(var3);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in Double>> DoubleArray.filterNotTo(destination: C, predicate: (Double) -> Boolean): C {
      val var6: Int = var0.length;

      for (int var5 = 0; var5 < var6; var5++) {
         val var3: Double = var0[var5];
         if (!var2.invoke(var0[var5]) as java.lang.Boolean) {
            var1.add(var3);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in Float>> FloatArray.filterNotTo(destination: C, predicate: (Float) -> Boolean): C {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         val var3: Float = var0[var4];
         if (!var2.invoke(var0[var4]) as java.lang.Boolean) {
            var1.add(var3);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in Int>> IntArray.filterNotTo(destination: C, predicate: (Int) -> Boolean): C {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Int = var0[var3];
         if (!var2.invoke(var0[var3]) as java.lang.Boolean) {
            var1.add(var5);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in Long>> LongArray.filterNotTo(destination: C, predicate: (Long) -> Boolean): C {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Long = var0[var3];
         if (!var2.invoke(var0[var3]) as java.lang.Boolean) {
            var1.add(var5);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T, C : MutableCollection<in T>> Array<out T>.filterNotTo(destination: C, predicate: (T) -> Boolean): C {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Any = var0[var3];
         if (!var2.invoke(var0[var3]) as java.lang.Boolean) {
            var1.add(var5);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in Short>> ShortArray.filterNotTo(destination: C, predicate: (Short) -> Boolean): C {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         val var3: Short = var0[var4];
         if (!var2.invoke(var0[var4]) as java.lang.Boolean) {
            var1.add(var3);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in Boolean>> BooleanArray.filterNotTo(destination: C, predicate: (Boolean) -> Boolean): C {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Boolean = var0[var3];
         if (!var2.invoke(var0[var3]) as java.lang.Boolean) {
            var1.add(var5);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in Byte>> ByteArray.filterTo(destination: C, predicate: (Byte) -> Boolean): C {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         val var3: Byte = var0[var4];
         if (var2.invoke(var0[var4]) as java.lang.Boolean) {
            var1.add(var3);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in Char>> CharArray.filterTo(destination: C, predicate: (Char) -> Boolean): C {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         val var3: Char = var0[var4];
         if (var2.invoke(var0[var4]) as java.lang.Boolean) {
            var1.add(var3);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in Double>> DoubleArray.filterTo(destination: C, predicate: (Double) -> Boolean): C {
      val var6: Int = var0.length;

      for (int var5 = 0; var5 < var6; var5++) {
         val var3: Double = var0[var5];
         if (var2.invoke(var0[var5]) as java.lang.Boolean) {
            var1.add(var3);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in Float>> FloatArray.filterTo(destination: C, predicate: (Float) -> Boolean): C {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         val var3: Float = var0[var4];
         if (var2.invoke(var0[var4]) as java.lang.Boolean) {
            var1.add(var3);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in Int>> IntArray.filterTo(destination: C, predicate: (Int) -> Boolean): C {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Int = var0[var3];
         if (var2.invoke(var0[var3]) as java.lang.Boolean) {
            var1.add(var5);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in Long>> LongArray.filterTo(destination: C, predicate: (Long) -> Boolean): C {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Long = var0[var3];
         if (var2.invoke(var0[var3]) as java.lang.Boolean) {
            var1.add(var5);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T, C : MutableCollection<in T>> Array<out T>.filterTo(destination: C, predicate: (T) -> Boolean): C {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Any = var0[var3];
         if (var2.invoke(var0[var3]) as java.lang.Boolean) {
            var1.add(var5);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in Short>> ShortArray.filterTo(destination: C, predicate: (Short) -> Boolean): C {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         val var3: Short = var0[var4];
         if (var2.invoke(var0[var4]) as java.lang.Boolean) {
            var1.add(var3);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in Boolean>> BooleanArray.filterTo(destination: C, predicate: (Boolean) -> Boolean): C {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Boolean = var0[var3];
         if (var2.invoke(var0[var3]) as java.lang.Boolean) {
            var1.add(var5);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun BooleanArray.find(predicate: (Boolean) -> Boolean): Boolean? {
      val var3: Int = var0.length;
      var var2: Int = 0;

      while (true) {
         if (var2 >= var3) {
            var5 = null;
            break;
         }

         val var4: Boolean = var0[var2];
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            var5 = var4;
            break;
         }

         var2++;
      }

      return var5;
   }

   @JvmStatic
   public inline fun ByteArray.find(predicate: (Byte) -> Boolean): Byte? {
      val var4: Int = var0.length;
      var var3: Int = 0;

      while (true) {
         if (var3 >= var4) {
            var5 = null;
            break;
         }

         val var2: Byte = var0[var3];
         if (var1.invoke(var0[var3]) as java.lang.Boolean) {
            var5 = var2;
            break;
         }

         var3++;
      }

      return var5;
   }

   @JvmStatic
   public inline fun CharArray.find(predicate: (Char) -> Boolean): Char? {
      val var4: Int = var0.length;
      var var3: Int = 0;

      while (true) {
         if (var3 >= var4) {
            var5 = null;
            break;
         }

         val var2: Char = var0[var3];
         if (var1.invoke(var0[var3]) as java.lang.Boolean) {
            var5 = var2;
            break;
         }

         var3++;
      }

      return var5;
   }

   @JvmStatic
   public inline fun DoubleArray.find(predicate: (Double) -> Boolean): Double? {
      val var5: Int = var0.length;
      var var4: Int = 0;

      while (true) {
         if (var4 >= var5) {
            var6 = null;
            break;
         }

         val var2: Double = var0[var4];
         if (var1.invoke(var0[var4]) as java.lang.Boolean) {
            var6 = var2;
            break;
         }

         var4++;
      }

      return var6;
   }

   @JvmStatic
   public inline fun FloatArray.find(predicate: (Float) -> Boolean): Float? {
      val var4: Int = var0.length;
      var var3: Int = 0;

      while (true) {
         if (var3 >= var4) {
            var5 = null;
            break;
         }

         val var2: Float = var0[var3];
         if (var1.invoke(var0[var3]) as java.lang.Boolean) {
            var5 = var2;
            break;
         }

         var3++;
      }

      return var5;
   }

   @JvmStatic
   public inline fun IntArray.find(predicate: (Int) -> Boolean): Int? {
      val var3: Int = var0.length;
      var var2: Int = 0;

      while (true) {
         if (var2 >= var3) {
            var5 = null;
            break;
         }

         val var4: Int = var0[var2];
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            var5 = var4;
            break;
         }

         var2++;
      }

      return var5;
   }

   @JvmStatic
   public inline fun LongArray.find(predicate: (Long) -> Boolean): Long? {
      val var3: Int = var0.length;
      var var2: Int = 0;

      while (true) {
         if (var2 >= var3) {
            var6 = null;
            break;
         }

         val var4: Long = var0[var2];
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            var6 = var4;
            break;
         }

         var2++;
      }

      return var6;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.find(predicate: (T) -> Boolean): T? {
      val var3: Int = ((Object[])var0).length;
      var var2: Int = 0;

      while (true) {
         if (var2 >= var3) {
            var0 = null;
            break;
         }

         val var4: Any = ((Object[])var0)[var2];
         if (var1.invoke(((Object[])var0)[var2]) as java.lang.Boolean) {
            var0 = var4;
            break;
         }

         var2++;
      }

      return (T)var0;
   }

   @JvmStatic
   public inline fun ShortArray.find(predicate: (Short) -> Boolean): Short? {
      val var4: Int = var0.length;
      var var3: Int = 0;

      while (true) {
         if (var3 >= var4) {
            var5 = null;
            break;
         }

         val var2: Short = var0[var3];
         if (var1.invoke(var0[var3]) as java.lang.Boolean) {
            var5 = var2;
            break;
         }

         var3++;
      }

      return var5;
   }

   @JvmStatic
   public inline fun BooleanArray.findLast(predicate: (Boolean) -> Boolean): Boolean? {
      var var2: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            val var4: Boolean = var0[var2];
            if (var1.invoke(var0[var2]) as java.lang.Boolean) {
               return var4;
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      return null;
   }

   @JvmStatic
   public inline fun ByteArray.findLast(predicate: (Byte) -> Boolean): Byte? {
      var var3: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var4: Int = var3 - 1;
            val var2: Byte = var0[var3];
            if (var1.invoke(var0[var3]) as java.lang.Boolean) {
               return var2;
            }

            if (var4 < 0) {
               break;
            }

            var3 = var4;
         }
      }

      return null;
   }

   @JvmStatic
   public inline fun CharArray.findLast(predicate: (Char) -> Boolean): Char? {
      var var3: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var4: Int = var3 - 1;
            val var2: Char = var0[var3];
            if (var1.invoke(var0[var3]) as java.lang.Boolean) {
               return var2;
            }

            if (var4 < 0) {
               break;
            }

            var3 = var4;
         }
      }

      return null;
   }

   @JvmStatic
   public inline fun DoubleArray.findLast(predicate: (Double) -> Boolean): Double? {
      var var4: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var5: Int = var4 - 1;
            val var2: Double = var0[var4];
            if (var1.invoke(var0[var4]) as java.lang.Boolean) {
               return var2;
            }

            if (var5 < 0) {
               break;
            }

            var4 = var5;
         }
      }

      return null;
   }

   @JvmStatic
   public inline fun FloatArray.findLast(predicate: (Float) -> Boolean): Float? {
      var var3: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var4: Int = var3 - 1;
            val var2: Float = var0[var3];
            if (var1.invoke(var0[var3]) as java.lang.Boolean) {
               return var2;
            }

            if (var4 < 0) {
               break;
            }

            var3 = var4;
         }
      }

      return null;
   }

   @JvmStatic
   public inline fun IntArray.findLast(predicate: (Int) -> Boolean): Int? {
      var var2: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            var2 = var0[var2];
            if (var1.invoke(var0[var2]) as java.lang.Boolean) {
               return var2;
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      return null;
   }

   @JvmStatic
   public inline fun LongArray.findLast(predicate: (Long) -> Boolean): Long? {
      var var2: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            val var4: Long = var0[var2];
            if (var1.invoke(var0[var2]) as java.lang.Boolean) {
               return var4;
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      return null;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.findLast(predicate: (T) -> Boolean): T? {
      var var2: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            val var4: Any = var0[var2];
            if (var1.invoke(var0[var2]) as java.lang.Boolean) {
               return (T)var4;
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      return null;
   }

   @JvmStatic
   public inline fun ShortArray.findLast(predicate: (Short) -> Boolean): Short? {
      var var3: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var4: Int = var3 - 1;
            val var2: Short = var0[var3];
            if (var1.invoke(var0[var3]) as java.lang.Boolean) {
               return var2;
            }

            if (var4 < 0) {
               break;
            }

            var3 = var4;
         }
      }

      return null;
   }

   @JvmStatic
   public fun ByteArray.first(): Byte {
      if (var0.length != 0) {
         return var0[0];
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun ByteArray.first(predicate: (Byte) -> Boolean): Byte {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Byte = var0[var3];
         if (var1.invoke(var0[var3]) as java.lang.Boolean) {
            return var2;
         }
      }

      throw new NoSuchElementException("Array contains no element matching the predicate.");
   }

   @JvmStatic
   public fun CharArray.first(): Char {
      if (var0.length != 0) {
         return var0[0];
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun CharArray.first(predicate: (Char) -> Boolean): Char {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Char = var0[var3];
         if (var1.invoke(var0[var3]) as java.lang.Boolean) {
            return var2;
         }
      }

      throw new NoSuchElementException("Array contains no element matching the predicate.");
   }

   @JvmStatic
   public fun DoubleArray.first(): Double {
      if (var0.length != 0) {
         return var0[0];
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun DoubleArray.first(predicate: (Double) -> Boolean): Double {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         val var2: Double = var0[var4];
         if (var1.invoke(var0[var4]) as java.lang.Boolean) {
            return var2;
         }
      }

      throw new NoSuchElementException("Array contains no element matching the predicate.");
   }

   @JvmStatic
   public fun FloatArray.first(): Float {
      if (var0.length != 0) {
         return var0[0];
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun FloatArray.first(predicate: (Float) -> Boolean): Float {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Float = var0[var3];
         if (var1.invoke(var0[var3]) as java.lang.Boolean) {
            return var2;
         }
      }

      throw new NoSuchElementException("Array contains no element matching the predicate.");
   }

   @JvmStatic
   public fun IntArray.first(): Int {
      if (var0.length != 0) {
         return var0[0];
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun IntArray.first(predicate: (Int) -> Boolean): Int {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Int = var0[var2];
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return var4;
         }
      }

      throw new NoSuchElementException("Array contains no element matching the predicate.");
   }

   @JvmStatic
   public fun LongArray.first(): Long {
      if (var0.length != 0) {
         return var0[0];
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun LongArray.first(predicate: (Long) -> Boolean): Long {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Long = var0[var2];
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return var4;
         }
      }

      throw new NoSuchElementException("Array contains no element matching the predicate.");
   }

   @JvmStatic
   public fun <T> Array<out T>.first(): T {
      if (var0.length != 0) {
         return (T)var0[0];
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun <T> Array<out T>.first(predicate: (T) -> Boolean): T {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Any = var0[var2];
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return (T)var4;
         }
      }

      throw new NoSuchElementException("Array contains no element matching the predicate.");
   }

   @JvmStatic
   public fun ShortArray.first(): Short {
      if (var0.length != 0) {
         return var0[0];
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun ShortArray.first(predicate: (Short) -> Boolean): Short {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Short = var0[var3];
         if (var1.invoke(var0[var3]) as java.lang.Boolean) {
            return var2;
         }
      }

      throw new NoSuchElementException("Array contains no element matching the predicate.");
   }

   @JvmStatic
   public fun BooleanArray.first(): Boolean {
      if (var0.length != 0) {
         return var0[0];
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun BooleanArray.first(predicate: (Boolean) -> Boolean): Boolean {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Boolean = var0[var2];
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return var4;
         }
      }

      throw new NoSuchElementException("Array contains no element matching the predicate.");
   }

   @JvmStatic
   public inline fun <T, R : Any> Array<out T>.firstNotNullOf(transform: (T) -> R?): R {
      val var3: Int = var0.length;
      var var2: Int = 0;

      var var4: Any;
      while (true) {
         if (var2 >= var3) {
            var4 = null;
            break;
         }

         val var5: Any = var1.invoke(var0[var2]);
         var4 = var5;
         if (var5 != null) {
            break;
         }

         var2++;
      }

      if (var4 != null) {
         return (R)var4;
      } else {
         throw new NoSuchElementException("No element of the array was transformed to a non-null value.");
      }
   }

   @JvmStatic
   public inline fun <T, R : Any> Array<out T>.firstNotNullOfOrNull(transform: (T) -> R?): R? {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Any = var1.invoke(var0[var2]);
         if (var4 != null) {
            return (R)var4;
         }
      }

      return null;
   }

   @JvmStatic
   public fun BooleanArray.firstOrNull(): Boolean? {
      val var1: java.lang.Boolean;
      if (var0.length == 0) {
         var1 = null;
      } else {
         var1 = var0[0];
      }

      return var1;
   }

   @JvmStatic
   public inline fun BooleanArray.firstOrNull(predicate: (Boolean) -> Boolean): Boolean? {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Boolean = var0[var2];
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return var4;
         }
      }

      return null;
   }

   @JvmStatic
   public fun ByteArray.firstOrNull(): Byte? {
      val var1: java.lang.Byte;
      if (var0.length == 0) {
         var1 = null;
      } else {
         var1 = var0[0];
      }

      return var1;
   }

   @JvmStatic
   public inline fun ByteArray.firstOrNull(predicate: (Byte) -> Boolean): Byte? {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Byte = var0[var3];
         if (var1.invoke(var0[var3]) as java.lang.Boolean) {
            return var2;
         }
      }

      return null;
   }

   @JvmStatic
   public fun CharArray.firstOrNull(): Char? {
      val var1: Character;
      if (var0.length == 0) {
         var1 = null;
      } else {
         var1 = var0[0];
      }

      return var1;
   }

   @JvmStatic
   public inline fun CharArray.firstOrNull(predicate: (Char) -> Boolean): Char? {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Char = var0[var3];
         if (var1.invoke(var0[var3]) as java.lang.Boolean) {
            return var2;
         }
      }

      return null;
   }

   @JvmStatic
   public fun DoubleArray.firstOrNull(): Double? {
      val var1: java.lang.Double;
      if (var0.length == 0) {
         var1 = null;
      } else {
         var1 = var0[0];
      }

      return var1;
   }

   @JvmStatic
   public inline fun DoubleArray.firstOrNull(predicate: (Double) -> Boolean): Double? {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         val var2: Double = var0[var4];
         if (var1.invoke(var0[var4]) as java.lang.Boolean) {
            return var2;
         }
      }

      return null;
   }

   @JvmStatic
   public fun FloatArray.firstOrNull(): Float? {
      val var1: java.lang.Float;
      if (var0.length == 0) {
         var1 = null;
      } else {
         var1 = var0[0];
      }

      return var1;
   }

   @JvmStatic
   public inline fun FloatArray.firstOrNull(predicate: (Float) -> Boolean): Float? {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Float = var0[var3];
         if (var1.invoke(var0[var3]) as java.lang.Boolean) {
            return var2;
         }
      }

      return null;
   }

   @JvmStatic
   public fun IntArray.firstOrNull(): Int? {
      val var1: Int;
      if (var0.length == 0) {
         var1 = null;
      } else {
         var1 = var0[0];
      }

      return var1;
   }

   @JvmStatic
   public inline fun IntArray.firstOrNull(predicate: (Int) -> Boolean): Int? {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Int = var0[var2];
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return var4;
         }
      }

      return null;
   }

   @JvmStatic
   public fun LongArray.firstOrNull(): Long? {
      val var1: java.lang.Long;
      if (var0.length == 0) {
         var1 = null;
      } else {
         var1 = var0[0];
      }

      return var1;
   }

   @JvmStatic
   public inline fun LongArray.firstOrNull(predicate: (Long) -> Boolean): Long? {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Long = var0[var2];
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return var4;
         }
      }

      return null;
   }

   @JvmStatic
   public fun <T> Array<out T>.firstOrNull(): T? {
      if (((Object[])var0).length == 0) {
         var0 = null;
      } else {
         var0 = ((Object[])var0)[0];
      }

      return (T)var0;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.firstOrNull(predicate: (T) -> Boolean): T? {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Any = var0[var2];
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return (T)var4;
         }
      }

      return null;
   }

   @JvmStatic
   public fun ShortArray.firstOrNull(): Short? {
      val var1: java.lang.Short;
      if (var0.length == 0) {
         var1 = null;
      } else {
         var1 = var0[0];
      }

      return var1;
   }

   @JvmStatic
   public inline fun ShortArray.firstOrNull(predicate: (Short) -> Boolean): Short? {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Short = var0[var3];
         if (var1.invoke(var0[var3]) as java.lang.Boolean) {
            return var2;
         }
      }

      return null;
   }

   @JvmStatic
   public inline fun <R> ByteArray.flatMap(transform: (Byte) -> Iterable<R>): List<R> {
      val var4: java.util.Collection = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         CollectionsKt.addAll(var4, var1.invoke(var0[var2]) as java.lang.Iterable);
      }

      return var4 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> CharArray.flatMap(transform: (Char) -> Iterable<R>): List<R> {
      val var4: java.util.Collection = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         CollectionsKt.addAll(var4, var1.invoke(var0[var2]) as java.lang.Iterable);
      }

      return var4 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> DoubleArray.flatMap(transform: (Double) -> Iterable<R>): List<R> {
      val var4: java.util.Collection = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         CollectionsKt.addAll(var4, var1.invoke(var0[var2]) as java.lang.Iterable);
      }

      return var4 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> FloatArray.flatMap(transform: (Float) -> Iterable<R>): List<R> {
      val var4: java.util.Collection = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         CollectionsKt.addAll(var4, var1.invoke(var0[var2]) as java.lang.Iterable);
      }

      return var4 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> IntArray.flatMap(transform: (Int) -> Iterable<R>): List<R> {
      val var4: java.util.Collection = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         CollectionsKt.addAll(var4, var1.invoke(var0[var2]) as java.lang.Iterable);
      }

      return var4 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> LongArray.flatMap(transform: (Long) -> Iterable<R>): List<R> {
      val var4: java.util.Collection = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         CollectionsKt.addAll(var4, var1.invoke(var0[var2]) as java.lang.Iterable);
      }

      return var4 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <T, R> Array<out T>.flatMap(transform: (T) -> Iterable<R>): List<R> {
      val var4: java.util.Collection = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         CollectionsKt.addAll(var4, var1.invoke(var0[var2]) as java.lang.Iterable);
      }

      return var4 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> ShortArray.flatMap(transform: (Short) -> Iterable<R>): List<R> {
      val var4: java.util.Collection = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         CollectionsKt.addAll(var4, var1.invoke(var0[var2]) as java.lang.Iterable);
      }

      return var4 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> BooleanArray.flatMap(transform: (Boolean) -> Iterable<R>): List<R> {
      val var4: java.util.Collection = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         CollectionsKt.addAll(var4, var1.invoke(var0[var2]) as java.lang.Iterable);
      }

      return var4 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> ByteArray.flatMapIndexed(transform: (Int, Byte) -> Iterable<R>): List<R> {
      val var5: java.util.Collection = new ArrayList();
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         CollectionsKt.addAll(var5, var1.invoke(var2, var0[var3]) as java.lang.Iterable);
         var3++;
      }

      return var5 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> CharArray.flatMapIndexed(transform: (Int, Char) -> Iterable<R>): List<R> {
      val var5: java.util.Collection = new ArrayList();
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         CollectionsKt.addAll(var5, var1.invoke(var2, var0[var3]) as java.lang.Iterable);
         var3++;
      }

      return var5 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> DoubleArray.flatMapIndexed(transform: (Int, Double) -> Iterable<R>): List<R> {
      val var5: java.util.Collection = new ArrayList();
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         CollectionsKt.addAll(var5, var1.invoke(var2, var0[var3]) as java.lang.Iterable);
         var3++;
      }

      return var5 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> FloatArray.flatMapIndexed(transform: (Int, Float) -> Iterable<R>): List<R> {
      val var5: java.util.Collection = new ArrayList();
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         CollectionsKt.addAll(var5, var1.invoke(var2, var0[var3]) as java.lang.Iterable);
         var3++;
      }

      return var5 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> IntArray.flatMapIndexed(transform: (Int, Int) -> Iterable<R>): List<R> {
      val var5: java.util.Collection = new ArrayList();
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         CollectionsKt.addAll(var5, var1.invoke(var2, var0[var3]) as java.lang.Iterable);
         var3++;
      }

      return var5 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> LongArray.flatMapIndexed(transform: (Int, Long) -> Iterable<R>): List<R> {
      val var5: java.util.Collection = new ArrayList();
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         CollectionsKt.addAll(var5, var1.invoke(var2, var0[var3]) as java.lang.Iterable);
         var3++;
      }

      return var5 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <T, R> Array<out T>.flatMapIndexed(transform: (Int, T) -> Iterable<R>): List<R> {
      val var5: java.util.Collection = new ArrayList();
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         CollectionsKt.addAll(var5, var1.invoke(var2, var0[var3]) as java.lang.Iterable);
         var3++;
      }

      return var5 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> ShortArray.flatMapIndexed(transform: (Int, Short) -> Iterable<R>): List<R> {
      val var5: java.util.Collection = new ArrayList();
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         CollectionsKt.addAll(var5, var1.invoke(var2, var0[var3]) as java.lang.Iterable);
         var3++;
      }

      return var5 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> BooleanArray.flatMapIndexed(transform: (Int, Boolean) -> Iterable<R>): List<R> {
      val var5: java.util.Collection = new ArrayList();
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         CollectionsKt.addAll(var5, var1.invoke(var2, var0[var3]) as java.lang.Iterable);
         var3++;
      }

      return var5 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> ByteArray.flatMapIndexedTo(destination: C, transform: (Int, Byte) -> Iterable<R>): C {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         CollectionsKt.addAll(var1, var2.invoke(var3, var0[var4]) as java.lang.Iterable);
         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> CharArray.flatMapIndexedTo(destination: C, transform: (Int, Char) -> Iterable<R>): C {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         CollectionsKt.addAll(var1, var2.invoke(var3, var0[var4]) as java.lang.Iterable);
         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> DoubleArray.flatMapIndexedTo(destination: C, transform: (Int, Double) -> Iterable<R>): C {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         CollectionsKt.addAll(var1, var2.invoke(var3, var0[var4]) as java.lang.Iterable);
         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> FloatArray.flatMapIndexedTo(destination: C, transform: (Int, Float) -> Iterable<R>): C {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         CollectionsKt.addAll(var1, var2.invoke(var3, var0[var4]) as java.lang.Iterable);
         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> IntArray.flatMapIndexedTo(destination: C, transform: (Int, Int) -> Iterable<R>): C {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         CollectionsKt.addAll(var1, var2.invoke(var3, var0[var4]) as java.lang.Iterable);
         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> LongArray.flatMapIndexedTo(destination: C, transform: (Int, Long) -> Iterable<R>): C {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         CollectionsKt.addAll(var1, var2.invoke(var3, var0[var4]) as java.lang.Iterable);
         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T, R, C : MutableCollection<in R>> Array<out T>.flatMapIndexedTo(destination: C, transform: (Int, T) -> Iterable<R>): C {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         CollectionsKt.addAll(var1, var2.invoke(var3, var0[var4]) as java.lang.Iterable);
         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> ShortArray.flatMapIndexedTo(destination: C, transform: (Int, Short) -> Iterable<R>): C {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         CollectionsKt.addAll(var1, var2.invoke(var3, var0[var4]) as java.lang.Iterable);
         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> BooleanArray.flatMapIndexedTo(destination: C, transform: (Int, Boolean) -> Iterable<R>): C {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         CollectionsKt.addAll(var1, var2.invoke(var3, var0[var4]) as java.lang.Iterable);
         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T, R> Array<out T>.flatMapIndexed(transform: (Int, T) -> Sequence<R>): List<R> {
      val var5: java.util.Collection = new ArrayList();
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         CollectionsKt.addAll(var5, var1.invoke(var2, var0[var3]) as Sequence);
         var3++;
      }

      return var5 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <T, R, C : MutableCollection<in R>> Array<out T>.flatMapIndexedTo(destination: C, transform: (Int, T) -> Sequence<R>): C {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         CollectionsKt.addAll(var1, var2.invoke(var3, var0[var4]) as Sequence);
         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T, R> Array<out T>.flatMap(transform: (T) -> Sequence<R>): List<R> {
      val var4: java.util.Collection = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         CollectionsKt.addAll(var4, var1.invoke(var0[var2]) as Sequence);
      }

      return var4 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <T, R, C : MutableCollection<in R>> Array<out T>.flatMapTo(destination: C, transform: (T) -> Sequence<R>): C {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         CollectionsKt.addAll(var1, var2.invoke(var0[var3]) as Sequence);
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> ByteArray.flatMapTo(destination: C, transform: (Byte) -> Iterable<R>): C {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         CollectionsKt.addAll(var1, var2.invoke(var0[var3]) as java.lang.Iterable);
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> CharArray.flatMapTo(destination: C, transform: (Char) -> Iterable<R>): C {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         CollectionsKt.addAll(var1, var2.invoke(var0[var3]) as java.lang.Iterable);
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> DoubleArray.flatMapTo(destination: C, transform: (Double) -> Iterable<R>): C {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         CollectionsKt.addAll(var1, var2.invoke(var0[var3]) as java.lang.Iterable);
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> FloatArray.flatMapTo(destination: C, transform: (Float) -> Iterable<R>): C {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         CollectionsKt.addAll(var1, var2.invoke(var0[var3]) as java.lang.Iterable);
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> IntArray.flatMapTo(destination: C, transform: (Int) -> Iterable<R>): C {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         CollectionsKt.addAll(var1, var2.invoke(var0[var3]) as java.lang.Iterable);
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> LongArray.flatMapTo(destination: C, transform: (Long) -> Iterable<R>): C {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         CollectionsKt.addAll(var1, var2.invoke(var0[var3]) as java.lang.Iterable);
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T, R, C : MutableCollection<in R>> Array<out T>.flatMapTo(destination: C, transform: (T) -> Iterable<R>): C {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         CollectionsKt.addAll(var1, var2.invoke(var0[var3]) as java.lang.Iterable);
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> ShortArray.flatMapTo(destination: C, transform: (Short) -> Iterable<R>): C {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         CollectionsKt.addAll(var1, var2.invoke(var0[var3]) as java.lang.Iterable);
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> BooleanArray.flatMapTo(destination: C, transform: (Boolean) -> Iterable<R>): C {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         CollectionsKt.addAll(var1, var2.invoke(var0[var3]) as java.lang.Iterable);
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R> ByteArray.fold(initial: R, operation: (R, Byte) -> R): R {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var1 = var2.invoke(var1, var0[var3]);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> CharArray.fold(initial: R, operation: (R, Char) -> R): R {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var1 = var2.invoke(var1, var0[var3]);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> DoubleArray.fold(initial: R, operation: (R, Double) -> R): R {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var1 = var2.invoke(var1, var0[var3]);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> FloatArray.fold(initial: R, operation: (R, Float) -> R): R {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var1 = var2.invoke(var1, var0[var3]);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> IntArray.fold(initial: R, operation: (R, Int) -> R): R {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var1 = var2.invoke(var1, var0[var3]);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> LongArray.fold(initial: R, operation: (R, Long) -> R): R {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var1 = var2.invoke(var1, var0[var3]);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <T, R> Array<out T>.fold(initial: R, operation: (R, T) -> R): R {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var1 = var2.invoke(var1, var0[var3]);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> ShortArray.fold(initial: R, operation: (R, Short) -> R): R {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var1 = var2.invoke(var1, var0[var3]);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> BooleanArray.fold(initial: R, operation: (R, Boolean) -> R): R {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var1 = var2.invoke(var1, var0[var3]);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> ByteArray.foldIndexed(initial: R, operation: (Int, R, Byte) -> R): R {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         var1 = var2.invoke(var3, var1, var0[var4]);
         var4++;
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> CharArray.foldIndexed(initial: R, operation: (Int, R, Char) -> R): R {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         var1 = var2.invoke(var3, var1, var0[var4]);
         var4++;
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> DoubleArray.foldIndexed(initial: R, operation: (Int, R, Double) -> R): R {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         var1 = var2.invoke(var3, var1, var0[var4]);
         var4++;
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> FloatArray.foldIndexed(initial: R, operation: (Int, R, Float) -> R): R {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         var1 = var2.invoke(var3, var1, var0[var4]);
         var4++;
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> IntArray.foldIndexed(initial: R, operation: (Int, R, Int) -> R): R {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         var1 = var2.invoke(var3, var1, var0[var4]);
         var4++;
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> LongArray.foldIndexed(initial: R, operation: (Int, R, Long) -> R): R {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         var1 = var2.invoke(var3, var1, var0[var4]);
         var4++;
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <T, R> Array<out T>.foldIndexed(initial: R, operation: (Int, R, T) -> R): R {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         var1 = var2.invoke(var3, var1, var0[var4]);
         var4++;
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> ShortArray.foldIndexed(initial: R, operation: (Int, R, Short) -> R): R {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         var1 = var2.invoke(var3, var1, var0[var4]);
         var4++;
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> BooleanArray.foldIndexed(initial: R, operation: (Int, R, Boolean) -> R): R {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         var1 = var2.invoke(var3, var1, var0[var4]);
         var4++;
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> ByteArray.foldRight(initial: R, operation: (Byte, R) -> R): R {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 >= 0; var3--) {
         var1 = var2.invoke(var0[var3], var1);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> CharArray.foldRight(initial: R, operation: (Char, R) -> R): R {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 >= 0; var3--) {
         var1 = var2.invoke(var0[var3], var1);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> DoubleArray.foldRight(initial: R, operation: (Double, R) -> R): R {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 >= 0; var3--) {
         var1 = var2.invoke(var0[var3], var1);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> FloatArray.foldRight(initial: R, operation: (Float, R) -> R): R {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 >= 0; var3--) {
         var1 = var2.invoke(var0[var3], var1);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> IntArray.foldRight(initial: R, operation: (Int, R) -> R): R {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 >= 0; var3--) {
         var1 = var2.invoke(var0[var3], var1);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> LongArray.foldRight(initial: R, operation: (Long, R) -> R): R {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 >= 0; var3--) {
         var1 = var2.invoke(var0[var3], var1);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <T, R> Array<out T>.foldRight(initial: R, operation: (T, R) -> R): R {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 >= 0; var3--) {
         var1 = var2.invoke(var0[var3], var1);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> ShortArray.foldRight(initial: R, operation: (Short, R) -> R): R {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 >= 0; var3--) {
         var1 = var2.invoke(var0[var3], var1);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> BooleanArray.foldRight(initial: R, operation: (Boolean, R) -> R): R {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 >= 0; var3--) {
         var1 = var2.invoke(var0[var3], var1);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> ByteArray.foldRightIndexed(initial: R, operation: (Int, Byte, R) -> R): R {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 >= 0; var3--) {
         var1 = var2.invoke(var3, var0[var3], var1);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> CharArray.foldRightIndexed(initial: R, operation: (Int, Char, R) -> R): R {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 >= 0; var3--) {
         var1 = var2.invoke(var3, var0[var3], var1);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> DoubleArray.foldRightIndexed(initial: R, operation: (Int, Double, R) -> R): R {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 >= 0; var3--) {
         var1 = var2.invoke(var3, var0[var3], var1);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> FloatArray.foldRightIndexed(initial: R, operation: (Int, Float, R) -> R): R {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 >= 0; var3--) {
         var1 = var2.invoke(var3, var0[var3], var1);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> IntArray.foldRightIndexed(initial: R, operation: (Int, Int, R) -> R): R {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 >= 0; var3--) {
         var1 = var2.invoke(var3, var0[var3], var1);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> LongArray.foldRightIndexed(initial: R, operation: (Int, Long, R) -> R): R {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 >= 0; var3--) {
         var1 = var2.invoke(var3, var0[var3], var1);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <T, R> Array<out T>.foldRightIndexed(initial: R, operation: (Int, T, R) -> R): R {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 >= 0; var3--) {
         var1 = var2.invoke(var3, var0[var3], var1);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> ShortArray.foldRightIndexed(initial: R, operation: (Int, Short, R) -> R): R {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 >= 0; var3--) {
         var1 = var2.invoke(var3, var0[var3], var1);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> BooleanArray.foldRightIndexed(initial: R, operation: (Int, Boolean, R) -> R): R {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 >= 0; var3--) {
         var1 = var2.invoke(var3, var0[var3], var1);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun ByteArray.forEach(action: (Byte) -> Unit) {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var1.invoke(var0[var2]);
      }
   }

   @JvmStatic
   public inline fun CharArray.forEach(action: (Char) -> Unit) {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var1.invoke(var0[var2]);
      }
   }

   @JvmStatic
   public inline fun DoubleArray.forEach(action: (Double) -> Unit) {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var1.invoke(var0[var2]);
      }
   }

   @JvmStatic
   public inline fun FloatArray.forEach(action: (Float) -> Unit) {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var1.invoke(var0[var2]);
      }
   }

   @JvmStatic
   public inline fun IntArray.forEach(action: (Int) -> Unit) {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var1.invoke(var0[var2]);
      }
   }

   @JvmStatic
   public inline fun LongArray.forEach(action: (Long) -> Unit) {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var1.invoke(var0[var2]);
      }
   }

   @JvmStatic
   public inline fun <T> Array<out T>.forEach(action: (T) -> Unit) {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var1.invoke(var0[var2]);
      }
   }

   @JvmStatic
   public inline fun ShortArray.forEach(action: (Short) -> Unit) {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var1.invoke(var0[var2]);
      }
   }

   @JvmStatic
   public inline fun BooleanArray.forEach(action: (Boolean) -> Unit) {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var1.invoke(var0[var2]);
      }
   }

   @JvmStatic
   public inline fun ByteArray.forEachIndexed(action: (Int, Byte) -> Unit) {
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var1.invoke(var2, var0[var3]);
         var3++;
      }
   }

   @JvmStatic
   public inline fun CharArray.forEachIndexed(action: (Int, Char) -> Unit) {
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var1.invoke(var2, var0[var3]);
         var3++;
      }
   }

   @JvmStatic
   public inline fun DoubleArray.forEachIndexed(action: (Int, Double) -> Unit) {
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var1.invoke(var2, var0[var3]);
         var3++;
      }
   }

   @JvmStatic
   public inline fun FloatArray.forEachIndexed(action: (Int, Float) -> Unit) {
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var1.invoke(var2, var0[var3]);
         var3++;
      }
   }

   @JvmStatic
   public inline fun IntArray.forEachIndexed(action: (Int, Int) -> Unit) {
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var1.invoke(var2, var0[var3]);
         var3++;
      }
   }

   @JvmStatic
   public inline fun LongArray.forEachIndexed(action: (Int, Long) -> Unit) {
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var1.invoke(var2, var0[var3]);
         var3++;
      }
   }

   @JvmStatic
   public inline fun <T> Array<out T>.forEachIndexed(action: (Int, T) -> Unit) {
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var1.invoke(var2, var0[var3]);
         var3++;
      }
   }

   @JvmStatic
   public inline fun ShortArray.forEachIndexed(action: (Int, Short) -> Unit) {
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var1.invoke(var2, var0[var3]);
         var3++;
      }
   }

   @JvmStatic
   public inline fun BooleanArray.forEachIndexed(action: (Int, Boolean) -> Unit) {
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var1.invoke(var2, var0[var3]);
         var3++;
      }
   }

   @JvmStatic
   public inline fun ByteArray.getOrElse(index: Int, defaultValue: (Int) -> Byte): Byte {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      val var3: Byte;
      if (var1 >= 0 && var1 < var0.length) {
         var3 = var0[var1];
      } else {
         var3 = (var2.invoke(var1) as java.lang.Number).byteValue();
      }

      return var3;
   }

   @JvmStatic
   public inline fun CharArray.getOrElse(index: Int, defaultValue: (Int) -> Char): Char {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      val var3: Char;
      if (var1 >= 0 && var1 < var0.length) {
         var3 = var0[var1];
      } else {
         var3 = var2.invoke(var1) as Character;
      }

      return var3;
   }

   @JvmStatic
   public inline fun DoubleArray.getOrElse(index: Int, defaultValue: (Int) -> Double): Double {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      val var3: Double;
      if (var1 >= 0 && var1 < var0.length) {
         var3 = var0[var1];
      } else {
         var3 = (var2.invoke(var1) as java.lang.Number).doubleValue();
      }

      return var3;
   }

   @JvmStatic
   public inline fun FloatArray.getOrElse(index: Int, defaultValue: (Int) -> Float): Float {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      val var3: Float;
      if (var1 >= 0 && var1 < var0.length) {
         var3 = var0[var1];
      } else {
         var3 = (var2.invoke(var1) as java.lang.Number).floatValue();
      }

      return var3;
   }

   @JvmStatic
   public inline fun IntArray.getOrElse(index: Int, defaultValue: (Int) -> Int): Int {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      if (var1 >= 0 && var1 < var0.length) {
         var1 = var0[var1];
      } else {
         var1 = (var2.invoke(var1) as java.lang.Number).intValue();
      }

      return var1;
   }

   @JvmStatic
   public inline fun LongArray.getOrElse(index: Int, defaultValue: (Int) -> Long): Long {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      val var3: Long;
      if (var1 >= 0 && var1 < var0.length) {
         var3 = var0[var1];
      } else {
         var3 = (var2.invoke(var1) as java.lang.Number).longValue();
      }

      return var3;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.getOrElse(index: Int, defaultValue: (Int) -> T): T {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      if (var1 >= 0 && var1 < ((Object[])var0).length) {
         var0 = ((Object[])var0)[var1];
      } else {
         var0 = var2.invoke(var1);
      }

      return (T)var0;
   }

   @JvmStatic
   public inline fun ShortArray.getOrElse(index: Int, defaultValue: (Int) -> Short): Short {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      val var3: Short;
      if (var1 >= 0 && var1 < var0.length) {
         var3 = var0[var1];
      } else {
         var3 = (var2.invoke(var1) as java.lang.Number).shortValue();
      }

      return var3;
   }

   @JvmStatic
   public inline fun BooleanArray.getOrElse(index: Int, defaultValue: (Int) -> Boolean): Boolean {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      val var3: Boolean;
      if (var1 >= 0 && var1 < var0.length) {
         var3 = var0[var1];
      } else {
         var3 = var2.invoke(var1) as java.lang.Boolean;
      }

      return var3;
   }

   @JvmStatic
   public fun BooleanArray.getOrNull(index: Int): Boolean? {
      val var2: java.lang.Boolean;
      if (var1 >= 0 && var1 < var0.length) {
         var2 = var0[var1];
      } else {
         var2 = null;
      }

      return var2;
   }

   @JvmStatic
   public fun ByteArray.getOrNull(index: Int): Byte? {
      val var2: java.lang.Byte;
      if (var1 >= 0 && var1 < var0.length) {
         var2 = var0[var1];
      } else {
         var2 = null;
      }

      return var2;
   }

   @JvmStatic
   public fun CharArray.getOrNull(index: Int): Char? {
      val var2: Character;
      if (var1 >= 0 && var1 < var0.length) {
         var2 = var0[var1];
      } else {
         var2 = null;
      }

      return var2;
   }

   @JvmStatic
   public fun DoubleArray.getOrNull(index: Int): Double? {
      val var2: java.lang.Double;
      if (var1 >= 0 && var1 < var0.length) {
         var2 = var0[var1];
      } else {
         var2 = null;
      }

      return var2;
   }

   @JvmStatic
   public fun FloatArray.getOrNull(index: Int): Float? {
      val var2: java.lang.Float;
      if (var1 >= 0 && var1 < var0.length) {
         var2 = var0[var1];
      } else {
         var2 = null;
      }

      return var2;
   }

   @JvmStatic
   public fun IntArray.getOrNull(index: Int): Int? {
      val var2: Int;
      if (var1 >= 0 && var1 < var0.length) {
         var2 = var0[var1];
      } else {
         var2 = null;
      }

      return var2;
   }

   @JvmStatic
   public fun LongArray.getOrNull(index: Int): Long? {
      val var2: java.lang.Long;
      if (var1 >= 0 && var1 < var0.length) {
         var2 = var0[var1];
      } else {
         var2 = null;
      }

      return var2;
   }

   @JvmStatic
   public fun <T> Array<out T>.getOrNull(index: Int): T? {
      if (var1 >= 0 && var1 < ((Object[])var0).length) {
         var0 = ((Object[])var0)[var1];
      } else {
         var0 = null;
      }

      return (T)var0;
   }

   @JvmStatic
   public fun ShortArray.getOrNull(index: Int): Short? {
      val var2: java.lang.Short;
      if (var1 >= 0 && var1 < var0.length) {
         var2 = var0[var1];
      } else {
         var2 = null;
      }

      return var2;
   }

   @JvmStatic
   public inline fun <K> ByteArray.groupBy(keySelector: (Byte) -> K): Map<K, List<Byte>> {
      val var7: java.util.Map = new LinkedHashMap();
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Byte = var0[var3];
         val var8: Any = var1.invoke(var0[var3]);
         val var6: Any = var7.get(var8);
         var var5: Any = var6;
         if (var6 == null) {
            var5 = new ArrayList();
            var7.put(var8, var5);
         }

         (var5 as java.util.List).add(var2);
      }

      return var7;
   }

   @JvmStatic
   public inline fun <K, V> ByteArray.groupBy(keySelector: (Byte) -> K, valueTransform: (Byte) -> V): Map<K, List<V>> {
      val var9: java.util.Map = new LinkedHashMap();
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         val var3: Byte = var0[var4];
         val var8: Any = var1.invoke(var0[var4]);
         val var7: Any = var9.get(var8);
         var var6: Any = var7;
         if (var7 == null) {
            var6 = new ArrayList();
            var9.put(var8, var6);
         }

         (var6 as java.util.List).add(var2.invoke(var3));
      }

      return var9;
   }

   @JvmStatic
   public inline fun <K> CharArray.groupBy(keySelector: (Char) -> K): Map<K, List<Char>> {
      val var8: java.util.Map = new LinkedHashMap();
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Char = var0[var3];
         val var7: Any = var1.invoke(var0[var3]);
         val var6: Any = var8.get(var7);
         var var5: Any = var6;
         if (var6 == null) {
            var5 = new ArrayList();
            var8.put(var7, var5);
         }

         (var5 as java.util.List).add(var2);
      }

      return var8;
   }

   @JvmStatic
   public inline fun <K, V> CharArray.groupBy(keySelector: (Char) -> K, valueTransform: (Char) -> V): Map<K, List<V>> {
      val var8: java.util.Map = new LinkedHashMap();
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         val var3: Char = var0[var4];
         val var9: Any = var1.invoke(var0[var4]);
         val var7: Any = var8.get(var9);
         var var6: Any = var7;
         if (var7 == null) {
            var6 = new ArrayList();
            var8.put(var9, var6);
         }

         (var6 as java.util.List).add(var2.invoke(var3));
      }

      return var8;
   }

   @JvmStatic
   public inline fun <K> DoubleArray.groupBy(keySelector: (Double) -> K): Map<K, List<Double>> {
      val var8: java.util.Map = new LinkedHashMap();
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         val var2: Double = var0[var4];
         val var9: Any = var1.invoke(var0[var4]);
         val var7: Any = var8.get(var9);
         var var6: Any = var7;
         if (var7 == null) {
            var6 = new ArrayList();
            var8.put(var9, var6);
         }

         (var6 as java.util.List).add(var2);
      }

      return var8;
   }

   @JvmStatic
   public inline fun <K, V> DoubleArray.groupBy(keySelector: (Double) -> K, valueTransform: (Double) -> V): Map<K, List<V>> {
      val var10: java.util.Map = new LinkedHashMap();
      val var6: Int = var0.length;

      for (int var5 = 0; var5 < var6; var5++) {
         val var3: Double = var0[var5];
         val var9: Any = var1.invoke(var0[var5]);
         val var8: Any = var10.get(var9);
         var var7: Any = var8;
         if (var8 == null) {
            var7 = new ArrayList();
            var10.put(var9, var7);
         }

         (var7 as java.util.List).add(var2.invoke(var3));
      }

      return var10;
   }

   @JvmStatic
   public inline fun <K> FloatArray.groupBy(keySelector: (Float) -> K): Map<K, List<Float>> {
      val var8: java.util.Map = new LinkedHashMap();
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Float = var0[var3];
         val var7: Any = var1.invoke(var0[var3]);
         val var6: Any = var8.get(var7);
         var var5: Any = var6;
         if (var6 == null) {
            var5 = new ArrayList();
            var8.put(var7, var5);
         }

         (var5 as java.util.List).add(var2);
      }

      return var8;
   }

   @JvmStatic
   public inline fun <K, V> FloatArray.groupBy(keySelector: (Float) -> K, valueTransform: (Float) -> V): Map<K, List<V>> {
      val var9: java.util.Map = new LinkedHashMap();
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         val var3: Float = var0[var4];
         val var8: Any = var1.invoke(var0[var4]);
         val var7: Any = var9.get(var8);
         var var6: Any = var7;
         if (var7 == null) {
            var6 = new ArrayList();
            var9.put(var8, var6);
         }

         (var6 as java.util.List).add(var2.invoke(var3));
      }

      return var9;
   }

   @JvmStatic
   public inline fun <K> IntArray.groupBy(keySelector: (Int) -> K): Map<K, List<Int>> {
      val var7: java.util.Map = new LinkedHashMap();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Int = var0[var2];
         val var8: Any = var1.invoke(var0[var2]);
         val var6: Any = var7.get(var8);
         var var5: Any = var6;
         if (var6 == null) {
            var5 = new ArrayList();
            var7.put(var8, var5);
         }

         (var5 as java.util.List).add(var4);
      }

      return var7;
   }

   @JvmStatic
   public inline fun <K, V> IntArray.groupBy(keySelector: (Int) -> K, valueTransform: (Int) -> V): Map<K, List<V>> {
      val var8: java.util.Map = new LinkedHashMap();
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Int = var0[var3];
         val var9: Any = var1.invoke(var0[var3]);
         val var7: Any = var8.get(var9);
         var var6: Any = var7;
         if (var7 == null) {
            var6 = new ArrayList();
            var8.put(var9, var6);
         }

         (var6 as java.util.List).add(var2.invoke(var5));
      }

      return var8;
   }

   @JvmStatic
   public inline fun <K> LongArray.groupBy(keySelector: (Long) -> K): Map<K, List<Long>> {
      val var8: java.util.Map = new LinkedHashMap();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Long = var0[var2];
         val var9: Any = var1.invoke(var0[var2]);
         val var7: Any = var8.get(var9);
         var var6: Any = var7;
         if (var7 == null) {
            var6 = new ArrayList();
            var8.put(var9, var6);
         }

         (var6 as java.util.List).add(var4);
      }

      return var8;
   }

   @JvmStatic
   public inline fun <K, V> LongArray.groupBy(keySelector: (Long) -> K, valueTransform: (Long) -> V): Map<K, List<V>> {
      val var10: java.util.Map = new LinkedHashMap();
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Long = var0[var3];
         val var9: Any = var1.invoke(var0[var3]);
         val var8: Any = var10.get(var9);
         var var7: Any = var8;
         if (var8 == null) {
            var7 = new ArrayList();
            var10.put(var9, var7);
         }

         (var7 as java.util.List).add(var2.invoke(var5));
      }

      return var10;
   }

   @JvmStatic
   public inline fun <T, K> Array<out T>.groupBy(keySelector: (T) -> K): Map<K, List<T>> {
      val var6: java.util.Map = new LinkedHashMap();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var7: Any = var0[var2];
         val var8: Any = var1.invoke(var0[var2]);
         val var5: Any = var6.get(var8);
         var var4: Any = var5;
         if (var5 == null) {
            var4 = new ArrayList();
            var6.put(var8, var4);
         }

         (var4 as java.util.List).add(var7);
      }

      return var6;
   }

   @JvmStatic
   public inline fun <T, K, V> Array<out T>.groupBy(keySelector: (T) -> K, valueTransform: (T) -> V): Map<K, List<V>> {
      val var7: java.util.Map = new LinkedHashMap();
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var8: Any = var0[var3];
         val var9: Any = var1.invoke(var0[var3]);
         val var6: Any = var7.get(var9);
         var var5: Any = var6;
         if (var6 == null) {
            var5 = new ArrayList();
            var7.put(var9, var5);
         }

         (var5 as java.util.List).add(var2.invoke(var8));
      }

      return var7;
   }

   @JvmStatic
   public inline fun <K> ShortArray.groupBy(keySelector: (Short) -> K): Map<K, List<Short>> {
      val var7: java.util.Map = new LinkedHashMap();
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Short = var0[var3];
         val var8: Any = var1.invoke(var0[var3]);
         val var6: Any = var7.get(var8);
         var var5: Any = var6;
         if (var6 == null) {
            var5 = new ArrayList();
            var7.put(var8, var5);
         }

         (var5 as java.util.List).add(var2);
      }

      return var7;
   }

   @JvmStatic
   public inline fun <K, V> ShortArray.groupBy(keySelector: (Short) -> K, valueTransform: (Short) -> V): Map<K, List<V>> {
      val var8: java.util.Map = new LinkedHashMap();
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         val var3: Short = var0[var4];
         val var9: Any = var1.invoke(var0[var4]);
         val var7: Any = var8.get(var9);
         var var6: Any = var7;
         if (var7 == null) {
            var6 = new ArrayList();
            var8.put(var9, var6);
         }

         (var6 as java.util.List).add(var2.invoke(var3));
      }

      return var8;
   }

   @JvmStatic
   public inline fun <K> BooleanArray.groupBy(keySelector: (Boolean) -> K): Map<K, List<Boolean>> {
      val var8: java.util.Map = new LinkedHashMap();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Boolean = var0[var2];
         val var7: Any = var1.invoke(var0[var2]);
         val var6: Any = var8.get(var7);
         var var5: Any = var6;
         if (var6 == null) {
            var5 = new ArrayList();
            var8.put(var7, var5);
         }

         (var5 as java.util.List).add(var4);
      }

      return var8;
   }

   @JvmStatic
   public inline fun <K, V> BooleanArray.groupBy(keySelector: (Boolean) -> K, valueTransform: (Boolean) -> V): Map<K, List<V>> {
      val var9: java.util.Map = new LinkedHashMap();
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Boolean = var0[var3];
         val var8: Any = var1.invoke(var0[var3]);
         val var7: Any = var9.get(var8);
         var var6: Any = var7;
         if (var7 == null) {
            var6 = new ArrayList();
            var9.put(var8, var6);
         }

         (var6 as java.util.List).add(var2.invoke(var5));
      }

      return var9;
   }

   @JvmStatic
   public inline fun <K, M : MutableMap<in K, MutableList<Byte>>> ByteArray.groupByTo(destination: M, keySelector: (Byte) -> K): M {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         val var3: Byte = var0[var4];
         val var8: Any = var2.invoke(var0[var4]);
         val var7: Any = var1.get(var8);
         var var6: Any = var7;
         if (var7 == null) {
            var6 = new ArrayList();
            var1.put(var8, var6);
         }

         (var6 as java.util.List).add(var3);
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, MutableList<V>>> ByteArray.groupByTo(destination: M, keySelector: (Byte) -> K, valueTransform: (Byte) -> V): M {
      val var6: Int = var0.length;

      for (int var5 = 0; var5 < var6; var5++) {
         val var4: Byte = var0[var5];
         val var9: Any = var2.invoke(var0[var5]);
         val var8: Any = var1.get(var9);
         var var7: Any = var8;
         if (var8 == null) {
            var7 = new ArrayList();
            var1.put(var9, var7);
         }

         (var7 as java.util.List).add(var3.invoke(var4));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, M : MutableMap<in K, MutableList<Char>>> CharArray.groupByTo(destination: M, keySelector: (Char) -> K): M {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         val var3: Char = var0[var4];
         val var8: Any = var2.invoke(var0[var4]);
         val var7: Any = var1.get(var8);
         var var6: Any = var7;
         if (var7 == null) {
            var6 = new ArrayList();
            var1.put(var8, var6);
         }

         (var6 as java.util.List).add(var3);
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, MutableList<V>>> CharArray.groupByTo(destination: M, keySelector: (Char) -> K, valueTransform: (Char) -> V): M {
      val var6: Int = var0.length;

      for (int var5 = 0; var5 < var6; var5++) {
         val var4: Char = var0[var5];
         val var9: Any = var2.invoke(var0[var5]);
         val var8: Any = var1.get(var9);
         var var7: Any = var8;
         if (var8 == null) {
            var7 = new ArrayList();
            var1.put(var9, var7);
         }

         (var7 as java.util.List).add(var3.invoke(var4));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, M : MutableMap<in K, MutableList<Double>>> DoubleArray.groupByTo(destination: M, keySelector: (Double) -> K): M {
      val var6: Int = var0.length;

      for (int var5 = 0; var5 < var6; var5++) {
         val var3: Double = var0[var5];
         val var9: Any = var2.invoke(var0[var5]);
         val var8: Any = var1.get(var9);
         var var7: Any = var8;
         if (var8 == null) {
            var7 = new ArrayList();
            var1.put(var9, var7);
         }

         (var7 as java.util.List).add(var3);
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, MutableList<V>>> DoubleArray.groupByTo(
      destination: M,
      keySelector: (Double) -> K,
      valueTransform: (Double) -> V
   ): M {
      val var7: Int = var0.length;

      for (int var6 = 0; var6 < var7; var6++) {
         val var4: Double = var0[var6];
         val var10: Any = var2.invoke(var0[var6]);
         val var9: Any = var1.get(var10);
         var var8: Any = var9;
         if (var9 == null) {
            var8 = new ArrayList();
            var1.put(var10, var8);
         }

         (var8 as java.util.List).add(var3.invoke(var4));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, M : MutableMap<in K, MutableList<Float>>> FloatArray.groupByTo(destination: M, keySelector: (Float) -> K): M {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         val var3: Float = var0[var4];
         val var8: Any = var2.invoke(var0[var4]);
         val var7: Any = var1.get(var8);
         var var6: Any = var7;
         if (var7 == null) {
            var6 = new ArrayList();
            var1.put(var8, var6);
         }

         (var6 as java.util.List).add(var3);
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, MutableList<V>>> FloatArray.groupByTo(destination: M, keySelector: (Float) -> K, valueTransform: (Float) -> V): M {
      val var6: Int = var0.length;

      for (int var5 = 0; var5 < var6; var5++) {
         val var4: Float = var0[var5];
         val var9: Any = var2.invoke(var0[var5]);
         val var8: Any = var1.get(var9);
         var var7: Any = var8;
         if (var8 == null) {
            var7 = new ArrayList();
            var1.put(var9, var7);
         }

         (var7 as java.util.List).add(var3.invoke(var4));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, M : MutableMap<in K, MutableList<Int>>> IntArray.groupByTo(destination: M, keySelector: (Int) -> K): M {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Int = var0[var3];
         val var8: Any = var2.invoke(var0[var3]);
         val var7: Any = var1.get(var8);
         var var6: Any = var7;
         if (var7 == null) {
            var6 = new ArrayList();
            var1.put(var8, var6);
         }

         (var6 as java.util.List).add(var5);
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, MutableList<V>>> IntArray.groupByTo(destination: M, keySelector: (Int) -> K, valueTransform: (Int) -> V): M {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         val var6: Int = var0[var4];
         val var9: Any = var2.invoke(var0[var4]);
         val var8: Any = var1.get(var9);
         var var7: Any = var8;
         if (var8 == null) {
            var7 = new ArrayList();
            var1.put(var9, var7);
         }

         (var7 as java.util.List).add(var3.invoke(var6));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, M : MutableMap<in K, MutableList<Long>>> LongArray.groupByTo(destination: M, keySelector: (Long) -> K): M {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Long = var0[var3];
         val var9: Any = var2.invoke(var0[var3]);
         val var8: Any = var1.get(var9);
         var var7: Any = var8;
         if (var8 == null) {
            var7 = new ArrayList();
            var1.put(var9, var7);
         }

         (var7 as java.util.List).add(var5);
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, MutableList<V>>> LongArray.groupByTo(destination: M, keySelector: (Long) -> K, valueTransform: (Long) -> V): M {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         val var6: Long = var0[var4];
         val var10: Any = var2.invoke(var0[var4]);
         val var9: Any = var1.get(var10);
         var var8: Any = var9;
         if (var9 == null) {
            var8 = new ArrayList();
            var1.put(var10, var8);
         }

         (var8 as java.util.List).add(var3.invoke(var6));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <T, K, M : MutableMap<in K, MutableList<T>>> Array<out T>.groupByTo(destination: M, keySelector: (T) -> K): M {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var7: Any = var0[var3];
         val var8: Any = var2.invoke(var0[var3]);
         val var6: Any = var1.get(var8);
         var var5: Any = var6;
         if (var6 == null) {
            var5 = new ArrayList();
            var1.put(var8, var5);
         }

         (var5 as java.util.List).add(var7);
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <T, K, V, M : MutableMap<in K, MutableList<V>>> Array<out T>.groupByTo(destination: M, keySelector: (T) -> K, valueTransform: (T) -> V): M {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         val var8: Any = var0[var4];
         val var9: Any = var2.invoke(var0[var4]);
         val var7: Any = var1.get(var9);
         var var6: Any = var7;
         if (var7 == null) {
            var6 = new ArrayList();
            var1.put(var9, var6);
         }

         (var6 as java.util.List).add(var3.invoke(var8));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, M : MutableMap<in K, MutableList<Short>>> ShortArray.groupByTo(destination: M, keySelector: (Short) -> K): M {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         val var3: Short = var0[var4];
         val var8: Any = var2.invoke(var0[var4]);
         val var7: Any = var1.get(var8);
         var var6: Any = var7;
         if (var7 == null) {
            var6 = new ArrayList();
            var1.put(var8, var6);
         }

         (var6 as java.util.List).add(var3);
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, MutableList<V>>> ShortArray.groupByTo(destination: M, keySelector: (Short) -> K, valueTransform: (Short) -> V): M {
      val var6: Int = var0.length;

      for (int var5 = 0; var5 < var6; var5++) {
         val var4: Short = var0[var5];
         val var9: Any = var2.invoke(var0[var5]);
         val var8: Any = var1.get(var9);
         var var7: Any = var8;
         if (var8 == null) {
            var7 = new ArrayList();
            var1.put(var9, var7);
         }

         (var7 as java.util.List).add(var3.invoke(var4));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, M : MutableMap<in K, MutableList<Boolean>>> BooleanArray.groupByTo(destination: M, keySelector: (Boolean) -> K): M {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Boolean = var0[var3];
         val var8: Any = var2.invoke(var0[var3]);
         val var7: Any = var1.get(var8);
         var var6: Any = var7;
         if (var7 == null) {
            var6 = new ArrayList();
            var1.put(var8, var6);
         }

         (var6 as java.util.List).add(var5);
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, MutableList<V>>> BooleanArray.groupByTo(
      destination: M,
      keySelector: (Boolean) -> K,
      valueTransform: (Boolean) -> V
   ): M {
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         val var6: Boolean = var0[var4];
         val var9: Any = var2.invoke(var0[var4]);
         val var8: Any = var1.get(var9);
         var var7: Any = var8;
         if (var8 == null) {
            var7 = new ArrayList();
            var1.put(var9, var7);
         }

         (var7 as java.util.List).add(var3.invoke(var6));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <T, K> Array<out T>.groupingBy(crossinline keySelector: (T) -> K): Grouping<T, K> {
      return new Grouping<T, K>(var0, var1) {
         final Function1<T, K> $keySelector;
         final T[] $this_groupingBy;

         {
            this.$this_groupingBy = (T[])var1;
            this.$keySelector = var2;
         }

         @Override
         public K keyOf(T var1) {
            return (K)this.$keySelector.invoke(var1);
         }

         @Override
         public java.util.Iterator<T> sourceIterator() {
            return ArrayIteratorKt.iterator(this.$this_groupingBy);
         }
      };
   }

   @JvmStatic
   public fun ByteArray.indexOf(element: Byte): Int {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1 == var0[var2]) {
            return var2;
         }
      }

      return -1;
   }

   @JvmStatic
   public fun CharArray.indexOf(element: Char): Int {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1 == var0[var2]) {
            return var2;
         }
      }

      return -1;
   }

   @JvmStatic
   public fun IntArray.indexOf(element: Int): Int {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1 == var0[var2]) {
            return var2;
         }
      }

      return -1;
   }

   @JvmStatic
   public fun LongArray.indexOf(element: Long): Int {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         if (var1 == var0[var3]) {
            return var3;
         }
      }

      return -1;
   }

   @JvmStatic
   public fun <T> Array<out T>.indexOf(element: T): Int {
      var var2: Int = 0;
      if (var1 == null) {
         val var4: Int = var0.length;

         for (int var5 = 0; var5 < var4; var5++) {
            if (var0[var5] == null) {
               return var5;
            }
         }
      } else {
         for (int var6 = var0.length; var2 < var6; var2++) {
            if (var1 == var0[var2]) {
               return var2;
            }
         }
      }

      return -1;
   }

   @JvmStatic
   public fun ShortArray.indexOf(element: Short): Int {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1 == var0[var2]) {
            return var2;
         }
      }

      return -1;
   }

   @JvmStatic
   public fun BooleanArray.indexOf(element: Boolean): Int {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1 == var0[var2]) {
            return var2;
         }
      }

      return -1;
   }

   @JvmStatic
   public inline fun ByteArray.indexOfFirst(predicate: (Byte) -> Boolean): Int {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return var2;
         }
      }

      return -1;
   }

   @JvmStatic
   public inline fun CharArray.indexOfFirst(predicate: (Char) -> Boolean): Int {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return var2;
         }
      }

      return -1;
   }

   @JvmStatic
   public inline fun DoubleArray.indexOfFirst(predicate: (Double) -> Boolean): Int {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return var2;
         }
      }

      return -1;
   }

   @JvmStatic
   public inline fun FloatArray.indexOfFirst(predicate: (Float) -> Boolean): Int {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return var2;
         }
      }

      return -1;
   }

   @JvmStatic
   public inline fun IntArray.indexOfFirst(predicate: (Int) -> Boolean): Int {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return var2;
         }
      }

      return -1;
   }

   @JvmStatic
   public inline fun LongArray.indexOfFirst(predicate: (Long) -> Boolean): Int {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return var2;
         }
      }

      return -1;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.indexOfFirst(predicate: (T) -> Boolean): Int {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return var2;
         }
      }

      return -1;
   }

   @JvmStatic
   public inline fun ShortArray.indexOfFirst(predicate: (Short) -> Boolean): Int {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return var2;
         }
      }

      return -1;
   }

   @JvmStatic
   public inline fun BooleanArray.indexOfFirst(predicate: (Boolean) -> Boolean): Int {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return var2;
         }
      }

      return -1;
   }

   @JvmStatic
   public inline fun ByteArray.indexOfLast(predicate: (Byte) -> Boolean): Int {
      var var2: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            if (var1.invoke(var0[var2]) as java.lang.Boolean) {
               return var2;
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      return -1;
   }

   @JvmStatic
   public inline fun CharArray.indexOfLast(predicate: (Char) -> Boolean): Int {
      var var2: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            if (var1.invoke(var0[var2]) as java.lang.Boolean) {
               return var2;
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      return -1;
   }

   @JvmStatic
   public inline fun DoubleArray.indexOfLast(predicate: (Double) -> Boolean): Int {
      var var2: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            if (var1.invoke(var0[var2]) as java.lang.Boolean) {
               return var2;
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      return -1;
   }

   @JvmStatic
   public inline fun FloatArray.indexOfLast(predicate: (Float) -> Boolean): Int {
      var var2: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            if (var1.invoke(var0[var2]) as java.lang.Boolean) {
               return var2;
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      return -1;
   }

   @JvmStatic
   public inline fun IntArray.indexOfLast(predicate: (Int) -> Boolean): Int {
      var var2: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            if (var1.invoke(var0[var2]) as java.lang.Boolean) {
               return var2;
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      return -1;
   }

   @JvmStatic
   public inline fun LongArray.indexOfLast(predicate: (Long) -> Boolean): Int {
      var var2: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            if (var1.invoke(var0[var2]) as java.lang.Boolean) {
               return var2;
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      return -1;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.indexOfLast(predicate: (T) -> Boolean): Int {
      var var2: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            if (var1.invoke(var0[var2]) as java.lang.Boolean) {
               return var2;
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      return -1;
   }

   @JvmStatic
   public inline fun ShortArray.indexOfLast(predicate: (Short) -> Boolean): Int {
      var var2: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            if (var1.invoke(var0[var2]) as java.lang.Boolean) {
               return var2;
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      return -1;
   }

   @JvmStatic
   public inline fun BooleanArray.indexOfLast(predicate: (Boolean) -> Boolean): Int {
      var var2: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            if (var1.invoke(var0[var2]) as java.lang.Boolean) {
               return var2;
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      return -1;
   }

   @JvmStatic
   public infix fun ByteArray.intersect(other: Iterable<Byte>): Set<Byte> {
      val var2: java.util.Set = ArraysKt.toMutableSet(var0);
      CollectionsKt.retainAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public infix fun CharArray.intersect(other: Iterable<Char>): Set<Char> {
      val var2: java.util.Set = ArraysKt.toMutableSet(var0);
      CollectionsKt.retainAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public infix fun DoubleArray.intersect(other: Iterable<Double>): Set<Double> {
      val var2: java.util.Set = ArraysKt.toMutableSet(var0);
      CollectionsKt.retainAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public infix fun FloatArray.intersect(other: Iterable<Float>): Set<Float> {
      val var2: java.util.Set = ArraysKt.toMutableSet(var0);
      CollectionsKt.retainAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public infix fun IntArray.intersect(other: Iterable<Int>): Set<Int> {
      val var2: java.util.Set = ArraysKt.toMutableSet(var0);
      CollectionsKt.retainAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public infix fun LongArray.intersect(other: Iterable<Long>): Set<Long> {
      val var2: java.util.Set = ArraysKt.toMutableSet(var0);
      CollectionsKt.retainAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public infix fun <T> Array<out T>.intersect(other: Iterable<T>): Set<T> {
      val var2: java.util.Set = ArraysKt.toMutableSet(var0);
      CollectionsKt.retainAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public infix fun ShortArray.intersect(other: Iterable<Short>): Set<Short> {
      val var2: java.util.Set = ArraysKt.toMutableSet(var0);
      CollectionsKt.retainAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public infix fun BooleanArray.intersect(other: Iterable<Boolean>): Set<Boolean> {
      val var2: java.util.Set = ArraysKt.toMutableSet(var0);
      CollectionsKt.retainAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public inline fun ByteArray.isEmpty(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   public inline fun CharArray.isEmpty(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   public inline fun DoubleArray.isEmpty(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   public inline fun FloatArray.isEmpty(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   public inline fun IntArray.isEmpty(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   public inline fun LongArray.isEmpty(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.isEmpty(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   public inline fun ShortArray.isEmpty(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   public inline fun BooleanArray.isEmpty(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   public inline fun ByteArray.isNotEmpty(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1 xor true;
   }

   @JvmStatic
   public inline fun CharArray.isNotEmpty(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1 xor true;
   }

   @JvmStatic
   public inline fun DoubleArray.isNotEmpty(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1 xor true;
   }

   @JvmStatic
   public inline fun FloatArray.isNotEmpty(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1 xor true;
   }

   @JvmStatic
   public inline fun IntArray.isNotEmpty(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1 xor true;
   }

   @JvmStatic
   public inline fun LongArray.isNotEmpty(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1 xor true;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.isNotEmpty(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1 xor true;
   }

   @JvmStatic
   public inline fun ShortArray.isNotEmpty(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1 xor true;
   }

   @JvmStatic
   public inline fun BooleanArray.isNotEmpty(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1 xor true;
   }

   @JvmStatic
   public fun <A : Appendable> ByteArray.joinTo(
      buffer: A,
      separator: CharSequence = ...,
      prefix: CharSequence = ...,
      postfix: CharSequence = ...,
      limit: Int = ...,
      truncated: CharSequence = ...,
      transform: ((Byte) -> CharSequence)? = ...
   ): A {
      var1.append(var3);
      val var12: Int = var0.length;
      var var11: Int = 0;
      val var9: Int = 0;

      var var10: Int;
      while (true) {
         var10 = var9;
         if (var11 >= var12) {
            break;
         }

         val var8: Byte = var0[var11];
         if (++var9 > 1) {
            var1.append(var2);
         }

         if (var5 >= 0) {
            var10 = var9;
            if (var9 > var5) {
               break;
            }
         }

         if (var7 != null) {
            var1.append(var7.invoke(var8) as java.lang.CharSequence);
         } else {
            var1.append(java.lang.String.valueOf((int)var8));
         }

         var11++;
      }

      if (var5 >= 0 && var10 > var5) {
         var1.append(var6);
      }

      var1.append(var4);
      return (A)var1;
   }

   @JvmStatic
   public fun <A : Appendable> CharArray.joinTo(
      buffer: A,
      separator: CharSequence = ...,
      prefix: CharSequence = ...,
      postfix: CharSequence = ...,
      limit: Int = ...,
      truncated: CharSequence = ...,
      transform: ((Char) -> CharSequence)? = ...
   ): A {
      var1.append(var3);
      val var12: Int = var0.length;
      var var11: Int = 0;
      val var9: Int = 0;

      var var10: Int;
      while (true) {
         var10 = var9;
         if (var11 >= var12) {
            break;
         }

         val var8: Char = var0[var11];
         if (++var9 > 1) {
            var1.append(var2);
         }

         if (var5 >= 0) {
            var10 = var9;
            if (var9 > var5) {
               break;
            }
         }

         if (var7 != null) {
            var1.append(var7.invoke(var8) as java.lang.CharSequence);
         } else {
            var1.append(var8);
         }

         var11++;
      }

      if (var5 >= 0 && var10 > var5) {
         var1.append(var6);
      }

      var1.append(var4);
      return (A)var1;
   }

   @JvmStatic
   public fun <A : Appendable> DoubleArray.joinTo(
      buffer: A,
      separator: CharSequence = ...,
      prefix: CharSequence = ...,
      postfix: CharSequence = ...,
      limit: Int = ...,
      truncated: CharSequence = ...,
      transform: ((Double) -> CharSequence)? = ...
   ): A {
      var1.append(var3);
      val var13: Int = var0.length;
      var var11: Int = 0;
      val var10: Int = 0;

      var var12: Int;
      while (true) {
         var12 = var10;
         if (var11 >= var13) {
            break;
         }

         val var8: Double = var0[var11];
         if (++var10 > 1) {
            var1.append(var2);
         }

         if (var5 >= 0) {
            var12 = var10;
            if (var10 > var5) {
               break;
            }
         }

         if (var7 != null) {
            var1.append(var7.invoke(var8) as java.lang.CharSequence);
         } else {
            var1.append(java.lang.String.valueOf(var8));
         }

         var11++;
      }

      if (var5 >= 0 && var12 > var5) {
         var1.append(var6);
      }

      var1.append(var4);
      return (A)var1;
   }

   @JvmStatic
   public fun <A : Appendable> FloatArray.joinTo(
      buffer: A,
      separator: CharSequence = ...,
      prefix: CharSequence = ...,
      postfix: CharSequence = ...,
      limit: Int = ...,
      truncated: CharSequence = ...,
      transform: ((Float) -> CharSequence)? = ...
   ): A {
      var1.append(var3);
      val var12: Int = var0.length;
      var var11: Int = 0;
      val var9: Int = 0;

      var var10: Int;
      while (true) {
         var10 = var9;
         if (var11 >= var12) {
            break;
         }

         val var8: Float = var0[var11];
         if (++var9 > 1) {
            var1.append(var2);
         }

         if (var5 >= 0) {
            var10 = var9;
            if (var9 > var5) {
               break;
            }
         }

         if (var7 != null) {
            var1.append(var7.invoke(var8) as java.lang.CharSequence);
         } else {
            var1.append(java.lang.String.valueOf(var8));
         }

         var11++;
      }

      if (var5 >= 0 && var10 > var5) {
         var1.append(var6);
      }

      var1.append(var4);
      return (A)var1;
   }

   @JvmStatic
   public fun <A : Appendable> IntArray.joinTo(
      buffer: A,
      separator: CharSequence = ...,
      prefix: CharSequence = ...,
      postfix: CharSequence = ...,
      limit: Int = ...,
      truncated: CharSequence = ...,
      transform: ((Int) -> CharSequence)? = ...
   ): A {
      var1.append(var3);
      val var11: Int = var0.length;
      var var9: Int = 0;
      val var8: Int = 0;

      var var10: Int;
      while (true) {
         var10 = var8;
         if (var9 >= var11) {
            break;
         }

         val var12: Int = var0[var9];
         if (++var8 > 1) {
            var1.append(var2);
         }

         if (var5 >= 0) {
            var10 = var8;
            if (var8 > var5) {
               break;
            }
         }

         if (var7 != null) {
            var1.append(var7.invoke(var12) as java.lang.CharSequence);
         } else {
            var1.append(java.lang.String.valueOf(var12));
         }

         var9++;
      }

      if (var5 >= 0 && var10 > var5) {
         var1.append(var6);
      }

      var1.append(var4);
      return (A)var1;
   }

   @JvmStatic
   public fun <A : Appendable> LongArray.joinTo(
      buffer: A,
      separator: CharSequence = ...,
      prefix: CharSequence = ...,
      postfix: CharSequence = ...,
      limit: Int = ...,
      truncated: CharSequence = ...,
      transform: ((Long) -> CharSequence)? = ...
   ): A {
      var1.append(var3);
      val var11: Int = var0.length;
      var var10: Int = 0;
      val var8: Int = 0;

      var var9: Int;
      while (true) {
         var9 = var8;
         if (var10 >= var11) {
            break;
         }

         val var12: Long = var0[var10];
         if (++var8 > 1) {
            var1.append(var2);
         }

         if (var5 >= 0) {
            var9 = var8;
            if (var8 > var5) {
               break;
            }
         }

         if (var7 != null) {
            var1.append(var7.invoke(var12) as java.lang.CharSequence);
         } else {
            var1.append(java.lang.String.valueOf(var12));
         }

         var10++;
      }

      if (var5 >= 0 && var9 > var5) {
         var1.append(var6);
      }

      var1.append(var4);
      return (A)var1;
   }

   @JvmStatic
   public fun <T, A : Appendable> Array<out T>.joinTo(
      buffer: A,
      separator: CharSequence = ...,
      prefix: CharSequence = ...,
      postfix: CharSequence = ...,
      limit: Int = ...,
      truncated: CharSequence = ...,
      transform: ((T) -> CharSequence)? = ...
   ): A {
      var1.append(var3);
      val var11: Int = var0.length;
      var var9: Int = 0;
      val var8: Int = 0;

      var var10: Int;
      while (true) {
         var10 = var8;
         if (var9 >= var11) {
            break;
         }

         val var12: Any = var0[var9];
         if (++var8 > 1) {
            var1.append(var2);
         }

         if (var5 >= 0) {
            var10 = var8;
            if (var8 > var5) {
               break;
            }
         }

         StringsKt.appendElement(var1, var12, var7);
         var9++;
      }

      if (var5 >= 0 && var10 > var5) {
         var1.append(var6);
      }

      var1.append(var4);
      return (A)var1;
   }

   @JvmStatic
   public fun <A : Appendable> ShortArray.joinTo(
      buffer: A,
      separator: CharSequence = ...,
      prefix: CharSequence = ...,
      postfix: CharSequence = ...,
      limit: Int = ...,
      truncated: CharSequence = ...,
      transform: ((Short) -> CharSequence)? = ...
   ): A {
      var1.append(var3);
      val var12: Int = var0.length;
      var var10: Int = 0;
      val var9: Int = 0;

      var var11: Int;
      while (true) {
         var11 = var9;
         if (var10 >= var12) {
            break;
         }

         val var8: Short = var0[var10];
         if (++var9 > 1) {
            var1.append(var2);
         }

         if (var5 >= 0) {
            var11 = var9;
            if (var9 > var5) {
               break;
            }
         }

         if (var7 != null) {
            var1.append(var7.invoke(var8) as java.lang.CharSequence);
         } else {
            var1.append(java.lang.String.valueOf((int)var8));
         }

         var10++;
      }

      if (var5 >= 0 && var11 > var5) {
         var1.append(var6);
      }

      var1.append(var4);
      return (A)var1;
   }

   @JvmStatic
   public fun <A : Appendable> BooleanArray.joinTo(
      buffer: A,
      separator: CharSequence = ...,
      prefix: CharSequence = ...,
      postfix: CharSequence = ...,
      limit: Int = ...,
      truncated: CharSequence = ...,
      transform: ((Boolean) -> CharSequence)? = ...
   ): A {
      var1.append(var3);
      val var11: Int = var0.length;
      var var10: Int = 0;
      val var8: Int = 0;

      var var9: Int;
      while (true) {
         var9 = var8;
         if (var10 >= var11) {
            break;
         }

         val var12: Boolean = var0[var10];
         if (++var8 > 1) {
            var1.append(var2);
         }

         if (var5 >= 0) {
            var9 = var8;
            if (var8 > var5) {
               break;
            }
         }

         if (var7 != null) {
            var1.append(var7.invoke(var12) as java.lang.CharSequence);
         } else {
            var1.append(java.lang.String.valueOf(var12));
         }

         var10++;
      }

      if (var5 >= 0 && var9 > var5) {
         var1.append(var6);
      }

      var1.append(var4);
      return (A)var1;
   }

   @JvmStatic
   public fun ByteArray.joinToString(
      separator: CharSequence = ", " as java.lang.CharSequence,
      prefix: CharSequence = "" as java.lang.CharSequence,
      postfix: CharSequence = "" as java.lang.CharSequence,
      limit: Int = -1,
      truncated: CharSequence = "..." as java.lang.CharSequence,
      transform: ((Byte) -> CharSequence)? = null
   ): String {
      return ArraysKt.joinTo(var0, new StringBuilder(), var1, var2, var3, var4, var5, var6).toString();
   }

   @JvmStatic
   public fun CharArray.joinToString(
      separator: CharSequence = ", " as java.lang.CharSequence,
      prefix: CharSequence = "" as java.lang.CharSequence,
      postfix: CharSequence = "" as java.lang.CharSequence,
      limit: Int = -1,
      truncated: CharSequence = "..." as java.lang.CharSequence,
      transform: ((Char) -> CharSequence)? = null
   ): String {
      return ArraysKt.joinTo(var0, new StringBuilder(), var1, var2, var3, var4, var5, var6).toString();
   }

   @JvmStatic
   public fun DoubleArray.joinToString(
      separator: CharSequence = ", " as java.lang.CharSequence,
      prefix: CharSequence = "" as java.lang.CharSequence,
      postfix: CharSequence = "" as java.lang.CharSequence,
      limit: Int = -1,
      truncated: CharSequence = "..." as java.lang.CharSequence,
      transform: ((Double) -> CharSequence)? = null
   ): String {
      return ArraysKt.joinTo(var0, new StringBuilder(), var1, var2, var3, var4, var5, var6).toString();
   }

   @JvmStatic
   public fun FloatArray.joinToString(
      separator: CharSequence = ", " as java.lang.CharSequence,
      prefix: CharSequence = "" as java.lang.CharSequence,
      postfix: CharSequence = "" as java.lang.CharSequence,
      limit: Int = -1,
      truncated: CharSequence = "..." as java.lang.CharSequence,
      transform: ((Float) -> CharSequence)? = null
   ): String {
      return ArraysKt.joinTo(var0, new StringBuilder(), var1, var2, var3, var4, var5, var6).toString();
   }

   @JvmStatic
   public fun IntArray.joinToString(
      separator: CharSequence = ", " as java.lang.CharSequence,
      prefix: CharSequence = "" as java.lang.CharSequence,
      postfix: CharSequence = "" as java.lang.CharSequence,
      limit: Int = -1,
      truncated: CharSequence = "..." as java.lang.CharSequence,
      transform: ((Int) -> CharSequence)? = null
   ): String {
      return ArraysKt.joinTo(var0, new StringBuilder(), var1, var2, var3, var4, var5, var6).toString();
   }

   @JvmStatic
   public fun LongArray.joinToString(
      separator: CharSequence = ", " as java.lang.CharSequence,
      prefix: CharSequence = "" as java.lang.CharSequence,
      postfix: CharSequence = "" as java.lang.CharSequence,
      limit: Int = -1,
      truncated: CharSequence = "..." as java.lang.CharSequence,
      transform: ((Long) -> CharSequence)? = null
   ): String {
      return ArraysKt.joinTo(var0, new StringBuilder(), var1, var2, var3, var4, var5, var6).toString();
   }

   @JvmStatic
   public fun <T> Array<out T>.joinToString(
      separator: CharSequence = ", " as java.lang.CharSequence,
      prefix: CharSequence = "" as java.lang.CharSequence,
      postfix: CharSequence = "" as java.lang.CharSequence,
      limit: Int = -1,
      truncated: CharSequence = "..." as java.lang.CharSequence,
      transform: ((T) -> CharSequence)? = null
   ): String {
      return ArraysKt.joinTo(var0, new StringBuilder(), var1, var2, var3, var4, var5, var6).toString();
   }

   @JvmStatic
   public fun ShortArray.joinToString(
      separator: CharSequence = ", " as java.lang.CharSequence,
      prefix: CharSequence = "" as java.lang.CharSequence,
      postfix: CharSequence = "" as java.lang.CharSequence,
      limit: Int = -1,
      truncated: CharSequence = "..." as java.lang.CharSequence,
      transform: ((Short) -> CharSequence)? = null
   ): String {
      return ArraysKt.joinTo(var0, new StringBuilder(), var1, var2, var3, var4, var5, var6).toString();
   }

   @JvmStatic
   public fun BooleanArray.joinToString(
      separator: CharSequence = ", " as java.lang.CharSequence,
      prefix: CharSequence = "" as java.lang.CharSequence,
      postfix: CharSequence = "" as java.lang.CharSequence,
      limit: Int = -1,
      truncated: CharSequence = "..." as java.lang.CharSequence,
      transform: ((Boolean) -> CharSequence)? = null
   ): String {
      return ArraysKt.joinTo(var0, new StringBuilder(), var1, var2, var3, var4, var5, var6).toString();
   }

   @JvmStatic
   public fun ByteArray.last(): Byte {
      if (var0.length != 0) {
         return var0[ArraysKt.getLastIndex(var0)];
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun ByteArray.last(predicate: (Byte) -> Boolean): Byte {
      var var3: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var4: Int = var3 - 1;
            val var2: Byte = var0[var3];
            if (var1.invoke(var0[var3]) as java.lang.Boolean) {
               return var2;
            }

            if (var4 < 0) {
               break;
            }

            var3 = var4;
         }
      }

      throw new NoSuchElementException("Array contains no element matching the predicate.");
   }

   @JvmStatic
   public fun CharArray.last(): Char {
      if (var0.length != 0) {
         return var0[ArraysKt.getLastIndex(var0)];
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun CharArray.last(predicate: (Char) -> Boolean): Char {
      var var3: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var4: Int = var3 - 1;
            val var2: Char = var0[var3];
            if (var1.invoke(var0[var3]) as java.lang.Boolean) {
               return var2;
            }

            if (var4 < 0) {
               break;
            }

            var3 = var4;
         }
      }

      throw new NoSuchElementException("Array contains no element matching the predicate.");
   }

   @JvmStatic
   public fun DoubleArray.last(): Double {
      if (var0.length != 0) {
         return var0[ArraysKt.getLastIndex(var0)];
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun DoubleArray.last(predicate: (Double) -> Boolean): Double {
      var var4: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var5: Int = var4 - 1;
            val var2: Double = var0[var4];
            if (var1.invoke(var0[var4]) as java.lang.Boolean) {
               return var2;
            }

            if (var5 < 0) {
               break;
            }

            var4 = var5;
         }
      }

      throw new NoSuchElementException("Array contains no element matching the predicate.");
   }

   @JvmStatic
   public fun FloatArray.last(): Float {
      if (var0.length != 0) {
         return var0[ArraysKt.getLastIndex(var0)];
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun FloatArray.last(predicate: (Float) -> Boolean): Float {
      var var3: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var4: Int = var3 - 1;
            val var2: Float = var0[var3];
            if (var1.invoke(var0[var3]) as java.lang.Boolean) {
               return var2;
            }

            if (var4 < 0) {
               break;
            }

            var3 = var4;
         }
      }

      throw new NoSuchElementException("Array contains no element matching the predicate.");
   }

   @JvmStatic
   public fun IntArray.last(): Int {
      if (var0.length != 0) {
         return var0[ArraysKt.getLastIndex(var0)];
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun IntArray.last(predicate: (Int) -> Boolean): Int {
      var var2: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            var2 = var0[var2];
            if (var1.invoke(var0[var2]) as java.lang.Boolean) {
               return var2;
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      throw new NoSuchElementException("Array contains no element matching the predicate.");
   }

   @JvmStatic
   public fun LongArray.last(): Long {
      if (var0.length != 0) {
         return var0[ArraysKt.getLastIndex(var0)];
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun LongArray.last(predicate: (Long) -> Boolean): Long {
      var var2: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            val var4: Long = var0[var2];
            if (var1.invoke(var0[var2]) as java.lang.Boolean) {
               return var4;
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      throw new NoSuchElementException("Array contains no element matching the predicate.");
   }

   @JvmStatic
   public fun <T> Array<out T>.last(): T {
      if (var0.length != 0) {
         return (T)var0[ArraysKt.getLastIndex(var0)];
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun <T> Array<out T>.last(predicate: (T) -> Boolean): T {
      var var2: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            val var4: Any = var0[var2];
            if (var1.invoke(var0[var2]) as java.lang.Boolean) {
               return (T)var4;
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      throw new NoSuchElementException("Array contains no element matching the predicate.");
   }

   @JvmStatic
   public fun ShortArray.last(): Short {
      if (var0.length != 0) {
         return var0[ArraysKt.getLastIndex(var0)];
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun ShortArray.last(predicate: (Short) -> Boolean): Short {
      var var3: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var4: Int = var3 - 1;
            val var2: Short = var0[var3];
            if (var1.invoke(var0[var3]) as java.lang.Boolean) {
               return var2;
            }

            if (var4 < 0) {
               break;
            }

            var3 = var4;
         }
      }

      throw new NoSuchElementException("Array contains no element matching the predicate.");
   }

   @JvmStatic
   public fun BooleanArray.last(): Boolean {
      if (var0.length != 0) {
         return var0[ArraysKt.getLastIndex(var0)];
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun BooleanArray.last(predicate: (Boolean) -> Boolean): Boolean {
      var var2: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            val var4: Boolean = var0[var2];
            if (var1.invoke(var0[var2]) as java.lang.Boolean) {
               return var4;
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      throw new NoSuchElementException("Array contains no element matching the predicate.");
   }

   @JvmStatic
   public fun ByteArray.lastIndexOf(element: Byte): Int {
      var var2: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            if (var1 == var0[var2]) {
               return var2;
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      return -1;
   }

   @JvmStatic
   public fun CharArray.lastIndexOf(element: Char): Int {
      var var2: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            if (var1 == var0[var2]) {
               return var2;
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      return -1;
   }

   @JvmStatic
   public fun IntArray.lastIndexOf(element: Int): Int {
      var var2: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            if (var1 == var0[var2]) {
               return var2;
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      return -1;
   }

   @JvmStatic
   public fun LongArray.lastIndexOf(element: Long): Int {
      var var3: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var4: Int = var3 - 1;
            if (var1 == var0[var3]) {
               return var3;
            }

            if (var4 < 0) {
               break;
            }

            var3 = var4;
         }
      }

      return -1;
   }

   @JvmStatic
   public fun <T> Array<out T>.lastIndexOf(element: T): Int {
      if (var1 == null) {
         var var2: Int = var0.length - 1;
         if (var0.length - 1 >= 0) {
            while (true) {
               val var3: Int = var2 - 1;
               if (var0[var2] == null) {
                  return var2;
               }

               if (var3 < 0) {
                  break;
               }

               var2 = var3;
            }
         }
      } else {
         var var4: Int = var0.length - 1;
         if (var0.length - 1 >= 0) {
            while (true) {
               val var5: Int = var4 - 1;
               if (var1 == var0[var4]) {
                  return var4;
               }

               if (var5 < 0) {
                  break;
               }

               var4 = var5;
            }
         }
      }

      return -1;
   }

   @JvmStatic
   public fun ShortArray.lastIndexOf(element: Short): Int {
      var var2: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            if (var1 == var0[var2]) {
               return var2;
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      return -1;
   }

   @JvmStatic
   public fun BooleanArray.lastIndexOf(element: Boolean): Int {
      var var2: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            if (var1 == var0[var2]) {
               return var2;
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      return -1;
   }

   @JvmStatic
   public fun BooleanArray.lastOrNull(): Boolean? {
      val var1: java.lang.Boolean;
      if (var0.length == 0) {
         var1 = null;
      } else {
         var1 = var0[var0.length - 1];
      }

      return var1;
   }

   @JvmStatic
   public inline fun BooleanArray.lastOrNull(predicate: (Boolean) -> Boolean): Boolean? {
      var var2: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            val var4: Boolean = var0[var2];
            if (var1.invoke(var0[var2]) as java.lang.Boolean) {
               return var4;
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      return null;
   }

   @JvmStatic
   public fun ByteArray.lastOrNull(): Byte? {
      val var1: java.lang.Byte;
      if (var0.length == 0) {
         var1 = null;
      } else {
         var1 = var0[var0.length - 1];
      }

      return var1;
   }

   @JvmStatic
   public inline fun ByteArray.lastOrNull(predicate: (Byte) -> Boolean): Byte? {
      var var3: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var4: Int = var3 - 1;
            val var2: Byte = var0[var3];
            if (var1.invoke(var0[var3]) as java.lang.Boolean) {
               return var2;
            }

            if (var4 < 0) {
               break;
            }

            var3 = var4;
         }
      }

      return null;
   }

   @JvmStatic
   public fun CharArray.lastOrNull(): Char? {
      val var1: Character;
      if (var0.length == 0) {
         var1 = null;
      } else {
         var1 = var0[var0.length - 1];
      }

      return var1;
   }

   @JvmStatic
   public inline fun CharArray.lastOrNull(predicate: (Char) -> Boolean): Char? {
      var var3: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var4: Int = var3 - 1;
            val var2: Char = var0[var3];
            if (var1.invoke(var0[var3]) as java.lang.Boolean) {
               return var2;
            }

            if (var4 < 0) {
               break;
            }

            var3 = var4;
         }
      }

      return null;
   }

   @JvmStatic
   public fun DoubleArray.lastOrNull(): Double? {
      val var1: java.lang.Double;
      if (var0.length == 0) {
         var1 = null;
      } else {
         var1 = var0[var0.length - 1];
      }

      return var1;
   }

   @JvmStatic
   public inline fun DoubleArray.lastOrNull(predicate: (Double) -> Boolean): Double? {
      var var4: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var5: Int = var4 - 1;
            val var2: Double = var0[var4];
            if (var1.invoke(var0[var4]) as java.lang.Boolean) {
               return var2;
            }

            if (var5 < 0) {
               break;
            }

            var4 = var5;
         }
      }

      return null;
   }

   @JvmStatic
   public fun FloatArray.lastOrNull(): Float? {
      val var1: java.lang.Float;
      if (var0.length == 0) {
         var1 = null;
      } else {
         var1 = var0[var0.length - 1];
      }

      return var1;
   }

   @JvmStatic
   public inline fun FloatArray.lastOrNull(predicate: (Float) -> Boolean): Float? {
      var var3: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var4: Int = var3 - 1;
            val var2: Float = var0[var3];
            if (var1.invoke(var0[var3]) as java.lang.Boolean) {
               return var2;
            }

            if (var4 < 0) {
               break;
            }

            var3 = var4;
         }
      }

      return null;
   }

   @JvmStatic
   public fun IntArray.lastOrNull(): Int? {
      val var1: Int;
      if (var0.length == 0) {
         var1 = null;
      } else {
         var1 = var0[var0.length - 1];
      }

      return var1;
   }

   @JvmStatic
   public inline fun IntArray.lastOrNull(predicate: (Int) -> Boolean): Int? {
      var var2: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            var2 = var0[var2];
            if (var1.invoke(var0[var2]) as java.lang.Boolean) {
               return var2;
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      return null;
   }

   @JvmStatic
   public fun LongArray.lastOrNull(): Long? {
      val var1: java.lang.Long;
      if (var0.length == 0) {
         var1 = null;
      } else {
         var1 = var0[var0.length - 1];
      }

      return var1;
   }

   @JvmStatic
   public inline fun LongArray.lastOrNull(predicate: (Long) -> Boolean): Long? {
      var var2: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            val var4: Long = var0[var2];
            if (var1.invoke(var0[var2]) as java.lang.Boolean) {
               return var4;
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      return null;
   }

   @JvmStatic
   public fun <T> Array<out T>.lastOrNull(): T? {
      if (((Object[])var0).length == 0) {
         var0 = null;
      } else {
         var0 = ((Object[])var0)[((Object[])var0).length - 1];
      }

      return (T)var0;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.lastOrNull(predicate: (T) -> Boolean): T? {
      var var2: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            val var4: Any = var0[var2];
            if (var1.invoke(var0[var2]) as java.lang.Boolean) {
               return (T)var4;
            }

            if (var3 < 0) {
               break;
            }

            var2 = var3;
         }
      }

      return null;
   }

   @JvmStatic
   public fun ShortArray.lastOrNull(): Short? {
      val var1: java.lang.Short;
      if (var0.length == 0) {
         var1 = null;
      } else {
         var1 = var0[var0.length - 1];
      }

      return var1;
   }

   @JvmStatic
   public inline fun ShortArray.lastOrNull(predicate: (Short) -> Boolean): Short? {
      var var3: Int = var0.length - 1;
      if (var0.length - 1 >= 0) {
         while (true) {
            val var4: Int = var3 - 1;
            val var2: Short = var0[var3];
            if (var1.invoke(var0[var3]) as java.lang.Boolean) {
               return var2;
            }

            if (var4 < 0) {
               break;
            }

            var3 = var4;
         }
      }

      return null;
   }

   @JvmStatic
   public inline fun <R> ByteArray.map(transform: (Byte) -> R): List<R> {
      val var4: java.util.Collection = new ArrayList(var0.length);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(var1.invoke(var0[var2]));
      }

      return var4 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> CharArray.map(transform: (Char) -> R): List<R> {
      val var4: java.util.Collection = new ArrayList(var0.length);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(var1.invoke(var0[var2]));
      }

      return var4 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> DoubleArray.map(transform: (Double) -> R): List<R> {
      val var4: java.util.Collection = new ArrayList(var0.length);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(var1.invoke(var0[var2]));
      }

      return var4 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> FloatArray.map(transform: (Float) -> R): List<R> {
      val var4: java.util.Collection = new ArrayList(var0.length);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(var1.invoke(var0[var2]));
      }

      return var4 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> IntArray.map(transform: (Int) -> R): List<R> {
      val var4: java.util.Collection = new ArrayList(var0.length);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(var1.invoke(var0[var2]));
      }

      return var4 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> LongArray.map(transform: (Long) -> R): List<R> {
      val var4: java.util.Collection = new ArrayList(var0.length);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(var1.invoke(var0[var2]));
      }

      return var4 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <T, R> Array<out T>.map(transform: (T) -> R): List<R> {
      val var4: java.util.Collection = new ArrayList(var0.length);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(var1.invoke(var0[var2]));
      }

      return var4 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> ShortArray.map(transform: (Short) -> R): List<R> {
      val var4: java.util.Collection = new ArrayList(var0.length);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(var1.invoke(var0[var2]));
      }

      return var4 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> BooleanArray.map(transform: (Boolean) -> R): List<R> {
      val var4: java.util.Collection = new ArrayList(var0.length);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(var1.invoke(var0[var2]));
      }

      return var4 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> ByteArray.mapIndexed(transform: (Int, Byte) -> R): List<R> {
      val var5: java.util.Collection = new ArrayList(var0.length);
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var5.add(var1.invoke(var2, var0[var3]));
         var3++;
      }

      return var5 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> CharArray.mapIndexed(transform: (Int, Char) -> R): List<R> {
      val var5: java.util.Collection = new ArrayList(var0.length);
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var5.add(var1.invoke(var2, var0[var3]));
         var3++;
      }

      return var5 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> DoubleArray.mapIndexed(transform: (Int, Double) -> R): List<R> {
      val var5: java.util.Collection = new ArrayList(var0.length);
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var5.add(var1.invoke(var2, var0[var3]));
         var3++;
      }

      return var5 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> FloatArray.mapIndexed(transform: (Int, Float) -> R): List<R> {
      val var5: java.util.Collection = new ArrayList(var0.length);
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var5.add(var1.invoke(var2, var0[var3]));
         var3++;
      }

      return var5 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> IntArray.mapIndexed(transform: (Int, Int) -> R): List<R> {
      val var5: java.util.Collection = new ArrayList(var0.length);
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var5.add(var1.invoke(var2, var0[var3]));
         var3++;
      }

      return var5 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> LongArray.mapIndexed(transform: (Int, Long) -> R): List<R> {
      val var5: java.util.Collection = new ArrayList(var0.length);
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var5.add(var1.invoke(var2, var0[var3]));
         var3++;
      }

      return var5 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <T, R> Array<out T>.mapIndexed(transform: (Int, T) -> R): List<R> {
      val var5: java.util.Collection = new ArrayList(var0.length);
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var5.add(var1.invoke(var2, var0[var3]));
         var3++;
      }

      return var5 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> ShortArray.mapIndexed(transform: (Int, Short) -> R): List<R> {
      val var5: java.util.Collection = new ArrayList(var0.length);
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var5.add(var1.invoke(var2, var0[var3]));
         var3++;
      }

      return var5 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> BooleanArray.mapIndexed(transform: (Int, Boolean) -> R): List<R> {
      val var5: java.util.Collection = new ArrayList(var0.length);
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var5.add(var1.invoke(var2, var0[var3]));
         var3++;
      }

      return var5 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <T, R : Any> Array<out T>.mapIndexedNotNull(transform: (Int, T) -> R?): List<R> {
      val var6: java.util.Collection = new ArrayList();
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         val var5: Any = var1.invoke(var2, var0[var3]);
         if (var5 != null) {
            var6.add(var5);
         }

         var3++;
      }

      return var6 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <T, R : Any, C : MutableCollection<in R>> Array<out T>.mapIndexedNotNullTo(destination: C, transform: (Int, T) -> R?): C {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         val var6: Any = var2.invoke(var3, var0[var4]);
         if (var6 != null) {
            var1.add(var6);
         }

         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> ByteArray.mapIndexedTo(destination: C, transform: (Int, Byte) -> R): C {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         var1.add(var2.invoke(var3, var0[var4]));
         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> CharArray.mapIndexedTo(destination: C, transform: (Int, Char) -> R): C {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         var1.add(var2.invoke(var3, var0[var4]));
         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> DoubleArray.mapIndexedTo(destination: C, transform: (Int, Double) -> R): C {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         var1.add(var2.invoke(var3, var0[var4]));
         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> FloatArray.mapIndexedTo(destination: C, transform: (Int, Float) -> R): C {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         var1.add(var2.invoke(var3, var0[var4]));
         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> IntArray.mapIndexedTo(destination: C, transform: (Int, Int) -> R): C {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         var1.add(var2.invoke(var3, var0[var4]));
         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> LongArray.mapIndexedTo(destination: C, transform: (Int, Long) -> R): C {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         var1.add(var2.invoke(var3, var0[var4]));
         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T, R, C : MutableCollection<in R>> Array<out T>.mapIndexedTo(destination: C, transform: (Int, T) -> R): C {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         var1.add(var2.invoke(var3, var0[var4]));
         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> ShortArray.mapIndexedTo(destination: C, transform: (Int, Short) -> R): C {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         var1.add(var2.invoke(var3, var0[var4]));
         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> BooleanArray.mapIndexedTo(destination: C, transform: (Int, Boolean) -> R): C {
      val var5: Int = var0.length;
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         var1.add(var2.invoke(var3, var0[var4]));
         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T, R : Any> Array<out T>.mapNotNull(transform: (T) -> R?): List<R> {
      val var5: java.util.Collection = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Any = var1.invoke(var0[var2]);
         if (var4 != null) {
            var5.add(var4);
         }
      }

      return var5 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <T, R : Any, C : MutableCollection<in R>> Array<out T>.mapNotNullTo(destination: C, transform: (T) -> R?): C {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Any = var2.invoke(var0[var3]);
         if (var5 != null) {
            var1.add(var5);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> ByteArray.mapTo(destination: C, transform: (Byte) -> R): C {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var1.add(var2.invoke(var0[var3]));
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> CharArray.mapTo(destination: C, transform: (Char) -> R): C {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var1.add(var2.invoke(var0[var3]));
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> DoubleArray.mapTo(destination: C, transform: (Double) -> R): C {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var1.add(var2.invoke(var0[var3]));
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> FloatArray.mapTo(destination: C, transform: (Float) -> R): C {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var1.add(var2.invoke(var0[var3]));
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> IntArray.mapTo(destination: C, transform: (Int) -> R): C {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var1.add(var2.invoke(var0[var3]));
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> LongArray.mapTo(destination: C, transform: (Long) -> R): C {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var1.add(var2.invoke(var0[var3]));
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <T, R, C : MutableCollection<in R>> Array<out T>.mapTo(destination: C, transform: (T) -> R): C {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var1.add(var2.invoke(var0[var3]));
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> ShortArray.mapTo(destination: C, transform: (Short) -> R): C {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var1.add(var2.invoke(var0[var3]));
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> BooleanArray.mapTo(destination: C, transform: (Boolean) -> R): C {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var1.add(var2.invoke(var0[var3]));
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> BooleanArray.maxByOrNull(selector: (Boolean) -> R): Boolean? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Boolean = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         if (var3 == 0) {
            return var4;
         } else {
            var var7: java.lang.Comparable = var1.invoke(var4) as java.lang.Comparable;
            var var2: Int = 1;
            var var5: Boolean = var4;
            if (1 <= var3) {
               while (true) {
                  var5 = var0[var2];
                  val var8: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
                  var var6: java.lang.Comparable = var7;
                  if (var7.compareTo(var8) < 0) {
                     var4 = var5;
                     var6 = var8;
                  }

                  var5 = var4;
                  if (var2 == var3) {
                     break;
                  }

                  var2++;
                  var7 = var6;
               }
            }

            return var5;
         }
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> ByteArray.maxByOrNull(selector: (Byte) -> R): Byte? {
      if (var0.length == 0) {
         return null;
      } else {
         var var2: Byte = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var7: java.lang.Comparable = var1.invoke(var2) as java.lang.Comparable;
            var var4: Int = 1;
            var var3: Byte = var2;
            if (1 <= var5) {
               while (true) {
                  var3 = var0[var4];
                  val var8: java.lang.Comparable = var1.invoke(var0[var4]) as java.lang.Comparable;
                  var var6: java.lang.Comparable = var7;
                  if (var7.compareTo(var8) < 0) {
                     var2 = var3;
                     var6 = var8;
                  }

                  var3 = var2;
                  if (var4 == var5) {
                     break;
                  }

                  var4++;
                  var7 = var6;
               }
            }

            return var3;
         }
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> CharArray.maxByOrNull(selector: (Char) -> R): Char? {
      if (var0.length == 0) {
         return null;
      } else {
         var var2: Char = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var7: java.lang.Comparable = var1.invoke(var2) as java.lang.Comparable;
            var var4: Int = 1;
            var var3: Char = var2;
            if (1 <= var5) {
               while (true) {
                  var3 = var0[var4];
                  val var8: java.lang.Comparable = var1.invoke(var0[var4]) as java.lang.Comparable;
                  var var6: java.lang.Comparable = var7;
                  if (var7.compareTo(var8) < 0) {
                     var2 = var3;
                     var6 = var8;
                  }

                  var3 = var2;
                  if (var4 == var5) {
                     break;
                  }

                  var4++;
                  var7 = var6;
               }
            }

            return var3;
         }
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> DoubleArray.maxByOrNull(selector: (Double) -> R): Double? {
      if (var0.length == 0) {
         return null;
      } else {
         var var2: Double = var0[0];
         val var7: Int = ArraysKt.getLastIndex(var0);
         if (var7 == 0) {
            return var2;
         } else {
            var var8: java.lang.Comparable = var1.invoke(var2) as java.lang.Comparable;
            var var6: Int = 1;
            var var4: Double = var2;
            if (1 <= var7) {
               while (true) {
                  var4 = var0[var6];
                  val var10: java.lang.Comparable = var1.invoke(var0[var6]) as java.lang.Comparable;
                  var var9: java.lang.Comparable = var8;
                  if (var8.compareTo(var10) < 0) {
                     var2 = var4;
                     var9 = var10;
                  }

                  var4 = var2;
                  if (var6 == var7) {
                     break;
                  }

                  var6++;
                  var8 = var9;
               }
            }

            return var4;
         }
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> FloatArray.maxByOrNull(selector: (Float) -> R): Float? {
      if (var0.length == 0) {
         return null;
      } else {
         var var2: Float = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var7: java.lang.Comparable = var1.invoke(var2) as java.lang.Comparable;
            var var4: Int = 1;
            var var3: Float = var2;
            if (1 <= var5) {
               while (true) {
                  var3 = var0[var4];
                  val var8: java.lang.Comparable = var1.invoke(var0[var4]) as java.lang.Comparable;
                  var var6: java.lang.Comparable = var7;
                  if (var7.compareTo(var8) < 0) {
                     var2 = var3;
                     var6 = var8;
                  }

                  var3 = var2;
                  if (var4 == var5) {
                     break;
                  }

                  var4++;
                  var7 = var6;
               }
            }

            return var3;
         }
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> IntArray.maxByOrNull(selector: (Int) -> R): Int? {
      if (var0.length == 0) {
         return null;
      } else {
         var var2: Int = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var7: java.lang.Comparable = var1.invoke(var2) as java.lang.Comparable;
            var var3: Int = 1;
            var var4: Int = var2;
            if (1 <= var5) {
               while (true) {
                  var4 = var0[var3];
                  val var8: java.lang.Comparable = var1.invoke(var0[var3]) as java.lang.Comparable;
                  var var6: java.lang.Comparable = var7;
                  if (var7.compareTo(var8) < 0) {
                     var2 = var4;
                     var6 = var8;
                  }

                  var4 = var2;
                  if (var3 == var5) {
                     break;
                  }

                  var3++;
                  var7 = var6;
               }
            }

            return var4;
         }
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> LongArray.maxByOrNull(selector: (Long) -> R): Long? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Long = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         if (var3 == 0) {
            return var4;
         } else {
            var var8: java.lang.Comparable = var1.invoke(var4) as java.lang.Comparable;
            var var2: Int = 1;
            var var6: Long = var4;
            if (1 <= var3) {
               while (true) {
                  var6 = var0[var2];
                  val var10: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
                  var var9: java.lang.Comparable = var8;
                  if (var8.compareTo(var10) < 0) {
                     var4 = var6;
                     var9 = var10;
                  }

                  var6 = var4;
                  if (var2 == var3) {
                     break;
                  }

                  var2++;
                  var8 = var9;
               }
            }

            return var6;
         }
      }
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Array<out T>.maxByOrNull(selector: (T) -> R): T? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Any = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         if (var3 == 0) {
            return (T)var4;
         } else {
            var var5: java.lang.Comparable = var1.invoke(var4) as java.lang.Comparable;
            var var2: Int = 1;
            var var6: Any = var4;
            if (1 <= var3) {
               var6 = var5;

               while (true) {
                  val var8: Any = var0[var2];
                  val var7: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
                  var5 = (java.lang.Comparable)var6;
                  if (var6.compareTo(var7) < 0) {
                     var4 = var8;
                     var5 = var7;
                  }

                  var6 = var4;
                  if (var2 == var3) {
                     break;
                  }

                  var2++;
                  var6 = var5;
               }
            }

            return (T)var6;
         }
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> ShortArray.maxByOrNull(selector: (Short) -> R): Short? {
      if (var0.length == 0) {
         return null;
      } else {
         var var2: Short = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var7: java.lang.Comparable = var1.invoke(var2) as java.lang.Comparable;
            var var4: Int = 1;
            var var3: Short = var2;
            if (1 <= var5) {
               while (true) {
                  var3 = var0[var4];
                  val var8: java.lang.Comparable = var1.invoke(var0[var4]) as java.lang.Comparable;
                  var var6: java.lang.Comparable = var7;
                  if (var7.compareTo(var8) < 0) {
                     var2 = var3;
                     var6 = var8;
                  }

                  var3 = var2;
                  if (var4 == var5) {
                     break;
                  }

                  var4++;
                  var7 = var6;
               }
            }

            return var3;
         }
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> ByteArray.maxBy(selector: (Byte) -> R): Byte {
      if (var0.length != 0) {
         var var2: Byte = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var7: java.lang.Comparable = var1.invoke(var2) as java.lang.Comparable;
            var var4: Int = 1;
            var var3: Byte = var2;
            if (1 <= var5) {
               while (true) {
                  var3 = var0[var4];
                  val var8: java.lang.Comparable = var1.invoke(var0[var4]) as java.lang.Comparable;
                  var var6: java.lang.Comparable = var7;
                  if (var7.compareTo(var8) < 0) {
                     var2 = var3;
                     var6 = var8;
                  }

                  var3 = var2;
                  if (var4 == var5) {
                     break;
                  }

                  var4++;
                  var7 = var6;
               }
            }

            return var3;
         }
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> CharArray.maxBy(selector: (Char) -> R): Char {
      if (var0.length != 0) {
         var var2: Char = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var7: java.lang.Comparable = var1.invoke(var2) as java.lang.Comparable;
            var var4: Int = 1;
            var var3: Char = var2;
            if (1 <= var5) {
               while (true) {
                  var3 = var0[var4];
                  val var8: java.lang.Comparable = var1.invoke(var0[var4]) as java.lang.Comparable;
                  var var6: java.lang.Comparable = var7;
                  if (var7.compareTo(var8) < 0) {
                     var2 = var3;
                     var6 = var8;
                  }

                  var3 = var2;
                  if (var4 == var5) {
                     break;
                  }

                  var4++;
                  var7 = var6;
               }
            }

            return var3;
         }
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> DoubleArray.maxBy(selector: (Double) -> R): Double {
      if (var0.length != 0) {
         var var2: Double = var0[0];
         val var7: Int = ArraysKt.getLastIndex(var0);
         if (var7 == 0) {
            return var2;
         } else {
            var var9: java.lang.Comparable = var1.invoke(var2) as java.lang.Comparable;
            var var6: Int = 1;
            var var4: Double = var2;
            if (1 <= var7) {
               while (true) {
                  var4 = var0[var6];
                  val var10: java.lang.Comparable = var1.invoke(var0[var6]) as java.lang.Comparable;
                  var var8: java.lang.Comparable = var9;
                  if (var9.compareTo(var10) < 0) {
                     var2 = var4;
                     var8 = var10;
                  }

                  var4 = var2;
                  if (var6 == var7) {
                     break;
                  }

                  var6++;
                  var9 = var8;
               }
            }

            return var4;
         }
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> FloatArray.maxBy(selector: (Float) -> R): Float {
      if (var0.length != 0) {
         var var2: Float = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var6: java.lang.Comparable = var1.invoke(var2) as java.lang.Comparable;
            var var4: Int = 1;
            var var3: Float = var2;
            if (1 <= var5) {
               while (true) {
                  var3 = var0[var4];
                  val var8: java.lang.Comparable = var1.invoke(var0[var4]) as java.lang.Comparable;
                  var var7: java.lang.Comparable = var6;
                  if (var6.compareTo(var8) < 0) {
                     var2 = var3;
                     var7 = var8;
                  }

                  var3 = var2;
                  if (var4 == var5) {
                     break;
                  }

                  var4++;
                  var6 = var7;
               }
            }

            return var3;
         }
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> IntArray.maxBy(selector: (Int) -> R): Int {
      if (var0.length != 0) {
         var var2: Int = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var6: java.lang.Comparable = var1.invoke(var2) as java.lang.Comparable;
            var var3: Int = 1;
            var var4: Int = var2;
            if (1 <= var5) {
               while (true) {
                  var4 = var0[var3];
                  val var8: java.lang.Comparable = var1.invoke(var0[var3]) as java.lang.Comparable;
                  var var7: java.lang.Comparable = var6;
                  if (var6.compareTo(var8) < 0) {
                     var2 = var4;
                     var7 = var8;
                  }

                  var4 = var2;
                  if (var3 == var5) {
                     break;
                  }

                  var3++;
                  var6 = var7;
               }
            }

            return var4;
         }
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> LongArray.maxBy(selector: (Long) -> R): Long {
      if (var0.length != 0) {
         var var4: Long = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         if (var3 == 0) {
            return var4;
         } else {
            var var9: java.lang.Comparable = var1.invoke(var4) as java.lang.Comparable;
            var var2: Int = 1;
            var var6: Long = var4;
            if (1 <= var3) {
               while (true) {
                  var6 = var0[var2];
                  val var10: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
                  var var8: java.lang.Comparable = var9;
                  if (var9.compareTo(var10) < 0) {
                     var4 = var6;
                     var8 = var10;
                  }

                  var6 = var4;
                  if (var2 == var3) {
                     break;
                  }

                  var2++;
                  var9 = var8;
               }
            }

            return var6;
         }
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Array<out T>.maxBy(selector: (T) -> R): T {
      if (var0.length != 0) {
         var var4: Any = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         if (var3 == 0) {
            return (T)var4;
         } else {
            var var5: java.lang.Comparable = var1.invoke(var4) as java.lang.Comparable;
            var var2: Int = 1;
            var var6: Any = var4;
            if (1 <= var3) {
               var6 = var5;

               while (true) {
                  val var8: Any = var0[var2];
                  val var7: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
                  var5 = (java.lang.Comparable)var6;
                  if (var6.compareTo(var7) < 0) {
                     var4 = var8;
                     var5 = var7;
                  }

                  var6 = var4;
                  if (var2 == var3) {
                     break;
                  }

                  var2++;
                  var6 = var5;
               }
            }

            return (T)var6;
         }
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> ShortArray.maxBy(selector: (Short) -> R): Short {
      if (var0.length != 0) {
         var var2: Short = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var7: java.lang.Comparable = var1.invoke(var2) as java.lang.Comparable;
            var var4: Int = 1;
            var var3: Short = var2;
            if (1 <= var5) {
               while (true) {
                  var3 = var0[var4];
                  val var8: java.lang.Comparable = var1.invoke(var0[var4]) as java.lang.Comparable;
                  var var6: java.lang.Comparable = var7;
                  if (var7.compareTo(var8) < 0) {
                     var2 = var3;
                     var6 = var8;
                  }

                  var3 = var2;
                  if (var4 == var5) {
                     break;
                  }

                  var4++;
                  var7 = var6;
               }
            }

            return var3;
         }
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> BooleanArray.maxBy(selector: (Boolean) -> R): Boolean {
      if (var0.length != 0) {
         var var4: Boolean = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         if (var3 == 0) {
            return var4;
         } else {
            var var7: java.lang.Comparable = var1.invoke(var4) as java.lang.Comparable;
            var var2: Int = 1;
            var var5: Boolean = var4;
            if (1 <= var3) {
               while (true) {
                  var5 = var0[var2];
                  val var8: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
                  var var6: java.lang.Comparable = var7;
                  if (var7.compareTo(var8) < 0) {
                     var4 = var5;
                     var6 = var8;
                  }

                  var5 = var4;
                  if (var2 == var3) {
                     break;
                  }

                  var2++;
                  var7 = var6;
               }
            }

            return var5;
         }
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun ByteArray.maxOf(selector: (Byte) -> Double): Double {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.max(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun CharArray.maxOf(selector: (Char) -> Double): Double {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.max(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun DoubleArray.maxOf(selector: (Double) -> Double): Double {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.max(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun FloatArray.maxOf(selector: (Float) -> Double): Double {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.max(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun IntArray.maxOf(selector: (Int) -> Double): Double {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.max(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun LongArray.maxOf(selector: (Long) -> Double): Double {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.max(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <T> Array<out T>.maxOf(selector: (T) -> Double): Double {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.max(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun ShortArray.maxOf(selector: (Short) -> Double): Double {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.max(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun BooleanArray.maxOf(selector: (Boolean) -> Double): Double {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.max(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun ByteArray.maxOf(selector: (Byte) -> Float): Float {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.max(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun CharArray.maxOf(selector: (Char) -> Float): Float {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.max(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun DoubleArray.maxOf(selector: (Double) -> Float): Float {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.max(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun FloatArray.maxOf(selector: (Float) -> Float): Float {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.max(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun IntArray.maxOf(selector: (Int) -> Float): Float {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.max(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun LongArray.maxOf(selector: (Long) -> Float): Float {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.max(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <T> Array<out T>.maxOf(selector: (T) -> Float): Float {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.max(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun ShortArray.maxOf(selector: (Short) -> Float): Float {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.max(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun BooleanArray.maxOf(selector: (Boolean) -> Float): Float {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.max(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> ByteArray.maxOf(selector: (Byte) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) < 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> CharArray.maxOf(selector: (Char) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) < 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> DoubleArray.maxOf(selector: (Double) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) < 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> FloatArray.maxOf(selector: (Float) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) < 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> IntArray.maxOf(selector: (Int) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) < 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> LongArray.maxOf(selector: (Long) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) < 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Array<out T>.maxOf(selector: (T) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) < 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> ShortArray.maxOf(selector: (Short) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) < 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> BooleanArray.maxOf(selector: (Boolean) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) < 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> ByteArray.maxOfOrNull(selector: (Byte) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) < 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> CharArray.maxOfOrNull(selector: (Char) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) < 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> DoubleArray.maxOfOrNull(selector: (Double) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) < 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> FloatArray.maxOfOrNull(selector: (Float) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) < 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> IntArray.maxOfOrNull(selector: (Int) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) < 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> LongArray.maxOfOrNull(selector: (Long) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) < 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Array<out T>.maxOfOrNull(selector: (T) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) < 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> ShortArray.maxOfOrNull(selector: (Short) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) < 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> BooleanArray.maxOfOrNull(selector: (Boolean) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) < 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun ByteArray.maxOfOrNull(selector: (Byte) -> Double): Double? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.max(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun CharArray.maxOfOrNull(selector: (Char) -> Double): Double? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.max(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun DoubleArray.maxOfOrNull(selector: (Double) -> Double): Double? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.max(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun FloatArray.maxOfOrNull(selector: (Float) -> Double): Double? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.max(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun IntArray.maxOfOrNull(selector: (Int) -> Double): Double? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.max(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun LongArray.maxOfOrNull(selector: (Long) -> Double): Double? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.max(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <T> Array<out T>.maxOfOrNull(selector: (T) -> Double): Double? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.max(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun ShortArray.maxOfOrNull(selector: (Short) -> Double): Double? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.max(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun BooleanArray.maxOfOrNull(selector: (Boolean) -> Double): Double? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.max(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun ByteArray.maxOfOrNull(selector: (Byte) -> Float): Float? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.max(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun CharArray.maxOfOrNull(selector: (Char) -> Float): Float? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.max(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun DoubleArray.maxOfOrNull(selector: (Double) -> Float): Float? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.max(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun FloatArray.maxOfOrNull(selector: (Float) -> Float): Float? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.max(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun IntArray.maxOfOrNull(selector: (Int) -> Float): Float? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.max(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun LongArray.maxOfOrNull(selector: (Long) -> Float): Float? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.max(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <T> Array<out T>.maxOfOrNull(selector: (T) -> Float): Float? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.max(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun ShortArray.maxOfOrNull(selector: (Short) -> Float): Float? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.max(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun BooleanArray.maxOfOrNull(selector: (Boolean) -> Float): Float? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.max(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <R> ByteArray.maxOfWith(comparator: Comparator<in R>, selector: (Byte) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) < 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> CharArray.maxOfWith(comparator: Comparator<in R>, selector: (Char) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) < 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> DoubleArray.maxOfWith(comparator: Comparator<in R>, selector: (Double) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) < 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> FloatArray.maxOfWith(comparator: Comparator<in R>, selector: (Float) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) < 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> IntArray.maxOfWith(comparator: Comparator<in R>, selector: (Int) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) < 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> LongArray.maxOfWith(comparator: Comparator<in R>, selector: (Long) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) < 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <T, R> Array<out T>.maxOfWith(comparator: Comparator<in R>, selector: (T) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) < 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> ShortArray.maxOfWith(comparator: Comparator<in R>, selector: (Short) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) < 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> BooleanArray.maxOfWith(comparator: Comparator<in R>, selector: (Boolean) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) < 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> ByteArray.maxOfWithOrNull(comparator: Comparator<in R>, selector: (Byte) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) < 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> CharArray.maxOfWithOrNull(comparator: Comparator<in R>, selector: (Char) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) < 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> DoubleArray.maxOfWithOrNull(comparator: Comparator<in R>, selector: (Double) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) < 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> FloatArray.maxOfWithOrNull(comparator: Comparator<in R>, selector: (Float) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) < 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> IntArray.maxOfWithOrNull(comparator: Comparator<in R>, selector: (Int) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) < 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> LongArray.maxOfWithOrNull(comparator: Comparator<in R>, selector: (Long) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) < 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <T, R> Array<out T>.maxOfWithOrNull(comparator: Comparator<in R>, selector: (T) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) < 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> ShortArray.maxOfWithOrNull(comparator: Comparator<in R>, selector: (Short) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) < 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> BooleanArray.maxOfWithOrNull(comparator: Comparator<in R>, selector: (Boolean) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) < 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public fun ByteArray.maxOrNull(): Byte? {
      if (var0.length == 0) {
         return null;
      } else {
         var var1: Byte = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Byte = var1;
         if (1 <= var5) {
            var2 = var1;

            while (true) {
               val var3: Byte = var0[var4];
               var1 = var2;
               if (var2 < var3) {
                  var1 = var3;
               }

               var2 = var1;
               if (var4 == var5) {
                  break;
               }

               var4++;
               var2 = var1;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public fun CharArray.maxOrNull(): Char? {
      if (var0.length == 0) {
         return null;
      } else {
         var var1: Char = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Char = var1;
         if (1 <= var5) {
            var2 = var1;

            while (true) {
               val var3: Char = var0[var4];
               var1 = var2;
               if (Intrinsics.compare((int)var2, (int)var3) < 0) {
                  var1 = var3;
               }

               var2 = var1;
               if (var4 == var5) {
                  break;
               }

               var4++;
               var2 = var1;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public fun <T : Comparable<T>> Array<out T>.maxOrNull(): T? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: java.lang.Comparable = var0[0];
         val var2: Int = ArraysKt.getLastIndex(var0);
         var var1: Int = 1;
         var var4: java.lang.Comparable = var3;
         if (1 <= var2) {
            var4 = var3;

            while (true) {
               val var5: java.lang.Comparable = var0[var1];
               var3 = var4;
               if (var4.compareTo(var5) < 0) {
                  var3 = var5;
               }

               var4 = var3;
               if (var1 == var2) {
                  break;
               }

               var1++;
               var4 = var3;
            }
         }

         return (T)var4;
      }
   }

   @JvmStatic
   public fun DoubleArray.maxOrNull(): Double? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Double = var0[0];
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var1: Double = var3;
         if (1 <= var6) {
            while (true) {
               var3 = Math.max(var3, var0[var5]);
               var1 = var3;
               if (var5 == var6) {
                  break;
               }

               var5++;
            }
         }

         return var1;
      }
   }

   @JvmStatic
   public fun Array<out Double>.maxOrNull(): Double? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Double = var0[0];
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var1: Double = var3;
         if (1 <= var6) {
            while (true) {
               var3 = Math.max(var3, var0[var5]);
               var1 = var3;
               if (var5 == var6) {
                  break;
               }

               var5++;
            }
         }

         return var1;
      }
   }

   @JvmStatic
   public fun FloatArray.maxOrNull(): Float? {
      if (var0.length == 0) {
         return null;
      } else {
         var var2: Float = var0[0];
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var1: Float = var2;
         if (1 <= var4) {
            while (true) {
               var2 = Math.max(var2, var0[var3]);
               var1 = var2;
               if (var3 == var4) {
                  break;
               }

               var3++;
            }
         }

         return var1;
      }
   }

   @JvmStatic
   public fun Array<out Float>.maxOrNull(): Float? {
      if (var0.length == 0) {
         return null;
      } else {
         var var2: Float = var0[0];
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var1: Float = var2;
         if (1 <= var4) {
            while (true) {
               var2 = Math.max(var2, var0[var3]);
               var1 = var2;
               if (var3 == var4) {
                  break;
               }

               var3++;
            }
         }

         return var1;
      }
   }

   @JvmStatic
   public fun IntArray.maxOrNull(): Int? {
      if (var0.length == 0) {
         return null;
      } else {
         var var1: Int = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var3: Int = var1;
         if (1 <= var5) {
            var3 = var1;

            while (true) {
               val var4: Int = var0[var2];
               var1 = var3;
               if (var3 < var4) {
                  var1 = var4;
               }

               var3 = var1;
               if (var2 == var5) {
                  break;
               }

               var2++;
               var3 = var1;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun LongArray.maxOrNull(): Long? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Long = var0[0];
         val var2: Int = ArraysKt.getLastIndex(var0);
         var var1: Int = 1;
         var var5: Long = var3;
         if (1 <= var2) {
            var5 = var3;

            while (true) {
               val var7: Long = var0[var1];
               var3 = var5;
               if (var5 < var7) {
                  var3 = var7;
               }

               var5 = var3;
               if (var1 == var2) {
                  break;
               }

               var1++;
               var5 = var3;
            }
         }

         return var5;
      }
   }

   @JvmStatic
   public fun ShortArray.maxOrNull(): Short? {
      if (var0.length == 0) {
         return null;
      } else {
         var var1: Short = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Short = var1;
         if (1 <= var5) {
            var2 = var1;

            while (true) {
               val var3: Short = var0[var4];
               var1 = var2;
               if (var2 < var3) {
                  var1 = var3;
               }

               var2 = var1;
               if (var4 == var5) {
                  break;
               }

               var4++;
               var2 = var1;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public fun ByteArray.max(): Byte {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var1: Byte = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Byte = var1;
         if (1 <= var5) {
            var2 = var1;

            while (true) {
               val var3: Byte = var0[var4];
               var1 = var2;
               if (var2 < var3) {
                  var1 = var3;
               }

               var2 = var1;
               if (var4 == var5) {
                  break;
               }

               var4++;
               var2 = var1;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public fun CharArray.max(): Char {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var1: Char = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Char = var1;
         if (1 <= var5) {
            var2 = var1;

            while (true) {
               val var3: Char = var0[var4];
               var1 = var2;
               if (Intrinsics.compare((int)var2, (int)var3) < 0) {
                  var1 = var3;
               }

               var2 = var1;
               if (var4 == var5) {
                  break;
               }

               var4++;
               var2 = var1;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public fun DoubleArray.max(): Double {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var3: Double = var0[0];
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var1: Double = var3;
         if (1 <= var6) {
            while (true) {
               var3 = Math.max(var3, var0[var5]);
               var1 = var3;
               if (var5 == var6) {
                  break;
               }

               var5++;
            }
         }

         return var1;
      }
   }

   @JvmStatic
   public fun Array<out Double>.max(): Double {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var3: Double = var0[0];
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var1: Double = var3;
         if (1 <= var6) {
            while (true) {
               var3 = Math.max(var3, var0[var5]);
               var1 = var3;
               if (var5 == var6) {
                  break;
               }

               var5++;
            }
         }

         return var1;
      }
   }

   @JvmStatic
   public fun FloatArray.max(): Float {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var2: Float = var0[0];
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var1: Float = var2;
         if (1 <= var4) {
            while (true) {
               var2 = Math.max(var2, var0[var3]);
               var1 = var2;
               if (var3 == var4) {
                  break;
               }

               var3++;
            }
         }

         return var1;
      }
   }

   @JvmStatic
   public fun Array<out Float>.max(): Float {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var2: Float = var0[0];
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var1: Float = var2;
         if (1 <= var4) {
            while (true) {
               var2 = Math.max(var2, var0[var3]);
               var1 = var2;
               if (var3 == var4) {
                  break;
               }

               var3++;
            }
         }

         return var1;
      }
   }

   @JvmStatic
   public fun IntArray.max(): Int {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var1: Int = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var3: Int = var1;
         if (1 <= var5) {
            var3 = var1;

            while (true) {
               val var4: Int = var0[var2];
               var1 = var3;
               if (var3 < var4) {
                  var1 = var4;
               }

               var3 = var1;
               if (var2 == var5) {
                  break;
               }

               var2++;
               var3 = var1;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun LongArray.max(): Long {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var3: Long = var0[0];
         val var2: Int = ArraysKt.getLastIndex(var0);
         var var1: Int = 1;
         var var5: Long = var3;
         if (1 <= var2) {
            var5 = var3;

            while (true) {
               val var7: Long = var0[var1];
               var3 = var5;
               if (var5 < var7) {
                  var3 = var7;
               }

               var5 = var3;
               if (var1 == var2) {
                  break;
               }

               var1++;
               var5 = var3;
            }
         }

         return var5;
      }
   }

   @JvmStatic
   public fun <T : Comparable<T>> Array<out T>.max(): T {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var3: java.lang.Comparable = var0[0];
         val var2: Int = ArraysKt.getLastIndex(var0);
         var var1: Int = 1;
         var var4: java.lang.Comparable = var3;
         if (1 <= var2) {
            var4 = var3;

            while (true) {
               val var5: java.lang.Comparable = var0[var1];
               var3 = var4;
               if (var4.compareTo(var5) < 0) {
                  var3 = var5;
               }

               var4 = var3;
               if (var1 == var2) {
                  break;
               }

               var1++;
               var4 = var3;
            }
         }

         return (T)var4;
      }
   }

   @JvmStatic
   public fun ShortArray.max(): Short {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var1: Short = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Short = var1;
         if (1 <= var5) {
            var2 = var1;

            while (true) {
               val var3: Short = var0[var4];
               var1 = var2;
               if (var2 < var3) {
                  var1 = var3;
               }

               var2 = var1;
               if (var4 == var5) {
                  break;
               }

               var4++;
               var2 = var1;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public fun BooleanArray.maxWithOrNull(comparator: Comparator<in Boolean>): Boolean? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Boolean = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: Boolean = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: Boolean = var0[var2];
               var4 = var5;
               if (var1.compare(var5, var6) < 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return var5;
      }
   }

   @JvmStatic
   public fun ByteArray.maxWithOrNull(comparator: Comparator<in Byte>): Byte? {
      if (var0.length == 0) {
         return null;
      } else {
         var var2: Byte = var0[0];
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var3: Byte = var2;
         if (1 <= var6) {
            var3 = var2;

            while (true) {
               val var4: Byte = var0[var5];
               var2 = var3;
               if (var1.compare(var3, var4) < 0) {
                  var2 = var4;
               }

               var3 = var2;
               if (var5 == var6) {
                  break;
               }

               var5++;
               var3 = var2;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun CharArray.maxWithOrNull(comparator: Comparator<in Char>): Char? {
      if (var0.length == 0) {
         return null;
      } else {
         var var2: Char = var0[0];
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var3: Char = var2;
         if (1 <= var6) {
            var3 = var2;

            while (true) {
               val var4: Char = var0[var5];
               var2 = var3;
               if (var1.compare(var3, var4) < 0) {
                  var2 = var4;
               }

               var3 = var2;
               if (var5 == var6) {
                  break;
               }

               var5++;
               var3 = var2;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun DoubleArray.maxWithOrNull(comparator: Comparator<in Double>): Double? {
      if (var0.length == 0) {
         return null;
      } else {
         var var2: Double = var0[0];
         val var9: Int = ArraysKt.getLastIndex(var0);
         var var8: Int = 1;
         var var4: Double = var2;
         if (1 <= var9) {
            var4 = var2;

            while (true) {
               val var6: Double = var0[var8];
               var2 = var4;
               if (var1.compare(var4, var6) < 0) {
                  var2 = var6;
               }

               var4 = var2;
               if (var8 == var9) {
                  break;
               }

               var8++;
               var4 = var2;
            }
         }

         return var4;
      }
   }

   @JvmStatic
   public fun FloatArray.maxWithOrNull(comparator: Comparator<in Float>): Float? {
      if (var0.length == 0) {
         return null;
      } else {
         var var2: Float = var0[0];
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var3: Float = var2;
         if (1 <= var6) {
            var3 = var2;

            while (true) {
               val var4: Float = var0[var5];
               var2 = var3;
               if (var1.compare(var3, var4) < 0) {
                  var2 = var4;
               }

               var3 = var2;
               if (var5 == var6) {
                  break;
               }

               var5++;
               var3 = var2;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun IntArray.maxWithOrNull(comparator: Comparator<in Int>): Int? {
      if (var0.length == 0) {
         return null;
      } else {
         var var2: Int = var0[0];
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var4: Int = var2;
         if (1 <= var6) {
            var4 = var2;

            while (true) {
               val var5: Int = var0[var3];
               var2 = var4;
               if (var1.compare(var4, var5) < 0) {
                  var2 = var5;
               }

               var4 = var2;
               if (var3 == var6) {
                  break;
               }

               var3++;
               var4 = var2;
            }
         }

         return var4;
      }
   }

   @JvmStatic
   public fun LongArray.maxWithOrNull(comparator: Comparator<in Long>): Long? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Long = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var6: Long = var4;
         if (1 <= var3) {
            var6 = var4;

            while (true) {
               val var8: Long = var0[var2];
               var4 = var6;
               if (var1.compare(var6, var8) < 0) {
                  var4 = var8;
               }

               var6 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var6 = var4;
            }
         }

         return var6;
      }
   }

   @JvmStatic
   public fun <T> Array<out T>.maxWithOrNull(comparator: Comparator<in T>): T? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Any = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: Any = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: Any = var0[var2];
               var4 = var5;
               if (var1.compare(var5, var6) < 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (T)var5;
      }
   }

   @JvmStatic
   public fun ShortArray.maxWithOrNull(comparator: Comparator<in Short>): Short? {
      if (var0.length == 0) {
         return null;
      } else {
         var var2: Short = var0[0];
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var3: Short = var2;
         if (1 <= var6) {
            var3 = var2;

            while (true) {
               val var4: Short = var0[var5];
               var2 = var3;
               if (var1.compare(var3, var4) < 0) {
                  var2 = var4;
               }

               var3 = var2;
               if (var5 == var6) {
                  break;
               }

               var5++;
               var3 = var2;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun ByteArray.maxWith(comparator: Comparator<in Byte>): Byte {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var2: Byte = var0[0];
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var3: Byte = var2;
         if (1 <= var6) {
            var3 = var2;

            while (true) {
               val var4: Byte = var0[var5];
               var2 = var3;
               if (var1.compare(var3, var4) < 0) {
                  var2 = var4;
               }

               var3 = var2;
               if (var5 == var6) {
                  break;
               }

               var5++;
               var3 = var2;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun CharArray.maxWith(comparator: Comparator<in Char>): Char {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var2: Char = var0[0];
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var3: Char = var2;
         if (1 <= var6) {
            var3 = var2;

            while (true) {
               val var4: Char = var0[var5];
               var2 = var3;
               if (var1.compare(var3, var4) < 0) {
                  var2 = var4;
               }

               var3 = var2;
               if (var5 == var6) {
                  break;
               }

               var5++;
               var3 = var2;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun DoubleArray.maxWith(comparator: Comparator<in Double>): Double {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var2: Double = var0[0];
         val var9: Int = ArraysKt.getLastIndex(var0);
         var var8: Int = 1;
         var var4: Double = var2;
         if (1 <= var9) {
            var4 = var2;

            while (true) {
               val var6: Double = var0[var8];
               var2 = var4;
               if (var1.compare(var4, var6) < 0) {
                  var2 = var6;
               }

               var4 = var2;
               if (var8 == var9) {
                  break;
               }

               var8++;
               var4 = var2;
            }
         }

         return var4;
      }
   }

   @JvmStatic
   public fun FloatArray.maxWith(comparator: Comparator<in Float>): Float {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var2: Float = var0[0];
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var3: Float = var2;
         if (1 <= var6) {
            var3 = var2;

            while (true) {
               val var4: Float = var0[var5];
               var2 = var3;
               if (var1.compare(var3, var4) < 0) {
                  var2 = var4;
               }

               var3 = var2;
               if (var5 == var6) {
                  break;
               }

               var5++;
               var3 = var2;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun IntArray.maxWith(comparator: Comparator<in Int>): Int {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var2: Int = var0[0];
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var4: Int = var2;
         if (1 <= var6) {
            var4 = var2;

            while (true) {
               val var5: Int = var0[var3];
               var2 = var4;
               if (var1.compare(var4, var5) < 0) {
                  var2 = var5;
               }

               var4 = var2;
               if (var3 == var6) {
                  break;
               }

               var3++;
               var4 = var2;
            }
         }

         return var4;
      }
   }

   @JvmStatic
   public fun LongArray.maxWith(comparator: Comparator<in Long>): Long {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: Long = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var6: Long = var4;
         if (1 <= var3) {
            var6 = var4;

            while (true) {
               val var8: Long = var0[var2];
               var4 = var6;
               if (var1.compare(var6, var8) < 0) {
                  var4 = var8;
               }

               var6 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var6 = var4;
            }
         }

         return var6;
      }
   }

   @JvmStatic
   public fun <T> Array<out T>.maxWith(comparator: Comparator<in T>): T {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: Any = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: Any = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: Any = var0[var2];
               var4 = var5;
               if (var1.compare(var5, var6) < 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (T)var5;
      }
   }

   @JvmStatic
   public fun ShortArray.maxWith(comparator: Comparator<in Short>): Short {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var2: Short = var0[0];
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var3: Short = var2;
         if (1 <= var6) {
            var3 = var2;

            while (true) {
               val var4: Short = var0[var5];
               var2 = var3;
               if (var1.compare(var3, var4) < 0) {
                  var2 = var4;
               }

               var3 = var2;
               if (var5 == var6) {
                  break;
               }

               var5++;
               var3 = var2;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun BooleanArray.maxWith(comparator: Comparator<in Boolean>): Boolean {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: Boolean = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: Boolean = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: Boolean = var0[var2];
               var4 = var5;
               if (var1.compare(var5, var6) < 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> BooleanArray.minByOrNull(selector: (Boolean) -> R): Boolean? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Boolean = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         if (var3 == 0) {
            return var4;
         } else {
            var var7: java.lang.Comparable = var1.invoke(var4) as java.lang.Comparable;
            var var2: Int = 1;
            var var5: Boolean = var4;
            if (1 <= var3) {
               while (true) {
                  var5 = var0[var2];
                  val var8: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
                  var var6: java.lang.Comparable = var7;
                  if (var7.compareTo(var8) > 0) {
                     var4 = var5;
                     var6 = var8;
                  }

                  var5 = var4;
                  if (var2 == var3) {
                     break;
                  }

                  var2++;
                  var7 = var6;
               }
            }

            return var5;
         }
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> ByteArray.minByOrNull(selector: (Byte) -> R): Byte? {
      if (var0.length == 0) {
         return null;
      } else {
         var var2: Byte = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var7: java.lang.Comparable = var1.invoke(var2) as java.lang.Comparable;
            var var4: Int = 1;
            var var3: Byte = var2;
            if (1 <= var5) {
               while (true) {
                  var3 = var0[var4];
                  val var8: java.lang.Comparable = var1.invoke(var0[var4]) as java.lang.Comparable;
                  var var6: java.lang.Comparable = var7;
                  if (var7.compareTo(var8) > 0) {
                     var2 = var3;
                     var6 = var8;
                  }

                  var3 = var2;
                  if (var4 == var5) {
                     break;
                  }

                  var4++;
                  var7 = var6;
               }
            }

            return var3;
         }
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> CharArray.minByOrNull(selector: (Char) -> R): Char? {
      if (var0.length == 0) {
         return null;
      } else {
         var var2: Char = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var7: java.lang.Comparable = var1.invoke(var2) as java.lang.Comparable;
            var var4: Int = 1;
            var var3: Char = var2;
            if (1 <= var5) {
               while (true) {
                  var3 = var0[var4];
                  val var8: java.lang.Comparable = var1.invoke(var0[var4]) as java.lang.Comparable;
                  var var6: java.lang.Comparable = var7;
                  if (var7.compareTo(var8) > 0) {
                     var2 = var3;
                     var6 = var8;
                  }

                  var3 = var2;
                  if (var4 == var5) {
                     break;
                  }

                  var4++;
                  var7 = var6;
               }
            }

            return var3;
         }
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> DoubleArray.minByOrNull(selector: (Double) -> R): Double? {
      if (var0.length == 0) {
         return null;
      } else {
         var var2: Double = var0[0];
         val var7: Int = ArraysKt.getLastIndex(var0);
         if (var7 == 0) {
            return var2;
         } else {
            var var8: java.lang.Comparable = var1.invoke(var2) as java.lang.Comparable;
            var var6: Int = 1;
            var var4: Double = var2;
            if (1 <= var7) {
               while (true) {
                  var4 = var0[var6];
                  val var10: java.lang.Comparable = var1.invoke(var0[var6]) as java.lang.Comparable;
                  var var9: java.lang.Comparable = var8;
                  if (var8.compareTo(var10) > 0) {
                     var2 = var4;
                     var9 = var10;
                  }

                  var4 = var2;
                  if (var6 == var7) {
                     break;
                  }

                  var6++;
                  var8 = var9;
               }
            }

            return var4;
         }
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> FloatArray.minByOrNull(selector: (Float) -> R): Float? {
      if (var0.length == 0) {
         return null;
      } else {
         var var2: Float = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var7: java.lang.Comparable = var1.invoke(var2) as java.lang.Comparable;
            var var4: Int = 1;
            var var3: Float = var2;
            if (1 <= var5) {
               while (true) {
                  var3 = var0[var4];
                  val var8: java.lang.Comparable = var1.invoke(var0[var4]) as java.lang.Comparable;
                  var var6: java.lang.Comparable = var7;
                  if (var7.compareTo(var8) > 0) {
                     var2 = var3;
                     var6 = var8;
                  }

                  var3 = var2;
                  if (var4 == var5) {
                     break;
                  }

                  var4++;
                  var7 = var6;
               }
            }

            return var3;
         }
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> IntArray.minByOrNull(selector: (Int) -> R): Int? {
      if (var0.length == 0) {
         return null;
      } else {
         var var2: Int = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var6: java.lang.Comparable = var1.invoke(var2) as java.lang.Comparable;
            var var3: Int = 1;
            var var4: Int = var2;
            if (1 <= var5) {
               while (true) {
                  var4 = var0[var3];
                  val var8: java.lang.Comparable = var1.invoke(var0[var3]) as java.lang.Comparable;
                  var var7: java.lang.Comparable = var6;
                  if (var6.compareTo(var8) > 0) {
                     var2 = var4;
                     var7 = var8;
                  }

                  var4 = var2;
                  if (var3 == var5) {
                     break;
                  }

                  var3++;
                  var6 = var7;
               }
            }

            return var4;
         }
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> LongArray.minByOrNull(selector: (Long) -> R): Long? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Long = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         if (var3 == 0) {
            return var4;
         } else {
            var var9: java.lang.Comparable = var1.invoke(var4) as java.lang.Comparable;
            var var2: Int = 1;
            var var6: Long = var4;
            if (1 <= var3) {
               while (true) {
                  var6 = var0[var2];
                  val var10: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
                  var var8: java.lang.Comparable = var9;
                  if (var9.compareTo(var10) > 0) {
                     var4 = var6;
                     var8 = var10;
                  }

                  var6 = var4;
                  if (var2 == var3) {
                     break;
                  }

                  var2++;
                  var9 = var8;
               }
            }

            return var6;
         }
      }
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Array<out T>.minByOrNull(selector: (T) -> R): T? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Any = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         if (var3 == 0) {
            return (T)var4;
         } else {
            var var5: java.lang.Comparable = var1.invoke(var4) as java.lang.Comparable;
            var var2: Int = 1;
            var var6: Any = var4;
            if (1 <= var3) {
               var6 = var5;

               while (true) {
                  val var8: Any = var0[var2];
                  val var7: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
                  var5 = (java.lang.Comparable)var6;
                  if (var6.compareTo(var7) > 0) {
                     var4 = var8;
                     var5 = var7;
                  }

                  var6 = var4;
                  if (var2 == var3) {
                     break;
                  }

                  var2++;
                  var6 = var5;
               }
            }

            return (T)var6;
         }
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> ShortArray.minByOrNull(selector: (Short) -> R): Short? {
      if (var0.length == 0) {
         return null;
      } else {
         var var2: Short = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var7: java.lang.Comparable = var1.invoke(var2) as java.lang.Comparable;
            var var4: Int = 1;
            var var3: Short = var2;
            if (1 <= var5) {
               while (true) {
                  var3 = var0[var4];
                  val var8: java.lang.Comparable = var1.invoke(var0[var4]) as java.lang.Comparable;
                  var var6: java.lang.Comparable = var7;
                  if (var7.compareTo(var8) > 0) {
                     var2 = var3;
                     var6 = var8;
                  }

                  var3 = var2;
                  if (var4 == var5) {
                     break;
                  }

                  var4++;
                  var7 = var6;
               }
            }

            return var3;
         }
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> ByteArray.minBy(selector: (Byte) -> R): Byte {
      if (var0.length != 0) {
         var var2: Byte = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var7: java.lang.Comparable = var1.invoke(var2) as java.lang.Comparable;
            var var4: Int = 1;
            var var3: Byte = var2;
            if (1 <= var5) {
               while (true) {
                  var3 = var0[var4];
                  val var8: java.lang.Comparable = var1.invoke(var0[var4]) as java.lang.Comparable;
                  var var6: java.lang.Comparable = var7;
                  if (var7.compareTo(var8) > 0) {
                     var2 = var3;
                     var6 = var8;
                  }

                  var3 = var2;
                  if (var4 == var5) {
                     break;
                  }

                  var4++;
                  var7 = var6;
               }
            }

            return var3;
         }
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> CharArray.minBy(selector: (Char) -> R): Char {
      if (var0.length != 0) {
         var var2: Char = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var7: java.lang.Comparable = var1.invoke(var2) as java.lang.Comparable;
            var var4: Int = 1;
            var var3: Char = var2;
            if (1 <= var5) {
               while (true) {
                  var3 = var0[var4];
                  val var8: java.lang.Comparable = var1.invoke(var0[var4]) as java.lang.Comparable;
                  var var6: java.lang.Comparable = var7;
                  if (var7.compareTo(var8) > 0) {
                     var2 = var3;
                     var6 = var8;
                  }

                  var3 = var2;
                  if (var4 == var5) {
                     break;
                  }

                  var4++;
                  var7 = var6;
               }
            }

            return var3;
         }
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> DoubleArray.minBy(selector: (Double) -> R): Double {
      if (var0.length != 0) {
         var var2: Double = var0[0];
         val var7: Int = ArraysKt.getLastIndex(var0);
         if (var7 == 0) {
            return var2;
         } else {
            var var9: java.lang.Comparable = var1.invoke(var2) as java.lang.Comparable;
            var var6: Int = 1;
            var var4: Double = var2;
            if (1 <= var7) {
               while (true) {
                  var4 = var0[var6];
                  val var10: java.lang.Comparable = var1.invoke(var0[var6]) as java.lang.Comparable;
                  var var8: java.lang.Comparable = var9;
                  if (var9.compareTo(var10) > 0) {
                     var2 = var4;
                     var8 = var10;
                  }

                  var4 = var2;
                  if (var6 == var7) {
                     break;
                  }

                  var6++;
                  var9 = var8;
               }
            }

            return var4;
         }
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> FloatArray.minBy(selector: (Float) -> R): Float {
      if (var0.length != 0) {
         var var2: Float = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var6: java.lang.Comparable = var1.invoke(var2) as java.lang.Comparable;
            var var4: Int = 1;
            var var3: Float = var2;
            if (1 <= var5) {
               while (true) {
                  var3 = var0[var4];
                  val var8: java.lang.Comparable = var1.invoke(var0[var4]) as java.lang.Comparable;
                  var var7: java.lang.Comparable = var6;
                  if (var6.compareTo(var8) > 0) {
                     var2 = var3;
                     var7 = var8;
                  }

                  var3 = var2;
                  if (var4 == var5) {
                     break;
                  }

                  var4++;
                  var6 = var7;
               }
            }

            return var3;
         }
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> IntArray.minBy(selector: (Int) -> R): Int {
      if (var0.length != 0) {
         var var2: Int = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var6: java.lang.Comparable = var1.invoke(var2) as java.lang.Comparable;
            var var3: Int = 1;
            var var4: Int = var2;
            if (1 <= var5) {
               while (true) {
                  var4 = var0[var3];
                  val var8: java.lang.Comparable = var1.invoke(var0[var3]) as java.lang.Comparable;
                  var var7: java.lang.Comparable = var6;
                  if (var6.compareTo(var8) > 0) {
                     var2 = var4;
                     var7 = var8;
                  }

                  var4 = var2;
                  if (var3 == var5) {
                     break;
                  }

                  var3++;
                  var6 = var7;
               }
            }

            return var4;
         }
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> LongArray.minBy(selector: (Long) -> R): Long {
      if (var0.length != 0) {
         var var4: Long = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         if (var3 == 0) {
            return var4;
         } else {
            var var9: java.lang.Comparable = var1.invoke(var4) as java.lang.Comparable;
            var var2: Int = 1;
            var var6: Long = var4;
            if (1 <= var3) {
               while (true) {
                  var6 = var0[var2];
                  val var10: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
                  var var8: java.lang.Comparable = var9;
                  if (var9.compareTo(var10) > 0) {
                     var4 = var6;
                     var8 = var10;
                  }

                  var6 = var4;
                  if (var2 == var3) {
                     break;
                  }

                  var2++;
                  var9 = var8;
               }
            }

            return var6;
         }
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Array<out T>.minBy(selector: (T) -> R): T {
      if (var0.length != 0) {
         var var4: Any = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         if (var3 == 0) {
            return (T)var4;
         } else {
            var var5: java.lang.Comparable = var1.invoke(var4) as java.lang.Comparable;
            var var2: Int = 1;
            var var6: Any = var4;
            if (1 <= var3) {
               var6 = var5;

               while (true) {
                  val var8: Any = var0[var2];
                  val var7: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
                  var5 = (java.lang.Comparable)var6;
                  if (var6.compareTo(var7) > 0) {
                     var4 = var8;
                     var5 = var7;
                  }

                  var6 = var4;
                  if (var2 == var3) {
                     break;
                  }

                  var2++;
                  var6 = var5;
               }
            }

            return (T)var6;
         }
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> ShortArray.minBy(selector: (Short) -> R): Short {
      if (var0.length != 0) {
         var var2: Short = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var7: java.lang.Comparable = var1.invoke(var2) as java.lang.Comparable;
            var var4: Int = 1;
            var var3: Short = var2;
            if (1 <= var5) {
               while (true) {
                  var3 = var0[var4];
                  val var8: java.lang.Comparable = var1.invoke(var0[var4]) as java.lang.Comparable;
                  var var6: java.lang.Comparable = var7;
                  if (var7.compareTo(var8) > 0) {
                     var2 = var3;
                     var6 = var8;
                  }

                  var3 = var2;
                  if (var4 == var5) {
                     break;
                  }

                  var4++;
                  var7 = var6;
               }
            }

            return var3;
         }
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> BooleanArray.minBy(selector: (Boolean) -> R): Boolean {
      if (var0.length != 0) {
         var var4: Boolean = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         if (var3 == 0) {
            return var4;
         } else {
            var var7: java.lang.Comparable = var1.invoke(var4) as java.lang.Comparable;
            var var2: Int = 1;
            var var5: Boolean = var4;
            if (1 <= var3) {
               while (true) {
                  var5 = var0[var2];
                  val var8: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
                  var var6: java.lang.Comparable = var7;
                  if (var7.compareTo(var8) > 0) {
                     var4 = var5;
                     var6 = var8;
                  }

                  var5 = var4;
                  if (var2 == var3) {
                     break;
                  }

                  var2++;
                  var7 = var6;
               }
            }

            return var5;
         }
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun ByteArray.minOf(selector: (Byte) -> Double): Double {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.min(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun CharArray.minOf(selector: (Char) -> Double): Double {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.min(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun DoubleArray.minOf(selector: (Double) -> Double): Double {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.min(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun FloatArray.minOf(selector: (Float) -> Double): Double {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.min(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun IntArray.minOf(selector: (Int) -> Double): Double {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.min(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun LongArray.minOf(selector: (Long) -> Double): Double {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.min(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <T> Array<out T>.minOf(selector: (T) -> Double): Double {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.min(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun ShortArray.minOf(selector: (Short) -> Double): Double {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.min(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun BooleanArray.minOf(selector: (Boolean) -> Double): Double {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.min(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun ByteArray.minOf(selector: (Byte) -> Float): Float {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.min(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun CharArray.minOf(selector: (Char) -> Float): Float {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.min(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun DoubleArray.minOf(selector: (Double) -> Float): Float {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.min(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun FloatArray.minOf(selector: (Float) -> Float): Float {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.min(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun IntArray.minOf(selector: (Int) -> Float): Float {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.min(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun LongArray.minOf(selector: (Long) -> Float): Float {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.min(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <T> Array<out T>.minOf(selector: (T) -> Float): Float {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.min(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun ShortArray.minOf(selector: (Short) -> Float): Float {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.min(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun BooleanArray.minOf(selector: (Boolean) -> Float): Float {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.min(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> ByteArray.minOf(selector: (Byte) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) > 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> CharArray.minOf(selector: (Char) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) > 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> DoubleArray.minOf(selector: (Double) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) > 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> FloatArray.minOf(selector: (Float) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) > 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> IntArray.minOf(selector: (Int) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) > 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> LongArray.minOf(selector: (Long) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) > 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Array<out T>.minOf(selector: (T) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) > 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> ShortArray.minOf(selector: (Short) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) > 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> BooleanArray.minOf(selector: (Boolean) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) > 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> ByteArray.minOfOrNull(selector: (Byte) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) > 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> CharArray.minOfOrNull(selector: (Char) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) > 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> DoubleArray.minOfOrNull(selector: (Double) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) > 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> FloatArray.minOfOrNull(selector: (Float) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) > 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> IntArray.minOfOrNull(selector: (Int) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) > 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> LongArray.minOfOrNull(selector: (Long) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) > 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Array<out T>.minOfOrNull(selector: (T) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) > 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> ShortArray.minOfOrNull(selector: (Short) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) > 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> BooleanArray.minOfOrNull(selector: (Boolean) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(var0[0]) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(var0[var2]) as java.lang.Comparable;
               var4 = var5;
               if (var5.compareTo(var6) > 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (R)var5;
      }
   }

   @JvmStatic
   public inline fun ByteArray.minOfOrNull(selector: (Byte) -> Double): Double? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.min(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun CharArray.minOfOrNull(selector: (Char) -> Double): Double? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.min(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun DoubleArray.minOfOrNull(selector: (Double) -> Double): Double? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.min(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun FloatArray.minOfOrNull(selector: (Float) -> Double): Double? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.min(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun IntArray.minOfOrNull(selector: (Int) -> Double): Double? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.min(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun LongArray.minOfOrNull(selector: (Long) -> Double): Double? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.min(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <T> Array<out T>.minOfOrNull(selector: (T) -> Double): Double? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.min(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun ShortArray.minOfOrNull(selector: (Short) -> Double): Double? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.min(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun BooleanArray.minOfOrNull(selector: (Boolean) -> Double): Double? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Double = (var1.invoke(var0[0]) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.min(var4, (var1.invoke(var0[var6]) as java.lang.Number).doubleValue());
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun ByteArray.minOfOrNull(selector: (Byte) -> Float): Float? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.min(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun CharArray.minOfOrNull(selector: (Char) -> Float): Float? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.min(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun DoubleArray.minOfOrNull(selector: (Double) -> Float): Float? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.min(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun FloatArray.minOfOrNull(selector: (Float) -> Float): Float? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.min(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun IntArray.minOfOrNull(selector: (Int) -> Float): Float? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.min(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun LongArray.minOfOrNull(selector: (Long) -> Float): Float? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.min(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <T> Array<out T>.minOfOrNull(selector: (T) -> Float): Float? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.min(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun ShortArray.minOfOrNull(selector: (Short) -> Float): Float? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.min(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun BooleanArray.minOfOrNull(selector: (Boolean) -> Float): Float? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Float = (var1.invoke(var0[0]) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.min(var3, (var1.invoke(var0[var4]) as java.lang.Number).floatValue());
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun <R> ByteArray.minOfWith(comparator: Comparator<in R>, selector: (Byte) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) > 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> CharArray.minOfWith(comparator: Comparator<in R>, selector: (Char) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) > 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> DoubleArray.minOfWith(comparator: Comparator<in R>, selector: (Double) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) > 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> FloatArray.minOfWith(comparator: Comparator<in R>, selector: (Float) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) > 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> IntArray.minOfWith(comparator: Comparator<in R>, selector: (Int) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) > 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> LongArray.minOfWith(comparator: Comparator<in R>, selector: (Long) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) > 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <T, R> Array<out T>.minOfWith(comparator: Comparator<in R>, selector: (T) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) > 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> ShortArray.minOfWith(comparator: Comparator<in R>, selector: (Short) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) > 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> BooleanArray.minOfWith(comparator: Comparator<in R>, selector: (Boolean) -> R): R {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) > 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> ByteArray.minOfWithOrNull(comparator: Comparator<in R>, selector: (Byte) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) > 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> CharArray.minOfWithOrNull(comparator: Comparator<in R>, selector: (Char) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) > 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> DoubleArray.minOfWithOrNull(comparator: Comparator<in R>, selector: (Double) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) > 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> FloatArray.minOfWithOrNull(comparator: Comparator<in R>, selector: (Float) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) > 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> IntArray.minOfWithOrNull(comparator: Comparator<in R>, selector: (Int) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) > 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> LongArray.minOfWithOrNull(comparator: Comparator<in R>, selector: (Long) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) > 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <T, R> Array<out T>.minOfWithOrNull(comparator: Comparator<in R>, selector: (T) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) > 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> ShortArray.minOfWithOrNull(comparator: Comparator<in R>, selector: (Short) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) > 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public inline fun <R> BooleanArray.minOfWithOrNull(comparator: Comparator<in R>, selector: (Boolean) -> R): R? {
      if (var0.length == 0) {
         return null;
      } else {
         var var5: Any = var2.invoke(var0[0]);
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(var0[var3]);
               var5 = var6;
               if (var1.compare(var6, var7) > 0) {
                  var5 = var7;
               }

               var6 = var5;
               if (var3 == var4) {
                  break;
               }

               var3++;
               var6 = var5;
            }
         }

         return (R)var6;
      }
   }

   @JvmStatic
   public fun ByteArray.minOrNull(): Byte? {
      if (var0.length == 0) {
         return null;
      } else {
         var var1: Byte = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Byte = var1;
         if (1 <= var5) {
            var2 = var1;

            while (true) {
               val var3: Byte = var0[var4];
               var1 = var2;
               if (var2 > var3) {
                  var1 = var3;
               }

               var2 = var1;
               if (var4 == var5) {
                  break;
               }

               var4++;
               var2 = var1;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public fun CharArray.minOrNull(): Char? {
      if (var0.length == 0) {
         return null;
      } else {
         var var1: Char = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Char = var1;
         if (1 <= var5) {
            var2 = var1;

            while (true) {
               val var3: Char = var0[var4];
               var1 = var2;
               if (Intrinsics.compare((int)var2, (int)var3) > 0) {
                  var1 = var3;
               }

               var2 = var1;
               if (var4 == var5) {
                  break;
               }

               var4++;
               var2 = var1;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public fun <T : Comparable<T>> Array<out T>.minOrNull(): T? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: java.lang.Comparable = var0[0];
         val var2: Int = ArraysKt.getLastIndex(var0);
         var var1: Int = 1;
         var var4: java.lang.Comparable = var3;
         if (1 <= var2) {
            var4 = var3;

            while (true) {
               val var5: java.lang.Comparable = var0[var1];
               var3 = var4;
               if (var4.compareTo(var5) > 0) {
                  var3 = var5;
               }

               var4 = var3;
               if (var1 == var2) {
                  break;
               }

               var1++;
               var4 = var3;
            }
         }

         return (T)var4;
      }
   }

   @JvmStatic
   public fun DoubleArray.minOrNull(): Double? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Double = var0[0];
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var1: Double = var3;
         if (1 <= var6) {
            while (true) {
               var3 = Math.min(var3, var0[var5]);
               var1 = var3;
               if (var5 == var6) {
                  break;
               }

               var5++;
            }
         }

         return var1;
      }
   }

   @JvmStatic
   public fun Array<out Double>.minOrNull(): Double? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Double = var0[0];
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var1: Double = var3;
         if (1 <= var6) {
            while (true) {
               var3 = Math.min(var3, var0[var5]);
               var1 = var3;
               if (var5 == var6) {
                  break;
               }

               var5++;
            }
         }

         return var1;
      }
   }

   @JvmStatic
   public fun FloatArray.minOrNull(): Float? {
      if (var0.length == 0) {
         return null;
      } else {
         var var2: Float = var0[0];
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var1: Float = var2;
         if (1 <= var4) {
            while (true) {
               var2 = Math.min(var2, var0[var3]);
               var1 = var2;
               if (var3 == var4) {
                  break;
               }

               var3++;
            }
         }

         return var1;
      }
   }

   @JvmStatic
   public fun Array<out Float>.minOrNull(): Float? {
      if (var0.length == 0) {
         return null;
      } else {
         var var2: Float = var0[0];
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var1: Float = var2;
         if (1 <= var4) {
            while (true) {
               var2 = Math.min(var2, var0[var3]);
               var1 = var2;
               if (var3 == var4) {
                  break;
               }

               var3++;
            }
         }

         return var1;
      }
   }

   @JvmStatic
   public fun IntArray.minOrNull(): Int? {
      if (var0.length == 0) {
         return null;
      } else {
         var var1: Int = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var3: Int = var1;
         if (1 <= var5) {
            var3 = var1;

            while (true) {
               val var4: Int = var0[var2];
               var1 = var3;
               if (var3 > var4) {
                  var1 = var4;
               }

               var3 = var1;
               if (var2 == var5) {
                  break;
               }

               var2++;
               var3 = var1;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun LongArray.minOrNull(): Long? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Long = var0[0];
         val var2: Int = ArraysKt.getLastIndex(var0);
         var var1: Int = 1;
         var var5: Long = var3;
         if (1 <= var2) {
            var5 = var3;

            while (true) {
               val var7: Long = var0[var1];
               var3 = var5;
               if (var5 > var7) {
                  var3 = var7;
               }

               var5 = var3;
               if (var1 == var2) {
                  break;
               }

               var1++;
               var5 = var3;
            }
         }

         return var5;
      }
   }

   @JvmStatic
   public fun ShortArray.minOrNull(): Short? {
      if (var0.length == 0) {
         return null;
      } else {
         var var1: Short = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Short = var1;
         if (1 <= var5) {
            var2 = var1;

            while (true) {
               val var3: Short = var0[var4];
               var1 = var2;
               if (var2 > var3) {
                  var1 = var3;
               }

               var2 = var1;
               if (var4 == var5) {
                  break;
               }

               var4++;
               var2 = var1;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public fun ByteArray.min(): Byte {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var1: Byte = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Byte = var1;
         if (1 <= var5) {
            var2 = var1;

            while (true) {
               val var3: Byte = var0[var4];
               var1 = var2;
               if (var2 > var3) {
                  var1 = var3;
               }

               var2 = var1;
               if (var4 == var5) {
                  break;
               }

               var4++;
               var2 = var1;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public fun CharArray.min(): Char {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var1: Char = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Char = var1;
         if (1 <= var5) {
            var2 = var1;

            while (true) {
               val var3: Char = var0[var4];
               var1 = var2;
               if (Intrinsics.compare((int)var2, (int)var3) > 0) {
                  var1 = var3;
               }

               var2 = var1;
               if (var4 == var5) {
                  break;
               }

               var4++;
               var2 = var1;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public fun DoubleArray.min(): Double {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var3: Double = var0[0];
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var1: Double = var3;
         if (1 <= var6) {
            while (true) {
               var3 = Math.min(var3, var0[var5]);
               var1 = var3;
               if (var5 == var6) {
                  break;
               }

               var5++;
            }
         }

         return var1;
      }
   }

   @JvmStatic
   public fun Array<out Double>.min(): Double {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var3: Double = var0[0];
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var1: Double = var3;
         if (1 <= var6) {
            while (true) {
               var3 = Math.min(var3, var0[var5]);
               var1 = var3;
               if (var5 == var6) {
                  break;
               }

               var5++;
            }
         }

         return var1;
      }
   }

   @JvmStatic
   public fun FloatArray.min(): Float {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var2: Float = var0[0];
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var1: Float = var2;
         if (1 <= var4) {
            while (true) {
               var2 = Math.min(var2, var0[var3]);
               var1 = var2;
               if (var3 == var4) {
                  break;
               }

               var3++;
            }
         }

         return var1;
      }
   }

   @JvmStatic
   public fun Array<out Float>.min(): Float {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var2: Float = var0[0];
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var1: Float = var2;
         if (1 <= var4) {
            while (true) {
               var2 = Math.min(var2, var0[var3]);
               var1 = var2;
               if (var3 == var4) {
                  break;
               }

               var3++;
            }
         }

         return var1;
      }
   }

   @JvmStatic
   public fun IntArray.min(): Int {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var1: Int = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var3: Int = var1;
         if (1 <= var5) {
            var3 = var1;

            while (true) {
               val var4: Int = var0[var2];
               var1 = var3;
               if (var3 > var4) {
                  var1 = var4;
               }

               var3 = var1;
               if (var2 == var5) {
                  break;
               }

               var2++;
               var3 = var1;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun LongArray.min(): Long {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var3: Long = var0[0];
         val var2: Int = ArraysKt.getLastIndex(var0);
         var var1: Int = 1;
         var var5: Long = var3;
         if (1 <= var2) {
            var5 = var3;

            while (true) {
               val var7: Long = var0[var1];
               var3 = var5;
               if (var5 > var7) {
                  var3 = var7;
               }

               var5 = var3;
               if (var1 == var2) {
                  break;
               }

               var1++;
               var5 = var3;
            }
         }

         return var5;
      }
   }

   @JvmStatic
   public fun <T : Comparable<T>> Array<out T>.min(): T {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var3: java.lang.Comparable = var0[0];
         val var2: Int = ArraysKt.getLastIndex(var0);
         var var1: Int = 1;
         var var4: java.lang.Comparable = var3;
         if (1 <= var2) {
            var4 = var3;

            while (true) {
               val var5: java.lang.Comparable = var0[var1];
               var3 = var4;
               if (var4.compareTo(var5) > 0) {
                  var3 = var5;
               }

               var4 = var3;
               if (var1 == var2) {
                  break;
               }

               var1++;
               var4 = var3;
            }
         }

         return (T)var4;
      }
   }

   @JvmStatic
   public fun ShortArray.min(): Short {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var1: Short = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Short = var1;
         if (1 <= var5) {
            var2 = var1;

            while (true) {
               val var3: Short = var0[var4];
               var1 = var2;
               if (var2 > var3) {
                  var1 = var3;
               }

               var2 = var1;
               if (var4 == var5) {
                  break;
               }

               var4++;
               var2 = var1;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public fun BooleanArray.minWithOrNull(comparator: Comparator<in Boolean>): Boolean? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Boolean = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: Boolean = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: Boolean = var0[var2];
               var4 = var5;
               if (var1.compare(var5, var6) > 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return var5;
      }
   }

   @JvmStatic
   public fun ByteArray.minWithOrNull(comparator: Comparator<in Byte>): Byte? {
      if (var0.length == 0) {
         return null;
      } else {
         var var2: Byte = var0[0];
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var3: Byte = var2;
         if (1 <= var6) {
            var3 = var2;

            while (true) {
               val var4: Byte = var0[var5];
               var2 = var3;
               if (var1.compare(var3, var4) > 0) {
                  var2 = var4;
               }

               var3 = var2;
               if (var5 == var6) {
                  break;
               }

               var5++;
               var3 = var2;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun CharArray.minWithOrNull(comparator: Comparator<in Char>): Char? {
      if (var0.length == 0) {
         return null;
      } else {
         var var2: Char = var0[0];
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var3: Char = var2;
         if (1 <= var6) {
            var3 = var2;

            while (true) {
               val var4: Char = var0[var5];
               var2 = var3;
               if (var1.compare(var3, var4) > 0) {
                  var2 = var4;
               }

               var3 = var2;
               if (var5 == var6) {
                  break;
               }

               var5++;
               var3 = var2;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun DoubleArray.minWithOrNull(comparator: Comparator<in Double>): Double? {
      if (var0.length == 0) {
         return null;
      } else {
         var var2: Double = var0[0];
         val var9: Int = ArraysKt.getLastIndex(var0);
         var var8: Int = 1;
         var var4: Double = var2;
         if (1 <= var9) {
            var4 = var2;

            while (true) {
               val var6: Double = var0[var8];
               var2 = var4;
               if (var1.compare(var4, var6) > 0) {
                  var2 = var6;
               }

               var4 = var2;
               if (var8 == var9) {
                  break;
               }

               var8++;
               var4 = var2;
            }
         }

         return var4;
      }
   }

   @JvmStatic
   public fun FloatArray.minWithOrNull(comparator: Comparator<in Float>): Float? {
      if (var0.length == 0) {
         return null;
      } else {
         var var2: Float = var0[0];
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var3: Float = var2;
         if (1 <= var6) {
            var3 = var2;

            while (true) {
               val var4: Float = var0[var5];
               var2 = var3;
               if (var1.compare(var3, var4) > 0) {
                  var2 = var4;
               }

               var3 = var2;
               if (var5 == var6) {
                  break;
               }

               var5++;
               var3 = var2;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun IntArray.minWithOrNull(comparator: Comparator<in Int>): Int? {
      if (var0.length == 0) {
         return null;
      } else {
         var var2: Int = var0[0];
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var4: Int = var2;
         if (1 <= var6) {
            var4 = var2;

            while (true) {
               val var5: Int = var0[var3];
               var2 = var4;
               if (var1.compare(var4, var5) > 0) {
                  var2 = var5;
               }

               var4 = var2;
               if (var3 == var6) {
                  break;
               }

               var3++;
               var4 = var2;
            }
         }

         return var4;
      }
   }

   @JvmStatic
   public fun LongArray.minWithOrNull(comparator: Comparator<in Long>): Long? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Long = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var6: Long = var4;
         if (1 <= var3) {
            var6 = var4;

            while (true) {
               val var8: Long = var0[var2];
               var4 = var6;
               if (var1.compare(var6, var8) > 0) {
                  var4 = var8;
               }

               var6 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var6 = var4;
            }
         }

         return var6;
      }
   }

   @JvmStatic
   public fun <T> Array<out T>.minWithOrNull(comparator: Comparator<in T>): T? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Any = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: Any = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: Any = var0[var2];
               var4 = var5;
               if (var1.compare(var5, var6) > 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (T)var5;
      }
   }

   @JvmStatic
   public fun ShortArray.minWithOrNull(comparator: Comparator<in Short>): Short? {
      if (var0.length == 0) {
         return null;
      } else {
         var var2: Short = var0[0];
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var3: Short = var2;
         if (1 <= var6) {
            var3 = var2;

            while (true) {
               val var4: Short = var0[var5];
               var2 = var3;
               if (var1.compare(var3, var4) > 0) {
                  var2 = var4;
               }

               var3 = var2;
               if (var5 == var6) {
                  break;
               }

               var5++;
               var3 = var2;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun ByteArray.minWith(comparator: Comparator<in Byte>): Byte {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var2: Byte = var0[0];
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var3: Byte = var2;
         if (1 <= var6) {
            var3 = var2;

            while (true) {
               val var4: Byte = var0[var5];
               var2 = var3;
               if (var1.compare(var3, var4) > 0) {
                  var2 = var4;
               }

               var3 = var2;
               if (var5 == var6) {
                  break;
               }

               var5++;
               var3 = var2;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun CharArray.minWith(comparator: Comparator<in Char>): Char {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var2: Char = var0[0];
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var3: Char = var2;
         if (1 <= var6) {
            var3 = var2;

            while (true) {
               val var4: Char = var0[var5];
               var2 = var3;
               if (var1.compare(var3, var4) > 0) {
                  var2 = var4;
               }

               var3 = var2;
               if (var5 == var6) {
                  break;
               }

               var5++;
               var3 = var2;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun DoubleArray.minWith(comparator: Comparator<in Double>): Double {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var2: Double = var0[0];
         val var9: Int = ArraysKt.getLastIndex(var0);
         var var8: Int = 1;
         var var4: Double = var2;
         if (1 <= var9) {
            var4 = var2;

            while (true) {
               val var6: Double = var0[var8];
               var2 = var4;
               if (var1.compare(var4, var6) > 0) {
                  var2 = var6;
               }

               var4 = var2;
               if (var8 == var9) {
                  break;
               }

               var8++;
               var4 = var2;
            }
         }

         return var4;
      }
   }

   @JvmStatic
   public fun FloatArray.minWith(comparator: Comparator<in Float>): Float {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var2: Float = var0[0];
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var3: Float = var2;
         if (1 <= var6) {
            var3 = var2;

            while (true) {
               val var4: Float = var0[var5];
               var2 = var3;
               if (var1.compare(var3, var4) > 0) {
                  var2 = var4;
               }

               var3 = var2;
               if (var5 == var6) {
                  break;
               }

               var5++;
               var3 = var2;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun IntArray.minWith(comparator: Comparator<in Int>): Int {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var2: Int = var0[0];
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var4: Int = var2;
         if (1 <= var6) {
            var4 = var2;

            while (true) {
               val var5: Int = var0[var3];
               var2 = var4;
               if (var1.compare(var4, var5) > 0) {
                  var2 = var5;
               }

               var4 = var2;
               if (var3 == var6) {
                  break;
               }

               var3++;
               var4 = var2;
            }
         }

         return var4;
      }
   }

   @JvmStatic
   public fun LongArray.minWith(comparator: Comparator<in Long>): Long {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: Long = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var6: Long = var4;
         if (1 <= var3) {
            var6 = var4;

            while (true) {
               val var8: Long = var0[var2];
               var4 = var6;
               if (var1.compare(var6, var8) > 0) {
                  var4 = var8;
               }

               var6 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var6 = var4;
            }
         }

         return var6;
      }
   }

   @JvmStatic
   public fun <T> Array<out T>.minWith(comparator: Comparator<in T>): T {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: Any = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: Any = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: Any = var0[var2];
               var4 = var5;
               if (var1.compare(var5, var6) > 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return (T)var5;
      }
   }

   @JvmStatic
   public fun ShortArray.minWith(comparator: Comparator<in Short>): Short {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var2: Short = var0[0];
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var3: Short = var2;
         if (1 <= var6) {
            var3 = var2;

            while (true) {
               val var4: Short = var0[var5];
               var2 = var3;
               if (var1.compare(var3, var4) > 0) {
                  var2 = var4;
               }

               var3 = var2;
               if (var5 == var6) {
                  break;
               }

               var5++;
               var3 = var2;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun BooleanArray.minWith(comparator: Comparator<in Boolean>): Boolean {
      if (var0.length == 0) {
         throw new NoSuchElementException();
      } else {
         var var4: Boolean = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: Boolean = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: Boolean = var0[var2];
               var4 = var5;
               if (var1.compare(var5, var6) > 0) {
                  var4 = var6;
               }

               var5 = var4;
               if (var2 == var3) {
                  break;
               }

               var2++;
               var5 = var4;
            }
         }

         return var5;
      }
   }

   @JvmStatic
   public fun ByteArray.none(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   public inline fun ByteArray.none(predicate: (Byte) -> Boolean): Boolean {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public fun CharArray.none(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   public inline fun CharArray.none(predicate: (Char) -> Boolean): Boolean {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public fun DoubleArray.none(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   public inline fun DoubleArray.none(predicate: (Double) -> Boolean): Boolean {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public fun FloatArray.none(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   public inline fun FloatArray.none(predicate: (Float) -> Boolean): Boolean {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public fun IntArray.none(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   public inline fun IntArray.none(predicate: (Int) -> Boolean): Boolean {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public fun LongArray.none(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   public inline fun LongArray.none(predicate: (Long) -> Boolean): Boolean {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public fun <T> Array<out T>.none(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.none(predicate: (T) -> Boolean): Boolean {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public fun ShortArray.none(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   public inline fun ShortArray.none(predicate: (Short) -> Boolean): Boolean {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public fun BooleanArray.none(): Boolean {
      val var1: Boolean;
      if (var0.length == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   public inline fun BooleanArray.none(predicate: (Boolean) -> Boolean): Boolean {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public inline fun ByteArray.onEach(action: (Byte) -> Unit): ByteArray {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var1.invoke(var0[var2]);
      }

      return var0;
   }

   @JvmStatic
   public inline fun CharArray.onEach(action: (Char) -> Unit): CharArray {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var1.invoke(var0[var2]);
      }

      return var0;
   }

   @JvmStatic
   public inline fun DoubleArray.onEach(action: (Double) -> Unit): DoubleArray {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var1.invoke(var0[var2]);
      }

      return var0;
   }

   @JvmStatic
   public inline fun FloatArray.onEach(action: (Float) -> Unit): FloatArray {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var1.invoke(var0[var2]);
      }

      return var0;
   }

   @JvmStatic
   public inline fun IntArray.onEach(action: (Int) -> Unit): IntArray {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var1.invoke(var0[var2]);
      }

      return var0;
   }

   @JvmStatic
   public inline fun LongArray.onEach(action: (Long) -> Unit): LongArray {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var1.invoke(var0[var2]);
      }

      return var0;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.onEach(action: (T) -> Unit): Array<out T> {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var1.invoke(var0[var2]);
      }

      return (T[])var0;
   }

   @JvmStatic
   public inline fun ShortArray.onEach(action: (Short) -> Unit): ShortArray {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var1.invoke(var0[var2]);
      }

      return var0;
   }

   @JvmStatic
   public inline fun BooleanArray.onEach(action: (Boolean) -> Unit): BooleanArray {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var1.invoke(var0[var2]);
      }

      return var0;
   }

   @JvmStatic
   public inline fun ByteArray.onEachIndexed(action: (Int, Byte) -> Unit): ByteArray {
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var1.invoke(var2, var0[var3]);
         var3++;
      }

      return var0;
   }

   @JvmStatic
   public inline fun CharArray.onEachIndexed(action: (Int, Char) -> Unit): CharArray {
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var1.invoke(var2, var0[var3]);
         var3++;
      }

      return var0;
   }

   @JvmStatic
   public inline fun DoubleArray.onEachIndexed(action: (Int, Double) -> Unit): DoubleArray {
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var1.invoke(var2, var0[var3]);
         var3++;
      }

      return var0;
   }

   @JvmStatic
   public inline fun FloatArray.onEachIndexed(action: (Int, Float) -> Unit): FloatArray {
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var1.invoke(var2, var0[var3]);
         var3++;
      }

      return var0;
   }

   @JvmStatic
   public inline fun IntArray.onEachIndexed(action: (Int, Int) -> Unit): IntArray {
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var1.invoke(var2, var0[var3]);
         var3++;
      }

      return var0;
   }

   @JvmStatic
   public inline fun LongArray.onEachIndexed(action: (Int, Long) -> Unit): LongArray {
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var1.invoke(var2, var0[var3]);
         var3++;
      }

      return var0;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.onEachIndexed(action: (Int, T) -> Unit): Array<out T> {
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var1.invoke(var2, var0[var3]);
         var3++;
      }

      return (T[])var0;
   }

   @JvmStatic
   public inline fun ShortArray.onEachIndexed(action: (Int, Short) -> Unit): ShortArray {
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var1.invoke(var2, var0[var3]);
         var3++;
      }

      return var0;
   }

   @JvmStatic
   public inline fun BooleanArray.onEachIndexed(action: (Int, Boolean) -> Unit): BooleanArray {
      val var4: Int = var0.length;
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var1.invoke(var2, var0[var3]);
         var3++;
      }

      return var0;
   }

   @JvmStatic
   public inline fun ByteArray.partition(predicate: (Byte) -> Boolean): Pair<List<Byte>, List<Byte>> {
      val var6: ArrayList = new ArrayList();
      val var5: ArrayList = new ArrayList();
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Byte = var0[var3];
         if (var1.invoke(var0[var3]) as java.lang.Boolean) {
            var6.add(var2);
         } else {
            var5.add(var2);
         }
      }

      return new Pair<>(var6, var5);
   }

   @JvmStatic
   public inline fun CharArray.partition(predicate: (Char) -> Boolean): Pair<List<Char>, List<Char>> {
      val var5: ArrayList = new ArrayList();
      val var6: ArrayList = new ArrayList();
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Char = var0[var3];
         if (var1.invoke(var0[var3]) as java.lang.Boolean) {
            var5.add(var2);
         } else {
            var6.add(var2);
         }
      }

      return new Pair<>(var5, var6);
   }

   @JvmStatic
   public inline fun DoubleArray.partition(predicate: (Double) -> Boolean): Pair<List<Double>, List<Double>> {
      val var7: ArrayList = new ArrayList();
      val var6: ArrayList = new ArrayList();
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         val var2: Double = var0[var4];
         if (var1.invoke(var0[var4]) as java.lang.Boolean) {
            var7.add(var2);
         } else {
            var6.add(var2);
         }
      }

      return new Pair<>(var7, var6);
   }

   @JvmStatic
   public inline fun FloatArray.partition(predicate: (Float) -> Boolean): Pair<List<Float>, List<Float>> {
      val var5: ArrayList = new ArrayList();
      val var6: ArrayList = new ArrayList();
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Float = var0[var3];
         if (var1.invoke(var0[var3]) as java.lang.Boolean) {
            var5.add(var2);
         } else {
            var6.add(var2);
         }
      }

      return new Pair<>(var5, var6);
   }

   @JvmStatic
   public inline fun IntArray.partition(predicate: (Int) -> Boolean): Pair<List<Int>, List<Int>> {
      val var5: ArrayList = new ArrayList();
      val var6: ArrayList = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Int = var0[var2];
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            var5.add(var4);
         } else {
            var6.add(var4);
         }
      }

      return new Pair<>(var5, var6);
   }

   @JvmStatic
   public inline fun LongArray.partition(predicate: (Long) -> Boolean): Pair<List<Long>, List<Long>> {
      val var7: ArrayList = new ArrayList();
      val var6: ArrayList = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Long = var0[var2];
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            var7.add(var4);
         } else {
            var6.add(var4);
         }
      }

      return new Pair<>(var7, var6);
   }

   @JvmStatic
   public inline fun <T> Array<out T>.partition(predicate: (T) -> Boolean): Pair<List<T>, List<T>> {
      val var5: ArrayList = new ArrayList();
      val var6: ArrayList = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Any = var0[var2];
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            var5.add(var4);
         } else {
            var6.add(var4);
         }
      }

      return new Pair<>(var5, var6);
   }

   @JvmStatic
   public inline fun ShortArray.partition(predicate: (Short) -> Boolean): Pair<List<Short>, List<Short>> {
      val var5: ArrayList = new ArrayList();
      val var6: ArrayList = new ArrayList();
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Short = var0[var3];
         if (var1.invoke(var0[var3]) as java.lang.Boolean) {
            var5.add(var2);
         } else {
            var6.add(var2);
         }
      }

      return new Pair<>(var5, var6);
   }

   @JvmStatic
   public inline fun BooleanArray.partition(predicate: (Boolean) -> Boolean): Pair<List<Boolean>, List<Boolean>> {
      val var5: ArrayList = new ArrayList();
      val var6: ArrayList = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Boolean = var0[var2];
         if (var1.invoke(var0[var2]) as java.lang.Boolean) {
            var5.add(var4);
         } else {
            var6.add(var4);
         }
      }

      return new Pair<>(var5, var6);
   }

   @JvmStatic
   public inline fun ByteArray.random(): Byte {
      return ArraysKt.random(var0, Random.Default);
   }

   @JvmStatic
   public fun ByteArray.random(random: Random): Byte {
      if (var0.length != 0) {
         return var0[var1.nextInt(var0.length)];
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun CharArray.random(): Char {
      return ArraysKt.random(var0, Random.Default);
   }

   @JvmStatic
   public fun CharArray.random(random: Random): Char {
      if (var0.length != 0) {
         return var0[var1.nextInt(var0.length)];
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun DoubleArray.random(): Double {
      return ArraysKt.random(var0, Random.Default);
   }

   @JvmStatic
   public fun DoubleArray.random(random: Random): Double {
      if (var0.length != 0) {
         return var0[var1.nextInt(var0.length)];
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun FloatArray.random(): Float {
      return ArraysKt.random(var0, Random.Default);
   }

   @JvmStatic
   public fun FloatArray.random(random: Random): Float {
      if (var0.length != 0) {
         return var0[var1.nextInt(var0.length)];
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun IntArray.random(): Int {
      return ArraysKt.random(var0, Random.Default);
   }

   @JvmStatic
   public fun IntArray.random(random: Random): Int {
      if (var0.length != 0) {
         return var0[var1.nextInt(var0.length)];
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun LongArray.random(): Long {
      return ArraysKt.random(var0, Random.Default);
   }

   @JvmStatic
   public fun LongArray.random(random: Random): Long {
      if (var0.length != 0) {
         return var0[var1.nextInt(var0.length)];
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun <T> Array<out T>.random(): T {
      return (T)ArraysKt.random(var0, Random.Default);
   }

   @JvmStatic
   public fun <T> Array<out T>.random(random: Random): T {
      if (var0.length != 0) {
         return (T)var0[var1.nextInt(var0.length)];
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun ShortArray.random(): Short {
      return ArraysKt.random(var0, Random.Default);
   }

   @JvmStatic
   public fun ShortArray.random(random: Random): Short {
      if (var0.length != 0) {
         return var0[var1.nextInt(var0.length)];
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun BooleanArray.random(): Boolean {
      return ArraysKt.random(var0, Random.Default);
   }

   @JvmStatic
   public fun BooleanArray.random(random: Random): Boolean {
      if (var0.length != 0) {
         return var0[var1.nextInt(var0.length)];
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun BooleanArray.randomOrNull(): Boolean? {
      return ArraysKt.randomOrNull(var0, Random.Default);
   }

   @JvmStatic
   public fun BooleanArray.randomOrNull(random: Random): Boolean? {
      return if (var0.length == 0) null else var0[var1.nextInt(var0.length)];
   }

   @JvmStatic
   public inline fun ByteArray.randomOrNull(): Byte? {
      return ArraysKt.randomOrNull(var0, Random.Default);
   }

   @JvmStatic
   public fun ByteArray.randomOrNull(random: Random): Byte? {
      return if (var0.length == 0) null else var0[var1.nextInt(var0.length)];
   }

   @JvmStatic
   public inline fun CharArray.randomOrNull(): Char? {
      return ArraysKt.randomOrNull(var0, Random.Default);
   }

   @JvmStatic
   public fun CharArray.randomOrNull(random: Random): Char? {
      return if (var0.length == 0) null else var0[var1.nextInt(var0.length)];
   }

   @JvmStatic
   public inline fun DoubleArray.randomOrNull(): Double? {
      return ArraysKt.randomOrNull(var0, Random.Default);
   }

   @JvmStatic
   public fun DoubleArray.randomOrNull(random: Random): Double? {
      return if (var0.length == 0) null else var0[var1.nextInt(var0.length)];
   }

   @JvmStatic
   public inline fun FloatArray.randomOrNull(): Float? {
      return ArraysKt.randomOrNull(var0, Random.Default);
   }

   @JvmStatic
   public fun FloatArray.randomOrNull(random: Random): Float? {
      return if (var0.length == 0) null else var0[var1.nextInt(var0.length)];
   }

   @JvmStatic
   public inline fun IntArray.randomOrNull(): Int? {
      return ArraysKt.randomOrNull(var0, Random.Default);
   }

   @JvmStatic
   public fun IntArray.randomOrNull(random: Random): Int? {
      return if (var0.length == 0) null else var0[var1.nextInt(var0.length)];
   }

   @JvmStatic
   public inline fun LongArray.randomOrNull(): Long? {
      return ArraysKt.randomOrNull(var0, Random.Default);
   }

   @JvmStatic
   public fun LongArray.randomOrNull(random: Random): Long? {
      return if (var0.length == 0) null else var0[var1.nextInt(var0.length)];
   }

   @JvmStatic
   public inline fun <T> Array<out T>.randomOrNull(): T? {
      return (T)ArraysKt.randomOrNull(var0, Random.Default);
   }

   @JvmStatic
   public fun <T> Array<out T>.randomOrNull(random: Random): T? {
      return (T)(if (var0.length == 0) null else var0[var1.nextInt(var0.length)]);
   }

   @JvmStatic
   public inline fun ShortArray.randomOrNull(): Short? {
      return ArraysKt.randomOrNull(var0, Random.Default);
   }

   @JvmStatic
   public fun ShortArray.randomOrNull(random: Random): Short? {
      return if (var0.length == 0) null else var0[var1.nextInt(var0.length)];
   }

   @JvmStatic
   public inline fun ByteArray.reduce(operation: (Byte, Byte) -> Byte): Byte {
      if (var0.length == 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var3: Byte = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Byte = var3;
         if (1 <= var5) {
            while (true) {
               var3 = (var1.invoke(var3, var0[var4]) as java.lang.Number).byteValue();
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun CharArray.reduce(operation: (Char, Char) -> Char): Char {
      if (var0.length == 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var3: Char = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Char = var3;
         if (1 <= var5) {
            while (true) {
               var3 = var1.invoke(var3, var0[var4]) as Character;
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun DoubleArray.reduce(operation: (Double, Double) -> Double): Double {
      if (var0.length == 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var4: Double = var0[0];
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = (var1.invoke(var4, var0[var6]) as java.lang.Number).doubleValue();
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun FloatArray.reduce(operation: (Float, Float) -> Float): Float {
      if (var0.length == 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var3: Float = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = (var1.invoke(var3, var0[var4]) as java.lang.Number).floatValue();
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun IntArray.reduce(operation: (Int, Int) -> Int): Int {
      if (var0.length == 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var4: Int = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var2: Int = var4;
         if (1 <= var5) {
            while (true) {
               var4 = (var1.invoke(var4, var0[var3]) as java.lang.Number).intValue();
               var2 = var4;
               if (var3 == var5) {
                  break;
               }

               var3++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun LongArray.reduce(operation: (Long, Long) -> Long): Long {
      if (var0.length == 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var6: Long = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var4: Long = var6;
         if (1 <= var3) {
            while (true) {
               var6 = (var1.invoke(var6, var0[var2]) as java.lang.Number).longValue();
               var4 = var6;
               if (var2 == var3) {
                  break;
               }

               var2++;
            }
         }

         return var4;
      }
   }

   @JvmStatic
   public inline fun <S, T : S> Array<out T>.reduce(operation: (S, T) -> S): S {
      if (var0.length == 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var5: Any = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var4: Any = var5;
         if (1 <= var3) {
            while (true) {
               var5 = var1.invoke(var5, var0[var2]);
               var4 = var5;
               if (var2 == var3) {
                  break;
               }

               var2++;
            }
         }

         return (S)var4;
      }
   }

   @JvmStatic
   public inline fun ShortArray.reduce(operation: (Short, Short) -> Short): Short {
      if (var0.length == 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var3: Short = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Short = var3;
         if (1 <= var5) {
            while (true) {
               var3 = (var1.invoke(var3, var0[var4]) as java.lang.Number).shortValue();
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun BooleanArray.reduce(operation: (Boolean, Boolean) -> Boolean): Boolean {
      if (var0.length == 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var5: Boolean = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var4: Boolean = var5;
         if (1 <= var3) {
            while (true) {
               var5 = var1.invoke(var5, var0[var2]) as java.lang.Boolean;
               var4 = var5;
               if (var2 == var3) {
                  break;
               }

               var2++;
            }
         }

         return var4;
      }
   }

   @JvmStatic
   public inline fun ByteArray.reduceIndexed(operation: (Int, Byte, Byte) -> Byte): Byte {
      if (var0.length == 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var3: Byte = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Byte = var3;
         if (1 <= var5) {
            while (true) {
               var3 = (var1.invoke(var4, var3, var0[var4]) as java.lang.Number).byteValue();
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun CharArray.reduceIndexed(operation: (Int, Char, Char) -> Char): Char {
      if (var0.length == 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var3: Char = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Char = var3;
         if (1 <= var5) {
            while (true) {
               var3 = var1.invoke(var4, var3, var0[var4]) as Character;
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun DoubleArray.reduceIndexed(operation: (Int, Double, Double) -> Double): Double {
      if (var0.length == 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var4: Double = var0[0];
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = (var1.invoke(var6, var4, var0[var6]) as java.lang.Number).doubleValue();
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun FloatArray.reduceIndexed(operation: (Int, Float, Float) -> Float): Float {
      if (var0.length == 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var3: Float = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = (var1.invoke(var4, var3, var0[var4]) as java.lang.Number).floatValue();
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun IntArray.reduceIndexed(operation: (Int, Int, Int) -> Int): Int {
      if (var0.length == 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var4: Int = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var2: Int = var4;
         if (1 <= var5) {
            while (true) {
               var4 = (var1.invoke(var3, var4, var0[var3]) as java.lang.Number).intValue();
               var2 = var4;
               if (var3 == var5) {
                  break;
               }

               var3++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun LongArray.reduceIndexed(operation: (Int, Long, Long) -> Long): Long {
      if (var0.length == 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var6: Long = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var4: Long = var6;
         if (1 <= var3) {
            while (true) {
               var6 = (var1.invoke(var2, var6, var0[var2]) as java.lang.Number).longValue();
               var4 = var6;
               if (var2 == var3) {
                  break;
               }

               var2++;
            }
         }

         return var4;
      }
   }

   @JvmStatic
   public inline fun <S, T : S> Array<out T>.reduceIndexed(operation: (Int, S, T) -> S): S {
      if (var0.length == 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var5: Any = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var4: Any = var5;
         if (1 <= var3) {
            while (true) {
               var5 = var1.invoke(var2, var5, var0[var2]);
               var4 = var5;
               if (var2 == var3) {
                  break;
               }

               var2++;
            }
         }

         return (S)var4;
      }
   }

   @JvmStatic
   public inline fun ShortArray.reduceIndexed(operation: (Int, Short, Short) -> Short): Short {
      if (var0.length == 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var3: Short = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Short = var3;
         if (1 <= var5) {
            while (true) {
               var3 = (var1.invoke(var4, var3, var0[var4]) as java.lang.Number).shortValue();
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun BooleanArray.reduceIndexed(operation: (Int, Boolean, Boolean) -> Boolean): Boolean {
      if (var0.length == 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var5: Boolean = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var4: Boolean = var5;
         if (1 <= var3) {
            while (true) {
               var5 = var1.invoke(var2, var5, var0[var2]) as java.lang.Boolean;
               var4 = var5;
               if (var2 == var3) {
                  break;
               }

               var2++;
            }
         }

         return var4;
      }
   }

   @JvmStatic
   public inline fun BooleanArray.reduceIndexedOrNull(operation: (Int, Boolean, Boolean) -> Boolean): Boolean? {
      if (var0.length == 0) {
         return null;
      } else {
         var var5: Boolean = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var4: Boolean = var5;
         if (1 <= var3) {
            while (true) {
               var5 = var1.invoke(var2, var5, var0[var2]) as java.lang.Boolean;
               var4 = var5;
               if (var2 == var3) {
                  break;
               }

               var2++;
            }
         }

         return var4;
      }
   }

   @JvmStatic
   public inline fun ByteArray.reduceIndexedOrNull(operation: (Int, Byte, Byte) -> Byte): Byte? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Byte = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Byte = var3;
         if (1 <= var5) {
            while (true) {
               var3 = (var1.invoke(var4, var3, var0[var4]) as java.lang.Number).byteValue();
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun CharArray.reduceIndexedOrNull(operation: (Int, Char, Char) -> Char): Char? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Char = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Char = var3;
         if (1 <= var5) {
            while (true) {
               var3 = var1.invoke(var4, var3, var0[var4]) as Character;
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun DoubleArray.reduceIndexedOrNull(operation: (Int, Double, Double) -> Double): Double? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Double = var0[0];
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = (var1.invoke(var6, var4, var0[var6]) as java.lang.Number).doubleValue();
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun FloatArray.reduceIndexedOrNull(operation: (Int, Float, Float) -> Float): Float? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Float = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = (var1.invoke(var4, var3, var0[var4]) as java.lang.Number).floatValue();
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun IntArray.reduceIndexedOrNull(operation: (Int, Int, Int) -> Int): Int? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Int = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var3: Int = var4;
         if (1 <= var5) {
            while (true) {
               var4 = (var1.invoke(var2, var4, var0[var2]) as java.lang.Number).intValue();
               var3 = var4;
               if (var2 == var5) {
                  break;
               }

               var2++;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public inline fun LongArray.reduceIndexedOrNull(operation: (Int, Long, Long) -> Long): Long? {
      if (var0.length == 0) {
         return null;
      } else {
         var var6: Long = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var4: Long = var6;
         if (1 <= var3) {
            while (true) {
               var6 = (var1.invoke(var2, var6, var0[var2]) as java.lang.Number).longValue();
               var4 = var6;
               if (var2 == var3) {
                  break;
               }

               var2++;
            }
         }

         return var4;
      }
   }

   @JvmStatic
   public inline fun <S, T : S> Array<out T>.reduceIndexedOrNull(operation: (Int, S, T) -> S): S? {
      if (var0.length == 0) {
         return null;
      } else {
         var var5: Any = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var4: Any = var5;
         if (1 <= var3) {
            while (true) {
               var5 = var1.invoke(var2, var5, var0[var2]);
               var4 = var5;
               if (var2 == var3) {
                  break;
               }

               var2++;
            }
         }

         return (S)var4;
      }
   }

   @JvmStatic
   public inline fun ShortArray.reduceIndexedOrNull(operation: (Int, Short, Short) -> Short): Short? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Short = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Short = var3;
         if (1 <= var5) {
            while (true) {
               var3 = (var1.invoke(var4, var3, var0[var4]) as java.lang.Number).shortValue();
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun BooleanArray.reduceOrNull(operation: (Boolean, Boolean) -> Boolean): Boolean? {
      if (var0.length == 0) {
         return null;
      } else {
         var var5: Boolean = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var4: Boolean = var5;
         if (1 <= var3) {
            while (true) {
               var5 = var1.invoke(var5, var0[var2]) as java.lang.Boolean;
               var4 = var5;
               if (var2 == var3) {
                  break;
               }

               var2++;
            }
         }

         return var4;
      }
   }

   @JvmStatic
   public inline fun ByteArray.reduceOrNull(operation: (Byte, Byte) -> Byte): Byte? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Byte = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Byte = var3;
         if (1 <= var5) {
            while (true) {
               var3 = (var1.invoke(var3, var0[var4]) as java.lang.Number).byteValue();
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun CharArray.reduceOrNull(operation: (Char, Char) -> Char): Char? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Char = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Char = var3;
         if (1 <= var5) {
            while (true) {
               var3 = var1.invoke(var3, var0[var4]) as Character;
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun DoubleArray.reduceOrNull(operation: (Double, Double) -> Double): Double? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Double = var0[0];
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = (var1.invoke(var4, var0[var6]) as java.lang.Number).doubleValue();
               var2 = var4;
               if (var6 == var7) {
                  break;
               }

               var6++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun FloatArray.reduceOrNull(operation: (Float, Float) -> Float): Float? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Float = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = (var1.invoke(var3, var0[var4]) as java.lang.Number).floatValue();
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun IntArray.reduceOrNull(operation: (Int, Int) -> Int): Int? {
      if (var0.length == 0) {
         return null;
      } else {
         var var4: Int = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var3: Int = var4;
         if (1 <= var5) {
            while (true) {
               var4 = (var1.invoke(var4, var0[var2]) as java.lang.Number).intValue();
               var3 = var4;
               if (var2 == var5) {
                  break;
               }

               var2++;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public inline fun LongArray.reduceOrNull(operation: (Long, Long) -> Long): Long? {
      if (var0.length == 0) {
         return null;
      } else {
         var var6: Long = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var4: Long = var6;
         if (1 <= var3) {
            while (true) {
               var6 = (var1.invoke(var6, var0[var2]) as java.lang.Number).longValue();
               var4 = var6;
               if (var2 == var3) {
                  break;
               }

               var2++;
            }
         }

         return var4;
      }
   }

   @JvmStatic
   public inline fun <S, T : S> Array<out T>.reduceOrNull(operation: (S, T) -> S): S? {
      if (var0.length == 0) {
         return null;
      } else {
         var var5: Any = var0[0];
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var4: Any = var5;
         if (1 <= var3) {
            while (true) {
               var5 = var1.invoke(var5, var0[var2]);
               var4 = var5;
               if (var2 == var3) {
                  break;
               }

               var2++;
            }
         }

         return (S)var4;
      }
   }

   @JvmStatic
   public inline fun ShortArray.reduceOrNull(operation: (Short, Short) -> Short): Short? {
      if (var0.length == 0) {
         return null;
      } else {
         var var3: Short = var0[0];
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Short = var3;
         if (1 <= var5) {
            while (true) {
               var3 = (var1.invoke(var3, var0[var4]) as java.lang.Number).shortValue();
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun ByteArray.reduceRight(operation: (Byte, Byte) -> Byte): Byte {
      val var4: Int = ArraysKt.getLastIndex(var0);
      if (var4 < 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var3: Int = var4 - 1;

         var var2: Byte;
         for (var2 = var0[var4]; var3 >= 0; var3--) {
            var2 = (var1.invoke(var0[var3], var2) as java.lang.Number).byteValue();
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun CharArray.reduceRight(operation: (Char, Char) -> Char): Char {
      val var4: Int = ArraysKt.getLastIndex(var0);
      if (var4 < 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var3: Int = var4 - 1;

         var var2: Char;
         for (var2 = var0[var4]; var3 >= 0; var3--) {
            var2 = var1.invoke(var0[var3], var2) as Character;
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun DoubleArray.reduceRight(operation: (Double, Double) -> Double): Double {
      val var5: Int = ArraysKt.getLastIndex(var0);
      if (var5 < 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var4: Int = var5 - 1;

         var var2: Double;
         for (var2 = var0[var5]; var4 >= 0; var4--) {
            var2 = (var1.invoke(var0[var4], var2) as java.lang.Number).doubleValue();
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun FloatArray.reduceRight(operation: (Float, Float) -> Float): Float {
      val var4: Int = ArraysKt.getLastIndex(var0);
      if (var4 < 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var3: Int = var4 - 1;

         var var2: Float;
         for (var2 = var0[var4]; var3 >= 0; var3--) {
            var2 = (var1.invoke(var0[var3], var2) as java.lang.Number).floatValue();
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun IntArray.reduceRight(operation: (Int, Int) -> Int): Int {
      var var3: Int = ArraysKt.getLastIndex(var0);
      if (var3 < 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var2: Int = var3 - 1;

         for (var3 = var0[var3]; var2 >= 0; var2--) {
            var3 = (var1.invoke(var0[var2], var3) as java.lang.Number).intValue();
         }

         return var3;
      }
   }

   @JvmStatic
   public inline fun LongArray.reduceRight(operation: (Long, Long) -> Long): Long {
      val var3: Int = ArraysKt.getLastIndex(var0);
      if (var3 < 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var2: Int = var3 - 1;

         var var4: Long;
         for (var4 = var0[var3]; var2 >= 0; var2--) {
            var4 = (var1.invoke(var0[var2], var4) as java.lang.Number).longValue();
         }

         return var4;
      }
   }

   @JvmStatic
   public inline fun <S, T : S> Array<out T>.reduceRight(operation: (T, S) -> S): S {
      val var3: Int = ArraysKt.getLastIndex(var0);
      if (var3 < 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var2: Int = var3 - 1;

         var var4: Any;
         for (var4 = var0[var3]; var2 >= 0; var2--) {
            var4 = var1.invoke(var0[var2], var4);
         }

         return (S)var4;
      }
   }

   @JvmStatic
   public inline fun ShortArray.reduceRight(operation: (Short, Short) -> Short): Short {
      val var4: Int = ArraysKt.getLastIndex(var0);
      if (var4 < 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var3: Int = var4 - 1;

         var var2: Short;
         for (var2 = var0[var4]; var3 >= 0; var3--) {
            var2 = (var1.invoke(var0[var3], var2) as java.lang.Number).shortValue();
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun BooleanArray.reduceRight(operation: (Boolean, Boolean) -> Boolean): Boolean {
      val var3: Int = ArraysKt.getLastIndex(var0);
      if (var3 < 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var2: Int = var3 - 1;

         var var4: Boolean;
         for (var4 = var0[var3]; var2 >= 0; var2--) {
            var4 = var1.invoke(var0[var2], var4) as java.lang.Boolean;
         }

         return var4;
      }
   }

   @JvmStatic
   public inline fun ByteArray.reduceRightIndexed(operation: (Int, Byte, Byte) -> Byte): Byte {
      val var4: Int = ArraysKt.getLastIndex(var0);
      if (var4 < 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var3: Int = var4 - 1;

         var var2: Byte;
         for (var2 = var0[var4]; var3 >= 0; var3--) {
            var2 = (var1.invoke(var3, var0[var3], var2) as java.lang.Number).byteValue();
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun CharArray.reduceRightIndexed(operation: (Int, Char, Char) -> Char): Char {
      val var4: Int = ArraysKt.getLastIndex(var0);
      if (var4 < 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var3: Int = var4 - 1;

         var var2: Char;
         for (var2 = var0[var4]; var3 >= 0; var3--) {
            var2 = var1.invoke(var3, var0[var3], var2) as Character;
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun DoubleArray.reduceRightIndexed(operation: (Int, Double, Double) -> Double): Double {
      val var5: Int = ArraysKt.getLastIndex(var0);
      if (var5 < 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var4: Int = var5 - 1;

         var var2: Double;
         for (var2 = var0[var5]; var4 >= 0; var4--) {
            var2 = (var1.invoke(var4, var0[var4], var2) as java.lang.Number).doubleValue();
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun FloatArray.reduceRightIndexed(operation: (Int, Float, Float) -> Float): Float {
      val var4: Int = ArraysKt.getLastIndex(var0);
      if (var4 < 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var3: Int = var4 - 1;

         var var2: Float;
         for (var2 = var0[var4]; var3 >= 0; var3--) {
            var2 = (var1.invoke(var3, var0[var3], var2) as java.lang.Number).floatValue();
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun IntArray.reduceRightIndexed(operation: (Int, Int, Int) -> Int): Int {
      var var3: Int = ArraysKt.getLastIndex(var0);
      if (var3 < 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var2: Int = var3 - 1;

         for (var3 = var0[var3]; var2 >= 0; var2--) {
            var3 = (var1.invoke(var2, var0[var2], var3) as java.lang.Number).intValue();
         }

         return var3;
      }
   }

   @JvmStatic
   public inline fun LongArray.reduceRightIndexed(operation: (Int, Long, Long) -> Long): Long {
      val var3: Int = ArraysKt.getLastIndex(var0);
      if (var3 < 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var2: Int = var3 - 1;

         var var4: Long;
         for (var4 = var0[var3]; var2 >= 0; var2--) {
            var4 = (var1.invoke(var2, var0[var2], var4) as java.lang.Number).longValue();
         }

         return var4;
      }
   }

   @JvmStatic
   public inline fun <S, T : S> Array<out T>.reduceRightIndexed(operation: (Int, T, S) -> S): S {
      val var3: Int = ArraysKt.getLastIndex(var0);
      if (var3 < 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var2: Int = var3 - 1;

         var var4: Any;
         for (var4 = var0[var3]; var2 >= 0; var2--) {
            var4 = var1.invoke(var2, var0[var2], var4);
         }

         return (S)var4;
      }
   }

   @JvmStatic
   public inline fun ShortArray.reduceRightIndexed(operation: (Int, Short, Short) -> Short): Short {
      val var4: Int = ArraysKt.getLastIndex(var0);
      if (var4 < 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var3: Int = var4 - 1;

         var var2: Short;
         for (var2 = var0[var4]; var3 >= 0; var3--) {
            var2 = (var1.invoke(var3, var0[var3], var2) as java.lang.Number).shortValue();
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun BooleanArray.reduceRightIndexed(operation: (Int, Boolean, Boolean) -> Boolean): Boolean {
      val var3: Int = ArraysKt.getLastIndex(var0);
      if (var3 < 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var2: Int = var3 - 1;

         var var4: Boolean;
         for (var4 = var0[var3]; var2 >= 0; var2--) {
            var4 = var1.invoke(var2, var0[var2], var4) as java.lang.Boolean;
         }

         return var4;
      }
   }

   @JvmStatic
   public inline fun BooleanArray.reduceRightIndexedOrNull(operation: (Int, Boolean, Boolean) -> Boolean): Boolean? {
      val var3: Int = ArraysKt.getLastIndex(var0);
      if (var3 < 0) {
         return null;
      } else {
         var var2: Int = var3 - 1;

         var var4: Boolean;
         for (var4 = var0[var3]; var2 >= 0; var2--) {
            var4 = var1.invoke(var2, var0[var2], var4) as java.lang.Boolean;
         }

         return var4;
      }
   }

   @JvmStatic
   public inline fun ByteArray.reduceRightIndexedOrNull(operation: (Int, Byte, Byte) -> Byte): Byte? {
      val var4: Int = ArraysKt.getLastIndex(var0);
      if (var4 < 0) {
         return null;
      } else {
         var var3: Int = var4 - 1;

         var var2: Byte;
         for (var2 = var0[var4]; var3 >= 0; var3--) {
            var2 = (var1.invoke(var3, var0[var3], var2) as java.lang.Number).byteValue();
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun CharArray.reduceRightIndexedOrNull(operation: (Int, Char, Char) -> Char): Char? {
      val var4: Int = ArraysKt.getLastIndex(var0);
      if (var4 < 0) {
         return null;
      } else {
         var var3: Int = var4 - 1;

         var var2: Char;
         for (var2 = var0[var4]; var3 >= 0; var3--) {
            var2 = var1.invoke(var3, var0[var3], var2) as Character;
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun DoubleArray.reduceRightIndexedOrNull(operation: (Int, Double, Double) -> Double): Double? {
      val var5: Int = ArraysKt.getLastIndex(var0);
      if (var5 < 0) {
         return null;
      } else {
         var var4: Int = var5 - 1;

         var var2: Double;
         for (var2 = var0[var5]; var4 >= 0; var4--) {
            var2 = (var1.invoke(var4, var0[var4], var2) as java.lang.Number).doubleValue();
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun FloatArray.reduceRightIndexedOrNull(operation: (Int, Float, Float) -> Float): Float? {
      val var4: Int = ArraysKt.getLastIndex(var0);
      if (var4 < 0) {
         return null;
      } else {
         var var3: Int = var4 - 1;

         var var2: Float;
         for (var2 = var0[var4]; var3 >= 0; var3--) {
            var2 = (var1.invoke(var3, var0[var3], var2) as java.lang.Number).floatValue();
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun IntArray.reduceRightIndexedOrNull(operation: (Int, Int, Int) -> Int): Int? {
      var var3: Int = ArraysKt.getLastIndex(var0);
      if (var3 < 0) {
         return null;
      } else {
         var var2: Int = var3 - 1;

         for (var3 = var0[var3]; var2 >= 0; var2--) {
            var3 = (var1.invoke(var2, var0[var2], var3) as java.lang.Number).intValue();
         }

         return var3;
      }
   }

   @JvmStatic
   public inline fun LongArray.reduceRightIndexedOrNull(operation: (Int, Long, Long) -> Long): Long? {
      val var3: Int = ArraysKt.getLastIndex(var0);
      if (var3 < 0) {
         return null;
      } else {
         var var2: Int = var3 - 1;

         var var4: Long;
         for (var4 = var0[var3]; var2 >= 0; var2--) {
            var4 = (var1.invoke(var2, var0[var2], var4) as java.lang.Number).longValue();
         }

         return var4;
      }
   }

   @JvmStatic
   public inline fun <S, T : S> Array<out T>.reduceRightIndexedOrNull(operation: (Int, T, S) -> S): S? {
      val var3: Int = ArraysKt.getLastIndex(var0);
      if (var3 < 0) {
         return null;
      } else {
         var var2: Int = var3 - 1;

         var var4: Any;
         for (var4 = var0[var3]; var2 >= 0; var2--) {
            var4 = var1.invoke(var2, var0[var2], var4);
         }

         return (S)var4;
      }
   }

   @JvmStatic
   public inline fun ShortArray.reduceRightIndexedOrNull(operation: (Int, Short, Short) -> Short): Short? {
      val var4: Int = ArraysKt.getLastIndex(var0);
      if (var4 < 0) {
         return null;
      } else {
         var var3: Int = var4 - 1;

         var var2: Short;
         for (var2 = var0[var4]; var3 >= 0; var3--) {
            var2 = (var1.invoke(var3, var0[var3], var2) as java.lang.Number).shortValue();
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun BooleanArray.reduceRightOrNull(operation: (Boolean, Boolean) -> Boolean): Boolean? {
      val var3: Int = ArraysKt.getLastIndex(var0);
      if (var3 < 0) {
         return null;
      } else {
         var var2: Int = var3 - 1;

         var var4: Boolean;
         for (var4 = var0[var3]; var2 >= 0; var2--) {
            var4 = var1.invoke(var0[var2], var4) as java.lang.Boolean;
         }

         return var4;
      }
   }

   @JvmStatic
   public inline fun ByteArray.reduceRightOrNull(operation: (Byte, Byte) -> Byte): Byte? {
      val var4: Int = ArraysKt.getLastIndex(var0);
      if (var4 < 0) {
         return null;
      } else {
         var var3: Int = var4 - 1;

         var var2: Byte;
         for (var2 = var0[var4]; var3 >= 0; var3--) {
            var2 = (var1.invoke(var0[var3], var2) as java.lang.Number).byteValue();
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun CharArray.reduceRightOrNull(operation: (Char, Char) -> Char): Char? {
      val var4: Int = ArraysKt.getLastIndex(var0);
      if (var4 < 0) {
         return null;
      } else {
         var var3: Int = var4 - 1;

         var var2: Char;
         for (var2 = var0[var4]; var3 >= 0; var3--) {
            var2 = var1.invoke(var0[var3], var2) as Character;
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun DoubleArray.reduceRightOrNull(operation: (Double, Double) -> Double): Double? {
      val var5: Int = ArraysKt.getLastIndex(var0);
      if (var5 < 0) {
         return null;
      } else {
         var var4: Int = var5 - 1;

         var var2: Double;
         for (var2 = var0[var5]; var4 >= 0; var4--) {
            var2 = (var1.invoke(var0[var4], var2) as java.lang.Number).doubleValue();
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun FloatArray.reduceRightOrNull(operation: (Float, Float) -> Float): Float? {
      val var4: Int = ArraysKt.getLastIndex(var0);
      if (var4 < 0) {
         return null;
      } else {
         var var3: Int = var4 - 1;

         var var2: Float;
         for (var2 = var0[var4]; var3 >= 0; var3--) {
            var2 = (var1.invoke(var0[var3], var2) as java.lang.Number).floatValue();
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun IntArray.reduceRightOrNull(operation: (Int, Int) -> Int): Int? {
      var var3: Int = ArraysKt.getLastIndex(var0);
      if (var3 < 0) {
         return null;
      } else {
         var var2: Int = var3 - 1;

         for (var3 = var0[var3]; var2 >= 0; var2--) {
            var3 = (var1.invoke(var0[var2], var3) as java.lang.Number).intValue();
         }

         return var3;
      }
   }

   @JvmStatic
   public inline fun LongArray.reduceRightOrNull(operation: (Long, Long) -> Long): Long? {
      val var3: Int = ArraysKt.getLastIndex(var0);
      if (var3 < 0) {
         return null;
      } else {
         var var2: Int = var3 - 1;

         var var4: Long;
         for (var4 = var0[var3]; var2 >= 0; var2--) {
            var4 = (var1.invoke(var0[var2], var4) as java.lang.Number).longValue();
         }

         return var4;
      }
   }

   @JvmStatic
   public inline fun <S, T : S> Array<out T>.reduceRightOrNull(operation: (T, S) -> S): S? {
      val var3: Int = ArraysKt.getLastIndex(var0);
      if (var3 < 0) {
         return null;
      } else {
         var var2: Int = var3 - 1;

         var var4: Any;
         for (var4 = var0[var3]; var2 >= 0; var2--) {
            var4 = var1.invoke(var0[var2], var4);
         }

         return (S)var4;
      }
   }

   @JvmStatic
   public inline fun ShortArray.reduceRightOrNull(operation: (Short, Short) -> Short): Short? {
      val var4: Int = ArraysKt.getLastIndex(var0);
      if (var4 < 0) {
         return null;
      } else {
         var var3: Int = var4 - 1;

         var var2: Short;
         for (var2 = var0[var4]; var3 >= 0; var3--) {
            var2 = (var1.invoke(var0[var3], var2) as java.lang.Number).shortValue();
         }

         return var2;
      }
   }

   @JvmStatic
   public fun <T : Any> Array<T?>.requireNoNulls(): Array<T> {
      val var2: Int = var0.length;

      for (int var1 = 0; var1 < var2; var1++) {
         if (var0[var1] == null) {
            val var3: StringBuilder = new StringBuilder("null element found in ");
            var3.append(var0);
            var3.append('.');
            throw new IllegalArgumentException(var3.toString());
         }
      }

      return (T[])var0;
   }

   @JvmStatic
   public fun ByteArray.reverse() {
      val var4: Int = var0.length / 2 - 1;
      if (var0.length / 2 - 1 >= 0) {
         var var3: Int = ArraysKt.getLastIndex(var0);
         if (var4 >= 0) {
            var var2: Int = 0;

            while (true) {
               val var1: Byte = var0[var2];
               var0[var2] = var0[var3];
               var0[var3] = var1;
               var3--;
               if (var2 == var4) {
                  break;
               }

               var2++;
            }
         }
      }
   }

   @JvmStatic
   public fun ByteArray.reverse(fromIndex: Int, toIndex: Int) {
      AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(var1, var2, var0.length);
      val var4: Int = (var1 + var2) / 2;
      if (var1 != (var1 + var2) / 2) {
         var2--;

         while (var1 < var4) {
            val var3: Byte = var0[var1];
            var0[var1] = var0[var2];
            var0[var2] = var3;
            var2--;
            var1++;
         }
      }
   }

   @JvmStatic
   public fun CharArray.reverse() {
      val var4: Int = var0.length / 2 - 1;
      if (var0.length / 2 - 1 >= 0) {
         var var3: Int = ArraysKt.getLastIndex(var0);
         if (var4 >= 0) {
            var var2: Int = 0;

            while (true) {
               val var1: Char = var0[var2];
               var0[var2] = var0[var3];
               var0[var3] = var1;
               var3--;
               if (var2 == var4) {
                  break;
               }

               var2++;
            }
         }
      }
   }

   @JvmStatic
   public fun CharArray.reverse(fromIndex: Int, toIndex: Int) {
      AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(var1, var2, var0.length);
      val var4: Int = (var1 + var2) / 2;
      if (var1 != (var1 + var2) / 2) {
         var2--;

         while (var1 < var4) {
            val var3: Char = var0[var1];
            var0[var1] = var0[var2];
            var0[var2] = var3;
            var2--;
            var1++;
         }
      }
   }

   @JvmStatic
   public fun DoubleArray.reverse() {
      val var5: Int = var0.length / 2 - 1;
      if (var0.length / 2 - 1 >= 0) {
         var var4: Int = ArraysKt.getLastIndex(var0);
         if (var5 >= 0) {
            var var3: Int = 0;

            while (true) {
               val var1: Double = var0[var3];
               var0[var3] = var0[var4];
               var0[var4] = var1;
               var4--;
               if (var3 == var5) {
                  break;
               }

               var3++;
            }
         }
      }
   }

   @JvmStatic
   public fun DoubleArray.reverse(fromIndex: Int, toIndex: Int) {
      AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(var1, var2, var0.length);
      val var5: Int = (var1 + var2) / 2;
      if (var1 != (var1 + var2) / 2) {
         var2--;

         while (var1 < var5) {
            val var3: Double = var0[var1];
            var0[var1] = var0[var2];
            var0[var2] = var3;
            var2--;
            var1++;
         }
      }
   }

   @JvmStatic
   public fun FloatArray.reverse() {
      val var4: Int = var0.length / 2 - 1;
      if (var0.length / 2 - 1 >= 0) {
         var var3: Int = ArraysKt.getLastIndex(var0);
         if (var4 >= 0) {
            var var2: Int = 0;

            while (true) {
               val var1: Float = var0[var2];
               var0[var2] = var0[var3];
               var0[var3] = var1;
               var3--;
               if (var2 == var4) {
                  break;
               }

               var2++;
            }
         }
      }
   }

   @JvmStatic
   public fun FloatArray.reverse(fromIndex: Int, toIndex: Int) {
      AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(var1, var2, var0.length);
      val var4: Int = (var1 + var2) / 2;
      if (var1 != (var1 + var2) / 2) {
         var2--;

         while (var1 < var4) {
            val var3: Float = var0[var1];
            var0[var1] = var0[var2];
            var0[var2] = var3;
            var2--;
            var1++;
         }
      }
   }

   @JvmStatic
   public fun IntArray.reverse() {
      val var3: Int = var0.length / 2 - 1;
      if (var0.length / 2 - 1 >= 0) {
         var var2: Int = ArraysKt.getLastIndex(var0);
         if (var3 >= 0) {
            var var1: Int = 0;

            while (true) {
               val var4: Int = var0[var1];
               var0[var1] = var0[var2];
               var0[var2] = var4;
               var2--;
               if (var1 == var3) {
                  break;
               }

               var1++;
            }
         }
      }
   }

   @JvmStatic
   public fun IntArray.reverse(fromIndex: Int, toIndex: Int) {
      AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(var1, var2, var0.length);
      val var3: Int = (var1 + var2) / 2;
      if (var1 != (var1 + var2) / 2) {
         var2--;

         while (var1 < var3) {
            val var4: Int = var0[var1];
            var0[var1] = var0[var2];
            var0[var2] = var4;
            var2--;
            var1++;
         }
      }
   }

   @JvmStatic
   public fun LongArray.reverse() {
      val var3: Int = var0.length / 2 - 1;
      if (var0.length / 2 - 1 >= 0) {
         var var2: Int = ArraysKt.getLastIndex(var0);
         if (var3 >= 0) {
            var var1: Int = 0;

            while (true) {
               val var4: Long = var0[var1];
               var0[var1] = var0[var2];
               var0[var2] = var4;
               var2--;
               if (var1 == var3) {
                  break;
               }

               var1++;
            }
         }
      }
   }

   @JvmStatic
   public fun LongArray.reverse(fromIndex: Int, toIndex: Int) {
      AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(var1, var2, var0.length);
      val var3: Int = (var1 + var2) / 2;
      if (var1 != (var1 + var2) / 2) {
         var2--;

         while (var1 < var3) {
            val var4: Long = var0[var1];
            var0[var1] = var0[var2];
            var0[var2] = var4;
            var2--;
            var1++;
         }
      }
   }

   @JvmStatic
   public fun <T> Array<T>.reverse() {
      val var3: Int = var0.length / 2 - 1;
      if (var0.length / 2 - 1 >= 0) {
         var var2: Int = ArraysKt.getLastIndex(var0);
         if (var3 >= 0) {
            var var1: Int = 0;

            while (true) {
               val var4: Any = var0[var1];
               var0[var1] = var0[var2];
               var0[var2] = var4;
               var2--;
               if (var1 == var3) {
                  break;
               }

               var1++;
            }
         }
      }
   }

   @JvmStatic
   public fun <T> Array<T>.reverse(fromIndex: Int, toIndex: Int) {
      AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(var1, var2, var0.length);
      val var3: Int = (var1 + var2) / 2;
      if (var1 != (var1 + var2) / 2) {
         var2--;

         while (var1 < var3) {
            val var4: Any = var0[var1];
            var0[var1] = var0[var2];
            var0[var2] = var4;
            var2--;
            var1++;
         }
      }
   }

   @JvmStatic
   public fun ShortArray.reverse() {
      val var4: Int = var0.length / 2 - 1;
      if (var0.length / 2 - 1 >= 0) {
         var var3: Int = ArraysKt.getLastIndex(var0);
         if (var4 >= 0) {
            var var2: Int = 0;

            while (true) {
               val var1: Short = var0[var2];
               var0[var2] = var0[var3];
               var0[var3] = var1;
               var3--;
               if (var2 == var4) {
                  break;
               }

               var2++;
            }
         }
      }
   }

   @JvmStatic
   public fun ShortArray.reverse(fromIndex: Int, toIndex: Int) {
      AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(var1, var2, var0.length);
      val var4: Int = (var1 + var2) / 2;
      if (var1 != (var1 + var2) / 2) {
         var2--;

         while (var1 < var4) {
            val var3: Short = var0[var1];
            var0[var1] = var0[var2];
            var0[var2] = var3;
            var2--;
            var1++;
         }
      }
   }

   @JvmStatic
   public fun BooleanArray.reverse() {
      val var3: Int = var0.length / 2 - 1;
      if (var0.length / 2 - 1 >= 0) {
         var var2: Int = ArraysKt.getLastIndex(var0);
         if (var3 >= 0) {
            var var1: Int = 0;

            while (true) {
               val var4: Boolean = var0[var1];
               var0[var1] = var0[var2];
               var0[var2] = var4;
               var2--;
               if (var1 == var3) {
                  break;
               }

               var1++;
            }
         }
      }
   }

   @JvmStatic
   public fun BooleanArray.reverse(fromIndex: Int, toIndex: Int) {
      AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(var1, var2, var0.length);
      val var3: Int = (var1 + var2) / 2;
      if (var1 != (var1 + var2) / 2) {
         var2--;

         while (var1 < var3) {
            val var4: Boolean = var0[var1];
            var0[var1] = var0[var2];
            var0[var2] = var4;
            var2--;
            var1++;
         }
      }
   }

   @JvmStatic
   public fun ByteArray.reversed(): List<Byte> {
      if (var0.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var1: java.util.List = ArraysKt.toMutableList(var0);
         CollectionsKt.reverse(var1);
         return var1;
      }
   }

   @JvmStatic
   public fun CharArray.reversed(): List<Char> {
      if (var0.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var1: java.util.List = ArraysKt.toMutableList(var0);
         CollectionsKt.reverse(var1);
         return var1;
      }
   }

   @JvmStatic
   public fun DoubleArray.reversed(): List<Double> {
      if (var0.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var1: java.util.List = ArraysKt.toMutableList(var0);
         CollectionsKt.reverse(var1);
         return var1;
      }
   }

   @JvmStatic
   public fun FloatArray.reversed(): List<Float> {
      if (var0.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var1: java.util.List = ArraysKt.toMutableList(var0);
         CollectionsKt.reverse(var1);
         return var1;
      }
   }

   @JvmStatic
   public fun IntArray.reversed(): List<Int> {
      if (var0.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var1: java.util.List = ArraysKt.toMutableList(var0);
         CollectionsKt.reverse(var1);
         return var1;
      }
   }

   @JvmStatic
   public fun LongArray.reversed(): List<Long> {
      if (var0.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var1: java.util.List = ArraysKt.toMutableList(var0);
         CollectionsKt.reverse(var1);
         return var1;
      }
   }

   @JvmStatic
   public fun <T> Array<out T>.reversed(): List<T> {
      if (var0.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var1: java.util.List = ArraysKt.toMutableList(var0);
         CollectionsKt.reverse(var1);
         return var1;
      }
   }

   @JvmStatic
   public fun ShortArray.reversed(): List<Short> {
      if (var0.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var1: java.util.List = ArraysKt.toMutableList(var0);
         CollectionsKt.reverse(var1);
         return var1;
      }
   }

   @JvmStatic
   public fun BooleanArray.reversed(): List<Boolean> {
      if (var0.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var1: java.util.List = ArraysKt.toMutableList(var0);
         CollectionsKt.reverse(var1);
         return var1;
      }
   }

   @JvmStatic
   public fun ByteArray.reversedArray(): ByteArray {
      if (var0.length == 0) {
         return var0;
      } else {
         val var3: ByteArray = new byte[var0.length];
         val var2: Int = ArraysKt.getLastIndex(var0);
         if (var2 >= 0) {
            var var1: Int = 0;

            while (true) {
               var3[var2 - var1] = var0[var1];
               if (var1 == var2) {
                  break;
               }

               var1++;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun CharArray.reversedArray(): CharArray {
      if (var0.length == 0) {
         return var0;
      } else {
         val var3: CharArray = new char[var0.length];
         val var2: Int = ArraysKt.getLastIndex(var0);
         if (var2 >= 0) {
            var var1: Int = 0;

            while (true) {
               var3[var2 - var1] = var0[var1];
               if (var1 == var2) {
                  break;
               }

               var1++;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun DoubleArray.reversedArray(): DoubleArray {
      if (var0.length == 0) {
         return var0;
      } else {
         val var3: DoubleArray = new double[var0.length];
         val var2: Int = ArraysKt.getLastIndex(var0);
         if (var2 >= 0) {
            var var1: Int = 0;

            while (true) {
               var3[var2 - var1] = var0[var1];
               if (var1 == var2) {
                  break;
               }

               var1++;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun FloatArray.reversedArray(): FloatArray {
      if (var0.length == 0) {
         return var0;
      } else {
         val var3: FloatArray = new float[var0.length];
         val var2: Int = ArraysKt.getLastIndex(var0);
         if (var2 >= 0) {
            var var1: Int = 0;

            while (true) {
               var3[var2 - var1] = var0[var1];
               if (var1 == var2) {
                  break;
               }

               var1++;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun IntArray.reversedArray(): IntArray {
      if (var0.length == 0) {
         return var0;
      } else {
         val var3: IntArray = new int[var0.length];
         val var2: Int = ArraysKt.getLastIndex(var0);
         if (var2 >= 0) {
            var var1: Int = 0;

            while (true) {
               var3[var2 - var1] = var0[var1];
               if (var1 == var2) {
                  break;
               }

               var1++;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun LongArray.reversedArray(): LongArray {
      if (var0.length == 0) {
         return var0;
      } else {
         val var3: LongArray = new long[var0.length];
         val var2: Int = ArraysKt.getLastIndex(var0);
         if (var2 >= 0) {
            var var1: Int = 0;

            while (true) {
               var3[var2 - var1] = var0[var1];
               if (var1 == var2) {
                  break;
               }

               var1++;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun <T> Array<T>.reversedArray(): Array<T> {
      if (var0.length == 0) {
         return (T[])var0;
      } else {
         val var3: Array<Any> = ArraysKt.arrayOfNulls(var0, var0.length);
         val var2: Int = ArraysKt.getLastIndex(var0);
         if (var2 >= 0) {
            var var1: Int = 0;

            while (true) {
               var3[var2 - var1] = var0[var1];
               if (var1 == var2) {
                  break;
               }

               var1++;
            }
         }

         return (T[])var3;
      }
   }

   @JvmStatic
   public fun ShortArray.reversedArray(): ShortArray {
      if (var0.length == 0) {
         return var0;
      } else {
         val var3: ShortArray = new short[var0.length];
         val var2: Int = ArraysKt.getLastIndex(var0);
         if (var2 >= 0) {
            var var1: Int = 0;

            while (true) {
               var3[var2 - var1] = var0[var1];
               if (var1 == var2) {
                  break;
               }

               var1++;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public fun BooleanArray.reversedArray(): BooleanArray {
      if (var0.length == 0) {
         return var0;
      } else {
         val var3: BooleanArray = new boolean[var0.length];
         val var2: Int = ArraysKt.getLastIndex(var0);
         if (var2 >= 0) {
            var var1: Int = 0;

            while (true) {
               var3[var2 - var1] = var0[var1];
               if (var1 == var2) {
                  break;
               }

               var1++;
            }
         }

         return var3;
      }
   }

   @JvmStatic
   public inline fun <R> ByteArray.runningFold(initial: R, operation: (R, Byte) -> R): List<R> {
      if (var0.length == 0) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var1, var0[var3]);
            var5.add(var1);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun <R> CharArray.runningFold(initial: R, operation: (R, Char) -> R): List<R> {
      if (var0.length == 0) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var1, var0[var3]);
            var5.add(var1);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun <R> DoubleArray.runningFold(initial: R, operation: (R, Double) -> R): List<R> {
      if (var0.length == 0) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var1, var0[var3]);
            var5.add(var1);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun <R> FloatArray.runningFold(initial: R, operation: (R, Float) -> R): List<R> {
      if (var0.length == 0) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var1, var0[var3]);
            var5.add(var1);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun <R> IntArray.runningFold(initial: R, operation: (R, Int) -> R): List<R> {
      if (var0.length == 0) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var1, var0[var3]);
            var5.add(var1);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun <R> LongArray.runningFold(initial: R, operation: (R, Long) -> R): List<R> {
      if (var0.length == 0) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var1, var0[var3]);
            var5.add(var1);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun <T, R> Array<out T>.runningFold(initial: R, operation: (R, T) -> R): List<R> {
      if (var0.length == 0) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var1, var0[var3]);
            var5.add(var1);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun <R> ShortArray.runningFold(initial: R, operation: (R, Short) -> R): List<R> {
      if (var0.length == 0) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var1, var0[var3]);
            var5.add(var1);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun <R> BooleanArray.runningFold(initial: R, operation: (R, Boolean) -> R): List<R> {
      if (var0.length == 0) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var1, var0[var3]);
            var5.add(var1);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun <R> ByteArray.runningFoldIndexed(initial: R, operation: (Int, R, Byte) -> R): List<R> {
      if (var0.length == 0) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var3, var1, var0[var3]);
            var5.add(var1);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun <R> CharArray.runningFoldIndexed(initial: R, operation: (Int, R, Char) -> R): List<R> {
      if (var0.length == 0) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var3, var1, var0[var3]);
            var5.add(var1);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun <R> DoubleArray.runningFoldIndexed(initial: R, operation: (Int, R, Double) -> R): List<R> {
      if (var0.length == 0) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var3, var1, var0[var3]);
            var5.add(var1);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun <R> FloatArray.runningFoldIndexed(initial: R, operation: (Int, R, Float) -> R): List<R> {
      if (var0.length == 0) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var3, var1, var0[var3]);
            var5.add(var1);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun <R> IntArray.runningFoldIndexed(initial: R, operation: (Int, R, Int) -> R): List<R> {
      if (var0.length == 0) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var3, var1, var0[var3]);
            var5.add(var1);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun <R> LongArray.runningFoldIndexed(initial: R, operation: (Int, R, Long) -> R): List<R> {
      if (var0.length == 0) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var3, var1, var0[var3]);
            var5.add(var1);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun <T, R> Array<out T>.runningFoldIndexed(initial: R, operation: (Int, R, T) -> R): List<R> {
      if (var0.length == 0) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var3, var1, var0[var3]);
            var5.add(var1);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun <R> ShortArray.runningFoldIndexed(initial: R, operation: (Int, R, Short) -> R): List<R> {
      if (var0.length == 0) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var3, var1, var0[var3]);
            var5.add(var1);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun <R> BooleanArray.runningFoldIndexed(initial: R, operation: (Int, R, Boolean) -> R): List<R> {
      if (var0.length == 0) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var3, var1, var0[var3]);
            var5.add(var1);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun ByteArray.runningReduce(operation: (Byte, Byte) -> Byte): List<Byte> {
      if (var0.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         var var2: Byte = var0[0];
         val var5: ArrayList = new ArrayList(var0.length);
         var5.add(var2);
         val var4: Int = var0.length;

         for (int var3 = 1; var3 < var4; var3++) {
            var2 = (var1.invoke(var2, var0[var3]) as java.lang.Number).byteValue();
            var5.add(var2);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun CharArray.runningReduce(operation: (Char, Char) -> Char): List<Char> {
      if (var0.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         var var2: Char = var0[0];
         val var5: ArrayList = new ArrayList(var0.length);
         var5.add(var2);
         val var4: Int = var0.length;

         for (int var3 = 1; var3 < var4; var3++) {
            val var6: Character = var1.invoke(var2, var0[var3]) as Character;
            var2 = var6;
            var5.add(var6);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun DoubleArray.runningReduce(operation: (Double, Double) -> Double): List<Double> {
      if (var0.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         var var2: Double = var0[0];
         val var6: ArrayList = new ArrayList(var0.length);
         var6.add(var2);
         val var5: Int = var0.length;

         for (int var4 = 1; var4 < var5; var4++) {
            var2 = (var1.invoke(var2, var0[var4]) as java.lang.Number).doubleValue();
            var6.add(var2);
         }

         return var6;
      }
   }

   @JvmStatic
   public inline fun FloatArray.runningReduce(operation: (Float, Float) -> Float): List<Float> {
      if (var0.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         var var2: Float = var0[0];
         val var5: ArrayList = new ArrayList(var0.length);
         var5.add(var2);
         val var4: Int = var0.length;

         for (int var3 = 1; var3 < var4; var3++) {
            var2 = (var1.invoke(var2, var0[var3]) as java.lang.Number).floatValue();
            var5.add(var2);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun IntArray.runningReduce(operation: (Int, Int) -> Int): List<Int> {
      if (var0.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         var var3: Int = var0[0];
         val var5: ArrayList = new ArrayList(var0.length);
         var5.add(var3);
         val var4: Int = var0.length;

         for (int var2 = 1; var2 < var4; var2++) {
            var3 = (var1.invoke(var3, var0[var2]) as java.lang.Number).intValue();
            var5.add(var3);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun LongArray.runningReduce(operation: (Long, Long) -> Long): List<Long> {
      if (var0.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         var var4: Long = var0[0];
         val var6: ArrayList = new ArrayList(var0.length);
         var6.add(var4);
         val var3: Int = var0.length;

         for (int var2 = 1; var2 < var3; var2++) {
            var4 = (var1.invoke(var4, var0[var2]) as java.lang.Number).longValue();
            var6.add(var4);
         }

         return var6;
      }
   }

   @JvmStatic
   public inline fun <S, T : S> Array<out T>.runningReduce(operation: (S, T) -> S): List<S> {
      if (var0.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         var var4: Any = var0[0];
         val var5: ArrayList = new ArrayList(var0.length);
         var5.add(var4);
         val var3: Int = var0.length;

         for (int var2 = 1; var2 < var3; var2++) {
            var4 = var1.invoke(var4, var0[var2]);
            var5.add(var4);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun ShortArray.runningReduce(operation: (Short, Short) -> Short): List<Short> {
      if (var0.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         var var2: Short = var0[0];
         val var5: ArrayList = new ArrayList(var0.length);
         var5.add(var2);
         val var4: Int = var0.length;

         for (int var3 = 1; var3 < var4; var3++) {
            var2 = (var1.invoke(var2, var0[var3]) as java.lang.Number).shortValue();
            var5.add(var2);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun BooleanArray.runningReduce(operation: (Boolean, Boolean) -> Boolean): List<Boolean> {
      if (var0.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         var var4: Boolean = var0[0];
         val var6: ArrayList = new ArrayList(var0.length);
         var6.add(var4);
         val var3: Int = var0.length;

         for (int var2 = 1; var2 < var3; var2++) {
            val var5: java.lang.Boolean = var1.invoke(var4, var0[var2]) as java.lang.Boolean;
            var4 = var5;
            var6.add(var5);
         }

         return var6;
      }
   }

   @JvmStatic
   public inline fun ByteArray.runningReduceIndexed(operation: (Int, Byte, Byte) -> Byte): List<Byte> {
      if (var0.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         var var2: Byte = var0[0];
         val var5: ArrayList = new ArrayList(var0.length);
         var5.add(var2);
         val var4: Int = var0.length;

         for (int var3 = 1; var3 < var4; var3++) {
            var2 = (var1.invoke(var3, var2, var0[var3]) as java.lang.Number).byteValue();
            var5.add(var2);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun CharArray.runningReduceIndexed(operation: (Int, Char, Char) -> Char): List<Char> {
      if (var0.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         var var2: Char = var0[0];
         val var5: ArrayList = new ArrayList(var0.length);
         var5.add(var2);
         val var4: Int = var0.length;

         for (int var3 = 1; var3 < var4; var3++) {
            val var6: Character = var1.invoke(var3, var2, var0[var3]) as Character;
            var2 = var6;
            var5.add(var6);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun DoubleArray.runningReduceIndexed(operation: (Int, Double, Double) -> Double): List<Double> {
      if (var0.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         var var2: Double = var0[0];
         val var6: ArrayList = new ArrayList(var0.length);
         var6.add(var2);
         val var5: Int = var0.length;

         for (int var4 = 1; var4 < var5; var4++) {
            var2 = (var1.invoke(var4, var2, var0[var4]) as java.lang.Number).doubleValue();
            var6.add(var2);
         }

         return var6;
      }
   }

   @JvmStatic
   public inline fun FloatArray.runningReduceIndexed(operation: (Int, Float, Float) -> Float): List<Float> {
      if (var0.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         var var2: Float = var0[0];
         val var5: ArrayList = new ArrayList(var0.length);
         var5.add(var2);
         val var4: Int = var0.length;

         for (int var3 = 1; var3 < var4; var3++) {
            var2 = (var1.invoke(var3, var2, var0[var3]) as java.lang.Number).floatValue();
            var5.add(var2);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun IntArray.runningReduceIndexed(operation: (Int, Int, Int) -> Int): List<Int> {
      if (var0.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         var var3: Int = var0[0];
         val var5: ArrayList = new ArrayList(var0.length);
         var5.add(var3);
         val var4: Int = var0.length;

         for (int var2 = 1; var2 < var4; var2++) {
            var3 = (var1.invoke(var2, var3, var0[var2]) as java.lang.Number).intValue();
            var5.add(var3);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun LongArray.runningReduceIndexed(operation: (Int, Long, Long) -> Long): List<Long> {
      if (var0.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         var var4: Long = var0[0];
         val var6: ArrayList = new ArrayList(var0.length);
         var6.add(var4);
         val var3: Int = var0.length;

         for (int var2 = 1; var2 < var3; var2++) {
            var4 = (var1.invoke(var2, var4, var0[var2]) as java.lang.Number).longValue();
            var6.add(var4);
         }

         return var6;
      }
   }

   @JvmStatic
   public inline fun <S, T : S> Array<out T>.runningReduceIndexed(operation: (Int, S, T) -> S): List<S> {
      if (var0.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         var var4: Any = var0[0];
         val var5: ArrayList = new ArrayList(var0.length);
         var5.add(var4);
         val var3: Int = var0.length;

         for (int var2 = 1; var2 < var3; var2++) {
            var4 = var1.invoke(var2, var4, var0[var2]);
            var5.add(var4);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun ShortArray.runningReduceIndexed(operation: (Int, Short, Short) -> Short): List<Short> {
      if (var0.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         var var2: Short = var0[0];
         val var5: ArrayList = new ArrayList(var0.length);
         var5.add(var2);
         val var4: Int = var0.length;

         for (int var3 = 1; var3 < var4; var3++) {
            var2 = (var1.invoke(var3, var2, var0[var3]) as java.lang.Number).shortValue();
            var5.add(var2);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun BooleanArray.runningReduceIndexed(operation: (Int, Boolean, Boolean) -> Boolean): List<Boolean> {
      if (var0.length == 0) {
         return CollectionsKt.emptyList();
      } else {
         var var4: Boolean = var0[0];
         val var6: ArrayList = new ArrayList(var0.length);
         var6.add(var4);
         val var3: Int = var0.length;

         for (int var2 = 1; var2 < var3; var2++) {
            val var5: java.lang.Boolean = var1.invoke(var2, var4, var0[var2]) as java.lang.Boolean;
            var4 = var5;
            var6.add(var5);
         }

         return var6;
      }
   }

   @JvmStatic
   public inline fun <R> ByteArray.scan(initial: R, operation: (R, Byte) -> R): List<R> {
      val var6: java.util.List;
      if (var0.length == 0) {
         var6 = CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var1, var0[var3]);
            var5.add(var1);
         }

         var6 = var5;
      }

      return var6;
   }

   @JvmStatic
   public inline fun <R> CharArray.scan(initial: R, operation: (R, Char) -> R): List<R> {
      val var6: java.util.List;
      if (var0.length == 0) {
         var6 = CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var1, var0[var3]);
            var5.add(var1);
         }

         var6 = var5;
      }

      return var6;
   }

   @JvmStatic
   public inline fun <R> DoubleArray.scan(initial: R, operation: (R, Double) -> R): List<R> {
      val var6: java.util.List;
      if (var0.length == 0) {
         var6 = CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var1, var0[var3]);
            var5.add(var1);
         }

         var6 = var5;
      }

      return var6;
   }

   @JvmStatic
   public inline fun <R> FloatArray.scan(initial: R, operation: (R, Float) -> R): List<R> {
      val var6: java.util.List;
      if (var0.length == 0) {
         var6 = CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var1, var0[var3]);
            var5.add(var1);
         }

         var6 = var5;
      }

      return var6;
   }

   @JvmStatic
   public inline fun <R> IntArray.scan(initial: R, operation: (R, Int) -> R): List<R> {
      val var6: java.util.List;
      if (var0.length == 0) {
         var6 = CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var1, var0[var3]);
            var5.add(var1);
         }

         var6 = var5;
      }

      return var6;
   }

   @JvmStatic
   public inline fun <R> LongArray.scan(initial: R, operation: (R, Long) -> R): List<R> {
      val var6: java.util.List;
      if (var0.length == 0) {
         var6 = CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var1, var0[var3]);
            var5.add(var1);
         }

         var6 = var5;
      }

      return var6;
   }

   @JvmStatic
   public inline fun <T, R> Array<out T>.scan(initial: R, operation: (R, T) -> R): List<R> {
      val var6: java.util.List;
      if (var0.length == 0) {
         var6 = CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var1, var0[var3]);
            var5.add(var1);
         }

         var6 = var5;
      }

      return var6;
   }

   @JvmStatic
   public inline fun <R> ShortArray.scan(initial: R, operation: (R, Short) -> R): List<R> {
      val var6: java.util.List;
      if (var0.length == 0) {
         var6 = CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var1, var0[var3]);
            var5.add(var1);
         }

         var6 = var5;
      }

      return var6;
   }

   @JvmStatic
   public inline fun <R> BooleanArray.scan(initial: R, operation: (R, Boolean) -> R): List<R> {
      val var6: java.util.List;
      if (var0.length == 0) {
         var6 = CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var1, var0[var3]);
            var5.add(var1);
         }

         var6 = var5;
      }

      return var6;
   }

   @JvmStatic
   public inline fun <R> ByteArray.scanIndexed(initial: R, operation: (Int, R, Byte) -> R): List<R> {
      val var6: java.util.List;
      if (var0.length == 0) {
         var6 = CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var3, var1, var0[var3]);
            var5.add(var1);
         }

         var6 = var5;
      }

      return var6;
   }

   @JvmStatic
   public inline fun <R> CharArray.scanIndexed(initial: R, operation: (Int, R, Char) -> R): List<R> {
      val var6: java.util.List;
      if (var0.length == 0) {
         var6 = CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var3, var1, var0[var3]);
            var5.add(var1);
         }

         var6 = var5;
      }

      return var6;
   }

   @JvmStatic
   public inline fun <R> DoubleArray.scanIndexed(initial: R, operation: (Int, R, Double) -> R): List<R> {
      val var6: java.util.List;
      if (var0.length == 0) {
         var6 = CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var3, var1, var0[var3]);
            var5.add(var1);
         }

         var6 = var5;
      }

      return var6;
   }

   @JvmStatic
   public inline fun <R> FloatArray.scanIndexed(initial: R, operation: (Int, R, Float) -> R): List<R> {
      val var6: java.util.List;
      if (var0.length == 0) {
         var6 = CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var3, var1, var0[var3]);
            var5.add(var1);
         }

         var6 = var5;
      }

      return var6;
   }

   @JvmStatic
   public inline fun <R> IntArray.scanIndexed(initial: R, operation: (Int, R, Int) -> R): List<R> {
      val var6: java.util.List;
      if (var0.length == 0) {
         var6 = CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var3, var1, var0[var3]);
            var5.add(var1);
         }

         var6 = var5;
      }

      return var6;
   }

   @JvmStatic
   public inline fun <R> LongArray.scanIndexed(initial: R, operation: (Int, R, Long) -> R): List<R> {
      val var6: java.util.List;
      if (var0.length == 0) {
         var6 = CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var3, var1, var0[var3]);
            var5.add(var1);
         }

         var6 = var5;
      }

      return var6;
   }

   @JvmStatic
   public inline fun <T, R> Array<out T>.scanIndexed(initial: R, operation: (Int, R, T) -> R): List<R> {
      val var6: java.util.List;
      if (var0.length == 0) {
         var6 = CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var3, var1, var0[var3]);
            var5.add(var1);
         }

         var6 = var5;
      }

      return var6;
   }

   @JvmStatic
   public inline fun <R> ShortArray.scanIndexed(initial: R, operation: (Int, R, Short) -> R): List<R> {
      val var6: java.util.List;
      if (var0.length == 0) {
         var6 = CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var3, var1, var0[var3]);
            var5.add(var1);
         }

         var6 = var5;
      }

      return var6;
   }

   @JvmStatic
   public inline fun <R> BooleanArray.scanIndexed(initial: R, operation: (Int, R, Boolean) -> R): List<R> {
      val var6: java.util.List;
      if (var0.length == 0) {
         var6 = CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(var0.length + 1);
         var5.add(var1);
         val var4: Int = var0.length;

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var3, var1, var0[var3]);
            var5.add(var1);
         }

         var6 = var5;
      }

      return var6;
   }

   @JvmStatic
   public fun ByteArray.shuffle() {
      ArraysKt.shuffle(var0, Random.Default);
   }

   @JvmStatic
   public fun ByteArray.shuffle(random: Random) {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 > 0; var3--) {
         val var4: Int = var1.nextInt(var3 + 1);
         val var2: Byte = var0[var3];
         var0[var3] = var0[var4];
         var0[var4] = var2;
      }
   }

   @JvmStatic
   public fun CharArray.shuffle() {
      ArraysKt.shuffle(var0, Random.Default);
   }

   @JvmStatic
   public fun CharArray.shuffle(random: Random) {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 > 0; var3--) {
         val var4: Int = var1.nextInt(var3 + 1);
         val var2: Char = var0[var3];
         var0[var3] = var0[var4];
         var0[var4] = var2;
      }
   }

   @JvmStatic
   public fun DoubleArray.shuffle() {
      ArraysKt.shuffle(var0, Random.Default);
   }

   @JvmStatic
   public fun DoubleArray.shuffle(random: Random) {
      for (int var4 = ArraysKt.getLastIndex(var0); var4 > 0; var4--) {
         val var5: Int = var1.nextInt(var4 + 1);
         val var2: Double = var0[var4];
         var0[var4] = var0[var5];
         var0[var5] = var2;
      }
   }

   @JvmStatic
   public fun FloatArray.shuffle() {
      ArraysKt.shuffle(var0, Random.Default);
   }

   @JvmStatic
   public fun FloatArray.shuffle(random: Random) {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 > 0; var3--) {
         val var4: Int = var1.nextInt(var3 + 1);
         val var2: Float = var0[var3];
         var0[var3] = var0[var4];
         var0[var4] = var2;
      }
   }

   @JvmStatic
   public fun IntArray.shuffle() {
      ArraysKt.shuffle(var0, Random.Default);
   }

   @JvmStatic
   public fun IntArray.shuffle(random: Random) {
      for (int var2 = ArraysKt.getLastIndex(var0); var2 > 0; var2--) {
         val var3: Int = var1.nextInt(var2 + 1);
         val var4: Int = var0[var2];
         var0[var2] = var0[var3];
         var0[var3] = var4;
      }
   }

   @JvmStatic
   public fun LongArray.shuffle() {
      ArraysKt.shuffle(var0, Random.Default);
   }

   @JvmStatic
   public fun LongArray.shuffle(random: Random) {
      for (int var2 = ArraysKt.getLastIndex(var0); var2 > 0; var2--) {
         val var3: Int = var1.nextInt(var2 + 1);
         val var4: Long = var0[var2];
         var0[var2] = var0[var3];
         var0[var3] = var4;
      }
   }

   @JvmStatic
   public fun <T> Array<T>.shuffle() {
      ArraysKt.shuffle(var0, Random.Default);
   }

   @JvmStatic
   public fun <T> Array<T>.shuffle(random: Random) {
      for (int var2 = ArraysKt.getLastIndex(var0); var2 > 0; var2--) {
         val var3: Int = var1.nextInt(var2 + 1);
         val var4: Any = var0[var2];
         var0[var2] = var0[var3];
         var0[var3] = var4;
      }
   }

   @JvmStatic
   public fun ShortArray.shuffle() {
      ArraysKt.shuffle(var0, Random.Default);
   }

   @JvmStatic
   public fun ShortArray.shuffle(random: Random) {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 > 0; var3--) {
         val var4: Int = var1.nextInt(var3 + 1);
         val var2: Short = var0[var3];
         var0[var3] = var0[var4];
         var0[var4] = var2;
      }
   }

   @JvmStatic
   public fun BooleanArray.shuffle() {
      ArraysKt.shuffle(var0, Random.Default);
   }

   @JvmStatic
   public fun BooleanArray.shuffle(random: Random) {
      for (int var2 = ArraysKt.getLastIndex(var0); var2 > 0; var2--) {
         val var3: Int = var1.nextInt(var2 + 1);
         val var4: Boolean = var0[var2];
         var0[var2] = var0[var3];
         var0[var3] = var4;
      }
   }

   @JvmStatic
   public fun ByteArray.single(): Byte {
      val var1: Int = var0.length;
      if (var0.length != 0) {
         if (var1 == 1) {
            return var0[0];
         } else {
            throw new IllegalArgumentException("Array has more than one element.");
         }
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun ByteArray.single(predicate: (Byte) -> Boolean): Byte {
      val var6: Int = var0.length;
      var var7: java.lang.Byte = null;
      var var3: Int = 0;
      var var4: Boolean = false;

      while (var3 < var6) {
         val var2: Byte = var0[var3];
         var var5: Boolean = var4;
         if (var1.invoke(var2) as java.lang.Boolean) {
            if (var4) {
               throw new IllegalArgumentException("Array contains more than one matching element.");
            }

            var7 = var2;
            var5 = true;
         }

         var3++;
         var4 = var5;
      }

      if (var4) {
         return var7;
      } else {
         throw new NoSuchElementException("Array contains no element matching the predicate.");
      }
   }

   @JvmStatic
   public fun CharArray.single(): Char {
      val var1: Int = var0.length;
      if (var0.length != 0) {
         if (var1 == 1) {
            return var0[0];
         } else {
            throw new IllegalArgumentException("Array has more than one element.");
         }
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun CharArray.single(predicate: (Char) -> Boolean): Char {
      val var6: Int = var0.length;
      var var7: Character = null;
      var var3: Int = 0;
      var var4: Boolean = false;

      while (var3 < var6) {
         val var2: Char = var0[var3];
         var var5: Boolean = var4;
         if (var1.invoke(var2) as java.lang.Boolean) {
            if (var4) {
               throw new IllegalArgumentException("Array contains more than one matching element.");
            }

            var7 = var2;
            var5 = true;
         }

         var3++;
         var4 = var5;
      }

      if (var4) {
         return var7;
      } else {
         throw new NoSuchElementException("Array contains no element matching the predicate.");
      }
   }

   @JvmStatic
   public fun DoubleArray.single(): Double {
      val var1: Int = var0.length;
      if (var0.length != 0) {
         if (var1 == 1) {
            return var0[0];
         } else {
            throw new IllegalArgumentException("Array has more than one element.");
         }
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun DoubleArray.single(predicate: (Double) -> Boolean): Double {
      val var7: Int = var0.length;
      var var8: java.lang.Double = null;
      var var6: Int = 0;
      var var4: Boolean = false;

      while (var6 < var7) {
         val var2: Double = var0[var6];
         var var5: Boolean = var4;
         if (var1.invoke(var2) as java.lang.Boolean) {
            if (var4) {
               throw new IllegalArgumentException("Array contains more than one matching element.");
            }

            var8 = var2;
            var5 = true;
         }

         var6++;
         var4 = var5;
      }

      if (var4) {
         return var8;
      } else {
         throw new NoSuchElementException("Array contains no element matching the predicate.");
      }
   }

   @JvmStatic
   public fun FloatArray.single(): Float {
      val var1: Int = var0.length;
      if (var0.length != 0) {
         if (var1 == 1) {
            return var0[0];
         } else {
            throw new IllegalArgumentException("Array has more than one element.");
         }
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun FloatArray.single(predicate: (Float) -> Boolean): Float {
      val var6: Int = var0.length;
      var var7: java.lang.Float = null;
      var var5: Int = 0;
      var var4: Boolean = false;

      while (var5 < var6) {
         val var2: Float = var0[var5];
         var var3: Boolean = var4;
         if (var1.invoke(var2) as java.lang.Boolean) {
            if (var4) {
               throw new IllegalArgumentException("Array contains more than one matching element.");
            }

            var7 = var2;
            var3 = true;
         }

         var5++;
         var4 = var3;
      }

      if (var4) {
         return var7;
      } else {
         throw new NoSuchElementException("Array contains no element matching the predicate.");
      }
   }

   @JvmStatic
   public fun IntArray.single(): Int {
      val var1: Int = var0.length;
      if (var0.length != 0) {
         if (var1 == 1) {
            return var0[0];
         } else {
            throw new IllegalArgumentException("Array has more than one element.");
         }
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun IntArray.single(predicate: (Int) -> Boolean): Int {
      val var5: Int = var0.length;
      var var7: Int = null;
      var var3: Int = 0;
      var var2: Boolean = false;

      while (var3 < var5) {
         val var6: Int = var0[var3];
         var var4: Boolean = var2;
         if (var1.invoke(var6) as java.lang.Boolean) {
            if (var2) {
               throw new IllegalArgumentException("Array contains more than one matching element.");
            }

            var7 = var6;
            var4 = true;
         }

         var3++;
         var2 = var4;
      }

      if (var2) {
         return var7;
      } else {
         throw new NoSuchElementException("Array contains no element matching the predicate.");
      }
   }

   @JvmStatic
   public fun LongArray.single(): Long {
      val var1: Int = var0.length;
      if (var0.length != 0) {
         if (var1 == 1) {
            return var0[0];
         } else {
            throw new IllegalArgumentException("Array has more than one element.");
         }
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun LongArray.single(predicate: (Long) -> Boolean): Long {
      val var5: Int = var0.length;
      var var8: java.lang.Long = null;
      var var2: Int = 0;
      var var3: Boolean = false;

      while (var2 < var5) {
         val var6: Long = var0[var2];
         var var4: Boolean = var3;
         if (var1.invoke(var6) as java.lang.Boolean) {
            if (var3) {
               throw new IllegalArgumentException("Array contains more than one matching element.");
            }

            var8 = var6;
            var4 = true;
         }

         var2++;
         var3 = var4;
      }

      if (var3) {
         return var8;
      } else {
         throw new NoSuchElementException("Array contains no element matching the predicate.");
      }
   }

   @JvmStatic
   public fun <T> Array<out T>.single(): T {
      val var1: Int = var0.length;
      if (var0.length != 0) {
         if (var1 == 1) {
            return (T)var0[0];
         } else {
            throw new IllegalArgumentException("Array has more than one element.");
         }
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun <T> Array<out T>.single(predicate: (T) -> Boolean): T {
      val var5: Int = var0.length;
      var var6: Any = null;
      var var4: Int = 0;
      var var3: Boolean = false;

      while (var4 < var5) {
         val var7: Any = var0[var4];
         var var2: Boolean = var3;
         if (var1.invoke(var7) as java.lang.Boolean) {
            if (var3) {
               throw new IllegalArgumentException("Array contains more than one matching element.");
            }

            var6 = var7;
            var2 = true;
         }

         var4++;
         var3 = var2;
      }

      if (var3) {
         return (T)var6;
      } else {
         throw new NoSuchElementException("Array contains no element matching the predicate.");
      }
   }

   @JvmStatic
   public fun ShortArray.single(): Short {
      val var1: Int = var0.length;
      if (var0.length != 0) {
         if (var1 == 1) {
            return var0[0];
         } else {
            throw new IllegalArgumentException("Array has more than one element.");
         }
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun ShortArray.single(predicate: (Short) -> Boolean): Short {
      val var6: Int = var0.length;
      var var7: java.lang.Short = null;
      var var5: Int = 0;
      var var3: Boolean = false;

      while (var5 < var6) {
         val var2: Short = var0[var5];
         var var4: Boolean = var3;
         if (var1.invoke(var2) as java.lang.Boolean) {
            if (var3) {
               throw new IllegalArgumentException("Array contains more than one matching element.");
            }

            var7 = var2;
            var4 = true;
         }

         var5++;
         var3 = var4;
      }

      if (var3) {
         return var7;
      } else {
         throw new NoSuchElementException("Array contains no element matching the predicate.");
      }
   }

   @JvmStatic
   public fun BooleanArray.single(): Boolean {
      val var1: Int = var0.length;
      if (var0.length != 0) {
         if (var1 == 1) {
            return var0[0];
         } else {
            throw new IllegalArgumentException("Array has more than one element.");
         }
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun BooleanArray.single(predicate: (Boolean) -> Boolean): Boolean {
      val var5: Int = var0.length;
      var var7: java.lang.Boolean = null;
      var var4: Int = 0;
      var var3: Boolean = false;

      while (var4 < var5) {
         val var6: Boolean = var0[var4];
         var var2: Boolean = var3;
         if (var1.invoke(var6) as java.lang.Boolean) {
            if (var3) {
               throw new IllegalArgumentException("Array contains more than one matching element.");
            }

            var7 = var6;
            var2 = true;
         }

         var4++;
         var3 = var2;
      }

      if (var3) {
         return var7;
      } else {
         throw new NoSuchElementException("Array contains no element matching the predicate.");
      }
   }

   @JvmStatic
   public fun BooleanArray.singleOrNull(): Boolean? {
      val var1: java.lang.Boolean;
      if (var0.length == 1) {
         var1 = var0[0];
      } else {
         var1 = null;
      }

      return var1;
   }

   @JvmStatic
   public inline fun BooleanArray.singleOrNull(predicate: (Boolean) -> Boolean): Boolean? {
      val var5: Int = var0.length;
      var var4: Int = 0;
      var var7: java.lang.Boolean = null;
      var var2: Boolean = false;

      while (var4 < var5) {
         val var6: Boolean = var0[var4];
         var var3: Boolean = var2;
         if (var1.invoke(var6) as java.lang.Boolean) {
            if (var2) {
               return null;
            }

            var7 = var6;
            var3 = true;
         }

         var4++;
         var2 = var3;
      }

      return if (!var2) null else var7;
   }

   @JvmStatic
   public fun ByteArray.singleOrNull(): Byte? {
      val var1: java.lang.Byte;
      if (var0.length == 1) {
         var1 = var0[0];
      } else {
         var1 = null;
      }

      return var1;
   }

   @JvmStatic
   public inline fun ByteArray.singleOrNull(predicate: (Byte) -> Boolean): Byte? {
      val var6: Int = var0.length;
      var var5: Int = 0;
      var var7: java.lang.Byte = null;
      var var3: Boolean = false;

      while (var5 < var6) {
         val var2: Byte = var0[var5];
         var var4: Boolean = var3;
         if (var1.invoke(var2) as java.lang.Boolean) {
            if (var3) {
               return null;
            }

            var7 = var2;
            var4 = true;
         }

         var5++;
         var3 = var4;
      }

      return if (!var3) null else var7;
   }

   @JvmStatic
   public fun CharArray.singleOrNull(): Char? {
      val var1: Character;
      if (var0.length == 1) {
         var1 = var0[0];
      } else {
         var1 = null;
      }

      return var1;
   }

   @JvmStatic
   public inline fun CharArray.singleOrNull(predicate: (Char) -> Boolean): Char? {
      val var6: Int = var0.length;
      var var4: Int = 0;
      var var7: Character = null;
      var var3: Boolean = false;

      while (var4 < var6) {
         val var2: Char = var0[var4];
         var var5: Boolean = var3;
         if (var1.invoke(var2) as java.lang.Boolean) {
            if (var3) {
               return null;
            }

            var7 = var2;
            var5 = true;
         }

         var4++;
         var3 = var5;
      }

      return if (!var3) null else var7;
   }

   @JvmStatic
   public fun DoubleArray.singleOrNull(): Double? {
      val var1: java.lang.Double;
      if (var0.length == 1) {
         var1 = var0[0];
      } else {
         var1 = null;
      }

      return var1;
   }

   @JvmStatic
   public inline fun DoubleArray.singleOrNull(predicate: (Double) -> Boolean): Double? {
      val var7: Int = var0.length;
      var var5: Int = 0;
      var var8: java.lang.Double = null;
      var var6: Boolean = false;

      while (var5 < var7) {
         val var2: Double = var0[var5];
         var var4: Boolean = var6;
         if (var1.invoke(var2) as java.lang.Boolean) {
            if (var6) {
               return null;
            }

            var8 = var2;
            var4 = true;
         }

         var5++;
         var6 = var4;
      }

      return if (!var6) null else var8;
   }

   @JvmStatic
   public fun FloatArray.singleOrNull(): Float? {
      val var1: java.lang.Float;
      if (var0.length == 1) {
         var1 = var0[0];
      } else {
         var1 = null;
      }

      return var1;
   }

   @JvmStatic
   public inline fun FloatArray.singleOrNull(predicate: (Float) -> Boolean): Float? {
      val var6: Int = var0.length;
      var var4: Int = 0;
      var var7: java.lang.Float = null;
      var var3: Boolean = false;

      while (var4 < var6) {
         val var2: Float = var0[var4];
         var var5: Boolean = var3;
         if (var1.invoke(var2) as java.lang.Boolean) {
            if (var3) {
               return null;
            }

            var7 = var2;
            var5 = true;
         }

         var4++;
         var3 = var5;
      }

      return if (!var3) null else var7;
   }

   @JvmStatic
   public fun IntArray.singleOrNull(): Int? {
      val var1: Int;
      if (var0.length == 1) {
         var1 = var0[0];
      } else {
         var1 = null;
      }

      return var1;
   }

   @JvmStatic
   public inline fun IntArray.singleOrNull(predicate: (Int) -> Boolean): Int? {
      val var5: Int = var0.length;
      var var2: Int = 0;
      var var7: Int = null;
      var var3: Boolean = false;

      while (var2 < var5) {
         val var6: Int = var0[var2];
         var var4: Boolean = var3;
         if (var1.invoke(var6) as java.lang.Boolean) {
            if (var3) {
               return null;
            }

            var7 = var6;
            var4 = true;
         }

         var2++;
         var3 = var4;
      }

      return if (!var3) null else var7;
   }

   @JvmStatic
   public fun LongArray.singleOrNull(): Long? {
      val var1: java.lang.Long;
      if (var0.length == 1) {
         var1 = var0[0];
      } else {
         var1 = null;
      }

      return var1;
   }

   @JvmStatic
   public inline fun LongArray.singleOrNull(predicate: (Long) -> Boolean): Long? {
      val var5: Int = var0.length;
      var var2: Int = 0;
      var var8: java.lang.Long = null;
      var var4: Boolean = false;

      while (var2 < var5) {
         val var6: Long = var0[var2];
         var var3: Boolean = var4;
         if (var1.invoke(var6) as java.lang.Boolean) {
            if (var4) {
               return null;
            }

            var8 = var6;
            var3 = true;
         }

         var2++;
         var4 = var3;
      }

      return if (!var4) null else var8;
   }

   @JvmStatic
   public fun <T> Array<out T>.singleOrNull(): T? {
      if (((Object[])var0).length == 1) {
         var0 = ((Object[])var0)[0];
      } else {
         var0 = null;
      }

      return (T)var0;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.singleOrNull(predicate: (T) -> Boolean): T? {
      val var5: Int = var0.length;
      var var2: Int = 0;
      var var6: Any = null;
      var var3: Boolean = false;

      while (var2 < var5) {
         val var7: Any = var0[var2];
         var var4: Boolean = var3;
         if (var1.invoke(var7) as java.lang.Boolean) {
            if (var3) {
               return null;
            }

            var4 = true;
            var6 = var7;
         }

         var2++;
         var3 = var4;
      }

      return (T)(if (!var3) null else var6);
   }

   @JvmStatic
   public fun ShortArray.singleOrNull(): Short? {
      val var1: java.lang.Short;
      if (var0.length == 1) {
         var1 = var0[0];
      } else {
         var1 = null;
      }

      return var1;
   }

   @JvmStatic
   public inline fun ShortArray.singleOrNull(predicate: (Short) -> Boolean): Short? {
      val var6: Int = var0.length;
      var var5: Int = 0;
      var var7: java.lang.Short = null;
      var var3: Boolean = false;

      while (var5 < var6) {
         val var2: Short = var0[var5];
         var var4: Boolean = var3;
         if (var1.invoke(var2) as java.lang.Boolean) {
            if (var3) {
               return null;
            }

            var7 = var2;
            var4 = true;
         }

         var5++;
         var3 = var4;
      }

      return if (!var3) null else var7;
   }

   @JvmStatic
   public fun ByteArray.slice(indices: Iterable<Int>): List<Byte> {
      val var2: Int = CollectionsKt.collectionSizeOrDefault(var1, 10);
      if (var2 == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var3: ArrayList = new ArrayList(var2);
         val var4: java.util.Iterator = var1.iterator();

         while (var4.hasNext()) {
            var3.add(var0[(var4.next() as java.lang.Number).intValue()]);
         }

         return var3;
      }
   }

   @JvmStatic
   public fun ByteArray.slice(indices: IntRange): List<Byte> {
      return if (var1.isEmpty()) CollectionsKt.emptyList() else ArraysKt.asList(ArraysKt.copyOfRange(var0, var1.getStart(), var1.getEndInclusive() + 1));
   }

   @JvmStatic
   public fun CharArray.slice(indices: Iterable<Int>): List<Char> {
      val var2: Int = CollectionsKt.collectionSizeOrDefault(var1, 10);
      if (var2 == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var3: ArrayList = new ArrayList(var2);
         val var4: java.util.Iterator = var1.iterator();

         while (var4.hasNext()) {
            var3.add(var0[(var4.next() as java.lang.Number).intValue()]);
         }

         return var3;
      }
   }

   @JvmStatic
   public fun CharArray.slice(indices: IntRange): List<Char> {
      return if (var1.isEmpty()) CollectionsKt.emptyList() else ArraysKt.asList(ArraysKt.copyOfRange(var0, var1.getStart(), var1.getEndInclusive() + 1));
   }

   @JvmStatic
   public fun DoubleArray.slice(indices: Iterable<Int>): List<Double> {
      val var2: Int = CollectionsKt.collectionSizeOrDefault(var1, 10);
      if (var2 == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var3: ArrayList = new ArrayList(var2);
         val var4: java.util.Iterator = var1.iterator();

         while (var4.hasNext()) {
            var3.add(var0[(var4.next() as java.lang.Number).intValue()]);
         }

         return var3;
      }
   }

   @JvmStatic
   public fun DoubleArray.slice(indices: IntRange): List<Double> {
      return if (var1.isEmpty()) CollectionsKt.emptyList() else ArraysKt.asList(ArraysKt.copyOfRange(var0, var1.getStart(), var1.getEndInclusive() + 1));
   }

   @JvmStatic
   public fun FloatArray.slice(indices: Iterable<Int>): List<Float> {
      val var2: Int = CollectionsKt.collectionSizeOrDefault(var1, 10);
      if (var2 == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var3: ArrayList = new ArrayList(var2);
         val var4: java.util.Iterator = var1.iterator();

         while (var4.hasNext()) {
            var3.add(var0[(var4.next() as java.lang.Number).intValue()]);
         }

         return var3;
      }
   }

   @JvmStatic
   public fun FloatArray.slice(indices: IntRange): List<Float> {
      return if (var1.isEmpty()) CollectionsKt.emptyList() else ArraysKt.asList(ArraysKt.copyOfRange(var0, var1.getStart(), var1.getEndInclusive() + 1));
   }

   @JvmStatic
   public fun IntArray.slice(indices: Iterable<Int>): List<Int> {
      val var2: Int = CollectionsKt.collectionSizeOrDefault(var1, 10);
      if (var2 == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var3: ArrayList = new ArrayList(var2);
         val var4: java.util.Iterator = var1.iterator();

         while (var4.hasNext()) {
            var3.add(var0[(var4.next() as java.lang.Number).intValue()]);
         }

         return var3;
      }
   }

   @JvmStatic
   public fun IntArray.slice(indices: IntRange): List<Int> {
      return if (var1.isEmpty()) CollectionsKt.emptyList() else ArraysKt.asList(ArraysKt.copyOfRange(var0, var1.getStart(), var1.getEndInclusive() + 1));
   }

   @JvmStatic
   public fun LongArray.slice(indices: Iterable<Int>): List<Long> {
      val var2: Int = CollectionsKt.collectionSizeOrDefault(var1, 10);
      if (var2 == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var3: ArrayList = new ArrayList(var2);
         val var4: java.util.Iterator = var1.iterator();

         while (var4.hasNext()) {
            var3.add(var0[(var4.next() as java.lang.Number).intValue()]);
         }

         return var3;
      }
   }

   @JvmStatic
   public fun LongArray.slice(indices: IntRange): List<Long> {
      return if (var1.isEmpty()) CollectionsKt.emptyList() else ArraysKt.asList(ArraysKt.copyOfRange(var0, var1.getStart(), var1.getEndInclusive() + 1));
   }

   @JvmStatic
   public fun <T> Array<out T>.slice(indices: Iterable<Int>): List<T> {
      val var2: Int = CollectionsKt.collectionSizeOrDefault(var1, 10);
      if (var2 == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var3: ArrayList = new ArrayList(var2);
         val var4: java.util.Iterator = var1.iterator();

         while (var4.hasNext()) {
            var3.add(var0[(var4.next() as java.lang.Number).intValue()]);
         }

         return var3;
      }
   }

   @JvmStatic
   public fun <T> Array<out T>.slice(indices: IntRange): List<T> {
      return if (var1.isEmpty()) CollectionsKt.emptyList() else ArraysKt.asList(ArraysKt.copyOfRange(var0, var1.getStart(), var1.getEndInclusive() + 1));
   }

   @JvmStatic
   public fun ShortArray.slice(indices: Iterable<Int>): List<Short> {
      val var2: Int = CollectionsKt.collectionSizeOrDefault(var1, 10);
      if (var2 == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var3: ArrayList = new ArrayList(var2);
         val var4: java.util.Iterator = var1.iterator();

         while (var4.hasNext()) {
            var3.add(var0[(var4.next() as java.lang.Number).intValue()]);
         }

         return var3;
      }
   }

   @JvmStatic
   public fun ShortArray.slice(indices: IntRange): List<Short> {
      return if (var1.isEmpty()) CollectionsKt.emptyList() else ArraysKt.asList(ArraysKt.copyOfRange(var0, var1.getStart(), var1.getEndInclusive() + 1));
   }

   @JvmStatic
   public fun BooleanArray.slice(indices: Iterable<Int>): List<Boolean> {
      val var2: Int = CollectionsKt.collectionSizeOrDefault(var1, 10);
      if (var2 == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var3: ArrayList = new ArrayList(var2);
         val var4: java.util.Iterator = var1.iterator();

         while (var4.hasNext()) {
            var3.add(var0[(var4.next() as java.lang.Number).intValue()]);
         }

         return var3;
      }
   }

   @JvmStatic
   public fun BooleanArray.slice(indices: IntRange): List<Boolean> {
      return if (var1.isEmpty()) CollectionsKt.emptyList() else ArraysKt.asList(ArraysKt.copyOfRange(var0, var1.getStart(), var1.getEndInclusive() + 1));
   }

   @JvmStatic
   public fun ByteArray.sliceArray(indices: Collection<Int>): ByteArray {
      val var3: ByteArray = new byte[var1.size()];
      val var4: java.util.Iterator = var1.iterator();

      for (int var2 = 0; var4.hasNext(); var2++) {
         var3[var2] = var0[(var4.next() as java.lang.Number).intValue()];
      }

      return var3;
   }

   @JvmStatic
   public fun ByteArray.sliceArray(indices: IntRange): ByteArray {
      return if (var1.isEmpty()) new byte[0] else ArraysKt.copyOfRange(var0, var1.getStart(), var1.getEndInclusive() + 1);
   }

   @JvmStatic
   public fun CharArray.sliceArray(indices: Collection<Int>): CharArray {
      val var3: CharArray = new char[var1.size()];
      val var4: java.util.Iterator = var1.iterator();

      for (int var2 = 0; var4.hasNext(); var2++) {
         var3[var2] = var0[(var4.next() as java.lang.Number).intValue()];
      }

      return var3;
   }

   @JvmStatic
   public fun CharArray.sliceArray(indices: IntRange): CharArray {
      return if (var1.isEmpty()) new char[0] else ArraysKt.copyOfRange(var0, var1.getStart(), var1.getEndInclusive() + 1);
   }

   @JvmStatic
   public fun DoubleArray.sliceArray(indices: Collection<Int>): DoubleArray {
      val var3: DoubleArray = new double[var1.size()];
      val var4: java.util.Iterator = var1.iterator();

      for (int var2 = 0; var4.hasNext(); var2++) {
         var3[var2] = var0[(var4.next() as java.lang.Number).intValue()];
      }

      return var3;
   }

   @JvmStatic
   public fun DoubleArray.sliceArray(indices: IntRange): DoubleArray {
      return if (var1.isEmpty()) new double[0] else ArraysKt.copyOfRange(var0, var1.getStart(), var1.getEndInclusive() + 1);
   }

   @JvmStatic
   public fun FloatArray.sliceArray(indices: Collection<Int>): FloatArray {
      val var3: FloatArray = new float[var1.size()];
      val var4: java.util.Iterator = var1.iterator();

      for (int var2 = 0; var4.hasNext(); var2++) {
         var3[var2] = var0[(var4.next() as java.lang.Number).intValue()];
      }

      return var3;
   }

   @JvmStatic
   public fun FloatArray.sliceArray(indices: IntRange): FloatArray {
      return if (var1.isEmpty()) new float[0] else ArraysKt.copyOfRange(var0, var1.getStart(), var1.getEndInclusive() + 1);
   }

   @JvmStatic
   public fun IntArray.sliceArray(indices: Collection<Int>): IntArray {
      val var3: IntArray = new int[var1.size()];
      val var4: java.util.Iterator = var1.iterator();

      for (int var2 = 0; var4.hasNext(); var2++) {
         var3[var2] = var0[(var4.next() as java.lang.Number).intValue()];
      }

      return var3;
   }

   @JvmStatic
   public fun IntArray.sliceArray(indices: IntRange): IntArray {
      return if (var1.isEmpty()) new int[0] else ArraysKt.copyOfRange(var0, var1.getStart(), var1.getEndInclusive() + 1);
   }

   @JvmStatic
   public fun LongArray.sliceArray(indices: Collection<Int>): LongArray {
      val var3: LongArray = new long[var1.size()];
      val var4: java.util.Iterator = var1.iterator();

      for (int var2 = 0; var4.hasNext(); var2++) {
         var3[var2] = var0[(var4.next() as java.lang.Number).intValue()];
      }

      return var3;
   }

   @JvmStatic
   public fun LongArray.sliceArray(indices: IntRange): LongArray {
      return if (var1.isEmpty()) new long[0] else ArraysKt.copyOfRange(var0, var1.getStart(), var1.getEndInclusive() + 1);
   }

   @JvmStatic
   public fun <T> Array<T>.sliceArray(indices: Collection<Int>): Array<T> {
      val var3: Array<Any> = ArraysKt.arrayOfNulls(var0, var1.size());
      val var4: java.util.Iterator = var1.iterator();

      for (int var2 = 0; var4.hasNext(); var2++) {
         var3[var2] = var0[(var4.next() as java.lang.Number).intValue()];
      }

      return (T[])var3;
   }

   @JvmStatic
   public fun <T> Array<T>.sliceArray(indices: IntRange): Array<T> {
      return (T[])(if (var1.isEmpty()) ArraysKt.copyOfRange(var0, 0, 0) else ArraysKt.copyOfRange(var0, var1.getStart(), var1.getEndInclusive() + 1));
   }

   @JvmStatic
   public fun ShortArray.sliceArray(indices: Collection<Int>): ShortArray {
      val var3: ShortArray = new short[var1.size()];
      val var4: java.util.Iterator = var1.iterator();

      for (int var2 = 0; var4.hasNext(); var2++) {
         var3[var2] = var0[(var4.next() as java.lang.Number).intValue()];
      }

      return var3;
   }

   @JvmStatic
   public fun ShortArray.sliceArray(indices: IntRange): ShortArray {
      return if (var1.isEmpty()) new short[0] else ArraysKt.copyOfRange(var0, var1.getStart(), var1.getEndInclusive() + 1);
   }

   @JvmStatic
   public fun BooleanArray.sliceArray(indices: Collection<Int>): BooleanArray {
      val var3: BooleanArray = new boolean[var1.size()];
      val var4: java.util.Iterator = var1.iterator();

      for (int var2 = 0; var4.hasNext(); var2++) {
         var3[var2] = var0[(var4.next() as java.lang.Number).intValue()];
      }

      return var3;
   }

   @JvmStatic
   public fun BooleanArray.sliceArray(indices: IntRange): BooleanArray {
      return if (var1.isEmpty()) new boolean[0] else ArraysKt.copyOfRange(var0, var1.getStart(), var1.getEndInclusive() + 1);
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Array<out T>.sortBy(crossinline selector: (T) -> R?) {
      if (var0.length > 1) {
         ArraysKt.sortWith(var0, new Comparator(var1) {
            final Function1<T, java.lang.Comparable<?>> $selector;

            {
               this.$selector = var1;
            }

            @Override
            public final int compare(T var1, T var2) {
               val var3: Function1 = this.$selector;
               return ComparisonsKt.compareValues(this.$selector.invoke((T)var1) as java.langComparable<?>, var3.invoke(var2) as java.langComparable<?>);
            }
         });
      }
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Array<out T>.sortByDescending(crossinline selector: (T) -> R?) {
      if (var0.length > 1) {
         ArraysKt.sortWith(var0, new Comparator(var1) {
            final Function1<T, java.lang.Comparable<?>> $selector;

            {
               this.$selector = var1;
            }

            @Override
            public final int compare(T var1, T var2) {
               val var3: Function1 = this.$selector;
               return ComparisonsKt.compareValues(this.$selector.invoke((T)var2) as java.langComparable<?>, var3.invoke(var1) as java.langComparable<?>);
            }
         });
      }
   }

   @JvmStatic
   public fun ByteArray.sortDescending() {
      if (var0.length > 1) {
         ArraysKt.sort(var0);
         ArraysKt.reverse(var0);
      }
   }

   @JvmStatic
   public fun ByteArray.sortDescending(fromIndex: Int, toIndex: Int) {
      ArraysKt.sort(var0, var1, var2);
      ArraysKt.reverse(var0, var1, var2);
   }

   @JvmStatic
   public fun CharArray.sortDescending() {
      if (var0.length > 1) {
         ArraysKt.sort(var0);
         ArraysKt.reverse(var0);
      }
   }

   @JvmStatic
   public fun CharArray.sortDescending(fromIndex: Int, toIndex: Int) {
      ArraysKt.sort(var0, var1, var2);
      ArraysKt.reverse(var0, var1, var2);
   }

   @JvmStatic
   public fun DoubleArray.sortDescending() {
      if (var0.length > 1) {
         ArraysKt.sort(var0);
         ArraysKt.reverse(var0);
      }
   }

   @JvmStatic
   public fun DoubleArray.sortDescending(fromIndex: Int, toIndex: Int) {
      ArraysKt.sort(var0, var1, var2);
      ArraysKt.reverse(var0, var1, var2);
   }

   @JvmStatic
   public fun FloatArray.sortDescending() {
      if (var0.length > 1) {
         ArraysKt.sort(var0);
         ArraysKt.reverse(var0);
      }
   }

   @JvmStatic
   public fun FloatArray.sortDescending(fromIndex: Int, toIndex: Int) {
      ArraysKt.sort(var0, var1, var2);
      ArraysKt.reverse(var0, var1, var2);
   }

   @JvmStatic
   public fun IntArray.sortDescending() {
      if (var0.length > 1) {
         ArraysKt.sort(var0);
         ArraysKt.reverse(var0);
      }
   }

   @JvmStatic
   public fun IntArray.sortDescending(fromIndex: Int, toIndex: Int) {
      ArraysKt.sort(var0, var1, var2);
      ArraysKt.reverse(var0, var1, var2);
   }

   @JvmStatic
   public fun LongArray.sortDescending() {
      if (var0.length > 1) {
         ArraysKt.sort(var0);
         ArraysKt.reverse(var0);
      }
   }

   @JvmStatic
   public fun LongArray.sortDescending(fromIndex: Int, toIndex: Int) {
      ArraysKt.sort(var0, var1, var2);
      ArraysKt.reverse(var0, var1, var2);
   }

   @JvmStatic
   public fun <T : Comparable<T>> Array<out T>.sortDescending() {
      ArraysKt.sortWith(var0, ComparisonsKt.reverseOrder());
   }

   @JvmStatic
   public fun <T : Comparable<T>> Array<out T>.sortDescending(fromIndex: Int, toIndex: Int) {
      ArraysKt.sortWith(var0, ComparisonsKt.reverseOrder(), var1, var2);
   }

   @JvmStatic
   public fun ShortArray.sortDescending() {
      if (var0.length > 1) {
         ArraysKt.sort(var0);
         ArraysKt.reverse(var0);
      }
   }

   @JvmStatic
   public fun ShortArray.sortDescending(fromIndex: Int, toIndex: Int) {
      ArraysKt.sort(var0, var1, var2);
      ArraysKt.reverse(var0, var1, var2);
   }

   @JvmStatic
   public fun ByteArray.sorted(): List<Byte> {
      val var1: Array<java.lang.Byte> = ArraysKt.toTypedArray(var0);
      ArraysKt.sort(var1);
      return ArraysKt.asList(var1);
   }

   @JvmStatic
   public fun CharArray.sorted(): List<Char> {
      val var1: Array<Character> = ArraysKt.toTypedArray(var0);
      ArraysKt.sort(var1);
      return ArraysKt.asList(var1);
   }

   @JvmStatic
   public fun DoubleArray.sorted(): List<Double> {
      val var1: Array<java.lang.Double> = ArraysKt.toTypedArray(var0);
      ArraysKt.sort(var1);
      return ArraysKt.asList(var1);
   }

   @JvmStatic
   public fun FloatArray.sorted(): List<Float> {
      val var1: Array<java.lang.Float> = ArraysKt.toTypedArray(var0);
      ArraysKt.sort(var1);
      return ArraysKt.asList(var1);
   }

   @JvmStatic
   public fun IntArray.sorted(): List<Int> {
      val var1: Array<Int> = ArraysKt.toTypedArray(var0);
      ArraysKt.sort(var1);
      return ArraysKt.asList(var1);
   }

   @JvmStatic
   public fun LongArray.sorted(): List<Long> {
      val var1: Array<java.lang.Long> = ArraysKt.toTypedArray(var0);
      ArraysKt.sort(var1);
      return ArraysKt.asList(var1);
   }

   @JvmStatic
   public fun <T : Comparable<T>> Array<out T>.sorted(): List<T> {
      return (java.util.List<T>)ArraysKt.asList(ArraysKt.sortedArray(var0));
   }

   @JvmStatic
   public fun ShortArray.sorted(): List<Short> {
      val var1: Array<java.lang.Short> = ArraysKt.toTypedArray(var0);
      ArraysKt.sort(var1);
      return ArraysKt.asList(var1);
   }

   @JvmStatic
   public fun ByteArray.sortedArray(): ByteArray {
      if (var0.length == 0) {
         return var0;
      } else {
         var0 = Arrays.copyOf(var0, var0.length);
         ArraysKt.sort(var0);
         return var0;
      }
   }

   @JvmStatic
   public fun CharArray.sortedArray(): CharArray {
      if (var0.length == 0) {
         return var0;
      } else {
         var0 = Arrays.copyOf(var0, var0.length);
         ArraysKt.sort(var0);
         return var0;
      }
   }

   @JvmStatic
   public fun DoubleArray.sortedArray(): DoubleArray {
      if (var0.length == 0) {
         return var0;
      } else {
         var0 = Arrays.copyOf(var0, var0.length);
         ArraysKt.sort(var0);
         return var0;
      }
   }

   @JvmStatic
   public fun FloatArray.sortedArray(): FloatArray {
      if (var0.length == 0) {
         return var0;
      } else {
         var0 = Arrays.copyOf(var0, var0.length);
         ArraysKt.sort(var0);
         return var0;
      }
   }

   @JvmStatic
   public fun IntArray.sortedArray(): IntArray {
      if (var0.length == 0) {
         return var0;
      } else {
         var0 = Arrays.copyOf(var0, var0.length);
         ArraysKt.sort(var0);
         return var0;
      }
   }

   @JvmStatic
   public fun LongArray.sortedArray(): LongArray {
      if (var0.length == 0) {
         return var0;
      } else {
         var0 = Arrays.copyOf(var0, var0.length);
         ArraysKt.sort(var0);
         return var0;
      }
   }

   @JvmStatic
   public fun <T : Comparable<T>> Array<T>.sortedArray(): Array<T> {
      if (var0.length == 0) {
         return (T[])var0;
      } else {
         val var1: Array<Any> = Arrays.copyOf(var0, var0.length);
         var0 = var1 as Array<java.lang.Comparable>;
         ArraysKt.sort(var1 as Array<java.lang.Comparable>);
         return (T[])var0;
      }
   }

   @JvmStatic
   public fun ShortArray.sortedArray(): ShortArray {
      if (var0.length == 0) {
         return var0;
      } else {
         var0 = Arrays.copyOf(var0, var0.length);
         ArraysKt.sort(var0);
         return var0;
      }
   }

   @JvmStatic
   public fun ByteArray.sortedArrayDescending(): ByteArray {
      if (var0.length == 0) {
         return var0;
      } else {
         var0 = Arrays.copyOf(var0, var0.length);
         ArraysKt.sortDescending(var0);
         return var0;
      }
   }

   @JvmStatic
   public fun CharArray.sortedArrayDescending(): CharArray {
      if (var0.length == 0) {
         return var0;
      } else {
         var0 = Arrays.copyOf(var0, var0.length);
         ArraysKt.sortDescending(var0);
         return var0;
      }
   }

   @JvmStatic
   public fun DoubleArray.sortedArrayDescending(): DoubleArray {
      if (var0.length == 0) {
         return var0;
      } else {
         var0 = Arrays.copyOf(var0, var0.length);
         ArraysKt.sortDescending(var0);
         return var0;
      }
   }

   @JvmStatic
   public fun FloatArray.sortedArrayDescending(): FloatArray {
      if (var0.length == 0) {
         return var0;
      } else {
         var0 = Arrays.copyOf(var0, var0.length);
         ArraysKt.sortDescending(var0);
         return var0;
      }
   }

   @JvmStatic
   public fun IntArray.sortedArrayDescending(): IntArray {
      if (var0.length == 0) {
         return var0;
      } else {
         var0 = Arrays.copyOf(var0, var0.length);
         ArraysKt.sortDescending(var0);
         return var0;
      }
   }

   @JvmStatic
   public fun LongArray.sortedArrayDescending(): LongArray {
      if (var0.length == 0) {
         return var0;
      } else {
         var0 = Arrays.copyOf(var0, var0.length);
         ArraysKt.sortDescending(var0);
         return var0;
      }
   }

   @JvmStatic
   public fun <T : Comparable<T>> Array<T>.sortedArrayDescending(): Array<T> {
      if (var0.length == 0) {
         return (T[])var0;
      } else {
         val var1: Array<Any> = Arrays.copyOf(var0, var0.length);
         var0 = var1 as Array<java.lang.Comparable>;
         ArraysKt.sortWith(var1 as Array<java.lang.Comparable>, ComparisonsKt.reverseOrder());
         return (T[])var0;
      }
   }

   @JvmStatic
   public fun ShortArray.sortedArrayDescending(): ShortArray {
      if (var0.length == 0) {
         return var0;
      } else {
         var0 = Arrays.copyOf(var0, var0.length);
         ArraysKt.sortDescending(var0);
         return var0;
      }
   }

   @JvmStatic
   public fun <T> Array<out T>.sortedArrayWith(comparator: Comparator<in T>): Array<out T> {
      if (var0.length == 0) {
         return (T[])var0;
      } else {
         var0 = Arrays.copyOf(var0, var0.length);
         ArraysKt.sortWith(var0, var1);
         return (T[])var0;
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> ByteArray.sortedBy(crossinline selector: (Byte) -> R?): List<Byte> {
      return ArraysKt.sortedWith(var0, new Comparator(var1) {
         final Function1<T, java.lang.Comparable<?>> $selector;

         {
            this.$selector = var1;
         }

         @Override
         public final int compare(T var1, T var2) {
            val var3: Function1 = this.$selector;
            return ComparisonsKt.compareValues(this.$selector.invoke((T)var1) as java.langComparable<?>, var3.invoke(var2) as java.langComparable<?>);
         }
      });
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> CharArray.sortedBy(crossinline selector: (Char) -> R?): List<Char> {
      return ArraysKt.sortedWith(var0, new Comparator(var1) {
         final Function1<T, java.lang.Comparable<?>> $selector;

         {
            this.$selector = var1;
         }

         @Override
         public final int compare(T var1, T var2) {
            val var3: Function1 = this.$selector;
            return ComparisonsKt.compareValues(this.$selector.invoke((T)var1) as java.langComparable<?>, var3.invoke(var2) as java.langComparable<?>);
         }
      });
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> DoubleArray.sortedBy(crossinline selector: (Double) -> R?): List<Double> {
      return ArraysKt.sortedWith(var0, new Comparator(var1) {
         final Function1<T, java.lang.Comparable<?>> $selector;

         {
            this.$selector = var1;
         }

         @Override
         public final int compare(T var1, T var2) {
            val var3: Function1 = this.$selector;
            return ComparisonsKt.compareValues(this.$selector.invoke((T)var1) as java.langComparable<?>, var3.invoke(var2) as java.langComparable<?>);
         }
      });
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> FloatArray.sortedBy(crossinline selector: (Float) -> R?): List<Float> {
      return ArraysKt.sortedWith(var0, new Comparator(var1) {
         final Function1<T, java.lang.Comparable<?>> $selector;

         {
            this.$selector = var1;
         }

         @Override
         public final int compare(T var1, T var2) {
            val var3: Function1 = this.$selector;
            return ComparisonsKt.compareValues(this.$selector.invoke((T)var1) as java.langComparable<?>, var3.invoke(var2) as java.langComparable<?>);
         }
      });
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> IntArray.sortedBy(crossinline selector: (Int) -> R?): List<Int> {
      return ArraysKt.sortedWith(var0, new Comparator(var1) {
         final Function1<T, java.lang.Comparable<?>> $selector;

         {
            this.$selector = var1;
         }

         @Override
         public final int compare(T var1, T var2) {
            val var3: Function1 = this.$selector;
            return ComparisonsKt.compareValues(this.$selector.invoke((T)var1) as java.langComparable<?>, var3.invoke(var2) as java.langComparable<?>);
         }
      });
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> LongArray.sortedBy(crossinline selector: (Long) -> R?): List<Long> {
      return ArraysKt.sortedWith(var0, new Comparator(var1) {
         final Function1<T, java.lang.Comparable<?>> $selector;

         {
            this.$selector = var1;
         }

         @Override
         public final int compare(T var1, T var2) {
            val var3: Function1 = this.$selector;
            return ComparisonsKt.compareValues(this.$selector.invoke((T)var1) as java.langComparable<?>, var3.invoke(var2) as java.langComparable<?>);
         }
      });
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Array<out T>.sortedBy(crossinline selector: (T) -> R?): List<T> {
      return (java.util.List<T>)ArraysKt.sortedWith(var0, new Comparator(var1) {
         final Function1<T, java.lang.Comparable<?>> $selector;

         {
            this.$selector = var1;
         }

         @Override
         public final int compare(T var1, T var2) {
            val var3: Function1 = this.$selector;
            return ComparisonsKt.compareValues(this.$selector.invoke((T)var1) as java.langComparable<?>, var3.invoke(var2) as java.langComparable<?>);
         }
      });
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> ShortArray.sortedBy(crossinline selector: (Short) -> R?): List<Short> {
      return ArraysKt.sortedWith(var0, new Comparator(var1) {
         final Function1<T, java.lang.Comparable<?>> $selector;

         {
            this.$selector = var1;
         }

         @Override
         public final int compare(T var1, T var2) {
            val var3: Function1 = this.$selector;
            return ComparisonsKt.compareValues(this.$selector.invoke((T)var1) as java.langComparable<?>, var3.invoke(var2) as java.langComparable<?>);
         }
      });
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> BooleanArray.sortedBy(crossinline selector: (Boolean) -> R?): List<Boolean> {
      return ArraysKt.sortedWith(var0, new Comparator(var1) {
         final Function1<T, java.lang.Comparable<?>> $selector;

         {
            this.$selector = var1;
         }

         @Override
         public final int compare(T var1, T var2) {
            val var3: Function1 = this.$selector;
            return ComparisonsKt.compareValues(this.$selector.invoke((T)var1) as java.langComparable<?>, var3.invoke(var2) as java.langComparable<?>);
         }
      });
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> ByteArray.sortedByDescending(crossinline selector: (Byte) -> R?): List<Byte> {
      return ArraysKt.sortedWith(var0, new Comparator(var1) {
         final Function1<T, java.lang.Comparable<?>> $selector;

         {
            this.$selector = var1;
         }

         @Override
         public final int compare(T var1, T var2) {
            val var3: Function1 = this.$selector;
            return ComparisonsKt.compareValues(this.$selector.invoke((T)var2) as java.langComparable<?>, var3.invoke(var1) as java.langComparable<?>);
         }
      });
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> CharArray.sortedByDescending(crossinline selector: (Char) -> R?): List<Char> {
      return ArraysKt.sortedWith(var0, new Comparator(var1) {
         final Function1<T, java.lang.Comparable<?>> $selector;

         {
            this.$selector = var1;
         }

         @Override
         public final int compare(T var1, T var2) {
            val var3: Function1 = this.$selector;
            return ComparisonsKt.compareValues(this.$selector.invoke((T)var2) as java.langComparable<?>, var3.invoke(var1) as java.langComparable<?>);
         }
      });
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> DoubleArray.sortedByDescending(crossinline selector: (Double) -> R?): List<Double> {
      return ArraysKt.sortedWith(var0, new Comparator(var1) {
         final Function1<T, java.lang.Comparable<?>> $selector;

         {
            this.$selector = var1;
         }

         @Override
         public final int compare(T var1, T var2) {
            val var3: Function1 = this.$selector;
            return ComparisonsKt.compareValues(this.$selector.invoke((T)var2) as java.langComparable<?>, var3.invoke(var1) as java.langComparable<?>);
         }
      });
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> FloatArray.sortedByDescending(crossinline selector: (Float) -> R?): List<Float> {
      return ArraysKt.sortedWith(var0, new Comparator(var1) {
         final Function1<T, java.lang.Comparable<?>> $selector;

         {
            this.$selector = var1;
         }

         @Override
         public final int compare(T var1, T var2) {
            val var3: Function1 = this.$selector;
            return ComparisonsKt.compareValues(this.$selector.invoke((T)var2) as java.langComparable<?>, var3.invoke(var1) as java.langComparable<?>);
         }
      });
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> IntArray.sortedByDescending(crossinline selector: (Int) -> R?): List<Int> {
      return ArraysKt.sortedWith(var0, new Comparator(var1) {
         final Function1<T, java.lang.Comparable<?>> $selector;

         {
            this.$selector = var1;
         }

         @Override
         public final int compare(T var1, T var2) {
            val var3: Function1 = this.$selector;
            return ComparisonsKt.compareValues(this.$selector.invoke((T)var2) as java.langComparable<?>, var3.invoke(var1) as java.langComparable<?>);
         }
      });
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> LongArray.sortedByDescending(crossinline selector: (Long) -> R?): List<Long> {
      return ArraysKt.sortedWith(var0, new Comparator(var1) {
         final Function1<T, java.lang.Comparable<?>> $selector;

         {
            this.$selector = var1;
         }

         @Override
         public final int compare(T var1, T var2) {
            val var3: Function1 = this.$selector;
            return ComparisonsKt.compareValues(this.$selector.invoke((T)var2) as java.langComparable<?>, var3.invoke(var1) as java.langComparable<?>);
         }
      });
   }

   @JvmStatic
   public inline fun <T, R : Comparable<R>> Array<out T>.sortedByDescending(crossinline selector: (T) -> R?): List<T> {
      return (java.util.List<T>)ArraysKt.sortedWith(var0, new Comparator(var1) {
         final Function1<T, java.lang.Comparable<?>> $selector;

         {
            this.$selector = var1;
         }

         @Override
         public final int compare(T var1, T var2) {
            val var3: Function1 = this.$selector;
            return ComparisonsKt.compareValues(this.$selector.invoke((T)var2) as java.langComparable<?>, var3.invoke(var1) as java.langComparable<?>);
         }
      });
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> ShortArray.sortedByDescending(crossinline selector: (Short) -> R?): List<Short> {
      return ArraysKt.sortedWith(var0, new Comparator(var1) {
         final Function1<T, java.lang.Comparable<?>> $selector;

         {
            this.$selector = var1;
         }

         @Override
         public final int compare(T var1, T var2) {
            val var3: Function1 = this.$selector;
            return ComparisonsKt.compareValues(this.$selector.invoke((T)var2) as java.langComparable<?>, var3.invoke(var1) as java.langComparable<?>);
         }
      });
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> BooleanArray.sortedByDescending(crossinline selector: (Boolean) -> R?): List<Boolean> {
      return ArraysKt.sortedWith(var0, new Comparator(var1) {
         final Function1<T, java.lang.Comparable<?>> $selector;

         {
            this.$selector = var1;
         }

         @Override
         public final int compare(T var1, T var2) {
            val var3: Function1 = this.$selector;
            return ComparisonsKt.compareValues(this.$selector.invoke((T)var2) as java.langComparable<?>, var3.invoke(var1) as java.langComparable<?>);
         }
      });
   }

   @JvmStatic
   public fun ByteArray.sortedDescending(): List<Byte> {
      var0 = Arrays.copyOf(var0, var0.length);
      ArraysKt.sort(var0);
      return ArraysKt.reversed(var0);
   }

   @JvmStatic
   public fun CharArray.sortedDescending(): List<Char> {
      var0 = Arrays.copyOf(var0, var0.length);
      ArraysKt.sort(var0);
      return ArraysKt.reversed(var0);
   }

   @JvmStatic
   public fun DoubleArray.sortedDescending(): List<Double> {
      var0 = Arrays.copyOf(var0, var0.length);
      ArraysKt.sort(var0);
      return ArraysKt.reversed(var0);
   }

   @JvmStatic
   public fun FloatArray.sortedDescending(): List<Float> {
      var0 = Arrays.copyOf(var0, var0.length);
      ArraysKt.sort(var0);
      return ArraysKt.reversed(var0);
   }

   @JvmStatic
   public fun IntArray.sortedDescending(): List<Int> {
      var0 = Arrays.copyOf(var0, var0.length);
      ArraysKt.sort(var0);
      return ArraysKt.reversed(var0);
   }

   @JvmStatic
   public fun LongArray.sortedDescending(): List<Long> {
      var0 = Arrays.copyOf(var0, var0.length);
      ArraysKt.sort(var0);
      return ArraysKt.reversed(var0);
   }

   @JvmStatic
   public fun <T : Comparable<T>> Array<out T>.sortedDescending(): List<T> {
      return (java.util.List<T>)ArraysKt.sortedWith(var0, ComparisonsKt.reverseOrder());
   }

   @JvmStatic
   public fun ShortArray.sortedDescending(): List<Short> {
      var0 = Arrays.copyOf(var0, var0.length);
      ArraysKt.sort(var0);
      return ArraysKt.reversed(var0);
   }

   @JvmStatic
   public fun ByteArray.sortedWith(comparator: Comparator<in Byte>): List<Byte> {
      val var2: Array<java.lang.Byte> = ArraysKt.toTypedArray(var0);
      ArraysKt.sortWith(var2, var1);
      return ArraysKt.asList(var2);
   }

   @JvmStatic
   public fun CharArray.sortedWith(comparator: Comparator<in Char>): List<Char> {
      val var2: Array<Character> = ArraysKt.toTypedArray(var0);
      ArraysKt.sortWith(var2, var1);
      return ArraysKt.asList(var2);
   }

   @JvmStatic
   public fun DoubleArray.sortedWith(comparator: Comparator<in Double>): List<Double> {
      val var2: Array<java.lang.Double> = ArraysKt.toTypedArray(var0);
      ArraysKt.sortWith(var2, var1);
      return ArraysKt.asList(var2);
   }

   @JvmStatic
   public fun FloatArray.sortedWith(comparator: Comparator<in Float>): List<Float> {
      val var2: Array<java.lang.Float> = ArraysKt.toTypedArray(var0);
      ArraysKt.sortWith(var2, var1);
      return ArraysKt.asList(var2);
   }

   @JvmStatic
   public fun IntArray.sortedWith(comparator: Comparator<in Int>): List<Int> {
      val var2: Array<Int> = ArraysKt.toTypedArray(var0);
      ArraysKt.sortWith(var2, var1);
      return ArraysKt.asList(var2);
   }

   @JvmStatic
   public fun LongArray.sortedWith(comparator: Comparator<in Long>): List<Long> {
      val var2: Array<java.lang.Long> = ArraysKt.toTypedArray(var0);
      ArraysKt.sortWith(var2, var1);
      return ArraysKt.asList(var2);
   }

   @JvmStatic
   public fun <T> Array<out T>.sortedWith(comparator: Comparator<in T>): List<T> {
      return (java.util.List<T>)ArraysKt.asList(ArraysKt.sortedArrayWith(var0, var1));
   }

   @JvmStatic
   public fun ShortArray.sortedWith(comparator: Comparator<in Short>): List<Short> {
      val var2: Array<java.lang.Short> = ArraysKt.toTypedArray(var0);
      ArraysKt.sortWith(var2, var1);
      return ArraysKt.asList(var2);
   }

   @JvmStatic
   public fun BooleanArray.sortedWith(comparator: Comparator<in Boolean>): List<Boolean> {
      val var2: Array<java.lang.Boolean> = ArraysKt.toTypedArray(var0);
      ArraysKt.sortWith(var2, var1);
      return ArraysKt.asList(var2);
   }

   @JvmStatic
   public infix fun ByteArray.subtract(other: Iterable<Byte>): Set<Byte> {
      val var2: java.util.Set = ArraysKt.toMutableSet(var0);
      CollectionsKt.removeAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public infix fun CharArray.subtract(other: Iterable<Char>): Set<Char> {
      val var2: java.util.Set = ArraysKt.toMutableSet(var0);
      CollectionsKt.removeAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public infix fun DoubleArray.subtract(other: Iterable<Double>): Set<Double> {
      val var2: java.util.Set = ArraysKt.toMutableSet(var0);
      CollectionsKt.removeAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public infix fun FloatArray.subtract(other: Iterable<Float>): Set<Float> {
      val var2: java.util.Set = ArraysKt.toMutableSet(var0);
      CollectionsKt.removeAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public infix fun IntArray.subtract(other: Iterable<Int>): Set<Int> {
      val var2: java.util.Set = ArraysKt.toMutableSet(var0);
      CollectionsKt.removeAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public infix fun LongArray.subtract(other: Iterable<Long>): Set<Long> {
      val var2: java.util.Set = ArraysKt.toMutableSet(var0);
      CollectionsKt.removeAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public infix fun <T> Array<out T>.subtract(other: Iterable<T>): Set<T> {
      val var2: java.util.Set = ArraysKt.toMutableSet(var0);
      CollectionsKt.removeAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public infix fun ShortArray.subtract(other: Iterable<Short>): Set<Short> {
      val var2: java.util.Set = ArraysKt.toMutableSet(var0);
      CollectionsKt.removeAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public infix fun BooleanArray.subtract(other: Iterable<Boolean>): Set<Boolean> {
      val var2: java.util.Set = ArraysKt.toMutableSet(var0);
      CollectionsKt.removeAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public fun DoubleArray.sum(): Double {
      val var4: Int = var0.length;
      var var1: Double = 0.0;

      for (int var3 = 0; var3 < var4; var3++) {
         var1 += var0[var3];
      }

      return var1;
   }

   @JvmStatic
   public fun FloatArray.sum(): Float {
      val var3: Int = var0.length;
      var var1: Float = 0.0F;

      for (int var2 = 0; var2 < var3; var2++) {
         var1 += var0[var2];
      }

      return var1;
   }

   @JvmStatic
   public fun ByteArray.sum(): Int {
      val var3: Int = var0.length;
      var var2: Int = 0;

      var var1: Int;
      for (var1 = 0; var2 < var3; var2++) {
         var1 += var0[var2];
      }

      return var1;
   }

   @JvmStatic
   public fun IntArray.sum(): Int {
      val var3: Int = var0.length;
      var var2: Int = 0;

      var var1: Int;
      for (var1 = 0; var2 < var3; var2++) {
         var1 += var0[var2];
      }

      return var1;
   }

   @JvmStatic
   public fun ShortArray.sum(): Int {
      val var3: Int = var0.length;
      var var1: Int = 0;

      var var2: Int;
      for (var2 = 0; var1 < var3; var1++) {
         var2 += var0[var1];
      }

      return var2;
   }

   @JvmStatic
   public fun LongArray.sum(): Long {
      val var2: Int = var0.length;
      var var3: Long = 0L;

      for (int var1 = 0; var1 < var2; var1++) {
         var3 += var0[var1];
      }

      return var3;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun ByteArray.sumBy(selector: (Byte) -> Int): Int {
      val var4: Int = var0.length;
      var var3: Int = 0;

      var var2: Int;
      for (var2 = 0; var3 < var4; var3++) {
         var2 += (var1.invoke(var0[var3]) as java.lang.Number).intValue();
      }

      return var2;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun CharArray.sumBy(selector: (Char) -> Int): Int {
      val var4: Int = var0.length;
      var var2: Int = 0;

      var var3: Int;
      for (var3 = 0; var2 < var4; var2++) {
         var3 += (var1.invoke(var0[var2]) as java.lang.Number).intValue();
      }

      return var3;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun DoubleArray.sumBy(selector: (Double) -> Int): Int {
      val var4: Int = var0.length;
      var var2: Int = 0;

      var var3: Int;
      for (var3 = 0; var2 < var4; var2++) {
         var3 += (var1.invoke(var0[var2]) as java.lang.Number).intValue();
      }

      return var3;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun FloatArray.sumBy(selector: (Float) -> Int): Int {
      val var4: Int = var0.length;
      var var2: Int = 0;

      var var3: Int;
      for (var3 = 0; var2 < var4; var2++) {
         var3 += (var1.invoke(var0[var2]) as java.lang.Number).intValue();
      }

      return var3;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun IntArray.sumBy(selector: (Int) -> Int): Int {
      val var4: Int = var0.length;
      var var3: Int = 0;

      var var2: Int;
      for (var2 = 0; var3 < var4; var3++) {
         var2 += (var1.invoke(var0[var3]) as java.lang.Number).intValue();
      }

      return var2;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun LongArray.sumBy(selector: (Long) -> Int): Int {
      val var4: Int = var0.length;
      var var3: Int = 0;

      var var2: Int;
      for (var2 = 0; var3 < var4; var3++) {
         var2 += (var1.invoke(var0[var3]) as java.lang.Number).intValue();
      }

      return var2;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun <T> Array<out T>.sumBy(selector: (T) -> Int): Int {
      val var4: Int = var0.length;
      var var3: Int = 0;

      var var2: Int;
      for (var2 = 0; var3 < var4; var3++) {
         var2 += (var1.invoke(var0[var3]) as java.lang.Number).intValue();
      }

      return var2;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun ShortArray.sumBy(selector: (Short) -> Int): Int {
      val var4: Int = var0.length;
      var var3: Int = 0;

      var var2: Int;
      for (var2 = 0; var3 < var4; var3++) {
         var2 += (var1.invoke(var0[var3]) as java.lang.Number).intValue();
      }

      return var2;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun BooleanArray.sumBy(selector: (Boolean) -> Int): Int {
      val var4: Int = var0.length;
      var var2: Int = 0;

      var var3: Int;
      for (var3 = 0; var2 < var4; var2++) {
         var3 += (var1.invoke(var0[var2]) as java.lang.Number).intValue();
      }

      return var3;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun ByteArray.sumByDouble(selector: (Byte) -> Double): Double {
      val var5: Int = var0.length;
      var var2: Double = 0.0;

      for (int var4 = 0; var4 < var5; var4++) {
         var2 += (var1.invoke(var0[var4]) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun CharArray.sumByDouble(selector: (Char) -> Double): Double {
      val var5: Int = var0.length;
      var var2: Double = 0.0;

      for (int var4 = 0; var4 < var5; var4++) {
         var2 += (var1.invoke(var0[var4]) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun DoubleArray.sumByDouble(selector: (Double) -> Double): Double {
      val var5: Int = var0.length;
      var var2: Double = 0.0;

      for (int var4 = 0; var4 < var5; var4++) {
         var2 += (var1.invoke(var0[var4]) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun FloatArray.sumByDouble(selector: (Float) -> Double): Double {
      val var5: Int = var0.length;
      var var2: Double = 0.0;

      for (int var4 = 0; var4 < var5; var4++) {
         var2 += (var1.invoke(var0[var4]) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun IntArray.sumByDouble(selector: (Int) -> Double): Double {
      val var5: Int = var0.length;
      var var2: Double = 0.0;

      for (int var4 = 0; var4 < var5; var4++) {
         var2 += (var1.invoke(var0[var4]) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun LongArray.sumByDouble(selector: (Long) -> Double): Double {
      val var5: Int = var0.length;
      var var2: Double = 0.0;

      for (int var4 = 0; var4 < var5; var4++) {
         var2 += (var1.invoke(var0[var4]) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun <T> Array<out T>.sumByDouble(selector: (T) -> Double): Double {
      val var5: Int = var0.length;
      var var2: Double = 0.0;

      for (int var4 = 0; var4 < var5; var4++) {
         var2 += (var1.invoke(var0[var4]) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun ShortArray.sumByDouble(selector: (Short) -> Double): Double {
      val var5: Int = var0.length;
      var var2: Double = 0.0;

      for (int var4 = 0; var4 < var5; var4++) {
         var2 += (var1.invoke(var0[var4]) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun BooleanArray.sumByDouble(selector: (Boolean) -> Double): Double {
      val var5: Int = var0.length;
      var var2: Double = 0.0;

      for (int var4 = 0; var4 < var5; var4++) {
         var2 += (var1.invoke(var0[var4]) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @JvmStatic
   public fun Array<out Byte>.sum(): Int {
      val var3: Int = var0.length;
      var var2: Int = 0;

      var var1: Int;
      for (var1 = 0; var2 < var3; var2++) {
         var1 += var0[var2];
      }

      return var1;
   }

   @JvmStatic
   public inline fun ByteArray.sumOf(selector: (Byte) -> Double): Double {
      val var5: Int = var0.length;
      var var2: Double = 0.0;

      for (int var4 = 0; var4 < var5; var4++) {
         var2 += (var1.invoke(var0[var4]) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @JvmStatic
   public inline fun CharArray.sumOf(selector: (Char) -> Double): Double {
      val var5: Int = var0.length;
      var var2: Double = 0.0;

      for (int var4 = 0; var4 < var5; var4++) {
         var2 += (var1.invoke(var0[var4]) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @JvmStatic
   public inline fun DoubleArray.sumOf(selector: (Double) -> Double): Double {
      val var5: Int = var0.length;
      var var2: Double = 0.0;

      for (int var4 = 0; var4 < var5; var4++) {
         var2 += (var1.invoke(var0[var4]) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @JvmStatic
   public inline fun FloatArray.sumOf(selector: (Float) -> Double): Double {
      val var5: Int = var0.length;
      var var2: Double = 0.0;

      for (int var4 = 0; var4 < var5; var4++) {
         var2 += (var1.invoke(var0[var4]) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @JvmStatic
   public inline fun IntArray.sumOf(selector: (Int) -> Double): Double {
      val var5: Int = var0.length;
      var var2: Double = 0.0;

      for (int var4 = 0; var4 < var5; var4++) {
         var2 += (var1.invoke(var0[var4]) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @JvmStatic
   public inline fun LongArray.sumOf(selector: (Long) -> Double): Double {
      val var5: Int = var0.length;
      var var2: Double = 0.0;

      for (int var4 = 0; var4 < var5; var4++) {
         var2 += (var1.invoke(var0[var4]) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @JvmStatic
   public fun Array<out Double>.sum(): Double {
      val var4: Int = var0.length;
      var var1: Double = 0.0;

      for (int var3 = 0; var3 < var4; var3++) {
         var1 += var0[var3];
      }

      return var1;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.sumOf(selector: (T) -> Double): Double {
      val var5: Int = var0.length;
      var var2: Double = 0.0;

      for (int var4 = 0; var4 < var5; var4++) {
         var2 += (var1.invoke(var0[var4]) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @JvmStatic
   public inline fun ShortArray.sumOf(selector: (Short) -> Double): Double {
      val var5: Int = var0.length;
      var var2: Double = 0.0;

      for (int var4 = 0; var4 < var5; var4++) {
         var2 += (var1.invoke(var0[var4]) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @JvmStatic
   public inline fun BooleanArray.sumOf(selector: (Boolean) -> Double): Double {
      val var5: Int = var0.length;
      var var2: Double = 0.0;

      for (int var4 = 0; var4 < var5; var4++) {
         var2 += (var1.invoke(var0[var4]) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @JvmStatic
   public fun Array<out Float>.sum(): Float {
      val var3: Int = var0.length;
      var var1: Float = 0.0F;

      for (int var2 = 0; var2 < var3; var2++) {
         var1 += var0[var2];
      }

      return var1;
   }

   @JvmStatic
   public inline fun ByteArray.sumOf(selector: (Byte) -> Int): Int {
      val var4: Int = var0.length;
      var var2: Int = 0;

      var var3: Int;
      for (var3 = 0; var2 < var4; var2++) {
         var3 += (var1.invoke(var0[var2]) as java.lang.Number).intValue();
      }

      return var3;
   }

   @JvmStatic
   public inline fun CharArray.sumOf(selector: (Char) -> Int): Int {
      val var4: Int = var0.length;
      var var3: Int = 0;

      var var2: Int;
      for (var2 = 0; var3 < var4; var3++) {
         var2 += (var1.invoke(var0[var3]) as java.lang.Number).intValue();
      }

      return var2;
   }

   @JvmStatic
   public inline fun DoubleArray.sumOf(selector: (Double) -> Int): Int {
      val var4: Int = var0.length;
      var var3: Int = 0;

      var var2: Int;
      for (var2 = 0; var3 < var4; var3++) {
         var2 += (var1.invoke(var0[var3]) as java.lang.Number).intValue();
      }

      return var2;
   }

   @JvmStatic
   public inline fun FloatArray.sumOf(selector: (Float) -> Int): Int {
      val var4: Int = var0.length;
      var var3: Int = 0;

      var var2: Int;
      for (var2 = 0; var3 < var4; var3++) {
         var2 += (var1.invoke(var0[var3]) as java.lang.Number).intValue();
      }

      return var2;
   }

   @JvmStatic
   public inline fun IntArray.sumOf(selector: (Int) -> Int): Int {
      val var4: Int = var0.length;
      var var2: Int = 0;

      var var3: Int;
      for (var3 = 0; var2 < var4; var2++) {
         var3 += (var1.invoke(var0[var2]) as java.lang.Number).intValue();
      }

      return var3;
   }

   @JvmStatic
   public inline fun LongArray.sumOf(selector: (Long) -> Int): Int {
      val var4: Int = var0.length;
      var var3: Int = 0;

      var var2: Int;
      for (var2 = 0; var3 < var4; var3++) {
         var2 += (var1.invoke(var0[var3]) as java.lang.Number).intValue();
      }

      return var2;
   }

   @JvmStatic
   public fun Array<out Int>.sum(): Int {
      val var3: Int = var0.length;
      var var2: Int = 0;

      var var1: Int;
      for (var1 = 0; var2 < var3; var2++) {
         var1 += var0[var2];
      }

      return var1;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.sumOf(selector: (T) -> Int): Int {
      val var4: Int = var0.length;
      var var2: Int = 0;

      var var3: Int;
      for (var3 = 0; var2 < var4; var2++) {
         var3 += (var1.invoke(var0[var2]) as java.lang.Number).intValue();
      }

      return var3;
   }

   @JvmStatic
   public inline fun ShortArray.sumOf(selector: (Short) -> Int): Int {
      val var4: Int = var0.length;
      var var2: Int = 0;

      var var3: Int;
      for (var3 = 0; var2 < var4; var2++) {
         var3 += (var1.invoke(var0[var2]) as java.lang.Number).intValue();
      }

      return var3;
   }

   @JvmStatic
   public inline fun BooleanArray.sumOf(selector: (Boolean) -> Int): Int {
      val var4: Int = var0.length;
      var var3: Int = 0;

      var var2: Int;
      for (var2 = 0; var3 < var4; var3++) {
         var2 += (var1.invoke(var0[var3]) as java.lang.Number).intValue();
      }

      return var2;
   }

   @JvmStatic
   public inline fun ByteArray.sumOf(selector: (Byte) -> Long): Long {
      val var3: Int = var0.length;
      var var4: Long = 0L;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 += (var1.invoke(var0[var2]) as java.lang.Number).longValue();
      }

      return var4;
   }

   @JvmStatic
   public inline fun CharArray.sumOf(selector: (Char) -> Long): Long {
      val var3: Int = var0.length;
      var var4: Long = 0L;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 += (var1.invoke(var0[var2]) as java.lang.Number).longValue();
      }

      return var4;
   }

   @JvmStatic
   public inline fun DoubleArray.sumOf(selector: (Double) -> Long): Long {
      val var3: Int = var0.length;
      var var4: Long = 0L;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 += (var1.invoke(var0[var2]) as java.lang.Number).longValue();
      }

      return var4;
   }

   @JvmStatic
   public inline fun FloatArray.sumOf(selector: (Float) -> Long): Long {
      val var3: Int = var0.length;
      var var4: Long = 0L;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 += (var1.invoke(var0[var2]) as java.lang.Number).longValue();
      }

      return var4;
   }

   @JvmStatic
   public inline fun IntArray.sumOf(selector: (Int) -> Long): Long {
      val var3: Int = var0.length;
      var var4: Long = 0L;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 += (var1.invoke(var0[var2]) as java.lang.Number).longValue();
      }

      return var4;
   }

   @JvmStatic
   public inline fun LongArray.sumOf(selector: (Long) -> Long): Long {
      val var3: Int = var0.length;
      var var4: Long = 0L;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 += (var1.invoke(var0[var2]) as java.lang.Number).longValue();
      }

      return var4;
   }

   @JvmStatic
   public fun Array<out Long>.sum(): Long {
      val var2: Int = var0.length;
      var var3: Long = 0L;

      for (int var1 = 0; var1 < var2; var1++) {
         var3 += var0[var1];
      }

      return var3;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.sumOf(selector: (T) -> Long): Long {
      val var3: Int = var0.length;
      var var4: Long = 0L;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 += (var1.invoke(var0[var2]) as java.lang.Number).longValue();
      }

      return var4;
   }

   @JvmStatic
   public inline fun ShortArray.sumOf(selector: (Short) -> Long): Long {
      val var3: Int = var0.length;
      var var4: Long = 0L;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 += (var1.invoke(var0[var2]) as java.lang.Number).longValue();
      }

      return var4;
   }

   @JvmStatic
   public inline fun BooleanArray.sumOf(selector: (Boolean) -> Long): Long {
      val var3: Int = var0.length;
      var var4: Long = 0L;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 += (var1.invoke(var0[var2]) as java.lang.Number).longValue();
      }

      return var4;
   }

   @JvmStatic
   public fun Array<out Short>.sum(): Int {
      val var3: Int = var0.length;
      var var1: Int = 0;

      var var2: Int;
      for (var2 = 0; var1 < var3; var1++) {
         var2 += var0[var1];
      }

      return var2;
   }

   @JvmStatic
   public inline fun ByteArray.sumOf(selector: (Byte) -> UInt): UInt {
      var var3: Int = 0;
      var var2: Int = UInt.constructor-impl(0);

      for (int var4 = var0.length; var3 < var4; var3++) {
         var2 = UInt.constructor-impl(var2 + (var1.invoke(var0[var3]) as UInt).unbox-impl());
      }

      return var2;
   }

   @JvmStatic
   public inline fun CharArray.sumOf(selector: (Char) -> UInt): UInt {
      var var2: Int = 0;
      var var3: Int = UInt.constructor-impl(0);

      for (int var4 = var0.length; var2 < var4; var2++) {
         var3 = UInt.constructor-impl(var3 + (var1.invoke(var0[var2]) as UInt).unbox-impl());
      }

      return var3;
   }

   @JvmStatic
   public inline fun DoubleArray.sumOf(selector: (Double) -> UInt): UInt {
      var var2: Int = 0;
      var var3: Int = UInt.constructor-impl(0);

      for (int var4 = var0.length; var2 < var4; var2++) {
         var3 = UInt.constructor-impl(var3 + (var1.invoke(var0[var2]) as UInt).unbox-impl());
      }

      return var3;
   }

   @JvmStatic
   public inline fun FloatArray.sumOf(selector: (Float) -> UInt): UInt {
      var var2: Int = 0;
      var var3: Int = UInt.constructor-impl(0);

      for (int var4 = var0.length; var2 < var4; var2++) {
         var3 = UInt.constructor-impl(var3 + (var1.invoke(var0[var2]) as UInt).unbox-impl());
      }

      return var3;
   }

   @JvmStatic
   public inline fun IntArray.sumOf(selector: (Int) -> UInt): UInt {
      var var3: Int = 0;
      var var2: Int = UInt.constructor-impl(0);

      for (int var4 = var0.length; var3 < var4; var3++) {
         var2 = UInt.constructor-impl(var2 + (var1.invoke(var0[var3]) as UInt).unbox-impl());
      }

      return var2;
   }

   @JvmStatic
   public inline fun LongArray.sumOf(selector: (Long) -> UInt): UInt {
      var var2: Int = 0;
      var var3: Int = UInt.constructor-impl(0);

      for (int var4 = var0.length; var2 < var4; var2++) {
         var3 = UInt.constructor-impl(var3 + (var1.invoke(var0[var2]) as UInt).unbox-impl());
      }

      return var3;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.sumOf(selector: (T) -> UInt): UInt {
      var var3: Int = 0;
      var var2: Int = UInt.constructor-impl(0);

      for (int var4 = var0.length; var3 < var4; var3++) {
         var2 = UInt.constructor-impl(var2 + (var1.invoke(var0[var3]) as UInt).unbox-impl());
      }

      return var2;
   }

   @JvmStatic
   public inline fun ShortArray.sumOf(selector: (Short) -> UInt): UInt {
      var var3: Int = 0;
      var var2: Int = UInt.constructor-impl(0);

      for (int var4 = var0.length; var3 < var4; var3++) {
         var2 = UInt.constructor-impl(var2 + (var1.invoke(var0[var3]) as UInt).unbox-impl());
      }

      return var2;
   }

   @JvmStatic
   public inline fun BooleanArray.sumOf(selector: (Boolean) -> UInt): UInt {
      var var3: Int = 0;
      var var2: Int = UInt.constructor-impl(0);

      for (int var4 = var0.length; var3 < var4; var3++) {
         var2 = UInt.constructor-impl(var2 + (var1.invoke(var0[var3]) as UInt).unbox-impl());
      }

      return var2;
   }

   @JvmStatic
   public inline fun ByteArray.sumOf(selector: (Byte) -> ULong): ULong {
      var var4: Long = ULong.constructor-impl(0L);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = ULong.constructor-impl(var4 + (var1.invoke(var0[var2]) as ULong).unbox-impl());
      }

      return var4;
   }

   @JvmStatic
   public inline fun CharArray.sumOf(selector: (Char) -> ULong): ULong {
      var var4: Long = ULong.constructor-impl(0L);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = ULong.constructor-impl(var4 + (var1.invoke(var0[var2]) as ULong).unbox-impl());
      }

      return var4;
   }

   @JvmStatic
   public inline fun DoubleArray.sumOf(selector: (Double) -> ULong): ULong {
      var var4: Long = ULong.constructor-impl(0L);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = ULong.constructor-impl(var4 + (var1.invoke(var0[var2]) as ULong).unbox-impl());
      }

      return var4;
   }

   @JvmStatic
   public inline fun FloatArray.sumOf(selector: (Float) -> ULong): ULong {
      var var4: Long = ULong.constructor-impl(0L);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = ULong.constructor-impl(var4 + (var1.invoke(var0[var2]) as ULong).unbox-impl());
      }

      return var4;
   }

   @JvmStatic
   public inline fun IntArray.sumOf(selector: (Int) -> ULong): ULong {
      var var4: Long = ULong.constructor-impl(0L);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = ULong.constructor-impl(var4 + (var1.invoke(var0[var2]) as ULong).unbox-impl());
      }

      return var4;
   }

   @JvmStatic
   public inline fun LongArray.sumOf(selector: (Long) -> ULong): ULong {
      var var4: Long = ULong.constructor-impl(0L);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = ULong.constructor-impl(var4 + (var1.invoke(var0[var2]) as ULong).unbox-impl());
      }

      return var4;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.sumOf(selector: (T) -> ULong): ULong {
      var var4: Long = ULong.constructor-impl(0L);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = ULong.constructor-impl(var4 + (var1.invoke(var0[var2]) as ULong).unbox-impl());
      }

      return var4;
   }

   @JvmStatic
   public inline fun ShortArray.sumOf(selector: (Short) -> ULong): ULong {
      var var4: Long = ULong.constructor-impl(0L);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = ULong.constructor-impl(var4 + (var1.invoke(var0[var2]) as ULong).unbox-impl());
      }

      return var4;
   }

   @JvmStatic
   public inline fun BooleanArray.sumOf(selector: (Boolean) -> ULong): ULong {
      var var4: Long = ULong.constructor-impl(0L);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = ULong.constructor-impl(var4 + (var1.invoke(var0[var2]) as ULong).unbox-impl());
      }

      return var4;
   }

   @JvmStatic
   public fun ByteArray.take(n: Int): List<Byte> {
      if (var1 < 0) {
         val var6: StringBuilder = new StringBuilder("Requested element count ");
         var6.append(var1);
         var6.append(" is less than zero.");
         throw new IllegalArgumentException(var6.toString().toString());
      } else if (var1 == 0) {
         return CollectionsKt.emptyList();
      } else if (var1 >= var0.length) {
         return ArraysKt.toList(var0);
      } else {
         var var3: Int = 0;
         if (var1 == 1) {
            return CollectionsKt.listOf(var0[0]);
         } else {
            val var5: ArrayList = new ArrayList(var1);
            val var4: Int = var0.length;

            for (int var2 = 0; var3 < var4; var3++) {
               var5.add(var0[var3]);
               if (++var2 == var1) {
                  break;
               }
            }

            return var5;
         }
      }
   }

   @JvmStatic
   public fun CharArray.take(n: Int): List<Char> {
      if (var1 < 0) {
         val var6: StringBuilder = new StringBuilder("Requested element count ");
         var6.append(var1);
         var6.append(" is less than zero.");
         throw new IllegalArgumentException(var6.toString().toString());
      } else if (var1 == 0) {
         return CollectionsKt.emptyList();
      } else if (var1 >= var0.length) {
         return ArraysKt.toList(var0);
      } else {
         var var3: Int = 0;
         if (var1 == 1) {
            return CollectionsKt.listOf(var0[0]);
         } else {
            val var5: ArrayList = new ArrayList(var1);
            val var4: Int = var0.length;

            for (int var2 = 0; var3 < var4; var3++) {
               var5.add(var0[var3]);
               if (++var2 == var1) {
                  break;
               }
            }

            return var5;
         }
      }
   }

   @JvmStatic
   public fun DoubleArray.take(n: Int): List<Double> {
      if (var1 < 0) {
         val var6: StringBuilder = new StringBuilder("Requested element count ");
         var6.append(var1);
         var6.append(" is less than zero.");
         throw new IllegalArgumentException(var6.toString().toString());
      } else if (var1 == 0) {
         return CollectionsKt.emptyList();
      } else if (var1 >= var0.length) {
         return ArraysKt.toList(var0);
      } else {
         var var2: Int = 0;
         if (var1 == 1) {
            return CollectionsKt.listOf(var0[0]);
         } else {
            val var5: ArrayList = new ArrayList(var1);
            val var4: Int = var0.length;

            for (int var3 = 0; var2 < var4; var2++) {
               var5.add(var0[var2]);
               if (++var3 == var1) {
                  break;
               }
            }

            return var5;
         }
      }
   }

   @JvmStatic
   public fun FloatArray.take(n: Int): List<Float> {
      if (var1 < 0) {
         val var6: StringBuilder = new StringBuilder("Requested element count ");
         var6.append(var1);
         var6.append(" is less than zero.");
         throw new IllegalArgumentException(var6.toString().toString());
      } else if (var1 == 0) {
         return CollectionsKt.emptyList();
      } else if (var1 >= var0.length) {
         return ArraysKt.toList(var0);
      } else {
         var var2: Int = 0;
         if (var1 == 1) {
            return CollectionsKt.listOf(var0[0]);
         } else {
            val var5: ArrayList = new ArrayList(var1);
            val var4: Int = var0.length;

            for (int var3 = 0; var2 < var4; var2++) {
               var5.add(var0[var2]);
               if (++var3 == var1) {
                  break;
               }
            }

            return var5;
         }
      }
   }

   @JvmStatic
   public fun IntArray.take(n: Int): List<Int> {
      if (var1 < 0) {
         val var6: StringBuilder = new StringBuilder("Requested element count ");
         var6.append(var1);
         var6.append(" is less than zero.");
         throw new IllegalArgumentException(var6.toString().toString());
      } else if (var1 == 0) {
         return CollectionsKt.emptyList();
      } else if (var1 >= var0.length) {
         return ArraysKt.toList(var0);
      } else {
         var var2: Int = 0;
         if (var1 == 1) {
            return CollectionsKt.listOf(var0[0]);
         } else {
            val var5: ArrayList = new ArrayList(var1);
            val var4: Int = var0.length;

            for (int var3 = 0; var2 < var4; var2++) {
               var5.add(var0[var2]);
               if (++var3 == var1) {
                  break;
               }
            }

            return var5;
         }
      }
   }

   @JvmStatic
   public fun LongArray.take(n: Int): List<Long> {
      if (var1 < 0) {
         val var6: StringBuilder = new StringBuilder("Requested element count ");
         var6.append(var1);
         var6.append(" is less than zero.");
         throw new IllegalArgumentException(var6.toString().toString());
      } else if (var1 == 0) {
         return CollectionsKt.emptyList();
      } else if (var1 >= var0.length) {
         return ArraysKt.toList(var0);
      } else {
         var var2: Int = 0;
         if (var1 == 1) {
            return CollectionsKt.listOf(var0[0]);
         } else {
            val var5: ArrayList = new ArrayList(var1);
            val var4: Int = var0.length;

            for (int var3 = 0; var2 < var4; var2++) {
               var5.add(var0[var2]);
               if (++var3 == var1) {
                  break;
               }
            }

            return var5;
         }
      }
   }

   @JvmStatic
   public fun <T> Array<out T>.take(n: Int): List<T> {
      if (var1 < 0) {
         val var6: StringBuilder = new StringBuilder("Requested element count ");
         var6.append(var1);
         var6.append(" is less than zero.");
         throw new IllegalArgumentException(var6.toString().toString());
      } else if (var1 == 0) {
         return CollectionsKt.emptyList();
      } else if (var1 >= var0.length) {
         return (java.util.List<T>)ArraysKt.toList(var0);
      } else {
         var var2: Int = 0;
         if (var1 == 1) {
            return (java.util.List<T>)CollectionsKt.listOf(var0[0]);
         } else {
            val var5: ArrayList = new ArrayList(var1);
            val var4: Int = var0.length;

            for (int var3 = 0; var2 < var4; var2++) {
               var5.add(var0[var2]);
               if (++var3 == var1) {
                  break;
               }
            }

            return var5;
         }
      }
   }

   @JvmStatic
   public fun ShortArray.take(n: Int): List<Short> {
      if (var1 < 0) {
         val var6: StringBuilder = new StringBuilder("Requested element count ");
         var6.append(var1);
         var6.append(" is less than zero.");
         throw new IllegalArgumentException(var6.toString().toString());
      } else if (var1 == 0) {
         return CollectionsKt.emptyList();
      } else if (var1 >= var0.length) {
         return ArraysKt.toList(var0);
      } else {
         var var2: Int = 0;
         if (var1 == 1) {
            return CollectionsKt.listOf(var0[0]);
         } else {
            val var5: ArrayList = new ArrayList(var1);
            val var4: Int = var0.length;

            for (int var3 = 0; var2 < var4; var2++) {
               var5.add(var0[var2]);
               if (++var3 == var1) {
                  break;
               }
            }

            return var5;
         }
      }
   }

   @JvmStatic
   public fun BooleanArray.take(n: Int): List<Boolean> {
      if (var1 < 0) {
         val var6: StringBuilder = new StringBuilder("Requested element count ");
         var6.append(var1);
         var6.append(" is less than zero.");
         throw new IllegalArgumentException(var6.toString().toString());
      } else if (var1 == 0) {
         return CollectionsKt.emptyList();
      } else if (var1 >= var0.length) {
         return ArraysKt.toList(var0);
      } else {
         var var3: Int = 0;
         if (var1 == 1) {
            return CollectionsKt.listOf(var0[0]);
         } else {
            val var5: ArrayList = new ArrayList(var1);
            val var4: Int = var0.length;

            for (int var2 = 0; var3 < var4; var3++) {
               var5.add(var0[var3]);
               if (++var2 == var1) {
                  break;
               }
            }

            return var5;
         }
      }
   }

   @JvmStatic
   public fun ByteArray.takeLast(n: Int): List<Byte> {
      if (var1 < 0) {
         val var4: StringBuilder = new StringBuilder("Requested element count ");
         var4.append(var1);
         var4.append(" is less than zero.");
         throw new IllegalArgumentException(var4.toString().toString());
      } else if (var1 == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var2: Int = var0.length;
         if (var1 >= var0.length) {
            return ArraysKt.toList(var0);
         } else if (var1 == 1) {
            return CollectionsKt.listOf(var0[var2 - 1]);
         } else {
            val var3: ArrayList = new ArrayList(var1);

            for (int var5 = var2 - var1; var5 < var2; var5++) {
               var3.add(var0[var5]);
            }

            return var3;
         }
      }
   }

   @JvmStatic
   public fun CharArray.takeLast(n: Int): List<Char> {
      if (var1 < 0) {
         val var4: StringBuilder = new StringBuilder("Requested element count ");
         var4.append(var1);
         var4.append(" is less than zero.");
         throw new IllegalArgumentException(var4.toString().toString());
      } else if (var1 == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var2: Int = var0.length;
         if (var1 >= var0.length) {
            return ArraysKt.toList(var0);
         } else if (var1 == 1) {
            return CollectionsKt.listOf(var0[var2 - 1]);
         } else {
            val var3: ArrayList = new ArrayList(var1);

            for (int var5 = var2 - var1; var5 < var2; var5++) {
               var3.add(var0[var5]);
            }

            return var3;
         }
      }
   }

   @JvmStatic
   public fun DoubleArray.takeLast(n: Int): List<Double> {
      if (var1 < 0) {
         val var4: StringBuilder = new StringBuilder("Requested element count ");
         var4.append(var1);
         var4.append(" is less than zero.");
         throw new IllegalArgumentException(var4.toString().toString());
      } else if (var1 == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var2: Int = var0.length;
         if (var1 >= var0.length) {
            return ArraysKt.toList(var0);
         } else if (var1 == 1) {
            return CollectionsKt.listOf(var0[var2 - 1]);
         } else {
            val var3: ArrayList = new ArrayList(var1);

            for (int var5 = var2 - var1; var5 < var2; var5++) {
               var3.add(var0[var5]);
            }

            return var3;
         }
      }
   }

   @JvmStatic
   public fun FloatArray.takeLast(n: Int): List<Float> {
      if (var1 < 0) {
         val var4: StringBuilder = new StringBuilder("Requested element count ");
         var4.append(var1);
         var4.append(" is less than zero.");
         throw new IllegalArgumentException(var4.toString().toString());
      } else if (var1 == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var2: Int = var0.length;
         if (var1 >= var0.length) {
            return ArraysKt.toList(var0);
         } else if (var1 == 1) {
            return CollectionsKt.listOf(var0[var2 - 1]);
         } else {
            val var3: ArrayList = new ArrayList(var1);

            for (int var5 = var2 - var1; var5 < var2; var5++) {
               var3.add(var0[var5]);
            }

            return var3;
         }
      }
   }

   @JvmStatic
   public fun IntArray.takeLast(n: Int): List<Int> {
      if (var1 < 0) {
         val var4: StringBuilder = new StringBuilder("Requested element count ");
         var4.append(var1);
         var4.append(" is less than zero.");
         throw new IllegalArgumentException(var4.toString().toString());
      } else if (var1 == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var2: Int = var0.length;
         if (var1 >= var0.length) {
            return ArraysKt.toList(var0);
         } else if (var1 == 1) {
            return CollectionsKt.listOf(var0[var2 - 1]);
         } else {
            val var3: ArrayList = new ArrayList(var1);

            for (int var5 = var2 - var1; var5 < var2; var5++) {
               var3.add(var0[var5]);
            }

            return var3;
         }
      }
   }

   @JvmStatic
   public fun LongArray.takeLast(n: Int): List<Long> {
      if (var1 < 0) {
         val var4: StringBuilder = new StringBuilder("Requested element count ");
         var4.append(var1);
         var4.append(" is less than zero.");
         throw new IllegalArgumentException(var4.toString().toString());
      } else if (var1 == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var2: Int = var0.length;
         if (var1 >= var0.length) {
            return ArraysKt.toList(var0);
         } else if (var1 == 1) {
            return CollectionsKt.listOf(var0[var2 - 1]);
         } else {
            val var3: ArrayList = new ArrayList(var1);

            for (int var5 = var2 - var1; var5 < var2; var5++) {
               var3.add(var0[var5]);
            }

            return var3;
         }
      }
   }

   @JvmStatic
   public fun <T> Array<out T>.takeLast(n: Int): List<T> {
      if (var1 < 0) {
         val var4: StringBuilder = new StringBuilder("Requested element count ");
         var4.append(var1);
         var4.append(" is less than zero.");
         throw new IllegalArgumentException(var4.toString().toString());
      } else if (var1 == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var2: Int = var0.length;
         if (var1 >= var0.length) {
            return (java.util.List<T>)ArraysKt.toList(var0);
         } else if (var1 == 1) {
            return (java.util.List<T>)CollectionsKt.listOf(var0[var2 - 1]);
         } else {
            val var3: ArrayList = new ArrayList(var1);

            for (int var5 = var2 - var1; var5 < var2; var5++) {
               var3.add(var0[var5]);
            }

            return var3;
         }
      }
   }

   @JvmStatic
   public fun ShortArray.takeLast(n: Int): List<Short> {
      if (var1 < 0) {
         val var4: StringBuilder = new StringBuilder("Requested element count ");
         var4.append(var1);
         var4.append(" is less than zero.");
         throw new IllegalArgumentException(var4.toString().toString());
      } else if (var1 == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var2: Int = var0.length;
         if (var1 >= var0.length) {
            return ArraysKt.toList(var0);
         } else if (var1 == 1) {
            return CollectionsKt.listOf(var0[var2 - 1]);
         } else {
            val var3: ArrayList = new ArrayList(var1);

            for (int var5 = var2 - var1; var5 < var2; var5++) {
               var3.add(var0[var5]);
            }

            return var3;
         }
      }
   }

   @JvmStatic
   public fun BooleanArray.takeLast(n: Int): List<Boolean> {
      if (var1 < 0) {
         val var4: StringBuilder = new StringBuilder("Requested element count ");
         var4.append(var1);
         var4.append(" is less than zero.");
         throw new IllegalArgumentException(var4.toString().toString());
      } else if (var1 == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var2: Int = var0.length;
         if (var1 >= var0.length) {
            return ArraysKt.toList(var0);
         } else if (var1 == 1) {
            return CollectionsKt.listOf(var0[var2 - 1]);
         } else {
            val var3: ArrayList = new ArrayList(var1);

            for (int var5 = var2 - var1; var5 < var2; var5++) {
               var3.add(var0[var5]);
            }

            return var3;
         }
      }
   }

   @JvmStatic
   public inline fun ByteArray.takeLastWhile(predicate: (Byte) -> Boolean): List<Byte> {
      for (int var2 = ArraysKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            return ArraysKt.drop(var0, var2 + 1);
         }
      }

      return ArraysKt.toList(var0);
   }

   @JvmStatic
   public inline fun CharArray.takeLastWhile(predicate: (Char) -> Boolean): List<Char> {
      for (int var2 = ArraysKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            return ArraysKt.drop(var0, var2 + 1);
         }
      }

      return ArraysKt.toList(var0);
   }

   @JvmStatic
   public inline fun DoubleArray.takeLastWhile(predicate: (Double) -> Boolean): List<Double> {
      for (int var2 = ArraysKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            return ArraysKt.drop(var0, var2 + 1);
         }
      }

      return ArraysKt.toList(var0);
   }

   @JvmStatic
   public inline fun FloatArray.takeLastWhile(predicate: (Float) -> Boolean): List<Float> {
      for (int var2 = ArraysKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            return ArraysKt.drop(var0, var2 + 1);
         }
      }

      return ArraysKt.toList(var0);
   }

   @JvmStatic
   public inline fun IntArray.takeLastWhile(predicate: (Int) -> Boolean): List<Int> {
      for (int var2 = ArraysKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            return ArraysKt.drop(var0, var2 + 1);
         }
      }

      return ArraysKt.toList(var0);
   }

   @JvmStatic
   public inline fun LongArray.takeLastWhile(predicate: (Long) -> Boolean): List<Long> {
      for (int var2 = ArraysKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            return ArraysKt.drop(var0, var2 + 1);
         }
      }

      return ArraysKt.toList(var0);
   }

   @JvmStatic
   public inline fun <T> Array<out T>.takeLastWhile(predicate: (T) -> Boolean): List<T> {
      for (int var2 = ArraysKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            return (java.util.List<T>)ArraysKt.drop(var0, var2 + 1);
         }
      }

      return (java.util.List<T>)ArraysKt.toList(var0);
   }

   @JvmStatic
   public inline fun ShortArray.takeLastWhile(predicate: (Short) -> Boolean): List<Short> {
      for (int var2 = ArraysKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            return ArraysKt.drop(var0, var2 + 1);
         }
      }

      return ArraysKt.toList(var0);
   }

   @JvmStatic
   public inline fun BooleanArray.takeLastWhile(predicate: (Boolean) -> Boolean): List<Boolean> {
      for (int var2 = ArraysKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            return ArraysKt.drop(var0, var2 + 1);
         }
      }

      return ArraysKt.toList(var0);
   }

   @JvmStatic
   public inline fun ByteArray.takeWhile(predicate: (Byte) -> Boolean): List<Byte> {
      val var5: ArrayList = new ArrayList();
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Byte = var0[var3];
         if (!var1.invoke(var0[var3]) as java.lang.Boolean) {
            break;
         }

         var5.add(var2);
      }

      return var5;
   }

   @JvmStatic
   public inline fun CharArray.takeWhile(predicate: (Char) -> Boolean): List<Char> {
      val var5: ArrayList = new ArrayList();
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Char = var0[var3];
         if (!var1.invoke(var0[var3]) as java.lang.Boolean) {
            break;
         }

         var5.add(var2);
      }

      return var5;
   }

   @JvmStatic
   public inline fun DoubleArray.takeWhile(predicate: (Double) -> Boolean): List<Double> {
      val var6: ArrayList = new ArrayList();
      val var5: Int = var0.length;

      for (int var4 = 0; var4 < var5; var4++) {
         val var2: Double = var0[var4];
         if (!var1.invoke(var0[var4]) as java.lang.Boolean) {
            break;
         }

         var6.add(var2);
      }

      return var6;
   }

   @JvmStatic
   public inline fun FloatArray.takeWhile(predicate: (Float) -> Boolean): List<Float> {
      val var5: ArrayList = new ArrayList();
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Float = var0[var3];
         if (!var1.invoke(var0[var3]) as java.lang.Boolean) {
            break;
         }

         var5.add(var2);
      }

      return var5;
   }

   @JvmStatic
   public inline fun IntArray.takeWhile(predicate: (Int) -> Boolean): List<Int> {
      val var5: ArrayList = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Int = var0[var2];
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            break;
         }

         var5.add(var4);
      }

      return var5;
   }

   @JvmStatic
   public inline fun LongArray.takeWhile(predicate: (Long) -> Boolean): List<Long> {
      val var6: ArrayList = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Long = var0[var2];
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            break;
         }

         var6.add(var4);
      }

      return var6;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.takeWhile(predicate: (T) -> Boolean): List<T> {
      val var5: ArrayList = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Any = var0[var2];
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            break;
         }

         var5.add(var4);
      }

      return var5;
   }

   @JvmStatic
   public inline fun ShortArray.takeWhile(predicate: (Short) -> Boolean): List<Short> {
      val var5: ArrayList = new ArrayList();
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Short = var0[var3];
         if (!var1.invoke(var0[var3]) as java.lang.Boolean) {
            break;
         }

         var5.add(var2);
      }

      return var5;
   }

   @JvmStatic
   public inline fun BooleanArray.takeWhile(predicate: (Boolean) -> Boolean): List<Boolean> {
      val var5: ArrayList = new ArrayList();
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Boolean = var0[var2];
         if (!var1.invoke(var0[var2]) as java.lang.Boolean) {
            break;
         }

         var5.add(var4);
      }

      return var5;
   }

   @JvmStatic
   public fun Array<out Boolean>.toBooleanArray(): BooleanArray {
      val var2: Int = var0.length;
      val var3: BooleanArray = new boolean[var0.length];

      for (int var1 = 0; var1 < var2; var1++) {
         var3[var1] = var0[var1];
      }

      return var3;
   }

   @JvmStatic
   public fun Array<out Byte>.toByteArray(): ByteArray {
      val var2: Int = var0.length;
      val var3: ByteArray = new byte[var0.length];

      for (int var1 = 0; var1 < var2; var1++) {
         var3[var1] = var0[var1];
      }

      return var3;
   }

   @JvmStatic
   public fun Array<out Char>.toCharArray(): CharArray {
      val var2: Int = var0.length;
      val var3: CharArray = new char[var0.length];

      for (int var1 = 0; var1 < var2; var1++) {
         var3[var1] = var0[var1];
      }

      return var3;
   }

   @JvmStatic
   public fun <C : MutableCollection<in Byte>> ByteArray.toCollection(destination: C): C {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var1.add(var0[var2]);
      }

      return (C)var1;
   }

   @JvmStatic
   public fun <C : MutableCollection<in Char>> CharArray.toCollection(destination: C): C {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var1.add(var0[var2]);
      }

      return (C)var1;
   }

   @JvmStatic
   public fun <C : MutableCollection<in Double>> DoubleArray.toCollection(destination: C): C {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var1.add(var0[var2]);
      }

      return (C)var1;
   }

   @JvmStatic
   public fun <C : MutableCollection<in Float>> FloatArray.toCollection(destination: C): C {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var1.add(var0[var2]);
      }

      return (C)var1;
   }

   @JvmStatic
   public fun <C : MutableCollection<in Int>> IntArray.toCollection(destination: C): C {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var1.add(var0[var2]);
      }

      return (C)var1;
   }

   @JvmStatic
   public fun <C : MutableCollection<in Long>> LongArray.toCollection(destination: C): C {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var1.add(var0[var2]);
      }

      return (C)var1;
   }

   @JvmStatic
   public fun <T, C : MutableCollection<in T>> Array<out T>.toCollection(destination: C): C {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var1.add(var0[var2]);
      }

      return (C)var1;
   }

   @JvmStatic
   public fun <C : MutableCollection<in Short>> ShortArray.toCollection(destination: C): C {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var1.add(var0[var2]);
      }

      return (C)var1;
   }

   @JvmStatic
   public fun <C : MutableCollection<in Boolean>> BooleanArray.toCollection(destination: C): C {
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var1.add(var0[var2]);
      }

      return (C)var1;
   }

   @JvmStatic
   public fun Array<out Double>.toDoubleArray(): DoubleArray {
      val var2: Int = var0.length;
      val var3: DoubleArray = new double[var0.length];

      for (int var1 = 0; var1 < var2; var1++) {
         var3[var1] = var0[var1];
      }

      return var3;
   }

   @JvmStatic
   public fun Array<out Float>.toFloatArray(): FloatArray {
      val var2: Int = var0.length;
      val var3: FloatArray = new float[var0.length];

      for (int var1 = 0; var1 < var2; var1++) {
         var3[var1] = var0[var1];
      }

      return var3;
   }

   @JvmStatic
   public fun ByteArray.toHashSet(): HashSet<Byte> {
      return ArraysKt.toCollection(var0, new HashSet<>(MapsKt.mapCapacity(var0.length)));
   }

   @JvmStatic
   public fun CharArray.toHashSet(): HashSet<Char> {
      return ArraysKt.toCollection(var0, new HashSet<>(MapsKt.mapCapacity(RangesKt.coerceAtMost(var0.length, 128))));
   }

   @JvmStatic
   public fun DoubleArray.toHashSet(): HashSet<Double> {
      return ArraysKt.toCollection(var0, new HashSet<>(MapsKt.mapCapacity(var0.length)));
   }

   @JvmStatic
   public fun FloatArray.toHashSet(): HashSet<Float> {
      return ArraysKt.toCollection(var0, new HashSet<>(MapsKt.mapCapacity(var0.length)));
   }

   @JvmStatic
   public fun IntArray.toHashSet(): HashSet<Int> {
      return ArraysKt.toCollection(var0, new HashSet<>(MapsKt.mapCapacity(var0.length)));
   }

   @JvmStatic
   public fun LongArray.toHashSet(): HashSet<Long> {
      return ArraysKt.toCollection(var0, new HashSet<>(MapsKt.mapCapacity(var0.length)));
   }

   @JvmStatic
   public fun <T> Array<out T>.toHashSet(): HashSet<T> {
      return ArraysKt.toCollection(var0, new HashSet(MapsKt.mapCapacity(var0.length))) as HashSet<T>;
   }

   @JvmStatic
   public fun ShortArray.toHashSet(): HashSet<Short> {
      return ArraysKt.toCollection(var0, new HashSet<>(MapsKt.mapCapacity(var0.length)));
   }

   @JvmStatic
   public fun BooleanArray.toHashSet(): HashSet<Boolean> {
      return ArraysKt.toCollection(var0, new HashSet<>(MapsKt.mapCapacity(var0.length)));
   }

   @JvmStatic
   public fun Array<out Int>.toIntArray(): IntArray {
      val var2: Int = var0.length;
      val var3: IntArray = new int[var0.length];

      for (int var1 = 0; var1 < var2; var1++) {
         var3[var1] = var0[var1];
      }

      return var3;
   }

   @JvmStatic
   public fun ByteArray.toList(): List<Byte> {
      val var1: Int = var0.length;
      val var2: java.util.List;
      if (var0.length != 0) {
         if (var1 != 1) {
            var2 = ArraysKt.toMutableList(var0);
         } else {
            var2 = CollectionsKt.listOf(var0[0]);
         }
      } else {
         var2 = CollectionsKt.emptyList();
      }

      return var2;
   }

   @JvmStatic
   public fun CharArray.toList(): List<Char> {
      val var1: Int = var0.length;
      val var2: java.util.List;
      if (var0.length != 0) {
         if (var1 != 1) {
            var2 = ArraysKt.toMutableList(var0);
         } else {
            var2 = CollectionsKt.listOf(var0[0]);
         }
      } else {
         var2 = CollectionsKt.emptyList();
      }

      return var2;
   }

   @JvmStatic
   public fun DoubleArray.toList(): List<Double> {
      val var1: Int = var0.length;
      val var2: java.util.List;
      if (var0.length != 0) {
         if (var1 != 1) {
            var2 = ArraysKt.toMutableList(var0);
         } else {
            var2 = CollectionsKt.listOf(var0[0]);
         }
      } else {
         var2 = CollectionsKt.emptyList();
      }

      return var2;
   }

   @JvmStatic
   public fun FloatArray.toList(): List<Float> {
      val var1: Int = var0.length;
      val var2: java.util.List;
      if (var0.length != 0) {
         if (var1 != 1) {
            var2 = ArraysKt.toMutableList(var0);
         } else {
            var2 = CollectionsKt.listOf(var0[0]);
         }
      } else {
         var2 = CollectionsKt.emptyList();
      }

      return var2;
   }

   @JvmStatic
   public fun IntArray.toList(): List<Int> {
      val var1: Int = var0.length;
      val var2: java.util.List;
      if (var0.length != 0) {
         if (var1 != 1) {
            var2 = ArraysKt.toMutableList(var0);
         } else {
            var2 = CollectionsKt.listOf(var0[0]);
         }
      } else {
         var2 = CollectionsKt.emptyList();
      }

      return var2;
   }

   @JvmStatic
   public fun LongArray.toList(): List<Long> {
      val var1: Int = var0.length;
      val var2: java.util.List;
      if (var0.length != 0) {
         if (var1 != 1) {
            var2 = ArraysKt.toMutableList(var0);
         } else {
            var2 = CollectionsKt.listOf(var0[0]);
         }
      } else {
         var2 = CollectionsKt.emptyList();
      }

      return var2;
   }

   @JvmStatic
   public fun <T> Array<out T>.toList(): List<T> {
      val var1: Int = var0.length;
      val var2: java.util.List;
      if (var0.length != 0) {
         if (var1 != 1) {
            var2 = ArraysKt.toMutableList(var0);
         } else {
            var2 = CollectionsKt.listOf(var0[0]);
         }
      } else {
         var2 = CollectionsKt.emptyList();
      }

      return var2;
   }

   @JvmStatic
   public fun ShortArray.toList(): List<Short> {
      val var1: Int = var0.length;
      val var2: java.util.List;
      if (var0.length != 0) {
         if (var1 != 1) {
            var2 = ArraysKt.toMutableList(var0);
         } else {
            var2 = CollectionsKt.listOf(var0[0]);
         }
      } else {
         var2 = CollectionsKt.emptyList();
      }

      return var2;
   }

   @JvmStatic
   public fun BooleanArray.toList(): List<Boolean> {
      val var1: Int = var0.length;
      val var2: java.util.List;
      if (var0.length != 0) {
         if (var1 != 1) {
            var2 = ArraysKt.toMutableList(var0);
         } else {
            var2 = CollectionsKt.listOf(var0[0]);
         }
      } else {
         var2 = CollectionsKt.emptyList();
      }

      return var2;
   }

   @JvmStatic
   public fun Array<out Long>.toLongArray(): LongArray {
      val var2: Int = var0.length;
      val var3: LongArray = new long[var0.length];

      for (int var1 = 0; var1 < var2; var1++) {
         var3[var1] = var0[var1];
      }

      return var3;
   }

   @JvmStatic
   public fun ByteArray.toMutableList(): MutableList<Byte> {
      val var3: ArrayList = new ArrayList(var0.length);
      val var2: Int = var0.length;

      for (int var1 = 0; var1 < var2; var1++) {
         var3.add(var0[var1]);
      }

      return var3;
   }

   @JvmStatic
   public fun CharArray.toMutableList(): MutableList<Char> {
      val var3: ArrayList = new ArrayList(var0.length);
      val var2: Int = var0.length;

      for (int var1 = 0; var1 < var2; var1++) {
         var3.add(var0[var1]);
      }

      return var3;
   }

   @JvmStatic
   public fun DoubleArray.toMutableList(): MutableList<Double> {
      val var3: ArrayList = new ArrayList(var0.length);
      val var2: Int = var0.length;

      for (int var1 = 0; var1 < var2; var1++) {
         var3.add(var0[var1]);
      }

      return var3;
   }

   @JvmStatic
   public fun FloatArray.toMutableList(): MutableList<Float> {
      val var3: ArrayList = new ArrayList(var0.length);
      val var2: Int = var0.length;

      for (int var1 = 0; var1 < var2; var1++) {
         var3.add(var0[var1]);
      }

      return var3;
   }

   @JvmStatic
   public fun IntArray.toMutableList(): MutableList<Int> {
      val var3: ArrayList = new ArrayList(var0.length);
      val var2: Int = var0.length;

      for (int var1 = 0; var1 < var2; var1++) {
         var3.add(var0[var1]);
      }

      return var3;
   }

   @JvmStatic
   public fun LongArray.toMutableList(): MutableList<Long> {
      val var3: ArrayList = new ArrayList(var0.length);
      val var2: Int = var0.length;

      for (int var1 = 0; var1 < var2; var1++) {
         var3.add(var0[var1]);
      }

      return var3;
   }

   @JvmStatic
   public fun <T> Array<out T>.toMutableList(): MutableList<T> {
      return (java.util.List<T>)(new ArrayList<>(CollectionsKt.asCollection(var0)));
   }

   @JvmStatic
   public fun ShortArray.toMutableList(): MutableList<Short> {
      val var3: ArrayList = new ArrayList(var0.length);
      val var2: Int = var0.length;

      for (int var1 = 0; var1 < var2; var1++) {
         var3.add(var0[var1]);
      }

      return var3;
   }

   @JvmStatic
   public fun BooleanArray.toMutableList(): MutableList<Boolean> {
      val var3: ArrayList = new ArrayList(var0.length);
      val var2: Int = var0.length;

      for (int var1 = 0; var1 < var2; var1++) {
         var3.add(var0[var1]);
      }

      return var3;
   }

   @JvmStatic
   public fun ByteArray.toMutableSet(): MutableSet<Byte> {
      return ArraysKt.toCollection(var0, new LinkedHashSet<>(MapsKt.mapCapacity(var0.length)));
   }

   @JvmStatic
   public fun CharArray.toMutableSet(): MutableSet<Char> {
      return ArraysKt.toCollection(var0, new LinkedHashSet<>(MapsKt.mapCapacity(RangesKt.coerceAtMost(var0.length, 128))));
   }

   @JvmStatic
   public fun DoubleArray.toMutableSet(): MutableSet<Double> {
      return ArraysKt.toCollection(var0, new LinkedHashSet<>(MapsKt.mapCapacity(var0.length)));
   }

   @JvmStatic
   public fun FloatArray.toMutableSet(): MutableSet<Float> {
      return ArraysKt.toCollection(var0, new LinkedHashSet<>(MapsKt.mapCapacity(var0.length)));
   }

   @JvmStatic
   public fun IntArray.toMutableSet(): MutableSet<Int> {
      return ArraysKt.toCollection(var0, new LinkedHashSet<>(MapsKt.mapCapacity(var0.length)));
   }

   @JvmStatic
   public fun LongArray.toMutableSet(): MutableSet<Long> {
      return ArraysKt.toCollection(var0, new LinkedHashSet<>(MapsKt.mapCapacity(var0.length)));
   }

   @JvmStatic
   public fun <T> Array<out T>.toMutableSet(): MutableSet<T> {
      return ArraysKt.toCollection(var0, new LinkedHashSet(MapsKt.mapCapacity(var0.length))) as MutableSet<T>;
   }

   @JvmStatic
   public fun ShortArray.toMutableSet(): MutableSet<Short> {
      return ArraysKt.toCollection(var0, new LinkedHashSet<>(MapsKt.mapCapacity(var0.length)));
   }

   @JvmStatic
   public fun BooleanArray.toMutableSet(): MutableSet<Boolean> {
      return ArraysKt.toCollection(var0, new LinkedHashSet<>(MapsKt.mapCapacity(var0.length)));
   }

   @JvmStatic
   public fun ByteArray.toSet(): Set<Byte> {
      val var1: Int = var0.length;
      val var2: java.util.Set;
      if (var0.length != 0) {
         if (var1 != 1) {
            var2 = ArraysKt.toCollection(var0, new LinkedHashSet(MapsKt.mapCapacity(var0.length)));
         } else {
            var2 = SetsKt.setOf(var0[0]);
         }
      } else {
         var2 = SetsKt.emptySet();
      }

      return var2;
   }

   @JvmStatic
   public fun CharArray.toSet(): Set<Char> {
      val var1: Int = var0.length;
      val var2: java.util.Set;
      if (var0.length != 0) {
         if (var1 != 1) {
            var2 = ArraysKt.toCollection(var0, new LinkedHashSet(MapsKt.mapCapacity(RangesKt.coerceAtMost(var0.length, 128))));
         } else {
            var2 = SetsKt.setOf(var0[0]);
         }
      } else {
         var2 = SetsKt.emptySet();
      }

      return var2;
   }

   @JvmStatic
   public fun DoubleArray.toSet(): Set<Double> {
      val var1: Int = var0.length;
      val var2: java.util.Set;
      if (var0.length != 0) {
         if (var1 != 1) {
            var2 = ArraysKt.toCollection(var0, new LinkedHashSet(MapsKt.mapCapacity(var0.length)));
         } else {
            var2 = SetsKt.setOf(var0[0]);
         }
      } else {
         var2 = SetsKt.emptySet();
      }

      return var2;
   }

   @JvmStatic
   public fun FloatArray.toSet(): Set<Float> {
      val var1: Int = var0.length;
      val var2: java.util.Set;
      if (var0.length != 0) {
         if (var1 != 1) {
            var2 = ArraysKt.toCollection(var0, new LinkedHashSet(MapsKt.mapCapacity(var0.length)));
         } else {
            var2 = SetsKt.setOf(var0[0]);
         }
      } else {
         var2 = SetsKt.emptySet();
      }

      return var2;
   }

   @JvmStatic
   public fun IntArray.toSet(): Set<Int> {
      val var1: Int = var0.length;
      val var2: java.util.Set;
      if (var0.length != 0) {
         if (var1 != 1) {
            var2 = ArraysKt.toCollection(var0, new LinkedHashSet(MapsKt.mapCapacity(var0.length)));
         } else {
            var2 = SetsKt.setOf(var0[0]);
         }
      } else {
         var2 = SetsKt.emptySet();
      }

      return var2;
   }

   @JvmStatic
   public fun LongArray.toSet(): Set<Long> {
      val var1: Int = var0.length;
      val var2: java.util.Set;
      if (var0.length != 0) {
         if (var1 != 1) {
            var2 = ArraysKt.toCollection(var0, new LinkedHashSet(MapsKt.mapCapacity(var0.length)));
         } else {
            var2 = SetsKt.setOf(var0[0]);
         }
      } else {
         var2 = SetsKt.emptySet();
      }

      return var2;
   }

   @JvmStatic
   public fun <T> Array<out T>.toSet(): Set<T> {
      val var1: Int = var0.length;
      val var2: java.util.Set;
      if (var0.length != 0) {
         if (var1 != 1) {
            var2 = ArraysKt.toCollection(var0, new LinkedHashSet(MapsKt.mapCapacity(var0.length)));
         } else {
            var2 = SetsKt.setOf(var0[0]);
         }
      } else {
         var2 = SetsKt.emptySet();
      }

      return var2;
   }

   @JvmStatic
   public fun ShortArray.toSet(): Set<Short> {
      val var1: Int = var0.length;
      val var2: java.util.Set;
      if (var0.length != 0) {
         if (var1 != 1) {
            var2 = ArraysKt.toCollection(var0, new LinkedHashSet(MapsKt.mapCapacity(var0.length)));
         } else {
            var2 = SetsKt.setOf(var0[0]);
         }
      } else {
         var2 = SetsKt.emptySet();
      }

      return var2;
   }

   @JvmStatic
   public fun BooleanArray.toSet(): Set<Boolean> {
      val var1: Int = var0.length;
      val var2: java.util.Set;
      if (var0.length != 0) {
         if (var1 != 1) {
            var2 = ArraysKt.toCollection(var0, new LinkedHashSet(MapsKt.mapCapacity(var0.length)));
         } else {
            var2 = SetsKt.setOf(var0[0]);
         }
      } else {
         var2 = SetsKt.emptySet();
      }

      return var2;
   }

   @JvmStatic
   public fun Array<out Short>.toShortArray(): ShortArray {
      val var2: Int = var0.length;
      val var3: ShortArray = new short[var0.length];

      for (int var1 = 0; var1 < var2; var1++) {
         var3[var1] = var0[var1];
      }

      return var3;
   }

   @JvmStatic
   public infix fun ByteArray.union(other: Iterable<Byte>): Set<Byte> {
      val var2: java.util.Set = ArraysKt.toMutableSet(var0);
      CollectionsKt.addAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public infix fun CharArray.union(other: Iterable<Char>): Set<Char> {
      val var2: java.util.Set = ArraysKt.toMutableSet(var0);
      CollectionsKt.addAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public infix fun DoubleArray.union(other: Iterable<Double>): Set<Double> {
      val var2: java.util.Set = ArraysKt.toMutableSet(var0);
      CollectionsKt.addAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public infix fun FloatArray.union(other: Iterable<Float>): Set<Float> {
      val var2: java.util.Set = ArraysKt.toMutableSet(var0);
      CollectionsKt.addAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public infix fun IntArray.union(other: Iterable<Int>): Set<Int> {
      val var2: java.util.Set = ArraysKt.toMutableSet(var0);
      CollectionsKt.addAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public infix fun LongArray.union(other: Iterable<Long>): Set<Long> {
      val var2: java.util.Set = ArraysKt.toMutableSet(var0);
      CollectionsKt.addAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public infix fun <T> Array<out T>.union(other: Iterable<T>): Set<T> {
      val var2: java.util.Set = ArraysKt.toMutableSet(var0);
      CollectionsKt.addAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public infix fun ShortArray.union(other: Iterable<Short>): Set<Short> {
      val var2: java.util.Set = ArraysKt.toMutableSet(var0);
      CollectionsKt.addAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public infix fun BooleanArray.union(other: Iterable<Boolean>): Set<Boolean> {
      val var2: java.util.Set = ArraysKt.toMutableSet(var0);
      CollectionsKt.addAll(var2, var1);
      return var2;
   }

   @JvmStatic
   public fun ByteArray.withIndex(): Iterable<IndexedValue<Byte>> {
      return new IndexingIterable<>(new ArraysKt___ArraysKt$$ExternalSyntheticLambda5(var0));
   }

   @JvmStatic
   public fun CharArray.withIndex(): Iterable<IndexedValue<Char>> {
      return new IndexingIterable<>(new ArraysKt___ArraysKt$$ExternalSyntheticLambda0(var0));
   }

   @JvmStatic
   public fun DoubleArray.withIndex(): Iterable<IndexedValue<Double>> {
      return new IndexingIterable<>(new ArraysKt___ArraysKt$$ExternalSyntheticLambda8(var0));
   }

   @JvmStatic
   public fun FloatArray.withIndex(): Iterable<IndexedValue<Float>> {
      return new IndexingIterable<>(new ArraysKt___ArraysKt$$ExternalSyntheticLambda2(var0));
   }

   @JvmStatic
   public fun IntArray.withIndex(): Iterable<IndexedValue<Int>> {
      return new IndexingIterable<>(new ArraysKt___ArraysKt$$ExternalSyntheticLambda7(var0));
   }

   @JvmStatic
   public fun LongArray.withIndex(): Iterable<IndexedValue<Long>> {
      return new IndexingIterable<>(new ArraysKt___ArraysKt$$ExternalSyntheticLambda4(var0));
   }

   @JvmStatic
   public fun <T> Array<out T>.withIndex(): Iterable<IndexedValue<T>> {
      return new IndexingIterable(new ArraysKt___ArraysKt$$ExternalSyntheticLambda6(var0));
   }

   @JvmStatic
   public fun ShortArray.withIndex(): Iterable<IndexedValue<Short>> {
      return new IndexingIterable<>(new ArraysKt___ArraysKt$$ExternalSyntheticLambda1(var0));
   }

   @JvmStatic
   public fun BooleanArray.withIndex(): Iterable<IndexedValue<Boolean>> {
      return new IndexingIterable<>(new ArraysKt___ArraysKt$$ExternalSyntheticLambda3(var0));
   }

   @JvmStatic
   fun `withIndex$lambda$108$ArraysKt___ArraysKt`(var0: Array<Any>): java.util.Iterator {
      return ArrayIteratorKt.iterator(var0);
   }

   @JvmStatic
   fun `withIndex$lambda$109$ArraysKt___ArraysKt`(var0: ByteArray): java.util.Iterator {
      return ArrayIteratorsKt.iterator(var0);
   }

   @JvmStatic
   fun `withIndex$lambda$110$ArraysKt___ArraysKt`(var0: ShortArray): java.util.Iterator {
      return ArrayIteratorsKt.iterator(var0);
   }

   @JvmStatic
   fun `withIndex$lambda$111$ArraysKt___ArraysKt`(var0: IntArray): java.util.Iterator {
      return ArrayIteratorsKt.iterator(var0);
   }

   @JvmStatic
   fun `withIndex$lambda$112$ArraysKt___ArraysKt`(var0: LongArray): java.util.Iterator {
      return ArrayIteratorsKt.iterator(var0);
   }

   @JvmStatic
   fun `withIndex$lambda$113$ArraysKt___ArraysKt`(var0: FloatArray): java.util.Iterator {
      return ArrayIteratorsKt.iterator(var0);
   }

   @JvmStatic
   fun `withIndex$lambda$114$ArraysKt___ArraysKt`(var0: DoubleArray): java.util.Iterator {
      return ArrayIteratorsKt.iterator(var0);
   }

   @JvmStatic
   fun `withIndex$lambda$115$ArraysKt___ArraysKt`(var0: BooleanArray): java.util.Iterator {
      return ArrayIteratorsKt.iterator(var0);
   }

   @JvmStatic
   fun `withIndex$lambda$116$ArraysKt___ArraysKt`(var0: CharArray): java.util.Iterator {
      return ArrayIteratorsKt.iterator(var0);
   }

   @JvmStatic
   public infix fun <R> ByteArray.zip(other: Iterable<R>): List<Pair<Byte, R>> {
      val var3: Int = var0.length;
      val var4: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var1, 10), var0.length));
      val var6: java.util.Iterator = var1.iterator();

      for (int var2 = 0; var6.hasNext(); var2++) {
         val var5: Any = var6.next();
         if (var2 >= var3) {
            break;
         }

         var4.add(TuplesKt.to(var0[var2], var5));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <R, V> ByteArray.zip(other: Iterable<R>, transform: (Byte, R) -> V): List<V> {
      val var4: Int = var0.length;
      val var5: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var1, 10), var0.length));
      val var7: java.util.Iterator = var1.iterator();

      for (int var3 = 0; var7.hasNext(); var3++) {
         val var6: Any = var7.next();
         if (var3 >= var4) {
            break;
         }

         var5.add(var2.invoke(var0[var3], var6));
      }

      return var5;
   }

   @JvmStatic
   public infix fun ByteArray.zip(other: ByteArray): List<Pair<Byte, Byte>> {
      val var3: Int = Math.min(var0.length, var1.length);
      val var4: ArrayList = new ArrayList(var3);

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(TuplesKt.to(var0[var2], var1[var2]));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <V> ByteArray.zip(other: ByteArray, transform: (Byte, Byte) -> V): List<V> {
      val var4: Int = Math.min(var0.length, var1.length);
      val var5: ArrayList = new ArrayList(var4);

      for (int var3 = 0; var3 < var4; var3++) {
         var5.add(var2.invoke(var0[var3], var1[var3]));
      }

      return var5;
   }

   @JvmStatic
   public infix fun <R> ByteArray.zip(other: Array<out R>): List<Pair<Byte, R>> {
      val var3: Int = Math.min(var0.length, var1.length);
      val var4: ArrayList = new ArrayList(var3);

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(TuplesKt.to(var0[var2], var1[var2]));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <R, V> ByteArray.zip(other: Array<out R>, transform: (Byte, R) -> V): List<V> {
      val var4: Int = Math.min(var0.length, var1.length);
      val var5: ArrayList = new ArrayList(var4);

      for (int var3 = 0; var3 < var4; var3++) {
         var5.add(var2.invoke(var0[var3], var1[var3]));
      }

      return var5;
   }

   @JvmStatic
   public infix fun <R> CharArray.zip(other: Iterable<R>): List<Pair<Char, R>> {
      val var3: Int = var0.length;
      val var4: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var1, 10), var0.length));
      val var6: java.util.Iterator = var1.iterator();

      for (int var2 = 0; var6.hasNext(); var2++) {
         val var5: Any = var6.next();
         if (var2 >= var3) {
            break;
         }

         var4.add(TuplesKt.to(var0[var2], var5));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <R, V> CharArray.zip(other: Iterable<R>, transform: (Char, R) -> V): List<V> {
      val var4: Int = var0.length;
      val var5: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var1, 10), var0.length));
      val var7: java.util.Iterator = var1.iterator();

      for (int var3 = 0; var7.hasNext(); var3++) {
         val var6: Any = var7.next();
         if (var3 >= var4) {
            break;
         }

         var5.add(var2.invoke(var0[var3], var6));
      }

      return var5;
   }

   @JvmStatic
   public infix fun CharArray.zip(other: CharArray): List<Pair<Char, Char>> {
      val var3: Int = Math.min(var0.length, var1.length);
      val var4: ArrayList = new ArrayList(var3);

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(TuplesKt.to(var0[var2], var1[var2]));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <V> CharArray.zip(other: CharArray, transform: (Char, Char) -> V): List<V> {
      val var4: Int = Math.min(var0.length, var1.length);
      val var5: ArrayList = new ArrayList(var4);

      for (int var3 = 0; var3 < var4; var3++) {
         var5.add(var2.invoke(var0[var3], var1[var3]));
      }

      return var5;
   }

   @JvmStatic
   public infix fun <R> CharArray.zip(other: Array<out R>): List<Pair<Char, R>> {
      val var3: Int = Math.min(var0.length, var1.length);
      val var4: ArrayList = new ArrayList(var3);

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(TuplesKt.to(var0[var2], var1[var2]));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <R, V> CharArray.zip(other: Array<out R>, transform: (Char, R) -> V): List<V> {
      val var4: Int = Math.min(var0.length, var1.length);
      val var5: ArrayList = new ArrayList(var4);

      for (int var3 = 0; var3 < var4; var3++) {
         var5.add(var2.invoke(var0[var3], var1[var3]));
      }

      return var5;
   }

   @JvmStatic
   public infix fun <R> DoubleArray.zip(other: Iterable<R>): List<Pair<Double, R>> {
      val var3: Int = var0.length;
      val var4: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var1, 10), var0.length));
      val var6: java.util.Iterator = var1.iterator();

      for (int var2 = 0; var6.hasNext(); var2++) {
         val var5: Any = var6.next();
         if (var2 >= var3) {
            break;
         }

         var4.add(TuplesKt.to(var0[var2], var5));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <R, V> DoubleArray.zip(other: Iterable<R>, transform: (Double, R) -> V): List<V> {
      val var4: Int = var0.length;
      val var5: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var1, 10), var0.length));
      val var6: java.util.Iterator = var1.iterator();

      for (int var3 = 0; var6.hasNext(); var3++) {
         val var7: Any = var6.next();
         if (var3 >= var4) {
            break;
         }

         var5.add(var2.invoke(var0[var3], var7));
      }

      return var5;
   }

   @JvmStatic
   public infix fun DoubleArray.zip(other: DoubleArray): List<Pair<Double, Double>> {
      val var3: Int = Math.min(var0.length, var1.length);
      val var4: ArrayList = new ArrayList(var3);

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(TuplesKt.to(var0[var2], var1[var2]));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <V> DoubleArray.zip(other: DoubleArray, transform: (Double, Double) -> V): List<V> {
      val var4: Int = Math.min(var0.length, var1.length);
      val var5: ArrayList = new ArrayList(var4);

      for (int var3 = 0; var3 < var4; var3++) {
         var5.add(var2.invoke(var0[var3], var1[var3]));
      }

      return var5;
   }

   @JvmStatic
   public infix fun <R> DoubleArray.zip(other: Array<out R>): List<Pair<Double, R>> {
      val var3: Int = Math.min(var0.length, var1.length);
      val var4: ArrayList = new ArrayList(var3);

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(TuplesKt.to(var0[var2], var1[var2]));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <R, V> DoubleArray.zip(other: Array<out R>, transform: (Double, R) -> V): List<V> {
      val var4: Int = Math.min(var0.length, var1.length);
      val var5: ArrayList = new ArrayList(var4);

      for (int var3 = 0; var3 < var4; var3++) {
         var5.add(var2.invoke(var0[var3], var1[var3]));
      }

      return var5;
   }

   @JvmStatic
   public infix fun <R> FloatArray.zip(other: Iterable<R>): List<Pair<Float, R>> {
      val var3: Int = var0.length;
      val var4: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var1, 10), var0.length));
      val var5: java.util.Iterator = var1.iterator();

      for (int var2 = 0; var5.hasNext(); var2++) {
         val var6: Any = var5.next();
         if (var2 >= var3) {
            break;
         }

         var4.add(TuplesKt.to(var0[var2], var6));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <R, V> FloatArray.zip(other: Iterable<R>, transform: (Float, R) -> V): List<V> {
      val var4: Int = var0.length;
      val var5: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var1, 10), var0.length));
      val var7: java.util.Iterator = var1.iterator();

      for (int var3 = 0; var7.hasNext(); var3++) {
         val var6: Any = var7.next();
         if (var3 >= var4) {
            break;
         }

         var5.add(var2.invoke(var0[var3], var6));
      }

      return var5;
   }

   @JvmStatic
   public infix fun FloatArray.zip(other: FloatArray): List<Pair<Float, Float>> {
      val var3: Int = Math.min(var0.length, var1.length);
      val var4: ArrayList = new ArrayList(var3);

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(TuplesKt.to(var0[var2], var1[var2]));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <V> FloatArray.zip(other: FloatArray, transform: (Float, Float) -> V): List<V> {
      val var4: Int = Math.min(var0.length, var1.length);
      val var5: ArrayList = new ArrayList(var4);

      for (int var3 = 0; var3 < var4; var3++) {
         var5.add(var2.invoke(var0[var3], var1[var3]));
      }

      return var5;
   }

   @JvmStatic
   public infix fun <R> FloatArray.zip(other: Array<out R>): List<Pair<Float, R>> {
      val var3: Int = Math.min(var0.length, var1.length);
      val var4: ArrayList = new ArrayList(var3);

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(TuplesKt.to(var0[var2], var1[var2]));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <R, V> FloatArray.zip(other: Array<out R>, transform: (Float, R) -> V): List<V> {
      val var4: Int = Math.min(var0.length, var1.length);
      val var5: ArrayList = new ArrayList(var4);

      for (int var3 = 0; var3 < var4; var3++) {
         var5.add(var2.invoke(var0[var3], var1[var3]));
      }

      return var5;
   }

   @JvmStatic
   public infix fun <R> IntArray.zip(other: Iterable<R>): List<Pair<Int, R>> {
      val var3: Int = var0.length;
      val var4: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var1, 10), var0.length));
      val var6: java.util.Iterator = var1.iterator();

      for (int var2 = 0; var6.hasNext(); var2++) {
         val var5: Any = var6.next();
         if (var2 >= var3) {
            break;
         }

         var4.add(TuplesKt.to(var0[var2], var5));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <R, V> IntArray.zip(other: Iterable<R>, transform: (Int, R) -> V): List<V> {
      val var4: Int = var0.length;
      val var5: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var1, 10), var0.length));
      val var6: java.util.Iterator = var1.iterator();

      for (int var3 = 0; var6.hasNext(); var3++) {
         val var7: Any = var6.next();
         if (var3 >= var4) {
            break;
         }

         var5.add(var2.invoke(var0[var3], var7));
      }

      return var5;
   }

   @JvmStatic
   public infix fun IntArray.zip(other: IntArray): List<Pair<Int, Int>> {
      val var3: Int = Math.min(var0.length, var1.length);
      val var4: ArrayList = new ArrayList(var3);

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(TuplesKt.to(var0[var2], var1[var2]));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <V> IntArray.zip(other: IntArray, transform: (Int, Int) -> V): List<V> {
      val var4: Int = Math.min(var0.length, var1.length);
      val var5: ArrayList = new ArrayList(var4);

      for (int var3 = 0; var3 < var4; var3++) {
         var5.add(var2.invoke(var0[var3], var1[var3]));
      }

      return var5;
   }

   @JvmStatic
   public infix fun <R> IntArray.zip(other: Array<out R>): List<Pair<Int, R>> {
      val var3: Int = Math.min(var0.length, var1.length);
      val var4: ArrayList = new ArrayList(var3);

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(TuplesKt.to(var0[var2], var1[var2]));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <R, V> IntArray.zip(other: Array<out R>, transform: (Int, R) -> V): List<V> {
      val var4: Int = Math.min(var0.length, var1.length);
      val var5: ArrayList = new ArrayList(var4);

      for (int var3 = 0; var3 < var4; var3++) {
         var5.add(var2.invoke(var0[var3], var1[var3]));
      }

      return var5;
   }

   @JvmStatic
   public infix fun <R> LongArray.zip(other: Iterable<R>): List<Pair<Long, R>> {
      val var3: Int = var0.length;
      val var4: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var1, 10), var0.length));
      val var5: java.util.Iterator = var1.iterator();

      for (int var2 = 0; var5.hasNext(); var2++) {
         val var6: Any = var5.next();
         if (var2 >= var3) {
            break;
         }

         var4.add(TuplesKt.to(var0[var2], var6));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <R, V> LongArray.zip(other: Iterable<R>, transform: (Long, R) -> V): List<V> {
      val var4: Int = var0.length;
      val var5: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var1, 10), var0.length));
      val var7: java.util.Iterator = var1.iterator();

      for (int var3 = 0; var7.hasNext(); var3++) {
         val var6: Any = var7.next();
         if (var3 >= var4) {
            break;
         }

         var5.add(var2.invoke(var0[var3], var6));
      }

      return var5;
   }

   @JvmStatic
   public infix fun LongArray.zip(other: LongArray): List<Pair<Long, Long>> {
      val var3: Int = Math.min(var0.length, var1.length);
      val var4: ArrayList = new ArrayList(var3);

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(TuplesKt.to(var0[var2], var1[var2]));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <V> LongArray.zip(other: LongArray, transform: (Long, Long) -> V): List<V> {
      val var4: Int = Math.min(var0.length, var1.length);
      val var5: ArrayList = new ArrayList(var4);

      for (int var3 = 0; var3 < var4; var3++) {
         var5.add(var2.invoke(var0[var3], var1[var3]));
      }

      return var5;
   }

   @JvmStatic
   public infix fun <R> LongArray.zip(other: Array<out R>): List<Pair<Long, R>> {
      val var3: Int = Math.min(var0.length, var1.length);
      val var4: ArrayList = new ArrayList(var3);

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(TuplesKt.to(var0[var2], var1[var2]));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <R, V> LongArray.zip(other: Array<out R>, transform: (Long, R) -> V): List<V> {
      val var4: Int = Math.min(var0.length, var1.length);
      val var5: ArrayList = new ArrayList(var4);

      for (int var3 = 0; var3 < var4; var3++) {
         var5.add(var2.invoke(var0[var3], var1[var3]));
      }

      return var5;
   }

   @JvmStatic
   public infix fun <T, R> Array<out T>.zip(other: Iterable<R>): List<Pair<T, R>> {
      val var3: Int = var0.length;
      val var4: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var1, 10), var0.length));
      val var5: java.util.Iterator = var1.iterator();

      for (int var2 = 0; var5.hasNext(); var2++) {
         val var6: Any = var5.next();
         if (var2 >= var3) {
            break;
         }

         var4.add(TuplesKt.to(var0[var2], var6));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <T, R, V> Array<out T>.zip(other: Iterable<R>, transform: (T, R) -> V): List<V> {
      val var4: Int = var0.length;
      val var5: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var1, 10), var0.length));
      val var7: java.util.Iterator = var1.iterator();

      for (int var3 = 0; var7.hasNext(); var3++) {
         val var6: Any = var7.next();
         if (var3 >= var4) {
            break;
         }

         var5.add(var2.invoke(var0[var3], var6));
      }

      return var5;
   }

   @JvmStatic
   public infix fun <T, R> Array<out T>.zip(other: Array<out R>): List<Pair<T, R>> {
      val var3: Int = Math.min(var0.length, var1.length);
      val var4: ArrayList = new ArrayList(var3);

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(TuplesKt.to(var0[var2], var1[var2]));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <T, R, V> Array<out T>.zip(other: Array<out R>, transform: (T, R) -> V): List<V> {
      val var4: Int = Math.min(var0.length, var1.length);
      val var5: ArrayList = new ArrayList(var4);

      for (int var3 = 0; var3 < var4; var3++) {
         var5.add(var2.invoke(var0[var3], var1[var3]));
      }

      return var5;
   }

   @JvmStatic
   public infix fun <R> ShortArray.zip(other: Iterable<R>): List<Pair<Short, R>> {
      val var3: Int = var0.length;
      val var4: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var1, 10), var0.length));
      val var6: java.util.Iterator = var1.iterator();

      for (int var2 = 0; var6.hasNext(); var2++) {
         val var5: Any = var6.next();
         if (var2 >= var3) {
            break;
         }

         var4.add(TuplesKt.to(var0[var2], var5));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <R, V> ShortArray.zip(other: Iterable<R>, transform: (Short, R) -> V): List<V> {
      val var4: Int = var0.length;
      val var5: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var1, 10), var0.length));
      val var7: java.util.Iterator = var1.iterator();

      for (int var3 = 0; var7.hasNext(); var3++) {
         val var6: Any = var7.next();
         if (var3 >= var4) {
            break;
         }

         var5.add(var2.invoke(var0[var3], var6));
      }

      return var5;
   }

   @JvmStatic
   public infix fun <R> ShortArray.zip(other: Array<out R>): List<Pair<Short, R>> {
      val var3: Int = Math.min(var0.length, var1.length);
      val var4: ArrayList = new ArrayList(var3);

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(TuplesKt.to(var0[var2], var1[var2]));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <R, V> ShortArray.zip(other: Array<out R>, transform: (Short, R) -> V): List<V> {
      val var4: Int = Math.min(var0.length, var1.length);
      val var5: ArrayList = new ArrayList(var4);

      for (int var3 = 0; var3 < var4; var3++) {
         var5.add(var2.invoke(var0[var3], var1[var3]));
      }

      return var5;
   }

   @JvmStatic
   public infix fun ShortArray.zip(other: ShortArray): List<Pair<Short, Short>> {
      val var3: Int = Math.min(var0.length, var1.length);
      val var4: ArrayList = new ArrayList(var3);

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(TuplesKt.to(var0[var2], var1[var2]));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <V> ShortArray.zip(other: ShortArray, transform: (Short, Short) -> V): List<V> {
      val var4: Int = Math.min(var0.length, var1.length);
      val var5: ArrayList = new ArrayList(var4);

      for (int var3 = 0; var3 < var4; var3++) {
         var5.add(var2.invoke(var0[var3], var1[var3]));
      }

      return var5;
   }

   @JvmStatic
   public infix fun <R> BooleanArray.zip(other: Iterable<R>): List<Pair<Boolean, R>> {
      val var3: Int = var0.length;
      val var4: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var1, 10), var0.length));
      val var6: java.util.Iterator = var1.iterator();

      for (int var2 = 0; var6.hasNext(); var2++) {
         val var5: Any = var6.next();
         if (var2 >= var3) {
            break;
         }

         var4.add(TuplesKt.to(var0[var2], var5));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <R, V> BooleanArray.zip(other: Iterable<R>, transform: (Boolean, R) -> V): List<V> {
      val var4: Int = var0.length;
      val var5: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var1, 10), var0.length));
      val var6: java.util.Iterator = var1.iterator();

      for (int var3 = 0; var6.hasNext(); var3++) {
         val var7: Any = var6.next();
         if (var3 >= var4) {
            break;
         }

         var5.add(var2.invoke(var0[var3], var7));
      }

      return var5;
   }

   @JvmStatic
   public infix fun <R> BooleanArray.zip(other: Array<out R>): List<Pair<Boolean, R>> {
      val var3: Int = Math.min(var0.length, var1.length);
      val var4: ArrayList = new ArrayList(var3);

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(TuplesKt.to(var0[var2], var1[var2]));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <R, V> BooleanArray.zip(other: Array<out R>, transform: (Boolean, R) -> V): List<V> {
      val var4: Int = Math.min(var0.length, var1.length);
      val var5: ArrayList = new ArrayList(var4);

      for (int var3 = 0; var3 < var4; var3++) {
         var5.add(var2.invoke(var0[var3], var1[var3]));
      }

      return var5;
   }

   @JvmStatic
   public infix fun BooleanArray.zip(other: BooleanArray): List<Pair<Boolean, Boolean>> {
      val var3: Int = Math.min(var0.length, var1.length);
      val var4: ArrayList = new ArrayList(var3);

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(TuplesKt.to(var0[var2], var1[var2]));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <V> BooleanArray.zip(other: BooleanArray, transform: (Boolean, Boolean) -> V): List<V> {
      val var4: Int = Math.min(var0.length, var1.length);
      val var5: ArrayList = new ArrayList(var4);

      for (int var3 = 0; var3 < var4; var3++) {
         var5.add(var2.invoke(var0[var3], var1[var3]));
      }

      return var5;
   }
}
