package com.polidea.rxandroidble2;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import bleshadow.javax.inject.Provider;
import io.reactivex.Scheduler;
import java.util.concurrent.ExecutorService;

public final class ClientComponent_ClientModule_ProvideFinalizationCloseableFactory implements Factory<ClientComponent.ClientComponentFinalizer> {
   private final Provider<Scheduler> callbacksSchedulerProvider;
   private final Provider<ExecutorService> connectionQueueExecutorServiceProvider;
   private final Provider<ExecutorService> interactionExecutorServiceProvider;

   public ClientComponent_ClientModule_ProvideFinalizationCloseableFactory(
      Provider<ExecutorService> var1, Provider<Scheduler> var2, Provider<ExecutorService> var3
   ) {
      this.interactionExecutorServiceProvider = var1;
      this.callbacksSchedulerProvider = var2;
      this.connectionQueueExecutorServiceProvider = var3;
   }

   public static ClientComponent_ClientModule_ProvideFinalizationCloseableFactory create(
      Provider<ExecutorService> var0, Provider<Scheduler> var1, Provider<ExecutorService> var2
   ) {
      return new ClientComponent_ClientModule_ProvideFinalizationCloseableFactory(var0, var1, var2);
   }

   public static ClientComponent.ClientComponentFinalizer provideFinalizationCloseable(ExecutorService var0, Scheduler var1, ExecutorService var2) {
      return (ClientComponent.ClientComponentFinalizer)Preconditions.checkNotNullFromProvides(
         ClientComponent.ClientModule.provideFinalizationCloseable(var0, var1, var2)
      );
   }

   public ClientComponent.ClientComponentFinalizer get() {
      return provideFinalizationCloseable(
         (ExecutorService)this.interactionExecutorServiceProvider.get(),
         (Scheduler)this.callbacksSchedulerProvider.get(),
         (ExecutorService)this.connectionQueueExecutorServiceProvider.get()
      );
   }
}
