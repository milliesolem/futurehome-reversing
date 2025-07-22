package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGattCharacteristic;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.internal.logger.LoggerUtil;
import com.polidea.rxandroidble2.internal.util.CharacteristicPropertiesParser;
import java.util.Locale;

public class IllegalOperationMessageCreator {
   private final CharacteristicPropertiesParser propertiesParser;

   @Inject
   public IllegalOperationMessageCreator(CharacteristicPropertiesParser var1) {
      this.propertiesParser = var1;
   }

   public String createMismatchMessage(BluetoothGattCharacteristic var1, int var2) {
      return String.format(
         Locale.getDefault(),
         "Characteristic %s supports properties: %s (%d) does not have any property matching %s (%d)",
         LoggerUtil.getUuidToLog(var1.getUuid()),
         this.propertiesParser.propertiesIntToString(var1.getProperties()),
         var1.getProperties(),
         this.propertiesParser.propertiesIntToString(var2),
         var2
      );
   }
}
