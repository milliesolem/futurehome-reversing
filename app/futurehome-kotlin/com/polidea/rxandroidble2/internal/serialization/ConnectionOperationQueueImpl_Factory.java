package com.polidea.rxandroidble2.internal.serialization;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.connection.DisconnectionRouterOutput;
import io.reactivex.Scheduler;
import java.util.concurrent.ExecutorService;

public final class ConnectionOperationQueueImpl_Factory implements Factory<ConnectionOperationQueueImpl> {
   private final Provider<Scheduler> callbackSchedulerProvider;
   private final Provider<String> deviceMacAddressProvider;
   private final Provider<DisconnectionRouterOutput> disconnectionRouterOutputProvider;
   private final Provider<ExecutorService> executorServiceProvider;

   public ConnectionOperationQueueImpl_Factory(
      Provider<String> var1, Provider<DisconnectionRouterOutput> var2, Provider<ExecutorService> var3, Provider<Scheduler> var4
   ) {
      this.deviceMacAddressProvider = var1;
      this.disconnectionRouterOutputProvider = var2;
      this.executorServiceProvider = var3;
      this.callbackSchedulerProvider = var4;
   }

   public static ConnectionOperationQueueImpl_Factory create(
      Provider<String> var0, Provider<DisconnectionRouterOutput> var1, Provider<ExecutorService> var2, Provider<Scheduler> var3
   ) {
      return new ConnectionOperationQueueImpl_Factory(var0, var1, var2, var3);
   }

   public static ConnectionOperationQueueImpl newInstance(String var0, DisconnectionRouterOutput var1, ExecutorService var2, Scheduler var3) {
      return new ConnectionOperationQueueImpl(var0, var1, var2, var3);
   }

   public ConnectionOperationQueueImpl get() {
      return newInstance(
         (String)this.deviceMacAddressProvider.get(),
         (DisconnectionRouterOutput)this.disconnectionRouterOutputProvider.get(),
         (ExecutorService)this.executorServiceProvider.get(),
         (Scheduler)this.callbackSchedulerProvider.get()
      );
   }
}
