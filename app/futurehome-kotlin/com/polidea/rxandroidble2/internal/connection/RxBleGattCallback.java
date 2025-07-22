package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import com.polidea.rxandroidble2.ConnectionParameters;
import com.polidea.rxandroidble2.HiddenBluetoothGattCallback;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.RxBleDeviceServices;
import com.polidea.rxandroidble2.exceptions.BleDisconnectedException;
import com.polidea.rxandroidble2.exceptions.BleGattCharacteristicException;
import com.polidea.rxandroidble2.exceptions.BleGattDescriptorException;
import com.polidea.rxandroidble2.exceptions.BleGattException;
import com.polidea.rxandroidble2.exceptions.BleGattOperationType;
import com.polidea.rxandroidble2.internal.logger.LoggerUtil;
import com.polidea.rxandroidble2.internal.util.ByteAssociation;
import com.polidea.rxandroidble2.internal.util.CharacteristicChangedEvent;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@ConnectionScope
public class RxBleGattCallback {
   private final BluetoothGattCallback bluetoothGattCallback;
   final BluetoothGattProvider bluetoothGattProvider;
   private final Scheduler callbackScheduler;
   final Relay<CharacteristicChangedEvent> changedCharacteristicSerializedPublishRelay;
   final RxBleGattCallback.Output<Integer> changedMtuOutput;
   final PublishRelay<RxBleConnection.RxBleConnectionState> connectionStatePublishRelay = PublishRelay.create();
   final DisconnectionRouter disconnectionRouter;
   private final Function<BleGattException, Observable<?>> errorMapper;
   final NativeCallbackDispatcher nativeCallbackDispatcher;
   final RxBleGattCallback.Output<ByteAssociation<UUID>> readCharacteristicOutput;
   final RxBleGattCallback.Output<ByteAssociation<BluetoothGattDescriptor>> readDescriptorOutput;
   final RxBleGattCallback.Output<Integer> readRssiOutput;
   final RxBleGattCallback.Output<RxBleDeviceServices> servicesDiscoveredOutput = new RxBleGattCallback.Output<>();
   final RxBleGattCallback.Output<ConnectionParameters> updatedConnectionOutput;
   final RxBleGattCallback.Output<ByteAssociation<UUID>> writeCharacteristicOutput;
   final RxBleGattCallback.Output<ByteAssociation<BluetoothGattDescriptor>> writeDescriptorOutput;

