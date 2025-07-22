package com.polidea.rxandroidble2.internal.scan;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build.VERSION;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.exceptions.BleScanException;
import com.polidea.rxandroidble2.internal.RxBleLog;
import com.polidea.rxandroidble2.internal.util.RxBleAdapterWrapper;
import com.polidea.rxandroidble2.scan.BackgroundScanner;
import com.polidea.rxandroidble2.scan.ScanFilter;
import com.polidea.rxandroidble2.scan.ScanResult;
import com.polidea.rxandroidble2.scan.ScanSettings;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BackgroundScannerImpl implements BackgroundScanner {
   private static final int NO_ERROR = 0;
   private final InternalScanResultCreator internalScanResultCreator;
   private final InternalToExternalScanResultConverter internalToExternalScanResultConverter;
   private final RxBleAdapterWrapper rxBleAdapterWrapper;
   private final AndroidScanObjectsConverter scanObjectsConverter;

   @Inject
   BackgroundScannerImpl(RxBleAdapterWrapper var1, AndroidScanObjectsConverter var2, InternalScanResultCreator var3, InternalToExternalScanResultConverter var4) {
      this.rxBleAdapterWrapper = var1;
      this.scanObjectsConverter = var2;
      this.internalScanResultCreator = var3;
      this.internalToExternalScanResultConverter = var4;
   }

   private ScanResult convertScanResultToRxAndroidBLEModel(int var1, android.bluetooth.le.ScanResult var2) {
      return this.internalToExternalScanResultConverter.apply(this.internalScanResultCreator.create(var1, var2));
   }

   private static List<android.bluetooth.le.ScanResult> extractScanResults(Intent var0) {
      return (List<android.bluetooth.le.ScanResult>)var0.getSerializableExtra("android.bluetooth.le.extra.LIST_SCAN_RESULT");
   }

   @Override
   public List<ScanResult> onScanResultReceived(Intent var1) {
      int var3 = var1.getIntExtra("android.bluetooth.le.extra.CALLBACK_TYPE", -1);
      int var2 = var1.getIntExtra("android.bluetooth.le.extra.ERROR_CODE", 0);
      List var4 = extractScanResults(var1);
      ArrayList var5 = new ArrayList();
      if (var2 != 0) {
         throw new BleScanException(var2);
      } else {
         Iterator var6 = var4.iterator();

         while (var6.hasNext()) {
            var5.add(this.convertScanResultToRxAndroidBLEModel(var3, (android.bluetooth.le.ScanResult)var6.next()));
         }

         return var5;
      }
   }

   @Override
   public void scanBleDeviceInBackground(PendingIntent var1, ScanSettings var2, ScanFilter... var3) {
      if (VERSION.SDK_INT < 26) {
         RxBleLog.w("PendingIntent based scanning is available for Android O and higher only.");
      } else if (this.rxBleAdapterWrapper.isBluetoothEnabled()) {
         RxBleLog.i("Requesting pending intent based scan.");
         List var7 = this.scanObjectsConverter.toNativeFilters(var3);
         android.bluetooth.le.ScanSettings var6 = this.scanObjectsConverter.toNativeSettings(var2);
         int var4 = this.rxBleAdapterWrapper.startLeScan(var7, var6, var1);
         if (var4 != 0) {
            BleScanException var5 = new BleScanException(var4);
            RxBleLog.w(var5, "Failed to start scan");
            throw var5;
         }
      } else {
         RxBleLog.w("PendingIntent based scanning is available only when Bluetooth is ON.");
         throw new BleScanException(1);
      }
   }

   @Override
   public void stopBackgroundBleScan(PendingIntent var1) {
      if (VERSION.SDK_INT < 26) {
         RxBleLog.w("PendingIntent based scanning is available for Android O and higher only.");
      } else if (!this.rxBleAdapterWrapper.isBluetoothEnabled()) {
         RxBleLog.w("PendingIntent based scanning is available only when Bluetooth is ON.");
      } else {
         RxBleLog.i("Stopping pending intent based scan.");
         this.rxBleAdapterWrapper.stopLeScan(var1);
      }
   }
}
