package io.sentry;

import java.util.concurrent.atomic.AtomicReference;

// $VF: synthetic class
public final class SentryTracer$$ExternalSyntheticLambda4 implements ScopeCallback {
   public final AtomicReference f$0;
   public final AtomicReference f$1;

   @Override
   public final void run(IScope var1) {
      SentryTracer.lambda$updateBaggageValues$4(this.f$0, this.f$1, var1);
   }
}
