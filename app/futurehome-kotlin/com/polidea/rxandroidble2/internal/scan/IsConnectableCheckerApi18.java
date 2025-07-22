package com.polidea.rxandroidble2.internal.scan;

import android.bluetooth.le.ScanResult;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.scan.IsConnectable;

public class IsConnectableCheckerApi18 implements IsConnectableChecker {
   @Override
   public IsConnectable check(ScanResult var1) {
      return IsConnectable.LEGACY_UNKNOWN;
   }
}
