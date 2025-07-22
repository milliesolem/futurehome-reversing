package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGattCharacteristic;
import io.reactivex.functions.Function;

// $VF: synthetic class
public final class NotificationAndIndicationManager$$ExternalSyntheticLambda6 implements Function {
   public final BluetoothGattCharacteristic f$0;

   @Override
   public final Object apply(Object var1) {
      return NotificationAndIndicationManager.lambda$writeClientCharacteristicConfig$9(this.f$0, (Throwable)var1);
   }
}
