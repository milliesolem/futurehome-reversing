package com.polidea.rxandroidble2.internal.scan;

import android.bluetooth.le.ScanResult;
import com.polidea.rxandroidble2.scan.IsConnectable;

public interface IsConnectableChecker {
   IsConnectable check(ScanResult var1);
}
