package com.polidea.rxandroidble2.exceptions;

import android.bluetooth.BluetoothGatt;

public class BleGattCannotStartException extends BleGattException {
   public BleGattCannotStartException(BluetoothGatt var1, BleGattOperationType var2) {
      super(var1, var2);
   }

   @Deprecated
   public BleGattCannotStartException(BleGattOperationType var1) {
      super(null, var1);
   }
}
