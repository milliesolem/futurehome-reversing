package com.polidea.rxandroidble2.internal.util;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;

public final class CheckerScanPermission_Factory implements Factory<CheckerScanPermission> {
   private final Provider<CheckerPermission> checkerPermissionProvider;
   private final Provider<String[][]> scanPermissionsProvider;

   public CheckerScanPermission_Factory(Provider<CheckerPermission> var1, Provider<String[][]> var2) {
      this.checkerPermissionProvider = var1;
      this.scanPermissionsProvider = var2;
   }

   public static CheckerScanPermission_Factory create(Provider<CheckerPermission> var0, Provider<String[][]> var1) {
      return new CheckerScanPermission_Factory(var0, var1);
   }

   public static CheckerScanPermission newInstance(CheckerPermission var0, String[][] var1) {
      return new CheckerScanPermission(var0, var1);
   }

   public CheckerScanPermission get() {
      return newInstance((CheckerPermission)this.checkerPermissionProvider.get(), (String[][])this.scanPermissionsProvider.get());
   }
}
