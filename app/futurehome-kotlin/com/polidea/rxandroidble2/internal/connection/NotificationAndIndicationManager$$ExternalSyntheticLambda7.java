package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGattCharacteristic;
import com.polidea.rxandroidble2.NotificationSetupMode;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;

// $VF: synthetic class
public final class NotificationAndIndicationManager$$ExternalSyntheticLambda7 implements CompletableTransformer {
   public final NotificationSetupMode f$0;
   public final BluetoothGattCharacteristic f$1;
   public final DescriptorWriter f$2;
   public final byte[] f$3;

   @Override
   public final CompletableSource apply(Completable var1) {
      return NotificationAndIndicationManager.lambda$teardownModeTransformer$6(this.f$0, this.f$1, this.f$2, this.f$3, var1);
   }
}
