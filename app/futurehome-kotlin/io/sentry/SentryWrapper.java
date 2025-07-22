package io.sentry;

import j..util.function.Supplier;
import java.util.concurrent.Callable;

public final class SentryWrapper {
   public static <U> Callable<U> wrapCallable(Callable<U> var0) {
      return new SentryWrapper$$ExternalSyntheticLambda0(Sentry.getCurrentHub().clone(), var0);
   }

   public static <U> Supplier<U> wrapSupplier(Supplier<U> var0) {
      return new SentryWrapper$$ExternalSyntheticLambda1(Sentry.getCurrentHub().clone(), var0);
   }
}
