package com.signify.hue.flutterreactiveble.ble

import android.bluetooth.BluetoothGattCharacteristic
import android.content.Context
import android.os.ParcelUuid
import android.os.Build.VERSION
import com.polidea.rxandroidble2.LogOptions
import com.polidea.rxandroidble2.NotificationSetupMode
import com.polidea.rxandroidble2.RxBleClient
import com.polidea.rxandroidble2.RxBleConnection
import com.polidea.rxandroidble2.RxBleDevice
import com.polidea.rxandroidble2.RxBleDeviceServices
import com.polidea.rxandroidble2.scan.IsConnectable
import com.polidea.rxandroidble2.scan.ScanFilter
import com.polidea.rxandroidble2.scan.ScanResult
import com.polidea.rxandroidble2.scan.ScanSettings
import com.signify.hue.flutterreactiveble.ble.extensions.RxBleConnectionExtensionKt
import com.signify.hue.flutterreactiveble.converters.ManufacturerDataConverterKt
import com.signify.hue.flutterreactiveble.model.ScanMode
import com.signify.hue.flutterreactiveble.model.ScanModeKt
import com.signify.hue.flutterreactiveble.utils.BleWrapperExtensionsKt
import com.signify.hue.flutterreactiveble.utils.Duration
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import java.util.ArrayList
import java.util.Arrays
import java.util.LinkedHashMap
import java.util.UUID
import java.util.Map.Entry
import java.util.concurrent.TimeUnit
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function3

public open class ReactiveBleClient(context: Context) : BleClient {
   private final val context: Context
   private final val connectionQueue: ConnectionQueue
   private final val allConnections: CompositeDisposable

   public open val connectionUpdateSubject: BehaviorSubject<ConnectionUpdate>
      public open get() {
         return connectionUpdateBehaviorSubject;
      }


   @JvmStatic
   fun {
      val var0: BehaviorSubject = BehaviorSubject.create();
      connectionUpdateBehaviorSubject = var0;
   }

   init {
      this.context = var1;
      this.connectionQueue = new ConnectionQueue();
      this.allConnections = new CompositeDisposable();
   }

