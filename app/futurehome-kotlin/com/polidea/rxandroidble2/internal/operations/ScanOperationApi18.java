package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import com.polidea.rxandroidble2.internal.RxBleLog;
import com.polidea.rxandroidble2.internal.logger.LoggerUtil;
import com.polidea.rxandroidble2.internal.scan.EmulatedScanFilterMatcher;
import com.polidea.rxandroidble2.internal.scan.InternalScanResultCreator;
import com.polidea.rxandroidble2.internal.scan.RxBleInternalScanResult;
import com.polidea.rxandroidble2.internal.util.RxBleAdapterWrapper;
import io.reactivex.ObservableEmitter;

public class ScanOperationApi18 extends ScanOperation<RxBleInternalScanResult, LeScanCallback> {
   final EmulatedScanFilterMatcher scanFilterMatcher;
   final InternalScanResultCreator scanResultCreator;

   public ScanOperationApi18(RxBleAdapterWrapper var1, InternalScanResultCreator var2, EmulatedScanFilterMatcher var3) {
      super(var1);
      this.scanResultCreator = var2;
      this.scanFilterMatcher = var3;
   }

   LeScanCallback createScanCallback(ObservableEmitter<RxBleInternalScanResult> var1) {
      return new LeScanCallback(this, var1) {
         final ScanOperationApi18 this$0;
         final ObservableEmitter val$emitter;

         {
            this.this$0 = var1;
            this.val$emitter = var2;
         }

         public void onLeScan(BluetoothDevice var1, int var2, byte[] var3) {
            if (!this.this$0.scanFilterMatcher.isEmpty() && RxBleLog.isAtLeast(3) && RxBleLog.getShouldLogScannedPeripherals()) {
               RxBleLog.d("%s, name=%s, rssi=%d, data=%s", LoggerUtil.commonMacMessage(var1.getAddress()), var1.getName(), var2, LoggerUtil.bytesToHex(var3));
            }

            RxBleInternalScanResult var4 = this.this$0.scanResultCreator.create(var1, var2, var3);
            if (this.this$0.scanFilterMatcher.matches(var4)) {
               this.val$emitter.onNext(var4);
            }
         }
      };
   }

   boolean startScan(RxBleAdapterWrapper var1, LeScanCallback var2) {
      if (this.scanFilterMatcher.isEmpty()) {
         RxBleLog.d("No library side filtering —> debug logs of scanned devices disabled");
      }

      return var1.startLegacyLeScan(var2);
   }

   void stopScan(RxBleAdapterWrapper var1, LeScanCallback var2) {
      var1.stopLegacyLeScan(var2);
   }

   @Override
   public String toString() {
      StringBuilder var2 = new StringBuilder("ScanOperationApi18{");
      String var1;
      if (this.scanFilterMatcher.isEmpty()) {
         var1 = "";
      } else {
         StringBuilder var3 = new StringBuilder("ANY_MUST_MATCH -> ");
         var3.append(this.scanFilterMatcher);
         var1 = var3.toString();
      }

      var2.append(var1);
      var2.append('}');
      return var2.toString();
   }
}
