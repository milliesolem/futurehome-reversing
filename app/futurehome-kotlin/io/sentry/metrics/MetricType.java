package io.sentry.metrics;

public enum MetricType {
   Counter("c"),
   Distribution("d"),
   Gauge("g"),
   Set("s");

   private static final MetricType[] $VALUES = $values();
   final String statsdCode;

   private MetricType(String var3) {
      this.statsdCode = var3;
   }
}
