package com.polidea.rxandroidble2.internal.connection;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;

public final class ThrowingIllegalOperationHandler_Factory implements Factory<ThrowingIllegalOperationHandler> {
   private final Provider<IllegalOperationMessageCreator> messageCreatorProvider;

   public ThrowingIllegalOperationHandler_Factory(Provider<IllegalOperationMessageCreator> var1) {
      this.messageCreatorProvider = var1;
   }

   public static ThrowingIllegalOperationHandler_Factory create(Provider<IllegalOperationMessageCreator> var0) {
      return new ThrowingIllegalOperationHandler_Factory(var0);
   }

   public static ThrowingIllegalOperationHandler newInstance(IllegalOperationMessageCreator var0) {
      return new ThrowingIllegalOperationHandler(var0);
   }

   public ThrowingIllegalOperationHandler get() {
      return newInstance((IllegalOperationMessageCreator)this.messageCreatorProvider.get());
   }
}
