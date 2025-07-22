package com.polidea.rxandroidble2.internal;

import java.util.UUID;

public class BleIllegalOperationException extends RuntimeException {
   public final UUID characteristicUUID;
   public final int neededProperties;
   public final int supportedProperties;

   public BleIllegalOperationException(String var1, UUID var2, int var3, int var4) {
      super(var1);
      this.characteristicUUID = var2;
      this.supportedProperties = var3;
      this.neededProperties = var4;
   }
}
