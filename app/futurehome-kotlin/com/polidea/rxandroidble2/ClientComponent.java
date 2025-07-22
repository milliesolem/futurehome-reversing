package com.polidea.rxandroidble2;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.LocationManager;
import android.os.Build.VERSION;
import bleshadow.dagger.Binds;
import bleshadow.dagger.BindsInstance;
import bleshadow.dagger.Component;
import bleshadow.dagger.Module;
import bleshadow.dagger.Provides;
import bleshadow.javax.inject.Named;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.helpers.LocationServicesOkObservable;
import com.polidea.rxandroidble2.internal.DeviceComponent;
import com.polidea.rxandroidble2.internal.RxBleLog;
import com.polidea.rxandroidble2.internal.scan.BackgroundScannerImpl;
import com.polidea.rxandroidble2.internal.scan.InternalToExternalScanResultConverter;
import com.polidea.rxandroidble2.internal.scan.IsConnectableChecker;
import com.polidea.rxandroidble2.internal.scan.IsConnectableCheckerApi18;
import com.polidea.rxandroidble2.internal.scan.IsConnectableCheckerApi26;
import com.polidea.rxandroidble2.internal.scan.RxBleInternalScanResult;
import com.polidea.rxandroidble2.internal.scan.ScanPreconditionsVerifier;
import com.polidea.rxandroidble2.internal.scan.ScanPreconditionsVerifierApi18;
import com.polidea.rxandroidble2.internal.scan.ScanPreconditionsVerifierApi24;
import com.polidea.rxandroidble2.internal.scan.ScanSetupBuilder;
import com.polidea.rxandroidble2.internal.scan.ScanSetupBuilderImplApi18;
import com.polidea.rxandroidble2.internal.scan.ScanSetupBuilderImplApi21;
import com.polidea.rxandroidble2.internal.scan.ScanSetupBuilderImplApi23;
import com.polidea.rxandroidble2.internal.serialization.ClientOperationQueue;
import com.polidea.rxandroidble2.internal.serialization.ClientOperationQueueImpl;
import com.polidea.rxandroidble2.internal.serialization.RxBleThreadFactory;
import com.polidea.rxandroidble2.internal.util.LocationServicesOkObservableApi23Factory;
import com.polidea.rxandroidble2.internal.util.LocationServicesStatus;
import com.polidea.rxandroidble2.internal.util.LocationServicesStatusApi18;
import com.polidea.rxandroidble2.internal.util.LocationServicesStatusApi23;
import com.polidea.rxandroidble2.internal.util.LocationServicesStatusApi31;
import com.polidea.rxandroidble2.internal.util.ObservableUtil;
import com.polidea.rxandroidble2.scan.BackgroundScanner;
import com.polidea.rxandroidble2.scan.ScanResult;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component(
   modules = {ClientComponent.ClientModule.class}
)
@ClientScope
public interface ClientComponent {
   LocationServicesOkObservable locationServicesOkObservable();

   RxBleClient rxBleClient();

   public static class BluetoothConstants {
      public static final String DISABLE_NOTIFICATION_VALUE = "disable-notification-value";
      public static final String ENABLE_INDICATION_VALUE = "enable-indication-value";
      public static final String ENABLE_NOTIFICATION_VALUE = "enable-notification-value";

      private BluetoothConstants() {
      }
   }

   @bleshadow.dagger.Component.Builder
   public interface Builder {
      @BindsInstance
      ClientComponent.Builder applicationContext(Context var1);

      ClientComponent build();
   }

   public interface ClientComponentFinalizer {
      void onFinalize();
   }

   @Module(
      subcomponents = {DeviceComponent.class}
   )
   public abstract static class ClientModule {
      @Provides
      static BluetoothAdapter provideBluetoothAdapter() {
         return BluetoothAdapter.getDefaultAdapter();
      }

      @Provides
      @Named("bluetooth_callbacks")
      @ClientScope
      static Scheduler provideBluetoothCallbacksScheduler() {
         return RxJavaPlugins.createSingleScheduler(new RxBleThreadFactory());
      }

      @Provides
      @Named("executor_bluetooth_interaction")
      @ClientScope
      static ExecutorService provideBluetoothInteractionExecutorService() {
         return Executors.newSingleThreadExecutor();
      }

      @Provides
      @Named("bluetooth_interaction")
      @ClientScope
      static Scheduler provideBluetoothInteractionScheduler(@Named("executor_bluetooth_interaction") ExecutorService var0) {
         return Schedulers.from(var0);
      }

      @Provides
      static BluetoothManager provideBluetoothManager(Context var0) {
         return (BluetoothManager)var0.getSystemService("bluetooth");
      }

