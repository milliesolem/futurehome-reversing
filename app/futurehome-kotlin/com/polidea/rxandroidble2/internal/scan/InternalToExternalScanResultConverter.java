package com.polidea.rxandroidble2.internal.scan;

import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.internal.RxBleDeviceProvider;
import com.polidea.rxandroidble2.scan.ScanResult;
import io.reactivex.functions.Function;

public class InternalToExternalScanResultConverter implements Function<RxBleInternalScanResult, ScanResult> {
   private final RxBleDeviceProvider deviceProvider;

   @Inject
   public InternalToExternalScanResultConverter(RxBleDeviceProvider var1) {
      this.deviceProvider = var1;
   }

   public ScanResult apply(RxBleInternalScanResult var1) {
      return new ScanResult(
         this.deviceProvider.getBleDevice(var1.getBluetoothDevice().getAddress()),
         var1.getRssi(),
         var1.getTimestampNanos(),
         var1.getScanCallbackType(),
         var1.getScanRecord(),
         var1.isConnectable()
      );
   }
}
