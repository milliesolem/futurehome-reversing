package com.polidea.rxandroidble2.internal.connection;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import com.polidea.rxandroidble2.internal.util.CharacteristicPropertiesParser;

public final class ConnectionModule_ProvideCharacteristicPropertiesParserFactory implements Factory<CharacteristicPropertiesParser> {
   public static ConnectionModule_ProvideCharacteristicPropertiesParserFactory create() {
      return ConnectionModule_ProvideCharacteristicPropertiesParserFactory.InstanceHolder.INSTANCE;
   }

   public static CharacteristicPropertiesParser provideCharacteristicPropertiesParser() {
      return (CharacteristicPropertiesParser)Preconditions.checkNotNullFromProvides(ConnectionModule.provideCharacteristicPropertiesParser());
   }

   public CharacteristicPropertiesParser get() {
      return provideCharacteristicPropertiesParser();
   }

   private static final class InstanceHolder {
      private static final ConnectionModule_ProvideCharacteristicPropertiesParserFactory INSTANCE = new ConnectionModule_ProvideCharacteristicPropertiesParserFactory();
   }
}
