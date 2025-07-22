package io.sentry.transport;

import io.sentry.Hint;
import io.sentry.SentryEnvelope;
import io.sentry.cache.IEnvelopeCache;
import java.util.Collections;
import java.util.Iterator;

public final class NoOpEnvelopeCache implements IEnvelopeCache {
   private static final NoOpEnvelopeCache instance = new NoOpEnvelopeCache();

   public static NoOpEnvelopeCache getInstance() {
      return instance;
   }

   @Override
   public void discard(SentryEnvelope var1) {
   }

   @Override
   public Iterator<SentryEnvelope> iterator() {
      return Collections.emptyIterator();
   }

   @Override
   public void store(SentryEnvelope var1, Hint var2) {
   }
}
