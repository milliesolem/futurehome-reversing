package io.sentry;

public interface IConnectionStatusProvider {
   boolean addConnectionStatusObserver(IConnectionStatusProvider.IConnectionStatusObserver var1);

   IConnectionStatusProvider.ConnectionStatus getConnectionStatus();

   String getConnectionType();

   void removeConnectionStatusObserver(IConnectionStatusProvider.IConnectionStatusObserver var1);

   public static enum ConnectionStatus {
      CONNECTED,
      DISCONNECTED,
      NO_PERMISSION,
      UNKNOWN;
      private static final IConnectionStatusProvider.ConnectionStatus[] $VALUES = $values();
   }

   public interface IConnectionStatusObserver {
      void onConnectionStatusChanged(IConnectionStatusProvider.ConnectionStatus var1);
   }
}
