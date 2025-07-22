package com.signify.hue.flutterreactiveble.ble

import android.bluetooth.BluetoothGatt
import com.polidea.rxandroidble2.RxBleConnection
import com.polidea.rxandroidble2.RxBleDevice
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback
import com.signify.hue.flutterreactiveble.model.ConnectionState
import com.signify.hue.flutterreactiveble.model.ConnectionStateKt
import com.signify.hue.flutterreactiveble.utils.Duration
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import kotlin.jvm.functions.Function1

internal class DeviceConnector(device: RxBleDevice, connectionTimeout: Duration, updateListeners: (ConnectionUpdate) -> Unit, connectionQueue: ConnectionQueue) {
   private final val device: RxBleDevice
   private final val connectionTimeout: Duration
   private final val updateListeners: (ConnectionUpdate) -> Unit
   private final val connectionQueue: ConnectionQueue
   private final val connectDeviceSubject: BehaviorSubject<EstablishConnectionResult>
   private final var timestampEstablishConnection: Long
   internal final var connectionDisposable: Disposable?
   private final val lazyConnection: Lazy<BehaviorSubject<EstablishConnectionResult>>

   private final val currentConnection: EstablishConnectionResult?
      private final get() {
         val var1: EstablishConnectionResult;
         if (this.lazyConnection.isInitialized()) {
            var1 = this.getConnection$reactive_ble_mobile_release().getValue();
         } else {
            var1 = null;
         }

         return var1;
      }


   internal final val connection: BehaviorSubject<EstablishConnectionResult>
      internal final get() {
         return this.lazyConnection.getValue();
      }


   private final val connectionStatusUpdates: Disposable
      private final get() {
         return this.connectionStatusUpdates$delegate.getValue() as Disposable;
      }


   init {
      this.device = var1;
      this.connectionTimeout = var2;
      this.updateListeners = var3;
      this.connectionQueue = var4;
      val var5: BehaviorSubject = BehaviorSubject.create();
      this.connectDeviceSubject = var5;
      this.lazyConnection = LazyKt.lazy(new DeviceConnector$$ExternalSyntheticLambda22(this));
      this.connectionStatusUpdates$delegate = LazyKt.lazy(new DeviceConnector$$ExternalSyntheticLambda24(this));
   }

   private fun clearGattCache(connection: RxBleConnection): Completable {
      val var2: Completable = var1.queue(new DeviceConnector$$ExternalSyntheticLambda17()).ignoreElements();
      return var2;
   }

   @JvmStatic
   fun `clearGattCache$lambda$27`(var0: BluetoothGatt, var1: RxBleGattCallback, var2: Scheduler): Observable {
      try {
         val var5: Any = var0.getClass().getMethod("refresh", null).invoke(var0, null);
         if (var5 as java.lang.Boolean) {
            var4 = Observable.empty().delay(300L, TimeUnit.MILLISECONDS);
         } else {
            var4 = Observable.error(new RuntimeException("BluetoothGatt.refresh() returned false"));
         }
      } catch (var3: ReflectiveOperationException) {
         var4 = Observable.error(var3);
      }

      return var4;
   }

   private fun connectDevice(rxBleDevice: RxBleDevice, shouldNotTimeout: Boolean): Observable<RxBleConnection> {
      val var3: Observable = var1.establishConnection(var2)
         .compose(new DeviceConnector$$ExternalSyntheticLambda16(new DeviceConnector$$ExternalSyntheticLambda15(var2, this)));
      return var3;
   }

   @JvmStatic
   fun `connectDevice$lambda$24`(var0: Boolean, var1: DeviceConnector, var2: Observable): ObservableSource {
      if (!var0) {
         var2 = var2.timeout(
            Observable.timer(var1.connectionTimeout.getValue(), var1.connectionTimeout.getUnit()), new DeviceConnector$$ExternalSyntheticLambda18()
         );
      }

      return var2;
   }

   @JvmStatic
   fun `connectDevice$lambda$24$lambda$23`(var0: RxBleConnection): Observable {
      return Observable.never();
   }

   @JvmStatic
   fun `connectDevice$lambda$25`(var0: Function1, var1: Observable): ObservableSource {
      return var0.invoke(var1) as ObservableSource;
   }

