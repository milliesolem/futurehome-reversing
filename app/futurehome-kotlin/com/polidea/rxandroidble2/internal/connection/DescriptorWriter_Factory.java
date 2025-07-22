package com.polidea.rxandroidble2.internal.connection;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.operations.OperationsProvider;
import com.polidea.rxandroidble2.internal.serialization.ConnectionOperationQueue;

public final class DescriptorWriter_Factory implements Factory<DescriptorWriter> {
   private final Provider<ConnectionOperationQueue> operationQueueProvider;
   private final Provider<OperationsProvider> operationsProvider;

   public DescriptorWriter_Factory(Provider<ConnectionOperationQueue> var1, Provider<OperationsProvider> var2) {
      this.operationQueueProvider = var1;
      this.operationsProvider = var2;
   }

   public static DescriptorWriter_Factory create(Provider<ConnectionOperationQueue> var0, Provider<OperationsProvider> var1) {
      return new DescriptorWriter_Factory(var0, var1);
   }

   public static DescriptorWriter newInstance(ConnectionOperationQueue var0, OperationsProvider var1) {
      return new DescriptorWriter(var0, var1);
   }

   public DescriptorWriter get() {
      return newInstance((ConnectionOperationQueue)this.operationQueueProvider.get(), (OperationsProvider)this.operationsProvider.get());
   }
}
