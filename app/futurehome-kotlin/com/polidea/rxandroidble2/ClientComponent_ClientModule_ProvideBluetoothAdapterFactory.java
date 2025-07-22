package com.polidea.rxandroidble2;

import android.bluetooth.BluetoothAdapter;
import bleshadow.dagger.internal.Factory;

public final class ClientComponent_ClientModule_ProvideBluetoothAdapterFactory implements Factory<BluetoothAdapter> {
   public static ClientComponent_ClientModule_ProvideBluetoothAdapterFactory create() {
      return ClientComponent_ClientModule_ProvideBluetoothAdapterFactory.InstanceHolder.INSTANCE;
   }

   public static BluetoothAdapter provideBluetoothAdapter() {
      return ClientComponent.ClientModule.provideBluetoothAdapter();
   }

   public BluetoothAdapter get() {
      return provideBluetoothAdapter();
   }

   private static final class InstanceHolder {
      private static final ClientComponent_ClientModule_ProvideBluetoothAdapterFactory INSTANCE = new ClientComponent_ClientModule_ProvideBluetoothAdapterFactory();
   }
}
