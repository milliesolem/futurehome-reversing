package com.polidea.rxandroidble2;

import com.polidea.rxandroidble2.internal.scan.RxBleInternalScanResultLegacy;
import io.reactivex.functions.Function;

// $VF: synthetic class
public final class RxBleClientImpl$$ExternalSyntheticLambda1 implements Function {
   public final RxBleClientImpl f$0;

   @Override
   public final Object apply(Object var1) {
      return this.f$0.convertToPublicScanResult((RxBleInternalScanResultLegacy)var1);
   }
}
