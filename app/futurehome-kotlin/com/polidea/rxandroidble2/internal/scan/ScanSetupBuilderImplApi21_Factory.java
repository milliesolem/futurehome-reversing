package com.polidea.rxandroidble2.internal.scan;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.util.RxBleAdapterWrapper;

public final class ScanSetupBuilderImplApi21_Factory implements Factory<ScanSetupBuilderImplApi21> {
   private final Provider<AndroidScanObjectsConverter> androidScanObjectsConverterProvider;
   private final Provider<InternalScanResultCreator> internalScanResultCreatorProvider;
   private final Provider<RxBleAdapterWrapper> rxBleAdapterWrapperProvider;
   private final Provider<ScanSettingsEmulator> scanSettingsEmulatorProvider;

   public ScanSetupBuilderImplApi21_Factory(
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

   public static ScanSetupBuilderImplApi21_Factory create(
      Provider<RxBleAdapterWrapper> var0,
      Provider<InternalScanResultCreator> var1,
      Provider<ScanSettingsEmulator> var2,
      Provider<AndroidScanObjectsConverter> var3
   ) {
      return new ScanSetupBuilderImplApi21_Factory(var0, var1, var2, var3);
   }

   public static ScanSetupBuilderImplApi21 newInstance(
      RxBleAdapterWrapper var0, InternalScanResultCreator var1, ScanSettingsEmulator var2, AndroidScanObjectsConverter var3
   ) {
      return new ScanSetupBuilderImplApi21(var0, var1, var2, var3);
   }

   public ScanSetupBuilderImplApi21 get() {
      return newInstance(
         (RxBleAdapterWrapper)this.rxBleAdapterWrapperProvider.get(),
         (InternalScanResultCreator)this.internalScanResultCreatorProvider.get(),
         (ScanSettingsEmulator)this.scanSettingsEmulatorProvider.get(),
         (AndroidScanObjectsConverter)this.androidScanObjectsConverterProvider.get()
      );
   }
}
