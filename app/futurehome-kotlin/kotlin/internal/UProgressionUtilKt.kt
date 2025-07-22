package kotlin.internal

private fun differenceModulo(a: UInt, b: UInt, c: UInt): UInt {
   var0 = UByte$$ExternalSyntheticBackport0.m$1(var0, var2);
   val var3: Int = UByte$$ExternalSyntheticBackport0.m$1(var1, var2);
   var1 = UByte$$ExternalSyntheticBackport0.m$2(var0, var3);
   var0 = UInt.constructor-impl(var0 - var3);
   if (var1 < 0) {
      var0 = UInt.constructor-impl(var0 + var2);
   }

   return var0;
}

private fun differenceModulo(a: ULong, b: ULong, c: ULong): ULong {
   var0 = UByte$$ExternalSyntheticBackport0.m(var0, var4);
   var2 = UByte$$ExternalSyntheticBackport0.m(var2, var4);
   val var6: Int = UByte$$ExternalSyntheticBackport0.m(var0, var2);
   var0 = ULong.constructor-impl(var0 - var2);
   if (var6 < 0) {
      var0 = ULong.constructor-impl(var0 + var4);
   }

   return var0;
}

internal fun getProgressionLastElement(start: ULong, end: ULong, step: Long): ULong {
   val var7: Long;
   val var6: Byte = (byte)(if ((var7 = var4 - 0L) == 0L) 0 else (if (var7 < 0L) -1 else 1));
   if (var4 > 0L) {
      if (UByte$$ExternalSyntheticBackport0.m(var0, var2) < 0) {
         var2 = ULong.constructor-impl(var2 - differenceModulo-sambcqE(var2, var0, ULong.constructor-impl(var4)));
      }
   } else {
      if (var6 >= 0) {
         throw new IllegalArgumentException("Step is zero.");
      }

      if (UByte$$ExternalSyntheticBackport0.m(var0, var2) > 0) {
         var2 = ULong.constructor-impl(var2 + differenceModulo-sambcqE(var0, var2, ULong.constructor-impl(-var4)));
      }
   }

   return var2;
}

internal fun getProgressionLastElement(start: UInt, end: UInt, step: Int): UInt {
   if (var2 > 0) {
      if (UByte$$ExternalSyntheticBackport0.m$2(var0, var1) < 0) {
         var1 = UInt.constructor-impl(var1 - differenceModulo-WZ9TVnA(var1, var0, UInt.constructor-impl(var2)));
      }
   } else {
      if (var2 >= 0) {
         throw new IllegalArgumentException("Step is zero.");
      }

      if (UByte$$ExternalSyntheticBackport0.m$2(var0, var1) > 0) {
         var1 = UInt.constructor-impl(var1 + differenceModulo-WZ9TVnA(var0, var1, UInt.constructor-impl(-var2)));
      }
   }

   return var1;
}
