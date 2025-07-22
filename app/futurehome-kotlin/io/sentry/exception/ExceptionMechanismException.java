package io.sentry.exception;

import io.sentry.protocol.Mechanism;
import io.sentry.util.Objects;

public final class ExceptionMechanismException extends RuntimeException {
   private static final long serialVersionUID = 142345454265713915L;
   private final Mechanism exceptionMechanism;
   private final boolean snapshot;
   private final Thread thread;
   private final Throwable throwable;

   public ExceptionMechanismException(Mechanism var1, Throwable var2, Thread var3) {
      this(var1, var2, var3, false);
   }

   public ExceptionMechanismException(Mechanism var1, Throwable var2, Thread var3, boolean var4) {
      this.exceptionMechanism = Objects.requireNonNull(var1, "Mechanism is required.");
      this.throwable = Objects.requireNonNull(var2, "Throwable is required.");
      this.thread = Objects.requireNonNull(var3, "Thread is required.");
      this.snapshot = var4;
   }

   public Mechanism getExceptionMechanism() {
      return this.exceptionMechanism;
   }

   public Thread getThread() {
      return this.thread;
   }

   public Throwable getThrowable() {
      return this.throwable;
   }

   public boolean isSnapshot() {
      return this.snapshot;
   }
}
