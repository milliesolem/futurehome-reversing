package com.polidea.rxandroidble2.internal;

import android.bluetooth.BluetoothDevice;
import bleshadow.javax.inject.Inject;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.polidea.rxandroidble2.ConnectionSetup;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.RxBleDevice;
import com.polidea.rxandroidble2.Timeout;
import com.polidea.rxandroidble2.internal.connection.Connector;
import com.polidea.rxandroidble2.internal.logger.LoggerUtil;
import com.polidea.rxandroidble2.internal.util.CheckerConnectPermission;
import io.reactivex.Observable;
import java.util.concurrent.atomic.AtomicBoolean;

@DeviceScope
class RxBleDeviceImpl implements RxBleDevice {
   final BluetoothDevice bluetoothDevice;
   private final CheckerConnectPermission checkerConnectPermission;
   private final BehaviorRelay<RxBleConnection.RxBleConnectionState> connectionStateRelay;
   final Connector connector;
   final AtomicBoolean isConnected = new AtomicBoolean(false);

   @Inject
   RxBleDeviceImpl(BluetoothDevice var1, Connector var2, BehaviorRelay<RxBleConnection.RxBleConnectionState> var3, CheckerConnectPermission var4) {
      this.bluetoothDevice = var1;
      this.connector = var2;
      this.connectionStateRelay = var3;
      this.checkerConnectPermission = var4;
   }

   private String getName(boolean var1) {
      return var1 && !this.checkerConnectPermission.isConnectRuntimePermissionGranted() ? "[NO BLUETOOTH_CONNECT PERMISSION]" : this.bluetoothDevice.getName();
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof RxBleDeviceImpl)) {
         return false;
      } else {
         var1 = var1;
         return this.bluetoothDevice.equals(var1.bluetoothDevice);
      }
   }

   public Observable<RxBleConnection> establishConnection(ConnectionSetup var1) {
      return Observable.defer(new RxBleDeviceImpl$$ExternalSyntheticLambda1(this, var1));
   }

   @Override
   public Observable<RxBleConnection> establishConnection(boolean var1) {
      return this.establishConnection(new ConnectionSetup.Builder().setAutoConnect(var1).setSuppressIllegalOperationCheck(true).build());
   }

   @Override
   public Observable<RxBleConnection> establishConnection(boolean var1, Timeout var2) {
      return this.establishConnection(
         new ConnectionSetup.Builder().setAutoConnect(var1).setOperationTimeout(var2).setSuppressIllegalOperationCheck(true).build()
      );
   }

   @Override
   public BluetoothDevice getBluetoothDevice() {
      return this.bluetoothDevice;
   }

   @Override
   public RxBleConnection.RxBleConnectionState getConnectionState() {
      return this.connectionStateRelay.getValue();
   }

   @Override
   public String getMacAddress() {
      return this.bluetoothDevice.getAddress();
   }

   @Override
   public String getName() {
      return this.getName(false);
   }

   @Override
   public int hashCode() {
      return this.bluetoothDevice.hashCode();
   }

   @Override
   public Observable<RxBleConnection.RxBleConnectionState> observeConnectionStateChanges() {
      return this.connectionStateRelay.distinctUntilChanged().skip(1L);
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("RxBleDeviceImpl{");
      var1.append(LoggerUtil.commonMacMessage(this.bluetoothDevice.getAddress()));
      var1.append(", name=");
      var1.append(this.getName(true));
      var1.append('}');
      return var1.toString();
   }
}
