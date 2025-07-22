package com.polidea.rxandroidble2.internal.connection;

import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble2.exceptions.BleGattException;
import com.polidea.rxandroidble2.exceptions.BleGattOperationType;
import io.reactivex.Observable;
import io.reactivex.disposables.SerialDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.functions.Functions;

@ConnectionScope
class MtuWatcher implements ConnectionSubscriptionWatcher, MtuProvider, Consumer<Integer> {
   private Integer currentMtu;
   private final Observable<Integer> mtuObservable;
   private final SerialDisposable serialSubscription = new SerialDisposable();

   @Inject
   MtuWatcher(RxBleGattCallback var1, @Named("GATT_MTU_MINIMUM") int var2) {
      this.mtuObservable = var1.getOnMtuChanged().retry(new Predicate<Throwable>(this) {
         final MtuWatcher this$0;

         {
            this.this$0 = var1;
         }

         public boolean test(Throwable var1) {
            boolean var2x;
            if (var1 instanceof BleGattException && ((BleGattException)var1).getBleGattOperationType() == BleGattOperationType.ON_MTU_CHANGED) {
               var2x = true;
            } else {
               var2x = false;
            }

            return var2x;
         }
      });
      this.currentMtu = var2;
   }

   public void accept(Integer var1) {
      this.currentMtu = var1;
   }

   @Override
   public int getMtu() {
      return this.currentMtu;
   }

   @Override
   public void onConnectionSubscribed() {
      this.serialSubscription.set(this.mtuObservable.subscribe(this, Functions.emptyConsumer()));
   }

   @Override
   public void onConnectionUnsubscribed() {
      this.serialSubscription.dispose();
   }
}
