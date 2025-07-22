package io.sentry.util;

import io.sentry.ILogger;
import io.sentry.SentryLevel;

public final class LogUtils {
   public static void logNotInstanceOf(Class<?> var0, Object var1, ILogger var2) {
      SentryLevel var3 = SentryLevel.DEBUG;
      if (var1 != null) {
         var1 = var1.getClass().getCanonicalName();
      } else {
         var1 = "Hint";
      }

      var2.log(var3, "%s is not %s", var1, var0.getCanonicalName());
   }
}
