package io.sentry.transport;

import io.sentry.Hint;
import io.sentry.SentryEnvelope;
import java.io.IOException;

public final class NoOpTransport implements ITransport {
   private static final NoOpTransport instance = new NoOpTransport();

   private NoOpTransport() {
   }

   public static NoOpTransport getInstance() {
      return instance;
   }

   @Override
   public void close() throws IOException {
   }

   @Override
   public void close(boolean var1) throws IOException {
   }

   @Override
   public void flush(long var1) {
   }

   @Override
   public RateLimiter getRateLimiter() {
      return null;
   }

   @Override
   public void send(SentryEnvelope var1, Hint var2) throws IOException {
   }
}
