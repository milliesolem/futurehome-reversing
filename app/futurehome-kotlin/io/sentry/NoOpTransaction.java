package io.sentry;

import io.sentry.metrics.LocalMetricsAggregator;
import io.sentry.protocol.Contexts;
import io.sentry.protocol.SentryId;
import io.sentry.protocol.TransactionNameSource;
import java.util.Collections;
import java.util.List;

public final class NoOpTransaction implements ITransaction {
   private static final NoOpTransaction instance = new NoOpTransaction();

   private NoOpTransaction() {
   }

   public static NoOpTransaction getInstance() {
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
   public void finish(SpanStatus var1, SentryDate var2, boolean var3, Hint var4) {
   }

   @Override
   public void forceFinish(SpanStatus var1, boolean var2, Hint var3) {
   }

   @Override
   public Contexts getContexts() {
      return new Contexts();
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
   public SentryId getEventId() {
      return SentryId.EMPTY_ID;
   }

   @Override
   public SentryDate getFinishDate() {
      return new SentryNanotimeDate();
   }

   @Override
   public Span getLatestActiveSpan() {
      return null;
   }

   @Override
   public LocalMetricsAggregator getLocalMetricsAggregator() {
      return null;
   }

   @Override
   public String getName() {
      return "";
   }

   @Override
   public String getOperation() {
      return "";
   }

   @Override
   public TracesSamplingDecision getSamplingDecision() {
      return null;
   }

   @Override
   public SpanContext getSpanContext() {
      return new SpanContext(SentryId.EMPTY_ID, SpanId.EMPTY_ID, "op", null, null);
   }

   @Override
   public List<Span> getSpans() {
      return Collections.emptyList();
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
   public TransactionNameSource getTransactionNameSource() {
      return TransactionNameSource.CUSTOM;
   }

   @Override
   public boolean isFinished() {
      return true;
   }

   @Override
   public boolean isNoOp() {
      return true;
   }

   @Override
   public Boolean isProfileSampled() {
      return null;
   }

   @Override
   public Boolean isSampled() {
      return null;
   }

   @Override
   public void scheduleFinish() {
   }

   @Override
   public void setContext(String var1, Object var2) {
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
   public void setName(String var1) {
   }

   @Override
   public void setName(String var1, TransactionNameSource var2) {
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
      return NoOpSpan.getInstance();
   }

   @Override
   public ISpan startChild(String var1, String var2) {
      return NoOpSpan.getInstance();
   }

   @Override
   public ISpan startChild(String var1, String var2, SentryDate var3) {
      return NoOpSpan.getInstance();
   }

   @Override
   public ISpan startChild(String var1, String var2, SentryDate var3, Instrumenter var4) {
      return NoOpSpan.getInstance();
   }

   @Override
   public ISpan startChild(String var1, String var2, SentryDate var3, Instrumenter var4, SpanOptions var5) {
      return NoOpSpan.getInstance();
   }

   @Override
   public ISpan startChild(String var1, String var2, SpanOptions var3) {
      return NoOpSpan.getInstance();
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
