package com.polidea.rxandroidble2.internal.scan;

import android.bluetooth.le.ScanResult;
import androidx.webkit.internal.ApiHelperForN..ExternalSyntheticApiModelOutline4;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.scan.IsConnectable;

public class IsConnectableCheckerApi26 implements IsConnectableChecker {
   @Override
   public IsConnectable check(ScanResult var1) {
      IsConnectable var2;
      if (ExternalSyntheticApiModelOutline4.m(var1)) {
         var2 = IsConnectable.CONNECTABLE;
      } else {
         var2 = IsConnectable.NOT_CONNECTABLE;
      }

      return var2;
   }
}
