package com.polidea.rxandroidble2.internal.util;

import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble2.RxBleAdapterStateObservable;
import com.polidea.rxandroidble2.RxBleClient;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.util.concurrent.TimeUnit;

public class ClientStateObservable extends Observable<RxBleClient.State> {
   final Observable<RxBleAdapterStateObservable.BleAdapterState> bleAdapterStateObservable;
   final Observable<Boolean> locationServicesOkObservable;
   private final LocationServicesStatus locationServicesStatus;
   final RxBleAdapterWrapper rxBleAdapterWrapper;
   private final Scheduler timerScheduler;

   @Inject
   protected ClientStateObservable(
      RxBleAdapterWrapper var1,
      Observable<RxBleAdapterStateObservable.BleAdapterState> var2,
      @Named("location-ok-boolean-observable") Observable<Boolean> var3,
      LocationServicesStatus var4,
      @Named("timeout") Scheduler var5
   ) {
      this.rxBleAdapterWrapper = var1;
      this.bleAdapterStateObservable = var2;
      this.locationServicesOkObservable = var3;
      this.locationServicesStatus = var4;
      this.timerScheduler = var5;
   }

   static Observable<RxBleClient.State> checkAdapterAndServicesState(
      RxBleAdapterWrapper var0, Observable<RxBleAdapterStateObservable.BleAdapterState> var1, Observable<Boolean> var2
   ) {
      RxBleAdapterStateObservable.BleAdapterState var3;
      if (var0.isBluetoothEnabled()) {
         var3 = RxBleAdapterStateObservable.BleAdapterState.STATE_ON;
      } else {
         var3 = RxBleAdapterStateObservable.BleAdapterState.STATE_OFF;
      }

      return var1.startWith(var3)
         .switchMap(
            new Function<RxBleAdapterStateObservable.BleAdapterState, Observable<RxBleClient.State>>(var2) {
               final Observable val$locationServicesOkObservable;

               {
                  this.val$locationServicesOkObservable = var1;
               }

               public Observable<RxBleClient.State> apply(RxBleAdapterStateObservable.BleAdapterState var1) {
                  return var1 != RxBleAdapterStateObservable.BleAdapterState.STATE_ON
                     ? Observable.just(RxBleClient.State.BLUETOOTH_NOT_ENABLED)
                     : this.val$locationServicesOkObservable.map(new Function<Boolean, RxBleClient.State>(this) {
                        final <unrepresentable> this$0;

                        {
                           this.this$0 = var1;
                        }

                        public RxBleClient.State apply(Boolean var1) {
                           RxBleClient.State var2x;
                           if (var1) {
                              var2x = RxBleClient.State.READY;
                           } else {
                              var2x = RxBleClient.State.LOCATION_SERVICES_NOT_ENABLED;
                           }

                           return var2x;
                        }
                     });
               }
            }
         );
   }

   private static Single<Boolean> checkPermissionUntilGranted(LocationServicesStatus var0, Scheduler var1) {
      return Observable.interval(0L, 1L, TimeUnit.SECONDS, var1).takeWhile(new Predicate<Long>(var0) {
         final LocationServicesStatus val$locationServicesStatus;

         {
            this.val$locationServicesStatus = var1;
         }

         public boolean test(Long var1) {
            return this.val$locationServicesStatus.isLocationPermissionOk() ^ true;
         }
      }).count().map(new Function<Long, Boolean>() {
         public Boolean apply(Long var1) {
            boolean var2;
            if (var1 == 0L) {
               var2 = true;
            } else {
               var2 = false;
            }

            return var2;
         }
      });
   }

   @Override
   protected void subscribeActual(Observer<? super RxBleClient.State> var1) {
      if (!this.rxBleAdapterWrapper.hasBluetoothAdapter()) {
         var1.onSubscribe(Disposables.empty());
         var1.onComplete();
      } else {
         checkPermissionUntilGranted(this.locationServicesStatus, this.timerScheduler)
            .flatMapObservable(
               new Function<Boolean, Observable<RxBleClient.State>>(this) {
                  final ClientStateObservable this$0;

                  {
                     this.this$0 = var1;
                  }

                  public Observable<RxBleClient.State> apply(Boolean var1) {
                     Observable var3 = ClientStateObservable.checkAdapterAndServicesState(
                           this.this$0.rxBleAdapterWrapper, this.this$0.bleAdapterStateObservable, this.this$0.locationServicesOkObservable
                        )
                        .distinctUntilChanged();
                     Observable var2 = var3;
                     if (var1) {
                        var2 = var3.skip(1L);
                     }

                     return var2;
                  }
               }
            )
            .subscribe(var1);
      }
   }
}
