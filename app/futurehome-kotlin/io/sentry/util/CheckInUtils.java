package io.sentry.util;

import io.sentry.CheckIn;
import io.sentry.CheckInStatus;
import io.sentry.DateUtils;
import io.sentry.IHub;
import io.sentry.MonitorConfig;
import io.sentry.Sentry;
import io.sentry.protocol.SentryId;
import java.util.List;
import java.util.concurrent.Callable;

public final class CheckInUtils {
   public static boolean isIgnored(List<String> var0, String var1) {
      if (var0 != null && !var0.isEmpty()) {
         for (String var3 : var0) {
            if (var3.equalsIgnoreCase(var1)) {
               return true;
            }

            boolean var2;
            try {
               var2 = var1.matches(var3);
            } finally {
               continue;
            }

            if (var2) {
               return true;
            }
         }
      }

      return false;
   }

   public static <U> U withCheckIn(String var0, MonitorConfig var1, Callable<U> var2) throws Exception {
      IHub var5 = Sentry.getCurrentHub();
      long var3 = System.currentTimeMillis();
      var5.pushScope();
      TracingUtils.startNewTrace(var5);
      CheckIn var6 = new CheckIn(var0, CheckInStatus.IN_PROGRESS);
      if (var1 != null) {
         var6.setMonitorConfig(var1);
      }

      SentryId var13 = var5.captureCheckIn(var6);

      try {
         var14 = var2.call();
      } finally {
         ;
      }

      CheckIn var12 = new CheckIn(var13, var0, CheckInStatus.OK);
      var12.setDuration(DateUtils.millisToSeconds(System.currentTimeMillis() - var3));
      var5.captureCheckIn(var12);
      var5.popScope();
      return (U)var14;
   }

   public static <U> U withCheckIn(String var0, Callable<U> var1) throws Exception {
      return withCheckIn(var0, null, var1);
   }
}
