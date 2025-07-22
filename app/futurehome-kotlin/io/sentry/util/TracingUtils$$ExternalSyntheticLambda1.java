package io.sentry.util;

import io.sentry.IScope;
import io.sentry.PropagationContext;
import io.sentry.Scope;

// $VF: synthetic class
public final class TracingUtils$$ExternalSyntheticLambda1 implements Scope.IWithPropagationContext {
   public final IScope f$0;

   @Override
   public final void accept(PropagationContext var1) {
      TracingUtils.lambda$startNewTrace$0(this.f$0, var1);
   }
}
