package com.polidea.rxandroidble2;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;

public final class ClientComponent_ClientModule_ProvideEnableIndicationValueFactory implements Factory<byte[]> {
   public static ClientComponent_ClientModule_ProvideEnableIndicationValueFactory create() {
      return ClientComponent_ClientModule_ProvideEnableIndicationValueFactory.InstanceHolder.INSTANCE;
   }

   public static byte[] provideEnableIndicationValue() {
      return (byte[])Preconditions.checkNotNullFromProvides(ClientComponent.ClientModule.provideEnableIndicationValue());
   }

   public byte[] get() {
      return provideEnableIndicationValue();
   }

   private static final class InstanceHolder {
      private static final ClientComponent_ClientModule_ProvideEnableIndicationValueFactory INSTANCE = new ClientComponent_ClientModule_ProvideEnableIndicationValueFactory();
   }
}
