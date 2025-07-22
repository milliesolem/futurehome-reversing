package io.sentry.protocol;

import io.sentry.DateUtils;
import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.SentryLevel;
import io.sentry.Span;
import io.sentry.SpanId;
import io.sentry.SpanStatus;
import io.sentry.metrics.LocalMetricsAggregator;
import io.sentry.util.CollectionUtils;
import io.sentry.util.Objects;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SentrySpan implements JsonUnknown, JsonSerializable {
   private Map<String, Object> data;
   private final String description;
   private final Map<String, MeasurementValue> measurements;
   private final Map<String, List<MetricSummary>> metricsSummaries;
   private final String op;
   private final String origin;
   private final SpanId parentSpanId;
   private final SpanId spanId;
   private final Double startTimestamp;
   private final SpanStatus status;
   private final Map<String, String> tags;
   private final Double timestamp;
   private final SentryId traceId;
   private Map<String, Object> unknown;

   public SentrySpan(Span var1) {
      this(var1, var1.getData());
   }

   public SentrySpan(Span var1, Map<String, Object> var2) {
      Objects.requireNonNull(var1, "span is required");
      this.description = var1.getDescription();
      this.op = var1.getOperation();
      this.spanId = var1.getSpanId();
      this.parentSpanId = var1.getParentSpanId();
      this.traceId = var1.getTraceId();
      this.status = var1.getStatus();
      this.origin = var1.getSpanContext().getOrigin();
      Object var3 = CollectionUtils.newConcurrentHashMap(var1.getTags());
      if (var3 == null) {
         var3 = new ConcurrentHashMap();
      }

      this.tags = (Map<String, String>)var3;
      var3 = CollectionUtils.newConcurrentHashMap(var1.getMeasurements());
      if (var3 == null) {
         var3 = new ConcurrentHashMap();
      }

      this.measurements = (Map<String, MeasurementValue>)var3;
      if (var1.getFinishDate() == null) {
         var3 = null;
      } else {
         var3 = DateUtils.nanosToSeconds(var1.getStartDate().laterDateNanosTimestampByDiff(var1.getFinishDate()));
      }

      this.timestamp = (Double)var3;
      this.startTimestamp = DateUtils.nanosToSeconds(var1.getStartDate().nanoTimestamp());
      this.data = var2;
      LocalMetricsAggregator var4 = var1.getLocalMetricsAggregator();
      if (var4 != null) {
         this.metricsSummaries = var4.getSummaries();
      } else {
         this.metricsSummaries = null;
      }
   }

   public SentrySpan(
      Double var1,
      Double var2,
      SentryId var3,
      SpanId var4,
      SpanId var5,
      String var6,
      String var7,
      SpanStatus var8,
      String var9,
      Map<String, String> var10,
      Map<String, MeasurementValue> var11,
      Map<String, List<MetricSummary>> var12,
      Map<String, Object> var13
   ) {
      this.startTimestamp = var1;
      this.timestamp = var2;
      this.traceId = var3;
      this.spanId = var4;
      this.parentSpanId = var5;
      this.op = var6;
      this.description = var7;
      this.status = var8;
      this.origin = var9;
      this.tags = var10;
      this.measurements = var11;
      this.metricsSummaries = var12;
      this.data = var13;
   }

   private BigDecimal doubleToBigDecimal(Double var1) {
      return BigDecimal.valueOf(var1).setScale(6, RoundingMode.DOWN);
   }

   public Map<String, Object> getData() {
      return this.data;
   }

   public String getDescription() {
      return this.description;
   }

   public Map<String, MeasurementValue> getMeasurements() {
      return this.measurements;
   }

   public Map<String, List<MetricSummary>> getMetricsSummaries() {
      return this.metricsSummaries;
   }

   public String getOp() {
      return this.op;
   }

   public String getOrigin() {
      return this.origin;
   }

   public SpanId getParentSpanId() {
      return this.parentSpanId;
   }

   public SpanId getSpanId() {
      return this.spanId;
   }

   public Double getStartTimestamp() {
      return this.startTimestamp;
   }

   public SpanStatus getStatus() {
      return this.status;
   }

   public Map<String, String> getTags() {
      return this.tags;
   }

   public Double getTimestamp() {
      return this.timestamp;
   }

   public SentryId getTraceId() {
      return this.traceId;
   }

   @Override
   public Map<String, Object> getUnknown() {
      return this.unknown;
   }

   public boolean isFinished() {
      boolean var1;
      if (this.timestamp != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      var1.name("start_timestamp").value(var2, this.doubleToBigDecimal(this.startTimestamp));
      if (this.timestamp != null) {
         var1.name("timestamp").value(var2, this.doubleToBigDecimal(this.timestamp));
      }

      var1.name("trace_id").value(var2, this.traceId);
      var1.name("span_id").value(var2, this.spanId);
      if (this.parentSpanId != null) {
         var1.name("parent_span_id").value(var2, this.parentSpanId);
      }

      var1.name("op").value(this.op);
      if (this.description != null) {
         var1.name("description").value(this.description);
      }

      if (this.status != null) {
         var1.name("status").value(var2, this.status);
      }

      if (this.origin != null) {
         var1.name("origin").value(var2, this.origin);
      }

      if (!this.tags.isEmpty()) {
         var1.name("tags").value(var2, this.tags);
      }

      if (this.data != null) {
         var1.name("data").value(var2, this.data);
      }

      if (!this.measurements.isEmpty()) {
         var1.name("measurements").value(var2, this.measurements);
      }

      Map var3 = this.metricsSummaries;
      if (var3 != null && !var3.isEmpty()) {
         var1.name("_metrics_summary").value(var2, this.metricsSummaries);
      }

      var3 = this.unknown;
      if (var3 != null) {
         for (String var7 : var3.keySet()) {
            Object var4 = this.unknown.get(var7);
            var1.name(var7);
            var1.value(var2, var4);
         }
      }

      var1.endObject();
   }

   public void setData(Map<String, Object> var1) {
      this.data = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<SentrySpan> {
      private Exception missingRequiredFieldException(String var1, ILogger var2) {
         StringBuilder var3 = new StringBuilder("Missing required field \"");
         var3.append(var1);
         var3.append("\"");
         var1 = var3.toString();
         IllegalStateException var5 = new IllegalStateException(var1);
         var2.log(SentryLevel.ERROR, var1, var5);
         return var5;
      }

      public SentrySpan deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         Map var7 = null;
         Double var14 = null;
         Double var13 = null;
         SentryId var12 = null;
         SpanId var11 = null;
         SpanId var18 = null;
         String var10 = null;
         String var17 = null;
         SpanStatus var16 = null;
         String var15 = null;
         Object var6 = null;
         ConcurrentHashMap var5 = null;
         Map var9 = null;
         Map var8 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var20 = var1.nextName();
            var20.hashCode();
            int var4 = var20.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -2011840976:
                  if (var20.equals("span_id")) {
                     var3 = 0;
                  }
                  break;
               case -1757797477:
                  if (var20.equals("parent_span_id")) {
                     var3 = 1;
                  }
                  break;
               case -1724546052:
                  if (var20.equals("description")) {
                     var3 = 2;
                  }
                  break;
               case -1526966919:
                  if (var20.equals("start_timestamp")) {
                     var3 = 3;
                  }
                  break;
               case -1008619738:
                  if (var20.equals("origin")) {
                     var3 = 4;
                  }
                  break;
               case -892481550:
                  if (var20.equals("status")) {
                     var3 = 5;
                  }
                  break;
               case -682561045:
                  if (var20.equals("_metrics_summary")) {
                     var3 = 6;
                  }
                  break;
               case -362243017:
                  if (var20.equals("measurements")) {
                     var3 = 7;
                  }
                  break;
               case 3553:
                  if (var20.equals("op")) {
                     var3 = 8;
                  }
                  break;
               case 3076010:
                  if (var20.equals("data")) {
                     var3 = 9;
                  }
                  break;
               case 3552281:
                  if (var20.equals("tags")) {
                     var3 = 10;
                  }
                  break;
               case 55126294:
                  if (var20.equals("timestamp")) {
                     var3 = 11;
                  }
                  break;
               case 1270300245:
                  if (var20.equals("trace_id")) {
                     var3 = 12;
                  }
            }

            String var36;
            label163: {
               label164: {
                  label165: {
                     label145: {
                        switch (var3) {
                           case 0:
                              var11 = new SpanId.Deserializer().deserialize(var1, var2);
                              break;
                           case 1:
                              var18 = var1.nextOrNull(var2, new SpanId.Deserializer());
                              var36 = var15;
                              var15 = var17;
                              var34 = var11;
                              var20 = var10;
                              break label163;
                           case 2:
                              String var40 = var1.nextStringOrNull();
                              var32 = var11;
                              var36 = var10;
                              var15 = var40;
                              var27 = var16;
                              var10 = var15;
                              break label164;
                           case 3:
                              try {
                                 var14 = var1.nextDoubleOrNull();
                              } catch (NumberFormatException var22) {
                                 Date var29 = var1.nextDateOrNull(var2);
                                 if (var29 != null) {
                                    var14 = DateUtils.dateToSeconds(var29);
                                 } else {
                                    var14 = null;
                                 }
                              }
                              break;
                           case 4:
                              var20 = var1.nextStringOrNull();
                              break label145;
                           case 5:
                              SpanStatus var21 = var1.nextOrNull(var2, new SpanStatus.Deserializer());
                              var20 = var15;
                              var32 = var11;
                              var36 = var10;
                              var30 = var21;
                              break label165;
                           case 6:
                              var9 = var1.nextMapOfListOrNull(var2, new MetricSummary.Deserializer());
                              break;
                           case 7:
                              var6 = var1.nextMapOrNull(var2, new MeasurementValue.Deserializer());
                              break;
                           case 8:
                              var10 = var1.nextStringOrNull();
                              break;
                           case 9:
                              var8 = (Map)var1.nextObjectOrNull();
                              break;
                           case 10:
                              var7 = (Map)var1.nextObjectOrNull();
                              break;
                           case 11:
                              try {
                                 var13 = var1.nextDoubleOrNull();
                              } catch (NumberFormatException var23) {
                                 Date var28 = var1.nextDateOrNull(var2);
                                 if (var28 != null) {
                                    var13 = DateUtils.dateToSeconds(var28);
                                 } else {
                                    var13 = null;
                                 }
                              }
                              break;
                           case 12:
                              var12 = new SentryId.Deserializer().deserialize(var1, var2);
                              break;
                           default:
                              ConcurrentHashMap var19 = var5;
                              if (var5 == null) {
                                 var19 = new ConcurrentHashMap();
                              }

                              var1.nextUnknown(var2, var19, var20);
                              var5 = var19;
                        }

                        var20 = var15;
                     }

                     var30 = var16;
                     var36 = var10;
                     var32 = var11;
                  }

                  var10 = var20;
                  var27 = var30;
                  var15 = var17;
               }

               var34 = var32;
               var20 = var36;
               var16 = var27;
               var36 = var10;
            }

            var11 = var34;
            var10 = var20;
            var17 = var15;
            var15 = var36;
         }

         if (var14 == null) {
            throw this.missingRequiredFieldException("start_timestamp", var2);
         } else if (var12 == null) {
            throw this.missingRequiredFieldException("trace_id", var2);
         } else if (var11 == null) {
            throw this.missingRequiredFieldException("span_id", var2);
         } else if (var10 != null) {
            Object var24;
            if (var7 == null) {
               var24 = new HashMap();
            } else {
               var24 = var7;
            }

            if (var6 == null) {
               var6 = new HashMap();
            }

            var24 = new SentrySpan(var14, var13, var12, var11, var18, var10, var17, var16, var15, var24, (Map<String, MeasurementValue>)var6, var9, var8);
            var24.setUnknown(var5);
            var1.endObject();
            return var24;
         } else {
            throw this.missingRequiredFieldException("op", var2);
         }
      }
   }

   public static final class JsonKeys {
      public static final String DATA = "data";
      public static final String DESCRIPTION = "description";
      public static final String MEASUREMENTS = "measurements";
      public static final String METRICS_SUMMARY = "_metrics_summary";
      public static final String OP = "op";
      public static final String ORIGIN = "origin";
      public static final String PARENT_SPAN_ID = "parent_span_id";
      public static final String SPAN_ID = "span_id";
      public static final String START_TIMESTAMP = "start_timestamp";
      public static final String STATUS = "status";
      public static final String TAGS = "tags";
      public static final String TIMESTAMP = "timestamp";
      public static final String TRACE_ID = "trace_id";
   }
}
