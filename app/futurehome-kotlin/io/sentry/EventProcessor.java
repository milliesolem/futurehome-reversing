package io.sentry;

import io.sentry.protocol.SentryTransaction;

public interface EventProcessor {
   SentryEvent process(SentryEvent var1, Hint var2);

   SentryReplayEvent process(SentryReplayEvent var1, Hint var2);

   SentryTransaction process(SentryTransaction var1, Hint var2);
}
