package io.sentry.android.core;

import io.sentry.IHub;

// $VF: synthetic class
public final class AnrIntegration$$ExternalSyntheticLambda1 implements ANRWatchDog.ANRListener {
   public final AnrIntegration f$0;
   public final IHub f$1;
   public final SentryAndroidOptions f$2;

   @Override
   public final void onAppNotResponding(ApplicationNotResponding var1) {
      this.f$0.lambda$startAnrWatchdog$1$io-sentry-android-core-AnrIntegration(this.f$1, this.f$2, var1);
   }
}
