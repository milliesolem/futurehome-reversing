package kotlin.comparisons

import kotlin.jvm.internal.Intrinsics

internal class UComparisonsKt___UComparisonsKt {
   open fun UComparisonsKt___UComparisonsKt() {
   }

   @JvmStatic
   public fun maxOf(a: UShort, b: UShort): UShort {
      if (Intrinsics.compare(var0 and '\uffff', '\uffff' and var1) < 0) {
         var0 = var1;
      }

      return var0;
   }

   @JvmStatic
   public fun maxOf(a: UInt, b: UInt): UInt {
      if (UByte$$ExternalSyntheticBackport0.m$2(var0, var1) < 0) {
         var0 = var1;
      }

      return var0;
   }

   @JvmStatic
   public fun maxOf(a: UByte, b: UByte): UByte {
      if (Intrinsics.compare(var0 and 255, var1 and 255) < 0) {
         var0 = var1;
      }

      return var0;
   }

   @JvmStatic
   public fun maxOf(a: UInt, other: UIntArray): UInt {
      val var4: Int = UIntArray.getSize-impl(var1);
      var var2: Int = var0;

      for (int var5 = 0; var5 < var4; var5++) {
         var2 = UComparisonsKt.maxOf-J1ME1BU(var2, UIntArray.get-pVg5ArA(var1, var5));
      }

      return var2;
   }

   @JvmStatic
   public fun maxOf(a: ULong, other: ULongArray): ULong {
      val var4: Int = ULongArray.getSize-impl(var2);

      for (int var3 = 0; var3 < var4; var3++) {
         var0 = UComparisonsKt.maxOf-eb3DHEI(var0, ULongArray.get-s-VKNKU(var2, var3));
      }

      return var0;
   }

   @JvmStatic
   public inline fun maxOf(a: UShort, b: UShort, c: UShort): UShort {
      return UComparisonsKt.maxOf-5PvTz6A(var0, UComparisonsKt.maxOf-5PvTz6A(var1, var2));
   }

   @JvmStatic
   public inline fun maxOf(a: UInt, b: UInt, c: UInt): UInt {
      return UComparisonsKt.maxOf-J1ME1BU(var0, UComparisonsKt.maxOf-J1ME1BU(var1, var2));
   }

   @JvmStatic
   public fun maxOf(a: UByte, other: UByteArray): UByte {
      val var3: Int = UByteArray.getSize-impl(var1);

      for (int var2 = 0; var2 < var3; var2++) {
         var0 = UComparisonsKt.maxOf-Kr8caGY(var0, UByteArray.get-w2LRezQ(var1, var2));
      }

      return var0;
   }

   @JvmStatic
   public inline fun maxOf(a: UByte, b: UByte, c: UByte): UByte {
      return UComparisonsKt.maxOf-Kr8caGY(var0, UComparisonsKt.maxOf-Kr8caGY(var1, var2));
   }

   @JvmStatic
   public fun maxOf(a: ULong, b: ULong): ULong {
      if (UByte$$ExternalSyntheticBackport0.m(var0, var2) < 0) {
         var0 = var2;
      }

      return var0;
   }

   @JvmStatic
   public inline fun maxOf(a: ULong, b: ULong, c: ULong): ULong {
      return UComparisonsKt.maxOf-eb3DHEI(var0, UComparisonsKt.maxOf-eb3DHEI(var2, var4));
   }

   @JvmStatic
   public fun maxOf(a: UShort, other: UShortArray): UShort {
      val var3: Int = UShortArray.getSize-impl(var1);

      for (int var2 = 0; var2 < var3; var2++) {
         var0 = UComparisonsKt.maxOf-5PvTz6A(var0, UShortArray.get-Mh2AYeg(var1, var2));
      }

      return var0;
   }

   @JvmStatic
   public fun minOf(a: UShort, b: UShort): UShort {
      if (Intrinsics.compare(var0 and '\uffff', '\uffff' and var1) > 0) {
         var0 = var1;
      }

      return var0;
   }

   @JvmStatic
   public fun minOf(a: UInt, b: UInt): UInt {
      if (UByte$$ExternalSyntheticBackport0.m$2(var0, var1) > 0) {
         var0 = var1;
      }

      return var0;
   }

   @JvmStatic
   public fun minOf(a: UByte, b: UByte): UByte {
      if (Intrinsics.compare(var0 and 255, var1 and 255) > 0) {
         var0 = var1;
      }

      return var0;
   }

   @JvmStatic
   public fun minOf(a: UInt, other: UIntArray): UInt {
      val var4: Int = UIntArray.getSize-impl(var1);
      var var2: Int = var0;

      for (int var5 = 0; var5 < var4; var5++) {
         var2 = UComparisonsKt.minOf-J1ME1BU(var2, UIntArray.get-pVg5ArA(var1, var5));
      }

      return var2;
   }

   @JvmStatic
   public fun minOf(a: ULong, other: ULongArray): ULong {
      val var4: Int = ULongArray.getSize-impl(var2);

      for (int var3 = 0; var3 < var4; var3++) {
         var0 = UComparisonsKt.minOf-eb3DHEI(var0, ULongArray.get-s-VKNKU(var2, var3));
      }

      return var0;
   }

   @JvmStatic
   public inline fun minOf(a: UShort, b: UShort, c: UShort): UShort {
      return UComparisonsKt.minOf-5PvTz6A(var0, UComparisonsKt.minOf-5PvTz6A(var1, var2));
   }

   @JvmStatic
   public inline fun minOf(a: UInt, b: UInt, c: UInt): UInt {
      return UComparisonsKt.minOf-J1ME1BU(var0, UComparisonsKt.minOf-J1ME1BU(var1, var2));
   }

   @JvmStatic
   public fun minOf(a: UByte, other: UByteArray): UByte {
      val var3: Int = UByteArray.getSize-impl(var1);

      for (int var2 = 0; var2 < var3; var2++) {
         var0 = UComparisonsKt.minOf-Kr8caGY(var0, UByteArray.get-w2LRezQ(var1, var2));
      }

      return var0;
   }

   @JvmStatic
   public inline fun minOf(a: UByte, b: UByte, c: UByte): UByte {
      return UComparisonsKt.minOf-Kr8caGY(var0, UComparisonsKt.minOf-Kr8caGY(var1, var2));
   }

   @JvmStatic
   public fun minOf(a: ULong, b: ULong): ULong {
      if (UByte$$ExternalSyntheticBackport0.m(var0, var2) > 0) {
         var0 = var2;
      }

      return var0;
   }

   @JvmStatic
   public inline fun minOf(a: ULong, b: ULong, c: ULong): ULong {
      return UComparisonsKt.minOf-eb3DHEI(var0, UComparisonsKt.minOf-eb3DHEI(var2, var4));
   }

   @JvmStatic
   public fun minOf(a: UShort, other: UShortArray): UShort {
      val var3: Int = UShortArray.getSize-impl(var1);

      for (int var2 = 0; var2 < var3; var2++) {
         var0 = UComparisonsKt.minOf-5PvTz6A(var0, UShortArray.get-Mh2AYeg(var1, var2));
      }

      return var0;
   }
}
