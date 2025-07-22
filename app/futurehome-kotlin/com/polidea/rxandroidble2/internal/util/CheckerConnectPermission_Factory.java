package com.polidea.rxandroidble2.internal.util;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;

public final class CheckerConnectPermission_Factory implements Factory<CheckerConnectPermission> {
   private final Provider<CheckerPermission> checkerPermissionProvider;
   private final Provider<String[][]> connectPermissionsProvider;

   public CheckerConnectPermission_Factory(Provider<CheckerPermission> var1, Provider<String[][]> var2) {
      this.checkerPermissionProvider = var1;
      this.connectPermissionsProvider = var2;
   }

   public static CheckerConnectPermission_Factory create(Provider<CheckerPermission> var0, Provider<String[][]> var1) {
      return new CheckerConnectPermission_Factory(var0, var1);
   }

   public static CheckerConnectPermission newInstance(CheckerPermission var0, String[][] var1) {
      return new CheckerConnectPermission(var0, var1);
   }

   public CheckerConnectPermission get() {
      return newInstance((CheckerPermission)this.checkerPermissionProvider.get(), (String[][])this.connectPermissionsProvider.get());
   }
}
