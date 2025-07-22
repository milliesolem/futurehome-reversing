package io.sentry.android.core;

import io.sentry.util.Objects;

final class ApplicationNotResponding extends RuntimeException {
   private static final long serialVersionUID = 252541144579117016L;
   private final Thread thread;

   ApplicationNotResponding(String var1, Thread var2) {
      super(var1);
      Thread var3 = Objects.requireNonNull(var2, "Thread must be provided.");
      this.thread = var3;
      this.setStackTrace(var3.getStackTrace());
   }

   public Thread getThread() {
      return this.thread;
   }
}
