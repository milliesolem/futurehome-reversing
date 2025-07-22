package io.sentry;

import java.io.File;
import java.util.concurrent.Callable;

// $VF: synthetic class
public final class SentryEnvelopeItem$$ExternalSyntheticLambda23 implements Callable {
   public final File f$0;
   public final long f$1;
   public final ProfilingTraceData f$2;
   public final ISerializer f$3;

   @Override
   public final Object call() {
      return SentryEnvelopeItem.lambda$fromProfilingTrace$18(this.f$0, this.f$1, this.f$2, this.f$3);
   }
}
