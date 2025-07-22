package io.flutter.plugins.firebase.messaging;

import com.google.android.gms.tasks.TaskCompletionSource;

// $VF: synthetic class
public final class FlutterFirebaseMessagingPlugin$$ExternalSyntheticLambda4 implements ErrorCallback {
   public final TaskCompletionSource f$0;

   @Override
   public final void onError(String var1) {
      FlutterFirebaseMessagingPlugin.lambda$requestPermissions$11(this.f$0, var1);
   }
}
