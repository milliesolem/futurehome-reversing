package com.polidea.rxandroidble2.internal.scan;

import com.polidea.rxandroidble2.internal.operations.Operation;
import io.reactivex.ObservableTransformer;

public class ScanSetup {
   public final Operation<RxBleInternalScanResult> scanOperation;
   public final ObservableTransformer<RxBleInternalScanResult, RxBleInternalScanResult> scanOperationBehaviourEmulatorTransformer;

   public ScanSetup(Operation<RxBleInternalScanResult> var1, ObservableTransformer<RxBleInternalScanResult, RxBleInternalScanResult> var2) {
      this.scanOperation = var1;
      this.scanOperationBehaviourEmulatorTransformer = var2;
   }
}
