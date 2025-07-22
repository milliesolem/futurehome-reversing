package kotlin.collections

import java.math.BigDecimal
import java.math.BigInteger
import java.util.ArrayList
import java.util.Arrays
import java.util.Comparator
import java.util.RandomAccess
import java.util.SortedSet
import java.util.TreeSet
import kotlin.internal.PlatformImplementationsKt

internal class ArraysKt___ArraysJvmKt : ArraysKt__ArraysKt {
   open fun ArraysKt___ArraysJvmKt() {
   }

   @JvmStatic
   public fun ByteArray.asList(): List<Byte> {
      return new RandomAccess(var0) {
         final byte[] $this_asList;

         {
            this.$this_asList = var1;
         }

         public boolean contains(byte var1) {
            return ArraysKt.contains(this.$this_asList, var1);
         }

         public java.lang.Byte get(int var1) {
            return this.$this_asList[var1];
         }

         @Override
         public int getSize() {
            return this.$this_asList.length;
         }

         public int indexOf(byte var1) {
            return ArraysKt.indexOf(this.$this_asList, var1);
         }

         @Override
         public boolean isEmpty() {
            val var1: Boolean;
            if (this.$this_asList.length == 0) {
               var1 = true;
            } else {
               var1 = false;
            }

            return var1;
         }

         public int lastIndexOf(byte var1) {
            return ArraysKt.lastIndexOf(this.$this_asList, var1);
         }
      };
   }

   @JvmStatic
   public fun CharArray.asList(): List<Char> {
      return new RandomAccess(var0) {
         final char[] $this_asList;

         {
            this.$this_asList = var1;
         }

         public boolean contains(char var1) {
            return ArraysKt.contains(this.$this_asList, var1);
         }

         public Character get(int var1) {
            return this.$this_asList[var1];
         }

         @Override
         public int getSize() {
            return this.$this_asList.length;
         }

         public int indexOf(char var1) {
            return ArraysKt.indexOf(this.$this_asList, var1);
         }

         @Override
         public boolean isEmpty() {
            val var1: Boolean;
            if (this.$this_asList.length == 0) {
               var1 = true;
            } else {
               var1 = false;
            }

            return var1;
         }

         public int lastIndexOf(char var1) {
            return ArraysKt.lastIndexOf(this.$this_asList, var1);
         }
      };
   }

   @JvmStatic
   public fun DoubleArray.asList(): List<Double> {
      return new RandomAccess(var0) {
         final double[] $this_asList;

         {
            this.$this_asList = var1;
         }

         public boolean contains(double var1) {
            val var7: DoubleArray = this.$this_asList;
            val var4: Int = this.$this_asList.length;
            val var6: Boolean = false;
            var var3: Int = 0;

            var var5: Boolean;
            while (true) {
               var5 = var6;
               if (var3 >= var4) {
                  break;
               }

               if (java.lang.Double.doubleToLongBits(var7[var3]) == java.lang.Double.doubleToLongBits(var1)) {
                  var5 = true;
                  break;
               }

               var3++;
            }

            return var5;
         }

         public java.lang.Double get(int var1) {
            return this.$this_asList[var1];
         }

         @Override
         public int getSize() {
            return this.$this_asList.length;
         }

         public int indexOf(double var1) {
            val var5: DoubleArray = this.$this_asList;
            val var4: Int = this.$this_asList.length;
            var var3: Int = 0;

            while (true) {
               if (var3 >= var4) {
                  var3 = -1;
                  break;
               }

               if (java.lang.Double.doubleToLongBits(var5[var3]) == java.lang.Double.doubleToLongBits(var1)) {
                  break;
               }

               var3++;
            }

            return var3;
         }

         @Override
         public boolean isEmpty() {
            val var1: Boolean;
            if (this.$this_asList.length == 0) {
               var1 = true;
            } else {
               var1 = false;
            }

            return var1;
         }

         public int lastIndexOf(double var1) {
            val var6: DoubleArray = this.$this_asList;
            var var5: Int = this.$this_asList.length - 1;
            var var7: Int = -1;
            if (var5 >= 0) {
               var7 = var5;

               while (true) {
                  var5 = var7 - 1;
                  if (java.lang.Double.doubleToLongBits(var6[var7]) == java.lang.Double.doubleToLongBits(var1)) {
                     break;
                  }

                  if (var5 < 0) {
                     var7 = -1;
                     break;
                  }

                  var7 = var5;
               }
            }

            return var7;
         }
      };
   }

   @JvmStatic
   public fun FloatArray.asList(): List<Float> {
      return new RandomAccess(var0) {
         final float[] $this_asList;

         {
            this.$this_asList = var1;
         }

         public boolean contains(float var1) {
            val var6: FloatArray = this.$this_asList;
            val var3: Int = this.$this_asList.length;
            val var5: Boolean = false;
            var var2: Int = 0;

            var var4: Boolean;
            while (true) {
               var4 = var5;
               if (var2 >= var3) {
                  break;
               }

               if (java.lang.Float.floatToIntBits(var6[var2]) == java.lang.Float.floatToIntBits(var1)) {
                  var4 = true;
                  break;
               }

               var2++;
            }

            return var4;
         }

         public java.lang.Float get(int var1) {
            return this.$this_asList[var1];
         }

         @Override
         public int getSize() {
            return this.$this_asList.length;
         }

         public int indexOf(float var1) {
            val var4: FloatArray = this.$this_asList;
            val var3: Int = this.$this_asList.length;
            var var2: Int = 0;

            while (true) {
               if (var2 >= var3) {
                  var2 = -1;
                  break;
               }

               if (java.lang.Float.floatToIntBits(var4[var2]) == java.lang.Float.floatToIntBits(var1)) {
                  break;
               }

               var2++;
            }

            return var2;
         }

         @Override
         public boolean isEmpty() {
            val var1: Boolean;
            if (this.$this_asList.length == 0) {
               var1 = true;
            } else {
               var1 = false;
            }

            return var1;
         }

         public int lastIndexOf(float var1) {
            val var5: FloatArray = this.$this_asList;
            var var4: Int = this.$this_asList.length - 1;
            var var6: Int = -1;
            if (var4 >= 0) {
               var6 = var4;

               while (true) {
                  var4 = var6 - 1;
                  if (java.lang.Float.floatToIntBits(var5[var6]) == java.lang.Float.floatToIntBits(var1)) {
                     break;
                  }

                  if (var4 < 0) {
                     var6 = -1;
                     break;
                  }

                  var6 = var4;
               }
            }

            return var6;
         }
      };
   }

