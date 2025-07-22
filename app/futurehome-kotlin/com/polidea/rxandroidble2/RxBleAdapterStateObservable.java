package com.polidea.rxandroidble2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.internal.RxBleLog;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.functions.Cancellable;
import io.reactivex.schedulers.Schedulers;

public class RxBleAdapterStateObservable extends Observable<RxBleAdapterStateObservable.BleAdapterState> {
   private final Observable<RxBleAdapterStateObservable.BleAdapterState> bleAdapterStateObservable;

   @Inject
   public RxBleAdapterStateObservable(Context var1) {
      this.bleAdapterStateObservable = Observable.create(
            new ObservableOnSubscribe<RxBleAdapterStateObservable.BleAdapterState>(this, var1) {
               final RxBleAdapterStateObservable this$0;
               final Context val$context;

               {
                  this.this$0 = var1;
                  this.val$context = var2;
               }

               @Override
               public void subscribe(ObservableEmitter<RxBleAdapterStateObservable.BleAdapterState> var1) {
                  BroadcastReceiver var2 = new BroadcastReceiver(this, var1) {
                     final <unrepresentable> this$1;
                     final ObservableEmitter val$emitter;

                     {
                        this.this$1 = var1;
                        this.val$emitter = var2x;
                     }

                     public void onReceive(Context var1, Intent var2x) {
                        RxBleAdapterStateObservable.BleAdapterState var3 = RxBleAdapterStateObservable.mapToBleAdapterState(
                           var2x.getIntExtra("android.bluetooth.adapter.extra.STATE", -1)
                        );
                        RxBleLog.i("Adapter state changed: %s", var3);
                        this.val$emitter.onNext(var3);
                     }
                  };
                  this.val$context.registerReceiver(var2, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
                  var1.setCancellable(new Cancellable(this, var2) {
                     final <unrepresentable> this$1;
                     final BroadcastReceiver val$receiver;

                     {
                        this.this$1 = var1;
                        this.val$receiver = var2x;
                     }

                     @Override
                     public void cancel() {
                        this.this$1.val$context.unregisterReceiver(this.val$receiver);
                     }
                  });
               }
            }
         )
         .subscribeOn(Schedulers.trampoline())
         .unsubscribeOn(Schedulers.trampoline())
         .share();
   }

   static RxBleAdapterStateObservable.BleAdapterState mapToBleAdapterState(int var0) {
      switch (var0) {
         case 11:
            return RxBleAdapterStateObservable.BleAdapterState.STATE_TURNING_ON;
         case 12:
            return RxBleAdapterStateObservable.BleAdapterState.STATE_ON;
         case 13:
            return RxBleAdapterStateObservable.BleAdapterState.STATE_TURNING_OFF;
         default:
            return RxBleAdapterStateObservable.BleAdapterState.STATE_OFF;
      }
   }

   @Override
   protected void subscribeActual(Observer<? super RxBleAdapterStateObservable.BleAdapterState> var1) {
      this.bleAdapterStateObservable.subscribe(var1);
   }

   public static class BleAdapterState {
      public static final RxBleAdapterStateObservable.BleAdapterState STATE_OFF = new RxBleAdapterStateObservable.BleAdapterState(false, "STATE_OFF");
      public static final RxBleAdapterStateObservable.BleAdapterState STATE_ON = new RxBleAdapterStateObservable.BleAdapterState(true, "STATE_ON");
      public static final RxBleAdapterStateObservable.BleAdapterState STATE_TURNING_OFF = new RxBleAdapterStateObservable.BleAdapterState(
         false, "STATE_TURNING_OFF"
      );
      public static final RxBleAdapterStateObservable.BleAdapterState STATE_TURNING_ON = new RxBleAdapterStateObservable.BleAdapterState(
         false, "STATE_TURNING_ON"
      );
      private final boolean isUsable;
      private final String stateName;

      private BleAdapterState(boolean var1, String var2) {
         this.isUsable = var1;
         this.stateName = var2;
      }

      public boolean isUsable() {
         return this.isUsable;
      }

      @Override
      public String toString() {
         return this.stateName;
      }
   }
}
