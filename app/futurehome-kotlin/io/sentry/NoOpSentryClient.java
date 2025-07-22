package io.sentry;

import io.sentry.metrics.NoopMetricsAggregator;
import io.sentry.protocol.SentryId;
import io.sentry.protocol.SentryTransaction;
import io.sentry.transport.RateLimiter;

final class NoOpSentryClient implements ISentryClient {
   private static final NoOpSentryClient instance = new NoOpSentryClient();

   private NoOpSentryClient() {
   }

   public static NoOpSentryClient getInstance() {
      return instance;
   }

   @Override
   public SentryId captureCheckIn(CheckIn var1, IScope var2, Hint var3) {
      return SentryId.EMPTY_ID;
   }

   @Override
   public SentryId captureEnvelope(SentryEnvelope var1, Hint var2) {
      return SentryId.EMPTY_ID;
   }

   @Override
   public SentryId captureEvent(SentryEvent var1, IScope var2, Hint var3) {
      return SentryId.EMPTY_ID;
   }

   @Override
   public SentryId captureReplayEvent(SentryReplayEvent var1, IScope var2, Hint var3) {
      return SentryId.EMPTY_ID;
   }

   @Override
   public void captureSession(Session var1, Hint var2) {
   }

   @Override
   public SentryId captureTransaction(SentryTransaction var1, TraceContext var2, IScope var3, Hint var4, ProfilingTraceData var5) {
      return SentryId.EMPTY_ID;
   }

   @Override
   public void captureUserFeedback(UserFeedback var1) {
   }

   @Override
   public void close() {
   }

   @Override
   public void close(boolean var1) {
   }

   @Override
   public void flush(long var1) {
   }

   @Override
   public IMetricsAggregator getMetricsAggregator() {
      return NoopMetricsAggregator.getInstance();
   }

   @Override
   public RateLimiter getRateLimiter() {
      return null;
   }

   @Override
   public boolean isEnabled() {
      return false;
   }
}
