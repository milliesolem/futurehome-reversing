package com.polidea.rxandroidble2.internal.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import bleshadow.javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Cancellable;
import io.reactivex.schedulers.Schedulers;

public class LocationServicesOkObservableApi23Factory {
   final Context context;
   final LocationServicesStatus locationServicesStatus;

   @Inject
   LocationServicesOkObservableApi23Factory(Context var1, LocationServicesStatus var2) {
      this.context = var1;
      this.locationServicesStatus = var2;
   }

   public Observable<Boolean> get() {
      return Observable.create(new ObservableOnSubscribe<Boolean>(this) {
         final LocationServicesOkObservableApi23Factory this$0;

         {
            this.this$0 = var1;
         }

         @Override
         public void subscribe(ObservableEmitter<Boolean> var1) {
            boolean var2 = this.this$0.locationServicesStatus.isLocationProviderOk();
            BroadcastReceiver var3 = new BroadcastReceiver(this, var1) {
               final <unrepresentable> this$1;
               final ObservableEmitter val$emitter;

               {
                  this.this$1 = var1;
                  this.val$emitter = var2x;
               }

               public void onReceive(Context var1, Intent var2x) {
                  boolean var3x = this.this$1.this$0.locationServicesStatus.isLocationProviderOk();
                  this.val$emitter.onNext(var3x);
               }
            };
            var1.onNext(var2);
            this.this$0.context.registerReceiver(var3, new IntentFilter("android.location.MODE_CHANGED"));
            var1.setCancellable(new Cancellable(this, var3) {
               final <unrepresentable> this$1;
               final BroadcastReceiver val$broadcastReceiver;

               {
                  this.this$1 = var1;
                  this.val$broadcastReceiver = var2x;
               }

               @Override
               public void cancel() {
                  this.this$1.this$0.context.unregisterReceiver(this.val$broadcastReceiver);
               }
            });
         }
      }).distinctUntilChanged().subscribeOn(Schedulers.trampoline()).unsubscribeOn(Schedulers.trampoline());
   }
}
