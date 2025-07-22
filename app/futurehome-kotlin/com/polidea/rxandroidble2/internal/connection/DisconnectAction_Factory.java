package com.polidea.rxandroidble2.internal.connection;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.operations.DisconnectOperation;
import com.polidea.rxandroidble2.internal.serialization.ClientOperationQueue;

public final class DisconnectAction_Factory implements Factory<DisconnectAction> {
   private final Provider<ClientOperationQueue> clientOperationQueueProvider;
   private final Provider<DisconnectOperation> operationDisconnectProvider;

   public DisconnectAction_Factory(Provider<ClientOperationQueue> var1, Provider<DisconnectOperation> var2) {
      this.clientOperationQueueProvider = var1;
      this.operationDisconnectProvider = var2;
   }

   public static DisconnectAction_Factory create(Provider<ClientOperationQueue> var0, Provider<DisconnectOperation> var1) {
      return new DisconnectAction_Factory(var0, var1);
   }

   public static DisconnectAction newInstance(ClientOperationQueue var0, DisconnectOperation var1) {
      return new DisconnectAction(var0, var1);
   }

   public DisconnectAction get() {
      return newInstance((ClientOperationQueue)this.clientOperationQueueProvider.get(), (DisconnectOperation)this.operationDisconnectProvider.get());
   }
}
