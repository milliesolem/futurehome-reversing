package com.polidea.rxandroidble2.exceptions;

import android.bluetooth.BluetoothGatt;

public class BleGattCallbackTimeoutException extends BleGattException {
   public BleGattCallbackTimeoutException(BluetoothGatt var1, BleGattOperationType var2) {
      super(var1, var2);
   }
}
