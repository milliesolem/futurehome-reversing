package io.sentry;

import io.sentry.transport.ITransport;
import io.sentry.transport.NoOpTransport;

public final class NoOpTransportFactory implements ITransportFactory {
   private static final NoOpTransportFactory instance = new NoOpTransportFactory();

   private NoOpTransportFactory() {
   }

   public static NoOpTransportFactory getInstance() {
      return instance;
   }

   @Override
   public ITransport create(SentryOptions var1, RequestDetails var2) {
      return NoOpTransport.getInstance();
   }
}
