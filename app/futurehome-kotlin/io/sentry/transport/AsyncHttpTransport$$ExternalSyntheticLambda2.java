package io.sentry.transport;

import io.sentry.ILogger;
import io.sentry.cache.IEnvelopeCache;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

// $VF: synthetic class
public final class AsyncHttpTransport$$ExternalSyntheticLambda2 implements RejectedExecutionHandler {
   public final IEnvelopeCache f$0;
   public final ILogger f$1;

   @Override
   public final void rejectedExecution(Runnable var1, ThreadPoolExecutor var2) {
      AsyncHttpTransport.lambda$initExecutor$1(this.f$0, this.f$1, var1, var2);
   }
}
