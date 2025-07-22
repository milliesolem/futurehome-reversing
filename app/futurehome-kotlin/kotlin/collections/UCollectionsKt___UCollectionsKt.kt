package kotlin.collections

internal class UCollectionsKt___UCollectionsKt {
   open fun UCollectionsKt___UCollectionsKt() {
   }

   @JvmStatic
   public fun Iterable<UByte>.sum(): UInt {
      val var2: java.util.Iterator = var0.iterator();
      var var1: Int = 0;

      while (var2.hasNext()) {
         var1 = UInt.constructor-impl(var1 + UInt.constructor-impl((var2.next() as UByte).unbox-impl() and 255));
      }

      return var1;
   }

   @JvmStatic
   public fun Iterable<UInt>.sum(): UInt {
      val var2: java.util.Iterator = var0.iterator();
      var var1: Int = 0;

      while (var2.hasNext()) {
         var1 = UInt.constructor-impl(var1 + (var2.next() as UInt).unbox-impl());
      }

      return var1;
   }

   @JvmStatic
   public fun Iterable<ULong>.sum(): ULong {
      val var3: java.util.Iterator = var0.iterator();
      var var1: Long = 0L;

      while (var3.hasNext()) {
         var1 = ULong.constructor-impl(var1 + (var3.next() as ULong).unbox-impl());
      }

      return var1;
   }

   @JvmStatic
   public fun Iterable<UShort>.sum(): UInt {
      val var2: java.util.Iterator = var0.iterator();
      var var1: Int = 0;

      while (var2.hasNext()) {
         var1 = UInt.constructor-impl(var1 + UInt.constructor-impl((var2.next() as UShort).unbox-impl() and '\uffff'));
      }

      return var1;
   }

   @JvmStatic
   public fun Collection<UByte>.toUByteArray(): UByteArray {
      val var2: ByteArray = UByteArray.constructor-impl(var0.size());
      val var3: java.util.Iterator = var0.iterator();

      for (int var1 = 0; var3.hasNext(); var1++) {
         UByteArray.set-VurrAj0(var2, var1, (var3.next() as UByte).unbox-impl());
      }

      return var2;
   }

   @JvmStatic
   public fun Collection<UInt>.toUIntArray(): UIntArray {
      val var2: IntArray = UIntArray.constructor-impl(var0.size());
      val var3: java.util.Iterator = var0.iterator();

      for (int var1 = 0; var3.hasNext(); var1++) {
         UIntArray.set-VXSXFK8(var2, var1, (var3.next() as UInt).unbox-impl());
      }

      return var2;
   }

   @JvmStatic
   public fun Collection<ULong>.toULongArray(): ULongArray {
      val var2: LongArray = ULongArray.constructor-impl(var0.size());
      val var3: java.util.Iterator = var0.iterator();

      for (int var1 = 0; var3.hasNext(); var1++) {
         ULongArray.set-k8EXiF4(var2, var1, (var3.next() as ULong).unbox-impl());
      }

      return var2;
   }

   @JvmStatic
   public fun Collection<UShort>.toUShortArray(): UShortArray {
      val var2: ShortArray = UShortArray.constructor-impl(var0.size());
      val var3: java.util.Iterator = var0.iterator();

      for (int var1 = 0; var3.hasNext(); var1++) {
         UShortArray.set-01HTLdE(var2, var1, (var3.next() as UShort).unbox-impl());
      }

      return var2;
   }
}
