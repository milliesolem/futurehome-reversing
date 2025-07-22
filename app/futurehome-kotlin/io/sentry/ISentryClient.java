package io.sentry;

import io.sentry.protocol.SentryId;
import io.sentry.protocol.SentryTransaction;
import io.sentry.transport.RateLimiter;

public interface ISentryClient {
   SentryId captureCheckIn(CheckIn var1, IScope var2, Hint var3);

   SentryId captureEnvelope(SentryEnvelope var1);

   SentryId captureEnvelope(SentryEnvelope var1, Hint var2);

   SentryId captureEvent(SentryEvent var1);

   SentryId captureEvent(SentryEvent var1, Hint var2);

   SentryId captureEvent(SentryEvent var1, IScope var2);

   SentryId captureEvent(SentryEvent var1, IScope var2, Hint var3);

   SentryId captureException(Throwable var1);

   SentryId captureException(Throwable var1, Hint var2);

   SentryId captureException(Throwable var1, IScope var2);

   SentryId captureException(Throwable var1, IScope var2, Hint var3);

   SentryId captureMessage(String var1, SentryLevel var2);

   SentryId captureMessage(String var1, SentryLevel var2, IScope var3);

   SentryId captureReplayEvent(SentryReplayEvent var1, IScope var2, Hint var3);

   void captureSession(Session var1);

   void captureSession(Session var1, Hint var2);

   SentryId captureTransaction(SentryTransaction var1);

   SentryId captureTransaction(SentryTransaction var1, IScope var2, Hint var3);

   SentryId captureTransaction(SentryTransaction var1, TraceContext var2);

   SentryId captureTransaction(SentryTransaction var1, TraceContext var2, IScope var3, Hint var4);

   SentryId captureTransaction(SentryTransaction var1, TraceContext var2, IScope var3, Hint var4, ProfilingTraceData var5);

   void captureUserFeedback(UserFeedback var1);

   void close();

   void close(boolean var1);

   void flush(long var1);

   IMetricsAggregator getMetricsAggregator();

   RateLimiter getRateLimiter();

   boolean isEnabled();

   boolean isHealthy();
}
