package io.sentry.exception;

public final class InvalidSentryTraceHeaderException extends Exception {
   private static final long serialVersionUID = -8353316997083420940L;
   private final String sentryTraceHeader;

   public InvalidSentryTraceHeaderException(String var1) {
      this(var1, null);
   }

   public InvalidSentryTraceHeaderException(String var1, Throwable var2) {
      StringBuilder var3 = new StringBuilder("sentry-trace header does not conform to expected format: ");
      var3.append(var1);
      super(var3.toString(), var2);
      this.sentryTraceHeader = var1;
   }

   public String getSentryTraceHeader() {
      return this.sentryTraceHeader;
   }
}
