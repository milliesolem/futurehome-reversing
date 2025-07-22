package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.BluetoothDevice;
import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.connection.BluetoothGattProvider;
import com.polidea.rxandroidble2.internal.connection.ConnectionStateChangeListener;
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback;
import com.polidea.rxandroidble2.internal.util.BleConnectionCompat;

public final class ConnectOperation_Factory implements Factory<ConnectOperation> {
   private final Provider<Boolean> autoConnectProvider;
   private final Provider<BluetoothDevice> bluetoothDeviceProvider;
   private final Provider<BluetoothGattProvider> bluetoothGattProvider;
   private final Provider<TimeoutConfiguration> connectTimeoutProvider;
   private final Provider<BleConnectionCompat> connectionCompatProvider;
   private final Provider<ConnectionStateChangeListener> connectionStateChangedActionProvider;
   private final Provider<RxBleGattCallback> rxBleGattCallbackProvider;

   public ConnectOperation_Factory(
      Provider<BluetoothDevice> var1,
      Provider<BleConnectionCompat> var2,
      Provider<RxBleGattCallback> var3,
      Provider<BluetoothGattProvider> var4,
      Provider<TimeoutConfiguration> var5,
      Provider<Boolean> var6,
      Provider<ConnectionStateChangeListener> var7
   ) {
      this.bluetoothDeviceProvider = var1;
      this.connectionCompatProvider = var2;
      this.rxBleGattCallbackProvider = var3;
      this.bluetoothGattProvider = var4;
      this.connectTimeoutProvider = var5;
      this.autoConnectProvider = var6;
      this.connectionStateChangedActionProvider = var7;
   }

   public static ConnectOperation_Factory create(
      Provider<BluetoothDevice> var0,
      Provider<BleConnectionCompat> var1,
      Provider<RxBleGattCallback> var2,
      Provider<BluetoothGattProvider> var3,
      Provider<TimeoutConfiguration> var4,
      Provider<Boolean> var5,
      Provider<ConnectionStateChangeListener> var6
   ) {
      return new ConnectOperation_Factory(var0, var1, var2, var3, var4, var5, var6);
   }

   public static ConnectOperation newInstance(
      BluetoothDevice var0,
      BleConnectionCompat var1,
      RxBleGattCallback var2,
      BluetoothGattProvider var3,
      TimeoutConfiguration var4,
      boolean var5,
      ConnectionStateChangeListener var6
   ) {
      return new ConnectOperation(var0, var1, var2, var3, var4, var5, var6);
   }

   public ConnectOperation get() {
      return newInstance(
         (BluetoothDevice)this.bluetoothDeviceProvider.get(),
         (BleConnectionCompat)this.connectionCompatProvider.get(),
         (RxBleGattCallback)this.rxBleGattCallbackProvider.get(),
         (BluetoothGattProvider)this.bluetoothGattProvider.get(),
         (TimeoutConfiguration)this.connectTimeoutProvider.get(),
         (Boolean)this.autoConnectProvider.get(),
         (ConnectionStateChangeListener)this.connectionStateChangedActionProvider.get()
      );
   }
}
