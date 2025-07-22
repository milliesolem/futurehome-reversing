package com.polidea.rxandroidble2.helpers;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import io.reactivex.Observable;

public final class LocationServicesOkObservable_Factory implements Factory<LocationServicesOkObservable> {
   private final Provider<Observable<Boolean>> locationServicesOkObsImplProvider;

   public LocationServicesOkObservable_Factory(Provider<Observable<Boolean>> var1) {
      this.locationServicesOkObsImplProvider = var1;
   }

   public static LocationServicesOkObservable_Factory create(Provider<Observable<Boolean>> var0) {
      return new LocationServicesOkObservable_Factory(var0);
   }

   public static LocationServicesOkObservable newInstance(Observable<Boolean> var0) {
      return new LocationServicesOkObservable(var0);
   }

   public LocationServicesOkObservable get() {
      return newInstance((Observable<Boolean>)this.locationServicesOkObsImplProvider.get());
   }
}
