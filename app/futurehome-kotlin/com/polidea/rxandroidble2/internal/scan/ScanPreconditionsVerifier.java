package com.polidea.rxandroidble2.internal.scan;

import com.polidea.rxandroidble2.exceptions.BleScanException;

public interface ScanPreconditionsVerifier {
   void verify(boolean var1) throws BleScanException;
}
