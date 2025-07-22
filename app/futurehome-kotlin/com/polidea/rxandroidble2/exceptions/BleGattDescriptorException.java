package com.polidea.rxandroidble2.exceptions;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattDescriptor;

public class BleGattDescriptorException extends BleGattException {
   public final BluetoothGattDescriptor descriptor;

   public BleGattDescriptorException(BluetoothGatt var1, BluetoothGattDescriptor var2, int var3, BleGattOperationType var4) {
      super(var1, var3, var4);
      this.descriptor = var2;
   }
}
