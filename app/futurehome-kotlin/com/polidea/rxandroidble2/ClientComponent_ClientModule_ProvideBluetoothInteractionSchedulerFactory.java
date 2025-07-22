package com.polidea.rxandroidble2;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import bleshadow.javax.inject.Provider;
import io.reactivex.Scheduler;
import java.util.concurrent.ExecutorService;

public final class ClientComponent_ClientModule_ProvideBluetoothInteractionSchedulerFactory implements Factory<Scheduler> {
   private final Provider<ExecutorService> serviceProvider;

   public ClientComponent_ClientModule_ProvideBluetoothInteractionSchedulerFactory(Provider<ExecutorService> var1) {
      this.serviceProvider = var1;
   }

   public static ClientComponent_ClientModule_ProvideBluetoothInteractionSchedulerFactory create(Provider<ExecutorService> var0) {
      return new ClientComponent_ClientModule_ProvideBluetoothInteractionSchedulerFactory(var0);
   }

   public static Scheduler provideBluetoothInteractionScheduler(ExecutorService var0) {
      return (Scheduler)Preconditions.checkNotNullFromProvides(ClientComponent.ClientModule.provideBluetoothInteractionScheduler(var0));
   }

   public Scheduler get() {
      return provideBluetoothInteractionScheduler((ExecutorService)this.serviceProvider.get());
   }
}
