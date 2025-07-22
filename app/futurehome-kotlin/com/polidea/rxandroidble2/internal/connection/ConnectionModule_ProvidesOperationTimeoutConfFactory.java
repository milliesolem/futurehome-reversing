package com.polidea.rxandroidble2.internal.connection;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.Timeout;
import com.polidea.rxandroidble2.internal.operations.TimeoutConfiguration;
import io.reactivex.Scheduler;

public final class ConnectionModule_ProvidesOperationTimeoutConfFactory implements Factory<TimeoutConfiguration> {
   private final Provider<Timeout> operationTimeoutProvider;
   private final Provider<Scheduler> timeoutSchedulerProvider;

   public ConnectionModule_ProvidesOperationTimeoutConfFactory(Provider<Scheduler> var1, Provider<Timeout> var2) {
      this.timeoutSchedulerProvider = var1;
      this.operationTimeoutProvider = var2;
   }

   public static ConnectionModule_ProvidesOperationTimeoutConfFactory create(Provider<Scheduler> var0, Provider<Timeout> var1) {
      return new ConnectionModule_ProvidesOperationTimeoutConfFactory(var0, var1);
   }

   public static TimeoutConfiguration providesOperationTimeoutConf(Scheduler var0, Timeout var1) {
      return (TimeoutConfiguration)Preconditions.checkNotNullFromProvides(ConnectionModule.providesOperationTimeoutConf(var0, var1));
   }

   public TimeoutConfiguration get() {
      return providesOperationTimeoutConf((Scheduler)this.timeoutSchedulerProvider.get(), (Timeout)this.operationTimeoutProvider.get());
   }
}
