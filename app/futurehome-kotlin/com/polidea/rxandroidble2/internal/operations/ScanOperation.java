package com.polidea.rxandroidble2.internal.operations;

import android.os.DeadObjectException;
import com.polidea.rxandroidble2.exceptions.BleException;
import com.polidea.rxandroidble2.exceptions.BleScanException;
import com.polidea.rxandroidble2.internal.QueueOperation;
import com.polidea.rxandroidble2.internal.RxBleLog;
import com.polidea.rxandroidble2.internal.serialization.QueueReleaseInterface;
import com.polidea.rxandroidble2.internal.util.RxBleAdapterWrapper;
import io.reactivex.ObservableEmitter;
import io.reactivex.functions.Cancellable;

public abstract class ScanOperation<SCAN_RESULT_TYPE, SCAN_CALLBACK_TYPE> extends QueueOperation<SCAN_RESULT_TYPE> {
   final RxBleAdapterWrapper rxBleAdapterWrapper;

   ScanOperation(RxBleAdapterWrapper var1) {
      this.rxBleAdapterWrapper = var1;
   }

   abstract SCAN_CALLBACK_TYPE createScanCallback(ObservableEmitter<SCAN_RESULT_TYPE> var1);

   // $VF: Could not inline inconsistent finally blocks
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   protected final void protectedRun(ObservableEmitter<SCAN_RESULT_TYPE> var1, QueueReleaseInterface var2) {
      Object var3 = this.createScanCallback(var1);

      try {
         Cancellable var13 = new Cancellable(this, var3) {
            final ScanOperation this$0;
            final Object val$scanCallback;

            {
               this.this$0 = var1;
               this.val$scanCallback = var2x;
            }

            @Override
            public void cancel() {
               RxBleLog.i("Scan operation is requested to stop.");
               ScanOperation var1x = this.this$0;
               var1x.stopScan(var1x.rxBleAdapterWrapper, this.val$scanCallback);
            }
         };
         var1.setCancellable(var13);
         RxBleLog.i("Scan operation is requested to start.");
         if (!this.startScan(this.rxBleAdapterWrapper, (SCAN_CALLBACK_TYPE)var3)) {
            var3 = new BleScanException(0);
            var1.tryOnError((Throwable)var3);
         }
      } catch (Throwable var10) {
         Throwable var4 = var10;

         try {
            RxBleLog.w(var4, "Error while calling the start scan function");
            var3 = new BleScanException(0, var4);
            var1.tryOnError((Throwable)var3);
            return;
         } finally {
            var2.release();
         }
      }
   }

   @Override
   protected BleException provideException(DeadObjectException var1) {
      return new BleScanException(1, var1);
   }

   abstract boolean startScan(RxBleAdapterWrapper var1, SCAN_CALLBACK_TYPE var2);

   abstract void stopScan(RxBleAdapterWrapper var1, SCAN_CALLBACK_TYPE var2);
}
