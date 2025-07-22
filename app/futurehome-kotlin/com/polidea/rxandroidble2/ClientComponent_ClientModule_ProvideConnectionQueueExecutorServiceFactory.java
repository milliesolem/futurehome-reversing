package com.polidea.rxandroidble2;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import java.util.concurrent.ExecutorService;

public final class ClientComponent_ClientModule_ProvideConnectionQueueExecutorServiceFactory implements Factory<ExecutorService> {
   public static ClientComponent_ClientModule_ProvideConnectionQueueExecutorServiceFactory create() {
      return ClientComponent_ClientModule_ProvideConnectionQueueExecutorServiceFactory.InstanceHolder.INSTANCE;
   }

   public static ExecutorService provideConnectionQueueExecutorService() {
      return (ExecutorService)Preconditions.checkNotNullFromProvides(ClientComponent.ClientModule.provideConnectionQueueExecutorService());
   }

   public ExecutorService get() {
      return provideConnectionQueueExecutorService();
   }

   private static final class InstanceHolder {
      private static final ClientComponent_ClientModule_ProvideConnectionQueueExecutorServiceFactory INSTANCE = new ClientComponent_ClientModule_ProvideConnectionQueueExecutorServiceFactory();
   }
}
