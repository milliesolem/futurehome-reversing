package com.polidea.rxandroidble2.internal.connection;

import bleshadow.dagger.internal.Factory;
import bleshadow.javax.inject.Provider;

public final class IllegalOperationChecker_Factory implements Factory<IllegalOperationChecker> {
   private final Provider<IllegalOperationHandler> resultHandlerProvider;

   public IllegalOperationChecker_Factory(Provider<IllegalOperationHandler> var1) {
      this.resultHandlerProvider = var1;
   }

   public static IllegalOperationChecker_Factory create(Provider<IllegalOperationHandler> var0) {
      return new IllegalOperationChecker_Factory(var0);
   }

   public static IllegalOperationChecker newInstance(IllegalOperationHandler var0) {
      return new IllegalOperationChecker(var0);
   }

   public IllegalOperationChecker get() {
      return newInstance((IllegalOperationHandler)this.resultHandlerProvider.get());
   }
}
