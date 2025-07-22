package com.polidea.rxandroidble2.internal.operations;

import io.reactivex.Scheduler;
import java.util.concurrent.TimeUnit;

public class TimeoutConfiguration {
   public final long timeout;
   public final Scheduler timeoutScheduler;
   public final TimeUnit timeoutTimeUnit;

   public TimeoutConfiguration(long var1, TimeUnit var3, Scheduler var4) {
      this.timeout = var1;
      this.timeoutTimeUnit = var3;
      this.timeoutScheduler = var4;
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("{value=");
      var1.append(this.timeout);
      var1.append(", timeUnit=");
      var1.append(this.timeoutTimeUnit);
      var1.append('}');
      return var1.toString();
   }
}
