package com.polidea.rxandroidble2.internal;

import android.bluetooth.BluetoothDevice;
import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.internal.connection.Connector;
import com.polidea.rxandroidble2.internal.util.CheckerConnectPermission;

public final class RxBleDeviceImpl_Factory implements Factory<RxBleDeviceImpl> {
   private final Provider<BluetoothDevice> bluetoothDeviceProvider;
   private final Provider<CheckerConnectPermission> checkerConnectPermissionProvider;
   private final Provider<BehaviorRelay<RxBleConnection.RxBleConnectionState>> connectionStateRelayProvider;
   private final Provider<Connector> connectorProvider;

   public RxBleDeviceImpl_Factory(
      Provider<BluetoothDevice> var1,
      Provider<Connector> var2,
      Provider<BehaviorRelay<RxBleConnection.RxBleConnectionState>> var3,
      Provider<CheckerConnectPermission> var4
   ) {
      this.bluetoothDeviceProvider = var1;
      this.connectorProvider = var2;
      this.connectionStateRelayProvider = var3;
      this.checkerConnectPermissionProvider = var4;
   }

   public static RxBleDeviceImpl_Factory create(
      Provider<BluetoothDevice> var0,
      Provider<Connector> var1,
      Provider<BehaviorRelay<RxBleConnection.RxBleConnectionState>> var2,
      Provider<CheckerConnectPermission> var3
   ) {
      return new RxBleDeviceImpl_Factory(var0, var1, var2, var3);
   }

   public static RxBleDeviceImpl newInstance(
      BluetoothDevice var0, Connector var1, BehaviorRelay<RxBleConnection.RxBleConnectionState> var2, CheckerConnectPermission var3
   ) {
      return new RxBleDeviceImpl(var0, var1, var2, var3);
   }

   public RxBleDeviceImpl get() {
      return newInstance(
         (BluetoothDevice)this.bluetoothDeviceProvider.get(),
         (Connector)this.connectorProvider.get(),
         (BehaviorRelay<RxBleConnection.RxBleConnectionState>)this.connectionStateRelayProvider.get(),
         (CheckerConnectPermission)this.checkerConnectPermissionProvider.get()
      );
   }
}
