package com.polidea.rxandroidble2;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import bleshadow.javax.inject.Provider;

public final class ClientComponent_ClientModule_ProvideRecommendedScanRuntimePermissionNamesFactory implements Factory<String[][]> {
   private final Provider<Integer> deviceSdkProvider;
   private final Provider<Boolean> isNearbyServicesNeverForLocationProvider;
   private final Provider<Integer> targetSdkProvider;

   public ClientComponent_ClientModule_ProvideRecommendedScanRuntimePermissionNamesFactory(
      Provider<Integer> var1, Provider<Integer> var2, Provider<Boolean> var3
   ) {
      this.deviceSdkProvider = var1;
      this.targetSdkProvider = var2;
      this.isNearbyServicesNeverForLocationProvider = var3;
   }

   public static ClientComponent_ClientModule_ProvideRecommendedScanRuntimePermissionNamesFactory create(
      Provider<Integer> var0, Provider<Integer> var1, Provider<Boolean> var2
   ) {
      return new ClientComponent_ClientModule_ProvideRecommendedScanRuntimePermissionNamesFactory(var0, var1, var2);
   }

   public static String[][] provideRecommendedScanRuntimePermissionNames(int var0, int var1, boolean var2) {
      return (String[][])Preconditions.checkNotNullFromProvides(ClientComponent.ClientModule.provideRecommendedScanRuntimePermissionNames(var0, var1, var2));
   }

   public String[][] get() {
      return provideRecommendedScanRuntimePermissionNames(
         (Integer)this.deviceSdkProvider.get(), (Integer)this.targetSdkProvider.get(), (Boolean)this.isNearbyServicesNeverForLocationProvider.get()
      );
   }
}
