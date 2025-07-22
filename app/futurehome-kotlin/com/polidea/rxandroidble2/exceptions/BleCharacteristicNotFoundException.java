package com.polidea.rxandroidble2.exceptions;

import java.util.UUID;

public class BleCharacteristicNotFoundException extends BleException {
   private final UUID characteristicUUID;

   public BleCharacteristicNotFoundException(UUID var1) {
      StringBuilder var2 = new StringBuilder("Characteristic not found with UUID ");
      var2.append(var1);
      super(var2.toString());
      this.characteristicUUID = var1;
   }

   public UUID getCharacteristicUUID() {
      return this.characteristicUUID;
   }

   @Deprecated
   public UUID getCharactersisticUUID() {
      return this.characteristicUUID;
   }
}
