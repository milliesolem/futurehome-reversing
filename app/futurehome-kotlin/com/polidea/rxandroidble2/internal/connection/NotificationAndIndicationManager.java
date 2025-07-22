package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble2.NotificationSetupMode;
import com.polidea.rxandroidble2.exceptions.BleCannotSetCharacteristicNotificationException;
import com.polidea.rxandroidble2.internal.util.ActiveCharacteristicNotification;
import com.polidea.rxandroidble2.internal.util.CharacteristicNotificationId;
import io.reactivex.Completable;
import io.reactivex.CompletableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ConnectionScope
class NotificationAndIndicationManager {
   static final UUID CLIENT_CHARACTERISTIC_CONFIG_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
   final Map<CharacteristicNotificationId, ActiveCharacteristicNotification> activeNotificationObservableMap = new HashMap<>();
   final BluetoothGatt bluetoothGatt;
   final byte[] configDisable;
   final byte[] configEnableIndication;
   final byte[] configEnableNotification;
   final DescriptorWriter descriptorWriter;
   final RxBleGattCallback gattCallback;

   @Inject
   NotificationAndIndicationManager(
      @Named("enable-notification-value") byte[] var1,
      @Named("enable-indication-value") byte[] var2,
      @Named("disable-notification-value") byte[] var3,
      BluetoothGatt var4,
      RxBleGattCallback var5,
      DescriptorWriter var6
   ) {
      this.configEnableNotification = var1;
      this.configEnableIndication = var2;
      this.configDisable = var3;
      this.bluetoothGatt = var4;
      this.gattCallback = var5;
      this.descriptorWriter = var6;
   }

   static Observable<byte[]> observeOnCharacteristicChangeCallbacks(RxBleGattCallback var0, CharacteristicNotificationId var1) {
      return var0.getOnCharacteristicChanged()
         .filter(new NotificationAndIndicationManager$$ExternalSyntheticLambda0(var1))
         .map(new NotificationAndIndicationManager$$ExternalSyntheticLambda1());
   }

   static Completable setCharacteristicNotification(BluetoothGatt var0, BluetoothGattCharacteristic var1, boolean var2) {
      return Completable.fromAction(new NotificationAndIndicationManager$$ExternalSyntheticLambda5(var0, var1, var2));
   }

   static ObservableTransformer<Observable<byte[]>, Observable<byte[]>> setupModeTransformer(
      DescriptorWriter var0, BluetoothGattCharacteristic var1, byte[] var2, NotificationSetupMode var3
   ) {
      return new NotificationAndIndicationManager$$ExternalSyntheticLambda3(var3, var1, var0, var2);
   }

   static CompletableTransformer teardownModeTransformer(DescriptorWriter var0, BluetoothGattCharacteristic var1, byte[] var2, NotificationSetupMode var3) {
      return new NotificationAndIndicationManager$$ExternalSyntheticLambda7(var3, var1, var0, var2);
   }

   static Completable writeClientCharacteristicConfig(BluetoothGattCharacteristic var0, DescriptorWriter var1, byte[] var2) {
      BluetoothGattDescriptor var3 = var0.getDescriptor(CLIENT_CHARACTERISTIC_CONFIG_UUID);
      return var3 == null
         ? Completable.error(new BleCannotSetCharacteristicNotificationException(var0, 2, null))
         : var1.writeDescriptor(var3, var2).onErrorResumeNext(new NotificationAndIndicationManager$$ExternalSyntheticLambda6(var0));
   }

   Observable<Observable<byte[]>> setupServerInitiatedCharacteristicRead(BluetoothGattCharacteristic var1, NotificationSetupMode var2, boolean var3) {
      return Observable.defer(new NotificationAndIndicationManager$$ExternalSyntheticLambda4(this, var1, var3, var2));
   }
}
