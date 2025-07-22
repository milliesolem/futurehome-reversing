package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGattCharacteristic;
import com.polidea.rxandroidble2.NotificationSetupMode;
import java.util.concurrent.Callable;

// $VF: synthetic class
public final class NotificationAndIndicationManager$$ExternalSyntheticLambda4 implements Callable {
   public final NotificationAndIndicationManager f$0;
   public final BluetoothGattCharacteristic f$1;
   public final boolean f$2;
   public final NotificationSetupMode f$3;

   @Override
   public final Object call() {
      return this.f$0
         .lambda$setupServerInitiatedCharacteristicRead$2$com-polidea-rxandroidble2-internal-connection-NotificationAndIndicationManager(
            this.f$1, this.f$2, this.f$3
         );
   }
}
