package com.polidea.rxandroidble2.internal.connection;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.serialization.ClientOperationQueue;
import io.reactivex.Scheduler;

public final class ConnectorImpl_Factory implements Factory<ConnectorImpl> {
   private final Provider<Scheduler> callbacksSchedulerProvider;
   private final Provider<ClientOperationQueue> clientOperationQueueProvider;
   private final Provider<ConnectionComponent.Builder> connectionComponentBuilderProvider;

   public ConnectorImpl_Factory(Provider<ClientOperationQueue> var1, Provider<ConnectionComponent.Builder> var2, Provider<Scheduler> var3) {
      this.clientOperationQueueProvider = var1;
      this.connectionComponentBuilderProvider = var2;
      this.callbacksSchedulerProvider = var3;
   }

   public static ConnectorImpl_Factory create(Provider<ClientOperationQueue> var0, Provider<ConnectionComponent.Builder> var1, Provider<Scheduler> var2) {
      return new ConnectorImpl_Factory(var0, var1, var2);
   }

   public static ConnectorImpl newInstance(ClientOperationQueue var0, ConnectionComponent.Builder var1, Scheduler var2) {
      return new ConnectorImpl(var0, var1, var2);
   }

   public ConnectorImpl get() {
      return newInstance(
         (ClientOperationQueue)this.clientOperationQueueProvider.get(),
         (ConnectionComponent.Builder)this.connectionComponentBuilderProvider.get(),
         (Scheduler)this.callbacksSchedulerProvider.get()
      );
   }
}
