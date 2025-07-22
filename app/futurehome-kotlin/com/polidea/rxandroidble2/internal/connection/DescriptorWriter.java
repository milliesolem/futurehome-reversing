package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGattDescriptor;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.internal.operations.OperationsProvider;
import com.polidea.rxandroidble2.internal.serialization.ConnectionOperationQueue;
import io.reactivex.Completable;

@ConnectionScope
class DescriptorWriter {
   private final ConnectionOperationQueue operationQueue;
   private final OperationsProvider operationsProvider;

   @Inject
   DescriptorWriter(ConnectionOperationQueue var1, OperationsProvider var2) {
      this.operationQueue = var1;
      this.operationsProvider = var2;
   }

   Completable writeDescriptor(BluetoothGattDescriptor var1, byte[] var2) {
      return this.operationQueue.queue(this.operationsProvider.provideWriteDescriptor(var1, var2)).ignoreElements();
   }
}
