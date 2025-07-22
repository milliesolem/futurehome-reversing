package com.polidea.rxandroidble2.internal.scan;

import java.util.Arrays;

public class EmulatedScanFilterMatcher {
   private final boolean isEmpty;
   private final ScanFilterInterface[] scanFilters;

   public EmulatedScanFilterMatcher(ScanFilterInterface... var1) {
      boolean var4;
      label23: {
         super();
         this.scanFilters = var1;
         if (var1 != null && var1.length != 0) {
            int var3 = var1.length;
            var4 = false;

            for (int var2 = 0; var2 < var3; var2++) {
               if (!var1[var2].isAllFieldsEmpty()) {
                  break label23;
               }
            }
         }

         var4 = true;
      }

      this.isEmpty = var4;
   }

   public boolean isEmpty() {
      return this.isEmpty;
   }

   public boolean matches(RxBleInternalScanResult var1) {
      ScanFilterInterface[] var4 = this.scanFilters;
      if (var4 != null && var4.length != 0) {
         int var3 = var4.length;

         for (int var2 = 0; var2 < var3; var2++) {
            if (var4[var2].matches(var1)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("emulatedFilters=");
      var1.append(Arrays.toString((Object[])this.scanFilters));
      return var1.toString();
   }
}
