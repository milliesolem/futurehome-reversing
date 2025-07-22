package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble2.exceptions.BleGattOperationType;
import com.polidea.rxandroidble2.internal.SingleResponseOperation;
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback;
import com.polidea.rxandroidble2.internal.logger.LoggerUtil;
import com.polidea.rxandroidble2.internal.util.ByteAssociationUtil;
import io.reactivex.Single;

public class CharacteristicReadOperation extends SingleResponseOperation<byte[]> {
   private final BluetoothGattCharacteristic bluetoothGattCharacteristic;

   CharacteristicReadOperation(
      RxBleGattCallback var1, BluetoothGatt var2, @Named("operation-timeout") TimeoutConfiguration var3, BluetoothGattCharacteristic var4
   ) {
      super(var2, var1, BleGattOperationType.CHARACTERISTIC_READ, var3);
      this.bluetoothGattCharacteristic = var4;
   }

   @Override
   protected Single<byte[]> getCallback(RxBleGattCallback var1) {
      return var1.getOnCharacteristicRead()
         .filter(ByteAssociationUtil.characteristicUUIDPredicate(this.bluetoothGattCharacteristic.getUuid()))
         .firstOrError()
         .map(ByteAssociationUtil.getBytesFromAssociation());
   }

   @Override
   protected boolean startOperation(BluetoothGatt var1) {
      return var1.readCharacteristic(this.bluetoothGattCharacteristic);
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("CharacteristicReadOperation{");
      var1.append(super.toString());
      var1.append(", characteristic=");
      var1.append(LoggerUtil.wrap(this.bluetoothGattCharacteristic, false));
      var1.append('}');
      return var1.toString();
   }
}
