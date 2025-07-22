package io.sentry;

import io.sentry.metrics.LocalMetricsAggregator;
import java.io.Closeable;
import java.util.Map;

public interface IMetricsAggregator extends Closeable {
   void distribution(String var1, double var2, MeasurementUnit var4, Map<String, String> var5, long var6, LocalMetricsAggregator var8);

   void flush(boolean var1);

   void gauge(String var1, double var2, MeasurementUnit var4, Map<String, String> var5, long var6, LocalMetricsAggregator var8);

   void increment(String var1, double var2, MeasurementUnit var4, Map<String, String> var5, long var6, LocalMetricsAggregator var8);

   void set(String var1, int var2, MeasurementUnit var3, Map<String, String> var4, long var5, LocalMetricsAggregator var7);

   void set(String var1, String var2, MeasurementUnit var3, Map<String, String> var4, long var5, LocalMetricsAggregator var7);
}
