package com.polidea.rxandroidble2;

import bleshadow.dagger.internal.Factory;

public final class ClientComponent_ClientModule_ProvideDeviceSdkFactory implements Factory<Integer> {
   public static ClientComponent_ClientModule_ProvideDeviceSdkFactory create() {
      return ClientComponent_ClientModule_ProvideDeviceSdkFactory.InstanceHolder.INSTANCE;
   }

   public static int provideDeviceSdk() {
      return ClientComponent.ClientModule.provideDeviceSdk();
   }

   public Integer get() {
      return provideDeviceSdk();
   }

   private static final class InstanceHolder {
      private static final ClientComponent_ClientModule_ProvideDeviceSdkFactory INSTANCE = new ClientComponent_ClientModule_ProvideDeviceSdkFactory();
   }
}
