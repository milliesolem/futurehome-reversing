package io.sentry.metrics;

import io.sentry.MeasurementUnit;
import java.util.Collections;
import java.util.Map;

public final class CounterMetric extends Metric {
   private double value;

   public CounterMetric(String var1, double var2, MeasurementUnit var4, Map<String, String> var5) {
      super(MetricType.Counter, var1, var4, var5);
      this.value = var2;
   }

   @Override
   public void add(double var1) {
      this.value += var1;
   }

   public double getValue() {
      return this.value;
   }

   @Override
   public int getWeight() {
      return 1;
   }

   @Override
   public Iterable<?> serialize() {
      return Collections.singletonList(this.value);
   }
}
