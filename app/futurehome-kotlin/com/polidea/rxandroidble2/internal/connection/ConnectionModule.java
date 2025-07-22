package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGatt;
import bleshadow.dagger.Binds;
import bleshadow.dagger.Module;
import bleshadow.dagger.Provides;
import bleshadow.dagger.multibindings.IntoSet;
import bleshadow.javax.inject.Named;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.Timeout;
import com.polidea.rxandroidble2.internal.operations.OperationsProvider;
import com.polidea.rxandroidble2.internal.operations.OperationsProviderImpl;
import com.polidea.rxandroidble2.internal.operations.TimeoutConfiguration;
import com.polidea.rxandroidble2.internal.serialization.ConnectionOperationQueue;
import com.polidea.rxandroidble2.internal.serialization.ConnectionOperationQueueImpl;
import com.polidea.rxandroidble2.internal.util.CharacteristicPropertiesParser;
import io.reactivex.Scheduler;

@Module
public abstract class ConnectionModule {
   public static final String OPERATION_TIMEOUT = "operation-timeout";

   @Provides
   @Named("GATT_WRITE_MTU_OVERHEAD")
   static int gattWriteMtuOverhead() {
      return 3;
   }

   @Provides
   @Named("GATT_MTU_MINIMUM")
   static int minimumMtu() {
      return 23;
   }

   @Provides
   static BluetoothGatt provideBluetoothGatt(BluetoothGattProvider var0) {
      return var0.getBluetoothGatt();
   }

   @Provides
   static CharacteristicPropertiesParser provideCharacteristicPropertiesParser() {
      return new CharacteristicPropertiesParser(1, 2, 4, 8, 16, 32, 64);
   }

   @Provides
   static IllegalOperationHandler provideIllegalOperationHandler(
      @Named("suppressOperationChecks") boolean var0, Provider<LoggingIllegalOperationHandler> var1, Provider<ThrowingIllegalOperationHandler> var2
   ) {
      return var0 ? (IllegalOperationHandler)var1.get() : (IllegalOperationHandler)var2.get();
   }

   @Provides
   @Named("operation-timeout")
   static TimeoutConfiguration providesOperationTimeoutConf(@Named("timeout") Scheduler var0, Timeout var1) {
      return new TimeoutConfiguration(var1.timeout, var1.timeUnit, var0);
   }

   @Binds
   abstract ConnectionOperationQueue bindConnectionOperationQueue(ConnectionOperationQueueImpl var1);

   @Binds
   @IntoSet
   abstract ConnectionSubscriptionWatcher bindConnectionQueueSubscriptionWatcher(ConnectionOperationQueueImpl var1);

   @Binds
   abstract MtuProvider bindCurrentMtuProvider(MtuWatcher var1);

   @Binds
   @IntoSet
   abstract ConnectionSubscriptionWatcher bindDisconnectActionSubscriptionWatcher(DisconnectAction var1);

   @Binds
   abstract DisconnectionRouterInput bindDisconnectionRouterInput(DisconnectionRouter var1);

   @Binds
   abstract DisconnectionRouterOutput bindDisconnectionRouterOutput(DisconnectionRouter var1);

   @Binds
   abstract RxBleConnection.LongWriteOperationBuilder bindLongWriteOperationBuilder(LongWriteOperationBuilderImpl var1);

   @Binds
   @IntoSet
   abstract ConnectionSubscriptionWatcher bindMtuWatcherSubscriptionWatcher(MtuWatcher var1);

   @Binds
   abstract OperationsProvider bindOperationsProvider(OperationsProviderImpl var1);

   @Binds
   abstract RxBleConnection bindRxBleConnection(RxBleConnectionImpl var1);
}
