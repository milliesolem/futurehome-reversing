package io.sentry;

public final class CpuCollectionData {
   final double cpuUsagePercentage;
   final long timestampMillis;

   public CpuCollectionData(long var1, double var3) {
      this.timestampMillis = var1;
      this.cpuUsagePercentage = var3;
   }

   public double getCpuUsagePercentage() {
      return this.cpuUsagePercentage;
   }

   public long getTimestampMillis() {
      return this.timestampMillis;
   }
}
