package com.polidea.rxandroidble2.internal;

import android.bluetooth.BluetoothDevice;
import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.util.RxBleAdapterWrapper;

public final class DeviceModule_ProvideBluetoothDeviceFactory implements Factory<BluetoothDevice> {
   private final Provider<RxBleAdapterWrapper> adapterWrapperProvider;
   private final Provider<String> macAddressProvider;

   public DeviceModule_ProvideBluetoothDeviceFactory(Provider<String> var1, Provider<RxBleAdapterWrapper> var2) {
      this.macAddressProvider = var1;
      this.adapterWrapperProvider = var2;
   }

   public static DeviceModule_ProvideBluetoothDeviceFactory create(Provider<String> var0, Provider<RxBleAdapterWrapper> var1) {
      return new DeviceModule_ProvideBluetoothDeviceFactory(var0, var1);
   }

   public static BluetoothDevice provideBluetoothDevice(String var0, RxBleAdapterWrapper var1) {
      return (BluetoothDevice)Preconditions.checkNotNullFromProvides(DeviceModule.provideBluetoothDevice(var0, var1));
   }

   public BluetoothDevice get() {
      return provideBluetoothDevice((String)this.macAddressProvider.get(), (RxBleAdapterWrapper)this.adapterWrapperProvider.get());
   }
}
