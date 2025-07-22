package io.sentry.android.core;

import android.view.View;
import io.sentry.ILogger;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

// $VF: synthetic class
public final class ViewHierarchyEventProcessor$$ExternalSyntheticLambda0 implements Runnable {
   public final AtomicReference f$0;
   public final View f$1;
   public final List f$2;
   public final CountDownLatch f$3;
   public final ILogger f$4;

   @Override
   public final void run() {
      ViewHierarchyEventProcessor.lambda$snapshotViewHierarchy$0(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4);
   }
}
