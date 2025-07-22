package com.polidea.rxandroidble2;

import java.util.concurrent.TimeUnit;

public class Timeout {
   public final TimeUnit timeUnit;
   public final long timeout;

   public Timeout(long var1, TimeUnit var3) {
      this.timeUnit = var3;
      this.timeout = var1;
   }
}
