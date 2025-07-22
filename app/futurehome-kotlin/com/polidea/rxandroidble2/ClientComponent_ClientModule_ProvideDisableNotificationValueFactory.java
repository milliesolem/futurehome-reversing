package com.polidea.rxandroidble2;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;

public final class ClientComponent_ClientModule_ProvideDisableNotificationValueFactory implements Factory<byte[]> {
   public static ClientComponent_ClientModule_ProvideDisableNotificationValueFactory create() {
      return ClientComponent_ClientModule_ProvideDisableNotificationValueFactory.InstanceHolder.INSTANCE;
   }

   public static byte[] provideDisableNotificationValue() {
      return (byte[])Preconditions.checkNotNullFromProvides(ClientComponent.ClientModule.provideDisableNotificationValue());
   }

   public byte[] get() {
      return provideDisableNotificationValue();
   }

   private static final class InstanceHolder {
      private static final ClientComponent_ClientModule_ProvideDisableNotificationValueFactory INSTANCE = new ClientComponent_ClientModule_ProvideDisableNotificationValueFactory();
   }
}
