package io.sentry;

import io.sentry.metrics.MetricsApi;
import io.sentry.protocol.SentryId;
import io.sentry.protocol.SentryTransaction;
import io.sentry.protocol.User;
import io.sentry.transport.RateLimiter;
import java.util.List;

public final class HubAdapter implements IHub {
   private static final HubAdapter INSTANCE = new HubAdapter();

   private HubAdapter() {
   }

   public static HubAdapter getInstance() {
      return INSTANCE;
   }

   @Override
   public void addBreadcrumb(Breadcrumb var1) {
      this.addBreadcrumb(var1, new Hint());
   }

   @Override
   public void addBreadcrumb(Breadcrumb var1, Hint var2) {
      Sentry.addBreadcrumb(var1, var2);
   }

   @Override
   public void bindClient(ISentryClient var1) {
      Sentry.bindClient(var1);
   }

   @Override
   public SentryId captureCheckIn(CheckIn var1) {
      return Sentry.captureCheckIn(var1);
   }

   @Override
   public SentryId captureEnvelope(SentryEnvelope var1, Hint var2) {
      return Sentry.getCurrentHub().captureEnvelope(var1, var2);
   }

   @Override
   public SentryId captureEvent(SentryEvent var1, Hint var2) {
      return Sentry.captureEvent(var1, var2);
   }

   @Override
   public SentryId captureEvent(SentryEvent var1, Hint var2, ScopeCallback var3) {
      return Sentry.captureEvent(var1, var2, var3);
   }

   @Override
   public SentryId captureException(Throwable var1, Hint var2) {
      return Sentry.captureException(var1, var2);
   }

   @Override
   public SentryId captureException(Throwable var1, Hint var2, ScopeCallback var3) {
      return Sentry.captureException(var1, var2, var3);
   }

   @Override
   public SentryId captureMessage(String var1, SentryLevel var2) {
      return Sentry.captureMessage(var1, var2);
   }

   @Override
   public SentryId captureMessage(String var1, SentryLevel var2, ScopeCallback var3) {
      return Sentry.captureMessage(var1, var2, var3);
   }

   @Override
   public SentryId captureReplay(SentryReplayEvent var1, Hint var2) {
      return Sentry.getCurrentHub().captureReplay(var1, var2);
   }

   @Override
   public SentryId captureTransaction(SentryTransaction var1, TraceContext var2, Hint var3, ProfilingTraceData var4) {
      return Sentry.getCurrentHub().captureTransaction(var1, var2, var3, var4);
   }

   @Override
   public void captureUserFeedback(UserFeedback var1) {
      Sentry.captureUserFeedback(var1);
   }

   @Override
   public void clearBreadcrumbs() {
      Sentry.clearBreadcrumbs();
   }

   @Override
   public IHub clone() {
      return Sentry.getCurrentHub().clone();
   }

   @Override
   public void close() {
      Sentry.close();
   }

   @Override
   public void close(boolean var1) {
      Sentry.close();
   }

   @Override
   public void configureScope(ScopeCallback var1) {
      Sentry.configureScope(var1);
   }

   @Override
   public TransactionContext continueTrace(String var1, List<String> var2) {
      return Sentry.continueTrace(var1, var2);
   }

   @Override
   public void endSession() {
      Sentry.endSession();
   }

   @Override
   public void flush(long var1) {
      Sentry.flush(var1);
   }

   @Override
   public BaggageHeader getBaggage() {
      return Sentry.getBaggage();
   }

   @Override
   public SentryId getLastEventId() {
      return Sentry.getLastEventId();
   }

   @Override
   public SentryOptions getOptions() {
      return Sentry.getCurrentHub().getOptions();
   }

   @Override
   public RateLimiter getRateLimiter() {
      return Sentry.getCurrentHub().getRateLimiter();
   }

   @Override
   public ISpan getSpan() {
      return Sentry.getCurrentHub().getSpan();
   }

   @Override
   public SentryTraceHeader getTraceparent() {
      return Sentry.getTraceparent();
   }

   @Override
   public ITransaction getTransaction() {
      return Sentry.getCurrentHub().getTransaction();
   }

   @Override
   public Boolean isCrashedLastRun() {
      return Sentry.isCrashedLastRun();
   }

   @Override
   public boolean isEnabled() {
      return Sentry.isEnabled();
   }

   @Override
   public boolean isHealthy() {
      return Sentry.isHealthy();
   }

   @Override
   public MetricsApi metrics() {
      return Sentry.getCurrentHub().metrics();
   }

   @Override
   public void popScope() {
      Sentry.popScope();
   }

   @Override
   public void pushScope() {
      Sentry.pushScope();
   }

   @Override
   public void removeExtra(String var1) {
      Sentry.removeExtra(var1);
   }

   @Override
   public void removeTag(String var1) {
      Sentry.removeTag(var1);
   }

   @Override
   public void reportFullyDisplayed() {
      Sentry.reportFullyDisplayed();
   }

   @Override
   public void setExtra(String var1, String var2) {
      Sentry.setExtra(var1, var2);
   }

   @Override
   public void setFingerprint(List<String> var1) {
      Sentry.setFingerprint(var1);
   }

   @Override
   public void setLevel(SentryLevel var1) {
      Sentry.setLevel(var1);
   }

   @Override
   public void setSpanContext(Throwable var1, ISpan var2, String var3) {
      Sentry.getCurrentHub().setSpanContext(var1, var2, var3);
   }

   @Override
   public void setTag(String var1, String var2) {
      Sentry.setTag(var1, var2);
   }

   @Override
   public void setTransaction(String var1) {
      Sentry.setTransaction(var1);
   }

   @Override
   public void setUser(User var1) {
      Sentry.setUser(var1);
   }

   @Override
   public void startSession() {
      Sentry.startSession();
   }

   @Override
   public ITransaction startTransaction(TransactionContext var1, TransactionOptions var2) {
      return Sentry.startTransaction(var1, var2);
   }

   @Deprecated
   @Override
   public SentryTraceHeader traceHeaders() {
      return Sentry.traceHeaders();
   }

   @Override
   public void withScope(ScopeCallback var1) {
      Sentry.withScope(var1);
   }
}
