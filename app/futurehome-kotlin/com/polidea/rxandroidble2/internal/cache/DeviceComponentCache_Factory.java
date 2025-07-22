package com.polidea.rxandroidble2.internal.cache;

import bleshadow.dagger.internal.Factory;

public final class DeviceComponentCache_Factory implements Factory<DeviceComponentCache> {
   public static DeviceComponentCache_Factory create() {
      return DeviceComponentCache_Factory.InstanceHolder.INSTANCE;
   }

   public static DeviceComponentCache newInstance() {
      return new DeviceComponentCache();
   }

   public DeviceComponentCache get() {
      return newInstance();
   }

   private static final class InstanceHolder {
      private static final DeviceComponentCache_Factory INSTANCE = new DeviceComponentCache_Factory();
   }
}
