package com.polidea.rxandroidble2.internal.logger;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import com.polidea.rxandroidble2.internal.RxBleLog;
import com.polidea.rxandroidble2.internal.operations.Operation;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class LoggerUtil {
   private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

   private LoggerUtil() {
   }

   public static String bytesToHex(byte[] var0) {
      if (var0 == null) {
         return "null";
      } else if (!RxBleLog.getShouldLogAttributeValues()) {
         return "[...]";
      } else {
         int var4 = var0.length;
         if (var4 == 0) {
            return "[]";
         } else {
            int var3 = var4 - 1;
            int var2 = var4 * 2 + var3 * 2;
            char[] var7 = new char[var2 + 2];

            for (int var1 = 0; var1 < var4; var1++) {
               byte var5 = var0[var1];
               int var6 = var1 * 2;
               var6 = var6 + 1 + var6;
               char[] var8 = HEX_ARRAY;
               var7[var6] = var8[(var5 & 255) >>> 4];
               var7[var6 + 1] = var8[var5 & 15];
            }

            for (int var9 = 0; var9 < var3; var9++) {
               var4 = var9 * 2;
               var4 = var4 + 1 + var4;
               var7[var4 + 2] = ',';
               var7[var4 + 3] = ' ';
            }

            var7[0] = '[';
            var7[var2 + 1] = ']';
            return new String(var7);
         }
      }
   }

   private static String commonCallbackMessage() {
      return " %24s()";
   }

   public static String commonMacMessage(BluetoothGatt var0) {
      return var0 == null ? "MAC=null" : commonMacMessage(var0.getDevice().getAddress());
   }

   public static String commonMacMessage(String var0) {
      if (var0 == null) {
         return "MAC=null";
      } else {
         int var1 = RxBleLog.getMacAddressLogSetting();
         if (var1 != 3) {
            if (var1 == Integer.MAX_VALUE) {
               var0 = "XX:XX:XX:XX:XX:XX";
            }
         } else {
            StringBuilder var2 = new StringBuilder();
            var2.append(var0.substring(0, 15));
            var2.append("XX");
            var0 = var2.toString();
         }

         return String.format("MAC='%s'", var0);
      }
   }

   private static String commonStatusMessage() {
      return ", status=%d";
   }

   private static String commonValueMessage() {
      return ", value=%s";
   }

   public static String getUuidSetToLog(Set<UUID> var0) {
      int var2 = var0.size();
      String[] var3 = new String[var2];
      Iterator var4 = var0.iterator();

      for (int var1 = 0; var1 < var2; var1++) {
         var3[var1] = getUuidToLog((UUID)var4.next());
      }

      return Arrays.toString((Object[])var3);
   }

   public static String getUuidToLog(UUID var0) {
      return RxBleLog.getUuidLogSetting() == 2 ? var0.toString() : "...";
   }

   public static void logCallback(String var0, BluetoothGatt var1, int var2) {
      if (RxBleLog.isAtLeast(4)) {
         StringBuilder var3 = new StringBuilder();
         var3.append(commonMacMessage(var1));
         var3.append(commonCallbackMessage());
         var3.append(commonStatusMessage());
         RxBleLog.i(var3.toString(), var0, var2);
      }
   }

   public static void logCallback(String var0, BluetoothGatt var1, int var2, int var3) {
      if (RxBleLog.isAtLeast(4)) {
         StringBuilder var4 = new StringBuilder();
         var4.append(commonMacMessage(var1));
         var4.append(commonCallbackMessage());
         var4.append(commonStatusMessage());
         var4.append(commonValueMessage());
         RxBleLog.i(var4.toString(), var0, var2, var3);
      }
   }

   public static void logCallback(String var0, BluetoothGatt var1, int var2, BluetoothGattCharacteristic var3, boolean var4) {
      if (RxBleLog.isAtLeast(4)) {
         LoggerUtil.AttributeLogWrapper var6 = new LoggerUtil.AttributeLogWrapper(var3.getUuid(), var3.getValue(), var4);
         StringBuilder var5 = new StringBuilder();
         var5.append(commonMacMessage(var1));
         var5.append(commonCallbackMessage());
         var5.append(commonStatusMessage());
         var5.append(commonValueMessage());
         RxBleLog.i(var5.toString(), var0, var2, var6);
      }
   }

   public static void logCallback(String var0, BluetoothGatt var1, int var2, BluetoothGattDescriptor var3, boolean var4) {
      if (RxBleLog.isAtLeast(4)) {
         LoggerUtil.AttributeLogWrapper var6 = new LoggerUtil.AttributeLogWrapper(var3.getUuid(), var3.getValue(), var4);
         StringBuilder var5 = new StringBuilder();
         var5.append(commonMacMessage(var1));
         var5.append(commonCallbackMessage());
         var5.append(commonStatusMessage());
         var5.append(commonValueMessage());
         RxBleLog.i(var5.toString(), var0, var2, var6);
      }
   }

   public static void logCallback(String var0, BluetoothGatt var1, BluetoothGattCharacteristic var2, boolean var3) {
      if (RxBleLog.isAtLeast(4)) {
         LoggerUtil.AttributeLogWrapper var4 = new LoggerUtil.AttributeLogWrapper(var2.getUuid(), var2.getValue(), var3);
         StringBuilder var5 = new StringBuilder();
         var5.append(commonMacMessage(var1));
         var5.append(commonCallbackMessage());
         var5.append(commonValueMessage());
         RxBleLog.i(var5.toString(), var0, var4);
      }
   }

   public static void logConnectionUpdateCallback(String var0, BluetoothGatt var1, int var2, int var3, int var4, int var5) {
      if (RxBleLog.isAtLeast(4)) {
         StringBuilder var6 = new StringBuilder();
         var6.append(commonMacMessage(var1));
         var6.append(commonCallbackMessage());
         var6.append(commonStatusMessage());
         var6.append(", interval=%d (%.2f ms), latency=%d, timeout=%d (%.0f ms)");
         RxBleLog.i(var6.toString(), var0, var2, var3, var3 * 1.25F, var4, var5, var5 * 10.0F);
      }
   }

   public static void logOperationFinished(Operation var0, long var1, long var3) {
      if (RxBleLog.isAtLeast(3)) {
         RxBleLog.d("FINISHED %s(%d) in %d ms", var0.getClass().getSimpleName(), System.identityHashCode(var0), var3 - var1);
      }
   }

   public static void logOperationQueued(Operation var0) {
      if (RxBleLog.isAtLeast(3)) {
         RxBleLog.d("QUEUED   %s(%d)", var0.getClass().getSimpleName(), System.identityHashCode(var0));
      }
   }

   public static void logOperationRemoved(Operation var0) {
      if (RxBleLog.isAtLeast(3)) {
         RxBleLog.d("REMOVED  %s(%d)", var0.getClass().getSimpleName(), System.identityHashCode(var0));
      }
   }

   public static void logOperationRunning(Operation var0) {
      RxBleLog.i("RUNNING  %s", var0);
   }

   public static void logOperationSkippedBecauseDisposedWhenAboutToRun(Operation var0) {
      if (RxBleLog.isAtLeast(2)) {
         RxBleLog.v("SKIPPED  %s(%d) just before running — is disposed", var0.getClass().getSimpleName(), System.identityHashCode(var0));
      }
   }

   public static void logOperationStarted(Operation var0) {
      if (RxBleLog.isAtLeast(3)) {
         RxBleLog.d("STARTED  %s(%d)", var0.getClass().getSimpleName(), System.identityHashCode(var0));
      }
   }

   public static LoggerUtil.AttributeLogWrapper wrap(BluetoothGattCharacteristic var0, boolean var1) {
      return new LoggerUtil.AttributeLogWrapper(var0.getUuid(), var0.getValue(), var1);
   }

   public static LoggerUtil.AttributeLogWrapper wrap(BluetoothGattDescriptor var0, boolean var1) {
      return new LoggerUtil.AttributeLogWrapper(var0.getUuid(), var0.getValue(), var1);
   }

   public static class AttributeLogWrapper {
      private final UUID uuid;
      private final byte[] value;
      private final boolean valueMatters;

      public AttributeLogWrapper(UUID var1, byte[] var2, boolean var3) {
         this.uuid = var1;
         this.value = var2;
         this.valueMatters = var3;
      }

      @Override
      public String toString() {
         StringBuilder var2 = new StringBuilder("[uuid='");
         var2.append(LoggerUtil.getUuidToLog(this.uuid));
         String var3;
         if (this.valueMatters) {
            StringBuilder var1 = new StringBuilder("', hexValue=");
            var1.append(LoggerUtil.bytesToHex(this.value));
            var3 = var1.toString();
         } else {
            var3 = "'";
         }

         var2.append(var3);
         var2.append(']');
         return var2.toString();
      }
   }
}
