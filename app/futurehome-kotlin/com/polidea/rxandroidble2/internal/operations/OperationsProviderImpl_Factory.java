package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.BluetoothGatt;
import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback;
import com.polidea.rxandroidble2.internal.logger.LoggerUtilBluetoothServices;
import io.reactivex.Scheduler;

public final class OperationsProviderImpl_Factory implements Factory<OperationsProviderImpl> {
   private final Provider<LoggerUtilBluetoothServices> bleServicesLoggerProvider;
   private final Provider<BluetoothGatt> bluetoothGattProvider;
   private final Provider<Scheduler> bluetoothInteractionSchedulerProvider;
   private final Provider<ReadRssiOperation> rssiReadOperationProvider;
   private final Provider<RxBleGattCallback> rxBleGattCallbackProvider;
   private final Provider<TimeoutConfiguration> timeoutConfigurationProvider;
   private final Provider<Scheduler> timeoutSchedulerProvider;

   public OperationsProviderImpl_Factory(
      Provider<RxBleGattCallback> var1,
      Provider<BluetoothGatt> var2,
      Provider<LoggerUtilBluetoothServices> var3,
      Provider<TimeoutConfiguration> var4,
      Provider<Scheduler> var5,
      Provider<Scheduler> var6,
      Provider<ReadRssiOperation> var7
   ) {
      this.rxBleGattCallbackProvider = var1;
      this.bluetoothGattProvider = var2;
      this.bleServicesLoggerProvider = var3;
      this.timeoutConfigurationProvider = var4;
      this.bluetoothInteractionSchedulerProvider = var5;
      this.timeoutSchedulerProvider = var6;
      this.rssiReadOperationProvider = var7;
   }

   public static OperationsProviderImpl_Factory create(
      Provider<RxBleGattCallback> var0,
      Provider<BluetoothGatt> var1,
      Provider<LoggerUtilBluetoothServices> var2,
      Provider<TimeoutConfiguration> var3,
      Provider<Scheduler> var4,
      Provider<Scheduler> var5,
      Provider<ReadRssiOperation> var6
   ) {
      return new OperationsProviderImpl_Factory(var0, var1, var2, var3, var4, var5, var6);
   }

   public static OperationsProviderImpl newInstance(
      RxBleGattCallback var0,
      BluetoothGatt var1,
      LoggerUtilBluetoothServices var2,
      TimeoutConfiguration var3,
      Scheduler var4,
      Scheduler var5,
      Provider<ReadRssiOperation> var6
   ) {
      return new OperationsProviderImpl(var0, var1, var2, var3, var4, var5, var6);
   }

   public OperationsProviderImpl get() {
      return newInstance(
         (RxBleGattCallback)this.rxBleGattCallbackProvider.get(),
         (BluetoothGatt)this.bluetoothGattProvider.get(),
         (LoggerUtilBluetoothServices)this.bleServicesLoggerProvider.get(),
         (TimeoutConfiguration)this.timeoutConfigurationProvider.get(),
         (Scheduler)this.bluetoothInteractionSchedulerProvider.get(),
         (Scheduler)this.timeoutSchedulerProvider.get(),
         this.rssiReadOperationProvider
      );
   }
}
