package io.sentry;

import j..time.Instant;

public final class SentryInstantDate extends SentryDate {
   private final Instant date;

   public SentryInstantDate() {
      this(Instant.now());
   }

   public SentryInstantDate(Instant var1) {
      this.date = var1;
   }

   @Override
   public long nanoTimestamp() {
      return DateUtils.secondsToNanos(this.date.getEpochSecond()) + this.date.getNano();
   }
}
