package kotlin.internal

private fun differenceModulo(a: Int, b: Int, c: Int): Int {
   return mod(mod(var0, var2) - mod(var1, var2), var2);
}

private fun differenceModulo(a: Long, b: Long, c: Long): Long {
   return mod(mod(var0, var4) - mod(var2, var4), var4);
}

internal fun getProgressionLastElement(start: Int, end: Int, step: Int): Int {
   if (var2 > 0) {
      if (var0 < var1) {
         var1 -= differenceModulo(var1, var0, var2);
      }
   } else {
      if (var2 >= 0) {
         throw new IllegalArgumentException("Step is zero.");
      }

      if (var0 > var1) {
         var1 += differenceModulo(var0, var1, -var2);
      }
   }

   return var1;
}

internal fun getProgressionLastElement(start: Long, end: Long, step: Long): Long {
   val var7: Long;
   val var6: Byte = (byte)(if ((var7 = var4 - 0L) == 0L) 0 else (if (var7 < 0L) -1 else 1));
   if (var4 > 0L) {
      if (var0 < var2) {
         var2 -= differenceModulo(var2, var0, var4);
      }
   } else {
      if (var6 >= 0) {
         throw new IllegalArgumentException("Step is zero.");
      }

      if (var0 > var2) {
         var2 += differenceModulo(var0, var2, -var4);
      }
   }

   return var2;
}

private fun mod(a: Int, b: Int): Int {
   var0 = var0 % var1;
   if (var0 % var1 < 0) {
      var0 += var1;
   }

   return var0;
}

private fun mod(a: Long, b: Long): Long {
   var0 = var0 % var2;
   if (var0 % var2 < 0L) {
      var0 += var2;
   }

   return var0;
}
