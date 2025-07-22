package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGatt;
import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import bleshadow.javax.inject.Provider;

public final class ConnectionModule_ProvideBluetoothGattFactory implements Factory<BluetoothGatt> {
   private final Provider<BluetoothGattProvider> bluetoothGattProvider;

   public ConnectionModule_ProvideBluetoothGattFactory(Provider<BluetoothGattProvider> var1) {
      this.bluetoothGattProvider = var1;
   }

   public static ConnectionModule_ProvideBluetoothGattFactory create(Provider<BluetoothGattProvider> var0) {
      return new ConnectionModule_ProvideBluetoothGattFactory(var0);
   }

   public static BluetoothGatt provideBluetoothGatt(BluetoothGattProvider var0) {
      return (BluetoothGatt)Preconditions.checkNotNullFromProvides(ConnectionModule.provideBluetoothGatt(var0));
   }

   public BluetoothGatt get() {
      return provideBluetoothGatt((BluetoothGattProvider)this.bluetoothGattProvider.get());
   }
}
