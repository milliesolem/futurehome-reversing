package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.BluetoothGatt;
import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback;

public final class MtuRequestOperation_Factory implements Factory<MtuRequestOperation> {
   private final Provider<BluetoothGatt> bluetoothGattProvider;
   private final Provider<Integer> requestedMtuProvider;
   private final Provider<RxBleGattCallback> rxBleGattCallbackProvider;
   private final Provider<TimeoutConfiguration> timeoutConfigurationProvider;

   public MtuRequestOperation_Factory(
      Provider<RxBleGattCallback> var1, Provider<BluetoothGatt> var2, Provider<TimeoutConfiguration> var3, Provider<Integer> var4
   ) {
      this.rxBleGattCallbackProvider = var1;
      this.bluetoothGattProvider = var2;
      this.timeoutConfigurationProvider = var3;
      this.requestedMtuProvider = var4;
   }

   public static MtuRequestOperation_Factory create(
      Provider<RxBleGattCallback> var0, Provider<BluetoothGatt> var1, Provider<TimeoutConfiguration> var2, Provider<Integer> var3
   ) {
      return new MtuRequestOperation_Factory(var0, var1, var2, var3);
   }

   public static MtuRequestOperation newInstance(RxBleGattCallback var0, BluetoothGatt var1, TimeoutConfiguration var2, int var3) {
      return new MtuRequestOperation(var0, var1, var2, var3);
   }

   public MtuRequestOperation get() {
      return newInstance(
         (RxBleGattCallback)this.rxBleGattCallbackProvider.get(),
         (BluetoothGatt)this.bluetoothGattProvider.get(),
         (TimeoutConfiguration)this.timeoutConfigurationProvider.get(),
         (Integer)this.requestedMtuProvider.get()
      );
   }
}
