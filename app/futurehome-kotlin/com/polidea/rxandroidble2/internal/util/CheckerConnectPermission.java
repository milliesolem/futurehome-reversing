package com.polidea.rxandroidble2.internal.util;

import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble2.ClientScope;

@ClientScope
public class CheckerConnectPermission {
   private final CheckerPermission checkerPermission;
   private final String[][] connectPermissions;

   @Inject
   CheckerConnectPermission(CheckerPermission var1, @Named("connect-permissions") String[][] var2) {
      this.checkerPermission = var1;
      this.connectPermissions = var2;
   }

   public String[] getRecommendedConnectRuntimePermissions() {
      String[][] var6 = this.connectPermissions;
      int var3 = var6.length;
      int var1 = 0;

      int var2;
      for (var2 = 0; var1 < var3; var1++) {
         var2 += var6[var1].length;
      }

      String[] var7 = new String[var2];
      String[][] var8 = this.connectPermissions;
      int var4 = var8.length;
      var1 = 0;

      for (int var10 = 0; var1 < var4; var1++) {
         String[] var12 = var8[var1];
         int var5 = var12.length;

         for (int var11 = 0; var11 < var5; var10++) {
            var7[var10] = var12[var11];
            var11++;
         }
      }

      return var7;
   }

   public boolean isConnectRuntimePermissionGranted() {
      String[][] var5 = this.connectPermissions;
      int var2 = var5.length;
      boolean var3 = true;

      for (int var1 = 0; var1 < var2; var1++) {
         String[] var4 = var5[var1];
         var3 &= this.checkerPermission.isAnyPermissionGranted(var4);
      }

      return var3;
   }
}
