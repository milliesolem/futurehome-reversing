package io.sentry;

import io.sentry.transport.ITransport;

public interface ITransportFactory {
   ITransport create(SentryOptions var1, RequestDetails var2);
}
