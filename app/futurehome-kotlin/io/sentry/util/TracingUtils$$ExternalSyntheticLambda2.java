package io.sentry.util;

import io.sentry.IScope;
import io.sentry.PropagationContext;
import io.sentry.Scope;
import io.sentry.SentryOptions;

// $VF: synthetic class
public final class TracingUtils$$ExternalSyntheticLambda2 implements Scope.IWithPropagationContext {
   public final SentryOptions f$0;
   public final IScope f$1;

   @Override
   public final void accept(PropagationContext var1) {
      TracingUtils.lambda$maybeUpdateBaggage$3(this.f$0, this.f$1, var1);
   }
}
