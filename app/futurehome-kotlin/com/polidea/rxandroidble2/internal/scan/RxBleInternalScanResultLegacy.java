package com.polidea.rxandroidble2.internal.scan;

import android.bluetooth.BluetoothDevice;

public class RxBleInternalScanResultLegacy {
   private final BluetoothDevice bluetoothDevice;
   private final int rssi;
   private final byte[] scanRecord;

   public RxBleInternalScanResultLegacy(BluetoothDevice var1, int var2, byte[] var3) {
      this.bluetoothDevice = var1;
      this.rssi = var2;
      this.scanRecord = var3;
   }

   public BluetoothDevice getBluetoothDevice() {
      return this.bluetoothDevice;
   }

   public int getRssi() {
      return this.rssi;
   }

   public byte[] getScanRecord() {
      return this.scanRecord;
   }
}
