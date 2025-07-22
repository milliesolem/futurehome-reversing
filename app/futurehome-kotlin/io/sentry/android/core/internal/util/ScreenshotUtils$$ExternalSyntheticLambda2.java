package io.sentry.android.core.internal.util;

import android.graphics.Canvas;
import android.view.View;
import io.sentry.ILogger;
import java.util.concurrent.CountDownLatch;

// $VF: synthetic class
public final class ScreenshotUtils$$ExternalSyntheticLambda2 implements Runnable {
   public final View f$0;
   public final Canvas f$1;
   public final ILogger f$2;
   public final CountDownLatch f$3;

   @Override
   public final void run() {
      ScreenshotUtils.lambda$takeScreenshot$1(this.f$0, this.f$1, this.f$2, this.f$3);
   }
}