      @Provides
      @Named("computation")
      static Scheduler provideComputationScheduler() {
         return Schedulers.computation();
      }

      @Provides
      @Named("executor_connection_queue")
      @ClientScope
      static ExecutorService provideConnectionQueueExecutorService() {
         return Executors.newCachedThreadPool();
      }

      @Provides
      static ContentResolver provideContentResolver(Context var0) {
         return var0.getContentResolver();
      }

      @Provides
      @Named("device-sdk")
      static int provideDeviceSdk() {
         return VERSION.SDK_INT;
      }

      @Provides
      @Named("disable-notification-value")
      static byte[] provideDisableNotificationValue() {
         return BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE;
      }

      @Provides
      @Named("enable-indication-value")
      static byte[] provideEnableIndicationValue() {
         return BluetoothGattDescriptor.ENABLE_INDICATION_VALUE;
      }

      @Provides
      @Named("enable-notification-value")
      static byte[] provideEnableNotificationValue() {
         return BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE;
      }

      @Provides
      static ClientComponent.ClientComponentFinalizer provideFinalizationCloseable(
         @Named("executor_bluetooth_interaction") ExecutorService var0,
         @Named("bluetooth_callbacks") Scheduler var1,
         @Named("executor_connection_queue") ExecutorService var2
      ) {
         return new ClientComponent.ClientComponentFinalizer(var0, var1, var2) {
            final Scheduler val$callbacksScheduler;
            final ExecutorService val$connectionQueueExecutorService;
            final ExecutorService val$interactionExecutorService;

            {
               this.val$interactionExecutorService = var1;
               this.val$callbacksScheduler = var2x;
               this.val$connectionQueueExecutorService = var3;
            }

            @Override
            public void onFinalize() {
               this.val$interactionExecutorService.shutdown();
               this.val$callbacksScheduler.shutdown();
               this.val$connectionQueueExecutorService.shutdown();
            }
         };
      }

