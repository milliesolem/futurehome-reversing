package com.polidea.rxandroidble2.internal.scan;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.util.LocationServicesStatus;
import com.polidea.rxandroidble2.internal.util.RxBleAdapterWrapper;

public final class ScanPreconditionsVerifierApi18_Factory implements Factory<ScanPreconditionsVerifierApi18> {
   private final Provider<LocationServicesStatus> locationServicesStatusProvider;
   private final Provider<RxBleAdapterWrapper> rxBleAdapterWrapperProvider;

   public ScanPreconditionsVerifierApi18_Factory(Provider<RxBleAdapterWrapper> var1, Provider<LocationServicesStatus> var2) {
      this.rxBleAdapterWrapperProvider = var1;
      this.locationServicesStatusProvider = var2;
   }

   public static ScanPreconditionsVerifierApi18_Factory create(Provider<RxBleAdapterWrapper> var0, Provider<LocationServicesStatus> var1) {
      return new ScanPreconditionsVerifierApi18_Factory(var0, var1);
   }

   public static ScanPreconditionsVerifierApi18 newInstance(RxBleAdapterWrapper var0, LocationServicesStatus var1) {
      return new ScanPreconditionsVerifierApi18(var0, var1);
   }

   public ScanPreconditionsVerifierApi18 get() {
      return newInstance((RxBleAdapterWrapper)this.rxBleAdapterWrapperProvider.get(), (LocationServicesStatus)this.locationServicesStatusProvider.get());
   }
}
