package com.polidea.rxandroidble2.internal.util;

import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;

public class LocationServicesStatusApi23 implements LocationServicesStatus {
   private final CheckerLocationProvider checkerLocationProvider;
   private final CheckerScanPermission checkerScanPermission;
   private final int deviceSdk;
   private final boolean isAndroidWear;
   private final int targetSdk;

   @Inject
   LocationServicesStatusApi23(
      CheckerLocationProvider var1,
      CheckerScanPermission var2,
      @Named("target-sdk") int var3,
      @Named("device-sdk") int var4,
      @Named("android-wear") boolean var5
   ) {
      this.checkerLocationProvider = var1;
      this.checkerScanPermission = var2;
      this.targetSdk = var3;
      this.deviceSdk = var4;
      this.isAndroidWear = var5;
   }

   private boolean isLocationProviderEnabledRequired() {
      boolean var1;
      if (this.isAndroidWear || this.deviceSdk < 29 && this.targetSdk < 23) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   @Override
   public boolean isLocationPermissionOk() {
      return this.checkerScanPermission.isScanRuntimePermissionGranted();
   }

   @Override
   public boolean isLocationProviderOk() {
      boolean var1;
      if (this.isLocationProviderEnabledRequired() && !this.checkerLocationProvider.isLocationProviderEnabled()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }
}
