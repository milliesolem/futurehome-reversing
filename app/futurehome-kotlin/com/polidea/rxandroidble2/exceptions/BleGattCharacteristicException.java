package com.polidea.rxandroidble2.exceptions;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;

public class BleGattCharacteristicException extends BleGattException {
   public final BluetoothGattCharacteristic characteristic;

   public BleGattCharacteristicException(BluetoothGatt var1, BluetoothGattCharacteristic var2, int var3, BleGattOperationType var4) {
      super(var1, var3, var4);
      this.characteristic = var2;
   }
}
