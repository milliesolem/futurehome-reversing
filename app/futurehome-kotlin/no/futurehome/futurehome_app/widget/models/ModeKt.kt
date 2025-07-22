package no.futurehome.futurehome_app.widget.models

public fun modeToString(mode: Mode): String {
   val var1: Int = ModeKt.WhenMappings.$EnumSwitchMapping$0[var0.ordinal()];
   val var2: java.lang.String;
   if (var1 != 1) {
      if (var1 != 2) {
         if (var1 != 3) {
            if (var1 != 4) {
               throw new NoWhenBranchMatchedException();
            }

            var2 = "vacation";
         } else {
            var2 = "sleep";
         }
      } else {
         var2 = "away";
      }
   } else {
      var2 = "home";
   }

   return var2;
}
// $VF: Class flags could not be determined
@JvmSynthetic
internal class WhenMappings {
   @JvmStatic
   public int[] $EnumSwitchMapping$0;

   @JvmStatic
   fun {
      val var0: IntArray = new int[Mode.values().length];

      try {
         var0[Mode.HOME.ordinal()] = 1;
      } catch (var5: NoSuchFieldError) {
      }

      try {
         var0[Mode.AWAY.ordinal()] = 2;
      } catch (var4: NoSuchFieldError) {
      }

      try {
         var0[Mode.SLEEP.ordinal()] = 3;
      } catch (var3: NoSuchFieldError) {
      }

      try {
         var0[Mode.VACATION.ordinal()] = 4;
      } catch (var2: NoSuchFieldError) {
      }

      $EnumSwitchMapping$0 = var0;
   }
}
