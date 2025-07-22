package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.BluetoothGatt;
import com.polidea.rxandroidble2.RxBleDeviceServices;
import com.polidea.rxandroidble2.exceptions.BleGattOperationType;
import com.polidea.rxandroidble2.internal.SingleResponseOperation;
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback;
import com.polidea.rxandroidble2.internal.logger.LoggerUtilBluetoothServices;
import io.reactivex.Scheduler;
import io.reactivex.Single;

public class ServiceDiscoveryOperation extends SingleResponseOperation<RxBleDeviceServices> {
   final LoggerUtilBluetoothServices bleServicesLogger;
   final BluetoothGatt bluetoothGatt;

   ServiceDiscoveryOperation(RxBleGattCallback var1, BluetoothGatt var2, LoggerUtilBluetoothServices var3, TimeoutConfiguration var4) {
      super(var2, var1, BleGattOperationType.SERVICE_DISCOVERY, var4);
      this.bluetoothGatt = var2;
      this.bleServicesLogger = var3;
   }

   @Override
   protected Single<RxBleDeviceServices> getCallback(RxBleGattCallback var1) {
      return var1.getOnServicesDiscovered().firstOrError().doOnSuccess(new ServiceDiscoveryOperation$$ExternalSyntheticLambda1(this));
   }

   @Override
   protected boolean startOperation(BluetoothGatt var1) {
      return var1.discoverServices();
   }

   @Override
   protected Single<RxBleDeviceServices> timeoutFallbackProcedure(BluetoothGatt var1, RxBleGattCallback var2, Scheduler var3) {
      return Single.defer(new ServiceDiscoveryOperation$$ExternalSyntheticLambda3(var1, var3));
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ServiceDiscoveryOperation{");
      var1.append(super.toString());
      var1.append('}');
      return var1.toString();
   }
}
