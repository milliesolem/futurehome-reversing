package io.sentry.metrics;

import io.sentry.MeasurementUnit;
import java.util.Arrays;
import java.util.Map;

public final class GaugeMetric extends Metric {
   private int count;
   private double last;
   private double max;
   private double min;
   private double sum;

   public GaugeMetric(String var1, double var2, MeasurementUnit var4, Map<String, String> var5) {
      super(MetricType.Gauge, var1, var4, var5);
      this.last = var2;
      this.min = var2;
      this.max = var2;
      this.sum = var2;
      this.count = 1;
   }

   @Override
   public void add(double var1) {
      this.last = var1;
      this.min = Math.min(this.min, var1);
      this.max = Math.max(this.max, var1);
      this.sum += var1;
      this.count++;
   }

   public int getCount() {
      return this.count;
   }

   public double getLast() {
      return this.last;
   }

   public double getMax() {
      return this.max;
   }

   public double getMin() {
      return this.min;
   }

   public double getSum() {
      return this.sum;
   }

   @Override
   public int getWeight() {
      return 5;
   }

   @Override
   public Iterable<?> serialize() {
      return Arrays.asList(this.last, this.min, this.max, this.sum, this.count);
   }
}
