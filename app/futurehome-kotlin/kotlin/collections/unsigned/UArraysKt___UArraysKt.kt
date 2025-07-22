package kotlin.collections.unsigned

import java.util.ArrayList
import java.util.Arrays
import java.util.Comparator
import java.util.LinkedHashMap
import java.util.NoSuchElementException
import kotlin.contracts.InvocationKind
import kotlin.internal.PlatformImplementationsKt
import kotlin.jvm.internal.Intrinsics
import kotlin.random.Random

internal class UArraysKt___UArraysKt : UArraysKt___UArraysJvmKt {
   public final val indices: IntRange
      public final inline get() {
         return ArraysKt.getIndices(var0);
      }


   public final val indices: IntRange
      public final inline get() {
         return ArraysKt.getIndices(var0);
      }


   public final val indices: IntRange
      public final inline get() {
         return ArraysKt.getIndices(var0);
      }


   public final val indices: IntRange
      public final inline get() {
         return ArraysKt.getIndices(var0);
      }


   public final val lastIndex: Int
      public final inline get() {
         return ArraysKt.getLastIndex(var0);
      }


   public final val lastIndex: Int
      public final inline get() {
         return ArraysKt.getLastIndex(var0);
      }


   public final val lastIndex: Int
      public final inline get() {
         return ArraysKt.getLastIndex(var0);
      }


   public final val lastIndex: Int
      public final inline get() {
         return ArraysKt.getLastIndex(var0);
      }


   open fun UArraysKt___UArraysKt() {
   }

