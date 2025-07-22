package com.polidea.rxandroidble2.internal.connection;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.internal.operations.OperationsProvider;
import com.polidea.rxandroidble2.internal.serialization.ConnectionOperationQueue;

public final class LongWriteOperationBuilderImpl_Factory implements Factory<LongWriteOperationBuilderImpl> {
   private final Provider<MtuBasedPayloadSizeLimit> defaultMaxBatchSizeProvider;
   private final Provider<ConnectionOperationQueue> operationQueueProvider;
   private final Provider<OperationsProvider> operationsProvider;
   private final Provider<RxBleConnection> rxBleConnectionProvider;

   public LongWriteOperationBuilderImpl_Factory(
      Provider<ConnectionOperationQueue> var1, Provider<MtuBasedPayloadSizeLimit> var2, Provider<RxBleConnection> var3, Provider<OperationsProvider> var4
   ) {
      this.operationQueueProvider = var1;
      this.defaultMaxBatchSizeProvider = var2;
      this.rxBleConnectionProvider = var3;
      this.operationsProvider = var4;
   }

   public static LongWriteOperationBuilderImpl_Factory create(
      Provider<ConnectionOperationQueue> var0, Provider<MtuBasedPayloadSizeLimit> var1, Provider<RxBleConnection> var2, Provider<OperationsProvider> var3
   ) {
      return new LongWriteOperationBuilderImpl_Factory(var0, var1, var2, var3);
   }

   public static LongWriteOperationBuilderImpl newInstance(ConnectionOperationQueue var0, Object var1, RxBleConnection var2, OperationsProvider var3) {
      return new LongWriteOperationBuilderImpl(var0, (MtuBasedPayloadSizeLimit)var1, var2, var3);
   }

   public LongWriteOperationBuilderImpl get() {
      return newInstance(
         (ConnectionOperationQueue)this.operationQueueProvider.get(),
         this.defaultMaxBatchSizeProvider.get(),
         (RxBleConnection)this.rxBleConnectionProvider.get(),
         (OperationsProvider)this.operationsProvider.get()
      );
   }
}
