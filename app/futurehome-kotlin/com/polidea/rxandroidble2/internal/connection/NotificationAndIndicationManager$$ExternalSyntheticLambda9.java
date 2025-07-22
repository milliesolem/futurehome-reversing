package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGattCharacteristic;
import com.polidea.rxandroidble2.NotificationSetupMode;
import com.polidea.rxandroidble2.internal.util.CharacteristicNotificationId;
import io.reactivex.functions.Action;
import io.reactivex.subjects.PublishSubject;

// $VF: synthetic class
public final class NotificationAndIndicationManager$$ExternalSyntheticLambda9 implements Action {
   public final NotificationAndIndicationManager f$0;
   public final PublishSubject f$1;
   public final CharacteristicNotificationId f$2;
   public final BluetoothGattCharacteristic f$3;
   public final NotificationSetupMode f$4;

   @Override
   public final void run() {
      this.f$0
         .lambda$setupServerInitiatedCharacteristicRead$1$com-polidea-rxandroidble2-internal-connection-NotificationAndIndicationManager(
            this.f$1, this.f$2, this.f$3, this.f$4
         );
   }
}
