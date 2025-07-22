package com.polidea.rxandroidble2;

import bleshadow.dagger.Lazy;
import bleshadow.dagger.internal.DoubleCheck;
import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.RxBleDeviceProvider;
import com.polidea.rxandroidble2.internal.scan.RxBleInternalScanResult;
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
import com.polidea.rxandroidble2.scan.ScanResult;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;

public final class RxBleClientImpl_Factory implements Factory<RxBleClientImpl> {
   private final Provider<Observable<RxBleAdapterStateObservable.BleAdapterState>> adapterStateObservableProvider;
   private final Provider<BackgroundScanner> backgroundScannerProvider;
   private final Provider<Scheduler> bluetoothInteractionSchedulerProvider;
   private final Provider<BluetoothManagerWrapper> bluetoothManagerWrapperProvider;
   private final Provider<CheckerConnectPermission> checkerConnectPermissionProvider;
   private final Provider<CheckerScanPermission> checkerScanPermissionProvider;
   private final Provider<ClientComponent.ClientComponentFinalizer> clientComponentFinalizerProvider;
   private final Provider<ClientStateObservable> clientStateObservableProvider;
   private final Provider<Function<RxBleInternalScanResult, ScanResult>> internalToExternalScanResultMapFunctionProvider;
   private final Provider<LocationServicesStatus> locationServicesStatusProvider;
   private final Provider<ClientOperationQueue> operationQueueProvider;
   private final Provider<RxBleAdapterWrapper> rxBleAdapterWrapperProvider;
   private final Provider<RxBleDeviceProvider> rxBleDeviceProvider;
   private final Provider<ScanPreconditionsVerifier> scanPreconditionVerifierProvider;
   private final Provider<ScanRecordParser> scanRecordParserProvider;
   private final Provider<ScanSetupBuilder> scanSetupBuilderProvider;

   public RxBleClientImpl_Factory(
      Provider<BluetoothManagerWrapper> var1,
      Provider<RxBleAdapterWrapper> var2,
      Provider<ClientOperationQueue> var3,
      Provider<Observable<RxBleAdapterStateObservable.BleAdapterState>> var4,
      Provider<ScanRecordParser> var5,
      Provider<LocationServicesStatus> var6,
      Provider<ClientStateObservable> var7,
      Provider<RxBleDeviceProvider> var8,
      Provider<ScanSetupBuilder> var9,
      Provider<ScanPreconditionsVerifier> var10,
      Provider<Function<RxBleInternalScanResult, ScanResult>> var11,
      Provider<Scheduler> var12,
      Provider<ClientComponent.ClientComponentFinalizer> var13,
      Provider<BackgroundScanner> var14,
      Provider<CheckerScanPermission> var15,
      Provider<CheckerConnectPermission> var16
   ) {
      this.bluetoothManagerWrapperProvider = var1;
      this.rxBleAdapterWrapperProvider = var2;
      this.operationQueueProvider = var3;
      this.adapterStateObservableProvider = var4;
      this.scanRecordParserProvider = var5;
      this.locationServicesStatusProvider = var6;
      this.clientStateObservableProvider = var7;
      this.rxBleDeviceProvider = var8;
      this.scanSetupBuilderProvider = var9;
      this.scanPreconditionVerifierProvider = var10;
      this.internalToExternalScanResultMapFunctionProvider = var11;
      this.bluetoothInteractionSchedulerProvider = var12;
      this.clientComponentFinalizerProvider = var13;
      this.backgroundScannerProvider = var14;
      this.checkerScanPermissionProvider = var15;
      this.checkerConnectPermissionProvider = var16;
   }

   public static RxBleClientImpl_Factory create(
      Provider<BluetoothManagerWrapper> var0,
      Provider<RxBleAdapterWrapper> var1,
      Provider<ClientOperationQueue> var2,
      Provider<Observable<RxBleAdapterStateObservable.BleAdapterState>> var3,
      Provider<ScanRecordParser> var4,
      Provider<LocationServicesStatus> var5,
      Provider<ClientStateObservable> var6,
      Provider<RxBleDeviceProvider> var7,
      Provider<ScanSetupBuilder> var8,
      Provider<ScanPreconditionsVerifier> var9,
      Provider<Function<RxBleInternalScanResult, ScanResult>> var10,
      Provider<Scheduler> var11,
      Provider<ClientComponent.ClientComponentFinalizer> var12,
      Provider<BackgroundScanner> var13,
      Provider<CheckerScanPermission> var14,
      Provider<CheckerConnectPermission> var15
   ) {
      return new RxBleClientImpl_Factory(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15);
   }

   public static RxBleClientImpl newInstance(
      BluetoothManagerWrapper var0,
      RxBleAdapterWrapper var1,
      ClientOperationQueue var2,
      Observable<RxBleAdapterStateObservable.BleAdapterState> var3,
      ScanRecordParser var4,
      LocationServicesStatus var5,
      Lazy<ClientStateObservable> var6,
      RxBleDeviceProvider var7,
      ScanSetupBuilder var8,
      ScanPreconditionsVerifier var9,
      Function<RxBleInternalScanResult, ScanResult> var10,
      Scheduler var11,
      ClientComponent.ClientComponentFinalizer var12,
      BackgroundScanner var13,
      CheckerScanPermission var14,
      CheckerConnectPermission var15
   ) {
      return new RxBleClientImpl(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15);
   }

   public RxBleClientImpl get() {
      return newInstance(
         (BluetoothManagerWrapper)this.bluetoothManagerWrapperProvider.get(),
         (RxBleAdapterWrapper)this.rxBleAdapterWrapperProvider.get(),
         (ClientOperationQueue)this.operationQueueProvider.get(),
         (Observable<RxBleAdapterStateObservable.BleAdapterState>)this.adapterStateObservableProvider.get(),
         (ScanRecordParser)this.scanRecordParserProvider.get(),
         (LocationServicesStatus)this.locationServicesStatusProvider.get(),
         DoubleCheck.lazy(this.clientStateObservableProvider),
         (RxBleDeviceProvider)this.rxBleDeviceProvider.get(),
         (ScanSetupBuilder)this.scanSetupBuilderProvider.get(),
         (ScanPreconditionsVerifier)this.scanPreconditionVerifierProvider.get(),
         (Function<RxBleInternalScanResult, ScanResult>)this.internalToExternalScanResultMapFunctionProvider.get(),
         (Scheduler)this.bluetoothInteractionSchedulerProvider.get(),
         (ClientComponent.ClientComponentFinalizer)this.clientComponentFinalizerProvider.get(),
         (BackgroundScanner)this.backgroundScannerProvider.get(),
         (CheckerScanPermission)this.checkerScanPermissionProvider.get(),
         (CheckerConnectPermission)this.checkerConnectPermissionProvider.get()
      );
   }
}
