package com.polidea.rxandroidble2.internal.serialization;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import io.reactivex.Scheduler;

public final class ClientOperationQueueImpl_Factory implements Factory<ClientOperationQueueImpl> {
   private final Provider<Scheduler> callbackSchedulerProvider;

   public ClientOperationQueueImpl_Factory(Provider<Scheduler> var1) {
      this.callbackSchedulerProvider = var1;
   }

   public static ClientOperationQueueImpl_Factory create(Provider<Scheduler> var0) {
      return new ClientOperationQueueImpl_Factory(var0);
   }

   public static ClientOperationQueueImpl newInstance(Scheduler var0) {
      return new ClientOperationQueueImpl(var0);
   }

   public ClientOperationQueueImpl get() {
      return newInstance((Scheduler)this.callbackSchedulerProvider.get());
   }
}
