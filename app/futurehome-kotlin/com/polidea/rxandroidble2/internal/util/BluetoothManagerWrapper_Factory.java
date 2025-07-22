package com.polidea.rxandroidble2.internal.util;

import android.bluetooth.BluetoothManager;
import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;

public final class BluetoothManagerWrapper_Factory implements Factory<BluetoothManagerWrapper> {
   private final Provider<BluetoothManager> bluetoothManagerProvider;

   public BluetoothManagerWrapper_Factory(Provider<BluetoothManager> var1) {
      this.bluetoothManagerProvider = var1;
   }

   public static BluetoothManagerWrapper_Factory create(Provider<BluetoothManager> var0) {
      return new BluetoothManagerWrapper_Factory(var0);
   }

   public static BluetoothManagerWrapper newInstance(BluetoothManager var0) {
      return new BluetoothManagerWrapper(var0);
   }

   public BluetoothManagerWrapper get() {
      return newInstance((BluetoothManager)this.bluetoothManagerProvider.get());
   }
}