   @JvmStatic
   fun `connectionStatusUpdates_delegate$lambda$7`(var0: DeviceConnector): Disposable {
      return var0.device
         .observeConnectionStateChanges()
         .startWith(var0.device.getConnectionState())
         .map(new DeviceConnector$$ExternalSyntheticLambda8(new DeviceConnector$$ExternalSyntheticLambda7(var0)))
         .onErrorReturn(new DeviceConnector$$ExternalSyntheticLambda10(new DeviceConnector$$ExternalSyntheticLambda9(var0)))
         .subscribe(new DeviceConnector$$ExternalSyntheticLambda13(new DeviceConnector$$ExternalSyntheticLambda12(var0)));
   }

   @JvmStatic
   fun `connectionStatusUpdates_delegate$lambda$7$lambda$1`(var0: DeviceConnector, var1: RxBleConnection.RxBleConnectionState): ConnectionUpdate {
      val var2: java.lang.String = var0.device.getMacAddress();
      return new ConnectionUpdateSuccess(var2, ConnectionStateKt.toConnectionState(var1).getCode());
   }

   @JvmStatic
   fun `connectionStatusUpdates_delegate$lambda$7$lambda$2`(var0: Function1, var1: Any): ConnectionUpdate {
      return var0.invoke(var1) as ConnectionUpdate;
   }

   @JvmStatic
   fun `connectionStatusUpdates_delegate$lambda$7$lambda$3`(var0: DeviceConnector, var1: java.lang.Throwable): ConnectionUpdate {
      val var2: java.lang.String = var0.device.getMacAddress();
      val var4: java.lang.String = var1.getMessage();
      var var3: java.lang.String = var4;
      if (var4 == null) {
         var3 = "Unknown error";
      }

      return new ConnectionUpdateError(var2, var3);
   }

   @JvmStatic
   fun `connectionStatusUpdates_delegate$lambda$7$lambda$4`(var0: Function1, var1: Any): ConnectionUpdate {
      return var0.invoke(var1) as ConnectionUpdate;
   }

