package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGatt;
import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;

public final class NotificationAndIndicationManager_Factory implements Factory<NotificationAndIndicationManager> {
   private final Provider<BluetoothGatt> bluetoothGattProvider;
   private final Provider<byte[]> configDisableProvider;
   private final Provider<byte[]> configEnableIndicationProvider;
   private final Provider<byte[]> configEnableNotificationProvider;
   private final Provider<DescriptorWriter> descriptorWriterProvider;
   private final Provider<RxBleGattCallback> gattCallbackProvider;

   public NotificationAndIndicationManager_Factory(
      Provider<byte[]> var1,
      Provider<byte[]> var2,
      Provider<byte[]> var3,
      Provider<BluetoothGatt> var4,
      Provider<RxBleGattCallback> var5,
      Provider<DescriptorWriter> var6
   ) {
      this.configEnableNotificationProvider = var1;
      this.configEnableIndicationProvider = var2;
      this.configDisableProvider = var3;
      this.bluetoothGattProvider = var4;
      this.gattCallbackProvider = var5;
      this.descriptorWriterProvider = var6;
   }

   public static NotificationAndIndicationManager_Factory create(
      Provider<byte[]> var0,
      Provider<byte[]> var1,
      Provider<byte[]> var2,
      Provider<BluetoothGatt> var3,
      Provider<RxBleGattCallback> var4,
      Provider<DescriptorWriter> var5
   ) {
      return new NotificationAndIndicationManager_Factory(var0, var1, var2, var3, var4, var5);
   }

   public static NotificationAndIndicationManager newInstance(byte[] var0, byte[] var1, byte[] var2, BluetoothGatt var3, RxBleGattCallback var4, Object var5) {
      return new NotificationAndIndicationManager(var0, var1, var2, var3, var4, (DescriptorWriter)var5);
   }

   public NotificationAndIndicationManager get() {
      return newInstance(
         (byte[])this.configEnableNotificationProvider.get(),
         (byte[])this.configEnableIndicationProvider.get(),
         (byte[])this.configDisableProvider.get(),
         (BluetoothGatt)this.bluetoothGattProvider.get(),
         (RxBleGattCallback)this.gattCallbackProvider.get(),
         this.descriptorWriterProvider.get()
      );
   }
}
