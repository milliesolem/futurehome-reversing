package io.sentry.android.core.internal.util;

import android.view.FrameMetrics;
import android.view.Window;
import android.view.Window.OnFrameMetricsAvailableListener;
import io.sentry.android.core.BuildInfoProvider;

// $VF: synthetic class
public final class SentryFrameMetricsCollector$$ExternalSyntheticLambda4 implements OnFrameMetricsAvailableListener {
   public final SentryFrameMetricsCollector f$0;
   public final BuildInfoProvider f$1;

   public final void onFrameMetricsAvailable(Window var1, FrameMetrics var2, int var3) {
      this.f$0.lambda$new$2$io-sentry-android-core-internal-util-SentryFrameMetricsCollector(this.f$1, var1, var2, var3);
   }
}
