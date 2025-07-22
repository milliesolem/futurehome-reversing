package io.sentry;

public interface ILogger {
   boolean isEnabled(SentryLevel var1);

   void log(SentryLevel var1, String var2, Throwable var3);

   void log(SentryLevel var1, String var2, Object... var3);

   void log(SentryLevel var1, Throwable var2, String var3, Object... var4);
}