      @Provides
      @Named("android-wear")
      static boolean provideIsAndroidWear(Context var0, @Named("device-sdk") int var1) {
         boolean var2;
         if (var1 >= 20 && var0.getPackageManager().hasSystemFeature("android.hardware.type.watch")) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      @Provides
      @ClientScope
      static IsConnectableChecker provideIsConnectableChecker(
         @Named("device-sdk") int var0, Provider<IsConnectableCheckerApi18> var1, Provider<IsConnectableCheckerApi26> var2
      ) {
         return var0 < 26 ? (IsConnectableChecker)var1.get() : (IsConnectableChecker)var2.get();
      }

      // $VF: Duplicated exception handlers to handle obfuscated exceptions
      @Provides
      @Named("nearby-permission-never-for-location")
      @ClientScope
      static boolean provideIsNearbyPermissionNeverForLocation(Context var0) {
         boolean var2 = false;

         try {
            var6 = var0.getPackageManager().getPackageInfo(var0.getPackageName(), 4096);
         } catch (NameNotFoundException var5) {
            RxBleLog.e(var5, "Could not find application PackageInfo");
            return false;
         }

         int var1 = 0;

         while (true) {
            try {
               if (var1 >= var6.requestedPermissions.length) {
                  return false;
               }

               if ("android.permission.BLUETOOTH_SCAN".equals(var6.requestedPermissions[var1])) {
                  break;
               }
            } catch (NameNotFoundException var4) {
               RxBleLog.e(var4, "Could not find application PackageInfo");
               return false;
            }

            var1++;
         }

         try {
            var1 = var6.requestedPermissionsFlags[var1];
         } catch (NameNotFoundException var3) {
            RxBleLog.e(var3, "Could not find application PackageInfo");
            return false;
         }

         if ((var1 & 65536) != 0) {
            var2 = true;
         }

         return var2;
      }

      @Provides
      static LocationManager provideLocationManager(Context var0) {
         return (LocationManager)var0.getSystemService("location");
      }

      @Provides
      @Named("location-ok-boolean-observable")
      static Observable<Boolean> provideLocationServicesOkObservable(@Named("device-sdk") int var0, LocationServicesOkObservableApi23Factory var1) {
         Observable var2;
         if (var0 < 23) {
            var2 = ObservableUtil.justOnNext(true);
         } else {
            var2 = var1.get();
         }

         return var2;
      }

      @Provides
      static LocationServicesStatus provideLocationServicesStatus(
         @Named("device-sdk") int var0,
         Provider<LocationServicesStatusApi18> var1,
         Provider<LocationServicesStatusApi23> var2,
         Provider<LocationServicesStatusApi31> var3
      ) {
         if (var0 < 23) {
            return (LocationServicesStatus)var1.get();
         } else {
            return var0 < 31 ? (LocationServicesStatus)var2.get() : (LocationServicesStatus)var3.get();
         }
      }

      @Provides
      @Named("connect-permissions")
      static String[][] provideRecommendedConnectRuntimePermissionNames(@Named("device-sdk") int var0, @Named("target-sdk") int var1) {
         return Math.min(var0, var1) < 31 ? new String[0][] : new String[][]{{"android.permission.BLUETOOTH_CONNECT"}};
      }

      @Provides
      @Named("scan-permissions")
      static String[][] provideRecommendedScanRuntimePermissionNames(
         @Named("device-sdk") int var0, @Named("target-sdk") int var1, @Named("nearby-permission-never-for-location") boolean var2
      ) {
         var0 = Math.min(var0, var1);
         if (var0 < 23) {
            return new String[0][];
         } else if (var0 < 29) {
            return new String[][]{{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"}};
         } else if (var0 < 31) {
            return new String[][]{{"android.permission.ACCESS_FINE_LOCATION"}};
         } else if (var2) {
            return new String[][]{{"android.permission.BLUETOOTH_SCAN"}};
         } else {
            String[] var3 = new String[]{"android.permission.ACCESS_FINE_LOCATION"};
            return new String[][]{{"android.permission.BLUETOOTH_SCAN"}, var3};
         }
      }

      @Provides
      static ScanPreconditionsVerifier provideScanPreconditionVerifier(
         @Named("device-sdk") int var0, Provider<ScanPreconditionsVerifierApi18> var1, Provider<ScanPreconditionsVerifierApi24> var2
      ) {
         return var0 < 24 ? (ScanPreconditionsVerifier)var1.get() : (ScanPreconditionsVerifier)var2.get();
      }

      @Provides
      @ClientScope
      static ScanSetupBuilder provideScanSetupProvider(
         @Named("device-sdk") int var0,
         Provider<ScanSetupBuilderImplApi18> var1,
         Provider<ScanSetupBuilderImplApi21> var2,
         Provider<ScanSetupBuilderImplApi23> var3
      ) {
         if (var0 < 21) {
            return (ScanSetupBuilder)var1.get();
         } else {
            return var0 < 23 ? (ScanSetupBuilder)var2.get() : (ScanSetupBuilder)var3.get();
         }
      }

      @Provides
      @Named("target-sdk")
      static int provideTargetSdk(Context var0) {
         try {
            return var0.getPackageManager().getApplicationInfo(var0.getPackageName(), 0).targetSdkVersion;
         } finally {
            ;
         }
      }

      @Binds
      abstract BackgroundScanner bindBackgroundScanner(BackgroundScannerImpl var1);

      @Binds
      @ClientScope
      abstract ClientOperationQueue bindClientOperationQueue(ClientOperationQueueImpl var1);

      @Binds
      @ClientScope
      abstract RxBleClient bindRxBleClient(RxBleClientImpl var1);

      @Binds
      abstract Observable<RxBleAdapterStateObservable.BleAdapterState> bindStateObs(RxBleAdapterStateObservable var1);

      @Binds
      @Named("timeout")
      abstract Scheduler bindTimeoutScheduler(@Named("computation") Scheduler var1);

      @Binds
      abstract Function<RxBleInternalScanResult, ScanResult> provideScanResultMapper(InternalToExternalScanResultConverter var1);
   }

   public static class NamedBooleanObservables {
      public static final String LOCATION_SERVICES_OK = "location-ok-boolean-observable";

      private NamedBooleanObservables() {
      }
   }

   public static class NamedExecutors {
      public static final String BLUETOOTH_INTERACTION = "executor_bluetooth_interaction";
      public static final String CONNECTION_QUEUE = "executor_connection_queue";

      private NamedExecutors() {
      }
   }

   public static class NamedSchedulers {
      public static final String BLUETOOTH_CALLBACKS = "bluetooth_callbacks";
      public static final String BLUETOOTH_INTERACTION = "bluetooth_interaction";
      public static final String COMPUTATION = "computation";
      public static final String TIMEOUT = "timeout";

      private NamedSchedulers() {
      }
   }

   public static class PlatformConstants {
      public static final String BOOL_IS_ANDROID_WEAR = "android-wear";
      public static final String BOOL_IS_NEARBY_PERMISSION_NEVER_FOR_LOCATION = "nearby-permission-never-for-location";
      public static final String INT_DEVICE_SDK = "device-sdk";
      public static final String INT_TARGET_SDK = "target-sdk";
      public static final String STRING_ARRAY_CONNECT_PERMISSIONS = "connect-permissions";
      public static final String STRING_ARRAY_SCAN_PERMISSIONS = "scan-permissions";

      private PlatformConstants() {
      }
   }
}
