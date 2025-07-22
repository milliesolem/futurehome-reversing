package com.polidea.rxandroidble2;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import java.util.concurrent.ExecutorService;

public final class ClientComponent_ClientModule_ProvideBluetoothInteractionExecutorServiceFactory implements Factory<ExecutorService> {
   public static ClientComponent_ClientModule_ProvideBluetoothInteractionExecutorServiceFactory create() {
      return ClientComponent_ClientModule_ProvideBluetoothInteractionExecutorServiceFactory.InstanceHolder.INSTANCE;
   }

   public static ExecutorService provideBluetoothInteractionExecutorService() {
      return (ExecutorService)Preconditions.checkNotNullFromProvides(ClientComponent.ClientModule.provideBluetoothInteractionExecutorService());
   }

   public ExecutorService get() {
      return provideBluetoothInteractionExecutorService();
   }

   private static final class InstanceHolder {
      private static final ClientComponent_ClientModule_ProvideBluetoothInteractionExecutorServiceFactory INSTANCE = new ClientComponent_ClientModule_ProvideBluetoothInteractionExecutorServiceFactory();
   }
}
