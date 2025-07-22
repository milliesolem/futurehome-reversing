package com.polidea.rxandroidble2;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.util.LocationServicesStatus;
import com.polidea.rxandroidble2.internal.util.LocationServicesStatusApi18;
import com.polidea.rxandroidble2.internal.util.LocationServicesStatusApi23;
import com.polidea.rxandroidble2.internal.util.LocationServicesStatusApi31;

public final class ClientComponent_ClientModule_ProvideLocationServicesStatusFactory implements Factory<LocationServicesStatus> {
   private final Provider<Integer> deviceSdkProvider;
   private final Provider<LocationServicesStatusApi18> locationServicesStatusApi18Provider;
   private final Provider<LocationServicesStatusApi23> locationServicesStatusApi23Provider;
   private final Provider<LocationServicesStatusApi31> locationServicesStatusApi31Provider;

   public ClientComponent_ClientModule_ProvideLocationServicesStatusFactory(
      Provider<Integer> var1,
      Provider<LocationServicesStatusApi18> var2,
      Provider<LocationServicesStatusApi23> var3,
      Provider<LocationServicesStatusApi31> var4
   ) {
      this.deviceSdkProvider = var1;
      this.locationServicesStatusApi18Provider = var2;
      this.locationServicesStatusApi23Provider = var3;
      this.locationServicesStatusApi31Provider = var4;
   }

   public static ClientComponent_ClientModule_ProvideLocationServicesStatusFactory create(
      Provider<Integer> var0,
      Provider<LocationServicesStatusApi18> var1,
      Provider<LocationServicesStatusApi23> var2,
      Provider<LocationServicesStatusApi31> var3
   ) {
      return new ClientComponent_ClientModule_ProvideLocationServicesStatusFactory(var0, var1, var2, var3);
   }

   public static LocationServicesStatus provideLocationServicesStatus(
      int var0, Provider<LocationServicesStatusApi18> var1, Provider<LocationServicesStatusApi23> var2, Provider<LocationServicesStatusApi31> var3
   ) {
      return (LocationServicesStatus)Preconditions.checkNotNullFromProvides(ClientComponent.ClientModule.provideLocationServicesStatus(var0, var1, var2, var3));
   }

   public LocationServicesStatus get() {
      return provideLocationServicesStatus(
         (Integer)this.deviceSdkProvider.get(),
         this.locationServicesStatusApi18Provider,
         this.locationServicesStatusApi23Provider,
         this.locationServicesStatusApi31Provider
      );
   }
}
