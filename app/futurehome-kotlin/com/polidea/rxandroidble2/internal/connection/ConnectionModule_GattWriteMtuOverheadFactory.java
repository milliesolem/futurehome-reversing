package com.polidea.rxandroidble2.internal.connection;

import bleshadow.dagger.internal.Factory;

public final class ConnectionModule_GattWriteMtuOverheadFactory implements Factory<Integer> {
   public static ConnectionModule_GattWriteMtuOverheadFactory create() {
      return ConnectionModule_GattWriteMtuOverheadFactory.InstanceHolder.INSTANCE;
   }

   public static int gattWriteMtuOverhead() {
      return ConnectionModule.gattWriteMtuOverhead();
   }

   public Integer get() {
      return gattWriteMtuOverhead();
   }

   private static final class InstanceHolder {
      private static final ConnectionModule_GattWriteMtuOverheadFactory INSTANCE = new ConnectionModule_GattWriteMtuOverheadFactory();
   }
}
