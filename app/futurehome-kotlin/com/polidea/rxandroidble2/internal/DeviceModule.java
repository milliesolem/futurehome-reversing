package com.polidea.rxandroidble2.internal;

import android.bluetooth.BluetoothDevice;
import bleshadow.dagger.Binds;
import bleshadow.dagger.Module;
import bleshadow.dagger.Provides;
import bleshadow.javax.inject.Named;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.RxBleDevice;
import com.polidea.rxandroidble2.internal.connection.ConnectionComponent;
import com.polidea.rxandroidble2.internal.connection.ConnectionStateChangeListener;
import com.polidea.rxandroidble2.internal.connection.Connector;
import com.polidea.rxandroidble2.internal.connection.ConnectorImpl;
import com.polidea.rxandroidble2.internal.operations.TimeoutConfiguration;
import com.polidea.rxandroidble2.internal.util.RxBleAdapterWrapper;
import io.reactivex.Scheduler;
import java.util.concurrent.TimeUnit;

@Module(
   subcomponents = {ConnectionComponent.class}
)
public abstract class DeviceModule {
   public static final String CONNECT_TIMEOUT = "connect-timeout";
   private static final int DEFAULT_CONNECT_TIMEOUT = 35;
   private static final int DEFAULT_DISCONNECT_TIMEOUT = 10;
   private static final int DEFAULT_OPERATION_TIMEOUT = 30;
   public static final String DISCONNECT_TIMEOUT = "disconnect-timeout";
   public static final String MAC_ADDRESS = "mac-address";
   public static final String OPERATION_TIMEOUT = "operation-timeout";

   @Provides
   static BluetoothDevice provideBluetoothDevice(@Named("mac-address") String var0, RxBleAdapterWrapper var1) {
      return var1.getRemoteDevice(var0);
   }

   @Provides
   @DeviceScope
   static ConnectionStateChangeListener provideConnectionStateChangeListener(BehaviorRelay<RxBleConnection.RxBleConnectionState> var0) {
      return new ConnectionStateChangeListener(var0) {
         final BehaviorRelay val$connectionStateBehaviorRelay;

         {
            this.val$connectionStateBehaviorRelay = var1;
         }

         @Override
         public void onConnectionStateChange(RxBleConnection.RxBleConnectionState var1) {
            this.val$connectionStateBehaviorRelay.accept(var1);
         }
      };
   }

   @Provides
   @DeviceScope
   static BehaviorRelay<RxBleConnection.RxBleConnectionState> provideConnectionStateRelay() {
      return BehaviorRelay.createDefault(RxBleConnection.RxBleConnectionState.DISCONNECTED);
   }

   @Provides
   @Named("connect-timeout")
   static TimeoutConfiguration providesConnectTimeoutConf(@Named("timeout") Scheduler var0) {
      return new TimeoutConfiguration(35L, TimeUnit.SECONDS, var0);
   }

   @Provides
   @Named("disconnect-timeout")
   static TimeoutConfiguration providesDisconnectTimeoutConf(@Named("timeout") Scheduler var0) {
      return new TimeoutConfiguration(10L, TimeUnit.SECONDS, var0);
   }

   @Binds
   abstract Connector bindConnector(ConnectorImpl var1);

   @Binds
   abstract RxBleDevice bindDevice(RxBleDeviceImpl var1);
}
