package com.polidea.rxandroidble2.internal;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.operations.TimeoutConfiguration;
import io.reactivex.Scheduler;

public final class DeviceModule_ProvidesConnectTimeoutConfFactory implements Factory<TimeoutConfiguration> {
   private final Provider<Scheduler> timeoutSchedulerProvider;

   public DeviceModule_ProvidesConnectTimeoutConfFactory(Provider<Scheduler> var1) {
      this.timeoutSchedulerProvider = var1;
   }

   public static DeviceModule_ProvidesConnectTimeoutConfFactory create(Provider<Scheduler> var0) {
      return new DeviceModule_ProvidesConnectTimeoutConfFactory(var0);
   }

   public static TimeoutConfiguration providesConnectTimeoutConf(Scheduler var0) {
      return (TimeoutConfiguration)Preconditions.checkNotNullFromProvides(DeviceModule.providesConnectTimeoutConf(var0));
   }

   public TimeoutConfiguration get() {
      return providesConnectTimeoutConf((Scheduler)this.timeoutSchedulerProvider.get());
   }
}
