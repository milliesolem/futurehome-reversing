package com.polidea.rxandroidble2.internal.scan;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import io.reactivex.Scheduler;

public final class ScanPreconditionsVerifierApi24_Factory implements Factory<ScanPreconditionsVerifierApi24> {
   private final Provider<ScanPreconditionsVerifierApi18> scanPreconditionVerifierApi18Provider;
   private final Provider<Scheduler> timeSchedulerProvider;

   public ScanPreconditionsVerifierApi24_Factory(Provider<ScanPreconditionsVerifierApi18> var1, Provider<Scheduler> var2) {
      this.scanPreconditionVerifierApi18Provider = var1;
      this.timeSchedulerProvider = var2;
   }

   public static ScanPreconditionsVerifierApi24_Factory create(Provider<ScanPreconditionsVerifierApi18> var0, Provider<Scheduler> var1) {
      return new ScanPreconditionsVerifierApi24_Factory(var0, var1);
   }

   public static ScanPreconditionsVerifierApi24 newInstance(ScanPreconditionsVerifierApi18 var0, Scheduler var1) {
      return new ScanPreconditionsVerifierApi24(var0, var1);
   }

   public ScanPreconditionsVerifierApi24 get() {
      return newInstance((ScanPreconditionsVerifierApi18)this.scanPreconditionVerifierApi18Provider.get(), (Scheduler)this.timeSchedulerProvider.get());
   }
}
