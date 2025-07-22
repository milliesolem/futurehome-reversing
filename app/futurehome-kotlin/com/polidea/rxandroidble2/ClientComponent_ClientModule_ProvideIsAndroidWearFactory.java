package com.polidea.rxandroidble2;

import android.content.Context;
import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;

public final class ClientComponent_ClientModule_ProvideIsAndroidWearFactory implements Factory<Boolean> {
   private final Provider<Context> contextProvider;
   private final Provider<Integer> deviceSdkProvider;

   public ClientComponent_ClientModule_ProvideIsAndroidWearFactory(Provider<Context> var1, Provider<Integer> var2) {
      this.contextProvider = var1;
      this.deviceSdkProvider = var2;
   }

   public static ClientComponent_ClientModule_ProvideIsAndroidWearFactory create(Provider<Context> var0, Provider<Integer> var1) {
      return new ClientComponent_ClientModule_ProvideIsAndroidWearFactory(var0, var1);
   }

   public static boolean provideIsAndroidWear(Context var0, int var1) {
      return ClientComponent.ClientModule.provideIsAndroidWear(var0, var1);
   }

   public Boolean get() {
      return provideIsAndroidWear((Context)this.contextProvider.get(), (Integer)this.deviceSdkProvider.get());
   }
}
