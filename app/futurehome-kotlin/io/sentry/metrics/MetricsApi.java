package io.sentry.metrics;

import io.sentry.IMetricsAggregator;
import io.sentry.ISpan;
import io.sentry.MeasurementUnit;
import io.sentry.SentryDate;
import io.sentry.SentryNanotimeDate;
import java.util.Map;
import java.util.Map.Entry;

public final class MetricsApi {
   private final MetricsApi.IMetricsInterface aggregator;

   public MetricsApi(MetricsApi.IMetricsInterface var1) {
      this.aggregator = var1;
   }

   public void distribution(String var1, double var2) {
      this.distribution(var1, var2, null, null, null);
   }

   public void distribution(String var1, double var2, MeasurementUnit var4) {
      this.distribution(var1, var2, var4, null, null);
   }

   public void distribution(String var1, double var2, MeasurementUnit var4, Map<String, String> var5) {
      this.distribution(var1, var2, var4, var5, null);
   }

   public void distribution(String var1, double var2, MeasurementUnit var4, Map<String, String> var5, Long var6) {
      long var7;
      if (var6 != null) {
         var7 = var6;
      } else {
         var7 = System.currentTimeMillis();
      }

      var5 = MetricsHelper.mergeTags(var5, this.aggregator.getDefaultTagsForMetrics());
      LocalMetricsAggregator var10 = this.aggregator.getLocalMetricsAggregator();
      this.aggregator.getMetricsAggregator().distribution(var1, var2, var4, var5, var7, var10);
   }

   public void gauge(String var1, double var2) {
      this.gauge(var1, var2, null, null, null);
   }

   public void gauge(String var1, double var2, MeasurementUnit var4) {
      this.gauge(var1, var2, var4, null, null);
   }

   public void gauge(String var1, double var2, MeasurementUnit var4, Map<String, String> var5) {
      this.gauge(var1, var2, var4, var5, null);
   }

   public void gauge(String var1, double var2, MeasurementUnit var4, Map<String, String> var5, Long var6) {
      long var7;
      if (var6 != null) {
         var7 = var6;
      } else {
         var7 = System.currentTimeMillis();
      }

      var5 = MetricsHelper.mergeTags(var5, this.aggregator.getDefaultTagsForMetrics());
      LocalMetricsAggregator var10 = this.aggregator.getLocalMetricsAggregator();
      this.aggregator.getMetricsAggregator().gauge(var1, var2, var4, var5, var7, var10);
   }

   public void increment(String var1) {
      this.increment(var1, 1.0, null, null, null);
   }

   public void increment(String var1, double var2) {
      this.increment(var1, var2, null, null, null);
   }

   public void increment(String var1, double var2, MeasurementUnit var4) {
      this.increment(var1, var2, var4, null, null);
   }

   public void increment(String var1, double var2, MeasurementUnit var4, Map<String, String> var5) {
      this.increment(var1, var2, var4, var5, null);
   }

   public void increment(String var1, double var2, MeasurementUnit var4, Map<String, String> var5, Long var6) {
      long var7;
      if (var6 != null) {
         var7 = var6;
      } else {
         var7 = System.currentTimeMillis();
      }

      Map var10 = MetricsHelper.mergeTags(var5, this.aggregator.getDefaultTagsForMetrics());
      LocalMetricsAggregator var9 = this.aggregator.getLocalMetricsAggregator();
      this.aggregator.getMetricsAggregator().increment(var1, var2, var4, var10, var7, var9);
   }

   public void set(String var1, int var2) {
      this.set(var1, var2, null, null, null);
   }

   public void set(String var1, int var2, MeasurementUnit var3) {
      this.set(var1, var2, var3, null, null);
   }

   public void set(String var1, int var2, MeasurementUnit var3, Map<String, String> var4) {
      this.set(var1, var2, var3, var4, null);
   }

   public void set(String var1, int var2, MeasurementUnit var3, Map<String, String> var4, Long var5) {
      long var6;
      if (var5 != null) {
         var6 = var5;
      } else {
         var6 = System.currentTimeMillis();
      }

      var4 = MetricsHelper.mergeTags(var4, this.aggregator.getDefaultTagsForMetrics());
      LocalMetricsAggregator var9 = this.aggregator.getLocalMetricsAggregator();
      this.aggregator.getMetricsAggregator().set(var1, var2, var3, var4, var6, var9);
   }

   public void set(String var1, String var2) {
      this.set(var1, var2, null, null, null);
   }

   public void set(String var1, String var2, MeasurementUnit var3) {
      this.set(var1, var2, var3, null, null);
   }

   public void set(String var1, String var2, MeasurementUnit var3, Map<String, String> var4) {
      this.set(var1, var2, var3, var4, null);
   }

   public void set(String var1, String var2, MeasurementUnit var3, Map<String, String> var4, Long var5) {
      long var6;
      if (var5 != null) {
         var6 = var5;
      } else {
         var6 = System.currentTimeMillis();
      }

      Map var9 = MetricsHelper.mergeTags(var4, this.aggregator.getDefaultTagsForMetrics());
      LocalMetricsAggregator var8 = this.aggregator.getLocalMetricsAggregator();
      this.aggregator.getMetricsAggregator().set(var1, var2, var3, var9, var6, var8);
   }

   public void timing(String var1, Runnable var2) {
      this.timing(var1, var2, null, null);
   }

   public void timing(String var1, Runnable var2, MeasurementUnit.Duration var3) {
      this.timing(var1, var2, var3, null);
   }

   public void timing(String var1, Runnable var2, MeasurementUnit.Duration var3, Map<String, String> var4) {
      if (var3 == null) {
         var3 = MeasurementUnit.Duration.SECOND;
      }

      Map var12 = MetricsHelper.mergeTags(var4, this.aggregator.getDefaultTagsForMetrics());
      ISpan var13 = this.aggregator.startSpanForMetric("metric.timing", var1);
      LocalMetricsAggregator var11;
      if (var13 != null) {
         var11 = var13.getLocalMetricsAggregator();
      } else {
         var11 = this.aggregator.getLocalMetricsAggregator();
      }

      if (var13 != null && var4 != null) {
         for (Entry var14 : var4.entrySet()) {
            var13.setTag((String)var14.getKey(), (String)var14.getValue());
         }
      }

      long var9 = System.currentTimeMillis();
      long var7 = System.nanoTime();

      try {
         var2.run();
      } finally {
         if (var13 != null) {
            var13.finish();
            Object var17;
            if (var13.getFinishDate() != null) {
               var17 = var13.getFinishDate();
            } else {
               var17 = new SentryNanotimeDate();
            }

            var7 = ((SentryDate)var17).diff(var13.getStartDate());
         } else {
            var7 = System.nanoTime() - var7;
         }

         double var5 = MetricsHelper.convertNanosTo(var3, var7);
         this.aggregator.getMetricsAggregator().distribution(var1, var5, var3, var12, var9, var11);
      }
   }

   public interface IMetricsInterface {
      Map<String, String> getDefaultTagsForMetrics();

      LocalMetricsAggregator getLocalMetricsAggregator();

      IMetricsAggregator getMetricsAggregator();

      ISpan startSpanForMetric(String var1, String var2);
   }
}
