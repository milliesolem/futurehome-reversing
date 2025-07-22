package com.polidea.rxandroidble2.internal;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.polidea.rxandroidble2.RxBleConnection;

public final class DeviceModule_ProvideConnectionStateRelayFactory implements Factory<BehaviorRelay<RxBleConnection.RxBleConnectionState>> {
   public static DeviceModule_ProvideConnectionStateRelayFactory create() {
      return DeviceModule_ProvideConnectionStateRelayFactory.InstanceHolder.INSTANCE;
   }

   public static BehaviorRelay<RxBleConnection.RxBleConnectionState> provideConnectionStateRelay() {
      return (BehaviorRelay<RxBleConnection.RxBleConnectionState>)Preconditions.checkNotNullFromProvides(DeviceModule.provideConnectionStateRelay());
   }

   public BehaviorRelay<RxBleConnection.RxBleConnectionState> get() {
      return provideConnectionStateRelay();
   }

   private static final class InstanceHolder {
      private static final DeviceModule_ProvideConnectionStateRelayFactory INSTANCE = new DeviceModule_ProvideConnectionStateRelayFactory();
   }
}
