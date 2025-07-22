package io.sentry;

public final class NoOpLogger implements ILogger {
   private static final NoOpLogger instance = new NoOpLogger();

   private NoOpLogger() {
   }

   public static NoOpLogger getInstance() {
      return instance;
   }

   @Override
   public boolean isEnabled(SentryLevel var1) {
      return false;
   }

   @Override
   public void log(SentryLevel var1, String var2, Throwable var3) {
   }

   @Override
   public void log(SentryLevel var1, String var2, Object... var3) {
   }

   @Override
   public void log(SentryLevel var1, Throwable var2, String var3, Object... var4) {
   }
}
