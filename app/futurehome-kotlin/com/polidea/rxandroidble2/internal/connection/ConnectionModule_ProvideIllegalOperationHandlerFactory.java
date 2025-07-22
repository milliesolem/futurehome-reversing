package com.polidea.rxandroidble2.internal.connection;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import bleshadow.javax.inject.Provider;

public final class ConnectionModule_ProvideIllegalOperationHandlerFactory implements Factory<IllegalOperationHandler> {
   private final Provider<LoggingIllegalOperationHandler> loggingIllegalOperationHandlerProvider;
   private final Provider<Boolean> suppressOperationCheckProvider;
   private final Provider<ThrowingIllegalOperationHandler> throwingIllegalOperationHandlerProvider;

   public ConnectionModule_ProvideIllegalOperationHandlerFactory(
      Provider<Boolean> var1, Provider<LoggingIllegalOperationHandler> var2, Provider<ThrowingIllegalOperationHandler> var3
   ) {
      this.suppressOperationCheckProvider = var1;
      this.loggingIllegalOperationHandlerProvider = var2;
      this.throwingIllegalOperationHandlerProvider = var3;
   }

   public static ConnectionModule_ProvideIllegalOperationHandlerFactory create(
      Provider<Boolean> var0, Provider<LoggingIllegalOperationHandler> var1, Provider<ThrowingIllegalOperationHandler> var2
   ) {
      return new ConnectionModule_ProvideIllegalOperationHandlerFactory(var0, var1, var2);
   }

   public static IllegalOperationHandler provideIllegalOperationHandler(
      boolean var0, Provider<LoggingIllegalOperationHandler> var1, Provider<ThrowingIllegalOperationHandler> var2
   ) {
      return (IllegalOperationHandler)Preconditions.checkNotNullFromProvides(ConnectionModule.provideIllegalOperationHandler(var0, var1, var2));
   }

   public IllegalOperationHandler get() {
      return provideIllegalOperationHandler(
         (Boolean)this.suppressOperationCheckProvider.get(), this.loggingIllegalOperationHandlerProvider, this.throwingIllegalOperationHandlerProvider
      );
   }
}
