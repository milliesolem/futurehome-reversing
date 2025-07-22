package com.polidea.rxandroidble2.internal.connection;

import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.internal.operations.DisconnectOperation;
import com.polidea.rxandroidble2.internal.serialization.ClientOperationQueue;
import io.reactivex.internal.functions.Functions;

@ConnectionScope
class DisconnectAction implements ConnectionSubscriptionWatcher {
   private final ClientOperationQueue clientOperationQueue;
   private final DisconnectOperation operationDisconnect;

   @Inject
   DisconnectAction(ClientOperationQueue var1, DisconnectOperation var2) {
      this.clientOperationQueue = var1;
      this.operationDisconnect = var2;
   }

   @Override
   public void onConnectionSubscribed() {
   }

   @Override
   public void onConnectionUnsubscribed() {
      this.clientOperationQueue.queue(this.operationDisconnect).subscribe(Functions.emptyConsumer(), Functions.emptyConsumer());
   }
}
