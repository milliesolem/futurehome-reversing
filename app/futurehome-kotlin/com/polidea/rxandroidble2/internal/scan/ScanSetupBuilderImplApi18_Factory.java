package com.polidea.rxandroidble2.internal.scan;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.util.RxBleAdapterWrapper;

public final class ScanSetupBuilderImplApi18_Factory implements Factory<ScanSetupBuilderImplApi18> {
   private final Provider<InternalScanResultCreator> internalScanResultCreatorProvider;
   private final Provider<RxBleAdapterWrapper> rxBleAdapterWrapperProvider;
   private final Provider<ScanSettingsEmulator> scanSettingsEmulatorProvider;

   public ScanSetupBuilderImplApi18_Factory(Provider<RxBleAdapterWrapper> var1, Provider<InternalScanResultCreator> var2, Provider<ScanSettingsEmulator> var3) {
      this.rxBleAdapterWrapperProvider = var1;
      this.internalScanResultCreatorProvider = var2;
      this.scanSettingsEmulatorProvider = var3;
   }

   public static ScanSetupBuilderImplApi18_Factory create(
      Provider<RxBleAdapterWrapper> var0, Provider<InternalScanResultCreator> var1, Provider<ScanSettingsEmulator> var2
   ) {
      return new ScanSetupBuilderImplApi18_Factory(var0, var1, var2);
   }

   public static ScanSetupBuilderImplApi18 newInstance(RxBleAdapterWrapper var0, InternalScanResultCreator var1, ScanSettingsEmulator var2) {
      return new ScanSetupBuilderImplApi18(var0, var1, var2);
   }

   public ScanSetupBuilderImplApi18 get() {
      return newInstance(
         (RxBleAdapterWrapper)this.rxBleAdapterWrapperProvider.get(),
         (InternalScanResultCreator)this.internalScanResultCreatorProvider.get(),
         (ScanSettingsEmulator)this.scanSettingsEmulatorProvider.get()
      );
   }
}
