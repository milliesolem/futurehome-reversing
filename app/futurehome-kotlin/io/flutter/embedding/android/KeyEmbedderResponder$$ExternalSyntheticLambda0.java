package io.flutter.embedding.android;

import io.flutter.plugin.common.BinaryMessenger;
import java.nio.ByteBuffer;

// $VF: synthetic class
public final class KeyEmbedderResponder$$ExternalSyntheticLambda0 implements BinaryMessenger.BinaryReply {
   public final KeyboardManager.Responder.OnKeyEventHandledCallback f$0;

   @Override
   public final void reply(ByteBuffer var1) {
      KeyEmbedderResponder.lambda$sendKeyEvent$2(this.f$0, var1);
   }
}
