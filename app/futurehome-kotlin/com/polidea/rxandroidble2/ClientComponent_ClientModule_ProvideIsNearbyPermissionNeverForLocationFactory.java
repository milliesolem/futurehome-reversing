package com.polidea.rxandroidble2;

import android.content.Context;
import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;

public final class ClientComponent_ClientModule_ProvideIsNearbyPermissionNeverForLocationFactory implements Factory<Boolean> {
   private final Provider<Context> contextProvider;

   public ClientComponent_ClientModule_ProvideIsNearbyPermissionNeverForLocationFactory(Provider<Context> var1) {
      this.contextProvider = var1;
   }

   public static ClientComponent_ClientModule_ProvideIsNearbyPermissionNeverForLocationFactory create(Provider<Context> var0) {
      return new ClientComponent_ClientModule_ProvideIsNearbyPermissionNeverForLocationFactory(var0);
   }

   public static boolean provideIsNearbyPermissionNeverForLocation(Context var0) {
      return ClientComponent.ClientModule.provideIsNearbyPermissionNeverForLocation(var0);
   }

   public Boolean get() {
      return provideIsNearbyPermissionNeverForLocation((Context)this.contextProvider.get());
   }
}
