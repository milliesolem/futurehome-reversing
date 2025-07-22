package com.polidea.rxandroidble2.internal.util;

import android.content.ContentResolver;
import android.location.LocationManager;
import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;

public final class CheckerLocationProvider_Factory implements Factory<CheckerLocationProvider> {
   private final Provider<ContentResolver> contentResolverProvider;
   private final Provider<LocationManager> locationManagerProvider;

   public CheckerLocationProvider_Factory(Provider<ContentResolver> var1, Provider<LocationManager> var2) {
      this.contentResolverProvider = var1;
      this.locationManagerProvider = var2;
   }

   public static CheckerLocationProvider_Factory create(Provider<ContentResolver> var0, Provider<LocationManager> var1) {
      return new CheckerLocationProvider_Factory(var0, var1);
   }

   public static CheckerLocationProvider newInstance(ContentResolver var0, LocationManager var1) {
      return new CheckerLocationProvider(var0, var1);
   }

   public CheckerLocationProvider get() {
      return newInstance((ContentResolver)this.contentResolverProvider.get(), (LocationManager)this.locationManagerProvider.get());
   }
}
