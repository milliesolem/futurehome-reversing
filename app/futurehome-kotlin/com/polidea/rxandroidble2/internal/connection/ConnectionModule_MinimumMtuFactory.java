package com.polidea.rxandroidble2.internal.connection;

import bleshadow.dagger.internal.Factory;

public final class ConnectionModule_MinimumMtuFactory implements Factory<Integer> {
   public static ConnectionModule_MinimumMtuFactory create() {
      return ConnectionModule_MinimumMtuFactory.InstanceHolder.INSTANCE;
   }

   public static int minimumMtu() {
      return ConnectionModule.minimumMtu();
   }

   public Integer get() {
      return minimumMtu();
   }

   private static final class InstanceHolder {
      private static final ConnectionModule_MinimumMtuFactory INSTANCE = new ConnectionModule_MinimumMtuFactory();
   }
}