   @JvmStatic
   public fun IntArray.asList(): List<Int> {
      return new RandomAccess(var0) {
         final int[] $this_asList;

         {
            this.$this_asList = var1;
         }

         public boolean contains(int var1) {
            return ArraysKt.contains(this.$this_asList, var1);
         }

         public Integer get(int var1) {
            return this.$this_asList[var1];
         }

         @Override
         public int getSize() {
            return this.$this_asList.length;
         }

         public int indexOf(int var1) {
            return ArraysKt.indexOf(this.$this_asList, var1);
         }

         @Override
         public boolean isEmpty() {
            val var1: Boolean;
            if (this.$this_asList.length == 0) {
               var1 = true;
            } else {
               var1 = false;
            }

            return var1;
         }

         public int lastIndexOf(int var1) {
            return ArraysKt.lastIndexOf(this.$this_asList, var1);
         }
      };
   }

   @JvmStatic
   public fun LongArray.asList(): List<Long> {
      return new RandomAccess(var0) {
         final long[] $this_asList;

         {
            this.$this_asList = var1;
         }

         public boolean contains(long var1) {
            return ArraysKt.contains(this.$this_asList, var1);
         }

         public java.lang.Long get(int var1) {
            return this.$this_asList[var1];
         }

         @Override
         public int getSize() {
            return this.$this_asList.length;
         }

         public int indexOf(long var1) {
            return ArraysKt.indexOf(this.$this_asList, var1);
         }

         @Override
         public boolean isEmpty() {
            val var1: Boolean;
            if (this.$this_asList.length == 0) {
               var1 = true;
            } else {
               var1 = false;
            }

            return var1;
         }

         public int lastIndexOf(long var1) {
            return ArraysKt.lastIndexOf(this.$this_asList, var1);
         }
      };
   }

   @JvmStatic
   public fun <T> Array<out T>.asList(): List<T> {
      val var1: java.util.List = ArraysUtilJVM.asList(var0);
      return var1;
   }

   @JvmStatic
   public fun ShortArray.asList(): List<Short> {
      return new RandomAccess(var0) {
         final short[] $this_asList;

         {
            this.$this_asList = var1;
         }

         public boolean contains(short var1) {
            return ArraysKt.contains(this.$this_asList, var1);
         }

         public java.lang.Short get(int var1) {
            return this.$this_asList[var1];
         }

         @Override
         public int getSize() {
            return this.$this_asList.length;
         }

         public int indexOf(short var1) {
            return ArraysKt.indexOf(this.$this_asList, var1);
         }

         @Override
         public boolean isEmpty() {
            val var1: Boolean;
            if (this.$this_asList.length == 0) {
               var1 = true;
            } else {
               var1 = false;
            }

            return var1;
         }

         public int lastIndexOf(short var1) {
            return ArraysKt.lastIndexOf(this.$this_asList, var1);
         }
      };
   }

   @JvmStatic
   public fun BooleanArray.asList(): List<Boolean> {
      return new RandomAccess(var0) {
         final boolean[] $this_asList;

         {
            this.$this_asList = var1;
         }

         public boolean contains(boolean var1) {
            return ArraysKt.contains(this.$this_asList, var1);
         }

         public java.lang.Boolean get(int var1) {
            return this.$this_asList[var1];
         }

         @Override
         public int getSize() {
            return this.$this_asList.length;
         }

         public int indexOf(boolean var1) {
            return ArraysKt.indexOf(this.$this_asList, var1);
         }

         @Override
         public boolean isEmpty() {
            val var1: Boolean;
            if (this.$this_asList.length == 0) {
               var1 = true;
            } else {
               var1 = false;
            }

            return var1;
         }

         public int lastIndexOf(boolean var1) {
            return ArraysKt.lastIndexOf(this.$this_asList, var1);
         }
      };
   }

   @JvmStatic
   public fun ByteArray.binarySearch(element: Byte, fromIndex: Int = 0, toIndex: Int = var0.length): Int {
      return Arrays.binarySearch(var0, var2, var3, var1);
   }

   @JvmStatic
   public fun CharArray.binarySearch(element: Char, fromIndex: Int = 0, toIndex: Int = var0.length): Int {
      return Arrays.binarySearch(var0, var2, var3, var1);
   }

   @JvmStatic
   public fun DoubleArray.binarySearch(element: Double, fromIndex: Int = 0, toIndex: Int = var0.length): Int {
      return Arrays.binarySearch(var0, var3, var4, var1);
   }

   @JvmStatic
   public fun FloatArray.binarySearch(element: Float, fromIndex: Int = 0, toIndex: Int = var0.length): Int {
      return Arrays.binarySearch(var0, var2, var3, var1);
   }

   @JvmStatic
   public fun IntArray.binarySearch(element: Int, fromIndex: Int = 0, toIndex: Int = var0.length): Int {
      return Arrays.binarySearch(var0, var2, var3, var1);
   }

   @JvmStatic
   public fun LongArray.binarySearch(element: Long, fromIndex: Int = 0, toIndex: Int = var0.length): Int {
      return Arrays.binarySearch(var0, var3, var4, var1);
   }

   @JvmStatic
   public fun <T> Array<out T>.binarySearch(element: T, fromIndex: Int = 0, toIndex: Int = var0.length): Int {
      return Arrays.binarySearch(var0, var2, var3, var1);
   }

   @JvmStatic
   public fun <T> Array<out T>.binarySearch(element: T, comparator: Comparator<in T>, fromIndex: Int = 0, toIndex: Int = var0.length): Int {
      return Arrays.binarySearch(var0, var3, var4, var1, var2);
   }

   @JvmStatic
   public fun ShortArray.binarySearch(element: Short, fromIndex: Int = 0, toIndex: Int = var0.length): Int {
      return Arrays.binarySearch(var0, var2, var3, var1);
   }

   @JvmStatic
   public inline infix fun <T> Array<out T>.contentDeepEquals(other: Array<out T>): Boolean {
      val var2: Boolean;
      if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
         var2 = ArraysKt.contentDeepEquals(var0, var1);
      } else {
         var2 = Arrays.deepEquals(var0, var1);
      }

