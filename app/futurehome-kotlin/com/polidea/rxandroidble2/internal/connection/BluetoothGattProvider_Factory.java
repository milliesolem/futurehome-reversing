package com.polidea.rxandroidble2.internal.connection;

import bleshadow.dagger.internal.Factory;

public final class BluetoothGattProvider_Factory implements Factory<BluetoothGattProvider> {
   public static BluetoothGattProvider_Factory create() {
      return BluetoothGattProvider_Factory.InstanceHolder.INSTANCE;
   }

   public static BluetoothGattProvider newInstance() {
      return new BluetoothGattProvider();
   }

   public BluetoothGattProvider get() {
      return newInstance();
   }

   private static final class InstanceHolder {
      private static final BluetoothGattProvider_Factory INSTANCE = new BluetoothGattProvider_Factory();
   }
}
