package com.polidea.rxandroidble2.exceptions;

import java.util.UUID;

public class BleConflictingNotificationAlreadySetException extends BleException {
   private final boolean alreadySetIsIndication;
   private final UUID characteristicUuid;

   public BleConflictingNotificationAlreadySetException(UUID var1, boolean var2) {
      StringBuilder var4 = new StringBuilder("Characteristic ");
      var4.append(var1);
      var4.append(" notification already set to ");
      String var3;
      if (var2) {
         var3 = "indication";
      } else {
         var3 = "notification";
      }

      var4.append(var3);
      super(var4.toString());
      this.characteristicUuid = var1;
      this.alreadySetIsIndication = var2;
   }

   public UUID getCharacteristicUuid() {
      return this.characteristicUuid;
   }

   public boolean indicationAlreadySet() {
      return this.alreadySetIsIndication;
   }

   public boolean notificationAlreadySet() {
      return this.alreadySetIsIndication ^ true;
   }
}
