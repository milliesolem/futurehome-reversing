package com.polidea.rxandroidble2.exceptions;

public class BleGattOperationType {
   public static final BleGattOperationType CHARACTERISTIC_CHANGED = new BleGattOperationType("CHARACTERISTIC_CHANGED");
   public static final BleGattOperationType CHARACTERISTIC_LONG_WRITE = new BleGattOperationType("CHARACTERISTIC_LONG_WRITE");
   public static final BleGattOperationType CHARACTERISTIC_READ = new BleGattOperationType("CHARACTERISTIC_READ");
   public static final BleGattOperationType CHARACTERISTIC_WRITE = new BleGattOperationType("CHARACTERISTIC_WRITE");
   public static final BleGattOperationType CONNECTION_PRIORITY_CHANGE = new BleGattOperationType("CONNECTION_PRIORITY_CHANGE");
   public static final BleGattOperationType CONNECTION_STATE = new BleGattOperationType("CONNECTION_STATE");
   public static final BleGattOperationType DESCRIPTOR_READ = new BleGattOperationType("DESCRIPTOR_READ");
   public static final BleGattOperationType DESCRIPTOR_WRITE = new BleGattOperationType("DESCRIPTOR_WRITE");
   public static final BleGattOperationType ON_MTU_CHANGED = new BleGattOperationType("ON_MTU_CHANGED");
   public static final BleGattOperationType READ_RSSI = new BleGattOperationType("READ_RSSI");
   public static final BleGattOperationType RELIABLE_WRITE_COMPLETED = new BleGattOperationType("RELIABLE_WRITE_COMPLETED");
   public static final BleGattOperationType SERVICE_DISCOVERY = new BleGattOperationType("SERVICE_DISCOVERY");
   private final String description;

   private BleGattOperationType(String var1) {
      this.description = var1;
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder("BleGattOperation{description='");
      var1.append(this.description);
      var1.append("'}");
      return var1.toString();
   }
}
