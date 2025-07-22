package com.polidea.rxandroidble2;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import io.reactivex.Scheduler;

public final class ClientComponent_ClientModule_ProvideBluetoothCallbacksSchedulerFactory implements Factory<Scheduler> {
   public static ClientComponent_ClientModule_ProvideBluetoothCallbacksSchedulerFactory create() {
      return ClientComponent_ClientModule_ProvideBluetoothCallbacksSchedulerFactory.InstanceHolder.INSTANCE;
   }

   public static Scheduler provideBluetoothCallbacksScheduler() {
      return (Scheduler)Preconditions.checkNotNullFromProvides(ClientComponent.ClientModule.provideBluetoothCallbacksScheduler());
   }

   public Scheduler get() {
      return provideBluetoothCallbacksScheduler();
   }

   private static final class InstanceHolder {
      private static final ClientComponent_ClientModule_ProvideBluetoothCallbacksSchedulerFactory INSTANCE = new ClientComponent_ClientModule_ProvideBluetoothCallbacksSchedulerFactory();
   }
}
