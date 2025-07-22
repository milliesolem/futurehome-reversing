package io.sentry.transport;

import io.sentry.hints.Retryable;
import io.sentry.util.HintUtils;

// $VF: synthetic class
public final class AsyncHttpTransport$$ExternalSyntheticLambda1 implements HintUtils.SentryConsumer {
   public final boolean f$0;

   @Override
   public final void accept(Object var1) {
      AsyncHttpTransport.lambda$markHintWhenSendingFailed$3(this.f$0, (Retryable)var1);
   }
}
