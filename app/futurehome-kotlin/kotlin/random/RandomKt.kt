package kotlin.random

public fun Random(seed: Int): Random {
   return new XorWowRandom(var0, var0 shr 31);
}

public fun Random(seed: Long): Random {
   return new XorWowRandom((int)var0, (int)(var0 shr 32));
}

internal fun boundsErrorMessage(from: Any, until: Any): String {
   val var2: StringBuilder = new StringBuilder("Random range is empty: [");
   var2.append(var0);
   var2.append(", ");
   var2.append(var1);
   var2.append(").");
   return var2.toString();
}

internal fun checkRangeBounds(from: Double, until: Double) {
   if (!(var2 > var0)) {
      throw new IllegalArgumentException(boundsErrorMessage(var0, var2).toString());
   }
}

internal fun checkRangeBounds(from: Int, until: Int) {
   if (var1 <= var0) {
      throw new IllegalArgumentException(boundsErrorMessage(var0, var1).toString());
   }
}

internal fun checkRangeBounds(from: Long, until: Long) {
   if (var2 <= var0) {
      throw new IllegalArgumentException(boundsErrorMessage(var0, var2).toString());
   }
}

internal fun fastLog2(value: Int): Int {
   return 31 - Integer.numberOfLeadingZeros(var0);
}

public fun Random.nextInt(range: IntRange): Int {
   if (!var1.isEmpty()) {
      val var2: Int;
      if (var1.getLast() < Integer.MAX_VALUE) {
         var2 = var0.nextInt(var1.getFirst(), var1.getLast() + 1);
      } else if (var1.getFirst() > Integer.MIN_VALUE) {
         var2 = var0.nextInt(var1.getFirst() - 1, var1.getLast()) + 1;
      } else {
         var2 = var0.nextInt();
      }

      return var2;
   } else {
      val var3: StringBuilder = new StringBuilder("Cannot get random in empty range: ");
      var3.append(var1);
      throw new IllegalArgumentException(var3.toString());
   }
}

public fun Random.nextLong(range: LongRange): Long {
   if (!var1.isEmpty()) {
      val var2: Long;
      if (var1.getLast() < java.lang.Long.MAX_VALUE) {
         var2 = var0.nextLong(var1.getFirst(), var1.getLast() + 1L);
      } else if (var1.getFirst() > java.lang.Long.MIN_VALUE) {
         var2 = var0.nextLong(var1.getFirst() - 1L, var1.getLast()) + 1L;
      } else {
         var2 = var0.nextLong();
      }

      return var2;
   } else {
      val var4: StringBuilder = new StringBuilder("Cannot get random in empty range: ");
      var4.append(var1);
      throw new IllegalArgumentException(var4.toString());
   }
}

internal fun Int.takeUpperBits(bitCount: Int): Int {
   return var0 ushr 32 - var1 and -var1 shr 31;
}
