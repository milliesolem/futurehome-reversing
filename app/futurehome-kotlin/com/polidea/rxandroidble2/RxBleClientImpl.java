package com.polidea.rxandroidble2;

import android.bluetooth.BluetoothDevice;
import bleshadow.dagger.Lazy;
import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble2.internal.RxBleDeviceProvider;
import com.polidea.rxandroidble2.internal.operations.LegacyScanOperation;
import com.polidea.rxandroidble2.internal.scan.RxBleInternalScanResult;
import com.polidea.rxandroidble2.internal.scan.RxBleInternalScanResultLegacy;
import com.polidea.rxandroidble2.internal.scan.ScanPreconditionsVerifier;
import com.polidea.rxandroidble2.internal.scan.ScanSetupBuilder;
import com.polidea.rxandroidble2.internal.serialization.ClientOperationQueue;
import com.polidea.rxandroidble2.internal.util.BluetoothManagerWrapper;
import com.polidea.rxandroidble2.internal.util.CheckerConnectPermission;
import com.polidea.rxandroidble2.internal.util.CheckerScanPermission;
import com.polidea.rxandroidble2.internal.util.ClientStateObservable;
import com.polidea.rxandroidble2.internal.util.LocationServicesStatus;
import com.polidea.rxandroidble2.internal.util.RxBleAdapterWrapper;
import com.polidea.rxandroidble2.internal.util.ScanRecordParser;
import com.polidea.rxandroidble2.scan.BackgroundScanner;
import com.polidea.rxandroidble2.scan.ScanFilter;
import com.polidea.rxandroidble2.scan.ScanResult;
import com.polidea.rxandroidble2.scan.ScanSettings;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

class RxBleClientImpl extends RxBleClient {
   @Deprecated
   public static final String TAG = "RxBleClient";
   private final BackgroundScanner backgroundScanner;
   final Scheduler bluetoothInteractionScheduler;
   private final BluetoothManagerWrapper bluetoothManagerWrapper;
   private final CheckerConnectPermission checkerConnectPermission;
   private final CheckerScanPermission checkerScanPermission;
   private final ClientComponent.ClientComponentFinalizer clientComponentFinalizer;
   final Function<RxBleInternalScanResult, ScanResult> internalToExternalScanResultMapFunction;
   private final Lazy<ClientStateObservable> lazyClientStateObservable;
   private final LocationServicesStatus locationServicesStatus;
   final ClientOperationQueue operationQueue;
   final Map<Set<UUID>, Observable<RxBleScanResult>> queuedScanOperations = new HashMap<>();
   private final Observable<RxBleAdapterStateObservable.BleAdapterState> rxBleAdapterStateObservable;
   private final RxBleAdapterWrapper rxBleAdapterWrapper;
   private final RxBleDeviceProvider rxBleDeviceProvider;
   final ScanPreconditionsVerifier scanPreconditionVerifier;
   private final ScanRecordParser scanRecordParser;
   final ScanSetupBuilder scanSetupBuilder;

   @Inject
   RxBleClientImpl(
      BluetoothManagerWrapper var1,
      RxBleAdapterWrapper var2,
      ClientOperationQueue var3,
      Observable<RxBleAdapterStateObservable.BleAdapterState> var4,
      ScanRecordParser var5,
      LocationServicesStatus var6,
      Lazy<ClientStateObservable> var7,
      RxBleDeviceProvider var8,
      ScanSetupBuilder var9,
      ScanPreconditionsVerifier var10,
      Function<RxBleInternalScanResult, ScanResult> var11,
      @Named("bluetooth_interaction") Scheduler var12,
      ClientComponent.ClientComponentFinalizer var13,
      BackgroundScanner var14,
      CheckerScanPermission var15,
      CheckerConnectPermission var16
   ) {
      this.operationQueue = var3;
      this.bluetoothManagerWrapper = var1;
      this.rxBleAdapterWrapper = var2;
      this.rxBleAdapterStateObservable = var4;
      this.scanRecordParser = var5;
      this.locationServicesStatus = var6;
      this.lazyClientStateObservable = var7;
      this.rxBleDeviceProvider = var8;
      this.scanSetupBuilder = var9;
      this.scanPreconditionVerifier = var10;
      this.internalToExternalScanResultMapFunction = var11;
      this.bluetoothInteractionScheduler = var12;
      this.clientComponentFinalizer = var13;
      this.backgroundScanner = var14;
      this.checkerScanPermission = var15;
      this.checkerConnectPermission = var16;
   }

   private Observable<RxBleScanResult> createScanOperationApi18(UUID[] var1) {
      Set var2 = this.toDistinctSet(var1);
      LegacyScanOperation var3 = new LegacyScanOperation(var1, this.rxBleAdapterWrapper, this.scanRecordParser);
      return this.operationQueue
         .queue(var3)
         .doFinally(new RxBleClientImpl$$ExternalSyntheticLambda0(this, var2))
         .mergeWith(this.bluetoothAdapterOffExceptionObservable())
         .map(new RxBleClientImpl$$ExternalSyntheticLambda1(this))
         .doOnNext(new RxBleClientImpl$$ExternalSyntheticLambda2())
         .share();
   }

   private void guardBluetoothAdapterAvailable() {
      if (!this.rxBleAdapterWrapper.hasBluetoothAdapter()) {
         throw new UnsupportedOperationException(
            "RxAndroidBle library needs a BluetoothAdapter to be available in the system to work. If this is a test on an emulator then you can use 'https://github.com/Polidea/RxAndroidBle/tree/master/mockrxandroidble'"
         );
      }
   }

   private Set<UUID> toDistinctSet(UUID[] var1) {
      UUID[] var2 = var1;
      if (var1 == null) {
         var2 = new UUID[0];
      }

      return new HashSet<>(Arrays.asList(var2));
   }

