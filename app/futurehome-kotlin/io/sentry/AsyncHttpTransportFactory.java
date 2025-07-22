package io.sentry;

import io.sentry.transport.AsyncHttpTransport;
import io.sentry.transport.ITransport;
import io.sentry.transport.RateLimiter;
import io.sentry.util.Objects;

public final class AsyncHttpTransportFactory implements ITransportFactory {
   @Override
   public ITransport create(SentryOptions var1, RequestDetails var2) {
      Objects.requireNonNull(var1, "options is required");
      Objects.requireNonNull(var2, "requestDetails is required");
      return new AsyncHttpTransport(var1, new RateLimiter(var1), var1.getTransportGate(), var2);
   }
}
