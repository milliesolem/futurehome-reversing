package com.polidea.rxandroidble2.internal.connection;

import bleshadow.dagger.internal.Factory;

public final class NativeCallbackDispatcher_Factory implements Factory<NativeCallbackDispatcher> {
   public static NativeCallbackDispatcher_Factory create() {
      return NativeCallbackDispatcher_Factory.InstanceHolder.INSTANCE;
   }

   public static NativeCallbackDispatcher newInstance() {
      return new NativeCallbackDispatcher();
   }

   public NativeCallbackDispatcher get() {
      return newInstance();
   }

   private static final class InstanceHolder {
      private static final NativeCallbackDispatcher_Factory INSTANCE = new NativeCallbackDispatcher_Factory();
   }
}
