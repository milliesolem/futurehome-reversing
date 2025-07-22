package com.polidea.rxandroidble2;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.content.ContentResolver;
import android.content.Context;
import android.location.LocationManager;
import bleshadow.dagger.internal.DelegateFactory;
import bleshadow.dagger.internal.DoubleCheck;
import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.InstanceFactory;
import bleshadow.dagger.internal.Preconditions;
import bleshadow.dagger.internal.SetBuilder;
import bleshadow.javax.inject.Provider;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.polidea.rxandroidble2.helpers.LocationServicesOkObservable;
import com.polidea.rxandroidble2.helpers.LocationServicesOkObservable_Factory;
import com.polidea.rxandroidble2.internal.DeviceComponent;
import com.polidea.rxandroidble2.internal.DeviceModule_ProvideBluetoothDeviceFactory;
import com.polidea.rxandroidble2.internal.DeviceModule_ProvideConnectionStateChangeListenerFactory;
import com.polidea.rxandroidble2.internal.DeviceModule_ProvideConnectionStateRelayFactory;
import com.polidea.rxandroidble2.internal.DeviceModule_ProvidesConnectTimeoutConfFactory;
import com.polidea.rxandroidble2.internal.DeviceModule_ProvidesDisconnectTimeoutConfFactory;
import com.polidea.rxandroidble2.internal.RxBleDeviceImpl_Factory;
import com.polidea.rxandroidble2.internal.RxBleDeviceProvider;
import com.polidea.rxandroidble2.internal.RxBleDeviceProvider_Factory;
import com.polidea.rxandroidble2.internal.cache.DeviceComponentCache;
import com.polidea.rxandroidble2.internal.cache.DeviceComponentCache_Factory;
import com.polidea.rxandroidble2.internal.connection.BluetoothGattProvider;
import com.polidea.rxandroidble2.internal.connection.BluetoothGattProvider_Factory;
import com.polidea.rxandroidble2.internal.connection.ConnectionComponent;
import com.polidea.rxandroidble2.internal.connection.ConnectionModule_GattWriteMtuOverheadFactory;
import com.polidea.rxandroidble2.internal.connection.ConnectionModule_MinimumMtuFactory;
import com.polidea.rxandroidble2.internal.connection.ConnectionModule_ProvideBluetoothGattFactory;
import com.polidea.rxandroidble2.internal.connection.ConnectionModule_ProvideCharacteristicPropertiesParserFactory;
import com.polidea.rxandroidble2.internal.connection.ConnectionModule_ProvideIllegalOperationHandlerFactory;
import com.polidea.rxandroidble2.internal.connection.ConnectionModule_ProvidesOperationTimeoutConfFactory;
import com.polidea.rxandroidble2.internal.connection.ConnectionStateChangeListener;
import com.polidea.rxandroidble2.internal.connection.ConnectionSubscriptionWatcher;
import com.polidea.rxandroidble2.internal.connection.ConnectorImpl;
import com.polidea.rxandroidble2.internal.connection.ConnectorImpl_Factory;
import com.polidea.rxandroidble2.internal.connection.DescriptorWriter_Factory;
import com.polidea.rxandroidble2.internal.connection.DisconnectAction_Factory;
import com.polidea.rxandroidble2.internal.connection.DisconnectionRouter_Factory;
import com.polidea.rxandroidble2.internal.connection.IllegalOperationChecker;
import com.polidea.rxandroidble2.internal.connection.IllegalOperationChecker_Factory;
import com.polidea.rxandroidble2.internal.connection.IllegalOperationHandler;
import com.polidea.rxandroidble2.internal.connection.IllegalOperationMessageCreator;
import com.polidea.rxandroidble2.internal.connection.IllegalOperationMessageCreator_Factory;
import com.polidea.rxandroidble2.internal.connection.LoggingIllegalOperationHandler;
import com.polidea.rxandroidble2.internal.connection.LoggingIllegalOperationHandler_Factory;
import com.polidea.rxandroidble2.internal.connection.LongWriteOperationBuilderImpl;
import com.polidea.rxandroidble2.internal.connection.LongWriteOperationBuilderImpl_Factory;
import com.polidea.rxandroidble2.internal.connection.MtuBasedPayloadSizeLimit_Factory;
import com.polidea.rxandroidble2.internal.connection.MtuWatcher_Factory;
import com.polidea.rxandroidble2.internal.connection.NativeCallbackDispatcher_Factory;
import com.polidea.rxandroidble2.internal.connection.NotificationAndIndicationManager_Factory;
import com.polidea.rxandroidble2.internal.connection.RxBleConnectionImpl;
import com.polidea.rxandroidble2.internal.connection.RxBleConnectionImpl_Factory;
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback;
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback_Factory;
import com.polidea.rxandroidble2.internal.connection.ServiceDiscoveryManager_Factory;
import com.polidea.rxandroidble2.internal.connection.ThrowingIllegalOperationHandler;
import com.polidea.rxandroidble2.internal.connection.ThrowingIllegalOperationHandler_Factory;
import com.polidea.rxandroidble2.internal.logger.LoggerUtilBluetoothServices;
import com.polidea.rxandroidble2.internal.logger.LoggerUtilBluetoothServices_Factory;
import com.polidea.rxandroidble2.internal.operations.ConnectOperation;
import com.polidea.rxandroidble2.internal.operations.ConnectOperation_Factory;
import com.polidea.rxandroidble2.internal.operations.DisconnectOperation;
import com.polidea.rxandroidble2.internal.operations.DisconnectOperation_Factory;
import com.polidea.rxandroidble2.internal.operations.OperationsProviderImpl;
import com.polidea.rxandroidble2.internal.operations.OperationsProviderImpl_Factory;
import com.polidea.rxandroidble2.internal.operations.ReadRssiOperation;
import com.polidea.rxandroidble2.internal.operations.ReadRssiOperation_Factory;
import com.polidea.rxandroidble2.internal.operations.TimeoutConfiguration;
import com.polidea.rxandroidble2.internal.scan.AndroidScanObjectsConverter;
import com.polidea.rxandroidble2.internal.scan.AndroidScanObjectsConverter_Factory;
import com.polidea.rxandroidble2.internal.scan.BackgroundScannerImpl;
import com.polidea.rxandroidble2.internal.scan.BackgroundScannerImpl_Factory;
import com.polidea.rxandroidble2.internal.scan.InternalScanResultCreator;
import com.polidea.rxandroidble2.internal.scan.InternalScanResultCreator_Factory;
import com.polidea.rxandroidble2.internal.scan.InternalToExternalScanResultConverter;
import com.polidea.rxandroidble2.internal.scan.InternalToExternalScanResultConverter_Factory;
import com.polidea.rxandroidble2.internal.scan.IsConnectableChecker;
import com.polidea.rxandroidble2.internal.scan.IsConnectableCheckerApi18_Factory;
import com.polidea.rxandroidble2.internal.scan.IsConnectableCheckerApi26_Factory;
import com.polidea.rxandroidble2.internal.scan.ScanPreconditionsVerifier;
import com.polidea.rxandroidble2.internal.scan.ScanPreconditionsVerifierApi18;
import com.polidea.rxandroidble2.internal.scan.ScanPreconditionsVerifierApi18_Factory;
import com.polidea.rxandroidble2.internal.scan.ScanPreconditionsVerifierApi24;
import com.polidea.rxandroidble2.internal.scan.ScanPreconditionsVerifierApi24_Factory;
import com.polidea.rxandroidble2.internal.scan.ScanSettingsEmulator;
import com.polidea.rxandroidble2.internal.scan.ScanSettingsEmulator_Factory;
import com.polidea.rxandroidble2.internal.scan.ScanSetupBuilder;
import com.polidea.rxandroidble2.internal.scan.ScanSetupBuilderImplApi18;
import com.polidea.rxandroidble2.internal.scan.ScanSetupBuilderImplApi18_Factory;
import com.polidea.rxandroidble2.internal.scan.ScanSetupBuilderImplApi21;
import com.polidea.rxandroidble2.internal.scan.ScanSetupBuilderImplApi21_Factory;
import com.polidea.rxandroidble2.internal.scan.ScanSetupBuilderImplApi23;
import com.polidea.rxandroidble2.internal.scan.ScanSetupBuilderImplApi23_Factory;
import com.polidea.rxandroidble2.internal.serialization.ClientOperationQueue;
import com.polidea.rxandroidble2.internal.serialization.ClientOperationQueueImpl;
import com.polidea.rxandroidble2.internal.serialization.ClientOperationQueueImpl_Factory;
import com.polidea.rxandroidble2.internal.serialization.ConnectionOperationQueueImpl;
import com.polidea.rxandroidble2.internal.serialization.ConnectionOperationQueueImpl_Factory;
import com.polidea.rxandroidble2.internal.util.BleConnectionCompat;
import com.polidea.rxandroidble2.internal.util.BluetoothManagerWrapper;
import com.polidea.rxandroidble2.internal.util.BluetoothManagerWrapper_Factory;
import com.polidea.rxandroidble2.internal.util.CheckerConnectPermission;
import com.polidea.rxandroidble2.internal.util.CheckerConnectPermission_Factory;
import com.polidea.rxandroidble2.internal.util.CheckerLocationProvider;
import com.polidea.rxandroidble2.internal.util.CheckerLocationProvider_Factory;
import com.polidea.rxandroidble2.internal.util.CheckerPermission;
import com.polidea.rxandroidble2.internal.util.CheckerPermission_Factory;
import com.polidea.rxandroidble2.internal.util.CheckerScanPermission;
import com.polidea.rxandroidble2.internal.util.CheckerScanPermission_Factory;
import com.polidea.rxandroidble2.internal.util.ClientStateObservable;
import com.polidea.rxandroidble2.internal.util.ClientStateObservable_Factory;
import com.polidea.rxandroidble2.internal.util.LocationServicesOkObservableApi23Factory;
import com.polidea.rxandroidble2.internal.util.LocationServicesOkObservableApi23Factory_Factory;
import com.polidea.rxandroidble2.internal.util.LocationServicesStatus;
import com.polidea.rxandroidble2.internal.util.LocationServicesStatusApi18_Factory;
import com.polidea.rxandroidble2.internal.util.LocationServicesStatusApi23;
import com.polidea.rxandroidble2.internal.util.LocationServicesStatusApi23_Factory;
import com.polidea.rxandroidble2.internal.util.LocationServicesStatusApi31;
import com.polidea.rxandroidble2.internal.util.LocationServicesStatusApi31_Factory;
import com.polidea.rxandroidble2.internal.util.RxBleAdapterWrapper;
import com.polidea.rxandroidble2.internal.util.RxBleAdapterWrapper_Factory;
import com.polidea.rxandroidble2.internal.util.ScanRecordParser_Factory;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public final class DaggerClientComponent {
   private DaggerClientComponent() {
   }

   public static ClientComponent.Builder builder() {
      return new DaggerClientComponent.Builder();
   }

   private static final class Builder implements ClientComponent.Builder {
      private Context applicationContext;

      private Builder() {
      }

      public DaggerClientComponent.Builder applicationContext(Context var1) {
         this.applicationContext = (Context)Preconditions.checkNotNull(var1);
         return this;
      }

      @Override
      public ClientComponent build() {
         Preconditions.checkBuilderRequirement(this.applicationContext, Context.class);
         return new DaggerClientComponent.ClientComponentImpl(this.applicationContext);
      }
   }

   private static final class ClientComponentImpl implements ClientComponent {
      private Provider<AndroidScanObjectsConverter> androidScanObjectsConverterProvider;
      private final Context applicationContext;
      private Provider<Context> applicationContextProvider;
      private Provider<BackgroundScannerImpl> backgroundScannerImplProvider;
      private Provider<ClientOperationQueue> bindClientOperationQueueProvider;
      private Provider<RxBleClient> bindRxBleClientProvider;
      private Provider<BluetoothManagerWrapper> bluetoothManagerWrapperProvider;
      private Provider<CheckerConnectPermission> checkerConnectPermissionProvider;
      private Provider<CheckerLocationProvider> checkerLocationProvider;
      private Provider<CheckerPermission> checkerPermissionProvider;
      private Provider<CheckerScanPermission> checkerScanPermissionProvider;
      private final DaggerClientComponent.ClientComponentImpl clientComponentImpl = this;
      private Provider<ClientOperationQueueImpl> clientOperationQueueImplProvider;
      private Provider<ClientStateObservable> clientStateObservableProvider;
      private Provider<DeviceComponent.Builder> deviceComponentBuilderProvider;
      private Provider<DeviceComponentCache> deviceComponentCacheProvider;
      private Provider<InternalScanResultCreator> internalScanResultCreatorProvider;
      private Provider<InternalToExternalScanResultConverter> internalToExternalScanResultConverterProvider;
      private Provider<LocationServicesOkObservableApi23Factory> locationServicesOkObservableApi23FactoryProvider;
      private Provider<LocationServicesStatusApi23> locationServicesStatusApi23Provider;
      private Provider<LocationServicesStatusApi31> locationServicesStatusApi31Provider;
      private Provider<Scheduler> provideBluetoothCallbacksSchedulerProvider;
      private Provider<ExecutorService> provideBluetoothInteractionExecutorServiceProvider;
      private Provider<Scheduler> provideBluetoothInteractionSchedulerProvider;
      private Provider<BluetoothManager> provideBluetoothManagerProvider;
      private Provider<ExecutorService> provideConnectionQueueExecutorServiceProvider;
      private Provider<ContentResolver> provideContentResolverProvider;
      private Provider<ClientComponent.ClientComponentFinalizer> provideFinalizationCloseableProvider;
      private Provider<Boolean> provideIsAndroidWearProvider;
      private Provider<IsConnectableChecker> provideIsConnectableCheckerProvider;
      private Provider<Boolean> provideIsNearbyPermissionNeverForLocationProvider;
      private Provider<LocationManager> provideLocationManagerProvider;
      private Provider<Observable<Boolean>> provideLocationServicesOkObservableProvider;
      private Provider<LocationServicesStatus> provideLocationServicesStatusProvider;
      private Provider<String[][]> provideRecommendedConnectRuntimePermissionNamesProvider;
      private Provider<String[][]> provideRecommendedScanRuntimePermissionNamesProvider;
      private Provider<ScanPreconditionsVerifier> provideScanPreconditionVerifierProvider;
      private Provider<ScanSetupBuilder> provideScanSetupProvider;
      private Provider<Integer> provideTargetSdkProvider;
      private Provider<RxBleAdapterStateObservable> rxBleAdapterStateObservableProvider;
      private Provider<RxBleAdapterWrapper> rxBleAdapterWrapperProvider;
      private Provider<RxBleClientImpl> rxBleClientImplProvider;
      private Provider<RxBleDeviceProvider> rxBleDeviceProvider;
      private Provider<ScanPreconditionsVerifierApi18> scanPreconditionsVerifierApi18Provider;
      private Provider<ScanPreconditionsVerifierApi24> scanPreconditionsVerifierApi24Provider;
      private Provider<ScanSettingsEmulator> scanSettingsEmulatorProvider;
      private Provider<ScanSetupBuilderImplApi18> scanSetupBuilderImplApi18Provider;
      private Provider<ScanSetupBuilderImplApi21> scanSetupBuilderImplApi21Provider;
      private Provider<ScanSetupBuilderImplApi23> scanSetupBuilderImplApi23Provider;

      private ClientComponentImpl(Context var1) {
         this.applicationContext = var1;
         this.initialize(var1);
      }

      private void initialize(Context var1) {
         Factory var2 = InstanceFactory.create(var1);
         this.applicationContextProvider = var2;
         this.provideContentResolverProvider = ClientComponent_ClientModule_ProvideContentResolverFactory.create(var2);
         ClientComponent_ClientModule_ProvideLocationManagerFactory var3 = ClientComponent_ClientModule_ProvideLocationManagerFactory.create(
            this.applicationContextProvider
         );
         this.provideLocationManagerProvider = var3;
         this.checkerLocationProvider = CheckerLocationProvider_Factory.create(this.provideContentResolverProvider, var3);
         this.checkerPermissionProvider = DoubleCheck.provider(CheckerPermission_Factory.create(this.applicationContextProvider));
         this.provideTargetSdkProvider = ClientComponent_ClientModule_ProvideTargetSdkFactory.create(this.applicationContextProvider);
         this.provideIsNearbyPermissionNeverForLocationProvider = DoubleCheck.provider(
            ClientComponent_ClientModule_ProvideIsNearbyPermissionNeverForLocationFactory.create(this.applicationContextProvider)
         );
         ClientComponent_ClientModule_ProvideRecommendedScanRuntimePermissionNamesFactory var4 = ClientComponent_ClientModule_ProvideRecommendedScanRuntimePermissionNamesFactory.create(
            ClientComponent_ClientModule_ProvideDeviceSdkFactory.create(),
            this.provideTargetSdkProvider,
            this.provideIsNearbyPermissionNeverForLocationProvider
         );
         this.provideRecommendedScanRuntimePermissionNamesProvider = var4;
         this.checkerScanPermissionProvider = DoubleCheck.provider(CheckerScanPermission_Factory.create(this.checkerPermissionProvider, var4));
         this.provideIsAndroidWearProvider = ClientComponent_ClientModule_ProvideIsAndroidWearFactory.create(
            this.applicationContextProvider, ClientComponent_ClientModule_ProvideDeviceSdkFactory.create()
         );
         this.locationServicesStatusApi23Provider = LocationServicesStatusApi23_Factory.create(
            this.checkerLocationProvider,
            this.checkerScanPermissionProvider,
            this.provideTargetSdkProvider,
            ClientComponent_ClientModule_ProvideDeviceSdkFactory.create(),
            this.provideIsAndroidWearProvider
         );
         this.locationServicesStatusApi31Provider = LocationServicesStatusApi31_Factory.create(
            this.checkerLocationProvider,
            this.checkerScanPermissionProvider,
            this.provideIsAndroidWearProvider,
            this.provideIsNearbyPermissionNeverForLocationProvider
         );
         ClientComponent_ClientModule_ProvideBluetoothManagerFactory var5 = ClientComponent_ClientModule_ProvideBluetoothManagerFactory.create(
            this.applicationContextProvider
         );
         this.provideBluetoothManagerProvider = var5;
         this.bluetoothManagerWrapperProvider = BluetoothManagerWrapper_Factory.create(var5);
         this.rxBleAdapterWrapperProvider = RxBleAdapterWrapper_Factory.create(ClientComponent_ClientModule_ProvideBluetoothAdapterFactory.create());
         Provider var6 = DoubleCheck.provider(ClientComponent_ClientModule_ProvideBluetoothInteractionExecutorServiceFactory.create());
         this.provideBluetoothInteractionExecutorServiceProvider = var6;
         Provider var7 = DoubleCheck.provider(ClientComponent_ClientModule_ProvideBluetoothInteractionSchedulerFactory.create(var6));
         this.provideBluetoothInteractionSchedulerProvider = var7;
         ClientOperationQueueImpl_Factory var8 = ClientOperationQueueImpl_Factory.create(var7);
         this.clientOperationQueueImplProvider = var8;
         this.bindClientOperationQueueProvider = DoubleCheck.provider(var8);
         this.rxBleAdapterStateObservableProvider = RxBleAdapterStateObservable_Factory.create(this.applicationContextProvider);
         ClientComponent_ClientModule_ProvideLocationServicesStatusFactory var9 = ClientComponent_ClientModule_ProvideLocationServicesStatusFactory.create(
            ClientComponent_ClientModule_ProvideDeviceSdkFactory.create(),
            LocationServicesStatusApi18_Factory.create(),
            this.locationServicesStatusApi23Provider,
            this.locationServicesStatusApi31Provider
         );
         this.provideLocationServicesStatusProvider = var9;
         this.locationServicesOkObservableApi23FactoryProvider = LocationServicesOkObservableApi23Factory_Factory.create(this.applicationContextProvider, var9);
         ClientComponent_ClientModule_ProvideLocationServicesOkObservableFactory var10 = ClientComponent_ClientModule_ProvideLocationServicesOkObservableFactory.create(
            ClientComponent_ClientModule_ProvideDeviceSdkFactory.create(), this.locationServicesOkObservableApi23FactoryProvider
         );
         this.provideLocationServicesOkObservableProvider = var10;
         this.clientStateObservableProvider = ClientStateObservable_Factory.create(
            this.rxBleAdapterWrapperProvider,
            this.rxBleAdapterStateObservableProvider,
            var10,
            this.provideLocationServicesStatusProvider,
            ClientComponent_ClientModule_ProvideComputationSchedulerFactory.create()
         );
         this.deviceComponentCacheProvider = DoubleCheck.provider(DeviceComponentCache_Factory.create());
         Provider var11 = new Provider<DeviceComponent.Builder>(this) {
            final DaggerClientComponent.ClientComponentImpl this$0;

            {
               this.this$0 = var1;
            }

            public DeviceComponent.Builder get() {
               return new DaggerClientComponent.DeviceComponentBuilder(this.this$0.clientComponentImpl);
            }
         };
         this.deviceComponentBuilderProvider = var11;
         this.rxBleDeviceProvider = DoubleCheck.provider(RxBleDeviceProvider_Factory.create(this.deviceComponentCacheProvider, var11));
         this.provideIsConnectableCheckerProvider = DoubleCheck.provider(
            ClientComponent_ClientModule_ProvideIsConnectableCheckerFactory.create(
               ClientComponent_ClientModule_ProvideDeviceSdkFactory.create(),
               IsConnectableCheckerApi18_Factory.create(),
               IsConnectableCheckerApi26_Factory.create()
            )
         );
         this.internalScanResultCreatorProvider = DoubleCheck.provider(
            InternalScanResultCreator_Factory.create(ScanRecordParser_Factory.create(), this.provideIsConnectableCheckerProvider)
         );
         ScanSettingsEmulator_Factory var12 = ScanSettingsEmulator_Factory.create(ClientComponent_ClientModule_ProvideComputationSchedulerFactory.create());
         this.scanSettingsEmulatorProvider = var12;
         this.scanSetupBuilderImplApi18Provider = ScanSetupBuilderImplApi18_Factory.create(
            this.rxBleAdapterWrapperProvider, this.internalScanResultCreatorProvider, var12
         );
         AndroidScanObjectsConverter_Factory var13 = AndroidScanObjectsConverter_Factory.create(ClientComponent_ClientModule_ProvideDeviceSdkFactory.create());
         this.androidScanObjectsConverterProvider = var13;
         this.scanSetupBuilderImplApi21Provider = ScanSetupBuilderImplApi21_Factory.create(
            this.rxBleAdapterWrapperProvider, this.internalScanResultCreatorProvider, this.scanSettingsEmulatorProvider, var13
         );
         this.scanSetupBuilderImplApi23Provider = ScanSetupBuilderImplApi23_Factory.create(
            this.rxBleAdapterWrapperProvider,
            this.internalScanResultCreatorProvider,
            this.scanSettingsEmulatorProvider,
            this.androidScanObjectsConverterProvider
         );
         this.provideScanSetupProvider = DoubleCheck.provider(
            ClientComponent_ClientModule_ProvideScanSetupProviderFactory.create(
               ClientComponent_ClientModule_ProvideDeviceSdkFactory.create(),
               this.scanSetupBuilderImplApi18Provider,
               this.scanSetupBuilderImplApi21Provider,
               this.scanSetupBuilderImplApi23Provider
            )
         );
         ScanPreconditionsVerifierApi18_Factory var14 = ScanPreconditionsVerifierApi18_Factory.create(
            this.rxBleAdapterWrapperProvider, this.provideLocationServicesStatusProvider
         );
         this.scanPreconditionsVerifierApi18Provider = var14;
         this.scanPreconditionsVerifierApi24Provider = ScanPreconditionsVerifierApi24_Factory.create(
            var14, ClientComponent_ClientModule_ProvideComputationSchedulerFactory.create()
         );
         this.provideScanPreconditionVerifierProvider = ClientComponent_ClientModule_ProvideScanPreconditionVerifierFactory.create(
            ClientComponent_ClientModule_ProvideDeviceSdkFactory.create(),
            this.scanPreconditionsVerifierApi18Provider,
            this.scanPreconditionsVerifierApi24Provider
         );
         this.internalToExternalScanResultConverterProvider = InternalToExternalScanResultConverter_Factory.create(this.rxBleDeviceProvider);
         this.provideBluetoothCallbacksSchedulerProvider = DoubleCheck.provider(ClientComponent_ClientModule_ProvideBluetoothCallbacksSchedulerFactory.create());
         Provider var15 = DoubleCheck.provider(ClientComponent_ClientModule_ProvideConnectionQueueExecutorServiceFactory.create());
         this.provideConnectionQueueExecutorServiceProvider = var15;
         this.provideFinalizationCloseableProvider = ClientComponent_ClientModule_ProvideFinalizationCloseableFactory.create(
            this.provideBluetoothInteractionExecutorServiceProvider, this.provideBluetoothCallbacksSchedulerProvider, var15
         );
         this.backgroundScannerImplProvider = BackgroundScannerImpl_Factory.create(
            this.rxBleAdapterWrapperProvider,
            this.androidScanObjectsConverterProvider,
            this.internalScanResultCreatorProvider,
            this.internalToExternalScanResultConverterProvider
         );
         ClientComponent_ClientModule_ProvideRecommendedConnectRuntimePermissionNamesFactory var16 = ClientComponent_ClientModule_ProvideRecommendedConnectRuntimePermissionNamesFactory.create(
            ClientComponent_ClientModule_ProvideDeviceSdkFactory.create(), this.provideTargetSdkProvider
         );
         this.provideRecommendedConnectRuntimePermissionNamesProvider = var16;
         this.checkerConnectPermissionProvider = DoubleCheck.provider(CheckerConnectPermission_Factory.create(this.checkerPermissionProvider, var16));
         RxBleClientImpl_Factory var17 = RxBleClientImpl_Factory.create(
            this.bluetoothManagerWrapperProvider,
            this.rxBleAdapterWrapperProvider,
            this.bindClientOperationQueueProvider,
            this.rxBleAdapterStateObservableProvider,
            ScanRecordParser_Factory.create(),
            this.provideLocationServicesStatusProvider,
            this.clientStateObservableProvider,
            this.rxBleDeviceProvider,
            this.provideScanSetupProvider,
            this.provideScanPreconditionVerifierProvider,
            this.internalToExternalScanResultConverterProvider,
            this.provideBluetoothInteractionSchedulerProvider,
            this.provideFinalizationCloseableProvider,
            this.backgroundScannerImplProvider,
            this.checkerScanPermissionProvider,
            this.checkerConnectPermissionProvider
         );
         this.rxBleClientImplProvider = var17;
         this.bindRxBleClientProvider = DoubleCheck.provider(var17);
      }

      private LocationServicesOkObservableApi23Factory locationServicesOkObservableApi23Factory() {
         return LocationServicesOkObservableApi23Factory_Factory.newInstance(this.applicationContext, this.locationServicesStatus());
      }

      private LocationServicesStatus locationServicesStatus() {
         return ClientComponent_ClientModule_ProvideLocationServicesStatusFactory.provideLocationServicesStatus(
            ClientComponent.ClientModule.provideDeviceSdk(),
            LocationServicesStatusApi18_Factory.create(),
            this.locationServicesStatusApi23Provider,
            this.locationServicesStatusApi31Provider
         );
      }

      private Observable<Boolean> namedObservableOfBoolean() {
         return ClientComponent_ClientModule_ProvideLocationServicesOkObservableFactory.provideLocationServicesOkObservable(
            ClientComponent.ClientModule.provideDeviceSdk(), this.locationServicesOkObservableApi23Factory()
         );
      }

      private RxBleAdapterWrapper rxBleAdapterWrapper() {
         return new RxBleAdapterWrapper(ClientComponent.ClientModule.provideBluetoothAdapter());
      }

      @Override
      public LocationServicesOkObservable locationServicesOkObservable() {
         return LocationServicesOkObservable_Factory.newInstance(this.namedObservableOfBoolean());
      }

      @Override
      public RxBleClient rxBleClient() {
         return (RxBleClient)this.bindRxBleClientProvider.get();
      }
   }

   private static final class ConnectionComponentBuilder implements ConnectionComponent.Builder {
      private Boolean autoConnect;
      private final DaggerClientComponent.ClientComponentImpl clientComponentImpl;
      private final DaggerClientComponent.DeviceComponentImpl deviceComponentImpl;
      private Timeout operationTimeout;
      private Boolean suppressOperationChecks;

      private ConnectionComponentBuilder(DaggerClientComponent.ClientComponentImpl var1, DaggerClientComponent.DeviceComponentImpl var2) {
         this.clientComponentImpl = var1;
         this.deviceComponentImpl = var2;
      }

      public DaggerClientComponent.ConnectionComponentBuilder autoConnect(boolean var1) {
         this.autoConnect = (Boolean)Preconditions.checkNotNull(var1);
         return this;
      }

      @Override
      public ConnectionComponent build() {
         Preconditions.checkBuilderRequirement(this.autoConnect, Boolean.class);
         Preconditions.checkBuilderRequirement(this.suppressOperationChecks, Boolean.class);
         Preconditions.checkBuilderRequirement(this.operationTimeout, Timeout.class);
         return new DaggerClientComponent.ConnectionComponentImpl(
            this.clientComponentImpl, this.deviceComponentImpl, this.autoConnect, this.suppressOperationChecks, this.operationTimeout
         );
      }

      public DaggerClientComponent.ConnectionComponentBuilder operationTimeout(Timeout var1) {
         this.operationTimeout = (Timeout)Preconditions.checkNotNull(var1);
         return this;
      }

      public DaggerClientComponent.ConnectionComponentBuilder suppressOperationChecks(boolean var1) {
         this.suppressOperationChecks = (Boolean)Preconditions.checkNotNull(var1);
         return this;
      }
   }

   private static final class ConnectionComponentImpl implements ConnectionComponent {
      private final Boolean autoConnect;
      private Provider<BluetoothGattProvider> bluetoothGattProvider;
      private final DaggerClientComponent.ClientComponentImpl clientComponentImpl;
      private final DaggerClientComponent.ConnectionComponentImpl connectionComponentImpl = this;
      private Provider<ConnectionOperationQueueImpl> connectionOperationQueueImplProvider;
      private Provider descriptorWriterProvider;
      private final DaggerClientComponent.DeviceComponentImpl deviceComponentImpl;
      private Provider disconnectActionProvider;
      private Provider<DisconnectOperation> disconnectOperationProvider;
      private Provider disconnectionRouterProvider;
      private Provider<IllegalOperationChecker> illegalOperationCheckerProvider;
      private Provider<IllegalOperationMessageCreator> illegalOperationMessageCreatorProvider;
      private Provider<LoggerUtilBluetoothServices> loggerUtilBluetoothServicesProvider;
      private Provider<LoggingIllegalOperationHandler> loggingIllegalOperationHandlerProvider;
      private Provider<LongWriteOperationBuilderImpl> longWriteOperationBuilderImplProvider;
      private Provider mtuBasedPayloadSizeLimitProvider;
      private Provider mtuWatcherProvider;
      private Provider notificationAndIndicationManagerProvider;
      private Provider<Timeout> operationTimeoutProvider;
      private Provider<OperationsProviderImpl> operationsProviderImplProvider;
      private Provider<BluetoothGatt> provideBluetoothGattProvider;
      private Provider<IllegalOperationHandler> provideIllegalOperationHandlerProvider;
      private Provider<TimeoutConfiguration> providesOperationTimeoutConfProvider;
      private Provider<ReadRssiOperation> readRssiOperationProvider;
      private Provider<RxBleConnectionImpl> rxBleConnectionImplProvider;
      private Provider<RxBleGattCallback> rxBleGattCallbackProvider;
      private Provider serviceDiscoveryManagerProvider;
      private Provider<Boolean> suppressOperationChecksProvider;
      private Provider<ThrowingIllegalOperationHandler> throwingIllegalOperationHandlerProvider;

      private ConnectionComponentImpl(
         DaggerClientComponent.ClientComponentImpl var1, DaggerClientComponent.DeviceComponentImpl var2, Boolean var3, Boolean var4, Timeout var5
      ) {
         this.clientComponentImpl = var1;
         this.deviceComponentImpl = var2;
         this.autoConnect = var3;
         this.initialize(var3, var4, var5);
      }

      private BleConnectionCompat bleConnectionCompat() {
         return new BleConnectionCompat(this.clientComponentImpl.applicationContext);
      }

      private void initialize(Boolean var1, Boolean var2, Timeout var3) {
         this.bluetoothGattProvider = DoubleCheck.provider(BluetoothGattProvider_Factory.create());
         this.disconnectionRouterProvider = DoubleCheck.provider(
            DisconnectionRouter_Factory.create(
               this.deviceComponentImpl.macAddressProvider,
               this.clientComponentImpl.rxBleAdapterWrapperProvider,
               this.clientComponentImpl.rxBleAdapterStateObservableProvider
            )
         );
         this.rxBleGattCallbackProvider = DoubleCheck.provider(
            RxBleGattCallback_Factory.create(
               this.clientComponentImpl.provideBluetoothCallbacksSchedulerProvider,
               this.bluetoothGattProvider,
               this.disconnectionRouterProvider,
               NativeCallbackDispatcher_Factory.create()
            )
         );
         this.connectionOperationQueueImplProvider = DoubleCheck.provider(
            ConnectionOperationQueueImpl_Factory.create(
               this.deviceComponentImpl.macAddressProvider,
               this.disconnectionRouterProvider,
               this.clientComponentImpl.provideConnectionQueueExecutorServiceProvider,
               this.clientComponentImpl.provideBluetoothInteractionSchedulerProvider
            )
         );
         this.provideBluetoothGattProvider = ConnectionModule_ProvideBluetoothGattFactory.create(this.bluetoothGattProvider);
         this.loggerUtilBluetoothServicesProvider = LoggerUtilBluetoothServices_Factory.create(
            ConnectionModule_ProvideCharacteristicPropertiesParserFactory.create()
         );
         this.operationTimeoutProvider = InstanceFactory.create(var3);
         ConnectionModule_ProvidesOperationTimeoutConfFactory var4 = ConnectionModule_ProvidesOperationTimeoutConfFactory.create(
            ClientComponent_ClientModule_ProvideComputationSchedulerFactory.create(), this.operationTimeoutProvider
         );
         this.providesOperationTimeoutConfProvider = var4;
         this.readRssiOperationProvider = ReadRssiOperation_Factory.create(this.rxBleGattCallbackProvider, this.provideBluetoothGattProvider, var4);
         OperationsProviderImpl_Factory var5 = OperationsProviderImpl_Factory.create(
            this.rxBleGattCallbackProvider,
            this.provideBluetoothGattProvider,
            this.loggerUtilBluetoothServicesProvider,
            this.providesOperationTimeoutConfProvider,
            this.clientComponentImpl.provideBluetoothInteractionSchedulerProvider,
            ClientComponent_ClientModule_ProvideComputationSchedulerFactory.create(),
            this.readRssiOperationProvider
         );
         this.operationsProviderImplProvider = var5;
         this.serviceDiscoveryManagerProvider = DoubleCheck.provider(
            ServiceDiscoveryManager_Factory.create(this.connectionOperationQueueImplProvider, this.provideBluetoothGattProvider, var5)
         );
         this.descriptorWriterProvider = DoubleCheck.provider(
            DescriptorWriter_Factory.create(this.connectionOperationQueueImplProvider, this.operationsProviderImplProvider)
         );
         this.notificationAndIndicationManagerProvider = DoubleCheck.provider(
            NotificationAndIndicationManager_Factory.create(
               ClientComponent_ClientModule_ProvideEnableNotificationValueFactory.create(),
               ClientComponent_ClientModule_ProvideEnableIndicationValueFactory.create(),
               ClientComponent_ClientModule_ProvideDisableNotificationValueFactory.create(),
               this.provideBluetoothGattProvider,
               this.rxBleGattCallbackProvider,
               this.descriptorWriterProvider
            )
         );
         this.mtuWatcherProvider = DoubleCheck.provider(MtuWatcher_Factory.create(this.rxBleGattCallbackProvider, ConnectionModule_MinimumMtuFactory.create()));
         DelegateFactory var6 = new DelegateFactory();
         this.rxBleConnectionImplProvider = var6;
         Provider var7 = DoubleCheck.provider(MtuBasedPayloadSizeLimit_Factory.create(var6, ConnectionModule_GattWriteMtuOverheadFactory.create()));
         this.mtuBasedPayloadSizeLimitProvider = var7;
         this.longWriteOperationBuilderImplProvider = LongWriteOperationBuilderImpl_Factory.create(
            this.connectionOperationQueueImplProvider, var7, this.rxBleConnectionImplProvider, this.operationsProviderImplProvider
         );
         this.suppressOperationChecksProvider = InstanceFactory.create(var2);
         IllegalOperationMessageCreator_Factory var8 = IllegalOperationMessageCreator_Factory.create(
            ConnectionModule_ProvideCharacteristicPropertiesParserFactory.create()
         );
         this.illegalOperationMessageCreatorProvider = var8;
         this.loggingIllegalOperationHandlerProvider = LoggingIllegalOperationHandler_Factory.create(var8);
         ThrowingIllegalOperationHandler_Factory var9 = ThrowingIllegalOperationHandler_Factory.create(this.illegalOperationMessageCreatorProvider);
         this.throwingIllegalOperationHandlerProvider = var9;
         ConnectionModule_ProvideIllegalOperationHandlerFactory var10 = ConnectionModule_ProvideIllegalOperationHandlerFactory.create(
            this.suppressOperationChecksProvider, this.loggingIllegalOperationHandlerProvider, var9
         );
         this.provideIllegalOperationHandlerProvider = var10;
         this.illegalOperationCheckerProvider = IllegalOperationChecker_Factory.create(var10);
         DelegateFactory.setDelegate(
            this.rxBleConnectionImplProvider,
            DoubleCheck.provider(
               RxBleConnectionImpl_Factory.create(
                  this.connectionOperationQueueImplProvider,
                  this.rxBleGattCallbackProvider,
                  this.provideBluetoothGattProvider,
                  this.serviceDiscoveryManagerProvider,
                  this.notificationAndIndicationManagerProvider,
                  this.mtuWatcherProvider,
                  this.descriptorWriterProvider,
                  this.operationsProviderImplProvider,
                  this.longWriteOperationBuilderImplProvider,
                  this.clientComponentImpl.provideBluetoothInteractionSchedulerProvider,
                  this.illegalOperationCheckerProvider
               )
            )
         );
         this.disconnectOperationProvider = DisconnectOperation_Factory.create(
            this.rxBleGattCallbackProvider,
            this.bluetoothGattProvider,
            this.deviceComponentImpl.macAddressProvider,
            this.clientComponentImpl.provideBluetoothManagerProvider,
            this.clientComponentImpl.provideBluetoothInteractionSchedulerProvider,
            this.deviceComponentImpl.providesDisconnectTimeoutConfProvider,
            this.deviceComponentImpl.provideConnectionStateChangeListenerProvider
         );
         this.disconnectActionProvider = DoubleCheck.provider(
            DisconnectAction_Factory.create(this.clientComponentImpl.bindClientOperationQueueProvider, this.disconnectOperationProvider)
         );
      }

      @Override
      public ConnectOperation connectOperation() {
         return ConnectOperation_Factory.newInstance(
            this.deviceComponentImpl.bluetoothDevice(),
            this.bleConnectionCompat(),
            (RxBleGattCallback)this.rxBleGattCallbackProvider.get(),
            (BluetoothGattProvider)this.bluetoothGattProvider.get(),
            this.deviceComponentImpl.namedTimeoutConfiguration(),
            this.autoConnect,
            (ConnectionStateChangeListener)this.deviceComponentImpl.provideConnectionStateChangeListenerProvider.get()
         );
      }

      @Override
      public Set<ConnectionSubscriptionWatcher> connectionSubscriptionWatchers() {
         return SetBuilder.newSetBuilder(3)
            .add((ConnectionSubscriptionWatcher)this.mtuWatcherProvider.get())
            .add((ConnectionSubscriptionWatcher)this.disconnectActionProvider.get())
            .add((ConnectionSubscriptionWatcher)this.connectionOperationQueueImplProvider.get())
            .build();
      }

      @Override
      public RxBleGattCallback gattCallback() {
         return (RxBleGattCallback)this.rxBleGattCallbackProvider.get();
      }

      @Override
      public RxBleConnection rxBleConnection() {
         return (RxBleConnection)this.rxBleConnectionImplProvider.get();
      }
   }

   private static final class DeviceComponentBuilder implements DeviceComponent.Builder {
      private final DaggerClientComponent.ClientComponentImpl clientComponentImpl;
      private String macAddress;

      private DeviceComponentBuilder(DaggerClientComponent.ClientComponentImpl var1) {
         this.clientComponentImpl = var1;
      }

      @Override
      public DeviceComponent build() {
         Preconditions.checkBuilderRequirement(this.macAddress, String.class);
         return new DaggerClientComponent.DeviceComponentImpl(this.clientComponentImpl, this.macAddress);
      }

      public DaggerClientComponent.DeviceComponentBuilder macAddress(String var1) {
         this.macAddress = (String)Preconditions.checkNotNull(var1);
         return this;
      }
   }

   private static final class DeviceComponentImpl implements DeviceComponent {
      private final DaggerClientComponent.ClientComponentImpl clientComponentImpl;
      private Provider<ConnectionComponent.Builder> connectionComponentBuilderProvider;
      private Provider<ConnectorImpl> connectorImplProvider;
      private final DaggerClientComponent.DeviceComponentImpl deviceComponentImpl = this;
      private final String macAddress;
      private Provider<String> macAddressProvider;
      private Provider<BluetoothDevice> provideBluetoothDeviceProvider;
      private Provider<ConnectionStateChangeListener> provideConnectionStateChangeListenerProvider;
      private Provider<BehaviorRelay<RxBleConnection.RxBleConnectionState>> provideConnectionStateRelayProvider;
      private Provider<TimeoutConfiguration> providesDisconnectTimeoutConfProvider;
      private Provider rxBleDeviceImplProvider;

      private DeviceComponentImpl(DaggerClientComponent.ClientComponentImpl var1, String var2) {
         this.clientComponentImpl = var1;
         this.macAddress = var2;
         this.initialize(var2);
      }

      private BluetoothDevice bluetoothDevice() {
         return DeviceModule_ProvideBluetoothDeviceFactory.provideBluetoothDevice(this.macAddress, this.clientComponentImpl.rxBleAdapterWrapper());
      }

      private void initialize(String var1) {
         Factory var2 = InstanceFactory.create(var1);
         this.macAddressProvider = var2;
         this.provideBluetoothDeviceProvider = DeviceModule_ProvideBluetoothDeviceFactory.create(var2, this.clientComponentImpl.rxBleAdapterWrapperProvider);
         this.connectionComponentBuilderProvider = new Provider<ConnectionComponent.Builder>(this) {
            final DaggerClientComponent.DeviceComponentImpl this$0;

            {
               this.this$0 = var1;
            }

            public ConnectionComponent.Builder get() {
               return new DaggerClientComponent.ConnectionComponentBuilder(this.this$0.clientComponentImpl, this.this$0.deviceComponentImpl);
            }
         };
         this.connectorImplProvider = ConnectorImpl_Factory.create(
            this.clientComponentImpl.bindClientOperationQueueProvider,
            this.connectionComponentBuilderProvider,
            this.clientComponentImpl.provideBluetoothCallbacksSchedulerProvider
         );
         Provider var3 = DoubleCheck.provider(DeviceModule_ProvideConnectionStateRelayFactory.create());
         this.provideConnectionStateRelayProvider = var3;
         this.rxBleDeviceImplProvider = DoubleCheck.provider(
            RxBleDeviceImpl_Factory.create(
               this.provideBluetoothDeviceProvider, this.connectorImplProvider, var3, this.clientComponentImpl.checkerConnectPermissionProvider
            )
         );
         this.provideConnectionStateChangeListenerProvider = DoubleCheck.provider(
            DeviceModule_ProvideConnectionStateChangeListenerFactory.create(this.provideConnectionStateRelayProvider)
         );
         this.providesDisconnectTimeoutConfProvider = DeviceModule_ProvidesDisconnectTimeoutConfFactory.create(
            ClientComponent_ClientModule_ProvideComputationSchedulerFactory.create()
         );
      }

      private TimeoutConfiguration namedTimeoutConfiguration() {
         return DeviceModule_ProvidesConnectTimeoutConfFactory.providesConnectTimeoutConf(
            ClientComponent_ClientModule_ProvideComputationSchedulerFactory.provideComputationScheduler()
         );
      }

      @Override
      public RxBleDevice provideDevice() {
         return (RxBleDevice)this.rxBleDeviceImplProvider.get();
      }
   }
}
