package com.polidea.rxandroidble2.internal.util;

import android.content.Context;
import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;

public final class BleConnectionCompat_Factory implements Factory<BleConnectionCompat> {
   private final Provider<Context> contextProvider;

   public BleConnectionCompat_Factory(Provider<Context> var1) {
      this.contextProvider = var1;
   }

   public static BleConnectionCompat_Factory create(Provider<Context> var0) {
      return new BleConnectionCompat_Factory(var0);
   }

   public static BleConnectionCompat newInstance(Context var0) {
      return new BleConnectionCompat(var0);
   }

   public BleConnectionCompat get() {
      return newInstance((Context)this.contextProvider.get());
   }
}
