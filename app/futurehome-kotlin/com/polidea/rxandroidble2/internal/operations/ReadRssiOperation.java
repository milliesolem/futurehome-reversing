package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.BluetoothGatt;
import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble2.exceptions.BleGattOperationType;
import com.polidea.rxandroidble2.internal.SingleResponseOperation;
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback;
import io.reactivex.Single;

public class ReadRssiOperation extends SingleResponseOperation<Integer> {
   @Inject
   ReadRssiOperation(RxBleGattCallback var1, BluetoothGatt var2, @Named("operation-timeout") TimeoutConfiguration var3) {
      super(var2, var1, BleGattOperationType.READ_RSSI, var3);
   }

   @Override
   protected Single<Integer> getCallback(RxBleGattCallback var1) {
      return var1.getOnRssiRead().firstOrError();
   }

   @Override
   protected boolean startOperation(BluetoothGatt var1) {
      return var1.readRemoteRssi();
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ReadRssiOperation{");
      var1.append(super.toString());
      var1.append('}');
      return var1.toString();
   }
}
