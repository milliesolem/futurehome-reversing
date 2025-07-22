package com.polidea.rxandroidble2.internal.util;

import android.os.ParcelUuid;
import android.util.SparseArray;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.internal.RxBleLog;
import com.polidea.rxandroidble2.internal.logger.LoggerUtil;
import com.polidea.rxandroidble2.internal.scan.ScanRecordImplCompat;
import com.polidea.rxandroidble2.scan.ScanRecord;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class ScanRecordParser {
   public static final UUID BASE_UUID = UUID.fromString("00000000-0000-1000-8000-00805F9B34FB");
   private static final int DATA_TYPE_FLAGS = 1;
   private static final int DATA_TYPE_LOCAL_NAME_COMPLETE = 9;
   private static final int DATA_TYPE_LOCAL_NAME_SHORT = 8;
   private static final int DATA_TYPE_MANUFACTURER_SPECIFIC_DATA = 255;
   private static final int DATA_TYPE_SERVICE_DATA_128_BIT = 33;
   private static final int DATA_TYPE_SERVICE_DATA_16_BIT = 22;
   private static final int DATA_TYPE_SERVICE_DATA_32_BIT = 32;
   private static final int DATA_TYPE_SERVICE_SOLICITATION_UUIDS_128_BIT = 21;
   private static final int DATA_TYPE_SERVICE_SOLICITATION_UUIDS_16_BIT = 20;
   private static final int DATA_TYPE_SERVICE_SOLICITATION_UUIDS_32_BIT = 31;
   private static final int DATA_TYPE_SERVICE_UUIDS_128_BIT_COMPLETE = 7;
   private static final int DATA_TYPE_SERVICE_UUIDS_128_BIT_PARTIAL = 6;
   private static final int DATA_TYPE_SERVICE_UUIDS_16_BIT_COMPLETE = 3;
   private static final int DATA_TYPE_SERVICE_UUIDS_16_BIT_PARTIAL = 2;
   private static final int DATA_TYPE_SERVICE_UUIDS_32_BIT_COMPLETE = 5;
   private static final int DATA_TYPE_SERVICE_UUIDS_32_BIT_PARTIAL = 4;
   private static final int DATA_TYPE_TX_POWER_LEVEL = 10;
   public static final int UUID_BYTES_128_BIT = 16;
   public static final int UUID_BYTES_16_BIT = 2;
   public static final int UUID_BYTES_32_BIT = 4;

   private byte[] extractBytes(byte[] var1, int var2, int var3) {
      byte[] var4 = new byte[var3];
      System.arraycopy(var1, var2, var4, 0, var3);
      return var4;
   }

   private int parseServiceSolicitationUuid(byte[] var1, int var2, int var3, int var4, List<ParcelUuid> var5) {
      while (var3 > 0) {
         var5.add(parseUuidFrom(this.extractBytes(var1, var2, var4)));
         var3 -= var4;
         var2 += var4;
      }

      return var2;
   }

   private int parseServiceUuid(byte[] var1, int var2, int var3, int var4, List<ParcelUuid> var5) {
      while (var3 > 0) {
         var5.add(parseUuidFrom(this.extractBytes(var1, var2, var4)));
         var3 -= var4;
         var2 += var4;
      }

      return var2;
   }

   private static ParcelUuid parseUuidFrom(byte[] var0) {
      if (var0 != null) {
         int var1 = var0.length;
         if (var1 != 2 && var1 != 4 && var1 != 16) {
            StringBuilder var10 = new StringBuilder("uuidBytes length invalid - ");
            var10.append(var1);
            throw new IllegalArgumentException(var10.toString());
         } else if (var1 == 16) {
            ByteBuffer var9 = ByteBuffer.wrap(var0).order(ByteOrder.LITTLE_ENDIAN);
            return new ParcelUuid(new UUID(var9.getLong(8), var9.getLong(0)));
         } else {
            long var2;
            if (var1 == 2) {
               var2 = (long)(var0[0] & 255) + ((var0[1] & 255) << 8);
            } else {
               var2 = var0[0] & 255;
               long var6 = (var0[1] & 255) << 8;
               long var4 = (var0[2] & 255) << 16;
               var2 = ((var0[3] & 255) << 24) + var2 + var6 + var4;
            }

            UUID var8 = BASE_UUID;
            return new ParcelUuid(new UUID(var8.getMostSignificantBits() + (var2 << 32), var8.getLeastSignificantBits()));
         }
      } else {
         throw new IllegalArgumentException("uuidBytes cannot be null");
      }
   }

   public List<UUID> extractUUIDs(byte[] var1) {
      List var2 = this.parseFromBytes(var1).getServiceUuids();
      if (var2 == null) {
         return new ArrayList<>();
      } else {
         ArrayList var3 = new ArrayList();
         Iterator var4 = var2.iterator();

         while (var4.hasNext()) {
            var3.add(((ParcelUuid)var4.next()).getUuid());
         }

         return var3;
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public ScanRecord parseFromBytes(byte[] var1) {
      if (var1 == null) {
         return null;
      } else {
         ArrayList var10 = new ArrayList();
         ArrayList var11 = new ArrayList();
         SparseArray var13 = new SparseArray();
         HashMap var12 = new HashMap();
         String var9 = null;
         int var2 = 0;
         int var4 = -1;
         int var3 = Integer.MIN_VALUE;

         while (true) {
            try {
               if (var2 >= var1.length) {
                  break;
               }
            } catch (Exception var25) {
               RxBleLog.e(var25, "Unable to parse scan record: %s", LoggerUtil.bytesToHex(var1));
               return new ScanRecordImplCompat(null, null, null, null, -1, Integer.MIN_VALUE, null, var1);
            }

            int var8 = var1[var2] & 255;
            if (var8 == 0) {
               break;
            }

            int var6 = var8 - 1;
            int var5 = var2 + 2;
            int var7 = var1[var2 + 1] & 255;
            if (var7 != 255) {
               switch (var7) {
                  case 1:
                     var4 = var1[var5] & 255;
                     break;
                  case 2:
                  case 3:
                     try {
                        this.parseServiceUuid(var1, var5, var6, 2, var10);
                        break;
                     } catch (Exception var16) {
                        RxBleLog.e(var16, "Unable to parse scan record: %s", LoggerUtil.bytesToHex(var1));
                        return new ScanRecordImplCompat(null, null, null, null, -1, Integer.MIN_VALUE, null, var1);
                     }
                  case 4:
                  case 5:
                     try {
                        this.parseServiceUuid(var1, var5, var6, 4, var10);
                        break;
                     } catch (Exception var17) {
                        RxBleLog.e(var17, "Unable to parse scan record: %s", LoggerUtil.bytesToHex(var1));
                        return new ScanRecordImplCompat(null, null, null, null, -1, Integer.MIN_VALUE, null, var1);
                     }
                  case 6:
                  case 7:
                     try {
                        this.parseServiceUuid(var1, var5, var6, 16, var10);
                        break;
                     } catch (Exception var18) {
                        RxBleLog.e(var18, "Unable to parse scan record: %s", LoggerUtil.bytesToHex(var1));
                        return new ScanRecordImplCompat(null, null, null, null, -1, Integer.MIN_VALUE, null, var1);
                     }
                  case 8:
                  case 9:
                     try {
                        var9 = new String(this.extractBytes(var1, var5, var6));
                        break;
                     } catch (Exception var19) {
                        RxBleLog.e(var19, "Unable to parse scan record: %s", LoggerUtil.bytesToHex(var1));
                        return new ScanRecordImplCompat(null, null, null, null, -1, Integer.MIN_VALUE, null, var1);
                     }
                  case 10:
                     var3 = var1[var5];
                     break;
                  default:
                     label99:
                     switch (var7) {
                        case 20:
                           try {
                              this.parseServiceSolicitationUuid(var1, var5, var6, 2, var11);
                              break;
                           } catch (Exception var20) {
                              RxBleLog.e(var20, "Unable to parse scan record: %s", LoggerUtil.bytesToHex(var1));
                              return new ScanRecordImplCompat(null, null, null, null, -1, Integer.MIN_VALUE, null, var1);
                           }
                        case 21:
                           try {
                              this.parseServiceSolicitationUuid(var1, var5, var6, 16, var11);
                              break;
                           } catch (Exception var21) {
                              RxBleLog.e(var21, "Unable to parse scan record: %s", LoggerUtil.bytesToHex(var1));
                              return new ScanRecordImplCompat(null, null, null, null, -1, Integer.MIN_VALUE, null, var1);
                           }
                        default:
                           switch (var7) {
                              case 31:
                                 try {
                                    this.parseServiceSolicitationUuid(var1, var5, var6, 4, var11);
                                    break label99;
                                 } catch (Exception var23) {
                                    RxBleLog.e(var23, "Unable to parse scan record: %s", LoggerUtil.bytesToHex(var1));
                                    return new ScanRecordImplCompat(null, null, null, null, -1, Integer.MIN_VALUE, null, var1);
                                 }
                              case 32:
                              case 33:
                                 break;
                              default:
                                 break label99;
                           }
                        case 22:
                           byte var26;
                           if (var7 == 32) {
                              var26 = 4;
                           } else if (var7 == 33) {
                              var26 = 16;
                           } else {
                              var26 = 2;
                           }

                           try {
                              var12.put(parseUuidFrom(this.extractBytes(var1, var5, var26)), this.extractBytes(var1, var5 + var26, var6 - var26));
                           } catch (Exception var22) {
                              RxBleLog.e(var22, "Unable to parse scan record: %s", LoggerUtil.bytesToHex(var1));
                              return new ScanRecordImplCompat(null, null, null, null, -1, Integer.MIN_VALUE, null, var1);
                           }
                     }
               }
            } else {
               try {
                  var13.put(((var1[var2 + 3] & 255) << 8) + (255 & var1[var5]), this.extractBytes(var1, var2 + 4, var8 - 3));
               } catch (Exception var15) {
                  RxBleLog.e(var15, "Unable to parse scan record: %s", LoggerUtil.bytesToHex(var1));
                  return new ScanRecordImplCompat(null, null, null, null, -1, Integer.MIN_VALUE, null, var1);
               }
            }

            var2 = var5 + var6;
         }

         label88: {
            try {
               if (!var10.isEmpty()) {
                  break label88;
               }
            } catch (Exception var24) {
               RxBleLog.e(var24, "Unable to parse scan record: %s", LoggerUtil.bytesToHex(var1));
               return new ScanRecordImplCompat(null, null, null, null, -1, Integer.MIN_VALUE, null, var1);
            }

            var10 = null;
         }

         try {
            return new ScanRecordImplCompat(var10, var11, var13, var12, var4, var3, var9, var1);
         } catch (Exception var14) {
            RxBleLog.e(var14, "Unable to parse scan record: %s", LoggerUtil.bytesToHex(var1));
            return new ScanRecordImplCompat(null, null, null, null, -1, Integer.MIN_VALUE, null, var1);
         }
      }
   }
}
