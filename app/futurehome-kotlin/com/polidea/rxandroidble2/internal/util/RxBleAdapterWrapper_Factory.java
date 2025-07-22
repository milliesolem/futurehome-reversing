package com.polidea.rxandroidble2.internal.util;

import android.bluetooth.BluetoothAdapter;
import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;

public final class RxBleAdapterWrapper_Factory implements Factory<RxBleAdapterWrapper> {
   private final Provider<BluetoothAdapter> bluetoothAdapterProvider;

   public RxBleAdapterWrapper_Factory(Provider<BluetoothAdapter> var1) {
      this.bluetoothAdapterProvider = var1;
   }

   public static RxBleAdapterWrapper_Factory create(Provider<BluetoothAdapter> var0) {
      return new RxBleAdapterWrapper_Factory(var0);
   }

   public static RxBleAdapterWrapper newInstance(BluetoothAdapter var0) {
      return new RxBleAdapterWrapper(var0);
   }

   public RxBleAdapterWrapper get() {
      return newInstance((BluetoothAdapter)this.bluetoothAdapterProvider.get());
   }
}
