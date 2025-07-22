package kotlin.random

internal fun checkUIntRangeBounds(from: UInt, until: UInt) {
   if (UByte$$ExternalSyntheticBackport0.m$2(var1, var0) <= 0) {
      throw new IllegalArgumentException(RandomKt.boundsErrorMessage(UInt.box-impl(var0), UInt.box-impl(var1)).toString());
   }
}

internal fun checkULongRangeBounds(from: ULong, until: ULong) {
   if (UByte$$ExternalSyntheticBackport0.m(var2, var0) <= 0) {
      throw new IllegalArgumentException(RandomKt.boundsErrorMessage(ULong.box-impl(var0), ULong.box-impl(var2)).toString());
   }
}

public fun Random.nextUBytes(size: Int): UByteArray {
   return UByteArray.constructor-impl(var0.nextBytes(var1));
}

public fun Random.nextUBytes(array: UByteArray): UByteArray {
   var0.nextBytes(var1);
   return var1;
}

public fun Random.nextUBytes(array: UByteArray, fromIndex: Int = ..., toIndex: Int = ...): UByteArray {
   var0.nextBytes(var1, var2, var3);
   return var1;
}

@JvmSynthetic
fun `nextUBytes-Wvrt4B4$default`(var0: Random, var1: ByteArray, var2: Int, var3: Int, var4: Int, var5: Any): ByteArray {
   if ((var4 and 2) != 0) {
      var2 = 0;
   }

   if ((var4 and 4) != 0) {
      var3 = UByteArray.getSize-impl(var1);
   }

   return nextUBytes-Wvrt4B4(var0, var1, var2, var3);
}

public fun Random.nextUInt(): UInt {
   return UInt.constructor-impl(var0.nextInt());
}

public fun Random.nextUInt(range: UIntRange): UInt {
   if (!var1.isEmpty()) {
      val var2: Int;
      if (UByte$$ExternalSyntheticBackport0.m$2(var1.getLast-pVg5ArA(), -1) < 0) {
         var2 = nextUInt-a8DCA5k(var0, var1.getFirst-pVg5ArA(), UInt.constructor-impl(var1.getLast-pVg5ArA() + 1));
      } else if (UByte$$ExternalSyntheticBackport0.m$2(var1.getFirst-pVg5ArA(), 0) > 0) {
         var2 = UInt.constructor-impl(nextUInt-a8DCA5k(var0, UInt.constructor-impl(var1.getFirst-pVg5ArA() - 1), var1.getLast-pVg5ArA()) + 1);
      } else {
         var2 = nextUInt(var0);
      }

      return var2;
   } else {
      val var3: StringBuilder = new StringBuilder("Cannot get random in empty range: ");
      var3.append(var1);
      throw new IllegalArgumentException(var3.toString());
   }
}

public fun Random.nextUInt(from: UInt, until: UInt): UInt {
   checkUIntRangeBounds-J1ME1BU(var1, var2);
   return UInt.constructor-impl(var0.nextInt(var1 xor Integer.MIN_VALUE, var2 xor Integer.MIN_VALUE) xor Integer.MIN_VALUE);
}

public fun Random.nextUInt(until: UInt): UInt {
   return nextUInt-a8DCA5k(var0, 0, var1);
}

public fun Random.nextULong(): ULong {
   return ULong.constructor-impl(var0.nextLong());
}

public fun Random.nextULong(range: ULongRange): ULong {
   if (!var1.isEmpty()) {
      val var2: Long;
      if (UByte$$ExternalSyntheticBackport0.m(var1.getLast-s-VKNKU(), -1L) < 0) {
         var2 = nextULong-jmpaW-c(
            var0, var1.getFirst-s-VKNKU(), ULong.constructor-impl(var1.getLast-s-VKNKU() + ULong.constructor-impl((long)1 and 4294967295L))
         );
      } else if (UByte$$ExternalSyntheticBackport0.m(var1.getFirst-s-VKNKU(), 0L) > 0) {
         var2 = ULong.constructor-impl(
            nextULong-jmpaW-c(var0, ULong.constructor-impl(var1.getFirst-s-VKNKU() - ULong.constructor-impl((long)1 and 4294967295L)), var1.getLast-s-VKNKU())
               + ULong.constructor-impl((long)1 and 4294967295L)
         );
      } else {
         var2 = nextULong(var0);
      }

      return var2;
   } else {
      val var6: StringBuilder = new StringBuilder("Cannot get random in empty range: ");
      var6.append(var1);
      throw new IllegalArgumentException(var6.toString());
   }
}

public fun Random.nextULong(until: ULong): ULong {
   return nextULong-jmpaW-c(var0, 0L, var1);
}

public fun Random.nextULong(from: ULong, until: ULong): ULong {
   checkULongRangeBounds-eb3DHEI(var1, var3);
   return ULong.constructor-impl(var0.nextLong(var1 xor java.lang.Long.MIN_VALUE, var3 xor java.lang.Long.MIN_VALUE) xor java.lang.Long.MIN_VALUE);
}
