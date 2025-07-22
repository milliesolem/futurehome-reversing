package io.sentry.metrics;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.Map.Entry;

public final class EncodedMetrics {
   private static final Charset UTF8 = Charset.forName("UTF-8");
   private final Map<Long, Map<String, Metric>> buckets;

   public EncodedMetrics(Map<Long, Map<String, Metric>> var1) {
      this.buckets = var1;
   }

   public byte[] encodeToStatsd() {
      StringBuilder var1 = new StringBuilder();

      for (Entry var3 : this.buckets.entrySet()) {
         MetricsHelper.encodeMetrics((Long)var3.getKey(), ((Map)var3.getValue()).values(), var1);
      }

      return var1.toString().getBytes(UTF8);
   }

   Map<Long, Map<String, Metric>> getBuckets() {
      return this.buckets;
   }
}
