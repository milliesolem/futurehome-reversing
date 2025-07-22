package com.polidea.rxandroidble2.scan;

import android.bluetooth.BluetoothAdapter;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.polidea.rxandroidble2.internal.ScanResultInterface;
import com.polidea.rxandroidble2.internal.logger.LoggerUtil;
import com.polidea.rxandroidble2.internal.scan.ScanFilterInterface;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ScanFilter implements Parcelable, ScanFilterInterface {
   public static final Creator<ScanFilter> CREATOR = new Creator<ScanFilter>() {
      public ScanFilter createFromParcel(Parcel var1) {
         ScanFilter.Builder var3 = new ScanFilter.Builder();
         if (var1.readInt() == 1) {
            var3.setDeviceName(var1.readString());
         }

         if (var1.readInt() == 1) {
            var3.setDeviceAddress(var1.readString());
         }

         if (var1.readInt() == 1) {
            ParcelUuid var4 = (ParcelUuid)var1.readParcelable(ParcelUuid.class.getClassLoader());
            var3.setServiceUuid(var4);
            if (var1.readInt() == 1) {
               var3.setServiceUuid(var4, (ParcelUuid)var1.readParcelable(ParcelUuid.class.getClassLoader()));
            }
         }

         if (var1.readInt() == 1) {
            ParcelUuid var7 = (ParcelUuid)var1.readParcelable(ParcelUuid.class.getClassLoader());
            var3.setServiceSolicitationUuid(var7);
            if (var1.readInt() == 1) {
               var3.setServiceSolicitationUuid(var7, (ParcelUuid)var1.readParcelable(ParcelUuid.class.getClassLoader()));
            }
         }

         if (var1.readInt() == 1) {
            ParcelUuid var8 = (ParcelUuid)var1.readParcelable(ParcelUuid.class.getClassLoader());
            if (var1.readInt() == 1) {
               byte[] var6 = new byte[var1.readInt()];
               var1.readByteArray(var6);
               if (var1.readInt() == 0) {
                  var3.setServiceData(var8, var6);
               } else {
                  byte[] var5 = new byte[var1.readInt()];
                  var1.readByteArray(var5);
                  var3.setServiceData(var8, var6, var5);
               }
            }
         }

         int var2 = var1.readInt();
         if (var1.readInt() == 1) {
            byte[] var9 = new byte[var1.readInt()];
            var1.readByteArray(var9);
            if (var1.readInt() == 0) {
               var3.setManufacturerData(var2, var9);
            } else {
               byte[] var10 = new byte[var1.readInt()];
               var1.readByteArray(var10);
               var3.setManufacturerData(var2, var9, var10);
            }
         }

         return var3.build();
      }

      public ScanFilter[] newArray(int var1) {
         return new ScanFilter[var1];
      }
   };
   private static final ScanFilter EMPTY = new ScanFilter.Builder().build();
   private final String mDeviceAddress;
   private final String mDeviceName;
   private final byte[] mManufacturerData;
   private final byte[] mManufacturerDataMask;
   private final int mManufacturerId;
   private final byte[] mServiceData;
   private final byte[] mServiceDataMask;
   private final ParcelUuid mServiceDataUuid;
   private final ParcelUuid mServiceSolicitationUuid;
   private final ParcelUuid mServiceSolicitationUuidMask;
   private final ParcelUuid mServiceUuid;
   private final ParcelUuid mServiceUuidMask;

   ScanFilter(
      String var1,
      String var2,
      ParcelUuid var3,
      ParcelUuid var4,
      ParcelUuid var5,
      ParcelUuid var6,
      ParcelUuid var7,
      byte[] var8,
      byte[] var9,
      int var10,
      byte[] var11,
      byte[] var12
   ) {
      this.mDeviceName = var1;
      this.mServiceUuid = var3;
      this.mServiceUuidMask = var4;
      this.mServiceSolicitationUuid = var5;
      this.mServiceSolicitationUuidMask = var6;
      this.mDeviceAddress = var2;
      this.mServiceDataUuid = var7;
      this.mServiceData = var8;
      this.mServiceDataMask = var9;
      this.mManufacturerId = var10;
      this.mManufacturerData = var11;
      this.mManufacturerDataMask = var12;
   }

   private static boolean deepEquals(byte[] var0, byte[] var1) {
      boolean var2;
      if (var0 == var1 || var0 != null && var1 != null && Arrays.equals(var0, var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static ScanFilter empty() {
      return new ScanFilter.Builder().build();
   }

   private static boolean equals(Object var0, Object var1) {
      boolean var2;
      if (var0 == var1 || var0 != null && var0.equals(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private static boolean matchesPartialData(byte[] var0, byte[] var1, byte[] var2) {
      if (var2 != null && var2.length >= var0.length) {
         if (var1 == null) {
            for (int var5 = 0; var5 < var0.length; var5++) {
               if (var2[var5] != var0[var5]) {
                  return false;
               }
            }

            return true;
         } else {
            for (int var3 = 0; var3 < var0.length; var3++) {
               byte var4 = var1[var3];
               if ((var2[var3] & var4) != (var4 & var0[var3])) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private static boolean matchesServiceSolicitationUuids(ParcelUuid var0, ParcelUuid var1, List<ParcelUuid> var2) {
      if (var0 == null) {
         return true;
      } else if (var2 == null) {
         return false;
      } else {
         for (ParcelUuid var4 : var2) {
            UUID var5;
            if (var1 == null) {
               var5 = null;
            } else {
               var5 = var1.getUuid();
            }

            if (matchesServiceUuid(var0.getUuid(), var5, var4.getUuid())) {
               return true;
            }
         }

         return false;
      }
   }

   private static boolean matchesServiceUuid(UUID var0, UUID var1, UUID var2) {
      if (var1 == null) {
         return var0.equals(var2);
      } else {
         long var5 = var0.getLeastSignificantBits();
         long var7 = var1.getLeastSignificantBits();
         long var3 = var2.getLeastSignificantBits();
         long var9 = var1.getLeastSignificantBits();
         boolean var11 = false;
         if ((var5 & var7) != (var3 & var9)) {
            return false;
         } else {
            var5 = var0.getMostSignificantBits();
            var7 = var1.getMostSignificantBits();
            var3 = var2.getMostSignificantBits();
            if ((var5 & var7) == (var1.getMostSignificantBits() & var3)) {
               var11 = true;
            }

            return var11;
         }
      }
   }

   private static boolean matchesServiceUuids(ParcelUuid var0, ParcelUuid var1, List<ParcelUuid> var2) {
      if (var0 == null) {
         return true;
      } else if (var2 == null) {
         return false;
      } else {
         for (ParcelUuid var4 : var2) {
            UUID var5;
            if (var1 == null) {
               var5 = null;
            } else {
               var5 = var1.getUuid();
            }

            if (matchesServiceUuid(var0.getUuid(), var5, var4.getUuid())) {
               return true;
            }
         }

         return false;
      }
   }

   public int describeContents() {
      return 0;
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         var1 = var1;
         if (!equals(this.mDeviceName, var1.mDeviceName)
            || !equals(this.mDeviceAddress, var1.mDeviceAddress)
            || this.mManufacturerId != var1.mManufacturerId
            || !deepEquals(this.mManufacturerData, var1.mManufacturerData)
            || !deepEquals(this.mManufacturerDataMask, var1.mManufacturerDataMask)
            || !equals(this.mServiceDataUuid, var1.mServiceDataUuid)
            || !deepEquals(this.mServiceData, var1.mServiceData)
            || !deepEquals(this.mServiceDataMask, var1.mServiceDataMask)
            || !equals(this.mServiceUuid, var1.mServiceUuid)
            || !equals(this.mServiceUuidMask, var1.mServiceUuidMask)
            || !equals(this.mServiceSolicitationUuid, var1.mServiceSolicitationUuid)
            || !equals(this.mServiceSolicitationUuidMask, var1.mServiceSolicitationUuidMask)) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   public String getDeviceAddress() {
      return this.mDeviceAddress;
   }

   public String getDeviceName() {
      return this.mDeviceName;
   }

   public byte[] getManufacturerData() {
      return this.mManufacturerData;
   }

   public byte[] getManufacturerDataMask() {
      return this.mManufacturerDataMask;
   }

   public int getManufacturerId() {
      return this.mManufacturerId;
   }

   public byte[] getServiceData() {
      return this.mServiceData;
   }

   public byte[] getServiceDataMask() {
      return this.mServiceDataMask;
   }

   public ParcelUuid getServiceDataUuid() {
      return this.mServiceDataUuid;
   }

   public ParcelUuid getServiceSolicitationUuid() {
      return this.mServiceSolicitationUuid;
   }

   public ParcelUuid getServiceSolicitationUuidMask() {
      return this.mServiceSolicitationUuidMask;
   }

   public ParcelUuid getServiceUuid() {
      return this.mServiceUuid;
   }

   public ParcelUuid getServiceUuidMask() {
      return this.mServiceUuidMask;
   }

   @Override
   public int hashCode() {
      return Arrays.hashCode(
         new Object[]{
            this.mDeviceName,
            this.mDeviceAddress,
            this.mManufacturerId,
            Arrays.hashCode(this.mManufacturerData),
            Arrays.hashCode(this.mManufacturerDataMask),
            this.mServiceDataUuid,
            Arrays.hashCode(this.mServiceData),
            Arrays.hashCode(this.mServiceDataMask),
            this.mServiceUuid,
            this.mServiceUuidMask,
            this.mServiceSolicitationUuid,
            this.mServiceSolicitationUuidMask
         }
      );
   }

   @Override
   public boolean isAllFieldsEmpty() {
      return this.equals(EMPTY);
   }

   @Override
   public boolean matches(ScanResultInterface var1) {
      boolean var4 = false;
      if (var1 == null) {
         return false;
      } else {
         String var5 = var1.getAddress();
         String var6 = this.mDeviceAddress;
         if (var6 != null && !var6.equals(var5)) {
            return false;
         } else {
            ScanRecord var10 = var1.getScanRecord();
            var6 = this.mDeviceName;
            if (var6 == null || var6.equals(var1.getDeviceName()) || var10 != null && this.mDeviceName.equals(var10.getDeviceName())) {
               if (var10 == null) {
                  boolean var3 = var4;
                  if (this.mServiceUuid == null) {
                     var3 = var4;
                     if (this.mManufacturerData == null) {
                        var3 = var4;
                        if (this.mServiceData == null) {
                           var3 = true;
                        }
                     }
                  }

                  return var3;
               } else {
                  ParcelUuid var7 = this.mServiceUuid;
                  if (var7 != null && !matchesServiceUuids(var7, this.mServiceUuidMask, var10.getServiceUuids())) {
                     return false;
                  } else {
                     ParcelUuid var8 = this.mServiceSolicitationUuid;
                     if (var8 != null && !matchesServiceSolicitationUuids(var8, this.mServiceSolicitationUuidMask, var10.getServiceSolicitationUuids())) {
                        return false;
                     } else {
                        ParcelUuid var9 = this.mServiceDataUuid;
                        if (var9 != null && !matchesPartialData(this.mServiceData, this.mServiceDataMask, var10.getServiceData(var9))) {
                           return false;
                        } else {
                           int var2 = this.mManufacturerId;
                           return var2 < 0 || matchesPartialData(this.mManufacturerData, this.mManufacturerDataMask, var10.getManufacturerSpecificData(var2));
                        }
                     }
                  }
               }
            } else {
               return false;
            }
         }
      }
   }

   @Override
   public String toString() {
      StringBuilder var3 = new StringBuilder("BluetoothLeScanFilter [mDeviceName=");
      var3.append(this.mDeviceName);
      var3.append(", ");
      var3.append(LoggerUtil.commonMacMessage(this.mDeviceAddress));
      var3.append(", mUuid=");
      ParcelUuid var1 = this.mServiceUuid;
      Object var2 = null;
      String var4;
      if (var1 == null) {
         var4 = null;
      } else {
         var4 = LoggerUtil.getUuidToLog(var1.getUuid());
      }

      var3.append(var4);
      var3.append(", mUuidMask=");
      var1 = this.mServiceUuidMask;
      String var6;
      if (var1 == null) {
         var6 = null;
      } else {
         var6 = LoggerUtil.getUuidToLog(var1.getUuid());
      }

      var3.append(var6);
      var3.append(", mSolicitedUuid=");
      var1 = this.mServiceSolicitationUuid;
      String var8;
      if (var1 == null) {
         var8 = null;
      } else {
         var8 = LoggerUtil.getUuidToLog(var1.getUuid());
      }

      var3.append(var8);
      var3.append(", mSolicitedUuidMask=");
      var1 = this.mServiceSolicitationUuidMask;
      String var10;
      if (var1 == null) {
         var10 = null;
      } else {
         var10 = LoggerUtil.getUuidToLog(var1.getUuid());
      }

      var3.append(var10);
      var3.append(", mServiceDataUuid=");
      var1 = this.mServiceDataUuid;
      String var12;
      if (var1 == null) {
         var12 = (String)var2;
      } else {
         var12 = LoggerUtil.getUuidToLog(var1.getUuid());
      }

      var3.append(var12);
      var3.append(", mServiceData=");
      var3.append(Arrays.toString(this.mServiceData));
      var3.append(", mServiceDataMask=");
      var3.append(Arrays.toString(this.mServiceDataMask));
      var3.append(", mManufacturerId=");
      var3.append(this.mManufacturerId);
      var3.append(", mManufacturerData=");
      var3.append(Arrays.toString(this.mManufacturerData));
      var3.append(", mManufacturerDataMask=");
      var3.append(Arrays.toString(this.mManufacturerDataMask));
      var3.append("]");
      return var3.toString();
   }

   public void writeToParcel(Parcel var1, int var2) {
      String var5 = this.mDeviceName;
      byte var4 = 0;
      byte var3;
      if (var5 == null) {
         var3 = 0;
      } else {
         var3 = 1;
      }

      var1.writeInt(var3);
      var5 = this.mDeviceName;
      if (var5 != null) {
         var1.writeString(var5);
      }

      if (this.mDeviceAddress == null) {
         var3 = 0;
      } else {
         var3 = 1;
      }

      var1.writeInt(var3);
      var5 = this.mDeviceAddress;
      if (var5 != null) {
         var1.writeString(var5);
      }

      if (this.mServiceUuid == null) {
         var3 = 0;
      } else {
         var3 = 1;
      }

      var1.writeInt(var3);
      ParcelUuid var18 = this.mServiceUuid;
      if (var18 != null) {
         var1.writeParcelable(var18, var2);
         if (this.mServiceUuidMask == null) {
            var3 = 0;
         } else {
            var3 = 1;
         }

         var1.writeInt(var3);
         ParcelUuid var19 = this.mServiceUuidMask;
         if (var19 != null) {
            var1.writeParcelable(var19, var2);
         }
      }

      if (this.mServiceSolicitationUuid == null) {
         var3 = 0;
      } else {
         var3 = 1;
      }

      var1.writeInt(var3);
      ParcelUuid var20 = this.mServiceSolicitationUuid;
      if (var20 != null) {
         var1.writeParcelable(var20, var2);
         if (this.mServiceSolicitationUuidMask == null) {
            var3 = 0;
         } else {
            var3 = 1;
         }

         var1.writeInt(var3);
         ParcelUuid var21 = this.mServiceSolicitationUuidMask;
         if (var21 != null) {
            var1.writeParcelable(var21, var2);
         }
      }

      if (this.mServiceDataUuid == null) {
         var3 = 0;
      } else {
         var3 = 1;
      }

      var1.writeInt(var3);
      ParcelUuid var22 = this.mServiceDataUuid;
      if (var22 != null) {
         var1.writeParcelable(var22, var2);
         byte var6;
         if (this.mServiceData == null) {
            var6 = 0;
         } else {
            var6 = 1;
         }

         var1.writeInt(var6);
         byte[] var23 = this.mServiceData;
         if (var23 != null) {
            var1.writeInt(var23.length);
            var1.writeByteArray(this.mServiceData);
            if (this.mServiceDataMask == null) {
               var6 = 0;
            } else {
               var6 = 1;
            }

            var1.writeInt(var6);
            byte[] var24 = this.mServiceDataMask;
            if (var24 != null) {
               var1.writeInt(var24.length);
               var1.writeByteArray(this.mServiceDataMask);
            }
         }
      }

      var1.writeInt(this.mManufacturerId);
      byte var8;
      if (this.mManufacturerData == null) {
         var8 = 0;
      } else {
         var8 = 1;
      }

      var1.writeInt(var8);
      byte[] var25 = this.mManufacturerData;
      if (var25 != null) {
         var1.writeInt(var25.length);
         var1.writeByteArray(this.mManufacturerData);
         if (this.mManufacturerDataMask == null) {
            var8 = var4;
         } else {
            var8 = 1;
         }

         var1.writeInt(var8);
         byte[] var26 = this.mManufacturerDataMask;
         if (var26 != null) {
            var1.writeInt(var26.length);
            var1.writeByteArray(this.mManufacturerDataMask);
         }
      }
   }

   public static final class Builder {
      private String mDeviceAddress;
      private String mDeviceName;
      private byte[] mManufacturerData;
      private byte[] mManufacturerDataMask;
      private int mManufacturerId = -1;
      private byte[] mServiceData;
      private byte[] mServiceDataMask;
      private ParcelUuid mServiceDataUuid;
      private ParcelUuid mServiceSolicitationUuid;
      private ParcelUuid mServiceSolicitationUuidMask;
      private ParcelUuid mServiceUuid;
      private ParcelUuid mServiceUuidMask;

      public ScanFilter build() {
         return new ScanFilter(
            this.mDeviceName,
            this.mDeviceAddress,
            this.mServiceUuid,
            this.mServiceUuidMask,
            this.mServiceSolicitationUuid,
            this.mServiceSolicitationUuidMask,
            this.mServiceDataUuid,
            this.mServiceData,
            this.mServiceDataMask,
            this.mManufacturerId,
            this.mManufacturerData,
            this.mManufacturerDataMask
         );
      }

      public ScanFilter.Builder setDeviceAddress(String var1) {
         if (var1 != null && !BluetoothAdapter.checkBluetoothAddress(var1)) {
            StringBuilder var2 = new StringBuilder("invalid device address ");
            var2.append(var1);
            throw new IllegalArgumentException(var2.toString());
         } else {
            this.mDeviceAddress = var1;
            return this;
         }
      }

      public ScanFilter.Builder setDeviceName(String var1) {
         this.mDeviceName = var1;
         return this;
      }

      public ScanFilter.Builder setManufacturerData(int var1, byte[] var2) {
         if (var2 != null && var1 < 0) {
            throw new IllegalArgumentException("invalid manufacture id");
         } else {
            this.mManufacturerId = var1;
            this.mManufacturerData = var2;
            this.mManufacturerDataMask = null;
            return this;
         }
      }

      public ScanFilter.Builder setManufacturerData(int var1, byte[] var2, byte[] var3) {
         if (var2 != null && var1 < 0) {
            throw new IllegalArgumentException("invalid manufacture id");
         } else {
            byte[] var5 = this.mManufacturerDataMask;
            if (var5 != null) {
               byte[] var4 = this.mManufacturerData;
               if (var4 == null) {
                  throw new IllegalArgumentException("manufacturerData is null while manufacturerDataMask is not null");
               }

               if (var4.length != var5.length) {
                  throw new IllegalArgumentException("size mismatch for manufacturerData and manufacturerDataMask");
               }
            }

            this.mManufacturerId = var1;
            this.mManufacturerData = var2;
            this.mManufacturerDataMask = var3;
            return this;
         }
      }

      public ScanFilter.Builder setServiceData(ParcelUuid var1, byte[] var2) {
         if (var1 != null) {
            this.mServiceDataUuid = var1;
            this.mServiceData = var2;
            this.mServiceDataMask = null;
            return this;
         } else {
            throw new IllegalArgumentException("serviceDataUuid is null");
         }
      }

      public ScanFilter.Builder setServiceData(ParcelUuid var1, byte[] var2, byte[] var3) {
         if (var1 != null) {
            byte[] var5 = this.mServiceDataMask;
            if (var5 != null) {
               byte[] var4 = this.mServiceData;
               if (var4 == null) {
                  throw new IllegalArgumentException("serviceData is null while serviceDataMask is not null");
               }

               if (var4.length != var5.length) {
                  throw new IllegalArgumentException("size mismatch for service data and service data mask");
               }
            }

            this.mServiceDataUuid = var1;
            this.mServiceData = var2;
            this.mServiceDataMask = var3;
            return this;
         } else {
            throw new IllegalArgumentException("serviceDataUuid is null");
         }
      }

      public ScanFilter.Builder setServiceSolicitationUuid(ParcelUuid var1) {
         this.mServiceSolicitationUuid = var1;
         this.mServiceSolicitationUuidMask = null;
         return this;
      }

      public ScanFilter.Builder setServiceSolicitationUuid(ParcelUuid var1, ParcelUuid var2) {
         if (var2 != null && var1 == null) {
            throw new IllegalArgumentException("SolicitationUuid is null while SolicitationUuidMask is not null!");
         } else {
            this.mServiceSolicitationUuid = var1;
            this.mServiceSolicitationUuidMask = var2;
            return this;
         }
      }

      public ScanFilter.Builder setServiceUuid(ParcelUuid var1) {
         this.mServiceUuid = var1;
         this.mServiceUuidMask = null;
         return this;
      }

      public ScanFilter.Builder setServiceUuid(ParcelUuid var1, ParcelUuid var2) {
         if (this.mServiceUuidMask != null && this.mServiceUuid == null) {
            throw new IllegalArgumentException("uuid is null while uuidMask is not null!");
         } else {
            this.mServiceUuid = var1;
            this.mServiceUuidMask = var2;
            return this;
         }
      }
   }
}