   <T> Observable<T> bluetoothAdapterOffExceptionObservable() {
      return this.rxBleAdapterStateObservable
         .filter(new RxBleClientImpl$$ExternalSyntheticLambda6())
         .firstElement()
         .<T>flatMap(new RxBleClientImpl$$ExternalSyntheticLambda7())
         .toObservable();
   }

   RxBleScanResult convertToPublicScanResult(RxBleInternalScanResultLegacy var1) {
      return new RxBleScanResult(this.getBleDevice(var1.getBluetoothDevice().getAddress()), var1.getRssi(), var1.getScanRecord());
   }

   @Override
   protected void finalize() throws Throwable {
      this.clientComponentFinalizer.onFinalize();
      super.finalize();
   }

   @Override
   public BackgroundScanner getBackgroundScanner() {
      return this.backgroundScanner;
   }

   @Override
   public RxBleDevice getBleDevice(String var1) {
      this.guardBluetoothAdapterAvailable();
      return this.rxBleDeviceProvider.getBleDevice(var1);
   }

   @Override
   public Set<RxBleDevice> getBondedDevices() {
      this.guardBluetoothAdapterAvailable();
      HashSet var2 = new HashSet();
      Iterator var1 = this.rxBleAdapterWrapper.getBondedDevices().iterator();

      while (var1.hasNext()) {
         var2.add(this.getBleDevice(((BluetoothDevice)var1.next()).getAddress()));
      }

      return var2;
   }

   @Override
   public Set<RxBleDevice> getConnectedPeripherals() {
      HashSet var1 = new HashSet();
      Iterator var2 = this.bluetoothManagerWrapper.getConnectedPeripherals().iterator();

      while (var2.hasNext()) {
         var1.add(this.getBleDevice(((BluetoothDevice)var2.next()).getAddress()));
      }

      return var1;
   }

   @Override
   public String[] getRecommendedConnectRuntimePermissions() {
      return this.checkerConnectPermission.getRecommendedConnectRuntimePermissions();
   }

   @Override
   public String[] getRecommendedScanRuntimePermissions() {
      return this.checkerScanPermission.getRecommendedScanRuntimePermissions();
   }

   @Override
   public RxBleClient.State getState() {
      if (!this.rxBleAdapterWrapper.hasBluetoothAdapter()) {
         return RxBleClient.State.BLUETOOTH_NOT_AVAILABLE;
      } else if (!this.locationServicesStatus.isLocationPermissionOk()) {
         return RxBleClient.State.LOCATION_PERMISSION_NOT_GRANTED;
      } else if (!this.rxBleAdapterWrapper.isBluetoothEnabled()) {
         return RxBleClient.State.BLUETOOTH_NOT_ENABLED;
      } else {
         return !this.locationServicesStatus.isLocationProviderOk() ? RxBleClient.State.LOCATION_SERVICES_NOT_ENABLED : RxBleClient.State.READY;
      }
   }

   Observable<RxBleScanResult> initializeScan(UUID[] param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: aload 0
      // 01: aload 1
      // 02: invokespecial com/polidea/rxandroidble2/RxBleClientImpl.toDistinctSet ([Ljava/util/UUID;)Ljava/util/Set;
      // 05: astore 5
      // 07: aload 0
      // 08: getfield com/polidea/rxandroidble2/RxBleClientImpl.queuedScanOperations Ljava/util/Map;
      // 0b: astore 4
      // 0d: aload 4
      // 0f: monitorenter
      // 10: aload 0
      // 11: getfield com/polidea/rxandroidble2/RxBleClientImpl.queuedScanOperations Ljava/util/Map;
      // 14: aload 5
      // 16: invokeinterface java/util/Map.get (Ljava/lang/Object;)Ljava/lang/Object; 2
      // 1b: checkcast io/reactivex/Observable
      // 1e: astore 3
      // 1f: aload 3
      // 20: astore 2
      // 21: aload 3
      // 22: ifnonnull 38
      // 25: aload 0
      // 26: aload 1
      // 27: invokespecial com/polidea/rxandroidble2/RxBleClientImpl.createScanOperationApi18 ([Ljava/util/UUID;)Lio/reactivex/Observable;
      // 2a: astore 2
      // 2b: aload 0
      // 2c: getfield com/polidea/rxandroidble2/RxBleClientImpl.queuedScanOperations Ljava/util/Map;
      // 2f: aload 5
      // 31: aload 2
      // 32: invokeinterface java/util/Map.put (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object; 3
      // 37: pop
      // 38: aload 4
      // 3a: monitorexit
      // 3b: aload 2
      // 3c: areturn
      // 3d: astore 1
      // 3e: aload 4
      // 40: monitorexit
      // 41: aload 1
      // 42: athrow
   }

   @Override
   public boolean isConnectRuntimePermissionGranted() {
      return this.checkerConnectPermission.isConnectRuntimePermissionGranted();
   }

   @Override
   public boolean isScanRuntimePermissionGranted() {
      return this.checkerScanPermission.isScanRuntimePermissionGranted();
   }

   @Override
   public Observable<RxBleClient.State> observeStateChanges() {
      return (Observable<RxBleClient.State>)this.lazyClientStateObservable.get();
   }

   @Override
   public Observable<ScanResult> scanBleDevices(ScanSettings var1, ScanFilter... var2) {
      return Observable.defer(new RxBleClientImpl$$ExternalSyntheticLambda5(this, var1, var2));
   }

   @Deprecated
   @Override
   public Observable<RxBleScanResult> scanBleDevices(UUID... var1) {
      return Observable.defer(new RxBleClientImpl$$ExternalSyntheticLambda3(this, var1));
   }
}
