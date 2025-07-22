package com.polidea.rxandroidble2.internal.scan;

import bleshadow.dagger.internal.Factory;

public final class IsConnectableCheckerApi26_Factory implements Factory<IsConnectableCheckerApi26> {
   public static IsConnectableCheckerApi26_Factory create() {
      return IsConnectableCheckerApi26_Factory.InstanceHolder.INSTANCE;
   }

   public static IsConnectableCheckerApi26 newInstance() {
      return new IsConnectableCheckerApi26();
   }

   public IsConnectableCheckerApi26 get() {
      return newInstance();
   }

   private static final class InstanceHolder {
      private static final IsConnectableCheckerApi26_Factory INSTANCE = new IsConnectableCheckerApi26_Factory();
   }
}
