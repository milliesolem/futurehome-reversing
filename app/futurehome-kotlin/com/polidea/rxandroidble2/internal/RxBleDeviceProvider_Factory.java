package com.polidea.rxandroidble2.internal;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.cache.DeviceComponentCache;

public final class RxBleDeviceProvider_Factory implements Factory<RxBleDeviceProvider> {
   private final Provider<DeviceComponent.Builder> deviceComponentBuilderProvider;
   private final Provider<DeviceComponentCache> deviceComponentCacheProvider;

   public RxBleDeviceProvider_Factory(Provider<DeviceComponentCache> var1, Provider<DeviceComponent.Builder> var2) {
      this.deviceComponentCacheProvider = var1;
      this.deviceComponentBuilderProvider = var2;
   }

   public static RxBleDeviceProvider_Factory create(Provider<DeviceComponentCache> var0, Provider<DeviceComponent.Builder> var1) {
      return new RxBleDeviceProvider_Factory(var0, var1);
   }

   public static RxBleDeviceProvider newInstance(DeviceComponentCache var0, Provider<DeviceComponent.Builder> var1) {
      return new RxBleDeviceProvider(var0, var1);
   }

   public RxBleDeviceProvider get() {
      return newInstance((DeviceComponentCache)this.deviceComponentCacheProvider.get(), this.deviceComponentBuilderProvider);
   }
}
