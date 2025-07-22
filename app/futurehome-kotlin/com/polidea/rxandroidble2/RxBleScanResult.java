package com.polidea.rxandroidble2;

import com.polidea.rxandroidble2.internal.logger.LoggerUtil;

public class RxBleScanResult {
   private final RxBleDevice bleDevice;
   private final int rssi;
   private final byte[] scanRecord;

   public RxBleScanResult(RxBleDevice var1, int var2, byte[] var3) {
      this.bleDevice = var1;
      this.rssi = var2;
      this.scanRecord = var3;
   }

   public RxBleDevice getBleDevice() {
      return this.bleDevice;
   }

   public int getRssi() {
      return this.rssi;
   }

   public byte[] getScanRecord() {
      return this.scanRecord;
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("RxBleScanResult{bleDevice=");
      var1.append(this.bleDevice);
      var1.append(", rssi=");
      var1.append(this.rssi);
      var1.append(", scanRecord=");
      var1.append(LoggerUtil.bytesToHex(this.scanRecord));
      var1.append('}');
      return var1.toString();
   }
}
