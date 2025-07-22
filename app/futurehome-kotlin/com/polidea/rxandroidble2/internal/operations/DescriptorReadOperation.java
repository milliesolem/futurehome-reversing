package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattDescriptor;
import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble2.exceptions.BleGattOperationType;
import com.polidea.rxandroidble2.internal.SingleResponseOperation;
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback;
import com.polidea.rxandroidble2.internal.logger.LoggerUtil;
import com.polidea.rxandroidble2.internal.util.ByteAssociation;
import com.polidea.rxandroidble2.internal.util.ByteAssociationUtil;
import io.reactivex.Single;

public class DescriptorReadOperation extends SingleResponseOperation<ByteAssociation<BluetoothGattDescriptor>> {
   private final BluetoothGattDescriptor bluetoothGattDescriptor;

   @Inject
   DescriptorReadOperation(RxBleGattCallback var1, BluetoothGatt var2, @Named("operation-timeout") TimeoutConfiguration var3, BluetoothGattDescriptor var4) {
      super(var2, var1, BleGattOperationType.DESCRIPTOR_READ, var3);
      this.bluetoothGattDescriptor = var4;
   }

   @Override
   protected Single<ByteAssociation<BluetoothGattDescriptor>> getCallback(RxBleGattCallback var1) {
      return var1.getOnDescriptorRead().filter(ByteAssociationUtil.descriptorPredicate(this.bluetoothGattDescriptor)).firstOrError();
   }

   @Override
   protected boolean startOperation(BluetoothGatt var1) {
      return var1.readDescriptor(this.bluetoothGattDescriptor);
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("DescriptorReadOperation{");
      var1.append(super.toString());
      var1.append(", descriptor=");
      var1.append(LoggerUtil.wrap(this.bluetoothGattDescriptor, false));
      var1.append('}');
      return var1.toString();
   }
}
