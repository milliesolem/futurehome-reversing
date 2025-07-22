package com.polidea.rxandroidble2.internal.scan;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.util.RxBleAdapterWrapper;

public final class ScanSetupBuilderImplApi23_Factory implements Factory<ScanSetupBuilderImplApi23> {
   private final Provider<AndroidScanObjectsConverter> androidScanObjectsConverterProvider;
   private final Provider<InternalScanResultCreator> internalScanResultCreatorProvider;
   private final Provider<RxBleAdapterWrapper> rxBleAdapterWrapperProvider;
   private final Provider<ScanSettingsEmulator> scanSettingsEmulatorProvider;

   public ScanSetupBuilderImplApi23_Factory(
      Provider<RxBleAdapterWrapper> var1,
      Provider<InternalScanResultCreator> var2,
      Provider<ScanSettingsEmulator> var3,
      Provider<AndroidScanObjectsConverter> var4
   ) {
      this.rxBleAdapterWrapperProvider = var1;
      this.internalScanResultCreatorProvider = var2;
      this.scanSettingsEmulatorProvider = var3;
      this.androidScanObjectsConverterProvider = var4;
   }

   public static ScanSetupBuilderImplApi23_Factory create(
      Provider<RxBleAdapterWrapper> var0,
      Provider<InternalScanResultCreator> var1,
      Provider<ScanSettingsEmulator> var2,
      Provider<AndroidScanObjectsConverter> var3
   ) {
      return new ScanSetupBuilderImplApi23_Factory(var0, var1, var2, var3);
   }

   public static ScanSetupBuilderImplApi23 newInstance(
      RxBleAdapterWrapper var0, InternalScanResultCreator var1, ScanSettingsEmulator var2, AndroidScanObjectsConverter var3
   ) {
      return new ScanSetupBuilderImplApi23(var0, var1, var2, var3);
   }

   public ScanSetupBuilderImplApi23 get() {
      return newInstance(
         (RxBleAdapterWrapper)this.rxBleAdapterWrapperProvider.get(),
         (InternalScanResultCreator)this.internalScanResultCreatorProvider.get(),
         (ScanSettingsEmulator)this.scanSettingsEmulatorProvider.get(),
         (AndroidScanObjectsConverter)this.androidScanObjectsConverterProvider.get()
      );
   }
}
