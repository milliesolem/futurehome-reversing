package com.polidea.rxandroidble2;

import android.bluetooth.BluetoothGatt;
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

public interface RxBleRadioOperationCustom<T> extends RxBleCustomOperation<T> {
   @Override
   Observable<T> asObservable(BluetoothGatt var1, RxBleGattCallback var2, Scheduler var3) throws Throwable;
}
