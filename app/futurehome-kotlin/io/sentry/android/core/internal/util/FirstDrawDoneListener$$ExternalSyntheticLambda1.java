package io.sentry.android.core.internal.util;

import android.view.Window;
import android.view.Window.Callback;
import io.sentry.android.core.BuildInfoProvider;

// $VF: synthetic class
public final class FirstDrawDoneListener$$ExternalSyntheticLambda1 implements Runnable {
   public final Window f$0;
   public final Callback f$1;
   public final Runnable f$2;
   public final BuildInfoProvider f$3;

   @Override
   public final void run() {
      FirstDrawDoneListener.lambda$registerForNextDraw$0(this.f$0, this.f$1, this.f$2, this.f$3);
   }
}
