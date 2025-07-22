package com.polidea.rxandroidble2.internal.logger;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.util.CharacteristicPropertiesParser;

public final class LoggerUtilBluetoothServices_Factory implements Factory<LoggerUtilBluetoothServices> {
   private final Provider<CharacteristicPropertiesParser> characteristicPropertiesParserProvider;

   public LoggerUtilBluetoothServices_Factory(Provider<CharacteristicPropertiesParser> var1) {
      this.characteristicPropertiesParserProvider = var1;
   }

   public static LoggerUtilBluetoothServices_Factory create(Provider<CharacteristicPropertiesParser> var0) {
      return new LoggerUtilBluetoothServices_Factory(var0);
   }

   public static LoggerUtilBluetoothServices newInstance(CharacteristicPropertiesParser var0) {
      return new LoggerUtilBluetoothServices(var0);
   }

   public LoggerUtilBluetoothServices get() {
      return newInstance((CharacteristicPropertiesParser)this.characteristicPropertiesParserProvider.get());
   }
}
