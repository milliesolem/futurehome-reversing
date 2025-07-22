package io.sentry.android.core.internal.util;

import io.sentry.ILogger;
import java.lang.Thread.UncaughtExceptionHandler;

// $VF: synthetic class
public final class SentryFrameMetricsCollector$$ExternalSyntheticLambda2 implements UncaughtExceptionHandler {
   public final ILogger f$0;

   @Override
   public final void uncaughtException(Thread var1, Throwable var2) {
      SentryFrameMetricsCollector.lambda$new$0(this.f$0, var1, var2);
   }
}
