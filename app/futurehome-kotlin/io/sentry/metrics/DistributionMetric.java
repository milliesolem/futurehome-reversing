package io.sentry.metrics;

import io.sentry.MeasurementUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class DistributionMetric extends Metric {
   private final List<Double> values;

   public DistributionMetric(String var1, double var2, MeasurementUnit var4, Map<String, String> var5) {
      super(MetricType.Distribution, var1, var4, var5);
      ArrayList var6 = new ArrayList();
      this.values = var6;
      var6.add(var2);
   }

   @Override
   public void add(double var1) {
      this.values.add(var1);
   }

   @Override
   public int getWeight() {
      return this.values.size();
   }

   @Override
   public Iterable<?> serialize() {
      return this.values;
   }
}
