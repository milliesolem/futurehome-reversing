package com.polidea.rxandroidble2.internal.scan;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanResult;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.ClientScope;
import com.polidea.rxandroidble2.internal.RxBleLog;
import com.polidea.rxandroidble2.internal.util.ScanRecordParser;
import com.polidea.rxandroidble2.scan.IsConnectable;
import com.polidea.rxandroidble2.scan.ScanCallbackType;
import com.polidea.rxandroidble2.scan.ScanRecord;

@ClientScope
public class InternalScanResultCreator {
   private final IsConnectableChecker isConnectableChecker;
   private final ScanRecordParser scanRecordParser;

   @Inject
   public InternalScanResultCreator(ScanRecordParser var1, IsConnectableChecker var2) {
      this.scanRecordParser = var1;
      this.isConnectableChecker = var2;
   }

   private static ScanCallbackType toScanCallbackType(int var0) {
      if (var0 != 1) {
         if (var0 != 2) {
            if (var0 != 4) {
               RxBleLog.w("Unknown callback type %d -> check android.bluetooth.le.ScanSettings", var0);
               return ScanCallbackType.CALLBACK_TYPE_UNKNOWN;
            } else {
               return ScanCallbackType.CALLBACK_TYPE_MATCH_LOST;
            }
         } else {
            return ScanCallbackType.CALLBACK_TYPE_FIRST_MATCH;
         }
      } else {
         return ScanCallbackType.CALLBACK_TYPE_ALL_MATCHES;
      }
   }

   public RxBleInternalScanResult create(int var1, ScanResult var2) {
      ScanRecordImplNativeWrapper var3 = new ScanRecordImplNativeWrapper(var2.getScanRecord(), this.scanRecordParser);
      return new RxBleInternalScanResult(
         var2.getDevice(), var2.getRssi(), var2.getTimestampNanos(), var3, toScanCallbackType(var1), this.isConnectableChecker.check(var2)
      );
   }

   public RxBleInternalScanResult create(BluetoothDevice var1, int var2, byte[] var3) {
      ScanRecord var4 = this.scanRecordParser.parseFromBytes(var3);
      return new RxBleInternalScanResult(var1, var2, System.nanoTime(), var4, ScanCallbackType.CALLBACK_TYPE_UNSPECIFIED, IsConnectable.LEGACY_UNKNOWN);
   }

   public RxBleInternalScanResult create(ScanResult var1) {
      ScanRecordImplNativeWrapper var2 = new ScanRecordImplNativeWrapper(var1.getScanRecord(), this.scanRecordParser);
      return new RxBleInternalScanResult(
         var1.getDevice(), var1.getRssi(), var1.getTimestampNanos(), var2, ScanCallbackType.CALLBACK_TYPE_BATCH, this.isConnectableChecker.check(var1)
      );
   }
}
