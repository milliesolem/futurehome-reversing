package io.sentry;

import java.util.concurrent.atomic.AtomicReference;

// $VF: synthetic class
public final class SentryTracer$$ExternalSyntheticLambda1 implements SpanFinishedCallback {
   public final SentryTracer f$0;
   public final SpanFinishedCallback f$1;
   public final AtomicReference f$2;

   @Override
   public final void execute(Span var1) {
      this.f$0.lambda$finish$0$io-sentry-SentryTracer(this.f$1, this.f$2, var1);
   }
}
