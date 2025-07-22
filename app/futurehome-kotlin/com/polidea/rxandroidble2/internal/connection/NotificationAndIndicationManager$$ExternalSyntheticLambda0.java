package com.polidea.rxandroidble2.internal.connection;

import com.polidea.rxandroidble2.internal.util.CharacteristicChangedEvent;
import com.polidea.rxandroidble2.internal.util.CharacteristicNotificationId;
import io.reactivex.functions.Predicate;

// $VF: synthetic class
public final class NotificationAndIndicationManager$$ExternalSyntheticLambda0 implements Predicate {
   public final CharacteristicNotificationId f$0;

   @Override
   public final boolean test(Object var1) {
      return NotificationAndIndicationManager.lambda$observeOnCharacteristicChangeCallbacks$7(this.f$0, (CharacteristicChangedEvent)var1);
   }
}
