package com.polidea.rxandroidble2.internal.util;

import bleshadow.dagger.internal.Factory;

public final class LocationServicesStatusApi18_Factory implements Factory<LocationServicesStatusApi18> {
   public static LocationServicesStatusApi18_Factory create() {
      return LocationServicesStatusApi18_Factory.InstanceHolder.INSTANCE;
   }

   public static LocationServicesStatusApi18 newInstance() {
      return new LocationServicesStatusApi18();
   }

   public LocationServicesStatusApi18 get() {
      return newInstance();
   }

   private static final class InstanceHolder {
      private static final LocationServicesStatusApi18_Factory INSTANCE = new LocationServicesStatusApi18_Factory();
   }
}
