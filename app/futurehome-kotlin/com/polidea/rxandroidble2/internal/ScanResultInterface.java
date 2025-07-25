package com.polidea.rxandroidble2.internal;

import com.polidea.rxandroidble2.scan.ScanCallbackType;
import com.polidea.rxandroidble2.scan.ScanRecord;

public interface ScanResultInterface {
   String getAddress();

   String getDeviceName();

   int getRssi();

   ScanCallbackType getScanCallbackType();

   ScanRecord getScanRecord();

   long getTimestampNanos();
}
