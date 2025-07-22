package io.sentry;

import io.sentry.metrics.MetricsApi;
import io.sentry.protocol.SentryId;
import io.sentry.protocol.SentryTransaction;
import io.sentry.protocol.User;
import io.sentry.transport.RateLimiter;
import java.util.List;

public interface IHub {
   void addBreadcrumb(Breadcrumb var1);

   void addBreadcrumb(Breadcrumb var1, Hint var2);

   void addBreadcrumb(String var1);

   void addBreadcrumb(String var1, String var2);

   void bindClient(ISentryClient var1);

   SentryId captureCheckIn(CheckIn var1);

   SentryId captureEnvelope(SentryEnvelope var1);

   SentryId captureEnvelope(SentryEnvelope var1, Hint var2);

   SentryId captureEvent(SentryEvent var1);

   SentryId captureEvent(SentryEvent var1, Hint var2);

   SentryId captureEvent(SentryEvent var1, Hint var2, ScopeCallback var3);

   SentryId captureEvent(SentryEvent var1, ScopeCallback var2);

   SentryId captureException(Throwable var1);

   SentryId captureException(Throwable var1, Hint var2);

   SentryId captureException(Throwable var1, Hint var2, ScopeCallback var3);

   SentryId captureException(Throwable var1, ScopeCallback var2);

   SentryId captureMessage(String var1);

   SentryId captureMessage(String var1, ScopeCallback var2);

   SentryId captureMessage(String var1, SentryLevel var2);

   SentryId captureMessage(String var1, SentryLevel var2, ScopeCallback var3);

   SentryId captureReplay(SentryReplayEvent var1, Hint var2);

   SentryId captureTransaction(SentryTransaction var1, Hint var2);

   SentryId captureTransaction(SentryTransaction var1, TraceContext var2);

   SentryId captureTransaction(SentryTransaction var1, TraceContext var2, Hint var3);

   SentryId captureTransaction(SentryTransaction var1, TraceContext var2, Hint var3, ProfilingTraceData var4);

   void captureUserFeedback(UserFeedback var1);

   void clearBreadcrumbs();

   IHub clone();

   void close();

   void close(boolean var1);

   void configureScope(ScopeCallback var1);

   TransactionContext continueTrace(String var1, List<String> var2);

   void endSession();

   void flush(long var1);

   BaggageHeader getBaggage();

   SentryId getLastEventId();

   SentryOptions getOptions();

   RateLimiter getRateLimiter();

   ISpan getSpan();

   SentryTraceHeader getTraceparent();

   ITransaction getTransaction();

   Boolean isCrashedLastRun();

   boolean isEnabled();

   boolean isHealthy();

   MetricsApi metrics();

   void popScope();

   void pushScope();

   void removeExtra(String var1);

   void removeTag(String var1);

   @Deprecated
   void reportFullDisplayed();

   void reportFullyDisplayed();

   void setExtra(String var1, String var2);

   void setFingerprint(List<String> var1);

   void setLevel(SentryLevel var1);

   void setSpanContext(Throwable var1, ISpan var2, String var3);

   void setTag(String var1, String var2);

   void setTransaction(String var1);

   void setUser(User var1);

   void startSession();

   ITransaction startTransaction(TransactionContext var1);

   ITransaction startTransaction(TransactionContext var1, TransactionOptions var2);

   ITransaction startTransaction(String var1, String var2);

   ITransaction startTransaction(String var1, String var2, TransactionOptions var3);

   @Deprecated
   SentryTraceHeader traceHeaders();

   void withScope(ScopeCallback var1);
}
