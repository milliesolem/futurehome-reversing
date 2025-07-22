package com.polidea.rxandroidble2;

import android.content.Context;
import android.location.LocationManager;
import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import bleshadow.javax.inject.Provider;

public final class ClientComponent_ClientModule_ProvideLocationManagerFactory implements Factory<LocationManager> {
   private final Provider<Context> contextProvider;

   public ClientComponent_ClientModule_ProvideLocationManagerFactory(Provider<Context> var1) {
      this.contextProvider = var1;
   }

   public static ClientComponent_ClientModule_ProvideLocationManagerFactory create(Provider<Context> var0) {
      return new ClientComponent_ClientModule_ProvideLocationManagerFactory(var0);
   }

   public static LocationManager provideLocationManager(Context var0) {
      return (LocationManager)Preconditions.checkNotNullFromProvides(ClientComponent.ClientModule.provideLocationManager(var0));
   }

   public LocationManager get() {
      return provideLocationManager((Context)this.contextProvider.get());
   }
}
