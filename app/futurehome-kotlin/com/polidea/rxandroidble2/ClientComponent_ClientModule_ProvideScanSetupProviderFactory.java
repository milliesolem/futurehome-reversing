package com.polidea.rxandroidble2;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.scan.ScanSetupBuilder;
import com.polidea.rxandroidble2.internal.scan.ScanSetupBuilderImplApi18;
import com.polidea.rxandroidble2.internal.scan.ScanSetupBuilderImplApi21;
import com.polidea.rxandroidble2.internal.scan.ScanSetupBuilderImplApi23;

public final class ClientComponent_ClientModule_ProvideScanSetupProviderFactory implements Factory<ScanSetupBuilder> {
   private final Provider<Integer> deviceSdkProvider;
   private final Provider<ScanSetupBuilderImplApi18> scanSetupBuilderProviderForApi18Provider;
   private final Provider<ScanSetupBuilderImplApi21> scanSetupBuilderProviderForApi21Provider;
   private final Provider<ScanSetupBuilderImplApi23> scanSetupBuilderProviderForApi23Provider;

   public ClientComponent_ClientModule_ProvideScanSetupProviderFactory(
      Provider<Integer> var1, Provider<ScanSetupBuilderImplApi18> var2, Provider<ScanSetupBuilderImplApi21> var3, Provider<ScanSetupBuilderImplApi23> var4
   ) {
      this.deviceSdkProvider = var1;
      this.scanSetupBuilderProviderForApi18Provider = var2;
      this.scanSetupBuilderProviderForApi21Provider = var3;
      this.scanSetupBuilderProviderForApi23Provider = var4;
   }

   public static ClientComponent_ClientModule_ProvideScanSetupProviderFactory create(
      Provider<Integer> var0, Provider<ScanSetupBuilderImplApi18> var1, Provider<ScanSetupBuilderImplApi21> var2, Provider<ScanSetupBuilderImplApi23> var3
   ) {
      return new ClientComponent_ClientModule_ProvideScanSetupProviderFactory(var0, var1, var2, var3);
   }

   public static ScanSetupBuilder provideScanSetupProvider(
      int var0, Provider<ScanSetupBuilderImplApi18> var1, Provider<ScanSetupBuilderImplApi21> var2, Provider<ScanSetupBuilderImplApi23> var3
   ) {
      return (ScanSetupBuilder)Preconditions.checkNotNullFromProvides(ClientComponent.ClientModule.provideScanSetupProvider(var0, var1, var2, var3));
   }

   public ScanSetupBuilder get() {
      return provideScanSetupProvider(
         (Integer)this.deviceSdkProvider.get(),
         this.scanSetupBuilderProviderForApi18Provider,
         this.scanSetupBuilderProviderForApi21Provider,
         this.scanSetupBuilderProviderForApi23Provider
      );
   }
}
