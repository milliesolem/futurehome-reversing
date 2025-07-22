package io.sentry.android.core;

import io.sentry.IScope;
import io.sentry.ITransaction;
import io.sentry.Scope;

// $VF: synthetic class
public final class ActivityLifecycleIntegration$$ExternalSyntheticLambda0 implements Scope.IWithTransaction {
   public final ITransaction f$0;
   public final IScope f$1;

   @Override
   public final void accept(ITransaction var1) {
      ActivityLifecycleIntegration.lambda$clearScope$4(this.f$0, this.f$1, var1);
   }
}
