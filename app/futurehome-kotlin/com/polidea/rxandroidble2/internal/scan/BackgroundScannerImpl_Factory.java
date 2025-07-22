package com.polidea.rxandroidble2.internal.scan;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.util.RxBleAdapterWrapper;

public final class BackgroundScannerImpl_Factory implements Factory<BackgroundScannerImpl> {
   private final Provider<InternalScanResultCreator> internalScanResultCreatorProvider;
   private final Provider<InternalToExternalScanResultConverter> internalToExternalScanResultConverterProvider;
   private final Provider<RxBleAdapterWrapper> rxBleAdapterWrapperProvider;
   private final Provider<AndroidScanObjectsConverter> scanObjectsConverterProvider;

   public BackgroundScannerImpl_Factory(
      Provider<RxBleAdapterWrapper> var1,
      Provider<AndroidScanObjectsConverter> var2,
      Provider<InternalScanResultCreator> var3,
      Provider<InternalToExternalScanResultConverter> var4
   ) {
      this.rxBleAdapterWrapperProvider = var1;
      this.scanObjectsConverterProvider = var2;
      this.internalScanResultCreatorProvider = var3;
      this.internalToExternalScanResultConverterProvider = var4;
   }

   public static BackgroundScannerImpl_Factory create(
      Provider<RxBleAdapterWrapper> var0,
      Provider<AndroidScanObjectsConverter> var1,
      Provider<InternalScanResultCreator> var2,
      Provider<InternalToExternalScanResultConverter> var3
   ) {
      return new BackgroundScannerImpl_Factory(var0, var1, var2, var3);
   }

   public static BackgroundScannerImpl newInstance(
      RxBleAdapterWrapper var0, AndroidScanObjectsConverter var1, InternalScanResultCreator var2, InternalToExternalScanResultConverter var3
   ) {
      return new BackgroundScannerImpl(var0, var1, var2, var3);
   }

   public BackgroundScannerImpl get() {
      return newInstance(
         (RxBleAdapterWrapper)this.rxBleAdapterWrapperProvider.get(),
         (AndroidScanObjectsConverter)this.scanObjectsConverterProvider.get(),
         (InternalScanResultCreator)this.internalScanResultCreatorProvider.get(),
         (InternalToExternalScanResultConverter)this.internalToExternalScanResultConverterProvider.get()
      );
   }
}
