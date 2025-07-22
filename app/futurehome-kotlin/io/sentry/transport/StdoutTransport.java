package io.sentry.transport;

import io.sentry.Hint;
import io.sentry.ISerializer;
import io.sentry.SentryEnvelope;
import io.sentry.util.Objects;
import java.io.IOException;

public final class StdoutTransport implements ITransport {
   private final ISerializer serializer;

   public StdoutTransport(ISerializer var1) {
      this.serializer = Objects.requireNonNull(var1, "Serializer is required");
   }

   @Override
   public void close() {
   }

   @Override
   public void close(boolean var1) {
   }

   @Override
   public void flush(long var1) {
      System.out.println("Flushing");
   }

   @Override
   public RateLimiter getRateLimiter() {
      return null;
   }

   @Override
   public void send(SentryEnvelope var1, Hint var2) throws IOException {
      Objects.requireNonNull(var1, "SentryEnvelope is required");

      try {
         this.serializer.serialize(var1, System.out);
      } finally {
         return;
      }
   }
}
