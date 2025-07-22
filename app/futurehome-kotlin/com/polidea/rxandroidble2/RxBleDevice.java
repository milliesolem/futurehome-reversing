package com.polidea.rxandroidble2;

import android.bluetooth.BluetoothDevice;
import io.reactivex.Observable;

public interface RxBleDevice {
   Observable<RxBleConnection> establishConnection(boolean var1);

   Observable<RxBleConnection> establishConnection(boolean var1, Timeout var2);

   BluetoothDevice getBluetoothDevice();

   RxBleConnection.RxBleConnectionState getConnectionState();

   String getMacAddress();

   String getName();

   Observable<RxBleConnection.RxBleConnectionState> observeConnectionStateChanges();
}
