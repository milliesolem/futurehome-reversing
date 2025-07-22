package com.signify.hue.flutterreactiveble.converters

import android.util.SparseArray
import java.util.ArrayList

public fun extractManufacturerData(manufacturerData: SparseArray<ByteArray>?): ByteArray {
   val var2: java.util.List = new ArrayList();
   if (var0 != null && var0.size() > 0) {
      val var1: Int = var0.keyAt(0);
      val var3: ByteArray = var0.get(var1) as ByteArray;
      var2.add((byte)var1);
      var2.add((byte)(var1 shr 8));
      var2.addAll(2, ArraysKt.asList(var3));
   }

   return CollectionsKt.toByteArray(var2);
}
