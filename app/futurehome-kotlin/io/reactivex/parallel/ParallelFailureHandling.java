package io.reactivex.parallel;

import io.reactivex.functions.BiFunction;

public enum ParallelFailureHandling implements BiFunction<Long, Throwable, ParallelFailureHandling> {
   ERROR,
   RETRY,
   SKIP,
   STOP;
   private static final ParallelFailureHandling[] $VALUES;

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static {
      ParallelFailureHandling var1 = new ParallelFailureHandling();
      STOP = var1;
      ParallelFailureHandling var2 = new ParallelFailureHandling();
      ERROR = var2;
      ParallelFailureHandling var0 = new ParallelFailureHandling();
      SKIP = var0;
      ParallelFailureHandling var3 = new ParallelFailureHandling();
      RETRY = var3;
      $VALUES = new ParallelFailureHandling[]{var1, var2, var0, var3};
   }

   public ParallelFailureHandling apply(Long var1, Throwable var2) {
      return this;
   }
}
