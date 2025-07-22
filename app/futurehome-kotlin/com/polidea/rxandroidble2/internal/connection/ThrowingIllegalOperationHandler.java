package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGattCharacteristic;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.internal.BleIllegalOperationException;

public class ThrowingIllegalOperationHandler extends IllegalOperationHandler {
   @Inject
   public ThrowingIllegalOperationHandler(IllegalOperationMessageCreator var1) {
      super(var1);
   }

   @Override
   public BleIllegalOperationException handleMismatchData(BluetoothGattCharacteristic var1, int var2) {
      return new BleIllegalOperationException(this.messageCreator.createMismatchMessage(var1, var2), var1.getUuid(), var1.getProperties(), var2);
   }
}
