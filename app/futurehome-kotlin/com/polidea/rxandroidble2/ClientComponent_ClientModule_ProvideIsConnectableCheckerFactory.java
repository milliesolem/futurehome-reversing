package com.polidea.rxandroidble2;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.scan.IsConnectableChecker;
import com.polidea.rxandroidble2.internal.scan.IsConnectableCheckerApi18;
import com.polidea.rxandroidble2.internal.scan.IsConnectableCheckerApi26;

public final class ClientComponent_ClientModule_ProvideIsConnectableCheckerFactory implements Factory<IsConnectableChecker> {
   private final Provider<Integer> deviceSdkProvider;
   private final Provider<IsConnectableCheckerApi18> isConnectableCheckerApi18Provider;
   private final Provider<IsConnectableCheckerApi26> isConnectableCheckerApi26Provider;

   public ClientComponent_ClientModule_ProvideIsConnectableCheckerFactory(
      Provider<Integer> var1, Provider<IsConnectableCheckerApi18> var2, Provider<IsConnectableCheckerApi26> var3
   ) {
      this.deviceSdkProvider = var1;
      this.isConnectableCheckerApi18Provider = var2;
      this.isConnectableCheckerApi26Provider = var3;
   }

   public static ClientComponent_ClientModule_ProvideIsConnectableCheckerFactory create(
      Provider<Integer> var0, Provider<IsConnectableCheckerApi18> var1, Provider<IsConnectableCheckerApi26> var2
   ) {
      return new ClientComponent_ClientModule_ProvideIsConnectableCheckerFactory(var0, var1, var2);
   }

   public static IsConnectableChecker provideIsConnectableChecker(int var0, Provider<IsConnectableCheckerApi18> var1, Provider<IsConnectableCheckerApi26> var2) {
      return (IsConnectableChecker)Preconditions.checkNotNullFromProvides(ClientComponent.ClientModule.provideIsConnectableChecker(var0, var1, var2));
   }

   public IsConnectableChecker get() {
      return provideIsConnectableChecker((Integer)this.deviceSdkProvider.get(), this.isConnectableCheckerApi18Provider, this.isConnectableCheckerApi26Provider);
   }
}
