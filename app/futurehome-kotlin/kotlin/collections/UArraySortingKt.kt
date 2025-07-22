package kotlin.collections

import kotlin.jvm.internal.Intrinsics

private fun partition(array: ULongArray, left: Int, right: Int): Int {
   val var5: Long = ULongArray.get-s-VKNKU(var0, (var1 + var2) / 2);

   while (var1 <= var2) {
      var var4: Int = var1;

      while (true) {
         var var3: Int = var2;
         if (UByte$$ExternalSyntheticBackport0.m(ULongArray.get-s-VKNKU(var0, var4), var5) >= 0) {
            while (UByte$$ExternalSyntheticBackport0.m(ULongArray.get-s-VKNKU(var0, var3), var5) > 0) {
               var3--;
            }

            var1 = var4;
            var2 = var3;
            if (var4 <= var3) {
               val var7: Long = ULongArray.get-s-VKNKU(var0, var4);
               ULongArray.set-k8EXiF4(var0, var4, ULongArray.get-s-VKNKU(var0, var3));
               ULongArray.set-k8EXiF4(var0, var3, var7);
               var1 = var4 + 1;
               var2 = var3 - 1;
            }
            break;
         }

         var4++;
      }
   }

   return var1;
}

private fun partition(array: UByteArray, left: Int, right: Int): Int {
   val var6: Byte = UByteArray.get-w2LRezQ(var0, (var1 + var2) / 2);

   while (var1 <= var2) {
      var var5: Int = var1;

      while (true) {
         val var7: Byte = UByteArray.get-w2LRezQ(var0, var5);
         var1 = var6 and 255;
         var var4: Int = var2;
         if (Intrinsics.compare(var7 and 255, var1) >= 0) {
            while (Intrinsics.compare(UByteArray.get-w2LRezQ(var0, var4) & 255, var1) > 0) {
               var4--;
            }

            var1 = var5;
            var2 = var4;
            if (var5 <= var4) {
               val var3: Byte = UByteArray.get-w2LRezQ(var0, var5);
               UByteArray.set-VurrAj0(var0, var5, UByteArray.get-w2LRezQ(var0, var4));
               UByteArray.set-VurrAj0(var0, var4, var3);
               var1 = var5 + 1;
               var2 = var4 - 1;
            }
            break;
         }

         var5++;
      }
   }

   return var1;
}

private fun partition(array: UShortArray, left: Int, right: Int): Int {
   val var6: Short = UShortArray.get-Mh2AYeg(var0, (var1 + var2) / 2);

   while (var1 <= var2) {
      var var5: Int = var1;

      while (true) {
         val var7: Short = UShortArray.get-Mh2AYeg(var0, var5);
         var1 = var6 and '\uffff';
         var var4: Int = var2;
         if (Intrinsics.compare(var7 and '\uffff', var1) >= 0) {
            while (Intrinsics.compare(UShortArray.get-Mh2AYeg(var0, var4) & '\uffff', var1) > 0) {
               var4--;
            }

            var1 = var5;
            var2 = var4;
            if (var5 <= var4) {
               val var3: Short = UShortArray.get-Mh2AYeg(var0, var5);
               UShortArray.set-01HTLdE(var0, var5, UShortArray.get-Mh2AYeg(var0, var4));
               UShortArray.set-01HTLdE(var0, var4, var3);
               var1 = var5 + 1;
               var2 = var4 - 1;
            }
            break;
         }

         var5++;
      }
   }

   return var1;
}

private fun partition(array: UIntArray, left: Int, right: Int): Int {
   val var5: Int = UIntArray.get-pVg5ArA(var0, (var1 + var2) / 2);

   while (var1 <= var2) {
      var var4: Int = var1;

      while (true) {
         var var3: Int = var2;
         if (UByte$$ExternalSyntheticBackport0.m$2(UIntArray.get-pVg5ArA(var0, var4), var5) >= 0) {
            while (UByte$$ExternalSyntheticBackport0.m$2(UIntArray.get-pVg5ArA(var0, var3), var5) > 0) {
               var3--;
            }

            var1 = var4;
            var2 = var3;
            if (var4 <= var3) {
               var1 = UIntArray.get-pVg5ArA(var0, var4);
               UIntArray.set-VXSXFK8(var0, var4, UIntArray.get-pVg5ArA(var0, var3));
               UIntArray.set-VXSXFK8(var0, var3, var1);
               var1 = var4 + 1;
               var2 = var3 - 1;
            }
            break;
         }

         var4++;
      }
   }

   return var1;
}

private fun quickSort(array: ULongArray, left: Int, right: Int) {
   val var4: Int = partition--nroSd4(var0, var1, var2);
   val var3: Int = var4 - 1;
   if (var1 < var4 - 1) {
      quickSort--nroSd4(var0, var1, var3);
   }

   if (var4 < var2) {
      quickSort--nroSd4(var0, var4, var2);
   }
}

private fun quickSort(array: UByteArray, left: Int, right: Int) {
   val var4: Int = partition-4UcCI2c(var0, var1, var2);
   val var3: Int = var4 - 1;
   if (var1 < var4 - 1) {
      quickSort-4UcCI2c(var0, var1, var3);
   }

   if (var4 < var2) {
      quickSort-4UcCI2c(var0, var4, var2);
   }
}

private fun quickSort(array: UShortArray, left: Int, right: Int) {
   val var3: Int = partition-Aa5vz7o(var0, var1, var2);
   val var4: Int = var3 - 1;
   if (var1 < var3 - 1) {
      quickSort-Aa5vz7o(var0, var1, var4);
   }

   if (var3 < var2) {
      quickSort-Aa5vz7o(var0, var3, var2);
   }
}

private fun quickSort(array: UIntArray, left: Int, right: Int) {
   val var3: Int = partition-oBK06Vg(var0, var1, var2);
   val var4: Int = var3 - 1;
   if (var1 < var3 - 1) {
      quickSort-oBK06Vg(var0, var1, var4);
   }

   if (var3 < var2) {
      quickSort-oBK06Vg(var0, var3, var2);
   }
}

internal fun sortArray(array: ULongArray, fromIndex: Int, toIndex: Int) {
   quickSort--nroSd4(var0, var1, var2 - 1);
}

internal fun sortArray(array: UByteArray, fromIndex: Int, toIndex: Int) {
   quickSort-4UcCI2c(var0, var1, var2 - 1);
}

internal fun sortArray(array: UShortArray, fromIndex: Int, toIndex: Int) {
   quickSort-Aa5vz7o(var0, var1, var2 - 1);
}

internal fun sortArray(array: UIntArray, fromIndex: Int, toIndex: Int) {
   quickSort-oBK06Vg(var0, var1, var2 - 1);
}
