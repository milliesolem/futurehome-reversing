package io.sentry.metrics;

import io.sentry.IMetricsAggregator;
import io.sentry.ISpan;
import io.sentry.MeasurementUnit;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public final class NoopMetricsAggregator implements IMetricsAggregator, MetricsApi.IMetricsInterface {
   private static final NoopMetricsAggregator instance = new NoopMetricsAggregator();

   public static NoopMetricsAggregator getInstance() {
      return instance;
   }

   @Override
   public void close() throws IOException {
   }

   @Override
   public void distribution(String var1, double var2, MeasurementUnit var4, Map<String, String> var5, long var6, LocalMetricsAggregator var8) {
   }

   @Override
   public void flush(boolean var1) {
   }

   @Override
   public void gauge(String var1, double var2, MeasurementUnit var4, Map<String, String> var5, long var6, LocalMetricsAggregator var8) {
   }

   @Override
   public Map<String, String> getDefaultTagsForMetrics() {
      return Collections.emptyMap();
   }

   @Override
   public LocalMetricsAggregator getLocalMetricsAggregator() {
      return null;
   }

   @Override
   public IMetricsAggregator getMetricsAggregator() {
      return this;
   }

   @Override
   public void increment(String var1, double var2, MeasurementUnit var4, Map<String, String> var5, long var6, LocalMetricsAggregator var8) {
   }

   @Override
   public void set(String var1, int var2, MeasurementUnit var3, Map<String, String> var4, long var5, LocalMetricsAggregator var7) {
   }

   @Override
   public void set(String var1, String var2, MeasurementUnit var3, Map<String, String> var4, long var5, LocalMetricsAggregator var7) {
   }

   @Override
   public ISpan startSpanForMetric(String var1, String var2) {
      return null;
   }
}
