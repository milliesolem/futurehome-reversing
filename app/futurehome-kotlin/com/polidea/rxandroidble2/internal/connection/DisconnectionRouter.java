package com.polidea.rxandroidble2.internal.connection;

import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.polidea.rxandroidble2.RxBleAdapterStateObservable;
import com.polidea.rxandroidble2.exceptions.BleDisconnectedException;
import com.polidea.rxandroidble2.exceptions.BleException;
import com.polidea.rxandroidble2.exceptions.BleGattException;
import com.polidea.rxandroidble2.internal.RxBleLog;
import com.polidea.rxandroidble2.internal.util.RxBleAdapterWrapper;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

@ConnectionScope
class DisconnectionRouter implements DisconnectionRouterInput, DisconnectionRouterOutput {
   private final BehaviorRelay<BleException> bleExceptionBehaviorRelay;
   private final Observable<Object> firstDisconnectionExceptionObs;
   private final Observable<BleException> firstDisconnectionValueObs;

   @Inject
   DisconnectionRouter(@Named("mac-address") String var1, RxBleAdapterWrapper var2, Observable<RxBleAdapterStateObservable.BleAdapterState> var3) {
      BehaviorRelay var4 = BehaviorRelay.create();
      this.bleExceptionBehaviorRelay = var4;
      Disposable var5 = awaitAdapterNotUsable(var2, var3).map(new Function<Boolean, BleException>(this, var1) {
         final DisconnectionRouter this$0;
         final String val$macAddress;

         {
            this.this$0 = var1;
            this.val$macAddress = var2x;
         }

         public BleException apply(Boolean var1) {
            return BleDisconnectedException.adapterDisabled(this.val$macAddress);
         }
      }).doOnNext(new Consumer<BleException>(this) {
         final DisconnectionRouter this$0;

         {
            this.this$0 = var1;
         }

         public void accept(BleException var1) {
            RxBleLog.v("An exception received, indicating that the adapter has became unusable.");
         }
      }).subscribe(var4, new Consumer<Throwable>(this) {
         final DisconnectionRouter this$0;

         {
            this.this$0 = var1;
         }

         public void accept(Throwable var1) {
            RxBleLog.e(var1, "Failed to monitor adapter state.");
         }
      });
      Observable var6 = var4.firstElement().toObservable().doOnTerminate(new Action(this, var5) {
         final DisconnectionRouter this$0;
         final Disposable val$adapterMonitoringDisposable;

         {
            this.this$0 = var1;
            this.val$adapterMonitoringDisposable = var2x;
         }

         @Override
         public void run() {
            this.val$adapterMonitoringDisposable.dispose();
         }
      }).replay().autoConnect(0);
      this.firstDisconnectionValueObs = var6;
      this.firstDisconnectionExceptionObs = var6.flatMap(new Function<BleException, ObservableSource<?>>(this) {
         final DisconnectionRouter this$0;

         {
            this.this$0 = var1;
         }

         public ObservableSource<?> apply(BleException var1) {
            return Observable.error(var1);
         }
      });
   }

   private static Observable<Boolean> awaitAdapterNotUsable(RxBleAdapterWrapper var0, Observable<RxBleAdapterStateObservable.BleAdapterState> var1) {
      return var1.map(new Function<RxBleAdapterStateObservable.BleAdapterState, Boolean>() {
         public Boolean apply(RxBleAdapterStateObservable.BleAdapterState var1) {
            return var1.isUsable();
         }
      }).startWith(var0.isBluetoothEnabled()).filter(new Predicate<Boolean>() {
         public boolean test(Boolean var1) {
            return var1 ^ true;
         }
      });
   }

   @Override
   public <T> Observable<T> asErrorOnlyObservable() {
      return (Observable<T>)this.firstDisconnectionExceptionObs;
   }

   @Override
   public Observable<BleException> asValueOnlyObservable() {
      return this.firstDisconnectionValueObs;
   }

   @Override
   public void onDisconnectedException(BleDisconnectedException var1) {
      this.bleExceptionBehaviorRelay.accept(var1);
   }

   @Override
   public void onGattConnectionStateException(BleGattException var1) {
      this.bleExceptionBehaviorRelay.accept(var1);
   }
}
