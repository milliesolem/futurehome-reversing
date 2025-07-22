package com.polidea.rxandroidble2.internal.util;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.RxBleAdapterStateObservable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

public final class ClientStateObservable_Factory implements Factory<ClientStateObservable> {
   private final Provider<Observable<RxBleAdapterStateObservable.BleAdapterState>> bleAdapterStateObservableProvider;
   private final Provider<Observable<Boolean>> locationServicesOkObservableProvider;
   private final Provider<LocationServicesStatus> locationServicesStatusProvider;
   private final Provider<RxBleAdapterWrapper> rxBleAdapterWrapperProvider;
   private final Provider<Scheduler> timerSchedulerProvider;

   public ClientStateObservable_Factory(
      Provider<RxBleAdapterWrapper> var1,
      Provider<Observable<RxBleAdapterStateObservable.BleAdapterState>> var2,
      Provider<Observable<Boolean>> var3,
      Provider<LocationServicesStatus> var4,
      Provider<Scheduler> var5
   ) {
      this.rxBleAdapterWrapperProvider = var1;
      this.bleAdapterStateObservableProvider = var2;
      this.locationServicesOkObservableProvider = var3;
      this.locationServicesStatusProvider = var4;
      this.timerSchedulerProvider = var5;
   }

   public static ClientStateObservable_Factory create(
      Provider<RxBleAdapterWrapper> var0,
      Provider<Observable<RxBleAdapterStateObservable.BleAdapterState>> var1,
      Provider<Observable<Boolean>> var2,
      Provider<LocationServicesStatus> var3,
      Provider<Scheduler> var4
   ) {
      return new ClientStateObservable_Factory(var0, var1, var2, var3, var4);
   }

   public static ClientStateObservable newInstance(
      RxBleAdapterWrapper var0,
      Observable<RxBleAdapterStateObservable.BleAdapterState> var1,
      Observable<Boolean> var2,
      LocationServicesStatus var3,
      Scheduler var4
   ) {
      return new ClientStateObservable(var0, var1, var2, var3, var4);
   }

   public ClientStateObservable get() {
      return newInstance(
         (RxBleAdapterWrapper)this.rxBleAdapterWrapperProvider.get(),
         (Observable<RxBleAdapterStateObservable.BleAdapterState>)this.bleAdapterStateObservableProvider.get(),
         (Observable<Boolean>)this.locationServicesOkObservableProvider.get(),
         (LocationServicesStatus)this.locationServicesStatusProvider.get(),
         (Scheduler)this.timerSchedulerProvider.get()
      );
   }
}
