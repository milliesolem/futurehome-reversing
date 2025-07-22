package com.polidea.rxandroidble2.internal.scan;

import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings.Builder;
import androidx.webkit.internal.ApiHelperForN..ExternalSyntheticApiModelOutline4;
import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble2.scan.ScanSettings;
import java.util.ArrayList;
import java.util.List;

public class AndroidScanObjectsConverter {
   private final int deviceSdk;

   @Inject
   public AndroidScanObjectsConverter(@Named("device-sdk") int var1) {
      this.deviceSdk = var1;
   }

   private static void setMarshmallowSettings(ScanSettings var0, Builder var1) {
      ExternalSyntheticApiModelOutline4.m$2(
         ExternalSyntheticApiModelOutline4.m$1(ExternalSyntheticApiModelOutline4.m(var1, var0.getCallbackType()), var0.getMatchMode()), var0.getNumOfMatches()
      );
   }

   private static ScanFilter toNative(com.polidea.rxandroidble2.scan.ScanFilter var0) {
      android.bluetooth.le.ScanFilter.Builder var1 = new android.bluetooth.le.ScanFilter.Builder();
      if (var0.getServiceDataUuid() != null) {
         var1.setServiceData(var0.getServiceDataUuid(), var0.getServiceData(), var0.getServiceDataMask());
      }

      if (var0.getDeviceAddress() != null) {
         var1.setDeviceAddress(var0.getDeviceAddress());
      }

      return var1.setDeviceName(var0.getDeviceName())
         .setManufacturerData(var0.getManufacturerId(), var0.getManufacturerData(), var0.getManufacturerDataMask())
         .setServiceUuid(var0.getServiceUuid(), var0.getServiceUuidMask())
         .build();
   }

   public List<ScanFilter> toNativeFilters(com.polidea.rxandroidble2.scan.ScanFilter... var1) {
      ArrayList var4;
      if (var1 != null && var1.length > 0) {
         ArrayList var5 = new ArrayList(var1.length);
         int var3 = var1.length;
         int var2 = 0;

         while (true) {
            var4 = var5;
            if (var2 >= var3) {
               break;
            }

            var5.add(toNative(var1[var2]));
            var2++;
         }
      } else {
         var4 = null;
      }

      return var4;
   }

   public android.bluetooth.le.ScanSettings toNativeSettings(ScanSettings var1) {
      Builder var2 = new Builder();
      if (this.deviceSdk >= 23) {
         setMarshmallowSettings(var1, var2);
         if (this.deviceSdk >= 26) {
            ExternalSyntheticApiModelOutline4.m(var2, var1.getLegacy());
         }
      }

      return var2.setReportDelay(var1.getReportDelayMillis()).setScanMode(var1.getScanMode()).build();
   }
}
