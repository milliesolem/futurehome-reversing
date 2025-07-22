package com.polidea.rxandroidble2.scan;

import com.polidea.rxandroidble2.RxBleDevice;
import com.polidea.rxandroidble2.internal.logger.LoggerUtil;

public class ScanResult {
   private final RxBleDevice bleDevice;
   private final ScanCallbackType callbackType;
   private final IsConnectable isConnectable;
   private final int rssi;
   private final ScanRecord scanRecord;
   private final long timestampNanos;

   public ScanResult(RxBleDevice var1, int var2, long var3, ScanCallbackType var5, ScanRecord var6, IsConnectable var7) {
      this.bleDevice = var1;
      this.rssi = var2;
      this.timestampNanos = var3;
      this.callbackType = var5;
      this.scanRecord = var6;
      this.isConnectable = var7;
   }

   public RxBleDevice getBleDevice() {
      return this.bleDevice;
   }

   public ScanCallbackType getCallbackType() {
      return this.callbackType;
   }

   public int getRssi() {
      return this.rssi;
   }

   public ScanRecord getScanRecord() {
      return this.scanRecord;
   }

   public long getTimestampNanos() {
      return this.timestampNanos;
   }

   public IsConnectable isConnectable() {
      return this.isConnectable;
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("ScanResult{bleDevice=");
      var1.append(this.bleDevice);
      var1.append(", rssi=");
      var1.append(this.rssi);
      var1.append(", timestampNanos=");
      var1.append(this.timestampNanos);
      var1.append(", callbackType=");
      var1.append(this.callbackType);
      var1.append(", scanRecord=");
      var1.append(LoggerUtil.bytesToHex(this.scanRecord.getBytes()));
      var1.append(", isConnectable=");
      var1.append(this.isConnectable);
      var1.append('}');
      return var1.toString();
   }
}
