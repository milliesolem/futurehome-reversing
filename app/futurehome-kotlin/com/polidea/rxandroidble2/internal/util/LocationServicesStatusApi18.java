package com.polidea.rxandroidble2.internal.util;

import bleshadow.javax.inject.Inject;

public class LocationServicesStatusApi18 implements LocationServicesStatus {
   @Inject
   LocationServicesStatusApi18() {
   }

   @Override
   public boolean isLocationPermissionOk() {
      return true;
   }

   @Override
   public boolean isLocationProviderOk() {
      return true;
   }
}