   @JvmStatic
   fun `connectionStatusUpdates_delegate$lambda$7$lambda$5`(var0: DeviceConnector, var1: ConnectionUpdate): Unit {
      val var2: Function1 = var0.updateListeners;
      var2.invoke(var1);
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `connectionStatusUpdates_delegate$lambda$7$lambda$6`(var0: Function1, var1: Any) {
      var0.invoke(var1);
   }

   @JvmStatic
   fun `disconnectDevice$lambda$8`(var0: DeviceConnector, var1: java.lang.String) {
      var0.sendDisconnectedUpdate(var1);
      var0.disposeSubscriptions();
   }

   private fun disposeSubscriptions() {
      if (this.connectionDisposable != null) {
         this.connectionDisposable.dispose();
      }

      this.connectDeviceSubject.onComplete();
      this.getConnectionStatusUpdates().dispose();
   }

   private fun establishConnection(rxBleDevice: RxBleDevice): Disposable {
      val var3: java.lang.String = var1.getMacAddress();
      val var2: Boolean;
      if (this.connectionTimeout.getValue() <= 0L) {
         var2 = true;
      } else {
         var2 = false;
      }

      val var4: ConnectionQueue = this.connectionQueue;
      var4.addToQueue(var3);
      this.updateListeners.invoke(new ConnectionUpdateSuccess(var3, ConnectionState.CONNECTING.getCode()));
      val var5: Disposable = this.waitUntilFirstOfQueue(var3)
         .switchMap(new DeviceConnector$$ExternalSyntheticLambda28(new DeviceConnector$$ExternalSyntheticLambda25(var3, this, var1, var2)))
         .onErrorReturn(new DeviceConnector$$ExternalSyntheticLambda30(new DeviceConnector$$ExternalSyntheticLambda29(var1)))
         .doOnNext(new DeviceConnector$$ExternalSyntheticLambda2(new DeviceConnector$$ExternalSyntheticLambda1(this, var3)))
         .doOnError(new DeviceConnector$$ExternalSyntheticLambda4(new DeviceConnector$$ExternalSyntheticLambda3(this, var3)))
         .subscribe(
            new DeviceConnector$$ExternalSyntheticLambda6(new DeviceConnector$$ExternalSyntheticLambda5(this)),
            new DeviceConnector$$ExternalSyntheticLambda27(new DeviceConnector$$ExternalSyntheticLambda26(this))
         );
      return var5;
   }

   @JvmStatic
   fun `establishConnection$lambda$11`(var0: java.lang.String, var1: DeviceConnector, var2: RxBleDevice, var3: Boolean, var4: java.util.List): ObservableSource {
      val var5: Observable;
      if (!var4.contains(var0)) {
         var5 = Observable.just(new EstablishConnectionFailure(var0, "Device is not in queue"));
      } else {
         var5 = var1.connectDevice(var2, var3).map(new DeviceConnector$$ExternalSyntheticLambda11(new DeviceConnector$$ExternalSyntheticLambda0(var2)));
      }

      return var5;
   }

   @JvmStatic
   fun `establishConnection$lambda$11$lambda$10`(var0: Function1, var1: Any): EstablishConnectionResult {
      return var0.invoke(var1) as EstablishConnectionResult;
   }

   @JvmStatic
   fun `establishConnection$lambda$11$lambda$9`(var0: RxBleDevice, var1: RxBleConnection): EstablishConnectionResult {
      val var2: java.lang.String = var0.getMacAddress();
      return new EstablishedConnection(var2, var1);
   }

   @JvmStatic
   fun `establishConnection$lambda$12`(var0: Function1, var1: Any): ObservableSource {
      return var0.invoke(var1) as ObservableSource;
   }

   @JvmStatic
   fun `establishConnection$lambda$13`(var0: RxBleDevice, var1: java.lang.Throwable): EstablishConnectionResult {
      val var2: java.lang.String = var0.getMacAddress();
      val var4: java.lang.String = var1.getMessage();
      var var3: java.lang.String = var4;
      if (var4 == null) {
         var3 = "Unknown error";
      }

      return new EstablishConnectionFailure(var2, var3);
   }

   @JvmStatic
   fun `establishConnection$lambda$14`(var0: Function1, var1: Any): EstablishConnectionResult {
      return var0.invoke(var1) as EstablishConnectionResult;
   }

   @JvmStatic
   fun `establishConnection$lambda$15`(var0: DeviceConnector, var1: java.lang.String, var2: EstablishConnectionResult): Unit {
      var0.getConnectionStatusUpdates();
      var0.timestampEstablishConnection = System.currentTimeMillis();
      val var3: ConnectionQueue = var0.connectionQueue;
      var3.removeFromQueue(var1);
      if (var2 is EstablishConnectionFailure) {
         var0.updateListeners.invoke(new ConnectionUpdateError(var1, (var2 as EstablishConnectionFailure).getErrorMessage()));
      }

      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `establishConnection$lambda$16`(var0: Function1, var1: Any) {
      var0.invoke(var1);
   }

   @JvmStatic
   fun `establishConnection$lambda$17`(var0: DeviceConnector, var1: java.lang.String, var2: java.lang.Throwable): Unit {
      val var3: ConnectionQueue = var0.connectionQueue;
      var3.removeFromQueue(var1);
      val var6: Function1 = var0.updateListeners;
      val var5: java.lang.String = var2.getMessage();
      var var4: java.lang.String = var5;
      if (var5 == null) {
         var4 = "Unknown error";
      }

      var6.invoke(new ConnectionUpdateError(var1, var4));
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `establishConnection$lambda$18`(var0: Function1, var1: Any) {
      var0.invoke(var1);
   }

   @JvmStatic
   fun `establishConnection$lambda$19`(var0: DeviceConnector, var1: EstablishConnectionResult): Unit {
      var0.connectDeviceSubject.onNext(var1);
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `establishConnection$lambda$20`(var0: Function1, var1: Any) {
      var0.invoke(var1);
   }

   @JvmStatic
   fun `establishConnection$lambda$21`(var0: DeviceConnector, var1: java.lang.Throwable): Unit {
      var0.connectDeviceSubject.onError(var1);
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `establishConnection$lambda$22`(var0: Function1, var1: Any) {
      var0.invoke(var1);
   }

   @JvmStatic
   fun `getConnection$reactive_ble_mobile_release$delegate`(var0: DeviceConnector): Any {
      return var0.lazyConnection;
   }

   @JvmStatic
   fun `lazyConnection$lambda$0`(var0: DeviceConnector): BehaviorSubject {
      var0.connectionDisposable = var0.establishConnection(var0.device);
      return var0.connectDeviceSubject;
   }

   private fun sendDisconnectedUpdate(deviceId: String) {
      this.updateListeners.invoke(new ConnectionUpdateSuccess(var1, ConnectionState.DISCONNECTED.getCode()));
   }

   private fun waitUntilFirstOfQueue(deviceId: String): Observable<List<String>> {
      return this.connectionQueue
         .observeQueue()
         .filter(new DeviceConnector$$ExternalSyntheticLambda20(new DeviceConnector$$ExternalSyntheticLambda19(var1)))
         .takeUntil(new DeviceConnector$$ExternalSyntheticLambda23(new DeviceConnector$$ExternalSyntheticLambda21(var1)));
   }

   @JvmStatic
   fun `waitUntilFirstOfQueue$lambda$28`(var0: java.lang.String, var1: java.util.List): Boolean {
      val var2: Boolean;
      if (!(CollectionsKt.firstOrNull(var1) == var0) && var1.contains(var0)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   @JvmStatic
   fun `waitUntilFirstOfQueue$lambda$29`(var0: Function1, var1: Any): Boolean {
      return var0.invoke(var1) as java.lang.Boolean;
   }

   @JvmStatic
   fun `waitUntilFirstOfQueue$lambda$30`(var0: java.lang.String, var1: java.util.List): Boolean {
      val var2: Boolean;
      if (!var1.isEmpty() && !(CollectionsKt.first(var1) == var0)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   @JvmStatic
   fun `waitUntilFirstOfQueue$lambda$31`(var0: Function1, var1: Any): Boolean {
      return var0.invoke(var1) as java.lang.Boolean;
   }

   internal fun clearGattCache(): Completable {
      val var1: EstablishConnectionResult = this.getCurrentConnection();
      if (var1 != null) {
         val var3: Completable;
         if (var1 is EstablishedConnection) {
            var3 = this.clearGattCache((var1 as EstablishedConnection).getRxConnection());
         } else {
            if (var1 !is EstablishConnectionFailure) {
               throw new NoWhenBranchMatchedException();
            }

            var3 = Completable.error(new java.lang.Throwable((var1 as EstablishConnectionFailure).getErrorMessage()));
         }

         if (var3 != null) {
            return var3;
         }
      }

      val var2: Completable = Completable.error(new IllegalStateException("Connection is not established"));
      return var2;
   }

   internal fun disconnectDevice(deviceId: String) {
      val var2: Long = System.currentTimeMillis() - this.timestampEstablishConnection;
      if (var2 < 200L) {
         Single.timer(200L - var2, TimeUnit.MILLISECONDS).doFinally(new DeviceConnector$$ExternalSyntheticLambda14(this, var1)).subscribe();
      } else {
         this.sendDisconnectedUpdate(var1);
         this.disposeSubscriptions();
      }
   }

   internal fun readRssi(): Single<Int> {
      val var1: EstablishConnectionResult = this.getCurrentConnection();
      if (var1 != null) {
         val var3: Single;
         if (var1 is EstablishedConnection) {
            var3 = (var1 as EstablishedConnection).getRxConnection().readRssi();
         } else {
            if (var1 !is EstablishConnectionFailure) {
               throw new NoWhenBranchMatchedException();
            }

            var3 = Single.error(new java.lang.Throwable((var1 as EstablishConnectionFailure).getErrorMessage()));
         }

         if (var3 != null) {
            return var3;
         }
      }

      val var2: Single = Single.error(new IllegalStateException("Connection is not established"));
      return var2;
   }

   public companion object {
      private const val minTimeMsBeforeDisconnectingIsAllowed: Long
      private const val delayMsAfterClearingCache: Long
   }
}
