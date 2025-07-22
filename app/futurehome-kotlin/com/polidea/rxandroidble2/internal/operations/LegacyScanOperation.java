package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import com.polidea.rxandroidble2.internal.RxBleLog;
import com.polidea.rxandroidble2.internal.logger.LoggerUtil;
import com.polidea.rxandroidble2.internal.scan.RxBleInternalScanResultLegacy;
import com.polidea.rxandroidble2.internal.util.RxBleAdapterWrapper;
import com.polidea.rxandroidble2.internal.util.ScanRecordParser;
import io.reactivex.ObservableEmitter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class LegacyScanOperation extends ScanOperation<RxBleInternalScanResultLegacy, LeScanCallback> {
   final Set<UUID> filterUuids;
   final ScanRecordParser scanRecordParser;

   public LegacyScanOperation(UUID[] var1, RxBleAdapterWrapper var2, ScanRecordParser var3) {
      super(var2);
      this.scanRecordParser = var3;
      if (var1 != null && var1.length > 0) {
         HashSet var4 = new HashSet(var1.length);
         this.filterUuids = var4;
         Collections.addAll(var4, var1);
      } else {
         this.filterUuids = null;
      }
   }

   LeScanCallback createScanCallback(ObservableEmitter<RxBleInternalScanResultLegacy> var1) {
      return new LeScanCallback(this, var1) {
         final LegacyScanOperation this$0;
         final ObservableEmitter val$emitter;

         {
            this.this$0 = var1;
            this.val$emitter = var2;
         }

         public void onLeScan(BluetoothDevice var1, int var2, byte[] var3) {
            if (this.this$0.filterUuids != null && RxBleLog.isAtLeast(3)) {
               RxBleLog.d("%s, name=%s, rssi=%d, data=%s", LoggerUtil.commonMacMessage(var1.getAddress()), var1.getName(), var2, LoggerUtil.bytesToHex(var3));
            }

            if (this.this$0.filterUuids == null || this.this$0.scanRecordParser.extractUUIDs(var3).containsAll(this.this$0.filterUuids)) {
               this.val$emitter.onNext(new RxBleInternalScanResultLegacy(var1, var2, var3));
            }
         }
      };
   }

   boolean startScan(RxBleAdapterWrapper var1, LeScanCallback var2) {
      if (this.filterUuids == null) {
         RxBleLog.d("No library side filtering —> debug logs of scanned devices disabled");
      }

      return var1.startLegacyLeScan(var2);
   }

   void stopScan(RxBleAdapterWrapper var1, LeScanCallback var2) {
      var1.stopLegacyLeScan(var2);
   }

   @Override
   public String toString() {
      StringBuilder var2 = new StringBuilder("LegacyScanOperation{");
      String var1;
      if (this.filterUuids == null) {
         var1 = "";
      } else {
         StringBuilder var3 = new StringBuilder("ALL_MUST_MATCH -> uuids=");
         var3.append(LoggerUtil.getUuidSetToLog(this.filterUuids));
         var1 = var3.toString();
      }

      var2.append(var1);
      var2.append('}');
      return var2.toString();
   }
}
