package io.sentry.protocol;

import io.sentry.DateUtils;
import io.sentry.ILogger;
import io.sentry.JsonDeserializer;
import io.sentry.JsonSerializable;
import io.sentry.JsonUnknown;
import io.sentry.ObjectReader;
import io.sentry.ObjectWriter;
import io.sentry.SentryBaseEvent;
import io.sentry.SentryTracer;
import io.sentry.Span;
import io.sentry.SpanContext;
import io.sentry.SpanStatus;
import io.sentry.TracesSamplingDecision;
import io.sentry.metrics.LocalMetricsAggregator;
import io.sentry.util.Objects;
import io.sentry.vendor.gson.stream.JsonToken;
import j..util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class SentryTransaction extends SentryBaseEvent implements JsonUnknown, JsonSerializable {
   private final Map<String, MeasurementValue> measurements;
   private Map<String, List<MetricSummary>> metricSummaries;
   private final List<SentrySpan> spans;
   private Double startTimestamp;
   private Double timestamp;
   private String transaction;
   private TransactionInfo transactionInfo;
   private final String type;
   private Map<String, Object> unknown;

   public SentryTransaction(SentryTracer var1) {
      super(var1.getEventId());
      this.spans = new ArrayList<>();
      this.type = "transaction";
      this.measurements = new HashMap<>();
      Objects.requireNonNull(var1, "sentryTracer is required");
      this.startTimestamp = DateUtils.nanosToSeconds(var1.getStartDate().nanoTimestamp());
      this.timestamp = DateUtils.nanosToSeconds(var1.getStartDate().laterDateNanosTimestampByDiff(var1.getFinishDate()));
      this.transaction = var1.getName();

      for (Span var3 : var1.getChildren()) {
         if (Boolean.TRUE.equals(var3.isSampled())) {
            this.spans.add(new SentrySpan(var3));
         }
      }

      Contexts var9 = this.getContexts();
      var9.putAll(var1.getContexts());
      SpanContext var5 = var1.getSpanContext();
      var9.setTrace(
         new SpanContext(
            var5.getTraceId(),
            var5.getSpanId(),
            var5.getParentSpanId(),
            var5.getOperation(),
            var5.getDescription(),
            var5.getSamplingDecision(),
            var5.getStatus(),
            var5.getOrigin()
         )
      );

      for (Entry var6 : var5.getTags().entrySet()) {
         this.setTag((String)var6.getKey(), (String)var6.getValue());
      }

      Map var7 = var1.getData();
      if (var7 != null) {
         for (Entry var11 : var7.entrySet()) {
            this.setExtra((String)var11.getKey(), var11.getValue());
         }
      }

      this.transactionInfo = new TransactionInfo(var1.getTransactionNameSource().apiName());
      LocalMetricsAggregator var4 = var1.getLocalMetricsAggregator();
      if (var4 != null) {
         this.metricSummaries = var4.getSummaries();
      } else {
         this.metricSummaries = null;
      }
   }

   public SentryTransaction(
      String var1,
      Double var2,
      Double var3,
      List<SentrySpan> var4,
      Map<String, MeasurementValue> var5,
      Map<String, List<MetricSummary>> var6,
      TransactionInfo var7
   ) {
      ArrayList var8 = new ArrayList();
      this.spans = var8;
      this.type = "transaction";
      HashMap var9 = new HashMap();
      this.measurements = var9;
      this.transaction = var1;
      this.startTimestamp = var2;
      this.timestamp = var3;
      var8.addAll(var4);
      var9.putAll(var5);

      for (SentrySpan var11 : var4) {
         this.measurements.putAll(var11.getMeasurements());
      }

      this.transactionInfo = var7;
      this.metricSummaries = var6;
   }

   private BigDecimal doubleToBigDecimal(Double var1) {
      return BigDecimal.valueOf(var1).setScale(6, RoundingMode.DOWN);
   }

   public Map<String, MeasurementValue> getMeasurements() {
      return this.measurements;
   }

   public Map<String, List<MetricSummary>> getMetricSummaries() {
      return this.metricSummaries;
   }

   public TracesSamplingDecision getSamplingDecision() {
      SpanContext var1 = this.getContexts().getTrace();
      return var1 == null ? null : var1.getSamplingDecision();
   }

   public List<SentrySpan> getSpans() {
      return this.spans;
   }

   public Double getStartTimestamp() {
      return this.startTimestamp;
   }

   public SpanStatus getStatus() {
      SpanContext var1 = this.getContexts().getTrace();
      SpanStatus var2;
      if (var1 != null) {
         var2 = var1.getStatus();
      } else {
         var2 = null;
      }

      return var2;
   }

   public Double getTimestamp() {
      return this.timestamp;
   }

   public String getTransaction() {
      return this.transaction;
   }

   public String getType() {
      return "transaction";
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

   public boolean isSampled() {
      TracesSamplingDecision var1 = this.getSamplingDecision();
      return var1 == null ? false : var1.getSampled();
   }

   @Override
   public void serialize(ObjectWriter var1, ILogger var2) throws IOException {
      var1.beginObject();
      if (this.transaction != null) {
         var1.name("transaction").value(this.transaction);
      }

      var1.name("start_timestamp").value(var2, this.doubleToBigDecimal(this.startTimestamp));
      if (this.timestamp != null) {
         var1.name("timestamp").value(var2, this.doubleToBigDecimal(this.timestamp));
      }

      if (!this.spans.isEmpty()) {
         var1.name("spans").value(var2, this.spans);
      }

      var1.name("type").value("transaction");
      if (!this.measurements.isEmpty()) {
         var1.name("measurements").value(var2, this.measurements);
      }

      Map var3 = this.metricSummaries;
      if (var3 != null && !var3.isEmpty()) {
         var1.name("_metrics_summary").value(var2, this.metricSummaries);
      }

      var1.name("transaction_info").value(var2, this.transactionInfo);
      new SentryBaseEvent.Serializer().serialize(this, var1, var2);
      var3 = this.unknown;
      if (var3 != null) {
         for (String var4 : var3.keySet()) {
            Object var5 = this.unknown.get(var4);
            var1.name(var4);
            var1.value(var2, var5);
         }
      }

      var1.endObject();
   }

   public void setMetricSummaries(Map<String, List<MetricSummary>> var1) {
      this.metricSummaries = var1;
   }

   @Override
   public void setUnknown(Map<String, Object> var1) {
      this.unknown = var1;
   }

   public static final class Deserializer implements JsonDeserializer<SentryTransaction> {
      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      public SentryTransaction deserialize(ObjectReader var1, ILogger var2) throws Exception {
         var1.beginObject();
         SentryTransaction var7 = new SentryTransaction(
            "", 0.0, null, new ArrayList<>(), new HashMap<>(), null, new TransactionInfo(TransactionNameSource.CUSTOM.apiName())
         );
         SentryBaseEvent.Deserializer var8 = new SentryBaseEvent.Deserializer();
         ConcurrentHashMap var5 = null;

         while (var1.peek() == JsonToken.NAME) {
            String var9 = var1.nextName();
            var9.hashCode();
            int var4 = var9.hashCode();
            byte var3 = -1;
            switch (var4) {
               case -1526966919:
                  if (var9.equals("start_timestamp")) {
                     var3 = 0;
                  }
                  break;
               case -682561045:
                  if (var9.equals("_metrics_summary")) {
                     var3 = 1;
                  }
                  break;
               case -362243017:
                  if (var9.equals("measurements")) {
                     var3 = 2;
                  }
                  break;
               case 3575610:
                  if (var9.equals("type")) {
                     var3 = 3;
                  }
                  break;
               case 55126294:
                  if (var9.equals("timestamp")) {
                     var3 = 4;
                  }
                  break;
               case 109638249:
                  if (var9.equals("spans")) {
                     var3 = 5;
                  }
                  break;
               case 508716399:
                  if (var9.equals("transaction_info")) {
                     var3 = 6;
                  }
                  break;
               case 2141246174:
                  if (var9.equals("transaction")) {
                     var3 = 7;
                  }
            }

            switch (var3) {
               case 0:
                  Date var18;
                  label114: {
                     try {
                        var19 = var1.nextDoubleOrNull();
                     } catch (NumberFormatException var13) {
                        var18 = var1.nextDateOrNull(var2);
                        if (var18 == null) {
                           break;
                        }
                        break label114;
                     }

                     if (var19 == null) {
                        break;
                     }

                     try {
                        var7.startTimestamp = var19;
                        break;
                     } catch (NumberFormatException var12) {
                        var18 = var1.nextDateOrNull(var2);
                        if (var18 == null) {
                           break;
                        }
                     }
                  }

                  var7.startTimestamp = DateUtils.dateToSeconds(var18);
                  break;
               case 1:
                  var7.metricSummaries = var1.nextMapOfListOrNull(var2, new MetricSummary.Deserializer());
                  break;
               case 2:
                  Map var17 = var1.nextMapOrNull(var2, new MeasurementValue.Deserializer());
                  if (var17 != null) {
                     var7.measurements.putAll(var17);
                  }
                  break;
               case 3:
                  var1.nextString();
                  break;
               case 4:
                  Date var15;
                  label113: {
                     try {
                        var16 = var1.nextDoubleOrNull();
                     } catch (NumberFormatException var11) {
                        var15 = var1.nextDateOrNull(var2);
                        if (var15 == null) {
                           break;
                        }
                        break label113;
                     }

                     if (var16 == null) {
                        break;
                     }

                     try {
                        var7.timestamp = var16;
                        break;
                     } catch (NumberFormatException var10) {
                        var15 = var1.nextDateOrNull(var2);
                        if (var15 == null) {
                           break;
                        }
                     }
                  }

                  var7.timestamp = DateUtils.dateToSeconds(var15);
                  break;
               case 5:
                  List var14 = var1.nextListOrNull(var2, new SentrySpan.Deserializer());
                  if (var14 != null) {
                     var7.spans.addAll(var14);
                  }
                  break;
               case 6:
                  var7.transactionInfo = new TransactionInfo.Deserializer().deserialize(var1, var2);
                  break;
               case 7:
                  var7.transaction = var1.nextStringOrNull();
                  break;
               default:
                  if (!var8.deserializeValue(var7, var9, var1, var2)) {
                     ConcurrentHashMap var6 = var5;
                     if (var5 == null) {
                        var6 = new ConcurrentHashMap();
                     }

                     var1.nextUnknown(var2, var6, var9);
                     var5 = var6;
                  }
            }
         }

         var7.setUnknown(var5);
         var1.endObject();
         return var7;
      }
   }

   public static final class JsonKeys {
      public static final String MEASUREMENTS = "measurements";
      public static final String METRICS_SUMMARY = "_metrics_summary";
      public static final String SPANS = "spans";
      public static final String START_TIMESTAMP = "start_timestamp";
      public static final String TIMESTAMP = "timestamp";
      public static final String TRANSACTION = "transaction";
      public static final String TRANSACTION_INFO = "transaction_info";
      public static final String TYPE = "type";
   }
}
