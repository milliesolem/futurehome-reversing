package kotlin.time

import kotlin.math.MathKt

internal const val NANOS_IN_MILLIS: Int = 1000000
internal const val MAX_NANOS: Long = 4611686018426999999L
internal const val MAX_MILLIS: Long = 4611686018427387903L
private const val MAX_NANOS_IN_MILLIS: Long = 4611686018426L

@JvmSynthetic
fun `access$durationOf`(var0: Long, var2: Int): Long {
   return durationOf(var0, var2);
}

@JvmSynthetic
fun `access$durationOfMillis`(var0: Long): Long {
   return durationOfMillis(var0);
}

@JvmSynthetic
fun `access$durationOfMillisNormalized`(var0: Long): Long {
   return durationOfMillisNormalized(var0);
}

@JvmSynthetic
fun `access$durationOfNanos`(var0: Long): Long {
   return durationOfNanos(var0);
}

@JvmSynthetic
fun `access$durationOfNanosNormalized`(var0: Long): Long {
   return durationOfNanosNormalized(var0);
}

@JvmSynthetic
fun `access$millisToNanos`(var0: Long): Long {
   return millisToNanos(var0);
}

@JvmSynthetic
fun `access$nanosToMillis`(var0: Long): Long {
   return nanosToMillis(var0);
}

@JvmSynthetic
fun `access$parseDuration`(var0: java.lang.String, var1: Boolean): Long {
   return parseDuration(var0, var1);
}

private fun durationOf(normalValue: Long, unitDiscriminator: Int): Duration {
   return Duration.constructor-impl((var0 shl 1) + (long)var2);
}

private fun durationOfMillis(normalMillis: Long): Duration {
   return Duration.constructor-impl((var0 shl 1) + 1L);
}

private fun durationOfMillisNormalized(millis: Long): Duration {
   if (-4611686018426L <= var0 && var0 < 4611686018427L) {
      var0 = durationOfNanos(millisToNanos(var0));
   } else {
      var0 = durationOfMillis(RangesKt.coerceIn(var0, -4611686018427387903L, 4611686018427387903L));
   }

   return var0;
}

private fun durationOfNanos(normalNanos: Long): Duration {
   return Duration.constructor-impl(var0 shl 1);
}

private fun durationOfNanosNormalized(nanos: Long): Duration {
   if (-4611686018426999999L <= var0 && var0 < 4611686018427000000L) {
      var0 = durationOfNanos(var0);
   } else {
      var0 = durationOfMillis(nanosToMillis(var0));
   }

   return var0;
}

private fun millisToNanos(millis: Long): Long {
   return var0 * 1000000;
}

private fun nanosToMillis(nanos: Long): Long {
   return var0 / 1000000;
}

