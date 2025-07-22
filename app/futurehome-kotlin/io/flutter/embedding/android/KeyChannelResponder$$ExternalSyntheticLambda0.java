package io.flutter.embedding.android;

import io.flutter.embedding.engine.systemchannels.KeyEventChannel;

// $VF: synthetic class
public final class KeyChannelResponder$$ExternalSyntheticLambda0 implements KeyEventChannel.EventResponseHandler {
   public final KeyboardManager.Responder.OnKeyEventHandledCallback f$0;

   @Override
   public final void onFrameworkResponse(boolean var1) {
      KeyChannelResponder.lambda$handleEvent$0(this.f$0, var1);
   }
}
