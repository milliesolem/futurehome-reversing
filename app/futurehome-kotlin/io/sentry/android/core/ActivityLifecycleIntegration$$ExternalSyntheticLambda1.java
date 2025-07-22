package io.sentry.android.core;

import io.sentry.ITransaction;
import io.sentry.TransactionFinishedCallback;
import java.lang.ref.WeakReference;

// $VF: synthetic class
public final class ActivityLifecycleIntegration$$ExternalSyntheticLambda1 implements TransactionFinishedCallback {
   public final ActivityLifecycleIntegration f$0;
   public final WeakReference f$1;
   public final String f$2;

   @Override
   public final void execute(ITransaction var1) {
      this.f$0.lambda$startTracing$0$io-sentry-android-core-ActivityLifecycleIntegration(this.f$1, this.f$2, var1);
   }
}
