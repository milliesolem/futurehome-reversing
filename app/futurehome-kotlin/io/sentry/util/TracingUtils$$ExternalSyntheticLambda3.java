package io.sentry.util;

import io.sentry.IScope;
import io.sentry.ScopeCallback;
import io.sentry.SentryOptions;

// $VF: synthetic class
public final class TracingUtils$$ExternalSyntheticLambda3 implements ScopeCallback {
   public final TracingUtils.PropagationContextHolder f$0;
   public final SentryOptions f$1;

   @Override
   public final void run(IScope var1) {
      TracingUtils.lambda$trace$2(this.f$0, this.f$1, var1);
   }
}
