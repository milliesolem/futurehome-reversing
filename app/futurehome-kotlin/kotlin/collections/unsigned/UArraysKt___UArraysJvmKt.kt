package kotlin.collections.unsigned

import java.math.BigDecimal
import java.math.BigInteger
import java.util.RandomAccess

internal class UArraysKt___UArraysJvmKt {
   open fun UArraysKt___UArraysJvmKt() {
   }

   @JvmStatic
   public fun UIntArray.asList(): List<UInt> {
      return new RandomAccess(var0) {
         final int[] $this_asList;

         {
            this.$this_asList = var1;
         }

         public boolean contains_WZ4Q5Ns/* $VF was: contains-WZ4Q5Ns*/(int var1) {
            return UIntArray.contains-WZ4Q5Ns(this.$this_asList, var1);
         }

         public int get_pVg5ArA/* $VF was: get-pVg5ArA*/(int var1) {
            return UIntArray.get-pVg5ArA(this.$this_asList, var1);
         }

         @Override
         public int getSize() {
            return UIntArray.getSize-impl(this.$this_asList);
         }

         public int indexOf_WZ4Q5Ns/* $VF was: indexOf-WZ4Q5Ns*/(int var1) {
            return ArraysKt.indexOf(this.$this_asList, var1);
         }

         @Override
         public boolean isEmpty() {
            return UIntArray.isEmpty-impl(this.$this_asList);
         }

         public int lastIndexOf_WZ4Q5Ns/* $VF was: lastIndexOf-WZ4Q5Ns*/(int var1) {
            return ArraysKt.lastIndexOf(this.$this_asList, var1);
         }
      };
   }

   @JvmStatic
   public fun UByteArray.asList(): List<UByte> {
      return new RandomAccess(var0) {
         final byte[] $this_asList;

         {
            this.$this_asList = var1;
         }

         public boolean contains_7apg3OU/* $VF was: contains-7apg3OU*/(byte var1) {
            return UByteArray.contains-7apg3OU(this.$this_asList, var1);
         }

         public byte get_w2LRezQ/* $VF was: get-w2LRezQ*/(int var1) {
            return UByteArray.get-w2LRezQ(this.$this_asList, var1);
         }

         @Override
         public int getSize() {
            return UByteArray.getSize-impl(this.$this_asList);
         }

         public int indexOf_7apg3OU/* $VF was: indexOf-7apg3OU*/(byte var1) {
            return ArraysKt.indexOf(this.$this_asList, var1);
         }

         @Override
         public boolean isEmpty() {
            return UByteArray.isEmpty-impl(this.$this_asList);
         }

         public int lastIndexOf_7apg3OU/* $VF was: lastIndexOf-7apg3OU*/(byte var1) {
            return ArraysKt.lastIndexOf(this.$this_asList, var1);
         }
      };
   }

   @JvmStatic
   public fun ULongArray.asList(): List<ULong> {
      return new RandomAccess(var0) {
         final long[] $this_asList;

         {
            this.$this_asList = var1;
         }

         public boolean contains_VKZWuLQ/* $VF was: contains-VKZWuLQ*/(long var1) {
            return ULongArray.contains-VKZWuLQ(this.$this_asList, var1);
         }

         public long get_s_VKNKU/* $VF was: get-s-VKNKU*/(int var1) {
            return ULongArray.get-s-VKNKU(this.$this_asList, var1);
         }

         @Override
         public int getSize() {
            return ULongArray.getSize-impl(this.$this_asList);
         }

         public int indexOf_VKZWuLQ/* $VF was: indexOf-VKZWuLQ*/(long var1) {
            return ArraysKt.indexOf(this.$this_asList, var1);
         }

         @Override
         public boolean isEmpty() {
            return ULongArray.isEmpty-impl(this.$this_asList);
         }

         public int lastIndexOf_VKZWuLQ/* $VF was: lastIndexOf-VKZWuLQ*/(long var1) {
            return ArraysKt.lastIndexOf(this.$this_asList, var1);
         }
      };
   }

   @JvmStatic
   public fun UShortArray.asList(): List<UShort> {
      return new RandomAccess(var0) {
         final short[] $this_asList;

         {
            this.$this_asList = var1;
         }

         public boolean contains_xj2QHRw/* $VF was: contains-xj2QHRw*/(short var1) {
            return UShortArray.contains-xj2QHRw(this.$this_asList, var1);
         }

         public short get_Mh2AYeg/* $VF was: get-Mh2AYeg*/(int var1) {
            return UShortArray.get-Mh2AYeg(this.$this_asList, var1);
         }

         @Override
         public int getSize() {
            return UShortArray.getSize-impl(this.$this_asList);
         }

         public int indexOf_xj2QHRw/* $VF was: indexOf-xj2QHRw*/(short var1) {
            return ArraysKt.indexOf(this.$this_asList, var1);
         }

         @Override
         public boolean isEmpty() {
            return UShortArray.isEmpty-impl(this.$this_asList);
         }

         public int lastIndexOf_xj2QHRw/* $VF was: lastIndexOf-xj2QHRw*/(short var1) {
            return ArraysKt.lastIndexOf(this.$this_asList, var1);
         }
      };
   }

   @JvmStatic
   public fun UIntArray.binarySearch(element: UInt, fromIndex: Int = ..., toIndex: Int = ...): Int {
      AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(var2, var3, UIntArray.getSize-impl(var0));
      var3--;

      while (var2 <= var3) {
         val var5: Int = var2 + var3 ushr 1;
         val var4: Int = UnsignedKt.uintCompare(var0[var2 + var3 ushr 1], var1);
         if (var4 < 0) {
            var2 = var5 + 1;
         } else {
            if (var4 <= 0) {
               return var5;
            }

            var3 = var5 - 1;
         }
      }

      return -(var2 + 1);
   }

