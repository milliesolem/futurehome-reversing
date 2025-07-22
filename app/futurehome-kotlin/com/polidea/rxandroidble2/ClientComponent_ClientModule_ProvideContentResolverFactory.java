package com.polidea.rxandroidble2;

import android.content.ContentResolver;
import android.content.Context;
import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import bleshadow.javax.inject.Provider;

public final class ClientComponent_ClientModule_ProvideContentResolverFactory implements Factory<ContentResolver> {
   private final Provider<Context> contextProvider;

   public ClientComponent_ClientModule_ProvideContentResolverFactory(Provider<Context> var1) {
      this.contextProvider = var1;
   }

   public static ClientComponent_ClientModule_ProvideContentResolverFactory create(Provider<Context> var0) {
      return new ClientComponent_ClientModule_ProvideContentResolverFactory(var0);
   }

   public static ContentResolver provideContentResolver(Context var0) {
      return (ContentResolver)Preconditions.checkNotNullFromProvides(ClientComponent.ClientModule.provideContentResolver(var0));
   }

   public ContentResolver get() {
      return provideContentResolver((Context)this.contextProvider.get());
   }
}
