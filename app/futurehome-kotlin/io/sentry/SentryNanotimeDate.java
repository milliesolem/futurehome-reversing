package io.sentry;

import java.util.Date;

public final class SentryNanotimeDate extends SentryDate {
   private final Date date;
   private final long nanos;

   public SentryNanotimeDate() {
      this(DateUtils.getCurrentDateTime(), System.nanoTime());
   }

   public SentryNanotimeDate(Date var1, long var2) {
      this.date = var1;
      this.nanos = var2;
   }

   private long nanotimeDiff(SentryNanotimeDate var1, SentryNanotimeDate var2) {
      long var3 = var2.nanos;
      long var5 = var1.nanos;
      return var1.nanoTimestamp() + (var3 - var5);
   }

   @Override
   public int compareTo(SentryDate var1) {
      if (var1 instanceof SentryNanotimeDate) {
         SentryNanotimeDate var6 = (SentryNanotimeDate)var1;
         long var4 = this.date.getTime();
         long var2 = var6.date.getTime();
         return var4 == var2 ? Long.valueOf(this.nanos).compareTo(var6.nanos) : Long.valueOf(var4).compareTo(var2);
      } else {
         return super.compareTo(var1);
      }
   }

   @Override
   public long diff(SentryDate var1) {
      if (var1 instanceof SentryNanotimeDate) {
         SentryNanotimeDate var2 = (SentryNanotimeDate)var1;
         return this.nanos - var2.nanos;
      } else {
         return super.diff(var1);
      }
   }

   @Override
   public long laterDateNanosTimestampByDiff(SentryDate var1) {
      if (var1 != null && var1 instanceof SentryNanotimeDate) {
         SentryNanotimeDate var2 = (SentryNanotimeDate)var1;
         return this.compareTo(var1) < 0 ? this.nanotimeDiff(this, var2) : this.nanotimeDiff(var2, this);
      } else {
         return super.laterDateNanosTimestampByDiff(var1);
      }
   }

   @Override
   public long nanoTimestamp() {
      return DateUtils.dateToNanos(this.date);
   }
}
