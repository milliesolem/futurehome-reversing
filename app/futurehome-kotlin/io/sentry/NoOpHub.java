package io.sentry;

import io.sentry.metrics.MetricsApi;
import io.sentry.metrics.NoopMetricsAggregator;
import io.sentry.protocol.SentryId;
import io.sentry.protocol.SentryTransaction;
import io.sentry.protocol.User;
import io.sentry.transport.RateLimiter;
import java.util.List;

public final class NoOpHub implements IHub {
   private static final NoOpHub instance = new NoOpHub();
   private final SentryOptions emptyOptions = SentryOptions.empty();
   private final MetricsApi metricsApi = new MetricsApi(NoopMetricsAggregator.getInstance());

   private NoOpHub() {
   }

   public static NoOpHub getInstance() {
      return instance;
   }

   @Override
   public void addBreadcrumb(Breadcrumb var1) {
   }

   @Override
   public void addBreadcrumb(Breadcrumb var1, Hint var2) {
   }

   @Override
   public void bindClient(ISentryClient var1) {
   }

   @Override
   public SentryId captureCheckIn(CheckIn var1) {
      return SentryId.EMPTY_ID;
   }

   @Override
   public SentryId captureEnvelope(SentryEnvelope var1, Hint var2) {
      return SentryId.EMPTY_ID;
   }

   @Override
   public SentryId captureEvent(SentryEvent var1, Hint var2) {
      return SentryId.EMPTY_ID;
   }

   @Override
   public SentryId captureEvent(SentryEvent var1, Hint var2, ScopeCallback var3) {
      return SentryId.EMPTY_ID;
   }

   @Override
   public SentryId captureException(Throwable var1, Hint var2) {
      return SentryId.EMPTY_ID;
   }

   @Override
   public SentryId captureException(Throwable var1, Hint var2, ScopeCallback var3) {
      return SentryId.EMPTY_ID;
   }

   @Override
   public SentryId captureMessage(String var1, SentryLevel var2) {
      return SentryId.EMPTY_ID;
   }

   @Override
   public SentryId captureMessage(String var1, SentryLevel var2, ScopeCallback var3) {
      return SentryId.EMPTY_ID;
   }

   @Override
   public SentryId captureReplay(SentryReplayEvent var1, Hint var2) {
      return SentryId.EMPTY_ID;
   }

   @Override
   public SentryId captureTransaction(SentryTransaction var1, TraceContext var2, Hint var3, ProfilingTraceData var4) {
      return SentryId.EMPTY_ID;
   }

   @Override
   public void captureUserFeedback(UserFeedback var1) {
   }

   @Override
   public void clearBreadcrumbs() {
   }

   @Override
   public IHub clone() {
      return instance;
   }

   @Override
   public void close() {
   }

   @Override
   public void close(boolean var1) {
   }

   @Override
   public void configureScope(ScopeCallback var1) {
   }

   @Override
   public TransactionContext continueTrace(String var1, List<String> var2) {
      return null;
   }

   @Override
   public void endSession() {
   }

   @Override
   public void flush(long var1) {
   }

   @Override
   public BaggageHeader getBaggage() {
      return null;
   }

   @Override
   public SentryId getLastEventId() {
      return SentryId.EMPTY_ID;
   }

   @Override
   public SentryOptions getOptions() {
      return this.emptyOptions;
   }

   @Override
   public RateLimiter getRateLimiter() {
      return null;
   }

   @Override
   public ISpan getSpan() {
      return null;
   }

   @Override
   public SentryTraceHeader getTraceparent() {
      return null;
   }

   @Override
   public ITransaction getTransaction() {
      return null;
   }

   @Override
   public Boolean isCrashedLastRun() {
      return null;
   }

   @Override
   public boolean isEnabled() {
      return false;
   }

   @Override
   public boolean isHealthy() {
      return true;
   }

   @Override
   public MetricsApi metrics() {
      return this.metricsApi;
   }

   @Override
   public void popScope() {
   }

   @Override
   public void pushScope() {
   }

   @Override
   public void removeExtra(String var1) {
   }

   @Override
   public void removeTag(String var1) {
   }

   @Override
   public void reportFullyDisplayed() {
   }

   @Override
   public void setExtra(String var1, String var2) {
   }

   @Override
   public void setFingerprint(List<String> var1) {
   }

   @Override
   public void setLevel(SentryLevel var1) {
   }

   @Override
   public void setSpanContext(Throwable var1, ISpan var2, String var3) {
   }

   @Override
   public void setTag(String var1, String var2) {
   }

   @Override
   public void setTransaction(String var1) {
   }

   @Override
   public void setUser(User var1) {
   }

   @Override
   public void startSession() {
   }

   @Override
   public ITransaction startTransaction(TransactionContext var1, TransactionOptions var2) {
      return NoOpTransaction.getInstance();
   }

   @Deprecated
   @Override
   public SentryTraceHeader traceHeaders() {
      return new SentryTraceHeader(SentryId.EMPTY_ID, SpanId.EMPTY_ID, true);
   }

   @Override
   public void withScope(ScopeCallback var1) {
      var1.run(NoOpScope.getInstance());
   }
}
