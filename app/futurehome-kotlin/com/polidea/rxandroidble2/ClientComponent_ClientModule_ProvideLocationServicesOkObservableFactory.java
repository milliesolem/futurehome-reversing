package com.polidea.rxandroidble2;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.util.LocationServicesOkObservableApi23Factory;
import io.reactivex.Observable;

public final class ClientComponent_ClientModule_ProvideLocationServicesOkObservableFactory implements Factory<Observable<Boolean>> {
   private final Provider<Integer> deviceSdkProvider;
   private final Provider<LocationServicesOkObservableApi23Factory> locationServicesOkObservableApi23FactoryProvider;

   public ClientComponent_ClientModule_ProvideLocationServicesOkObservableFactory(
      Provider<Integer> var1, Provider<LocationServicesOkObservableApi23Factory> var2
   ) {
      this.deviceSdkProvider = var1;
      this.locationServicesOkObservableApi23FactoryProvider = var2;
   }

   public static ClientComponent_ClientModule_ProvideLocationServicesOkObservableFactory create(
      Provider<Integer> var0, Provider<LocationServicesOkObservableApi23Factory> var1
   ) {
      return new ClientComponent_ClientModule_ProvideLocationServicesOkObservableFactory(var0, var1);
   }

   public static Observable<Boolean> provideLocationServicesOkObservable(int var0, LocationServicesOkObservableApi23Factory var1) {
      return (Observable<Boolean>)Preconditions.checkNotNullFromProvides(ClientComponent.ClientModule.provideLocationServicesOkObservable(var0, var1));
   }

   public Observable<Boolean> get() {
      return provideLocationServicesOkObservable(
         (Integer)this.deviceSdkProvider.get(), (LocationServicesOkObservableApi23Factory)this.locationServicesOkObservableApi23FactoryProvider.get()
      );
   }
}
