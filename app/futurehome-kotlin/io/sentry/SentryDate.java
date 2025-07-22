package io.sentry;

public abstract class SentryDate implements Comparable<SentryDate> {
   public int compareTo(SentryDate var1) {
      return Long.valueOf(this.nanoTimestamp()).compareTo(var1.nanoTimestamp());
   }

   public long diff(SentryDate var1) {
      return this.nanoTimestamp() - var1.nanoTimestamp();
   }

   public final boolean isAfter(SentryDate var1) {
      boolean var2;
      if (this.diff(var1) > 0L) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public final boolean isBefore(SentryDate var1) {
      boolean var2;
      if (this.diff(var1) < 0L) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public long laterDateNanosTimestampByDiff(SentryDate var1) {
      return var1 != null && this.compareTo(var1) < 0 ? var1.nanoTimestamp() : this.nanoTimestamp();
   }

   public abstract long nanoTimestamp();
}
