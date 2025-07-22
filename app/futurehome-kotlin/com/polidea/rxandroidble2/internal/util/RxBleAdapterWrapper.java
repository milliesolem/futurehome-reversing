package com.polidea.rxandroidble2.internal.util;

import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.exceptions.BleException;
import com.polidea.rxandroidble2.internal.RxBleLog;
import io.flutter.view.AccessibilityBridge$$ExternalSyntheticApiModelOutline0;
import java.util.List;
import java.util.Set;

public class RxBleAdapterWrapper {
   private static BleException nullBluetoothAdapter = new BleException("bluetoothAdapter is null");
   private final BluetoothAdapter bluetoothAdapter;

   @Inject
   public RxBleAdapterWrapper(BluetoothAdapter var1) {
      this.bluetoothAdapter = var1;
   }

   public Set<BluetoothDevice> getBondedDevices() {
      BluetoothAdapter var1 = this.bluetoothAdapter;
      if (var1 != null) {
         return var1.getBondedDevices();
      } else {
         throw nullBluetoothAdapter;
      }
   }

   public BluetoothDevice getRemoteDevice(String var1) {
      BluetoothAdapter var2 = this.bluetoothAdapter;
      if (var2 != null) {
         return var2.getRemoteDevice(var1);
      } else {
         throw nullBluetoothAdapter;
      }
   }

   public boolean hasBluetoothAdapter() {
      boolean var1;
      if (this.bluetoothAdapter != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isBluetoothEnabled() {
      BluetoothAdapter var2 = this.bluetoothAdapter;
      boolean var1;
      if (var2 != null && var2.isEnabled()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public int startLeScan(List<ScanFilter> var1, ScanSettings var2, PendingIntent var3) {
      BluetoothAdapter var4 = this.bluetoothAdapter;
      if (var4 != null) {
         return AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var4.getBluetoothLeScanner(), var1, var2, var3);
      } else {
         throw nullBluetoothAdapter;
      }
   }

   public void startLeScan(List<ScanFilter> var1, ScanSettings var2, ScanCallback var3) {
      BluetoothAdapter var4 = this.bluetoothAdapter;
      if (var4 != null) {
         var4.getBluetoothLeScanner().startScan(var1, var2, var3);
      } else {
         throw nullBluetoothAdapter;
      }
   }

   public boolean startLegacyLeScan(LeScanCallback var1) {
      BluetoothAdapter var2 = this.bluetoothAdapter;
      if (var2 != null) {
         return var2.startLeScan(var1);
      } else {
         throw nullBluetoothAdapter;
      }
   }

   public void stopLeScan(PendingIntent var1) {
      BluetoothAdapter var2 = this.bluetoothAdapter;
      if (var2 != null) {
         AccessibilityBridge$$ExternalSyntheticApiModelOutline0.m(var2.getBluetoothLeScanner(), var1);
      } else {
         throw nullBluetoothAdapter;
      }
   }

   public void stopLeScan(ScanCallback var1) {
      BluetoothAdapter var2 = this.bluetoothAdapter;
      if (var2 != null) {
         if (!var2.isEnabled()) {
            RxBleLog.v("BluetoothAdapter is disabled, calling BluetoothLeScanner.stopScan(ScanCallback) may cause IllegalStateException");
         } else {
            BluetoothLeScanner var3 = this.bluetoothAdapter.getBluetoothLeScanner();
            if (var3 == null) {
               RxBleLog.w(
                  "Cannot call BluetoothLeScanner.stopScan(ScanCallback) on 'null' reference; BluetoothAdapter.isEnabled() == %b",
                  this.bluetoothAdapter.isEnabled()
               );
            } else {
               var3.stopScan(var1);
            }
         }
      } else {
         throw nullBluetoothAdapter;
      }
   }

   public void stopLegacyLeScan(LeScanCallback var1) {
      BluetoothAdapter var2 = this.bluetoothAdapter;
      if (var2 != null) {
         var2.stopLeScan(var1);
      } else {
         throw nullBluetoothAdapter;
      }
   }
}
