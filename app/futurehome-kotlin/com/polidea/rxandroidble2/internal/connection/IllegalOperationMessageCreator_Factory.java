package com.polidea.rxandroidble2.internal.connection;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;
import com.polidea.rxandroidble2.internal.util.CharacteristicPropertiesParser;

public final class IllegalOperationMessageCreator_Factory implements Factory<IllegalOperationMessageCreator> {
   private final Provider<CharacteristicPropertiesParser> propertiesParserProvider;

   public IllegalOperationMessageCreator_Factory(Provider<CharacteristicPropertiesParser> var1) {
      this.propertiesParserProvider = var1;
   }

   public static IllegalOperationMessageCreator_Factory create(Provider<CharacteristicPropertiesParser> var0) {
      return new IllegalOperationMessageCreator_Factory(var0);
   }

   public static IllegalOperationMessageCreator newInstance(CharacteristicPropertiesParser var0) {
      return new IllegalOperationMessageCreator(var0);
   }

   public IllegalOperationMessageCreator get() {
      return newInstance((CharacteristicPropertiesParser)this.propertiesParserProvider.get());
   }
}
