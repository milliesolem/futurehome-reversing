package io.sentry.android.core.internal.util;

import io.sentry.transport.ICurrentDateProvider;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Debouncer {
   private final AtomicInteger executions = new AtomicInteger(0);
   private final AtomicLong lastExecutionTime = new AtomicLong(0L);
   private final int maxExecutions;
   private final ICurrentDateProvider timeProvider;
   private final long waitTimeMs;

   public Debouncer(ICurrentDateProvider var1, long var2, int var4) {
      this.timeProvider = var1;
      this.waitTimeMs = var2;
      int var5 = var4;
      if (var4 <= 0) {
         var5 = 1;
      }

      this.maxExecutions = var5;
   }

   public boolean checkForDebounce() {
      long var1 = this.timeProvider.getCurrentTimeMillis();
      if (this.lastExecutionTime.get() == 0L || this.lastExecutionTime.get() + this.waitTimeMs <= var1) {
         this.executions.set(0);
         this.lastExecutionTime.set(var1);
         return false;
      } else if (this.executions.incrementAndGet() < this.maxExecutions) {
         return false;
      } else {
         this.executions.set(0);
         return true;
      }
   }
}