private fun parseDuration(value: String, strictIso: Boolean): Duration {
   val var9: Int = var0.length();
   if (var9 == 0) {
      throw new IllegalArgumentException("The string is empty");
   } else {
      var var11: Long = Duration.Companion.getZERO-UwyO8pc();
      var var3: Int = var0.charAt(0);
      if (var3 != 43 && var3 != 45) {
         var3 = 0;
      } else {
         var3 = 1;
      }

      var var8: Boolean;
      if (var3 > 0) {
         var8 = '\u0001';
      } else {
         var8 = '\u0000';
      }

      var var4: Boolean;
      if (var8 && StringsKt.startsWith$default(var0, '-', false, 2, null)) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      if (var9 <= var3) {
         throw new IllegalArgumentException("No components");
      } else {
         if (var0.charAt(var3) != 'P') {
            val var35: Boolean = (boolean)var4;
            if (var1) {
               throw new IllegalArgumentException();
            }

            if (StringsKt.regionMatches(var0, var3, "Infinity", 0, Math.max(var9 - var3, 8), true)) {
               var11 = Duration.Companion.getINFINITE-UwyO8pc();
               var4 = var4;
            } else {
               val var10: Boolean = (boolean)(var8 xor true);
               var var6: Boolean = (boolean)(var8 xor true);
               var var7: Int = var9;
               var4 = var3;
               if (var8) {
                  var6 = var10;
                  var7 = var9;
                  var4 = var3;
                  if (var0.charAt(var3) == '(') {
                     var6 = var10;
                     var7 = var9;
                     var4 = var3;
                     if (StringsKt.last(var0) == ')') {
                        var4 = var3 + 1;
                        var7 = var9 - 1;
                        if (var4 == var9 - 1) {
                           throw new IllegalArgumentException("No components");
                        }

                        var6 = true;
                     }
                  }
               }

               var var46: DurationUnit = null;
               var8 = (boolean)'\u0000';
               var3 = var4;
               var var13: Long = var11;

               while (true) {
                  var11 = var13;
                  var4 = var35;
                  if (var3 >= var7) {
                     break;
                  }

                  var4 = var3;
                  if (var8) {
                     var4 = var3;
                     if (var6) {
                        while (true) {
                           var4 = var3;
                           if (var3 >= var0.length()) {
                              break;
                           }

                           var4 = var3;
                           if (var0.charAt(var3) != ' ') {
                              break;
                           }

                           var3++;
                        }
                     }
                  }

                  for (var3 = var4; var3 < var0.length(); var3++) {
                     var8 = var0.charAt(var3);
                     if (('0' > var8 || var8 >= ':') && var8 != '.') {
                        break;
                     }
                  }

                  val var51: java.lang.String = var0.substring(var4, var3);
                  val var19: java.lang.CharSequence = var51;
                  if (var51.length() == 0) {
                     throw new IllegalArgumentException();
                  }

                  var4 = var4 + var51.length();

                  for (var3 = var4; var3 < var0.length(); var3++) {
                     var8 = var0.charAt(var3);
                     if ('a' > var8 || var8 >= '{') {
                        break;
                     }
                  }

                  val var49: java.lang.String = var0.substring(var4, var3);
                  var3 = var4 + var49.length();
                  val var50: DurationUnit = DurationUnitKt.durationUnitByShortName(var49);
                  if (var46 != null && var46.compareTo(var50) <= 0) {
                     throw new IllegalArgumentException("Unexpected order of duration components");
                  }

                  var4 = StringsKt.indexOf$default(var19, '.', 0, false, 6, null);
                  if (var4 > 0) {
                     val var47: java.lang.String = var51.substring(0, var4);
                     var11 = Duration.plus-LRDsOJo(var13, toDuration(java.lang.Long.parseLong(var47), var50));
                     val var48: java.lang.String = var51.substring(var4);
                     var13 = Duration.plus-LRDsOJo(var11, toDuration(java.lang.Double.parseDouble(var48), var50));
                     if (var3 < var7) {
                        throw new IllegalArgumentException("Fractional component must be last");
                     }
                  } else {
                     var13 = Duration.plus-LRDsOJo(var13, toDuration(java.lang.Long.parseLong(var51), var50));
                  }

                  var46 = var50;
                  var8 = (boolean)'\u0001';
               }
            }
         } else {
            if (++var3 == var9) {
               throw new IllegalArgumentException();
            }

            var var15: DurationUnit = null;
            var1 = false;

            while (var3 < var9) {
               if (var0.charAt(var3) == 'T') {
                  if (var1 || ++var3 == var9) {
                     throw new IllegalArgumentException();
                  }

                  var1 = true;
               } else {
                  var var5: Int;
                  for (var5 = var3; var5 < var0.length(); var5++) {
                     val var2: Char = var0.charAt(var5);
                     if (('0' > var2 || var2 >= ':') && !StringsKt.contains$default("+-.", var2, false, 2, null)) {
                        break;
                     }
                  }

                  val var17: java.lang.String = var0.substring(var3, var5);
                  val var18: java.lang.CharSequence = var17;
                  if (var17.length() == 0) {
                     throw new IllegalArgumentException();
                  }

                  var3 += var17.length();
                  val var16: java.lang.CharSequence = var0;
                  if (var3 < 0 || var3 >= var0.length()) {
                     val var20: StringBuilder = new StringBuilder("Missing unit for value ");
                     var20.append(var17);
                     throw new IllegalArgumentException(var20.toString());
                  }

                  val var22: Char = var16.charAt(var3);
                  var3++;
                  val var45: DurationUnit = DurationUnitKt.durationUnitByIsoChar(var22, var1);
                  if (var15 != null && var15.compareTo(var45) <= 0) {
                     throw new IllegalArgumentException("Unexpected order of duration components");
                  }

                  var5 = StringsKt.indexOf$default(var18, '.', 0, false, 6, null);
                  if (var45 === DurationUnit.SECONDS && var5 > 0) {
                     val var42: java.lang.String = var17.substring(0, var5);
                     var11 = Duration.plus-LRDsOJo(var11, toDuration(parseOverLongIsoComponent(var42), var45));
                     val var43: java.lang.String = var17.substring(var5);
                     var11 = Duration.plus-LRDsOJo(var11, toDuration(java.lang.Double.parseDouble(var43), var45));
                  } else {
                     var11 = Duration.plus-LRDsOJo(var11, toDuration(parseOverLongIsoComponent(var17), var45));
                  }

                  var15 = var45;
               }
            }
         }

         var var41: Long = var11;
         if (var4) {
            var41 = Duration.unaryMinus-UwyO8pc(var11);
         }

         return var41;
      }
   }
}

