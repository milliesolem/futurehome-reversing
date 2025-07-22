package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.BluetoothManager;
import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.connection.BluetoothGattProvider;
import com.polidea.rxandroidble2.internal.connection.ConnectionStateChangeListener;
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback;
import io.reactivex.Scheduler;

public final class DisconnectOperation_Factory implements Factory<DisconnectOperation> {
   private final Provider<BluetoothGattProvider> bluetoothGattProvider;
   private final Provider<Scheduler> bluetoothInteractionSchedulerProvider;
   private final Provider<BluetoothManager> bluetoothManagerProvider;
   private final Provider<ConnectionStateChangeListener> connectionStateChangeListenerProvider;
   private final Provider<String> macAddressProvider;
   private final Provider<RxBleGattCallback> rxBleGattCallbackProvider;
   private final Provider<TimeoutConfiguration> timeoutConfigurationProvider;

   public DisconnectOperation_Factory(
      Provider<RxBleGattCallback> var1,
      Provider<BluetoothGattProvider> var2,
      Provider<String> var3,
      Provider<BluetoothManager> var4,
      Provider<Scheduler> var5,
      Provider<TimeoutConfiguration> var6,
      Provider<ConnectionStateChangeListener> var7
   ) {
      this.rxBleGattCallbackProvider = var1;
      this.bluetoothGattProvider = var2;
      this.macAddressProvider = var3;
      this.bluetoothManagerProvider = var4;
      this.bluetoothInteractionSchedulerProvider = var5;
      this.timeoutConfigurationProvider = var6;
      this.connectionStateChangeListenerProvider = var7;
   }

   public static DisconnectOperation_Factory create(
      Provider<RxBleGattCallback> var0,
      Provider<BluetoothGattProvider> var1,
      Provider<String> var2,
      Provider<BluetoothManager> var3,
      Provider<Scheduler> var4,
      Provider<TimeoutConfiguration> var5,
      Provider<ConnectionStateChangeListener> var6
   ) {
      return new DisconnectOperation_Factory(var0, var1, var2, var3, var4, var5, var6);
   }

   public static DisconnectOperation newInstance(
      RxBleGattCallback var0,
      BluetoothGattProvider var1,
      String var2,
      BluetoothManager var3,
      Scheduler var4,
      TimeoutConfiguration var5,
      ConnectionStateChangeListener var6
   ) {
      return new DisconnectOperation(var0, var1, var2, var3, var4, var5, var6);
   }

   public DisconnectOperation get() {
      return newInstance(
         (RxBleGattCallback)this.rxBleGattCallbackProvider.get(),
         (BluetoothGattProvider)this.bluetoothGattProvider.get(),
         (String)this.macAddressProvider.get(),
         (BluetoothManager)this.bluetoothManagerProvider.get(),
         (Scheduler)this.bluetoothInteractionSchedulerProvider.get(),
         (TimeoutConfiguration)this.timeoutConfigurationProvider.get(),
         (ConnectionStateChangeListener)this.connectionStateChangeListenerProvider.get()
      );
   }
}