   @JvmStatic
   fun `connectToDevice$lambda$5`(var0: java.lang.String, var1: EstablishConnectionResult): Unit {
      if (var1 !is EstablishedConnection) {
         if (var1 !is EstablishConnectionFailure) {
            throw new NoWhenBranchMatchedException();
         }

         connectionUpdateBehaviorSubject.onNext(new ConnectionUpdateError(var0, (var1 as EstablishConnectionFailure).getErrorMessage()));
      }

      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `connectToDevice$lambda$6`(var0: Function1, var1: Any) {
      var0.invoke(var1);
   }

   @JvmStatic
   fun `connectToDevice$lambda$7`(var0: java.lang.String, var1: java.lang.Throwable): Unit {
      label11: {
         if (var1 != null) {
            val var2: java.lang.String = var1.getMessage();
            var4 = var2;
            if (var2 != null) {
               break label11;
            }
         }

         var4 = "unknown error";
      }

      connectionUpdateBehaviorSubject.onNext(new ConnectionUpdateError(var0, var4));
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `connectToDevice$lambda$8`(var0: Function1, var1: Any) {
      var0.invoke(var1);
   }

   @JvmStatic
   fun `discoverServices$lambda$10`(var0: EstablishConnectionResult): SingleSource {
      val var3: Single;
      if (var0 is EstablishedConnection) {
         val var1: RxBleClient = Companion.getRxBleClient();
         val var2: EstablishedConnection = var0 as EstablishedConnection;
         if (var1.getBleDevice((var0 as EstablishedConnection).getDeviceId()).getBluetoothDevice().getBondState() == 11) {
            var3 = Single.error(new Exception("Bonding is in progress wait for bonding to be finished before executing more operations on the device"));
         } else {
            var3 = var2.getRxConnection().discoverServices();
         }
      } else {
         if (var0 !is EstablishConnectionFailure) {
            throw new NoWhenBranchMatchedException();
         }

         var3 = Single.error(new Exception((var0 as EstablishConnectionFailure).getErrorMessage()));
      }

      return var3;
   }

   @JvmStatic
   fun `discoverServices$lambda$11`(var0: Function1, var1: Any): SingleSource {
      return var0.invoke(var1) as SingleSource;
   }

   private fun enableDebugLogging() {
      val var1: LogOptions.Builder = new LogOptions.Builder();
      val var2: Int = 2;
      RxBleClient.updateLogOptions(var1.setLogLevel(var2).setMacAddressLogSetting(var2).setUuidsLogSetting(var2).setShouldLogAttributeValues(true).build());
   }

   private fun executeWriteOperation(
      deviceId: String,
      characteristicId: UUID,
      characteristicInstanceId: Int,
      value: ByteArray,
      bleOperation: (RxBleConnection, BluetoothGattCharacteristic, ByteArray) -> Single<ByteArray>
   ): Single<CharOperationResult> {
      val var6: Single = getConnection$default(this, var1, null, 2, null)
         .<CharOperationFailed>flatMapSingle(
            new ReactiveBleClient$$ExternalSyntheticLambda10(new ReactiveBleClient$$ExternalSyntheticLambda9(var2, var3, var1, var5, var4))
         )
         .first(new CharOperationFailed(var1, "Writechar timed-out"));
      return var6;
   }

   @JvmStatic
   fun `executeWriteOperation$lambda$35`(var0: UUID, var1: Int, var2: java.lang.String, var3: Function3, var4: ByteArray, var5: EstablishConnectionResult): SingleSource {
      val var6: Single;
      if (var5 is EstablishedConnection) {
         var6 = RxBleConnectionExtensionKt.resolveCharacteristic((var5 as EstablishedConnection).getRxConnection(), var0, var1)
            .flatMap(new ReactiveBleClient$$ExternalSyntheticLambda35(new ReactiveBleClient$$ExternalSyntheticLambda34(var3, var5, var4, var2)));
      } else {
         if (var5 !is EstablishConnectionFailure) {
            throw new NoWhenBranchMatchedException();
         }

         val var7: StringBuilder = new StringBuilder("failed to connect ");
         var7.append((var5 as EstablishConnectionFailure).getErrorMessage());
         var6 = Single.just(new CharOperationFailed(var2, var7.toString()));
      }

      return var6;
   }

   @JvmStatic
   fun `executeWriteOperation$lambda$35$lambda$33`(
      var0: Function3, var1: EstablishConnectionResult, var2: ByteArray, var3: java.lang.String, var4: BluetoothGattCharacteristic
   ): SingleSource {
      return (var0.invoke((var1 as EstablishedConnection).getRxConnection(), var4, var2) as Single)
         .map(new ReactiveBleClient$$ExternalSyntheticLambda24(new ReactiveBleClient$$ExternalSyntheticLambda23(var3)));
   }

   @JvmStatic
   fun `executeWriteOperation$lambda$35$lambda$33$lambda$31`(var0: java.lang.String, var1: ByteArray): CharOperationSuccessful {
      return new CharOperationSuccessful(var0, ArraysKt.asList(var1));
   }

   @JvmStatic
   fun `executeWriteOperation$lambda$35$lambda$33$lambda$32`(var0: Function1, var1: Any): CharOperationSuccessful {
      return var0.invoke(var1) as CharOperationSuccessful;
   }

   @JvmStatic
   fun `executeWriteOperation$lambda$35$lambda$34`(var0: Function1, var1: Any): SingleSource {
      return var0.invoke(var1) as SingleSource;
   }

   @JvmStatic
   fun `executeWriteOperation$lambda$36`(var0: Function1, var1: Any): SingleSource {
      return var0.invoke(var1) as SingleSource;
   }

   private fun getConnection(deviceId: String, timeout: Duration = new Duration(0L, TimeUnit.MILLISECONDS)): Observable<EstablishConnectionResult> {
      val var6: RxBleDevice = Companion.getRxBleClient().getBleDevice(var1);
      val var5: java.util.Map = activeConnections;
      val var4: Any = activeConnections.get(var1);
      var var3: Any = var4;
      if (var4 == null) {
         var3 = this.createDeviceConnector$reactive_ble_mobile_release(var6, var2);
         var5.put(var1, var3);
      }

      return (var3 as DeviceConnector).getConnection$reactive_ble_mobile_release();
   }

   @JvmStatic
   fun `negotiateMtuSize$lambda$26`(var0: Int, var1: java.lang.String, var2: EstablishConnectionResult): SingleSource {
      val var4: Single;
      if (var2 is EstablishedConnection) {
         var4 = (var2 as EstablishedConnection)
            .getRxConnection()
            .requestMtu(var0)
            .map(new ReactiveBleClient$$ExternalSyntheticLambda2(new ReactiveBleClient$$ExternalSyntheticLambda1(var1)));
      } else {
         if (var2 !is EstablishConnectionFailure) {
            throw new NoWhenBranchMatchedException();
         }

         val var3: StringBuilder = new StringBuilder("failed to connect ");
         var3.append((var2 as EstablishConnectionFailure).getErrorMessage());
         var4 = Single.just(new MtuNegotiateFailed(var1, var3.toString()));
      }

      return var4;
   }

   @JvmStatic
   fun `negotiateMtuSize$lambda$26$lambda$24`(var0: java.lang.String, var1: Int): MtuNegotiateSuccessful {
      return new MtuNegotiateSuccessful(var0, var1);
   }

   @JvmStatic
   fun `negotiateMtuSize$lambda$26$lambda$25`(var0: Function1, var1: Any): MtuNegotiateSuccessful {
      return var0.invoke(var1) as MtuNegotiateSuccessful;
   }

   @JvmStatic
   fun `negotiateMtuSize$lambda$27`(var0: Function1, var1: Any): SingleSource {
      return var0.invoke(var1) as SingleSource;
   }

   @JvmStatic
   fun `observeBleStatus$lambda$28`(var0: RxBleClient.State): BleStatus {
      return BleWrapperExtensionsKt.toBleState(var0);
   }

   @JvmStatic
   fun `observeBleStatus$lambda$29`(var0: Function1, var1: Any): BleStatus {
      return var0.invoke(var1) as BleStatus;
   }

   @JvmStatic
   fun `readCharacteristic$lambda$18`(var0: UUID, var1: Int, var2: java.lang.String, var3: EstablishConnectionResult): SingleSource {
      val var4: Single;
      if (var3 is EstablishedConnection) {
         var4 = RxBleConnectionExtensionKt.resolveCharacteristic((var3 as EstablishedConnection).getRxConnection(), var0, var1)
            .flatMap(new ReactiveBleClient$$ExternalSyntheticLambda39(new ReactiveBleClient$$ExternalSyntheticLambda38(var3, var2)));
      } else {
         if (var3 !is EstablishConnectionFailure) {
            throw new NoWhenBranchMatchedException();
         }

         val var5: StringBuilder = new StringBuilder("failed to connect ");
         var5.append((var3 as EstablishConnectionFailure).getErrorMessage());
         var4 = Single.just(new CharOperationFailed(var2, var5.toString()));
      }

      return var4;
   }

   @JvmStatic
   fun `readCharacteristic$lambda$18$lambda$16`(var0: EstablishConnectionResult, var1: java.lang.String, var2: BluetoothGattCharacteristic): SingleSource {
      return (var0 as EstablishedConnection)
         .getRxConnection()
         .readCharacteristic(var2)
         .retry(1L, new ReactiveBleClient$$ExternalSyntheticLambda11(new ReactiveBleClient$$ExternalSyntheticLambda0()))
         .map(new ReactiveBleClient$$ExternalSyntheticLambda33(new ReactiveBleClient$$ExternalSyntheticLambda22(var1)));
   }

   @JvmStatic
   fun `readCharacteristic$lambda$18$lambda$16$lambda$12`(var0: java.lang.Throwable): Boolean {
      val var1: Boolean;
      if (VERSION.SDK_INT < 26) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @JvmStatic
   fun `readCharacteristic$lambda$18$lambda$16$lambda$13`(var0: Function1, var1: Any): Boolean {
      return var0.invoke(var1) as java.lang.Boolean;
   }

   @JvmStatic
   fun `readCharacteristic$lambda$18$lambda$16$lambda$14`(var0: java.lang.String, var1: ByteArray): CharOperationSuccessful {
      return new CharOperationSuccessful(var0, ArraysKt.asList(var1));
   }

   @JvmStatic
   fun `readCharacteristic$lambda$18$lambda$16$lambda$15`(var0: Function1, var1: Any): CharOperationSuccessful {
      return var0.invoke(var1) as CharOperationSuccessful;
   }

   @JvmStatic
   fun `readCharacteristic$lambda$18$lambda$17`(var0: Function1, var1: Any): SingleSource {
      return var0.invoke(var1) as SingleSource;
   }

   @JvmStatic
   fun `readCharacteristic$lambda$19`(var0: Function1, var1: Any): SingleSource {
      return var0.invoke(var1) as SingleSource;
   }

   @JvmStatic
   fun `readRssi$lambda$43`(var0: EstablishConnectionResult): SingleSource {
      val var1: Single;
      if (var0 is EstablishedConnection) {
         var1 = (var0 as EstablishedConnection).getRxConnection().readRssi();
      } else {
         if (var0 !is EstablishConnectionFailure) {
            throw new NoWhenBranchMatchedException();
         }

         var1 = Single.error(new IllegalStateException("Reading RSSI failed. Device is not connected"));
      }

      return var1;
   }

   @JvmStatic
   fun `readRssi$lambda$44`(var0: Function1, var1: Any): SingleSource {
      return var0.invoke(var1) as SingleSource;
   }

   @JvmStatic
   fun `requestConnectionPriority$lambda$41`(var0: ConnectionPriority, var1: java.lang.String, var2: EstablishConnectionResult): SingleSource {
      val var3: Single;
      if (var2 is EstablishedConnection) {
         var3 = (var2 as EstablishedConnection)
            .getRxConnection()
            .requestConnectionPriority(var0.getCode(), 2L, TimeUnit.SECONDS)
            .toSingle(new ReactiveBleClient$$ExternalSyntheticLambda14(var1));
      } else {
         if (var2 !is EstablishConnectionFailure) {
            throw new NoWhenBranchMatchedException();
         }

         var3 = Single.fromCallable(new ReactiveBleClient$$ExternalSyntheticLambda15(var1, var2));
      }

      return var3;
   }

   @JvmStatic
   fun `requestConnectionPriority$lambda$41$lambda$39`(var0: java.lang.String): RequestConnectionPrioritySuccess {
      return new RequestConnectionPrioritySuccess(var0);
   }

   @JvmStatic
   fun `requestConnectionPriority$lambda$41$lambda$40`(var0: java.lang.String, var1: EstablishConnectionResult): RequestConnectionPriorityFailed {
      return new RequestConnectionPriorityFailed(var0, (var1 as EstablishConnectionFailure).getErrorMessage());
   }

   @JvmStatic
   fun `requestConnectionPriority$lambda$42`(var0: Function1, var1: Any): SingleSource {
      return var0.invoke(var1) as SingleSource;
   }

   @JvmStatic
   fun `scanForDevices$lambda$3`(var0: ScanResult): ScanInfo {
      val var7: java.lang.String = var0.getBleDevice().getMacAddress();
      var var3: java.lang.String = var0.getScanRecord().getDeviceName();
      var var4: java.lang.String = var3;
      if (var3 == null) {
         var3 = var0.getBleDevice().getName();
         var4 = var3;
         if (var3 == null) {
            var4 = "";
         }
      }

      val var2: Int = var0.getRssi();
      val var11: IsConnectable = var0.isConnectable();
      val var1: Int;
      if (var11 == null) {
         var1 = -1;
      } else {
         var1 = ReactiveBleClient.WhenMappings.$EnumSwitchMapping$0[var11.ordinal()];
      }

      val var12: Connectable;
      if (var1 != -1) {
         if (var1 != 1) {
            if (var1 != 2) {
               if (var1 != 3) {
                  throw new NoWhenBranchMatchedException();
               }

               var12 = Connectable.CONNECTABLE;
            } else {
               var12 = Connectable.NOT_CONNECTABLE;
            }
         } else {
            var12 = Connectable.UNKNOWN;
         }
      } else {
         var12 = Connectable.UNKNOWN;
      }

      val var6: java.util.Map = var0.getScanRecord().getServiceData();
      val var5: java.util.Map;
      if (var6 != null) {
         var5 = new LinkedHashMap(MapsKt.mapCapacity(var6.size()));

         for (Entry var9 : var6.entrySet()) {
            val var8: UUID = (var9.getKey() as ParcelUuid).getUuid();
            var5.put(var8, var9.getValue());
         }
      } else {
         var5 = MapsKt.emptyMap();
      }

      val var14: java.util.List = var0.getScanRecord().getServiceUuids();
      val var16: java.util.List;
      if (var14 != null) {
         val var15: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var14, 10));
         val var18: java.util.Iterator = var14.iterator();

         while (var18.hasNext()) {
            var15.add((var18.next() as ParcelUuid).getUuid());
         }

         var16 = var15 as java.util.List;
      } else {
         var16 = CollectionsKt.emptyList();
      }

      return new ScanInfo(
         var7, var4, var2, var12, var5, var16, ManufacturerDataConverterKt.extractManufacturerData(var0.getScanRecord().getManufacturerSpecificData())
      );
   }

   @JvmStatic
   fun `scanForDevices$lambda$4`(var0: Function1, var1: Any): ScanInfo {
      return var0.invoke(var1) as ScanInfo;
   }

   @JvmStatic
   fun `setupNotification$lambda$20`(var0: ReactiveBleClient, var1: UUID, var2: Int, var3: EstablishConnectionResult): ObservableSource {
      return var0.setupNotificationOrIndication(var3, var1, var2);
   }

   @JvmStatic
   fun `setupNotification$lambda$21`(var0: Function1, var1: Any): ObservableSource {
      return var0.invoke(var1) as ObservableSource;
   }

   @JvmStatic
   fun `setupNotification$lambda$22`(var0: Observable): ObservableSource {
      return var0;
   }

   @JvmStatic
   fun `setupNotification$lambda$23`(var0: Function1, var1: Any): ObservableSource {
      return var0.invoke(var1) as ObservableSource;
   }

   private fun setupNotificationOrIndication(deviceConnection: EstablishConnectionResult, characteristicId: UUID, characteristicInstanceId: Int): Observable<
         Observable<ByteArray>
      > {
      val var6: Observable;
      if (var1 is EstablishedConnection) {
         val var4: RxBleClient = Companion.getRxBleClient();
         val var5: EstablishedConnection = var1 as EstablishedConnection;
         if (var4.getBleDevice((var1 as EstablishedConnection).getDeviceId()).getBluetoothDevice().getBondState() == 11) {
            var6 = Observable.error(new Exception("Bonding is in progress wait for bonding to be finished before executing more operations on the device"));
         } else {
            var6 = RxBleConnectionExtensionKt.resolveCharacteristic(var5.getRxConnection(), var2, var3)
               .flatMapObservable(new ReactiveBleClient$$ExternalSyntheticLambda30(new ReactiveBleClient$$ExternalSyntheticLambda29(var1)));
         }
      } else {
         if (var1 !is EstablishConnectionFailure) {
            throw new NoWhenBranchMatchedException();
         }

         var6 = Observable.just(Observable.empty());
      }

      return var6;
   }

   @JvmStatic
   fun `setupNotificationOrIndication$lambda$37`(var0: EstablishConnectionResult, var1: BluetoothGattCharacteristic): ObservableSource {
      val var2: NotificationSetupMode;
      if (var1.getDescriptors().isEmpty()) {
         var2 = NotificationSetupMode.COMPAT;
      } else {
         var2 = NotificationSetupMode.DEFAULT;
      }

      val var3: Observable;
      if ((var1.getProperties() and 16) > 0) {
         var3 = (var0 as EstablishedConnection).getRxConnection().setupNotification(var1, var2);
      } else {
         var3 = (var0 as EstablishedConnection).getRxConnection().setupIndication(var1, var2);
      }

      return var3;
   }

   @JvmStatic
   fun `setupNotificationOrIndication$lambda$38`(var0: Function1, var1: Any): ObservableSource {
      return var0.invoke(var1) as ObservableSource;
   }

   public override fun clearGattCache(deviceId: String): Completable {
      val var3: DeviceConnector = activeConnections.get(var1);
      if (var3 != null) {
         val var2: Completable = var3.clearGattCache$reactive_ble_mobile_release();
         if (var2 != null) {
            return var2;
         }
      }

      val var4: Completable = Completable.error(new IllegalStateException("Device is not connected"));
      return var4;
   }

   public override fun connectToDevice(deviceId: String, timeout: Duration) {
      this.allConnections
         .add(
            this.getConnection(var1, var2)
               .subscribe(
                  new ReactiveBleClient$$ExternalSyntheticLambda19(new ReactiveBleClient$$ExternalSyntheticLambda18(var1)),
                  new ReactiveBleClient$$ExternalSyntheticLambda21(new ReactiveBleClient$$ExternalSyntheticLambda20(var1))
               )
         );
   }

   internal open fun createDeviceConnector(device: RxBleDevice, timeout: Duration): DeviceConnector {
      return new DeviceConnector(var1, var2, (new Function1<ConnectionUpdate, Unit>(connectionUpdateBehaviorSubject) {
         {
            super(1, var1, BehaviorSubject::class.java, "onNext", "onNext(Ljava/lang/Object;)V", 0);
         }

         public final void invoke(ConnectionUpdate var1) {
            (this.receiver as BehaviorSubject).onNext(var1);
         }
      }) as (ConnectionUpdate?) -> Unit, this.connectionQueue);
   }

   public override fun disconnectAllDevices() {
      for (Entry var2 : activeConnections.entrySet()) {
         (var2.getValue() as DeviceConnector).disconnectDevice$reactive_ble_mobile_release(var2.getKey() as java.lang.String);
      }

      this.allConnections.dispose();
   }

   public override fun disconnectDevice(deviceId: String) {
      val var2: DeviceConnector = activeConnections.get(var1);
      if (var2 != null) {
         var2.disconnectDevice$reactive_ble_mobile_release(var1);
      }

      activeConnections.remove(var1);
   }

   public override fun discoverServices(deviceId: String): Single<RxBleDeviceServices> {
      val var2: Single = getConnection$default(this, var1, null, 2, null)
         .flatMapSingle(new ReactiveBleClient$$ExternalSyntheticLambda8(new ReactiveBleClient$$ExternalSyntheticLambda7()))
         .firstOrError();
      return var2;
   }

   public override fun initializeClient() {
      val var1: ReactiveBleClient.Companion = Companion;
      activeConnections = new LinkedHashMap<>();
      var1.setRxBleClient$reactive_ble_mobile_release(RxBleClient.create(this.context));
   }

   public override fun negotiateMtuSize(deviceId: String, size: Int): Single<MtuNegotiateResult> {
      val var3: Single = getConnection$default(this, var1, null, 2, null)
         .<MtuNegotiateFailed>flatMapSingle(new ReactiveBleClient$$ExternalSyntheticLambda32(new ReactiveBleClient$$ExternalSyntheticLambda31(var2, var1)))
         .first(new MtuNegotiateFailed(var1, "negotiate mtu timed out"));
      return var3;
   }

   public override fun observeBleStatus(): Observable<BleStatus> {
      val var1: ReactiveBleClient.Companion = Companion;
      val var2: Observable = Companion.getRxBleClient()
         .observeStateChanges()
         .startWith(var1.getRxBleClient().getState())
         .map(new ReactiveBleClient$$ExternalSyntheticLambda37(new ReactiveBleClient$$ExternalSyntheticLambda36()));
      return var2;
   }

   public override fun readCharacteristic(deviceId: String, characteristicId: UUID, characteristicInstanceId: Int): Single<CharOperationResult> {
      val var4: Single = getConnection$default(this, var1, null, 2, null)
         .<CharOperationFailed>flatMapSingle(new ReactiveBleClient$$ExternalSyntheticLambda6(new ReactiveBleClient$$ExternalSyntheticLambda5(var2, var3, var1)))
         .first(new CharOperationFailed(var1, "read char failed"));
      return var4;
   }

   public override fun readRssi(deviceId: String): Single<Int> {
      val var2: Single = getConnection$default(this, var1, null, 2, null)
         .flatMapSingle(new ReactiveBleClient$$ExternalSyntheticLambda4(new ReactiveBleClient$$ExternalSyntheticLambda3()))
         .firstOrError();
      return var2;
   }

   public override fun requestConnectionPriority(deviceId: String, priority: ConnectionPriority): Single<RequestConnectionPriorityResult> {
      val var3: Single = getConnection$default(this, var1, null, 2, null)
         .<RequestConnectionPriorityFailed>switchMapSingle(
            new ReactiveBleClient$$ExternalSyntheticLambda17(new ReactiveBleClient$$ExternalSyntheticLambda16(var2, var1))
         )
         .first(new RequestConnectionPriorityFailed(var1, "Unknown failure"));
      return var3;
   }

   public override fun scanForDevices(services: List<ParcelUuid>, scanMode: ScanMode, requireLocationServicesEnabled: Boolean): Observable<ScanInfo> {
      val var4: java.lang.Iterable = var1;
      val var6: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var1, 10));

