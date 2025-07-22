package com.polidea.rxandroidble2;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.scan.ScanPreconditionsVerifier;
import com.polidea.rxandroidble2.internal.scan.ScanPreconditionsVerifierApi18;
import com.polidea.rxandroidble2.internal.scan.ScanPreconditionsVerifierApi24;

public final class ClientComponent_ClientModule_ProvideScanPreconditionVerifierFactory implements Factory<ScanPreconditionsVerifier> {
   private final Provider<Integer> deviceSdkProvider;
   private final Provider<ScanPreconditionsVerifierApi18> scanPreconditionVerifierForApi18Provider;
   private final Provider<ScanPreconditionsVerifierApi24> scanPreconditionVerifierForApi24Provider;

   public ClientComponent_ClientModule_ProvideScanPreconditionVerifierFactory(
      Provider<Integer> var1, Provider<ScanPreconditionsVerifierApi18> var2, Provider<ScanPreconditionsVerifierApi24> var3
   ) {
      this.deviceSdkProvider = var1;
      this.scanPreconditionVerifierForApi18Provider = var2;
      this.scanPreconditionVerifierForApi24Provider = var3;
   }

   public static ClientComponent_ClientModule_ProvideScanPreconditionVerifierFactory create(
      Provider<Integer> var0, Provider<ScanPreconditionsVerifierApi18> var1, Provider<ScanPreconditionsVerifierApi24> var2
   ) {
      return new ClientComponent_ClientModule_ProvideScanPreconditionVerifierFactory(var0, var1, var2);
   }

   public static ScanPreconditionsVerifier provideScanPreconditionVerifier(
      int var0, Provider<ScanPreconditionsVerifierApi18> var1, Provider<ScanPreconditionsVerifierApi24> var2
   ) {
      return (ScanPreconditionsVerifier)Preconditions.checkNotNullFromProvides(ClientComponent.ClientModule.provideScanPreconditionVerifier(var0, var1, var2));
   }

   public ScanPreconditionsVerifier get() {
      return provideScanPreconditionVerifier(
         (Integer)this.deviceSdkProvider.get(), this.scanPreconditionVerifierForApi18Provider, this.scanPreconditionVerifierForApi24Provider
      );
   }
}
