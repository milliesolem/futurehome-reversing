package io.sentry.util;

public final class ExceptionUtils {
   public static Throwable findRootCause(Throwable var0) {
      Objects.requireNonNull(var0, "throwable cannot be null");

      while (var0.getCause() != null && var0.getCause() != var0) {
         var0 = var0.getCause();
      }

      return var0;
   }
}
