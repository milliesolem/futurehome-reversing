package com.polidea.rxandroidble2.exceptions;

import android.bluetooth.BluetoothGatt;
import com.polidea.rxandroidble2.internal.logger.LoggerUtil;
import com.polidea.rxandroidble2.utils.GattStatusParser;

public class BleGattException extends BleException {
   public static final int UNKNOWN_STATUS = -1;
   private final BleGattOperationType bleGattOperationType;
   private final BluetoothGatt gatt;
   private final int status;

   @Deprecated
   public BleGattException(int var1, BleGattOperationType var2) {
      super(createMessage(null, var1, var2));
      this.gatt = null;
      this.status = var1;
      this.bleGattOperationType = var2;
   }

   public BleGattException(BluetoothGatt var1, int var2, BleGattOperationType var3) {
      super(createMessage(var1, var2, var3));
      this.gatt = var1;
      this.status = var2;
      this.bleGattOperationType = var3;
   }

   public BleGattException(BluetoothGatt var1, BleGattOperationType var2) {
      this(var1, -1, var2);
   }

   private static String createMessage(BluetoothGatt var0, int var1, BleGattOperationType var2) {
      if (var1 == -1) {
         return String.format("GATT exception from MAC address %s, with type %s", getMacAddress(var0), var2);
      } else {
         String var3 = GattStatusParser.getGattCallbackStatusDescription(var1);
         return String.format(
            "GATT exception from %s, status %d (%s), type %s. (Look up status 0x%02x here %s)",
            LoggerUtil.commonMacMessage(var0),
            var1,
            var3,
            var2,
            var1,
            "https://cs.android.com/android/platform/superproject/+/master:packages/modules/Bluetooth/system/stack/include/gatt_api.h"
         );
      }
   }

   private static String getMacAddress(BluetoothGatt var0) {
      String var1;
      if (var0 != null && var0.getDevice() != null) {
         var1 = var0.getDevice().getAddress();
      } else {
         var1 = null;
      }

      return var1;
   }

   public BleGattOperationType getBleGattOperationType() {
      return this.bleGattOperationType;
   }

   public String getMacAddress() {
      return getMacAddress(this.gatt);
   }

   public int getStatus() {
      return this.status;
   }
}
