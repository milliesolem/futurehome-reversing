package com.polidea.rxandroidble2.helpers;

import android.content.Context;
import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble2.DaggerClientComponent;
import io.reactivex.Observable;
import io.reactivex.Observer;

public class LocationServicesOkObservable extends Observable<Boolean> {
   private final Observable<Boolean> locationServicesOkObsImpl;

   @Inject
   LocationServicesOkObservable(@Named("location-ok-boolean-observable") Observable<Boolean> var1) {
      this.locationServicesOkObsImpl = var1;
   }

   public static LocationServicesOkObservable createInstance(Context var0) {
      return DaggerClientComponent.builder().applicationContext(var0.getApplicationContext()).build().locationServicesOkObservable();
   }

   @Override
   protected void subscribeActual(Observer<? super Boolean> var1) {
      this.locationServicesOkObsImpl.subscribe(var1);
   }
}
