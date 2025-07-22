package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGattCharacteristic;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.internal.BleIllegalOperationException;
import com.polidea.rxandroidble2.internal.RxBleLog;

public class LoggingIllegalOperationHandler extends IllegalOperationHandler {
   @Inject
   public LoggingIllegalOperationHandler(IllegalOperationMessageCreator var1) {
      super(var1);
   }

   @Override
   public BleIllegalOperationException handleMismatchData(BluetoothGattCharacteristic var1, int var2) {
      RxBleLog.w(this.messageCreator.createMismatchMessage(var1, var2));
      return null;
   }
}
