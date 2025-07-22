package io.flutter.plugins.firebase.messaging;

import android.content.Intent;
import java.util.concurrent.CountDownLatch;

// $VF: synthetic class
public final class FlutterFirebaseMessagingBackgroundService$$ExternalSyntheticLambda0 implements Runnable {
   public final Intent f$0;
   public final CountDownLatch f$1;

   @Override
   public final void run() {
      FlutterFirebaseMessagingBackgroundService.lambda$onHandleWork$0(this.f$0, this.f$1);
   }
}
