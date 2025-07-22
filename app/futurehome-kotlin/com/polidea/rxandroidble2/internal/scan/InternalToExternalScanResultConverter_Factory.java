package com.polidea.rxandroidble2.internal.scan;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.RxBleDeviceProvider;

public final class InternalToExternalScanResultConverter_Factory implements Factory<InternalToExternalScanResultConverter> {
   private final Provider<RxBleDeviceProvider> deviceProvider;

   public InternalToExternalScanResultConverter_Factory(Provider<RxBleDeviceProvider> var1) {
      this.deviceProvider = var1;
   }

   public static InternalToExternalScanResultConverter_Factory create(Provider<RxBleDeviceProvider> var0) {
      return new InternalToExternalScanResultConverter_Factory(var0);
   }

   public static InternalToExternalScanResultConverter newInstance(RxBleDeviceProvider var0) {
      return new InternalToExternalScanResultConverter(var0);
   }

   public InternalToExternalScanResultConverter get() {
      return newInstance((RxBleDeviceProvider)this.deviceProvider.get());
   }
}
