package com.polidea.rxandroidble2.internal.util;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;

public final class LocationServicesStatusApi31_Factory implements Factory<LocationServicesStatusApi31> {
   private final Provider<CheckerLocationProvider> checkerLocationProvider;
   private final Provider<CheckerScanPermission> checkerScanPermissionProvider;
   private final Provider<Boolean> isAndroidWearProvider;
   private final Provider<Boolean> isNearbyPermissionNeverForLocProvider;

   public LocationServicesStatusApi31_Factory(
      Provider<CheckerLocationProvider> var1, Provider<CheckerScanPermission> var2, Provider<Boolean> var3, Provider<Boolean> var4
   ) {
      this.checkerLocationProvider = var1;
      this.checkerScanPermissionProvider = var2;
      this.isAndroidWearProvider = var3;
      this.isNearbyPermissionNeverForLocProvider = var4;
   }

   public static LocationServicesStatusApi31_Factory create(
      Provider<CheckerLocationProvider> var0, Provider<CheckerScanPermission> var1, Provider<Boolean> var2, Provider<Boolean> var3
   ) {
      return new LocationServicesStatusApi31_Factory(var0, var1, var2, var3);
   }

   public static LocationServicesStatusApi31 newInstance(CheckerLocationProvider var0, CheckerScanPermission var1, boolean var2, boolean var3) {
      return new LocationServicesStatusApi31(var0, var1, var2, var3);
   }

   public LocationServicesStatusApi31 get() {
      return newInstance(
         (CheckerLocationProvider)this.checkerLocationProvider.get(),
         (CheckerScanPermission)this.checkerScanPermissionProvider.get(),
         (Boolean)this.isAndroidWearProvider.get(),
         (Boolean)this.isNearbyPermissionNeverForLocProvider.get()
      );
   }
}
