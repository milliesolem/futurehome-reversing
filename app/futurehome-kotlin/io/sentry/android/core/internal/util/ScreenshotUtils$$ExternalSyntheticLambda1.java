package io.sentry.android.core.internal.util;

import android.view.PixelCopy.OnPixelCopyFinishedListener;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

// $VF: synthetic class
public final class ScreenshotUtils$$ExternalSyntheticLambda1 implements OnPixelCopyFinishedListener {
   public final AtomicBoolean f$0;
   public final CountDownLatch f$1;

   public final void onPixelCopyFinished(int var1) {
      ScreenshotUtils.lambda$takeScreenshot$0(this.f$0, this.f$1, var1);
   }
}