   @Inject
   public RxBleGattCallback(@Named("bluetooth_callbacks") Scheduler var1, BluetoothGattProvider var2, DisconnectionRouter var3, NativeCallbackDispatcher var4) {
      this.readCharacteristicOutput = new RxBleGattCallback.Output<>();
      this.writeCharacteristicOutput = new RxBleGattCallback.Output<>();
      this.changedCharacteristicSerializedPublishRelay = PublishRelay.<CharacteristicChangedEvent>create().toSerialized();
      this.readDescriptorOutput = new RxBleGattCallback.Output<>();
      this.writeDescriptorOutput = new RxBleGattCallback.Output<>();
      this.readRssiOutput = new RxBleGattCallback.Output<>();
      this.changedMtuOutput = new RxBleGattCallback.Output<>();
      this.updatedConnectionOutput = new RxBleGattCallback.Output<>();
      this.errorMapper = new Function<BleGattException, Observable<?>>(this) {
         final RxBleGattCallback this$0;

         {
            this.this$0 = var1;
         }

         public Observable<?> apply(BleGattException var1) {
            return Observable.error(var1);
         }
      };
      this.bluetoothGattCallback = new BluetoothGattCallback(this) {
         final RxBleGattCallback this$0;

         {
            this.this$0 = var1;
         }

         private boolean isDisconnectedOrDisconnecting(int var1) {
            boolean var2x;
            if (var1 != 0 && var1 != 3) {
               var2x = false;
            } else {
               var2x = true;
            }

            return var2x;
         }

         public void onCharacteristicChanged(BluetoothGatt var1, BluetoothGattCharacteristic var2x) {
            LoggerUtil.logCallback("onCharacteristicChanged", var1, var2x, true);
            this.this$0.nativeCallbackDispatcher.notifyNativeChangedCallback(var1, var2x);
            super.onCharacteristicChanged(var1, var2x);
            if (this.this$0.changedCharacteristicSerializedPublishRelay.hasObservers()) {
               this.this$0
                  .changedCharacteristicSerializedPublishRelay
                  .accept(new CharacteristicChangedEvent(var2x.getUuid(), var2x.getInstanceId(), var2x.getValue()));
            }
         }

         public void onCharacteristicRead(BluetoothGatt var1, BluetoothGattCharacteristic var2x, int var3x) {
            LoggerUtil.logCallback("onCharacteristicRead", var1, var3x, var2x, true);
            this.this$0.nativeCallbackDispatcher.notifyNativeReadCallback(var1, var2x, var3x);
            super.onCharacteristicRead(var1, var2x, var3x);
            if (this.this$0.readCharacteristicOutput.hasObservers()
               && !RxBleGattCallback.propagateErrorIfOccurred(
                  this.this$0.readCharacteristicOutput, var1, var2x, var3x, BleGattOperationType.CHARACTERISTIC_READ
               )) {
               this.this$0.readCharacteristicOutput.valueRelay.accept(new ByteAssociation<>(var2x.getUuid(), var2x.getValue()));
            }
         }

         public void onCharacteristicWrite(BluetoothGatt var1, BluetoothGattCharacteristic var2x, int var3x) {
            LoggerUtil.logCallback("onCharacteristicWrite", var1, var3x, var2x, false);
            this.this$0.nativeCallbackDispatcher.notifyNativeWriteCallback(var1, var2x, var3x);
            super.onCharacteristicWrite(var1, var2x, var3x);
            if (this.this$0.writeCharacteristicOutput.hasObservers()
               && !RxBleGattCallback.propagateErrorIfOccurred(
                  this.this$0.writeCharacteristicOutput, var1, var2x, var3x, BleGattOperationType.CHARACTERISTIC_WRITE
               )) {
               this.this$0.writeCharacteristicOutput.valueRelay.accept(new ByteAssociation<>(var2x.getUuid(), var2x.getValue()));
            }
         }

         public void onConnectionStateChange(BluetoothGatt var1, int var2x, int var3x) {
            LoggerUtil.logCallback("onConnectionStateChange", var1, var2x, var3x);
            this.this$0.nativeCallbackDispatcher.notifyNativeConnectionStateCallback(var1, var2x, var3x);
            super.onConnectionStateChange(var1, var2x, var3x);
            this.this$0.bluetoothGattProvider.updateBluetoothGatt(var1);
            if (this.isDisconnectedOrDisconnecting(var3x)) {
               this.this$0.disconnectionRouter.onDisconnectedException(new BleDisconnectedException(var1.getDevice().getAddress(), var2x));
            } else if (var2x != 0) {
               this.this$0.disconnectionRouter.onGattConnectionStateException(new BleGattException(var1, var2x, BleGattOperationType.CONNECTION_STATE));
            }

            this.this$0.connectionStatePublishRelay.accept(RxBleGattCallback.mapConnectionStateToRxBleConnectionStatus(var3x));
         }

         public void onConnectionUpdated(BluetoothGatt var1, int var2x, int var3x, int var4x, int var5) {
            LoggerUtil.logConnectionUpdateCallback("onConnectionUpdated", var1, var5, var2x, var3x, var4x);
            this.this$0.nativeCallbackDispatcher.notifyNativeParamsUpdateCallback(var1, var2x, var3x, var4x, var5);
            if (this.this$0.updatedConnectionOutput.hasObservers()
               && !RxBleGattCallback.propagateErrorIfOccurred(this.this$0.updatedConnectionOutput, var1, var5, BleGattOperationType.CONNECTION_PRIORITY_CHANGE)
               )
             {
               this.this$0.updatedConnectionOutput.valueRelay.accept(new ConnectionParametersImpl(var2x, var3x, var4x));
            }
         }

         public void onDescriptorRead(BluetoothGatt var1, BluetoothGattDescriptor var2x, int var3x) {
            LoggerUtil.logCallback("onDescriptorRead", var1, var3x, var2x, true);
            this.this$0.nativeCallbackDispatcher.notifyNativeDescriptorReadCallback(var1, var2x, var3x);
            super.onDescriptorRead(var1, var2x, var3x);
            if (this.this$0.readDescriptorOutput.hasObservers()
               && !RxBleGattCallback.propagateErrorIfOccurred(this.this$0.readDescriptorOutput, var1, var2x, var3x, BleGattOperationType.DESCRIPTOR_READ)) {
               this.this$0.readDescriptorOutput.valueRelay.accept(new ByteAssociation<>(var2x, var2x.getValue()));
            }
         }

         public void onDescriptorWrite(BluetoothGatt var1, BluetoothGattDescriptor var2x, int var3x) {
            LoggerUtil.logCallback("onDescriptorWrite", var1, var3x, var2x, false);
            this.this$0.nativeCallbackDispatcher.notifyNativeDescriptorWriteCallback(var1, var2x, var3x);
            super.onDescriptorWrite(var1, var2x, var3x);
            if (this.this$0.writeDescriptorOutput.hasObservers()
               && !RxBleGattCallback.propagateErrorIfOccurred(this.this$0.writeDescriptorOutput, var1, var2x, var3x, BleGattOperationType.DESCRIPTOR_WRITE)) {
               this.this$0.writeDescriptorOutput.valueRelay.accept(new ByteAssociation<>(var2x, var2x.getValue()));
            }
         }

         public void onMtuChanged(BluetoothGatt var1, int var2x, int var3x) {
            LoggerUtil.logCallback("onMtuChanged", var1, var3x, var2x);
            this.this$0.nativeCallbackDispatcher.notifyNativeMtuChangedCallback(var1, var2x, var3x);
            super.onMtuChanged(var1, var2x, var3x);
            if (this.this$0.changedMtuOutput.hasObservers()
               && !RxBleGattCallback.propagateErrorIfOccurred(this.this$0.changedMtuOutput, var1, var3x, BleGattOperationType.ON_MTU_CHANGED)) {
               this.this$0.changedMtuOutput.valueRelay.accept(var2x);
            }
         }

         public void onReadRemoteRssi(BluetoothGatt var1, int var2x, int var3x) {
            LoggerUtil.logCallback("onReadRemoteRssi", var1, var3x, var2x);
            this.this$0.nativeCallbackDispatcher.notifyNativeReadRssiCallback(var1, var2x, var3x);
            super.onReadRemoteRssi(var1, var2x, var3x);
            if (this.this$0.readRssiOutput.hasObservers()
               && !RxBleGattCallback.propagateErrorIfOccurred(this.this$0.readRssiOutput, var1, var3x, BleGattOperationType.READ_RSSI)) {
               this.this$0.readRssiOutput.valueRelay.accept(var2x);
            }
         }

         public void onReliableWriteCompleted(BluetoothGatt var1, int var2x) {
            LoggerUtil.logCallback("onReliableWriteCompleted", var1, var2x);
            this.this$0.nativeCallbackDispatcher.notifyNativeReliableWriteCallback(var1, var2x);
            super.onReliableWriteCompleted(var1, var2x);
         }

         public void onServicesDiscovered(BluetoothGatt var1, int var2x) {
            LoggerUtil.logCallback("onServicesDiscovered", var1, var2x);
            this.this$0.nativeCallbackDispatcher.notifyNativeServicesDiscoveredCallback(var1, var2x);
            super.onServicesDiscovered(var1, var2x);
            if (this.this$0.servicesDiscoveredOutput.hasObservers()
               && !RxBleGattCallback.propagateErrorIfOccurred(this.this$0.servicesDiscoveredOutput, var1, var2x, BleGattOperationType.SERVICE_DISCOVERY)) {
               this.this$0.servicesDiscoveredOutput.valueRelay.accept(new RxBleDeviceServices(var1.getServices()));
            }
         }
      };
      this.callbackScheduler = var1;
      this.bluetoothGattProvider = var2;
      this.disconnectionRouter = var3;
      this.nativeCallbackDispatcher = var4;
   }

