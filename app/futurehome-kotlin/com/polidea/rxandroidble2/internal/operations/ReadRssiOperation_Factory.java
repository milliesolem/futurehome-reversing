package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.BluetoothGatt;
import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback;

public final class ReadRssiOperation_Factory implements Factory<ReadRssiOperation> {
   private final Provider<RxBleGattCallback> bleGattCallbackProvider;
   private final Provider<BluetoothGatt> bluetoothGattProvider;
   private final Provider<TimeoutConfiguration> timeoutConfigurationProvider;

   public ReadRssiOperation_Factory(Provider<RxBleGattCallback> var1, Provider<BluetoothGatt> var2, Provider<TimeoutConfiguration> var3) {
      this.bleGattCallbackProvider = var1;
      this.bluetoothGattProvider = var2;
      this.timeoutConfigurationProvider = var3;
   }

   public static ReadRssiOperation_Factory create(Provider<RxBleGattCallback> var0, Provider<BluetoothGatt> var1, Provider<TimeoutConfiguration> var2) {
      return new ReadRssiOperation_Factory(var0, var1, var2);
   }

   public static ReadRssiOperation newInstance(RxBleGattCallback var0, BluetoothGatt var1, TimeoutConfiguration var2) {
      return new ReadRssiOperation(var0, var1, var2);
   }

   public ReadRssiOperation get() {
      return newInstance(
         (RxBleGattCallback)this.bleGattCallbackProvider.get(),
         (BluetoothGatt)this.bluetoothGattProvider.get(),
         (TimeoutConfiguration)this.timeoutConfigurationProvider.get()
      );
   }
}
