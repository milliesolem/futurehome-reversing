package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGattCharacteristic;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.internal.BleIllegalOperationException;
import io.reactivex.Completable;
import io.reactivex.functions.Action;

public class IllegalOperationChecker {
   final IllegalOperationHandler resultHandler;

   @Inject
   public IllegalOperationChecker(IllegalOperationHandler var1) {
      this.resultHandler = var1;
   }

   public Completable checkAnyPropertyMatches(BluetoothGattCharacteristic var1, int var2) {
      return Completable.fromAction(new Action(this, var1, var2) {
         final IllegalOperationChecker this$0;
         final BluetoothGattCharacteristic val$characteristic;
         final int val$neededProperties;

         {
            this.this$0 = var1;
            this.val$characteristic = var2x;
            this.val$neededProperties = var3;
         }

         @Override
         public void run() {
            if ((this.val$characteristic.getProperties() & this.val$neededProperties) == 0) {
               BleIllegalOperationException var1x = this.this$0.resultHandler.handleMismatchData(this.val$characteristic, this.val$neededProperties);
               if (var1x != null) {
                  throw var1x;
               }
            }
         }
      });
   }
}
