package com.polidea.rxandroidble2;

import android.content.Context;
import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;

public final class RxBleAdapterStateObservable_Factory implements Factory<RxBleAdapterStateObservable> {
   private final Provider<Context> contextProvider;

   public RxBleAdapterStateObservable_Factory(Provider<Context> var1) {
      this.contextProvider = var1;
   }

   public static RxBleAdapterStateObservable_Factory create(Provider<Context> var0) {
      return new RxBleAdapterStateObservable_Factory(var0);
   }

   public static RxBleAdapterStateObservable newInstance(Context var0) {
      return new RxBleAdapterStateObservable(var0);
   }

   public RxBleAdapterStateObservable get() {
      return newInstance((Context)this.contextProvider.get());
   }
}
