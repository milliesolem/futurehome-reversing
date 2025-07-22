package com.polidea.rxandroidble2.internal;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.operations.TimeoutConfiguration;
import io.reactivex.Scheduler;

public final class DeviceModule_ProvidesDisconnectTimeoutConfFactory implements Factory<TimeoutConfiguration> {
   private final Provider<Scheduler> timeoutSchedulerProvider;

   public DeviceModule_ProvidesDisconnectTimeoutConfFactory(Provider<Scheduler> var1) {
      this.timeoutSchedulerProvider = var1;
   }

   public static DeviceModule_ProvidesDisconnectTimeoutConfFactory create(Provider<Scheduler> var0) {
      return new DeviceModule_ProvidesDisconnectTimeoutConfFactory(var0);
   }

   public static TimeoutConfiguration providesDisconnectTimeoutConf(Scheduler var0) {
      return (TimeoutConfiguration)Preconditions.checkNotNullFromProvides(DeviceModule.providesDisconnectTimeoutConf(var0));
   }

   public TimeoutConfiguration get() {
      return providesDisconnectTimeoutConf((Scheduler)this.timeoutSchedulerProvider.get());
   }
}
