package com.polidea.rxandroidble2.internal.util;

import io.reactivex.Observable;

public class ActiveCharacteristicNotification {
   public final boolean isIndication;
   public final Observable<Observable<byte[]>> notificationObservable;

   public ActiveCharacteristicNotification(Observable<Observable<byte[]>> var1, boolean var2) {
      this.notificationObservable = var1;
      this.isIndication = var2;
   }
}
