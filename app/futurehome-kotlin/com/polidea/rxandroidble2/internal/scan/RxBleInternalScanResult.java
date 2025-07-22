package com.polidea.rxandroidble2.internal.scan;

import android.bluetooth.BluetoothDevice;
import com.polidea.rxandroidble2.internal.ScanResultInterface;
import com.polidea.rxandroidble2.scan.IsConnectable;
import com.polidea.rxandroidble2.scan.ScanCallbackType;
import com.polidea.rxandroidble2.scan.ScanRecord;

public class RxBleInternalScanResult implements ScanResultInterface {
   private final BluetoothDevice bluetoothDevice;
   private final IsConnectable isConnectable;
   private final int rssi;
   private final ScanCallbackType scanCallbackType;
   private final ScanRecord scanRecord;
   private final long timestampNanos;

   public RxBleInternalScanResult(BluetoothDevice var1, int var2, long var3, ScanRecord var5, ScanCallbackType var6, IsConnectable var7) {
      this.bluetoothDevice = var1;
      this.rssi = var2;
      this.timestampNanos = var3;
      this.scanRecord = var5;
      this.scanCallbackType = var6;
      this.isConnectable = var7;
   }

   @Override
   public String getAddress() {
      return this.bluetoothDevice.getAddress();
   }

   public BluetoothDevice getBluetoothDevice() {
      return this.bluetoothDevice;
   }

   @Override
   public String getDeviceName() {
      BluetoothDevice var1 = this.getBluetoothDevice();
      String var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = var1.getName();
      }

      return var2;
   }

   @Override
   public int getRssi() {
      return this.rssi;
   }

   @Override
   public ScanCallbackType getScanCallbackType() {
      return this.scanCallbackType;
   }

   @Override
   public ScanRecord getScanRecord() {
      return this.scanRecord;
   }

   @Override
   public long getTimestampNanos() {
      return this.timestampNanos;
   }

   public IsConnectable isConnectable() {
      return this.isConnectable;
   }
}
