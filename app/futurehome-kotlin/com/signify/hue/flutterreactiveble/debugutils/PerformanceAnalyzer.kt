package com.signify.hue.flutterreactiveble.debugutils

public object PerformanceAnalyzer {
   public final var timer: Pair<Long, Long>
      internal set

   @JvmStatic
   fun {
      val var0: java.lang.Long = 0L;
      timer = new Pair<>(var0, var0);
   }

   public fun end(endTime: Long) {
      timer = Pair.copy$default(timer, null, var1, 1, null);
   }

   public fun start(startTime: Long) {
      timer = Pair.copy$default(timer, var1, null, 2, null);
   }

   public fun timeElapsed(): Long {
      return timer.getSecond().longValue() - timer.getFirst().longValue();
   }
}
