package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGattCharacteristic;
import com.polidea.rxandroidble2.internal.BleIllegalOperationException;

public abstract class IllegalOperationHandler {
   protected final IllegalOperationMessageCreator messageCreator;

   IllegalOperationHandler(IllegalOperationMessageCreator var1) {
      this.messageCreator = var1;
   }

   public abstract BleIllegalOperationException handleMismatchData(BluetoothGattCharacteristic var1, int var2);
}
