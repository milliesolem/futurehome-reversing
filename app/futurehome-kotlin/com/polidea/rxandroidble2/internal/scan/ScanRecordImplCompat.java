package com.polidea.rxandroidble2.internal.scan;

import android.os.ParcelUuid;
import android.util.SparseArray;
import com.polidea.rxandroidble2.scan.ScanRecord;
import java.util.List;
import java.util.Map;

public class ScanRecordImplCompat implements ScanRecord {
   private final int advertiseFlags;
   private final byte[] bytes;
   private final String deviceName;
   private final SparseArray<byte[]> manufacturerSpecificData;
   private final Map<ParcelUuid, byte[]> serviceData;
   private final List<ParcelUuid> serviceSolicitationUuids;
   private final List<ParcelUuid> serviceUuids;
   private final int txPowerLevel;

   public ScanRecordImplCompat(
      List<ParcelUuid> var1, List<ParcelUuid> var2, SparseArray<byte[]> var3, Map<ParcelUuid, byte[]> var4, int var5, int var6, String var7, byte[] var8
   ) {
      this.serviceUuids = var1;
      this.serviceSolicitationUuids = var2;
      this.manufacturerSpecificData = var3;
      this.serviceData = var4;
      this.deviceName = var7;
      this.advertiseFlags = var5;
      this.txPowerLevel = var6;
      this.bytes = var8;
   }

   @Override
   public int getAdvertiseFlags() {
      return this.advertiseFlags;
   }

   @Override
   public byte[] getBytes() {
      return this.bytes;
   }

   @Override
   public String getDeviceName() {
      return this.deviceName;
   }

   @Override
   public SparseArray<byte[]> getManufacturerSpecificData() {
      return this.manufacturerSpecificData;
   }

   @Override
   public byte[] getManufacturerSpecificData(int var1) {
      return (byte[])this.manufacturerSpecificData.get(var1);
   }

   @Override
   public Map<ParcelUuid, byte[]> getServiceData() {
      return this.serviceData;
   }

   @Override
   public byte[] getServiceData(ParcelUuid var1) {
      return var1 == null ? null : this.serviceData.get(var1);
   }

   @Override
   public List<ParcelUuid> getServiceSolicitationUuids() {
      return this.serviceSolicitationUuids;
   }

   @Override
   public List<ParcelUuid> getServiceUuids() {
      return this.serviceUuids;
   }

   @Override
   public int getTxPowerLevel() {
      return this.txPowerLevel;
   }
}
