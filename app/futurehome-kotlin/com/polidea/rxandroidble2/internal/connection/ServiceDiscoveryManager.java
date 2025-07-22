package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattService;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.RxBleDeviceServices;
import com.polidea.rxandroidble2.internal.operations.OperationsProvider;
import com.polidea.rxandroidble2.internal.operations.ServiceDiscoveryOperation;
import com.polidea.rxandroidble2.internal.operations.TimeoutConfiguration;
import com.polidea.rxandroidble2.internal.serialization.ConnectionOperationQueue;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@ConnectionScope
class ServiceDiscoveryManager {
   final BluetoothGatt bluetoothGatt;
   private Single<RxBleDeviceServices> deviceServicesObservable;
   boolean hasCachedResults;
   final OperationsProvider operationProvider;
   final ConnectionOperationQueue operationQueue;
   final Subject<TimeoutConfiguration> timeoutBehaviorSubject = BehaviorSubject.<TimeoutConfiguration>create().toSerialized();

   @Inject
   ServiceDiscoveryManager(ConnectionOperationQueue var1, BluetoothGatt var2, OperationsProvider var3) {
      this.hasCachedResults = false;
      this.operationQueue = var1;
      this.bluetoothGatt = var2;
      this.operationProvider = var3;
      this.reset();
   }

   private Maybe<List<BluetoothGattService>> getListOfServicesFromGatt() {
      return Single.fromCallable(new Callable<List<BluetoothGattService>>(this) {
         final ServiceDiscoveryManager this$0;

         {
            this.this$0 = var1;
         }

         public List<BluetoothGattService> call() {
            return this.this$0.bluetoothGatt.getServices();
         }
      }).filter(new Predicate<List<BluetoothGattService>>(this) {
         final ServiceDiscoveryManager this$0;

         {
            this.this$0 = var1;
         }

         public boolean test(List<BluetoothGattService> var1) {
            boolean var2;
            if (var1.size() > 0) {
               var2 = true;
            } else {
               var2 = false;
            }

            return var2;
         }
      });
   }

   private Single<TimeoutConfiguration> getTimeoutConfiguration() {
      return this.timeoutBehaviorSubject.firstOrError();
   }

   private Function<TimeoutConfiguration, Single<RxBleDeviceServices>> scheduleActualDiscoveryWithTimeout() {
      return new Function<TimeoutConfiguration, Single<RxBleDeviceServices>>(this) {
         final ServiceDiscoveryManager this$0;

         {
            this.this$0 = var1;
         }

         public Single<RxBleDeviceServices> apply(TimeoutConfiguration var1) {
            ServiceDiscoveryOperation var2 = this.this$0.operationProvider.provideServiceDiscoveryOperation(var1.timeout, var1.timeoutTimeUnit);
            return this.this$0.operationQueue.queue(var2).firstOrError();
         }
      };
   }

   private static Function<List<BluetoothGattService>, RxBleDeviceServices> wrapIntoRxBleDeviceServices() {
      return new Function<List<BluetoothGattService>, RxBleDeviceServices>() {
         public RxBleDeviceServices apply(List<BluetoothGattService> var1) {
            return new RxBleDeviceServices(var1);
         }
      };
   }

   Single<RxBleDeviceServices> getDiscoverServicesSingle(long var1, TimeUnit var3) {
      return this.hasCachedResults ? this.deviceServicesObservable : this.deviceServicesObservable.doOnSubscribe(new Consumer<Disposable>(this, var1, var3) {
         final ServiceDiscoveryManager this$0;
         final long val$timeout;
         final TimeUnit val$timeoutTimeUnit;

         {
            this.this$0 = var1;
            this.val$timeout = var2;
            this.val$timeoutTimeUnit = var4;
         }

         public void accept(Disposable var1) {
            this.this$0.timeoutBehaviorSubject.onNext(new TimeoutConfiguration(this.val$timeout, this.val$timeoutTimeUnit, Schedulers.computation()));
         }
      });
   }

   void reset() {
      this.hasCachedResults = false;
      this.deviceServicesObservable = this.getListOfServicesFromGatt()
         .map(wrapIntoRxBleDeviceServices())
         .switchIfEmpty(this.getTimeoutConfiguration().flatMap(this.scheduleActualDiscoveryWithTimeout()))
         .doOnSuccess(Functions.actionConsumer(new Action(this) {
            final ServiceDiscoveryManager this$0;

            {
               this.this$0 = var1;
            }

            @Override
            public void run() {
               this.this$0.hasCachedResults = true;
            }
         }))
         .doOnError(Functions.actionConsumer(new Action(this) {
            final ServiceDiscoveryManager this$0;

            {
               this.this$0 = var1;
            }

            @Override
            public void run() {
               this.this$0.reset();
            }
         }))
         .cache();
   }
}
