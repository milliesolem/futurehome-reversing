package com.polidea.rxandroidble2.scan;

import android.app.PendingIntent;
import android.content.Intent;
import java.util.List;

public interface BackgroundScanner {
   List<ScanResult> onScanResultReceived(Intent var1);

   void scanBleDeviceInBackground(PendingIntent var1, ScanSettings var2, ScanFilter... var3);

   void stopBackgroundBleScan(PendingIntent var1);
}
