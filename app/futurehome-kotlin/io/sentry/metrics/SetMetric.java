package io.sentry.metrics;

import io.sentry.MeasurementUnit;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class SetMetric extends Metric {
   private final Set<Integer> values = new HashSet<>();

   public SetMetric(String var1, MeasurementUnit var2, Map<String, String> var3) {
      super(MetricType.Set, var1, var2, var3);
   }

   @Override
   public void add(double var1) {
      this.values.add((int)var1);
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
