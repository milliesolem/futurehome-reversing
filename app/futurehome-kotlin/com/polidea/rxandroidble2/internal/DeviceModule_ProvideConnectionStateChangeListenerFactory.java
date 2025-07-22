package com.polidea.rxandroidble2.internal;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import bleshadow.javax.inject.Provider;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.internal.connection.ConnectionStateChangeListener;

public final class DeviceModule_ProvideConnectionStateChangeListenerFactory implements Factory<ConnectionStateChangeListener> {
   private final Provider<BehaviorRelay<RxBleConnection.RxBleConnectionState>> connectionStateBehaviorRelayProvider;

   public DeviceModule_ProvideConnectionStateChangeListenerFactory(Provider<BehaviorRelay<RxBleConnection.RxBleConnectionState>> var1) {
      this.connectionStateBehaviorRelayProvider = var1;
   }

   public static DeviceModule_ProvideConnectionStateChangeListenerFactory create(Provider<BehaviorRelay<RxBleConnection.RxBleConnectionState>> var0) {
      return new DeviceModule_ProvideConnectionStateChangeListenerFactory(var0);
   }

   public static ConnectionStateChangeListener provideConnectionStateChangeListener(BehaviorRelay<RxBleConnection.RxBleConnectionState> var0) {
      return (ConnectionStateChangeListener)Preconditions.checkNotNullFromProvides(DeviceModule.provideConnectionStateChangeListener(var0));
   }

   public ConnectionStateChangeListener get() {
      return provideConnectionStateChangeListener((BehaviorRelay<RxBleConnection.RxBleConnectionState>)this.connectionStateBehaviorRelayProvider.get());
   }
}
