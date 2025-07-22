package kotlin.random

import kotlin.internal.PlatformImplementationsKt

public fun Random.asJavaRandom(): java.util.Random {
   val var1: AbstractPlatformRandom;
   if (var0 is AbstractPlatformRandom) {
      var1 = var0 as AbstractPlatformRandom;
   } else {
      var1 = null;
   }

   if (var1 != null) {
      val var2: java.util.Random = var1.getImpl();
      if (var2 != null) {
         return var2;
      }
   }

   return new KotlinRandom(var0);
}

public fun java.util.Random.asKotlinRandom(): Random {
   val var1: KotlinRandom;
   if (var0 is KotlinRandom) {
      var1 = var0 as KotlinRandom;
   } else {
      var1 = null;
   }

   if (var1 != null) {
      val var2: Random = var1.getImpl();
      if (var2 != null) {
         return var2;
      }
   }

   return new PlatformRandom(var0);
}

internal inline fun defaultPlatformRandom(): Random {
   return PlatformImplementationsKt.IMPLEMENTATIONS.defaultPlatformRandom();
}

internal fun doubleFromParts(hi26: Int, low27: Int): Double {
   return (((long)var0 shl 27) + var1) / 9.007199E15F;
}
