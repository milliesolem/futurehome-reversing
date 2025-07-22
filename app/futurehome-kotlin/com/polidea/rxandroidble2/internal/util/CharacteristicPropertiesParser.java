package com.polidea.rxandroidble2.internal.util;

import com.polidea.rxandroidble2.internal.RxBleLog;

public class CharacteristicPropertiesParser {
   private final int[] possibleProperties;
   private final int propertyBroadcast;
   private final int propertyIndicate;
   private final int propertyNotify;
   private final int propertyRead;
   private final int propertySignedWrite;
   private final int propertyWrite;
   private final int propertyWriteNoResponse;

   public CharacteristicPropertiesParser(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      this.propertyBroadcast = var1;
      this.propertyRead = var2;
      this.propertyWriteNoResponse = var3;
      this.propertyWrite = var4;
      this.propertyNotify = var5;
      this.propertyIndicate = var6;
      this.propertySignedWrite = var7;
      this.possibleProperties = this.getPossibleProperties();
   }

   private int[] getPossibleProperties() {
      return new int[]{
         this.propertyBroadcast,
         this.propertyRead,
         this.propertyWriteNoResponse,
         this.propertyWrite,
         this.propertyNotify,
         this.propertyIndicate,
         this.propertySignedWrite
      };
   }

   private static boolean propertiesIntContains(int var0, int var1) {
      boolean var2;
      if ((var0 & var1) != 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private String propertyToString(int var1) {
      if (var1 == this.propertyRead) {
         return "READ";
      } else if (var1 == this.propertyWrite) {
         return "WRITE";
      } else if (var1 == this.propertyWriteNoResponse) {
         return "WRITE_NO_RESPONSE";
      } else if (var1 == this.propertySignedWrite) {
         return "SIGNED_WRITE";
      } else if (var1 == this.propertyIndicate) {
         return "INDICATE";
      } else if (var1 == this.propertyBroadcast) {
         return "BROADCAST";
      } else if (var1 == this.propertyNotify) {
         return "NOTIFY";
      } else if (var1 == 0) {
         return "";
      } else {
         RxBleLog.e("Unknown property specified (%d)", var1);
         StringBuilder var2 = new StringBuilder("UNKNOWN (");
         var2.append(var1);
         var2.append(" -> check android.bluetooth.BluetoothGattCharacteristic)");
         return var2.toString();
      }
   }

   public String propertiesIntToString(int var1) {
      StringBuilder var6 = new StringBuilder("[ ");

      for (int var4 : this.possibleProperties) {
         if (propertiesIntContains(var1, var4)) {
            var6.append(this.propertyToString(var4));
            var6.append(" ");
         }
      }

      var6.append("]");
      return var6.toString();
   }
}
