package com.polidea.rxandroidble2.exceptions;

import android.bluetooth.BluetoothGattCharacteristic;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class BleCannotSetCharacteristicNotificationException extends BleException {
   public static final int CANNOT_FIND_CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR = 2;
   public static final int CANNOT_SET_LOCAL_NOTIFICATION = 1;
   public static final int CANNOT_WRITE_CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR = 3;
   @Deprecated
   public static final int UNKNOWN = -1;
   private final BluetoothGattCharacteristic bluetoothGattCharacteristic;
   private final int reason;

   @Deprecated
   public BleCannotSetCharacteristicNotificationException(BluetoothGattCharacteristic var1) {
      super(createMessage(var1, -1));
      this.bluetoothGattCharacteristic = var1;
      this.reason = -1;
   }

   public BleCannotSetCharacteristicNotificationException(BluetoothGattCharacteristic var1, int var2, Throwable var3) {
      super(createMessage(var1, var2), var3);
      this.bluetoothGattCharacteristic = var1;
      this.reason = var2;
   }

   private static String createMessage(BluetoothGattCharacteristic var0, int var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(reasonDescription(var1));
      var2.append(" (code ");
      var2.append(var1);
      var2.append(") with characteristic UUID ");
      var2.append(var0.getUuid());
      return var2.toString();
   }

   private static String reasonDescription(int var0) {
      if (var0 != 1) {
         if (var0 != 2) {
            return var0 != 3 ? "Unknown error" : "Cannot write client characteristic config descriptor";
         } else {
            return "Cannot find client characteristic config descriptor";
         }
      } else {
         return "Cannot set local notification";
      }
   }

   public BluetoothGattCharacteristic getBluetoothGattCharacteristic() {
      return this.bluetoothGattCharacteristic;
   }

   public int getReason() {
      return this.reason;
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface Reason {
   }
}
