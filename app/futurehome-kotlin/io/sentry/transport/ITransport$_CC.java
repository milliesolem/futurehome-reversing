package io.sentry.transport;

import io.sentry.Hint;
import io.sentry.SentryEnvelope;
import java.io.IOException;

// $VF: synthetic class
public final class ITransport$_CC {
   public static boolean $default$isHealthy(ITransport var0) {
      return true;
   }

   public static void $default$send(ITransport var0, SentryEnvelope var1) throws IOException {
      var0.send(var1, new Hint());
   }
}
