package com.polidea.rxandroidble2.internal.util;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import bleshadow.javax.inject.Inject;
import java.util.List;

public class BluetoothManagerWrapper {
   private final BluetoothManager bluetoothManager;

   @Inject
   public BluetoothManagerWrapper(BluetoothManager var1) {
      this.bluetoothManager = var1;
   }

   public List<BluetoothDevice> getConnectedPeripherals() {
      return this.bluetoothManager.getConnectedDevices(8);
   }
}
