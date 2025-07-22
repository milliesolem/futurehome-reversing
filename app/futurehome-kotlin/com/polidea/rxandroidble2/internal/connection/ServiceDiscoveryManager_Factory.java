package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGatt;
import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.operations.OperationsProvider;
import com.polidea.rxandroidble2.internal.serialization.ConnectionOperationQueue;

public final class ServiceDiscoveryManager_Factory implements Factory<ServiceDiscoveryManager> {
   private final Provider<BluetoothGatt> bluetoothGattProvider;
   private final Provider<OperationsProvider> operationProvider;
   private final Provider<ConnectionOperationQueue> operationQueueProvider;

   public ServiceDiscoveryManager_Factory(Provider<ConnectionOperationQueue> var1, Provider<BluetoothGatt> var2, Provider<OperationsProvider> var3) {
      this.operationQueueProvider = var1;
      this.bluetoothGattProvider = var2;
      this.operationProvider = var3;
   }

   public static ServiceDiscoveryManager_Factory create(
      Provider<ConnectionOperationQueue> var0, Provider<BluetoothGatt> var1, Provider<OperationsProvider> var2
   ) {
      return new ServiceDiscoveryManager_Factory(var0, var1, var2);
   }

   public static ServiceDiscoveryManager newInstance(ConnectionOperationQueue var0, BluetoothGatt var1, OperationsProvider var2) {
      return new ServiceDiscoveryManager(var0, var1, var2);
   }

   public ServiceDiscoveryManager get() {
      return newInstance(
         (ConnectionOperationQueue)this.operationQueueProvider.get(),
         (BluetoothGatt)this.bluetoothGattProvider.get(),
         (OperationsProvider)this.operationProvider.get()
      );
   }
}
