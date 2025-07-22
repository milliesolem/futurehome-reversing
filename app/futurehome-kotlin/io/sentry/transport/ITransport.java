package io.sentry.transport;

import io.sentry.Hint;
import io.sentry.SentryEnvelope;
import java.io.Closeable;
import java.io.IOException;

public interface ITransport extends Closeable {
   void close(boolean var1) throws IOException;

   void flush(long var1);

   RateLimiter getRateLimiter();

   boolean isHealthy();

   void send(SentryEnvelope var1) throws IOException;

   void send(SentryEnvelope var1, Hint var2) throws IOException;
}
