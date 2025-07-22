package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGattCharacteristic;
import com.polidea.rxandroidble2.NotificationSetupMode;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

// $VF: synthetic class
public final class NotificationAndIndicationManager$$ExternalSyntheticLambda3 implements ObservableTransformer {
   public final NotificationSetupMode f$0;
   public final BluetoothGattCharacteristic f$1;
   public final DescriptorWriter f$2;
   public final byte[] f$3;

   @Override
   public final ObservableSource apply(Observable var1) {
      return NotificationAndIndicationManager.lambda$setupModeTransformer$5(this.f$0, this.f$1, this.f$2, this.f$3, var1);
   }
}
