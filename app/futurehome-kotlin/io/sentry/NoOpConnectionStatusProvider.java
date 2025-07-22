package io.sentry;

public final class NoOpConnectionStatusProvider implements IConnectionStatusProvider {
   @Override
   public boolean addConnectionStatusObserver(IConnectionStatusProvider.IConnectionStatusObserver var1) {
      return false;
   }

   @Override
   public IConnectionStatusProvider.ConnectionStatus getConnectionStatus() {
      return IConnectionStatusProvider.ConnectionStatus.UNKNOWN;
   }

   @Override
   public String getConnectionType() {
      return null;
   }

   @Override
   public void removeConnectionStatusObserver(IConnectionStatusProvider.IConnectionStatusObserver var1) {
   }
}
