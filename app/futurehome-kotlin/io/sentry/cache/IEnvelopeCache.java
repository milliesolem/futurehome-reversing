package io.sentry.cache;

import io.sentry.Hint;
import io.sentry.SentryEnvelope;

public interface IEnvelopeCache extends Iterable<SentryEnvelope> {
   void discard(SentryEnvelope var1);

   void store(SentryEnvelope var1);

   void store(SentryEnvelope var1, Hint var2);
}
