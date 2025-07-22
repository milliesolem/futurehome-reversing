package com.polidea.rxandroidble2;

import android.content.Context;
import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;

public final class ClientComponent_ClientModule_ProvideTargetSdkFactory implements Factory<Integer> {
   private final Provider<Context> contextProvider;

   public ClientComponent_ClientModule_ProvideTargetSdkFactory(Provider<Context> var1) {
      this.contextProvider = var1;
   }

   public static ClientComponent_ClientModule_ProvideTargetSdkFactory create(Provider<Context> var0) {
      return new ClientComponent_ClientModule_ProvideTargetSdkFactory(var0);
   }

   public static int provideTargetSdk(Context var0) {
      return ClientComponent.ClientModule.provideTargetSdk(var0);
   }

   public Integer get() {
      return provideTargetSdk((Context)this.contextProvider.get());
   }
}
