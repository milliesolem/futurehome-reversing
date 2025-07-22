package io.sentry;

import io.sentry.metrics.LocalMetricsAggregator;
import io.sentry.protocol.SentryId;
import java.util.List;

public final class NoOpSpan implements ISpan {
   private static final NoOpSpan instance = new NoOpSpan();

   private NoOpSpan() {
   }

   public static NoOpSpan getInstance() {
      return instance;
   }

   @Override
   public void finish() {
   }

   @Override
   public void finish(SpanStatus var1) {
   }

   @Override
   public void finish(SpanStatus var1, SentryDate var2) {
   }

   @Override
   public Object getData(String var1) {
      return null;
   }

   @Override
   public String getDescription() {
      return null;
   }

   @Override
   public SentryDate getFinishDate() {
      return new SentryNanotimeDate();
   }

   @Override
   public LocalMetricsAggregator getLocalMetricsAggregator() {
      return null;
   }

   @Override
   public String getOperation() {
      return "";
   }

   @Override
   public SpanContext getSpanContext() {
      return new SpanContext(SentryId.EMPTY_ID, SpanId.EMPTY_ID, "op", null, null);
   }

   @Override
   public SentryDate getStartDate() {
      return new SentryNanotimeDate();
   }

   @Override
   public SpanStatus getStatus() {
      return null;
   }

   @Override
   public String getTag(String var1) {
      return null;
   }

   @Override
   public Throwable getThrowable() {
      return null;
   }

   @Override
   public boolean isFinished() {
      return false;
   }

   @Override
   public boolean isNoOp() {
      return true;
   }

   @Override
   public void setData(String var1, Object var2) {
   }

   @Override
   public void setDescription(String var1) {
   }

   @Override
   public void setMeasurement(String var1, Number var2) {
   }

   @Override
   public void setMeasurement(String var1, Number var2, MeasurementUnit var3) {
   }

   @Override
   public void setOperation(String var1) {
   }

   @Override
   public void setStatus(SpanStatus var1) {
   }

   @Override
   public void setTag(String var1, String var2) {
   }

   @Override
   public void setThrowable(Throwable var1) {
   }

   @Override
   public ISpan startChild(String var1) {
      return getInstance();
   }

   @Override
   public ISpan startChild(String var1, String var2) {
      return getInstance();
   }

   @Override
   public ISpan startChild(String var1, String var2, SentryDate var3, Instrumenter var4) {
      return getInstance();
   }

   @Override
   public ISpan startChild(String var1, String var2, SentryDate var3, Instrumenter var4, SpanOptions var5) {
      return getInstance();
   }

   @Override
   public ISpan startChild(String var1, String var2, SpanOptions var3) {
      return getInstance();
   }

   @Override
   public BaggageHeader toBaggageHeader(List<String> var1) {
      return null;
   }

   @Override
   public SentryTraceHeader toSentryTrace() {
      return new SentryTraceHeader(SentryId.EMPTY_ID, SpanId.EMPTY_ID, false);
   }

   @Override
   public TraceContext traceContext() {
      return new TraceContext(SentryId.EMPTY_ID, "");
   }

   @Override
   public boolean updateEndDate(SentryDate var1) {
      return false;
   }
}
