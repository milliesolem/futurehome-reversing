package com.polidea.rxandroidble2.internal;

import android.bluetooth.BluetoothGatt;
import android.os.DeadObjectException;
import com.polidea.rxandroidble2.exceptions.BleDisconnectedException;
import com.polidea.rxandroidble2.exceptions.BleException;
import com.polidea.rxandroidble2.exceptions.BleGattCallbackTimeoutException;
import com.polidea.rxandroidble2.exceptions.BleGattCannotStartException;
import com.polidea.rxandroidble2.exceptions.BleGattOperationType;
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback;
import com.polidea.rxandroidble2.internal.logger.LoggerUtil;
import com.polidea.rxandroidble2.internal.operations.TimeoutConfiguration;
import com.polidea.rxandroidble2.internal.serialization.QueueReleaseInterface;
import com.polidea.rxandroidble2.internal.util.QueueReleasingEmitterWrapper;
import io.reactivex.ObservableEmitter;
import io.reactivex.Scheduler;
import io.reactivex.Single;

public abstract class SingleResponseOperation<T> extends QueueOperation<T> {
   private final BluetoothGatt bluetoothGatt;
   private final BleGattOperationType operationType;
   private final RxBleGattCallback rxBleGattCallback;
   private final TimeoutConfiguration timeoutConfiguration;

   public SingleResponseOperation(BluetoothGatt var1, RxBleGattCallback var2, BleGattOperationType var3, TimeoutConfiguration var4) {
      this.bluetoothGatt = var1;
      this.rxBleGattCallback = var2;
      this.operationType = var3;
      this.timeoutConfiguration = var4;
   }

   protected abstract Single<T> getCallback(RxBleGattCallback var1);

   @Override
   protected final void protectedRun(ObservableEmitter<T> var1, QueueReleaseInterface var2) {
      QueueReleasingEmitterWrapper var3 = new QueueReleasingEmitterWrapper(var1, var2);
      this.getCallback(this.rxBleGattCallback)
         .timeout(
            this.timeoutConfiguration.timeout,
            this.timeoutConfiguration.timeoutTimeUnit,
            this.timeoutConfiguration.timeoutScheduler,
            this.timeoutFallbackProcedure(this.bluetoothGatt, this.rxBleGattCallback, this.timeoutConfiguration.timeoutScheduler)
         )
         .toObservable()
         .subscribe(var3);
      if (!this.startOperation(this.bluetoothGatt)) {
         var3.cancel();
         var3.onError(new BleGattCannotStartException(this.bluetoothGatt, this.operationType));
      }
   }

   @Override
   protected BleException provideException(DeadObjectException var1) {
      return new BleDisconnectedException(var1, this.bluetoothGatt.getDevice().getAddress(), -1);
   }

   protected abstract boolean startOperation(BluetoothGatt var1);

   protected Single<T> timeoutFallbackProcedure(BluetoothGatt var1, RxBleGattCallback var2, Scheduler var3) {
      return Single.error(new BleGattCallbackTimeoutException(this.bluetoothGatt, this.operationType));
   }

   @Override
   public String toString() {
      return LoggerUtil.commonMacMessage(this.bluetoothGatt);
   }
}
