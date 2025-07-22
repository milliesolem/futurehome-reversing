package com.polidea.rxandroidble2.internal.scan;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import io.reactivex.Scheduler;

public final class ScanSettingsEmulator_Factory implements Factory<ScanSettingsEmulator> {
   private final Provider<Scheduler> schedulerProvider;

   public ScanSettingsEmulator_Factory(Provider<Scheduler> var1) {
      this.schedulerProvider = var1;
   }

   public static ScanSettingsEmulator_Factory create(Provider<Scheduler> var0) {
      return new ScanSettingsEmulator_Factory(var0);
   }

   public static ScanSettingsEmulator newInstance(Scheduler var0) {
      return new ScanSettingsEmulator(var0);
   }

   public ScanSettingsEmulator get() {
      return newInstance((Scheduler)this.schedulerProvider.get());
   }
}
