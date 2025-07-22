package io.sentry.android.core;

import io.sentry.IScope;
import io.sentry.ScopeCallback;
import io.sentry.SentryOptions;
import io.sentry.Session;
import java.util.concurrent.atomic.AtomicReference;

// $VF: synthetic class
public final class InternalSentrySdk$$ExternalSyntheticLambda2 implements ScopeCallback {
   public final Session.State f$0;
   public final boolean f$1;
   public final AtomicReference f$2;
   public final SentryOptions f$3;

   @Override
   public final void run(IScope var1) {
      InternalSentrySdk.lambda$updateSession$2(this.f$0, this.f$1, this.f$2, this.f$3, var1);
   }
}
