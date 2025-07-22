package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble2.exceptions.BleGattOperationType;
import com.polidea.rxandroidble2.internal.SingleResponseOperation;
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback;
import com.polidea.rxandroidble2.internal.logger.LoggerUtil;
import com.polidea.rxandroidble2.internal.util.ByteAssociationUtil;
import io.reactivex.Single;

public class DescriptorWriteOperation extends SingleResponseOperation<byte[]> {
   private final int bluetoothGattCharacteristicDefaultWriteType;
   private final BluetoothGattDescriptor bluetoothGattDescriptor;
   private final byte[] data;

   DescriptorWriteOperation(
      RxBleGattCallback var1, BluetoothGatt var2, @Named("operation-timeout") TimeoutConfiguration var3, int var4, BluetoothGattDescriptor var5, byte[] var6
   ) {
      super(var2, var1, BleGattOperationType.DESCRIPTOR_WRITE, var3);
      this.bluetoothGattCharacteristicDefaultWriteType = var4;
      this.bluetoothGattDescriptor = var5;
      this.data = var6;
   }

   @Override
   protected Single<byte[]> getCallback(RxBleGattCallback var1) {
      return var1.getOnDescriptorWrite()
         .filter(ByteAssociationUtil.descriptorPredicate(this.bluetoothGattDescriptor))
         .firstOrError()
         .map(ByteAssociationUtil.getBytesFromAssociation());
   }

   @Override
   protected boolean startOperation(BluetoothGatt var1) {
      this.bluetoothGattDescriptor.setValue(this.data);
      BluetoothGattCharacteristic var4 = this.bluetoothGattDescriptor.getCharacteristic();
      int var2 = var4.getWriteType();
      var4.setWriteType(this.bluetoothGattCharacteristicDefaultWriteType);
      boolean var3 = var1.writeDescriptor(this.bluetoothGattDescriptor);
      var4.setWriteType(var2);
      return var3;
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("DescriptorWriteOperation{");
      var1.append(super.toString());
      var1.append(", descriptor=");
      var1.append(new LoggerUtil.AttributeLogWrapper(this.bluetoothGattDescriptor.getUuid(), this.data, true));
      var1.append('}');
      return var1.toString();
   }
}
