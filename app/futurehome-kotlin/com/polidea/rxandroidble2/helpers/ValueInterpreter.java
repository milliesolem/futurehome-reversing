package com.polidea.rxandroidble2.helpers;

import com.polidea.rxandroidble2.internal.RxBleLog;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ValueInterpreter {
   public static final int FORMAT_FLOAT = 52;
   public static final int FORMAT_SFLOAT = 50;
   public static final int FORMAT_SINT16 = 34;
   public static final int FORMAT_SINT32 = 36;
   public static final int FORMAT_SINT8 = 33;
   public static final int FORMAT_UINT16 = 18;
   public static final int FORMAT_UINT32 = 20;
   public static final int FORMAT_UINT8 = 17;

   private ValueInterpreter() {
   }

   private static float bytesToFloat(byte var0, byte var1) {
      int var3 = unsignedToSigned(unsignedByteToInt(var0) + ((unsignedByteToInt(var1) & 15) << 8), 12);
      int var2 = unsignedToSigned(unsignedByteToInt(var1) >> 4, 4);
      return (float)(var3 * Math.pow(10.0, var2));
   }

   private static float bytesToFloat(byte var0, byte var1, byte var2, byte var3) {
      return (float)(unsignedToSigned(unsignedByteToInt(var0) + (unsignedByteToInt(var1) << 8) + (unsignedByteToInt(var2) << 16), 24) * Math.pow(10.0, var3));
   }

   public static Float getFloatValue(byte[] var0, int var1, int var2) {
      if (getTypeLen(var1) + var2 > var0.length) {
         RxBleLog.w("Float formatType (0x%x) is longer than remaining bytes (%d) - returning null", var1, var0.length - var2);
         return null;
      } else if (var1 != 50) {
         if (var1 != 52) {
            RxBleLog.w("Passed an invalid float formatType (0x%x) - returning null", var1);
            return null;
         } else {
            return bytesToFloat(var0[var2], var0[var2 + 1], var0[var2 + 2], var0[var2 + 3]);
         }
      } else {
         return bytesToFloat(var0[var2], var0[var2 + 1]);
      }
   }

   public static Integer getIntValue(byte[] var0, int var1, int var2) {
      if (getTypeLen(var1) + var2 > var0.length) {
         RxBleLog.w("Int formatType (0x%x) is longer than remaining bytes (%d) - returning null", var1, var0.length - var2);
         return null;
      } else if (var1 != 17) {
         if (var1 != 18) {
            if (var1 != 20) {
               if (var1 != 36) {
                  if (var1 != 33) {
                     if (var1 != 34) {
                        RxBleLog.w("Passed an invalid integer formatType (0x%x) - returning null", var1);
                        return null;
                     } else {
                        return unsignedToSigned(unsignedBytesToInt(var0[var2], var0[var2 + 1]), 16);
                     }
                  } else {
                     return unsignedToSigned(unsignedByteToInt(var0[var2]), 8);
                  }
               } else {
                  return unsignedToSigned(unsignedBytesToInt(var0[var2], var0[var2 + 1], var0[var2 + 2], var0[var2 + 3]), 32);
               }
            } else {
               return unsignedBytesToInt(var0[var2], var0[var2 + 1], var0[var2 + 2], var0[var2 + 3]);
            }
         } else {
            return unsignedBytesToInt(var0[var2], var0[var2 + 1]);
         }
      } else {
         return unsignedByteToInt(var0[var2]);
      }
   }

   public static String getStringValue(byte[] var0, int var1) {
      int var3 = var0.length;
      int var2 = 0;
      if (var1 > var3) {
         RxBleLog.w("Passed offset that exceeds the length of the byte array - returning null");
         return null;
      } else {
         byte[] var4;
         for (var4 = new byte[var0.length - var1]; var2 != var0.length - var1; var2++) {
            var4[var2] = var0[var1 + var2];
         }

         return new String(var4);
      }
   }

   private static int getTypeLen(int var0) {
      return var0 & 15;
   }

   private static int unsignedByteToInt(byte var0) {
      return var0 & 0xFF;
   }

   private static int unsignedBytesToInt(byte var0, byte var1) {
      return unsignedByteToInt(var0) + (unsignedByteToInt(var1) << 8);
   }

   private static int unsignedBytesToInt(byte var0, byte var1, byte var2, byte var3) {
      return unsignedByteToInt(var0) + (unsignedByteToInt(var1) << 8) + (unsignedByteToInt(var2) << 16) + (unsignedByteToInt(var3) << 24);
   }

   private static int unsignedToSigned(int var0, int var1) {
      int var2 = 1 << var1 - 1;
      var1 = var0;
      if ((var0 & var2) != 0) {
         var1 = (var2 - (var0 & var2 - 1)) * -1;
      }

      return var1;
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface FloatFormatType {
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface IntFormatType {
   }
}
