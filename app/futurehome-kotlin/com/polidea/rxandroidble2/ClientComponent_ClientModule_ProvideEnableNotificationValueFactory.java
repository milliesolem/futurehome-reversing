package com.polidea.rxandroidble2;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;

public final class ClientComponent_ClientModule_ProvideEnableNotificationValueFactory implements Factory<byte[]> {
   public static ClientComponent_ClientModule_ProvideEnableNotificationValueFactory create() {
      return ClientComponent_ClientModule_ProvideEnableNotificationValueFactory.InstanceHolder.INSTANCE;
   }

   public static byte[] provideEnableNotificationValue() {
      return (byte[])Preconditions.checkNotNullFromProvides(ClientComponent.ClientModule.provideEnableNotificationValue());
   }

   public byte[] get() {
      return provideEnableNotificationValue();
   }

   private static final class InstanceHolder {
      private static final ClientComponent_ClientModule_ProvideEnableNotificationValueFactory INSTANCE = new ClientComponent_ClientModule_ProvideEnableNotificationValueFactory();
   }
}
