package com.polidea.rxandroidble2;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import bleshadow.javax.inject.Provider;

public final class ClientComponent_ClientModule_ProvideRecommendedConnectRuntimePermissionNamesFactory implements Factory<String[][]> {
   private final Provider<Integer> deviceSdkProvider;
   private final Provider<Integer> targetSdkProvider;

   public ClientComponent_ClientModule_ProvideRecommendedConnectRuntimePermissionNamesFactory(Provider<Integer> var1, Provider<Integer> var2) {
      this.deviceSdkProvider = var1;
      this.targetSdkProvider = var2;
   }

   public static ClientComponent_ClientModule_ProvideRecommendedConnectRuntimePermissionNamesFactory create(Provider<Integer> var0, Provider<Integer> var1) {
      return new ClientComponent_ClientModule_ProvideRecommendedConnectRuntimePermissionNamesFactory(var0, var1);
   }

   public static String[][] provideRecommendedConnectRuntimePermissionNames(int var0, int var1) {
      return (String[][])Preconditions.checkNotNullFromProvides(ClientComponent.ClientModule.provideRecommendedConnectRuntimePermissionNames(var0, var1));
   }

   public String[][] get() {
      return provideRecommendedConnectRuntimePermissionNames((Integer)this.deviceSdkProvider.get(), (Integer)this.targetSdkProvider.get());
   }
}
