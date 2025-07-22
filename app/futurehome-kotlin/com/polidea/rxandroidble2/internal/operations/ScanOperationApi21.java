package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import com.polidea.rxandroidble2.exceptions.BleScanException;
import com.polidea.rxandroidble2.internal.RxBleLog;
import com.polidea.rxandroidble2.internal.logger.LoggerUtil;
import com.polidea.rxandroidble2.internal.scan.AndroidScanObjectsConverter;
import com.polidea.rxandroidble2.internal.scan.EmulatedScanFilterMatcher;
import com.polidea.rxandroidble2.internal.scan.InternalScanResultCreator;
import com.polidea.rxandroidble2.internal.scan.RxBleInternalScanResult;
import com.polidea.rxandroidble2.internal.util.RxBleAdapterWrapper;
import com.polidea.rxandroidble2.scan.ScanFilter;
import com.polidea.rxandroidble2.scan.ScanSettings;
import io.reactivex.ObservableEmitter;
import java.util.Arrays;
import java.util.List;

public class ScanOperationApi21 extends ScanOperation<RxBleInternalScanResult, ScanCallback> {
   private final AndroidScanObjectsConverter androidScanObjectsConverter;
   final EmulatedScanFilterMatcher emulatedScanFilterMatcher;
   final InternalScanResultCreator internalScanResultCreator;
   private ObservableEmitter<RxBleInternalScanResult> scanEmitter;
   private final ScanFilter[] scanFilters;
   private final ScanSettings scanSettings;

   public ScanOperationApi21(
      RxBleAdapterWrapper var1,
      InternalScanResultCreator var2,
      AndroidScanObjectsConverter var3,
      ScanSettings var4,
      EmulatedScanFilterMatcher var5,
      ScanFilter[] var6
   ) {
      super(var1);
      this.internalScanResultCreator = var2;
      this.scanSettings = var4;
      this.emulatedScanFilterMatcher = var5;
      this.scanFilters = var6;
      this.androidScanObjectsConverter = var3;
      this.scanEmitter = null;
   }

   static int errorCodeToBleErrorCode(int var0) {
      if (var0 != 1) {
         if (var0 != 2) {
            if (var0 != 3) {
               if (var0 != 4) {
                  if (var0 != 5) {
                     RxBleLog.w("Encountered unknown scanning error code: %d -> check android.bluetooth.le.ScanCallback");
                     return Integer.MAX_VALUE;
                  } else {
                     return 9;
                  }
               } else {
                  return 8;
               }
            } else {
               return 7;
            }
         } else {
            return 6;
         }
      } else {
         return 5;
      }
   }

   ScanCallback createScanCallback(ObservableEmitter<RxBleInternalScanResult> var1) {
      this.scanEmitter = var1;
      return new ScanCallback(this) {
         final ScanOperationApi21 this$0;

         {
            this.this$0 = var1;
         }

         public void onBatchScanResults(List<ScanResult> var1) {
            for (ScanResult var2 : var1) {
               RxBleInternalScanResult var5 = this.this$0.internalScanResultCreator.create(var2);
               if (this.this$0.emulatedScanFilterMatcher.matches(var5)) {
                  ObservableEmitter var3 = this.this$0.scanEmitter;
                  if (var3 != null) {
                     var3.onNext(var5);
                  }
               }
            }
         }

         public void onScanFailed(int var1) {
            ObservableEmitter var2 = this.this$0.scanEmitter;
            if (var2 != null) {
               var2.tryOnError(new BleScanException(ScanOperationApi21.errorCodeToBleErrorCode(var1)));
            }
         }

         public void onScanResult(int var1, ScanResult var2) {
            if (!this.this$0.emulatedScanFilterMatcher.isEmpty() && RxBleLog.isAtLeast(3) && RxBleLog.getShouldLogScannedPeripherals()) {
               ScanRecord var4 = var2.getScanRecord();
               String var5 = LoggerUtil.commonMacMessage(var2.getDevice().getAddress());
               String var6 = var2.getDevice().getName();
               int var3 = var2.getRssi();
               byte[] var8;
               if (var4 != null) {
                  var8 = var4.getBytes();
               } else {
                  var8 = null;
               }

               RxBleLog.d("%s, name=%s, rssi=%d, data=%s", var5, var6, var3, LoggerUtil.bytesToHex(var8));
            }

            RxBleInternalScanResult var7 = this.this$0.internalScanResultCreator.create(var1, var2);
            if (this.this$0.emulatedScanFilterMatcher.matches(var7)) {
               ObservableEmitter var9 = this.this$0.scanEmitter;
               if (var9 != null) {
                  var9.onNext(var7);
               }
            }
         }
      };
   }

   boolean startScan(RxBleAdapterWrapper var1, ScanCallback var2) {
      if (this.emulatedScanFilterMatcher.isEmpty()) {
         RxBleLog.d("No library side filtering —> debug logs of scanned devices disabled");
      }

      var1.startLeScan(
         this.androidScanObjectsConverter.toNativeFilters(this.scanFilters), this.androidScanObjectsConverter.toNativeSettings(this.scanSettings), var2
      );
      return true;
   }

   void stopScan(RxBleAdapterWrapper var1, ScanCallback var2) {
      var1.stopLeScan(var2);
      ObservableEmitter var3 = this.scanEmitter;
      if (var3 != null) {
         var3.onComplete();
         this.scanEmitter = null;
      }
   }

   @Override
   public String toString() {
      ScanFilter[] var3 = this.scanFilters;
      boolean var1;
      if (var3 != null && var3.length != 0) {
         var1 = false;
      } else {
         var1 = true;
      }

      boolean var2 = this.emulatedScanFilterMatcher.isEmpty();
      StringBuilder var5 = new StringBuilder("ScanOperationApi21{");
      String var4 = "";
      String var6;
      if (var1) {
         var6 = "";
      } else {
         StringBuilder var7 = new StringBuilder("ANY_MUST_MATCH -> nativeFilters=");
         var7.append(Arrays.toString((Object[])this.scanFilters));
         var6 = var7.toString();
      }

      var5.append(var6);
      String var8;
      if (!var1 && !var2) {
         var8 = " and then ";
      } else {
         var8 = "";
      }

      var5.append(var8);
      String var9;
      if (var2) {
         var9 = var4;
      } else {
         StringBuilder var10 = new StringBuilder("ANY_MUST_MATCH -> ");
         var10.append(this.emulatedScanFilterMatcher);
         var9 = var10.toString();
      }

      var5.append(var9);
      var5.append('}');
      return var5.toString();
   }
}