   private static boolean isException(int var0) {
      boolean var1;
      if (var0 != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   static RxBleConnection.RxBleConnectionState mapConnectionStateToRxBleConnectionStatus(int var0) {
      if (var0 != 1) {
         if (var0 != 2) {
            return var0 != 3 ? RxBleConnection.RxBleConnectionState.DISCONNECTED : RxBleConnection.RxBleConnectionState.DISCONNECTING;
         } else {
            return RxBleConnection.RxBleConnectionState.CONNECTED;
         }
      } else {
         return RxBleConnection.RxBleConnectionState.CONNECTING;
      }
   }

   static boolean propagateErrorIfOccurred(RxBleGattCallback.Output<?> var0, BluetoothGatt var1, int var2, BleGattOperationType var3) {
      boolean var4;
      if (isException(var2) && propagateStatusError(var0, new BleGattException(var1, var2, var3))) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   static boolean propagateErrorIfOccurred(
      RxBleGattCallback.Output<?> var0, BluetoothGatt var1, BluetoothGattCharacteristic var2, int var3, BleGattOperationType var4
   ) {
      boolean var5;
      if (isException(var3) && propagateStatusError(var0, new BleGattCharacteristicException(var1, var2, var3, var4))) {
         var5 = true;
      } else {
         var5 = false;
      }

      return var5;
   }

   static boolean propagateErrorIfOccurred(
      RxBleGattCallback.Output<?> var0, BluetoothGatt var1, BluetoothGattDescriptor var2, int var3, BleGattOperationType var4
   ) {
      boolean var5;
      if (isException(var3) && propagateStatusError(var0, new BleGattDescriptorException(var1, var2, var3, var4))) {
         var5 = true;
      } else {
         var5 = false;
      }

      return var5;
   }

   private static boolean propagateStatusError(RxBleGattCallback.Output<?> var0, BleGattException var1) {
      var0.errorRelay.accept(var1);
      return true;
   }

   private <T> Observable<T> withDisconnectionHandling(RxBleGattCallback.Output<T> var1) {
      return Observable.merge(this.disconnectionRouter.asErrorOnlyObservable(), var1.valueRelay, var1.errorRelay.flatMap(this.errorMapper));
   }

   public BluetoothGattCallback getBluetoothGattCallback() {
      return this.bluetoothGattCallback;
   }

   public Observable<ConnectionParameters> getConnectionParametersUpdates() {
      return this.withDisconnectionHandling(this.updatedConnectionOutput).delay(0L, TimeUnit.SECONDS, this.callbackScheduler);
   }

   public Observable<CharacteristicChangedEvent> getOnCharacteristicChanged() {
      return Observable.merge(this.disconnectionRouter.asErrorOnlyObservable(), this.changedCharacteristicSerializedPublishRelay)
         .delay(0L, TimeUnit.SECONDS, this.callbackScheduler);
   }

   public Observable<ByteAssociation<UUID>> getOnCharacteristicRead() {
      return this.withDisconnectionHandling(this.readCharacteristicOutput).delay(0L, TimeUnit.SECONDS, this.callbackScheduler);
   }

   public Observable<ByteAssociation<UUID>> getOnCharacteristicWrite() {
      return this.withDisconnectionHandling(this.writeCharacteristicOutput).delay(0L, TimeUnit.SECONDS, this.callbackScheduler);
   }

   public Observable<RxBleConnection.RxBleConnectionState> getOnConnectionStateChange() {
      return this.connectionStatePublishRelay.delay(0L, TimeUnit.SECONDS, this.callbackScheduler);
   }

   public Observable<ByteAssociation<BluetoothGattDescriptor>> getOnDescriptorRead() {
      return this.withDisconnectionHandling(this.readDescriptorOutput).delay(0L, TimeUnit.SECONDS, this.callbackScheduler);
   }

   public Observable<ByteAssociation<BluetoothGattDescriptor>> getOnDescriptorWrite() {
      return this.withDisconnectionHandling(this.writeDescriptorOutput).delay(0L, TimeUnit.SECONDS, this.callbackScheduler);
   }

   public Observable<Integer> getOnMtuChanged() {
      return this.withDisconnectionHandling(this.changedMtuOutput).delay(0L, TimeUnit.SECONDS, this.callbackScheduler);
   }

   public Observable<Integer> getOnRssiRead() {
      return this.withDisconnectionHandling(this.readRssiOutput).delay(0L, TimeUnit.SECONDS, this.callbackScheduler);
   }

   public Observable<RxBleDeviceServices> getOnServicesDiscovered() {
      return this.withDisconnectionHandling(this.servicesDiscoveredOutput).delay(0L, TimeUnit.SECONDS, this.callbackScheduler);
   }

   public <T> Observable<T> observeDisconnect() {
      return this.disconnectionRouter.asErrorOnlyObservable();
   }

   public void setHiddenNativeCallback(HiddenBluetoothGattCallback var1) {
      this.nativeCallbackDispatcher.setNativeCallbackHidden(var1);
   }

   public void setNativeCallback(BluetoothGattCallback var1) {
      this.nativeCallbackDispatcher.setNativeCallback(var1);
   }

   private static class Output<T> {
      final PublishRelay<BleGattException> errorRelay;
      final PublishRelay<T> valueRelay = PublishRelay.create();

      Output() {
         this.errorRelay = PublishRelay.create();
      }

      boolean hasObservers() {
         boolean var1;
         if (!this.valueRelay.hasObservers() && !this.errorRelay.hasObservers()) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }
   }
}
