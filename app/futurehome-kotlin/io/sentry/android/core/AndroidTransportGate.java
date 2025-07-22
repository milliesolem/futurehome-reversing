package io.sentry.android.core;

import io.sentry.IConnectionStatusProvider;
import io.sentry.SentryOptions;
import io.sentry.transport.ITransportGate;

final class AndroidTransportGate implements ITransportGate {
   private final SentryOptions options;

   AndroidTransportGate(SentryOptions var1) {
      this.options = var1;
   }

   @Override
   public boolean isConnected() {
      return this.isConnected(this.options.getConnectionStatusProvider().getConnectionStatus());
   }

   boolean isConnected(IConnectionStatusProvider.ConnectionStatus var1) {
      int var2 = <unrepresentable>.$SwitchMap$io$sentry$IConnectionStatusProvider$ConnectionStatus[var1.ordinal()];
      return var2 == 1 || var2 == 2 || var2 == 3;
   }
}
