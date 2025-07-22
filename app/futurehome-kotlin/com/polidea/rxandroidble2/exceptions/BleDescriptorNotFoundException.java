package com.polidea.rxandroidble2.exceptions;

import java.util.UUID;

public class BleDescriptorNotFoundException extends BleException {
   private final UUID descriptorUUID;

   public BleDescriptorNotFoundException(UUID var1) {
      StringBuilder var2 = new StringBuilder("Descriptor not found with UUID ");
      var2.append(var1);
      super(var2.toString());
      this.descriptorUUID = var1;
   }

   public UUID getDescriptorUUID() {
      return this.descriptorUUID;
   }
}
