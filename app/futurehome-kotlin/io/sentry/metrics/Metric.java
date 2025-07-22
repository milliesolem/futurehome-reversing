package io.sentry.metrics;

import io.sentry.MeasurementUnit;
import java.util.Map;

public abstract class Metric {
   private final String key;
   private final Map<String, String> tags;
   private final MetricType type;
   private final MeasurementUnit unit;

   public Metric(MetricType var1, String var2, MeasurementUnit var3, Map<String, String> var4) {
      this.type = var1;
      this.key = var2;
      this.unit = var3;
      this.tags = var4;
   }

   public abstract void add(double var1);

   public String getKey() {
      return this.key;
   }

   public Map<String, String> getTags() {
      return this.tags;
   }

   public MetricType getType() {
      return this.type;
   }

   public MeasurementUnit getUnit() {
      return this.unit;
   }

   public abstract int getWeight();

   public abstract Iterable<?> serialize();
}