   @JvmStatic
   public fun UShortArray.binarySearch(element: UShort, fromIndex: Int = ..., toIndex: Int = ...): Int {
      AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(var2, var3, UShortArray.getSize-impl(var0));
      var3--;

      while (var2 <= var3) {
         val var5: Int = var2 + var3 ushr 1;
         val var4: Int = UnsignedKt.uintCompare(var0[var2 + var3 ushr 1], var1 and '\uffff');
         if (var4 < 0) {
            var2 = var5 + 1;
         } else {
            if (var4 <= 0) {
               return var5;
            }

            var3 = var5 - 1;
         }
      }

      return -(var2 + 1);
   }

   @JvmStatic
   public fun ULongArray.binarySearch(element: ULong, fromIndex: Int = ..., toIndex: Int = ...): Int {
      AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(var3, var4, ULongArray.getSize-impl(var0));
      var4--;

      while (var3 <= var4) {
         val var5: Int = var3 + var4 ushr 1;
         val var6: Int = UnsignedKt.ulongCompare(var0[var3 + var4 ushr 1], var1);
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
   public fun UByteArray.binarySearch(element: UByte, fromIndex: Int = ..., toIndex: Int = ...): Int {
      AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(var2, var3, UByteArray.getSize-impl(var0));
      var3--;

      while (var2 <= var3) {
         val var5: Int = var2 + var3 ushr 1;
         val var4: Int = UnsignedKt.uintCompare(var0[var2 + var3 ushr 1], var1 and 255);
         if (var4 < 0) {
            var2 = var5 + 1;
         } else {
            if (var4 <= 0) {
               return var5;
            }

            var3 = var5 - 1;
         }
      }

      return -(var2 + 1);
   }

   @JvmStatic
   public inline fun UByteArray.elementAt(index: Int): UByte {
      return UByteArray.get-w2LRezQ(var0, var1);
   }

   @JvmStatic
   public inline fun UShortArray.elementAt(index: Int): UShort {
      return UShortArray.get-Mh2AYeg(var0, var1);
   }

   @JvmStatic
   public inline fun UIntArray.elementAt(index: Int): UInt {
      return UIntArray.get-pVg5ArA(var0, var1);
   }

   @JvmStatic
   public inline fun ULongArray.elementAt(index: Int): ULong {
      return ULongArray.get-s-VKNKU(var0, var1);
   }

   @JvmStatic
   public inline fun UByteArray.sumOf(selector: (UByte) -> BigDecimal): BigDecimal {
      var var4: BigDecimal = BigDecimal.valueOf(0L);
      val var3: Int = UByteArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = var4.add(var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var2))) as BigDecimal);
      }

      return var4;
   }

   @JvmStatic
   public inline fun UIntArray.sumOf(selector: (UInt) -> BigDecimal): BigDecimal {
      var var4: BigDecimal = BigDecimal.valueOf(0L);
      val var3: Int = UIntArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = var4.add(var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2))) as BigDecimal);
      }

      return var4;
   }

   @JvmStatic
   public inline fun ULongArray.sumOf(selector: (ULong) -> BigDecimal): BigDecimal {
      var var4: BigDecimal = BigDecimal.valueOf(0L);
      val var3: Int = ULongArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = var4.add(var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2))) as BigDecimal);
      }

      return var4;
   }

   @JvmStatic
   public inline fun UShortArray.sumOf(selector: (UShort) -> BigDecimal): BigDecimal {
      var var4: BigDecimal = BigDecimal.valueOf(0L);
      val var3: Int = UShortArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = var4.add(var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var2))) as BigDecimal);
      }

      return var4;
   }

   @JvmStatic
   public inline fun UByteArray.sumOf(selector: (UByte) -> BigInteger): BigInteger {
      var var4: BigInteger = BigInteger.valueOf(0L);
      val var3: Int = UByteArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = var4.add(var1.invoke(UByte.box-impl(UByteArray.get-w2LRezQ(var0, var2))) as BigInteger);
      }

      return var4;
   }

   @JvmStatic
   public inline fun UIntArray.sumOf(selector: (UInt) -> BigInteger): BigInteger {
      var var4: BigInteger = BigInteger.valueOf(0L);
      val var3: Int = UIntArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = var4.add(var1.invoke(UInt.box-impl(UIntArray.get-pVg5ArA(var0, var2))) as BigInteger);
      }

      return var4;
   }

   @JvmStatic
   public inline fun ULongArray.sumOf(selector: (ULong) -> BigInteger): BigInteger {
      var var4: BigInteger = BigInteger.valueOf(0L);
      val var3: Int = ULongArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = var4.add(var1.invoke(ULong.box-impl(ULongArray.get-s-VKNKU(var0, var2))) as BigInteger);
      }

      return var4;
   }

   @JvmStatic
   public inline fun UShortArray.sumOf(selector: (UShort) -> BigInteger): BigInteger {
      var var4: BigInteger = BigInteger.valueOf(0L);
      val var3: Int = UShortArray.getSize-impl(var0);

      for (int var2 = 0; var2 < var3; var2++) {
         var4 = var4.add(var1.invoke(UShort.box-impl(UShortArray.get-Mh2AYeg(var0, var2))) as BigInteger);
      }

      return var4;
   }
}
