package com.polidea.rxandroidble2.internal.util;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;

public final class LocationServicesStatusApi23_Factory implements Factory<LocationServicesStatusApi23> {
   private final Provider<CheckerLocationProvider> checkerLocationProvider;
   private final Provider<CheckerScanPermission> checkerScanPermissionProvider;
   private final Provider<Integer> deviceSdkProvider;
   private final Provider<Boolean> isAndroidWearProvider;
   private final Provider<Integer> targetSdkProvider;

   public LocationServicesStatusApi23_Factory(
      Provider<CheckerLocationProvider> var1, Provider<CheckerScanPermission> var2, Provider<Integer> var3, Provider<Integer> var4, Provider<Boolean> var5
   ) {
      this.checkerLocationProvider = var1;
      this.checkerScanPermissionProvider = var2;
      this.targetSdkProvider = var3;
      this.deviceSdkProvider = var4;
      this.isAndroidWearProvider = var5;
   }

   public static LocationServicesStatusApi23_Factory create(
      Provider<CheckerLocationProvider> var0, Provider<CheckerScanPermission> var1, Provider<Integer> var2, Provider<Integer> var3, Provider<Boolean> var4
   ) {
      return new LocationServicesStatusApi23_Factory(var0, var1, var2, var3, var4);
   }

   public static LocationServicesStatusApi23 newInstance(CheckerLocationProvider var0, CheckerScanPermission var1, int var2, int var3, boolean var4) {
      return new LocationServicesStatusApi23(var0, var1, var2, var3, var4);
   }

   public LocationServicesStatusApi23 get() {
      return newInstance(
         (CheckerLocationProvider)this.checkerLocationProvider.get(),
         (CheckerScanPermission)this.checkerScanPermissionProvider.get(),
         (Integer)this.targetSdkProvider.get(),
         (Integer)this.deviceSdkProvider.get(),
         (Boolean)this.isAndroidWearProvider.get()
      );
   }
}
