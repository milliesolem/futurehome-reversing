package io.sentry;

import io.sentry.hints.Retryable;
import io.sentry.util.HintUtils;
import java.io.File;

// $VF: synthetic class
public final class OutboxSender$$ExternalSyntheticLambda1 implements HintUtils.SentryConsumer {
   public final OutboxSender f$0;
   public final File f$1;

   @Override
   public final void accept(Object var1) {
      this.f$0.lambda$processFile$0$io-sentry-OutboxSender(this.f$1, (Retryable)var1);
   }
}
