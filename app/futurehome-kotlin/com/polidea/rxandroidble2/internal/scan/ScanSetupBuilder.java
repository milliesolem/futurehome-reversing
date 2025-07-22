package com.polidea.rxandroidble2.internal.scan;

import com.polidea.rxandroidble2.scan.ScanFilter;
import com.polidea.rxandroidble2.scan.ScanSettings;

public interface ScanSetupBuilder {
   ScanSetup build(ScanSettings var1, ScanFilter... var2);
}
