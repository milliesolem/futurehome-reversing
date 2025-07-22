package com.polidea.rxandroidble2.internal.util;

import android.content.Context;
import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;

public final class CheckerPermission_Factory implements Factory<CheckerPermission> {
   private final Provider<Context> contextProvider;

   public CheckerPermission_Factory(Provider<Context> var1) {
      this.contextProvider = var1;
   }

   public static CheckerPermission_Factory create(Provider<Context> var0) {
      return new CheckerPermission_Factory(var0);
   }

   public static CheckerPermission newInstance(Context var0) {
      return new CheckerPermission(var0);
   }

   public CheckerPermission get() {
      return newInstance((Context)this.contextProvider.get());
   }
}
