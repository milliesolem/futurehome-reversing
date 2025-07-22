package io.sentry;

import io.sentry.util.Objects;

public final class DiagnosticLogger implements ILogger {
   private final ILogger logger;
   private final SentryOptions options;

   public DiagnosticLogger(SentryOptions var1, ILogger var2) {
      this.options = Objects.requireNonNull(var1, "SentryOptions is required.");
      this.logger = var2;
   }

   public ILogger getLogger() {
      return this.logger;
   }

   @Override
   public boolean isEnabled(SentryLevel var1) {
      SentryLevel var4 = this.options.getDiagnosticLevel();
      boolean var3 = false;
      if (var1 == null) {
         return false;
      } else {
         boolean var2 = var3;
         if (this.options.isDebug()) {
            var2 = var3;
            if (var1.ordinal() >= var4.ordinal()) {
               var2 = true;
            }
         }

         return var2;
      }
   }

   @Override
   public void log(SentryLevel var1, String var2, Throwable var3) {
      if (this.logger != null && this.isEnabled(var1)) {
         this.logger.log(var1, var2, var3);
      }
   }

   @Override
   public void log(SentryLevel var1, String var2, Object... var3) {
      if (this.logger != null && this.isEnabled(var1)) {
         this.logger.log(var1, var2, var3);
      }
   }

   @Override
   public void log(SentryLevel var1, Throwable var2, String var3, Object... var4) {
      if (this.logger != null && this.isEnabled(var1)) {
         this.logger.log(var1, var2, var3, var4);
      }
   }
}
