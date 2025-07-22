package com.signify.hue.flutterreactiveble.model

internal fun createScanMode(mode: Int): ScanMode {
   val var1: ScanMode;
   if (var0 != -1) {
      if (var0 != 0) {
         if (var0 != 1) {
            if (var0 != 2) {
               var1 = ScanMode.LOW_POWER;
            } else {
               var1 = ScanMode.LOW_LATENCY;
            }
         } else {
            var1 = ScanMode.BALANCED;
         }
      } else {
         var1 = ScanMode.LOW_POWER;
      }
   } else {
      var1 = ScanMode.OPPORTUNISTIC;
   }

   return var1;
}

internal fun ScanMode.toScanSettings(): Int {
   val var2: Int = ScanModeKt.WhenMappings.$EnumSwitchMapping$0[var0.ordinal()];
   var var1: Byte = 1;
   if (var2 != 1) {
      if (var2 != 2) {
         if (var2 != 3) {
            if (var2 != 4) {
               throw new NoWhenBranchMatchedException();
            }

            var1 = 2;
         }
      } else {
         var1 = 0;
      }
   } else {
      var1 = -1;
   }

   return var1;
}
// $VF: Class flags could not be determined
@JvmSynthetic
internal class WhenMappings {
   @JvmStatic
   public int[] $EnumSwitchMapping$0;

   @JvmStatic
   fun {
      val var0: IntArray = new int[ScanMode.values().length];

      try {
         var0[ScanMode.OPPORTUNISTIC.ordinal()] = 1;
      } catch (var5: NoSuchFieldError) {
      }

      try {
         var0[ScanMode.LOW_POWER.ordinal()] = 2;
      } catch (var4: NoSuchFieldError) {
      }

      try {
         var0[ScanMode.BALANCED.ordinal()] = 3;
      } catch (var3: NoSuchFieldError) {
      }

      try {
         var0[ScanMode.LOW_LATENCY.ordinal()] = 4;
      } catch (var2: NoSuchFieldError) {
      }

      $EnumSwitchMapping$0 = var0;
   }
}