      return var2;
   }

   @JvmStatic
   public inline infix fun <T> Array<out T>?.contentDeepEquals(other: Array<out T>?): Boolean {
      return if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) ArraysKt.contentDeepEquals(var0, var1) else Arrays.deepEquals(var0, var1);
   }

   @JvmStatic
   public inline fun <T> Array<out T>.contentDeepHashCode(): Int {
      val var1: Int;
      if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
         var1 = ArraysKt.contentDeepHashCode(var0);
      } else {
         var1 = Arrays.deepHashCode(var0);
      }

      return var1;
   }

   @JvmStatic
   public inline fun <T> Array<out T>?.contentDeepHashCode(): Int {
      return if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) ArraysKt.contentDeepHashCode(var0) else Arrays.deepHashCode(var0);
   }

   @JvmStatic
   public inline fun <T> Array<out T>.contentDeepToString(): String {
      val var1: java.lang.String;
      if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
         var1 = ArraysKt.contentDeepToString(var0);
      } else {
         var1 = Arrays.deepToString(var0);
      }

      return var1;
   }

   @JvmStatic
   public inline fun <T> Array<out T>?.contentDeepToString(): String {
      if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
         return ArraysKt.contentDeepToString(var0);
      } else {
         val var1: java.lang.String = Arrays.deepToString(var0);
         return var1;
      }
   }

   @JvmStatic
   public inline infix fun ByteArray?.contentEquals(other: ByteArray?): Boolean {
      return Arrays.equals(var0, var1);
   }

   @JvmStatic
   public inline infix fun CharArray?.contentEquals(other: CharArray?): Boolean {
      return Arrays.equals(var0, var1);
   }

   @JvmStatic
   public inline infix fun DoubleArray?.contentEquals(other: DoubleArray?): Boolean {
      return Arrays.equals(var0, var1);
   }

   @JvmStatic
   public inline infix fun FloatArray?.contentEquals(other: FloatArray?): Boolean {
      return Arrays.equals(var0, var1);
   }

   @JvmStatic
   public inline infix fun IntArray?.contentEquals(other: IntArray?): Boolean {
      return Arrays.equals(var0, var1);
   }

   @JvmStatic
   public inline infix fun LongArray?.contentEquals(other: LongArray?): Boolean {
      return Arrays.equals(var0, var1);
   }

   @JvmStatic
   public inline infix fun <T> Array<out T>?.contentEquals(other: Array<out T>?): Boolean {
      return Arrays.equals(var0, var1);
   }

   @JvmStatic
   public inline infix fun ShortArray?.contentEquals(other: ShortArray?): Boolean {
      return Arrays.equals(var0, var1);
   }

   @JvmStatic
   public inline infix fun BooleanArray?.contentEquals(other: BooleanArray?): Boolean {
      return Arrays.equals(var0, var1);
   }

   @JvmStatic
   public inline fun ByteArray?.contentHashCode(): Int {
      return Arrays.hashCode(var0);
   }

   @JvmStatic
   public inline fun CharArray?.contentHashCode(): Int {
      return Arrays.hashCode(var0);
   }

   @JvmStatic
   public inline fun DoubleArray?.contentHashCode(): Int {
      return Arrays.hashCode(var0);
   }

   @JvmStatic
   public inline fun FloatArray?.contentHashCode(): Int {
      return Arrays.hashCode(var0);
   }

   @JvmStatic
   public inline fun IntArray?.contentHashCode(): Int {
      return Arrays.hashCode(var0);
   }

   @JvmStatic
   public inline fun LongArray?.contentHashCode(): Int {
      return Arrays.hashCode(var0);
   }

   @JvmStatic
   public inline fun <T> Array<out T>?.contentHashCode(): Int {
      return Arrays.hashCode(var0);
   }

   @JvmStatic
   public inline fun ShortArray?.contentHashCode(): Int {
      return Arrays.hashCode(var0);
   }

   @JvmStatic
   public inline fun BooleanArray?.contentHashCode(): Int {
      return Arrays.hashCode(var0);
   }

   @JvmStatic
   public inline fun ByteArray?.contentToString(): String {
      val var1: java.lang.String = Arrays.toString(var0);
      return var1;
   }

   @JvmStatic
   public inline fun CharArray?.contentToString(): String {
      val var1: java.lang.String = Arrays.toString(var0);
      return var1;
   }

   @JvmStatic
   public inline fun DoubleArray?.contentToString(): String {
      val var1: java.lang.String = Arrays.toString(var0);
      return var1;
   }

   @JvmStatic
   public inline fun FloatArray?.contentToString(): String {
      val var1: java.lang.String = Arrays.toString(var0);
      return var1;
   }

   @JvmStatic
   public inline fun IntArray?.contentToString(): String {
      val var1: java.lang.String = Arrays.toString(var0);
      return var1;
   }

   @JvmStatic
   public inline fun LongArray?.contentToString(): String {
      val var1: java.lang.String = Arrays.toString(var0);
      return var1;
   }

   @JvmStatic
   public inline fun <T> Array<out T>?.contentToString(): String {
      val var1: java.lang.String = Arrays.toString(var0);
      return var1;
   }

   @JvmStatic
   public inline fun ShortArray?.contentToString(): String {
      val var1: java.lang.String = Arrays.toString(var0);
      return var1;
   }

   @JvmStatic
   public inline fun BooleanArray?.contentToString(): String {
      val var1: java.lang.String = Arrays.toString(var0);
      return var1;
   }

   @JvmStatic
   public fun ByteArray.copyInto(destination: ByteArray, destinationOffset: Int = 0, startIndex: Int = 0, endIndex: Int = var0.length): ByteArray {
      System.arraycopy(var0, var3, var1, var2, var4 - var3);
      return var1;
   }

   @JvmStatic
   public fun CharArray.copyInto(destination: CharArray, destinationOffset: Int = 0, startIndex: Int = 0, endIndex: Int = var0.length): CharArray {
      System.arraycopy(var0, var3, var1, var2, var4 - var3);
      return var1;
   }

   @JvmStatic
   public fun DoubleArray.copyInto(destination: DoubleArray, destinationOffset: Int = 0, startIndex: Int = 0, endIndex: Int = var0.length): DoubleArray {
      System.arraycopy(var0, var3, var1, var2, var4 - var3);
      return var1;
   }

   @JvmStatic
   public fun FloatArray.copyInto(destination: FloatArray, destinationOffset: Int = 0, startIndex: Int = 0, endIndex: Int = var0.length): FloatArray {
      System.arraycopy(var0, var3, var1, var2, var4 - var3);
      return var1;
   }

   @JvmStatic
   public fun IntArray.copyInto(destination: IntArray, destinationOffset: Int = 0, startIndex: Int = 0, endIndex: Int = var0.length): IntArray {
      System.arraycopy(var0, var3, var1, var2, var4 - var3);
      return var1;
   }

   @JvmStatic
   public fun LongArray.copyInto(destination: LongArray, destinationOffset: Int = 0, startIndex: Int = 0, endIndex: Int = var0.length): LongArray {
      System.arraycopy(var0, var3, var1, var2, var4 - var3);
      return var1;
   }

   @JvmStatic
   public fun <T> Array<out T>.copyInto(destination: Array<T>, destinationOffset: Int = 0, startIndex: Int = 0, endIndex: Int = var0.length): Array<T> {
      System.arraycopy(var0, var3, var1, var2, var4 - var3);
      return (T[])var1;
   }

   @JvmStatic
   public fun ShortArray.copyInto(destination: ShortArray, destinationOffset: Int = 0, startIndex: Int = 0, endIndex: Int = var0.length): ShortArray {
      System.arraycopy(var0, var3, var1, var2, var4 - var3);
      return var1;
   }

   @JvmStatic
   public fun BooleanArray.copyInto(destination: BooleanArray, destinationOffset: Int = 0, startIndex: Int = 0, endIndex: Int = var0.length): BooleanArray {
      System.arraycopy(var0, var3, var1, var2, var4 - var3);
      return var1;
   }

   @JvmStatic
   public inline fun ByteArray.copyOf(): ByteArray {
      var0 = Arrays.copyOf(var0, var0.length);
      return var0;
   }

   @JvmStatic
   public inline fun ByteArray.copyOf(newSize: Int): ByteArray {
      var0 = Arrays.copyOf(var0, var1);
      return var0;
   }

   @JvmStatic
   public inline fun CharArray.copyOf(): CharArray {
      var0 = Arrays.copyOf(var0, var0.length);
      return var0;
   }

   @JvmStatic
   public inline fun CharArray.copyOf(newSize: Int): CharArray {
      var0 = Arrays.copyOf(var0, var1);
      return var0;
   }

   @JvmStatic
   public inline fun DoubleArray.copyOf(): DoubleArray {
      var0 = Arrays.copyOf(var0, var0.length);
      return var0;
   }

   @JvmStatic
   public inline fun DoubleArray.copyOf(newSize: Int): DoubleArray {
      var0 = Arrays.copyOf(var0, var1);
      return var0;
   }

   @JvmStatic
   public inline fun FloatArray.copyOf(): FloatArray {
      var0 = Arrays.copyOf(var0, var0.length);
      return var0;
   }

   @JvmStatic
   public inline fun FloatArray.copyOf(newSize: Int): FloatArray {
      var0 = Arrays.copyOf(var0, var1);
      return var0;
   }

   @JvmStatic
   public inline fun IntArray.copyOf(): IntArray {
      var0 = Arrays.copyOf(var0, var0.length);
      return var0;
   }

   @JvmStatic
   public inline fun IntArray.copyOf(newSize: Int): IntArray {
      var0 = Arrays.copyOf(var0, var1);
      return var0;
   }

   @JvmStatic
   public inline fun LongArray.copyOf(): LongArray {
      var0 = Arrays.copyOf(var0, var0.length);
      return var0;
   }

   @JvmStatic
   public inline fun LongArray.copyOf(newSize: Int): LongArray {
      var0 = Arrays.copyOf(var0, var1);
      return var0;
   }

   @JvmStatic
   public inline fun <T> Array<T>.copyOf(): Array<T> {
      var0 = Arrays.copyOf(var0, var0.length);
      return (T[])var0;
   }

   @JvmStatic
   public inline fun <T> Array<T>.copyOf(newSize: Int): Array<T?> {
      var0 = Arrays.copyOf(var0, var1);
      return (T[])var0;
   }

   @JvmStatic
   public inline fun ShortArray.copyOf(): ShortArray {
      var0 = Arrays.copyOf(var0, var0.length);
      return var0;
   }

   @JvmStatic
   public inline fun ShortArray.copyOf(newSize: Int): ShortArray {
      var0 = Arrays.copyOf(var0, var1);
      return var0;
   }

   @JvmStatic
   public inline fun BooleanArray.copyOf(): BooleanArray {
      var0 = Arrays.copyOf(var0, var0.length);
      return var0;
   }

   @JvmStatic
   public inline fun BooleanArray.copyOf(newSize: Int): BooleanArray {
      var0 = Arrays.copyOf(var0, var1);
      return var0;
   }

   @JvmStatic
   internal fun ByteArray.copyOfRangeImpl(fromIndex: Int, toIndex: Int): ByteArray {
      ArraysKt.copyOfRangeToIndexCheck(var2, var0.length);
      var0 = Arrays.copyOfRange(var0, var1, var2);
      return var0;
   }

   @JvmStatic
   internal fun CharArray.copyOfRangeImpl(fromIndex: Int, toIndex: Int): CharArray {
      ArraysKt.copyOfRangeToIndexCheck(var2, var0.length);
      var0 = Arrays.copyOfRange(var0, var1, var2);
      return var0;
   }

   @JvmStatic
   internal fun DoubleArray.copyOfRangeImpl(fromIndex: Int, toIndex: Int): DoubleArray {
      ArraysKt.copyOfRangeToIndexCheck(var2, var0.length);
      var0 = Arrays.copyOfRange(var0, var1, var2);
      return var0;
   }

   @JvmStatic
   internal fun FloatArray.copyOfRangeImpl(fromIndex: Int, toIndex: Int): FloatArray {
      ArraysKt.copyOfRangeToIndexCheck(var2, var0.length);
      var0 = Arrays.copyOfRange(var0, var1, var2);
      return var0;
   }

   @JvmStatic
   internal fun IntArray.copyOfRangeImpl(fromIndex: Int, toIndex: Int): IntArray {
      ArraysKt.copyOfRangeToIndexCheck(var2, var0.length);
      var0 = Arrays.copyOfRange(var0, var1, var2);
      return var0;
   }

   @JvmStatic
   internal fun LongArray.copyOfRangeImpl(fromIndex: Int, toIndex: Int): LongArray {
      ArraysKt.copyOfRangeToIndexCheck(var2, var0.length);
      var0 = Arrays.copyOfRange(var0, var1, var2);
      return var0;
   }

   @JvmStatic
   internal fun <T> Array<T>.copyOfRangeImpl(fromIndex: Int, toIndex: Int): Array<T> {
      ArraysKt.copyOfRangeToIndexCheck(var2, var0.length);
      var0 = Arrays.copyOfRange(var0, var1, var2);
      return (T[])var0;
   }

   @JvmStatic
   internal fun ShortArray.copyOfRangeImpl(fromIndex: Int, toIndex: Int): ShortArray {
      ArraysKt.copyOfRangeToIndexCheck(var2, var0.length);
      var0 = Arrays.copyOfRange(var0, var1, var2);
      return var0;
   }

   @JvmStatic
   internal fun BooleanArray.copyOfRangeImpl(fromIndex: Int, toIndex: Int): BooleanArray {
      ArraysKt.copyOfRangeToIndexCheck(var2, var0.length);
      var0 = Arrays.copyOfRange(var0, var1, var2);
      return var0;
   }

   @JvmStatic
   public inline fun ByteArray.copyOfRange(fromIndex: Int, toIndex: Int): ByteArray {
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

      return var0;
   }

   @JvmStatic
   public inline fun CharArray.copyOfRange(fromIndex: Int, toIndex: Int): CharArray {
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

      return var0;
   }

   @JvmStatic
   public inline fun DoubleArray.copyOfRange(fromIndex: Int, toIndex: Int): DoubleArray {
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

      return var0;
   }

   @JvmStatic
   public inline fun FloatArray.copyOfRange(fromIndex: Int, toIndex: Int): FloatArray {
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

      return var0;
   }

   @JvmStatic
   public inline fun IntArray.copyOfRange(fromIndex: Int, toIndex: Int): IntArray {
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

      return var0;
   }

   @JvmStatic
   public inline fun LongArray.copyOfRange(fromIndex: Int, toIndex: Int): LongArray {
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

      return var0;
   }

   @JvmStatic
   public inline fun <T> Array<T>.copyOfRange(fromIndex: Int, toIndex: Int): Array<T> {
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

      return (T[])var0;
   }

   @JvmStatic
   public inline fun ShortArray.copyOfRange(fromIndex: Int, toIndex: Int): ShortArray {
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

      return var0;
   }

   @JvmStatic
   public inline fun BooleanArray.copyOfRange(fromIndex: Int, toIndex: Int): BooleanArray {
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

      return var0;
   }

   @JvmStatic
   public inline fun ByteArray.elementAt(index: Int): Byte {
      return var0[var1];
   }

   @JvmStatic
   public inline fun CharArray.elementAt(index: Int): Char {
      return var0[var1];
   }

   @JvmStatic
   public inline fun DoubleArray.elementAt(index: Int): Double {
      return var0[var1];
   }

   @JvmStatic
   public inline fun FloatArray.elementAt(index: Int): Float {
      return var0[var1];
   }

   @JvmStatic
   public inline fun IntArray.elementAt(index: Int): Int {
      return var0[var1];
   }

   @JvmStatic
   public inline fun LongArray.elementAt(index: Int): Long {
      return var0[var1];
   }

   @JvmStatic
   public inline fun <T> Array<out T>.elementAt(index: Int): T {
      return (T)var0[var1];
   }

   @JvmStatic
   public inline fun ShortArray.elementAt(index: Int): Short {
      return var0[var1];
   }

   @JvmStatic
   public inline fun BooleanArray.elementAt(index: Int): Boolean {
      return var0[var1];
   }

   @JvmStatic
   public fun ByteArray.fill(element: Byte, fromIndex: Int = 0, toIndex: Int = var0.length) {
      Arrays.fill(var0, var2, var3, var1);
   }

   @JvmStatic
   public fun CharArray.fill(element: Char, fromIndex: Int = 0, toIndex: Int = var0.length) {
      Arrays.fill(var0, var2, var3, var1);
   }

   @JvmStatic
   public fun DoubleArray.fill(element: Double, fromIndex: Int = 0, toIndex: Int = var0.length) {
      Arrays.fill(var0, var3, var4, var1);
   }

   @JvmStatic
   public fun FloatArray.fill(element: Float, fromIndex: Int = 0, toIndex: Int = var0.length) {
      Arrays.fill(var0, var2, var3, var1);
   }

   @JvmStatic
   public fun IntArray.fill(element: Int, fromIndex: Int = 0, toIndex: Int = var0.length) {
      Arrays.fill(var0, var2, var3, var1);
   }

   @JvmStatic
   public fun LongArray.fill(element: Long, fromIndex: Int = 0, toIndex: Int = var0.length) {
      Arrays.fill(var0, var3, var4, var1);
   }

   @JvmStatic
   public fun <T> Array<T>.fill(element: T, fromIndex: Int = 0, toIndex: Int = var0.length) {
      Arrays.fill(var0, var2, var3, var1);
   }

   @JvmStatic
   public fun ShortArray.fill(element: Short, fromIndex: Int = 0, toIndex: Int = var0.length) {
      Arrays.fill(var0, var2, var3, var1);
   }

   @JvmStatic
   public fun BooleanArray.fill(element: Boolean, fromIndex: Int = 0, toIndex: Int = var0.length) {
      Arrays.fill(var0, var2, var3, var1);
   }

   @JvmStatic
   public fun <R> Array<*>.filterIsInstance(klass: Class<R>): List<R> {
      return ArraysKt.filterIsInstanceTo(var0, new ArrayList(), var1) as MutableList<R>;
   }

   @JvmStatic
   public fun <C : MutableCollection<in R>, R> Array<*>.filterIsInstanceTo(destination: C, klass: Class<R>): C {
      val var4: Int = var0.length;

      for (int var3 = 0; var3 < var4; var3++) {
         val var5: Any = var0[var3];
         if (var2.isInstance(var0[var3])) {
            var1.add(var5);
         }
      }

      return (C)var1;
   }

   @JvmStatic
   public operator fun ByteArray.plus(element: Byte): ByteArray {
      val var3: ByteArray = Arrays.copyOf(var0, var0.length + 1);
      var3[var0.length] = var1;
      return var3;
   }

   @JvmStatic
   public operator fun ByteArray.plus(elements: Collection<Byte>): ByteArray {
      var var2: Int = var0.length;
      var0 = Arrays.copyOf(var0, var1.size() + var0.length);

      for (java.util.Iterator var4 = var1.iterator(); var4.hasNext(); var2++) {
         var0[var2] = (var4.next() as java.lang.Number).byteValue();
      }

      return var0;
   }

   @JvmStatic
   public operator fun ByteArray.plus(elements: ByteArray): ByteArray {
      val var4: ByteArray = Arrays.copyOf(var0, var0.length + var1.length);
      System.arraycopy(var1, 0, var4, var0.length, var1.length);
      return var4;
   }

   @JvmStatic
   public operator fun CharArray.plus(element: Char): CharArray {
      val var3: CharArray = Arrays.copyOf(var0, var0.length + 1);
      var3[var0.length] = var1;
      return var3;
   }

   @JvmStatic
   public operator fun CharArray.plus(elements: Collection<Char>): CharArray {
      var var2: Int = var0.length;
      var0 = Arrays.copyOf(var0, var1.size() + var0.length);

      for (java.util.Iterator var4 = var1.iterator(); var4.hasNext(); var2++) {
         var0[var2] = var4.next() as Character;
      }

      return var0;
   }

   @JvmStatic
   public operator fun CharArray.plus(elements: CharArray): CharArray {
      val var4: CharArray = Arrays.copyOf(var0, var0.length + var1.length);
      System.arraycopy(var1, 0, var4, var0.length, var1.length);
      return var4;
   }

   @JvmStatic
   public operator fun DoubleArray.plus(element: Double): DoubleArray {
      val var4: DoubleArray = Arrays.copyOf(var0, var0.length + 1);
      var4[var0.length] = var1;
      return var4;
   }

   @JvmStatic
   public operator fun DoubleArray.plus(elements: Collection<Double>): DoubleArray {
      var var2: Int = var0.length;
      var0 = Arrays.copyOf(var0, var1.size() + var0.length);

      for (java.util.Iterator var4 = var1.iterator(); var4.hasNext(); var2++) {
         var0[var2] = (var4.next() as java.lang.Number).doubleValue();
      }

      return var0;
   }

   @JvmStatic
   public operator fun DoubleArray.plus(elements: DoubleArray): DoubleArray {
      val var4: DoubleArray = Arrays.copyOf(var0, var0.length + var1.length);
      System.arraycopy(var1, 0, var4, var0.length, var1.length);
      return var4;
   }

   @JvmStatic
   public operator fun FloatArray.plus(element: Float): FloatArray {
      val var3: FloatArray = Arrays.copyOf(var0, var0.length + 1);
      var3[var0.length] = var1;
      return var3;
   }

   @JvmStatic
   public operator fun FloatArray.plus(elements: Collection<Float>): FloatArray {
      var var2: Int = var0.length;
      var0 = Arrays.copyOf(var0, var1.size() + var0.length);

      for (java.util.Iterator var4 = var1.iterator(); var4.hasNext(); var2++) {
         var0[var2] = (var4.next() as java.lang.Number).floatValue();
      }

      return var0;
   }

   @JvmStatic
   public operator fun FloatArray.plus(elements: FloatArray): FloatArray {
      val var4: FloatArray = Arrays.copyOf(var0, var0.length + var1.length);
      System.arraycopy(var1, 0, var4, var0.length, var1.length);
      return var4;
   }

   @JvmStatic
   public operator fun IntArray.plus(element: Int): IntArray {
      val var3: IntArray = Arrays.copyOf(var0, var0.length + 1);
      var3[var0.length] = var1;
      return var3;
   }

   @JvmStatic
   public operator fun IntArray.plus(elements: Collection<Int>): IntArray {
      var var2: Int = var0.length;
      var0 = Arrays.copyOf(var0, var1.size() + var0.length);

      for (java.util.Iterator var4 = var1.iterator(); var4.hasNext(); var2++) {
         var0[var2] = (var4.next() as java.lang.Number).intValue();
      }

      return var0;
   }

   @JvmStatic
   public operator fun IntArray.plus(elements: IntArray): IntArray {
      val var4: IntArray = Arrays.copyOf(var0, var0.length + var1.length);
      System.arraycopy(var1, 0, var4, var0.length, var1.length);
      return var4;
   }

   @JvmStatic
   public operator fun LongArray.plus(element: Long): LongArray {
      val var4: LongArray = Arrays.copyOf(var0, var0.length + 1);
      var4[var0.length] = var1;
      return var4;
   }

   @JvmStatic
   public operator fun LongArray.plus(elements: Collection<Long>): LongArray {
      var var2: Int = var0.length;
      var0 = Arrays.copyOf(var0, var1.size() + var0.length);

      for (java.util.Iterator var4 = var1.iterator(); var4.hasNext(); var2++) {
         var0[var2] = (var4.next() as java.lang.Number).longValue();
      }

      return var0;
   }

   @JvmStatic
   public operator fun LongArray.plus(elements: LongArray): LongArray {
      val var4: LongArray = Arrays.copyOf(var0, var0.length + var1.length);
      System.arraycopy(var1, 0, var4, var0.length, var1.length);
      return var4;
   }

   @JvmStatic
   public operator fun <T> Array<T>.plus(element: T): Array<T> {
      val var3: Array<Any> = Arrays.copyOf(var0, var0.length + 1);
      var3[var0.length] = var1;
      return (T[])var3;
   }

   @JvmStatic
   public operator fun <T> Array<T>.plus(elements: Collection<T>): Array<T> {
      var var2: Int = var0.length;
      var0 = Arrays.copyOf(var0, var1.size() + var0.length);

      for (java.util.Iterator var4 = var1.iterator(); var4.hasNext(); var2++) {
         var0[var2] = var4.next();
      }

      return (T[])var0;
   }

   @JvmStatic
   public operator fun <T> Array<T>.plus(elements: Array<out T>): Array<T> {
      val var4: Array<Any> = Arrays.copyOf(var0, var0.length + var1.length);
      System.arraycopy(var1, 0, var4, var0.length, var1.length);
      return (T[])var4;
   }

   @JvmStatic
   public operator fun ShortArray.plus(elements: Collection<Short>): ShortArray {
      var var2: Int = var0.length;
      var0 = Arrays.copyOf(var0, var1.size() + var0.length);

      for (java.util.Iterator var4 = var1.iterator(); var4.hasNext(); var2++) {
         var0[var2] = (var4.next() as java.lang.Number).shortValue();
      }

      return var0;
   }

   @JvmStatic
   public operator fun ShortArray.plus(element: Short): ShortArray {
      val var3: ShortArray = Arrays.copyOf(var0, var0.length + 1);
      var3[var0.length] = var1;
      return var3;
   }

   @JvmStatic
   public operator fun ShortArray.plus(elements: ShortArray): ShortArray {
      val var4: ShortArray = Arrays.copyOf(var0, var0.length + var1.length);
      System.arraycopy(var1, 0, var4, var0.length, var1.length);
      return var4;
   }

   @JvmStatic
   public operator fun BooleanArray.plus(elements: Collection<Boolean>): BooleanArray {
      var var2: Int = var0.length;
      var0 = Arrays.copyOf(var0, var1.size() + var0.length);

      for (java.util.Iterator var4 = var1.iterator(); var4.hasNext(); var2++) {
         var0[var2] = var4.next() as java.lang.Boolean;
      }

      return var0;
   }

   @JvmStatic
   public operator fun BooleanArray.plus(element: Boolean): BooleanArray {
      val var3: BooleanArray = Arrays.copyOf(var0, var0.length + 1);
      var3[var0.length] = var1;
      return var3;
   }

   @JvmStatic
   public operator fun BooleanArray.plus(elements: BooleanArray): BooleanArray {
      val var4: BooleanArray = Arrays.copyOf(var0, var0.length + var1.length);
      System.arraycopy(var1, 0, var4, var0.length, var1.length);
      return var4;
   }

   @JvmStatic
   public inline fun <T> Array<T>.plusElement(element: T): Array<T> {
      return (T[])ArraysKt.plus(var0, var1);
   }

   @JvmStatic
   public fun ByteArray.sort() {
      if (var0.length > 1) {
         Arrays.sort(var0);
      }
   }

   @JvmStatic
   public fun ByteArray.sort(fromIndex: Int = 0, toIndex: Int = var0.length) {
      Arrays.sort(var0, var1, var2);
   }

   @JvmStatic
   public fun CharArray.sort() {
      if (var0.length > 1) {
         Arrays.sort(var0);
      }
   }

   @JvmStatic
   public fun CharArray.sort(fromIndex: Int = 0, toIndex: Int = var0.length) {
      Arrays.sort(var0, var1, var2);
   }

   @JvmStatic
   public fun DoubleArray.sort() {
      if (var0.length > 1) {
         Arrays.sort(var0);
      }
   }

   @JvmStatic
   public fun DoubleArray.sort(fromIndex: Int = 0, toIndex: Int = var0.length) {
      Arrays.sort(var0, var1, var2);
   }

   @JvmStatic
   public fun FloatArray.sort() {
      if (var0.length > 1) {
         Arrays.sort(var0);
      }
   }

   @JvmStatic
   public fun FloatArray.sort(fromIndex: Int = 0, toIndex: Int = var0.length) {
      Arrays.sort(var0, var1, var2);
   }

   @JvmStatic
   public fun IntArray.sort() {
      if (var0.length > 1) {
         Arrays.sort(var0);
      }
   }

   @JvmStatic
   public fun IntArray.sort(fromIndex: Int = 0, toIndex: Int = var0.length) {
      Arrays.sort(var0, var1, var2);
   }

   @JvmStatic
   public fun LongArray.sort() {
      if (var0.length > 1) {
         Arrays.sort(var0);
      }
   }

   @JvmStatic
   public fun LongArray.sort(fromIndex: Int = 0, toIndex: Int = var0.length) {
      Arrays.sort(var0, var1, var2);
   }

   @JvmStatic
   public inline fun <T : Comparable<T>> Array<out T>.sort() {
      ArraysKt.sort(var0);
   }

   @JvmStatic
   public fun <T : Comparable<T>> Array<out T>.sort(fromIndex: Int = 0, toIndex: Int = var0.length) {
      Arrays.sort((Object[])var0, var1, var2);
   }

   @JvmStatic
   public fun <T> Array<out T>.sort() {
      if (var0.length > 1) {
         Arrays.sort(var0);
      }
   }

   @JvmStatic
   public fun <T> Array<out T>.sort(fromIndex: Int = 0, toIndex: Int = var0.length) {
      Arrays.sort(var0, var1, var2);
   }

   @JvmStatic
   public fun ShortArray.sort() {
      if (var0.length > 1) {
         Arrays.sort(var0);
      }
   }

   @JvmStatic
   public fun ShortArray.sort(fromIndex: Int = 0, toIndex: Int = var0.length) {
      Arrays.sort(var0, var1, var2);
   }

   @JvmStatic
   public fun <T> Array<out T>.sortWith(comparator: Comparator<in T>) {
      if (var0.length > 1) {
         Arrays.sort(var0, var1);
      }
   }

   @JvmStatic
   public fun <T> Array<out T>.sortWith(comparator: Comparator<in T>, fromIndex: Int = 0, toIndex: Int = var0.length) {
      Arrays.sort(var0, var2, var3, var1);
   }

   @JvmStatic
   public inline fun ByteArray.sumOf(selector: (Byte) -> BigDecimal): BigDecimal {
      var var4: BigDecimal = BigDecimal.valueOf(0L);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = var4.add(var1.invoke(var0[var2]) as BigDecimal);
      }

      return var4;
   }

   @JvmStatic
   public inline fun CharArray.sumOf(selector: (Char) -> BigDecimal): BigDecimal {
      var var4: BigDecimal = BigDecimal.valueOf(0L);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = var4.add(var1.invoke(var0[var2]) as BigDecimal);
      }

      return var4;
   }

   @JvmStatic
   public inline fun DoubleArray.sumOf(selector: (Double) -> BigDecimal): BigDecimal {
      var var4: BigDecimal = BigDecimal.valueOf(0L);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = var4.add(var1.invoke(var0[var2]) as BigDecimal);
      }

      return var4;
   }

   @JvmStatic
   public inline fun FloatArray.sumOf(selector: (Float) -> BigDecimal): BigDecimal {
      var var4: BigDecimal = BigDecimal.valueOf(0L);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = var4.add(var1.invoke(var0[var2]) as BigDecimal);
      }

      return var4;
   }

   @JvmStatic
   public inline fun IntArray.sumOf(selector: (Int) -> BigDecimal): BigDecimal {
      var var4: BigDecimal = BigDecimal.valueOf(0L);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = var4.add(var1.invoke(var0[var2]) as BigDecimal);
      }

      return var4;
   }

   @JvmStatic
   public inline fun LongArray.sumOf(selector: (Long) -> BigDecimal): BigDecimal {
      var var4: BigDecimal = BigDecimal.valueOf(0L);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = var4.add(var1.invoke(var0[var2]) as BigDecimal);
      }

      return var4;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.sumOf(selector: (T) -> BigDecimal): BigDecimal {
      var var4: BigDecimal = BigDecimal.valueOf(0L);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = var4.add(var1.invoke(var0[var2]) as BigDecimal);
      }

      return var4;
   }

   @JvmStatic
   public inline fun ShortArray.sumOf(selector: (Short) -> BigDecimal): BigDecimal {
      var var4: BigDecimal = BigDecimal.valueOf(0L);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = var4.add(var1.invoke(var0[var2]) as BigDecimal);
      }

      return var4;
   }

   @JvmStatic
   public inline fun BooleanArray.sumOf(selector: (Boolean) -> BigDecimal): BigDecimal {
      var var4: BigDecimal = BigDecimal.valueOf(0L);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = var4.add(var1.invoke(var0[var2]) as BigDecimal);
      }

      return var4;
   }

   @JvmStatic
   public inline fun ByteArray.sumOf(selector: (Byte) -> BigInteger): BigInteger {
      var var4: BigInteger = BigInteger.valueOf(0L);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = var4.add(var1.invoke(var0[var2]) as BigInteger);
      }

      return var4;
   }

   @JvmStatic
   public inline fun CharArray.sumOf(selector: (Char) -> BigInteger): BigInteger {
      var var4: BigInteger = BigInteger.valueOf(0L);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = var4.add(var1.invoke(var0[var2]) as BigInteger);
      }

      return var4;
   }

   @JvmStatic
   public inline fun DoubleArray.sumOf(selector: (Double) -> BigInteger): BigInteger {
      var var4: BigInteger = BigInteger.valueOf(0L);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = var4.add(var1.invoke(var0[var2]) as BigInteger);
      }

      return var4;
   }

   @JvmStatic
   public inline fun FloatArray.sumOf(selector: (Float) -> BigInteger): BigInteger {
      var var4: BigInteger = BigInteger.valueOf(0L);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = var4.add(var1.invoke(var0[var2]) as BigInteger);
      }

      return var4;
   }

   @JvmStatic
   public inline fun IntArray.sumOf(selector: (Int) -> BigInteger): BigInteger {
      var var4: BigInteger = BigInteger.valueOf(0L);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = var4.add(var1.invoke(var0[var2]) as BigInteger);
      }

      return var4;
   }

   @JvmStatic
   public inline fun LongArray.sumOf(selector: (Long) -> BigInteger): BigInteger {
      var var4: BigInteger = BigInteger.valueOf(0L);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = var4.add(var1.invoke(var0[var2]) as BigInteger);
      }

      return var4;
   }

   @JvmStatic
   public inline fun <T> Array<out T>.sumOf(selector: (T) -> BigInteger): BigInteger {
      var var4: BigInteger = BigInteger.valueOf(0L);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = var4.add(var1.invoke(var0[var2]) as BigInteger);
      }

      return var4;
   }

   @JvmStatic
   public inline fun ShortArray.sumOf(selector: (Short) -> BigInteger): BigInteger {
      var var4: BigInteger = BigInteger.valueOf(0L);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = var4.add(var1.invoke(var0[var2]) as BigInteger);
      }

      return var4;
   }

   @JvmStatic
   public inline fun BooleanArray.sumOf(selector: (Boolean) -> BigInteger): BigInteger {
      var var4: BigInteger = BigInteger.valueOf(0L);
      val var3: Int = var0.length;

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = var4.add(var1.invoke(var0[var2]) as BigInteger);
      }

      return var4;
   }

   @JvmStatic
   public fun ByteArray.toSortedSet(): SortedSet<Byte> {
      return ArraysKt.toCollection(var0, new TreeSet<>());
   }

   @JvmStatic
   public fun CharArray.toSortedSet(): SortedSet<Char> {
      return ArraysKt.toCollection(var0, new TreeSet<>());
   }

   @JvmStatic
   public fun DoubleArray.toSortedSet(): SortedSet<Double> {
      return ArraysKt.toCollection(var0, new TreeSet<>());
   }

   @JvmStatic
   public fun FloatArray.toSortedSet(): SortedSet<Float> {
      return ArraysKt.toCollection(var0, new TreeSet<>());
   }

   @JvmStatic
   public fun IntArray.toSortedSet(): SortedSet<Int> {
      return ArraysKt.toCollection(var0, new TreeSet<>());
   }

   @JvmStatic
   public fun LongArray.toSortedSet(): SortedSet<Long> {
      return ArraysKt.toCollection(var0, new TreeSet<>());
   }

   @JvmStatic
   public fun <T : Comparable<T>> Array<out T>.toSortedSet(): SortedSet<T> {
      return ArraysKt.toCollection(var0, new TreeSet()) as SortedSet<T>;
   }

   @JvmStatic
   public fun <T> Array<out T>.toSortedSet(comparator: Comparator<in T>): SortedSet<T> {
      return ArraysKt.toCollection(var0, new TreeSet(var1)) as SortedSet<T>;
   }

   @JvmStatic
   public fun ShortArray.toSortedSet(): SortedSet<Short> {
      return ArraysKt.toCollection(var0, new TreeSet<>());
   }

   @JvmStatic
   public fun BooleanArray.toSortedSet(): SortedSet<Boolean> {
      return ArraysKt.toCollection(var0, new TreeSet<>());
   }

   @JvmStatic
   public fun BooleanArray.toTypedArray(): Array<Boolean> {
      val var3: Array<java.lang.Boolean> = new java.lang.Boolean[var0.length];
      val var2: Int = var0.length;

      for (int var1 = 0; var1 < var2; var1++) {
         var3[var1] = var0[var1];
      }

      return var3;
   }

   @JvmStatic
   public fun ByteArray.toTypedArray(): Array<Byte> {
      val var3: Array<java.lang.Byte> = new java.lang.Byte[var0.length];
      val var2: Int = var0.length;

      for (int var1 = 0; var1 < var2; var1++) {
         var3[var1] = var0[var1];
      }

      return var3;
   }

   @JvmStatic
   public fun CharArray.toTypedArray(): Array<Char> {
      val var3: Array<Character> = new Character[var0.length];
      val var2: Int = var0.length;

      for (int var1 = 0; var1 < var2; var1++) {
         var3[var1] = var0[var1];
      }

      return var3;
   }

   @JvmStatic
   public fun DoubleArray.toTypedArray(): Array<Double> {
      val var3: Array<java.lang.Double> = new java.lang.Double[var0.length];
      val var2: Int = var0.length;

      for (int var1 = 0; var1 < var2; var1++) {
         var3[var1] = var0[var1];
      }

      return var3;
   }

   @JvmStatic
   public fun FloatArray.toTypedArray(): Array<Float> {
      val var3: Array<java.lang.Float> = new java.lang.Float[var0.length];
      val var2: Int = var0.length;

      for (int var1 = 0; var1 < var2; var1++) {
         var3[var1] = var0[var1];
      }

      return var3;
   }

   @JvmStatic
   public fun IntArray.toTypedArray(): Array<Int> {
      val var3: Array<Int> = new Integer[var0.length];
      val var2: Int = var0.length;

      for (int var1 = 0; var1 < var2; var1++) {
         var3[var1] = var0[var1];
      }

      return var3;
   }

   @JvmStatic
   public fun LongArray.toTypedArray(): Array<Long> {
      val var3: Array<java.lang.Long> = new java.lang.Long[var0.length];
      val var2: Int = var0.length;

      for (int var1 = 0; var1 < var2; var1++) {
         var3[var1] = var0[var1];
      }

      return var3;
   }

   @JvmStatic
   public fun ShortArray.toTypedArray(): Array<Short> {
      val var3: Array<java.lang.Short> = new java.lang.Short[var0.length];
      val var2: Int = var0.length;

      for (int var1 = 0; var1 < var2; var1++) {
         var3[var1] = var0[var1];
      }

      return var3;
   }
}
