package com.polidea.rxandroidble2.internal.util;

import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble2.ClientScope;

@ClientScope
public class CheckerScanPermission {
   private final CheckerPermission checkerPermission;
   private final String[][] scanPermissions;

   @Inject
   CheckerScanPermission(CheckerPermission var1, @Named("scan-permissions") String[][] var2) {
      this.checkerPermission = var1;
      this.scanPermissions = var2;
   }

   public String[] getRecommendedScanRuntimePermissions() {
      String[][] var6 = this.scanPermissions;
      int var3 = var6.length;
      int var2 = 0;

      int var1;
      for (var1 = 0; var2 < var3; var2++) {
         var1 += var6[var2].length;
      }

      String[] var7 = new String[var1];
      var6 = this.scanPermissions;
      int var4 = var6.length;
      var1 = 0;

      for (int var10 = 0; var1 < var4; var1++) {
         String[] var8 = var6[var1];
         int var5 = var8.length;

         for (int var11 = 0; var11 < var5; var10++) {
            var7[var10] = var8[var11];
            var11++;
         }
      }

      return var7;
   }

   public boolean isScanRuntimePermissionGranted() {
      String[][] var5 = this.scanPermissions;
      int var2 = var5.length;
      boolean var3 = true;

      for (int var1 = 0; var1 < var2; var1++) {
         String[] var4 = var5[var1];
         var3 &= this.checkerPermission.isAnyPermissionGranted(var4);
      }

      return var3;
   }
}
