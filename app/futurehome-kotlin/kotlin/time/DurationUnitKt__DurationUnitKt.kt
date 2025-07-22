package kotlin.time

internal class DurationUnitKt__DurationUnitKt : DurationUnitKt__DurationUnitJvmKt {
   open fun DurationUnitKt__DurationUnitKt() {
   }

   @JvmStatic
   internal fun durationUnitByIsoChar(isoChar: Char, isTimeComponent: Boolean): DurationUnit {
      val var2: DurationUnit;
      if (!var1) {
         if (var0 != 'D') {
            val var3: StringBuilder = new StringBuilder("Invalid or unsupported duration ISO non-time unit: ");
            var3.append(var0);
            throw new IllegalArgumentException(var3.toString());
         }

         var2 = DurationUnit.DAYS;
      } else if (var0 != 'H') {
         if (var0 != 'M') {
            if (var0 != 'S') {
               val var4: StringBuilder = new StringBuilder("Invalid duration ISO time unit: ");
               var4.append(var0);
               throw new IllegalArgumentException(var4.toString());
            }

            var2 = DurationUnit.SECONDS;
         } else {
            var2 = DurationUnit.MINUTES;
         }
      } else {
         var2 = DurationUnit.HOURS;
      }

      return var2;
   }

   @JvmStatic
   internal fun durationUnitByShortName(shortName: String): DurationUnit {
      val var1: Int = var0.hashCode();
      if (var1 != 100) {
         if (var1 != 104) {
            if (var1 != 109) {
               if (var1 != 115) {
                  if (var1 != 3494) {
                     if (var1 != 3525) {
                        if (var1 == 3742 && var0.equals("us")) {
                           return DurationUnit.MICROSECONDS;
                        }
                     } else if (var0.equals("ns")) {
                        return DurationUnit.NANOSECONDS;
                     }
                  } else if (var0.equals("ms")) {
                     return DurationUnit.MILLISECONDS;
                  }
               } else if (var0.equals("s")) {
                  return DurationUnit.SECONDS;
               }
            } else if (var0.equals("m")) {
               return DurationUnit.MINUTES;
            }
         } else if (var0.equals("h")) {
            return DurationUnit.HOURS;
         }
      } else if (var0.equals("d")) {
         return DurationUnit.DAYS;
      }

      val var2: StringBuilder = new StringBuilder("Unknown duration unit short name: ");
      var2.append(var0);
      throw new IllegalArgumentException(var2.toString());
   }

   @JvmStatic
   internal fun DurationUnit.shortName(): String {
      var var2: java.lang.String;
      switch (DurationUnitKt__DurationUnitKt.WhenMappings.$EnumSwitchMapping$0[var0.ordinal()]) {
         case 1:
            var2 = "ns";
            break;
         case 2:
            var2 = "us";
            break;
         case 3:
            var2 = "ms";
            break;
         case 4:
            var2 = "s";
            break;
         case 5:
            var2 = "m";
            break;
         case 6:
            var2 = "h";
            break;
         case 7:
            var2 = "d";
            break;
         default:
            val var1: StringBuilder = new StringBuilder("Unknown unit: ");
            var1.append(var0);
            throw new IllegalStateException(var1.toString().toString());
      }

      return var2;
   }
}
