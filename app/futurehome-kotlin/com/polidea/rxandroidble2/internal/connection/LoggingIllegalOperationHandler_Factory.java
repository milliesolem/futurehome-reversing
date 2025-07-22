package com.polidea.rxandroidble2.internal.connection;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;

public final class LoggingIllegalOperationHandler_Factory implements Factory<LoggingIllegalOperationHandler> {
   private final Provider<IllegalOperationMessageCreator> messageCreatorProvider;

   public LoggingIllegalOperationHandler_Factory(Provider<IllegalOperationMessageCreator> var1) {
      this.messageCreatorProvider = var1;
   }

   public static LoggingIllegalOperationHandler_Factory create(Provider<IllegalOperationMessageCreator> var0) {
      return new LoggingIllegalOperationHandler_Factory(var0);
   }

   public static LoggingIllegalOperationHandler newInstance(IllegalOperationMessageCreator var0) {
      return new LoggingIllegalOperationHandler(var0);
   }

   public LoggingIllegalOperationHandler get() {
      return newInstance((IllegalOperationMessageCreator)this.messageCreatorProvider.get());
   }
}
