package com.polidea.rxandroidble2.internal.connection;

import com.polidea.rxandroidble2.exceptions.BleDisconnectedException;
import com.polidea.rxandroidble2.exceptions.BleGattException;

interface DisconnectionRouterInput {
   void onDisconnectedException(BleDisconnectedException var1);

   void onGattConnectionStateException(BleGattException var1);
}
