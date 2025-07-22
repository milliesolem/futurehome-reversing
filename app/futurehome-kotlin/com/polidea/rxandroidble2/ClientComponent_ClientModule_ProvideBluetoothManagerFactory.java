package com.polidea.rxandroidble2;

import android.bluetooth.BluetoothManager;
import android.content.Context;
import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import bleshadow.javax.inject.Provider;

public final class ClientComponent_ClientModule_ProvideBluetoothManagerFactory implements Factory<BluetoothManager> {
   private final Provider<Context> contextProvider;

   public ClientComponent_ClientModule_ProvideBluetoothManagerFactory(Provider<Context> var1) {
      this.contextProvider = var1;
   }

   public static ClientComponent_ClientModule_ProvideBluetoothManagerFactory create(Provider<Context> var0) {
      return new ClientComponent_ClientModule_ProvideBluetoothManagerFactory(var0);
   }

   public static BluetoothManager provideBluetoothManager(Context var0) {
      return (BluetoothManager)Preconditions.checkNotNullFromProvides(ClientComponent.ClientModule.provideBluetoothManager(var0));
   }

   public BluetoothManager get() {
      return provideBluetoothManager((Context)this.contextProvider.get());
   }
}
