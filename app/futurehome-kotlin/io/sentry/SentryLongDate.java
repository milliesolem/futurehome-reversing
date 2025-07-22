package io.sentry;

public final class SentryLongDate extends SentryDate {
   private final long nanos;

   public SentryLongDate(long var1) {
      this.nanos = var1;
   }

   @Override
   public long nanoTimestamp() {
      return this.nanos;
   }
}
