package dev.fluttercommunity.plus.connectivity;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import com.baseflow.geocoding.Geocoding..ExternalSyntheticApiModelOutline0;

public class Connectivity {
   static final String CONNECTIVITY_BLUETOOTH = "bluetooth";
   static final String CONNECTIVITY_ETHERNET = "ethernet";
   static final String CONNECTIVITY_MOBILE = "mobile";
   static final String CONNECTIVITY_NONE = "none";
   static final String CONNECTIVITY_VPN = "vpn";
   static final String CONNECTIVITY_WIFI = "wifi";
   private final ConnectivityManager connectivityManager;

   public Connectivity(ConnectivityManager var1) {
      this.connectivityManager = var1;
   }

   private String getNetworkTypeLegacy() {
      NetworkInfo var2 = this.connectivityManager.getActiveNetworkInfo();
      if (var2 != null && var2.isConnected()) {
         int var1 = var2.getType();
         if (var1 != 0) {
            if (var1 == 1) {
               return "wifi";
            }

            if (var1 != 4 && var1 != 5) {
               if (var1 != 6) {
                  if (var1 != 7) {
                     if (var1 != 9) {
                        if (var1 != 17) {
                           return "none";
                        }

                        return "vpn";
                     }

                     return "ethernet";
                  }

                  return "bluetooth";
               }

               return "wifi";
            }
         }

         return "mobile";
      } else {
         return "none";
      }
   }

   public ConnectivityManager getConnectivityManager() {
      return this.connectivityManager;
   }

   String getNetworkType() {
      if (VERSION.SDK_INT >= 23) {
         Network var1 = ExternalSyntheticApiModelOutline0.m(this.connectivityManager);
         NetworkCapabilities var2 = this.connectivityManager.getNetworkCapabilities(var1);
         if (var2 == null) {
            return "none";
         }

         if (var2.hasTransport(1)) {
            return "wifi";
         }

         if (var2.hasTransport(3)) {
            return "ethernet";
         }

         if (var2.hasTransport(4)) {
            return "vpn";
         }

         if (var2.hasTransport(0)) {
            return "mobile";
         }

         if (var2.hasTransport(2)) {
            return "bluetooth";
         }
      }

      return this.getNetworkTypeLegacy();
   }
}