      for (ParcelUuid var9 : var4) {
         var6.add(new ScanFilter.Builder().setServiceUuid(var9).build());
      }

      val var7: Array<ScanFilter> = (var6 as java.util.List).toArray(new ScanFilter[0]);
      val var8: Observable = Companion.getRxBleClient()
         .scanBleDevices(
            new ScanSettings.Builder()
               .setScanMode(ScanModeKt.toScanSettings(var2))
               .setLegacy(false)
               .setCallbackType(1)
               .setShouldCheckLocationServicesState(var3)
               .build(),
            Arrays.copyOf(var7, var7.length)
         )
         .map(new ReactiveBleClient$$ExternalSyntheticLambda13(new ReactiveBleClient$$ExternalSyntheticLambda12()));
      return var8;
   }

   public override fun setupNotification(deviceId: String, characteristicId: UUID, characteristicInstanceId: Int): Observable<ByteArray> {
      val var4: Observable = getConnection$default(this, var1, null, 2, null)
         .flatMap(new ReactiveBleClient$$ExternalSyntheticLambda26(new ReactiveBleClient$$ExternalSyntheticLambda25(this, var2, var3)))
         .flatMap(new ReactiveBleClient$$ExternalSyntheticLambda28(new ReactiveBleClient$$ExternalSyntheticLambda27()));
      return var4;
   }

   public override fun writeCharacteristicWithResponse(deviceId: String, characteristicId: UUID, characteristicInstanceId: Int, value: ByteArray): Single<
         CharOperationResult
      > {
      return this.executeWriteOperation(var1, var2, var3, var4, <unrepresentable>.INSTANCE);
   }

   public override fun writeCharacteristicWithoutResponse(deviceId: String, characteristicId: UUID, characteristicInstanceId: Int, value: ByteArray): Single<
         CharOperationResult
      > {
      return this.executeWriteOperation(var1, var2, var3, var4, <unrepresentable>.INSTANCE);
   }

   public companion object {
      private final val connectionUpdateBehaviorSubject: BehaviorSubject<ConnectionUpdate>

      public final lateinit var rxBleClient: RxBleClient
         internal final set(value) {
            ReactiveBleClient.rxBleClient = var1;
         }


      internal final var activeConnections: MutableMap<String, DeviceConnector>
   }
}
