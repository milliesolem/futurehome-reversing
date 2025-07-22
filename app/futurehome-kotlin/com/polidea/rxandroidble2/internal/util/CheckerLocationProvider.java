package com.polidea.rxandroidble2.internal.util;

import android.content.ContentResolver;
import android.location.LocationManager;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.provider.Settings.SettingNotFoundException;
import androidx.webkit.internal.ApiHelperForN..ExternalSyntheticApiModelOutline4;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.internal.RxBleLog;

public class CheckerLocationProvider {
   private final ContentResolver contentResolver;
   private final LocationManager locationManager;

   @Inject
   CheckerLocationProvider(ContentResolver var1, LocationManager var2) {
      this.contentResolver = var1;
      this.locationManager = var2;
   }

   private boolean isLocationProviderEnabledBelowApi19() {
      boolean var1;
      if (!this.locationManager.isProviderEnabled("network") && !this.locationManager.isProviderEnabled("gps")) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   private boolean isLocationProviderEnabledBelowApi28() {
      boolean var2 = false;

      int var1;
      try {
         var1 = Secure.getInt(this.contentResolver, "location_mode");
      } catch (SettingNotFoundException var4) {
         RxBleLog.w(var4, "Could not use LOCATION_MODE check. Falling back to a legacy/heuristic function.");
         return this.isLocationProviderEnabledBelowApi19();
      }

      if (var1 != 0) {
         var2 = true;
      }

      return var2;
   }

   public boolean isLocationProviderEnabled() {
      return VERSION.SDK_INT >= 28 ? ExternalSyntheticApiModelOutline4.m(this.locationManager) : this.isLocationProviderEnabledBelowApi28();
   }
}
