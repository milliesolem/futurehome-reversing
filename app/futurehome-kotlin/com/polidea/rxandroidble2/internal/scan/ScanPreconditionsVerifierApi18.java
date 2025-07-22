package com.polidea.rxandroidble2.internal.scan;

import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.exceptions.BleScanException;
import com.polidea.rxandroidble2.internal.util.LocationServicesStatus;
import com.polidea.rxandroidble2.internal.util.RxBleAdapterWrapper;

public class ScanPreconditionsVerifierApi18 implements ScanPreconditionsVerifier {
   final LocationServicesStatus locationServicesStatus;
   final RxBleAdapterWrapper rxBleAdapterWrapper;

   @Inject
   public ScanPreconditionsVerifierApi18(RxBleAdapterWrapper var1, LocationServicesStatus var2) {
      this.rxBleAdapterWrapper = var1;
      this.locationServicesStatus = var2;
   }

   @Override
   public void verify(boolean var1) {
      if (this.rxBleAdapterWrapper.hasBluetoothAdapter()) {
         if (this.rxBleAdapterWrapper.isBluetoothEnabled()) {
            if (this.locationServicesStatus.isLocationPermissionOk()) {
               if (var1 && !this.locationServicesStatus.isLocationProviderOk()) {
                  throw new BleScanException(4);
               }
            } else {
               throw new BleScanException(3);
            }
         } else {
            throw new BleScanException(1);
         }
      } else {
         throw new BleScanException(2);
      }
   }
}
