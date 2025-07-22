package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGatt;
import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble2.ConnectionSetup;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.internal.serialization.ClientOperationQueue;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import java.util.Objects;

public class ConnectorImpl implements Connector {
   final Scheduler callbacksScheduler;
   private final ClientOperationQueue clientOperationQueue;
   final ConnectionComponent.Builder connectionComponentBuilder;

   @Inject
   public ConnectorImpl(ClientOperationQueue var1, ConnectionComponent.Builder var2, @Named("bluetooth_callbacks") Scheduler var3) {
      this.clientOperationQueue = var1;
      this.connectionComponentBuilder = var2;
      this.callbacksScheduler = var3;
   }

   static Observable<RxBleConnection> observeDisconnections(ConnectionComponent var0) {
      return var0.gattCallback().observeDisconnect();
   }

   static Observable<RxBleConnection> obtainRxBleConnection(ConnectionComponent var0) {
      Objects.requireNonNull(var0);
      return Observable.fromCallable(new ConnectorImpl$$ExternalSyntheticLambda3(var0));
   }

   Observable<BluetoothGatt> enqueueConnectOperation(ConnectionComponent var1) {
      return this.clientOperationQueue.queue(var1.connectOperation());
   }

   @Override
   public Observable<RxBleConnection> prepareConnection(ConnectionSetup var1) {
      return Observable.defer(new ConnectorImpl$$ExternalSyntheticLambda0(this, var1));
   }
}
