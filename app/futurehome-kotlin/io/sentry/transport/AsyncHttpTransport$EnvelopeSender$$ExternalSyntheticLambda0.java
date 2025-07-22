package io.sentry.transport;

import io.sentry.hints.DiskFlushNotification;
import io.sentry.util.HintUtils;

// $VF: synthetic class
public final class AsyncHttpTransport$EnvelopeSender$$ExternalSyntheticLambda0 implements HintUtils.SentryConsumer {
   public final AsyncHttpTransport.EnvelopeSender f$0;

   @Override
   public final void accept(Object var1) {
      this.f$0.lambda$flush$1$io-sentry-transport-AsyncHttpTransport$EnvelopeSender((DiskFlushNotification)var1);
   }
}
