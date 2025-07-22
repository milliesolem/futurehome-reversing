package com.polidea.rxandroidble2;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import io.reactivex.Scheduler;

public final class ClientComponent_ClientModule_ProvideComputationSchedulerFactory implements Factory<Scheduler> {
   public static ClientComponent_ClientModule_ProvideComputationSchedulerFactory create() {
      return ClientComponent_ClientModule_ProvideComputationSchedulerFactory.InstanceHolder.INSTANCE;
   }

   public static Scheduler provideComputationScheduler() {
      return (Scheduler)Preconditions.checkNotNullFromProvides(ClientComponent.ClientModule.provideComputationScheduler());
   }

   public Scheduler get() {
      return provideComputationScheduler();
   }

   private static final class InstanceHolder {
      private static final ClientComponent_ClientModule_ProvideComputationSchedulerFactory INSTANCE = new ClientComponent_ClientModule_ProvideComputationSchedulerFactory();
   }
}
