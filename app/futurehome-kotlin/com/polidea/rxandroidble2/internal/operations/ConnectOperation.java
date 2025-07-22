package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.os.DeadObjectException;
import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.exceptions.BleDisconnectedException;
import com.polidea.rxandroidble2.exceptions.BleException;
import com.polidea.rxandroidble2.exceptions.BleGattCallbackTimeoutException;
import com.polidea.rxandroidble2.exceptions.BleGattOperationType;
import com.polidea.rxandroidble2.internal.QueueOperation;
import com.polidea.rxandroidble2.internal.connection.BluetoothGattProvider;
import com.polidea.rxandroidble2.internal.connection.ConnectionStateChangeListener;
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback;
import com.polidea.rxandroidble2.internal.logger.LoggerUtil;
import com.polidea.rxandroidble2.internal.serialization.QueueReleaseInterface;
import com.polidea.rxandroidble2.internal.util.BleConnectionCompat;
import com.polidea.rxandroidble2.internal.util.DisposableUtil;
import io.reactivex.ObservableEmitter;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleTransformer;
import io.reactivex.functions.Action;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableSingleObserver;
import java.util.concurrent.Callable;

public class ConnectOperation extends QueueOperation<BluetoothGatt> {
   final boolean autoConnect;
   final BluetoothDevice bluetoothDevice;
   final BluetoothGattProvider bluetoothGattProvider;
   final TimeoutConfiguration connectTimeout;
   final BleConnectionCompat connectionCompat;
   final ConnectionStateChangeListener connectionStateChangedAction;
   final RxBleGattCallback rxBleGattCallback;

   @Inject
   ConnectOperation(
      BluetoothDevice var1,
      BleConnectionCompat var2,
      RxBleGattCallback var3,
      BluetoothGattProvider var4,
      @Named("connect-timeout") TimeoutConfiguration var5,
      @Named("autoConnect") boolean var6,
      ConnectionStateChangeListener var7
   ) {
      this.bluetoothDevice = var1;
      this.connectionCompat = var2;
      this.rxBleGattCallback = var3;
      this.bluetoothGattProvider = var4;
      this.connectTimeout = var5;
      this.autoConnect = var6;
      this.connectionStateChangedAction = var7;
   }

   private Single<BluetoothGatt> getConnectedBluetoothGatt() {
      return Single.create(
         new SingleOnSubscribe<BluetoothGatt>(this) {
            final ConnectOperation this$0;

            {
               this.this$0 = var1;
            }

            @Override
            public void subscribe(SingleEmitter<BluetoothGatt> var1) {
               var1.setDisposable(
                  (DisposableSingleObserver)this.this$0
                     .getBluetoothGattAndChangeStatusToConnected()
                     .delaySubscription(
                        this.this$0.rxBleGattCallback.getOnConnectionStateChange().filter(new Predicate<RxBleConnection.RxBleConnectionState>(this) {
                           final <unrepresentable> this$1;

                           {
                              this.this$1 = var1;
                           }

                           public boolean test(RxBleConnection.RxBleConnectionState var1) {
                              boolean var2;
                              if (var1 == RxBleConnection.RxBleConnectionState.CONNECTED) {
                                 var2 = true;
                              } else {
                                 var2 = false;
                              }

                              return var2;
                           }
                        })
                     )
                     .mergeWith(this.this$0.rxBleGattCallback.observeDisconnect().firstOrError())
                     .firstOrError()
                     .subscribeWith(DisposableUtil.disposableSingleObserverFromEmitter(var1))
               );
               this.this$0.connectionStateChangedAction.onConnectionStateChange(RxBleConnection.RxBleConnectionState.CONNECTING);
               BluetoothGatt var2 = this.this$0
                  .connectionCompat
                  .connectGatt(this.this$0.bluetoothDevice, this.this$0.autoConnect, this.this$0.rxBleGattCallback.getBluetoothGattCallback());
               this.this$0.bluetoothGattProvider.updateBluetoothGatt(var2);
            }
         }
      );
   }

   private SingleTransformer<BluetoothGatt, BluetoothGatt> wrapWithTimeoutWhenNotAutoconnecting() {
      return new SingleTransformer<BluetoothGatt, BluetoothGatt>(this) {
         final ConnectOperation this$0;

         {
            this.this$0 = var1;
         }

         public Single<BluetoothGatt> apply(Single<BluetoothGatt> var1) {
            if (!this.this$0.autoConnect) {
               var1 = var1.timeout(
                  this.this$0.connectTimeout.timeout,
                  this.this$0.connectTimeout.timeoutTimeUnit,
                  this.this$0.connectTimeout.timeoutScheduler,
                  this.this$0.prepareConnectionTimeoutError()
               );
            }

            return var1;
         }
      };
   }

   Single<BluetoothGatt> getBluetoothGattAndChangeStatusToConnected() {
      return Single.fromCallable(new Callable<BluetoothGatt>(this) {
         final ConnectOperation this$0;

         {
            this.this$0 = var1;
         }

         public BluetoothGatt call() {
            this.this$0.connectionStateChangedAction.onConnectionStateChange(RxBleConnection.RxBleConnectionState.CONNECTED);
            return this.this$0.bluetoothGattProvider.getBluetoothGatt();
         }
      });
   }

   Single<BluetoothGatt> prepareConnectionTimeoutError() {
      return Single.fromCallable(new Callable<BluetoothGatt>(this) {
         final ConnectOperation this$0;

         {
            this.this$0 = var1;
         }

         public BluetoothGatt call() {
            throw new BleGattCallbackTimeoutException(this.this$0.bluetoothGattProvider.getBluetoothGatt(), BleGattOperationType.CONNECTION_STATE);
         }
      });
   }

   @Override
   protected void protectedRun(ObservableEmitter<BluetoothGatt> var1, QueueReleaseInterface var2) {
      Action var3 = new Action(this, var2) {
         final ConnectOperation this$0;
         final QueueReleaseInterface val$queueReleaseInterface;

         {
            this.this$0 = var1;
            this.val$queueReleaseInterface = var2x;
         }

         @Override
         public void run() {
            this.val$queueReleaseInterface.release();
         }
      };
      var1.setDisposable(
         (DisposableSingleObserver)this.getConnectedBluetoothGatt()
            .compose(this.wrapWithTimeoutWhenNotAutoconnecting())
            .doFinally(var3)
            .subscribeWith(DisposableUtil.disposableSingleObserverFromEmitter(var1))
      );
      if (this.autoConnect) {
         var2.release();
      }
   }

   @Override
   protected BleException provideException(DeadObjectException var1) {
      return new BleDisconnectedException(var1, this.bluetoothDevice.getAddress(), -1);
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ConnectOperation{");
      var1.append(LoggerUtil.commonMacMessage(this.bluetoothDevice.getAddress()));
      var1.append(", autoConnect=");
      var1.append(this.autoConnect);
      var1.append('}');
      return var1.toString();
   }
}
