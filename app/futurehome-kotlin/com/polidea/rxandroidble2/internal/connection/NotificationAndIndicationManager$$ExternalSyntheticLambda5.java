package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import io.reactivex.functions.Action;

// $VF: synthetic class
public final class NotificationAndIndicationManager$$ExternalSyntheticLambda5 implements Action {
   public final BluetoothGatt f$0;
   public final BluetoothGattCharacteristic f$1;
   public final boolean f$2;

   @Override
   public final void run() {
      NotificationAndIndicationManager.lambda$setCharacteristicNotification$3(this.f$0, this.f$1, this.f$2);
   }
}
