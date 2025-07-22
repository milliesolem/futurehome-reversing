package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattDescriptor;
import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback;

public final class DescriptorReadOperation_Factory implements Factory<DescriptorReadOperation> {
   private final Provider<BluetoothGatt> bluetoothGattProvider;
   private final Provider<BluetoothGattDescriptor> descriptorProvider;
   private final Provider<RxBleGattCallback> rxBleGattCallbackProvider;
   private final Provider<TimeoutConfiguration> timeoutConfigurationProvider;

   public DescriptorReadOperation_Factory(
      Provider<RxBleGattCallback> var1, Provider<BluetoothGatt> var2, Provider<TimeoutConfiguration> var3, Provider<BluetoothGattDescriptor> var4
   ) {
      this.rxBleGattCallbackProvider = var1;
      this.bluetoothGattProvider = var2;
      this.timeoutConfigurationProvider = var3;
      this.descriptorProvider = var4;
   }

   public static DescriptorReadOperation_Factory create(
      Provider<RxBleGattCallback> var0, Provider<BluetoothGatt> var1, Provider<TimeoutConfiguration> var2, Provider<BluetoothGattDescriptor> var3
   ) {
      return new DescriptorReadOperation_Factory(var0, var1, var2, var3);
   }

   public static DescriptorReadOperation newInstance(RxBleGattCallback var0, BluetoothGatt var1, TimeoutConfiguration var2, BluetoothGattDescriptor var3) {
      return new DescriptorReadOperation(var0, var1, var2, var3);
   }

   public DescriptorReadOperation get() {
      return newInstance(
         (RxBleGattCallback)this.rxBleGattCallbackProvider.get(),
         (BluetoothGatt)this.bluetoothGattProvider.get(),
         (TimeoutConfiguration)this.timeoutConfigurationProvider.get(),
         (BluetoothGattDescriptor)this.descriptorProvider.get()
      );
   }
}
