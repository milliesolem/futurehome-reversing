package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.BluetoothGatt;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.exceptions.BleGattCannotStartException;
import com.polidea.rxandroidble2.exceptions.BleGattOperationType;
import com.polidea.rxandroidble2.internal.SingleResponseOperation;
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback;
import io.reactivex.Single;

public class ConnectionPriorityChangeOperation extends SingleResponseOperation<Long> {
   private final int connectionPriority;
   private final TimeoutConfiguration successTimeoutConfiguration;

   @Inject
   ConnectionPriorityChangeOperation(RxBleGattCallback var1, BluetoothGatt var2, TimeoutConfiguration var3, int var4, TimeoutConfiguration var5) {
      super(var2, var1, BleGattOperationType.CONNECTION_PRIORITY_CHANGE, var3);
      this.connectionPriority = var4;
      this.successTimeoutConfiguration = var5;
   }

   private static String connectionPriorityToString(int var0) {
      if (var0 != 0) {
         return var0 != 2 ? "CONNECTION_PRIORITY_HIGH" : "CONNECTION_PRIORITY_LOW_POWER";
      } else {
         return "CONNECTION_PRIORITY_BALANCED";
      }
   }

   @Override
   protected Single<Long> getCallback(RxBleGattCallback var1) {
      return Single.timer(
         this.successTimeoutConfiguration.timeout, this.successTimeoutConfiguration.timeoutTimeUnit, this.successTimeoutConfiguration.timeoutScheduler
      );
   }

   @Override
   protected boolean startOperation(BluetoothGatt var1) throws IllegalArgumentException, BleGattCannotStartException {
      return var1.requestConnectionPriority(this.connectionPriority);
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ConnectionPriorityChangeOperation{");
      var1.append(super.toString());
      var1.append(", connectionPriority=");
      var1.append(connectionPriorityToString(this.connectionPriority));
      var1.append(", successTimeout=");
      var1.append(this.successTimeoutConfiguration);
      var1.append('}');
      return var1.toString();
   }
}
