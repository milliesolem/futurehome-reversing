package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.BluetoothGatt;
import io.reactivex.functions.Function;

// $VF: synthetic class
public final class ServiceDiscoveryOperation$$ExternalSyntheticLambda0 implements Function {
   public final BluetoothGatt f$0;

   @Override
   public final Object apply(Object var1) {
      return ServiceDiscoveryOperation.lambda$timeoutFallbackProcedure$2(this.f$0, (Long)var1);
   }
}
