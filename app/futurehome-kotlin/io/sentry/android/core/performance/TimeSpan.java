package io.sentry.android.core.performance;

import android.os.SystemClock;
import io.sentry.DateUtils;
import io.sentry.SentryDate;
import io.sentry.SentryLongDate;
import java.util.concurrent.TimeUnit;

public class TimeSpan implements Comparable<TimeSpan> {
   private String description;
   private long startSystemNanos;
   private long startUnixTimeMs;
   private long startUptimeMs;
   private long stopUptimeMs;

   public int compareTo(TimeSpan var1) {
      return Long.compare(this.startUnixTimeMs, var1.startUnixTimeMs);
   }

   public String getDescription() {
      return this.description;
   }

   public long getDurationMs() {
      return this.hasStopped() ? this.stopUptimeMs - this.startUptimeMs : 0L;
   }

   public SentryDate getProjectedStopTimestamp() {
      return this.hasStopped() ? new SentryLongDate(DateUtils.millisToNanos(this.getProjectedStopTimestampMs())) : null;
   }

   public long getProjectedStopTimestampMs() {
      return this.hasStarted() ? this.startUnixTimeMs + this.getDurationMs() : 0L;
   }

   public double getProjectedStopTimestampSecs() {
      return DateUtils.millisToSeconds(this.getProjectedStopTimestampMs());
   }

   public SentryDate getStartTimestamp() {
      return this.hasStarted() ? new SentryLongDate(DateUtils.millisToNanos(this.getStartTimestampMs())) : null;
   }

   public long getStartTimestampMs() {
      return this.startUnixTimeMs;
   }

   public double getStartTimestampSecs() {
      return DateUtils.millisToSeconds(this.startUnixTimeMs);
   }

   public long getStartUptimeMs() {
      return this.startUptimeMs;
   }

   public boolean hasNotStarted() {
      boolean var1;
      if (this.startUptimeMs == 0L) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean hasNotStopped() {
      boolean var1;
      if (this.stopUptimeMs == 0L) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean hasStarted() {
      boolean var1;
      if (this.startUptimeMs != 0L) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean hasStopped() {
      boolean var1;
      if (this.stopUptimeMs != 0L) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void reset() {
      this.description = null;
      this.startUptimeMs = 0L;
      this.stopUptimeMs = 0L;
      this.startUnixTimeMs = 0L;
      this.startSystemNanos = 0L;
   }

   public void setDescription(String var1) {
      this.description = var1;
   }

   public void setStartUnixTimeMs(long var1) {
      this.startUnixTimeMs = var1;
   }

   public void setStartedAt(long var1) {
      this.startUptimeMs = var1;
      var1 = SystemClock.uptimeMillis() - this.startUptimeMs;
      this.startUnixTimeMs = System.currentTimeMillis() - var1;
      this.startSystemNanos = System.nanoTime() - TimeUnit.MILLISECONDS.toNanos(var1);
   }

   public void setStoppedAt(long var1) {
      this.stopUptimeMs = var1;
   }

   public void start() {
      this.startUptimeMs = SystemClock.uptimeMillis();
      this.startUnixTimeMs = System.currentTimeMillis();
      this.startSystemNanos = System.nanoTime();
   }

   public void stop() {
      this.stopUptimeMs = SystemClock.uptimeMillis();
   }
}
