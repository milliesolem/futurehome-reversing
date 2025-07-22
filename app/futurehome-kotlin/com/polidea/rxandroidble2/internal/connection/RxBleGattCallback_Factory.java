package com.polidea.rxandroidble2.internal.connection;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import io.reactivex.Scheduler;

public final class RxBleGattCallback_Factory implements Factory<RxBleGattCallback> {
   private final Provider<BluetoothGattProvider> bluetoothGattProvider;
   private final Provider<Scheduler> callbackSchedulerProvider;
   private final Provider<DisconnectionRouter> disconnectionRouterProvider;
   private final Provider<NativeCallbackDispatcher> nativeCallbackDispatcherProvider;

   public RxBleGattCallback_Factory(
      Provider<Scheduler> var1, Provider<BluetoothGattProvider> var2, Provider<DisconnectionRouter> var3, Provider<NativeCallbackDispatcher> var4
   ) {
      this.callbackSchedulerProvider = var1;
      this.bluetoothGattProvider = var2;
      this.disconnectionRouterProvider = var3;
      this.nativeCallbackDispatcherProvider = var4;
   }

   public static RxBleGattCallback_Factory create(
      Provider<Scheduler> var0, Provider<BluetoothGattProvider> var1, Provider<DisconnectionRouter> var2, Provider<NativeCallbackDispatcher> var3
   ) {
      return new RxBleGattCallback_Factory(var0, var1, var2, var3);
   }

   public static RxBleGattCallback newInstance(Scheduler var0, BluetoothGattProvider var1, Object var2, Object var3) {
      return new RxBleGattCallback(var0, var1, (DisconnectionRouter)var2, (NativeCallbackDispatcher)var3);
   }

   public RxBleGattCallback get() {
      return newInstance(
         (Scheduler)this.callbackSchedulerProvider.get(),
         (BluetoothGattProvider)this.bluetoothGattProvider.get(),
         this.disconnectionRouterProvider.get(),
         this.nativeCallbackDispatcherProvider.get()
      );
   }
}
