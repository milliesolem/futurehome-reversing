package com.polidea.rxandroidble2.internal.scan;

import android.os.ParcelUuid;
import android.os.Build.VERSION;
import android.util.SparseArray;
import androidx.webkit.internal.ApiHelperForN..ExternalSyntheticApiModelOutline4;
import com.polidea.rxandroidble2.internal.util.ScanRecordParser;
import com.polidea.rxandroidble2.scan.ScanRecord;
import java.util.List;
import java.util.Map;

public class ScanRecordImplNativeWrapper implements ScanRecord {
   private final android.bluetooth.le.ScanRecord nativeScanRecord;
   private final ScanRecordParser scanRecordParser;

   public ScanRecordImplNativeWrapper(android.bluetooth.le.ScanRecord var1, ScanRecordParser var2) {
      this.nativeScanRecord = var1;
      this.scanRecordParser = var2;
   }

   @Override
   public int getAdvertiseFlags() {
      return this.nativeScanRecord.getAdvertiseFlags();
   }

   @Override
   public byte[] getBytes() {
      return this.nativeScanRecord.getBytes();
   }

   @Override
   public String getDeviceName() {
      return this.nativeScanRecord.getDeviceName();
   }

   @Override
   public SparseArray<byte[]> getManufacturerSpecificData() {
      return this.nativeScanRecord.getManufacturerSpecificData();
   }

   @Override
   public byte[] getManufacturerSpecificData(int var1) {
      return this.nativeScanRecord.getManufacturerSpecificData(var1);
   }

   @Override
   public Map<ParcelUuid, byte[]> getServiceData() {
      return this.nativeScanRecord.getServiceData();
   }

   @Override
   public byte[] getServiceData(ParcelUuid var1) {
      return this.nativeScanRecord.getServiceData(var1);
   }

   @Override
   public List<ParcelUuid> getServiceSolicitationUuids() {
      return VERSION.SDK_INT >= 29
         ? ExternalSyntheticApiModelOutline4.m(this.nativeScanRecord)
         : this.scanRecordParser.parseFromBytes(this.nativeScanRecord.getBytes()).getServiceSolicitationUuids();
   }

   @Override
   public List<ParcelUuid> getServiceUuids() {
      return this.nativeScanRecord.getServiceUuids();
   }

   @Override
   public int getTxPowerLevel() {
      return this.nativeScanRecord.getTxPowerLevel();
   }
}
