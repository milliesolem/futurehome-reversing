package com.polidea.rxandroidble2.internal.util;

import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;

public class LocationServicesStatusApi31 implements LocationServicesStatus {
   private final CheckerLocationProvider checkerLocationProvider;
   private final CheckerScanPermission checkerScanPermission;
   private final boolean isAndroidWear;
   private final boolean isNearbyPermissionNeverForLoc;

   @Inject
   LocationServicesStatusApi31(
      CheckerLocationProvider var1,
      CheckerScanPermission var2,
      @Named("android-wear") boolean var3,
      @Named("nearby-permission-never-for-location") boolean var4
   ) {
      this.checkerLocationProvider = var1;
      this.checkerScanPermission = var2;
      this.isAndroidWear = var3;
      this.isNearbyPermissionNeverForLoc = var4;
   }

   private boolean isLocationProviderEnabledRequired() {
      return this.isAndroidWear ? false : this.isNearbyPermissionNeverForLoc ^ true;
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