   @JvmStatic
   public inline fun UByteArray.all(predicate: (UByte) -> Boolean): Boolean {
      val var3: Int = UByteArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         if (!var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var2))) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public inline fun ULongArray.all(predicate: (ULong) -> Boolean): Boolean {
      val var3: Int = ULongArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         if (!var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2))) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public inline fun UIntArray.all(predicate: (UInt) -> Boolean): Boolean {
      val var3: Int = UIntArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         if (!var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2))) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public inline fun UShortArray.all(predicate: (UShort) -> Boolean): Boolean {
      val var3: Int = UShortArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         if (!var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var2))) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public inline fun UIntArray.any(): Boolean {
      return ArraysKt.any(var0);
   }

   @JvmStatic
   public inline fun UByteArray.any(): Boolean {
      return ArraysKt.any(var0);
   }

   @JvmStatic
   public inline fun UByteArray.any(predicate: (UByte) -> Boolean): Boolean {
      val var3: Int = UByteArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var2))) as java.lang.Boolean) {
            return true;
         }
      }

      return false;
   }

   @JvmStatic
   public inline fun ULongArray.any(predicate: (ULong) -> Boolean): Boolean {
      val var3: Int = ULongArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2))) as java.lang.Boolean) {
            return true;
         }
      }

      return false;
   }

   @JvmStatic
   public inline fun ULongArray.any(): Boolean {
      return ArraysKt.any(var0);
   }

   @JvmStatic
   public inline fun UIntArray.any(predicate: (UInt) -> Boolean): Boolean {
      val var3: Int = UIntArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2))) as java.lang.Boolean) {
            return true;
         }
      }

      return false;
   }

   @JvmStatic
   public inline fun UShortArray.any(): Boolean {
      return ArraysKt.any(var0);
   }

   @JvmStatic
   public inline fun UShortArray.any(predicate: (UShort) -> Boolean): Boolean {
      val var3: Int = UShortArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var2))) as java.lang.Boolean) {
            return true;
         }
      }

      return false;
   }

   @JvmStatic
   public inline fun UByteArray.asByteArray(): ByteArray {
      return var0;
   }

   @JvmStatic
   public inline fun UIntArray.asIntArray(): IntArray {
      return var0;
   }

   @JvmStatic
   public inline fun ULongArray.asLongArray(): LongArray {
      return var0;
   }

   @JvmStatic
   public inline fun UShortArray.asShortArray(): ShortArray {
      return var0;
   }

   @JvmStatic
   public inline fun ByteArray.asUByteArray(): UByteArray {
      return UByteArray.constructor-impl(var0);
   }

   @JvmStatic
   public inline fun IntArray.asUIntArray(): UIntArray {
      return UIntArray.constructor-impl(var0);
   }

   @JvmStatic
   public inline fun LongArray.asULongArray(): ULongArray {
      return ULongArray.constructor-impl(var0);
   }

   @JvmStatic
   public inline fun ShortArray.asUShortArray(): UShortArray {
      return UShortArray.constructor-impl(var0);
   }

   @JvmStatic
   public inline fun <V> UByteArray.associateWith(valueSelector: (UByte) -> V): Map<UByte, V> {
      val var5: LinkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(UByteArray.getSize-impl(var0)), 16));
      val var4: Int = UByteArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Byte = UByteArray.get-w2LRezQ(var0, var3);
         var5.put(UByte.box-impl(var2), var1.invoke(UByte.box-impl(var2)));
      }

      return var5;
   }

   @JvmStatic
   public inline fun <V> ULongArray.associateWith(valueSelector: (ULong) -> V): Map<ULong, V> {
      val var6: LinkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(ULongArray.getSize-impl(var0)), 16));
      val var3: Int = ULongArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Long = ULongArray.get-s-VKNKU(var0, var2);
         var6.put(ULong.box-impl(var4), var1.invoke(ULong.box-impl(var4)));
      }

      return var6;
   }

   @JvmStatic
   public inline fun <V> UIntArray.associateWith(valueSelector: (UInt) -> V): Map<UInt, V> {
      val var5: LinkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(UIntArray.getSize-impl(var0)), 16));
      val var3: Int = UIntArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Int = UIntArray.get-pVg5ArA(var0, var2);
         var5.put(UInt.box-impl(var4), var1.invoke(UInt.box-impl(var4)));
      }

      return var5;
   }

   @JvmStatic
   public inline fun <V> UShortArray.associateWith(valueSelector: (UShort) -> V): Map<UShort, V> {
      val var5: LinkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(UShortArray.getSize-impl(var0)), 16));
      val var4: Int = UShortArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Short = UShortArray.get-Mh2AYeg(var0, var3);
         var5.put(UShort.box-impl(var2), var1.invoke(UShort.box-impl(var2)));
      }

      return var5;
   }

   @JvmStatic
   public inline fun <V, M : MutableMap<in UInt, in V>> UIntArray.associateWithTo(destination: M, valueSelector: (UInt) -> V): M {
      val var4: Int = UIntArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Int = UIntArray.get-pVg5ArA(var0, var3);
         var1.put(UInt.box-impl(var5), var2.invoke(UInt.box-impl(var5)));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <V, M : MutableMap<in UByte, in V>> UByteArray.associateWithTo(destination: M, valueSelector: (UByte) -> V): M {
      val var5: Int = UByteArray.getSize-impl(var0);

      for (int var4 = 0; var4 < var5; var4++) {
         val var3: Byte = UByteArray.get-w2LRezQ(var0, var4);
         var1.put(UByte.box-impl(var3), var2.invoke(UByte.box-impl(var3)));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <V, M : MutableMap<in ULong, in V>> ULongArray.associateWithTo(destination: M, valueSelector: (ULong) -> V): M {
      val var4: Int = ULongArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Long = ULongArray.get-s-VKNKU(var0, var3);
         var1.put(ULong.box-impl(var5), var2.invoke(ULong.box-impl(var5)));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <V, M : MutableMap<in UShort, in V>> UShortArray.associateWithTo(destination: M, valueSelector: (UShort) -> V): M {
      val var5: Int = UShortArray.getSize-impl(var0);

      for (int var4 = 0; var4 < var5; var4++) {
         val var3: Short = UShortArray.get-Mh2AYeg(var0, var4);
         var1.put(UShort.box-impl(var3), var2.invoke(UShort.box-impl(var3)));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline operator fun UIntArray.component1(): UInt {
      return UIntArray.get-pVg5ArA(var0, 0);
   }

   @JvmStatic
   public inline operator fun UByteArray.component1(): UByte {
      return UByteArray.get-w2LRezQ(var0, 0);
   }

   @JvmStatic
   public inline operator fun ULongArray.component1(): ULong {
      return ULongArray.get-s-VKNKU(var0, 0);
   }

   @JvmStatic
   public inline operator fun UShortArray.component1(): UShort {
      return UShortArray.get-Mh2AYeg(var0, 0);
   }

   @JvmStatic
   public inline operator fun UIntArray.component2(): UInt {
      return UIntArray.get-pVg5ArA(var0, 1);
   }

   @JvmStatic
   public inline operator fun UByteArray.component2(): UByte {
      return UByteArray.get-w2LRezQ(var0, 1);
   }

   @JvmStatic
   public inline operator fun ULongArray.component2(): ULong {
      return ULongArray.get-s-VKNKU(var0, 1);
   }

   @JvmStatic
   public inline operator fun UShortArray.component2(): UShort {
      return UShortArray.get-Mh2AYeg(var0, 1);
   }

   @JvmStatic
   public inline operator fun UIntArray.component3(): UInt {
      return UIntArray.get-pVg5ArA(var0, 2);
   }

   @JvmStatic
   public inline operator fun UByteArray.component3(): UByte {
      return UByteArray.get-w2LRezQ(var0, 2);
   }

   @JvmStatic
   public inline operator fun ULongArray.component3(): ULong {
      return ULongArray.get-s-VKNKU(var0, 2);
   }

   @JvmStatic
   public inline operator fun UShortArray.component3(): UShort {
      return UShortArray.get-Mh2AYeg(var0, 2);
   }

   @JvmStatic
   public inline operator fun UIntArray.component4(): UInt {
      return UIntArray.get-pVg5ArA(var0, 3);
   }

   @JvmStatic
   public inline operator fun UByteArray.component4(): UByte {
      return UByteArray.get-w2LRezQ(var0, 3);
   }

   @JvmStatic
   public inline operator fun ULongArray.component4(): ULong {
      return ULongArray.get-s-VKNKU(var0, 3);
   }

   @JvmStatic
   public inline operator fun UShortArray.component4(): UShort {
      return UShortArray.get-Mh2AYeg(var0, 3);
   }

   @JvmStatic
   public inline operator fun UIntArray.component5(): UInt {
      return UIntArray.get-pVg5ArA(var0, 4);
   }

   @JvmStatic
   public inline operator fun UByteArray.component5(): UByte {
      return UByteArray.get-w2LRezQ(var0, 4);
   }

   @JvmStatic
   public inline operator fun ULongArray.component5(): ULong {
      return ULongArray.get-s-VKNKU(var0, 4);
   }

   @JvmStatic
   public inline operator fun UShortArray.component5(): UShort {
      return UShortArray.get-Mh2AYeg(var0, 4);
   }

   @JvmStatic
   public infix fun UShortArray?.contentEquals(other: UShortArray?): Boolean {
      var var2: ShortArray = var0;
      if (var0 == null) {
         var2 = null;
      }

      var0 = var1;
      if (var1 == null) {
         var0 = null;
      }

      return Arrays.equals(var2, var0);
   }

   @JvmStatic
   public infix fun UIntArray?.contentEquals(other: UIntArray?): Boolean {
      var var2: IntArray = var0;
      if (var0 == null) {
         var2 = null;
      }

      var0 = var1;
      if (var1 == null) {
         var0 = null;
      }

      return Arrays.equals(var2, var0);
   }

   @JvmStatic
   public infix fun UByteArray?.contentEquals(other: UByteArray?): Boolean {
      var var2: ByteArray = var0;
      if (var0 == null) {
         var2 = null;
      }

      var0 = var1;
      if (var1 == null) {
         var0 = null;
      }

      return Arrays.equals(var2, var0);
   }

   @JvmStatic
   public infix fun ULongArray?.contentEquals(other: ULongArray?): Boolean {
      var var2: LongArray = var0;
      if (var0 == null) {
         var2 = null;
      }

      var0 = var1;
      if (var1 == null) {
         var0 = null;
      }

      return Arrays.equals(var2, var0);
   }

   @JvmStatic
   public fun UByteArray?.contentHashCode(): Int {
      var var1: ByteArray = var0;
      if (var0 == null) {
         var1 = null;
      }

      return Arrays.hashCode(var1);
   }

   @JvmStatic
   public fun UIntArray?.contentHashCode(): Int {
      var var1: IntArray = var0;
      if (var0 == null) {
         var1 = null;
      }

      return Arrays.hashCode(var1);
   }

   @JvmStatic
   public fun UShortArray?.contentHashCode(): Int {
      var var1: ShortArray = var0;
      if (var0 == null) {
         var1 = null;
      }

      return Arrays.hashCode(var1);
   }

   @JvmStatic
   public fun ULongArray?.contentHashCode(): Int {
      var var1: LongArray = var0;
      if (var0 == null) {
         var1 = null;
      }

      return Arrays.hashCode(var1);
   }

   @JvmStatic
   public fun UByteArray?.contentToString(): String {
      if (var0 != null) {
         val var1: java.lang.String = CollectionsKt.joinToString$default(UByteArray.box-impl(var0), ", ", "[", "]", 0, null, null, 56, null);
         if (var1 != null) {
            return var1;
         }
      }

      return "null";
   }

   @JvmStatic
   public fun UIntArray?.contentToString(): String {
      if (var0 != null) {
         val var1: java.lang.String = CollectionsKt.joinToString$default(UIntArray.box-impl(var0), ", ", "[", "]", 0, null, null, 56, null);
         if (var1 != null) {
            return var1;
         }
      }

      return "null";
   }

   @JvmStatic
   public fun UShortArray?.contentToString(): String {
      if (var0 != null) {
         val var1: java.lang.String = CollectionsKt.joinToString$default(UShortArray.box-impl(var0), ", ", "[", "]", 0, null, null, 56, null);
         if (var1 != null) {
            return var1;
         }
      }

      return "null";
   }

   @JvmStatic
   public fun ULongArray?.contentToString(): String {
      if (var0 != null) {
         val var1: java.lang.String = CollectionsKt.joinToString$default(ULongArray.box-impl(var0), ", ", "[", "]", 0, null, null, 56, null);
         if (var1 != null) {
            return var1;
         }
      }

      return "null";
   }

   @JvmStatic
   public inline fun ULongArray.copyInto(destination: ULongArray, destinationOffset: Int = ..., startIndex: Int = ..., endIndex: Int = ...): ULongArray {
      ArraysKt.copyInto(var0, var1, var2, var3, var4);
      return var1;
   }

   @JvmStatic
   public inline fun UShortArray.copyInto(destination: UShortArray, destinationOffset: Int = ..., startIndex: Int = ..., endIndex: Int = ...): UShortArray {
      ArraysKt.copyInto(var0, var1, var2, var3, var4);
      return var1;
   }

   @JvmStatic
   public inline fun UByteArray.copyInto(destination: UByteArray, destinationOffset: Int = ..., startIndex: Int = ..., endIndex: Int = ...): UByteArray {
      ArraysKt.copyInto(var0, var1, var2, var3, var4);
      return var1;
   }

   @JvmStatic
   public inline fun UIntArray.copyInto(destination: UIntArray, destinationOffset: Int = ..., startIndex: Int = ..., endIndex: Int = ...): UIntArray {
      ArraysKt.copyInto(var0, var1, var2, var3, var4);
      return var1;
   }

   @JvmStatic
   public inline fun UIntArray.copyOf(): UIntArray {
      var0 = Arrays.copyOf(var0, var0.length);
      return UIntArray.constructor-impl(var0);
   }

   @JvmStatic
   public inline fun UByteArray.copyOf(): UByteArray {
      var0 = Arrays.copyOf(var0, var0.length);
      return UByteArray.constructor-impl(var0);
   }

   @JvmStatic
   public inline fun UByteArray.copyOf(newSize: Int): UByteArray {
      var0 = Arrays.copyOf(var0, var1);
      return UByteArray.constructor-impl(var0);
   }

   @JvmStatic
   public inline fun ULongArray.copyOf(): ULongArray {
      var0 = Arrays.copyOf(var0, var0.length);
      return ULongArray.constructor-impl(var0);
   }

   @JvmStatic
   public inline fun UShortArray.copyOf(newSize: Int): UShortArray {
      var0 = Arrays.copyOf(var0, var1);
      return UShortArray.constructor-impl(var0);
   }

   @JvmStatic
   public inline fun UIntArray.copyOf(newSize: Int): UIntArray {
      var0 = Arrays.copyOf(var0, var1);
      return UIntArray.constructor-impl(var0);
   }

   @JvmStatic
   public inline fun ULongArray.copyOf(newSize: Int): ULongArray {
      var0 = Arrays.copyOf(var0, var1);
      return ULongArray.constructor-impl(var0);
   }

   @JvmStatic
   public inline fun UShortArray.copyOf(): UShortArray {
      var0 = Arrays.copyOf(var0, var0.length);
      return UShortArray.constructor-impl(var0);
   }

   @JvmStatic
   public inline fun ULongArray.copyOfRange(fromIndex: Int, toIndex: Int): ULongArray {
      if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
         var0 = ArraysKt.copyOfRange(var0, var1, var2);
      } else {
         if (var2 > var0.length) {
            val var3: StringBuilder = new StringBuilder("toIndex: ");
            var3.append(var2);
            var3.append(", size: ");
            var3.append(var0.length);
            throw new IndexOutOfBoundsException(var3.toString());
         }

         var0 = Arrays.copyOfRange(var0, var1, var2);
      }

      return ULongArray.constructor-impl(var0);
   }

   @JvmStatic
   public inline fun UByteArray.copyOfRange(fromIndex: Int, toIndex: Int): UByteArray {
      if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
         var0 = ArraysKt.copyOfRange(var0, var1, var2);
      } else {
         if (var2 > var0.length) {
            val var3: StringBuilder = new StringBuilder("toIndex: ");
            var3.append(var2);
            var3.append(", size: ");
            var3.append(var0.length);
            throw new IndexOutOfBoundsException(var3.toString());
         }

         var0 = Arrays.copyOfRange(var0, var1, var2);
      }

      return UByteArray.constructor-impl(var0);
   }

   @JvmStatic
   public inline fun UShortArray.copyOfRange(fromIndex: Int, toIndex: Int): UShortArray {
      if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
         var0 = ArraysKt.copyOfRange(var0, var1, var2);
      } else {
         if (var2 > var0.length) {
            val var3: StringBuilder = new StringBuilder("toIndex: ");
            var3.append(var2);
            var3.append(", size: ");
            var3.append(var0.length);
            throw new IndexOutOfBoundsException(var3.toString());
         }

         var0 = Arrays.copyOfRange(var0, var1, var2);
      }

      return UShortArray.constructor-impl(var0);
   }

   @JvmStatic
   public inline fun UIntArray.copyOfRange(fromIndex: Int, toIndex: Int): UIntArray {
      if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
         var0 = ArraysKt.copyOfRange(var0, var1, var2);
      } else {
         if (var2 > var0.length) {
            val var3: StringBuilder = new StringBuilder("toIndex: ");
            var3.append(var2);
            var3.append(", size: ");
            var3.append(var0.length);
            throw new IndexOutOfBoundsException(var3.toString());
         }

         var0 = Arrays.copyOfRange(var0, var1, var2);
      }

      return UIntArray.constructor-impl(var0);
   }

   @JvmStatic
   public inline fun UByteArray.count(predicate: (UByte) -> Boolean): Int {
      val var5: Int = UByteArray.getSize-impl(var0);
      var var2: Int = 0;
      var var3: Int = 0;

      while (var2 < var5) {
         var var4: Int = var3;
         if (var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var2))) as java.lang.Boolean) {
            var4 = var3 + 1;
         }

         var2++;
         var3 = var4;
      }

      return var3;
   }

   @JvmStatic
   public inline fun ULongArray.count(predicate: (ULong) -> Boolean): Int {
      val var5: Int = ULongArray.getSize-impl(var0);
      var var2: Int = 0;
      var var4: Int = 0;

      while (var2 < var5) {
         var var3: Int = var4;
         if (var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2))) as java.lang.Boolean) {
            var3 = var4 + 1;
         }

         var2++;
         var4 = var3;
      }

      return var4;
   }

   @JvmStatic
   public inline fun UIntArray.count(predicate: (UInt) -> Boolean): Int {
      val var5: Int = UIntArray.getSize-impl(var0);
      var var2: Int = 0;
      var var3: Int = 0;

      while (var2 < var5) {
         var var4: Int = var3;
         if (var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2))) as java.lang.Boolean) {
            var4 = var3 + 1;
         }

         var2++;
         var3 = var4;
      }

      return var3;
   }

   @JvmStatic
   public inline fun UShortArray.count(predicate: (UShort) -> Boolean): Int {
      val var5: Int = UShortArray.getSize-impl(var0);
      var var4: Int = 0;
      var var3: Int = 0;

      while (var4 < var5) {
         var var2: Int = var3;
         if (var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var4))) as java.lang.Boolean) {
            var2 = var3 + 1;
         }

         var4++;
         var3 = var2;
      }

      return var3;
   }

   @JvmStatic
   public fun UByteArray.drop(n: Int): List<UByte> {
      if (var1 >= 0) {
         return UArraysKt.takeLast-PpDY95g(var0, RangesKt.coerceAtLeast(UByteArray.getSize-impl(var0) - var1, 0));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun UShortArray.drop(n: Int): List<UShort> {
      if (var1 >= 0) {
         return UArraysKt.takeLast-nggk6HY(var0, RangesKt.coerceAtLeast(UShortArray.getSize-impl(var0) - var1, 0));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun UIntArray.drop(n: Int): List<UInt> {
      if (var1 >= 0) {
         return UArraysKt.takeLast-qFRl0hI(var0, RangesKt.coerceAtLeast(UIntArray.getSize-impl(var0) - var1, 0));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun ULongArray.drop(n: Int): List<ULong> {
      if (var1 >= 0) {
         return UArraysKt.takeLast-r7IrZao(var0, RangesKt.coerceAtLeast(ULongArray.getSize-impl(var0) - var1, 0));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun UByteArray.dropLast(n: Int): List<UByte> {
      if (var1 >= 0) {
         return UArraysKt.take-PpDY95g(var0, RangesKt.coerceAtLeast(UByteArray.getSize-impl(var0) - var1, 0));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun UShortArray.dropLast(n: Int): List<UShort> {
      if (var1 >= 0) {
         return UArraysKt.take-nggk6HY(var0, RangesKt.coerceAtLeast(UShortArray.getSize-impl(var0) - var1, 0));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun UIntArray.dropLast(n: Int): List<UInt> {
      if (var1 >= 0) {
         return UArraysKt.take-qFRl0hI(var0, RangesKt.coerceAtLeast(UIntArray.getSize-impl(var0) - var1, 0));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public fun ULongArray.dropLast(n: Int): List<ULong> {
      if (var1 >= 0) {
         return UArraysKt.take-r7IrZao(var0, RangesKt.coerceAtLeast(ULongArray.getSize-impl(var0) - var1, 0));
      } else {
         val var2: StringBuilder = new StringBuilder("Requested element count ");
         var2.append(var1);
         var2.append(" is less than zero.");
         throw new IllegalArgumentException(var2.toString().toString());
      }
   }

   @JvmStatic
   public inline fun UByteArray.dropLastWhile(predicate: (UByte) -> Boolean): List<UByte> {
      for (int var2 = ArraysKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var2))) as java.lang.Boolean) {
            return UArraysKt.take-PpDY95g(var0, var2 + 1);
         }
      }

      return CollectionsKt.emptyList();
   }

   @JvmStatic
   public inline fun ULongArray.dropLastWhile(predicate: (ULong) -> Boolean): List<ULong> {
      for (int var2 = ArraysKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2))) as java.lang.Boolean) {
            return UArraysKt.take-r7IrZao(var0, var2 + 1);
         }
      }

      return CollectionsKt.emptyList();
   }

   @JvmStatic
   public inline fun UIntArray.dropLastWhile(predicate: (UInt) -> Boolean): List<UInt> {
      for (int var2 = ArraysKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2))) as java.lang.Boolean) {
            return UArraysKt.take-qFRl0hI(var0, var2 + 1);
         }
      }

      return CollectionsKt.emptyList();
   }

   @JvmStatic
   public inline fun UShortArray.dropLastWhile(predicate: (UShort) -> Boolean): List<UShort> {
      for (int var2 = ArraysKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var2))) as java.lang.Boolean) {
            return UArraysKt.take-nggk6HY(var0, var2 + 1);
         }
      }

      return CollectionsKt.emptyList();
   }

   @JvmStatic
   public inline fun UByteArray.dropWhile(predicate: (UByte) -> Boolean): List<UByte> {
      val var6: ArrayList = new ArrayList();
      val var5: Int = UByteArray.getSize-impl(var0);
      var var3: Int = 0;

      for (boolean var4 = false; var3 < var5; var3++) {
         val var2: Byte = UByteArray.get-w2LRezQ(var0, var3);
         if (var4) {
            var6.add(UByte.box-impl(var2));
         } else if (!var1.invoke(UByte.box-impl(var2)) as java.lang.Boolean) {
            var6.add(UByte.box-impl(var2));
            var4 = true;
         }
      }

      return var6;
   }

   @JvmStatic
   public inline fun ULongArray.dropWhile(predicate: (ULong) -> Boolean): List<ULong> {
      val var7: ArrayList = new ArrayList();
      val var4: Int = ULongArray.getSize-impl(var0);
      var var2: Int = 0;

      for (boolean var3 = false; var2 < var4; var2++) {
         val var5: Long = ULongArray.get-s-VKNKU(var0, var2);
         if (var3) {
            var7.add(ULong.box-impl(var5));
         } else if (!var1.invoke(ULong.box-impl(var5)) as java.lang.Boolean) {
            var7.add(ULong.box-impl(var5));
            var3 = true;
         }
      }

      return var7;
   }

   @JvmStatic
   public inline fun UIntArray.dropWhile(predicate: (UInt) -> Boolean): List<UInt> {
      val var6: ArrayList = new ArrayList();
      val var4: Int = UIntArray.getSize-impl(var0);
      var var2: Int = 0;

      for (boolean var3 = false; var2 < var4; var2++) {
         val var5: Int = UIntArray.get-pVg5ArA(var0, var2);
         if (var3) {
            var6.add(UInt.box-impl(var5));
         } else if (!var1.invoke(UInt.box-impl(var5)) as java.lang.Boolean) {
            var6.add(UInt.box-impl(var5));
            var3 = true;
         }
      }

      return var6;
   }

   @JvmStatic
   public inline fun UShortArray.dropWhile(predicate: (UShort) -> Boolean): List<UShort> {
      val var6: ArrayList = new ArrayList();
      val var5: Int = UShortArray.getSize-impl(var0);
      var var3: Int = 0;

      for (boolean var4 = false; var3 < var5; var3++) {
         val var2: Short = UShortArray.get-Mh2AYeg(var0, var3);
         if (var4) {
            var6.add(UShort.box-impl(var2));
         } else if (!var1.invoke(UShort.box-impl(var2)) as java.lang.Boolean) {
            var6.add(UShort.box-impl(var2));
            var4 = true;
         }
      }

      return var6;
   }

   @JvmStatic
   public inline fun UShortArray.elementAtOrElse(index: Int, defaultValue: (Int) -> UShort): UShort {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      val var3: Short;
      if (var1 >= 0 && var1 < UShortArray.getSize-impl(var0)) {
         var3 = UShortArray.get-Mh2AYeg(var0, var1);
      } else {
         var3 = (var2.invoke(var1) as UShort).unbox-impl();
      }

      return var3;
   }

   @JvmStatic
   public inline fun UIntArray.elementAtOrElse(index: Int, defaultValue: (Int) -> UInt): UInt {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      if (var1 >= 0 && var1 < UIntArray.getSize-impl(var0)) {
         var1 = UIntArray.get-pVg5ArA(var0, var1);
      } else {
         var1 = (var2.invoke(var1) as UInt).unbox-impl();
      }

      return var1;
   }

   @JvmStatic
   public inline fun ULongArray.elementAtOrElse(index: Int, defaultValue: (Int) -> ULong): ULong {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      val var3: Long;
      if (var1 >= 0 && var1 < ULongArray.getSize-impl(var0)) {
         var3 = ULongArray.get-s-VKNKU(var0, var1);
      } else {
         var3 = (var2.invoke(var1) as ULong).unbox-impl();
      }

      return var3;
   }

   @JvmStatic
   public inline fun UByteArray.elementAtOrElse(index: Int, defaultValue: (Int) -> UByte): UByte {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      val var3: Byte;
      if (var1 >= 0 && var1 < UByteArray.getSize-impl(var0)) {
         var3 = UByteArray.get-w2LRezQ(var0, var1);
      } else {
         var3 = (var2.invoke(var1) as UByte).unbox-impl();
      }

      return var3;
   }

   @JvmStatic
   public inline fun UByteArray.elementAtOrNull(index: Int): UByte? {
      return UArraysKt.getOrNull-PpDY95g(var0, var1);
   }

   @JvmStatic
   public inline fun UShortArray.elementAtOrNull(index: Int): UShort? {
      return UArraysKt.getOrNull-nggk6HY(var0, var1);
   }

   @JvmStatic
   public inline fun UIntArray.elementAtOrNull(index: Int): UInt? {
      return UArraysKt.getOrNull-qFRl0hI(var0, var1);
   }

   @JvmStatic
   public inline fun ULongArray.elementAtOrNull(index: Int): ULong? {
      return UArraysKt.getOrNull-r7IrZao(var0, var1);
   }

   @JvmStatic
   public fun UIntArray.fill(element: UInt, fromIndex: Int = ..., toIndex: Int = ...) {
      ArraysKt.fill(var0, var1, var2, var3);
   }

   @JvmStatic
   public fun UShortArray.fill(element: UShort, fromIndex: Int = ..., toIndex: Int = ...) {
      ArraysKt.fill(var0, var1, var2, var3);
   }

   @JvmStatic
   public fun ULongArray.fill(element: ULong, fromIndex: Int = ..., toIndex: Int = ...) {
      ArraysKt.fill(var0, var1, var3, var4);
   }

   @JvmStatic
   public fun UByteArray.fill(element: UByte, fromIndex: Int = ..., toIndex: Int = ...) {
      ArraysKt.fill(var0, var1, var2, var3);
   }

   @JvmStatic
   public inline fun UByteArray.filter(predicate: (UByte) -> Boolean): List<UByte> {
      val var5: java.util.Collection = new ArrayList();
      val var4: Int = UByteArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Byte = UByteArray.get-w2LRezQ(var0, var3);
         if (var1.invoke(UByte.box-impl(var2)) as java.lang.Boolean) {
            var5.add(UByte.box-impl(var2));
         }
      }

      return var5 as MutableList<UByte>;
   }

   @JvmStatic
   public inline fun ULongArray.filter(predicate: (ULong) -> Boolean): List<ULong> {
      val var6: java.util.Collection = new ArrayList();
      val var3: Int = ULongArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Long = ULongArray.get-s-VKNKU(var0, var2);
         if (var1.invoke(ULong.box-impl(var4)) as java.lang.Boolean) {
            var6.add(ULong.box-impl(var4));
         }
      }

      return var6 as MutableList<ULong>;
   }

   @JvmStatic
   public inline fun UIntArray.filter(predicate: (UInt) -> Boolean): List<UInt> {
      val var5: java.util.Collection = new ArrayList();
      val var3: Int = UIntArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Int = UIntArray.get-pVg5ArA(var0, var2);
         if (var1.invoke(UInt.box-impl(var4)) as java.lang.Boolean) {
            var5.add(UInt.box-impl(var4));
         }
      }

      return var5 as MutableList<UInt>;
   }

   @JvmStatic
   public inline fun UShortArray.filter(predicate: (UShort) -> Boolean): List<UShort> {
      val var5: java.util.Collection = new ArrayList();
      val var4: Int = UShortArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Short = UShortArray.get-Mh2AYeg(var0, var3);
         if (var1.invoke(UShort.box-impl(var2)) as java.lang.Boolean) {
            var5.add(UShort.box-impl(var2));
         }
      }

      return var5 as MutableList<UShort>;
   }

   @JvmStatic
   public inline fun UByteArray.filterIndexed(predicate: (Int, UByte) -> Boolean): List<UByte> {
      val var6: java.util.Collection = new ArrayList();
      val var5: Int = UByteArray.getSize-impl(var0);
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         val var2: Byte = UByteArray.get-w2LRezQ(var0, var4);
         if (var1.invoke(var3, UByte.box-impl(var2)) as java.lang.Boolean) {
            var6.add(UByte.box-impl(var2));
         }

         var4++;
      }

      return var6 as MutableList<UByte>;
   }

   @JvmStatic
   public inline fun UIntArray.filterIndexed(predicate: (Int, UInt) -> Boolean): List<UInt> {
      val var6: java.util.Collection = new ArrayList();
      val var4: Int = UIntArray.getSize-impl(var0);
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         val var5: Int = UIntArray.get-pVg5ArA(var0, var3);
         if (var1.invoke(var2, UInt.box-impl(var5)) as java.lang.Boolean) {
            var6.add(UInt.box-impl(var5));
         }

         var3++;
      }

      return var6 as MutableList<UInt>;
   }

   @JvmStatic
   public inline fun ULongArray.filterIndexed(predicate: (Int, ULong) -> Boolean): List<ULong> {
      val var7: java.util.Collection = new ArrayList();
      val var4: Int = ULongArray.getSize-impl(var0);
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         val var5: Long = ULongArray.get-s-VKNKU(var0, var3);
         if (var1.invoke(var2, ULong.box-impl(var5)) as java.lang.Boolean) {
            var7.add(ULong.box-impl(var5));
         }

         var3++;
      }

      return var7 as MutableList<ULong>;
   }

   @JvmStatic
   public inline fun UShortArray.filterIndexed(predicate: (Int, UShort) -> Boolean): List<UShort> {
      val var6: java.util.Collection = new ArrayList();
      val var5: Int = UShortArray.getSize-impl(var0);
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         val var2: Short = UShortArray.get-Mh2AYeg(var0, var4);
         if (var1.invoke(var3, UShort.box-impl(var2)) as java.lang.Boolean) {
            var6.add(UShort.box-impl(var2));
         }

         var4++;
      }

      return var6 as MutableList<UShort>;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in UInt>> UIntArray.filterIndexedTo(destination: C, predicate: (Int, UInt) -> Boolean): C {
      val var5: Int = UIntArray.getSize-impl(var0);
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         val var6: Int = UIntArray.get-pVg5ArA(var0, var4);
         if (var2.invoke(var3, UInt.box-impl(var6)) as java.lang.Boolean) {
            var1.add(UInt.box-impl(var6));
         }

         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in UShort>> UShortArray.filterIndexedTo(destination: C, predicate: (Int, UShort) -> Boolean): C {
      val var6: Int = UShortArray.getSize-impl(var0);
      var var5: Int = 0;

      for (int var4 = 0; var5 < var6; var4++) {
         val var3: Short = UShortArray.get-Mh2AYeg(var0, var5);
         if (var2.invoke(var4, UShort.box-impl(var3)) as java.lang.Boolean) {
            var1.add(UShort.box-impl(var3));
         }

         var5++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in UByte>> UByteArray.filterIndexedTo(destination: C, predicate: (Int, UByte) -> Boolean): C {
      val var6: Int = UByteArray.getSize-impl(var0);
      var var5: Int = 0;

      for (int var4 = 0; var5 < var6; var4++) {
         val var3: Byte = UByteArray.get-w2LRezQ(var0, var5);
         if (var2.invoke(var4, UByte.box-impl(var3)) as java.lang.Boolean) {
            var1.add(UByte.box-impl(var3));
         }

         var5++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in ULong>> ULongArray.filterIndexedTo(destination: C, predicate: (Int, ULong) -> Boolean): C {
      val var5: Int = ULongArray.getSize-impl(var0);
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         val var6: Long = ULongArray.get-s-VKNKU(var0, var4);
         if (var2.invoke(var3, ULong.box-impl(var6)) as java.lang.Boolean) {
            var1.add(ULong.box-impl(var6));
         }

         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun UByteArray.filterNot(predicate: (UByte) -> Boolean): List<UByte> {
      val var5: java.util.Collection = new ArrayList();
      val var4: Int = UByteArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Byte = UByteArray.get-w2LRezQ(var0, var3);
         if (!var1.invoke(UByte.box-impl(var2)) as java.lang.Boolean) {
            var5.add(UByte.box-impl(var2));
         }
      }

      return var5 as MutableList<UByte>;
   }

   @JvmStatic
   public inline fun ULongArray.filterNot(predicate: (ULong) -> Boolean): List<ULong> {
      val var6: java.util.Collection = new ArrayList();
      val var3: Int = ULongArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Long = ULongArray.get-s-VKNKU(var0, var2);
         if (!var1.invoke(ULong.box-impl(var4)) as java.lang.Boolean) {
            var6.add(ULong.box-impl(var4));
         }
      }

      return var6 as MutableList<ULong>;
   }

   @JvmStatic
   public inline fun UIntArray.filterNot(predicate: (UInt) -> Boolean): List<UInt> {
      val var5: java.util.Collection = new ArrayList();
      val var3: Int = UIntArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Int = UIntArray.get-pVg5ArA(var0, var2);
         if (!var1.invoke(UInt.box-impl(var4)) as java.lang.Boolean) {
            var5.add(UInt.box-impl(var4));
         }
      }

      return var5 as MutableList<UInt>;
   }

   @JvmStatic
   public inline fun UShortArray.filterNot(predicate: (UShort) -> Boolean): List<UShort> {
      val var5: java.util.Collection = new ArrayList();
      val var4: Int = UShortArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Short = UShortArray.get-Mh2AYeg(var0, var3);
         if (!var1.invoke(UShort.box-impl(var2)) as java.lang.Boolean) {
            var5.add(UShort.box-impl(var2));
         }
      }

      return var5 as MutableList<UShort>;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in ULong>> ULongArray.filterNotTo(destination: C, predicate: (ULong) -> Boolean): C {
      val var4: Int = ULongArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Long = ULongArray.get-s-VKNKU(var0, var3);
         if (!var2.invoke(ULong.box-impl(var5)) as java.lang.Boolean) {
            var1.add(ULong.box-impl(var5));
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in UShort>> UShortArray.filterNotTo(destination: C, predicate: (UShort) -> Boolean): C {
      val var5: Int = UShortArray.getSize-impl(var0);

      for (int var4 = 0; var4 < var5; var4++) {
         val var3: Short = UShortArray.get-Mh2AYeg(var0, var4);
         if (!var2.invoke(UShort.box-impl(var3)) as java.lang.Boolean) {
            var1.add(UShort.box-impl(var3));
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in UInt>> UIntArray.filterNotTo(destination: C, predicate: (UInt) -> Boolean): C {
      val var4: Int = UIntArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Int = UIntArray.get-pVg5ArA(var0, var3);
         if (!var2.invoke(UInt.box-impl(var5)) as java.lang.Boolean) {
            var1.add(UInt.box-impl(var5));
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in UByte>> UByteArray.filterNotTo(destination: C, predicate: (UByte) -> Boolean): C {
      val var5: Int = UByteArray.getSize-impl(var0);

      for (int var4 = 0; var4 < var5; var4++) {
         val var3: Byte = UByteArray.get-w2LRezQ(var0, var4);
         if (!var2.invoke(UByte.box-impl(var3)) as java.lang.Boolean) {
            var1.add(UByte.box-impl(var3));
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in ULong>> ULongArray.filterTo(destination: C, predicate: (ULong) -> Boolean): C {
      val var4: Int = ULongArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Long = ULongArray.get-s-VKNKU(var0, var3);
         if (var2.invoke(ULong.box-impl(var5)) as java.lang.Boolean) {
            var1.add(ULong.box-impl(var5));
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in UShort>> UShortArray.filterTo(destination: C, predicate: (UShort) -> Boolean): C {
      val var5: Int = UShortArray.getSize-impl(var0);

      for (int var4 = 0; var4 < var5; var4++) {
         val var3: Short = UShortArray.get-Mh2AYeg(var0, var4);
         if (var2.invoke(UShort.box-impl(var3)) as java.lang.Boolean) {
            var1.add(UShort.box-impl(var3));
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in UInt>> UIntArray.filterTo(destination: C, predicate: (UInt) -> Boolean): C {
      val var4: Int = UIntArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Int = UIntArray.get-pVg5ArA(var0, var3);
         if (var2.invoke(UInt.box-impl(var5)) as java.lang.Boolean) {
            var1.add(UInt.box-impl(var5));
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <C : MutableCollection<in UByte>> UByteArray.filterTo(destination: C, predicate: (UByte) -> Boolean): C {
      val var5: Int = UByteArray.getSize-impl(var0);

      for (int var4 = 0; var4 < var5; var4++) {
         val var3: Byte = UByteArray.get-w2LRezQ(var0, var4);
         if (var2.invoke(UByte.box-impl(var3)) as java.lang.Boolean) {
            var1.add(UByte.box-impl(var3));
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun UByteArray.find(predicate: (UByte) -> Boolean): UByte? {
      val var4: Int = UByteArray.getSize-impl(var0);
      var var3: Int = 0;

      while (true) {
         if (var3 >= var4) {
            var5 = null;
            break;
         }

         val var2: Byte = UByteArray.get-w2LRezQ(var0, var3);
         if (var1.invoke(UByte.box-impl(var2)) as java.lang.Boolean) {
            var5 = UByte.box-impl(var2);
            break;
         }

         var3++;
      }

      return var5;
   }

   @JvmStatic
   public inline fun ULongArray.find(predicate: (ULong) -> Boolean): ULong? {
      val var3: Int = ULongArray.getSize-impl(var0);
      var var2: Int = 0;

      while (true) {
         if (var2 >= var3) {
            var6 = null;
            break;
         }

         val var4: Long = ULongArray.get-s-VKNKU(var0, var2);
         if (var1.invoke(ULong.box-impl(var4)) as java.lang.Boolean) {
            var6 = ULong.box-impl(var4);
            break;
         }

         var2++;
      }

      return var6;
   }

   @JvmStatic
   public inline fun UIntArray.find(predicate: (UInt) -> Boolean): UInt? {
      val var3: Int = UIntArray.getSize-impl(var0);
      var var2: Int = 0;

      while (true) {
         if (var2 >= var3) {
            var5 = null;
            break;
         }

         val var4: Int = UIntArray.get-pVg5ArA(var0, var2);
         if (var1.invoke(UInt.box-impl(var4)) as java.lang.Boolean) {
            var5 = UInt.box-impl(var4);
            break;
         }

         var2++;
      }

      return var5;
   }

   @JvmStatic
   public inline fun UShortArray.find(predicate: (UShort) -> Boolean): UShort? {
      val var4: Int = UShortArray.getSize-impl(var0);
      var var3: Int = 0;

      while (true) {
         if (var3 >= var4) {
            var5 = null;
            break;
         }

         val var2: Short = UShortArray.get-Mh2AYeg(var0, var3);
         if (var1.invoke(UShort.box-impl(var2)) as java.lang.Boolean) {
            var5 = UShort.box-impl(var2);
            break;
         }

         var3++;
      }

      return var5;
   }

   @JvmStatic
   public inline fun UByteArray.findLast(predicate: (UByte) -> Boolean): UByte? {
      var var3: Int = UByteArray.getSize-impl(var0) - 1;
      if (var3 >= 0) {
         while (true) {
            val var4: Int = var3 - 1;
            val var2: Byte = UByteArray.get-w2LRezQ(var0, var3);
            if (var1.invoke(UByte.box-impl(var2)) as java.lang.Boolean) {
               return UByte.box-impl(var2);
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
   public inline fun ULongArray.findLast(predicate: (ULong) -> Boolean): ULong? {
      var var2: Int = ULongArray.getSize-impl(var0) - 1;
      if (var2 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            val var4: Long = ULongArray.get-s-VKNKU(var0, var2);
            if (var1.invoke(ULong.box-impl(var4)) as java.lang.Boolean) {
               return ULong.box-impl(var4);
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
   public inline fun UIntArray.findLast(predicate: (UInt) -> Boolean): UInt? {
      var var2: Int = UIntArray.getSize-impl(var0) - 1;
      if (var2 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            var2 = UIntArray.get-pVg5ArA(var0, var2);
            if (var1.invoke(UInt.box-impl(var2)) as java.lang.Boolean) {
               return UInt.box-impl(var2);
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
   public inline fun UShortArray.findLast(predicate: (UShort) -> Boolean): UShort? {
      var var3: Int = UShortArray.getSize-impl(var0) - 1;
      if (var3 >= 0) {
         while (true) {
            val var4: Int = var3 - 1;
            val var2: Short = UShortArray.get-Mh2AYeg(var0, var3);
            if (var1.invoke(UShort.box-impl(var2)) as java.lang.Boolean) {
               return UShort.box-impl(var2);
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
   public inline fun UIntArray.first(): UInt {
      return UInt.constructor-impl(ArraysKt.first(var0));
   }

   @JvmStatic
   public inline fun UByteArray.first(): UByte {
      return UByte.constructor-impl(ArraysKt.first(var0));
   }

   @JvmStatic
   public inline fun UByteArray.first(predicate: (UByte) -> Boolean): UByte {
      val var4: Int = UByteArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Byte = UByteArray.get-w2LRezQ(var0, var3);
         if (var1.invoke(UByte.box-impl(var2)) as java.lang.Boolean) {
            return var2;
         }
      }

      throw new NoSuchElementException("Array contains no element matching the predicate.");
   }

   @JvmStatic
   public inline fun ULongArray.first(predicate: (ULong) -> Boolean): ULong {
      val var3: Int = ULongArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Long = ULongArray.get-s-VKNKU(var0, var2);
         if (var1.invoke(ULong.box-impl(var4)) as java.lang.Boolean) {
            return var4;
         }
      }

      throw new NoSuchElementException("Array contains no element matching the predicate.");
   }

   @JvmStatic
   public inline fun ULongArray.first(): ULong {
      return ULong.constructor-impl(ArraysKt.first(var0));
   }

   @JvmStatic
   public inline fun UIntArray.first(predicate: (UInt) -> Boolean): UInt {
      val var3: Int = UIntArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Int = UIntArray.get-pVg5ArA(var0, var2);
         if (var1.invoke(UInt.box-impl(var4)) as java.lang.Boolean) {
            return var4;
         }
      }

      throw new NoSuchElementException("Array contains no element matching the predicate.");
   }

   @JvmStatic
   public inline fun UShortArray.first(): UShort {
      return UShort.constructor-impl(ArraysKt.first(var0));
   }

   @JvmStatic
   public inline fun UShortArray.first(predicate: (UShort) -> Boolean): UShort {
      val var4: Int = UShortArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Short = UShortArray.get-Mh2AYeg(var0, var3);
         if (var1.invoke(UShort.box-impl(var2)) as java.lang.Boolean) {
            return var2;
         }
      }

      throw new NoSuchElementException("Array contains no element matching the predicate.");
   }

   @JvmStatic
   public fun UIntArray.firstOrNull(): UInt? {
      val var1: UInt;
      if (UIntArray.isEmpty-impl(var0)) {
         var1 = null;
      } else {
         var1 = UInt.box-impl(UIntArray.get-pVg5ArA(var0, 0));
      }

      return var1;
   }

   @JvmStatic
   public fun UByteArray.firstOrNull(): UByte? {
      val var1: UByte;
      if (UByteArray.isEmpty-impl(var0)) {
         var1 = null;
      } else {
         var1 = UByte.box-impl(UByteArray.get-w2LRezQ(var0, 0));
      }

      return var1;
   }

   @JvmStatic
   public inline fun UByteArray.firstOrNull(predicate: (UByte) -> Boolean): UByte? {
      val var4: Int = UByteArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Byte = UByteArray.get-w2LRezQ(var0, var3);
         if (var1.invoke(UByte.box-impl(var2)) as java.lang.Boolean) {
            return UByte.box-impl(var2);
         }
      }

      return null;
   }

   @JvmStatic
   public inline fun ULongArray.firstOrNull(predicate: (ULong) -> Boolean): ULong? {
      val var3: Int = ULongArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Long = ULongArray.get-s-VKNKU(var0, var2);
         if (var1.invoke(ULong.box-impl(var4)) as java.lang.Boolean) {
            return ULong.box-impl(var4);
         }
      }

      return null;
   }

   @JvmStatic
   public fun ULongArray.firstOrNull(): ULong? {
      val var1: ULong;
      if (ULongArray.isEmpty-impl(var0)) {
         var1 = null;
      } else {
         var1 = ULong.box-impl(ULongArray.get-s-VKNKU(var0, 0));
      }

      return var1;
   }

   @JvmStatic
   public inline fun UIntArray.firstOrNull(predicate: (UInt) -> Boolean): UInt? {
      val var3: Int = UIntArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Int = UIntArray.get-pVg5ArA(var0, var2);
         if (var1.invoke(UInt.box-impl(var4)) as java.lang.Boolean) {
            return UInt.box-impl(var4);
         }
      }

      return null;
   }

   @JvmStatic
   public fun UShortArray.firstOrNull(): UShort? {
      val var1: UShort;
      if (UShortArray.isEmpty-impl(var0)) {
         var1 = null;
      } else {
         var1 = UShort.box-impl(UShortArray.get-Mh2AYeg(var0, 0));
      }

      return var1;
   }

   @JvmStatic
   public inline fun UShortArray.firstOrNull(predicate: (UShort) -> Boolean): UShort? {
      val var4: Int = UShortArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Short = UShortArray.get-Mh2AYeg(var0, var3);
         if (var1.invoke(UShort.box-impl(var2)) as java.lang.Boolean) {
            return UShort.box-impl(var2);
         }
      }

      return null;
   }

   @JvmStatic
   public inline fun <R> UByteArray.flatMap(transform: (UByte) -> Iterable<R>): List<R> {
      val var4: java.util.Collection = new ArrayList();
      val var3: Int = UByteArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         CollectionsKt.addAll(var4, var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var2))) as java.lang.Iterable);
      }

      return var4 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> ULongArray.flatMap(transform: (ULong) -> Iterable<R>): List<R> {
      val var4: java.util.Collection = new ArrayList();
      val var3: Int = ULongArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         CollectionsKt.addAll(var4, var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2))) as java.lang.Iterable);
      }

      return var4 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> UIntArray.flatMap(transform: (UInt) -> Iterable<R>): List<R> {
      val var4: java.util.Collection = new ArrayList();
      val var3: Int = UIntArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         CollectionsKt.addAll(var4, var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2))) as java.lang.Iterable);
      }

      return var4 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> UShortArray.flatMap(transform: (UShort) -> Iterable<R>): List<R> {
      val var4: java.util.Collection = new ArrayList();
      val var3: Int = UShortArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         CollectionsKt.addAll(var4, var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var2))) as java.lang.Iterable);
      }

      return var4 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> UByteArray.flatMapIndexed(transform: (Int, UByte) -> Iterable<R>): List<R> {
      val var5: java.util.Collection = new ArrayList();
      val var4: Int = UByteArray.getSize-impl(var0);
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         CollectionsKt.addAll(var5, var1.invoke(var2, UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3))) as java.lang.Iterable);
         var3++;
      }

      return var5 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> UIntArray.flatMapIndexed(transform: (Int, UInt) -> Iterable<R>): List<R> {
      val var5: java.util.Collection = new ArrayList();
      val var4: Int = UIntArray.getSize-impl(var0);
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         CollectionsKt.addAll(var5, var1.invoke(var2, UInt.box-impl(UIntArray.get-pVg5ArA(var0, var3))) as java.lang.Iterable);
         var3++;
      }

      return var5 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> ULongArray.flatMapIndexed(transform: (Int, ULong) -> Iterable<R>): List<R> {
      val var5: java.util.Collection = new ArrayList();
      val var4: Int = ULongArray.getSize-impl(var0);
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         CollectionsKt.addAll(var5, var1.invoke(var2, ULong.box-impl(ULongArray.get-s-VKNKU(var0, var3))) as java.lang.Iterable);
         var3++;
      }

      return var5 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> UShortArray.flatMapIndexed(transform: (Int, UShort) -> Iterable<R>): List<R> {
      val var5: java.util.Collection = new ArrayList();
      val var4: Int = UShortArray.getSize-impl(var0);
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         CollectionsKt.addAll(var5, var1.invoke(var2, UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var3))) as java.lang.Iterable);
         var3++;
      }

      return var5 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> UIntArray.flatMapIndexedTo(destination: C, transform: (Int, UInt) -> Iterable<R>): C {
      val var5: Int = UIntArray.getSize-impl(var0);
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         CollectionsKt.addAll(var1, var2.invoke(var3, UInt.box-impl(UIntArray.get-pVg5ArA(var0, var4))) as java.lang.Iterable);
         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> UShortArray.flatMapIndexedTo(destination: C, transform: (Int, UShort) -> Iterable<R>): C {
      val var5: Int = UShortArray.getSize-impl(var0);
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         CollectionsKt.addAll(var1, var2.invoke(var3, UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var4))) as java.lang.Iterable);
         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> UByteArray.flatMapIndexedTo(destination: C, transform: (Int, UByte) -> Iterable<R>): C {
      val var5: Int = UByteArray.getSize-impl(var0);
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         CollectionsKt.addAll(var1, var2.invoke(var3, UByte.box-impl(UByteArray.get-w2LRezQ(var0, var4))) as java.lang.Iterable);
         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> ULongArray.flatMapIndexedTo(destination: C, transform: (Int, ULong) -> Iterable<R>): C {
      val var5: Int = ULongArray.getSize-impl(var0);
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         CollectionsKt.addAll(var1, var2.invoke(var3, ULong.box-impl(ULongArray.get-s-VKNKU(var0, var4))) as java.lang.Iterable);
         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> ULongArray.flatMapTo(destination: C, transform: (ULong) -> Iterable<R>): C {
      val var4: Int = ULongArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         CollectionsKt.addAll(var1, var2.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var3))) as java.lang.Iterable);
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> UShortArray.flatMapTo(destination: C, transform: (UShort) -> Iterable<R>): C {
      val var4: Int = UShortArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         CollectionsKt.addAll(var1, var2.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var3))) as java.lang.Iterable);
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> UIntArray.flatMapTo(destination: C, transform: (UInt) -> Iterable<R>): C {
      val var4: Int = UIntArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         CollectionsKt.addAll(var1, var2.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var3))) as java.lang.Iterable);
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> UByteArray.flatMapTo(destination: C, transform: (UByte) -> Iterable<R>): C {
      val var4: Int = UByteArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         CollectionsKt.addAll(var1, var2.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3))) as java.lang.Iterable);
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R> ULongArray.fold(initial: R, operation: (R, ULong) -> R): R {
      val var4: Int = ULongArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         var1 = var2.invoke(var1, ULong.box-impl(ULongArray.get-s-VKNKU(var0, var3)));
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> UByteArray.fold(initial: R, operation: (R, UByte) -> R): R {
      val var4: Int = UByteArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         var1 = var2.invoke(var1, UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3)));
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> UIntArray.fold(initial: R, operation: (R, UInt) -> R): R {
      val var4: Int = UIntArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         var1 = var2.invoke(var1, UInt.box-impl(UIntArray.get-pVg5ArA(var0, var3)));
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> UShortArray.fold(initial: R, operation: (R, UShort) -> R): R {
      val var4: Int = UShortArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         var1 = var2.invoke(var1, UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var3)));
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> UByteArray.foldIndexed(initial: R, operation: (Int, R, UByte) -> R): R {
      val var5: Int = UByteArray.getSize-impl(var0);
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         var1 = var2.invoke(var3, var1, UByte.box-impl(UByteArray.get-w2LRezQ(var0, var4)));
         var4++;
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> UShortArray.foldIndexed(initial: R, operation: (Int, R, UShort) -> R): R {
      val var5: Int = UShortArray.getSize-impl(var0);
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         var1 = var2.invoke(var3, var1, UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var4)));
         var4++;
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> ULongArray.foldIndexed(initial: R, operation: (Int, R, ULong) -> R): R {
      val var5: Int = ULongArray.getSize-impl(var0);
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         var1 = var2.invoke(var3, var1, ULong.box-impl(ULongArray.get-s-VKNKU(var0, var4)));
         var4++;
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> UIntArray.foldIndexed(initial: R, operation: (Int, R, UInt) -> R): R {
      val var5: Int = UIntArray.getSize-impl(var0);
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         var1 = var2.invoke(var3, var1, UInt.box-impl(UIntArray.get-pVg5ArA(var0, var4)));
         var4++;
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> ULongArray.foldRight(initial: R, operation: (ULong, R) -> R): R {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 >= 0; var3--) {
         var1 = var2.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var3)), var1);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> UByteArray.foldRight(initial: R, operation: (UByte, R) -> R): R {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 >= 0; var3--) {
         var1 = var2.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3)), var1);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> UIntArray.foldRight(initial: R, operation: (UInt, R) -> R): R {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 >= 0; var3--) {
         var1 = var2.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var3)), var1);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> UShortArray.foldRight(initial: R, operation: (UShort, R) -> R): R {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 >= 0; var3--) {
         var1 = var2.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var3)), var1);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> UByteArray.foldRightIndexed(initial: R, operation: (Int, UByte, R) -> R): R {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 >= 0; var3--) {
         var1 = var2.invoke(var3, UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3)), var1);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> UShortArray.foldRightIndexed(initial: R, operation: (Int, UShort, R) -> R): R {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 >= 0; var3--) {
         var1 = var2.invoke(var3, UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var3)), var1);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> ULongArray.foldRightIndexed(initial: R, operation: (Int, ULong, R) -> R): R {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 >= 0; var3--) {
         var1 = var2.invoke(var3, ULong.box-impl(ULongArray.get-s-VKNKU(var0, var3)), var1);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun <R> UIntArray.foldRightIndexed(initial: R, operation: (Int, UInt, R) -> R): R {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 >= 0; var3--) {
         var1 = var2.invoke(var3, UInt.box-impl(UIntArray.get-pVg5ArA(var0, var3)), var1);
      }

      return (R)var1;
   }

   @JvmStatic
   public inline fun UByteArray.forEach(action: (UByte) -> Unit) {
      val var3: Int = UByteArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var2)));
      }
   }

   @JvmStatic
   public inline fun ULongArray.forEach(action: (ULong) -> Unit) {
      val var3: Int = ULongArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2)));
      }
   }

   @JvmStatic
   public inline fun UIntArray.forEach(action: (UInt) -> Unit) {
      val var3: Int = UIntArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2)));
      }
   }

   @JvmStatic
   public inline fun UShortArray.forEach(action: (UShort) -> Unit) {
      val var3: Int = UShortArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var2)));
      }
   }

   @JvmStatic
   public inline fun UByteArray.forEachIndexed(action: (Int, UByte) -> Unit) {
      val var4: Int = UByteArray.getSize-impl(var0);
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var1.invoke(var2, UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3)));
         var3++;
      }
   }

   @JvmStatic
   public inline fun UIntArray.forEachIndexed(action: (Int, UInt) -> Unit) {
      val var4: Int = UIntArray.getSize-impl(var0);
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var1.invoke(var2, UInt.box-impl(UIntArray.get-pVg5ArA(var0, var3)));
         var3++;
      }
   }

   @JvmStatic
   public inline fun ULongArray.forEachIndexed(action: (Int, ULong) -> Unit) {
      val var4: Int = ULongArray.getSize-impl(var0);
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var1.invoke(var2, ULong.box-impl(ULongArray.get-s-VKNKU(var0, var3)));
         var3++;
      }
   }

   @JvmStatic
   public inline fun UShortArray.forEachIndexed(action: (Int, UShort) -> Unit) {
      val var4: Int = UShortArray.getSize-impl(var0);
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var1.invoke(var2, UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var3)));
         var3++;
      }
   }

   @JvmStatic
   public inline fun UShortArray.getOrElse(index: Int, defaultValue: (Int) -> UShort): UShort {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      val var3: Short;
      if (var1 >= 0 && var1 < UShortArray.getSize-impl(var0)) {
         var3 = UShortArray.get-Mh2AYeg(var0, var1);
      } else {
         var3 = (var2.invoke(var1) as UShort).unbox-impl();
      }

      return var3;
   }

   @JvmStatic
   public inline fun UIntArray.getOrElse(index: Int, defaultValue: (Int) -> UInt): UInt {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      if (var1 >= 0 && var1 < UIntArray.getSize-impl(var0)) {
         var1 = UIntArray.get-pVg5ArA(var0, var1);
      } else {
         var1 = (var2.invoke(var1) as UInt).unbox-impl();
      }

      return var1;
   }

   @JvmStatic
   public inline fun ULongArray.getOrElse(index: Int, defaultValue: (Int) -> ULong): ULong {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      val var3: Long;
      if (var1 >= 0 && var1 < ULongArray.getSize-impl(var0)) {
         var3 = ULongArray.get-s-VKNKU(var0, var1);
      } else {
         var3 = (var2.invoke(var1) as ULong).unbox-impl();
      }

      return var3;
   }

   @JvmStatic
   public inline fun UByteArray.getOrElse(index: Int, defaultValue: (Int) -> UByte): UByte {
      contract {
         callsInPlace(defaultValue, InvocationKind.AT_MOST_ONCE)
      }

      val var3: Byte;
      if (var1 >= 0 && var1 < UByteArray.getSize-impl(var0)) {
         var3 = UByteArray.get-w2LRezQ(var0, var1);
      } else {
         var3 = (var2.invoke(var1) as UByte).unbox-impl();
      }

      return var3;
   }

   @JvmStatic
   public fun UByteArray.getOrNull(index: Int): UByte? {
      val var2: UByte;
      if (var1 >= 0 && var1 < UByteArray.getSize-impl(var0)) {
         var2 = UByte.box-impl(UByteArray.get-w2LRezQ(var0, var1));
      } else {
         var2 = null;
      }

      return var2;
   }

   @JvmStatic
   public fun UShortArray.getOrNull(index: Int): UShort? {
      val var2: UShort;
      if (var1 >= 0 && var1 < UShortArray.getSize-impl(var0)) {
         var2 = UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var1));
      } else {
         var2 = null;
      }

      return var2;
   }

   @JvmStatic
   public fun UIntArray.getOrNull(index: Int): UInt? {
      val var2: UInt;
      if (var1 >= 0 && var1 < UIntArray.getSize-impl(var0)) {
         var2 = UInt.box-impl(UIntArray.get-pVg5ArA(var0, var1));
      } else {
         var2 = null;
      }

      return var2;
   }

   @JvmStatic
   public fun ULongArray.getOrNull(index: Int): ULong? {
      val var2: ULong;
      if (var1 >= 0 && var1 < ULongArray.getSize-impl(var0)) {
         var2 = ULong.box-impl(ULongArray.get-s-VKNKU(var0, var1));
      } else {
         var2 = null;
      }

      return var2;
   }

   @JvmStatic
   public inline fun <K, V> ULongArray.groupBy(keySelector: (ULong) -> K, valueTransform: (ULong) -> V): Map<K, List<V>> {
      val var9: java.util.Map = new LinkedHashMap();
      val var4: Int = ULongArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Long = ULongArray.get-s-VKNKU(var0, var3);
         val var10: Any = var1.invoke(ULong.box-impl(var5));
         val var8: Any = var9.get(var10);
         var var7: Any = var8;
         if (var8 == null) {
            var7 = new ArrayList();
            var9.put(var10, var7);
         }

         (var7 as java.util.List).add(var2.invoke(ULong.box-impl(var5)));
      }

      return var9;
   }

   @JvmStatic
   public inline fun <K, V> UShortArray.groupBy(keySelector: (UShort) -> K, valueTransform: (UShort) -> V): Map<K, List<V>> {
      val var9: java.util.Map = new LinkedHashMap();
      val var5: Int = UShortArray.getSize-impl(var0);

      for (int var4 = 0; var4 < var5; var4++) {
         val var3: Short = UShortArray.get-Mh2AYeg(var0, var4);
         val var8: Any = var1.invoke(UShort.box-impl(var3));
         val var7: Any = var9.get(var8);
         var var6: Any = var7;
         if (var7 == null) {
            var6 = new ArrayList();
            var9.put(var8, var6);
         }

         (var6 as java.util.List).add(var2.invoke(UShort.box-impl(var3)));
      }

      return var9;
   }

   @JvmStatic
   public inline fun <K> UByteArray.groupBy(keySelector: (UByte) -> K): Map<K, List<UByte>> {
      val var7: java.util.Map = new LinkedHashMap();
      val var4: Int = UByteArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Byte = UByteArray.get-w2LRezQ(var0, var3);
         val var8: Any = var1.invoke(UByte.box-impl(var2));
         val var6: Any = var7.get(var8);
         var var5: Any = var6;
         if (var6 == null) {
            var5 = new ArrayList();
            var7.put(var8, var5);
         }

         (var5 as java.util.List).add(UByte.box-impl(var2));
      }

      return var7;
   }

   @JvmStatic
   public inline fun <K, V> UIntArray.groupBy(keySelector: (UInt) -> K, valueTransform: (UInt) -> V): Map<K, List<V>> {
      val var9: java.util.Map = new LinkedHashMap();
      val var4: Int = UIntArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Int = UIntArray.get-pVg5ArA(var0, var3);
         val var8: Any = var1.invoke(UInt.box-impl(var5));
         val var7: Any = var9.get(var8);
         var var6: Any = var7;
         if (var7 == null) {
            var6 = new ArrayList();
            var9.put(var8, var6);
         }

         (var6 as java.util.List).add(var2.invoke(UInt.box-impl(var5)));
      }

      return var9;
   }

   @JvmStatic
   public inline fun <K> ULongArray.groupBy(keySelector: (ULong) -> K): Map<K, List<ULong>> {
      val var8: java.util.Map = new LinkedHashMap();
      val var3: Int = ULongArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Long = ULongArray.get-s-VKNKU(var0, var2);
         val var9: Any = var1.invoke(ULong.box-impl(var4));
         val var7: Any = var8.get(var9);
         var var6: Any = var7;
         if (var7 == null) {
            var6 = new ArrayList();
            var8.put(var9, var6);
         }

         (var6 as java.util.List).add(ULong.box-impl(var4));
      }

      return var8;
   }

   @JvmStatic
   public inline fun <K, V> UByteArray.groupBy(keySelector: (UByte) -> K, valueTransform: (UByte) -> V): Map<K, List<V>> {
      val var8: java.util.Map = new LinkedHashMap();
      val var5: Int = UByteArray.getSize-impl(var0);

      for (int var4 = 0; var4 < var5; var4++) {
         val var3: Byte = UByteArray.get-w2LRezQ(var0, var4);
         val var9: Any = var1.invoke(UByte.box-impl(var3));
         val var7: Any = var8.get(var9);
         var var6: Any = var7;
         if (var7 == null) {
            var6 = new ArrayList();
            var8.put(var9, var6);
         }

         (var6 as java.util.List).add(var2.invoke(UByte.box-impl(var3)));
      }

      return var8;
   }

   @JvmStatic
   public inline fun <K> UIntArray.groupBy(keySelector: (UInt) -> K): Map<K, List<UInt>> {
      val var8: java.util.Map = new LinkedHashMap();
      val var3: Int = UIntArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Int = UIntArray.get-pVg5ArA(var0, var2);
         val var7: Any = var1.invoke(UInt.box-impl(var4));
         val var6: Any = var8.get(var7);
         var var5: Any = var6;
         if (var6 == null) {
            var5 = new ArrayList();
            var8.put(var7, var5);
         }

         (var5 as java.util.List).add(UInt.box-impl(var4));
      }

      return var8;
   }

   @JvmStatic
   public inline fun <K> UShortArray.groupBy(keySelector: (UShort) -> K): Map<K, List<UShort>> {
      val var8: java.util.Map = new LinkedHashMap();
      val var4: Int = UShortArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Short = UShortArray.get-Mh2AYeg(var0, var3);
         val var7: Any = var1.invoke(UShort.box-impl(var2));
         val var6: Any = var8.get(var7);
         var var5: Any = var6;
         if (var6 == null) {
            var5 = new ArrayList();
            var8.put(var7, var5);
         }

         (var5 as java.util.List).add(UShort.box-impl(var2));
      }

      return var8;
   }

   @JvmStatic
   public inline fun <K, M : MutableMap<in K, MutableList<UInt>>> UIntArray.groupByTo(destination: M, keySelector: (UInt) -> K): M {
      val var4: Int = UIntArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Int = UIntArray.get-pVg5ArA(var0, var3);
         val var8: Any = var2.invoke(UInt.box-impl(var5));
         val var7: Any = var1.get(var8);
         var var6: Any = var7;
         if (var7 == null) {
            var6 = new ArrayList();
            var1.put(var8, var6);
         }

         (var6 as java.util.List).add(UInt.box-impl(var5));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, M : MutableMap<in K, MutableList<UByte>>> UByteArray.groupByTo(destination: M, keySelector: (UByte) -> K): M {
      val var5: Int = UByteArray.getSize-impl(var0);

      for (int var4 = 0; var4 < var5; var4++) {
         val var3: Byte = UByteArray.get-w2LRezQ(var0, var4);
         val var8: Any = var2.invoke(UByte.box-impl(var3));
         val var7: Any = var1.get(var8);
         var var6: Any = var7;
         if (var7 == null) {
            var6 = new ArrayList();
            var1.put(var8, var6);
         }

         (var6 as java.util.List).add(UByte.box-impl(var3));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, MutableList<V>>> UIntArray.groupByTo(destination: M, keySelector: (UInt) -> K, valueTransform: (UInt) -> V): M {
      val var5: Int = UIntArray.getSize-impl(var0);

      for (int var4 = 0; var4 < var5; var4++) {
         val var6: Int = UIntArray.get-pVg5ArA(var0, var4);
         val var9: Any = var2.invoke(UInt.box-impl(var6));
         val var8: Any = var1.get(var9);
         var var7: Any = var8;
         if (var8 == null) {
            var7 = new ArrayList();
            var1.put(var9, var7);
         }

         (var7 as java.util.List).add(var3.invoke(UInt.box-impl(var6)));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, MutableList<V>>> ULongArray.groupByTo(destination: M, keySelector: (ULong) -> K, valueTransform: (ULong) -> V): M {
      val var5: Int = ULongArray.getSize-impl(var0);

      for (int var4 = 0; var4 < var5; var4++) {
         val var6: Long = ULongArray.get-s-VKNKU(var0, var4);
         val var10: Any = var2.invoke(ULong.box-impl(var6));
         val var9: Any = var1.get(var10);
         var var8: Any = var9;
         if (var9 == null) {
            var8 = new ArrayList();
            var1.put(var10, var8);
         }

         (var8 as java.util.List).add(var3.invoke(ULong.box-impl(var6)));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, M : MutableMap<in K, MutableList<ULong>>> ULongArray.groupByTo(destination: M, keySelector: (ULong) -> K): M {
      val var4: Int = ULongArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Long = ULongArray.get-s-VKNKU(var0, var3);
         val var9: Any = var2.invoke(ULong.box-impl(var5));
         val var8: Any = var1.get(var9);
         var var7: Any = var8;
         if (var8 == null) {
            var7 = new ArrayList();
            var1.put(var9, var7);
         }

         (var7 as java.util.List).add(ULong.box-impl(var5));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, M : MutableMap<in K, MutableList<UShort>>> UShortArray.groupByTo(destination: M, keySelector: (UShort) -> K): M {
      val var5: Int = UShortArray.getSize-impl(var0);

      for (int var4 = 0; var4 < var5; var4++) {
         val var3: Short = UShortArray.get-Mh2AYeg(var0, var4);
         val var8: Any = var2.invoke(UShort.box-impl(var3));
         val var7: Any = var1.get(var8);
         var var6: Any = var7;
         if (var7 == null) {
            var6 = new ArrayList();
            var1.put(var8, var6);
         }

         (var6 as java.util.List).add(UShort.box-impl(var3));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, MutableList<V>>> UShortArray.groupByTo(
      destination: M,
      keySelector: (UShort) -> K,
      valueTransform: (UShort) -> V
   ): M {
      val var6: Int = UShortArray.getSize-impl(var0);

      for (int var5 = 0; var5 < var6; var5++) {
         val var4: Short = UShortArray.get-Mh2AYeg(var0, var5);
         val var9: Any = var2.invoke(UShort.box-impl(var4));
         val var8: Any = var1.get(var9);
         var var7: Any = var8;
         if (var8 == null) {
            var7 = new ArrayList();
            var1.put(var9, var7);
         }

         (var7 as java.util.List).add(var3.invoke(UShort.box-impl(var4)));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun <K, V, M : MutableMap<in K, MutableList<V>>> UByteArray.groupByTo(destination: M, keySelector: (UByte) -> K, valueTransform: (UByte) -> V): M {
      val var6: Int = UByteArray.getSize-impl(var0);

      for (int var5 = 0; var5 < var6; var5++) {
         val var4: Byte = UByteArray.get-w2LRezQ(var0, var5);
         val var9: Any = var2.invoke(UByte.box-impl(var4));
         val var8: Any = var1.get(var9);
         var var7: Any = var8;
         if (var8 == null) {
            var7 = new ArrayList();
            var1.put(var9, var7);
         }

         (var7 as java.util.List).add(var3.invoke(UByte.box-impl(var4)));
      }

      return (M)var1;
   }

   @JvmStatic
   public inline fun ULongArray.indexOf(element: ULong): Int {
      return ArraysKt.indexOf(var0, var1);
   }

   @JvmStatic
   public inline fun UShortArray.indexOf(element: UShort): Int {
      return ArraysKt.indexOf(var0, var1);
   }

   @JvmStatic
   public inline fun UByteArray.indexOf(element: UByte): Int {
      return ArraysKt.indexOf(var0, var1);
   }

   @JvmStatic
   public inline fun UIntArray.indexOf(element: UInt): Int {
      return ArraysKt.indexOf(var0, var1);
   }

   @JvmStatic
   public inline fun UByteArray.indexOfFirst(predicate: (UByte) -> Boolean): Int {
      val var3: Int = var0.length;
      var var2: Int = 0;

      while (true) {
         if (var2 >= var3) {
            var2 = -1;
            break;
         }

         if (var1.invoke(UByte.box-impl(UByte.constructor-impl(var0[var2]))) as java.lang.Boolean) {
            break;
         }

         var2++;
      }

      return var2;
   }

   @JvmStatic
   public inline fun ULongArray.indexOfFirst(predicate: (ULong) -> Boolean): Int {
      val var3: Int = var0.length;
      var var2: Int = 0;

      while (true) {
         if (var2 >= var3) {
            var2 = -1;
            break;
         }

         if (var1.invoke(ULong.box-impl(ULong.constructor-impl(var0[var2]))) as java.lang.Boolean) {
            break;
         }

         var2++;
      }

      return var2;
   }

   @JvmStatic
   public inline fun UIntArray.indexOfFirst(predicate: (UInt) -> Boolean): Int {
      val var3: Int = var0.length;
      var var2: Int = 0;

      while (true) {
         if (var2 >= var3) {
            var2 = -1;
            break;
         }

         if (var1.invoke(UInt.box-impl(UInt.constructor-impl(var0[var2]))) as java.lang.Boolean) {
            break;
         }

         var2++;
      }

      return var2;
   }

   @JvmStatic
   public inline fun UShortArray.indexOfFirst(predicate: (UShort) -> Boolean): Int {
      val var3: Int = var0.length;
      var var2: Int = 0;

      while (true) {
         if (var2 >= var3) {
            var2 = -1;
            break;
         }

         if (var1.invoke(UShort.box-impl(UShort.constructor-impl(var0[var2]))) as java.lang.Boolean) {
            break;
         }

         var2++;
      }

      return var2;
   }

   @JvmStatic
   public inline fun UByteArray.indexOfLast(predicate: (UByte) -> Boolean): Int {
      var var4: Int = var0.length - 1;
      var var5: Int = -1;
      if (var4 >= 0) {
         var5 = var4;

         while (true) {
            var4 = var5 - 1;
            if (var1.invoke(UByte.box-impl(UByte.constructor-impl(var0[var5]))) as java.lang.Boolean) {
               break;
            }

            if (var4 < 0) {
               var5 = -1;
               break;
            }

            var5 = var4;
         }
      }

      return var5;
   }

   @JvmStatic
   public inline fun ULongArray.indexOfLast(predicate: (ULong) -> Boolean): Int {
      var var4: Int = var0.length - 1;
      var var5: Int = -1;
      if (var4 >= 0) {
         var5 = var4;

         while (true) {
            var4 = var5 - 1;
            if (var1.invoke(ULong.box-impl(ULong.constructor-impl(var0[var5]))) as java.lang.Boolean) {
               break;
            }

            if (var4 < 0) {
               var5 = -1;
               break;
            }

            var5 = var4;
         }
      }

      return var5;
   }

   @JvmStatic
   public inline fun UIntArray.indexOfLast(predicate: (UInt) -> Boolean): Int {
      var var4: Int = var0.length - 1;
      var var5: Int = -1;
      if (var4 >= 0) {
         var5 = var4;

         while (true) {
            var4 = var5 - 1;
            if (var1.invoke(UInt.box-impl(UInt.constructor-impl(var0[var5]))) as java.lang.Boolean) {
               break;
            }

            if (var4 < 0) {
               var5 = -1;
               break;
            }

            var5 = var4;
         }
      }

      return var5;
   }

   @JvmStatic
   public inline fun UShortArray.indexOfLast(predicate: (UShort) -> Boolean): Int {
      var var4: Int = var0.length - 1;
      var var5: Int = -1;
      if (var4 >= 0) {
         var5 = var4;

         while (true) {
            var4 = var5 - 1;
            if (var1.invoke(UShort.box-impl(UShort.constructor-impl(var0[var5]))) as java.lang.Boolean) {
               break;
            }

            if (var4 < 0) {
               var5 = -1;
               break;
            }

            var5 = var4;
         }
      }

      return var5;
   }

   @JvmStatic
   public inline fun UIntArray.last(): UInt {
      return UInt.constructor-impl(ArraysKt.last(var0));
   }

   @JvmStatic
   public inline fun UByteArray.last(): UByte {
      return UByte.constructor-impl(ArraysKt.last(var0));
   }

   @JvmStatic
   public inline fun UByteArray.last(predicate: (UByte) -> Boolean): UByte {
      var var3: Int = UByteArray.getSize-impl(var0) - 1;
      if (var3 >= 0) {
         while (true) {
            val var4: Int = var3 - 1;
            val var2: Byte = UByteArray.get-w2LRezQ(var0, var3);
            if (var1.invoke(UByte.box-impl(var2)) as java.lang.Boolean) {
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
   public inline fun ULongArray.last(predicate: (ULong) -> Boolean): ULong {
      var var2: Int = ULongArray.getSize-impl(var0) - 1;
      if (var2 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            val var4: Long = ULongArray.get-s-VKNKU(var0, var2);
            if (var1.invoke(ULong.box-impl(var4)) as java.lang.Boolean) {
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
   public inline fun ULongArray.last(): ULong {
      return ULong.constructor-impl(ArraysKt.last(var0));
   }

   @JvmStatic
   public inline fun UIntArray.last(predicate: (UInt) -> Boolean): UInt {
      var var2: Int = UIntArray.getSize-impl(var0) - 1;
      if (var2 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            var2 = UIntArray.get-pVg5ArA(var0, var2);
            if (var1.invoke(UInt.box-impl(var2)) as java.lang.Boolean) {
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
   public inline fun UShortArray.last(): UShort {
      return UShort.constructor-impl(ArraysKt.last(var0));
   }

   @JvmStatic
   public inline fun UShortArray.last(predicate: (UShort) -> Boolean): UShort {
      var var3: Int = UShortArray.getSize-impl(var0) - 1;
      if (var3 >= 0) {
         while (true) {
            val var4: Int = var3 - 1;
            val var2: Short = UShortArray.get-Mh2AYeg(var0, var3);
            if (var1.invoke(UShort.box-impl(var2)) as java.lang.Boolean) {
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
   public inline fun ULongArray.lastIndexOf(element: ULong): Int {
      return ArraysKt.lastIndexOf(var0, var1);
   }

   @JvmStatic
   public inline fun UShortArray.lastIndexOf(element: UShort): Int {
      return ArraysKt.lastIndexOf(var0, var1);
   }

   @JvmStatic
   public inline fun UByteArray.lastIndexOf(element: UByte): Int {
      return ArraysKt.lastIndexOf(var0, var1);
   }

   @JvmStatic
   public inline fun UIntArray.lastIndexOf(element: UInt): Int {
      return ArraysKt.lastIndexOf(var0, var1);
   }

   @JvmStatic
   public fun UIntArray.lastOrNull(): UInt? {
      val var1: UInt;
      if (UIntArray.isEmpty-impl(var0)) {
         var1 = null;
      } else {
         var1 = UInt.box-impl(UIntArray.get-pVg5ArA(var0, UIntArray.getSize-impl(var0) - 1));
      }

      return var1;
   }

   @JvmStatic
   public fun UByteArray.lastOrNull(): UByte? {
      val var1: UByte;
      if (UByteArray.isEmpty-impl(var0)) {
         var1 = null;
      } else {
         var1 = UByte.box-impl(UByteArray.get-w2LRezQ(var0, UByteArray.getSize-impl(var0) - 1));
      }

      return var1;
   }

   @JvmStatic
   public inline fun UByteArray.lastOrNull(predicate: (UByte) -> Boolean): UByte? {
      var var3: Int = UByteArray.getSize-impl(var0) - 1;
      if (var3 >= 0) {
         while (true) {
            val var4: Int = var3 - 1;
            val var2: Byte = UByteArray.get-w2LRezQ(var0, var3);
            if (var1.invoke(UByte.box-impl(var2)) as java.lang.Boolean) {
               return UByte.box-impl(var2);
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
   public inline fun ULongArray.lastOrNull(predicate: (ULong) -> Boolean): ULong? {
      var var2: Int = ULongArray.getSize-impl(var0) - 1;
      if (var2 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            val var4: Long = ULongArray.get-s-VKNKU(var0, var2);
            if (var1.invoke(ULong.box-impl(var4)) as java.lang.Boolean) {
               return ULong.box-impl(var4);
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
   public fun ULongArray.lastOrNull(): ULong? {
      val var1: ULong;
      if (ULongArray.isEmpty-impl(var0)) {
         var1 = null;
      } else {
         var1 = ULong.box-impl(ULongArray.get-s-VKNKU(var0, ULongArray.getSize-impl(var0) - 1));
      }

      return var1;
   }

   @JvmStatic
   public inline fun UIntArray.lastOrNull(predicate: (UInt) -> Boolean): UInt? {
      var var2: Int = UIntArray.getSize-impl(var0) - 1;
      if (var2 >= 0) {
         while (true) {
            val var3: Int = var2 - 1;
            var2 = UIntArray.get-pVg5ArA(var0, var2);
            if (var1.invoke(UInt.box-impl(var2)) as java.lang.Boolean) {
               return UInt.box-impl(var2);
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
   public fun UShortArray.lastOrNull(): UShort? {
      val var1: UShort;
      if (UShortArray.isEmpty-impl(var0)) {
         var1 = null;
      } else {
         var1 = UShort.box-impl(UShortArray.get-Mh2AYeg(var0, UShortArray.getSize-impl(var0) - 1));
      }

      return var1;
   }

   @JvmStatic
   public inline fun UShortArray.lastOrNull(predicate: (UShort) -> Boolean): UShort? {
      var var3: Int = UShortArray.getSize-impl(var0) - 1;
      if (var3 >= 0) {
         while (true) {
            val var4: Int = var3 - 1;
            val var2: Short = UShortArray.get-Mh2AYeg(var0, var3);
            if (var1.invoke(UShort.box-impl(var2)) as java.lang.Boolean) {
               return UShort.box-impl(var2);
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
   public inline fun <R> UByteArray.map(transform: (UByte) -> R): List<R> {
      val var4: java.util.Collection = new ArrayList(UByteArray.getSize-impl(var0));
      val var3: Int = UByteArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var2))));
      }

      return var4 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> ULongArray.map(transform: (ULong) -> R): List<R> {
      val var4: java.util.Collection = new ArrayList(ULongArray.getSize-impl(var0));
      val var3: Int = ULongArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2))));
      }

      return var4 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> UIntArray.map(transform: (UInt) -> R): List<R> {
      val var4: java.util.Collection = new ArrayList(UIntArray.getSize-impl(var0));
      val var3: Int = UIntArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2))));
      }

      return var4 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> UShortArray.map(transform: (UShort) -> R): List<R> {
      val var4: java.util.Collection = new ArrayList(UShortArray.getSize-impl(var0));
      val var3: Int = UShortArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         var4.add(var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var2))));
      }

      return var4 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> UByteArray.mapIndexed(transform: (Int, UByte) -> R): List<R> {
      val var5: java.util.Collection = new ArrayList(UByteArray.getSize-impl(var0));
      val var4: Int = UByteArray.getSize-impl(var0);
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var5.add(var1.invoke(var2, UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3))));
         var3++;
      }

      return var5 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> UIntArray.mapIndexed(transform: (Int, UInt) -> R): List<R> {
      val var5: java.util.Collection = new ArrayList(UIntArray.getSize-impl(var0));
      val var4: Int = UIntArray.getSize-impl(var0);
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var5.add(var1.invoke(var2, UInt.box-impl(UIntArray.get-pVg5ArA(var0, var3))));
         var3++;
      }

      return var5 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> ULongArray.mapIndexed(transform: (Int, ULong) -> R): List<R> {
      val var5: java.util.Collection = new ArrayList(ULongArray.getSize-impl(var0));
      val var4: Int = ULongArray.getSize-impl(var0);
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var5.add(var1.invoke(var2, ULong.box-impl(ULongArray.get-s-VKNKU(var0, var3))));
         var3++;
      }

      return var5 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R> UShortArray.mapIndexed(transform: (Int, UShort) -> R): List<R> {
      val var5: java.util.Collection = new ArrayList(UShortArray.getSize-impl(var0));
      val var4: Int = UShortArray.getSize-impl(var0);
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var5.add(var1.invoke(var2, UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var3))));
         var3++;
      }

      return var5 as MutableList<R>;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> UIntArray.mapIndexedTo(destination: C, transform: (Int, UInt) -> R): C {
      val var5: Int = UIntArray.getSize-impl(var0);
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         var1.add(var2.invoke(var3, UInt.box-impl(UIntArray.get-pVg5ArA(var0, var4))));
         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> UShortArray.mapIndexedTo(destination: C, transform: (Int, UShort) -> R): C {
      val var5: Int = UShortArray.getSize-impl(var0);
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         var1.add(var2.invoke(var3, UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var4))));
         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> UByteArray.mapIndexedTo(destination: C, transform: (Int, UByte) -> R): C {
      val var5: Int = UByteArray.getSize-impl(var0);
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         var1.add(var2.invoke(var3, UByte.box-impl(UByteArray.get-w2LRezQ(var0, var4))));
         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> ULongArray.mapIndexedTo(destination: C, transform: (Int, ULong) -> R): C {
      val var5: Int = ULongArray.getSize-impl(var0);
      var var4: Int = 0;

      for (int var3 = 0; var4 < var5; var3++) {
         var1.add(var2.invoke(var3, ULong.box-impl(ULongArray.get-s-VKNKU(var0, var4))));
         var4++;
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> ULongArray.mapTo(destination: C, transform: (ULong) -> R): C {
      val var4: Int = ULongArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         var1.add(var2.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var3))));
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> UShortArray.mapTo(destination: C, transform: (UShort) -> R): C {
      val var4: Int = UShortArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         var1.add(var2.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var3))));
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> UIntArray.mapTo(destination: C, transform: (UInt) -> R): C {
      val var4: Int = UIntArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         var1.add(var2.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var3))));
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R, C : MutableCollection<in R>> UByteArray.mapTo(destination: C, transform: (UByte) -> R): C {
      val var4: Int = UByteArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         var1.add(var2.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3))));
      }

      return (C)var1;
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> UByteArray.maxByOrNull(selector: (UByte) -> R): UByte? {
      if (UByteArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var2: Byte = UByteArray.get-w2LRezQ(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return UByte.box-impl(var2);
         } else {
            var var7: java.lang.Comparable = var1.invoke(UByte.box-impl(var2)) as java.lang.Comparable;
            var var4: Int = 1;
            var var3: Byte = var2;
            if (1 <= var5) {
               while (true) {
                  var3 = UByteArray.get-w2LRezQ(var0, var4);
                  val var8: java.lang.Comparable = var1.invoke(UByte.box-impl(var3)) as java.lang.Comparable;
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

            return UByte.box-impl(var3);
         }
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> ULongArray.maxByOrNull(selector: (ULong) -> R): ULong? {
      if (ULongArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var4: Long = ULongArray.get-s-VKNKU(var0, 0);
         val var3: Int = ArraysKt.getLastIndex(var0);
         if (var3 == 0) {
            return ULong.box-impl(var4);
         } else {
            var var8: java.lang.Comparable = var1.invoke(ULong.box-impl(var4)) as java.lang.Comparable;
            var var2: Int = 1;
            var var6: Long = var4;
            if (1 <= var3) {
               while (true) {
                  var6 = ULongArray.get-s-VKNKU(var0, var2);
                  val var10: java.lang.Comparable = var1.invoke(ULong.box-impl(var6)) as java.lang.Comparable;
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

            return ULong.box-impl(var6);
         }
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> UIntArray.maxByOrNull(selector: (UInt) -> R): UInt? {
      if (UIntArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var2: Int = UIntArray.get-pVg5ArA(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return UInt.box-impl(var2);
         } else {
            var var7: java.lang.Comparable = var1.invoke(UInt.box-impl(var2)) as java.lang.Comparable;
            var var3: Int = 1;
            var var4: Int = var2;
            if (1 <= var5) {
               while (true) {
                  var4 = UIntArray.get-pVg5ArA(var0, var3);
                  val var8: java.lang.Comparable = var1.invoke(UInt.box-impl(var4)) as java.lang.Comparable;
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

            return UInt.box-impl(var4);
         }
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> UShortArray.maxByOrNull(selector: (UShort) -> R): UShort? {
      if (UShortArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var2: Short = UShortArray.get-Mh2AYeg(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return UShort.box-impl(var2);
         } else {
            var var6: java.lang.Comparable = var1.invoke(UShort.box-impl(var2)) as java.lang.Comparable;
            var var4: Int = 1;
            var var3: Short = var2;
            if (1 <= var5) {
               while (true) {
                  var3 = UShortArray.get-Mh2AYeg(var0, var4);
                  val var8: java.lang.Comparable = var1.invoke(UShort.box-impl(var3)) as java.lang.Comparable;
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

            return UShort.box-impl(var3);
         }
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> UByteArray.maxBy(selector: (UByte) -> R): UByte {
      if (!UByteArray.isEmpty-impl(var0)) {
         var var2: Byte = UByteArray.get-w2LRezQ(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var6: java.lang.Comparable = var1.invoke(UByte.box-impl(var2)) as java.lang.Comparable;
            var var4: Int = 1;
            var var3: Byte = var2;
            if (1 <= var5) {
               while (true) {
                  var3 = UByteArray.get-w2LRezQ(var0, var4);
                  val var8: java.lang.Comparable = var1.invoke(UByte.box-impl(var3)) as java.lang.Comparable;
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
   public inline fun <R : Comparable<R>> UIntArray.maxBy(selector: (UInt) -> R): UInt {
      if (!UIntArray.isEmpty-impl(var0)) {
         var var2: Int = UIntArray.get-pVg5ArA(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var7: java.lang.Comparable = var1.invoke(UInt.box-impl(var2)) as java.lang.Comparable;
            var var3: Int = 1;
            var var4: Int = var2;
            if (1 <= var5) {
               while (true) {
                  var4 = UIntArray.get-pVg5ArA(var0, var3);
                  val var8: java.lang.Comparable = var1.invoke(UInt.box-impl(var4)) as java.lang.Comparable;
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
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> ULongArray.maxBy(selector: (ULong) -> R): ULong {
      if (!ULongArray.isEmpty-impl(var0)) {
         var var4: Long = ULongArray.get-s-VKNKU(var0, 0);
         val var3: Int = ArraysKt.getLastIndex(var0);
         if (var3 == 0) {
            return var4;
         } else {
            var var8: java.lang.Comparable = var1.invoke(ULong.box-impl(var4)) as java.lang.Comparable;
            var var2: Int = 1;
            var var6: Long = var4;
            if (1 <= var3) {
               while (true) {
                  var6 = ULongArray.get-s-VKNKU(var0, var2);
                  val var10: java.lang.Comparable = var1.invoke(ULong.box-impl(var6)) as java.lang.Comparable;
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
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> UShortArray.maxBy(selector: (UShort) -> R): UShort {
      if (!UShortArray.isEmpty-impl(var0)) {
         var var2: Short = UShortArray.get-Mh2AYeg(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var6: java.lang.Comparable = var1.invoke(UShort.box-impl(var2)) as java.lang.Comparable;
            var var4: Int = 1;
            var var3: Short = var2;
            if (1 <= var5) {
               while (true) {
                  var3 = UShortArray.get-Mh2AYeg(var0, var4);
                  val var8: java.lang.Comparable = var1.invoke(UShort.box-impl(var3)) as java.lang.Comparable;
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
   public inline fun UByteArray.maxOf(selector: (UByte) -> Double): Double {
      if (UByteArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var4: Double = (var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, 0))) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.max(var4, (var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var6))) as java.lang.Number).doubleValue());
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
   public inline fun UByteArray.maxOf(selector: (UByte) -> Float): Float {
      if (UByteArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var3: Float = (var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, 0))) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.max(var3, (var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var4))) as java.lang.Number).floatValue());
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
   public inline fun <R : Comparable<R>> UByteArray.maxOf(selector: (UByte) -> R): R {
      if (UByteArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var4: java.lang.Comparable = var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, 0))) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var2))) as java.lang.Comparable;
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
   public inline fun ULongArray.maxOf(selector: (ULong) -> Double): Double {
      if (ULongArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var4: Double = (var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, 0))) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.max(var4, (var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var6))) as java.lang.Number).doubleValue());
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
   public inline fun ULongArray.maxOf(selector: (ULong) -> Float): Float {
      if (ULongArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var3: Float = (var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, 0))) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.max(var3, (var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var4))) as java.lang.Number).floatValue());
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
   public inline fun <R : Comparable<R>> ULongArray.maxOf(selector: (ULong) -> R): R {
      if (ULongArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var4: java.lang.Comparable = var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, 0))) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2))) as java.lang.Comparable;
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
   public inline fun UIntArray.maxOf(selector: (UInt) -> Double): Double {
      if (UIntArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var4: Double = (var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, 0))) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.max(var4, (var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var6))) as java.lang.Number).doubleValue());
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
   public inline fun UIntArray.maxOf(selector: (UInt) -> Float): Float {
      if (UIntArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var3: Float = (var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, 0))) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.max(var3, (var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var4))) as java.lang.Number).floatValue());
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
   public inline fun <R : Comparable<R>> UIntArray.maxOf(selector: (UInt) -> R): R {
      if (UIntArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var4: java.lang.Comparable = var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, 0))) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2))) as java.lang.Comparable;
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
   public inline fun UShortArray.maxOf(selector: (UShort) -> Double): Double {
      if (UShortArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var4: Double = (var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, 0))) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.max(var4, (var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var6))) as java.lang.Number).doubleValue());
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
   public inline fun UShortArray.maxOf(selector: (UShort) -> Float): Float {
      if (UShortArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var3: Float = (var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, 0))) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.max(var3, (var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var4))) as java.lang.Number).floatValue());
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
   public inline fun <R : Comparable<R>> UShortArray.maxOf(selector: (UShort) -> R): R {
      if (UShortArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var4: java.lang.Comparable = var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, 0))) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var2))) as java.lang.Comparable;
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
   public inline fun <R : Comparable<R>> UByteArray.maxOfOrNull(selector: (UByte) -> R): R? {
      if (UByteArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, 0))) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var2))) as java.lang.Comparable;
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
   public inline fun UByteArray.maxOfOrNull(selector: (UByte) -> Double): Double? {
      if (UByteArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var4: Double = (var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, 0))) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.max(var4, (var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var6))) as java.lang.Number).doubleValue());
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
   public inline fun UByteArray.maxOfOrNull(selector: (UByte) -> Float): Float? {
      if (UByteArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var3: Float = (var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, 0))) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.max(var3, (var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var4))) as java.lang.Number).floatValue());
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
   public inline fun <R : Comparable<R>> ULongArray.maxOfOrNull(selector: (ULong) -> R): R? {
      if (ULongArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, 0))) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2))) as java.lang.Comparable;
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
   public inline fun ULongArray.maxOfOrNull(selector: (ULong) -> Double): Double? {
      if (ULongArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var4: Double = (var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, 0))) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.max(var4, (var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var6))) as java.lang.Number).doubleValue());
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
   public inline fun ULongArray.maxOfOrNull(selector: (ULong) -> Float): Float? {
      if (ULongArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var3: Float = (var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, 0))) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.max(var3, (var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var4))) as java.lang.Number).floatValue());
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
   public inline fun <R : Comparable<R>> UIntArray.maxOfOrNull(selector: (UInt) -> R): R? {
      if (UIntArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, 0))) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2))) as java.lang.Comparable;
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
   public inline fun UIntArray.maxOfOrNull(selector: (UInt) -> Double): Double? {
      if (UIntArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var4: Double = (var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, 0))) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.max(var4, (var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var6))) as java.lang.Number).doubleValue());
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
   public inline fun UIntArray.maxOfOrNull(selector: (UInt) -> Float): Float? {
      if (UIntArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var3: Float = (var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, 0))) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.max(var3, (var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var4))) as java.lang.Number).floatValue());
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
   public inline fun <R : Comparable<R>> UShortArray.maxOfOrNull(selector: (UShort) -> R): R? {
      if (UShortArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, 0))) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var2))) as java.lang.Comparable;
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
   public inline fun UShortArray.maxOfOrNull(selector: (UShort) -> Double): Double? {
      if (UShortArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var4: Double = (var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, 0))) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.max(var4, (var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var6))) as java.lang.Number).doubleValue());
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
   public inline fun UShortArray.maxOfOrNull(selector: (UShort) -> Float): Float? {
      if (UShortArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var3: Float = (var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, 0))) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.max(var3, (var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var4))) as java.lang.Number).floatValue());
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
   public inline fun <R> ULongArray.maxOfWith(comparator: Comparator<in R>, selector: (ULong) -> R): R {
      if (ULongArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var5: Any = var2.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, 0)));
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var3)));
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
   public inline fun <R> UByteArray.maxOfWith(comparator: Comparator<in R>, selector: (UByte) -> R): R {
      if (UByteArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var5: Any = var2.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, 0)));
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3)));
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
   public inline fun <R> UShortArray.maxOfWith(comparator: Comparator<in R>, selector: (UShort) -> R): R {
      if (UShortArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var5: Any = var2.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, 0)));
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var3)));
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
   public inline fun <R> UIntArray.maxOfWith(comparator: Comparator<in R>, selector: (UInt) -> R): R {
      if (UIntArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var5: Any = var2.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, 0)));
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var3)));
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
   public inline fun <R> ULongArray.maxOfWithOrNull(comparator: Comparator<in R>, selector: (ULong) -> R): R? {
      if (ULongArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var5: Any = var2.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, 0)));
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var3)));
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
   public inline fun <R> UByteArray.maxOfWithOrNull(comparator: Comparator<in R>, selector: (UByte) -> R): R? {
      if (UByteArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var5: Any = var2.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, 0)));
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3)));
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
   public inline fun <R> UShortArray.maxOfWithOrNull(comparator: Comparator<in R>, selector: (UShort) -> R): R? {
      if (UShortArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var5: Any = var2.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, 0)));
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var3)));
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
   public inline fun <R> UIntArray.maxOfWithOrNull(comparator: Comparator<in R>, selector: (UInt) -> R): R? {
      if (UIntArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var5: Any = var2.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, 0)));
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var3)));
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
   public fun UIntArray.maxOrNull(): UInt? {
      if (UIntArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var1: Int = UIntArray.get-pVg5ArA(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var3: Int = var1;
         if (1 <= var5) {
            var3 = var1;

            while (true) {
               val var4: Int = UIntArray.get-pVg5ArA(var0, var2);
               var1 = var3;
               if (UByte$$ExternalSyntheticBackport0.m$2(var3, var4) < 0) {
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

         return UInt.box-impl(var3);
      }
   }

   @JvmStatic
   public fun UByteArray.maxOrNull(): UByte? {
      if (UByteArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var1: Byte = UByteArray.get-w2LRezQ(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Byte = var1;
         if (1 <= var5) {
            var2 = var1;

            while (true) {
               val var3: Byte = UByteArray.get-w2LRezQ(var0, var4);
               var1 = var2;
               if (Intrinsics.compare(var2 and 255, var3 and 255) < 0) {
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

         return UByte.box-impl(var2);
      }
   }

   @JvmStatic
   public fun ULongArray.maxOrNull(): ULong? {
      if (ULongArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var3: Long = ULongArray.get-s-VKNKU(var0, 0);
         val var2: Int = ArraysKt.getLastIndex(var0);
         var var1: Int = 1;
         var var5: Long = var3;
         if (1 <= var2) {
            var5 = var3;

            while (true) {
               val var7: Long = ULongArray.get-s-VKNKU(var0, var1);
               var3 = var5;
               if (UByte$$ExternalSyntheticBackport0.m(var5, var7) < 0) {
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

         return ULong.box-impl(var5);
      }
   }

   @JvmStatic
   public fun UShortArray.maxOrNull(): UShort? {
      if (UShortArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var1: Short = UShortArray.get-Mh2AYeg(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Short = var1;
         if (1 <= var5) {
            var2 = var1;

            while (true) {
               val var3: Short = UShortArray.get-Mh2AYeg(var0, var4);
               var1 = var2;
               if (Intrinsics.compare(var2 and '\uffff', '\uffff' and var3) < 0) {
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

         return UShort.box-impl(var2);
      }
   }

   @JvmStatic
   public fun UByteArray.max(): UByte {
      if (UByteArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var1: Byte = UByteArray.get-w2LRezQ(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Byte = var1;
         if (1 <= var5) {
            var2 = var1;

            while (true) {
               val var3: Byte = UByteArray.get-w2LRezQ(var0, var4);
               var1 = var2;
               if (Intrinsics.compare(var2 and 255, var3 and 255) < 0) {
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
   public fun UIntArray.max(): UInt {
      if (UIntArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var1: Int = UIntArray.get-pVg5ArA(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var3: Int = var1;
         if (1 <= var5) {
            var3 = var1;

            while (true) {
               val var4: Int = UIntArray.get-pVg5ArA(var0, var2);
               var1 = var3;
               if (UByte$$ExternalSyntheticBackport0.m$2(var3, var4) < 0) {
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
   public fun ULongArray.max(): ULong {
      if (ULongArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var3: Long = ULongArray.get-s-VKNKU(var0, 0);
         val var2: Int = ArraysKt.getLastIndex(var0);
         var var1: Int = 1;
         var var5: Long = var3;
         if (1 <= var2) {
            var5 = var3;

            while (true) {
               val var7: Long = ULongArray.get-s-VKNKU(var0, var1);
               var3 = var5;
               if (UByte$$ExternalSyntheticBackport0.m(var5, var7) < 0) {
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
   public fun UShortArray.max(): UShort {
      if (UShortArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var1: Short = UShortArray.get-Mh2AYeg(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Short = var1;
         if (1 <= var5) {
            var2 = var1;

            while (true) {
               val var3: Short = UShortArray.get-Mh2AYeg(var0, var4);
               var1 = var2;
               if (Intrinsics.compare(var2 and '\uffff', '\uffff' and var3) < 0) {
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
   public fun UByteArray.maxWithOrNull(comparator: Comparator<in UByte>): UByte? {
      if (UByteArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var2: Byte = UByteArray.get-w2LRezQ(var0, 0);
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var3: Byte = var2;
         if (1 <= var6) {
            var3 = var2;

            while (true) {
               val var4: Byte = UByteArray.get-w2LRezQ(var0, var5);
               var2 = var3;
               if (var1.compare(UByte.box-impl(var3), UByte.box-impl(var4)) < 0) {
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

         return UByte.box-impl(var3);
      }
   }

   @JvmStatic
   public fun UIntArray.maxWithOrNull(comparator: Comparator<in UInt>): UInt? {
      if (UIntArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var2: Int = UIntArray.get-pVg5ArA(var0, 0);
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var4: Int = var2;
         if (1 <= var6) {
            var4 = var2;

            while (true) {
               val var5: Int = UIntArray.get-pVg5ArA(var0, var3);
               var2 = var4;
               if (var1.compare(UInt.box-impl(var4), UInt.box-impl(var5)) < 0) {
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

         return UInt.box-impl(var4);
      }
   }

   @JvmStatic
   public fun UShortArray.maxWithOrNull(comparator: Comparator<in UShort>): UShort? {
      if (UShortArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var2: Short = UShortArray.get-Mh2AYeg(var0, 0);
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var3: Short = var2;
         if (1 <= var6) {
            var3 = var2;

            while (true) {
               val var4: Short = UShortArray.get-Mh2AYeg(var0, var5);
               var2 = var3;
               if (var1.compare(UShort.box-impl(var3), UShort.box-impl(var4)) < 0) {
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

         return UShort.box-impl(var3);
      }
   }

   @JvmStatic
   public fun ULongArray.maxWithOrNull(comparator: Comparator<in ULong>): ULong? {
      if (ULongArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var4: Long = ULongArray.get-s-VKNKU(var0, 0);
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var6: Long = var4;
         if (1 <= var3) {
            var6 = var4;

            while (true) {
               val var8: Long = ULongArray.get-s-VKNKU(var0, var2);
               var4 = var6;
               if (var1.compare(ULong.box-impl(var6), ULong.box-impl(var8)) < 0) {
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

         return ULong.box-impl(var6);
      }
   }

   @JvmStatic
   public fun UByteArray.maxWith(comparator: Comparator<in UByte>): UByte {
      if (UByteArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var2: Byte = UByteArray.get-w2LRezQ(var0, 0);
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var3: Byte = var2;
         if (1 <= var6) {
            var3 = var2;

            while (true) {
               val var4: Byte = UByteArray.get-w2LRezQ(var0, var5);
               var2 = var3;
               if (var1.compare(UByte.box-impl(var3), UByte.box-impl(var4)) < 0) {
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
   public fun UIntArray.maxWith(comparator: Comparator<in UInt>): UInt {
      if (UIntArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var2: Int = UIntArray.get-pVg5ArA(var0, 0);
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var4: Int = var2;
         if (1 <= var6) {
            var4 = var2;

            while (true) {
               val var5: Int = UIntArray.get-pVg5ArA(var0, var3);
               var2 = var4;
               if (var1.compare(UInt.box-impl(var4), UInt.box-impl(var5)) < 0) {
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
   public fun ULongArray.maxWith(comparator: Comparator<in ULong>): ULong {
      if (ULongArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var4: Long = ULongArray.get-s-VKNKU(var0, 0);
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var6: Long = var4;
         if (1 <= var3) {
            var6 = var4;

            while (true) {
               val var8: Long = ULongArray.get-s-VKNKU(var0, var2);
               var4 = var6;
               if (var1.compare(ULong.box-impl(var6), ULong.box-impl(var8)) < 0) {
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
   public fun UShortArray.maxWith(comparator: Comparator<in UShort>): UShort {
      if (UShortArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var2: Short = UShortArray.get-Mh2AYeg(var0, 0);
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var3: Short = var2;
         if (1 <= var6) {
            var3 = var2;

            while (true) {
               val var4: Short = UShortArray.get-Mh2AYeg(var0, var5);
               var2 = var3;
               if (var1.compare(UShort.box-impl(var3), UShort.box-impl(var4)) < 0) {
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
   public inline fun <R : Comparable<R>> UByteArray.minByOrNull(selector: (UByte) -> R): UByte? {
      if (UByteArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var2: Byte = UByteArray.get-w2LRezQ(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return UByte.box-impl(var2);
         } else {
            var var7: java.lang.Comparable = var1.invoke(UByte.box-impl(var2)) as java.lang.Comparable;
            var var4: Int = 1;
            var var3: Byte = var2;
            if (1 <= var5) {
               while (true) {
                  var3 = UByteArray.get-w2LRezQ(var0, var4);
                  val var8: java.lang.Comparable = var1.invoke(UByte.box-impl(var3)) as java.lang.Comparable;
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

            return UByte.box-impl(var3);
         }
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> ULongArray.minByOrNull(selector: (ULong) -> R): ULong? {
      if (ULongArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var4: Long = ULongArray.get-s-VKNKU(var0, 0);
         val var3: Int = ArraysKt.getLastIndex(var0);
         if (var3 == 0) {
            return ULong.box-impl(var4);
         } else {
            var var9: java.lang.Comparable = var1.invoke(ULong.box-impl(var4)) as java.lang.Comparable;
            var var2: Int = 1;
            var var6: Long = var4;
            if (1 <= var3) {
               while (true) {
                  var6 = ULongArray.get-s-VKNKU(var0, var2);
                  val var10: java.lang.Comparable = var1.invoke(ULong.box-impl(var6)) as java.lang.Comparable;
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

            return ULong.box-impl(var6);
         }
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> UIntArray.minByOrNull(selector: (UInt) -> R): UInt? {
      if (UIntArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var2: Int = UIntArray.get-pVg5ArA(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return UInt.box-impl(var2);
         } else {
            var var7: java.lang.Comparable = var1.invoke(UInt.box-impl(var2)) as java.lang.Comparable;
            var var3: Int = 1;
            var var4: Int = var2;
            if (1 <= var5) {
               while (true) {
                  var4 = UIntArray.get-pVg5ArA(var0, var3);
                  val var8: java.lang.Comparable = var1.invoke(UInt.box-impl(var4)) as java.lang.Comparable;
                  var var6: java.lang.Comparable = var7;
                  if (var7.compareTo(var8) > 0) {
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

            return UInt.box-impl(var4);
         }
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> UShortArray.minByOrNull(selector: (UShort) -> R): UShort? {
      if (UShortArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var2: Short = UShortArray.get-Mh2AYeg(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return UShort.box-impl(var2);
         } else {
            var var6: java.lang.Comparable = var1.invoke(UShort.box-impl(var2)) as java.lang.Comparable;
            var var4: Int = 1;
            var var3: Short = var2;
            if (1 <= var5) {
               while (true) {
                  var3 = UShortArray.get-Mh2AYeg(var0, var4);
                  val var8: java.lang.Comparable = var1.invoke(UShort.box-impl(var3)) as java.lang.Comparable;
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

            return UShort.box-impl(var3);
         }
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> UByteArray.minBy(selector: (UByte) -> R): UByte {
      if (!UByteArray.isEmpty-impl(var0)) {
         var var2: Byte = UByteArray.get-w2LRezQ(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var6: java.lang.Comparable = var1.invoke(UByte.box-impl(var2)) as java.lang.Comparable;
            var var4: Int = 1;
            var var3: Byte = var2;
            if (1 <= var5) {
               while (true) {
                  var3 = UByteArray.get-w2LRezQ(var0, var4);
                  val var8: java.lang.Comparable = var1.invoke(UByte.box-impl(var3)) as java.lang.Comparable;
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
   public inline fun <R : Comparable<R>> UIntArray.minBy(selector: (UInt) -> R): UInt {
      if (!UIntArray.isEmpty-impl(var0)) {
         var var2: Int = UIntArray.get-pVg5ArA(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var7: java.lang.Comparable = var1.invoke(UInt.box-impl(var2)) as java.lang.Comparable;
            var var3: Int = 1;
            var var4: Int = var2;
            if (1 <= var5) {
               while (true) {
                  var4 = UIntArray.get-pVg5ArA(var0, var3);
                  val var8: java.lang.Comparable = var1.invoke(UInt.box-impl(var4)) as java.lang.Comparable;
                  var var6: java.lang.Comparable = var7;
                  if (var7.compareTo(var8) > 0) {
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
      } else {
         throw new NoSuchElementException();
      }
   }

   @JvmStatic
   public inline fun <R : Comparable<R>> ULongArray.minBy(selector: (ULong) -> R): ULong {
      if (!ULongArray.isEmpty-impl(var0)) {
         var var4: Long = ULongArray.get-s-VKNKU(var0, 0);
         val var3: Int = ArraysKt.getLastIndex(var0);
         if (var3 == 0) {
            return var4;
         } else {
            var var9: java.lang.Comparable = var1.invoke(ULong.box-impl(var4)) as java.lang.Comparable;
            var var2: Int = 1;
            var var6: Long = var4;
            if (1 <= var3) {
               while (true) {
                  var6 = ULongArray.get-s-VKNKU(var0, var2);
                  val var10: java.lang.Comparable = var1.invoke(ULong.box-impl(var6)) as java.lang.Comparable;
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
   public inline fun <R : Comparable<R>> UShortArray.minBy(selector: (UShort) -> R): UShort {
      if (!UShortArray.isEmpty-impl(var0)) {
         var var2: Short = UShortArray.get-Mh2AYeg(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         if (var5 == 0) {
            return var2;
         } else {
            var var7: java.lang.Comparable = var1.invoke(UShort.box-impl(var2)) as java.lang.Comparable;
            var var4: Int = 1;
            var var3: Short = var2;
            if (1 <= var5) {
               while (true) {
                  var3 = UShortArray.get-Mh2AYeg(var0, var4);
                  val var8: java.lang.Comparable = var1.invoke(UShort.box-impl(var3)) as java.lang.Comparable;
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
   public inline fun UByteArray.minOf(selector: (UByte) -> Double): Double {
      if (UByteArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var4: Double = (var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, 0))) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.min(var4, (var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var6))) as java.lang.Number).doubleValue());
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
   public inline fun UByteArray.minOf(selector: (UByte) -> Float): Float {
      if (UByteArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var3: Float = (var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, 0))) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.min(var3, (var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var4))) as java.lang.Number).floatValue());
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
   public inline fun <R : Comparable<R>> UByteArray.minOf(selector: (UByte) -> R): R {
      if (UByteArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var4: java.lang.Comparable = var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, 0))) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var2))) as java.lang.Comparable;
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
   public inline fun ULongArray.minOf(selector: (ULong) -> Double): Double {
      if (ULongArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var4: Double = (var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, 0))) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.min(var4, (var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var6))) as java.lang.Number).doubleValue());
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
   public inline fun ULongArray.minOf(selector: (ULong) -> Float): Float {
      if (ULongArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var3: Float = (var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, 0))) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.min(var3, (var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var4))) as java.lang.Number).floatValue());
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
   public inline fun <R : Comparable<R>> ULongArray.minOf(selector: (ULong) -> R): R {
      if (ULongArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var4: java.lang.Comparable = var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, 0))) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2))) as java.lang.Comparable;
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
   public inline fun UIntArray.minOf(selector: (UInt) -> Double): Double {
      if (UIntArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var4: Double = (var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, 0))) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.min(var4, (var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var6))) as java.lang.Number).doubleValue());
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
   public inline fun UIntArray.minOf(selector: (UInt) -> Float): Float {
      if (UIntArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var3: Float = (var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, 0))) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.min(var3, (var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var4))) as java.lang.Number).floatValue());
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
   public inline fun <R : Comparable<R>> UIntArray.minOf(selector: (UInt) -> R): R {
      if (UIntArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var4: java.lang.Comparable = var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, 0))) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2))) as java.lang.Comparable;
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
   public inline fun UShortArray.minOf(selector: (UShort) -> Double): Double {
      if (UShortArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var4: Double = (var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, 0))) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.min(var4, (var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var6))) as java.lang.Number).doubleValue());
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
   public inline fun UShortArray.minOf(selector: (UShort) -> Float): Float {
      if (UShortArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var3: Float = (var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, 0))) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.min(var3, (var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var4))) as java.lang.Number).floatValue());
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
   public inline fun <R : Comparable<R>> UShortArray.minOf(selector: (UShort) -> R): R {
      if (UShortArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var4: java.lang.Comparable = var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, 0))) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var2))) as java.lang.Comparable;
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
   public inline fun <R : Comparable<R>> UByteArray.minOfOrNull(selector: (UByte) -> R): R? {
      if (UByteArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, 0))) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var2))) as java.lang.Comparable;
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
   public inline fun UByteArray.minOfOrNull(selector: (UByte) -> Double): Double? {
      if (UByteArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var4: Double = (var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, 0))) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.min(var4, (var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var6))) as java.lang.Number).doubleValue());
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
   public inline fun UByteArray.minOfOrNull(selector: (UByte) -> Float): Float? {
      if (UByteArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var3: Float = (var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, 0))) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.min(var3, (var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var4))) as java.lang.Number).floatValue());
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
   public inline fun <R : Comparable<R>> ULongArray.minOfOrNull(selector: (ULong) -> R): R? {
      if (ULongArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, 0))) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2))) as java.lang.Comparable;
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
   public inline fun ULongArray.minOfOrNull(selector: (ULong) -> Double): Double? {
      if (ULongArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var4: Double = (var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, 0))) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.min(var4, (var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var6))) as java.lang.Number).doubleValue());
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
   public inline fun ULongArray.minOfOrNull(selector: (ULong) -> Float): Float? {
      if (ULongArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var3: Float = (var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, 0))) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.min(var3, (var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var4))) as java.lang.Number).floatValue());
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
   public inline fun <R : Comparable<R>> UIntArray.minOfOrNull(selector: (UInt) -> R): R? {
      if (UIntArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, 0))) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2))) as java.lang.Comparable;
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
   public inline fun UIntArray.minOfOrNull(selector: (UInt) -> Double): Double? {
      if (UIntArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var4: Double = (var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, 0))) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.min(var4, (var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var6))) as java.lang.Number).doubleValue());
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
   public inline fun UIntArray.minOfOrNull(selector: (UInt) -> Float): Float? {
      if (UIntArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var3: Float = (var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, 0))) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.min(var3, (var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var4))) as java.lang.Number).floatValue());
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
   public inline fun <R : Comparable<R>> UShortArray.minOfOrNull(selector: (UShort) -> R): R? {
      if (UShortArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var4: java.lang.Comparable = var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, 0))) as java.lang.Comparable;
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var5: java.lang.Comparable = var4;
         if (1 <= var3) {
            var5 = var4;

            while (true) {
               val var6: java.lang.Comparable = var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var2))) as java.lang.Comparable;
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
   public inline fun UShortArray.minOfOrNull(selector: (UShort) -> Double): Double? {
      if (UShortArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var4: Double = (var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, 0))) as java.lang.Number).doubleValue();
         val var7: Int = ArraysKt.getLastIndex(var0);
         var var6: Int = 1;
         var var2: Double = var4;
         if (1 <= var7) {
            while (true) {
               var4 = Math.min(var4, (var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var6))) as java.lang.Number).doubleValue());
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
   public inline fun UShortArray.minOfOrNull(selector: (UShort) -> Float): Float? {
      if (UShortArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var3: Float = (var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, 0))) as java.lang.Number).floatValue();
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Float = var3;
         if (1 <= var5) {
            while (true) {
               var3 = Math.min(var3, (var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var4))) as java.lang.Number).floatValue());
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
   public inline fun <R> ULongArray.minOfWith(comparator: Comparator<in R>, selector: (ULong) -> R): R {
      if (ULongArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var5: Any = var2.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, 0)));
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var3)));
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
   public inline fun <R> UByteArray.minOfWith(comparator: Comparator<in R>, selector: (UByte) -> R): R {
      if (UByteArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var5: Any = var2.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, 0)));
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3)));
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
   public inline fun <R> UShortArray.minOfWith(comparator: Comparator<in R>, selector: (UShort) -> R): R {
      if (UShortArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var5: Any = var2.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, 0)));
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var3)));
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
   public inline fun <R> UIntArray.minOfWith(comparator: Comparator<in R>, selector: (UInt) -> R): R {
      if (UIntArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var5: Any = var2.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, 0)));
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var3)));
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
   public inline fun <R> ULongArray.minOfWithOrNull(comparator: Comparator<in R>, selector: (ULong) -> R): R? {
      if (ULongArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var5: Any = var2.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, 0)));
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var3)));
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
   public inline fun <R> UByteArray.minOfWithOrNull(comparator: Comparator<in R>, selector: (UByte) -> R): R? {
      if (UByteArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var5: Any = var2.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, 0)));
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3)));
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
   public inline fun <R> UShortArray.minOfWithOrNull(comparator: Comparator<in R>, selector: (UShort) -> R): R? {
      if (UShortArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var5: Any = var2.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, 0)));
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var3)));
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
   public inline fun <R> UIntArray.minOfWithOrNull(comparator: Comparator<in R>, selector: (UInt) -> R): R? {
      if (UIntArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var5: Any = var2.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, 0)));
         val var4: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var6: Any = var5;
         if (1 <= var4) {
            var6 = var5;

            while (true) {
               val var7: Any = var2.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var3)));
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
   public fun UIntArray.minOrNull(): UInt? {
      if (UIntArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var1: Int = UIntArray.get-pVg5ArA(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var3: Int = var1;
         if (1 <= var5) {
            var3 = var1;

            while (true) {
               val var4: Int = UIntArray.get-pVg5ArA(var0, var2);
               var1 = var3;
               if (UByte$$ExternalSyntheticBackport0.m$2(var3, var4) > 0) {
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

         return UInt.box-impl(var3);
      }
   }

   @JvmStatic
   public fun UByteArray.minOrNull(): UByte? {
      if (UByteArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var1: Byte = UByteArray.get-w2LRezQ(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Byte = var1;
         if (1 <= var5) {
            var2 = var1;

            while (true) {
               val var3: Byte = UByteArray.get-w2LRezQ(var0, var4);
               var1 = var2;
               if (Intrinsics.compare(var2 and 255, var3 and 255) > 0) {
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

         return UByte.box-impl(var2);
      }
   }

   @JvmStatic
   public fun ULongArray.minOrNull(): ULong? {
      if (ULongArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var3: Long = ULongArray.get-s-VKNKU(var0, 0);
         val var2: Int = ArraysKt.getLastIndex(var0);
         var var1: Int = 1;
         var var5: Long = var3;
         if (1 <= var2) {
            var5 = var3;

            while (true) {
               val var7: Long = ULongArray.get-s-VKNKU(var0, var1);
               var3 = var5;
               if (UByte$$ExternalSyntheticBackport0.m(var5, var7) > 0) {
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

         return ULong.box-impl(var5);
      }
   }

   @JvmStatic
   public fun UShortArray.minOrNull(): UShort? {
      if (UShortArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var1: Short = UShortArray.get-Mh2AYeg(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Short = var1;
         if (1 <= var5) {
            var2 = var1;

            while (true) {
               val var3: Short = UShortArray.get-Mh2AYeg(var0, var4);
               var1 = var2;
               if (Intrinsics.compare(var2 and '\uffff', '\uffff' and var3) > 0) {
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

         return UShort.box-impl(var2);
      }
   }

   @JvmStatic
   public fun UByteArray.min(): UByte {
      if (UByteArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var1: Byte = UByteArray.get-w2LRezQ(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Byte = var1;
         if (1 <= var5) {
            var2 = var1;

            while (true) {
               val var3: Byte = UByteArray.get-w2LRezQ(var0, var4);
               var1 = var2;
               if (Intrinsics.compare(var2 and 255, var3 and 255) > 0) {
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
   public fun UIntArray.min(): UInt {
      if (UIntArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var1: Int = UIntArray.get-pVg5ArA(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var3: Int = var1;
         if (1 <= var5) {
            var3 = var1;

            while (true) {
               val var4: Int = UIntArray.get-pVg5ArA(var0, var2);
               var1 = var3;
               if (UByte$$ExternalSyntheticBackport0.m$2(var3, var4) > 0) {
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
   public fun ULongArray.min(): ULong {
      if (ULongArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var3: Long = ULongArray.get-s-VKNKU(var0, 0);
         val var2: Int = ArraysKt.getLastIndex(var0);
         var var1: Int = 1;
         var var5: Long = var3;
         if (1 <= var2) {
            var5 = var3;

            while (true) {
               val var7: Long = ULongArray.get-s-VKNKU(var0, var1);
               var3 = var5;
               if (UByte$$ExternalSyntheticBackport0.m(var5, var7) > 0) {
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
   public fun UShortArray.min(): UShort {
      if (UShortArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var1: Short = UShortArray.get-Mh2AYeg(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Short = var1;
         if (1 <= var5) {
            var2 = var1;

            while (true) {
               val var3: Short = UShortArray.get-Mh2AYeg(var0, var4);
               var1 = var2;
               if (Intrinsics.compare(var2 and '\uffff', '\uffff' and var3) > 0) {
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
   public fun UByteArray.minWithOrNull(comparator: Comparator<in UByte>): UByte? {
      if (UByteArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var2: Byte = UByteArray.get-w2LRezQ(var0, 0);
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var3: Byte = var2;
         if (1 <= var6) {
            var3 = var2;

            while (true) {
               val var4: Byte = UByteArray.get-w2LRezQ(var0, var5);
               var2 = var3;
               if (var1.compare(UByte.box-impl(var3), UByte.box-impl(var4)) > 0) {
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

         return UByte.box-impl(var3);
      }
   }

   @JvmStatic
   public fun UIntArray.minWithOrNull(comparator: Comparator<in UInt>): UInt? {
      if (UIntArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var2: Int = UIntArray.get-pVg5ArA(var0, 0);
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var4: Int = var2;
         if (1 <= var6) {
            var4 = var2;

            while (true) {
               val var5: Int = UIntArray.get-pVg5ArA(var0, var3);
               var2 = var4;
               if (var1.compare(UInt.box-impl(var4), UInt.box-impl(var5)) > 0) {
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

         return UInt.box-impl(var4);
      }
   }

   @JvmStatic
   public fun UShortArray.minWithOrNull(comparator: Comparator<in UShort>): UShort? {
      if (UShortArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var2: Short = UShortArray.get-Mh2AYeg(var0, 0);
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var3: Short = var2;
         if (1 <= var6) {
            var3 = var2;

            while (true) {
               val var4: Short = UShortArray.get-Mh2AYeg(var0, var5);
               var2 = var3;
               if (var1.compare(UShort.box-impl(var3), UShort.box-impl(var4)) > 0) {
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

         return UShort.box-impl(var3);
      }
   }

   @JvmStatic
   public fun ULongArray.minWithOrNull(comparator: Comparator<in ULong>): ULong? {
      if (ULongArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var4: Long = ULongArray.get-s-VKNKU(var0, 0);
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var6: Long = var4;
         if (1 <= var3) {
            var6 = var4;

            while (true) {
               val var8: Long = ULongArray.get-s-VKNKU(var0, var2);
               var4 = var6;
               if (var1.compare(ULong.box-impl(var6), ULong.box-impl(var8)) > 0) {
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

         return ULong.box-impl(var6);
      }
   }

   @JvmStatic
   public fun UByteArray.minWith(comparator: Comparator<in UByte>): UByte {
      if (UByteArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var2: Byte = UByteArray.get-w2LRezQ(var0, 0);
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var3: Byte = var2;
         if (1 <= var6) {
            var3 = var2;

            while (true) {
               val var4: Byte = UByteArray.get-w2LRezQ(var0, var5);
               var2 = var3;
               if (var1.compare(UByte.box-impl(var3), UByte.box-impl(var4)) > 0) {
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
   public fun UIntArray.minWith(comparator: Comparator<in UInt>): UInt {
      if (UIntArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var2: Int = UIntArray.get-pVg5ArA(var0, 0);
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var4: Int = var2;
         if (1 <= var6) {
            var4 = var2;

            while (true) {
               val var5: Int = UIntArray.get-pVg5ArA(var0, var3);
               var2 = var4;
               if (var1.compare(UInt.box-impl(var4), UInt.box-impl(var5)) > 0) {
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
   public fun ULongArray.minWith(comparator: Comparator<in ULong>): ULong {
      if (ULongArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var4: Long = ULongArray.get-s-VKNKU(var0, 0);
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var6: Long = var4;
         if (1 <= var3) {
            var6 = var4;

            while (true) {
               val var8: Long = ULongArray.get-s-VKNKU(var0, var2);
               var4 = var6;
               if (var1.compare(ULong.box-impl(var6), ULong.box-impl(var8)) > 0) {
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
   public fun UShortArray.minWith(comparator: Comparator<in UShort>): UShort {
      if (UShortArray.isEmpty-impl(var0)) {
         throw new NoSuchElementException();
      } else {
         var var2: Short = UShortArray.get-Mh2AYeg(var0, 0);
         val var6: Int = ArraysKt.getLastIndex(var0);
         var var5: Int = 1;
         var var3: Short = var2;
         if (1 <= var6) {
            var3 = var2;

            while (true) {
               val var4: Short = UShortArray.get-Mh2AYeg(var0, var5);
               var2 = var3;
               if (var1.compare(UShort.box-impl(var3), UShort.box-impl(var4)) > 0) {
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
   public inline fun UIntArray.none(): Boolean {
      return UIntArray.isEmpty-impl(var0);
   }

   @JvmStatic
   public inline fun UByteArray.none(): Boolean {
      return UByteArray.isEmpty-impl(var0);
   }

   @JvmStatic
   public inline fun UByteArray.none(predicate: (UByte) -> Boolean): Boolean {
      val var3: Int = UByteArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var2))) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public inline fun ULongArray.none(predicate: (ULong) -> Boolean): Boolean {
      val var3: Int = ULongArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2))) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public inline fun ULongArray.none(): Boolean {
      return ULongArray.isEmpty-impl(var0);
   }

   @JvmStatic
   public inline fun UIntArray.none(predicate: (UInt) -> Boolean): Boolean {
      val var3: Int = UIntArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2))) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public inline fun UShortArray.none(): Boolean {
      return UShortArray.isEmpty-impl(var0);
   }

   @JvmStatic
   public inline fun UShortArray.none(predicate: (UShort) -> Boolean): Boolean {
      val var3: Int = UShortArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         if (var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var2))) as java.lang.Boolean) {
            return false;
         }
      }

      return true;
   }

   @JvmStatic
   public inline fun UByteArray.onEach(action: (UByte) -> Unit): UByteArray {
      val var3: Int = UByteArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var2)));
      }

      return var0;
   }

   @JvmStatic
   public inline fun ULongArray.onEach(action: (ULong) -> Unit): ULongArray {
      val var3: Int = ULongArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2)));
      }

      return var0;
   }

   @JvmStatic
   public inline fun UIntArray.onEach(action: (UInt) -> Unit): UIntArray {
      val var3: Int = UIntArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2)));
      }

      return var0;
   }

   @JvmStatic
   public inline fun UShortArray.onEach(action: (UShort) -> Unit): UShortArray {
      val var3: Int = UShortArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var2)));
      }

      return var0;
   }

   @JvmStatic
   public inline fun UByteArray.onEachIndexed(action: (Int, UByte) -> Unit): UByteArray {
      val var4: Int = UByteArray.getSize-impl(var0);
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var1.invoke(var2, UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3)));
         var3++;
      }

      return var0;
   }

   @JvmStatic
   public inline fun UIntArray.onEachIndexed(action: (Int, UInt) -> Unit): UIntArray {
      val var4: Int = UIntArray.getSize-impl(var0);
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var1.invoke(var2, UInt.box-impl(UIntArray.get-pVg5ArA(var0, var3)));
         var3++;
      }

      return var0;
   }

   @JvmStatic
   public inline fun ULongArray.onEachIndexed(action: (Int, ULong) -> Unit): ULongArray {
      val var4: Int = ULongArray.getSize-impl(var0);
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var1.invoke(var2, ULong.box-impl(ULongArray.get-s-VKNKU(var0, var3)));
         var3++;
      }

      return var0;
   }

   @JvmStatic
   public inline fun UShortArray.onEachIndexed(action: (Int, UShort) -> Unit): UShortArray {
      val var4: Int = UShortArray.getSize-impl(var0);
      var var3: Int = 0;

      for (int var2 = 0; var3 < var4; var2++) {
         var1.invoke(var2, UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var3)));
         var3++;
      }

      return var0;
   }

   @JvmStatic
   public inline operator fun ULongArray.plus(element: ULong): ULongArray {
      return ULongArray.constructor-impl(ArraysKt.plus(var0, var1));
   }

   @JvmStatic
   public operator fun UIntArray.plus(elements: Collection<UInt>): UIntArray {
      var var2: Int = UIntArray.getSize-impl(var0);
      var0 = Arrays.copyOf(var0, UIntArray.getSize-impl(var0) + var1.size());

      for (java.util.Iterator var4 = var1.iterator(); var4.hasNext(); var2++) {
         var0[var2] = (var4.next() as UInt).unbox-impl();
      }

      return UIntArray.constructor-impl(var0);
   }

   @JvmStatic
   public inline operator fun UShortArray.plus(element: UShort): UShortArray {
      return UShortArray.constructor-impl(ArraysKt.plus(var0, var1));
   }

   @JvmStatic
   public inline operator fun UIntArray.plus(elements: UIntArray): UIntArray {
      return UIntArray.constructor-impl(ArraysKt.plus(var0, var1));
   }

   @JvmStatic
   public inline operator fun UByteArray.plus(element: UByte): UByteArray {
      return UByteArray.constructor-impl(ArraysKt.plus(var0, var1));
   }

   @JvmStatic
   public inline operator fun UByteArray.plus(elements: UByteArray): UByteArray {
      return UByteArray.constructor-impl(ArraysKt.plus(var0, var1));
   }

   @JvmStatic
   public operator fun ULongArray.plus(elements: Collection<ULong>): ULongArray {
      var var2: Int = ULongArray.getSize-impl(var0);
      var0 = Arrays.copyOf(var0, ULongArray.getSize-impl(var0) + var1.size());

      for (java.util.Iterator var4 = var1.iterator(); var4.hasNext(); var2++) {
         var0[var2] = (var4.next() as ULong).unbox-impl();
      }

      return ULongArray.constructor-impl(var0);
   }

   @JvmStatic
   public inline operator fun UShortArray.plus(elements: UShortArray): UShortArray {
      return UShortArray.constructor-impl(ArraysKt.plus(var0, var1));
   }

   @JvmStatic
   public operator fun UShortArray.plus(elements: Collection<UShort>): UShortArray {
      var var2: Int = UShortArray.getSize-impl(var0);
      var0 = Arrays.copyOf(var0, UShortArray.getSize-impl(var0) + var1.size());

      for (java.util.Iterator var4 = var1.iterator(); var4.hasNext(); var2++) {
         var0[var2] = (var4.next() as UShort).unbox-impl();
      }

      return UShortArray.constructor-impl(var0);
   }

   @JvmStatic
   public inline operator fun UIntArray.plus(element: UInt): UIntArray {
      return UIntArray.constructor-impl(ArraysKt.plus(var0, var1));
   }

   @JvmStatic
   public inline operator fun ULongArray.plus(elements: ULongArray): ULongArray {
      return ULongArray.constructor-impl(ArraysKt.plus(var0, var1));
   }

   @JvmStatic
   public operator fun UByteArray.plus(elements: Collection<UByte>): UByteArray {
      var var2: Int = UByteArray.getSize-impl(var0);
      var0 = Arrays.copyOf(var0, UByteArray.getSize-impl(var0) + var1.size());

      for (java.util.Iterator var4 = var1.iterator(); var4.hasNext(); var2++) {
         var0[var2] = (var4.next() as UByte).unbox-impl();
      }

      return UByteArray.constructor-impl(var0);
   }

   @JvmStatic
   public inline fun UIntArray.random(): UInt {
      return UArraysKt.random-2D5oskM(var0, Random.Default);
   }

   @JvmStatic
   public fun UIntArray.random(random: Random): UInt {
      if (!UIntArray.isEmpty-impl(var0)) {
         return UIntArray.get-pVg5ArA(var0, var1.nextInt(UIntArray.getSize-impl(var0)));
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun UByteArray.random(): UByte {
      return UArraysKt.random-oSF2wD8(var0, Random.Default);
   }

   @JvmStatic
   public fun ULongArray.random(random: Random): ULong {
      if (!ULongArray.isEmpty-impl(var0)) {
         return ULongArray.get-s-VKNKU(var0, var1.nextInt(ULongArray.getSize-impl(var0)));
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun ULongArray.random(): ULong {
      return UArraysKt.random-JzugnMA(var0, Random.Default);
   }

   @JvmStatic
   public fun UByteArray.random(random: Random): UByte {
      if (!UByteArray.isEmpty-impl(var0)) {
         return UByteArray.get-w2LRezQ(var0, var1.nextInt(UByteArray.getSize-impl(var0)));
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun UShortArray.random(): UShort {
      return UArraysKt.random-s5X_as8(var0, Random.Default);
   }

   @JvmStatic
   public fun UShortArray.random(random: Random): UShort {
      if (!UShortArray.isEmpty-impl(var0)) {
         return UShortArray.get-Mh2AYeg(var0, var1.nextInt(UShortArray.getSize-impl(var0)));
      } else {
         throw new NoSuchElementException("Array is empty.");
      }
   }

   @JvmStatic
   public inline fun UIntArray.randomOrNull(): UInt? {
      return UArraysKt.randomOrNull-2D5oskM(var0, Random.Default);
   }

   @JvmStatic
   public fun UIntArray.randomOrNull(random: Random): UInt? {
      return if (UIntArray.isEmpty-impl(var0)) null else UInt.box-impl(UIntArray.get-pVg5ArA(var0, var1.nextInt(UIntArray.getSize-impl(var0))));
   }

   @JvmStatic
   public inline fun UByteArray.randomOrNull(): UByte? {
      return UArraysKt.randomOrNull-oSF2wD8(var0, Random.Default);
   }

   @JvmStatic
   public fun ULongArray.randomOrNull(random: Random): ULong? {
      return if (ULongArray.isEmpty-impl(var0)) null else ULong.box-impl(ULongArray.get-s-VKNKU(var0, var1.nextInt(ULongArray.getSize-impl(var0))));
   }

   @JvmStatic
   public inline fun ULongArray.randomOrNull(): ULong? {
      return UArraysKt.randomOrNull-JzugnMA(var0, Random.Default);
   }

   @JvmStatic
   public fun UByteArray.randomOrNull(random: Random): UByte? {
      return if (UByteArray.isEmpty-impl(var0)) null else UByte.box-impl(UByteArray.get-w2LRezQ(var0, var1.nextInt(UByteArray.getSize-impl(var0))));
   }

   @JvmStatic
   public inline fun UShortArray.randomOrNull(): UShort? {
      return UArraysKt.randomOrNull-s5X_as8(var0, Random.Default);
   }

   @JvmStatic
   public fun UShortArray.randomOrNull(random: Random): UShort? {
      return if (UShortArray.isEmpty-impl(var0)) null else UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var1.nextInt(UShortArray.getSize-impl(var0))));
   }

   @JvmStatic
   public inline fun UByteArray.reduce(operation: (UByte, UByte) -> UByte): UByte {
      if (UByteArray.isEmpty-impl(var0)) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var3: Byte = UByteArray.get-w2LRezQ(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Byte = var3;
         if (1 <= var5) {
            while (true) {
               var3 = (var1.invoke(UByte.box-impl(var3), UByte.box-impl(UByteArray.get-w2LRezQ(var0, var4))) as UByte).unbox-impl();
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
   public inline fun UIntArray.reduce(operation: (UInt, UInt) -> UInt): UInt {
      if (UIntArray.isEmpty-impl(var0)) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var4: Int = UIntArray.get-pVg5ArA(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var3: Int = var4;
         if (1 <= var5) {
            while (true) {
               var4 = (var1.invoke(UInt.box-impl(var4), UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2))) as UInt).unbox-impl();
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
   public inline fun ULongArray.reduce(operation: (ULong, ULong) -> ULong): ULong {
      if (ULongArray.isEmpty-impl(var0)) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var6: Long = ULongArray.get-s-VKNKU(var0, 0);
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var4: Long = var6;
         if (1 <= var3) {
            while (true) {
               var6 = (var1.invoke(ULong.box-impl(var6), ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2))) as ULong).unbox-impl();
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
   public inline fun UShortArray.reduce(operation: (UShort, UShort) -> UShort): UShort {
      if (UShortArray.isEmpty-impl(var0)) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var3: Short = UShortArray.get-Mh2AYeg(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Short = var3;
         if (1 <= var5) {
            while (true) {
               var3 = (var1.invoke(UShort.box-impl(var3), UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var4))) as UShort).unbox-impl();
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
   public inline fun UIntArray.reduceIndexed(operation: (Int, UInt, UInt) -> UInt): UInt {
      if (UIntArray.isEmpty-impl(var0)) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var4: Int = UIntArray.get-pVg5ArA(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var3: Int = 1;
         var var2: Int = var4;
         if (1 <= var5) {
            while (true) {
               var4 = (var1.invoke(var3, UInt.box-impl(var4), UInt.box-impl(UIntArray.get-pVg5ArA(var0, var3))) as UInt).unbox-impl();
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
   public inline fun UByteArray.reduceIndexed(operation: (Int, UByte, UByte) -> UByte): UByte {
      if (UByteArray.isEmpty-impl(var0)) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var3: Byte = UByteArray.get-w2LRezQ(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Byte = var3;
         if (1 <= var5) {
            while (true) {
               var3 = (var1.invoke(var4, UByte.box-impl(var3), UByte.box-impl(UByteArray.get-w2LRezQ(var0, var4))) as UByte).unbox-impl();
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
   public inline fun UShortArray.reduceIndexed(operation: (Int, UShort, UShort) -> UShort): UShort {
      if (UShortArray.isEmpty-impl(var0)) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var3: Short = UShortArray.get-Mh2AYeg(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Short = var3;
         if (1 <= var5) {
            while (true) {
               var3 = (var1.invoke(var4, UShort.box-impl(var3), UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var4))) as UShort).unbox-impl();
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
   public inline fun ULongArray.reduceIndexed(operation: (Int, ULong, ULong) -> ULong): ULong {
      if (ULongArray.isEmpty-impl(var0)) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var6: Long = ULongArray.get-s-VKNKU(var0, 0);
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var4: Long = var6;
         if (1 <= var3) {
            while (true) {
               var6 = (var1.invoke(var2, ULong.box-impl(var6), ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2))) as ULong).unbox-impl();
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
   public inline fun UIntArray.reduceIndexedOrNull(operation: (Int, UInt, UInt) -> UInt): UInt? {
      if (UIntArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var4: Int = UIntArray.get-pVg5ArA(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var3: Int = var4;
         if (1 <= var5) {
            while (true) {
               var4 = (var1.invoke(var2, UInt.box-impl(var4), UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2))) as UInt).unbox-impl();
               var3 = var4;
               if (var2 == var5) {
                  break;
               }

               var2++;
            }
         }

         return UInt.box-impl(var3);
      }
   }

   @JvmStatic
   public inline fun UByteArray.reduceIndexedOrNull(operation: (Int, UByte, UByte) -> UByte): UByte? {
      if (UByteArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var3: Byte = UByteArray.get-w2LRezQ(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Byte = var3;
         if (1 <= var5) {
            while (true) {
               var3 = (var1.invoke(var4, UByte.box-impl(var3), UByte.box-impl(UByteArray.get-w2LRezQ(var0, var4))) as UByte).unbox-impl();
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return UByte.box-impl(var2);
      }
   }

   @JvmStatic
   public inline fun UShortArray.reduceIndexedOrNull(operation: (Int, UShort, UShort) -> UShort): UShort? {
      if (UShortArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var3: Short = UShortArray.get-Mh2AYeg(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Short = var3;
         if (1 <= var5) {
            while (true) {
               var3 = (var1.invoke(var4, UShort.box-impl(var3), UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var4))) as UShort).unbox-impl();
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return UShort.box-impl(var2);
      }
   }

   @JvmStatic
   public inline fun ULongArray.reduceIndexedOrNull(operation: (Int, ULong, ULong) -> ULong): ULong? {
      if (ULongArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var6: Long = ULongArray.get-s-VKNKU(var0, 0);
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var4: Long = var6;
         if (1 <= var3) {
            while (true) {
               var6 = (var1.invoke(var2, ULong.box-impl(var6), ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2))) as ULong).unbox-impl();
               var4 = var6;
               if (var2 == var3) {
                  break;
               }

               var2++;
            }
         }

         return ULong.box-impl(var4);
      }
   }

   @JvmStatic
   public inline fun UByteArray.reduceOrNull(operation: (UByte, UByte) -> UByte): UByte? {
      if (UByteArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var3: Byte = UByteArray.get-w2LRezQ(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Byte = var3;
         if (1 <= var5) {
            while (true) {
               var3 = (var1.invoke(UByte.box-impl(var3), UByte.box-impl(UByteArray.get-w2LRezQ(var0, var4))) as UByte).unbox-impl();
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return UByte.box-impl(var2);
      }
   }

   @JvmStatic
   public inline fun UIntArray.reduceOrNull(operation: (UInt, UInt) -> UInt): UInt? {
      if (UIntArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var4: Int = UIntArray.get-pVg5ArA(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var3: Int = var4;
         if (1 <= var5) {
            while (true) {
               var4 = (var1.invoke(UInt.box-impl(var4), UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2))) as UInt).unbox-impl();
               var3 = var4;
               if (var2 == var5) {
                  break;
               }

               var2++;
            }
         }

         return UInt.box-impl(var3);
      }
   }

   @JvmStatic
   public inline fun ULongArray.reduceOrNull(operation: (ULong, ULong) -> ULong): ULong? {
      if (ULongArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var6: Long = ULongArray.get-s-VKNKU(var0, 0);
         val var3: Int = ArraysKt.getLastIndex(var0);
         var var2: Int = 1;
         var var4: Long = var6;
         if (1 <= var3) {
            while (true) {
               var6 = (var1.invoke(ULong.box-impl(var6), ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2))) as ULong).unbox-impl();
               var4 = var6;
               if (var2 == var3) {
                  break;
               }

               var2++;
            }
         }

         return ULong.box-impl(var4);
      }
   }

   @JvmStatic
   public inline fun UShortArray.reduceOrNull(operation: (UShort, UShort) -> UShort): UShort? {
      if (UShortArray.isEmpty-impl(var0)) {
         return null;
      } else {
         var var3: Short = UShortArray.get-Mh2AYeg(var0, 0);
         val var5: Int = ArraysKt.getLastIndex(var0);
         var var4: Int = 1;
         var var2: Short = var3;
         if (1 <= var5) {
            while (true) {
               var3 = (var1.invoke(UShort.box-impl(var3), UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var4))) as UShort).unbox-impl();
               var2 = var3;
               if (var4 == var5) {
                  break;
               }

               var4++;
            }
         }

         return UShort.box-impl(var2);
      }
   }

   @JvmStatic
   public inline fun UByteArray.reduceRight(operation: (UByte, UByte) -> UByte): UByte {
      val var4: Int = ArraysKt.getLastIndex(var0);
      if (var4 < 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var3: Int = var4 - 1;

         var var2: Byte;
         for (var2 = UByteArray.get-w2LRezQ(var0, var4); var3 >= 0; var3--) {
            var2 = (var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3)), UByte.box-impl(var2)) as UByte).unbox-impl();
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun UIntArray.reduceRight(operation: (UInt, UInt) -> UInt): UInt {
      var var3: Int = ArraysKt.getLastIndex(var0);
      if (var3 < 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var2: Int = var3 - 1;

         for (var3 = UIntArray.get-pVg5ArA(var0, var3); var2 >= 0; var2--) {
            var3 = (var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2)), UInt.box-impl(var3)) as UInt).unbox-impl();
         }

         return var3;
      }
   }

   @JvmStatic
   public inline fun ULongArray.reduceRight(operation: (ULong, ULong) -> ULong): ULong {
      val var3: Int = ArraysKt.getLastIndex(var0);
      if (var3 < 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var2: Int = var3 - 1;

         var var4: Long;
         for (var4 = ULongArray.get-s-VKNKU(var0, var3); var2 >= 0; var2--) {
            var4 = (var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2)), ULong.box-impl(var4)) as ULong).unbox-impl();
         }

         return var4;
      }
   }

   @JvmStatic
   public inline fun UShortArray.reduceRight(operation: (UShort, UShort) -> UShort): UShort {
      val var4: Int = ArraysKt.getLastIndex(var0);
      if (var4 < 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var3: Int = var4 - 1;

         var var2: Short;
         for (var2 = UShortArray.get-Mh2AYeg(var0, var4); var3 >= 0; var3--) {
            var2 = (var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var3)), UShort.box-impl(var2)) as UShort).unbox-impl();
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun UIntArray.reduceRightIndexed(operation: (Int, UInt, UInt) -> UInt): UInt {
      var var3: Int = ArraysKt.getLastIndex(var0);
      if (var3 < 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var2: Int = var3 - 1;

         for (var3 = UIntArray.get-pVg5ArA(var0, var3); var2 >= 0; var2--) {
            var3 = (var1.invoke(var2, UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2)), UInt.box-impl(var3)) as UInt).unbox-impl();
         }

         return var3;
      }
   }

   @JvmStatic
   public inline fun UByteArray.reduceRightIndexed(operation: (Int, UByte, UByte) -> UByte): UByte {
      val var4: Int = ArraysKt.getLastIndex(var0);
      if (var4 < 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var3: Int = var4 - 1;

         var var2: Byte;
         for (var2 = UByteArray.get-w2LRezQ(var0, var4); var3 >= 0; var3--) {
            var2 = (var1.invoke(var3, UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3)), UByte.box-impl(var2)) as UByte).unbox-impl();
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun UShortArray.reduceRightIndexed(operation: (Int, UShort, UShort) -> UShort): UShort {
      val var4: Int = ArraysKt.getLastIndex(var0);
      if (var4 < 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var3: Int = var4 - 1;

         var var2: Short;
         for (var2 = UShortArray.get-Mh2AYeg(var0, var4); var3 >= 0; var3--) {
            var2 = (var1.invoke(var3, UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var3)), UShort.box-impl(var2)) as UShort).unbox-impl();
         }

         return var2;
      }
   }

   @JvmStatic
   public inline fun ULongArray.reduceRightIndexed(operation: (Int, ULong, ULong) -> ULong): ULong {
      val var3: Int = ArraysKt.getLastIndex(var0);
      if (var3 < 0) {
         throw new UnsupportedOperationException("Empty array can't be reduced.");
      } else {
         var var2: Int = var3 - 1;

         var var4: Long;
         for (var4 = ULongArray.get-s-VKNKU(var0, var3); var2 >= 0; var2--) {
            var4 = (var1.invoke(var2, ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2)), ULong.box-impl(var4)) as ULong).unbox-impl();
         }

         return var4;
      }
   }

   @JvmStatic
   public inline fun UIntArray.reduceRightIndexedOrNull(operation: (Int, UInt, UInt) -> UInt): UInt? {
      var var3: Int = ArraysKt.getLastIndex(var0);
      if (var3 < 0) {
         return null;
      } else {
         var var2: Int = var3 - 1;

         for (var3 = UIntArray.get-pVg5ArA(var0, var3); var2 >= 0; var2--) {
            var3 = (var1.invoke(var2, UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2)), UInt.box-impl(var3)) as UInt).unbox-impl();
         }

         return UInt.box-impl(var3);
      }
   }

   @JvmStatic
   public inline fun UByteArray.reduceRightIndexedOrNull(operation: (Int, UByte, UByte) -> UByte): UByte? {
      val var4: Int = ArraysKt.getLastIndex(var0);
      if (var4 < 0) {
         return null;
      } else {
         var var3: Int = var4 - 1;

         var var2: Byte;
         for (var2 = UByteArray.get-w2LRezQ(var0, var4); var3 >= 0; var3--) {
            var2 = (var1.invoke(var3, UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3)), UByte.box-impl(var2)) as UByte).unbox-impl();
         }

         return UByte.box-impl(var2);
      }
   }

   @JvmStatic
   public inline fun UShortArray.reduceRightIndexedOrNull(operation: (Int, UShort, UShort) -> UShort): UShort? {
      val var4: Int = ArraysKt.getLastIndex(var0);
      if (var4 < 0) {
         return null;
      } else {
         var var3: Int = var4 - 1;

         var var2: Short;
         for (var2 = UShortArray.get-Mh2AYeg(var0, var4); var3 >= 0; var3--) {
            var2 = (var1.invoke(var3, UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var3)), UShort.box-impl(var2)) as UShort).unbox-impl();
         }

         return UShort.box-impl(var2);
      }
   }

   @JvmStatic
   public inline fun ULongArray.reduceRightIndexedOrNull(operation: (Int, ULong, ULong) -> ULong): ULong? {
      val var3: Int = ArraysKt.getLastIndex(var0);
      if (var3 < 0) {
         return null;
      } else {
         var var2: Int = var3 - 1;

         var var4: Long;
         for (var4 = ULongArray.get-s-VKNKU(var0, var3); var2 >= 0; var2--) {
            var4 = (var1.invoke(var2, ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2)), ULong.box-impl(var4)) as ULong).unbox-impl();
         }

         return ULong.box-impl(var4);
      }
   }

   @JvmStatic
   public inline fun UByteArray.reduceRightOrNull(operation: (UByte, UByte) -> UByte): UByte? {
      val var4: Int = ArraysKt.getLastIndex(var0);
      if (var4 < 0) {
         return null;
      } else {
         var var3: Int = var4 - 1;

         var var2: Byte;
         for (var2 = UByteArray.get-w2LRezQ(var0, var4); var3 >= 0; var3--) {
            var2 = (var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3)), UByte.box-impl(var2)) as UByte).unbox-impl();
         }

         return UByte.box-impl(var2);
      }
   }

   @JvmStatic
   public inline fun UIntArray.reduceRightOrNull(operation: (UInt, UInt) -> UInt): UInt? {
      var var3: Int = ArraysKt.getLastIndex(var0);
      if (var3 < 0) {
         return null;
      } else {
         var var2: Int = var3 - 1;

         for (var3 = UIntArray.get-pVg5ArA(var0, var3); var2 >= 0; var2--) {
            var3 = (var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2)), UInt.box-impl(var3)) as UInt).unbox-impl();
         }

         return UInt.box-impl(var3);
      }
   }

   @JvmStatic
   public inline fun ULongArray.reduceRightOrNull(operation: (ULong, ULong) -> ULong): ULong? {
      val var3: Int = ArraysKt.getLastIndex(var0);
      if (var3 < 0) {
         return null;
      } else {
         var var2: Int = var3 - 1;

         var var4: Long;
         for (var4 = ULongArray.get-s-VKNKU(var0, var3); var2 >= 0; var2--) {
            var4 = (var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2)), ULong.box-impl(var4)) as ULong).unbox-impl();
         }

         return ULong.box-impl(var4);
      }
   }

   @JvmStatic
   public inline fun UShortArray.reduceRightOrNull(operation: (UShort, UShort) -> UShort): UShort? {
      val var4: Int = ArraysKt.getLastIndex(var0);
      if (var4 < 0) {
         return null;
      } else {
         var var3: Int = var4 - 1;

         var var2: Short;
         for (var2 = UShortArray.get-Mh2AYeg(var0, var4); var3 >= 0; var3--) {
            var2 = (var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var3)), UShort.box-impl(var2)) as UShort).unbox-impl();
         }

         return UShort.box-impl(var2);
      }
   }

   @JvmStatic
   public inline fun UIntArray.reverse() {
      ArraysKt.reverse(var0);
   }

   @JvmStatic
   public inline fun ULongArray.reverse(fromIndex: Int, toIndex: Int) {
      ArraysKt.reverse(var0, var1, var2);
   }

   @JvmStatic
   public inline fun UByteArray.reverse(fromIndex: Int, toIndex: Int) {
      ArraysKt.reverse(var0, var1, var2);
   }

   @JvmStatic
   public inline fun UShortArray.reverse(fromIndex: Int, toIndex: Int) {
      ArraysKt.reverse(var0, var1, var2);
   }

   @JvmStatic
   public inline fun UByteArray.reverse() {
      ArraysKt.reverse(var0);
   }

   @JvmStatic
   public inline fun ULongArray.reverse() {
      ArraysKt.reverse(var0);
   }

   @JvmStatic
   public inline fun UIntArray.reverse(fromIndex: Int, toIndex: Int) {
      ArraysKt.reverse(var0, var1, var2);
   }

   @JvmStatic
   public inline fun UShortArray.reverse() {
      ArraysKt.reverse(var0);
   }

   @JvmStatic
   public fun UIntArray.reversed(): List<UInt> {
      if (UIntArray.isEmpty-impl(var0)) {
         return CollectionsKt.emptyList();
      } else {
         val var1: java.util.List = CollectionsKt.toMutableList(UIntArray.box-impl(var0));
         CollectionsKt.reverse(var1);
         return var1;
      }
   }

   @JvmStatic
   public fun UByteArray.reversed(): List<UByte> {
      if (UByteArray.isEmpty-impl(var0)) {
         return CollectionsKt.emptyList();
      } else {
         val var1: java.util.List = CollectionsKt.toMutableList(UByteArray.box-impl(var0));
         CollectionsKt.reverse(var1);
         return var1;
      }
   }

   @JvmStatic
   public fun ULongArray.reversed(): List<ULong> {
      if (ULongArray.isEmpty-impl(var0)) {
         return CollectionsKt.emptyList();
      } else {
         val var1: java.util.List = CollectionsKt.toMutableList(ULongArray.box-impl(var0));
         CollectionsKt.reverse(var1);
         return var1;
      }
   }

   @JvmStatic
   public fun UShortArray.reversed(): List<UShort> {
      if (UShortArray.isEmpty-impl(var0)) {
         return CollectionsKt.emptyList();
      } else {
         val var1: java.util.List = CollectionsKt.toMutableList(UShortArray.box-impl(var0));
         CollectionsKt.reverse(var1);
         return var1;
      }
   }

   @JvmStatic
   public inline fun UIntArray.reversedArray(): UIntArray {
      return UIntArray.constructor-impl(ArraysKt.reversedArray(var0));
   }

   @JvmStatic
   public inline fun UByteArray.reversedArray(): UByteArray {
      return UByteArray.constructor-impl(ArraysKt.reversedArray(var0));
   }

   @JvmStatic
   public inline fun ULongArray.reversedArray(): ULongArray {
      return ULongArray.constructor-impl(ArraysKt.reversedArray(var0));
   }

   @JvmStatic
   public inline fun UShortArray.reversedArray(): UShortArray {
      return UShortArray.constructor-impl(ArraysKt.reversedArray(var0));
   }

   @JvmStatic
   public inline fun <R> ULongArray.runningFold(initial: R, operation: (R, ULong) -> R): List<R> {
      if (ULongArray.isEmpty-impl(var0)) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(ULongArray.getSize-impl(var0) + 1);
         var5.add(var1);
         val var4: Int = ULongArray.getSize-impl(var0);

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var1, ULong.box-impl(ULongArray.get-s-VKNKU(var0, var3)));
            var5.add(var1);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun <R> UByteArray.runningFold(initial: R, operation: (R, UByte) -> R): List<R> {
      if (UByteArray.isEmpty-impl(var0)) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(UByteArray.getSize-impl(var0) + 1);
         var5.add(var1);
         val var4: Int = UByteArray.getSize-impl(var0);

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var1, UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3)));
            var5.add(var1);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun <R> UIntArray.runningFold(initial: R, operation: (R, UInt) -> R): List<R> {
      if (UIntArray.isEmpty-impl(var0)) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(UIntArray.getSize-impl(var0) + 1);
         var5.add(var1);
         val var4: Int = UIntArray.getSize-impl(var0);

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var1, UInt.box-impl(UIntArray.get-pVg5ArA(var0, var3)));
            var5.add(var1);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun <R> UShortArray.runningFold(initial: R, operation: (R, UShort) -> R): List<R> {
      if (UShortArray.isEmpty-impl(var0)) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(UShortArray.getSize-impl(var0) + 1);
         var5.add(var1);
         val var4: Int = UShortArray.getSize-impl(var0);

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var1, UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var3)));
            var5.add(var1);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun <R> UByteArray.runningFoldIndexed(initial: R, operation: (Int, R, UByte) -> R): List<R> {
      if (UByteArray.isEmpty-impl(var0)) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(UByteArray.getSize-impl(var0) + 1);
         var5.add(var1);
         val var4: Int = UByteArray.getSize-impl(var0);

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var3, var1, UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3)));
            var5.add(var1);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun <R> UShortArray.runningFoldIndexed(initial: R, operation: (Int, R, UShort) -> R): List<R> {
      if (UShortArray.isEmpty-impl(var0)) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(UShortArray.getSize-impl(var0) + 1);
         var5.add(var1);
         val var4: Int = UShortArray.getSize-impl(var0);

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var3, var1, UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var3)));
            var5.add(var1);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun <R> ULongArray.runningFoldIndexed(initial: R, operation: (Int, R, ULong) -> R): List<R> {
      if (ULongArray.isEmpty-impl(var0)) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(ULongArray.getSize-impl(var0) + 1);
         var5.add(var1);
         val var4: Int = ULongArray.getSize-impl(var0);

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var3, var1, ULong.box-impl(ULongArray.get-s-VKNKU(var0, var3)));
            var5.add(var1);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun <R> UIntArray.runningFoldIndexed(initial: R, operation: (Int, R, UInt) -> R): List<R> {
      if (UIntArray.isEmpty-impl(var0)) {
         return (java.util.List<R>)CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(UIntArray.getSize-impl(var0) + 1);
         var5.add(var1);
         val var4: Int = UIntArray.getSize-impl(var0);

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var3, var1, UInt.box-impl(UIntArray.get-pVg5ArA(var0, var3)));
            var5.add(var1);
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun UByteArray.runningReduce(operation: (UByte, UByte) -> UByte): List<UByte> {
      if (UByteArray.isEmpty-impl(var0)) {
         return CollectionsKt.emptyList();
      } else {
         var var2: Byte = UByteArray.get-w2LRezQ(var0, 0);
         val var5: ArrayList = new ArrayList(UByteArray.getSize-impl(var0));
         var5.add(UByte.box-impl(var2));
         val var4: Int = UByteArray.getSize-impl(var0);

         for (int var3 = 1; var3 < var4; var3++) {
            var2 = (var1.invoke(UByte.box-impl(var2), UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3))) as UByte).unbox-impl();
            var5.add(UByte.box-impl(var2));
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun UIntArray.runningReduce(operation: (UInt, UInt) -> UInt): List<UInt> {
      if (UIntArray.isEmpty-impl(var0)) {
         return CollectionsKt.emptyList();
      } else {
         var var3: Int = UIntArray.get-pVg5ArA(var0, 0);
         val var5: ArrayList = new ArrayList(UIntArray.getSize-impl(var0));
         var5.add(UInt.box-impl(var3));
         val var4: Int = UIntArray.getSize-impl(var0);

         for (int var2 = 1; var2 < var4; var2++) {
            var3 = (var1.invoke(UInt.box-impl(var3), UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2))) as UInt).unbox-impl();
            var5.add(UInt.box-impl(var3));
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun ULongArray.runningReduce(operation: (ULong, ULong) -> ULong): List<ULong> {
      if (ULongArray.isEmpty-impl(var0)) {
         return CollectionsKt.emptyList();
      } else {
         var var4: Long = ULongArray.get-s-VKNKU(var0, 0);
         val var6: ArrayList = new ArrayList(ULongArray.getSize-impl(var0));
         var6.add(ULong.box-impl(var4));
         val var3: Int = ULongArray.getSize-impl(var0);

         for (int var2 = 1; var2 < var3; var2++) {
            var4 = (var1.invoke(ULong.box-impl(var4), ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2))) as ULong).unbox-impl();
            var6.add(ULong.box-impl(var4));
         }

         return var6;
      }
   }

   @JvmStatic
   public inline fun UShortArray.runningReduce(operation: (UShort, UShort) -> UShort): List<UShort> {
      if (UShortArray.isEmpty-impl(var0)) {
         return CollectionsKt.emptyList();
      } else {
         var var2: Short = UShortArray.get-Mh2AYeg(var0, 0);
         val var5: ArrayList = new ArrayList(UShortArray.getSize-impl(var0));
         var5.add(UShort.box-impl(var2));
         val var4: Int = UShortArray.getSize-impl(var0);

         for (int var3 = 1; var3 < var4; var3++) {
            var2 = (var1.invoke(UShort.box-impl(var2), UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var3))) as UShort).unbox-impl();
            var5.add(UShort.box-impl(var2));
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun UIntArray.runningReduceIndexed(operation: (Int, UInt, UInt) -> UInt): List<UInt> {
      if (UIntArray.isEmpty-impl(var0)) {
         return CollectionsKt.emptyList();
      } else {
         var var3: Int = UIntArray.get-pVg5ArA(var0, 0);
         val var5: ArrayList = new ArrayList(UIntArray.getSize-impl(var0));
         var5.add(UInt.box-impl(var3));
         val var4: Int = UIntArray.getSize-impl(var0);

         for (int var2 = 1; var2 < var4; var2++) {
            var3 = (var1.invoke(var2, UInt.box-impl(var3), UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2))) as UInt).unbox-impl();
            var5.add(UInt.box-impl(var3));
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun UByteArray.runningReduceIndexed(operation: (Int, UByte, UByte) -> UByte): List<UByte> {
      if (UByteArray.isEmpty-impl(var0)) {
         return CollectionsKt.emptyList();
      } else {
         var var2: Byte = UByteArray.get-w2LRezQ(var0, 0);
         val var5: ArrayList = new ArrayList(UByteArray.getSize-impl(var0));
         var5.add(UByte.box-impl(var2));
         val var4: Int = UByteArray.getSize-impl(var0);

         for (int var3 = 1; var3 < var4; var3++) {
            var2 = (var1.invoke(var3, UByte.box-impl(var2), UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3))) as UByte).unbox-impl();
            var5.add(UByte.box-impl(var2));
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun UShortArray.runningReduceIndexed(operation: (Int, UShort, UShort) -> UShort): List<UShort> {
      if (UShortArray.isEmpty-impl(var0)) {
         return CollectionsKt.emptyList();
      } else {
         var var2: Short = UShortArray.get-Mh2AYeg(var0, 0);
         val var5: ArrayList = new ArrayList(UShortArray.getSize-impl(var0));
         var5.add(UShort.box-impl(var2));
         val var4: Int = UShortArray.getSize-impl(var0);

         for (int var3 = 1; var3 < var4; var3++) {
            var2 = (var1.invoke(var3, UShort.box-impl(var2), UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var3))) as UShort).unbox-impl();
            var5.add(UShort.box-impl(var2));
         }

         return var5;
      }
   }

   @JvmStatic
   public inline fun ULongArray.runningReduceIndexed(operation: (Int, ULong, ULong) -> ULong): List<ULong> {
      if (ULongArray.isEmpty-impl(var0)) {
         return CollectionsKt.emptyList();
      } else {
         var var4: Long = ULongArray.get-s-VKNKU(var0, 0);
         val var6: ArrayList = new ArrayList(ULongArray.getSize-impl(var0));
         var6.add(ULong.box-impl(var4));
         val var3: Int = ULongArray.getSize-impl(var0);

         for (int var2 = 1; var2 < var3; var2++) {
            var4 = (var1.invoke(var2, ULong.box-impl(var4), ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2))) as ULong).unbox-impl();
            var6.add(ULong.box-impl(var4));
         }

         return var6;
      }
   }

   @JvmStatic
   public inline fun <R> ULongArray.scan(initial: R, operation: (R, ULong) -> R): List<R> {
      val var6: java.util.List;
      if (ULongArray.isEmpty-impl(var0)) {
         var6 = CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(ULongArray.getSize-impl(var0) + 1);
         var5.add(var1);
         val var4: Int = ULongArray.getSize-impl(var0);

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var1, ULong.box-impl(ULongArray.get-s-VKNKU(var0, var3)));
            var5.add(var1);
         }

         var6 = var5;
      }

      return var6;
   }

   @JvmStatic
   public inline fun <R> UByteArray.scan(initial: R, operation: (R, UByte) -> R): List<R> {
      val var6: java.util.List;
      if (UByteArray.isEmpty-impl(var0)) {
         var6 = CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(UByteArray.getSize-impl(var0) + 1);
         var5.add(var1);
         val var4: Int = UByteArray.getSize-impl(var0);

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var1, UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3)));
            var5.add(var1);
         }

         var6 = var5;
      }

      return var6;
   }

   @JvmStatic
   public inline fun <R> UIntArray.scan(initial: R, operation: (R, UInt) -> R): List<R> {
      val var6: java.util.List;
      if (UIntArray.isEmpty-impl(var0)) {
         var6 = CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(UIntArray.getSize-impl(var0) + 1);
         var5.add(var1);
         val var4: Int = UIntArray.getSize-impl(var0);

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var1, UInt.box-impl(UIntArray.get-pVg5ArA(var0, var3)));
            var5.add(var1);
         }

         var6 = var5;
      }

      return var6;
   }

   @JvmStatic
   public inline fun <R> UShortArray.scan(initial: R, operation: (R, UShort) -> R): List<R> {
      val var6: java.util.List;
      if (UShortArray.isEmpty-impl(var0)) {
         var6 = CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(UShortArray.getSize-impl(var0) + 1);
         var5.add(var1);
         val var4: Int = UShortArray.getSize-impl(var0);

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var1, UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var3)));
            var5.add(var1);
         }

         var6 = var5;
      }

      return var6;
   }

   @JvmStatic
   public inline fun <R> UByteArray.scanIndexed(initial: R, operation: (Int, R, UByte) -> R): List<R> {
      val var6: java.util.List;
      if (UByteArray.isEmpty-impl(var0)) {
         var6 = CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(UByteArray.getSize-impl(var0) + 1);
         var5.add(var1);
         val var4: Int = UByteArray.getSize-impl(var0);

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var3, var1, UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3)));
            var5.add(var1);
         }

         var6 = var5;
      }

      return var6;
   }

   @JvmStatic
   public inline fun <R> UShortArray.scanIndexed(initial: R, operation: (Int, R, UShort) -> R): List<R> {
      val var6: java.util.List;
      if (UShortArray.isEmpty-impl(var0)) {
         var6 = CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(UShortArray.getSize-impl(var0) + 1);
         var5.add(var1);
         val var4: Int = UShortArray.getSize-impl(var0);

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var3, var1, UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var3)));
            var5.add(var1);
         }

         var6 = var5;
      }

      return var6;
   }

   @JvmStatic
   public inline fun <R> ULongArray.scanIndexed(initial: R, operation: (Int, R, ULong) -> R): List<R> {
      val var6: java.util.List;
      if (ULongArray.isEmpty-impl(var0)) {
         var6 = CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(ULongArray.getSize-impl(var0) + 1);
         var5.add(var1);
         val var4: Int = ULongArray.getSize-impl(var0);

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var3, var1, ULong.box-impl(ULongArray.get-s-VKNKU(var0, var3)));
            var5.add(var1);
         }

         var6 = var5;
      }

      return var6;
   }

   @JvmStatic
   public inline fun <R> UIntArray.scanIndexed(initial: R, operation: (Int, R, UInt) -> R): List<R> {
      val var6: java.util.List;
      if (UIntArray.isEmpty-impl(var0)) {
         var6 = CollectionsKt.listOf(var1);
      } else {
         val var5: ArrayList = new ArrayList(UIntArray.getSize-impl(var0) + 1);
         var5.add(var1);
         val var4: Int = UIntArray.getSize-impl(var0);

         for (int var3 = 0; var3 < var4; var3++) {
            var1 = var2.invoke(var3, var1, UInt.box-impl(UIntArray.get-pVg5ArA(var0, var3)));
            var5.add(var1);
         }

         var6 = var5;
      }

      return var6;
   }

   @JvmStatic
   public fun UIntArray.shuffle() {
      UArraysKt.shuffle-2D5oskM(var0, Random.Default);
   }

   @JvmStatic
   public fun UIntArray.shuffle(random: Random) {
      for (int var2 = ArraysKt.getLastIndex(var0); var2 > 0; var2--) {
         val var3: Int = var1.nextInt(var2 + 1);
         val var4: Int = UIntArray.get-pVg5ArA(var0, var2);
         UIntArray.set-VXSXFK8(var0, var2, UIntArray.get-pVg5ArA(var0, var3));
         UIntArray.set-VXSXFK8(var0, var3, var4);
      }
   }

   @JvmStatic
   public fun UByteArray.shuffle() {
      UArraysKt.shuffle-oSF2wD8(var0, Random.Default);
   }

   @JvmStatic
   public fun ULongArray.shuffle(random: Random) {
      for (int var2 = ArraysKt.getLastIndex(var0); var2 > 0; var2--) {
         val var3: Int = var1.nextInt(var2 + 1);
         val var4: Long = ULongArray.get-s-VKNKU(var0, var2);
         ULongArray.set-k8EXiF4(var0, var2, ULongArray.get-s-VKNKU(var0, var3));
         ULongArray.set-k8EXiF4(var0, var3, var4);
      }
   }

   @JvmStatic
   public fun ULongArray.shuffle() {
      UArraysKt.shuffle-JzugnMA(var0, Random.Default);
   }

   @JvmStatic
   public fun UByteArray.shuffle(random: Random) {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 > 0; var3--) {
         val var4: Int = var1.nextInt(var3 + 1);
         val var2: Byte = UByteArray.get-w2LRezQ(var0, var3);
         UByteArray.set-VurrAj0(var0, var3, UByteArray.get-w2LRezQ(var0, var4));
         UByteArray.set-VurrAj0(var0, var4, var2);
      }
   }

   @JvmStatic
   public fun UShortArray.shuffle() {
      UArraysKt.shuffle-s5X_as8(var0, Random.Default);
   }

   @JvmStatic
   public fun UShortArray.shuffle(random: Random) {
      for (int var3 = ArraysKt.getLastIndex(var0); var3 > 0; var3--) {
         val var4: Int = var1.nextInt(var3 + 1);
         val var2: Short = UShortArray.get-Mh2AYeg(var0, var3);
         UShortArray.set-01HTLdE(var0, var3, UShortArray.get-Mh2AYeg(var0, var4));
         UShortArray.set-01HTLdE(var0, var4, var2);
      }
   }

   @JvmStatic
   public inline fun UIntArray.single(): UInt {
      return UInt.constructor-impl(ArraysKt.single(var0));
   }

   @JvmStatic
   public inline fun UByteArray.single(): UByte {
      return UByte.constructor-impl(ArraysKt.single(var0));
   }

   @JvmStatic
   public inline fun UByteArray.single(predicate: (UByte) -> Boolean): UByte {
      val var6: Int = UByteArray.getSize-impl(var0);
      var var7: UByte = null;
      var var4: Int = 0;
      var var3: Boolean = false;

      while (var4 < var6) {
         val var2: Byte = UByteArray.get-w2LRezQ(var0, var4);
         var var5: Boolean = var3;
         if (var1.invoke(UByte.box-impl(var2)) as java.lang.Boolean) {
            if (var3) {
               throw new IllegalArgumentException("Array contains more than one matching element.");
            }

            var7 = UByte.box-impl(var2);
            var5 = true;
         }

         var4++;
         var3 = var5;
      }

      if (var3) {
         return var7.unbox-impl();
      } else {
         throw new NoSuchElementException("Array contains no element matching the predicate.");
      }
   }

   @JvmStatic
   public inline fun ULongArray.single(predicate: (ULong) -> Boolean): ULong {
      val var5: Int = ULongArray.getSize-impl(var0);
      var var8: ULong = null;
      var var2: Int = 0;
      var var3: Boolean = false;

      while (var2 < var5) {
         val var6: Long = ULongArray.get-s-VKNKU(var0, var2);
         var var4: Boolean = var3;
         if (var1.invoke(ULong.box-impl(var6)) as java.lang.Boolean) {
            if (var3) {
               throw new IllegalArgumentException("Array contains more than one matching element.");
            }

            var8 = ULong.box-impl(var6);
            var4 = true;
         }

         var2++;
         var3 = var4;
      }

      if (var3) {
         return var8.unbox-impl();
      } else {
         throw new NoSuchElementException("Array contains no element matching the predicate.");
      }
   }

   @JvmStatic
   public inline fun ULongArray.single(): ULong {
      return ULong.constructor-impl(ArraysKt.single(var0));
   }

   @JvmStatic
   public inline fun UIntArray.single(predicate: (UInt) -> Boolean): UInt {
      val var5: Int = UIntArray.getSize-impl(var0);
      var var7: UInt = null;
      var var4: Int = 0;
      var var2: Boolean = false;

      while (var4 < var5) {
         val var6: Int = UIntArray.get-pVg5ArA(var0, var4);
         var var3: Boolean = var2;
         if (var1.invoke(UInt.box-impl(var6)) as java.lang.Boolean) {
            if (var2) {
               throw new IllegalArgumentException("Array contains more than one matching element.");
            }

            var7 = UInt.box-impl(var6);
            var3 = true;
         }

         var4++;
         var2 = var3;
      }

      if (var2) {
         return var7.unbox-impl();
      } else {
         throw new NoSuchElementException("Array contains no element matching the predicate.");
      }
   }

   @JvmStatic
   public inline fun UShortArray.single(): UShort {
      return UShort.constructor-impl(ArraysKt.single(var0));
   }

   @JvmStatic
   public inline fun UShortArray.single(predicate: (UShort) -> Boolean): UShort {
      val var6: Int = UShortArray.getSize-impl(var0);
      var var7: UShort = null;
      var var4: Int = 0;
      var var3: Boolean = false;

      while (var4 < var6) {
         val var2: Short = UShortArray.get-Mh2AYeg(var0, var4);
         var var5: Boolean = var3;
         if (var1.invoke(UShort.box-impl(var2)) as java.lang.Boolean) {
            if (var3) {
               throw new IllegalArgumentException("Array contains more than one matching element.");
            }

            var7 = UShort.box-impl(var2);
            var5 = true;
         }

         var4++;
         var3 = var5;
      }

      if (var3) {
         return var7.unbox-impl();
      } else {
         throw new NoSuchElementException("Array contains no element matching the predicate.");
      }
   }

   @JvmStatic
   public fun UIntArray.singleOrNull(): UInt? {
      val var1: UInt;
      if (UIntArray.getSize-impl(var0) == 1) {
         var1 = UInt.box-impl(UIntArray.get-pVg5ArA(var0, 0));
      } else {
         var1 = null;
      }

      return var1;
   }

   @JvmStatic
   public fun UByteArray.singleOrNull(): UByte? {
      val var1: UByte;
      if (UByteArray.getSize-impl(var0) == 1) {
         var1 = UByte.box-impl(UByteArray.get-w2LRezQ(var0, 0));
      } else {
         var1 = null;
      }

      return var1;
   }

   @JvmStatic
   public inline fun UByteArray.singleOrNull(predicate: (UByte) -> Boolean): UByte? {
      val var6: Int = UByteArray.getSize-impl(var0);
      var var3: Int = 0;
      var var7: UByte = null;
      var var4: Boolean = false;

      while (var3 < var6) {
         val var2: Byte = UByteArray.get-w2LRezQ(var0, var3);
         var var5: Boolean = var4;
         if (var1.invoke(UByte.box-impl(var2)) as java.lang.Boolean) {
            if (var4) {
               return null;
            }

            var7 = UByte.box-impl(var2);
            var5 = true;
         }

         var3++;
         var4 = var5;
      }

      return if (!var4) null else var7;
   }

   @JvmStatic
   public inline fun ULongArray.singleOrNull(predicate: (ULong) -> Boolean): ULong? {
      val var5: Int = ULongArray.getSize-impl(var0);
      var var4: Int = 0;
      var var8: ULong = null;
      var var2: Boolean = false;

      while (var4 < var5) {
         val var6: Long = ULongArray.get-s-VKNKU(var0, var4);
         var var3: Boolean = var2;
         if (var1.invoke(ULong.box-impl(var6)) as java.lang.Boolean) {
            if (var2) {
               return null;
            }

            var8 = ULong.box-impl(var6);
            var3 = true;
         }

         var4++;
         var2 = var3;
      }

      return if (!var2) null else var8;
   }

   @JvmStatic
   public fun ULongArray.singleOrNull(): ULong? {
      val var1: ULong;
      if (ULongArray.getSize-impl(var0) == 1) {
         var1 = ULong.box-impl(ULongArray.get-s-VKNKU(var0, 0));
      } else {
         var1 = null;
      }

      return var1;
   }

   @JvmStatic
   public inline fun UIntArray.singleOrNull(predicate: (UInt) -> Boolean): UInt? {
      val var5: Int = UIntArray.getSize-impl(var0);
      var var2: Int = 0;
      var var7: UInt = null;
      var var3: Boolean = false;

      while (var2 < var5) {
         val var6: Int = UIntArray.get-pVg5ArA(var0, var2);
         var var4: Boolean = var3;
         if (var1.invoke(UInt.box-impl(var6)) as java.lang.Boolean) {
            if (var3) {
               return null;
            }

            var7 = UInt.box-impl(var6);
            var4 = true;
         }

         var2++;
         var3 = var4;
      }

      return if (!var3) null else var7;
   }

   @JvmStatic
   public fun UShortArray.singleOrNull(): UShort? {
      val var1: UShort;
      if (UShortArray.getSize-impl(var0) == 1) {
         var1 = UShort.box-impl(UShortArray.get-Mh2AYeg(var0, 0));
      } else {
         var1 = null;
      }

      return var1;
   }

   @JvmStatic
   public inline fun UShortArray.singleOrNull(predicate: (UShort) -> Boolean): UShort? {
      val var6: Int = UShortArray.getSize-impl(var0);
      var var3: Int = 0;
      var var7: UShort = null;
      var var4: Boolean = false;

      while (var3 < var6) {
         val var2: Short = UShortArray.get-Mh2AYeg(var0, var3);
         var var5: Boolean = var4;
         if (var1.invoke(UShort.box-impl(var2)) as java.lang.Boolean) {
            if (var4) {
               return null;
            }

            var7 = UShort.box-impl(var2);
            var5 = true;
         }

         var3++;
         var4 = var5;
      }

      return if (!var4) null else var7;
   }

   @JvmStatic
   public fun ULongArray.slice(indices: Iterable<Int>): List<ULong> {
      val var2: Int = CollectionsKt.collectionSizeOrDefault(var1, 10);
      if (var2 == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var3: ArrayList = new ArrayList(var2);
         val var4: java.util.Iterator = var1.iterator();

         while (var4.hasNext()) {
            var3.add(ULong.box-impl(ULongArray.get-s-VKNKU(var0, (var4.next() as java.lang.Number).intValue())));
         }

         return var3;
      }
   }

   @JvmStatic
   public fun UIntArray.slice(indices: Iterable<Int>): List<UInt> {
      val var2: Int = CollectionsKt.collectionSizeOrDefault(var1, 10);
      if (var2 == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var3: ArrayList = new ArrayList(var2);
         val var4: java.util.Iterator = var1.iterator();

         while (var4.hasNext()) {
            var3.add(UInt.box-impl(UIntArray.get-pVg5ArA(var0, (var4.next() as java.lang.Number).intValue())));
         }

         return var3;
      }
   }

   @JvmStatic
   public fun UShortArray.slice(indices: Iterable<Int>): List<UShort> {
      val var2: Int = CollectionsKt.collectionSizeOrDefault(var1, 10);
      if (var2 == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var3: ArrayList = new ArrayList(var2);
         val var4: java.util.Iterator = var1.iterator();

         while (var4.hasNext()) {
            var3.add(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, (var4.next() as java.lang.Number).intValue())));
         }

         return var3;
      }
   }

   @JvmStatic
   public fun UByteArray.slice(indices: Iterable<Int>): List<UByte> {
      val var2: Int = CollectionsKt.collectionSizeOrDefault(var1, 10);
      if (var2 == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var3: ArrayList = new ArrayList(var2);
         val var4: java.util.Iterator = var1.iterator();

         while (var4.hasNext()) {
            var3.add(UByte.box-impl(UByteArray.get-w2LRezQ(var0, (var4.next() as java.lang.Number).intValue())));
         }

         return var3;
      }
   }

   @JvmStatic
   public fun UShortArray.slice(indices: IntRange): List<UShort> {
      return if (var1.isEmpty())
         CollectionsKt.emptyList()
         else
         UArraysKt.asList-rL5Bavg(UShortArray.constructor-impl(ArraysKt.copyOfRange(var0, var1.getStart(), var1.getEndInclusive() + 1)));
   }

   @JvmStatic
   public fun ULongArray.slice(indices: IntRange): List<ULong> {
      return if (var1.isEmpty())
         CollectionsKt.emptyList()
         else
         UArraysKt.asList-QwZRm1k(ULongArray.constructor-impl(ArraysKt.copyOfRange(var0, var1.getStart(), var1.getEndInclusive() + 1)));
   }

   @JvmStatic
   public fun UByteArray.slice(indices: IntRange): List<UByte> {
      return if (var1.isEmpty())
         CollectionsKt.emptyList()
         else
         UArraysKt.asList-GBYM_sE(UByteArray.constructor-impl(ArraysKt.copyOfRange(var0, var1.getStart(), var1.getEndInclusive() + 1)));
   }

   @JvmStatic
   public fun UIntArray.slice(indices: IntRange): List<UInt> {
      return if (var1.isEmpty())
         CollectionsKt.emptyList()
         else
         UArraysKt.asList--ajY-9A(UIntArray.constructor-impl(ArraysKt.copyOfRange(var0, var1.getStart(), var1.getEndInclusive() + 1)));
   }

   @JvmStatic
   public fun UIntArray.sliceArray(indices: Collection<Int>): UIntArray {
      return UIntArray.constructor-impl(ArraysKt.sliceArray(var0, var1));
   }

   @JvmStatic
   public fun UShortArray.sliceArray(indices: IntRange): UShortArray {
      return UShortArray.constructor-impl(ArraysKt.sliceArray(var0, var1));
   }

   @JvmStatic
   public fun ULongArray.sliceArray(indices: IntRange): ULongArray {
      return ULongArray.constructor-impl(ArraysKt.sliceArray(var0, var1));
   }

   @JvmStatic
   public fun UByteArray.sliceArray(indices: IntRange): UByteArray {
      return UByteArray.constructor-impl(ArraysKt.sliceArray(var0, var1));
   }

   @JvmStatic
   public fun ULongArray.sliceArray(indices: Collection<Int>): ULongArray {
      return ULongArray.constructor-impl(ArraysKt.sliceArray(var0, var1));
   }

   @JvmStatic
   public fun UShortArray.sliceArray(indices: Collection<Int>): UShortArray {
      return UShortArray.constructor-impl(ArraysKt.sliceArray(var0, var1));
   }

   @JvmStatic
   public fun UIntArray.sliceArray(indices: IntRange): UIntArray {
      return UIntArray.constructor-impl(ArraysKt.sliceArray(var0, var1));
   }

   @JvmStatic
   public fun UByteArray.sliceArray(indices: Collection<Int>): UByteArray {
      return UByteArray.constructor-impl(ArraysKt.sliceArray(var0, var1));
   }

   @JvmStatic
   public fun UIntArray.sort() {
      if (UIntArray.getSize-impl(var0) > 1) {
         UArraySortingKt.sortArray-oBK06Vg(var0, 0, UIntArray.getSize-impl(var0));
      }
   }

   @JvmStatic
   public fun ULongArray.sort(fromIndex: Int = ..., toIndex: Int = ...) {
      AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(var1, var2, ULongArray.getSize-impl(var0));
      if (var1 < var2 - 1) {
         UArraySortingKt.sortArray--nroSd4(var0, var1, var2);
      }
   }

   @JvmStatic
   public fun UByteArray.sort(fromIndex: Int = ..., toIndex: Int = ...) {
      AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(var1, var2, UByteArray.getSize-impl(var0));
      if (var1 < var2 - 1) {
         UArraySortingKt.sortArray-4UcCI2c(var0, var1, var2);
      }
   }

   @JvmStatic
   public fun UShortArray.sort(fromIndex: Int = ..., toIndex: Int = ...) {
      AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(var1, var2, UShortArray.getSize-impl(var0));
      if (var1 < var2 - 1) {
         UArraySortingKt.sortArray-Aa5vz7o(var0, var1, var2);
      }
   }

   @JvmStatic
   public fun UByteArray.sort() {
      if (UByteArray.getSize-impl(var0) > 1) {
         UArraySortingKt.sortArray-4UcCI2c(var0, 0, UByteArray.getSize-impl(var0));
      }
   }

   @JvmStatic
   public fun ULongArray.sort() {
      if (ULongArray.getSize-impl(var0) > 1) {
         UArraySortingKt.sortArray--nroSd4(var0, 0, ULongArray.getSize-impl(var0));
      }
   }

   @JvmStatic
   public fun UIntArray.sort(fromIndex: Int = ..., toIndex: Int = ...) {
      AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(var1, var2, UIntArray.getSize-impl(var0));
      if (var1 < var2 - 1) {
         UArraySortingKt.sortArray-oBK06Vg(var0, var1, var2);
      }
   }

   @JvmStatic
   public fun UShortArray.sort() {
      if (UShortArray.getSize-impl(var0) > 1) {
         UArraySortingKt.sortArray-Aa5vz7o(var0, 0, UShortArray.getSize-impl(var0));
      }
   }

   @JvmStatic
   public fun UIntArray.sortDescending() {
      if (UIntArray.getSize-impl(var0) > 1) {
         UArraysKt.sort--ajY-9A(var0);
         ArraysKt.reverse(var0);
      }
   }

   @JvmStatic
   public fun ULongArray.sortDescending(fromIndex: Int, toIndex: Int) {
      UArraysKt.sort--nroSd4(var0, var1, var2);
      ArraysKt.reverse(var0, var1, var2);
   }

   @JvmStatic
   public fun UByteArray.sortDescending(fromIndex: Int, toIndex: Int) {
      UArraysKt.sort-4UcCI2c(var0, var1, var2);
      ArraysKt.reverse(var0, var1, var2);
   }

   @JvmStatic
   public fun UShortArray.sortDescending(fromIndex: Int, toIndex: Int) {
      UArraysKt.sort-Aa5vz7o(var0, var1, var2);
      ArraysKt.reverse(var0, var1, var2);
   }

   @JvmStatic
   public fun UByteArray.sortDescending() {
      if (UByteArray.getSize-impl(var0) > 1) {
         UArraysKt.sort-GBYM_sE(var0);
         ArraysKt.reverse(var0);
      }
   }

   @JvmStatic
   public fun ULongArray.sortDescending() {
      if (ULongArray.getSize-impl(var0) > 1) {
         UArraysKt.sort-QwZRm1k(var0);
         ArraysKt.reverse(var0);
      }
   }

   @JvmStatic
   public fun UIntArray.sortDescending(fromIndex: Int, toIndex: Int) {
      UArraysKt.sort-oBK06Vg(var0, var1, var2);
      ArraysKt.reverse(var0, var1, var2);
   }

   @JvmStatic
   public fun UShortArray.sortDescending() {
      if (UShortArray.getSize-impl(var0) > 1) {
         UArraysKt.sort-rL5Bavg(var0);
         ArraysKt.reverse(var0);
      }
   }

   @JvmStatic
   public fun UIntArray.sorted(): List<UInt> {
      var0 = Arrays.copyOf(var0, var0.length);
      var0 = UIntArray.constructor-impl(var0);
      UArraysKt.sort--ajY-9A(var0);
      return UArraysKt.asList--ajY-9A(var0);
   }

   @JvmStatic
   public fun UByteArray.sorted(): List<UByte> {
      var0 = Arrays.copyOf(var0, var0.length);
      var0 = UByteArray.constructor-impl(var0);
      UArraysKt.sort-GBYM_sE(var0);
      return UArraysKt.asList-GBYM_sE(var0);
   }

   @JvmStatic
   public fun ULongArray.sorted(): List<ULong> {
      var0 = Arrays.copyOf(var0, var0.length);
      var0 = ULongArray.constructor-impl(var0);
      UArraysKt.sort-QwZRm1k(var0);
      return UArraysKt.asList-QwZRm1k(var0);
   }

   @JvmStatic
   public fun UShortArray.sorted(): List<UShort> {
      var0 = Arrays.copyOf(var0, var0.length);
      var0 = UShortArray.constructor-impl(var0);
      UArraysKt.sort-rL5Bavg(var0);
      return UArraysKt.asList-rL5Bavg(var0);
   }

   @JvmStatic
   public fun UIntArray.sortedArray(): UIntArray {
      if (UIntArray.isEmpty-impl(var0)) {
         return var0;
      } else {
         var0 = Arrays.copyOf(var0, var0.length);
         var0 = UIntArray.constructor-impl(var0);
         UArraysKt.sort--ajY-9A(var0);
         return var0;
      }
   }

   @JvmStatic
   public fun UByteArray.sortedArray(): UByteArray {
      if (UByteArray.isEmpty-impl(var0)) {
         return var0;
      } else {
         var0 = Arrays.copyOf(var0, var0.length);
         var0 = UByteArray.constructor-impl(var0);
         UArraysKt.sort-GBYM_sE(var0);
         return var0;
      }
   }

   @JvmStatic
   public fun ULongArray.sortedArray(): ULongArray {
      if (ULongArray.isEmpty-impl(var0)) {
         return var0;
      } else {
         var0 = Arrays.copyOf(var0, var0.length);
         var0 = ULongArray.constructor-impl(var0);
         UArraysKt.sort-QwZRm1k(var0);
         return var0;
      }
   }

   @JvmStatic
   public fun UShortArray.sortedArray(): UShortArray {
      if (UShortArray.isEmpty-impl(var0)) {
         return var0;
      } else {
         var0 = Arrays.copyOf(var0, var0.length);
         var0 = UShortArray.constructor-impl(var0);
         UArraysKt.sort-rL5Bavg(var0);
         return var0;
      }
   }

   @JvmStatic
   public fun UIntArray.sortedArrayDescending(): UIntArray {
      if (UIntArray.isEmpty-impl(var0)) {
         return var0;
      } else {
         var0 = Arrays.copyOf(var0, var0.length);
         var0 = UIntArray.constructor-impl(var0);
         UArraysKt.sortDescending--ajY-9A(var0);
         return var0;
      }
   }

   @JvmStatic
   public fun UByteArray.sortedArrayDescending(): UByteArray {
      if (UByteArray.isEmpty-impl(var0)) {
         return var0;
      } else {
         var0 = Arrays.copyOf(var0, var0.length);
         var0 = UByteArray.constructor-impl(var0);
         UArraysKt.sortDescending-GBYM_sE(var0);
         return var0;
      }
   }

   @JvmStatic
   public fun ULongArray.sortedArrayDescending(): ULongArray {
      if (ULongArray.isEmpty-impl(var0)) {
         return var0;
      } else {
         var0 = Arrays.copyOf(var0, var0.length);
         var0 = ULongArray.constructor-impl(var0);
         UArraysKt.sortDescending-QwZRm1k(var0);
         return var0;
      }
   }

   @JvmStatic
   public fun UShortArray.sortedArrayDescending(): UShortArray {
      if (UShortArray.isEmpty-impl(var0)) {
         return var0;
      } else {
         var0 = Arrays.copyOf(var0, var0.length);
         var0 = UShortArray.constructor-impl(var0);
         UArraysKt.sortDescending-rL5Bavg(var0);
         return var0;
      }
   }

   @JvmStatic
   public fun UIntArray.sortedDescending(): List<UInt> {
      var0 = Arrays.copyOf(var0, var0.length);
      var0 = UIntArray.constructor-impl(var0);
      UArraysKt.sort--ajY-9A(var0);
      return UArraysKt.reversed--ajY-9A(var0);
   }

   @JvmStatic
   public fun UByteArray.sortedDescending(): List<UByte> {
      var0 = Arrays.copyOf(var0, var0.length);
      var0 = UByteArray.constructor-impl(var0);
      UArraysKt.sort-GBYM_sE(var0);
      return UArraysKt.reversed-GBYM_sE(var0);
   }

   @JvmStatic
   public fun ULongArray.sortedDescending(): List<ULong> {
      var0 = Arrays.copyOf(var0, var0.length);
      var0 = ULongArray.constructor-impl(var0);
      UArraysKt.sort-QwZRm1k(var0);
      return UArraysKt.reversed-QwZRm1k(var0);
   }

   @JvmStatic
   public fun UShortArray.sortedDescending(): List<UShort> {
      var0 = Arrays.copyOf(var0, var0.length);
      var0 = UShortArray.constructor-impl(var0);
      UArraysKt.sort-rL5Bavg(var0);
      return UArraysKt.reversed-rL5Bavg(var0);
   }

   @JvmStatic
   public inline fun UIntArray.sum(): UInt {
      return UInt.constructor-impl(ArraysKt.sum(var0));
   }

   @JvmStatic
   public inline fun UByteArray.sum(): UInt {
      var var2: Int = 0;
      var var1: Int = UInt.constructor-impl(0);

      for (int var3 = UByteArray.getSize-impl(var0); var2 < var3; var2++) {
         var1 = UInt.constructor-impl(var1 + UInt.constructor-impl(UByteArray.get-w2LRezQ(var0, var2) and 255));
      }

      return var1;
   }

   @JvmStatic
   public inline fun ULongArray.sum(): ULong {
      return ULong.constructor-impl(ArraysKt.sum(var0));
   }

   @JvmStatic
   public inline fun UShortArray.sum(): UInt {
      var var1: Int = 0;
      var var2: Int = UInt.constructor-impl(0);

      for (int var3 = UShortArray.getSize-impl(var0); var1 < var3; var1++) {
         var2 = UInt.constructor-impl(var2 + UInt.constructor-impl(UShortArray.get-Mh2AYeg(var0, var1) and '\uffff'));
      }

      return var2;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun UByteArray.sumBy(selector: (UByte) -> UInt): UInt {
      val var4: Int = UByteArray.getSize-impl(var0);
      var var3: Int = 0;

      var var2: Int;
      for (var2 = 0; var3 < var4; var3++) {
         var2 = UInt.constructor-impl(var2 + (var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3))) as UInt).unbox-impl());
      }

      return var2;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun ULongArray.sumBy(selector: (ULong) -> UInt): UInt {
      val var4: Int = ULongArray.getSize-impl(var0);
      var var2: Int = 0;

      var var3: Int;
      for (var3 = 0; var2 < var4; var2++) {
         var3 = UInt.constructor-impl(var3 + (var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2))) as UInt).unbox-impl());
      }

      return var3;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun UIntArray.sumBy(selector: (UInt) -> UInt): UInt {
      val var4: Int = UIntArray.getSize-impl(var0);
      var var3: Int = 0;

      var var2: Int;
      for (var2 = 0; var3 < var4; var3++) {
         var2 = UInt.constructor-impl(var2 + (var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var3))) as UInt).unbox-impl());
      }

      return var2;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun UShortArray.sumBy(selector: (UShort) -> UInt): UInt {
      val var4: Int = UShortArray.getSize-impl(var0);
      var var3: Int = 0;

      var var2: Int;
      for (var2 = 0; var3 < var4; var3++) {
         var2 = UInt.constructor-impl(var2 + (var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var3))) as UInt).unbox-impl());
      }

      return var2;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun UByteArray.sumByDouble(selector: (UByte) -> Double): Double {
      val var5: Int = UByteArray.getSize-impl(var0);
      var var2: Double = 0.0;

      for (int var4 = 0; var4 < var5; var4++) {
         var2 += (var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var4))) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun ULongArray.sumByDouble(selector: (ULong) -> Double): Double {
      val var5: Int = ULongArray.getSize-impl(var0);
      var var2: Double = 0.0;

      for (int var4 = 0; var4 < var5; var4++) {
         var2 += (var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var4))) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun UIntArray.sumByDouble(selector: (UInt) -> Double): Double {
      val var5: Int = UIntArray.getSize-impl(var0);
      var var2: Double = 0.0;

      for (int var4 = 0; var4 < var5; var4++) {
         var2 += (var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var4))) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @Deprecated(message = "Use sumOf instead.", replaceWith = @ReplaceWith(expression = "this.sumOf(selector)", imports = []))
   @DeprecatedSinceKotlin(warningSince = "1.5")
   @JvmStatic
   public inline fun UShortArray.sumByDouble(selector: (UShort) -> Double): Double {
      val var5: Int = UShortArray.getSize-impl(var0);
      var var2: Double = 0.0;

      for (int var4 = 0; var4 < var5; var4++) {
         var2 += (var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var4))) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @JvmStatic
   public inline fun UByteArray.sumOf(selector: (UByte) -> Double): Double {
      val var5: Int = UByteArray.getSize-impl(var0);
      var var2: Double = 0.0;

      for (int var4 = 0; var4 < var5; var4++) {
         var2 += (var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var4))) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @JvmStatic
   public inline fun UIntArray.sumOf(selector: (UInt) -> Double): Double {
      val var5: Int = UIntArray.getSize-impl(var0);
      var var2: Double = 0.0;

      for (int var4 = 0; var4 < var5; var4++) {
         var2 += (var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var4))) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @JvmStatic
   public inline fun ULongArray.sumOf(selector: (ULong) -> Double): Double {
      val var5: Int = ULongArray.getSize-impl(var0);
      var var2: Double = 0.0;

      for (int var4 = 0; var4 < var5; var4++) {
         var2 += (var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var4))) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @JvmStatic
   public inline fun UShortArray.sumOf(selector: (UShort) -> Double): Double {
      val var5: Int = UShortArray.getSize-impl(var0);
      var var2: Double = 0.0;

      for (int var4 = 0; var4 < var5; var4++) {
         var2 += (var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var4))) as java.lang.Number).doubleValue();
      }

      return var2;
   }

   @JvmStatic
   public inline fun UByteArray.sumOf(selector: (UByte) -> Int): Int {
      val var4: Int = UByteArray.getSize-impl(var0);
      var var3: Int = 0;

      var var2: Int;
      for (var2 = 0; var3 < var4; var3++) {
         var2 += (var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3))) as java.lang.Number).intValue();
      }

      return var2;
   }

   @JvmStatic
   public inline fun UIntArray.sumOf(selector: (UInt) -> Int): Int {
      val var4: Int = UIntArray.getSize-impl(var0);
      var var3: Int = 0;

      var var2: Int;
      for (var2 = 0; var3 < var4; var3++) {
         var2 += (var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var3))) as java.lang.Number).intValue();
      }

      return var2;
   }

   @JvmStatic
   public inline fun ULongArray.sumOf(selector: (ULong) -> Int): Int {
      val var4: Int = ULongArray.getSize-impl(var0);
      var var3: Int = 0;

      var var2: Int;
      for (var2 = 0; var3 < var4; var3++) {
         var2 += (var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var3))) as java.lang.Number).intValue();
      }

      return var2;
   }

   @JvmStatic
   public inline fun UShortArray.sumOf(selector: (UShort) -> Int): Int {
      val var4: Int = UShortArray.getSize-impl(var0);
      var var2: Int = 0;

      var var3: Int;
      for (var3 = 0; var2 < var4; var2++) {
         var3 += (var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var2))) as java.lang.Number).intValue();
      }

      return var3;
   }

   @JvmStatic
   public inline fun UByteArray.sumOf(selector: (UByte) -> Long): Long {
      val var3: Int = UByteArray.getSize-impl(var0);
      var var4: Long = 0L;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 += (var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var2))) as java.lang.Number).longValue();
      }

      return var4;
   }

   @JvmStatic
   public inline fun UIntArray.sumOf(selector: (UInt) -> Long): Long {
      val var3: Int = UIntArray.getSize-impl(var0);
      var var4: Long = 0L;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 += (var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2))) as java.lang.Number).longValue();
      }

      return var4;
   }

   @JvmStatic
   public inline fun ULongArray.sumOf(selector: (ULong) -> Long): Long {
      val var3: Int = ULongArray.getSize-impl(var0);
      var var4: Long = 0L;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 += (var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2))) as java.lang.Number).longValue();
      }

      return var4;
   }

   @JvmStatic
   public inline fun UShortArray.sumOf(selector: (UShort) -> Long): Long {
      val var3: Int = UShortArray.getSize-impl(var0);
      var var4: Long = 0L;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 += (var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var2))) as java.lang.Number).longValue();
      }

      return var4;
   }

   @JvmStatic
   public fun Array<out UByte>.sum(): UInt {
      val var3: Int = var0.length;
      var var2: Int = 0;

      var var1: Int;
      for (var1 = 0; var2 < var3; var2++) {
         var1 = UInt.constructor-impl(var1 + UInt.constructor-impl(var0[var2].unbox-impl() and 255));
      }

      return var1;
   }

   @JvmStatic
   public inline fun UByteArray.sumOf(selector: (UByte) -> UInt): UInt {
      var var2: Int = 0;
      var var3: Int = UInt.constructor-impl(0);

      for (int var4 = UByteArray.getSize-impl(var0); var2 < var4; var2++) {
         var3 = UInt.constructor-impl(var3 + (var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var2))) as UInt).unbox-impl());
      }

      return var3;
   }

   @JvmStatic
   public inline fun UIntArray.sumOf(selector: (UInt) -> UInt): UInt {
      var var2: Int = 0;
      var var3: Int = UInt.constructor-impl(0);

      for (int var4 = UIntArray.getSize-impl(var0); var2 < var4; var2++) {
         var3 = UInt.constructor-impl(var3 + (var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2))) as UInt).unbox-impl());
      }

      return var3;
   }

   @JvmStatic
   public inline fun ULongArray.sumOf(selector: (ULong) -> UInt): UInt {
      var var3: Int = 0;
      var var2: Int = UInt.constructor-impl(0);

      for (int var4 = ULongArray.getSize-impl(var0); var3 < var4; var3++) {
         var2 = UInt.constructor-impl(var2 + (var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var3))) as UInt).unbox-impl());
      }

      return var2;
   }

   @JvmStatic
   public fun Array<out UInt>.sum(): UInt {
      val var3: Int = var0.length;
      var var1: Int = 0;

      var var2: Int;
      for (var2 = 0; var1 < var3; var1++) {
         var2 = UInt.constructor-impl(var2 + var0[var1].unbox-impl());
      }

      return var2;
   }

   @JvmStatic
   public inline fun UShortArray.sumOf(selector: (UShort) -> UInt): UInt {
      var var2: Int = 0;
      var var3: Int = UInt.constructor-impl(0);

      for (int var4 = UShortArray.getSize-impl(var0); var2 < var4; var2++) {
         var3 = UInt.constructor-impl(var3 + (var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var2))) as UInt).unbox-impl());
      }

      return var3;
   }

   @JvmStatic
   public inline fun UByteArray.sumOf(selector: (UByte) -> ULong): ULong {
      var var4: Long = ULong.constructor-impl(0L);
      val var3: Int = UByteArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = ULong.constructor-impl(var4 + (var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var2))) as ULong).unbox-impl());
      }

      return var4;
   }

   @JvmStatic
   public inline fun UIntArray.sumOf(selector: (UInt) -> ULong): ULong {
      var var4: Long = ULong.constructor-impl(0L);
      val var3: Int = UIntArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = ULong.constructor-impl(var4 + (var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2))) as ULong).unbox-impl());
      }

      return var4;
   }

   @JvmStatic
   public inline fun ULongArray.sumOf(selector: (ULong) -> ULong): ULong {
      var var4: Long = ULong.constructor-impl(0L);
      val var3: Int = ULongArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = ULong.constructor-impl(var4 + (var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2))) as ULong).unbox-impl());
      }

      return var4;
   }

   @JvmStatic
   public fun Array<out ULong>.sum(): ULong {
      val var2: Int = var0.length;
      var var3: Long = 0L;

      for (int var1 = 0; var1 < var2; var1++) {
         var3 = ULong.constructor-impl(var3 + var0[var1].unbox-impl());
      }

      return var3;
   }

   @JvmStatic
   public inline fun UShortArray.sumOf(selector: (UShort) -> ULong): ULong {
      var var4: Long = ULong.constructor-impl(0L);
      val var3: Int = UShortArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = ULong.constructor-impl(var4 + (var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var2))) as ULong).unbox-impl());
      }

      return var4;
   }

   @JvmStatic
   public fun Array<out UShort>.sum(): UInt {
      val var3: Int = var0.length;
      var var2: Int = 0;

      var var1: Int;
      for (var1 = 0; var2 < var3; var2++) {
         var1 = UInt.constructor-impl(var1 + UInt.constructor-impl(var0[var2].unbox-impl() and '\uffff'));
      }

      return var1;
   }

   @JvmStatic
   public fun UByteArray.take(n: Int): List<UByte> {
      if (var1 < 0) {
         val var6: StringBuilder = new StringBuilder("Requested element count ");
         var6.append(var1);
         var6.append(" is less than zero.");
         throw new IllegalArgumentException(var6.toString().toString());
      } else if (var1 == 0) {
         return CollectionsKt.emptyList();
      } else if (var1 >= UByteArray.getSize-impl(var0)) {
         return CollectionsKt.toList(UByteArray.box-impl(var0));
      } else {
         var var3: Int = 0;
         if (var1 == 1) {
            return CollectionsKt.listOf(UByte.box-impl(UByteArray.get-w2LRezQ(var0, 0)));
         } else {
            val var5: ArrayList = new ArrayList(var1);
            val var4: Int = UByteArray.getSize-impl(var0);

            for (int var2 = 0; var3 < var4; var3++) {
               var5.add(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3)));
               if (++var2 == var1) {
                  break;
               }
            }

            return var5;
         }
      }
   }

   @JvmStatic
   public fun UShortArray.take(n: Int): List<UShort> {
      if (var1 < 0) {
         val var6: StringBuilder = new StringBuilder("Requested element count ");
         var6.append(var1);
         var6.append(" is less than zero.");
         throw new IllegalArgumentException(var6.toString().toString());
      } else if (var1 == 0) {
         return CollectionsKt.emptyList();
      } else if (var1 >= UShortArray.getSize-impl(var0)) {
         return CollectionsKt.toList(UShortArray.box-impl(var0));
      } else {
         var var2: Int = 0;
         if (var1 == 1) {
            return CollectionsKt.listOf(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, 0)));
         } else {
            val var5: ArrayList = new ArrayList(var1);
            val var4: Int = UShortArray.getSize-impl(var0);

            for (int var3 = 0; var2 < var4; var2++) {
               var5.add(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var2)));
               if (++var3 == var1) {
                  break;
               }
            }

            return var5;
         }
      }
   }

   @JvmStatic
   public fun UIntArray.take(n: Int): List<UInt> {
      if (var1 < 0) {
         val var6: StringBuilder = new StringBuilder("Requested element count ");
         var6.append(var1);
         var6.append(" is less than zero.");
         throw new IllegalArgumentException(var6.toString().toString());
      } else if (var1 == 0) {
         return CollectionsKt.emptyList();
      } else if (var1 >= UIntArray.getSize-impl(var0)) {
         return CollectionsKt.toList(UIntArray.box-impl(var0));
      } else {
         var var3: Int = 0;
         if (var1 == 1) {
            return CollectionsKt.listOf(UInt.box-impl(UIntArray.get-pVg5ArA(var0, 0)));
         } else {
            val var5: ArrayList = new ArrayList(var1);
            val var4: Int = UIntArray.getSize-impl(var0);

            for (int var2 = 0; var3 < var4; var3++) {
               var5.add(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var3)));
               if (++var2 == var1) {
                  break;
               }
            }

            return var5;
         }
      }
   }

   @JvmStatic
   public fun ULongArray.take(n: Int): List<ULong> {
      if (var1 < 0) {
         val var6: StringBuilder = new StringBuilder("Requested element count ");
         var6.append(var1);
         var6.append(" is less than zero.");
         throw new IllegalArgumentException(var6.toString().toString());
      } else if (var1 == 0) {
         return CollectionsKt.emptyList();
      } else if (var1 >= ULongArray.getSize-impl(var0)) {
         return CollectionsKt.toList(ULongArray.box-impl(var0));
      } else {
         var var2: Int = 0;
         if (var1 == 1) {
            return CollectionsKt.listOf(ULong.box-impl(ULongArray.get-s-VKNKU(var0, 0)));
         } else {
            val var5: ArrayList = new ArrayList(var1);
            val var4: Int = ULongArray.getSize-impl(var0);

            for (int var3 = 0; var2 < var4; var2++) {
               var5.add(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2)));
               if (++var3 == var1) {
                  break;
               }
            }

            return var5;
         }
      }
   }

   @JvmStatic
   public fun UByteArray.takeLast(n: Int): List<UByte> {
      if (var1 < 0) {
         val var4: StringBuilder = new StringBuilder("Requested element count ");
         var4.append(var1);
         var4.append(" is less than zero.");
         throw new IllegalArgumentException(var4.toString().toString());
      } else if (var1 == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var2: Int = UByteArray.getSize-impl(var0);
         if (var1 >= var2) {
            return CollectionsKt.toList(UByteArray.box-impl(var0));
         } else if (var1 == 1) {
            return CollectionsKt.listOf(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var2 - 1)));
         } else {
            val var3: ArrayList = new ArrayList(var1);

            for (int var5 = var2 - var1; var5 < var2; var5++) {
               var3.add(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var5)));
            }

            return var3;
         }
      }
   }

   @JvmStatic
   public fun UShortArray.takeLast(n: Int): List<UShort> {
      if (var1 < 0) {
         val var4: StringBuilder = new StringBuilder("Requested element count ");
         var4.append(var1);
         var4.append(" is less than zero.");
         throw new IllegalArgumentException(var4.toString().toString());
      } else if (var1 == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var2: Int = UShortArray.getSize-impl(var0);
         if (var1 >= var2) {
            return CollectionsKt.toList(UShortArray.box-impl(var0));
         } else if (var1 == 1) {
            return CollectionsKt.listOf(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var2 - 1)));
         } else {
            val var3: ArrayList = new ArrayList(var1);

            for (int var5 = var2 - var1; var5 < var2; var5++) {
               var3.add(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var5)));
            }

            return var3;
         }
      }
   }

   @JvmStatic
   public fun UIntArray.takeLast(n: Int): List<UInt> {
      if (var1 < 0) {
         val var4: StringBuilder = new StringBuilder("Requested element count ");
         var4.append(var1);
         var4.append(" is less than zero.");
         throw new IllegalArgumentException(var4.toString().toString());
      } else if (var1 == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var2: Int = UIntArray.getSize-impl(var0);
         if (var1 >= var2) {
            return CollectionsKt.toList(UIntArray.box-impl(var0));
         } else if (var1 == 1) {
            return CollectionsKt.listOf(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2 - 1)));
         } else {
            val var3: ArrayList = new ArrayList(var1);

            for (int var5 = var2 - var1; var5 < var2; var5++) {
               var3.add(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var5)));
            }

            return var3;
         }
      }
   }

   @JvmStatic
   public fun ULongArray.takeLast(n: Int): List<ULong> {
      if (var1 < 0) {
         val var4: StringBuilder = new StringBuilder("Requested element count ");
         var4.append(var1);
         var4.append(" is less than zero.");
         throw new IllegalArgumentException(var4.toString().toString());
      } else if (var1 == 0) {
         return CollectionsKt.emptyList();
      } else {
         val var2: Int = ULongArray.getSize-impl(var0);
         if (var1 >= var2) {
            return CollectionsKt.toList(ULongArray.box-impl(var0));
         } else if (var1 == 1) {
            return CollectionsKt.listOf(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2 - 1)));
         } else {
            val var3: ArrayList = new ArrayList(var1);

            for (int var5 = var2 - var1; var5 < var2; var5++) {
               var3.add(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var5)));
            }

            return var3;
         }
      }
   }

   @JvmStatic
   public inline fun UByteArray.takeLastWhile(predicate: (UByte) -> Boolean): List<UByte> {
      for (int var2 = ArraysKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var2))) as java.lang.Boolean) {
            return UArraysKt.drop-PpDY95g(var0, var2 + 1);
         }
      }

      return CollectionsKt.toList(UByteArray.box-impl(var0));
   }

   @JvmStatic
   public inline fun ULongArray.takeLastWhile(predicate: (ULong) -> Boolean): List<ULong> {
      for (int var2 = ArraysKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2))) as java.lang.Boolean) {
            return UArraysKt.drop-r7IrZao(var0, var2 + 1);
         }
      }

      return CollectionsKt.toList(ULongArray.box-impl(var0));
   }

   @JvmStatic
   public inline fun UIntArray.takeLastWhile(predicate: (UInt) -> Boolean): List<UInt> {
      for (int var2 = ArraysKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2))) as java.lang.Boolean) {
            return UArraysKt.drop-qFRl0hI(var0, var2 + 1);
         }
      }

      return CollectionsKt.toList(UIntArray.box-impl(var0));
   }

   @JvmStatic
   public inline fun UShortArray.takeLastWhile(predicate: (UShort) -> Boolean): List<UShort> {
      for (int var2 = ArraysKt.getLastIndex(var0); -1 < var2; var2--) {
         if (!var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var2))) as java.lang.Boolean) {
            return UArraysKt.drop-nggk6HY(var0, var2 + 1);
         }
      }

      return CollectionsKt.toList(UShortArray.box-impl(var0));
   }

   @JvmStatic
   public inline fun UByteArray.takeWhile(predicate: (UByte) -> Boolean): List<UByte> {
      val var5: ArrayList = new ArrayList();
      val var4: Int = UByteArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Byte = UByteArray.get-w2LRezQ(var0, var3);
         if (!var1.invoke(UByte.box-impl(var2)) as java.lang.Boolean) {
            break;
         }

         var5.add(UByte.box-impl(var2));
      }

      return var5;
   }

   @JvmStatic
   public inline fun ULongArray.takeWhile(predicate: (ULong) -> Boolean): List<ULong> {
      val var6: ArrayList = new ArrayList();
      val var3: Int = ULongArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Long = ULongArray.get-s-VKNKU(var0, var2);
         if (!var1.invoke(ULong.box-impl(var4)) as java.lang.Boolean) {
            break;
         }

         var6.add(ULong.box-impl(var4));
      }

      return var6;
   }

   @JvmStatic
   public inline fun UIntArray.takeWhile(predicate: (UInt) -> Boolean): List<UInt> {
      val var5: ArrayList = new ArrayList();
      val var3: Int = UIntArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         val var4: Int = UIntArray.get-pVg5ArA(var0, var2);
         if (!var1.invoke(UInt.box-impl(var4)) as java.lang.Boolean) {
            break;
         }

         var5.add(UInt.box-impl(var4));
      }

      return var5;
   }

   @JvmStatic
   public inline fun UShortArray.takeWhile(predicate: (UShort) -> Boolean): List<UShort> {
      val var5: ArrayList = new ArrayList();
      val var4: Int = UShortArray.getSize-impl(var0);

      for (int var3 = 0; var3 < var4; var3++) {
         val var2: Short = UShortArray.get-Mh2AYeg(var0, var3);
         if (!var1.invoke(UShort.box-impl(var2)) as java.lang.Boolean) {
            break;
         }

         var5.add(UShort.box-impl(var2));
      }

      return var5;
   }

   @JvmStatic
   public inline fun UByteArray.toByteArray(): ByteArray {
      var0 = Arrays.copyOf(var0, var0.length);
      return var0;
   }

   @JvmStatic
   public inline fun UIntArray.toIntArray(): IntArray {
      var0 = Arrays.copyOf(var0, var0.length);
      return var0;
   }

   @JvmStatic
   public inline fun ULongArray.toLongArray(): LongArray {
      var0 = Arrays.copyOf(var0, var0.length);
      return var0;
   }

   @JvmStatic
   public inline fun UShortArray.toShortArray(): ShortArray {
      var0 = Arrays.copyOf(var0, var0.length);
      return var0;
   }

   @JvmStatic
   public fun UIntArray.toTypedArray(): Array<UInt> {
      val var2: Int = UIntArray.getSize-impl(var0);
      val var3: Array<UInt> = new UInt[var2];

      for (int var1 = 0; var1 < var2; var1++) {
         var3[var1] = UInt.box-impl(UIntArray.get-pVg5ArA(var0, var1));
      }

      return var3;
   }

   @JvmStatic
   public fun UByteArray.toTypedArray(): Array<UByte> {
      val var2: Int = UByteArray.getSize-impl(var0);
      val var3: Array<UByte> = new UByte[var2];

      for (int var1 = 0; var1 < var2; var1++) {
         var3[var1] = UByte.box-impl(UByteArray.get-w2LRezQ(var0, var1));
      }

      return var3;
   }

   @JvmStatic
   public fun ULongArray.toTypedArray(): Array<ULong> {
      val var2: Int = ULongArray.getSize-impl(var0);
      val var3: Array<ULong> = new ULong[var2];

      for (int var1 = 0; var1 < var2; var1++) {
         var3[var1] = ULong.box-impl(ULongArray.get-s-VKNKU(var0, var1));
      }

      return var3;
   }

   @JvmStatic
   public fun UShortArray.toTypedArray(): Array<UShort> {
      val var2: Int = UShortArray.getSize-impl(var0);
      val var3: Array<UShort> = new UShort[var2];

      for (int var1 = 0; var1 < var2; var1++) {
         var3[var1] = UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var1));
      }

      return var3;
   }

   @JvmStatic
   public inline fun ByteArray.toUByteArray(): UByteArray {
      var0 = Arrays.copyOf(var0, var0.length);
      return UByteArray.constructor-impl(var0);
   }

   @JvmStatic
   public fun Array<out UByte>.toUByteArray(): UByteArray {
      val var2: Int = var0.length;
      val var3: ByteArray = new byte[var0.length];

      for (int var1 = 0; var1 < var2; var1++) {
         var3[var1] = var0[var1].unbox-impl();
      }

      return UByteArray.constructor-impl(var3);
   }

   @JvmStatic
   public inline fun IntArray.toUIntArray(): UIntArray {
      var0 = Arrays.copyOf(var0, var0.length);
      return UIntArray.constructor-impl(var0);
   }

   @JvmStatic
   public fun Array<out UInt>.toUIntArray(): UIntArray {
      val var2: Int = var0.length;
      val var3: IntArray = new int[var0.length];

      for (int var1 = 0; var1 < var2; var1++) {
         var3[var1] = var0[var1].unbox-impl();
      }

      return UIntArray.constructor-impl(var3);
   }

   @JvmStatic
   public inline fun LongArray.toULongArray(): ULongArray {
      var0 = Arrays.copyOf(var0, var0.length);
      return ULongArray.constructor-impl(var0);
   }

   @JvmStatic
   public fun Array<out ULong>.toULongArray(): ULongArray {
      val var2: Int = var0.length;
      val var3: LongArray = new long[var0.length];

      for (int var1 = 0; var1 < var2; var1++) {
         var3[var1] = var0[var1].unbox-impl();
      }

      return ULongArray.constructor-impl(var3);
   }

   @JvmStatic
   public fun Array<out UShort>.toUShortArray(): UShortArray {
      val var2: Int = var0.length;
      val var3: ShortArray = new short[var0.length];

      for (int var1 = 0; var1 < var2; var1++) {
         var3[var1] = var0[var1].unbox-impl();
      }

      return UShortArray.constructor-impl(var3);
   }

   @JvmStatic
   public inline fun ShortArray.toUShortArray(): UShortArray {
      var0 = Arrays.copyOf(var0, var0.length);
      return UShortArray.constructor-impl(var0);
   }

   @JvmStatic
   public fun UIntArray.withIndex(): Iterable<IndexedValue<UInt>> {
      return new IndexingIterable<>(new UArraysKt___UArraysKt$$ExternalSyntheticLambda0(var0));
   }

   @JvmStatic
   public fun UByteArray.withIndex(): Iterable<IndexedValue<UByte>> {
      return new IndexingIterable<>(new UArraysKt___UArraysKt$$ExternalSyntheticLambda2(var0));
   }

   @JvmStatic
   public fun ULongArray.withIndex(): Iterable<IndexedValue<ULong>> {
      return new IndexingIterable<>(new UArraysKt___UArraysKt$$ExternalSyntheticLambda1(var0));
   }

   @JvmStatic
   public fun UShortArray.withIndex(): Iterable<IndexedValue<UShort>> {
      return new IndexingIterable<>(new UArraysKt___UArraysKt$$ExternalSyntheticLambda3(var0));
   }

   @JvmStatic
   fun `withIndex_GBYM_sE$lambda$58$UArraysKt___UArraysKt`(var0: ByteArray): java.util.Iterator {
      return UByteArray.iterator-impl(var0);
   }

   @JvmStatic
   fun `withIndex_QwZRm1k$lambda$57$UArraysKt___UArraysKt`(var0: LongArray): java.util.Iterator {
      return ULongArray.iterator-impl(var0);
   }

   @JvmStatic
   fun `withIndex__ajY_9A$lambda$56$UArraysKt___UArraysKt`(var0: IntArray): java.util.Iterator {
      return UIntArray.iterator-impl(var0);
   }

   @JvmStatic
   fun `withIndex_rL5Bavg$lambda$59$UArraysKt___UArraysKt`(var0: ShortArray): java.util.Iterator {
      return UShortArray.iterator-impl(var0);
   }

   @JvmStatic
   public inline fun <R, V> UIntArray.zip(other: Iterable<R>, transform: (UInt, R) -> V): List<V> {
      val var4: Int = UIntArray.getSize-impl(var0);
      val var5: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var1, 10), var4));
      val var6: java.util.Iterator = var1.iterator();

      for (int var3 = 0; var6.hasNext(); var3++) {
         val var7: Any = var6.next();
         if (var3 >= var4) {
            break;
         }

         var5.add(var2.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var3)), var7));
      }

      return var5;
   }

   @JvmStatic
   public inline fun <R, V> ULongArray.zip(other: Array<out R>, transform: (ULong, R) -> V): List<V> {
      val var4: Int = Math.min(ULongArray.getSize-impl(var0), var1.length);
      val var5: ArrayList = new ArrayList(var4);

      for (int var3 = 0; var3 < var4; var3++) {
         var5.add(var2.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var3)), var1[var3]));
      }

      return var5;
   }

   @JvmStatic
   public infix fun <R> UIntArray.zip(other: Array<out R>): List<Pair<UInt, R>> {
      val var3: Int = Math.min(UIntArray.getSize-impl(var0), var1.length);
      val var6: ArrayList = new ArrayList(var3);

      for (int var2 = 0; var2 < var3; var2++) {
         var6.add(TuplesKt.to(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2)), var1[var2]));
      }

      return var6;
   }

   @JvmStatic
   public infix fun <R> ULongArray.zip(other: Iterable<R>): List<Pair<ULong, R>> {
      val var3: Int = ULongArray.getSize-impl(var0);
      val var4: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var1, 10), var3));
      val var5: java.util.Iterator = var1.iterator();

      for (int var2 = 0; var5.hasNext(); var2++) {
         val var6: Any = var5.next();
         if (var2 >= var3) {
            break;
         }

         var4.add(TuplesKt.to(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2)), var6));
      }

      return var4;
   }

   @JvmStatic
   public infix fun <R> UIntArray.zip(other: Iterable<R>): List<Pair<UInt, R>> {
      val var3: Int = UIntArray.getSize-impl(var0);
      val var4: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var1, 10), var3));
      val var5: java.util.Iterator = var1.iterator();

      for (int var2 = 0; var5.hasNext(); var2++) {
         val var6: Any = var5.next();
         if (var2 >= var3) {
            break;
         }

         var4.add(TuplesKt.to(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2)), var6));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <V> UByteArray.zip(other: UByteArray, transform: (UByte, UByte) -> V): List<V> {
      val var4: Int = Math.min(UByteArray.getSize-impl(var0), UByteArray.getSize-impl(var1));
      val var5: ArrayList = new ArrayList(var4);

      for (int var3 = 0; var3 < var4; var3++) {
         var5.add(var2.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3)), UByte.box-impl(UByteArray.get-w2LRezQ(var1, var3))));
      }

      return var5;
   }

   @JvmStatic
   public infix fun <R> UShortArray.zip(other: Iterable<R>): List<Pair<UShort, R>> {
      val var3: Int = UShortArray.getSize-impl(var0);
      val var4: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var1, 10), var3));
      val var6: java.util.Iterator = var1.iterator();

      for (int var2 = 0; var6.hasNext(); var2++) {
         val var5: Any = var6.next();
         if (var2 >= var3) {
            break;
         }

         var4.add(TuplesKt.to(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var2)), var5));
      }

      return var4;
   }

   @JvmStatic
   public infix fun <R> UByteArray.zip(other: Iterable<R>): List<Pair<UByte, R>> {
      val var3: Int = UByteArray.getSize-impl(var0);
      val var4: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var1, 10), var3));
      val var6: java.util.Iterator = var1.iterator();

      for (int var2 = 0; var6.hasNext(); var2++) {
         val var5: Any = var6.next();
         if (var2 >= var3) {
            break;
         }

         var4.add(TuplesKt.to(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var2)), var5));
      }

      return var4;
   }

   @JvmStatic
   public inline fun <V> UIntArray.zip(other: UIntArray, transform: (UInt, UInt) -> V): List<V> {
      val var4: Int = Math.min(UIntArray.getSize-impl(var0), UIntArray.getSize-impl(var1));
      val var5: ArrayList = new ArrayList(var4);

      for (int var3 = 0; var3 < var4; var3++) {
         var5.add(var2.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var3)), UInt.box-impl(UIntArray.get-pVg5ArA(var1, var3))));
      }

      return var5;
   }

   @JvmStatic
   public inline fun <R, V> UByteArray.zip(other: Array<out R>, transform: (UByte, R) -> V): List<V> {
      val var4: Int = Math.min(UByteArray.getSize-impl(var0), var1.length);
      val var5: ArrayList = new ArrayList(var4);

      for (int var3 = 0; var3 < var4; var3++) {
         var5.add(var2.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3)), var1[var3]));
      }

      return var5;
   }

   @JvmStatic
   public inline fun <V> ULongArray.zip(other: ULongArray, transform: (ULong, ULong) -> V): List<V> {
      val var4: Int = Math.min(ULongArray.getSize-impl(var0), ULongArray.getSize-impl(var1));
      val var5: ArrayList = new ArrayList(var4);

      for (int var3 = 0; var3 < var4; var3++) {
         var5.add(var2.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var3)), ULong.box-impl(ULongArray.get-s-VKNKU(var1, var3))));
      }

      return var5;
   }

   @JvmStatic
   public inline fun <R, V> ULongArray.zip(other: Iterable<R>, transform: (ULong, R) -> V): List<V> {
      val var4: Int = ULongArray.getSize-impl(var0);
      val var5: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var1, 10), var4));
      val var6: java.util.Iterator = var1.iterator();

      for (int var3 = 0; var6.hasNext(); var3++) {
         val var7: Any = var6.next();
         if (var3 >= var4) {
            break;
         }

         var5.add(var2.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var3)), var7));
      }

      return var5;
   }

   @JvmStatic
   public inline fun <R, V> UByteArray.zip(other: Iterable<R>, transform: (UByte, R) -> V): List<V> {
      val var4: Int = UByteArray.getSize-impl(var0);
      val var5: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var1, 10), var4));
      val var6: java.util.Iterator = var1.iterator();

      for (int var3 = 0; var6.hasNext(); var3++) {
         val var7: Any = var6.next();
         if (var3 >= var4) {
            break;
         }

         var5.add(var2.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3)), var7));
      }

      return var5;
   }

   @JvmStatic
   public inline fun <R, V> UIntArray.zip(other: Array<out R>, transform: (UInt, R) -> V): List<V> {
      val var4: Int = Math.min(UIntArray.getSize-impl(var0), var1.length);
      val var5: ArrayList = new ArrayList(var4);

      for (int var3 = 0; var3 < var4; var3++) {
         var5.add(var2.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var3)), var1[var3]));
      }

      return var5;
   }

   @JvmStatic
   public infix fun UIntArray.zip(other: UIntArray): List<Pair<UInt, UInt>> {
      val var3: Int = Math.min(UIntArray.getSize-impl(var0), UIntArray.getSize-impl(var1));
      val var6: ArrayList = new ArrayList(var3);

      for (int var2 = 0; var2 < var3; var2++) {
         var6.add(TuplesKt.to(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2)), UInt.box-impl(UIntArray.get-pVg5ArA(var1, var2))));
      }

      return var6;
   }

   @JvmStatic
   public inline fun <R, V> UShortArray.zip(other: Array<out R>, transform: (UShort, R) -> V): List<V> {
      val var4: Int = Math.min(UShortArray.getSize-impl(var0), var1.length);
      val var5: ArrayList = new ArrayList(var4);

      for (int var3 = 0; var3 < var4; var3++) {
         var5.add(var2.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var3)), var1[var3]));
      }

      return var5;
   }

   @JvmStatic
   public infix fun <R> ULongArray.zip(other: Array<out R>): List<Pair<ULong, R>> {
      val var3: Int = Math.min(ULongArray.getSize-impl(var0), var1.length);
      val var7: ArrayList = new ArrayList(var3);

      for (int var2 = 0; var2 < var3; var2++) {
         var7.add(TuplesKt.to(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2)), var1[var2]));
      }

      return var7;
   }

   @JvmStatic
   public inline fun <V> UShortArray.zip(other: UShortArray, transform: (UShort, UShort) -> V): List<V> {
      val var4: Int = Math.min(UShortArray.getSize-impl(var0), UShortArray.getSize-impl(var1));
      val var5: ArrayList = new ArrayList(var4);

      for (int var3 = 0; var3 < var4; var3++) {
         var5.add(var2.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var3)), UShort.box-impl(UShortArray.get-Mh2AYeg(var1, var3))));
      }

      return var5;
   }

   @JvmStatic
   public inline fun <R, V> UShortArray.zip(other: Iterable<R>, transform: (UShort, R) -> V): List<V> {
      val var4: Int = UShortArray.getSize-impl(var0);
      val var5: ArrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(var1, 10), var4));
      val var7: java.util.Iterator = var1.iterator();

      for (int var3 = 0; var7.hasNext(); var3++) {
         val var6: Any = var7.next();
         if (var3 >= var4) {
            break;
         }

         var5.add(var2.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var3)), var6));
      }

      return var5;
   }

   @JvmStatic
   public infix fun UByteArray.zip(other: UByteArray): List<Pair<UByte, UByte>> {
      val var5: Int = Math.min(UByteArray.getSize-impl(var0), UByteArray.getSize-impl(var1));
      val var6: ArrayList = new ArrayList(var5);

      for (int var4 = 0; var4 < var5; var4++) {
         var6.add(TuplesKt.to(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var4)), UByte.box-impl(UByteArray.get-w2LRezQ(var1, var4))));
      }

      return var6;
   }

   @JvmStatic
   public infix fun UShortArray.zip(other: UShortArray): List<Pair<UShort, UShort>> {
      val var5: Int = Math.min(UShortArray.getSize-impl(var0), UShortArray.getSize-impl(var1));
      val var6: ArrayList = new ArrayList(var5);

      for (int var4 = 0; var4 < var5; var4++) {
         var6.add(TuplesKt.to(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var4)), UShort.box-impl(UShortArray.get-Mh2AYeg(var1, var4))));
      }

      return var6;
   }

   @JvmStatic
   public infix fun <R> UByteArray.zip(other: Array<out R>): List<Pair<UByte, R>> {
      val var4: Int = Math.min(UByteArray.getSize-impl(var0), var1.length);
      val var6: ArrayList = new ArrayList(var4);

      for (int var3 = 0; var3 < var4; var3++) {
         var6.add(TuplesKt.to(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var3)), var1[var3]));
      }

      return var6;
   }

   @JvmStatic
   public infix fun <R> UShortArray.zip(other: Array<out R>): List<Pair<UShort, R>> {
      val var4: Int = Math.min(UShortArray.getSize-impl(var0), var1.length);
      val var6: ArrayList = new ArrayList(var4);

      for (int var3 = 0; var3 < var4; var3++) {
         var6.add(TuplesKt.to(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var3)), var1[var3]));
      }

      return var6;
   }

   @JvmStatic
   public infix fun ULongArray.zip(other: ULongArray): List<Pair<ULong, ULong>> {
      val var3: Int = Math.min(ULongArray.getSize-impl(var0), ULongArray.getSize-impl(var1));
      val var8: ArrayList = new ArrayList(var3);

      for (int var2 = 0; var2 < var3; var2++) {
         var8.add(TuplesKt.to(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2)), ULong.box-impl(ULongArray.get-s-VKNKU(var1, var2))));
      }

      return var8;
   }
}
