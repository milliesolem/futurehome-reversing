package com.polidea.rxandroidble2.internal.connection;

import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble2.RxBleConnection;

@ConnectionScope
class MtuBasedPayloadSizeLimit implements PayloadSizeLimitProvider {
   private final int gattWriteMtuOverhead;
   private final RxBleConnection rxBleConnection;

   @Inject
   MtuBasedPayloadSizeLimit(RxBleConnection var1, @Named("GATT_WRITE_MTU_OVERHEAD") int var2) {
      this.rxBleConnection = var1;
      this.gattWriteMtuOverhead = var2;
   }

   @Override
   public int getPayloadSizeLimit() {
      return this.rxBleConnection.getMtu() - this.gattWriteMtuOverhead;
   }
}
