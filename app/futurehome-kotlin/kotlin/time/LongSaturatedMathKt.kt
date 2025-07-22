package kotlin.time

private fun checkInfiniteSumDefined(value: Long, duration: Duration, durationInUnit: Long): Long {
   if (Duration.isInfinite-impl(var2) && (var0 xor var4) < 0L) {
      throw new IllegalArgumentException("Summing infinities of different signs");
   } else {
      return var0;
   }
}

private fun infinityOfSign(value: Long): Duration {
   if (var0 < 0L) {
      var0 = Duration.Companion.getNEG_INFINITE-UwyO8pc$kotlin_stdlib();
   } else {
      var0 = Duration.Companion.getINFINITE-UwyO8pc();
   }

   return var0;
}

internal inline fun Long.isSaturated(): Boolean {
   val var2: Boolean;
   if ((var0 - 1L or 1L) == java.lang.Long.MAX_VALUE) {
      var2 = true;
   } else {
      var2 = false;
   }

   return var2;
}

internal fun saturatingAdd(value: Long, unit: DurationUnit, duration: Duration): Long {
   val var7: Long = Duration.toLong-impl(var3, var2);
   if ((var0 - 1L or 1L) == java.lang.Long.MAX_VALUE) {
      return checkInfiniteSumDefined-PjuGub4(var0, var3, var7);
   } else if ((1L or var7 - 1L) == java.lang.Long.MAX_VALUE) {
      return saturatingAddInHalves-NuflL3o(var0, var2, var3);
   } else {
      var3 = var0 + var7;
      if (((var0 xor var0 + var7) and (var7 xor var0 + var7)) < 0L) {
         var3 = java.lang.Long.MAX_VALUE;
         if (var0 < 0L) {
            var3 = java.lang.Long.MIN_VALUE;
         }

         return var3;
      } else {
         return var3;
      }
   }
}

private fun saturatingAddInHalves(value: Long, unit: DurationUnit, duration: Duration): Long {
   val var7: Long = Duration.div-UwyO8pc(var3, 2);
   val var5: Long = Duration.toLong-impl(var7, var2);
   return if ((1L or var5 - 1L) == java.lang.Long.MAX_VALUE)
      var5
      else
      saturatingAdd-NuflL3o(saturatingAdd-NuflL3o(var0, var2, var7), var2, Duration.minus-LRDsOJo(var3, var7));
}

internal fun saturatingDiff(valueNs: Long, origin: Long, unit: DurationUnit): Duration {
   return if ((1L or var2 - 1L) == java.lang.Long.MAX_VALUE) Duration.unaryMinus-UwyO8pc(infinityOfSign(var2)) else saturatingFiniteDiff(var0, var2, var4);
}

private fun saturatingFiniteDiff(value1: Long, value2: Long, unit: DurationUnit): Duration {
   var var5: Long = var0 - var2;
   if (((var0 - var2 xor var0) and (var0 - var2 xor var2).inv()) < 0L) {
      if (var4.compareTo(DurationUnit.MILLISECONDS) < 0) {
         var5 = DurationUnitKt.convertDurationUnit(1L, DurationUnit.MILLISECONDS, var4);
         val var9: Long = var0 / var5;
         val var7: Long = var2 / var5;
         val var11: Duration.Companion = Duration.Companion;
         return Duration.plus-LRDsOJo(DurationKt.toDuration(var9 - var7, DurationUnit.MILLISECONDS), DurationKt.toDuration(var0 % var5 - var2 % var5, var4));
      } else {
         return Duration.unaryMinus-UwyO8pc(infinityOfSign(var5));
      }
   } else {
      return DurationKt.toDuration(var5, var4);
   }
}

internal fun saturatingOriginsDiff(origin1: Long, origin2: Long, unit: DurationUnit): Duration {
   if ((var2 - 1L or 1L) == java.lang.Long.MAX_VALUE) {
      return if (var0 == var2) Duration.Companion.getZERO-UwyO8pc() else Duration.unaryMinus-UwyO8pc(infinityOfSign(var2));
   } else {
      return if ((1L or var0 - 1L) == java.lang.Long.MAX_VALUE) infinityOfSign(var0) else saturatingFiniteDiff(var0, var2, var4);
   }
}
