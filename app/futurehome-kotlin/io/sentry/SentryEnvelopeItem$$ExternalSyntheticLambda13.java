package io.sentry;

import io.sentry.clientreport.ClientReport;
import java.util.concurrent.Callable;

// $VF: synthetic class
public final class SentryEnvelopeItem$$ExternalSyntheticLambda13 implements Callable {
   public final ISerializer f$0;
   public final ClientReport f$1;

   @Override
   public final Object call() {
      return SentryEnvelopeItem.lambda$fromClientReport$21(this.f$0, this.f$1);
   }
}
