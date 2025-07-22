package com.polidea.rxandroidble2.exceptions;

import java.util.UUID;

public class BleServiceNotFoundException extends BleException {
   private final UUID serviceUUID;

   public BleServiceNotFoundException(UUID var1) {
      StringBuilder var2 = new StringBuilder("BLE Service not found with UUID ");
      var2.append(var1);
      super(var2.toString());
      this.serviceUUID = var1;
   }

   public UUID getServiceUUID() {
      return this.serviceUUID;
   }
}
