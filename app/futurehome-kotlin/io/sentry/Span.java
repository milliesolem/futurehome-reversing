package io.sentry;

import io.sentry.metrics.LocalMetricsAggregator;
import io.sentry.protocol.MeasurementValue;
import io.sentry.protocol.SentryId;
import io.sentry.util.LazyEvaluator;
import io.sentry.util.Objects;
import j..util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public final class Span implements ISpan {
   private final SpanContext context;
   private final Map<String, Object> data;
   private boolean finished = false;
   private final IHub hub;
   private final AtomicBoolean isFinishing = new AtomicBoolean(false);
   private final Map<String, MeasurementValue> measurements;
   private final LazyEvaluator<LocalMetricsAggregator> metricsAggregator;
   private final SpanOptions options;
   private SpanFinishedCallback spanFinishedCallback;
   private SentryDate startTimestamp;
   private Throwable throwable;
   private SentryDate timestamp;
   private final SentryTracer transaction;

   public Span(TransactionContext var1, SentryTracer var2, IHub var3, SentryDate var4, SpanOptions var5) {
      this.data = new ConcurrentHashMap();
      this.measurements = new ConcurrentHashMap();
      this.metricsAggregator = new LazyEvaluator<>(new Span$$ExternalSyntheticLambda0());
      this.context = Objects.requireNonNull(var1, "context is required");
      this.transaction = Objects.requireNonNull(var2, "sentryTracer is required");
      this.hub = Objects.requireNonNull(var3, "hub is required");
      this.spanFinishedCallback = null;
      if (var4 != null) {
         this.startTimestamp = var4;
      } else {
         this.startTimestamp = var3.getOptions().getDateProvider().now();
      }

      this.options = var5;
   }

   Span(SentryId var1, SpanId var2, SentryTracer var3, String var4, IHub var5) {
      this(var1, var2, var3, var4, var5, null, new SpanOptions(), null);
   }

   Span(SentryId var1, SpanId var2, SentryTracer var3, String var4, IHub var5, SentryDate var6, SpanOptions var7, SpanFinishedCallback var8) {
      this.data = new ConcurrentHashMap();
      this.measurements = new ConcurrentHashMap();
      this.metricsAggregator = new LazyEvaluator<>(new Span$$ExternalSyntheticLambda0());
      this.context = new SpanContext(var1, new SpanId(), var4, var2, var3.getSamplingDecision());
      this.transaction = Objects.requireNonNull(var3, "transaction is required");
      this.hub = Objects.requireNonNull(var5, "hub is required");
      this.options = var7;
      this.spanFinishedCallback = var8;
      if (var6 != null) {
         this.startTimestamp = var6;
      } else {
         this.startTimestamp = var5.getOptions().getDateProvider().now();
      }
   }

   private List<Span> getDirectChildren() {
      ArrayList var1 = new ArrayList();

      for (Span var3 : this.transaction.getSpans()) {
         if (var3.getParentSpanId() != null && var3.getParentSpanId().equals(this.getSpanId())) {
            var1.add(var3);
         }
      }

      return var1;
   }

   private void updateStartDate(SentryDate var1) {
      this.startTimestamp = var1;
   }

   @Override
   public void finish() {
      this.finish(this.context.getStatus());
   }

   @Override
   public void finish(SpanStatus var1) {
      this.finish(var1, this.hub.getOptions().getDateProvider().now());
   }

   @Override
   public void finish(SpanStatus var1, SentryDate var2) {
      if (!this.finished && this.isFinishing.compareAndSet(false, true)) {
         this.context.setStatus(var1);
         SentryDate var6 = var2;
         if (var2 == null) {
            var6 = this.hub.getOptions().getDateProvider().now();
         }

         this.timestamp = var6;
         if (this.options.isTrimStart() || this.options.isTrimEnd()) {
            List var7;
            if (this.transaction.getRoot().getSpanId().equals(this.getSpanId())) {
               var7 = this.transaction.getChildren();
            } else {
               var7 = this.getDirectChildren();
            }

            Iterator var4 = var7.iterator();
            var2 = null;
            SentryDate var8 = null;

            while (var4.hasNext()) {
               SentryDate var3;
               Span var5;
               label58: {
                  var5 = (Span)var4.next();
                  if (var2 != null) {
                     var3 = var2;
                     if (!var5.getStartDate().isBefore(var2)) {
                        break label58;
                     }
                  }

                  var3 = var5.getStartDate();
               }

               if (var8 != null) {
                  var2 = var3;
                  if (var5.getFinishDate() == null) {
                     continue;
                  }

                  var2 = var3;
                  if (!var5.getFinishDate().isAfter(var8)) {
                     continue;
                  }
               }

               var8 = var5.getFinishDate();
               var2 = var3;
            }

            if (this.options.isTrimStart() && var2 != null && this.startTimestamp.isBefore(var2)) {
               this.updateStartDate(var2);
            }

            if (this.options.isTrimEnd() && var8 != null) {
               var2 = this.timestamp;
               if (var2 == null || var2.isAfter(var8)) {
                  this.updateEndDate(var8);
               }
            }
         }

         Throwable var9 = this.throwable;
         if (var9 != null) {
            this.hub.setSpanContext(var9, this, this.transaction.getName());
         }

         SpanFinishedCallback var10 = this.spanFinishedCallback;
         if (var10 != null) {
            var10.execute(this);
         }

         this.finished = true;
      }
   }

   @Override
   public Object getData(String var1) {
      return this.data.get(var1);
   }

   public Map<String, Object> getData() {
      return this.data;
   }

   @Override
   public String getDescription() {
      return this.context.getDescription();
   }

   @Override
   public SentryDate getFinishDate() {
      return this.timestamp;
   }

   @Override
   public LocalMetricsAggregator getLocalMetricsAggregator() {
      return this.metricsAggregator.getValue();
   }

   public Map<String, MeasurementValue> getMeasurements() {
      return this.measurements;
   }

   @Override
   public String getOperation() {
      return this.context.getOperation();
   }

   SpanOptions getOptions() {
      return this.options;
   }

   public SpanId getParentSpanId() {
      return this.context.getParentSpanId();
   }

   public TracesSamplingDecision getSamplingDecision() {
      return this.context.getSamplingDecision();
   }

   @Override
   public SpanContext getSpanContext() {
      return this.context;
   }

   SpanFinishedCallback getSpanFinishedCallback() {
      return this.spanFinishedCallback;
   }

   public SpanId getSpanId() {
      return this.context.getSpanId();
   }

   @Override
   public SentryDate getStartDate() {
      return this.startTimestamp;
   }

   @Override
   public SpanStatus getStatus() {
      return this.context.getStatus();
   }

   @Override
   public String getTag(String var1) {
      return this.context.getTags().get(var1);
   }

   public Map<String, String> getTags() {
      return this.context.getTags();
   }

   @Override
   public Throwable getThrowable() {
      return this.throwable;
   }

   public SentryId getTraceId() {
      return this.context.getTraceId();
   }

   @Override
   public boolean isFinished() {
      return this.finished;
   }

   @Override
   public boolean isNoOp() {
      return false;
   }

   public Boolean isProfileSampled() {
      return this.context.getProfileSampled();
   }

   public Boolean isSampled() {
      return this.context.getSampled();
   }

   @Override
   public void setData(String var1, Object var2) {
      this.data.put(var1, var2);
   }

   @Override
   public void setDescription(String var1) {
      this.context.setDescription(var1);
   }

   @Override
   public void setMeasurement(String var1, Number var2) {
      if (this.isFinished()) {
         this.hub.getOptions().getLogger().log(SentryLevel.DEBUG, "The span is already finished. Measurement %s cannot be set", var1);
      } else {
         this.measurements.put(var1, new MeasurementValue(var2, null));
         if (this.transaction.getRoot() != this) {
            this.transaction.setMeasurementFromChild(var1, var2);
         }
      }
   }

   @Override
   public void setMeasurement(String var1, Number var2, MeasurementUnit var3) {
      if (this.isFinished()) {
         this.hub.getOptions().getLogger().log(SentryLevel.DEBUG, "The span is already finished. Measurement %s cannot be set", var1);
      } else {
         this.measurements.put(var1, new MeasurementValue(var2, var3.apiName()));
         if (this.transaction.getRoot() != this) {
            this.transaction.setMeasurementFromChild(var1, var2, var3);
         }
      }
   }

   @Override
   public void setOperation(String var1) {
      this.context.setOperation(var1);
   }

   void setSpanFinishedCallback(SpanFinishedCallback var1) {
      this.spanFinishedCallback = var1;
   }

   @Override
   public void setStatus(SpanStatus var1) {
      this.context.setStatus(var1);
   }

   @Override
   public void setTag(String var1, String var2) {
      this.context.setTag(var1, var2);
   }

   @Override
   public void setThrowable(Throwable var1) {
      this.throwable = var1;
   }

   @Override
   public ISpan startChild(String var1) {
      String var2 = (String)null;
      return this.startChild(var1, null);
   }

   @Override
   public ISpan startChild(String var1, String var2) {
      return (ISpan)(this.finished ? NoOpSpan.getInstance() : this.transaction.startChild(this.context.getSpanId(), var1, var2));
   }

   @Override
   public ISpan startChild(String var1, String var2, SentryDate var3, Instrumenter var4) {
      return this.startChild(var1, var2, var3, var4, new SpanOptions());
   }

   @Override
   public ISpan startChild(String var1, String var2, SentryDate var3, Instrumenter var4, SpanOptions var5) {
      return (ISpan)(this.finished ? NoOpSpan.getInstance() : this.transaction.startChild(this.context.getSpanId(), var1, var2, var3, var4, var5));
   }

   @Override
   public ISpan startChild(String var1, String var2, SpanOptions var3) {
      return (ISpan)(this.finished ? NoOpSpan.getInstance() : this.transaction.startChild(this.context.getSpanId(), var1, var2, var3));
   }

   @Override
   public BaggageHeader toBaggageHeader(List<String> var1) {
      return this.transaction.toBaggageHeader(var1);
   }

   @Override
   public SentryTraceHeader toSentryTrace() {
      return new SentryTraceHeader(this.context.getTraceId(), this.context.getSpanId(), this.context.getSampled());
   }

   @Override
   public TraceContext traceContext() {
      return this.transaction.traceContext();
   }

   @Override
   public boolean updateEndDate(SentryDate var1) {
      if (this.timestamp != null) {
         this.timestamp = var1;
         return true;
      } else {
         return false;
      }
   }
}
