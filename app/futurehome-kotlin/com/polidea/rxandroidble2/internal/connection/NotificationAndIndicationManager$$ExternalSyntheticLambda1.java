package com.polidea.rxandroidble2.internal.connection;

import com.polidea.rxandroidble2.internal.util.CharacteristicChangedEvent;
import io.reactivex.functions.Function;

// $VF: synthetic class
public final class NotificationAndIndicationManager$$ExternalSyntheticLambda1 implements Function {
   @Override
   public final Object apply(Object var1) {
      return NotificationAndIndicationManager.lambda$observeOnCharacteristicChangeCallbacks$8((CharacteristicChangedEvent)var1);
   }
}
