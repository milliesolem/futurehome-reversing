package com.polidea.rxandroidble2.internal.scan;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;

public final class AndroidScanObjectsConverter_Factory implements Factory<AndroidScanObjectsConverter> {
   private final Provider<Integer> deviceSdkProvider;

   public AndroidScanObjectsConverter_Factory(Provider<Integer> var1) {
      this.deviceSdkProvider = var1;
   }

   public static AndroidScanObjectsConverter_Factory create(Provider<Integer> var0) {
      return new AndroidScanObjectsConverter_Factory(var0);
   }

   public static AndroidScanObjectsConverter newInstance(int var0) {
      return new AndroidScanObjectsConverter(var0);
   }

   public AndroidScanObjectsConverter get() {
      return newInstance((Integer)this.deviceSdkProvider.get());
   }
}
