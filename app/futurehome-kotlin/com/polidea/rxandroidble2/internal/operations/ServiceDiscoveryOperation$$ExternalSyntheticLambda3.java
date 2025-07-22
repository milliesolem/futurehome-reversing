package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.BluetoothGatt;
import io.reactivex.Scheduler;
import java.util.concurrent.Callable;

// $VF: synthetic class
public final class ServiceDiscoveryOperation$$ExternalSyntheticLambda3 implements Callable {
   public final BluetoothGatt f$0;
   public final Scheduler f$1;

   @Override
   public final Object call() {
      return ServiceDiscoveryOperation.lambda$timeoutFallbackProcedure$3(this.f$0, this.f$1);
   }
}
