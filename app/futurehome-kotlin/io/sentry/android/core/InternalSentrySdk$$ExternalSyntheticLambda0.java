package io.sentry.android.core;

import io.sentry.IScope;
import io.sentry.ScopeCallback;
import java.util.concurrent.atomic.AtomicReference;

// $VF: synthetic class
public final class InternalSentrySdk$$ExternalSyntheticLambda0 implements ScopeCallback {
   public final AtomicReference f$0;

   @Override
   public final void run(IScope var1) {
      InternalSentrySdk.lambda$getCurrentScope$0(this.f$0, var1);
   }
}
