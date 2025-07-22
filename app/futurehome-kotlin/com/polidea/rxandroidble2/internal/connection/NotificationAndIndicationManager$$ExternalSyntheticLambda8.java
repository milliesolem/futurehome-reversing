package com.polidea.rxandroidble2.internal.connection;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.subjects.PublishSubject;

// $VF: synthetic class
public final class NotificationAndIndicationManager$$ExternalSyntheticLambda8 implements Function {
   public final PublishSubject f$0;

   @Override
   public final Object apply(Object var1) {
      return NotificationAndIndicationManager.lambda$setupServerInitiatedCharacteristicRead$0(this.f$0, (Observable)var1);
   }
}
