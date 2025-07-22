package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.internal.connection.PayloadSizeLimitProvider;
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback;
import com.polidea.rxandroidble2.internal.logger.LoggerUtilBluetoothServices;
import io.reactivex.Scheduler;
import java.util.concurrent.TimeUnit;

public class OperationsProviderImpl implements OperationsProvider {
   private final LoggerUtilBluetoothServices bleServicesLogger;
   private final BluetoothGatt bluetoothGatt;
   private final Scheduler bluetoothInteractionScheduler;
   private final Provider<ReadRssiOperation> rssiReadOperationProvider;
   private final RxBleGattCallback rxBleGattCallback;
   private final TimeoutConfiguration timeoutConfiguration;
   private final Scheduler timeoutScheduler;

   @Inject
   OperationsProviderImpl(
      RxBleGattCallback var1,
      BluetoothGatt var2,
      LoggerUtilBluetoothServices var3,
      @Named("operation-timeout") TimeoutConfiguration var4,
      @Named("bluetooth_interaction") Scheduler var5,
      @Named("timeout") Scheduler var6,
      Provider<ReadRssiOperation> var7
   ) {
      this.rxBleGattCallback = var1;
      this.bluetoothGatt = var2;
      this.bleServicesLogger = var3;
      this.timeoutConfiguration = var4;
      this.bluetoothInteractionScheduler = var5;
      this.timeoutScheduler = var6;
      this.rssiReadOperationProvider = var7;
   }

   @Override
   public ConnectionPriorityChangeOperation provideConnectionPriorityChangeOperation(int var1, long var2, TimeUnit var4) {
      return new ConnectionPriorityChangeOperation(
         this.rxBleGattCallback, this.bluetoothGatt, this.timeoutConfiguration, var1, new TimeoutConfiguration(var2, var4, this.timeoutScheduler)
      );
   }

   @Override
   public CharacteristicLongWriteOperation provideLongWriteOperation(
      BluetoothGattCharacteristic var1,
      RxBleConnection.WriteOperationAckStrategy var2,
      RxBleConnection.WriteOperationRetryStrategy var3,
      PayloadSizeLimitProvider var4,
      byte[] var5
   ) {
      return new CharacteristicLongWriteOperation(
         this.bluetoothGatt, this.rxBleGattCallback, this.bluetoothInteractionScheduler, this.timeoutConfiguration, var1, var4, var2, var3, var5
      );
   }

   @Override
   public MtuRequestOperation provideMtuChangeOperation(int var1) {
      return new MtuRequestOperation(this.rxBleGattCallback, this.bluetoothGatt, this.timeoutConfiguration, var1);
   }

   @Override
   public CharacteristicReadOperation provideReadCharacteristic(BluetoothGattCharacteristic var1) {
      return new CharacteristicReadOperation(this.rxBleGattCallback, this.bluetoothGatt, this.timeoutConfiguration, var1);
   }

   @Override
   public DescriptorReadOperation provideReadDescriptor(BluetoothGattDescriptor var1) {
      return new DescriptorReadOperation(this.rxBleGattCallback, this.bluetoothGatt, this.timeoutConfiguration, var1);
   }

   @Override
   public ReadRssiOperation provideRssiReadOperation() {
      return (ReadRssiOperation)this.rssiReadOperationProvider.get();
   }

   @Override
   public ServiceDiscoveryOperation provideServiceDiscoveryOperation(long var1, TimeUnit var3) {
      return new ServiceDiscoveryOperation(
         this.rxBleGattCallback, this.bluetoothGatt, this.bleServicesLogger, new TimeoutConfiguration(var1, var3, this.timeoutScheduler)
      );
   }

   @Override
   public CharacteristicWriteOperation provideWriteCharacteristic(BluetoothGattCharacteristic var1, byte[] var2) {
      return new CharacteristicWriteOperation(this.rxBleGattCallback, this.bluetoothGatt, this.timeoutConfiguration, var1, var2);
   }

   @Override
   public DescriptorWriteOperation provideWriteDescriptor(BluetoothGattDescriptor var1, byte[] var2) {
      return new DescriptorWriteOperation(this.rxBleGattCallback, this.bluetoothGatt, this.timeoutConfiguration, 2, var1, var2);
   }
}
