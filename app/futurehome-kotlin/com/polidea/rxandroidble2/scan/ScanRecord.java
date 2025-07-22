package com.polidea.rxandroidble2.scan;

import android.os.ParcelUuid;
import android.util.SparseArray;
import java.util.List;
import java.util.Map;

public interface ScanRecord {
   int getAdvertiseFlags();

   byte[] getBytes();

   String getDeviceName();

   SparseArray<byte[]> getManufacturerSpecificData();

   byte[] getManufacturerSpecificData(int var1);

   Map<ParcelUuid, byte[]> getServiceData();

   byte[] getServiceData(ParcelUuid var1);

   List<ParcelUuid> getServiceSolicitationUuids();

   List<ParcelUuid> getServiceUuids();

   int getTxPowerLevel();
}
