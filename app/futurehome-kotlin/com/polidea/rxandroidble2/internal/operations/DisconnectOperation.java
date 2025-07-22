package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.os.DeadObjectException;
import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.exceptions.BleDisconnectedException;
import com.polidea.rxandroidble2.exceptions.BleException;
import com.polidea.rxandroidble2.internal.QueueOperation;
import com.polidea.rxandroidble2.internal.RxBleLog;
import com.polidea.rxandroidble2.internal.connection.BluetoothGattProvider;
import com.polidea.rxandroidble2.internal.connection.ConnectionStateChangeListener;
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback;
import com.polidea.rxandroidble2.internal.logger.LoggerUtil;
import com.polidea.rxandroidble2.internal.serialization.QueueReleaseInterface;
import io.reactivex.Emitter;
import io.reactivex.ObservableEmitter;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class DisconnectOperation extends QueueOperation<Void> {
   private final BluetoothGattProvider bluetoothGattProvider;
   private final Scheduler bluetoothInteractionScheduler;
   private final BluetoothManager bluetoothManager;
   private final ConnectionStateChangeListener connectionStateChangeListener;
   private final String macAddress;
   private final RxBleGattCallback rxBleGattCallback;
   private final TimeoutConfiguration timeoutConfiguration;

   @Inject
   DisconnectOperation(
      RxBleGattCallback var1,
      BluetoothGattProvider var2,
      @Named("mac-address") String var3,
      BluetoothManager var4,
      @Named("bluetooth_interaction") Scheduler var5,
      @Named("disconnect-timeout") TimeoutConfiguration var6,
      ConnectionStateChangeListener var7
   ) {
      this.rxBleGattCallback = var1;
      this.bluetoothGattProvider = var2;
      this.macAddress = var3;
      this.bluetoothManager = var4;
      this.bluetoothInteractionScheduler = var5;
      this.timeoutConfiguration = var6;
      this.connectionStateChangeListener = var7;
   }

   private Single<BluetoothGatt> disconnect(BluetoothGatt var1) {
      return new DisconnectOperation.DisconnectGattObservable(var1, this.rxBleGattCallback, this.bluetoothInteractionScheduler)
         .timeout(this.timeoutConfiguration.timeout, this.timeoutConfiguration.timeoutTimeUnit, this.timeoutConfiguration.timeoutScheduler, Single.just(var1));
   }

   private Single<BluetoothGatt> disconnectIfRequired(BluetoothGatt var1) {
      Single var2;
      if (this.isDisconnected(var1)) {
         var2 = Single.just(var1);
      } else {
         var2 = this.disconnect(var1);
      }

      return var2;
   }

   private boolean isDisconnected(BluetoothGatt var1) {
      boolean var2;
      if (this.bluetoothManager.getConnectionState(var1.getDevice(), 7) == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   void considerGattDisconnected(Emitter<Void> var1, QueueReleaseInterface var2) {
      this.connectionStateChangeListener.onConnectionStateChange(RxBleConnection.RxBleConnectionState.DISCONNECTED);
      var2.release();
      var1.onComplete();
   }

   @Override
   protected void protectedRun(ObservableEmitter<Void> var1, QueueReleaseInterface var2) {
      this.connectionStateChangeListener.onConnectionStateChange(RxBleConnection.RxBleConnectionState.DISCONNECTING);
      BluetoothGatt var3 = this.bluetoothGattProvider.getBluetoothGatt();
      if (var3 == null) {
         RxBleLog.w("Disconnect operation has been executed but GATT instance was null - considering disconnected.");
         this.considerGattDisconnected(var1, var2);
      } else {
         this.disconnectIfRequired(var3).observeOn(this.bluetoothInteractionScheduler).subscribe(new SingleObserver<BluetoothGatt>(this, var1, var2) {
            final DisconnectOperation this$0;
            final ObservableEmitter val$emitter;
            final QueueReleaseInterface val$queueReleaseInterface;

            {
               this.this$0 = var1;
               this.val$emitter = var2x;
               this.val$queueReleaseInterface = var3x;
            }

            @Override
            public void onError(Throwable var1) {
               RxBleLog.w(var1, "Disconnect operation has been executed but finished with an error - considering disconnected.");
               this.this$0.considerGattDisconnected(this.val$emitter, this.val$queueReleaseInterface);
            }

            @Override
            public void onSubscribe(Disposable var1) {
            }

            public void onSuccess(BluetoothGatt var1) {
               var1.close();
               this.this$0.considerGattDisconnected(this.val$emitter, this.val$queueReleaseInterface);
            }
         });
      }
   }

   @Override
   protected BleException provideException(DeadObjectException var1) {
      return new BleDisconnectedException(var1, this.macAddress, -1);
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("DisconnectOperation{");
      var1.append(LoggerUtil.commonMacMessage(this.macAddress));
      var1.append('}');
      return var1.toString();
   }

   private static class DisconnectGattObservable extends Single<BluetoothGatt> {
      final BluetoothGatt bluetoothGatt;
      private final Scheduler disconnectScheduler;
      private final RxBleGattCallback rxBleGattCallback;

      DisconnectGattObservable(BluetoothGatt var1, RxBleGattCallback var2, Scheduler var3) {
         this.bluetoothGatt = var1;
         this.rxBleGattCallback = var2;
         this.disconnectScheduler = var3;
      }

      @Override
      protected void subscribeActual(SingleObserver<? super BluetoothGatt> var1) {
         this.rxBleGattCallback.getOnConnectionStateChange().filter(new Predicate<RxBleConnection.RxBleConnectionState>(this) {
            final DisconnectOperation.DisconnectGattObservable this$0;

            {
               this.this$0 = var1;
            }

            public boolean test(RxBleConnection.RxBleConnectionState var1) {
               boolean var2;
               if (var1 == RxBleConnection.RxBleConnectionState.DISCONNECTED) {
                  var2 = true;
               } else {
                  var2 = false;
               }

               return var2;
            }
         }).firstOrError().map(new Function<RxBleConnection.RxBleConnectionState, BluetoothGatt>(this) {
            final DisconnectOperation.DisconnectGattObservable this$0;

            {
               this.this$0 = var1;
            }

            public BluetoothGatt apply(RxBleConnection.RxBleConnectionState var1) {
               return this.this$0.bluetoothGatt;
            }
         }).subscribe(var1);
         this.disconnectScheduler.createWorker().schedule(new Runnable(this) {
            final DisconnectOperation.DisconnectGattObservable this$0;

            {
               this.this$0 = var1;
            }

            @Override
            public void run() {
               this.this$0.bluetoothGatt.disconnect();
            }
         });
      }
   }
}