private fun parseOverLongIsoComponent(value: String): Long {
   val var2: Int = var0.length();
   val var1: Byte;
   if (var2 > 0 && StringsKt.contains$default("+-", var0.charAt(0), false, 2, null)) {
      var1 = 1;
   } else {
      var1 = 0;
   }

   label50: {
      if (var2 - var1 > 16) {
         val var5: java.lang.Iterable = new IntRange(var1, StringsKt.getLastIndex(var0));
         if (var5 is java.util.Collection && (var5 as java.util.Collection).isEmpty()) {
            break label50;
         }

         val var7: java.util.Iterator = var5.iterator();

         do {
            if (!var7.hasNext()) {
               break label50;
            }

            var6 = var0.charAt((var7 as IntIterator).nextInt());
         } while ('0' <= var6 && var6 < ':');
      }

      var var8: java.lang.String = var0;
      if (StringsKt.startsWith$default(var0, "+", false, 2, null)) {
         var8 = StringsKt.drop(var0, 1);
      }

      return java.lang.Long.parseLong(var8);
   }

   val var3: Long;
   if (var0.charAt(0) == '-') {
      var3 = java.lang.Long.MIN_VALUE;
   } else {
      var3 = java.lang.Long.MAX_VALUE;
   }

   return var3;
}

private inline fun String.skipWhile(startIndex: Int, predicate: (Char) -> Boolean): Int {
   while (var1 < var0.length() && var2.invoke(var0.charAt(var1))) {
      var1++;
   }

   return var1;
}

private inline fun String.substringWhile(startIndex: Int, predicate: (Char) -> Boolean): String {
   var var3: Int = var1;

   while (var3 < var0.length() && var2.invoke(var0.charAt(var3))) {
      var3++;
   }

   var0 = var0.substring(var1, var3);
   return var0;
}

public inline operator fun Double.times(duration: Duration): Duration {
   return Duration.times-UwyO8pc(var2, var0);
}

public inline operator fun Int.times(duration: Duration): Duration {
   return Duration.times-UwyO8pc(var1, var0);
}

public fun Double.toDuration(unit: DurationUnit): Duration {
   val var3: Double = DurationUnitKt.convertDurationUnit(var0, var2, DurationUnit.NANOSECONDS);
   if (java.lang.Double.isNaN(var3)) {
      throw new IllegalArgumentException("Duration value cannot be NaN.".toString());
   } else {
      var var5: Long = MathKt.roundToLong(var3);
      if (-4611686018426999999L <= var5 && var5 < 4611686018427000000L) {
         var5 = durationOfNanos(var5);
      } else {
         var5 = durationOfMillisNormalized(MathKt.roundToLong(DurationUnitKt.convertDurationUnit(var0, var2, DurationUnit.MILLISECONDS)));
      }

      return var5;
   }
}

public fun Int.toDuration(unit: DurationUnit): Duration {
   val var2: Long;
   if (var1.compareTo(DurationUnit.SECONDS) <= 0) {
      var2 = durationOfNanos(DurationUnitKt.convertDurationUnitOverflow((long)var0, var1, DurationUnit.NANOSECONDS));
   } else {
      var2 = toDuration((long)var0, var1);
   }

   return var2;
}

public fun Long.toDuration(unit: DurationUnit): Duration {
   val var3: Long = DurationUnitKt.convertDurationUnitOverflow(4611686018426999999L, DurationUnit.NANOSECONDS, var2);
   return if (-var3 <= var0 && var0 <= var3)
      durationOfNanos(DurationUnitKt.convertDurationUnitOverflow(var0, var2, DurationUnit.NANOSECONDS))
      else
      durationOfMillis(
         RangesKt.coerceIn(DurationUnitKt.convertDurationUnit(var0, var2, DurationUnit.MILLISECONDS), -4611686018427387903L, 4611686018427387903L)
      );
}
