package io.sentry.android.core;

import android.content.Context;
import io.sentry.ILogger;
import io.sentry.Sentry;
import io.sentry.SentryOptions;

// $VF: synthetic class
public final class SentryAndroid$$ExternalSyntheticLambda1 implements Sentry.OptionsConfiguration {
   public final ILogger f$0;
   public final Context f$1;
   public final Sentry.OptionsConfiguration f$2;

   @Override
   public final void configure(SentryOptions var1) {
      SentryAndroid.lambda$init$1(this.f$0, this.f$1, this.f$2, (SentryAndroidOptions)var1);
   }
}
