package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGatt;
import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.internal.operations.OperationsProvider;
import com.polidea.rxandroidble2.internal.serialization.ConnectionOperationQueue;
import io.reactivex.Scheduler;

public final class RxBleConnectionImpl_Factory implements Factory<RxBleConnectionImpl> {
   private final Provider<BluetoothGatt> bluetoothGattProvider;
   private final Provider<Scheduler> callbackSchedulerProvider;
   private final Provider<DescriptorWriter> descriptorWriterProvider;
   private final Provider<RxBleGattCallback> gattCallbackProvider;
   private final Provider<IllegalOperationChecker> illegalOperationCheckerProvider;
   private final Provider<RxBleConnection.LongWriteOperationBuilder> longWriteOperationBuilderProvider;
   private final Provider<MtuProvider> mtuProvider;
   private final Provider<NotificationAndIndicationManager> notificationIndicationManagerProvider;
   private final Provider<OperationsProvider> operationProvider;
   private final Provider<ConnectionOperationQueue> operationQueueProvider;
   private final Provider<ServiceDiscoveryManager> serviceDiscoveryManagerProvider;

   public RxBleConnectionImpl_Factory(
      Provider<ConnectionOperationQueue> var1,
      Provider<RxBleGattCallback> var2,
      Provider<BluetoothGatt> var3,
      Provider<ServiceDiscoveryManager> var4,
      Provider<NotificationAndIndicationManager> var5,
      Provider<MtuProvider> var6,
      Provider<DescriptorWriter> var7,
      Provider<OperationsProvider> var8,
      Provider<RxBleConnection.LongWriteOperationBuilder> var9,
      Provider<Scheduler> var10,
      Provider<IllegalOperationChecker> var11
   ) {
      this.operationQueueProvider = var1;
      this.gattCallbackProvider = var2;
      this.bluetoothGattProvider = var3;
      this.serviceDiscoveryManagerProvider = var4;
      this.notificationIndicationManagerProvider = var5;
      this.mtuProvider = var6;
      this.descriptorWriterProvider = var7;
      this.operationProvider = var8;
      this.longWriteOperationBuilderProvider = var9;
      this.callbackSchedulerProvider = var10;
      this.illegalOperationCheckerProvider = var11;
   }

   public static RxBleConnectionImpl_Factory create(
      Provider<ConnectionOperationQueue> var0,
      Provider<RxBleGattCallback> var1,
      Provider<BluetoothGatt> var2,
      Provider<ServiceDiscoveryManager> var3,
      Provider<NotificationAndIndicationManager> var4,
      Provider<MtuProvider> var5,
      Provider<DescriptorWriter> var6,
      Provider<OperationsProvider> var7,
      Provider<RxBleConnection.LongWriteOperationBuilder> var8,
      Provider<Scheduler> var9,
      Provider<IllegalOperationChecker> var10
   ) {
      return new RxBleConnectionImpl_Factory(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10);
   }

   public static RxBleConnectionImpl newInstance(
      ConnectionOperationQueue var0,
      RxBleGattCallback var1,
      BluetoothGatt var2,
      Object var3,
      Object var4,
      Object var5,
      Object var6,
      OperationsProvider var7,
      Provider<RxBleConnection.LongWriteOperationBuilder> var8,
      Scheduler var9,
      IllegalOperationChecker var10
   ) {
      return new RxBleConnectionImpl(
         var0,
         var1,
         var2,
         (ServiceDiscoveryManager)var3,
         (NotificationAndIndicationManager)var4,
         (MtuProvider)var5,
         (DescriptorWriter)var6,
         var7,
         var8,
         var9,
         var10
      );
   }

   public RxBleConnectionImpl get() {
      return newInstance(
         (ConnectionOperationQueue)this.operationQueueProvider.get(),
         (RxBleGattCallback)this.gattCallbackProvider.get(),
         (BluetoothGatt)this.bluetoothGattProvider.get(),
         this.serviceDiscoveryManagerProvider.get(),
         this.notificationIndicationManagerProvider.get(),
         this.mtuProvider.get(),
         this.descriptorWriterProvider.get(),
         (OperationsProvider)this.operationProvider.get(),
         this.longWriteOperationBuilderProvider,
         (Scheduler)this.callbackSchedulerProvider.get(),
         (IllegalOperationChecker)this.illegalOperationCheckerProvider.get()
      );
   }
}
