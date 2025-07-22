package io.sentry;

import java.io.File;
import java.util.concurrent.Callable;

// $VF: synthetic class
public final class SentryEnvelopeItem$$ExternalSyntheticLambda0 implements Callable {
   public final ISerializer f$0;
   public final SentryReplayEvent f$1;
   public final ReplayRecording f$2;
   public final File f$3;
   public final ILogger f$4;
   public final boolean f$5;

   @Override
   public final Object call() {
      return SentryEnvelopeItem.lambda$fromReplay$24(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5);
   }
}
