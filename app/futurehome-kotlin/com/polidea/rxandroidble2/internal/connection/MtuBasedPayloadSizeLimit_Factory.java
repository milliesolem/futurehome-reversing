package com.polidea.rxandroidble2.internal.connection;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.RxBleConnection;

public final class MtuBasedPayloadSizeLimit_Factory implements Factory<MtuBasedPayloadSizeLimit> {
   private final Provider<Integer> gattWriteMtuOverheadProvider;
   private final Provider<RxBleConnection> rxBleConnectionProvider;

   public MtuBasedPayloadSizeLimit_Factory(Provider<RxBleConnection> var1, Provider<Integer> var2) {
      this.rxBleConnectionProvider = var1;
      this.gattWriteMtuOverheadProvider = var2;
   }

   public static MtuBasedPayloadSizeLimit_Factory create(Provider<RxBleConnection> var0, Provider<Integer> var1) {
      return new MtuBasedPayloadSizeLimit_Factory(var0, var1);
   }

   public static MtuBasedPayloadSizeLimit newInstance(RxBleConnection var0, int var1) {
      return new MtuBasedPayloadSizeLimit(var0, var1);
   }

   public MtuBasedPayloadSizeLimit get() {
      return newInstance((RxBleConnection)this.rxBleConnectionProvider.get(), (Integer)this.gattWriteMtuOverheadProvider.get());
   }
}
