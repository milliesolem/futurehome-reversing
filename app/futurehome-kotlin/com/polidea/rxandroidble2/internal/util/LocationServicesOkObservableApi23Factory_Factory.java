package com.polidea.rxandroidble2.internal.util;

import android.content.Context;
import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;

public final class LocationServicesOkObservableApi23Factory_Factory implements Factory<LocationServicesOkObservableApi23Factory> {
   private final Provider<Context> contextProvider;
   private final Provider<LocationServicesStatus> locationServicesStatusProvider;

   public LocationServicesOkObservableApi23Factory_Factory(Provider<Context> var1, Provider<LocationServicesStatus> var2) {
      this.contextProvider = var1;
      this.locationServicesStatusProvider = var2;
   }

   public static LocationServicesOkObservableApi23Factory_Factory create(Provider<Context> var0, Provider<LocationServicesStatus> var1) {
      return new LocationServicesOkObservableApi23Factory_Factory(var0, var1);
   }

   public static LocationServicesOkObservableApi23Factory newInstance(Context var0, LocationServicesStatus var1) {
      return new LocationServicesOkObservableApi23Factory(var0, var1);
   }

   public LocationServicesOkObservableApi23Factory get() {
      return newInstance((Context)this.contextProvider.get(), (LocationServicesStatus)this.locationServicesStatusProvider.get());
   }
}
