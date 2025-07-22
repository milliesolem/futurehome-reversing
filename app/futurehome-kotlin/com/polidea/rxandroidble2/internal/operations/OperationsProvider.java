package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.internal.connection.PayloadSizeLimitProvider;
import java.util.concurrent.TimeUnit;

public interface OperationsProvider {
   ConnectionPriorityChangeOperation provideConnectionPriorityChangeOperation(int var1, long var2, TimeUnit var4);

   CharacteristicLongWriteOperation provideLongWriteOperation(
      BluetoothGattCharacteristic var1,
      RxBleConnection.WriteOperationAckStrategy var2,
      RxBleConnection.WriteOperationRetryStrategy var3,
      PayloadSizeLimitProvider var4,
      byte[] var5
   );

   MtuRequestOperation provideMtuChangeOperation(int var1);

   CharacteristicReadOperation provideReadCharacteristic(BluetoothGattCharacteristic var1);

   DescriptorReadOperation provideReadDescriptor(BluetoothGattDescriptor var1);

   ReadRssiOperation provideRssiReadOperation();

   ServiceDiscoveryOperation provideServiceDiscoveryOperation(long var1, TimeUnit var3);

   CharacteristicWriteOperation provideWriteCharacteristic(BluetoothGattCharacteristic var1, byte[] var2);

   DescriptorWriteOperation provideWriteDescriptor(BluetoothGattDescriptor var1, byte[] var2);
}
