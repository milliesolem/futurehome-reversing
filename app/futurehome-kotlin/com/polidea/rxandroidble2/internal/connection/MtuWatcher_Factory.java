package com.polidea.rxandroidble2.internal.connection;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;

public final class MtuWatcher_Factory implements Factory<MtuWatcher> {
   private final Provider<Integer> initialValueProvider;
   private final Provider<RxBleGattCallback> rxBleGattCallbackProvider;

   public MtuWatcher_Factory(Provider<RxBleGattCallback> var1, Provider<Integer> var2) {
      this.rxBleGattCallbackProvider = var1;
      this.initialValueProvider = var2;
   }

   public static MtuWatcher_Factory create(Provider<RxBleGattCallback> var0, Provider<Integer> var1) {
      return new MtuWatcher_Factory(var0, var1);
   }

   public static MtuWatcher newInstance(RxBleGattCallback var0, int var1) {
      return new MtuWatcher(var0, var1);
   }

   public MtuWatcher get() {
      return newInstance((RxBleGattCallback)this.rxBleGattCallbackProvider.get(), (Integer)this.initialValueProvider.get());
   }
}
