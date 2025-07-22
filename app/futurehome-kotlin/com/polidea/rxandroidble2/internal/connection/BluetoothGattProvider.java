package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGatt;
import androidx.lifecycle.LifecycleKt..ExternalSyntheticBackportWithForwarding0;
import bleshadow.javax.inject.Inject;
import java.util.concurrent.atomic.AtomicReference;

@ConnectionScope
public class BluetoothGattProvider {
   private final AtomicReference<BluetoothGatt> reference = new AtomicReference<>();

   @Inject
   BluetoothGattProvider() {
   }

   public BluetoothGatt getBluetoothGatt() {
      return this.reference.get();
   }

   public void updateBluetoothGatt(BluetoothGatt var1) {
      ExternalSyntheticBackportWithForwarding0.m(this.reference, null, var1);
   }
}
