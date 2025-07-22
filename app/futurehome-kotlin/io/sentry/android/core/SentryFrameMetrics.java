package io.sentry.android.core;

final class SentryFrameMetrics {
   private int frozenFrameCount;
   private long frozenFrameDelayNanos;
   private int slowFrameCount;
   private long slowFrameDelayNanos;
   private long totalDurationNanos;

   public SentryFrameMetrics() {
   }

   public SentryFrameMetrics(int var1, long var2, int var4, long var5, long var7) {
      this.slowFrameCount = var1;
      this.slowFrameDelayNanos = var2;
      this.frozenFrameCount = var4;
      this.frozenFrameDelayNanos = var5;
      this.totalDurationNanos = var7;
   }

   public void addFrame(long var1, long var3, boolean var5, boolean var6) {
      this.totalDurationNanos += var1;
      if (var6) {
         this.frozenFrameDelayNanos += var3;
         this.frozenFrameCount++;
      } else if (var5) {
         this.slowFrameDelayNanos += var3;
         this.slowFrameCount++;
      }
   }

   public void clear() {
      this.slowFrameCount = 0;
      this.slowFrameDelayNanos = 0L;
      this.frozenFrameCount = 0;
      this.frozenFrameDelayNanos = 0L;
      this.totalDurationNanos = 0L;
   }

   public boolean containsValidData() {
      boolean var1;
      if (this.slowFrameCount >= 0
         && this.slowFrameDelayNanos >= 0L
         && this.frozenFrameCount >= 0
         && this.frozenFrameDelayNanos >= 0L
         && this.totalDurationNanos >= 0L) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public SentryFrameMetrics diffTo(SentryFrameMetrics var1) {
      return new SentryFrameMetrics(
         this.slowFrameCount - var1.slowFrameCount,
         this.slowFrameDelayNanos - var1.slowFrameDelayNanos,
         this.frozenFrameCount - var1.frozenFrameCount,
         this.frozenFrameDelayNanos - var1.frozenFrameDelayNanos,
         this.totalDurationNanos - var1.totalDurationNanos
      );
   }

   public SentryFrameMetrics duplicate() {
      return new SentryFrameMetrics(this.slowFrameCount, this.slowFrameDelayNanos, this.frozenFrameCount, this.frozenFrameDelayNanos, this.totalDurationNanos);
   }

   public int getFrozenFrameCount() {
      return this.frozenFrameCount;
   }

   public long getFrozenFrameDelayNanos() {
      return this.frozenFrameDelayNanos;
   }

   public int getSlowFrameCount() {
      return this.slowFrameCount;
   }

   public long getSlowFrameDelayNanos() {
      return this.slowFrameDelayNanos;
   }

   public int getSlowFrozenFrameCount() {
      return this.slowFrameCount + this.frozenFrameCount;
   }

   public long getTotalDurationNanos() {
      return this.totalDurationNanos;
   }
}
