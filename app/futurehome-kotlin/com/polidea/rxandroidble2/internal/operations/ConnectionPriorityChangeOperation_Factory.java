package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.BluetoothGatt;
import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback;

public final class ConnectionPriorityChangeOperation_Factory implements Factory<ConnectionPriorityChangeOperation> {
   private final Provider<BluetoothGatt> bluetoothGattProvider;
   private final Provider<Integer> connectionPriorityProvider;
   private final Provider<RxBleGattCallback> rxBleGattCallbackProvider;
   private final Provider<TimeoutConfiguration> successTimeoutConfigurationProvider;
   private final Provider<TimeoutConfiguration> timeoutConfigurationProvider;

   public ConnectionPriorityChangeOperation_Factory(
      Provider<RxBleGattCallback> var1,
      Provider<BluetoothGatt> var2,
      Provider<TimeoutConfiguration> var3,
      Provider<Integer> var4,
      Provider<TimeoutConfiguration> var5
   ) {
      this.rxBleGattCallbackProvider = var1;
      this.bluetoothGattProvider = var2;
      this.timeoutConfigurationProvider = var3;
      this.connectionPriorityProvider = var4;
      this.successTimeoutConfigurationProvider = var5;
   }

   public static ConnectionPriorityChangeOperation_Factory create(
      Provider<RxBleGattCallback> var0,
      Provider<BluetoothGatt> var1,
      Provider<TimeoutConfiguration> var2,
      Provider<Integer> var3,
      Provider<TimeoutConfiguration> var4
   ) {
      return new ConnectionPriorityChangeOperation_Factory(var0, var1, var2, var3, var4);
   }

   public static ConnectionPriorityChangeOperation newInstance(
      RxBleGattCallback var0, BluetoothGatt var1, TimeoutConfiguration var2, int var3, TimeoutConfiguration var4
   ) {
      return new ConnectionPriorityChangeOperation(var0, var1, var2, var3, var4);
   }

   public ConnectionPriorityChangeOperation get() {
      return newInstance(
         (RxBleGattCallback)this.rxBleGattCallbackProvider.get(),
         (BluetoothGatt)this.bluetoothGattProvider.get(),
         (TimeoutConfiguration)this.timeoutConfigurationProvider.get(),
         (Integer)this.connectionPriorityProvider.get(),
         (TimeoutConfiguration)this.successTimeoutConfigurationProvider.get()
      );
   }
}
